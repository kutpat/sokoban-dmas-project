package sokoban.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.function.Supplier;

/**
 * Classic Sokoban-styled board renderer.
 * Expects char[width][height] with:
 *   '#': wall, ' ': floor, 'A': agent, 'B': box, 'E': goal/exit.
 *
 * Tip: keep each frame's snapshot immutable for thread-safety.
 */
public final class BoardPanel extends JPanel {

    private final Supplier<char[][]> snapshotSupplier;
    private final int tile;
    private int widthTiles;
    private int heightTiles;

    // ===== Theme palette (tweak to taste) =====
    private static final Color FLOOR_BASE   = new Color(224, 215, 200);  // light beige
    private static final Color FLOOR_NOISE1 = new Color(235, 228, 214);
    private static final Color FLOOR_NOISE2 = new Color(216, 205, 188);

    private static final Color WALL_STONE   = new Color(128, 118, 104);  // stone block
    private static final Color WALL_EDGE    = new Color(90, 82, 70);
    private static final Color WALL_HILITE  = new Color(160, 150, 136);

    private static final Color BOX_WOOD     = new Color(167, 124, 73);
    private static final Color BOX_WOOD_DK  = new Color(120, 85, 45);
    private static final Color BOX_EDGE     = new Color(90, 65, 35);
    private static final Color BOX_STRAP    = new Color(70, 50, 30);

    private static final Color AGENT_HELMET = new Color(240, 190, 50);
    private static final Color AGENT_BODY   = new Color(64, 114, 178);
    private static final Color AGENT_OUTLINE= new Color(26, 48, 76);
    private static final Color SHADOW       = new Color(0, 0, 0, 40);

    private static final Color GOAL_TILE    = new Color(170, 210, 180);
    private static final Color GOAL_RING    = new Color(40, 130, 90);

    public BoardPanel(Supplier<char[][]> snapshotSupplier, int tileSize, int initialW, int initialH) {
        this.snapshotSupplier = snapshotSupplier;
        this.tile = Math.max(12, tileSize);
        this.widthTiles = Math.max(1, initialW);
        this.heightTiles = Math.max(1, initialH);
        setPreferredSize(new Dimension(this.widthTiles * this.tile, this.heightTiles * this.tile));
        setBackground(new Color(30, 30, 30)); // outside the board
        setDoubleBuffered(true);
    }

    @Override
    protected void paintComponent(Graphics gRaw) {
        super.paintComponent(gRaw);
        Graphics2D g = (Graphics2D) gRaw.create();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        char[][] grid = null;
        try { grid = snapshotSupplier == null ? null : snapshotSupplier.get(); } catch (Throwable ignore) {}

        if (grid != null) {
            widthTiles = grid.length;
            heightTiles = grid[0] != null ? grid[0].length : heightTiles;
            ensureSize();
            drawGrid(g, grid);
        } else {
            g.setColor(Color.LIGHT_GRAY);
            g.drawString("Waiting for grid snapshot…", 12, 20);
        }

        g.dispose();
    }

    private void ensureSize() {
        int w = widthTiles * tile;
        int h = heightTiles * tile;
        if (getWidth() != w || getHeight() != h) {
            setPreferredSize(new Dimension(w, h));
            revalidate();
        }
    }

    private void drawGrid(Graphics2D g, char[][] grid) {
        for (int x = 0; x < widthTiles; x++) {
            char[] col = grid[x];
            if (col == null) continue;
            for (int y = 0; y < heightTiles; y++) {
                char c = col[y];
                int px = x * tile;
                int py = y * tile;

                // Floor (paint first so every tile has a base)
                drawFloor(g, px, py);

                switch (c) {
                    case '#': drawWall(g, px, py); break;
                    case 'B': drawBox(g, px, py); break;
                    case 'A': drawAgent(g, px, py); break;
                    case 'E': drawGoal(g, px, py); break;
                    default:  /* empty floor only */ break;
                }
            }
        }
    }

    // ===== Painters =====

    private void drawFloor(Graphics2D g, int px, int py) {
        // Subtle mottled floor
        g.setColor(FLOOR_BASE);
        g.fillRect(px, py, tile, tile);
        // noise tiles
        int pad = Math.max(1, tile / 12);
        g.setColor(FLOOR_NOISE1);
        g.fillRect(px + pad, py + pad, Math.max(1, tile/3), Math.max(1, tile/6));
        g.setColor(FLOOR_NOISE2);
        g.fillRect(px + tile - 2*pad - Math.max(1, tile/4), py + tile - 2*pad - Math.max(1, tile/8),
                   Math.max(1, tile/4), Math.max(1, tile/8));
    }

    private void drawWall(Graphics2D g, int px, int py) {
        // Stone block with bevel
        g.setColor(WALL_STONE);
        g.fillRect(px, py, tile, tile);

        // bevel
        g.setColor(WALL_HILITE);
        g.drawLine(px, py, px + tile - 1, py);
        g.drawLine(px, py, px, py + tile - 1);
        g.setColor(WALL_EDGE);
        g.drawLine(px, py + tile - 1, px + tile - 1, py + tile - 1);
        g.drawLine(px + tile - 1, py, px + tile - 1, py + tile - 1);

        // carve brick pattern (2 rows x 2 columns)
        g.setColor(new Color(0, 0, 0, 35));
        int half = tile / 2;
        int quarter = tile / 4;
        g.drawLine(px, py + half, px + tile - 1, py + half);
        g.drawLine(px + half, py, px + half, py + half);
        g.drawLine(px + quarter, py + half, px + quarter, py + tile - 1);
        g.drawLine(px + 3*quarter, py + half, px + 3*quarter, py + tile - 1);
    }

    private void drawBox(Graphics2D g, int px, int py) {
        // drop shadow
        g.setColor(SHADOW);
        g.fillRoundRect(px + tile/8, py + tile/8, tile - tile/6, tile - tile/6, tile/6, tile/6);

        int pad = Math.max(2, tile / 8);
        int w = tile - 2 * pad;
        int h = tile - 2 * pad;

        // body
        g.setColor(BOX_WOOD);
        g.fillRoundRect(px + pad, py + pad, w, h, tile/8, tile/8);

        // edge
        g.setColor(BOX_EDGE);
        g.drawRoundRect(px + pad, py + pad, w, h, tile/8, tile/8);

        // planks (vertical)
        g.setColor(BOX_WOOD_DK);
        int plankW = Math.max(2, w / 4);
        g.fillRect(px + pad + plankW, py + pad + 2, Math.max(1, plankW/3), h - 4);
        g.fillRect(px + pad + 2*plankW, py + pad + 2, Math.max(1, plankW/3), h - 4);
        g.fillRect(px + pad + 3*plankW, py + pad + 2, Math.max(1, plankW/3), h - 4);

        // straps
        g.setColor(BOX_STRAP);
        int strap = Math.max(2, tile / 12);
        g.fillRect(px + pad, py + pad + h/3, w, strap);
        g.fillRect(px + pad, py + pad + 2*h/3, w, strap);

        // classic "X"
        g.setColor(BOX_EDGE);
        g.drawLine(px + pad + 3, py + pad + 3, px + pad + w - 3, py + pad + h - 3);
        g.drawLine(px + pad + 3, py + pad + h - 3, px + pad + w - 3, py + pad + 3);
    }

    private void drawAgent(Graphics2D g, int px, int py) {
        // worker: helmet + torso
        int pad = Math.max(2, tile / 12);

        // shadow
        g.setColor(SHADOW);
        g.fillOval(px + pad + tile/8, py + pad + tile/3, tile - 2*pad - tile/6, tile/2);

        // torso
        int torsoW = tile - 2*pad;
        int torsoH = (int) (tile * 0.55);
        int torsoX = px + pad;
        int torsoY = py + tile - torsoH - pad;
        g.setColor(AGENT_BODY);
        g.fillRoundRect(torsoX, torsoY, torsoW, torsoH, tile/8, tile/8);
        g.setColor(AGENT_OUTLINE);
        g.drawRoundRect(torsoX, torsoY, torsoW, torsoH, tile/8, tile/8);

        // head
        int headD = (int) (tile * 0.42);
        int headX = px + (tile - headD)/2;
        int headY = torsoY - headD/2;
        g.setColor(new Color(242, 220, 190)); // skin
        Ellipse2D.Double head = new Ellipse2D.Double(headX, headY, headD, headD);
        g.fill(head);
        g.setColor(AGENT_OUTLINE);
        g.draw(head);

        // helmet
        g.setColor(AGENT_HELMET);
        Ellipse2D.Double cap = new Ellipse2D.Double(headX, headY - headD*0.25, headD, headD*0.6);
        g.fill(cap);
        g.setColor(AGENT_OUTLINE);
        g.draw(cap);

        // simple vest detail
        g.setColor(new Color(230, 245, 255, 90));
        g.fillRect(torsoX + torsoW/3, torsoY + pad, Math.max(2, torsoW/6), torsoH - 2*pad);
    }

    private void drawGoal(Graphics2D g, int px, int py) {
        // goal pad with ring/bullseye
        g.setColor(GOAL_TILE);
        g.fillRect(px, py, tile, tile);

        g.setColor(GOAL_RING);
        int ringPad = Math.max(3, tile / 6);
        g.drawOval(px + ringPad, py + ringPad, tile - 2*ringPad, tile - 2*ringPad);

        int dotD = Math.max(4, tile / 5);
        g.fillOval(px + (tile - dotD)/2, py + (tile - dotD)/2, dotD, dotD);
    }
}
