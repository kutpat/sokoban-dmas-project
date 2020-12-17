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
 * Box in the sokoban environment.
 * Boxes can be pushed by the player.
 * 
 * @author Generated
 */
@SarlSpecification("0.15")
@SarlElementType(10)
@XbaseGenerated
@SuppressWarnings("all")
public class BoxObject extends sokobanObject {
  public BoxObject(final int x, final int y, final Maze maze) {
    super(x, y, maze);
  }

  public BoxObject(final Point2i position, final Maze maze) {
    super(position, maze);
  }

  @Pure
  public final boolean isOccluder() {
    return true;
  }

  @Pure
  public final boolean isPickable() {
    return false;
  }

  @SyntheticMember
  private static final long serialVersionUID = -1937199526L;
}
