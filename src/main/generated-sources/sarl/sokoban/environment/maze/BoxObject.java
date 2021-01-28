/**
 * Box object in the Sokoban environment.
 * 
 * Boxes can be pushed by the player but block movement.
 */
package sokoban.environment.maze;

import framework.math.Point2i;
import io.sarl.lang.core.annotation.SarlElementType;
import io.sarl.lang.core.annotation.SarlSpecification;
import io.sarl.lang.core.annotation.SyntheticMember;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;

/**
 * Box artifact in the Sokoban environment.
 * Boxes can be pushed by the agent and must be placed on target cells.
 * 
 * This is a passive artifact - it has state (position, onTarget) but no autonomy.
 * 
 * @author Generated
 */
@SarlSpecification("0.15")
@SarlElementType(10)
@XbaseGenerated
@SuppressWarnings("all")
public class BoxObject extends sokobanObject {
  /**
   * Flag indicating if this box is currently on a target cell.
   * Updated by the environment when box position changes.
   */
  private boolean onTarget = false;

  public BoxObject(final int x, final int y, final Maze maze) {
    super(x, y, maze);
    this.updateOnTargetStatus();
  }

  public BoxObject(final Point2i position, final Maze maze) {
    super(position, maze);
    this.updateOnTargetStatus();
  }

  @Pure
  public final boolean isOccluder() {
    return true;
  }

  @Pure
  public final boolean isPickable() {
    return false;
  }

  /**
   * Check if this box is on a target cell.
   * 
   * @return true if box is on a target cell
   */
  @Pure
  public boolean isOnTarget() {
    return this.onTarget;
  }

  /**
   * Update the onTarget flag based on current position.
   * Called by environment when box position changes.
   */
  public void updateOnTargetStatus() {
    Maze maze = this.getMaze();
    if ((maze != null)) {
      this.onTarget = maze.isExit(this.getPosition().getX(), this.getPosition().getY());
    }
  }

  /**
   * Set the onTarget flag explicitly.
   * Used by environment for state management.
   * 
   * @param onTarget the new onTarget status
   */
  public void setOnTarget(final boolean onTarget) {
    this.onTarget = onTarget;
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
    BoxObject other = (BoxObject) obj;
    if (other.onTarget != this.onTarget)
      return false;
    return super.equals(obj);
  }

  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Boolean.hashCode(this.onTarget);
    return result;
  }

  @SyntheticMember
  private static final long serialVersionUID = -2377854057L;
}
