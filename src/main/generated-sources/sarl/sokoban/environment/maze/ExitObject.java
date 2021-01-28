/**
 * Exit object in the Sokoban environment.
 * 
 * Agents must reach exits to complete the simulation.
 */
package sokoban.environment.maze;

import framework.math.Point2i;
import io.sarl.lang.core.annotation.SarlElementType;
import io.sarl.lang.core.annotation.SarlSpecification;
import io.sarl.lang.core.annotation.SyntheticMember;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;

/**
 * Exit in the sokoban environment.
 * Agents can reach exits to complete the simulation.
 * 
 * @author Generated
 */
@SarlSpecification("0.15")
@SarlElementType(10)
@XbaseGenerated
@SuppressWarnings("all")
public class ExitObject extends sokobanObject {
  public ExitObject(final int x, final int y, final Maze maze) {
    super(x, y, maze);
  }

  public ExitObject(final Point2i position, final Maze maze) {
    super(position, maze);
  }

  @Pure
  public final boolean isOccluder() {
    return false;
  }

  @Pure
  public final boolean isPickable() {
    return false;
  }

  @SyntheticMember
  private static final long serialVersionUID = -1937199526L;
}
