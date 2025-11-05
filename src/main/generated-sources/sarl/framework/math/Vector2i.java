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

import io.sarl.lang.core.annotation.SarlElementType;
import io.sarl.lang.core.annotation.SarlSpecification;
import io.sarl.lang.core.annotation.SyntheticMember;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Inline;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;

/**
 * 2D Vector with 2 integer values.
 * 
 * Copied from {@link https://github.com/gallandarakhneorg/afc/}.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
@SarlSpecification("0.15")
@SarlElementType(10)
@XbaseGenerated
@SuppressWarnings("all")
public class Vector2i extends Tuple2i<Vector2i> {
  public Vector2i() {
  }

  /**
   * @param tuple is the tuple to copy.
   */
  public Vector2i(final Tuple2i<?> tuple) {
    this(tuple, false);
  }

  /**
   * @param tuple is the tuple to copy.
   * @param normalize
   */
  public Vector2i(final Tuple2i<?> tuple, final boolean normalize) {
    super(tuple);
    if (normalize) {
      try {
        this.normalize();
      } catch (final Throwable _t) {
        if (_t instanceof Throwable) {
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
    }
  }

  /**
   * @param tuple is the tuple to copy.
   */
  public Vector2i(final int[] tuple) {
    this(tuple, false);
  }

  /**
   * @param tuple is the tuple to copy.
   * @param normalize
   */
  public Vector2i(final int[] tuple, final boolean normalize) {
    super(tuple);
    if (normalize) {
      try {
        this.normalize();
      } catch (final Throwable _t) {
        if (_t instanceof Throwable) {
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
    }
  }

  /**
   * @param x
   * @param y
   */
  public Vector2i(final int x, final int y) {
    this(x, y, false);
  }

  /**
   * @param x
   * @param y
   * @param normalize
   */
  public Vector2i(final int x, final int y, final boolean normalize) {
    super(x, y);
    if (normalize) {
      try {
        this.normalize();
      } catch (final Throwable _t) {
        if (_t instanceof Throwable) {
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
    }
  }

  /**
   * Sets the value of this tuple to the sum of tuples t1 and t2.
   * @param t1 the first tuple
   * @param t2 the second tuple
   */
  public int add(final Vector2i t1, final Vector2i t2) {
    int _xblockexpression = (int) 0;
    {
      this.x = (t1.x + t2.x);
      _xblockexpression = this.y = (t1.y + t2.y);
    }
    return _xblockexpression;
  }

  /**
   * Sets the value of this tuple to the sum of itself and t1.
   * @param t1 the other tuple
   */
  public int add(final Vector2i t1) {
    int _xblockexpression = (int) 0;
    {
      int _x = this.x;
      this.x = (_x + t1.x);
      int _y = this.y;
      _xblockexpression = this.y = (_y + t1.y);
    }
    return _xblockexpression;
  }

  /**
   * Sets the value of this tuple to the scalar multiplication
   * of tuple t1 plus tuple t2 (this = s*t1 + t2).
   * @param s the scalar value
   * @param t1 the tuple to be multipled
   * @param t2 the tuple to be added
   */
  public int scaleAdd(final int s, final Vector2i t1, final Vector2i t2) {
    int _xblockexpression = (int) 0;
    {
      this.x = ((s * t1.x) + t2.x);
      _xblockexpression = this.y = ((s * t1.y) + t2.y);
    }
    return _xblockexpression;
  }

  /**
   * Sets the value of this tuple to the scalar multiplication
   * of itself and then adds tuple t1 (this = s*this + t1).
   * @param s the scalar value
   * @param t1 the tuple to be added
   */
  public int scaleAdd(final int s, final Vector2i t1) {
    int _xblockexpression = (int) 0;
    {
      this.x = ((s * this.x) + t1.x);
      _xblockexpression = this.y = ((s * this.y) + t1.y);
    }
    return _xblockexpression;
  }

  /**
   * Sets the value of this tuple to the difference
   * of tuples t1 and t2 (this = t1 - t2).
   * @param t1 the first tuple
   * @param t2 the second tuple
   */
  public int sub(final Vector2i t1, final Vector2i t2) {
    int _xblockexpression = (int) 0;
    {
      this.x = (t1.x - t2.x);
      _xblockexpression = this.y = (t1.y - t2.y);
    }
    return _xblockexpression;
  }

  /**
   * Sets the value of this tuple to the difference
   * of tuples t1 and t2 (this = t1 - t2).
   * @param t1 the first tuple
   * @param t2 the second tuple
   */
  public int sub(final Point2i t1, final Point2i t2) {
    int _xblockexpression = (int) 0;
    {
      this.x = (t1.x - t2.x);
      _xblockexpression = this.y = (t1.y - t2.y);
    }
    return _xblockexpression;
  }

  /**
   * Sets the value of this tuple to the difference
   * of itself and t1 (this = this - t1).
   * @param t1 the other tuple
   */
  public int sub(final Vector2i t1) {
    int _xblockexpression = (int) 0;
    {
      int _x = this.x;
      this.x = (_x - t1.x);
      int _y = this.y;
      _xblockexpression = this.y = (_y - t1.y);
    }
    return _xblockexpression;
  }

  /**
   * Computes the dot product of the this vector and vector v1.
   * @param v1 the other vector
   * @return the dot product.
   */
  @Pure
  public float dot(final Vector2i v1) {
    return ((this.x * v1.x) + (this.y * v1.y));
  }

  /**
   * Change the coordinates of this vector to make it a perpendicular
   * vector to the original coordinates.
   */
  public int perpendicularize() {
    int _y = this.getY();
    return this.set((-_y), this.getX());
  }

  /**
   * Returns the length of this vector.
   * @return the length of this vector
   */
  @Pure
  public float length() {
    double _sqrt = Math.sqrt(((this.x * this.x) + (this.y * this.y)));
    return ((float) _sqrt);
  }

  /**
   * Returns the squared length of this vector.
   * @return the squared length of this vector
   */
  @Pure
  public float lengthSquared() {
    return ((this.x * this.x) + (this.y * this.y));
  }

  /**
   * Set the length of this vector.
   * @param length - the length of this vector
   */
  public int setLength(final float length) {
    int _xifexpression = (int) 0;
    if ((length >= 0f)) {
      int _xblockexpression = (int) 0;
      {
        float norm = ((this.x * this.x) + (this.y * this.y));
        int _xifexpression_1 = (int) 0;
        if ((norm != 0f)) {
          int _xblockexpression_1 = (int) 0;
          {
            double _sqrt = Math.sqrt(norm);
            norm = ((float) _sqrt);
            norm = (length / norm);
            this.x = ((int) (this.x * norm));
            _xblockexpression_1 = this.y = ((int) (this.y * norm));
          }
          _xifexpression_1 = _xblockexpression_1;
        } else {
          int _xblockexpression_2 = (int) 0;
          {
            this.x = ((int) length);
            _xblockexpression_2 = this.y = 0;
          }
          _xifexpression_1 = _xblockexpression_2;
        }
        _xblockexpression = _xifexpression_1;
      }
      _xifexpression = _xblockexpression;
    } else {
      _xifexpression = this.x = (this.y = 0);
    }
    return _xifexpression;
  }

  /**
   * Sets the value of this vector to the normalization of vector v1.
   * @param v1 the un-normalized vector
   */
  public int normalize(final Vector2i v1) {
    int _xblockexpression = (int) 0;
    {
      float _length = v1.length();
      float norm = (1f / _length);
      this.x = ((int) (v1.x * norm));
      _xblockexpression = this.y = ((int) (v1.y * norm));
    }
    return _xblockexpression;
  }

  /**
   * Normalizes this vector in place.
   */
  public int normalize() {
    int _xblockexpression = (int) 0;
    {
      double _sqrt = Math.sqrt(((this.x * this.x) + (this.y * this.y)));
      double norm = (1f / _sqrt);
      this.x = ((int) (this.x * norm));
      _xblockexpression = this.y = ((int) (this.y * norm));
    }
    return _xblockexpression;
  }

  /**
   * Sum of vectors: r = this + v.
   * 
   * @param v the vector
   * @return the result.
   * @see #add(Vector2i, Vector2i)
   */
  @Pure
  public Vector2i operator_plus(final Vector2i v) {
    Vector2i r = new Vector2i();
    r.add(this, v);
    return r;
  }

  /**
   * Add vector to a point: r = this + p.
   * 
   * @param p the point
   * @return the result.
   * @see Point2i#add(Point2i, Vector2i)
   */
  @Pure
  public Point2i operator_plus(final Point2i p) {
    Point2i r = new Point2i();
    r.add(p, this);
    return r;
  }

  /**
   * Sum of vectors: this += v.
   * It is equivalent to {@code this.add(v)}.
   * 
   * @param v the vector
   * @return the result.
   * @see #add(Vector2i)
   */
  @Inline(value = "$1.add($2)")
  public int operator_add(final Vector2i v) {
    return this.add(v);
  }

  /**
   * Subtract of vectors: r = this - v.
   * 
   * @param v the vector
   * @return the result.
   * @see #sub(Vector2i, Vector2i)
   */
  @Pure
  public Vector2i operator_minus(final Vector2i v) {
    Vector2i r = new Vector2i();
    r.sub(this, v);
    return r;
  }

  /**
   * Subtract of vectors: this -= v.
   * It is equivalent to {@code this.sub(v)}.
   * 
   * @param v the vector
   * @return the result.
   * @see #sub(Vector2i)
   */
  @Inline(value = "$1.sub($2)")
  public int operator_remove(final Vector2i v) {
    return this.sub(v);
  }

  /**
   * Negation: r = -this.
   * It is equivalent to {@code this.negate(r)}
   * 
   * @param v the vector
   * @return the result.
   * @see #negate(Vector2i)
   */
  public Vector2i operator_minus() {
    Vector2i r = new Vector2i();
    this.negate(r);
    return r;
  }

  /**
   * Dot product: r = this.v.
   * 
   * @param v the vector
   * @return the result.
   * @see #dot(Vector2i)
   */
  @Pure
  @Inline(value = "$1.dot($2)")
  public float operator_multiply(final Vector2i v) {
    return this.dot(v);
  }

  /**
   * Scale a vector: r = this * f.
   * 
   * @param v the vector
   * @return the scaled vector.
   */
  @Pure
  public Vector2i operator_multiply(final int f) {
    Vector2i r = new Vector2i(this);
    r.scale(f);
    return r;
  }

  /**
   * Scale a vector: r = this / f.
   * 
   * @param v the vector
   * @return the scaled vector.
   */
  @Pure
  public Vector2i operator_divide(final int f) {
    Vector2i r = new Vector2i(this);
    r.scale((1 / f));
    return r;
  }

  /**
   * Replies if the vectors are equal.
   * 
   * @param v the vector.
   * @return test result.
   */
  @Pure
  @Inline(value = "$1.equals($2)")
  public boolean operator_equals(final Vector2i v) {
    return this.equals(v);
  }

  /**
   * Replies if the vectors are not equal.
   * 
   * @param v the vector.
   * @return test result.
   */
  @Pure
  @Inline(value = "!$1.equals($2)")
  public boolean operator_notEquals(final Vector2i v) {
    boolean _equals = this.equals(v);
    return (!_equals);
  }

  /**
   * If the tuple is nul then b else a.
   * 
   * @param v the vector.
   * @return the vector.
   * @see #epsilonNul(float)
   * @see MathUtil#EPSILON
   */
  @Pure
  public Vector2i operator_elvis(final Vector2i v) {
    boolean _epsilonNul = this.epsilonNul(MathUtil.EPSILON);
    if (_epsilonNul) {
      return v;
    }
    return this;
  }

  @Override
  @Pure
  @SyntheticMember
  public Vector2i clone() {
    try {
      return (Vector2i) super.clone();
    } catch (Throwable exception) {
      throw new Error(exception);
    }
  }

  @SyntheticMember
  private static final long serialVersionUID = 3997623167L;
}
