/**
 * $Id$
 * 
 * Copyright (c) 2014-17 Stephane GALLAND <stephane.galland@utbm.fr>.
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
import framework.environment.Influence;
import framework.environment.InfluenceEvent;
import framework.environment.KillInfluence;
import framework.environment.MotionInfluence;
import framework.math.Vector2f;
import io.sarl.api.core.Behaviors;
import io.sarl.api.core.DefaultContextInteractions;
import io.sarl.api.core.spaces.OpenEventSpace;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.AtomicSkillReference;
import io.sarl.lang.core.Scope;
import io.sarl.lang.core.Skill;
import io.sarl.lang.core.annotation.DefaultValue;
import io.sarl.lang.core.annotation.DefaultValueSource;
import io.sarl.lang.core.annotation.ImportedCapacityFeature;
import io.sarl.lang.core.annotation.SarlElementType;
import io.sarl.lang.core.annotation.SarlSpecification;
import io.sarl.lang.core.annotation.SyntheticMember;
import io.sarl.lang.core.util.SerializableProxy;
import java.io.ObjectStreamException;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;

@SarlSpecification("0.15")
@SarlElementType(22)
@XbaseGenerated
@SuppressWarnings("all")
public class StandardPhysicEnvironment extends Skill implements PhysicEnvironment {
  private final UUID spaceID;

  private final UUID environmentID;

  private OpenEventSpace physicSpace;

  private Address myAdr;

  public StandardPhysicEnvironment(final UUID spaceID, final UUID environmentID) {
    this.environmentID = environmentID;
    this.spaceID = spaceID;
  }

  @SuppressWarnings("discouraged_reference")
  public void install() {
    do {
      {
        DefaultContextInteractions _$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
        this.physicSpace = _$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.getDefaultContext().<OpenEventSpace>getSpace(this.spaceID);
        Thread.yield();
      }
    } while((this.physicSpace == null));
    Behaviors _$CAPACITY_USE$IO_SARL_API_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_API_CORE_BEHAVIORS$CALLER();
    this.physicSpace.registerStrongParticipant(_$CAPACITY_USE$IO_SARL_API_CORE_BEHAVIORS$CALLER.asEventListener());
    this.myAdr = this.physicSpace.getAddress(this.getOwner().getID());
  }

  public void uninstall() {
    KillInfluence _killInfluence = new KillInfluence();
    InfluenceEvent event = new InfluenceEvent(_killInfluence);
    class $SerializableClosureProxy implements Scope<Address> {
      
      private final UUID $_environmentID;
      
      public $SerializableClosureProxy(final UUID $_environmentID) {
        this.$_environmentID = $_environmentID;
      }
      
      @Override
      public boolean matches(final Address it) {
        UUID _iD = it.getID();
        return Objects.equals(_iD, $_environmentID);
      }
    }
    final Scope<Address> _function = new Scope<Address>() {
      @Override
      public boolean matches(final Address it) {
        UUID _iD = it.getID();
        return Objects.equals(_iD, StandardPhysicEnvironment.this.environmentID);
      }
      private Object writeReplace() throws ObjectStreamException {
        return new SerializableProxy($SerializableClosureProxy.class, StandardPhysicEnvironment.this.environmentID);
      }
    };
    this.physicSpace.emit(this.myAdr.getID(), event, _function);
    this.physicSpace = null;
  }

  @DefaultValueSource
  public void influenceKinematic(@DefaultValue("framework.agent.PhysicEnvironment#INFLUENCEKINEMATIC_0") final Vector2f linearInfluence, @DefaultValue("framework.agent.PhysicEnvironment#INFLUENCEKINEMATIC_1") final float angularInfluence, final Influence... otherInfluences) {
    MotionInfluence mi = null;
    if ((linearInfluence == null)) {
      MotionInfluence _motionInfluence = new MotionInfluence(DynamicType.KINEMATIC, angularInfluence);
      mi = _motionInfluence;
    } else {
      MotionInfluence _motionInfluence_1 = new MotionInfluence(DynamicType.KINEMATIC, linearInfluence, angularInfluence);
      mi = _motionInfluence_1;
    }
    this.emitInfluences(mi, otherInfluences);
  }

  @DefaultValueSource
  public void influenceSteering(@DefaultValue("framework.agent.PhysicEnvironment#INFLUENCESTEERING_0") final Vector2f linearInfluence, @DefaultValue("framework.agent.PhysicEnvironment#INFLUENCESTEERING_1") final float angularInfluence, final Influence... otherInfluences) {
    MotionInfluence mi = null;
    if ((linearInfluence == null)) {
      MotionInfluence _motionInfluence = new MotionInfluence(DynamicType.STEERING, angularInfluence);
      mi = _motionInfluence;
    } else {
      MotionInfluence _motionInfluence_1 = new MotionInfluence(DynamicType.STEERING, linearInfluence, angularInfluence);
      mi = _motionInfluence_1;
    }
    this.emitInfluences(mi, otherInfluences);
  }

  public void emitInfluences(final MotionInfluence motionInfluence, final Influence... otherInfluences) {
    Influence[] influences = null;
    boolean _isEmpty = ((List<Influence>)Conversions.doWrapArray(otherInfluences)).isEmpty();
    if (_isEmpty) {
      Object _newInstance = Array.newInstance(Influence.class, 1);
      influences = ((Influence[]) _newInstance);
      influences[0] = motionInfluence;
    } else {
      int _length = otherInfluences.length;
      Object _newInstance_1 = Array.newInstance(Influence.class, (_length + 1));
      influences = ((Influence[]) _newInstance_1);
      influences[0] = motionInfluence;
      System.arraycopy(otherInfluences, 0, influences, 1, otherInfluences.length);
    }
    InfluenceEvent event = new InfluenceEvent(influences);
    class $SerializableClosureProxy implements Scope<Address> {
      
      private final UUID $_environmentID;
      
      public $SerializableClosureProxy(final UUID $_environmentID) {
        this.$_environmentID = $_environmentID;
      }
      
      @Override
      public boolean matches(final Address it) {
        UUID _iD = it.getID();
        return Objects.equals(_iD, $_environmentID);
      }
    }
    final Scope<Address> _function = new Scope<Address>() {
      @Override
      public boolean matches(final Address it) {
        UUID _iD = it.getID();
        return Objects.equals(_iD, StandardPhysicEnvironment.this.environmentID);
      }
      private Object writeReplace() throws ObjectStreamException {
        return new SerializableProxy($SerializableClosureProxy.class, StandardPhysicEnvironment.this.environmentID);
      }
    };
    this.physicSpace.emit(this.myAdr.getID(), event, _function);
  }

  @Extension
  @ImportedCapacityFeature(DefaultContextInteractions.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS;

  @SyntheticMember
  @Pure
  private DefaultContextInteractions $CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) {
      this.$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS = $getSkill(DefaultContextInteractions.class);
    }
    return $castSkill(DefaultContextInteractions.class, this.$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS);
  }

  @Extension
  @ImportedCapacityFeature(Behaviors.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$IO_SARL_API_CORE_BEHAVIORS;

  @SyntheticMember
  @Pure
  private Behaviors $CAPACITY_USE$IO_SARL_API_CORE_BEHAVIORS$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_API_CORE_BEHAVIORS == null || this.$CAPACITY_USE$IO_SARL_API_CORE_BEHAVIORS.get() == null) {
      this.$CAPACITY_USE$IO_SARL_API_CORE_BEHAVIORS = $getSkill(Behaviors.class);
    }
    return $castSkill(Behaviors.class, this.$CAPACITY_USE$IO_SARL_API_CORE_BEHAVIORS);
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
    StandardPhysicEnvironment other = (StandardPhysicEnvironment) obj;
    if (!Objects.equals(this.spaceID, other.spaceID))
      return false;
    if (!Objects.equals(this.environmentID, other.environmentID))
      return false;
    return super.equals(obj);
  }

  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Objects.hashCode(this.spaceID);
    result = prime * result + Objects.hashCode(this.environmentID);
    return result;
  }
}
