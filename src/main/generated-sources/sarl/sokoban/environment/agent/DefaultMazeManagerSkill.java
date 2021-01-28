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
package sokoban.environment.agent;

import framework.math.Point2i;
import io.sarl.lang.core.Skill;
import io.sarl.lang.core.annotation.SarlElementType;
import io.sarl.lang.core.annotation.SarlSpecification;
import io.sarl.lang.core.annotation.SyntheticMember;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.IntegerRange;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;
import sokoban.environment.maze.AgentBody;
import sokoban.environment.maze.BoxObject;
import sokoban.environment.maze.Direction;
import sokoban.environment.maze.GhostBody;
import sokoban.environment.maze.Maze;
import sokoban.environment.maze.SuperPowerAccessor;
import sokoban.environment.maze.sokobanBody;
import sokoban.environment.maze.sokobanObject;
import sokoban.rl.SokobanState;

@SarlSpecification("0.15")
@SarlElementType(22)
@XbaseGenerated
@SuppressWarnings("all")
public class DefaultMazeManagerSkill extends Skill implements MazeManager {
  private final Random random = new Random();

  private final int width;

  private final int height;

  private Maze maze;

  public DefaultMazeManagerSkill(final int width, final int height) {
    this.width = width;
    this.height = height;
  }

  public void install() {
    Maze _maze = new Maze(this.width, this.height);
    this.maze = _maze;
  }

  public void uninstall() {
    this.maze = null;
  }

  @Pure
  public int getBodyCount() {
    return this.maze.getBodyCount();
  }

  public boolean applyActions(final List<MazeChangeQuery> actions) {
    TreeMap<UUID, Direction> lastActions = new TreeMap<UUID, Direction>();
    for (final MazeChangeQuery action : actions) {
      lastActions.put(action.getEmitter(), action.getChange());
    }
    boolean sokobanDead = false;
    final List<sokobanObject> objects = this.getsokobanObjects();
    sokobanBody sokoban = null;
    final ArrayList<GhostBody> ghosts = new ArrayList<GhostBody>();
    for (final sokobanObject o : objects) {
      if ((o instanceof sokobanBody)) {
        sokoban = ((sokobanBody)o);
      } else {
        if ((o instanceof GhostBody)) {
          ghosts.add(((GhostBody)o));
        }
      }
    }
    Set<Map.Entry<UUID, Direction>> _entrySet = lastActions.entrySet();
    for (final Map.Entry<UUID, Direction> entry : _entrySet) {
      {
        final AgentBody body = this.getAgentBody(entry.getKey());
        if ((body != null)) {
          if ((body instanceof sokobanBody)) {
            int dx = 0;
            int dy = 0;
            Direction _value = entry.getValue();
            if (_value != null) {
              switch (_value) {
                case NORTH:
                  dy = (-1);
                  break;
                case SOUTH:
                  dy = 1;
                  break;
                case WEST:
                  dx = (-1);
                  break;
                case EAST:
                  dx = 1;
                  break;
                default:
                  break;
              }
            } else {
            }
            this.maze.movePlayer(body, dx, dy);
          } else {
            final Point2i pos = body.getPosition();
            int nx = pos.getX();
            int ny = pos.getY();
            Direction _value_1 = entry.getValue();
            if (_value_1 != null) {
              switch (_value_1) {
                case NORTH:
                  ny--;
                  break;
                case SOUTH:
                  ny++;
                  break;
                case WEST:
                  nx--;
                  break;
                case EAST:
                  nx++;
                  break;
                default:
                  break;
              }
            } else {
            }
            if (((((nx >= 0) && (ny >= 0)) && (nx < this.width)) && (ny < this.height))) {
              boolean _isWalkable = this.maze.isWalkable(nx, ny);
              if (_isWalkable) {
                this.maze.setObjectAt(nx, ny, body);
              }
            }
          }
        }
      }
    }
    if ((sokoban != null)) {
      for (final GhostBody g : ghosts) {
        boolean _equals = g.getPosition().equals(sokoban.getPosition());
        if (_equals) {
          sokobanDead = true;
          break;
        }
      }
    }
    return sokobanDead;
  }

  @Pure
  public boolean in(final int v, final IntegerRange r) {
    return r.contains(v);
  }

  @Pure
  public boolean isInside(final int x, final int y) {
    return ((((x >= 0) && (y >= 0)) && (x < this.width)) && (y < this.height));
  }

  @Pure
  public boolean cellIsOccluder(final int x, final int y) {
    sokobanObject cellContent = this.maze.getObjectAt(x, y);
    if ((cellContent == null)) {
      return false;
    }
    return cellContent.isOccluder();
  }

  /**
   * Check if there's a clear line-of-sight between two points using Bresenham's line algorithm.
   * Returns true if the path is clear (no occluders blocking the view).
   */
  @Pure
  public boolean hasLineOfSight(final int x1, final int y1, final int x2, final int y2) {
    if (((x1 == x2) && (y1 == y2))) {
      return true;
    }
    int dx = Math.abs((x2 - x1));
    int dy = Math.abs((y2 - y1));
    int _xifexpression = (int) 0;
    if ((x1 < x2)) {
      _xifexpression = 1;
    } else {
      _xifexpression = (-1);
    }
    int sx = _xifexpression;
    int _xifexpression_1 = (int) 0;
    if ((y1 < y2)) {
      _xifexpression_1 = 1;
    } else {
      _xifexpression_1 = (-1);
    }
    int sy = _xifexpression_1;
    int err = (dx - dy);
    int x = x1;
    int y = y1;
    int maxSteps = ((dx + dy) + 1);
    int steps = 0;
    while ((steps < maxSteps)) {
      {
        if (((x == x2) && (y == y2))) {
          return true;
        }
        if ((!((x == x1) && (y == y1)))) {
          if (((!this.isInside(x, y)) || this.cellIsOccluder(x, y))) {
            return false;
          }
        }
        int e2 = (2 * err);
        if ((e2 > (-dy))) {
          err = (err - dy);
          x = (x + sx);
        }
        if ((e2 < dx)) {
          err = (err + dx);
          y = (y + sy);
        }
        steps++;
      }
    }
    return false;
  }

  @Pure
  public Map<AgentBody, List<sokobanObject>> getPerceptions() {
    TreeMap<AgentBody, List<sokobanObject>> perceptions = new TreeMap<AgentBody, List<sokobanObject>>();
    Collection<AgentBody> bodies = this.maze.getAgentBodies();
    if (((bodies == null) || bodies.isEmpty())) {
      System.out.println("[MazeManager] No agent bodies found for perception");
      return perceptions;
    }
    int _size = bodies.size();
    System.out.println((("[MazeManager] Getting perceptions for " + Integer.valueOf(_size)) + " agent body/bodies"));
    ArrayList<sokobanObject> allObjects = new ArrayList<sokobanObject>();
    int boxCount = 0;
    for (int x = 0; (x < this.width); x++) {
      for (int y = 0; (y < this.height); y++) {
        {
          sokobanObject obj = this.maze.getObjectAt(x, y);
          if ((obj != null)) {
            allObjects.add(obj);
            if ((obj instanceof BoxObject)) {
              boxCount++;
            }
          }
        }
      }
    }
    int _size_1 = allObjects.size();
    System.out.println((((("[MazeManager] Total objects in maze: " + Integer.valueOf(_size_1)) + " (including ") + Integer.valueOf(boxCount)) + " boxes)"));
    for (final AgentBody b : bodies) {
      {
        ArrayList<sokobanObject> seen = new ArrayList<sokobanObject>();
        Point2i bp = b.getPosition();
        int d = b.getPerceptionDistance();
        if ((d > 0)) {
          for (final sokobanObject o : allObjects) {
            if ((o != b)) {
              final Point2i op = o.getPosition();
              int _x = op.getX();
              int _x_1 = bp.getX();
              final int dx = (_x - _x_1);
              int _y = op.getY();
              int _y_1 = bp.getY();
              final int dy = (_y - _y_1);
              int _abs = Math.abs(dx);
              int _abs_1 = Math.abs(dy);
              final int manhattanDist = (_abs + _abs_1);
              if (((manhattanDist <= d) && (manhattanDist > 0))) {
                boolean visible = this.hasLineOfSight(bp.getX(), bp.getY(), op.getX(), op.getY());
                if (visible) {
                  seen.add(o);
                  if ((o instanceof BoxObject)) {
                    int _x_2 = bp.getX();
                    int _y_2 = bp.getY();
                    int _x_3 = op.getX();
                    int _y_3 = op.getY();
                    System.out.println((((((((("[MazeManager] Agent at (" + Integer.valueOf(_x_2)) + ";") + Integer.valueOf(_y_2)) + ") sees BoxObject at (") + Integer.valueOf(_x_3)) + ";") + Integer.valueOf(_y_3)) + ")"));
                  }
                }
              }
            }
          }
        }
        int seenBoxes = 0;
        for (final sokobanObject obj : seen) {
          if ((obj instanceof BoxObject)) {
            seenBoxes++;
          }
        }
        if ((seenBoxes > 0)) {
          int _x_4 = b.getPosition().getX();
          int _y_4 = b.getPosition().getY();
          int _size_2 = seen.size();
          System.out.println((((((((("[MazeManager] Agent at (" + Integer.valueOf(_x_4)) + ";") + Integer.valueOf(_y_4)) + ") sees ") + Integer.valueOf(seenBoxes)) + " box(es) out of ") + Integer.valueOf(_size_2)) + " objects"));
        }
        perceptions.put(b, seen);
        int _x_5 = bp.getX();
        int _y_5 = bp.getY();
        int _size_3 = seen.size();
        System.out.println((((((("[MazeManager] Agent at (" + Integer.valueOf(_x_5)) + ";") + Integer.valueOf(_y_5)) + ") sees ") + Integer.valueOf(_size_3)) + " object(s)"));
      }
    }
    return perceptions;
  }

  @Pure
  public int getMazeHeight() {
    return this.height;
  }

  @Pure
  public int getMazeWidth() {
    return this.width;
  }

  public List<sokobanObject> getsokobanObjects() {
    ArrayList<sokobanObject> list = CollectionLiterals.<sokobanObject>newArrayList();
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, this.width, true);
    for (final Integer i : _doubleDotLessThan) {
      ExclusiveRange _doubleDotLessThan_1 = new ExclusiveRange(0, this.height, true);
      for (final Integer j : _doubleDotLessThan_1) {
        {
          sokobanObject o = this.maze.getObjectAt(((i) == null ? 0 : (i).intValue()), ((j) == null ? 0 : (j).intValue()));
          if ((o != null)) {
            list.add(o);
          }
        }
      }
    }
    return list;
  }

  @Pure
  public AgentBody getAgentBody(final UUID id) {
    return this.maze.getAgentBody(id);
  }

  @Pure
  public SuperPowerAccessor getSuperPowerAccessor(final UUID id) {
    return this.maze.getSuperPowerAccessorFor(id);
  }

  public GhostBody createGhost(final int perceptionDistance) {
    return this.maze.<GhostBody>createBody(GhostBody.class, null, perceptionDistance);
  }

  public sokobanBody createsokoban() {
    return this.maze.<sokobanBody>createBody(sokobanBody.class, null, 5);
  }

  public boolean createBox(final int x, final int y) {
    if ((this.maze.inBounds(x, y) && this.maze.isWalkable(x, y))) {
      sokobanObject obj = this.maze.getObjectAt(x, y);
      if (((obj == null) || obj.isPickable())) {
        BoxObject box = new BoxObject(x, y, this.maze);
        this.maze.setObjectAt(x, y, box);
        return true;
      }
    }
    return false;
  }

  public void createBoxes(final int numberOfBoxes) {
    int boxesCreated = 0;
    int startX = Math.max(2, (this.width / 4));
    int startY = Math.max(2, (this.height / 3));
    System.out.println(((((("[MazeManager] Creating " + Integer.valueOf(numberOfBoxes)) + " boxes, maze size: ") + Integer.valueOf(this.width)) + "x") + Integer.valueOf(this.height)));
    int boxesPerRow = Math.min(3, numberOfBoxes);
    int rows = (((numberOfBoxes + boxesPerRow) - 1) / boxesPerRow);
    for (int row = 0; ((row < rows) && (boxesCreated < numberOfBoxes)); row++) {
      for (int col = 0; ((col < boxesPerRow) && (boxesCreated < numberOfBoxes)); col++) {
        {
          int x = (startX + (col * 2));
          int y = (startY + (row * 2));
          if (((x < (this.width - 2)) && (y < (this.height - 2)))) {
            boolean _createBox = this.createBox(x, y);
            if (_createBox) {
              boxesCreated++;
              System.out.println((((((("[MazeManager] Created box #" + Integer.valueOf(boxesCreated)) + " at (") + Integer.valueOf(x)) + ";") + Integer.valueOf(y)) + ")"));
            }
          }
        }
      }
    }
    int attempts = 0;
    final int maxAttempts = 500;
    while (((boxesCreated < numberOfBoxes) && (attempts < maxAttempts))) {
      {
        attempts++;
        int _nextInt = this.random.nextInt((this.width - 2));
        int x = (_nextInt + 1);
        int _nextInt_1 = this.random.nextInt((this.height - 2));
        int y = (_nextInt_1 + 1);
        boolean _createBox = this.createBox(x, y);
        if (_createBox) {
          boxesCreated++;
        }
      }
    }
  }

  public boolean createExit(final int x, final int y) {
    if ((this.maze.inBounds(x, y) && this.maze.isWalkable(x, y))) {
      return this.maze.addExit(x, y);
    }
    return false;
  }

  public void createExits(final int numberOfExits) {
    int exitsCreated = 0;
    int startX = Math.max((this.width - 4), ((this.width * 3) / 4));
    int startY = Math.max(2, (this.height / 3));
    int exitsPerRow = Math.min(3, numberOfExits);
    int rows = (((numberOfExits + exitsPerRow) - 1) / exitsPerRow);
    for (int row = 0; ((row < rows) && (exitsCreated < numberOfExits)); row++) {
      for (int col = 0; ((col < exitsPerRow) && (exitsCreated < numberOfExits)); col++) {
        {
          int x = (startX - (col * 2));
          int y = (startY + (row * 2));
          if (((((x > 1) && (x < (this.width - 1))) && (y > 1)) && (y < (this.height - 1)))) {
            boolean _createExit = this.createExit(x, y);
            if (_createExit) {
              exitsCreated++;
            }
          }
        }
      }
    }
    int attempts = 0;
    final int maxAttempts = 500;
    while (((exitsCreated < numberOfExits) && (attempts < maxAttempts))) {
      {
        attempts++;
        int _nextInt = this.random.nextInt((this.width - 2));
        int x = (_nextInt + 1);
        int _nextInt_1 = this.random.nextInt((this.height - 2));
        int y = (_nextInt_1 + 1);
        boolean _createExit = this.createExit(x, y);
        if (_createExit) {
          exitsCreated++;
        }
      }
    }
  }

  public boolean allAgentsAtExit() {
    final List<Point2i> exits = this.maze.getExits();
    boolean _isEmpty = exits.isEmpty();
    if (_isEmpty) {
      return false;
    }
    final Collection<AgentBody> agentBodies = this.maze.getAgentBodies();
    boolean _isEmpty_1 = agentBodies.isEmpty();
    if (_isEmpty_1) {
      return false;
    }
    ArrayList<AgentBody> agentBodiesToCheck = new ArrayList<AgentBody>();
    for (final AgentBody body : agentBodies) {
      if ((body instanceof sokobanBody)) {
        agentBodiesToCheck.add(body);
      }
    }
    boolean _isEmpty_2 = agentBodiesToCheck.isEmpty();
    if (_isEmpty_2) {
      return false;
    }
    for (final AgentBody body_1 : agentBodiesToCheck) {
      {
        Point2i agentPos = body_1.getPosition();
        boolean atExit = false;
        for (final Point2i exitPos : exits) {
          boolean _equals = agentPos.equals(exitPos);
          if (_equals) {
            atExit = true;
            break;
          }
        }
        if ((!atExit)) {
          return false;
        }
      }
    }
    return true;
  }

  public boolean allBoxesOnGoals() {
    final List<BoxObject> boxes = this.maze.getAllBoxes();
    boolean _isEmpty = boxes.isEmpty();
    if (_isEmpty) {
      return false;
    }
    for (final BoxObject box : boxes) {
      boolean _isOnTarget = box.isOnTarget();
      if ((!_isOnTarget)) {
        return false;
      }
    }
    return true;
  }

  @Pure
  public List<Point2i> getExitPositions() {
    return this.maze.getExits();
  }

  public SokobanState extractSokobanState(final int stepCount) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method newInstance(Class<boolean[]>, int) is undefined for the type Class<Array>"
      + "\nThe method newInstance(Class<Boolean>, int) is undefined for the type Class<Array>"
      + "\nPrimitives cannot be used as type arguments.");
  }

  public boolean executePlayerAction(final Direction direction) {
    Collection<AgentBody> agentBodies = this.maze.getAgentBodies();
    sokobanBody playerBody = null;
    for (final AgentBody body : agentBodies) {
      if ((body instanceof sokobanBody)) {
        playerBody = ((sokobanBody)body);
        break;
      }
    }
    if ((playerBody == null)) {
      return false;
    }
    int dx = 0;
    int dy = 0;
    if (direction != null) {
      switch (direction) {
        case NORTH:
          dy = (-1);
          break;
        case SOUTH:
          dy = 1;
          break;
        case EAST:
          dx = 1;
          break;
        case WEST:
          dx = (-1);
          break;
        default:
          return false;
      }
    } else {
      return false;
    }
    return this.maze.movePlayer(playerBody, dx, dy);
  }

  public int countBoxesOnTargets() {
    int count = 0;
    final List<Point2i> exits = this.maze.getExits();
    boolean _isEmpty = exits.isEmpty();
    if (_isEmpty) {
      return 0;
    }
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, this.width, true);
    for (final Integer i : _doubleDotLessThan) {
      ExclusiveRange _doubleDotLessThan_1 = new ExclusiveRange(0, this.height, true);
      for (final Integer j : _doubleDotLessThan_1) {
        {
          sokobanObject obj = this.maze.getObjectAt(((i) == null ? 0 : (i).intValue()), ((j) == null ? 0 : (j).intValue()));
          if ((obj instanceof BoxObject)) {
            Point2i boxPos = ((BoxObject)obj).getPosition();
            for (final Point2i exitPos : exits) {
              boolean _equals = boxPos.equals(exitPos);
              if (_equals) {
                count++;
                break;
              }
            }
          }
        }
      }
    }
    return count;
  }

  public int countAgentsAtExit() {
    int count = 0;
    final List<Point2i> exits = this.maze.getExits();
    boolean _isEmpty = exits.isEmpty();
    if (_isEmpty) {
      return 0;
    }
    final Collection<AgentBody> agentBodies = this.maze.getAgentBodies();
    for (final AgentBody body : agentBodies) {
      {
        Point2i agentPos = body.getPosition();
        for (final Point2i exitPos : exits) {
          boolean _equals = agentPos.equals(exitPos);
          if (_equals) {
            count++;
            break;
          }
        }
      }
    }
    return count;
  }

  @Pure
  public int getTotalBoxCount() {
    int count = 0;
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, this.width, true);
    for (final Integer i : _doubleDotLessThan) {
      ExclusiveRange _doubleDotLessThan_1 = new ExclusiveRange(0, this.height, true);
      for (final Integer j : _doubleDotLessThan_1) {
        {
          sokobanObject obj = this.maze.getObjectAt(((i) == null ? 0 : (i).intValue()), ((j) == null ? 0 : (j).intValue()));
          if ((obj instanceof BoxObject)) {
            count++;
          }
        }
      }
    }
    return count;
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
    DefaultMazeManagerSkill other = (DefaultMazeManagerSkill) obj;
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
