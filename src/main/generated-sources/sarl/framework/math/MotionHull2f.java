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
import java.lang.ref.SoftReference;
import java.util.Objects;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;

/**
 * 2D Path with floating-point values.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
@SarlSpecification("0.15")
@SarlElementType(10)
@XbaseGenerated
@SuppressWarnings("all")
public class MotionHull2f extends Shape2f<MotionHull2f> {
  private final Point2f start = new Point2f();

  private final Vector2f direction = new Vector2f();

  private final float size;

  private SoftReference<Rectangle2f> bounds = null;

  /**
   * @param point
   * @param vector
   * @param lateralSize
   */
  public MotionHull2f(final Point2f point, final Vector2f vector, final float lateralSize) {
    this.start.set(point);
    this.direction.set(vector);
    this.size = lateralSize;
  }

  @Override
  @Pure
  public boolean equals(final Object obj) {
    if ((obj instanceof MotionHull2f)) {
      return ((Objects.equals(this.start, ((MotionHull2f)obj).start) && this.direction.operator_equals(((MotionHull2f)obj).direction)) && (this.size == ((MotionHull2f)obj).size));
    }
    return false;
  }

  @Override
  @Pure
  public int hashCode() {
    return com.google.common.base.Objects.hashCode(this.start, this.direction, Float.valueOf(this.size));
  }

  /**
   * Replies a copy of the start point of this path.
   * 
   * @return the start point.
   */
  @Pure
  public Point2f getStart() {
    return this.start.clone();
  }

  /**
   * Replies a copy of the direction of this path.
   * 
   * @return the upper point.
   */
  @Pure
  public Vector2f getDirection() {
    return this.direction.clone();
  }

  @Override
  @Pure
  public String toString() {
    String _string = this.start.toString();
    String _string_1 = this.direction.toString();
    return (((("[" + _string) + "-") + _string_1) + "]");
  }

  /**
   * Replies if an intersection exists between this rectangle and the given shape.
   * 
   * @param s - the shape.
   * @return <code>true</code> if an intersection exists.
   */
  public boolean intersects(final Shape2f<?> s) {
    if ((s instanceof MotionHull2f)) {
      Point2f end = this.start.operator_plus(this.direction);
      Point2f end2 = ((MotionHull2f)s).start.operator_plus(((MotionHull2f)s).direction);
      float d = MathUtil.distanceSegmentToSegment(this.start, end, ((MotionHull2f)s).start, end2);
      return (d < (this.size + ((MotionHull2f)s).size));
    }
    if ((s instanceof Rectangle2f)) {
      Point2f end_1 = this.start.operator_plus(this.direction);
      Point2f pa = new Point2f(((Rectangle2f)s).getLower().x, ((Rectangle2f)s).getUpper().y);
      Point2f pb = new Point2f(((Rectangle2f)s).getUpper().x, ((Rectangle2f)s).getLower().y);
      float d1 = MathUtil.distanceSegmentToSegment(((Rectangle2f)s).getLower(), pa, this.start, end_1);
      float d2 = MathUtil.distanceSegmentToSegment(pa, ((Rectangle2f)s).getUpper(), this.start, end_1);
      float d3 = MathUtil.distanceSegmentToSegment(((Rectangle2f)s).getUpper(), pb, this.start, end_1);
      float d4 = MathUtil.distanceSegmentToSegment(pb, ((Rectangle2f)s).getLower(), this.start, end_1);
      float d_1 = MathUtil.min(d1, d2, d3, d4);
      return (d_1 < this.size);
    }
    if ((s instanceof Circle2f)) {
      Point2f center = ((Circle2f)s).getCenter();
      Point2f end_2 = this.start.operator_plus(this.direction);
      float _distancePointToSegment = MathUtil.distancePointToSegment(center, this.start, end_2);
      float _radius = ((Circle2f)s).getRadius();
      return (_distancePointToSegment < (this.size + _radius));
    }
    throw new IllegalArgumentException();
  }

  /**
   * Replies the center point of the rectangle.
   * 
   * @return the center point.
   */
  @Pure
  public Point2f getCenter() {
    return new Point2f(
      (this.start.x + (this.direction.x / 2f)), 
      (this.start.y + (this.direction.y / 2f)));
  }

  @Override
  public MotionHull2f translate(final Tuple2f<?> vector) {
    Point2f _point2f = new Point2f(
      (this.start.x + vector.x), 
      (this.start.y + vector.y));
    Vector2f _clone = this.direction.clone();
    return new MotionHull2f(_point2f, _clone, 
      this.size);
  }

  @Override
  @Pure
  public Rectangle2f getBounds() {
    SoftReference<Rectangle2f> _bounds = this.bounds;
    Rectangle2f _get = null;
    if (_bounds!=null) {
      _get=_bounds.get();
    }
    Rectangle2f bb = _get;
    if ((bb == null)) {
      float x = (this.start.x + this.direction.x);
      float y = (this.start.y + this.direction.y);
      Vector2f d = this.direction.clone();
      d.turn((MathUtil.PI / 2f));
      float x1 = (this.start.x + d.x);
      float y1 = (this.start.y + d.y);
      float x2 = (this.start.x - d.x);
      float y2 = (this.start.y - d.y);
      float x3 = (x + d.x);
      float y3 = (y + d.y);
      float x4 = (x - d.x);
      float y4 = (y - d.y);
      float _min = MathUtil.min(x1, x2, x3, x4);
      float _min_1 = MathUtil.min(y1, y2, y3, y4);
      float _max = MathUtil.max(x1, x2, x3, x4);
      float _max_1 = MathUtil.max(y1, y2, y3, y4);
      Rectangle2f _rectangle2f = new Rectangle2f(_min, _min_1, _max, _max_1);
      bb = _rectangle2f;
      SoftReference<Rectangle2f> _softReference = new SoftReference<Rectangle2f>(bb);
      this.bounds = _softReference;
    }
    return bb;
  }

  @Override
  @Pure
  public float getMaxDemiSize() {
    return this.size;
  }

  @Override
  @Pure
  @SyntheticMember
  public MotionHull2f clone() {
    try {
      return (MotionHull2f) super.clone();
    } catch (Throwable exception) {
      throw new Error(exception);
    }
  }

  @SyntheticMember
  private static final long serialVersionUID = 9792773856L;
}
