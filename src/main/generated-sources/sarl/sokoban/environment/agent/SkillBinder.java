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

import io.sarl.lang.core.Event;
import io.sarl.lang.core.EventListener;
import io.sarl.lang.core.annotation.SarlElementType;
import io.sarl.lang.core.annotation.SarlSpecification;
import io.sarl.lang.core.annotation.SyntheticMember;
import java.util.Objects;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;

/**
 * Object that may be used to link a skill to a MazeSpace.
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
public class SkillBinder implements EventListener {
  private final EventListener owner;

  private int currentTime;

  private final UUID bodyId;

  /**
   * @param owner - the owner of the skill.
   * @param id - the identifier of the agent / body.
   */
  public SkillBinder(final EventListener owner, final UUID id) {
    this.owner = owner;
    this.bodyId = id;
  }

  /**
   * Replies the owner.
   * 
   * @return the owner.
   */
  @Pure
  public EventListener getOwner() {
    return this.owner;
  }

  /**
   * Replies the current simulation time.
   * 
   * @return the current time.
   */
  @Pure
  public int getCurrentTime() {
    return this.currentTime;
  }

  @Pure
  public UUID getID() {
    return this.bodyId;
  }

  public void receiveEvent(final Event event) {
    if ((event instanceof Perception)) {
      this.currentTime = ((Perception)event).time;
      this.owner.receiveEvent(event);
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
    SkillBinder other = (SkillBinder) obj;
    if (other.currentTime != this.currentTime)
      return false;
    if (!Objects.equals(this.bodyId, other.bodyId))
      return false;
    return super.equals(obj);
  }

  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Integer.hashCode(this.currentTime);
    result = prime * result + Objects.hashCode(this.bodyId);
    return result;
  }
}
