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
package framework.environment;

import framework.math.Rectangle2f;
import framework.math.Shape2f;
import io.sarl.lang.core.annotation.SarlElementType;
import io.sarl.lang.core.annotation.SarlSpecification;
import java.io.Serializable;
import java.util.Iterator;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;

/**
 * Definition of a spatial data-structure.
 * 
 * @param <N> - type of the root node.
 * @param <D> - the type of the objects inside the tree.
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
@SarlSpecification("0.15")
@SarlElementType(11)
@XbaseGenerated
@SuppressWarnings("all")
public interface SpatialDataStructure<D extends ShapedObject> extends Serializable {
  /**
   * Initialize the data-structure that is covering the given area.
   * 
   * @param worldSize the size of the world.
   */
  void initialize(final Rectangle2f worldSize);

  /**
   * Replies the bounds covered by the tree nodes.
   * 
   * @return the bounds covered by the tree.
   */
  @Pure
  Rectangle2f getBounds();

  /**
   * Change the data associated to the node.
   * 
   * @param data - the data.
   * @return <code>true</code> if the data was added.
   */
  boolean addData(final D data);

  /**
   * Change the data associated to the node.
   * 
   * @param data - the data.
   * @return <code>true</code> if the data was removed.
   */
  boolean removeData(final D data);

  /**
   * Replies an iterator on the data.
   * 
   * @return the iterator on the data.
   */
  Iterator<D> dataIterator();

  /**
   * Replies an iterable on the data.
   * 
   * @return the iterable on the data.
   */
  @Pure
  Iterable<D> getData();

  /**
   * Replies an iterator on the data elements that are intersecting the given bounds.
   * 
   * @param bounds - the selection bounds.
   * @return the iterator.
   */
  Iterator<D> dataIterator(final Shape2f<?> bounds);
}
