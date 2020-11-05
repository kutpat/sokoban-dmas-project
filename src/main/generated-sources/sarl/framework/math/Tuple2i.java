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
 * 2D tuple with 2 integer numbers.
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
public abstract class Tuple2i<T extends Tuple2i<?>> implements Serializable, Cloneable, Comparable<T> {
  /**
   * x coordinate.
   */
  protected int x;

  /**
   * y coordinate.
   */
  protected int y;

  public Tuple2i() {
    this.x = (this.y = 0);
  }

  /**
   * @param tuple is the tuple to copy.
   */
  public Tuple2i(final Tuple2i<?> tuple) {
    this.x = tuple.x;
    this.y = tuple.y;
  }

  /**
   * @param tuple is the tuple to copy.
   */
  public Tuple2i(final int[] tuple) {
    this.x = tuple[0];
    this.y = tuple[1];
  }

  /**
   * @param x
   * @param y
   */
  public Tuple2i(final int x, final int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Clone this point.
   * 
   * @return the clone.
   */
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
  public int absolute() {
    int _xblockexpression = (int) 0;
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
  public int absolute(final T t) {
    return t.set(Math.abs(this.x), Math.abs(this.y));
  }

  /**
   * Sets the value of this tuple to the sum of itself and x and y.
   * @param x
   * @param y
   */
  public int add(final int x, final int y) {
    int _xblockexpression = (int) 0;
    {
      int _x = this.x;
      this.x = (_x + x);
      int _y = this.y;
      _xblockexpression = this.y = (_y + y);
    }
    return _xblockexpression;
  }

  /**
   * Sets the x value of this tuple to the sum of itself and x.
   * @param x
   */
  public int addX(final int x) {
    int _x = this.x;
    return this.x = (_x + x);
  }

  /**
   * Sets the y value of this tuple to the sum of itself and y.
   * @param y
   */
  public int addY(final int y) {
    int _y = this.y;
    return this.y = (_y + y);
  }

  /**
   * Clamps this tuple to the range [low, high].
   *  @param min  the lowest value in this tuple after clamping
   *  @param max  the highest value in this tuple after clamping
   */
  public int clamp(final int min, final int max) {
    int _xblockexpression = (int) 0;
    {
      if ((this.x < min)) {
        this.x = min;
      } else {
        if ((this.x > max)) {
          this.x = max;
        }
      }
      int _xifexpression = (int) 0;
      if ((this.y < min)) {
        _xifexpression = this.y = min;
      } else {
        int _xifexpression_1 = (int) 0;
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
  public int clampMin(final int min) {
    int _xblockexpression = (int) 0;
    {
      if ((this.x < min)) {
        this.x = min;
      }
      int _xifexpression = (int) 0;
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
  public int clampMax(final int max) {
    int _xblockexpression = (int) 0;
    {
      if ((this.x > max)) {
        this.x = max;
      }
      int _xifexpression = (int) 0;
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
  public int get(final T t) {
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
      t[0] = this.x;
      _xblockexpression = t[1] = this.y;
    }
    return _xblockexpression;
  }

  /**
   * Sets the value of this tuple to the negation of tuple t1.
   * @param t1 the source tuple
   */
  public int negate(final T t1) {
    int _xblockexpression = (int) 0;
    {
      this.x = (-t1.x);
      _xblockexpression = this.y = (-t1.y);
    }
    return _xblockexpression;
  }

  /**
   * Negates the value of this tuple in place.
   */
  public int negate() {
    int _xblockexpression = (int) 0;
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
  public int scale(final int s, final T t1) {
    int _xblockexpression = (int) 0;
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
  public int scale(final int s) {
    int _xblockexpression = (int) 0;
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
  public int set(final Tuple2i<?> t1) {
    int _xblockexpression = (int) 0;
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
  public int set(final int x, final int y) {
    int _xblockexpression = (int) 0;
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
  public int set(final int[] t) {
    int _xblockexpression = (int) 0;
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
  public int getX() {
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
   * Get the <i>y</i> coordinate.
   * 
   * @return  the <i>y</i> coordinate.
   */
  @Pure
  public int getY() {
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
   * Sets the value of this tuple to the difference of itself and x and y.
   * @param x
   * @param y
   */
  public int sub(final int x, final int y) {
    int _xblockexpression = (int) 0;
    {
      int _x = this.x;
      this.x = (_x - x);
      int _y = this.y;
      _xblockexpression = this.y = (_y - y);
    }
    return _xblockexpression;
  }

  /**
   * Sets the x value of this tuple to the difference of itself and x.
   * @param x
   */
  public int subX(final int x) {
    int _x = this.x;
    return this.x = (_x - x);
  }

  /**
   * Sets the y value of this tuple to the difference of itself and y.
   * @param y
   */
  public int subY(final int y) {
    int _y = this.y;
    return this.y = (_y - y);
  }

  /**
   * Linearly interpolates between tuples t1 and t2 and places the
   *  result into this tuple:  this = (1-alpha)*t1 + alpha*t2.
   *  @param t1  the first tuple
   *  @param t2  the second tuple
   *  @param alpha  the alpha interpolation parameter
   */
  public int interpolate(final T t1, final T t2, final float alpha) {
    int _xblockexpression = (int) 0;
    {
      this.x = ((int) (((1f - alpha) * t1.x) + (alpha * t2.x)));
      _xblockexpression = this.y = ((int) (((1f - alpha) * t1.y) + (alpha * t2.y)));
    }
    return _xblockexpression;
  }

  /**
   * Linearly interpolates between this tuple and tuple t1 and
   *  places the result into this tuple:  this = (1-alpha)*this + alpha*t1.
   *  @param t1  the first tuple
   *  @param alpha  the alpha interpolation parameter
   */
  public int interpolate(final T t1, final float alpha) {
    int _xblockexpression = (int) 0;
    {
      this.x = ((int) (((1f - alpha) * this.x) + (alpha * t1.x)));
      _xblockexpression = this.y = ((int) (((1f - alpha) * this.y) + (alpha * t1.y)));
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
  public boolean equals(final Tuple2i<?> t1) {
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
   * Returns true if the Object t1 is of type Tuple2f and all of the
   * data members of t1 are equal to the corresponding data members in
   * this Tuple2f.
   * @param t1  the object with which the comparison is made
   * @return  true or false
   */
  @Pure
  public boolean equals(final Object t1) {
    try {
      T t2 = ((T) t1);
      return ((this.x == t2.x) && (this.y == t2.y));
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
    boolean _isNaN = Float.isNaN(diff);
    if (_isNaN) {
      return false;
    }
    float _abs = Math.abs(diff);
    if ((_abs > epsilon)) {
      return false;
    }
    diff = (this.y - t1.y);
    boolean _isNaN_1 = Float.isNaN(diff);
    if (_isNaN_1) {
      return false;
    }
    float _abs_1 = Math.abs(diff);
    if ((_abs_1 > epsilon)) {
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
    int _abs = Math.abs(this.x);
    if ((_abs > epsilon)) {
      return false;
    }
    int _abs_1 = Math.abs(this.y);
    if ((_abs_1 > epsilon)) {
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
  @Pure
  public int hashCode() {
    return Objects.hashCode(Integer.valueOf(this.x), Integer.valueOf(this.y));
  }

  @Pure
  public String toString() {
    return (((("(" + Integer.valueOf(this.x)) + ";") + Integer.valueOf(this.y)) + ")");
  }

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
  private static final long serialVersionUID = -2022305507L;
}
