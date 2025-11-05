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
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;

/**
 * 2D Point with 2 floating-point numbers.
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
public class Point2f extends Tuple2f<Point2f> {
  /**
   * Computes the square of the distance between this point and point p1.
   * @param p1 the other point
   * @return the distance.
   */
  @Pure
  public float distanceSquared(final Point2f p1) {
    float dx = (this.x - p1.x);
    float dy = (this.y - p1.y);
    return ((dx * dx) + (dy * dy));
  }

  /**
   * Computes the distance between this point and point p1.
   * @param p1 the other point
   * @return the distance.
   */
  @Pure
  public float distance(final Point2f p1) {
    float dx = (this.x - p1.x);
    float dy = (this.y - p1.y);
    double _sqrt = Math.sqrt(((dx * dx) + (dy * dy)));
    return ((float) _sqrt);
  }

  /**
   * Computes the L-1 (Manhattan) distance between this point and
   * point p1.  The L-1 distance is equal to abs(x1-x2) + abs(y1-y2).
   * @param p1 the other point
   * @return the distance.
   */
  @Pure
  public float distanceL1(final Point2f p1) {
    float _abs = Math.abs((this.x - p1.x));
    float _abs_1 = Math.abs((this.y - p1.y));
    return (_abs + _abs_1);
  }

  /**
   * Computes the L-infinite distance between this point and
   * point p1.  The L-infinite distance is equal to
   * MAX[abs(x1-x2), abs(y1-y2)].
   * @param p1 the other point
   * @return the distance.
   */
  @Pure
  public float distanceLinf(final Point2f p1) {
    return Math.max(Math.abs((this.x - p1.x)), Math.abs((this.y - p1.y)));
  }

  /**
   * Sets the value of this tuple to the sum of tuples t1 and t2.
   * @param t1 the first tuple
   * @param t2 the second tuple
   */
  public float add(final Point2f t1, final Vector2f t2) {
    float _xblockexpression = (float) 0;
    {
      this.x = (t1.x + t2.x);
      _xblockexpression = this.y = (t1.y + t2.y);
    }
    return _xblockexpression;
  }

  /**
   * Sets the value of this tuple to the sum of tuples t1 and t2.
   * @param t1 the first tuple
   * @param t2 the second tuple
   */
  public float add(final Vector2f t1, final Point2f t2) {
    float _xblockexpression = (float) 0;
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
  public float add(final Vector2f t1) {
    float _xblockexpression = (float) 0;
    {
      float _x = this.x;
      this.x = (_x + t1.x);
      float _y = this.y;
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
  public float scaleAdd(final int s, final Vector2f t1, final Point2f t2) {
    float _xblockexpression = (float) 0;
    {
      this.x = ((s * t1.x) + t2.x);
      _xblockexpression = this.y = ((s * t1.y) + t2.y);
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
  public float scaleAdd(final float s, final Vector2f t1, final Point2f t2) {
    float _xblockexpression = (float) 0;
    {
      this.x = ((s * t1.x) + t2.x);
      _xblockexpression = this.y = ((s * t1.y) + t2.y);
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
  public float scaleAdd(final int s, final Point2f t1, final Vector2f t2) {
    float _xblockexpression = (float) 0;
    {
      this.x = ((s * t1.x) + t2.x);
      _xblockexpression = this.y = ((s * t1.y) + t2.y);
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
  public float scaleAdd(final float s, final Point2f t1, final Vector2f t2) {
    float _xblockexpression = (float) 0;
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
  public float scaleAdd(final int s, final Vector2f t1) {
    float _xblockexpression = (float) 0;
    {
      this.x = ((s * this.x) + t1.x);
      _xblockexpression = this.y = ((s * this.y) + t1.y);
    }
    return _xblockexpression;
  }

  /**
   * Sets the value of this tuple to the scalar multiplication
   * of itself and then adds tuple t1 (this = s*this + t1).
   * @param s the scalar value
   * @param t1 the tuple to be added
   */
  public float scaleAdd(final float s, final Vector2f t1) {
    float _xblockexpression = (float) 0;
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
  public float sub(final Point2f t1, final Vector2f t2) {
    float _xblockexpression = (float) 0;
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
  public float sub(final Vector2f t1) {
    float _xblockexpression = (float) 0;
    {
      float _x = this.x;
      this.x = (_x - t1.x);
      float _y = this.y;
      _xblockexpression = this.y = (_y - t1.y);
    }
    return _xblockexpression;
  }

  /**
   * Sum of vectors: r = this + v.
   * 
   * @param v the vector
   * @return the result.
   * @see #add(Point2f, Vector2f)
   */
  public Point2f operator_plus(final Vector2f v) {
    Point2f r = new Point2f();
    r.add(this, v);
    return r;
  }

  /**
   * Sum of vectors: this += v.
   * It is equivalent to {@code this.add(v)}.
   * 
   * @param v the vector
   * @return the result.
   * @see #add(Vector2f)
   */
  public float operator_add(final Vector2f v) {
    return this.add(v);
  }

  /**
   * Subtract of vectors: r = this - v.
   * 
   * @param v the vector
   * @return the result.
   * @see #sub(Point2f, Vector2f)
   */
  public Point2f operator_minus(final Vector2f v) {
    Point2f r = new Point2f();
    r.sub(this, v);
    return r;
  }

  /**
   * Compute a vectors: r = this - p.
   * 
   * @param p the point
   * @return the vector from the p to this.
   * @see Vector2f#sub(Point2f, Point2f)
   */
  public Vector2f operator_minus(final Point2f p) {
    Vector2f r = new Vector2f();
    r.sub(this, p);
    return r;
  }

  /**
   * Subtract of vectors: this -= v.
   * It is equivalent to {@code this.sub(v)}.
   * 
   * @param v the vector
   * @return the result.
   * @see #sub(Vector2f)
   */
  public float operator_remove(final Vector2f v) {
    return this.sub(v);
  }

  /**
   * Replies if the vectors are equal.
   * 
   * @param v the vector.
   * @return test result.
   */
  @Pure
  public boolean operator_equals(final Vector2f v) {
    return this.equals(v);
  }

  /**
   * Replies if the vectors are not equal.
   * 
   * @param v the vector.
   * @return test result.
   */
  @Pure
  public boolean operator_notEquals(final Vector2f v) {
    boolean _equals = this.equals(v);
    return (!_equals);
  }

  /**
   * Replies if the distance between this and v
   * 
   * @param v the vector.
   * @return the distance.
   * @see #distance(Point2f)
   */
  @Pure
  public float operator_upTo(final Point2f v) {
    return this.distance(v);
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
  public Point2f operator_elvis(final Point2f v) {
    Point2f _xifexpression = null;
    boolean _epsilonNul = this.epsilonNul(MathUtil.EPSILON);
    if (_epsilonNul) {
      _xifexpression = v;
    } else {
      _xifexpression = this;
    }
    return _xifexpression;
  }

  @Override
  @Pure
  @SyntheticMember
  public Point2f clone() {
    try {
      return (Point2f) super.clone();
    } catch (Throwable exception) {
      throw new Error(exception);
    }
  }

  @SyntheticMember
  public Point2f() {
    super();
  }

  /**
   * @param tuple is the tuple to copy.
   */
  @SyntheticMember
  public Point2f(final double[] tuple) {
    super(tuple);
  }

  /**
   * @param tuple is the tuple to copy.
   */
  @SyntheticMember
  public Point2f(final float[] tuple) {
    super(tuple);
  }

  /**
   * @param tuple is the tuple to copy.
   */
  @SyntheticMember
  public Point2f(final Tuple2f<?> tuple) {
    super(tuple);
  }

  /**
   * @param tuple is the tuple to copy.
   */
  @SyntheticMember
  public Point2f(final int[] tuple) {
    super(tuple);
  }

  /**
   * @param tuple is the tuple to copy.
   */
  @SyntheticMember
  public Point2f(final long[] tuple) {
    super(tuple);
  }

  /**
   * @param x
   * @param y
   */
  @SyntheticMember
  public Point2f(final double x, final double y) {
    super(x, y);
  }

  /**
   * @param x
   * @param y
   */
  @SyntheticMember
  public Point2f(final float x, final float y) {
    super(x, y);
  }

  /**
   * @param x
   * @param y
   */
  @SyntheticMember
  public Point2f(final int x, final int y) {
    super(x, y);
  }

  /**
   * @param x
   * @param y
   */
  @SyntheticMember
  public Point2f(final long x, final long y) {
    super(x, y);
  }

  @SyntheticMember
  private static final long serialVersionUID = 8593679085L;
}
