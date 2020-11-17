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

import io.sarl.lang.core.annotation.SarlElementType;
import io.sarl.lang.core.annotation.SarlSpecification;
import io.sarl.lang.core.annotation.SyntheticMember;
import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.UUID;
import java.util.logging.Logger;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;
import sokoban.environment.maze.Direction;

/**
 * Interface that must be used by the player for moving his/her avatar.
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
public class Player {
  private final Queue<Direction> pendingDirections = new LinkedList<Direction>();

  private final UUID bodyId;

  private final Logger logger;

  Player(final UUID bodyId, final Logger logger) {
    this.bodyId = bodyId;
    this.logger = logger;
  }

  /**
   * Move the player avatar.
   */
  public synchronized void move(final Direction direction) {
    if ((this.logger != null)) {
      if ((direction == null)) {
        this.logger.info("Player wants to stay static.");
      } else {
        this.logger.info(MessageFormat.format("Player wants to move to {0}.", direction.name()));
      }
    }
    this.pendingDirections.offer(direction);
  }

  /**
   * Replies the last input direction.
   */
  @Pure
  synchronized Direction getDirection() {
    return this.pendingDirections.peek();
  }

  /**
   * Replies and removes the next requested direction.
   */
  synchronized Direction consumeDirection() {
    return this.pendingDirections.poll();
  }

  /**
   * Replies the identifier of the body.
   */
  @Pure
  public UUID getBodyId() {
    return this.bodyId;
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
    Player other = (Player) obj;
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
    result = prime * result + Objects.hashCode(this.bodyId);
    return result;
  }
}
