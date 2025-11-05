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
package framework.agent;

import framework.environment.DynamicType;
import framework.math.Vector2f;
import io.sarl.lang.core.annotation.SarlElementType;
import io.sarl.lang.core.annotation.SarlSpecification;
import io.sarl.lang.core.annotation.SyntheticMember;
import java.io.Serializable;
import java.util.Objects;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;

/**
 * Output of a behaviour.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
@SarlSpecification("0.15")
@SarlElementType(10)
@XbaseGenerated
@SuppressWarnings("all")
public class MotionAlgorithmOutput implements Serializable {
  private final DynamicType type;

  private final Vector2f linear = new Vector2f();

  private float angular = 0f;

  /**
   * @param type is the type of the output.
   */
  public MotionAlgorithmOutput(final DynamicType type) {
    this.type = type;
  }

  /**
   * @param outputToCopy is the output tp copy.
   */
  public MotionAlgorithmOutput(final MotionAlgorithmOutput outputToCopy) {
    class $AssertEvaluator$ {
      final boolean $$result;
      $AssertEvaluator$() {
        this.$$result = (outputToCopy != null);
      }
    }
    assert new $AssertEvaluator$().$$result;
    this.type = outputToCopy.type;
    this.linear.set(outputToCopy.linear);
    this.angular = outputToCopy.angular;
  }

  /**
   * Replies the type of the output.
   * 
   * @return the type of the output.
   */
  @Pure
  public DynamicType getType() {
    return this.type;
  }

  /**
   * Replies the linear output.
   * 
   * @return the linear output.
   */
  @Pure
  public Vector2f getLinear() {
    return this.linear;
  }

  /**
   * Replies the angular output.
   * 
   * @return the angular output.
   */
  @Pure
  public float getAngular() {
    return this.angular;
  }

  /**
   * Set the linear output.
   * 
   * @param linear
   */
  public float setLinear(final Vector2f linear) {
    float _xblockexpression = (float) 0;
    {
      class $AssertEvaluator$ {
        final boolean $$result;
        $AssertEvaluator$() {
          this.$$result = (linear != null);
        }
      }
      assert new $AssertEvaluator$().$$result;
      _xblockexpression = this.linear.set(linear);
    }
    return _xblockexpression;
  }

  /**
   * Set the linear output.
   * 
   * @param dx
   * @param dy
   */
  public float setLinear(final float dx, final float dy) {
    return this.linear.set(dx, dy);
  }

  /**
   * Set the angular output.
   * 
   * @param angular
   */
  public void setAngular(final float angular) {
    this.angular = angular;
  }

  /**
   * Set the linear output.
   * 
   * @param outputToCopy
   */
  public float setLinear(final MotionAlgorithmOutput outputToCopy) {
    float _xifexpression = (float) 0;
    if ((outputToCopy != null)) {
      float _xblockexpression = (float) 0;
      {
        boolean _notEquals = (!Objects.equals(outputToCopy.type, this.type));
        if (_notEquals) {
          throw new IllegalArgumentException();
        }
        _xblockexpression = this.linear.set(outputToCopy.linear);
      }
      _xifexpression = _xblockexpression;
    }
    return _xifexpression;
  }

  /**
   * Set the angular output.
   * 
   * @param outputToCopy
   */
  public float setAngular(final MotionAlgorithmOutput outputToCopy) {
    float _xifexpression = (float) 0;
    if ((outputToCopy != null)) {
      float _xblockexpression = (float) 0;
      {
        boolean _notEquals = (!Objects.equals(outputToCopy.type, this.type));
        if (_notEquals) {
          throw new IllegalArgumentException();
        }
        _xblockexpression = this.angular = outputToCopy.angular;
      }
      _xifexpression = _xblockexpression;
    }
    return _xifexpression;
  }

  /**
   * Set the linear and angular output.
   * 
   * @param outputToCopy
   */
  public float set(final MotionAlgorithmOutput outputToCopy) {
    float _xifexpression = (float) 0;
    if ((outputToCopy != null)) {
      float _xblockexpression = (float) 0;
      {
        boolean _notEquals = (!Objects.equals(outputToCopy.type, this.type));
        if (_notEquals) {
          throw new IllegalArgumentException();
        }
        this.linear.set(outputToCopy.linear);
        _xblockexpression = this.angular = outputToCopy.angular;
      }
      _xifexpression = _xblockexpression;
    }
    return _xifexpression;
  }

  @Override
  @Pure
  public String toString() {
    String _string = this.linear.toString();
    return ((("l=" + _string) + "; a=") + Float.valueOf(this.angular));
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
    MotionAlgorithmOutput other = (MotionAlgorithmOutput) obj;
    if (Float.floatToIntBits(other.angular) != Float.floatToIntBits(this.angular))
      return false;
    return super.equals(obj);
  }

  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Float.hashCode(this.angular);
    return result;
  }

  @SyntheticMember
  private static final long serialVersionUID = 1088473199L;
}
