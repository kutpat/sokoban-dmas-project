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
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;

/**
 * The body of the sokoban.
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
public class sokobanBody extends AgentBody {
  private static final int SUPER_POWER = 10;

  private int superPower;

  public sokobanBody(final int x, final int y, final Maze maze, final UUID agentId, final int perceptionDistance) {
    super(x, y, maze, agentId, perceptionDistance);
  }

  public sokobanBody(final Point2i position, final Maze maze, final UUID agentId, final int perceptionDistance) {
    super(position, maze, agentId, perceptionDistance);
  }

  @Pure
  public boolean isPickable() {
    return true;
  }

  /**
   * Replies if the sokoban body has super power.
   */
  @Pure
  public boolean isSupersokoban() {
    return (this.superPower > 0);
  }

  /**
   * Set if the sokoban body has super power.
   */
  void resetSuperPower() {
    this.superPower = sokobanBody.SUPER_POWER;
  }

  /**
   * Decrease the super power.
   */
  void decreaseSuperPower() {
    if ((this.superPower > 0)) {
      this.superPower--;
    }
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
    sokobanBody other = (sokobanBody) obj;
    if (other.superPower != this.superPower)
      return false;
    return super.equals(obj);
  }

  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Integer.hashCode(this.superPower);
    return result;
  }

  @SyntheticMember
  private static final long serialVersionUID = -3132605412L;
}
