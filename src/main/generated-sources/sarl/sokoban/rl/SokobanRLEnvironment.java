/**
 * RL-compatible wrapper for Sokoban environment.
 * 
 * This class provides the standard RL interface:
 * - step(action) → (state, reward, done, info)
 * - reset() → initial state
 * - getState() → current state
 * 
 * Bridges RL frameworks (Python/Java) to the Sokoban environment.
 */
package sokoban.rl;

import io.sarl.lang.core.annotation.SarlElementType;
import io.sarl.lang.core.annotation.SarlSpecification;
import io.sarl.lang.core.annotation.SyntheticMember;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;
import sokoban.environment.agent.MazeManager;
import sokoban.environment.maze.Direction;

/**
 * RL-compatible wrapper for Sokoban environment.
 * 
 * Provides standard RL interface for training:
 * - step(action) - Execute action and return result
 * - reset() - Reset to initial state
 * - getState() - Get current state
 * 
 * This wrapper manages:
 * - Episode state (step count, termination)
 * - State tracking (previous state for reward calculation)
 * - Action validation
 * - Reward computation
 */
@SarlSpecification("0.15")
@SarlElementType(10)
@XbaseGenerated
@SuppressWarnings("all")
public class SokobanRLEnvironment {
  private final MazeManager mazeManager;

  private final int maxSteps;

  private SokobanState currentState = null;

  private SokobanState previousState = null;

  private int stepCount = 0;

  private boolean episodeDone = false;

  private SokobanState initialState = null;

  /**
   * Create RL environment wrapper.
   * 
   * @param mazeManager the maze manager to use
   * @param maxSteps maximum steps per episode (0 = no limit)
   */
  public SokobanRLEnvironment(final MazeManager mazeManager, final int maxSteps) {
    this.mazeManager = mazeManager;
    this.maxSteps = maxSteps;
  }

  /**
   * Execute an action and return the result.
   * 
   * @param action integer action: 0=UP, 1=RIGHT, 2=DOWN, 3=LEFT
   * @return RLStepResult containing (state, reward, done, info)
   */
  public RLStepResult step(final int action) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe field PENALTY_INVALID_MOVE is not visible"
      + "\nThe field PENALTY_PER_STEP is not visible"
      + "\nThe field REWARD_ALL_BOXES_ON_TARGETS is not visible");
  }

  /**
   * Execute an action in the environment.
   * 
   * @param direction the direction to move
   * @return true if action was valid and executed
   */
  private boolean executeAction(final Direction direction) {
    return this.mazeManager.executePlayerAction(direction);
  }

  /**
   * Reset the environment to initial state.
   * 
   * @return initial SokobanState
   */
  public SokobanState reset() {
    this.stepCount = 0;
    this.episodeDone = false;
    this.previousState = null;
    this.currentState = this.mazeManager.extractSokobanState(0);
    this.initialState = this.currentState.copy();
    return this.currentState;
  }

  /**
   * Get current state without executing an action.
   * 
   * @return current SokobanState
   */
  @Pure
  public SokobanState getState() {
    if ((this.currentState == null)) {
      this.currentState = this.mazeManager.extractSokobanState(this.stepCount);
    }
    return this.currentState;
  }

  /**
   * Check if episode is done.
   * 
   * @return true if episode terminated
   */
  @Pure
  public boolean isDone() {
    return this.episodeDone;
  }

  /**
   * Get current step count.
   * 
   * @return current step count
   */
  @Pure
  public int getStepCount() {
    return this.stepCount;
  }

  /**
   * Get maximum steps per episode.
   * 
   * @return max steps (0 = no limit)
   */
  @Pure
  public int getMaxSteps() {
    return this.maxSteps;
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
    SokobanRLEnvironment other = (SokobanRLEnvironment) obj;
    if (other.maxSteps != this.maxSteps)
      return false;
    if (other.stepCount != this.stepCount)
      return false;
    if (other.episodeDone != this.episodeDone)
      return false;
    return super.equals(obj);
  }

  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Integer.hashCode(this.maxSteps);
    result = prime * result + Integer.hashCode(this.stepCount);
    result = prime * result + Boolean.hashCode(this.episodeDone);
    return result;
  }
}
