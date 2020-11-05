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

import framework.math.Point2f;
import framework.math.Shape2f;
import io.sarl.lang.core.annotation.DefaultValue;
import io.sarl.lang.core.annotation.DefaultValueSource;
import io.sarl.lang.core.annotation.DefaultValueUse;
import io.sarl.lang.core.annotation.SarlElementType;
import io.sarl.lang.core.annotation.SarlSourceCode;
import io.sarl.lang.core.annotation.SarlSpecification;
import io.sarl.lang.core.annotation.SyntheticMember;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;

/**
 * Abstract implementation of an object on the environment.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
@SarlSpecification("0.15")
@SarlElementType(10)
@XbaseGenerated
@SuppressWarnings("all")
public abstract class AbstractSituatedObject implements SituatedObject, Serializable {
  private final UUID id;

  private Point2f position = new Point2f();

  private Shape2f<?> shape;

  private String name;

  private Serializable type;

  /**
   * @param id the identifier of the object.
   * @param shape the shape of the body, considering that it is centered at the (0,0) position.
   * @param position is the position of the object.
   */
  @DefaultValueSource
  public AbstractSituatedObject(final UUID id, final Shape2f<?> shape, @DefaultValue("framework.environment.AbstractSituatedObject#NEW_0") final Point2f position) {
    class $AssertEvaluator$ {
      final boolean $$result;
      $AssertEvaluator$() {
        this.$$result = (id != null);
      }
    }
    assert new $AssertEvaluator$().$$result;
    this.id = id;
    this.shape = shape;
    if ((position != null)) {
      this.position.set(position);
    }
  }

  /**
   * @param id the identifier of the object.
   * @param shape the shape of the body, considering that it is centered at the (0,0) position.
   * @param x is the position of the object.
   * @param y is the position of the object.
   */
  public AbstractSituatedObject(final UUID id, final Shape2f<?> shape, final float x, final float y) {
    class $AssertEvaluator$ {
      final boolean $$result;
      $AssertEvaluator$() {
        this.$$result = (id != null);
      }
    }
    assert new $AssertEvaluator$().$$result;
    this.id = id;
    this.shape = shape;
    this.position.set(x, y);
  }

  @Override
  @Pure
  public AbstractSituatedObject clone() {
    try {
      Object _clone = super.clone();
      AbstractSituatedObject clone = ((AbstractSituatedObject) _clone);
      clone.position = this.position.clone();
      clone.shape = this.shape.clone();
      return clone;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  @Override
  @Pure
  public boolean equals(final Object obj) {
    if ((obj == this)) {
      return true;
    }
    if ((obj instanceof SituatedObject)) {
      UUID _iD = ((SituatedObject)obj).getID();
      UUID _iD_1 = this.getID();
      return Objects.equals(_iD, _iD_1);
    }
    return false;
  }

  @Override
  @Pure
  public int hashCode() {
    return com.google.common.base.Objects.hashCode(this.getID());
  }

  @Override
  @Pure
  public int compareTo(final SituatedObject o) {
    int _xifexpression = (int) 0;
    if ((o == null)) {
      _xifexpression = Integer.MAX_VALUE;
    } else {
      UUID _iD = this.getID();
      UUID _iD_1 = o.getID();
      _xifexpression = (_iD.compareTo(_iD_1));
    }
    return _xifexpression;
  }

  @Override
  @Pure
  public Serializable getType() {
    return this.type;
  }

  @Override
  @Pure
  public UUID getID() {
    return this.id;
  }

  /**
   * Set the type of the object.
   * 
   * @param type
   */
  public void setType(final Serializable type) {
    this.type = type;
  }

  @Override
  @Pure
  public String getName() {
    return this.name;
  }

  /**
   * Change the name of the object.
   * 
   * The name is defined only for displaying purpose.
   * 
   * @return the name of the object.
   */
  public void setName(final String name) {
    this.name = name;
  }

  @Pure
  public Shape2f<?> getShape() {
    return this.shape.translate(this.position);
  }

  @Override
  @Pure
  public Point2f getPosition() {
    return this.position.clone();
  }

  @Override
  @Pure
  public float getX() {
    return this.position.getX();
  }

  @Override
  @Pure
  public float getY() {
    return this.position.getY();
  }

  /**
   * Set the position of the object.
   * 
   * @param x
   * @param y
   */
  @SuppressWarnings("discouraged_reference")
  protected void setPosition(final float x, final float y) {
    if ((Double.isNaN(x) || Double.isNaN(y))) {
      System.err.println(
        MessageFormat.format("**** INVALID POSITION (NaN); object with name \'{0}\'.", this.name));
    } else {
      this.position.set(x, y);
    }
  }

  /**
   * Set the position of the object.
   * 
   * @param position
   */
  @SuppressWarnings("discouraged_reference")
  protected void setPosition(final Point2f position) {
    if ((Double.isNaN(position.getX()) || Double.isNaN(position.getY()))) {
      System.err.println(
        MessageFormat.format("**** INVALID POSITION (NaN); object with name \'{0}\'.", this.name));
    } else {
      this.position.set(position);
    }
  }

  /**
   * Move the position of the object.
   * 
   * @param x
   * @param y
   */
  @SuppressWarnings("discouraged_reference")
  protected void addPosition(final float x, final float y) {
    if ((Double.isNaN(x) || Double.isNaN(y))) {
      System.err.println(
        MessageFormat.format("**** INVALID POSITION (NaN); object with name \'{0}\'.", this.name));
    } else {
      this.position.add(x, y);
    }
  }

  @Override
  @Pure
  public String toString() {
    String _elvis = null;
    if (this.name != null) {
      _elvis = this.name;
    } else {
      String _string = super.toString();
      _elvis = _string;
    }
    return _elvis;
  }

  /**
   * Default value for the parameter position
   */
  @Pure
  @SyntheticMember
  @SarlSourceCode("null")
  private static Point2f $DEFAULT_VALUE$NEW_0() {
    return null;
  }

  /**
   * @optionalparam id the identifier of the object.
   * @optionalparam shape the shape of the body, considering that it is centered at the (0,0) position.
   * @optionalparam position is the position of the object.
   */
  @DefaultValueUse("java.util.UUID,framework.math.Shape2f,framework.math.Point2f")
  @SyntheticMember
  public AbstractSituatedObject(final UUID id, final Shape2f<?> shape) {
    this(id, shape, $DEFAULT_VALUE$NEW_0());
  }

  @SyntheticMember
  private static final long serialVersionUID = 8048970959L;
}
