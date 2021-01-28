package sokoban.ui;

import framework.math.Point2i;
import framework.util.Resources;
import io.sarl.lang.core.annotation.SarlElementType;
import io.sarl.lang.core.annotation.SarlSpecification;
import io.sarl.lang.core.annotation.SyntheticMember;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;
import sokoban.environment.agent.Controller;
import sokoban.environment.agent.EnvironmentEvent;
import sokoban.environment.agent.EnvironmentListener;
import sokoban.environment.agent.Player;
import sokoban.environment.maze.BoxObject;
import sokoban.environment.maze.GhostBody;
import sokoban.environment.maze.WallObject;
import sokoban.environment.maze.sokobanBody;
import sokoban.environment.maze.sokobanObject;

/**
 * Swing UI for the sokoban game.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@SarlSpecification("0.15")
@SarlElementType(10)
@XbaseGenerated
@SuppressWarnings("all")
public class sokobanGUI extends JFrame implements KeyListener, EnvironmentListener {
  /**
   * Swing panel that is displaying the environment state.
   * 
   * @author $Author: sgalland$
   * @version $FullVersion$
   * @mavengroupid $GroupId$
   * @mavenartifactid $ArtifactId$
   */
  @SarlSpecification("0.15")
  @SarlElementType(10)
  @XbaseGenerated
  private static class GridPanel extends JPanel {
    private static final Color FLOOR_BASE = new Color(240, 235, 225);

    private static final Color FLOOR_NOISE1 = new Color(250, 245, 235);

    private static final Color FLOOR_NOISE2 = new Color(230, 225, 215);

    private static final Color WALL_BASE = new Color(60, 60, 60);

    private static final Color WALL_EDGE = new Color(40, 40, 40);

    private static final Color WALL_LIGHT = new Color(80, 80, 80);

    private static final Color CRATE_BASE = new Color(176, 134, 84);

    private static final Color CRATE_EDGE = new Color(111, 80, 42);

    private static final Color CRATE_STRAP = new Color(79, 55, 31);

    private static final Color CRATE_ON_TARGET_BASE = new Color(100, 70, 40);

    private static final Color CRATE_ON_TARGET_EDGE = new Color(60, 40, 20);

    private static final Color CRATE_ON_TARGET_STRAP = new Color(40, 25, 15);

    private static final Color GOAL_BASE = new Color(76, 175, 80);

    private static final Color GOAL_BORDER = new Color(27, 94, 32);

    private static final Color GOAL_HIGHLIGHT = new Color(129, 199, 132);

    private static final Color GOAL_INNER = new Color(56, 142, 60);

    private static final Color SHADOW = new Color(0, 0, 0, 45);

    private static final Color DISHDASHA_WHITE = new Color(255, 255, 255);

    private static final Color DISHDASHA_SHADOW = new Color(240, 240, 240);

    private static final Color DISHDASHA_FOLD = new Color(230, 230, 230);

    private static final Color AGENT_SKIN = new Color(210, 180, 140);

    private static final Color AGENT_OUTLINE = new Color(50, 50, 50);

    private static final Color KUMMA_COLOR = new Color(200, 200, 200);

    private static final Color KUMMA_BAND = new Color(100, 100, 100);

    private Map<Point2i, sokobanObject> objects;

    private AtomicInteger time = new AtomicInteger();

    private HashMap<Point2i, List<Point2i>> plannedPaths = CollectionLiterals.<Point2i, List<Point2i>>newHashMap();

    public GridPanel() {
      this.setBackground(sokobanGUI.GridPanel.FLOOR_BASE);
    }

    private Set<Point2i> exitPositions;

    /**
     * Update planned paths for visualization.
     */
    public List<Point2i> updatePlannedPath(final Point2i boxPosition, final List<Point2i> path) {
      List<Point2i> _xsynchronizedexpression = null;
      synchronized (this.getTreeLock()) {
        List<Point2i> _xifexpression = null;
        if (((path != null) && (!path.isEmpty()))) {
          _xifexpression = this.plannedPaths.put(boxPosition, path);
        } else {
          _xifexpression = this.plannedPaths.remove(boxPosition);
        }
        _xsynchronizedexpression = _xifexpression;
      }
      return _xsynchronizedexpression;
    }

    /**
     * Clear all planned paths.
     */
    public void clearPlannedPaths() {
      synchronized (this.getTreeLock()) {
        this.plannedPaths.clear();
      }
    }

    public void setObjects(final int time, final Map<Point2i, sokobanObject> objects, final Set<Point2i> exitPositions) {
      synchronized (this.getTreeLock()) {
        this.time.set(time);
        this.objects = objects;
        this.exitPositions = exitPositions;
        this.repaint();
      }
    }

    public void paint(final Graphics g) {
      super.paint(g);
      int px = 0;
      int py = 0;
      Graphics2D g2d = ((Graphics2D) g);
      g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      int _get = this.time.get();
      boolean isEvenTime = ((_get % 2) == 0);
      if ((this.objects != null)) {
        if ((this.exitPositions != null)) {
          for (final Point2i exitPos : this.exitPositions) {
            {
              int _x = exitPos.getX();
              px = (sokobanGUI.CELL_WIDTH * _x);
              int _y = exitPos.getY();
              py = (sokobanGUI.CELL_HEIGHT * _y);
              this.drawGoal(g2d, px, py, isEvenTime);
            }
          }
        }
        Set<Map.Entry<Point2i, sokobanObject>> _entrySet = this.objects.entrySet();
        for (final Map.Entry<Point2i, sokobanObject> entry : _entrySet) {
          {
            Point2i pos = entry.getKey();
            int _x = pos.getX();
            px = (sokobanGUI.CELL_WIDTH * _x);
            int _y = pos.getY();
            py = (sokobanGUI.CELL_HEIGHT * _y);
            if (((this.exitPositions == null) || (!this.exitPositions.contains(pos)))) {
              this.drawFloor(g2d, px, py);
            }
          }
        }
        synchronized (this.getTreeLock()) {
          if (((this.plannedPaths != null) && (!this.plannedPaths.isEmpty()))) {
            Set<Map.Entry<Point2i, List<Point2i>>> _entrySet_1 = this.plannedPaths.entrySet();
            for (final Map.Entry<Point2i, List<Point2i>> entry_1 : _entrySet_1) {
              {
                List<Point2i> path = entry_1.getValue();
                if (((path != null) && (!path.isEmpty()))) {
                  this.drawPathTrail(g2d, path);
                }
              }
            }
          }
        }
        Set<Map.Entry<Point2i, sokobanObject>> _entrySet_1 = this.objects.entrySet();
        for (final Map.Entry<Point2i, sokobanObject> entry_1 : _entrySet_1) {
          {
            Point2i pos = entry_1.getKey();
            sokobanObject obj = entry_1.getValue();
            int _x = pos.getX();
            px = (sokobanGUI.CELL_WIDTH * _x);
            int _y = pos.getY();
            py = (sokobanGUI.CELL_HEIGHT * _y);
            if ((obj instanceof WallObject)) {
              this.drawWall(g2d, px, py);
            } else {
              if ((obj instanceof BoxObject)) {
                boolean isOnTarget = ((BoxObject)obj).isOnTarget();
                this.drawCrate(g2d, px, py, isEvenTime, isOnTarget);
              } else {
                if ((obj instanceof sokobanBody)) {
                  this.drawWorker(g2d, px, py);
                } else {
                  if ((obj instanceof GhostBody)) {
                    this.drawCrate(g2d, px, py, isEvenTime, false);
                  }
                }
              }
            }
          }
        }
      }
    }

    public void drawFloor(final Graphics2D g2d, final int px, final int py) {
      g2d.setColor(sokobanGUI.GridPanel.FLOOR_BASE);
      g2d.fillRect(px, py, sokobanGUI.CELL_WIDTH, sokobanGUI.CELL_HEIGHT);
      int pad = Math.max(1, (sokobanGUI.CELL_WIDTH / 12));
      g2d.setColor(sokobanGUI.GridPanel.FLOOR_NOISE1);
      g2d.fillRect((px + pad), (py + pad), Math.max(1, (sokobanGUI.CELL_WIDTH / 3)), Math.max(1, (sokobanGUI.CELL_HEIGHT / 6)));
      g2d.setColor(sokobanGUI.GridPanel.FLOOR_NOISE2);
      int _max = Math.max(2, (sokobanGUI.CELL_WIDTH / 4));
      int _max_1 = Math.max(2, (sokobanGUI.CELL_HEIGHT / 7));
      g2d.fillRect((((px + sokobanGUI.CELL_WIDTH) - pad) - _max), 
        (((py + sokobanGUI.CELL_HEIGHT) - pad) - _max_1), 
        Math.max(2, (sokobanGUI.CELL_WIDTH / 4)), Math.max(1, (sokobanGUI.CELL_HEIGHT / 7)));
    }

    public void drawWall(final Graphics2D g2d, final int px, final int py) {
      g2d.setColor(sokobanGUI.GridPanel.WALL_BASE);
      g2d.fillRect(px, py, sokobanGUI.CELL_WIDTH, sokobanGUI.CELL_HEIGHT);
      g2d.setColor(sokobanGUI.GridPanel.WALL_LIGHT);
      g2d.drawLine(px, py, ((px + sokobanGUI.CELL_WIDTH) - 1), py);
      g2d.drawLine(px, py, px, ((py + sokobanGUI.CELL_HEIGHT) - 1));
      g2d.setColor(sokobanGUI.GridPanel.WALL_EDGE);
      g2d.drawLine(px, ((py + sokobanGUI.CELL_HEIGHT) - 1), ((px + sokobanGUI.CELL_WIDTH) - 1), ((py + sokobanGUI.CELL_HEIGHT) - 1));
      g2d.drawLine(((px + sokobanGUI.CELL_WIDTH) - 1), py, ((px + sokobanGUI.CELL_WIDTH) - 1), ((py + sokobanGUI.CELL_HEIGHT) - 1));
    }

    public void drawCrate(final Graphics2D g2d, final int px, final int py, final boolean glow, final boolean isOnTarget) {
      int shadowOffset = Math.max(1, (sokobanGUI.CELL_WIDTH / 15));
      Color _color = new Color(0, 0, 0, 60);
      g2d.setColor(_color);
      g2d.fillRoundRect((px + shadowOffset), (py + shadowOffset), 
        (sokobanGUI.CELL_WIDTH - shadowOffset), (sokobanGUI.CELL_HEIGHT - shadowOffset), 6, 6);
      int pad = Math.max(2, (sokobanGUI.CELL_WIDTH / 8));
      int w = (sokobanGUI.CELL_WIDTH - (2 * pad));
      int h = (sokobanGUI.CELL_HEIGHT - (2 * pad));
      if (isOnTarget) {
        g2d.setColor(sokobanGUI.GridPanel.CRATE_ON_TARGET_BASE);
        g2d.fillRoundRect((px + pad), (py + pad), w, h, 8, 8);
        g2d.setColor(sokobanGUI.GridPanel.CRATE_ON_TARGET_EDGE);
        g2d.drawRoundRect((px + pad), (py + pad), w, h, 8, 8);
        g2d.drawRoundRect(((px + pad) - 1), ((py + pad) - 1), (w + 2), (h + 2), 8, 8);
        g2d.setColor(sokobanGUI.GridPanel.CRATE_ON_TARGET_STRAP);
      } else {
        g2d.setColor(sokobanGUI.GridPanel.CRATE_BASE);
        g2d.fillRoundRect((px + pad), (py + pad), w, h, 8, 8);
        g2d.setColor(sokobanGUI.GridPanel.CRATE_EDGE);
        g2d.drawRoundRect((px + pad), (py + pad), w, h, 8, 8);
        g2d.setColor(sokobanGUI.GridPanel.CRATE_STRAP);
      }
      int strap = Math.max(2, (sokobanGUI.CELL_WIDTH / 12));
      g2d.fillRect((px + pad), ((py + pad) + (h / 3)), w, strap);
      g2d.fillRect((px + pad), ((py + pad) + ((2 * h) / 3)), w, strap);
      if (glow) {
        Color _color_1 = new Color(255, 255, 255, 40);
        g2d.setColor(_color_1);
        g2d.drawLine(((px + pad) + 2), ((py + pad) + 2), (((px + pad) + w) - 2), ((py + pad) + 2));
        g2d.drawLine(((px + pad) + 2), ((py + pad) + 2), ((px + pad) + 2), ((py + pad) + (h / 3)));
        g2d.drawLine((((px + pad) + w) - 2), ((py + pad) + 2), (((px + pad) + w) - 2), ((py + pad) + (h / 3)));
      }
    }

    public void drawWorker(final Graphics2D g2d, final int px, final int py) {
      int pad = Math.max(2, (sokobanGUI.CELL_WIDTH / 12));
      g2d.setColor(sokobanGUI.GridPanel.SHADOW);
      g2d.fillOval(((px + pad) + (sokobanGUI.CELL_WIDTH / 10)), (((py + sokobanGUI.CELL_HEIGHT) - pad) - (sokobanGUI.CELL_HEIGHT / 6)), 
        ((sokobanGUI.CELL_WIDTH - (2 * pad)) - (sokobanGUI.CELL_WIDTH / 6)), (sokobanGUI.CELL_HEIGHT / 5));
      int robeW = (sokobanGUI.CELL_WIDTH - (2 * pad));
      int robeH = ((int) (sokobanGUI.CELL_HEIGHT * 0.65));
      int robeX = (px + pad);
      int robeY = (((py + sokobanGUI.CELL_HEIGHT) - robeH) - pad);
      g2d.setColor(sokobanGUI.GridPanel.DISHDASHA_WHITE);
      g2d.fillRect(robeX, robeY, robeW, robeH);
      g2d.setColor(sokobanGUI.GridPanel.AGENT_OUTLINE);
      g2d.drawRect(robeX, robeY, robeW, robeH);
      int foldX1 = (robeX + (robeW / 3));
      int foldX2 = (robeX + ((2 * robeW) / 3));
      g2d.setColor(sokobanGUI.GridPanel.DISHDASHA_FOLD);
      g2d.drawLine(foldX1, robeY, foldX1, (robeY + robeH));
      g2d.drawLine(foldX2, robeY, foldX2, (robeY + robeH));
      g2d.setColor(sokobanGUI.GridPanel.DISHDASHA_SHADOW);
      g2d.fillRect((robeX + 1), ((robeY + robeH) - 3), (robeW - 2), 2);
      int headD = ((int) (sokobanGUI.CELL_WIDTH * 0.4));
      int headX = (px + ((sokobanGUI.CELL_WIDTH - headD) / 2));
      int headY = (robeY - (headD / 2));
      g2d.setColor(sokobanGUI.GridPanel.AGENT_SKIN);
      g2d.fillOval(headX, headY, headD, headD);
      g2d.setColor(sokobanGUI.GridPanel.AGENT_OUTLINE);
      g2d.drawOval(headX, headY, headD, headD);
      int kummaD = ((int) (headD * 0.7));
      int kummaX = (headX + ((headD - kummaD) / 2));
      int kummaY = (headY - (kummaD / 3));
      g2d.setColor(sokobanGUI.GridPanel.KUMMA_COLOR);
      g2d.fillOval(kummaX, kummaY, kummaD, kummaD);
      g2d.setColor(sokobanGUI.GridPanel.AGENT_OUTLINE);
      g2d.drawOval(kummaX, kummaY, kummaD, kummaD);
      int bandY = (kummaY + (kummaD / 3));
      g2d.setColor(sokobanGUI.GridPanel.KUMMA_BAND);
      g2d.fillRect((kummaX + 2), bandY, (kummaD - 4), 2);
    }

    public void drawGoal(final Graphics2D g2d, final int px, final int py, final boolean pulse) {
      g2d.setColor(sokobanGUI.GridPanel.GOAL_BASE);
      g2d.fillRect(px, py, sokobanGUI.CELL_WIDTH, sokobanGUI.CELL_HEIGHT);
      int borderWidth = Math.max(2, (sokobanGUI.CELL_WIDTH / 8));
      g2d.setColor(sokobanGUI.GridPanel.GOAL_BORDER);
      g2d.fillRect(px, py, sokobanGUI.CELL_WIDTH, borderWidth);
      g2d.fillRect(px, ((py + sokobanGUI.CELL_HEIGHT) - borderWidth), sokobanGUI.CELL_WIDTH, borderWidth);
      g2d.fillRect(px, py, borderWidth, sokobanGUI.CELL_HEIGHT);
      g2d.fillRect(((px + sokobanGUI.CELL_WIDTH) - borderWidth), py, borderWidth, sokobanGUI.CELL_HEIGHT);
      int innerPad = Math.max(3, (sokobanGUI.CELL_WIDTH / 5));
      g2d.setColor(sokobanGUI.GridPanel.GOAL_INNER);
      g2d.fillRect((px + innerPad), (py + innerPad), 
        (sokobanGUI.CELL_WIDTH - (2 * innerPad)), (sokobanGUI.CELL_HEIGHT - (2 * innerPad)));
      if (pulse) {
        int highlightPad = Math.max(2, (sokobanGUI.CELL_WIDTH / 6));
        g2d.setColor(sokobanGUI.GridPanel.GOAL_HIGHLIGHT);
        g2d.fillRect((px + highlightPad), (py + highlightPad), 
          (sokobanGUI.CELL_WIDTH - (2 * highlightPad)), (sokobanGUI.CELL_HEIGHT - (2 * highlightPad)));
      }
      int dotSize = Math.max(3, (sokobanGUI.CELL_WIDTH / 6));
      g2d.setColor(sokobanGUI.GridPanel.GOAL_BORDER);
      g2d.fillOval((px + ((sokobanGUI.CELL_WIDTH - dotSize) / 2)), (py + ((sokobanGUI.CELL_HEIGHT - dotSize) / 2)), dotSize, dotSize);
    }

    /**
     * Draw path trail for debugging - shows the planned path from box to exit.
     */
    public void drawPathTrail(final Graphics2D g2d, final List<Point2i> path) {
      if (((path == null) || path.isEmpty())) {
        return;
      }
      Color pathColor = new Color(100, 150, 255, 120);
      Color pathLineColor = new Color(50, 100, 200, 180);
      Color startColor = new Color(0, 255, 0, 150);
      Color endColor = new Color(255, 0, 0, 150);
      BasicStroke _basicStroke = new BasicStroke(2.0f);
      g2d.setStroke(_basicStroke);
      int _size = path.size();
      ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, (_size - 1), true);
      for (final Integer i : _doubleDotLessThan) {
        {
          Point2i from = path.get(((i) == null ? 0 : (i).intValue()));
          Point2i to = path.get((((i) == null ? 0 : (i).intValue()) + 1));
          int _x = from.getX();
          int fromX = ((sokobanGUI.CELL_WIDTH * _x) + sokobanGUI.DEMI_CELL_WIDTH);
          int _y = from.getY();
          int fromY = ((sokobanGUI.CELL_HEIGHT * _y) + sokobanGUI.DEMI_CELL_HEIGHT);
          int _x_1 = to.getX();
          int toX = ((sokobanGUI.CELL_WIDTH * _x_1) + sokobanGUI.DEMI_CELL_WIDTH);
          int _y_1 = to.getY();
          int toY = ((sokobanGUI.CELL_HEIGHT * _y_1) + sokobanGUI.DEMI_CELL_HEIGHT);
          g2d.setColor(pathLineColor);
          g2d.drawLine(fromX, fromY, toX, toY);
        }
      }
      int _size_1 = path.size();
      ExclusiveRange _doubleDotLessThan_1 = new ExclusiveRange(0, _size_1, true);
      for (final Integer i_1 : _doubleDotLessThan_1) {
        {
          Point2i point = path.get(((i_1) == null ? 0 : (i_1).intValue()));
          int _x = point.getX();
          int centerX = ((sokobanGUI.CELL_WIDTH * _x) + sokobanGUI.DEMI_CELL_WIDTH);
          int _y = point.getY();
          int centerY = ((sokobanGUI.CELL_HEIGHT * _y) + sokobanGUI.DEMI_CELL_HEIGHT);
          int nodeSize = Math.max(4, (sokobanGUI.CELL_WIDTH / 5));
          if ((i_1 != null && (i_1.intValue() == 0))) {
            g2d.setColor(startColor);
            g2d.fillOval((centerX - (nodeSize / 2)), (centerY - (nodeSize / 2)), nodeSize, nodeSize);
            Color _color = new Color(0, 200, 0, 200);
            g2d.setColor(_color);
            g2d.drawOval((centerX - (nodeSize / 2)), (centerY - (nodeSize / 2)), nodeSize, nodeSize);
          } else {
            int _size_2 = path.size();
            if ((i_1 != null && (i_1.intValue() == (_size_2 - 1)))) {
              g2d.setColor(endColor);
              g2d.fillOval((centerX - (nodeSize / 2)), (centerY - (nodeSize / 2)), nodeSize, nodeSize);
              Color _color_1 = new Color(200, 0, 0, 200);
              g2d.setColor(_color_1);
              g2d.drawOval((centerX - (nodeSize / 2)), (centerY - (nodeSize / 2)), nodeSize, nodeSize);
            } else {
              g2d.setColor(pathColor);
              g2d.fillOval((centerX - (nodeSize / 2)), (centerY - (nodeSize / 2)), nodeSize, nodeSize);
              g2d.setColor(pathLineColor);
              g2d.drawOval((centerX - (nodeSize / 2)), (centerY - (nodeSize / 2)), nodeSize, nodeSize);
            }
          }
        }
      }
    }

    @Override
    @Pure
    @SyntheticMember
    public boolean equals(final Object obj) {
      return super.equals(obj);
    }

    @Override
    @Pure
    @SyntheticMember
    public int hashCode() {
      int result = super.hashCode();
      return result;
    }

    @SyntheticMember
    private static final long serialVersionUID = -669943173L;
  }

  /**
   * Width in pixels of a cell.
   */
  public static final int CELL_WIDTH = 20;

  /**
   * Height in pixels of a cell.
   */
  public static final int CELL_HEIGHT = 20;

  /**
   * Demi-width in pixels of a cell.
   */
  public static final int DEMI_CELL_WIDTH = (sokobanGUI.CELL_WIDTH / 2);

  /**
   * Demi-height in pixels of a cell.
   */
  public static final int DEMI_CELL_HEIGHT = (sokobanGUI.CELL_HEIGHT / 2);

  private final AtomicBoolean isInit = new AtomicBoolean();

  private final sokobanGUI.GridPanel gridPanel;

  /**
   * Update planned path for visualization (called by Environment).
   */
  public List<Point2i> updatePlannedPath(final Point2i boxPosition, final List<Point2i> path) {
    return this.gridPanel.updatePlannedPath(boxPosition, path);
  }

  private final JButton startButton;

  private final JButton fastForwardButton;

  private final DashboardPanel dashboardPanel;

  private long waitingDuration;

  private long originalWaitingDuration;

  private boolean isFastForward;

  private Controller controller;

  /**
   * @param waitingDuration - the duration of sleeping before giving the hand to the simulator back.
   */
  public sokobanGUI(final long waitingDuration) {
    try {
      this.waitingDuration = waitingDuration;
      this.originalWaitingDuration = waitingDuration;
      this.isFastForward = false;
      this.setTitle("Sokoban Simulator");
      URL sokobanIcon = Resources.getResource(sokobanGUI.class, "sokoban.png");
      this.setIconImage(ImageIO.read(sokobanIcon));
      URL playIconURL = Resources.getResource(this.getClass(), "play.png");
      DashboardPanel _dashboardPanel = new DashboardPanel();
      this.dashboardPanel = _dashboardPanel;
      ImageIcon _imageIcon = new ImageIcon(playIconURL);
      JButton _jButton = new JButton(_imageIcon);
      this.startButton = _jButton;
      this.startButton.setToolTipText("Start simulation");
      this.startButton.setEnabled(false);
      final ActionListener _function = (ActionEvent it) -> {
        Controller ctrl = this.controller;
        if (((ctrl != null) && (!ctrl.isStarted()))) {
          this.startButton.setEnabled(false);
          this.dashboardPanel.setAgentCountEnabled(false);
          ctrl.startSimulation();
        }
      };
      this.startButton.addActionListener(_function);
      JButton _jButton_1 = new JButton("⏩ Fast Forward");
      this.fastForwardButton = _jButton_1;
      this.fastForwardButton.setToolTipText("Toggle fast forward (speed up simulation)");
      this.fastForwardButton.setEnabled(false);
      final ActionListener _function_1 = (ActionEvent it) -> {
        this.toggleFastForward();
      };
      this.fastForwardButton.addActionListener(_function_1);
      JPanel topPanel = new JPanel();
      BoxLayout _boxLayout = new BoxLayout(topPanel, BoxLayout.X_AXIS);
      topPanel.setLayout(_boxLayout);
      topPanel.add(this.startButton);
      topPanel.add(this.fastForwardButton);
      sokobanGUI.GridPanel _gridPanel = new sokobanGUI.GridPanel();
      this.gridPanel = _gridPanel;
      this.gridPanel.setFocusable(true);
      this.gridPanel.addKeyListener(this);
      JScrollPane sc = new JScrollPane(this.gridPanel);
      Container _contentPane = this.getContentPane();
      BorderLayout _borderLayout = new BorderLayout();
      _contentPane.setLayout(_borderLayout);
      this.getContentPane().add(BorderLayout.NORTH, topPanel);
      this.getContentPane().add(BorderLayout.CENTER, sc);
      this.getContentPane().add(BorderLayout.SOUTH, this.dashboardPanel);
      Dimension _dimension = new Dimension(600, 600);
      this.setPreferredSize(_dimension);
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.pack();
      this.requestFocus();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  public void bindPlayer(final Player player) {
  }

  public void unbindPlayer(final Player player) {
  }

  public void bindController(final Controller controller) {
    synchronized (this) {
      this.controller = controller;
      boolean _isStarted = this.controller.isStarted();
      this.startButton.setEnabled((!_isStarted));
      this.fastForwardButton.setEnabled(true);
    }
  }

  /**
   * Toggle fast forward mode.
   */
  public void toggleFastForward() {
    synchronized (this) {
      this.isFastForward = (!this.isFastForward);
      if (this.isFastForward) {
        this.waitingDuration = 0;
        this.fastForwardButton.setText("⏸ Normal Speed");
        this.fastForwardButton.setToolTipText("Return to normal speed");
        System.out.println("[GUI] Fast forward enabled");
      } else {
        this.waitingDuration = this.originalWaitingDuration;
        this.fastForwardButton.setText("⏩ Fast Forward");
        this.fastForwardButton.setToolTipText("Toggle fast forward (speed up simulation)");
        System.out.println("[GUI] Normal speed restored");
      }
    }
  }

  public void unbindController(final Controller controller) {
    synchronized (this) {
      this.startButton.setEnabled(false);
      this.controller = null;
    }
  }

  public void gameOver() {
    this.setVisible(false);
    JOptionPane.showMessageDialog(this, 
      "Simulation Complete!\n\nAll agents have reached the exits.\n\nWin Condition Met!", 
      this.getTitle(), 
      JOptionPane.INFORMATION_MESSAGE);
    this.dispose();
  }

  @Override
  @Pure
  public int getAgentCount() {
    if ((this.dashboardPanel != null)) {
      int count = this.dashboardPanel.getAgentCount();
      System.out.println(("[GUI] Dashboard reports agent count: " + Integer.valueOf(count)));
      return count;
    }
    System.out.println("[GUI] Dashboard panel is null, returning default: 2");
    return 2;
  }

  @SuppressWarnings("discouraged_reference")
  public void environmentChanged(final EnvironmentEvent event) {
    boolean _get = this.isInit.get();
    if ((!_get)) {
      this.isInit.set(true);
      int _width = event.getWidth();
      int _height = event.getHeight();
      Dimension _dimension = new Dimension((sokobanGUI.CELL_WIDTH * _width), (sokobanGUI.CELL_HEIGHT * _height));
      this.gridPanel.setPreferredSize(_dimension);
      this.revalidate();
      this.pack();
      this.setVisible(true);
      this.gridPanel.requestFocus();
    }
    this.gridPanel.setObjects(event.getTime(), event.getObjects(), event.getExitPositions());
    this.updateDashboardStats(event);
    if ((this.waitingDuration > 0)) {
      try {
        Thread.sleep(this.waitingDuration);
      } catch (final Throwable _t) {
        if (_t instanceof InterruptedException) {
          final InterruptedException e = (InterruptedException)_t;
          throw new RuntimeException(e);
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
    }
  }

  /**
   * Update dashboard statistics from environment event.
   * 
   * @param ^event the environment event
   */
  public void updateDashboardStats(final EnvironmentEvent event) {
    int totalBoxes = 0;
    int boxesOnTargets = 0;
    int totalAgents = 0;
    int agentsAtExit = 0;
    Set<Point2i> exitPositions = event.getExitPositions();
    Set<Map.Entry<Point2i, sokobanObject>> _entrySet = event.getObjects().entrySet();
    for (final Map.Entry<Point2i, sokobanObject> entry : _entrySet) {
      {
        sokobanObject obj = entry.getValue();
        if ((obj instanceof BoxObject)) {
          totalBoxes++;
          boolean _isOnTarget = ((BoxObject)obj).isOnTarget();
          if (_isOnTarget) {
            boxesOnTargets++;
          }
        } else {
          if ((obj instanceof sokobanBody)) {
            totalAgents++;
            boolean _contains = exitPositions.contains(entry.getKey());
            if (_contains) {
              agentsAtExit++;
            }
          }
        }
      }
    }
    this.dashboardPanel.updateBoxStats(boxesOnTargets, totalBoxes);
    this.dashboardPanel.updateAgentStats(agentsAtExit, totalAgents);
    this.dashboardPanel.updateTimeStats(0, event.getTime());
  }

  public void keyPressed(final KeyEvent e) {
  }

  public void keyReleased(final KeyEvent e) {
  }

  public void keyTyped(final KeyEvent e) {
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
    sokobanGUI other = (sokobanGUI) obj;
    if (other.waitingDuration != this.waitingDuration)
      return false;
    if (other.originalWaitingDuration != this.originalWaitingDuration)
      return false;
    if (other.isFastForward != this.isFastForward)
      return false;
    return super.equals(obj);
  }

  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Long.hashCode(this.waitingDuration);
    result = prime * result + Long.hashCode(this.originalWaitingDuration);
    result = prime * result + Boolean.hashCode(this.isFastForward);
    return result;
  }

  @SyntheticMember
  private static final long serialVersionUID = 3793998778L;
}
