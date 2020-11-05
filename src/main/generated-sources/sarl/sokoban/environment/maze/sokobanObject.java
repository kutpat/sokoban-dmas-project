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
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.Objects;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;

/**
 * Object in the sokoban environment.
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
public abstract class sokobanObject implements Serializable {
  private final Point2i position;

  private final transient WeakReference<Maze> maze;

  public sokobanObject(final int x, final int y, final Maze maze) {
    Point2i _point2i = new Point2i(x, y);
    this.position = _point2i;
    WeakReference<Maze> _weakReference = new WeakReference<Maze>(maze);
    this.maze = _weakReference;
  }

  public sokobanObject(final Point2i position, final Maze maze) {
    this(position.getX(), position.getY(), maze);
  }

  @Pure
  public boolean equals(final Object obj) {
    boolean _equals = Objects.equals(obj, this);
    if (_equals) {
      return true;
    }
    if (((obj != null) && Objects.equals(this.getClass(), obj.getClass()))) {
      sokobanObject o = ((sokobanObject) obj);
      return Objects.equals(o.position, this.position);
    }
    return false;
  }

  @Pure
  public int hashCode() {
    return Objects.hash(this.position);
  }

  /**
   * Replies the position of the object.
   * 
   * @return the position.
   */
  @Pure
  public Point2i getPosition() {
    return this.position.clone();
  }

  /**
   * Change the position of the object.
   */
  void setPosition(final int x, final int y) {
    this.position.set(x, y);
  }

  /**
   * Replies the maze in which this object is located.
   * 
   * @return the maze.
   */
  @Pure
  Maze getMaze() {
    WeakReference<Maze> _maze = this.maze;
    Maze _get = null;
    if (_maze!=null) {
      _get=_maze.get();
    }
    return _get;
  }

  /**
   * Replies if this object occludes other objects during the agent's perception.
   */
  @Pure
  public abstract boolean isOccluder();

  /**
   * Replies if this object could be pick by the agents.
   */
  @Pure
  public abstract boolean isPickable();

  @SyntheticMember
  private static final long serialVersionUID = 6322334873L;
}
