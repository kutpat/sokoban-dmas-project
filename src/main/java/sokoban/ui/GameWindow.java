package sokoban.ui;

import javax.swing.*;
import java.awt.*;
import java.util.function.Supplier;

/**
 * Simple window + game loop for rendering a Sokoban-style grid.
 * Usage:
 *   GameWindow.launch(() -> yourSnapshotCharGrid, 24);
 *
 * The supplier must return a char[width][height] or null if not ready yet.
 * Valid chars: '#'(wall), ' '(floor), 'A'(agent), 'B'(box), 'E'(exit).
 */
public final class GameWindow {

    private GameWindow() { /* no-op */ }

    /**
     * Launches the window and starts a repaint timer (about 60 FPS).
     * @param snapshotSupplier supplies the current grid or null if not ready.
     * @param tileSize pixels per tile (e.g., 24).
     */
    public static void launch(Supplier<char[][]> snapshotSupplier, int tileSize) {
        SwingUtilities.invokeLater(() -> {
            // If supplier is null or not ready, fall back to a tiny demo grid
            Supplier<char[][]> safeSupplier = () -> {
                try {
                    return snapshotSupplier == null ? null : snapshotSupplier.get();
                } catch (Throwable t) {
                    t.printStackTrace();
                    return null;
                }
            };

            char[][] initial = safeSupplier.get();
            if (initial == null) {
                initial = demoGrid();
            }

            BoardPanel board = new BoardPanel(safeSupplier, tileSize, detectWidth(initial), detectHeight(initial));
            JFrame frame = new JFrame("Sokoban Viewer");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setContentPane(board);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            // ~60 FPS
            new Timer(16, e -> board.repaint()).start();
        });
    }

    private static int detectWidth(char[][] grid) {
        return (grid == null) ? 8 : grid.length;
    }

    private static int detectHeight(char[][] grid) {
        if (grid == null) return 3;
        int h = 0;
        for (int x = 0; x < grid.length; x++) {
            h = Math.max(h, grid[x] == null ? 0 : grid[x].length);
        }
        return h == 0 ? 3 : h;
    }

    // Tiny fallback grid so the window shows something if your supplier isn’t ready yet.
    private static char[][] demoGrid() {
        // 8x3: ######## / #A   E# / ########
        int w = 8, h = 3;
        char[][] g = new char[w][h];
        for (int x = 0; x < w; x++) { g[x][0] = '#'; g[x][2] = '#'; }
        for (int x = 0; x < w; x++) g[x][1] = ' ';
        g[0][1] = '#'; g[7][1] = '#';
        g[1][1] = 'A'; g[6][1] = 'E';
        return g;
    }
}
