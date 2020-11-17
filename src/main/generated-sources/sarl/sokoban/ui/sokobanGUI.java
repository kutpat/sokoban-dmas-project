package sokoban.ui;

import framework.math.Point2i;
import framework.util.Resources;
import io.sarl.lang.core.annotation.SarlElementType;
import io.sarl.lang.core.annotation.SarlSpecification;
import io.sarl.lang.core.annotation.SyntheticMember;
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
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;
import sokoban.environment.agent.Controller;
import sokoban.environment.agent.EnvironmentEvent;
import sokoban.environment.agent.EnvironmentListener;
import sokoban.environment.agent.Player;
import sokoban.environment.maze.Direction;
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
    private static final Color FLOOR_BASE = new Color(224, 215, 200);

    private static final Color FLOOR_NOISE1 = new Color(236, 229, 215);

    private static final Color FLOOR_NOISE2 = new Color(210, 200, 184);

    private static final Color WALL_BASE = new Color(125, 117, 103);

    private static final Color WALL_EDGE = new Color(87, 80, 69);

    private static final Color WALL_LIGHT = new Color(160, 151, 136);

    private static final Color CRATE_BASE = new Color(176, 134, 84);

    private static final Color CRATE_EDGE = new Color(111, 80, 42);

    private static final Color CRATE_STRAP = new Color(79, 55, 31);

    private static final Color GOAL_BASE = new Color(171, 206, 187);

    private static final Color GOAL_RING = new Color(39, 110, 88);

    private static final Color SHADOW = new Color(0, 0, 0, 45);

    private static final Color WORKER_BODY = new Color(63, 116, 184);

    private static final Color WORKER_OUTLINE = new Color(26, 48, 76);

    private static final Color WORKER_HELMET = new Color(243, 192, 56);

    private static final Color WORKER_SKIN = new Color(241, 218, 189);

    private static final Color WORKER_STRAP = new Color(226, 238, 250, 140);

    private Map<Point2i, sokobanObject> objects;

    private AtomicInteger time = new AtomicInteger();

    public GridPanel() {
      this.setBackground(Color.BLACK);
    }

    public void setObjects(final int time, final Map<Point2i, sokobanObject> objects) {
      synchronized (this.getTreeLock()) {
        this.time.set(time);
        this.objects = objects;
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
        Set<Map.Entry<Point2i, sokobanObject>> _entrySet = this.objects.entrySet();
        for (final Map.Entry<Point2i, sokobanObject> entry : _entrySet) {
          {
            Point2i pos = entry.getKey();
            sokobanObject obj = entry.getValue();
            int _x = pos.getX();
            px = (sokobanGUI.CELL_WIDTH * _x);
            int _y = pos.getY();
            py = (sokobanGUI.CELL_HEIGHT * _y);
            this.drawFloor(g2d, px, py);
            if ((obj instanceof WallObject)) {
              this.drawWall(g2d, px, py);
            } else {
              if ((obj instanceof sokobanBody)) {
                this.drawWorker(g2d, px, py);
              } else {
                if ((obj instanceof GhostBody)) {
                  this.drawCrate(g2d, px, py, isEvenTime);
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
      Color _color = new Color(0, 0, 0, 35);
      g2d.setColor(_color);
      int half = (sokobanGUI.CELL_HEIGHT / 2);
      int quarter = (sokobanGUI.CELL_WIDTH / 4);
      g2d.drawLine(px, (py + half), ((px + sokobanGUI.CELL_WIDTH) - 1), (py + half));
      g2d.drawLine((px + quarter), py, (px + quarter), (py + half));
      g2d.drawLine((px + (2 * quarter)), (py + half), (px + (2 * quarter)), ((py + sokobanGUI.CELL_HEIGHT) - 1));
      g2d.drawLine((px + (3 * quarter)), py, (px + (3 * quarter)), (py + half));
    }

    public void drawCrate(final Graphics2D g2d, final int px, final int py, final boolean glow) {
      g2d.setColor(sokobanGUI.GridPanel.SHADOW);
      g2d.fillRoundRect((px + (sokobanGUI.CELL_WIDTH / 10)), (py + (sokobanGUI.CELL_HEIGHT / 10)), 
        (sokobanGUI.CELL_WIDTH - (sokobanGUI.CELL_WIDTH / 7)), (sokobanGUI.CELL_HEIGHT - (sokobanGUI.CELL_HEIGHT / 7)), 6, 6);
      int pad = Math.max(2, (sokobanGUI.CELL_WIDTH / 8));
      int w = (sokobanGUI.CELL_WIDTH - (2 * pad));
      int h = (sokobanGUI.CELL_HEIGHT - (2 * pad));
      g2d.setColor(sokobanGUI.GridPanel.CRATE_BASE);
      g2d.fillRoundRect((px + pad), (py + pad), w, h, 8, 8);
      g2d.setColor(sokobanGUI.GridPanel.CRATE_EDGE);
      g2d.drawRoundRect((px + pad), (py + pad), w, h, 8, 8);
      g2d.setColor(sokobanGUI.GridPanel.CRATE_STRAP);
      int strap = Math.max(2, (sokobanGUI.CELL_WIDTH / 12));
      g2d.fillRect((px + pad), ((py + pad) + (h / 3)), w, strap);
      g2d.fillRect((px + pad), ((py + pad) + ((2 * h) / 3)), w, strap);
      if (glow) {
        Color _color = new Color(255, 255, 255, 35);
        g2d.setColor(_color);
        g2d.drawLine(((px + pad) + 3), ((py + pad) + 3), (((px + pad) + w) - 3), ((py + pad) + 3));
      }
    }

    public void drawWorker(final Graphics2D g2d, final int px, final int py) {
      int pad = Math.max(2, (sokobanGUI.CELL_WIDTH / 10));
      g2d.setColor(sokobanGUI.GridPanel.SHADOW);
      g2d.fillOval(((px + pad) + (sokobanGUI.CELL_WIDTH / 10)), (((py + sokobanGUI.CELL_HEIGHT) - pad) - (sokobanGUI.CELL_HEIGHT / 5)), 
        ((sokobanGUI.CELL_WIDTH - (2 * pad)) - (sokobanGUI.CELL_WIDTH / 6)), (sokobanGUI.CELL_HEIGHT / 4));
      int torsoW = (sokobanGUI.CELL_WIDTH - (2 * pad));
      int torsoH = ((int) (sokobanGUI.CELL_HEIGHT * 0.55));
      int torsoX = (px + pad);
      int torsoY = (((py + sokobanGUI.CELL_HEIGHT) - torsoH) - pad);
      g2d.setColor(sokobanGUI.GridPanel.WORKER_BODY);
      g2d.fillRoundRect(torsoX, torsoY, torsoW, torsoH, (pad * 2), (pad * 2));
      g2d.setColor(sokobanGUI.GridPanel.WORKER_OUTLINE);
      g2d.drawRoundRect(torsoX, torsoY, torsoW, torsoH, (pad * 2), (pad * 2));
      int armW = Math.max(3, (sokobanGUI.CELL_WIDTH / 6));
      int armH = Math.max(4, (sokobanGUI.CELL_HEIGHT / 3));
      g2d.setColor(sokobanGUI.GridPanel.WORKER_BODY);
      g2d.fillRoundRect((px + (pad / 2)), (torsoY + pad), armW, armH, pad, pad);
      g2d.fillRoundRect((((px + sokobanGUI.CELL_WIDTH) - (pad / 2)) - armW), (torsoY + pad), armW, armH, pad, pad);
      g2d.setColor(sokobanGUI.GridPanel.WORKER_STRAP);
      g2d.fillRect((torsoX + (torsoW / 3)), (torsoY + pad), Math.max(2, (torsoW / 6)), (torsoH - (2 * pad)));
      int headD = ((int) (sokobanGUI.CELL_WIDTH * 0.45));
      int headX = (px + ((sokobanGUI.CELL_WIDTH - headD) / 2));
      int headY = (torsoY - (headD / 2));
      g2d.setColor(sokobanGUI.GridPanel.WORKER_SKIN);
      g2d.fillOval(headX, headY, headD, headD);
      g2d.setColor(sokobanGUI.GridPanel.WORKER_OUTLINE);
      g2d.drawOval(headX, headY, headD, headD);
      int helmetH = Math.max(4, ((int) (headD * 0.55)));
      int helmetY = (headY - (helmetH / 3));
      g2d.setColor(sokobanGUI.GridPanel.WORKER_HELMET);
      g2d.fillOval(headX, helmetY, headD, helmetH);
      g2d.setColor(sokobanGUI.GridPanel.WORKER_OUTLINE);
      g2d.drawOval(headX, helmetY, headD, helmetH);
    }

    public void drawGoal(final Graphics2D g2d, final int px, final int py, final boolean pulse) {
      g2d.setColor(sokobanGUI.GridPanel.GOAL_BASE);
      g2d.fillRect(px, py, sokobanGUI.CELL_WIDTH, sokobanGUI.CELL_HEIGHT);
      int ringPad = Math.max(3, (sokobanGUI.CELL_WIDTH / 6));
      g2d.setColor(sokobanGUI.GridPanel.GOAL_RING);
      g2d.drawOval((px + ringPad), (py + ringPad), (sokobanGUI.CELL_WIDTH - (2 * ringPad)), (sokobanGUI.CELL_HEIGHT - (2 * ringPad)));
      int dotD = Math.max(4, (sokobanGUI.CELL_WIDTH / 4));
      if (pulse) {
        dotD = (dotD + 2);
      }
      g2d.fillOval((px + ((sokobanGUI.CELL_WIDTH - dotD) / 2)), (py + ((sokobanGUI.CELL_HEIGHT - dotD) / 2)), dotD, dotD);
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
    private static final long serialVersionUID = -11716464286L;
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

  private final JButton startButton;

  private final long waitingDuration;

  private Player player;

  private Controller controller;

  /**
   * @param waitingDuration - the duration of sleeping before giving the hand to the simulator back.
   */
  public sokobanGUI(final long waitingDuration) {
    try {
      this.waitingDuration = waitingDuration;
      this.setTitle("Sokoban Simulator");
      URL sokobanIcon = Resources.getResource(sokobanGUI.class, "sokoban.png");
      this.setIconImage(ImageIO.read(sokobanIcon));
      URL iconURL = Resources.getResource(this.getClass(), "play.png");
      ImageIcon _imageIcon = new ImageIcon(iconURL);
      JButton _jButton = new JButton(_imageIcon);
      this.startButton = _jButton;
      this.startButton.setToolTipText("Start simulation");
      this.startButton.setEnabled(false);
      final ActionListener _function = (ActionEvent it) -> {
        Controller ctrl = this.controller;
        if (((ctrl != null) && (!ctrl.isStarted()))) {
          this.startButton.setEnabled(false);
          ctrl.startSimulation();
        }
      };
      this.startButton.addActionListener(_function);
      JPanel topPanel = new JPanel();
      BoxLayout _boxLayout = new BoxLayout(topPanel, BoxLayout.X_AXIS);
      topPanel.setLayout(_boxLayout);
      topPanel.add(this.startButton);
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
    synchronized (this) {
      this.player = player;
    }
  }

  public void unbindPlayer(final Player player) {
    synchronized (this) {
      this.player = null;
    }
  }

  public void bindController(final Controller controller) {
    synchronized (this) {
      this.controller = controller;
      boolean _isStarted = this.controller.isStarted();
      this.startButton.setEnabled((!_isStarted));
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
    JOptionPane.showMessageDialog(this, "The sokoban is dead!", this.getTitle(), JOptionPane.INFORMATION_MESSAGE);
    this.dispose();
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
    this.gridPanel.setObjects(event.getTime(), event.getObjects());
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

  public void keyPressed(final KeyEvent e) {
    Player player = null;
    synchronized (this) {
      player = this.player;
    }
    if ((player != null)) {
      int _keyCode = e.getKeyCode();
      switch (_keyCode) {
        case KeyEvent.VK_LEFT:
          player.move(Direction.WEST);
          break;
        case KeyEvent.VK_RIGHT:
          player.move(Direction.EAST);
          break;
        case KeyEvent.VK_UP:
          player.move(Direction.NORTH);
          break;
        case KeyEvent.VK_DOWN:
          player.move(Direction.SOUTH);
          break;
      }
    }
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
    return super.equals(obj);
  }

  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Long.hashCode(this.waitingDuration);
    return result;
  }

  @SyntheticMember
  private static final long serialVersionUID = 3313200392L;
}
