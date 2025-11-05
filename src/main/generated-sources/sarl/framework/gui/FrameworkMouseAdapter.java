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

import framework.math.Point2f;
import io.sarl.lang.core.annotation.SarlElementType;
import io.sarl.lang.core.annotation.SarlSpecification;
import io.sarl.lang.core.annotation.SyntheticMember;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.ref.WeakReference;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;

/**
 * Mouse adapter for the framework UI.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
@SarlSpecification("0.15")
@SarlElementType(10)
@XbaseGenerated
@SuppressWarnings("all")
public class FrameworkMouseAdapter extends MouseAdapter {
  private final WeakReference<FrameworkGUI> parent;

  public FrameworkMouseAdapter(final FrameworkGUI g) {
    WeakReference<FrameworkGUI> _weakReference = new WeakReference<FrameworkGUI>(g);
    this.parent = _weakReference;
  }

  @Override
  public void mouseDragged(final MouseEvent e) {
    FrameworkGUI _get = this.parent.get();
    int _x = e.getX();
    int _y = e.getY();
    Point2f _point2f = new Point2f(_x, _y);
    _get.setMouseTargetOnScreen(_point2f);
  }

  @Override
  public void mouseMoved(final MouseEvent e) {
    FrameworkGUI _get = this.parent.get();
    int _x = e.getX();
    int _y = e.getY();
    Point2f _point2f = new Point2f(_x, _y);
    _get.setMouseTargetOnScreen(_point2f);
  }

  @Override
  public void mouseEntered(final MouseEvent e) {
    FrameworkGUI _get = this.parent.get();
    int _x = e.getX();
    int _y = e.getY();
    Point2f _point2f = new Point2f(_x, _y);
    _get.setMouseTargetOnScreen(_point2f);
  }

  @Override
  public void mouseExited(final MouseEvent e) {
    FrameworkGUI _get = this.parent.get();
    _get.setMouseTargetOnScreen(null);
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
}
