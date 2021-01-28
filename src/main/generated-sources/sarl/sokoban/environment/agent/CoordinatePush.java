/**
 * Communication events for agent coordination.
 * 
 * Agents use these events to coordinate box pushing and movement.
 */
package sokoban.environment.agent;

import framework.math.Point2i;
import io.sarl.lang.core.Event;
import io.sarl.lang.core.annotation.SarlElementType;
import io.sarl.lang.core.annotation.SarlSpecification;
import io.sarl.lang.core.annotation.SyntheticMember;
import java.util.Objects;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;
import sokoban.environment.maze.Direction;

/**
 * Event to coordinate box pushing.
 * 
 * Agents use this to coordinate simultaneous box pushes.
 */
@SarlSpecification("0.15")
@SarlElementType(15)
@XbaseGenerated
@SuppressWarnings("all")
public class CoordinatePush extends Event {
  public final UUID coordinatorId;

  public final Point2i boxPosition;

  public final Direction pushDirection;

  public final boolean ready;

  public CoordinatePush(final UUID coordinatorId, final Point2i boxPosition, final Direction pushDirection, final boolean ready) {
    this.coordinatorId = coordinatorId;
    this.boxPosition = boxPosition;
    this.pushDirection = pushDirection;
    this.ready = ready;
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
    CoordinatePush other = (CoordinatePush) obj;
    if (!Objects.equals(this.coordinatorId, other.coordinatorId))
      return false;
    if (other.ready != this.ready)
      return false;
    return super.equals(obj);
  }

  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Objects.hashCode(this.coordinatorId);
    result = prime * result + Boolean.hashCode(this.ready);
    return result;
  }

  /**
   * Returns a String representation of the CoordinatePush event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("coordinatorId", this.coordinatorId);
    builder.add("boxPosition", this.boxPosition);
    builder.add("pushDirection", this.pushDirection);
    builder.add("ready", this.ready);
  }

  @SyntheticMember
  private static final long serialVersionUID = -569880684L;
}
