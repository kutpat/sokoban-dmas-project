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

import framework.environment.WorldModelState;
import framework.math.Point2f;
import io.sarl.lang.core.annotation.SarlElementType;
import io.sarl.lang.core.annotation.SarlSpecification;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;

/**
 * State provider for the world model.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
@SarlSpecification("0.15")
@SarlElementType(11)
@XbaseGenerated
@SuppressWarnings("all")
public interface WorldModelStateProvider {
  /**
   * Set the mouse target.
   * 
   * @param target
   */
  void setMouseTarget(final Point2f target);

  /**
   * Replies the width of the environment.
   * 
   * @return the width of the environment.
   */
  @Pure
  float getWidth();

  /**
   * Replies the height of the environment.
   * 
   * @return the height of the environment.
   */
  @Pure
  float getHeight();

  /**
   * Replies the state of the world model.
   * 
   * @return the state of the world model.
   */
  @Pure
  WorldModelState getState();
}
