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

/**
 * Event to offer help to another agent.
 * 
 * An agent sends this to indicate it can help with a task.
 */
@SarlSpecification("0.15")
@SarlElementType(15)
@XbaseGenerated
@SuppressWarnings("all")
public class OfferHelp extends Event {
  public final UUID helperId;

  public final Point2i targetPosition;

  public final String message;

  public OfferHelp(final UUID helperId, final Point2i targetPosition, final String message) {
    this.helperId = helperId;
    this.targetPosition = targetPosition;
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
    OfferHelp other = (OfferHelp) obj;
    if (!Objects.equals(this.helperId, other.helperId))
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
    result = prime * result + Objects.hashCode(this.helperId);
    result = prime * result + Objects.hashCode(this.message);
    return result;
  }

  /**
   * Returns a String representation of the OfferHelp event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("helperId", this.helperId);
    builder.add("targetPosition", this.targetPosition);
    builder.add("message", this.message);
  }

  @SyntheticMember
  private static final long serialVersionUID = 4377852433L;
}
