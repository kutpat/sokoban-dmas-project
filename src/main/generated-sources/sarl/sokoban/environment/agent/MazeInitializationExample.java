/**
 * Example initialization showing how to add boxes to the maze.
 * 
 * This demonstrates how to initialize the maze with 2 boxes
 * and integrate with the existing maze environment.
 */
package sokoban.environment.agent;

import framework.math.Point2i;
import io.sarl.lang.core.annotation.SarlElementType;
import io.sarl.lang.core.annotation.SarlSpecification;
import io.sarl.lang.core.annotation.SyntheticMember;
import java.util.List;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;
import sokoban.environment.maze.BoxObject;
import sokoban.environment.maze.Maze;
import sokoban.environment.maze.sokobanObject;

/**
 * Example helper class showing how to initialize boxes in the maze.
 * 
 * Usage in Environment agent:
 * 
 * on Initialize {
 *     // ... existing initialization code ...
 *     // After creating the maze, add boxes
 *     initializeBoxes(maze, 2)
 * }
 */
@SarlSpecification("0.15")
@SarlElementType(10)
@XbaseGenerated
@SuppressWarnings("all")
public class MazeInitializationExample {
  /**
   * Initialize boxes in the maze at walkable positions.
   * 
   * @param maze the maze instance
   * @param numberOfBoxes number of boxes to create
   */
  public static void initializeBoxes(final Maze maze, final int numberOfBoxes) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe field width is not visible"
      + "\nThe field height is not visible");
  }

  /**
   * Initialize boxes at specific positions.
   * 
   * @param maze the maze instance
   * @param positions list of Point2i positions for boxes
   */
  public static void initializeBoxesAt(final Maze maze, final List<Point2i> positions) {
    for (final Point2i pos : positions) {
      {
        int x = pos.getX();
        int y = pos.getY();
        if ((maze.inBounds(x, y) && maze.isWalkable(x, y))) {
          sokobanObject obj = maze.getObjectAt(x, y);
          if (((obj == null) || obj.isPickable())) {
            BoxObject box = new BoxObject(x, y, maze);
            maze.setObjectAt(x, y, box);
          }
        }
      }
    }
  }

  @SyntheticMember
  public MazeInitializationExample() {
    super();
  }
}
