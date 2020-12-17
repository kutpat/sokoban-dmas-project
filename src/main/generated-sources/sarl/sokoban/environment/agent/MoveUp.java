/**
 * Movement events for Sokoban player.
 */
package sokoban.environment.agent;

import io.sarl.lang.core.Address;
import io.sarl.lang.core.Event;
import io.sarl.lang.core.annotation.SarlElementType;
import io.sarl.lang.core.annotation.SarlSpecification;
import io.sarl.lang.core.annotation.SyntheticMember;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;

/**
 * Event to move the player up.
 */
@SarlSpecification("0.15")
@SarlElementType(15)
@XbaseGenerated
@SuppressWarnings("all")
public class MoveUp extends Event {
  @SyntheticMember
  public MoveUp() {
    super();
  }

  @SyntheticMember
  public MoveUp(final Address source) {
    super(source);
  }

  @SyntheticMember
  private static final long serialVersionUID = 588368462L;
}
