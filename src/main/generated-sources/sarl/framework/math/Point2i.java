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
import org.eclipse.xtext.xbase.lib.Inline;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;

/**
 * 2D Point with 2 integer numbers.
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
public class Point2i extends Tuple2i<Point2i> {
  /**
   * Computes the square of the distance between this point and point p1.
   * @param p1 the other point
   * @return the distance.
   */
  @Pure
  public float distanceSquared(final Point2i p1) {
    float dx = 0;
    float dy = 0;
    dx = (this.x - p1.x);
    dy = (this.y - p1.y);
    return ((dx * dx) + (dy * dy));
  }

  /**
   * Computes the distance between this point and point p1.
   * @param p1 the other point
   * @return the distance.
   */
  @Pure
  public float distance(final Point2i p1) {
    float dx = 0;
    float dy = 0;
    dx = (this.x - p1.x);
    dy = (this.y - p1.y);
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
  public float distanceL1(final Point2i p1) {
    int _abs = Math.abs((this.x - p1.x));
    int _abs_1 = Math.abs((this.y - p1.y));
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
  public float distanceLinf(final Point2i p1) {
    return Math.max(Math.abs((this.x - p1.x)), Math.abs((this.y - p1.y)));
  }

  /**
   * Sets the value of this tuple to the sum of tuples t1 and t2.
   * @param t1 the first tuple
   * @param t2 the second tuple
   */
  public int add(final Point2i t1, final Vector2i t2) {
    int _xblockexpression = (int) 0;
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
  public int add(final Vector2i t1, final Point2i t2) {
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
  public int scaleAdd(final int s, final Vector2i t1, final Point2i t2) {
    int _xblockexpression = (int) 0;
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
  public int scaleAdd(final float s, final Vector2i t1, final Point2i t2) {
    int _xblockexpression = (int) 0;
    {
      this.x = ((int) ((s * t1.x) + t2.x));
      _xblockexpression = this.y = ((int) ((s * t1.y) + t2.y));
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
  public int scaleAdd(final int s, final Point2i t1, final Vector2i t2) {
    int _xblockexpression = (int) 0;
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
  public int scaleAdd(final float s, final Point2i t1, final Vector2i t2) {
    int _xblockexpression = (int) 0;
    {
      this.x = ((int) ((s * t1.x) + t2.x));
      _xblockexpression = this.y = ((int) ((s * t1.y) + t2.y));
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
   * Sets the value of this tuple to the scalar multiplication
   * of itself and then adds tuple t1 (this = s*this + t1).
   * @param s the scalar value
   * @param t1 the tuple to be added
   */
  public int scaleAdd(final float s, final Vector2i t1) {
    int _xblockexpression = (int) 0;
    {
      this.x = ((int) ((s * this.x) + t1.x));
      _xblockexpression = this.y = ((int) ((s * this.y) + t1.y));
    }
    return _xblockexpression;
  }

  /**
   * Sets the value of this tuple to the difference
   * of tuples t1 and t2 (this = t1 - t2).
   * @param t1 the first tuple
   * @param t2 the second tuple
   */
  public int sub(final Point2i t1, final Vector2i t2) {
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
   * Sum of vectors: r = this + v.
   * 
   * @param v the vector
   * @return the result.
   * @see #add(Point2i, Vector2f)
   */
  @Pure
  public Point2i operator_plus(final Vector2i v) {
    Point2i r = new Point2i();
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
  @Inline(value = "$1.add($2)")
  public int operator_add(final Vector2i v) {
    return this.add(v);
  }

  /**
   * Subtract of vectors: r = this - v.
   * 
   * @param v the vector
   * @return the result.
   * @see #sub(Point2i, Vector2f)
   */
  @Pure
  public Point2i operator_minus(final Vector2i v) {
    Point2i r = new Point2i();
    r.sub(this, v);
    return r;
  }

  /**
   * Compute a vectors: r = this - p.
   * 
   * @param p the point
   * @return the vector from the p to this.
   * @see Vector2f#sub(Point2i, Point2i)
   */
  @Pure
  public Vector2i operator_minus(final Point2i p) {
    Vector2i r = new Vector2i();
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
  @Inline(value = "$1.sub($2)")
  public int operator_remove(final Vector2i v) {
    return this.sub(v);
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
   * Replies if the distance between this and v
   * 
   * @param v the vector.
   * @return the distance.
   * @see #distance(Point2i)
   */
  @Pure
  @Inline(value = "$1.distance($2)")
  public float operator_upTo(final Point2i v) {
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
  public Point2i operator_elvis(final Point2i v) {
    boolean _epsilonNul = this.epsilonNul(MathUtil.EPSILON);
    if (_epsilonNul) {
      return v;
    }
    return this;
  }

  @Override
  @Pure
  @SyntheticMember
  public Point2i clone() {
    try {
      return (Point2i) super.clone();
    } catch (Throwable exception) {
      throw new Error(exception);
    }
  }

  @SyntheticMember
  public Point2i() {
    super();
  }

  /**
   * @param tuple is the tuple to copy.
   */
  @SyntheticMember
  public Point2i(final Tuple2i<?> tuple) {
    super(tuple);
  }

  /**
   * @param tuple is the tuple to copy.
   */
  @SyntheticMember
  public Point2i(final int[] tuple) {
    super(tuple);
  }

  /**
   * @param x
   * @param y
   */
  @SyntheticMember
  public Point2i(final int x, final int y) {
    super(x, y);
  }

  @SyntheticMember
  private static final long serialVersionUID = 8593701498L;
}
