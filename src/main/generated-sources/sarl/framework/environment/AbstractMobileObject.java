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

import framework.math.MathUtil;
import framework.math.Rectangle2f;
import framework.math.Shape2f;
import framework.math.Vector2f;
import framework.time.TimeManager;
import io.sarl.lang.core.annotation.SarlElementType;
import io.sarl.lang.core.annotation.SarlSpecification;
import io.sarl.lang.core.annotation.SyntheticMember;
import java.util.UUID;
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
public abstract class AbstractMobileObject extends AbstractSituatedObject implements MobileObject {
  private final float maxLinearSpeed;

  private final float maxLinearAcceleration;

  private final float maxAngularSpeed;

  private final float maxAngularAcceleration;

  private float angle = 0f;

  private float currentAngularSpeed = 0f;

  private Vector2f linearMove = new Vector2f();

  /**
   * @param id the identifier of the object.
   * @param shape the shape of the body, considering that it is centered at the (0,0) position.
   * @param maxLinearSpeed is the maximal linear speed.
   * @param maxLinearAcceleration is the maximal linear acceleration.
   * @param maxAngularSpeed is the maximal angular speed.
   * @param maxAngularAcceleration is the maximal angular acceleration.
   */
  public AbstractMobileObject(final UUID id, final Shape2f<?> shape, final float maxLinearSpeed, final float maxLinearAcceleration, final float maxAngularSpeed, final float maxAngularAcceleration) {
    super(id, shape);
    this.maxLinearSpeed = Math.abs(maxLinearSpeed);
    this.maxLinearAcceleration = Math.abs(maxLinearAcceleration);
    this.maxAngularAcceleration = Math.abs(maxAngularAcceleration);
    this.maxAngularSpeed = Math.abs(maxAngularSpeed);
  }

  @Override
  @Pure
  public AbstractMobileObject clone() {
    AbstractSituatedObject _clone = super.clone();
    AbstractMobileObject clone = ((AbstractMobileObject) _clone);
    clone.linearMove = this.linearMove.clone();
    return clone;
  }

  @Override
  @Pure
  public float getAngle() {
    return this.angle;
  }

  @Override
  @Pure
  public Vector2f getDirection() {
    return Vector2f.toOrientationVector(this.angle);
  }

  /**
   * Set the orientation of the object.
   * 
   * @param angle
   */
  protected float setAngle(final float angle) {
    float _xblockexpression = (float) 0;
    {
      this.angle = angle;
      _xblockexpression = this.currentAngularSpeed = 0;
    }
    return _xblockexpression;
  }

  /**
   * Set the direction of the object.
   * 
   * @param dx
   * @param dy
   */
  protected float setDirection(final float dx, final float dy) {
    float _xblockexpression = (float) 0;
    {
      this.angle = new Vector2f(dx, dy).getOrientationAngle();
      _xblockexpression = this.currentAngularSpeed = 0;
    }
    return _xblockexpression;
  }

  @Override
  @Pure
  public float getMaxLinearSpeed() {
    return this.maxLinearSpeed;
  }

  @Override
  @Pure
  public float getMaxAngularSpeed() {
    return this.maxAngularSpeed;
  }

  @Override
  @Pure
  public float getMaxLinearAcceleration() {
    return this.maxLinearAcceleration;
  }

  @Override
  @Pure
  public float getMaxAngularAcceleration() {
    return this.maxAngularAcceleration;
  }

  @Override
  @Pure
  public float getCurrentAngularSpeed() {
    return this.currentAngularSpeed;
  }

  @Override
  @Pure
  public float getCurrentLinearSpeed() {
    return this.linearMove.length();
  }

  @Override
  @Pure
  public Vector2f getCurrentLinearMotion() {
    return this.linearMove.clone();
  }

  /**
   * Rotate the object.
   * 
   * @param rotation is the real instant motion.
   * @param simulationDuration is the time during which the motion is applied.
   */
  protected float rotate(final float rotation, final float simulationDuration) {
    float _xblockexpression = (float) 0;
    {
      class $AssertEvaluator$ {
        final boolean $$result;
        $AssertEvaluator$() {
          boolean _isNaN = Double.isNaN(rotation);
          this.$$result = (!_isNaN);
        }
      }
      assert new $AssertEvaluator$().$$result;
      class $AssertEvaluator$_1 {
        final boolean $$result;
        $AssertEvaluator$_1() {
          boolean _isNaN = Double.isNaN(simulationDuration);
          this.$$result = (!_isNaN);
        }
      }
      assert new $AssertEvaluator$_1().$$result;
      float _angle = this.angle;
      this.angle = (_angle + rotation);
      _xblockexpression = this.currentAngularSpeed = (rotation / simulationDuration);
    }
    return _xblockexpression;
  }

  public void setPosition(final float x, final float y) {
    super.setPosition(x, y);
    this.linearMove.set(0, 0);
  }

  /**
   * Move the situated object.
   * 
   * @param dx is the real instant motion.
   * @param dy is the real instant motion.
   * @param simulationDuration is the time during which the motion is applied.
   * @param worldWidth is the width of the world.
   * @param worldHeight is the height of the world.
   * @return the real motion.
   */
  protected Vector2f move(final float dx, final float dy, final float simulationDuration, final float worldWidth, final float worldHeight) {
    Vector2f r = new Vector2f(dx, dy);
    Shape2f<?> currentShape = this.getShape();
    Shape2f<?> targetShape = currentShape.translate(r);
    Rectangle2f targetBounds = targetShape.getBounds();
    float _x = targetBounds.getLower().getX();
    if ((_x < 0)) {
      float _x_1 = targetBounds.getLower().getX();
      r.addX((-_x_1));
    } else {
      float _x_2 = targetBounds.getUpper().getX();
      if ((_x_2 > worldWidth)) {
        float _x_3 = targetBounds.getUpper().getX();
        r.subX((_x_3 - worldWidth));
      }
    }
    float _y = targetBounds.getLower().getY();
    if ((_y < 0)) {
      float _y_1 = targetBounds.getLower().getY();
      r.addY((-_y_1));
    } else {
      float _y_2 = targetBounds.getUpper().getY();
      if ((_y_2 > worldHeight)) {
        float _y_3 = targetBounds.getUpper().getY();
        r.subY((_y_3 - worldHeight));
      }
    }
    this.addPosition(r.getX(), r.getY());
    if ((simulationDuration > 0)) {
      this.linearMove.set(r.getX(), r.getY());
      float distance = this.linearMove.length();
      if ((distance > 0)) {
        this.linearMove.normalize();
        Vector2f _linearMove = this.linearMove;
        this.linearMove = _linearMove.operator_multiply((distance / simulationDuration));
      }
    } else {
      this.linearMove.set(0, 0);
    }
    return r;
  }

  /**
   * Compute a steering move according to the linear move and to
   * the internal attributes of this object.
   * 
   * @param move is the requested motion, expressed with acceleration.
   * @param clock is the simulation time manager
   * @return the linear instant motion.
   */
  @Pure
  protected Vector2f computeSteeringTranslation(final Vector2f move, final TimeManager clock) {
    float length = move.length();
    Vector2f v = null;
    if ((length != 0f)) {
      float _xifexpression = (float) 0;
      float _multiply = move.operator_multiply(this.linearMove);
      if ((_multiply < 0f)) {
        _xifexpression = (-length);
      } else {
        _xifexpression = length;
      }
      float acceleration = MathUtil.clamp(_xifexpression, 
        (-this.maxLinearAcceleration), 
        this.maxLinearAcceleration);
      float _abs = Math.abs(acceleration);
      acceleration = (_abs / length);
      Vector2f _multiply_1 = move.operator_multiply(acceleration);
      v = _multiply_1;
      float _lastStepDuration = clock.getLastStepDuration();
      v = v.operator_multiply((0.5f * _lastStepDuration));
      v.operator_add(this.linearMove);
    } else {
      v = this.linearMove.clone();
    }
    float _x = v.getX();
    float _x_1 = v.getX();
    float _y = v.getY();
    float _y_1 = v.getY();
    length = ((_x * _x_1) + (_y * _y_1));
    if ((length != 0f)) {
      double _sqrt = Math.sqrt(length);
      length = ((float) _sqrt);
      float _xifexpression_1 = (float) 0;
      float _multiply_2 = v.operator_multiply(this.linearMove);
      if ((_multiply_2 < 0f)) {
        _xifexpression_1 = (-length);
      } else {
        _xifexpression_1 = length;
      }
      float speed = MathUtil.clamp(_xifexpression_1, 
        0f, 
        this.maxLinearSpeed);
      float _lastStepDuration_1 = clock.getLastStepDuration();
      float _abs_1 = Math.abs(speed);
      float factor = ((_lastStepDuration_1 * _abs_1) / length);
      return v.operator_multiply(factor);
    }
    return new Vector2f();
  }

  /**
   * Compute a kinematic move according to the linear move and to
   * the internal attributes of this object.
   * 
   * @param move is the requested motion, expressed with speed.
   * @param clock is the simulation time manager
   * @return the linear instant motion.
   */
  @Pure
  protected Vector2f computeKinematicTranslation(final Vector2f move, final TimeManager clock) {
    float speed = move.length();
    if ((speed != 0f)) {
      float _lastStepDuration = clock.getLastStepDuration();
      float _clamp = MathUtil.clamp(speed, 0, this.maxLinearSpeed);
      float factor = ((_lastStepDuration * _clamp) / speed);
      return move.operator_multiply(factor);
    }
    return new Vector2f();
  }

  /**
   * Compute a kinematic move according to the angular move and to
   * the internal attributes of this object.
   * 
   * @param move is the requested motion with speed.
   * @param clock is the simulation time manager
   * @return the angular instant motion.
   */
  @Pure
  protected float computeKinematicRotation(final float move, final TimeManager clock) {
    float speed = Math.abs(move);
    if ((speed != 0f)) {
      float _lastStepDuration = clock.getLastStepDuration();
      float _clamp = MathUtil.clamp(speed, 0, this.maxAngularSpeed);
      float factor = ((_lastStepDuration * _clamp) / speed);
      return (move * factor);
    }
    return 0f;
  }

  /**
   * Compute a steering move according to the angular move and to
   * the internal attributes of this object.
   * 
   * @param move is the requested motion.
   * @param clock is the simulation time manager
   * @return the angular instant motion.
   */
  @Pure
  protected float computeSteeringRotation(final float move, final TimeManager clock) {
    float v = 0;
    if ((move != 0f)) {
      float acceleration = MathUtil.clamp(move, 
        (-this.maxAngularAcceleration), 
        this.maxAngularAcceleration);
      float _abs = Math.abs(acceleration);
      float _abs_1 = Math.abs(move);
      acceleration = (_abs / _abs_1);
      v = (move * acceleration);
      float _lastStepDuration = clock.getLastStepDuration();
      v = (v * (0.5f * _lastStepDuration));
      v = (v + this.currentAngularSpeed);
    } else {
      v = this.currentAngularSpeed;
    }
    if ((v != 0f)) {
      float speed = MathUtil.clamp(v, 
        (-this.maxAngularSpeed), 
        this.maxAngularSpeed);
      float _lastStepDuration_1 = clock.getLastStepDuration();
      float _abs_2 = Math.abs(speed);
      float _abs_3 = Math.abs(v);
      float factor = ((_lastStepDuration_1 * _abs_2) / _abs_3);
      return (v * factor);
    }
    return 0f;
  }

  @Override
  @Pure
  @SyntheticMember
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    AbstractMobileObject other = (AbstractMobileObject) obj;
    if (Float.floatToIntBits(other.maxLinearSpeed) != Float.floatToIntBits(this.maxLinearSpeed))
      return false;
    if (Float.floatToIntBits(other.maxLinearAcceleration) != Float.floatToIntBits(this.maxLinearAcceleration))
      return false;
    if (Float.floatToIntBits(other.maxAngularSpeed) != Float.floatToIntBits(this.maxAngularSpeed))
      return false;
    if (Float.floatToIntBits(other.maxAngularAcceleration) != Float.floatToIntBits(this.maxAngularAcceleration))
      return false;
    if (Float.floatToIntBits(other.angle) != Float.floatToIntBits(this.angle))
      return false;
    if (Float.floatToIntBits(other.currentAngularSpeed) != Float.floatToIntBits(this.currentAngularSpeed))
      return false;
    return super.equals(obj);
  }

  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Float.hashCode(this.maxLinearSpeed);
    result = prime * result + Float.hashCode(this.maxLinearAcceleration);
    result = prime * result + Float.hashCode(this.maxAngularSpeed);
    result = prime * result + Float.hashCode(this.maxAngularAcceleration);
    result = prime * result + Float.hashCode(this.angle);
    result = prime * result + Float.hashCode(this.currentAngularSpeed);
    return result;
  }

  @SyntheticMember
  private static final long serialVersionUID = 4159222274L;
}
