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
import java.util.EventListener;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;

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
public interface EnvironmentListener extends EventListener {
  /**
   * Invoked for binding the player to the simulated environment.
   */
  void bindPlayer(final Player player);

  /**
   * Invoked for unbinding the player to the simulated environment.
   */
  void unbindPlayer(final Player player);

  /**
   * Invoked for binding the controller of the simulated environment.
   */
  void bindController(final Controller controller);

  /**
   * Invoked for unbinding the controller of the simulated environment.
   */
  void unbindController(final Controller player);

  /**
   * Invoked when the environment has changed.
   * 
   * @param event
   */
  void environmentChanged(final EnvironmentEvent event);

  /**
   * Invoked when the game is over.
   */
  void gameOver();

  /**
   * Get the number of agents to spawn (from dashboard).
   * This is called during initialization to determine how many agents to create.
   * 
   * @return number of agents to spawn (default: 2)
   */
  @Pure
  int getAgentCount();
}
