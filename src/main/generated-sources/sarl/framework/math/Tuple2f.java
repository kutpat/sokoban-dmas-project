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
package framework.math;

import com.google.common.base.Objects;
import io.sarl.lang.core.annotation.SarlElementType;
import io.sarl.lang.core.annotation.SarlSpecification;
import io.sarl.lang.core.annotation.SyntheticMember;
import java.io.Serializable;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;

/**
 * 2D tuple with 2 floating-point numbers.
 * 
 * Copied from {@link https://github.com/gallandarakhneorg/afc/}.
 * 
 * @param <T> is the implementation type of the tuple.
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
@SarlSpecification("0.15")
@SarlElementType(10)
@XbaseGenerated
@SuppressWarnings("all")
public abstract class Tuple2f<T extends Tuple2f<?>> implements Serializable, Cloneable, Comparable<T> {
  /**
   * x coordinate.
   */
  protected float x;

  /**
   * y coordinate.
   */
  protected float y;

  public Tuple2f() {
    this.x = (this.y = 0);
  }

  /**
   * @param tuple is the tuple to copy.
   */
  public Tuple2f(final Tuple2f<?> tuple) {
    this.x = tuple.x;
    this.y = tuple.y;
  }

  /**
   * @param tuple is the tuple to copy.
   */
  public Tuple2f(final int[] tuple) {
    this.x = tuple[0];
    this.y = tuple[1];
  }

  /**
   * @param tuple is the tuple to copy.
   */
  public Tuple2f(final float[] tuple) {
    this.x = tuple[0];
    this.y = tuple[1];
  }

  /**
   * @param tuple is the tuple to copy.
   */
  public Tuple2f(final long[] tuple) {
    this.x = tuple[0];
    this.y = tuple[1];
  }

  /**
   * @param tuple is the tuple to copy.
   */
  public Tuple2f(final double[] tuple) {
    double _get = tuple[0];
    this.x = ((float) _get);
    double _get_1 = tuple[1];
    this.y = ((float) _get_1);
  }

  /**
   * @param x
   * @param y
   */
  public Tuple2f(final int x, final int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * @param x
   * @param y
   */
  public Tuple2f(final float x, final float y) {
    this.x = x;
    this.y = y;
  }

  /**
   * @param x
   * @param y
   */
  public Tuple2f(final long x, final long y) {
    this.x = x;
    this.y = y;
  }

  /**
   * @param x
   * @param y
   */
  public Tuple2f(final double x, final double y) {
    this.x = ((float) x);
    this.y = ((float) y);
  }

  /**
   * Clone this point.
   * 
   * @return the clone.
   */
  @Override
  @Pure
  public T clone() {
    try {
      Object _clone = super.clone();
      return ((T) _clone);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  /**
   * Sets each component of this tuple to its absolute value.
   */
  public float absolute() {
    float _xblockexpression = (float) 0;
    {
      this.x = Math.abs(this.x);
      _xblockexpression = this.y = Math.abs(this.y);
    }
    return _xblockexpression;
  }

  /**
   * Sets each component of the tuple parameter to its absolute
   *  value and places the modified values into this tuple.
   *  @param t   the source tuple, which will not be modified
   */
  public float absolute(final T t) {
    return t.set(Math.abs(this.x), Math.abs(this.y));
  }

  /**
   * Sets the value of this tuple to the sum of itself and x and y.
   * @param x
   * @param y
   */
  public float add(final int x, final int y) {
    float _xblockexpression = (float) 0;
    {
      float _x = this.x;
      this.x = (_x + x);
      float _y = this.y;
      _xblockexpression = this.y = (_y + y);
    }
    return _xblockexpression;
  }

  /**
   * Sets the value of this tuple to the sum of itself and x and y.
   * @param x
   * @param y
   */
  public float add(final float x, final float y) {
    float _xblockexpression = (float) 0;
    {
      float _x = this.x;
      this.x = (_x + x);
      float _y = this.y;
      _xblockexpression = this.y = (_y + y);
    }
    return _xblockexpression;
  }

  /**
   * Sets the x value of this tuple to the sum of itself and x.
   * @param x
   */
  public float addX(final int x) {
    float _x = this.x;
    return this.x = (_x + x);
  }

  /**
   * Sets the x value of this tuple to the sum of itself and x.
   * @param x
   */
  public float addX(final float x) {
    float _x = this.x;
    return this.x = (_x + x);
  }

  /**
   * Sets the y value of this tuple to the sum of itself and y.
   * @param y
   */
  public float addY(final int y) {
    float _y = this.y;
    return this.y = (_y + y);
  }

  /**
   * Sets the y value of this tuple to the sum of itself and y.
   * @param y
   */
  public float addY(final float y) {
    float _y = this.y;
    return this.y = (_y + y);
  }

  /**
   * Clamps this tuple to the range [low, high].
   *  @param min  the lowest value in this tuple after clamping
   *  @param max  the highest value in this tuple after clamping
   */
  public float clamp(final int min, final int max) {
    float _xblockexpression = (float) 0;
    {
      if ((this.x < min)) {
        this.x = min;
      } else {
        if ((this.x > max)) {
          this.x = max;
        }
      }
      float _xifexpression = (float) 0;
      if ((this.y < min)) {
        _xifexpression = this.y = min;
      } else {
        float _xifexpression_1 = (float) 0;
        if ((this.y > max)) {
          _xifexpression_1 = this.y = max;
        }
        _xifexpression = _xifexpression_1;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }

  /**
   * Clamps this tuple to the range [low, high].
   *  @param min  the lowest value in this tuple after clamping
   *  @param max  the highest value in this tuple after clamping
   */
  public float clamp(final float min, final float max) {
    float _xblockexpression = (float) 0;
    {
      if ((this.x < min)) {
        this.x = min;
      } else {
        if ((this.x > max)) {
          this.x = max;
        }
      }
      float _xifexpression = (float) 0;
      if ((this.y < min)) {
        _xifexpression = this.y = min;
      } else {
        float _xifexpression_1 = (float) 0;
        if ((this.y > max)) {
          _xifexpression_1 = this.y = max;
        }
        _xifexpression = _xifexpression_1;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }

  /**
   * Clamps the minimum value of this tuple to the min parameter.
   *  @param min   the lowest value in this tuple after clamping
   */
  public float clampMin(final int min) {
    float _xblockexpression = (float) 0;
    {
      if ((this.x < min)) {
        this.x = min;
      }
      float _xifexpression = (float) 0;
      if ((this.y < min)) {
        _xifexpression = this.y = min;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }

  /**
   * Clamps the minimum value of this tuple to the min parameter.
   *  @param min   the lowest value in this tuple after clamping
   */
  public float clampMin(final float min) {
    float _xblockexpression = (float) 0;
    {
      if ((this.x < min)) {
        this.x = min;
      }
      float _xifexpression = (float) 0;
      if ((this.y < min)) {
        _xifexpression = this.y = min;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }

  /**
   * Clamps the maximum value of this tuple to the max parameter.
   *  @param max   the highest value in the tuple after clamping
   */
  public float clampMax(final int max) {
    float _xblockexpression = (float) 0;
    {
      if ((this.x > max)) {
        this.x = max;
      }
      float _xifexpression = (float) 0;
      if ((this.y > max)) {
        _xifexpression = this.y = max;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }

  /**
   * Clamps the maximum value of this tuple to the max parameter.
   *  @param max   the highest value in the tuple after clamping
   */
  public float clampMax(final float max) {
    float _xblockexpression = (float) 0;
    {
      if ((this.x > max)) {
        this.x = max;
      }
      float _xifexpression = (float) 0;
      if ((this.y > max)) {
        _xifexpression = this.y = max;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }

  /**
   * Copies the values of this tuple into the tuple t.
   * @param t is the target tuple
   */
  @Pure
  public float get(final T t) {
    return t.set(this.x, this.y);
  }

  /**
   * Copies the value of the elements of this tuple into the array t.
   *  @param t the array that will contain the values of the vector
   */
  @Pure
  public int get(final int[] t) {
    int _xblockexpression = (int) 0;
    {
      t[0] = ((int) this.x);
      _xblockexpression = t[1] = ((int) this.y);
    }
    return _xblockexpression;
  }

  /**
   * Copies the value of the elements of this tuple into the array t.
   *  @param t the array that will contain the values of the vector
   */
  @Pure
  public float get(final float[] t) {
    float _xblockexpression = (float) 0;
    {
      t[0] = this.x;
      _xblockexpression = t[1] = this.y;
    }
    return _xblockexpression;
  }

  /**
   * Sets the value of this tuple to the negation of tuple t1.
   * @param t1 the source tuple
   */
  public float negate(final T t1) {
    float _xblockexpression = (float) 0;
    {
      this.x = (-t1.x);
      _xblockexpression = this.y = (-t1.y);
    }
    return _xblockexpression;
  }

  /**
   * Negates the value of this tuple in place.
   */
  public float negate() {
    float _xblockexpression = (float) 0;
    {
      this.x = (-this.x);
      _xblockexpression = this.y = (-this.y);
    }
    return _xblockexpression;
  }

  /**
   * Sets the value of this tuple to the scalar multiplication
   * of tuple t1.
   * @param s the scalar value
   * @param t1 the source tuple
   */
  public float scale(final int s, final T t1) {
    float _xblockexpression = (float) 0;
    {
      this.x = (s * t1.x);
      _xblockexpression = this.y = (s * t1.y);
    }
    return _xblockexpression;
  }

  /**
   * Sets the value of this tuple to the scalar multiplication
   * of tuple t1.
   * @param s the scalar value
   * @param t1 the source tuple
   */
  public float scale(final float s, final T t1) {
    float _xblockexpression = (float) 0;
    {
      this.x = (s * t1.x);
      _xblockexpression = this.y = (s * t1.y);
    }
    return _xblockexpression;
  }

  /**
   * Sets the value of this tuple to the scalar multiplication
   * of the scale factor with this.
   * @param s the scalar value
   */
  public float scale(final int s) {
    float _xblockexpression = (float) 0;
    {
      this.x = (s * this.x);
      _xblockexpression = this.y = (s * this.y);
    }
    return _xblockexpression;
  }

  /**
   * Sets the value of this tuple to the scalar multiplication
   * of the scale factor with this.
   * @param s the scalar value
   */
  public float scale(final float s) {
    float _xblockexpression = (float) 0;
    {
      this.x = (s * this.x);
      _xblockexpression = this.y = (s * this.y);
    }
    return _xblockexpression;
  }

  /**
   * Sets the value of this tuple to the value of tuple t1.
   * @param t1 the tuple to be copied
   */
  public float set(final Tuple2f<?> t1) {
    float _xblockexpression = (float) 0;
    {
      this.x = t1.x;
      _xblockexpression = this.y = t1.y;
    }
    return _xblockexpression;
  }

  /**
   * Sets the value of this tuple to the specified x and y
   * coordinates.
   * @param x the x coordinate
   * @param y the y coordinate
   */
  public float set(final int x, final int y) {
    float _xblockexpression = (float) 0;
    {
      this.x = x;
      _xblockexpression = this.y = y;
    }
    return _xblockexpression;
  }

  /**
   * Sets the value of this tuple to the specified x and y
   * coordinates.
   * @param x the x coordinate
   * @param y the y coordinate
   */
  public float set(final float x, final float y) {
    float _xblockexpression = (float) 0;
    {
      this.x = x;
      _xblockexpression = this.y = y;
    }
    return _xblockexpression;
  }

  /**
   * Sets the value of this tuple from the 2 values specified in
   * the array.
   * @param t the array of length 2 containing xy in order
   */
  public float set(final int[] t) {
    float _xblockexpression = (float) 0;
    {
      this.x = t[0];
      _xblockexpression = this.y = t[1];
    }
    return _xblockexpression;
  }

  /**
   * Sets the value of this tuple from the 2 values specified in
   * the array.
   * @param t the array of length 2 containing xy in order
   */
  public float set(final float[] t) {
    float _xblockexpression = (float) 0;
    {
      this.x = t[0];
      _xblockexpression = this.y = t[1];
    }
    return _xblockexpression;
  }

  /**
   * Get the <i>x</i> coordinate.
   * 
   * @return the x coordinate.
   */
  @Pure
  public float getX() {
    return this.x;
  }

  /**
   * Set the <i>x</i> coordinate.
   * 
   * @param x  value to <i>x</i> coordinate.
   */
  public void setX(final int x) {
    this.x = x;
  }

  /**
   * Set the <i>x</i> coordinate.
   * 
   * @param x  value to <i>x</i> coordinate.
   */
  public void setX(final float x) {
    this.x = x;
  }

  /**
   * Get the <i>y</i> coordinate.
   * 
   * @return  the <i>y</i> coordinate.
   */
  @Pure
  public float getY() {
    return this.y;
  }

  /**
   * Set the <i>y</i> coordinate.
   * 
   * @param y value to <i>y</i> coordinate.
   */
  public void setY(final int y) {
    this.y = y;
  }

  /**
   * Set the <i>y</i> coordinate.
   * 
   * @param y value to <i>y</i> coordinate.
   */
  public void setY(final float y) {
    this.y = y;
  }

  /**
   * Sets the value of this tuple to the difference of itself and x and y.
   * @param x
   * @param y
   */
  public float sub(final int x, final int y) {
    float _xblockexpression = (float) 0;
    {
      float _x = this.x;
      this.x = (_x - x);
      float _y = this.y;
      _xblockexpression = this.y = (_y - y);
    }
    return _xblockexpression;
  }

  /**
   * Sets the value of this tuple to the difference of itself and x and y.
   * @param x
   * @param y
   */
  public float sub(final float x, final float y) {
    float _xblockexpression = (float) 0;
    {
      float _x = this.x;
      this.x = (_x - x);
      float _y = this.y;
      _xblockexpression = this.y = (_y - y);
    }
    return _xblockexpression;
  }

  /**
   * Sets the x value of this tuple to the difference of itself and x.
   * @param x
   */
  public float subX(final int x) {
    float _x = this.x;
    return this.x = (_x - x);
  }

  /**
   * Sets the x value of this tuple to the difference of itself and x.
   * @param x
   */
  public float subX(final float x) {
    float _x = this.x;
    return this.x = (_x - x);
  }

  /**
   * Sets the y value of this tuple to the difference of itself and y.
   * @param y
   */
  public float subY(final int y) {
    float _y = this.y;
    return this.y = (_y - y);
  }

  /**
   * Sets the y value of this tuple to the difference of itself and y.
   * @param y
   */
  public float subY(final float y) {
    float _y = this.y;
    return this.y = (_y - y);
  }

  /**
   * Linearly interpolates between tuples t1 and t2 and places the
   *  result into this tuple:  this = (1-alpha)*t1 + alpha*t2.
   *  @param t1  the first tuple
   *  @param t2  the second tuple
   *  @param alpha  the alpha interpolation parameter
   */
  public float interpolate(final T t1, final T t2, final float alpha) {
    float _xblockexpression = (float) 0;
    {
      this.x = (((1f - alpha) * t1.x) + (alpha * t2.x));
      _xblockexpression = this.y = (((1f - alpha) * t1.y) + (alpha * t2.y));
    }
    return _xblockexpression;
  }

  /**
   * Linearly interpolates between this tuple and tuple t1 and
   *  places the result into this tuple:  this = (1-alpha)*this + alpha*t1.
   *  @param t1  the first tuple
   *  @param alpha  the alpha interpolation parameter
   */
  public float interpolate(final T t1, final float alpha) {
    float _xblockexpression = (float) 0;
    {
      this.x = (((1f - alpha) * this.x) + (alpha * t1.x));
      _xblockexpression = this.y = (((1f - alpha) * this.y) + (alpha * t1.y));
    }
    return _xblockexpression;
  }

  /**
   * Returns true if all of the data members of Tuple2f t1 are
   * equal to the corresponding data members in this Tuple2f.
   * @param t1  the vector with which the comparison is made
   * @return  true or false
   */
  @Pure
  public boolean equals(final Tuple2f<?> t1) {
    try {
      return ((this.x == t1.x) && (this.y == t1.y));
    } catch (final Throwable _t) {
      if (_t instanceof NullPointerException) {
        return false;
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }

  /**
   * Returns true if the Object1 : T is of type Tuple2f and all of the
   * data members of t1 are equal to the corresponding data members in
   * this Tuple2f.
   * @param t1  the object with which the comparison is made
   * @return  true or false
   */
  @Override
  @Pure
  public boolean equals(final Object t1) {
    try {
      if ((t1 instanceof Tuple2f<?>)) {
        return this.equals(((Tuple2f<?>)t1));
      }
      return false;
    } catch (final Throwable _t) {
      if (_t instanceof AssertionError) {
        final AssertionError e = (AssertionError)_t;
        throw e;
      } else if (_t instanceof Throwable) {
        return false;
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }

  /**
   * Returns true if the L-infinite distance between this tuple
   * and tuple t1 is less than or equal to the epsilon parameter,
   * otherwise returns false.  The L-infinite
   * distance is equal to MAX[abs(x1-x2), abs(y1-y2)].
   * @param t1  the tuple to be compared to this tuple
   * @param epsilon  the threshold value
   * @return  true or false
   */
  @Pure
  public boolean epsilonEquals(final T t1, final float epsilon) {
    float diff = 0;
    diff = (this.x - t1.x);
    boolean _isNaN = Float.valueOf(diff).isNaN();
    if (_isNaN) {
      return false;
    }
    float _xifexpression = (float) 0;
    if ((diff < 0f)) {
      _xifexpression = (-diff);
    } else {
      _xifexpression = diff;
    }
    if ((_xifexpression > epsilon)) {
      return false;
    }
    diff = (this.y - t1.y);
    boolean _isNaN_1 = Float.valueOf(diff).isNaN();
    if (_isNaN_1) {
      return false;
    }
    float _xifexpression_1 = (float) 0;
    if ((diff < 0f)) {
      _xifexpression_1 = (-diff);
    } else {
      _xifexpression_1 = diff;
    }
    if ((_xifexpression_1 > epsilon)) {
      return false;
    }
    return true;
  }

  /**
   * Returns true if the L-infinite distance between this tuple
   * and the zero tuple is less than or equal to the epsilon parameter,
   * otherwise returns false.  The L-infinite
   * distance is equal to MAX[abs(x1), abs(y1)].
   * @param epsilon  the threshold value
   * @return  true or false
   */
  @Pure
  public boolean epsilonNul(final float epsilon) {
    float _xifexpression = (float) 0;
    if ((this.x < 0f)) {
      _xifexpression = (-this.x);
    } else {
      _xifexpression = this.x;
    }
    if ((_xifexpression > epsilon)) {
      return false;
    }
    float _xifexpression_1 = (float) 0;
    if ((this.y < 0f)) {
      _xifexpression_1 = (-this.y);
    } else {
      _xifexpression_1 = this.y;
    }
    if ((_xifexpression_1 > epsilon)) {
      return false;
    }
    return true;
  }

  /**
   * Returns a hash code value based on the data values in this
   * object.  Two different Tuple2f objects with identical data values
   * (i.e., Tuple2f.equals returns true) will return the same hash
   * code value.  Two objects with different data members may return the
   * same hash value, although this is not likely.
   * @return the integer hash code value
   */
  @Override
  @Pure
  public int hashCode() {
    return Objects.hashCode(Float.valueOf(this.x), Float.valueOf(this.y));
  }

  @Override
  @Pure
  public String toString() {
    return (((("(" + Float.valueOf(this.x)) + ";") + Float.valueOf(this.y)) + ")");
  }

  @Override
  @Pure
  public int compareTo(final T o) {
    if ((o == null)) {
      return Integer.MAX_VALUE;
    }
    int c = Float.compare(this.x, o.x);
    if ((c != 0)) {
      return c;
    }
    return Float.compare(this.y, o.y);
  }

  /**
   * Replies if the tuple has zero coordinates.
   * 
   * @return <code>true</code> if the x and y coordinates are equal to zero.
   */
  @Pure
  public boolean isEmpty() {
    return ((this.x == 0f) && (this.y == 0f));
  }

  @SyntheticMember
  private static final long serialVersionUID = -11621025765L;
}
