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

import io.sarl.lang.core.annotation.DefaultValue;
import io.sarl.lang.core.annotation.DefaultValueSource;
import io.sarl.lang.core.annotation.DefaultValueUse;
import io.sarl.lang.core.annotation.SarlElementType;
import io.sarl.lang.core.annotation.SarlSourceCode;
import io.sarl.lang.core.annotation.SarlSpecification;
import io.sarl.lang.core.annotation.SyntheticMember;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;

/**
 * 2D Vector with 2 floating-point values.
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
public class Vector2f extends Tuple2f<Vector2f> {
  public Vector2f() {
    super();
  }

  /**
   * @param tuple is the tuple to copy.
   * @param normalize indicate if the vector should be normalize at init.
   */
  @DefaultValueSource
  public Vector2f(final Tuple2f<?> tuple, @DefaultValue("framework.math.Vector2f#NEW_0") final boolean normalize) {
    super(tuple);
    if (normalize) {
      this.safeNormalize();
    }
  }

  /**
   * @param tuple is the tuple to copy.
   * @param normalize indicate if the vector should be normalize at init.
   */
  @DefaultValueSource
  public Vector2f(final int[] tuple, @DefaultValue("framework.math.Vector2f#NEW_1") final boolean normalize) {
    super(tuple);
    if (normalize) {
      this.safeNormalize();
    }
  }

  /**
   * @param tuple is the tuple to copy.
   * @param normalize indicate if the vector should be normalize at init.
   */
  @DefaultValueSource
  public Vector2f(final float[] tuple, @DefaultValue("framework.math.Vector2f#NEW_2") final boolean normalize) {
    super(tuple);
    if (normalize) {
      this.safeNormalize();
    }
  }

  /**
   * @param tuple is the tuple to copy.
   * @param normalize indicate if the vector should be normalize at init.
   */
  @DefaultValueSource
  public Vector2f(final long[] tuple, @DefaultValue("framework.math.Vector2f#NEW_3") final boolean normalize) {
    super(tuple);
    if (normalize) {
      this.safeNormalize();
    }
  }

  /**
   * @param tuple is the tuple to copy.
   * @param normalize indicate if the vector should be normalize at init.
   */
  @DefaultValueSource
  public Vector2f(final double[] tuple, @DefaultValue("framework.math.Vector2f#NEW_4") final boolean normalize) {
    super(tuple);
    if (normalize) {
      this.safeNormalize();
    }
  }

  /**
   * @param x
   * @param y
   * @param normalize indicate if the vector should be normalize at init.
   */
  @DefaultValueSource
  public Vector2f(final int x, final int y, @DefaultValue("framework.math.Vector2f#NEW_5") final boolean normalize) {
    super(x, y);
    if (normalize) {
      this.safeNormalize();
    }
  }

  /**
   * @param x
   * @param y
   * @param normalize indicate if the vector should be normalize at init.
   */
  @DefaultValueSource
  public Vector2f(final float x, final float y, @DefaultValue("framework.math.Vector2f#NEW_6") final boolean normalize) {
    super(x, y);
    if (normalize) {
      this.safeNormalize();
    }
  }

  /**
   * @param x
   * @param y
   * @param normalize indicate if the vector should be normalize at init.
   */
  @DefaultValueSource
  public Vector2f(final long x, final long y, @DefaultValue("framework.math.Vector2f#NEW_7") final boolean normalize) {
    super(x, y);
    if (normalize) {
      this.safeNormalize();
    }
  }

  /**
   * @param x
   * @param y
   * @param normalize indicate if the vector should be normalize at init.
   */
  @DefaultValueSource
  public Vector2f(final double x, final double y, @DefaultValue("framework.math.Vector2f#NEW_8") final boolean normalize) {
    super(x, y);
    if (normalize) {
      this.safeNormalize();
    }
  }

  protected Float safeNormalize() {
    Float _xtrycatchfinallyexpression = null;
    try {
      _xtrycatchfinallyexpression = Float.valueOf(this.normalize());
    } catch (final Throwable _t) {
      if (_t instanceof Throwable) {
        _xtrycatchfinallyexpression = null;
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    return _xtrycatchfinallyexpression;
  }

  /**
   * Sets the value of this tuple to the sum of tuples t1 and t2.
   * @param t1 the first tuple
   * @param t2 the second tuple
   */
  public float add(final Vector2f t1, final Vector2f t2) {
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
  public float scaleAdd(final int s, final Vector2f t1, final Vector2f t2) {
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
  public float caleAdd(final float s, final Vector2f t1, final Vector2f t2) {
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
  public float sub(final Vector2f t1, final Vector2f t2) {
    float _xblockexpression = (float) 0;
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
  public float sub(final Point2f t1, final Point2f t2) {
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
   * Computes the dot product of the this vector and vector v1.
   * @param v1 the other vector
   * @return the dot product.
   */
  @Pure
  public float dot(final Vector2f v1) {
    return ((this.x * v1.x) + (this.y * v1.y));
  }

  /**
   * Change the coordinates of this vector to make it a perpendicular
   * vector to the original coordinates.
   */
  public float perpendicularize() {
    return this.set((-this.y), this.x);
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
  public float setLength(final float length) {
    float _xifexpression = (float) 0;
    if ((length >= 0f)) {
      float _xblockexpression = (float) 0;
      {
        float norm = ((this.x * this.x) + (this.y * this.y));
        float _xifexpression_1 = (float) 0;
        if ((norm != 0f)) {
          float _xblockexpression_1 = (float) 0;
          {
            double _sqrt = Math.sqrt(norm);
            norm = ((float) _sqrt);
            norm = (length / norm);
            float _x = this.x;
            this.x = (_x * norm);
            float _y = this.y;
            _xblockexpression_1 = this.y = (_y * norm);
          }
          _xifexpression_1 = _xblockexpression_1;
        } else {
          float _xblockexpression_2 = (float) 0;
          {
            this.x = length;
            _xblockexpression_2 = this.y = 0f;
          }
          _xifexpression_1 = _xblockexpression_2;
        }
        _xblockexpression = _xifexpression_1;
      }
      _xifexpression = _xblockexpression;
    } else {
      _xifexpression = this.x = (this.y = 0f);
    }
    return _xifexpression;
  }

  /**
   * Create a colinear vector with the given length.
   * 
   * @param length - the length.
   * @return the colinear vector.
   */
  @Pure
  public Vector2f toColinearVector(final float length) {
    Vector2f _xblockexpression = null;
    {
      float x = 0;
      float y = 0;
      if ((length >= 0f)) {
        float norm = ((this.x * this.x) + (this.y * this.y));
        if ((norm != 0f)) {
          double _sqrt = Math.sqrt(norm);
          norm = ((float) _sqrt);
          norm = (length / norm);
          x = (this.x * norm);
          y = (this.y * norm);
        } else {
          x = length;
          y = 0f;
        }
      } else {
        x = (y = 0f);
      }
      _xblockexpression = new Vector2f(x, y);
    }
    return _xblockexpression;
  }

  /**
   * Sets the value of this vector to the normalization of vector v1.
   * @param v1 the un-normalized vector
   */
  public float normalize(final Vector2f v1) {
    float _xblockexpression = (float) 0;
    {
      float _length = v1.length();
      float norm = (1f / _length);
      this.x = (v1.x * norm);
      _xblockexpression = this.y = (v1.y * norm);
    }
    return _xblockexpression;
  }

  /**
   * Normalizes this vector in place.
   */
  public float normalize() {
    float _xblockexpression = (float) 0;
    {
      double _sqrt = Math.sqrt(((this.x * this.x) + (this.y * this.y)));
      float norm = ((float) (1.0 / _sqrt));
      float _x = this.x;
      this.x = (_x * norm);
      float _y = this.y;
      _xblockexpression = this.y = (_y * norm);
    }
    return _xblockexpression;
  }

  /**
   * Returns the angle in radians between this vector and the vector
   *   parameter; the return value is constrained to the range [0,PI].
   *   @param v1    the other vector
   *   @return   the angle in radians in the range [0,PI]
   */
  @Pure
  public float angle(final Vector2f v1) {
    float _xblockexpression = (float) 0;
    {
      float _dot = this.dot(v1);
      float _length = this.length();
      float _length_1 = v1.length();
      double vDot = (_dot / (_length * _length_1));
      if ((vDot < (-1.0))) {
        vDot = (-1.0);
      }
      if ((vDot > 1.0)) {
        vDot = 1.0;
      }
      double _acos = Math.acos(vDot);
      _xblockexpression = ((float) _acos);
    }
    return _xblockexpression;
  }

  /**
   * Compute a signed angle between this vector and the given vector.
   * <p>
   * The signed angle between this vector and {@code v}
   * is the rotation angle to apply to this vector
   * to be colinear to {@code v} and pointing the
   * same demi-plane. It means that the angle replied
   * by this function is be negative if the rotation
   * to apply is clockwise, and positive if
   * the rotation is counterclockwise.
   * <p>
   * The value replied by {@link #angle(Vector2D)}
   * is the absolute value of the vlaue replied by this
   * function.
   * 
   * @param v is the vector to reach.
   * @return the rotation angle to turn this vector to reach
   * {@code v}.
   */
  @Pure
  public float signedAngle(final Vector2f v) {
    return MathUtil.signedAngle(this.x, this.y, v.x, v.y);
  }

  /**
   * Turn this vector about the given rotation angle.
   * 
   * @param angle is the rotation angle in radians.
   */
  public float turn(final float angle) {
    float _xblockexpression = (float) 0;
    {
      double sin = Math.sin(angle);
      double cos = Math.cos(angle);
      double x = ((cos * this.x) + ((-sin) * this.y));
      double y = ((sin * this.x) + (cos * this.y));
      _xblockexpression = this.set(((float) x), ((float) y));
    }
    return _xblockexpression;
  }

  /**
   * Replies the orientation angle on a trigonometric circle
   * that is corresponding to the given direction of this vector.
   * 
   * @return the angle on a trigonometric circle that is corresponding
   * to the given orientation vector.
   */
  @Pure
  public float getOrientationAngle() {
    double angle = Math.acos(this.x);
    if ((this.y < 0f)) {
      angle = (-angle);
    }
    return MathUtil.clampRadian(((float) angle));
  }

  /**
   * Set this vector with the direction of the orientation angle on a trigonometric circle.
   * 
   * @param angle the angle on a trigonometric circle.
   */
  public float setOrientationAngle(final float angle) {
    double _cos = Math.cos(angle);
    double _sin = Math.sin(angle);
    return this.set(((float) _cos), ((float) _sin));
  }

  /**
   * Replies the orientation vector, which is corresponding
   * to the given angle on a trigonometric circle.
   * 
   * @param angle is the angle in radians to translate.
   * @return the orientation vector which is corresponding to the given angle.
   */
  @Pure
  public static Vector2f toOrientationVector(final float angle) {
    double _cos = Math.cos(angle);
    double _sin = Math.sin(angle);
    return new Vector2f(((float) _cos), ((float) _sin));
  }

  /**
   * Sum of vectors: r = this + v.
   * 
   * @param v the vector
   * @return the result.
   * @see #add(Vector2f, Vector2f)
   */
  public Vector2f operator_plus(final Vector2f v) {
    Vector2f r = new Vector2f();
    r.add(this, v);
    return r;
  }

  /**
   * Add vector to a point: r = this + p.
   * 
   * @param p the point
   * @return the result.
   * @see Point2f#add(Point2f, Vector2f)
   */
  public Point2f operator_plus(final Point2f p) {
    Point2f r = new Point2f();
    r.add(p, this);
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
   * @see #sub(Vector2f, Vector2f)
   */
  public Vector2f operator_minus(final Vector2f v) {
    Vector2f r = new Vector2f();
    r.sub(this, v);
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
   * Negation: r = -this.
   * It is equivalent to {@code this.negate(r)}
   * 
   * @param v the vector
   * @return the result.
   * @see #negate(Vector2f)
   */
  public Vector2f operator_minus() {
    Vector2f r = new Vector2f();
    this.negate(r);
    return r;
  }

  /**
   * Dot product: r = this.v.
   * 
   * @param v the vector
   * @return the result.
   * @see #dot(Vector2f)
   */
  @Pure
  public float operator_multiply(final Vector2f v) {
    return this.dot(v);
  }

  /**
   * Scale a vector: r = this * f.
   * 
   * @param v the vector
   * @return the scaled vector.
   */
  public Vector2f operator_multiply(final float f) {
    Vector2f r = new Vector2f(this);
    r.scale(f);
    return r;
  }

  /**
   * Scale a vector: r = this / f.
   * 
   * @param v the vector
   * @return the scaled vector.
   */
  public Vector2f operator_divide(final float f) {
    Vector2f r = new Vector2f(this);
    r.scale((1f / f));
    return r;
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
   * Replies if the absolute angle between this and v
   * 
   * @param v the vector.
   * @return the signed angle.
   * @see #angle(Vector2f)
   */
  @Pure
  public float operator_upTo(final Vector2f v) {
    return this.angle(v);
  }

  /**
   * Replies if the signed angle from this to v
   * 
   * @param v the vector.
   * @return the signed angle.
   * @see #signedAngle(Vector2f)
   */
  @Pure
  public float operator_greaterThanDoubleDot(final Vector2f v) {
    return this.signedAngle(v);
  }

  /**
   * Replies if the signed angle from v to this
   * 
   * @param v the vector.
   * @return the signed angle.
   * @see #signedAngle(Vector2f)
   */
  @Pure
  public float operator_doubleDotLessThan(final Vector2f v) {
    float _signedAngle = this.signedAngle(v);
    return (-_signedAngle);
  }

  /**
   * If the tuple is nul then v else this.
   * 
   * @param v the vector.
   * @return the vector.
   * @see #epsilonNul(float)
   * @see MathUtil#EPSILON
   */
  @Pure
  public Vector2f operator_elvis(final Vector2f v) {
    Vector2f _xifexpression = null;
    boolean _epsilonNul = this.epsilonNul(MathUtil.EPSILON);
    if (_epsilonNul) {
      _xifexpression = v;
    } else {
      _xifexpression = this;
    }
    return _xifexpression;
  }

  /**
   * Default value for the parameter normalize
   */
  @Pure
  @SyntheticMember
  @SarlSourceCode("false")
  private static boolean $DEFAULT_VALUE$NEW_0() {
    return false;
  }

  /**
   * Default value for the parameter normalize
   */
  @Pure
  @SyntheticMember
  @SarlSourceCode("false")
  private static boolean $DEFAULT_VALUE$NEW_1() {
    return false;
  }

  /**
   * Default value for the parameter normalize
   */
  @Pure
  @SyntheticMember
  @SarlSourceCode("false")
  private static boolean $DEFAULT_VALUE$NEW_2() {
    return false;
  }

  /**
   * Default value for the parameter normalize
   */
  @Pure
  @SyntheticMember
  @SarlSourceCode("false")
  private static boolean $DEFAULT_VALUE$NEW_3() {
    return false;
  }

  /**
   * Default value for the parameter normalize
   */
  @Pure
  @SyntheticMember
  @SarlSourceCode("false")
  private static boolean $DEFAULT_VALUE$NEW_4() {
    return false;
  }

  /**
   * Default value for the parameter normalize
   */
  @Pure
  @SyntheticMember
  @SarlSourceCode("false")
  private static boolean $DEFAULT_VALUE$NEW_5() {
    return false;
  }

  /**
   * Default value for the parameter normalize
   */
  @Pure
  @SyntheticMember
  @SarlSourceCode("false")
  private static boolean $DEFAULT_VALUE$NEW_6() {
    return false;
  }

  /**
   * Default value for the parameter normalize
   */
  @Pure
  @SyntheticMember
  @SarlSourceCode("false")
  private static boolean $DEFAULT_VALUE$NEW_7() {
    return false;
  }

  /**
   * Default value for the parameter normalize
   */
  @Pure
  @SyntheticMember
  @SarlSourceCode("false")
  private static boolean $DEFAULT_VALUE$NEW_8() {
    return false;
  }

  /**
   * @optionalparam tuple is the tuple to copy.
   * @optionalparam normalize indicate if the vector should be normalize at init.
   */
  @DefaultValueUse("framework.math.Tuple2f,boolean")
  @SyntheticMember
  public Vector2f(final Tuple2f<?> tuple) {
    this(tuple, $DEFAULT_VALUE$NEW_0());
  }

  /**
   * @optionalparam tuple is the tuple to copy.
   * @optionalparam normalize indicate if the vector should be normalize at init.
   */
  @DefaultValueUse("int[],boolean")
  @SyntheticMember
  public Vector2f(final int[] tuple) {
    this(tuple, $DEFAULT_VALUE$NEW_1());
  }

  /**
   * @optionalparam tuple is the tuple to copy.
   * @optionalparam normalize indicate if the vector should be normalize at init.
   */
  @DefaultValueUse("float[],boolean")
  @SyntheticMember
  public Vector2f(final float[] tuple) {
    this(tuple, $DEFAULT_VALUE$NEW_2());
  }

  /**
   * @optionalparam tuple is the tuple to copy.
   * @optionalparam normalize indicate if the vector should be normalize at init.
   */
  @DefaultValueUse("long[],boolean")
  @SyntheticMember
  public Vector2f(final long[] tuple) {
    this(tuple, $DEFAULT_VALUE$NEW_3());
  }

  /**
   * @optionalparam tuple is the tuple to copy.
   * @optionalparam normalize indicate if the vector should be normalize at init.
   */
  @DefaultValueUse("double[],boolean")
  @SyntheticMember
  public Vector2f(final double[] tuple) {
    this(tuple, $DEFAULT_VALUE$NEW_4());
  }

  /**
   * @optionalparam x @optionalparam y @optionalparam normalize indicate if the vector should be normalize at init.
   */
  @DefaultValueUse("int,int,boolean")
  @SyntheticMember
  public Vector2f(final int x, final int y) {
    this(x, y, $DEFAULT_VALUE$NEW_5());
  }

  /**
   * @optionalparam x @optionalparam y @optionalparam normalize indicate if the vector should be normalize at init.
   */
  @DefaultValueUse("float,float,boolean")
  @SyntheticMember
  public Vector2f(final float x, final float y) {
    this(x, y, $DEFAULT_VALUE$NEW_6());
  }

  /**
   * @optionalparam x @optionalparam y @optionalparam normalize indicate if the vector should be normalize at init.
   */
  @DefaultValueUse("long,long,boolean")
  @SyntheticMember
  public Vector2f(final long x, final long y) {
    this(x, y, $DEFAULT_VALUE$NEW_7());
  }

  /**
   * @optionalparam x @optionalparam y @optionalparam normalize indicate if the vector should be normalize at init.
   */
  @DefaultValueUse("double,double,boolean")
  @SyntheticMember
  public Vector2f(final double x, final double y) {
    this(x, y, $DEFAULT_VALUE$NEW_8());
  }

  @Override
  @Pure
  @SyntheticMember
  public Vector2f clone() {
    try {
      return (Vector2f) super.clone();
    } catch (Throwable exception) {
      throw new Error(exception);
    }
  }

  @SyntheticMember
  private static final long serialVersionUID = 15524333005L;
}
