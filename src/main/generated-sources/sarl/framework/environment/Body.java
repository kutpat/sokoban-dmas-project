/**
 * $Id$
 * 
 * Copyright (c) 2011-15 Stephane GALLAND <stephane.galland@utbm.fr>.
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
import java.util.List;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;

/**
 * Provides the services of a body.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
@SarlSpecification("0.15")
@SarlElementType(11)
@XbaseGenerated
@SuppressWarnings("all")
public interface Body {
  /**
   * Replies the id of this body.
   * 
   * @return the id of this body.
   */
  @Pure
  UUID getID();

  /**
   * Invoked to send the influence to the environment.
   * 
   * @param linearInfluence is the linear influence to apply on the object.
   * @param angularInfluence is the angular influence to apply on the object.
   */
  @DefaultValueSource
  void influenceKinematic(@DefaultValue("framework.environment.Body#INFLUENCEKINEMATIC_0") final Vector2f linearInfluence, @DefaultValue("framework.environment.Body#INFLUENCEKINEMATIC_1") final float angularInfluence);

  /**
   * Invoked to send the influence to the environment.
   * 
   * @param linearInfluence is the linear influence to apply on the object.
   * @param angularInfluence is the angular influence to apply on the object.
   */
  @DefaultValueSource
  void influenceSteering(@DefaultValue("framework.environment.Body#INFLUENCESTEERING_0") final Vector2f linearInfluence, @DefaultValue("framework.environment.Body#INFLUENCESTEERING_1") final float angularInfluence);

  /**
   * Replies all the perceived objects.
   * 
   * @return the perceived objects.
   */
  @Pure
  List<Percept> getPerceivedObjects();

  /**
   * Default value for the parameter linearInfluence
   */
  @Pure
  @SyntheticMember
  @SarlSourceCode("null")
  default Vector2f $DEFAULT_VALUE$INFLUENCEKINEMATIC_0() {
    return null;
  }

  /**
   * Default value for the parameter angularInfluence
   */
  @Pure
  @SyntheticMember
  @SarlSourceCode("0")
  default float $DEFAULT_VALUE$INFLUENCEKINEMATIC_1() {
    return 0;
  }

  /**
   * Default value for the parameter linearInfluence
   */
  @Pure
  @SyntheticMember
  @SarlSourceCode("null")
  default Vector2f $DEFAULT_VALUE$INFLUENCESTEERING_0() {
    return null;
  }

  /**
   * Default value for the parameter angularInfluence
   */
  @Pure
  @SyntheticMember
  @SarlSourceCode("0")
  default float $DEFAULT_VALUE$INFLUENCESTEERING_1() {
    return 0;
  }

  /**
   * Invoked to send the influence to the environment.
   * 
   * @optionalparam linearInfluence is the linear influence to apply on the object.
   * @optionalparam angularInfluence is the angular influence to apply on the object.
   */
  @DefaultValueUse("framework.math.Vector2f,float")
  @SyntheticMember
  default void influenceKinematic() {
    influenceKinematic($DEFAULT_VALUE$INFLUENCEKINEMATIC_0(), $DEFAULT_VALUE$INFLUENCEKINEMATIC_1());
  }

  /**
   * Invoked to send the influence to the environment.
   * 
   * @optionalparam linearInfluence is the linear influence to apply on the object.
   * @param angularInfluence is the angular influence to apply on the object.
   */
  @DefaultValueUse("framework.math.Vector2f,float")
  @SyntheticMember
  default void influenceKinematic(final float angularInfluence) {
    influenceKinematic($DEFAULT_VALUE$INFLUENCEKINEMATIC_0(), angularInfluence);
  }

  /**
   * Invoked to send the influence to the environment.
   * 
   * @param linearInfluence is the linear influence to apply on the object.
   * @optionalparam angularInfluence is the angular influence to apply on the object.
   */
  @DefaultValueUse("framework.math.Vector2f,float")
  @SyntheticMember
  default void influenceKinematic(final Vector2f linearInfluence) {
    influenceKinematic(linearInfluence, $DEFAULT_VALUE$INFLUENCEKINEMATIC_1());
  }

  /**
   * Invoked to send the influence to the environment.
   * 
   * @optionalparam linearInfluence is the linear influence to apply on the object.
   * @optionalparam angularInfluence is the angular influence to apply on the object.
   */
  @DefaultValueUse("framework.math.Vector2f,float")
  @SyntheticMember
  default void influenceSteering() {
    influenceSteering($DEFAULT_VALUE$INFLUENCESTEERING_0(), $DEFAULT_VALUE$INFLUENCESTEERING_1());
  }

  /**
   * Invoked to send the influence to the environment.
   * 
   * @optionalparam linearInfluence is the linear influence to apply on the object.
   * @param angularInfluence is the angular influence to apply on the object.
   */
  @DefaultValueUse("framework.math.Vector2f,float")
  @SyntheticMember
  default void influenceSteering(final float angularInfluence) {
    influenceSteering($DEFAULT_VALUE$INFLUENCESTEERING_0(), angularInfluence);
  }

  /**
   * Invoked to send the influence to the environment.
   * 
   * @param linearInfluence is the linear influence to apply on the object.
   * @optionalparam angularInfluence is the angular influence to apply on the object.
   */
  @DefaultValueUse("framework.math.Vector2f,float")
  @SyntheticMember
  default void influenceSteering(final Vector2f linearInfluence) {
    influenceSteering(linearInfluence, $DEFAULT_VALUE$INFLUENCESTEERING_1());
  }
}
