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
package framework.environment;

import framework.time.TimePercept;
import io.sarl.api.core.Behaviors;
import io.sarl.api.core.DefaultContextInteractions;
import io.sarl.api.core.Initialize;
import io.sarl.api.core.Lifecycle;
import io.sarl.api.core.spaces.OpenEventSpace;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.AtomicSkillReference;
import io.sarl.lang.core.DynamicSkillProvider;
import io.sarl.lang.core.Event;
import io.sarl.lang.core.Scope;
import io.sarl.lang.core.annotation.ImportedCapacityFeature;
import io.sarl.lang.core.annotation.PerceptGuardEvaluator;
import io.sarl.lang.core.annotation.SarlElementType;
import io.sarl.lang.core.annotation.SarlSpecification;
import io.sarl.lang.core.annotation.SyntheticMember;
import io.sarl.lang.core.scoping.extensions.cast.PrimitiveCastExtensions;
import io.sarl.lang.core.util.SerializableProxy;
import jakarta.inject.Inject;
import java.io.ObjectStreamException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;

/**
 * This agent is managing the physic space.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 */
@SarlSpecification("0.15")
@SarlElementType(19)
@XbaseGenerated
@SuppressWarnings("all")
public class EnvironmentAgent extends Agent {
  private Environment environment;

  private OpenEventSpace space;

  private Address myAdr;

  private final AtomicInteger influences = new AtomicInteger(0);

  private final AtomicBoolean freeze = new AtomicBoolean(false);

  @SuppressWarnings("potential_inefficient_value_conversion")
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    Object _get = occurrence.parameters[0];
    this.environment = ((Environment) _get);
    Object _get_1 = occurrence.parameters[1];
    UUID spaceId = (_get_1 == null ? null : PrimitiveCastExtensions.toUUID(_get_1));
    DefaultContextInteractions _$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
    this.space = _$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.getDefaultContext().<OpenEventSpace>getSpace(spaceId);
    Behaviors _$CAPACITY_USE$IO_SARL_API_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_API_CORE_BEHAVIORS$CALLER();
    this.space.registerStrongParticipant(_$CAPACITY_USE$IO_SARL_API_CORE_BEHAVIORS$CALLER.asEventListener());
    this.myAdr = this.space.getAddress(this.getID());
    DefaultContextInteractions _$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1 = this.$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
    SimulationAgentReady _simulationAgentReady = new SimulationAgentReady();
    _$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1.emit(_simulationAgentReady);
  }

  private void $behaviorUnit$InfluenceEvent$1(final InfluenceEvent occurrence) {
    for (final Influence influence : occurrence.influences) {
      {
        UUID id = influence.getEmitter();
        if ((id == null)) {
          id = occurrence.getSource().getID();
        }
        AgentBody body = this.environment.getAgentBodyFor(id);
        body.influence(influence);
      }
    }
    int v = this.influences.incrementAndGet();
    int _agentBodyNumber = this.environment.getAgentBodyNumber();
    if ((v >= _agentBodyNumber)) {
      this.runEnvironmentBehavior();
    }
  }

  @Pure
  private boolean $behaviorUnitGuard$InfluenceEvent$1(final InfluenceEvent it, final InfluenceEvent occurrence) {
    boolean _get = this.freeze.get();
    return (!_get);
  }

  private void $behaviorUnit$StopSimulation$2(final StopSimulation occurrence) {
    Lifecycle _$CAPACITY_USE$IO_SARL_API_CORE_LIFECYCLE$CALLER = this.$CAPACITY_USE$IO_SARL_API_CORE_LIFECYCLE$CALLER();
    _$CAPACITY_USE$IO_SARL_API_CORE_LIFECYCLE$CALLER.killMe();
  }

  private void $behaviorUnit$StartSimulation$3(final StartSimulation occurrence) {
    this.runEnvironmentBehavior();
  }

  @SuppressWarnings("discouraged_reference")
  protected void runEnvironmentBehavior() {
    try {
      this.freeze.set(true);
      try {
        this.influences.set(0);
        this.environment.runBehaviour();
        float _simulationDelay = this.environment.getTimeManager().getSimulationDelay();
        long delay = ((long) _simulationDelay);
        if ((delay > 0)) {
          Thread.sleep(delay);
        }
      } finally {
        this.freeze.set(false);
      }
      this.notifyAgentsOrDie();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  protected void notifyAgentsOrDie() {
    boolean run = false;
    float _currentTime = this.environment.getTimeManager().getCurrentTime();
    float _lastStepDuration = this.environment.getTimeManager().getLastStepDuration();
    final TimePercept timePercept = new TimePercept(_currentTime, _lastStepDuration);
    Iterable<? extends AgentBody> _agentBodies = this.environment.getAgentBodies();
    for (final AgentBody body : _agentBodies) {
      {
        run = true;
        List<Percept> _perceivedObjects = body.getPerceivedObjects();
        Percept _percept = new Percept(body);
        PerceptionEvent event = new PerceptionEvent(_perceivedObjects, _percept, timePercept);
        class $SerializableClosureProxy implements Scope<Address> {
          
          private final UUID $_iD;
          
          public $SerializableClosureProxy(final UUID $_iD) {
            this.$_iD = $_iD;
          }
          
          @Override
          public boolean matches(final Address it) {
            UUID _iD = it.getID();
            return Objects.equals(_iD, $_iD);
          }
        }
        final Scope<Address> _function = new Scope<Address>() {
          @Override
          public boolean matches(final Address it) {
            UUID _iD = it.getID();
            UUID _iD_1 = body.getID();
            return Objects.equals(_iD, _iD_1);
          }
          private Object writeReplace() throws ObjectStreamException {
            return new SerializableProxy($SerializableClosureProxy.class, body.getID());
          }
        };
        this.space.emit(this.myAdr.getID(), event, _function);
      }
    }
    if ((!run)) {
      Lifecycle _$CAPACITY_USE$IO_SARL_API_CORE_LIFECYCLE$CALLER = this.$CAPACITY_USE$IO_SARL_API_CORE_LIFECYCLE$CALLER();
      _$CAPACITY_USE$IO_SARL_API_CORE_LIFECYCLE$CALLER.killMe();
    }
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
  @ImportedCapacityFeature(Lifecycle.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$IO_SARL_API_CORE_LIFECYCLE;

  @SyntheticMember
  @Pure
  private Lifecycle $CAPACITY_USE$IO_SARL_API_CORE_LIFECYCLE$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_API_CORE_LIFECYCLE == null || this.$CAPACITY_USE$IO_SARL_API_CORE_LIFECYCLE.get() == null) {
      this.$CAPACITY_USE$IO_SARL_API_CORE_LIFECYCLE = $getSkill(Lifecycle.class);
    }
    return $castSkill(Lifecycle.class, this.$CAPACITY_USE$IO_SARL_API_CORE_LIFECYCLE);
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

  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$InfluenceEvent(final InfluenceEvent occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    if ($behaviorUnitGuard$InfluenceEvent$1(occurrence, occurrence)) {
      ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$InfluenceEvent$1(occurrence));
    }
  }

  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$StartSimulation(final StartSimulation occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$StartSimulation$3(occurrence));
  }

  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$StopSimulation(final StopSimulation occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$StopSimulation$2(occurrence));
  }

  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$Initialize(final Initialize occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Initialize$0(occurrence));
  }

  @SyntheticMember
  @Override
  public void $getSupportedEvents(final Set<Class<? extends Event>> toBeFilled) {
    super.$getSupportedEvents(toBeFilled);
    toBeFilled.add(InfluenceEvent.class);
    toBeFilled.add(StartSimulation.class);
    toBeFilled.add(StopSimulation.class);
    toBeFilled.add(Initialize.class);
  }

  @SyntheticMember
  @Override
  public boolean $isSupportedEvent(final Class<? extends Event> event) {
    if (InfluenceEvent.class.isAssignableFrom(event)) {
      return true;
    }
    if (StartSimulation.class.isAssignableFrom(event)) {
      return true;
    }
    if (StopSimulation.class.isAssignableFrom(event)) {
      return true;
    }
    if (Initialize.class.isAssignableFrom(event)) {
      return true;
    }
    return false;
  }

  @SyntheticMember
  @Override
  public void $evaluateBehaviorGuards(final Class<?> eventType, final Object event, final Collection<Runnable> callbacks) {
    assert eventType != null;
    assert event != null;
    super.$evaluateBehaviorGuards(eventType, event, callbacks);
    if (InfluenceEvent.class.equals(eventType)) {
      final var occurrence = (InfluenceEvent) event;
      $guardEvaluator$InfluenceEvent(occurrence, callbacks);
    }
    if (StartSimulation.class.equals(eventType)) {
      final var occurrence = (StartSimulation) event;
      $guardEvaluator$StartSimulation(occurrence, callbacks);
    }
    if (StopSimulation.class.equals(eventType)) {
      final var occurrence = (StopSimulation) event;
      $guardEvaluator$StopSimulation(occurrence, callbacks);
    }
    if (Initialize.class.equals(eventType)) {
      final var occurrence = (Initialize) event;
      $guardEvaluator$Initialize(occurrence, callbacks);
    }
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

  @SyntheticMember
  public EnvironmentAgent(final UUID parentID, final UUID agentID) {
    super(parentID, agentID);
  }

  @SyntheticMember
  @Inject
  public EnvironmentAgent(final UUID parentID, final UUID agentID, final DynamicSkillProvider skillProvider) {
    super(parentID, agentID, skillProvider);
  }
}
