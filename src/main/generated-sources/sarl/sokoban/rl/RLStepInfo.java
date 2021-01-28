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
import java.util.Objects;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;

/**
 * Additional information about a step.
 */
@SarlSpecification("0.15")
@SarlElementType(10)
@XbaseGenerated
@SuppressWarnings("all")
public class RLStepInfo implements Serializable {
  private final boolean actionValid;

  private final int boxesOnTargets;

  private final int totalBoxes;

  private final int stepCount;

  private final String message;

  public RLStepInfo(final boolean actionValid, final int boxesOnTargets, final int totalBoxes, final int stepCount, final String message) {
    this.actionValid = actionValid;
    this.boxesOnTargets = boxesOnTargets;
    this.totalBoxes = totalBoxes;
    this.stepCount = stepCount;
    this.message = message;
  }

  @Pure
  public boolean getActionValid() {
    return this.actionValid;
  }

  @Pure
  public int getBoxesOnTargets() {
    return this.boxesOnTargets;
  }

  @Pure
  public int getTotalBoxes() {
    return this.totalBoxes;
  }

  @Pure
  public int getStepCount() {
    return this.stepCount;
  }

  @Pure
  public String getMessage() {
    return this.message;
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
    RLStepInfo other = (RLStepInfo) obj;
    if (other.actionValid != this.actionValid)
      return false;
    if (other.boxesOnTargets != this.boxesOnTargets)
      return false;
    if (other.totalBoxes != this.totalBoxes)
      return false;
    if (other.stepCount != this.stepCount)
      return false;
    if (!Objects.equals(this.message, other.message))
      return false;
    return super.equals(obj);
  }

  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Boolean.hashCode(this.actionValid);
    result = prime * result + Integer.hashCode(this.boxesOnTargets);
    result = prime * result + Integer.hashCode(this.totalBoxes);
    result = prime * result + Integer.hashCode(this.stepCount);
    result = prime * result + Objects.hashCode(this.message);
    return result;
  }

  @SyntheticMember
  private static final long serialVersionUID = 5474347064L;
}
