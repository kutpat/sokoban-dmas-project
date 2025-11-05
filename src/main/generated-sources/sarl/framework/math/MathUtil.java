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
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;

/**
 * Mathematic and geometric utilities.
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
public final class MathUtil {
  private MathUtil() {
  }

  /**
   * PI
   */
  public static final float PI = ((float) Math.PI);

  /**
   * E
   */
  public static final float E = ((float) Math.E);

  /**
   * Epsilon value, smallest such that 1.0+EPSILON != 1.0
   * <p>
   * Given by the Java3D's implementation of the Matrix3d class.
   */
  public static final float EPSILON = ((float) 1.110223024E-16);

  /**
   * 2 * PI
   */
  public static final float TWO_PI = ((float) (2.0 * Math.PI));

  /**
   * Compute the signed angle between two vectors.
   * 
   * @param x1
   * @param y1
   * @param x2
   * @param y2
   * @return the angle between <code>-PI</code> and <code>PI</code>.
   */
  @Pure
  public static float signedAngle(final float x1, final float y1, final float x2, final float y2) {
    double _sqrt = Math.sqrt(((x1 * x1) + (y1 * y1)));
    float length1 = ((float) _sqrt);
    double _sqrt_1 = Math.sqrt(((x2 * x2) + (y2 * y2)));
    float length2 = ((float) _sqrt_1);
    if (((length1 == 0f) || (length2 == 0f))) {
      return Float.NaN;
    }
    float cx1 = x1;
    float cy1 = y1;
    float cx2 = x2;
    float cy2 = y2;
    if ((length1 != 1f)) {
      cx1 = (cx1 / length1);
      cy1 = (cy1 / length1);
    }
    if ((length2 != 1f)) {
      cx2 = (cx2 / length2);
      cy2 = (cy2 / length2);
    }
    float cos = ((cx1 * cx2) + (cy1 * cy2));
    float sin = ((cx1 * cy2) - (cy1 * cx2));
    double _atan2 = Math.atan2(sin, cos);
    float angle = ((float) _atan2);
    return angle;
  }

  /**
   * Clamp the given angle in radians to {@code [0;2PI)}.
   * 
   * @param radians is the angle to clamp
   * @return the angle in {@code [0;2PI)} range.
   */
  @Pure
  public static float clampRadian(final float radians) {
    return MathUtil.clampRadian(radians, 0f, MathUtil.TWO_PI);
  }

  /**
   * Clamp the given angle in radians.
   * 
   * @param radians is the angle to clamp
   * @param min is the min value of the range.
   * @param max is the max value of the range.
   * @return the angle in the given range.
   */
  @Pure
  public static float clampRadian(final float radians, final float min, final float max) {
    float r = radians;
    while ((r < min)) {
      r = (r + MathUtil.TWO_PI);
    }
    while ((r >= max)) {
      r = (r - MathUtil.TWO_PI);
    }
    return r;
  }

  /**
   * Clamp the given value to the given range.
   * <p>
   * If the value is outside the {@code [min;max]}
   * range, it is clamp to the nearest bounding value
   * <var>min</var> or <var>max</var>.
   * 
   * @param v is the value to clamp.
   * @param min is the min value of the range.
   * @param max is the max value of the range.
   * @return the value in {@code [min;max]} range.
   */
  @Pure
  public static float clamp(final float v, final float min, final float max) {
    if ((min < max)) {
      if ((v < min)) {
        return min;
      }
      if ((v > max)) {
        return max;
      }
    } else {
      if ((v > min)) {
        return min;
      }
      if ((v < max)) {
        return max;
      }
    }
    return v;
  }

  /**
   * Replies the min value in the given values.
   * 
   * @return the min value.
   */
  @Pure
  public static float min(final float... values) {
    float min = values[0];
    int _length = values.length;
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(1, _length, true);
    for (final Integer i : _doubleDotLessThan) {
      float _get = values[((i) == null ? 0 : (i).intValue())];
      if ((min > _get)) {
        min = values[((i) == null ? 0 : (i).intValue())];
      }
    }
    return min;
  }

  /**
   * Replies the max value in the given values.
   * 
   * @return the max value.
   */
  @Pure
  public static float max(final float... values) {
    float max = values[0];
    int _length = values.length;
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(1, _length, true);
    for (final Integer i : _doubleDotLessThan) {
      float _get = values[((i) == null ? 0 : (i).intValue())];
      if ((max < _get)) {
        max = values[((i) == null ? 0 : (i).intValue())];
      }
    }
    return max;
  }

  /**
   * Replies the projection a point on a segment.
   * 
   * @param p is the coordinate of the point to project
   * @param s1 is the x-coordinate of the first line point.
   * @param s2 is the x-coordinate of the second line point.
   * @return the projection of the specified point on the line. If
   * equal to {@code 0}, the projection is equal to the first segment point.
   * If equal to {@code 1}, the projection is equal to the second segment point.
   * If inside {@code ]0;1[}, the projection is between the two segment points.
   * If inside {@code ]-inf;0[}, the projection is outside on the side of the
   * first segment point. If inside {@code ]1;+inf[}, the projection is
   * outside on the side of the second segment point.
   */
  @Pure
  public static float projectsPointOnLine(final Point2f p, final Point2f s1, final Point2f s2) {
    float r_numerator = (((p.x - s1.x) * (s2.x - s1.x)) + ((p.y - s1.y) * (s2.y - s1.y)));
    float r_denomenator = (((s2.x - s1.x) * (s2.x - s1.x)) + ((s2.y - s1.y) * (s2.y - s1.y)));
    return (r_numerator / r_denomenator);
  }

  @Pure
  private static float determinant(final Tuple2f<?> a, final Tuple2f<?> b) {
    return ((a.x * b.y) - (b.x * a.y));
  }

  /**
   * Replies one position factor for the intersection point between two lines.
   * <p>
   * Let line equations for L1 and L2:<br>
   * <code>L1: P1 + factor1 * (P2-P1)</code><br>
   * <code>L2: P3 + factor2 * (P4-P3)</code><br>
   * If lines are intersecting, then<br>
   * <code>P1 + factor1 * (P2-P1) = P3 + factor2 * (P4-P3)</code>
   * <p>
   * This function computes and replies <code>factor1</code>.
   * 
   * @param p1
   *            is the coordinates of the first point of the first segment.
   * @param p2
   *            is the coordinates of the second point of the first segment.
   * @param p3
   *            is the coordinates of the first point of the second segment.
   * @param p4
   *            is the coordinates of the second point of the second segment.
   * @return <code>factor1</code> or {@link Float#NaN} if no intersection.
   */
  @Pure
  public static float getSegmentSegmentIntersectionFactor(final Point2f p1, final Point2f p2, final Point2f p3, final Point2f p4) {
    Vector2f v1 = p2.operator_minus(p1);
    Vector2f v2 = p4.operator_minus(p3);
    float det = MathUtil.determinant(v1, v2);
    if ((det == 0f)) {
      return Float.NaN;
    }
    Vector2f v3 = p1.operator_minus(p3);
    float _determinant = MathUtil.determinant(v1, v3);
    float u = (_determinant / det);
    if (((u < 0.0) || (u > 1.0))) {
      return Float.NaN;
    }
    float _determinant_1 = MathUtil.determinant(v2, v3);
    u = (_determinant_1 / det);
    float _xifexpression = (float) 0;
    if (((u < 0.0) || (u > 1.0))) {
      _xifexpression = Float.NaN;
    } else {
      _xifexpression = u;
    }
    return _xifexpression;
  }

  /**
   * Compute the distance between a point and a segment.
   * 
   * @param p position of the point.
   * @param s1 position of the first point of the segment.
   * @param s2 position of the second point of the segment.
   * @return the distance beetween the point and the segment.
   */
  @Pure
  public static float distancePointToSegment(final Point2f p, final Point2f s1, final Point2f s2) {
    float r_denomenator = (((s2.x - s1.x) * (s2.x - s1.x)) + ((s2.y - s1.y) * (s2.y - s1.y)));
    if ((r_denomenator == 0f)) {
      return p.distance(s1);
    }
    float r_numerator = (((p.x - s1.x) * (s2.x - s1.x)) + ((p.y - s1.y) * (s2.y - s1.y)));
    float ratio = (r_numerator / r_denomenator);
    if ((ratio <= 0f)) {
      double _sqrt = Math.sqrt((((p.x - s1.x) * (p.x - s1.x)) + ((p.y - s1.y) * (p.y - s1.y))));
      return ((float) _sqrt);
    }
    if ((ratio >= 1f)) {
      double _sqrt_1 = Math.sqrt((((p.x - s2.x) * (p.x - s2.x)) + ((p.y - s2.y) * (p.y - s2.y))));
      return ((float) _sqrt_1);
    }
    float s = ((((s1.y - p.y) * (s2.x - s1.x)) - ((s1.x - p.x) * (s2.y - s1.y))) / r_denomenator);
    float _abs = Math.abs(s);
    double _sqrt_2 = Math.sqrt(r_denomenator);
    return ((float) (_abs * _sqrt_2));
  }

  /**
   * Compute the distance between two segments.
   * 
   * @param p position of the point.
   * @param s1 position of the first point of the segment.
   * @param s2 position of the second point of the segment.
   * @return the distance beetween the segments.
   */
  @Pure
  public static float distanceSegmentToSegment(final Point2f s1, final Point2f s2, final Point2f s3, final Point2f s4) {
    float f = MathUtil.getSegmentSegmentIntersectionFactor(s1, s2, s3, s4);
    boolean _isNaN = Float.isNaN(f);
    if ((!_isNaN)) {
      return 0f;
    }
    float d1 = MathUtil.distancePointToSegment(s1, s3, s4);
    float d2 = MathUtil.distancePointToSegment(s2, s3, s4);
    float d3 = MathUtil.distancePointToSegment(s3, s1, s2);
    float d4 = MathUtil.distancePointToSegment(s4, s1, s2);
    return MathUtil.min(d1, d2, d3, d4);
  }
}
