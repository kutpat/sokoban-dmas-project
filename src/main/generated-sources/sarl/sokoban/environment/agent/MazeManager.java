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
import sokoban.environment.maze.Direction;
import sokoban.environment.maze.GhostBody;
import sokoban.environment.maze.SuperPowerAccessor;
import sokoban.environment.maze.sokobanBody;
import sokoban.environment.maze.sokobanObject;
import sokoban.rl.SokobanState;

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
   * Create a box at the given position.
   */
  boolean createBox(final int x, final int y);

  /**
   * Create boxes randomly in the maze.
   */
  void createBoxes(final int numberOfBoxes);

  /**
   * Create an exit at the given position.
   */
  boolean createExit(final int x, final int y);

  /**
   * Create exits randomly in the maze.
   */
  void createExits(final int numberOfExits);

  /**
   * Check if all agents have reached exits.
   * 
   * Only counts sokobanBody instances (autonomous SokobanAgent),
   * excludes GhostBody.
   * 
   * All agents are autonomous (project requirement).
   * 
   * @return true if all agents are at exit positions
   */
  boolean allAgentsAtExit();

  /**
   * Check if all boxes are placed on goal cells (exits).
   * This is the correct win condition for Sokoban.
   * 
   * @return true if all boxes are on goals
   */
  boolean allBoxesOnGoals();

  /**
   * Get all exit positions.
   */
  @Pure
  List<Point2i> getExitPositions();

  /**
   * Extract SokobanState from current maze state for RL.
   * 
   * @param stepCount current step count
   * @return SokobanState representation of current state
   */
  SokobanState extractSokobanState(final int stepCount);

  /**
   * Execute a player movement action directly (for RL).
   * 
   * @param direction the direction to move
   * @return true if action was valid and executed
   */
  boolean executePlayerAction(final Direction direction);

  /**
   * Count boxes currently on target cells (exits).
   * 
   * @return number of boxes on targets
   */
  int countBoxesOnTargets();

  /**
   * Count agents currently at exit positions.
   * 
   * @return number of agents at exits
   */
  int countAgentsAtExit();

  /**
   * Get total number of boxes in the maze.
   * 
   * @return total number of boxes
   */
  @Pure
  int getTotalBoxCount();

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

    public boolean createBox(final int x, final int y) {
      try {
        ensureCallerInLocalThread();
        return this.capacity.createBox(x, y);
      } finally {
        resetCallerInLocalThread();
      }
    }

    public void createBoxes(final int numberOfBoxes) {
      try {
        ensureCallerInLocalThread();
        this.capacity.createBoxes(numberOfBoxes);
      } finally {
        resetCallerInLocalThread();
      }
    }

    public boolean createExit(final int x, final int y) {
      try {
        ensureCallerInLocalThread();
        return this.capacity.createExit(x, y);
      } finally {
        resetCallerInLocalThread();
      }
    }

    public void createExits(final int numberOfExits) {
      try {
        ensureCallerInLocalThread();
        this.capacity.createExits(numberOfExits);
      } finally {
        resetCallerInLocalThread();
      }
    }

    public boolean allAgentsAtExit() {
      try {
        ensureCallerInLocalThread();
        return this.capacity.allAgentsAtExit();
      } finally {
        resetCallerInLocalThread();
      }
    }

    public boolean allBoxesOnGoals() {
      try {
        ensureCallerInLocalThread();
        return this.capacity.allBoxesOnGoals();
      } finally {
        resetCallerInLocalThread();
      }
    }

    public List<Point2i> getExitPositions() {
      try {
        ensureCallerInLocalThread();
        return this.capacity.getExitPositions();
      } finally {
        resetCallerInLocalThread();
      }
    }

    public SokobanState extractSokobanState(final int stepCount) {
      try {
        ensureCallerInLocalThread();
        return this.capacity.extractSokobanState(stepCount);
      } finally {
        resetCallerInLocalThread();
      }
    }

    public boolean executePlayerAction(final Direction direction) {
      try {
        ensureCallerInLocalThread();
        return this.capacity.executePlayerAction(direction);
      } finally {
        resetCallerInLocalThread();
      }
    }

    public int countBoxesOnTargets() {
      try {
        ensureCallerInLocalThread();
        return this.capacity.countBoxesOnTargets();
      } finally {
        resetCallerInLocalThread();
      }
    }

    public int countAgentsAtExit() {
      try {
        ensureCallerInLocalThread();
        return this.capacity.countAgentsAtExit();
      } finally {
        resetCallerInLocalThread();
      }
    }

    public int getTotalBoxCount() {
      try {
        ensureCallerInLocalThread();
        return this.capacity.getTotalBoxCount();
      } finally {
        resetCallerInLocalThread();
      }
    }
  }
}
