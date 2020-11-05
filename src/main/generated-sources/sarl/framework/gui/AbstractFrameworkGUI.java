/**
 * $Id$
 * 
 * Copyright (c) 2011-17 Stephane GALLAND <stephane.galland@utbm.fr>.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * This program is free software; you can redistribute it and/or modify
 */
package framework.gui;

import framework.environment.EnvironmentEvent;
import framework.environment.WorldModelState;
import framework.math.Circle2f;
import framework.math.Point2f;
import framework.math.Rectangle2f;
import framework.math.Shape2f;
import framework.math.Vector2f;
import framework.time.TimeManager;
import io.sarl.lang.core.annotation.SarlElementType;
import io.sarl.lang.core.annotation.SarlSpecification;
import io.sarl.lang.core.annotation.SyntheticMember;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.net.URL;
import java.text.MessageFormat;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure0;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;

/**
 * Abstract implementation of a GUI for the agent framework.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
@SarlSpecification("0.15")
@SarlElementType(10)
@XbaseGenerated
@SuppressWarnings("all")
public abstract class AbstractFrameworkGUI extends JFrame implements FrameworkGUI {
  private static final int WAITING_FACTOR = 2;

  private static final int WAITING_MIN = 0;

  private static final int WAITING_MAX = 20;

  private final float worldWidth;

  private final float worldHeight;

  private final TimeManager timeManager;

  private WorldModelState lastState;

  private WorldModelStateProvider environment;

  private final JScrollPane scroll;

  private final JLabel messageBox;

  private final JSlider speedSlider;

  private Procedure0 terminationHandler;

  @Pure
  private static int time2slider(final float delay) {
    return ((int) ((AbstractFrameworkGUI.WAITING_MAX - AbstractFrameworkGUI.WAITING_MIN) - (delay / AbstractFrameworkGUI.WAITING_FACTOR)));
  }

  @Pure
  private static float slider2time(final int value) {
    return (((AbstractFrameworkGUI.WAITING_MAX - AbstractFrameworkGUI.WAITING_MIN) - value) * AbstractFrameworkGUI.WAITING_FACTOR);
  }

  @Pure
  public boolean equals(final Object o) {
    return false;
  }

  @Pure
  public int hashCode() {
    return System.identityHashCode(this);
  }

  public void setTerminationHandler(final Procedure0 handler) {
    this.terminationHandler = handler;
  }

  @Pure
  public Procedure0 getTerminationHandler() {
    return this.terminationHandler;
  }

  /**
   * @param title
   * @param worldWidth
   * @param worldHeight
   * @param frameIcon
   * @param timeManager
   */
  public AbstractFrameworkGUI(final String title, final float worldWidth, final float worldHeight, final URL frameIcon, final TimeManager timeManager) {
    this.setTitle(title);
    ImageIcon icon = new ImageIcon(frameIcon);
    this.setIconImage(icon.getImage());
    Container content = this.getContentPane();
    BorderLayout _borderLayout = new BorderLayout();
    content.setLayout(_borderLayout);
    boolean _isMouseCursorHidden = this.isMouseCursorHidden();
    WorldPanel world = new WorldPanel(this, _isMouseCursorHidden);
    this.worldWidth = worldWidth;
    this.worldHeight = worldHeight;
    this.timeManager = timeManager;
    JScrollPane _jScrollPane = new JScrollPane(world);
    this.scroll = _jScrollPane;
    content.add(BorderLayout.CENTER, this.scroll);
    JButton closeBt = new JButton("Quit");
    final ActionListener _function = (ActionEvent it) -> {
      this.terminationHandler.apply();
    };
    closeBt.addActionListener(_function);
    JSlider _jSlider = new JSlider(JSlider.HORIZONTAL);
    this.speedSlider = _jSlider;
    this.speedSlider.setToolTipText("Change the simulation speed");
    this.speedSlider.setMinimum(AbstractFrameworkGUI.WAITING_MIN);
    this.speedSlider.setMaximum(AbstractFrameworkGUI.WAITING_MAX);
    this.speedSlider.setValue(AbstractFrameworkGUI.time2slider(timeManager.getSimulationDelay()));
    final ChangeListener _function_1 = (ChangeEvent it) -> {
      this.timeManager.setSimulationDelay(AbstractFrameworkGUI.slider2time(this.speedSlider.getValue()));
    };
    this.speedSlider.addChangeListener(_function_1);
    JLabel _jLabel = new JLabel();
    this.messageBox = _jLabel;
    content.add(BorderLayout.SOUTH, this.createBottomPanel(this.speedSlider, closeBt, this.messageBox));
    Dimension _dimension = new Dimension(((int) worldWidth), ((int) worldHeight));
    world.setPreferredSize(_dimension);
    WindowClosingHandler _windowClosingHandler = new WindowClosingHandler(this);
    this.addWindowListener(_windowClosingHandler);
    FrameworkMouseAdapter mouseAdapter = new FrameworkMouseAdapter(this);
    world.addMouseMotionListener(mouseAdapter);
    world.addMouseListener(mouseAdapter);
    this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    this.pack();
    Dimension d = this.getSize();
    this.setLocation(((-d.width) / 2), ((-d.height) / 2));
    this.setLocationRelativeTo(null);
  }

  /**
   * Replies if the mouse cursor must be hidden or not.
   * 
   * @return <code>true</code> to hide the cursor, <code>false</code> to show.
   */
  @Pure
  protected abstract boolean isMouseCursorHidden();

  @Override
  public void setMessage(final String message) {
    this.messageBox.setText(message);
  }

  @Override
  @Pure
  public String getMessage() {
    return this.messageBox.getText();
  }

  /**
   * Create the bottom panel.
   * 
   * @param speedSlider the slider for changing the simulation speed.
   * @param closeButton the close button.
   * @param messageBox the box for messages.
   * @return the bottom panel.
   */
  protected JComponent createBottomPanel(final JSlider speedSlider, final JComponent closeButton, final JComponent messageBox) {
    JPanel bottomPanel = new JPanel();
    GridLayout _gridLayout = new GridLayout(3, 1);
    bottomPanel.setLayout(_gridLayout);
    bottomPanel.add(speedSlider);
    bottomPanel.add(closeButton);
    bottomPanel.add(messageBox);
    return bottomPanel;
  }

  @Override
  public void environmentChanged(final EnvironmentEvent event) {
    synchronized (this.getTreeLock()) {
      if ((this.environment == null)) {
        this.environment = event.getStateProvider();
      }
      this.lastState = this.environment.getState();
      this.repaint();
    }
  }

  @Override
  public void setMouseTargetOnScreen(final Point2f screenPosition) {
    synchronized (this.getTreeLock()) {
      Point2f masPosition = this.screen2mas(screenPosition);
      if ((this.environment != null)) {
        this.environment.setMouseTarget(masPosition);
      }
      String _xifexpression = null;
      if ((masPosition == null)) {
        _xifexpression = null;
      } else {
        _xifexpression = MessageFormat.format("Target: ({0,number,#.#}; {1,number,#.#})", Float.valueOf(masPosition.getX()), Float.valueOf(masPosition.getY()));
      }
      this.setMessage(_xifexpression);
    }
  }

  /**
   * @param masPosition the position of the target in the MAS
   */
  protected void setMouseTargetInMAS(final Point2f masPosition) {
    synchronized (this.getTreeLock()) {
      if ((this.environment != null)) {
        this.environment.setMouseTarget(masPosition);
      }
      String _xifexpression = null;
      if ((masPosition == null)) {
        _xifexpression = null;
      } else {
        _xifexpression = MessageFormat.format("Target: ({0,number,#.#}; {1,number,#.#})", Float.valueOf(masPosition.getX()), Float.valueOf(masPosition.getY()));
      }
      this.setMessage(_xifexpression);
    }
  }

  @Override
  @Pure
  public float getWorldWidth() {
    return this.worldWidth;
  }

  /**
   * Replies the height of the world.
   * 
   * @return the height.
   */
  @Override
  @Pure
  public float getWorldHeight() {
    return this.worldHeight;
  }

  /**
   * Replies the last environment state.
   * 
   * @return the last environment state.
   */
  @Override
  @Pure
  public WorldModelState getLastWorldState() {
    return this.lastState;
  }

  /**
   * Convert the coordinates in the MAS into the equivalent coordinates on the screen.
   * 
   * @param p the coordinates
   * @return the coordinates on the screen.
   */
  @Override
  public Point2f mas2screen(final Point2f p) {
    boolean _equals = p.operator_equals(null);
    if (_equals) {
      return null;
    }
    float _x = p.getX();
    float _y = p.getY();
    return new Point2f(_x, (this.worldHeight - _y));
  }

  /**
   * Convert the vector in the MAS into the equivalent vector on the screen.
   * 
   * @param v the vector
   * @return the vector on the screen.
   */
  @Override
  public Vector2f mas2screen(final Vector2f p) {
    if ((p == null)) {
      return null;
    }
    float _x = p.getX();
    float _y = p.getY();
    return new Vector2f(_x, (-_y), true);
  }

  /**
   * Convert the size in the MAS into the equivalent size on the screen.
   * 
   * @param size the size
   * @return the size on the screen.
   */
  @Override
  public float mas2screen(final float size) {
    return size;
  }

  /**
   * Convert the point from the screen coordinate to the MAS coordinate.
   * 
   * @param point the point on the screen.
   * @return the point in the MAS
   */
  @Override
  public Point2f screen2mas(final Point2f point) {
    if ((point == null)) {
      return null;
    }
    float _x = point.getX();
    float _y = point.getY();
    return new Point2f(_x, 
      (this.worldHeight - _y));
  }

  /**
   * Convert the given MAS shape to the equivalent AWT shape.
   * 
   * @param shape the MAS shape
   * @return the AWT shape.
   */
  @Override
  public Shape mas2screen(final Shape2f<?> shape) {
    if ((shape == null)) {
      return null;
    }
    if ((shape instanceof Circle2f)) {
      Circle2f circle = ((Circle2f)shape);
      Point2f p = this.mas2screen(((Circle2f)shape).getCenter());
      float radius = this.mas2screen(circle.getRadius());
      float _x = p.getX();
      float _y = p.getY();
      return new Ellipse2D.Float(
        (_x - radius), 
        (_y - radius), 
        (2f * radius), 
        (2f * radius));
    }
    if ((shape instanceof Rectangle2f)) {
      Rectangle2f rect = ((Rectangle2f)shape);
      Point2f l = this.mas2screen(rect.getLower());
      Point2f u = this.mas2screen(rect.getUpper());
      float _min = Math.min(l.getX(), u.getX());
      float _min_1 = Math.min(l.getY(), u.getY());
      float _mas2screen = this.mas2screen(rect.getWidth());
      float _mas2screen_1 = this.mas2screen(rect.getHeight());
      return new Rectangle2D.Float(
        _min, 
        _min_1, _mas2screen, _mas2screen_1);
    }
    throw new IllegalArgumentException();
  }

  @Override
  public void paintWorld(final Graphics2D g2d) {
  }

  @Override
  public void paintAxes(final Graphics2D g2d) {
    Dimension dim = this.scroll.getViewport().getViewSize();
    g2d.setColor(g2d.getBackground().darker());
    GeneralPath p = new GeneralPath();
    p.moveTo(19, (dim.height - 5));
    p.lineTo(22, (dim.height - 2));
    p.lineTo(2, (dim.height - 2));
    p.lineTo(2, (dim.height - 22));
    p.lineTo(5, (dim.height - 19));
    g2d.draw(p);
    Font oldFont = g2d.getFont();
    Font f = oldFont.deriveFont(6);
    g2d.setFont(f);
    g2d.drawString("x", 24, (dim.height - 1));
    g2d.drawString("y", 1, (dim.height - 25));
    g2d.setFont(oldFont);
  }

  @SyntheticMember
  private static final long serialVersionUID = 1181946723L;
}
