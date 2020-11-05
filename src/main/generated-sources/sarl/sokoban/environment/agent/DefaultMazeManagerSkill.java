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
import sokoban.environment.maze.Direction;
import sokoban.environment.maze.GhostBody;
import sokoban.environment.maze.Maze;
import sokoban.environment.maze.SuperPowerAccessor;
import sokoban.environment.maze.sokobanBody;
import sokoban.environment.maze.sokobanObject;

/**
 * Skill for managing a maze.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
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
          final Point2i pos = body.getPosition();
          int nx = pos.getX();
          int ny = pos.getY();
          Direction _value = entry.getValue();
          if (_value != null) {
            switch (_value) {
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
          if ((nx < 0)) {
            nx = 0;
          }
          if ((ny < 0)) {
            ny = 0;
          }
          if ((nx >= this.width)) {
            nx = (this.width - 1);
          }
          if ((ny >= this.height)) {
            ny = (this.height - 1);
          }
          this.maze.setObjectAt(nx, ny, body);
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
    return ((((x >= 0) && (y >= 0)) && (x < this.width)) && (y < this.width));
  }

  @Pure
  public boolean cellIsOccluder(final int x, final int y) {
    sokobanObject cellContent = this.maze.getObjectAt(x, y);
    return cellContent.isOccluder();
  }

  @Pure
  public Map<AgentBody, List<sokobanObject>> getPerceptions() {
    TreeMap<AgentBody, List<sokobanObject>> perceptions = new TreeMap<AgentBody, List<sokobanObject>>();
    List<AgentBody> bodies = null;
    if ((bodies == null)) {
      return perceptions;
    }
    for (final AgentBody b : bodies) {
      {
        ArrayList<sokobanObject> seen = new ArrayList<sokobanObject>();
        Point2i bp = b.getPosition();
        int d = b.getPerceptionDistance();
        if ((d > 0)) {
          for (final AgentBody o : bodies) {
            if ((o != b)) {
              final Point2i op = o.getPosition();
              int _x = op.getX();
              int _x_1 = bp.getX();
              final int dx = Math.abs((_x - _x_1));
              int _y = op.getY();
              int _y_1 = bp.getY();
              final int dy = Math.abs((_y - _y_1));
              boolean visible = false;
              if (((dy == 0) && (dx <= d))) {
                int _xifexpression = (int) 0;
                if ((dx > 0)) {
                  _xifexpression = 1;
                } else {
                  _xifexpression = (-1);
                }
                final int stepX = _xifexpression;
                int _x_2 = bp.getX();
                int x = (_x_2 + stepX);
                int y = bp.getY();
                visible = true;
                while ((x != op.getX())) {
                  {
                    if (((!this.isInside(x, y)) || this.cellIsOccluder(x, y))) {
                      visible = false;
                      break;
                    }
                    x = (x + stepX);
                  }
                }
              }
              if (((dx == 0) && (dy <= d))) {
                int _xifexpression_1 = (int) 0;
                if ((dy > 0)) {
                  _xifexpression_1 = 1;
                } else {
                  _xifexpression_1 = (-1);
                }
                final int stepY = _xifexpression_1;
                int x_1 = bp.getX();
                int _y_2 = bp.getY();
                int y_1 = (_y_2 + stepY);
                visible = true;
                while ((y_1 != op.getY())) {
                  {
                    if (((!this.isInside(x_1, y_1)) || this.cellIsOccluder(x_1, y_1))) {
                      visible = false;
                      break;
                    }
                    y_1 = (y_1 + stepY);
                  }
                }
              }
              if (visible) {
                seen.add(o);
              }
            }
          }
        }
        perceptions.put(b, seen);
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
    return this.maze.<sokobanBody>createBody(sokobanBody.class, null, 0);
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
