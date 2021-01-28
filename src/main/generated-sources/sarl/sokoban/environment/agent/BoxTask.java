/**
 * BoxTask structure for tracking box-clearing tasks.
 * 
 * Represents a coordinated task to move a box to a clearing point.
 */
package sokoban.environment.agent;

import framework.math.Point2i;
import io.sarl.lang.core.annotation.SarlElementType;
import io.sarl.lang.core.annotation.SarlSpecification;
import io.sarl.lang.core.annotation.SyntheticMember;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;
import sokoban.environment.maze.Direction;

/**
 * Represents a box task with assigned agents and target goal.
 * Boxes are pushed TO goals (exits), not to clearing points.
 */
@SarlSpecification("0.15")
@SarlElementType(10)
@XbaseGenerated
@SuppressWarnings("all")
public class BoxTask implements Serializable {
  private final UUID boxId;

  private Point2i boxPosition;

  private final Point2i targetGoal;

  private final UUID leaderId;

  private final List<UUID> assignedAgents;

  private String taskState;

  private final Direction pushDirection;

  public BoxTask(final UUID boxId, final Point2i boxPosition, final Point2i targetGoal, final UUID leaderId, final Direction pushDirection) {
    this.boxId = boxId;
    this.boxPosition = boxPosition;
    this.targetGoal = targetGoal;
    this.leaderId = leaderId;
    this.pushDirection = pushDirection;
    ArrayList<UUID> _arrayList = new ArrayList<UUID>();
    this.assignedAgents = _arrayList;
    this.assignedAgents.add(leaderId);
    this.taskState = "PROPOSED";
  }

  public boolean addHelper(final UUID agentId) {
    boolean _xifexpression = false;
    boolean _contains = this.assignedAgents.contains(agentId);
    if ((!_contains)) {
      _xifexpression = this.assignedAgents.add(agentId);
    }
    return _xifexpression;
  }

  @Pure
  public boolean isActive() {
    return Objects.equals(this.taskState, "ACTIVE");
  }

  @Pure
  public boolean isCompleted() {
    return Objects.equals(this.taskState, "COMPLETED");
  }

  public void markActive() {
    this.taskState = "ACTIVE";
  }

  public void markCompleted() {
    this.taskState = "COMPLETED";
  }

  /**
   * Get the box position.
   */
  @Pure
  public Point2i getBoxPosition() {
    return this.boxPosition;
  }

  /**
   * Get the target goal.
   */
  @Pure
  public Point2i getTargetGoal() {
    return this.targetGoal;
  }

  /**
   * Legacy method for compatibility - returns targetGoal.
   */
  @Pure
  public Point2i getTargetClearingCell() {
    return this.targetGoal;
  }

  /**
   * Get the box ID.
   */
  @Pure
  public UUID getBoxId() {
    return this.boxId;
  }

  /**
   * Get the assigned agents list.
   */
  @Pure
  public List<UUID> getAssignedAgents() {
    return this.assignedAgents;
  }

  /**
   * Get the task state.
   */
  @Pure
  public String getTaskState() {
    return this.taskState;
  }

  /**
   * Get the push direction.
   */
  @Pure
  public Direction getPushDirection() {
    return this.pushDirection;
  }

  /**
   * Update the box position when box moves.
   */
  public void updateBoxPosition(final Point2i newPosition) {
    this.boxPosition = newPosition;
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
    BoxTask other = (BoxTask) obj;
    if (!Objects.equals(this.boxId, other.boxId))
      return false;
    if (!Objects.equals(this.leaderId, other.leaderId))
      return false;
    if (!Objects.equals(this.taskState, other.taskState))
      return false;
    return super.equals(obj);
  }

  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Objects.hashCode(this.boxId);
    result = prime * result + Objects.hashCode(this.leaderId);
    result = prime * result + Objects.hashCode(this.taskState);
    return result;
  }

  @SyntheticMember
  private static final long serialVersionUID = 2836552422L;
}
