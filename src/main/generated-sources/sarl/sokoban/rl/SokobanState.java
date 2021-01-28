/**
 * Serializable state representation for RL Sokoban environment.
 * 
 * This class represents the complete state of a Sokoban puzzle,
 * suitable for serialization, state comparison, and RL training.
 */
package sokoban.rl;

import framework.math.Point2i;
import io.sarl.lang.core.annotation.SarlElementType;
import io.sarl.lang.core.annotation.SarlSpecification;
import io.sarl.lang.core.annotation.SyntheticMember;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;

/**
 * Serializable state representation for Sokoban RL environment.
 * 
 * This state captures:
 * - Agent position
 * - Box positions and onTarget flags
 * - Wall layout (immutable reference)
 * - Target positions (immutable reference)
 * - Step count
 * 
 * The state is serializable for RL frameworks, replay buffers, and debugging.
 */
@SarlSpecification("0.15")
@SarlElementType(10)
@XbaseGenerated
@SuppressWarnings("all")
public class SokobanState implements Serializable {
  private final Point2i agentPosition;

  private final List<Point2i> boxPositions;

  private final List<Boolean> boxOnTargetFlags;

  private final boolean[][] wallGrid;

  private final List<Point2i> targetPositions;

  private final int stepCount;

  private final int width;

  private final int height;

  public SokobanState(final Point2i agentPosition, final List<Point2i> boxPositions, final List<Boolean> boxOnTargetFlags, final boolean[][] wallGrid, final List<Point2i> targetPositions, final int stepCount, final int width, final int height) {
    this.agentPosition = agentPosition;
    ArrayList<Point2i> _arrayList = new ArrayList<Point2i>(boxPositions);
    this.boxPositions = _arrayList;
    ArrayList<Boolean> _arrayList_1 = new ArrayList<Boolean>(boxOnTargetFlags);
    this.boxOnTargetFlags = _arrayList_1;
    this.wallGrid = wallGrid;
    ArrayList<Point2i> _arrayList_2 = new ArrayList<Point2i>(targetPositions);
    this.targetPositions = _arrayList_2;
    this.stepCount = stepCount;
    this.width = width;
    this.height = height;
  }

  @Pure
  public Point2i getAgentPosition() {
    return this.agentPosition;
  }

  @Pure
  public List<Point2i> getBoxPositions() {
    return this.boxPositions;
  }

  @Pure
  public List<Boolean> getBoxOnTargetFlags() {
    return this.boxOnTargetFlags;
  }

  @Pure
  public boolean[][] getWallGrid() {
    return this.wallGrid;
  }

  @Pure
  public List<Point2i> getTargetPositions() {
    return this.targetPositions;
  }

  @Pure
  public int getStepCount() {
    return this.stepCount;
  }

  @Pure
  public int getWidth() {
    return this.width;
  }

  @Pure
  public int getHeight() {
    return this.height;
  }

  /**
   * Check if all boxes are on target cells.
   * 
   * @return true if all boxes are on targets
   */
  @Pure
  public boolean allBoxesOnTargets() {
    boolean _isEmpty = this.boxOnTargetFlags.isEmpty();
    if (_isEmpty) {
      return false;
    }
    for (final Boolean onTarget : this.boxOnTargetFlags) {
      if ((!((onTarget) == null ? false : (onTarget).booleanValue()))) {
        return false;
      }
    }
    return true;
  }

  /**
   * Convert state to vector representation for neural networks.
   * Format: [agent_x, agent_y, box1_x, box1_y, box1_onTarget, box2_x, box2_y, box2_onTarget, ...]
   * 
   * @return state as double array
   */
  @Pure
  public double[] toVector() {
    int _size = this.boxPositions.size();
    int size = (2 + (_size * 3));
    Object _newInstance = Array.newInstance(double.class, size);
    double[] vector = ((double[]) _newInstance);
    int idx = 0;
    int _plusPlus = idx++;
    int _x = this.agentPosition.getX();
    vector[_plusPlus] = (_x / this.width);
    int _plusPlus_1 = idx++;
    int _y = this.agentPosition.getY();
    vector[_plusPlus_1] = (_y / this.height);
    for (int i = 0; (i < this.boxPositions.size()); i++) {
      {
        Point2i boxPos = this.boxPositions.get(i);
        int _plusPlus_2 = idx++;
        int _x_1 = boxPos.getX();
        vector[_plusPlus_2] = (_x_1 / this.width);
        int _plusPlus_3 = idx++;
        int _y_1 = boxPos.getY();
        vector[_plusPlus_3] = (_y_1 / this.height);
        int _plusPlus_4 = idx++;
        double _xifexpression = (double) 0;
        Boolean _get = this.boxOnTargetFlags.get(i);
        if (((_get) == null ? false : (_get).booleanValue())) {
          _xifexpression = 1.0;
        } else {
          _xifexpression = 0.0;
        }
        vector[_plusPlus_4] = _xifexpression;
      }
    }
    return vector;
  }

  /**
   * Convert state to grid representation for CNN.
   * Returns 4 channels: [walls, targets, agent, boxes]
   * 
   * @return 4D array [channels][height][width]
   */
  @Pure
  public double[][][] toGrid() {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method set(int, double) is undefined for the type double"
      + "\nThe method set(int, double) is undefined for the type double"
      + "\nThe method set(int, double) is undefined for the type double"
      + "\nThe method set(int, double) is undefined for the type double"
      + "\nType mismatch: cannot convert from double[] to double"
      + "\nType mismatch: cannot convert from double[][] to double[][][]");
  }

  /**
   * Compute hash code for state comparison.
   * 
   * @return hash code
   */
  @Pure
  @Override
  public int hashCode() {
    int hash = this.agentPosition.hashCode();
    hash = ((hash * 31) + this.width);
    hash = ((hash * 31) + this.height);
    for (final Point2i boxPos : this.boxPositions) {
      int _hashCode = boxPos.hashCode();
      hash = ((hash * 31) + _hashCode);
    }
    for (final Boolean onTarget : this.boxOnTargetFlags) {
      int _xifexpression = (int) 0;
      if (((onTarget) == null ? false : (onTarget).booleanValue())) {
        _xifexpression = 1;
      } else {
        _xifexpression = 0;
      }
      hash = ((hash * 31) + _xifexpression);
    }
    return hash;
  }

  /**
   * Check if this state equals another state.
   * 
   * @param obj the other state
   * @return true if states are equal
   */
  @Pure
  @Override
  public boolean equals(final Object obj) {
    if ((obj == this)) {
      return true;
    }
    if (((obj == null) || (!(obj instanceof SokobanState)))) {
      return false;
    }
    SokobanState other = ((SokobanState) obj);
    if (((this.width != other.width) || (this.height != other.height))) {
      return false;
    }
    boolean _equals = this.agentPosition.equals(other.agentPosition);
    if ((!_equals)) {
      return false;
    }
    int _size = this.boxPositions.size();
    int _size_1 = other.boxPositions.size();
    if ((_size != _size_1)) {
      return false;
    }
    for (int i = 0; (i < this.boxPositions.size()); i++) {
      {
        boolean _equals_1 = this.boxPositions.get(i).equals(other.boxPositions.get(i));
        if ((!_equals_1)) {
          return false;
        }
        Boolean _get = this.boxOnTargetFlags.get(i);
        Boolean _get_1 = other.boxOnTargetFlags.get(i);
        boolean _notEquals = (!Objects.equals(_get, _get_1));
        if (_notEquals) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Create a copy of this state.
   * 
   * @return a new state with copied values
   */
  @Pure
  public SokobanState copy() {
    return new SokobanState(
      this.agentPosition, 
      this.boxPositions, 
      this.boxOnTargetFlags, 
      this.wallGrid, 
      this.targetPositions, 
      this.stepCount, 
      this.width, 
      this.height);
  }

  @SyntheticMember
  private static final long serialVersionUID = 7172155440L;
}
