/**
 * Reward calculation for RL Sokoban environment.
 * 
 * Computes rewards based on state changes and game events.
 */
package sokoban.rl;

import framework.math.Point2i;
import io.sarl.lang.core.annotation.SarlElementType;
import io.sarl.lang.core.annotation.SarlSpecification;
import io.sarl.lang.core.annotation.SyntheticMember;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;

/**
 * Reward calculator for Sokoban RL environment.
 * 
 * Computes rewards based on:
 * - State changes (boxes moved onto/off targets)
 * - Action validity
 * - Terminal conditions
 * - Per-step penalties
 */
@SarlSpecification("0.15")
@SarlElementType(10)
@XbaseGenerated
@SuppressWarnings("all")
public class RewardCalculator {
  /**
   * Reward when a box is moved onto a target cell.
   */
  private static final double REWARD_BOX_ON_TARGET = 10.0;

  /**
   * Per-step penalty to encourage efficiency.
   */
  private static final double PENALTY_PER_STEP = (-0.1);

  /**
   * Penalty for invalid moves (wall collision, boundary violation).
   */
  private static final double PENALTY_INVALID_MOVE = (-1.0);

  /**
   * Terminal reward when all boxes are on targets.
   */
  private static final double REWARD_ALL_BOXES_ON_TARGETS = 100.0;

  /**
   * Penalty when a box is pushed off a target cell.
   */
  private static final double PENALTY_BOX_OFF_TARGET = (-0.5);

  /**
   * Compute reward based on state changes.
   * 
   * @param prevState previous state (before action)
   * @param newState new state (after action)
   * @param actionValid whether the action was valid (not blocked)
   * @return computed reward
   */
  @Pure
  public static double computeReward(final SokobanState prevState, final SokobanState newState, final boolean actionValid) {
    double reward = 0.0;
    if ((!actionValid)) {
      reward = (reward + RewardCalculator.PENALTY_INVALID_MOVE);
      return reward;
    }
    reward = (reward + RewardCalculator.PENALTY_PER_STEP);
    int boxesOnTargetBefore = RewardCalculator.countBoxesOnTargets(prevState);
    int boxesOnTargetAfter = RewardCalculator.countBoxesOnTargets(newState);
    if ((boxesOnTargetAfter > boxesOnTargetBefore)) {
      int boxesGained = (boxesOnTargetAfter - boxesOnTargetBefore);
      reward = (reward + (boxesGained * RewardCalculator.REWARD_BOX_ON_TARGET));
    } else {
      if ((boxesOnTargetAfter < boxesOnTargetBefore)) {
        int boxesLost = (boxesOnTargetBefore - boxesOnTargetAfter);
        reward = (reward + (boxesLost * RewardCalculator.PENALTY_BOX_OFF_TARGET));
      }
    }
    boolean _allBoxesOnTargets = newState.allBoxesOnTargets();
    if (_allBoxesOnTargets) {
      reward = (reward + RewardCalculator.REWARD_ALL_BOXES_ON_TARGETS);
    }
    return reward;
  }

  /**
   * Compute reward for terminal state.
   * 
   * @param state terminal state
   * @return terminal reward
   */
  @Pure
  public static double computeTerminalReward(final SokobanState state) {
    boolean _allBoxesOnTargets = state.allBoxesOnTargets();
    if (_allBoxesOnTargets) {
      return RewardCalculator.REWARD_ALL_BOXES_ON_TARGETS;
    }
    return 0.0;
  }

  /**
   * Count how many boxes are on target cells.
   * 
   * @param state the state to check
   * @return number of boxes on targets
   */
  @Pure
  public static int countBoxesOnTargets(final SokobanState state) {
    int count = 0;
    List<Boolean> onTargetFlags = state.getBoxOnTargetFlags();
    for (final Boolean onTarget : onTargetFlags) {
      if (((onTarget) == null ? false : (onTarget).booleanValue())) {
        count++;
      }
    }
    return count;
  }

  /**
   * Check if a box moved from one position to another.
   * 
   * @param prevState previous state
   * @param newState new state
   * @return list of box indices that moved, empty if no boxes moved
   */
  @Pure
  public static List<Integer> getMovedBoxes(final SokobanState prevState, final SokobanState newState) {
    ArrayList<Integer> movedBoxes = new ArrayList<Integer>();
    List<Point2i> prevBoxPositions = prevState.getBoxPositions();
    List<Point2i> newBoxPositions = newState.getBoxPositions();
    int _size = prevBoxPositions.size();
    int _size_1 = newBoxPositions.size();
    if ((_size != _size_1)) {
      return movedBoxes;
    }
    for (int i = 0; (i < prevBoxPositions.size()); i++) {
      {
        Point2i prevPos = prevBoxPositions.get(i);
        Point2i newPos = newBoxPositions.get(i);
        boolean _equals = prevPos.equals(newPos);
        if ((!_equals)) {
          movedBoxes.add(Integer.valueOf(i));
        }
      }
    }
    return movedBoxes;
  }

  /**
   * Check if a specific box moved onto a target.
   * 
   * @param prevState previous state
   * @param newState new state
   * @param boxIndex index of the box to check
   * @return true if box moved onto target
   */
  @Pure
  public static boolean boxMovedOntoTarget(final SokobanState prevState, final SokobanState newState, final int boxIndex) {
    if (((boxIndex < 0) || (boxIndex >= prevState.getBoxOnTargetFlags().size()))) {
      return false;
    }
    Boolean wasOnTarget = prevState.getBoxOnTargetFlags().get(boxIndex);
    Boolean isOnTarget = newState.getBoxOnTargetFlags().get(boxIndex);
    return ((!((wasOnTarget) == null ? false : (wasOnTarget).booleanValue())) && ((isOnTarget) == null ? false : (isOnTarget).booleanValue()));
  }

  /**
   * Check if a specific box moved off a target.
   * 
   * @param prevState previous state
   * @param newState new state
   * @param boxIndex index of the box to check
   * @return true if box moved off target
   */
  @Pure
  public static boolean boxMovedOffTarget(final SokobanState prevState, final SokobanState newState, final int boxIndex) {
    if (((boxIndex < 0) || (boxIndex >= prevState.getBoxOnTargetFlags().size()))) {
      return false;
    }
    Boolean wasOnTarget = prevState.getBoxOnTargetFlags().get(boxIndex);
    Boolean isOnTarget = newState.getBoxOnTargetFlags().get(boxIndex);
    return (((wasOnTarget) == null ? false : (wasOnTarget).booleanValue()) && (!((isOnTarget) == null ? false : (isOnTarget).booleanValue())));
  }

  @SyntheticMember
  public RewardCalculator() {
    super();
  }
}
