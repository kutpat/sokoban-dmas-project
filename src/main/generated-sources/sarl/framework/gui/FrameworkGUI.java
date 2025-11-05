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

import framework.environment.EnvironmentListener;
import framework.environment.WorldModelState;
import framework.math.Point2f;
import framework.math.Shape2f;
import framework.math.Vector2f;
import io.sarl.lang.core.annotation.SarlElementType;
import io.sarl.lang.core.annotation.SarlSpecification;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.io.Serializable;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure0;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;

/**
 * GUI for the simulation framework.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
@SarlSpecification("0.15")
@SarlElementType(11)
@XbaseGenerated
@SuppressWarnings("all")
public interface FrameworkGUI extends EnvironmentListener {
  /**
   * Change the visibility of the GUI.
   * 
   * @param visible
   */
  void setVisible(final boolean visible);

  /**
   * Release all the resources own by the UI.
   */
  void dispose();

  /**
   * Change the message in the dedicated box.
   * 
   * @param message - the message.
   */
  void setMessage(final String message);

  /**
   * Replies the message in the dedicated box.
   * 
   * @return the message (could be <code>null</code>).
   */
  @Pure
  String getMessage();

  /**
   * Replies the width of the world.
   * 
   * @return the width.
   */
  @Pure
  float getWorldWidth();

  /**
   * Replies the height of the world.
   * 
   * @return the height.
   */
  @Pure
  float getWorldHeight();

  /**
   * Replies the last environment state.
   * 
   * @return the last environment state.
   */
  @Pure
  WorldModelState getLastWorldState();

  /**
   * Convert the coordinates in the MAS into the equivalent coordinates on the screen.
   * 
   * @param p the coordinates
   * @return the coordinates on the screen.
   */
  Point2f mas2screen(final Point2f p);

  /**
   * Convert the vector in the MAS into the equivalent vector on the screen.
   * 
   * @param v the vector
   * @return the vector on the screen.
   */
  Vector2f mas2screen(final Vector2f p);

  /**
   * Convert the size in the MAS into the equivalent size on the screen.
   * 
   * @param size the size
   * @return the size on the screen.
   */
  float mas2screen(final float size);

  /**
   * Convert the point from the screen coordinate to the MAS coordinate.
   * 
   * @param point the point on the screen.
   * @return the point in the MAS
   */
  Point2f screen2mas(final Point2f point);

  /**
   * Convert the given MAS shape to the equivalent AWT shape.
   * 
   * @param shape the MAS shape
   * @return the AWT shape.
   */
  Shape mas2screen(final Shape2f<?> shape);

  /**
   * Paint the world
   */
  void paintWorld(final Graphics2D g);

  /**
   * Paint the coordinate system axes.
   */
  void paintAxes(final Graphics2D g);

  /**
   * Paint an agent body.
   * 
   * @param g2d the graphical context in which is must be drawn.
   * @param positionOnScreen the position of the object on the screen.
   * @param orientationOnScreen the orientation of the body on the screen.
   * @param shape the shape of the body on the screen.
   * @param type the type of the body.
   * @param name the name of the body (could be <code>null</code>).
   * @param positionInMas the position of the body in the MAS.
   * @param frustum the frustum.
   */
  void paintAgentBody(final Graphics2D g2d, final Point2f positionOnScreen, final Vector2f orientationOnScreen, final Shape shape, final Serializable type, final String name, final Point2f positionInMas, final Shape frustum);

  /**
   * Paint a situated object.
   * 
   * @param g2d the graphical context in which is must be drawn.
   * @param positionOnScreen the position of the object on the screen.
   * @param orientationOnScreen the orientation of the body on the screen.
   * @param shape the shape of the body on the screen.
   * @param type the type of the object.
   * @param name the name of the body (could be <code>null</code>).
   * @param positionInMas the position of the body in the MAS.
   */
  void paintSituatedObject(final Graphics2D g2d, final Point2f positionOnScreen, final Vector2f orientationOnScreen, final Shape shape, final Serializable type, final String name, final Point2f positionInMas);

  /**
   * Change the mouse target.
   * 
   * @param screenPosition the position of the target on the screen
   */
  void setMouseTargetOnScreen(final Point2f screenPosition);

  /**
   * Change the handler for termination queried with the UI.
   * 
   * @param handler the handler
   */
  void setTerminationHandler(final Procedure0 handler);

  /**
   * Replies the handler for termination queried with the UI.
   * 
   * @return the handler
   */
  @Pure
  Procedure0 getTerminationHandler();
}
