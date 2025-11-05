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

import framework.environment.AgentBody;
import framework.environment.Frustum;
import framework.environment.MobileObject;
import framework.environment.SituatedObject;
import framework.environment.WorldModelState;
import framework.math.Point2f;
import framework.math.Shape2f;
import framework.math.Vector2f;
import io.sarl.lang.core.annotation.SarlElementType;
import io.sarl.lang.core.annotation.SarlSpecification;
import io.sarl.lang.core.annotation.SyntheticMember;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.lang.ref.WeakReference;
import javax.swing.JPanel;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;

/**
 * Graphical panel for displaying the world.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
@SarlSpecification("0.15")
@SarlElementType(10)
@XbaseGenerated
@SuppressWarnings("all")
public class WorldPanel extends JPanel {
  private final WeakReference<FrameworkGUI> windowContainer;

  /**
   * @param window the container.
   * @param hideMouseCursor indicates if the mouse cursor must be hidden.
   */
  public WorldPanel(final FrameworkGUI window, final boolean hideMouseCursor) {
    WeakReference<FrameworkGUI> _weakReference = new WeakReference<FrameworkGUI>(window);
    this.windowContainer = _weakReference;
    if (hideMouseCursor) {
      BufferedImage _bufferedImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
      Point _point = new Point();
      this.setCursor(this.getToolkit().createCustomCursor(_bufferedImage, _point, null));
    }
  }

  /**
   * Replies the container of this panel for the simulator UI.
   * 
   * @return the container.
   */
  @Pure
  protected final FrameworkGUI getWindow() {
    return this.windowContainer.get();
  }

  @Override
  public void paint(final Graphics g) {
    super.paint(g);
    final Graphics2D g2d = ((Graphics2D) g);
    g2d.setColor(this.getBackground().darker());
    float _mas2screen = this.getWindow().mas2screen(this.getWindow().getWorldWidth());
    float _mas2screen_1 = this.getWindow().mas2screen(this.getWindow().getWorldHeight());
    Rectangle2D.Float _float = new Rectangle2D.Float(
      0, 0, _mas2screen, _mas2screen_1);
    g2d.draw(_float);
    Dimension currentDim = this.getPreferredSize();
    this.getWindow().paintWorld(g2d);
    this.drawObjects(g2d, currentDim);
    this.getWindow().paintAxes(g2d);
  }

  private void drawObjects(final Graphics2D g2d, final Dimension currentDim) {
    final WorldModelState state = this.getWindow().getLastWorldState();
    if ((state == null)) {
      return;
    }
    Iterable<SituatedObject> _objects = state.getObjects();
    for (final SituatedObject o : _objects) {
      {
        Point2f positionOnScreen = this.getWindow().mas2screen(o.getPosition());
        Vector2f directionOnScreen = null;
        if ((o instanceof MobileObject)) {
          directionOnScreen = this.getWindow().mas2screen(((MobileObject)o).getDirection());
        } else {
          directionOnScreen = null;
        }
        Shape awtShape = this.getWindow().mas2screen(o.getShape());
        if ((o instanceof AgentBody)) {
          Frustum frustum = ((AgentBody)o).getFrustum();
          Shape2f<?> _shape = null;
          if (frustum!=null) {
            _shape=frustum.toShape(((AgentBody)o).getPosition(), ((AgentBody)o).getDirection());
          }
          Shape2f<?> frustumShape = _shape;
          this.getWindow().paintAgentBody(g2d, positionOnScreen, directionOnScreen, awtShape, 
            ((AgentBody)o).getType(), 
            ((AgentBody)o).getName(), 
            ((AgentBody)o).getPosition(), 
            this.getWindow().mas2screen(frustumShape));
        } else {
          this.getWindow().paintSituatedObject(g2d, positionOnScreen, directionOnScreen, awtShape, 
            o.getType(), 
            o.getName(), 
            o.getPosition());
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
  private static final long serialVersionUID = -1760833347L;
}
