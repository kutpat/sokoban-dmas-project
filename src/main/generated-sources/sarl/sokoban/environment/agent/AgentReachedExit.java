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
 * Event to announce agent reached exit.
 */
@SarlSpecification("0.15")
@SarlElementType(15)
@XbaseGenerated
@SuppressWarnings("all")
public class AgentReachedExit extends Event {
  public final UUID agentId;

  public final Point2i exitPosition;

  public AgentReachedExit(final UUID agentId, final Point2i exitPosition) {
    this.agentId = agentId;
    this.exitPosition = exitPosition;
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
    AgentReachedExit other = (AgentReachedExit) obj;
    if (!Objects.equals(this.agentId, other.agentId))
      return false;
    return super.equals(obj);
  }

  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Objects.hashCode(this.agentId);
    return result;
  }

  /**
   * Returns a String representation of the AgentReachedExit event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("agentId", this.agentId);
    builder.add("exitPosition", this.exitPosition);
  }

  @SyntheticMember
  private static final long serialVersionUID = 991139008L;
}
