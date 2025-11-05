/**
 * $Id$
 * 
 * Copyright (c) 2015-17 Stephane GALLAND.
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
package sokoban.environment.agent;

import io.sarl.api.core.spaces.OpenEventSpace;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.EventListener;
import io.sarl.lang.core.Scope;
import io.sarl.lang.core.SpaceID;
import io.sarl.lang.core.annotation.SarlElementType;
import io.sarl.lang.core.annotation.SarlSpecification;
import io.sarl.lang.core.annotation.SyntheticMember;
import io.sarl.lang.core.util.SerializableProxy;
import java.io.ObjectStreamException;
import java.util.Objects;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;
import sokoban.environment.maze.Direction;

/**
 * Abstract implementation of a distributed space.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@SarlSpecification("0.15")
@SarlElementType(10)
@XbaseGenerated
@SuppressWarnings("all")
class DefaultMazeSpaceImpl implements MazeSpace {
  private EventListener creator;

  private OpenEventSpace communicationSpace;

  private SpaceID spaceID;

  /**
   * @param id - the identifier of the space.
   * @param commmunicationSpace - the communication space.
   */
  public DefaultMazeSpaceImpl(final SpaceID id, final OpenEventSpace communicationSpace, final EventListener creator) {
    class $AssertEvaluator$ {
      final boolean $$result;
      $AssertEvaluator$() {
        this.$$result = (communicationSpace != null);
      }
    }
    assert new $AssertEvaluator$().$$result;
    this.creator = creator;
    this.communicationSpace = communicationSpace;
    this.spaceID = id;
    if ((this.creator != null)) {
      this.communicationSpace.unregister(this.creator);
      this.communicationSpace.registerStrongParticipant(this.creator);
    }
  }

  public void destroy() {
    if ((this.creator != null)) {
      this.communicationSpace.unregister(this.creator);
    }
  }

  public void spawnBody(final EventListener binder) {
    this.communicationSpace.unregister(binder);
    this.communicationSpace.registerStrongParticipant(binder);
  }

  public void killBody(final EventListener binder) {
    this.communicationSpace.unregister(binder);
  }

  public void notifyPerception(final UUID sender, final Perception perception) {
    UUID id = perception.bodyId;
    class $SerializableClosureProxy implements Scope<Address> {
      
      private final UUID id;
      
      public $SerializableClosureProxy(final UUID id) {
        this.id = id;
      }
      
      @Override
      public boolean matches(final Address it) {
        UUID _iD = it.getID();
        return Objects.equals(_iD, id);
      }
    }
    final Scope<Address> _function = new Scope<Address>() {
      @Override
      public boolean matches(final Address it) {
        UUID _iD = it.getID();
        return Objects.equals(_iD, id);
      }
      private Object writeReplace() throws ObjectStreamException {
        return new SerializableProxy($SerializableClosureProxy.class, id);
      }
    };
    this.communicationSpace.emit(sender, perception, _function);
  }

  public void influence(final int influenceTime, final UUID emitter, final Direction influence) {
    Action event = new Action(influenceTime, influence);
    class $SerializableClosureProxy implements Scope<Address> {
      
      private final UUID $_iD_1;
      
      public $SerializableClosureProxy(final UUID $_iD_1) {
        this.$_iD_1 = $_iD_1;
      }
      
      @Override
      public boolean matches(final Address it) {
        UUID _iD = it.getID();
        return Objects.equals(_iD, $_iD_1);
      }
    }
    final Scope<Address> _function = new Scope<Address>() {
      @Override
      public boolean matches(final Address it) {
        UUID _iD = it.getID();
        UUID _iD_1 = DefaultMazeSpaceImpl.this.creator.getID();
        return Objects.equals(_iD, _iD_1);
      }
      private Object writeReplace() throws ObjectStreamException {
        return new SerializableProxy($SerializableClosureProxy.class, DefaultMazeSpaceImpl.this.creator.getID());
      }
    };
    this.communicationSpace.emit(emitter, event, _function);
  }

  @Deprecated
  @Pure
  public SpaceID getID() {
    return this.spaceID;
  }

  @Pure
  public SpaceID getSpaceID() {
    return this.spaceID;
  }

  public void forEachStrongParticipant(final Procedure1<? super UUID> callback) {
    this.communicationSpace.forEachStrongParticipant(callback);
  }

  public void forEachWeakParticipant(final Procedure1<? super UUID> callback) {
    this.communicationSpace.forEachWeakParticipant(callback);
  }

  @Pure
  public int getNumberOfStrongParticipants() {
    return this.communicationSpace.getNumberOfStrongParticipants();
  }

  @Pure
  public int getNumberOfWeakParticipants() {
    return this.communicationSpace.getNumberOfWeakParticipants();
  }

  @Pure
  public boolean isPseudoEmpty(final UUID id) {
    return this.communicationSpace.isPseudoEmpty(id);
  }

  @Override
  @Pure
  @SyntheticMember
  public boolean equals(final Object obj) {
    return super.equals(obj);
  }

  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    return result;
  }
}
