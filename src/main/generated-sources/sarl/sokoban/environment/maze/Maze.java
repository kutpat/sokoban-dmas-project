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
import framework.math.Vector2i;
import io.sarl.lang.core.annotation.SarlElementType;
import io.sarl.lang.core.annotation.SarlSpecification;
import io.sarl.lang.core.annotation.SyntheticMember;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;

/**
 * Define the maze.
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
   * @author $Author: sgalland$
   * @version $FullVersion$
   * @mavengroupid $GroupId$
   * @mavenartifactid $ArtifactId$
   */
  @SarlSpecification("0.15")
  @SarlElementType(10)
  @XbaseGenerated
  private static class PrimWall {
    public final Point2i passage;

    public final Point2i corridor;

    public final Point2i passageCandidate1;

    public final Point2i passageCandidate2;

    public final Point2i passageCandidate3;

    public PrimWall(final int passageX, final int passageY, final int corridorX, final int corridorY, final int passageCandidateX1, final int passageCandidateY1, final int passageCandidateX2, final int passageCandidateY2, final int passageCandidateX3, final int passageCandidateY3) {
      Point2i _point2i = new Point2i(passageX, passageY);
      this.passage = _point2i;
      Point2i _point2i_1 = new Point2i(corridorX, corridorY);
      this.corridor = _point2i_1;
      Point2i _point2i_2 = new Point2i(passageCandidateX1, passageCandidateY1);
      this.passageCandidate1 = _point2i_2;
      Point2i _point2i_3 = new Point2i(passageCandidateX2, passageCandidateY2);
      this.passageCandidate2 = _point2i_3;
      Point2i _point2i_4 = new Point2i(passageCandidateX3, passageCandidateY3);
      this.passageCandidate3 = _point2i_4;
    }

    @Pure
    public String toString() {
      return ((("passage=" + this.passage) + "|corridor=") + this.corridor);
    }

    @Override
    @Pure
    @SyntheticMember
    public boolean equals(final Object obj) {
      return super.equals(obj);
    }

    @Override
    @Pure
    @SyntheticMember
    public int hashCode() {
      int result = super.hashCode();
      return result;
    }
  }

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
   * Build the maze and fill the spaces with pills.
   * 
   * @param width width of the maze.
   * @param height height of the maze.
   */
  private void buildWorld(final int width, final int height) {
    this.buildMaze();
    for (int i = 0; (i < width); i++) {
      for (int j = 0; (j < height); j++) {
        sokobanObject _get = this.grid[i][j];
        if ((_get == null)) {
          PillObject _pillObject = new PillObject(i, j, this, false);
          this.grid[i][j] = _pillObject;
        }
      }
    }
    for (int i = 0; (i < Maze.MAX_SUPER_PILLS); i++) {
      {
        int x = this.random.nextInt(width);
        int y = this.random.nextInt(height);
        while ((!this.canMoveInside(x, y))) {
          {
            x = this.random.nextInt(width);
            y = this.random.nextInt(height);
          }
        }
        PillObject _pillObject = new PillObject(x, y, this, true);
        this.grid[x][y] = _pillObject;
      }
    }
  }

  /**
   * Build the maze with the Prim's algorithm.
   */
  private void buildMaze() {
    for (int i = 0; (i < this.width); i++) {
      for (int j = 0; (j < this.height); j++) {
        WallObject _wallObject = new WallObject(i, j, this);
        this.grid[i][j] = _wallObject;
      }
    }
    ArrayList<Maze.PrimWall> walls = new ArrayList<Maze.PrimWall>();
    int x = this.random.nextInt(this.width);
    int y = this.random.nextInt(this.height);
    this.grid[x][y] = null;
    Maze.PrimWall _primWall = new Maze.PrimWall(x, (y - 1), x, (y - 2), (x - 1), (y - 2), x, (y - 3), (x + 1), (y - 2));
    walls.add(_primWall);
    Maze.PrimWall _primWall_1 = new Maze.PrimWall(x, (y + 1), x, (y + 2), (x - 1), (y + 2), x, (y + 3), (x + 1), (y + 2));
    walls.add(_primWall_1);
    Maze.PrimWall _primWall_2 = new Maze.PrimWall((x - 1), y, (x - 2), y, (x - 2), (y + 1), (x - 3), y, (x - 2), (y - 1));
    walls.add(_primWall_2);
    Maze.PrimWall _primWall_3 = new Maze.PrimWall((x + 1), y, (x + 2), y, (x + 2), (y - 1), (x + 3), y, (x + 2), (y + 1));
    walls.add(_primWall_3);
    while ((!walls.isEmpty())) {
      {
        Maze.PrimWall diggeableWall = walls.remove(this.random.nextInt(walls.size()));
        if (((((((diggeableWall.corridor.getX() >= 0) && (diggeableWall.corridor.getX() < this.width)) && 
          (diggeableWall.corridor.getY() >= 0)) && (diggeableWall.corridor.getY() < this.height)) && 
          (this.grid[diggeableWall.corridor.getX()][diggeableWall.corridor.getY()] instanceof WallObject)) && 
          (this.grid[diggeableWall.passage.getX()][diggeableWall.passage.getY()] instanceof WallObject))) {
          this.grid[diggeableWall.passage.getX()][diggeableWall.passage.getY()] = null;
          this.grid[diggeableWall.corridor.getX()][diggeableWall.corridor.getY()] = null;
          this.addWallCandidate(walls, diggeableWall.corridor, diggeableWall.passageCandidate1);
          this.addWallCandidate(walls, diggeableWall.corridor, diggeableWall.passageCandidate2);
          this.addWallCandidate(walls, diggeableWall.corridor, diggeableWall.passageCandidate3);
        }
      }
    }
  }

  private void addWallCandidate(final List<Maze.PrimWall> walls, final Point2i corridor, final Point2i candidate) {
    final Vector2i v = candidate.operator_minus(corridor);
    final Vector2i r = v.clone();
    r.perpendicularize();
    int _x = corridor.getX();
    int _x_1 = v.getX();
    int _y = corridor.getY();
    int _y_1 = v.getY();
    int _x_2 = corridor.getX();
    int _x_3 = v.getX();
    int _y_2 = corridor.getY();
    int _y_3 = v.getY();
    int _x_4 = corridor.getX();
    int _x_5 = v.getX();
    int _y_4 = corridor.getY();
    int _y_5 = v.getY();
    int _x_6 = corridor.getX();
    int _x_7 = v.getX();
    int _x_8 = r.getX();
    int _y_6 = corridor.getY();
    int _y_7 = v.getY();
    int _y_8 = r.getY();
    int _x_9 = corridor.getX();
    int _x_10 = v.getX();
    int _x_11 = r.getX();
    int _y_9 = corridor.getY();
    int _y_10 = v.getY();
    int _y_11 = r.getY();
    Maze.PrimWall pw = new Maze.PrimWall(
      (_x + _x_1), (_y + _y_1), 
      (_x_2 + (2 * _x_3)), (_y_2 + (2 * _y_3)), 
      (_x_4 + (3 * _x_5)), (_y_4 + (3 * _y_5)), 
      ((_x_6 + (2 * _x_7)) + _x_8), ((_y_6 + (2 * _y_7)) + _y_8), 
      ((_x_9 + (2 * _x_10)) - _x_11), ((_y_9 + (2 * _y_10)) - _y_11));
    walls.add(pw);
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
      final T _converted_body = (T)body;
      this.bodies.put(id, _converted_body);
      return body;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
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
    if (((((x >= 0) && (x < this.width)) && (y >= 0)) && (y < this.height))) {
      sokobanObject old = this.grid[x][y];
      this.grid[x][y] = obj;
      if ((obj != null)) {
        obj.setPosition(x, y);
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
    if ((destObj != null)) {
      destObj.setPosition(fromX, fromY);
    }
    return true;
  }

  /**
   * Move the player by the given delta.
   * 
   * @param playerBody the player body to move
   * @param dx delta x
   * @param dy delta y
   * @return true if the player was successfully moved
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
    return result;
  }
}
