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
 * Event to request help pushing a box.
 * 
 * An agent sends this when it needs another agent's help
 * to push a box that requires coordination.
 */
@SarlSpecification("0.15")
@SarlElementType(15)
@XbaseGenerated
@SuppressWarnings("all")
public class RequestHelp extends Event {
  public final UUID requesterId;

  public final Point2i boxPosition;

  public final Direction pushDirection;

  public final String message;

  public RequestHelp(final UUID requesterId, final Point2i boxPosition, final Direction pushDirection, final String message) {
    this.requesterId = requesterId;
    this.boxPosition = boxPosition;
    this.pushDirection = pushDirection;
    this.message = message;
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
    RequestHelp other = (RequestHelp) obj;
    if (!Objects.equals(this.requesterId, other.requesterId))
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
    result = prime * result + Objects.hashCode(this.requesterId);
    result = prime * result + Objects.hashCode(this.message);
    return result;
  }

  /**
   * Returns a String representation of the RequestHelp event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("requesterId", this.requesterId);
    builder.add("boxPosition", this.boxPosition);
    builder.add("pushDirection", this.pushDirection);
    builder.add("message", this.message);
  }

  @SyntheticMember
  private static final long serialVersionUID = 3921295076L;
}
