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

import io.sarl.lang.core.EventListener;
import io.sarl.lang.core.Space;
import io.sarl.lang.core.annotation.SarlElementType;
import io.sarl.lang.core.annotation.SarlSpecification;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;
import sokoban.environment.maze.Direction;

/**
 * Space that is representing the Jaak environment.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@SarlSpecification("0.15")
@SarlElementType(11)
@XbaseGenerated
@SuppressWarnings("all")
public interface MazeSpace extends Space {
  /**
   * Destroy the space.
   */
  void destroy();

  /**
   * Spawn the body with the given ID.
   * 
   * @param binder - the binder between the skill and the space.
   */
  void spawnBody(final EventListener binder);

  /**
   * Destroy the body with the given ID.
   * 
   * @param binder - the binder between the skill and the space.
   */
  void killBody(final EventListener binder);

  /**
   * Give the perceptions to the agents that is owning the given body.
   * 
   * @param sender - the identifier of the sender.
   * @param perception - the event to give to the agent.
   */
  void notifyPerception(final UUID sender, final Perception perception);

  /**
   * Emit an influence for the given agent.
   * 
   * @param influenceTime - the time at which the influence is applied.
   * @param emitter - the identifier of the emitter.
   * @param influence - the influence to emit.
   */
  void influence(final int influenceTime, final UUID emitter, final Direction direction);
}
