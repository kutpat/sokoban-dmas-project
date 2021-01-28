/**
 * BoxTask structure for tracking box-clearing tasks.
 * 
 * Represents a coordinated task to move a box to a clearing point.
 */
package sokoban.environment.agent;

import framework.math.Point2i;
import io.sarl.lang.core.Event;
import io.sarl.lang.core.annotation.SarlElementType;
import io.sarl.lang.core.annotation.SarlSpecification;
import io.sarl.lang.core.annotation.SyntheticMember;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Event to announce readiness for synchronized push.
 */
@SarlSpecification("0.15")
@SarlElementType(15)
@XbaseGenerated
@SuppressWarnings("all")
public class ReadyForPush extends Event {
  public final UUID taskId;

  public final UUID agentId;

  public final Point2i position;

  public ReadyForPush(final UUID taskId, final UUID agentId, final Point2i position) {
    this.taskId = taskId;
    this.agentId = agentId;
    this.position = position;
  }

  @Override
  @Pure
  @SyntheticMember
  public boolean equals(final Object obj) {
    throw new Error("Unresolved compilation problems:"
      + "\nInvalid supertype. Expecting a class"
      + "\nInvalid supertype. Expecting a class");
  }

  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    throw new Error("Unresolved compilation problems:"
      + "\nInvalid supertype. Expecting a class");
  }

  /**
   * Returns a String representation of the ReadyForPush event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    throw new Error("Unresolved compilation problems:"
      + "\nInvalid supertype. Expecting a class");
  }

  @SyntheticMember
  private static final long serialVersionUID = 377735236L;
}
