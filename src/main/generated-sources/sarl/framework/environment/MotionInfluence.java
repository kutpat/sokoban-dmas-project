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

import framework.math.Vector2f;
import io.sarl.lang.core.annotation.DefaultValue;
import io.sarl.lang.core.annotation.DefaultValueSource;
import io.sarl.lang.core.annotation.DefaultValueUse;
import io.sarl.lang.core.annotation.SarlElementType;
import io.sarl.lang.core.annotation.SarlSourceCode;
import io.sarl.lang.core.annotation.SarlSpecification;
import io.sarl.lang.core.annotation.SyntheticMember;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;

/**
 * Abstract implementation of a motion influence.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
@SarlSpecification("0.15")
@SarlElementType(10)
@XbaseGenerated
@SuppressWarnings("all")
public class MotionInfluence extends Influence {
  private final DynamicType type;

  private final Vector2f linearInfluence = new Vector2f();

  private float angularInfluence;

  /**
   * @param type indicates if the influence is kinematic or steering.
   * @param influencedObject is the influenced object.
   * @param linearInfluence is the linear influence to apply on the object.
   * @param angularInfluence is the angular influence to apply on the object.
   */
  @DefaultValueSource
  public MotionInfluence(final DynamicType type, @DefaultValue("framework.environment.MotionInfluence#NEW_0") final UUID influencedObject, @DefaultValue("framework.environment.MotionInfluence#NEW_1") final Vector2f linearInfluence, @DefaultValue("framework.environment.MotionInfluence#NEW_2") final float angularInfluence) {
    super(influencedObject);
    this.type = type;
    if ((linearInfluence != null)) {
      this.linearInfluence.set(linearInfluence);
    }
    this.angularInfluence = angularInfluence;
  }

  /**
   * Set the linear influence.
   * 
   * @param l is the linear influence
   */
  public float setLinarInfluence(final Vector2f l) {
    float _xblockexpression = (float) 0;
    {
      class $AssertEvaluator$ {
        final boolean $$result;
        $AssertEvaluator$() {
          this.$$result = (l != null);
        }
      }
      assert new $AssertEvaluator$().$$result;
      _xblockexpression = this.linearInfluence.set(l);
    }
    return _xblockexpression;
  }

  /**
   * Set the linear influence.
   * 
   * @param dx is the linear influence
   * @param dy is the linear influence
   */
  public float setLinarInfluence(final float dx, final float dy) {
    return this.linearInfluence.set(dx, dy);
  }

  /**
   * Set the angular influence.
   * 
   * @param a
   */
  public void setAngularInfluence(final float a) {
    this.angularInfluence = a;
  }

  /**
   * Replies the linear influence.
   * 
   * @return the linear influence
   */
  @Pure
  public Vector2f getLinearInfluence() {
    return this.linearInfluence;
  }

  /**
   * Replies the angular influence.
   * 
   * @return the angular influence
   */
  @Pure
  public float getAngularInfluence() {
    return this.angularInfluence;
  }

  /**
   * Replies the type of the influence.
   * 
   * @return the type of the influence.
   */
  @Pure
  public DynamicType getType() {
    return this.type;
  }

  @Override
  @Pure
  public String toString() {
    String _plus = (this.linearInfluence + "|");
    return (_plus + Float.valueOf(this.angularInfluence));
  }

  /**
   * Default value for the parameter influencedObject
   */
  @Pure
  @SyntheticMember
  @SarlSourceCode("null")
  private static UUID $DEFAULT_VALUE$NEW_0() {
    return null;
  }

  /**
   * Default value for the parameter linearInfluence
   */
  @Pure
  @SyntheticMember
  @SarlSourceCode("null")
  private static Vector2f $DEFAULT_VALUE$NEW_1() {
    return null;
  }

  /**
   * Default value for the parameter angularInfluence
   */
  @Pure
  @SyntheticMember
  @SarlSourceCode("0")
  private static float $DEFAULT_VALUE$NEW_2() {
    return 0;
  }

  /**
   * @optionalparam type indicates if the influence is kinematic or steering.
   * @optionalparam influencedObject is the influenced object.
   * @optionalparam linearInfluence is the linear influence to apply on the object.
   * @optionalparam angularInfluence is the angular influence to apply on the object.
   */
  @DefaultValueUse("framework.environment.DynamicType,java.util.UUID,framework.math.Vector2f,float")
  @SyntheticMember
  public MotionInfluence(final DynamicType type) {
    this(type, $DEFAULT_VALUE$NEW_0(), $DEFAULT_VALUE$NEW_1(), $DEFAULT_VALUE$NEW_2());
  }

  /**
   * @optionalparam type indicates if the influence is kinematic or steering.
   * @optionalparam influencedObject is the influenced object.
   * @optionalparam linearInfluence is the linear influence to apply on the object.
   * @optionalparam angularInfluence is the angular influence to apply on the object.
   */
  @DefaultValueUse("framework.environment.DynamicType,java.util.UUID,framework.math.Vector2f,float")
  @SyntheticMember
  public MotionInfluence(final DynamicType type, final float angularInfluence) {
    this(type, $DEFAULT_VALUE$NEW_0(), $DEFAULT_VALUE$NEW_1(), angularInfluence);
  }

  /**
   * @optionalparam type indicates if the influence is kinematic or steering.
   * @optionalparam influencedObject is the influenced object.
   * @optionalparam linearInfluence is the linear influence to apply on the object.
   * @optionalparam angularInfluence is the angular influence to apply on the object.
   */
  @DefaultValueUse("framework.environment.DynamicType,java.util.UUID,framework.math.Vector2f,float")
  @SyntheticMember
  public MotionInfluence(final DynamicType type, final Vector2f linearInfluence) {
    this(type, $DEFAULT_VALUE$NEW_0(), linearInfluence, $DEFAULT_VALUE$NEW_2());
  }

  /**
   * @optionalparam type indicates if the influence is kinematic or steering.
   * @optionalparam influencedObject is the influenced object.
   * @optionalparam linearInfluence is the linear influence to apply on the object.
   * @optionalparam angularInfluence is the angular influence to apply on the object.
   */
  @DefaultValueUse("framework.environment.DynamicType,java.util.UUID,framework.math.Vector2f,float")
  @SyntheticMember
  public MotionInfluence(final DynamicType type, final UUID influencedObject) {
    this(type, influencedObject, $DEFAULT_VALUE$NEW_1(), $DEFAULT_VALUE$NEW_2());
  }

  /**
   * @optionalparam type indicates if the influence is kinematic or steering.
   * @optionalparam influencedObject is the influenced object.
   * @optionalparam linearInfluence is the linear influence to apply on the object.
   * @optionalparam angularInfluence is the angular influence to apply on the object.
   */
  @DefaultValueUse("framework.environment.DynamicType,java.util.UUID,framework.math.Vector2f,float")
  @SyntheticMember
  public MotionInfluence(final DynamicType type, final Vector2f linearInfluence, final float angularInfluence) {
    this(type, $DEFAULT_VALUE$NEW_0(), linearInfluence, angularInfluence);
  }

  /**
   * @optionalparam type indicates if the influence is kinematic or steering.
   * @optionalparam influencedObject is the influenced object.
   * @optionalparam linearInfluence is the linear influence to apply on the object.
   * @optionalparam angularInfluence is the angular influence to apply on the object.
   */
  @DefaultValueUse("framework.environment.DynamicType,java.util.UUID,framework.math.Vector2f,float")
  @SyntheticMember
  public MotionInfluence(final DynamicType type, final UUID influencedObject, final float angularInfluence) {
    this(type, influencedObject, $DEFAULT_VALUE$NEW_1(), angularInfluence);
  }

  /**
   * @optionalparam type indicates if the influence is kinematic or steering.
   * @optionalparam influencedObject is the influenced object.
   * @optionalparam linearInfluence is the linear influence to apply on the object.
   * @optionalparam angularInfluence is the angular influence to apply on the object.
   */
  @DefaultValueUse("framework.environment.DynamicType,java.util.UUID,framework.math.Vector2f,float")
  @SyntheticMember
  public MotionInfluence(final DynamicType type, final UUID influencedObject, final Vector2f linearInfluence) {
    this(type, influencedObject, linearInfluence, $DEFAULT_VALUE$NEW_2());
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
    MotionInfluence other = (MotionInfluence) obj;
    if (Float.floatToIntBits(other.angularInfluence) != Float.floatToIntBits(this.angularInfluence))
      return false;
    return super.equals(obj);
  }

  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Float.hashCode(this.angularInfluence);
    return result;
  }

  @SyntheticMember
  private static final long serialVersionUID = 5430341464L;
}
