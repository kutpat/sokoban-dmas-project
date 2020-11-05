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

import io.sarl.lang.core.AgentTrait;
import io.sarl.lang.core.Capacity;
import io.sarl.lang.core.DefaultSkill;
import io.sarl.lang.core.annotation.SarlElementType;
import io.sarl.lang.core.annotation.SarlSpecification;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;
import sokoban.environment.maze.Direction;

/**
 * Capacity to move in the maze.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@DefaultSkill(DefaultMazeMotionSkill.class)
@FunctionalInterface
@SarlSpecification("0.15")
@SarlElementType(20)
@XbaseGenerated
@SuppressWarnings("all")
public interface MazeMotion extends Capacity {
  void move(final Direction direction);

  /**
   * @ExcludeFromApidoc
   */
  class ContextAwareCapacityWrapper<C extends MazeMotion> extends Capacity.ContextAwareCapacityWrapper<C> implements MazeMotion {
    public ContextAwareCapacityWrapper(final C capacity, final AgentTrait caller) {
      super(capacity, caller);
    }

    public void move(final Direction direction) {
      try {
        ensureCallerInLocalThread();
        this.capacity.move(direction);
      } finally {
        resetCallerInLocalThread();
      }
    }
  }
}
