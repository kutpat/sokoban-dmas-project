/**
 * BoxTask structure for tracking box-clearing tasks.
 * 
 * Represents a coordinated task to move a box to a clearing point.
 */
package sokoban.environment.agent;

import io.sarl.lang.core.Event;
import io.sarl.lang.core.annotation.SarlElementType;
import io.sarl.lang.core.annotation.SarlSpecification;
import io.sarl.lang.core.annotation.SyntheticMember;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Event to accept a box-clearing task.
 */
@SarlSpecification("0.15")
@SarlElementType(15)
@XbaseGenerated
@SuppressWarnings("all")
public class AcceptBoxTask extends Event {
  public final UUID taskId;

  public final UUID agentId;

  public AcceptBoxTask(final UUID taskId, final UUID agentId) {
    this.taskId = taskId;
    this.agentId = agentId;
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
   * Returns a String representation of the AcceptBoxTask event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    throw new Error("Unresolved compilation problems:"
      + "\nInvalid supertype. Expecting a class");
  }

  @SyntheticMember
  private static final long serialVersionUID = -105555175L;
}
