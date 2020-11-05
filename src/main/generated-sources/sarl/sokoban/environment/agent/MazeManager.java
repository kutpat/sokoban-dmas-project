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
import io.sarl.lang.core.annotation.SarlElementType;
import io.sarl.lang.core.annotation.SarlSpecification;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;
import sokoban.environment.maze.AgentBody;
import sokoban.environment.maze.GhostBody;
import sokoban.environment.maze.SuperPowerAccessor;
import sokoban.environment.maze.sokobanBody;
import sokoban.environment.maze.sokobanObject;

/**
 * Capacity to manage a maze.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@SarlSpecification("0.15")
@SarlElementType(20)
@XbaseGenerated
@SuppressWarnings("all")
public interface MazeManager extends Capacity {
  /**
   * Replies the number of bodies in the maze
   */
  @Pure
  int getBodyCount();

  /**
   * Apply the list of actions.
   * @return true if the sokoban is dead.
   */
  boolean applyActions(final List<MazeChangeQuery> actions);

  /**
   * Replies the perceptions.
   */
  @Pure
  Map<AgentBody, List<sokobanObject>> getPerceptions();

  /**
   * Replies the width of the maze.
   */
  @Pure
  int getMazeWidth();

  /**
   * Replies the height of the maze.
   */
  @Pure
  int getMazeHeight();

  /**
   * Replies all the objects.
   */
  List<sokobanObject> getsokobanObjects();

  /**
   * Replies a specific body.
   */
  @Pure
  AgentBody getAgentBody(final UUID id);

  /**
   * Replies super power accessor.
   */
  @Pure
  SuperPowerAccessor getSuperPowerAccessor(final UUID id);

  /**
   * Create a ghost body.
   */
  GhostBody createGhost(final int perceptionDistance);

  /**
   * Create a sokoban body.
   */
  sokobanBody createsokoban();

  /**
   * @ExcludeFromApidoc
   */
  class ContextAwareCapacityWrapper<C extends MazeManager> extends Capacity.ContextAwareCapacityWrapper<C> implements MazeManager {
    public ContextAwareCapacityWrapper(final C capacity, final AgentTrait caller) {
      super(capacity, caller);
    }

    public int getBodyCount() {
      try {
        ensureCallerInLocalThread();
        return this.capacity.getBodyCount();
      } finally {
        resetCallerInLocalThread();
      }
    }

    public boolean applyActions(final List<MazeChangeQuery> actions) {
      try {
        ensureCallerInLocalThread();
        return this.capacity.applyActions(actions);
      } finally {
        resetCallerInLocalThread();
      }
    }

    public Map<AgentBody, List<sokobanObject>> getPerceptions() {
      try {
        ensureCallerInLocalThread();
        return this.capacity.getPerceptions();
      } finally {
        resetCallerInLocalThread();
      }
    }

    public int getMazeWidth() {
      try {
        ensureCallerInLocalThread();
        return this.capacity.getMazeWidth();
      } finally {
        resetCallerInLocalThread();
      }
    }

    public int getMazeHeight() {
      try {
        ensureCallerInLocalThread();
        return this.capacity.getMazeHeight();
      } finally {
        resetCallerInLocalThread();
      }
    }

    public List<sokobanObject> getsokobanObjects() {
      try {
        ensureCallerInLocalThread();
        return this.capacity.getsokobanObjects();
      } finally {
        resetCallerInLocalThread();
      }
    }

    public AgentBody getAgentBody(final UUID id) {
      try {
        ensureCallerInLocalThread();
        return this.capacity.getAgentBody(id);
      } finally {
        resetCallerInLocalThread();
      }
    }

    public SuperPowerAccessor getSuperPowerAccessor(final UUID id) {
      try {
        ensureCallerInLocalThread();
        return this.capacity.getSuperPowerAccessor(id);
      } finally {
        resetCallerInLocalThread();
      }
    }

    public GhostBody createGhost(final int perceptionDistance) {
      try {
        ensureCallerInLocalThread();
        return this.capacity.createGhost(perceptionDistance);
      } finally {
        resetCallerInLocalThread();
      }
    }

    public sokobanBody createsokoban() {
      try {
        ensureCallerInLocalThread();
        return this.capacity.createsokoban();
      } finally {
        resetCallerInLocalThread();
      }
    }
  }
}
