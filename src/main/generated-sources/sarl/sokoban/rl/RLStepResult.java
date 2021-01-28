/**
 * Result of a step in the RL Sokoban environment.
 * 
 * This class encapsulates the standard RL step return value:
 * (state, reward, done, info)
 */
package sokoban.rl;

import io.sarl.lang.core.annotation.SarlElementType;
import io.sarl.lang.core.annotation.SarlSpecification;
import io.sarl.lang.core.annotation.SyntheticMember;
import java.io.Serializable;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;

/**
 * Result of a step in the RL Sokoban environment.
 * 
 * Standard RL interface return value:
 * - state: Current state after action
 * - reward: Reward for this step
 * - done: Whether episode terminated
 * - info: Additional information (optional)
 */
@SarlSpecification("0.15")
@SarlElementType(10)
@XbaseGenerated
@SuppressWarnings("all")
public class RLStepResult implements Serializable {
  private final SokobanState state;

  private final double reward;

  private final boolean done;

  private final RLStepInfo info;

  public RLStepResult(final SokobanState state, final double reward, final boolean done, final RLStepInfo info) {
    this.state = state;
    this.reward = reward;
    this.done = done;
    this.info = info;
  }

  @Pure
  public SokobanState getState() {
    return this.state;
  }

  @Pure
  public double getReward() {
    return this.reward;
  }

  @Pure
  public boolean isDone() {
    return this.done;
  }

  @Pure
  public RLStepInfo getInfo() {
    return this.info;
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
    RLStepResult other = (RLStepResult) obj;
    if (Double.doubleToLongBits(other.reward) != Double.doubleToLongBits(this.reward))
      return false;
    if (other.done != this.done)
      return false;
    return super.equals(obj);
  }

  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Double.hashCode(this.reward);
    result = prime * result + Boolean.hashCode(this.done);
    return result;
  }

  @SyntheticMember
  private static final long serialVersionUID = 4605555660L;
}
