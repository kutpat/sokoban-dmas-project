/**
 * $Id$
 * 
 * Copyright (c) 2015-17 Stephane GALLAND.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * This program is free software; you can redistribute it and/or modify
 */
package sokoban.environment.maze;

import framework.math.Point2i;
import io.sarl.lang.core.annotation.SarlElementType;
import io.sarl.lang.core.annotation.SarlSpecification;
import io.sarl.lang.core.annotation.SyntheticMember;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;

/**
 * Sokoban puzzle environment - a discrete, grid-based state transformation world.
 * 
 * IMPORTANT: This is NOT a maze. Sokoban is about state transformation, not navigation.
 * 
 * Key differences from mazes:
 * - Maze: Tests where you can go (navigation problem)
 * - Sokoban: Tests whether you should go there (state transformation problem)
 * 
 * Characteristics:
 * - Discrete 2D grid (each cell contains exactly one thing)
 * - Fully observable (no fog of war, no randomness, no hidden state)
 * - Deterministic (same action from same state → same result)
 * - State changes permanently with every move (often irreversible)
 * - Goal: All boxes on target cells (player position irrelevant)
 * 
 * Static elements: Walls (immovable), Target locations (goals)
 * Dynamic elements: Player (agent), Boxes (pushable artifacts)
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@SarlSpecification("0.15")
@SarlElementType(10)
@XbaseGenerated
@SuppressWarnings("all")
public class Maze {
  /**
   * Max number of super pills in the world.
   */
  private static final int MAX_SUPER_PILLS = 5;

  /**
   * Matrix of the objects.
   */
  private final sokobanObject[][] grid;

  private final TreeMap<UUID, AgentBody> bodies = new TreeMap<UUID, AgentBody>();

  /**
   * Set of exit positions (cell properties, not objects).
   * Exits are stored as cell coordinates, allowing boxes to be placed on them.
   */
  private final HashSet<Point2i> exitPositions = CollectionLiterals.<Point2i>newHashSet();

  /**
   * Width of the world.
   */
  private final int width;

  /**
   * Height of the world.
   */
  private final int height;

  /**
   * Random generator.
   */
  private final Random random = new Random();

  /**
   * Track which agent-count layout is currently active.
   */
  private int activeAgentLayoutCount = 0;

  /**
   * @param width is the width of the world.
   * @param height is the height of the world.
   */
  public Maze(final int width, final int height) {
    this.width = width;
    this.height = height;
    Object _newInstance = Array.newInstance(sokobanObject.class, width, height);
    this.grid = ((sokobanObject[][]) _newInstance);
    this.buildWorld(this.width, this.height);
  }

  /**
   * Build the Sokoban puzzle world.
   * 
   * Creates a Sokoban-style puzzle layout with deterministic wall patterns:
   * - Outer walls around the perimeter
   * - One of 3 predefined simple wall layouts (selected at initialization)
   * - Walls are sparse and don't block all paths
   * 
   * @param width width of the puzzle grid.
   * @param height height of the puzzle grid.
   */
  private void buildWorld(final int width, final int height) {
    this.buildSimpleOpenLayout();
  }

  /**
   * Build a randomly generated Sokoban layout.
   * 
   * Creates a Sokoban-style puzzle with:
   * - Outer walls around edges
   * - Random wall placement inside (20-30% wall density)
   * - Ensures connectivity (all areas reachable)
   * - Walkable paths for agents and boxes
   */
  private void buildRandomSokobanLayout() {
    for (int i = 0; (i < this.width); i++) {
      for (int j = 0; (j < this.height); j++) {
        WallObject _wallObject = new WallObject(i, j, this);
        this.grid[i][j] = _wallObject;
      }
    }
    for (int i = 1; (i < (this.width - 1)); i++) {
      for (int j = 1; (j < (this.height - 1)); j++) {
        double _nextDouble = this.random.nextDouble();
        if ((_nextDouble < 0.25)) {
          WallObject _wallObject = new WallObject(i, j, this);
          this.grid[i][j] = _wallObject;
        } else {
          this.grid[i][j] = null;
        }
      }
    }
    this.ensureConnectivity();
    this.ensureMinimumWalkableArea();
  }

  /**
   * Build a simple open layout with NO INNER WALLS (for testing).
   * 
   * Creates a completely open environment:
   * - Outer walls around edges only
   * - All inner cells are empty (walkable)
   * - No obstacles to block movement
   * 
   * This makes it easier to test if agents can move and coordinate.
   */
  private void buildSimpleOpenLayout() {
    for (int i = 0; (i < this.width); i++) {
      for (int j = 0; (j < this.height); j++) {
        WallObject _wallObject = new WallObject(i, j, this);
        this.grid[i][j] = _wallObject;
      }
    }
    for (int i = 1; (i < (this.width - 1)); i++) {
      for (int j = 1; (j < (this.height - 1)); j++) {
        this.grid[i][j] = null;
      }
    }
  }

  /**
   * Place a wall at the given position if it's safe.
   * 
   * @param x the x coordinate
   * @param y the y coordinate
   * @return true if wall was placed, false if position is invalid
   */
  private boolean placeWall(final int x, final int y) {
    if (((((x <= 1) || (x >= (this.width - 2))) || (y <= 1)) || (y >= (this.height - 2)))) {
      return false;
    }
    Point2i _point2i = new Point2i(x, y);
    boolean _contains = this.exitPositions.contains(_point2i);
    if (_contains) {
      return false;
    }
    sokobanObject _get = this.grid[x][y];
    if ((_get != null)) {
      return false;
    }
    WallObject _wallObject = new WallObject(x, y, this);
    this.grid[x][y] = _wallObject;
    return true;
  }

  /**
   * Clear inner walls while preserving outer borders and objects.
   */
  private void clearInnerWalls() {
    for (int i = 1; (i < (this.width - 1)); i++) {
      for (int j = 1; (j < (this.height - 1)); j++) {
        Object _get = this.grid[i][j];
        if ((_get instanceof WallObject)) {
          this.grid[i][j] = null;
        }
      }
    }
  }

  /**
   * Place a straight line of walls.
   */
  private void placeWallLine(final int startX, final int startY, final int dx, final int dy, final int length) {
    for (int i = 0; (i < length); i++) {
      {
        int x = (startX + (dx * i));
        int y = (startY + (dy * i));
        this.placeWall(x, y);
      }
    }
  }

  /**
   * Place a small rectangular block of walls.
   */
  private void placeWallBlock(final int startX, final int startY, final int blockWidth, final int blockHeight) {
    for (int i = 0; (i < blockWidth); i++) {
      for (int j = 0; (j < blockHeight); j++) {
        this.placeWall((startX + i), (startY + j));
      }
    }
  }

  /**
   * Place a vertical wall segment with one or two "doors" (gaps).
   */
  private void placeVerticalWallWithDoors(final int x, final int yStart, final int yEnd, final int doorY1, final int doorY2) {
    int ys = Math.max(2, Math.min(yStart, (this.height - 3)));
    int ye = Math.max(2, Math.min(yEnd, (this.height - 3)));
    if ((ys > ye)) {
      int tmp = ys;
      ys = ye;
      ye = tmp;
    }
    for (int y = ys; (y <= ye); y++) {
      if (((y != doorY1) && (y != doorY2))) {
        this.placeWall(x, y);
      }
    }
  }

  /**
   * Place a horizontal wall segment with one or two "doors" (gaps).
   */
  private void placeHorizontalWallWithDoors(final int y, final int xStart, final int xEnd, final int doorX1, final int doorX2) {
    int xs = Math.max(2, Math.min(xStart, (this.width - 3)));
    int xe = Math.max(2, Math.min(xEnd, (this.width - 3)));
    if ((xs > xe)) {
      int tmp = xs;
      xs = xe;
      xe = tmp;
    }
    for (int x = xs; (x <= xe); x++) {
      if (((x != doorX1) && (x != doorX2))) {
        this.placeWall(x, y);
      }
    }
  }

  /**
   * Place a rectangular "room" perimeter (walls), with exactly one door on one side.
   * doorSide: 0=TOP, 1=RIGHT, 2=BOTTOM, 3=LEFT
   * doorOffset is along that side (from 1..(len-2)), auto-clamped.
   */
  private void placeRoom(final int x, final int y, final int w, final int h, final int doorSide, final int doorOffset) {
    int ww = Math.max(4, w);
    int hh = Math.max(4, h);
    int maxX0 = ((this.width - ww) - 2);
    int maxY0 = ((this.height - hh) - 2);
    int x0 = Math.max(2, Math.min(x, Math.max(2, maxX0)));
    int y0 = Math.max(2, Math.min(y, Math.max(2, maxY0)));
    int x1 = ((x0 + ww) - 1);
    int y1 = ((y0 + hh) - 1);
    int _max = Math.max(1, Math.min(doorOffset, (ww - 2)));
    int doorX = (x0 + _max);
    int _max_1 = Math.max(1, Math.min(doorOffset, (hh - 2)));
    int doorY = (y0 + _max_1);
    for (int xx = x0; (xx <= x1); xx++) {
      {
        if ((!((doorSide == 0) && (xx == doorX)))) {
          this.placeWall(xx, y0);
        }
        if ((!((doorSide == 2) && (xx == doorX)))) {
          this.placeWall(xx, y1);
        }
      }
    }
    for (int yy = y0; (yy <= y1); yy++) {
      {
        if ((!((doorSide == 3) && (yy == doorY)))) {
          this.placeWall(x0, yy);
        }
        if ((!((doorSide == 1) && (yy == doorY)))) {
          this.placeWall(x1, yy);
        }
      }
    }
  }

  /**
   * Builds a classic “maze columns” pattern:
   * vertical wall columns with alternating door gaps → long winding corridors.
   */
  private void buildAlternatingColumnsMaze(final int step, final int gapSize, final int startX) {
    if (((this.width < 9) || (this.height < 9))) {
      return;
    }
    int s = Math.max(3, step);
    int g = Math.max(1, gapSize);
    int x = Math.max(3, startX);
    boolean flip = false;
    while ((x < (this.width - 3))) {
      {
        int gapTop = Math.max(3, (this.height / 4));
        int gapBottom = Math.max(3, ((this.height - 4) - g));
        int _xifexpression = (int) 0;
        if (flip) {
          _xifexpression = gapBottom;
        } else {
          _xifexpression = gapTop;
        }
        int doorStart = _xifexpression;
        int doorY1 = doorStart;
        int doorY2 = (doorStart + g);
        this.placeVerticalWallWithDoors(x, 2, (this.height - 3), doorY1, doorY2);
        flip = (!flip);
        x = (x + s);
      }
    }
  }

  /**
   * Sprinkle small 2x2 pillars to reduce open space but keep movement possible.
   */
  private void sprinklePillars(final int count) {
    int c = Math.max(0, count);
    for (int k = 0; (k < c); k++) {
      {
        int _nextInt = this.random.nextInt(Math.max(1, (this.width - 5)));
        int px = (2 + _nextInt);
        int _nextInt_1 = this.random.nextInt(Math.max(1, (this.height - 5)));
        int py = (2 + _nextInt_1);
        this.placeWallBlock(px, py, 2, 2);
      }
    }
  }

  private Point2i findRandomEmptyInnerCell(final boolean avoidExitCells) {
    int tries = 0;
    while ((tries < 5000)) {
      {
        int _nextInt = this.random.nextInt(Math.max(1, (this.width - 2)));
        int x = (1 + _nextInt);
        int _nextInt_1 = this.random.nextInt(Math.max(1, (this.height - 2)));
        int y = (1 + _nextInt_1);
        if (((((x <= 1) || (x >= (this.width - 2))) || (y <= 1)) || (y >= (this.height - 2)))) {
          tries++;
          continue;
        }
        sokobanObject _get = this.grid[x][y];
        if ((_get != null)) {
          tries++;
          continue;
        }
        Point2i p = new Point2i(x, y);
        if ((avoidExitCells && this.exitPositions.contains(p))) {
          tries++;
          continue;
        }
        tries++;
        return p;
      }
    }
    for (int i = 2; (i < (this.width - 2)); i++) {
      for (int j = 2; (j < (this.height - 2)); j++) {
        sokobanObject _get = this.grid[i][j];
        if ((_get == null)) {
          Point2i p = new Point2i(i, j);
          if (((!avoidExitCells) || (!this.exitPositions.contains(p)))) {
            return p;
          }
        }
      }
    }
    return null;
  }

  /**
   * Update layout based on the current number of Sokoban agents.
   */
  private void updateLayoutForAgentCount(final int agentCount) {
    if ((agentCount <= 0)) {
      return;
    }
    int normalizedCount = Math.min(Math.max(agentCount, 1), 4);
    if ((normalizedCount == this.activeAgentLayoutCount)) {
      return;
    }
    this.clearInnerWalls();
    switch (normalizedCount) {
      case 1:
        int layoutIndex = this.random.nextInt(2);
        switch (layoutIndex) {
          case 0:
            this.buildLayout4();
            break;
          case 1:
            this.buildLayout5();
            break;
          default:
            this.buildLayout4();
            break;
        }
        break;
      case 2:
        int layoutIndex_1 = this.random.nextInt(2);
        switch (layoutIndex_1) {
          case 0:
            this.buildLayout4();
            break;
          case 1:
            this.buildLayout5();
            break;
          default:
            this.buildLayout4();
            break;
        }
        break;
      case 3:
        int layoutIndex_2 = this.random.nextInt(2);
        switch (layoutIndex_2) {
          case 0:
            this.buildLayout6();
            break;
          case 1:
            this.buildLayout7();
            break;
          default:
            this.buildLayout6();
            break;
        }
        break;
      case 4:
        int layoutIndex_3 = this.random.nextInt(2);
        switch (layoutIndex_3) {
          case 0:
            this.buildLayout8();
            break;
          case 1:
            this.buildLayout9();
            break;
          default:
            this.buildLayout8();
            break;
        }
        break;
      default:
        this.buildLayout0();
        break;
    }
    this.activeAgentLayoutCount = normalizedCount;
  }

  /**
   * Layout 0 (1 agent): Serpentine corridors + a few choke gates
   */
  private void buildLayout0() {
    this.buildAlternatingColumnsMaze(3, 1, 4);
    int yA = Math.max(4, (this.height / 3));
    int yB = Math.max(6, ((this.height * 2) / 3));
    int doorA = Math.max(4, (this.width / 4));
    int doorB = Math.max(6, ((this.width * 3) / 4));
    this.placeHorizontalWallWithDoors(yA, 2, (this.width - 3), doorA, (doorA + 1));
    this.placeHorizontalWallWithDoors(yB, 2, (this.width - 3), doorB, (doorB - 1));
    this.sprinklePillars(2);
  }

  /**
   * Layout 1 (1 agent): Spiral-ish nested rooms (maze feel without full blockage)
   */
  private void buildLayout1() {
    if (((this.width < 12) || (this.height < 12))) {
      this.buildAlternatingColumnsMaze(3, 1, 4);
      return;
    }
    int _min = Math.min(this.width, this.height);
    int layers = Math.min(((_min - 8) / 2), 4);
    if ((layers < 2)) {
      layers = 2;
    }
    for (int l = 0; (l < layers); l++) {
      {
        int inset = (2 + (l * 2));
        int rw = ((this.width - (inset * 2)) - 1);
        int rh = ((this.height - (inset * 2)) - 1);
        if (((rw < 6) || (rh < 6))) {
          break;
        }
        int doorSide = (l % 4);
        int _xifexpression = (int) 0;
        if (((doorSide == 0) || (doorSide == 2))) {
          _xifexpression = (rw / 2);
        } else {
          _xifexpression = (rh / 2);
        }
        int doorOffset = _xifexpression;
        this.placeRoom(inset, inset, rw, rh, doorSide, doorOffset);
      }
    }
    int midX = Math.max(5, (this.width / 2));
    int midY = Math.max(5, (this.height / 2));
    this.placeVerticalWallWithDoors(midX, 3, (this.height - 4), (midY - 1), (midY + 1));
  }

  /**
   * Layout 2 (1 agent): Three rooms connected by corridors (doors offset)
   */
  private void buildLayout2() {
    int rw = Math.max(6, (this.width / 3));
    int rh = Math.max(6, (this.height / 3));
    this.placeRoom(3, 3, rw, rh, 1, (rh / 2));
    this.placeRoom(Math.max(3, ((this.width - rw) - 3)), 3, rw, rh, 3, (rh / 2));
    this.placeRoom(Math.max(3, ((this.width / 2) - (rw / 2))), Math.max(3, ((this.height - rh) - 3)), rw, rh, 0, (rw / 2));
    int yC = Math.max(5, (this.height / 2));
    this.placeHorizontalWallWithDoors(yC, 2, (this.width - 3), (this.width / 2), ((this.width / 2) + 1));
    this.sprinklePillars(1);
  }

  /**
   * Layout 3 (1 agent): Dense alcoves + pockets (more dead ends)
   */
  private void buildLayout3() {
    this.buildAlternatingColumnsMaze(3, 1, 3);
    int y = 4;
    while ((y < (this.height - 4))) {
      {
        this.placeWallLine(3, y, 1, 0, Math.min(4, (this.width - 6)));
        this.placeWallLine(Math.max(4, (this.width - 6)), (y + 1), (-1), 0, Math.min(4, (this.width - 6)));
        y = (y + 4);
      }
    }
    this.sprinklePillars(2);
  }

  /**
   * Layout 4 (2 agents): Split map with multiple doors + zigzag inside each half
   */
  private void buildLayout4() {
    int splitX = Math.max(5, (this.width / 2));
    int laneX1 = Math.max(3, (this.width / 3));
    int laneX2 = Math.max(4, ((this.width * 2) / 3));
    this.placeWallLine(laneX1, 3, 0, 1, 4);
    this.placeWallLine(laneX2, Math.max(3, (this.height / 2)), 0, 1, 4);
    int midY = Math.max(3, (this.height / 2));
    this.placeWallLine(4, midY, 1, 0, 4);
    this.placeWallLine(13, 11, 1, 0, 8);
    this.placeWallBlock(3, 3, 2, 2);
  }

  /**
   * Layout 5 (2 agents): Two loops + cross choke points (forces coordination)
   */
  private void buildLayout5() {
    this.placeWallBlock(3, 3, 2, 2);
    int rightBlockX = Math.max(3, (this.width - 6));
    this.placeWallBlock(rightBlockX, 3, 2, 2);
    int yBridge = Math.max(5, (this.height / 2));
    int lowerY = Math.max(4, (this.height - 5));
    this.placeWallLine(5, lowerY, 1, 0, 4);
    this.placeHorizontalWallWithDoors(yBridge, 2, (this.width - 3), (this.width / 2), ((this.width / 2) + 1));
  }

  /**
   * Layout 6 (3 agents): Three chambers with narrow-ish connectors
   */
  private void buildLayout6() {
    int rw = Math.max(6, (this.width / 3));
    int rh = Math.max(6, (this.height / 3));
    int midX = Math.max(4, (this.width / 2));
    int midY = Math.max(4, (this.height / 2));
    this.placeWallBlock(3, (midY - 2), 2, 2);
    this.placeWallBlock((midX - 1), 3, 2, 2);
    this.placeWallBlock(Math.max(3, (this.width - 6)), Math.max(3, (this.height - 6)), 2, 2);
    this.placeWallLine((midX + 2), midY, 0, 1, 3);
  }

  /**
   * Layout 7 (3 agents): Dense zigzag maze (more corridors, fewer open plains)
   */
  private void buildLayout7() {
    this.placeWallLine(4, 4, 1, 0, 3);
    this.placeWallLine(6, 6, 1, 0, 3);
    this.placeWallLine(8, 8, 1, 0, 3);
    int rightX = Math.max(4, (this.width - 5));
    int midY = Math.max(4, (this.height / 2));
    this.placeWallLine(rightX, (midY - 1), 0, 1, 3);
  }

  /**
   * Layout 8 (4 agents): Four quadrants + doors + inner clutter
   */
  private void buildLayout8() {
    int midX = Math.max(5, (this.width / 2));
    int midY = Math.max(5, (this.height / 2));
    this.placeVerticalWallWithDoors(midX, 2, (this.height - 3), (midY - 2), (midY + 2));
    this.placeHorizontalWallWithDoors(midY, 2, (this.width - 3), (midX - 2), (midX + 2));
    this.buildAlternatingColumnsMaze(4, 1, 3);
    this.placeWallBlock(3, 3, 2, 2);
    this.placeWallBlock(Math.max(3, (this.width - 6)), 3, 2, 2);
    this.placeWallBlock(3, Math.max(3, (this.height - 6)), 2, 2);
    this.placeWallBlock(Math.max(3, (this.width - 6)), Math.max(3, (this.height - 6)), 2, 2);
  }

  /**
   * Layout 9 (4 agents): “Donut” ring + spokes (maze ring around center)
   */
  private void buildLayout9() {
    int topY = Math.max(4, ((this.height / 2) - 3));
    int bottomY = Math.max(4, ((this.height / 2) + 2));
    this.placeWallLine(4, topY, 1, 0, 5);
    this.placeWallLine(Math.max(4, (this.width - 9)), bottomY, 1, 0, 5);
    int leftX = Math.max(4, ((this.width / 2) - 3));
    int rightX = Math.max(4, ((this.width / 2) + 2));
    this.placeWallLine(leftX, 4, 0, 1, 4);
    this.placeWallLine(rightX, Math.max(4, (this.height - 8)), 0, 1, 4);
  }

  /**
   * Ensure all walkable cells are connected (reachable from each other).
   * Uses flood-fill algorithm to verify connectivity.
   */
  private void ensureConnectivity() {
    int startX = (-1);
    int startY = (-1);
    for (int i = 1; (i < (this.width - 1)); i++) {
      {
        for (int j = 1; (j < (this.height - 1)); j++) {
          sokobanObject _get = this.grid[i][j];
          if ((_get == null)) {
            startX = i;
            startY = j;
            break;
          }
        }
        if ((startX >= 0)) {
          break;
        }
      }
    }
    if ((startX < 0)) {
      for (int i = 1; (i < Math.min(5, (this.width - 1))); i++) {
        for (int j = 1; (j < Math.min(5, (this.height - 1))); j++) {
          this.grid[i][j] = null;
        }
      }
      return;
    }
    HashSet<Point2i> visited = new HashSet<Point2i>();
    LinkedList<Point2i> queue = new LinkedList<Point2i>();
    Point2i _point2i = new Point2i(startX, startY);
    queue.add(_point2i);
    Point2i _point2i_1 = new Point2i(startX, startY);
    visited.add(_point2i_1);
    Point2i _point2i_2 = new Point2i(0, (-1));
    Point2i _point2i_3 = new Point2i(0, 1);
    Point2i _point2i_4 = new Point2i((-1), 0);
    Point2i _point2i_5 = new Point2i(1, 0);
    final List<Point2i> directions = Collections.<Point2i>unmodifiableList(CollectionLiterals.<Point2i>newArrayList(_point2i_2, _point2i_3, _point2i_4, _point2i_5));
    while ((!queue.isEmpty())) {
      {
        Point2i current = queue.poll();
        for (final Point2i dir : directions) {
          {
            int _x = current.getX();
            int _x_1 = dir.getX();
            int nextX = (_x + _x_1);
            int _y = current.getY();
            int _y_1 = dir.getY();
            int nextY = (_y + _y_1);
            if (((((nextX >= 1) && (nextX < (this.width - 1))) && (nextY >= 1)) && (nextY < (this.height - 1)))) {
              Point2i nextPos = new Point2i(nextX, nextY);
              if (((!visited.contains(nextPos)) && (this.grid[nextX][nextY] == null))) {
                visited.add(nextPos);
                queue.add(nextPos);
              }
            }
          }
        }
      }
    }
    ArrayList<Point2i> unreachable = new ArrayList<Point2i>();
    for (int i = 1; (i < (this.width - 1)); i++) {
      for (int j = 1; (j < (this.height - 1)); j++) {
        sokobanObject _get = this.grid[i][j];
        if ((_get == null)) {
          Point2i pos = new Point2i(i, j);
          boolean _contains = visited.contains(pos);
          if ((!_contains)) {
            unreachable.add(pos);
          }
        }
      }
    }
    for (final Point2i unreachablePos : unreachable) {
      {
        Point2i nearestReachable = null;
        int minDist = Integer.MAX_VALUE;
        for (final Point2i reachablePos : visited) {
          {
            int _x = unreachablePos.getX();
            int _x_1 = reachablePos.getX();
            int _abs = Math.abs((_x - _x_1));
            int _y = unreachablePos.getY();
            int _y_1 = reachablePos.getY();
            int _abs_1 = Math.abs((_y - _y_1));
            int dist = (_abs + _abs_1);
            if ((dist < minDist)) {
              minDist = dist;
              nearestReachable = reachablePos;
            }
          }
        }
        if ((nearestReachable != null)) {
          this.createPath(unreachablePos, nearestReachable);
        }
      }
    }
  }

  /**
   * Create a path between two positions by removing walls.
   * Uses simple pathfinding to connect two points.
   */
  private void createPath(final Point2i from, final Point2i to) {
    int currentX = from.getX();
    int currentY = from.getY();
    int targetX = to.getX();
    int targetY = to.getY();
    while (((currentX != targetX) || (currentY != targetY))) {
      {
        Object _get = this.grid[currentX][currentY];
        if ((_get instanceof WallObject)) {
          this.grid[currentX][currentY] = null;
        }
        if ((currentX < targetX)) {
          currentX++;
        } else {
          if ((currentX > targetX)) {
            currentX--;
          } else {
            if ((currentY < targetY)) {
              currentY++;
            } else {
              if ((currentY > targetY)) {
                currentY--;
              }
            }
          }
        }
      }
    }
    Object _get = this.grid[currentX][currentY];
    if ((_get instanceof WallObject)) {
      this.grid[currentX][currentY] = null;
    }
  }

  /**
   * Ensure minimum walkable area (at least 50% of inner area).
   * Removes walls if walkable area is too small.
   */
  private void ensureMinimumWalkableArea() {
    int walkableCount = 0;
    int totalInnerArea = ((this.width - 2) * (this.height - 2));
    int minWalkable = ((int) (totalInnerArea * 0.5));
    for (int i = 1; (i < (this.width - 1)); i++) {
      for (int j = 1; (j < (this.height - 1)); j++) {
        sokobanObject _get = this.grid[i][j];
        if ((_get == null)) {
          walkableCount++;
        }
      }
    }
    if ((walkableCount < minWalkable)) {
      int wallsToRemove = (minWalkable - walkableCount);
      int removed = 0;
      for (int i = 1; ((i < (this.width - 1)) && (removed < wallsToRemove)); i++) {
        for (int j = 1; ((j < (this.height - 1)) && (removed < wallsToRemove)); j++) {
          Object _get = this.grid[i][j];
          if ((_get instanceof WallObject)) {
            this.grid[i][j] = null;
            removed++;
          }
        }
      }
    }
  }

  /**
   * Replies if the cell at the given coordinates can receive a body.
   * 
   * @param x
   * @param y
   * @return <code>true</code> if the cell is empty or has a pickable object; <code>false</code>
   * otherwise.
   */
  @Pure
  public synchronized boolean canMoveInside(final int x, final int y) {
    return (((((x >= 0) && (y >= 0)) && (x < this.width)) && (y < this.height)) && ((this.grid[x][y] == null) || this.grid[x][y].isPickable()));
  }

  /**
   * Create a body of the given type.
   * 
   * @param bodyType the type of the body.
   * @param agentId the identifier of the agent that will be linked to the body.
   * @param perceptionDistance the distance of perception.
   * @return the body.
   * @throws Exception if it is impossible to retrieve the body constructor or to create the instance.
   */
  public <T extends AgentBody> T createBody(final Class<T> bodyType, final UUID agentId, final int perceptionDistance) {
    try {
      int x = this.random.nextInt(this.width);
      int y = this.random.nextInt(this.height);
      while ((!this.canMoveInside(x, y))) {
        {
          x = this.random.nextInt(this.width);
          y = this.random.nextInt(this.height);
        }
      }
      UUID id = agentId;
      if ((id == null)) {
        id = UUID.randomUUID();
      }
      Constructor<T> cons = bodyType.getDeclaredConstructor(int.class, int.class, Maze.class, UUID.class, int.class);
      T body = cons.newInstance(Integer.valueOf(x), Integer.valueOf(y), this, id, Integer.valueOf(perceptionDistance));
      this.grid[x][y] = body;
      this.bodies.put(id, body);
      if ((body instanceof sokobanBody)) {
        this.updateLayoutForAgentCount(this.getSokobanBodyCount());
      }
      return body;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  /**
   * Count autonomous Sokoban agents (excludes ghosts).
   */
  @Pure
  private int getSokobanBodyCount() {
    int count = 0;
    Collection<AgentBody> _values = this.bodies.values();
    for (final AgentBody body : _values) {
      if ((body instanceof sokobanBody)) {
        count++;
      }
    }
    return count;
  }

  /**
   * Replies the number of bodies in the maze.
   * 
   * @return the number of bodies.
   */
  @Pure
  public int getBodyCount() {
    return this.bodies.size();
  }

  /**
   * Replies the object at the given position.
   * 
   * @param x
   * @param y
   * @return the object or {@code null} or a instance of {@code WallObject} if outside the maze limits.
   */
  @Pure
  public sokobanObject getObjectAt(final int x, final int y) {
    if (((((x >= 0) && (x < this.width)) && (y >= 0)) && (y < this.height))) {
      return this.grid[x][y];
    }
    return new WallObject(x, y, this);
  }

  /**
   * Set the object at the given position.
   * 
   * @param x
   * @param y
   * @param obj the object to put at the position
   * @return the object in the cell before the change.
   */
  public sokobanObject setObjectAt(final int x, final int y, final sokobanObject obj) {
    int tx = x;
    int ty = y;
    if ((obj instanceof BoxObject)) {
      Point2i p = this.findRandomEmptyInnerCell(true);
      if ((p != null)) {
        tx = p.getX();
        ty = p.getY();
      }
    }
    if (((((tx >= 0) && (tx < this.width)) && (ty >= 0)) && (ty < this.height))) {
      sokobanObject old = this.grid[tx][ty];
      this.grid[tx][ty] = obj;
      if ((obj != null)) {
        obj.setPosition(tx, ty);
      }
      return old;
    }
    return null;
  }

  /**
   * Set the object at the given position.
   * 
   * @param position
   * @param obj the object to put at the position
   * @return the object in the cell before the change.
   */
  public sokobanObject setObjectAt(final Point2i position, final sokobanObject obj) {
    return this.setObjectAt(position.getX(), position.getY(), obj);
  }

  /**
   * Replies the agent bodies.
   * 
   * @return the agent bodies.
   */
  @Pure
  public Collection<AgentBody> getAgentBodies() {
    return Collections.<UUID, AgentBody>unmodifiableMap(this.bodies).values();
  }

  /**
   * Replies the agent body.
   * 
   * @param id
   * @return the body.
   */
  @Pure
  public AgentBody getAgentBody(final UUID id) {
    return this.bodies.get(id);
  }

  /**
   * Replies the accessors for managing the super power of sokoban.
   * 
   * @param id the id of sokoban.
   * @return the accessor.
   */
  @Pure
  public SuperPowerAccessor getSuperPowerAccessorFor(final UUID id) {
    abstract class __Maze_0 extends SuperPowerAccessor {
      public abstract void resetSuperPower();

      public abstract void decreaseSuperPower();
    }

    AgentBody body = this.bodies.get(id);
    if ((body instanceof sokobanBody)) {
      final sokobanBody sokoban = ((sokobanBody)body);
      return new __Maze_0() {
        public void resetSuperPower() {
          sokoban.resetSuperPower();
        }

        public void decreaseSuperPower() {
          sokoban.decreaseSuperPower();
        }
      };
    }
    return new SuperPowerAccessor();
  }

  /**
   * Check if coordinates are within maze bounds.
   * 
   * @param x the x coordinate
   * @param y the y coordinate
   * @return true if coordinates are within bounds
   */
  @Pure
  public synchronized boolean inBounds(final int x, final int y) {
    return ((((x >= 0) && (y >= 0)) && (x < this.width)) && (y < this.height));
  }

  /**
   * Check if a tile is walkable (no wall, no box, no blocking object).
   * 
   * @param x the x coordinate
   * @param y the y coordinate
   * @return true if the tile is walkable
   */
  @Pure
  public synchronized boolean isWalkable(final int x, final int y) {
    boolean _inBounds = this.inBounds(x, y);
    if ((!_inBounds)) {
      return false;
    }
    sokobanObject obj = this.grid[x][y];
    if ((obj == null)) {
      return true;
    }
    if (((obj instanceof WallObject) || (obj instanceof BoxObject))) {
      return false;
    }
    if ((obj instanceof AgentBody)) {
      return false;
    }
    return true;
  }

  /**
   * Check if a tile is an exit (cell property, not object).
   * 
   * @param x the x coordinate
   * @param y the y coordinate
   * @return true if the tile is an exit cell
   */
  @Pure
  public synchronized boolean isExit(final int x, final int y) {
    boolean _inBounds = this.inBounds(x, y);
    if ((!_inBounds)) {
      return false;
    }
    Point2i _point2i = new Point2i(x, y);
    return this.exitPositions.contains(_point2i);
  }

  /**
   * Get all exit positions in the maze.
   * 
   * @return list of exit positions
   */
  @Pure
  public synchronized List<Point2i> getExits() {
    return new ArrayList<Point2i>(this.exitPositions);
  }

  /**
   * Add an exit position to the maze.
   * 
   * @param x the x coordinate
   * @param y the y coordinate
   * @return true if the exit was added successfully
   */
  public synchronized boolean addExit(final int x, final int y) {
    Point2i p = this.findRandomEmptyInnerCell(true);
    if ((p != null)) {
      this.exitPositions.add(p);
      return true;
    }
    return false;
  }

  /**
   * Get all boxes in the maze.
   * 
   * @return list of all BoxObject instances
   */
  @Pure
  public synchronized List<BoxObject> getAllBoxes() {
    ArrayList<BoxObject> boxes = new ArrayList<BoxObject>();
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, this.width, true);
    for (final Integer i : _doubleDotLessThan) {
      ExclusiveRange _doubleDotLessThan_1 = new ExclusiveRange(0, this.height, true);
      for (final Integer j : _doubleDotLessThan_1) {
        {
          sokobanObject obj = this.grid[((i) == null ? 0 : (i).intValue())][((j) == null ? 0 : (j).intValue())];
          if ((obj instanceof BoxObject)) {
            boxes.add(((BoxObject)obj));
          }
        }
      }
    }
    return boxes;
  }

  /**
   * Check if a tile contains a box.
   * 
   * @param x the x coordinate
   * @param y the y coordinate
   * @return true if the tile contains a box
   */
  @Pure
  public synchronized boolean isBox(final int x, final int y) {
    boolean _inBounds = this.inBounds(x, y);
    if ((!_inBounds)) {
      return false;
    }
    sokobanObject obj = this.grid[x][y];
    return (obj instanceof BoxObject);
  }

  /**
   * Move a box from one position to another.
   * 
   * @param fromX source x coordinate
   * @param fromY source y coordinate
   * @param toX destination x coordinate
   * @param toY destination y coordinate
   * @return true if the box was successfully moved
   */
  public synchronized boolean moveBox(final int fromX, final int fromY, final int toX, final int toY) {
    if (((!this.inBounds(fromX, fromY)) || (!this.inBounds(toX, toY)))) {
      return false;
    }
    sokobanObject box = this.grid[fromX][fromY];
    if ((!(box instanceof BoxObject))) {
      return false;
    }
    sokobanObject destObj = this.grid[toX][toY];
    if (((destObj != null) && (!destObj.isPickable()))) {
      return false;
    }
    this.grid[fromX][fromY] = destObj;
    this.grid[toX][toY] = box;
    box.setPosition(toX, toY);
    if ((box instanceof BoxObject)) {
      ((BoxObject)box).updateOnTargetStatus();
    }
    if ((destObj != null)) {
      destObj.setPosition(fromX, fromY);
    }
    return true;
  }

  /**
   * Move the player with Sokoban physics rules.
   * 
   * Sokoban movement rules:
   * 1. Player can move into empty cell → move player
   * 2. Player moves into box → push box if cell behind box is empty/target
   * 3. Box cannot be pushed into wall or another box → movement blocked
   * 4. One action = one grid step (no diagonal movement)
   * 
   * This implements state transformation: every move changes the world configuration.
   * Some moves are irreversible (deadlocks).
   * 
   * @param playerBody the player body to move
   * @param dx delta x (-1, 0, or 1)
   * @param dy delta y (-1, 0, or 1)
   * @return true if the player was successfully moved (state changed)
   */
  public synchronized boolean movePlayer(final AgentBody playerBody, final int dx, final int dy) {
    if ((playerBody == null)) {
      return false;
    }
    Point2i pos = playerBody.getPosition();
    int oldX = pos.getX();
    int oldY = pos.getY();
    int newX = (oldX + dx);
    int newY = (oldY + dy);
    boolean _inBounds = this.inBounds(newX, newY);
    if ((!_inBounds)) {
      return false;
    }
    boolean _isWalkable = this.isWalkable(newX, newY);
    if ((!_isWalkable)) {
      boolean _isBox = this.isBox(newX, newY);
      if (_isBox) {
        int boxNewX = (newX + dx);
        int boxNewY = (newY + dy);
        boolean _isWalkable_1 = this.isWalkable(boxNewX, boxNewY);
        if (_isWalkable_1) {
          boolean _moveBox = this.moveBox(newX, newY, boxNewX, boxNewY);
          if (_moveBox) {
            sokobanObject destObj = this.grid[newX][newY];
            sokobanObject oldPosObj = this.grid[oldX][oldY];
            if ((oldPosObj == playerBody)) {
              this.grid[oldX][oldY] = null;
            }
            this.grid[newX][newY] = playerBody;
            playerBody.setPosition(newX, newY);
            if (((destObj != null) && (destObj != playerBody))) {
              this.grid[oldX][oldY] = destObj;
              destObj.setPosition(oldX, oldY);
            }
            return true;
          }
        }
      }
      return false;
    }
    sokobanObject destObj_1 = this.grid[newX][newY];
    sokobanObject oldPosObj_1 = this.grid[oldX][oldY];
    if ((oldPosObj_1 == playerBody)) {
      this.grid[oldX][oldY] = null;
    }
    this.grid[newX][newY] = playerBody;
    playerBody.setPosition(newX, newY);
    if (((destObj_1 != null) && (destObj_1 != playerBody))) {
      this.grid[oldX][oldY] = destObj_1;
      destObj_1.setPosition(oldX, oldY);
    }
    return true;
  }

  @Override
  @Pure
  @SyntheticMember
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Maze other = (Maze) obj;
    if (other.width != this.width)
      return false;
    if (other.height != this.height)
      return false;
    if (other.activeAgentLayoutCount != this.activeAgentLayoutCount)
      return false;
    return super.equals(obj);
  }

  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Integer.hashCode(this.width);
    result = prime * result + Integer.hashCode(this.height);
    result = prime * result + Integer.hashCode(this.activeAgentLayoutCount);
    return result;
  }
}
