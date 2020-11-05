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

import framework.math.Point2f;
import framework.math.Vector2f;
import framework.time.TimeManager;
import io.sarl.lang.core.annotation.SarlElementType;
import io.sarl.lang.core.annotation.SarlSpecification;
import io.sarl.lang.core.annotation.SyntheticMember;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;

/**
 * Abstract implementation of a situated environment.
 * 
 * @version $Name$ $Revision$ $Date$
 */
@SarlSpecification("0.15")
@SarlElementType(10)
@XbaseGenerated
@SuppressWarnings("all")
public abstract class AbstractEnvironment implements Environment {
  private final Map<UUID, AgentBody> agentBodyList = new TreeMap<UUID, AgentBody>();

  private final TimeManager timeManager;

  private final float width;

  private final float height;

  private final Collection<EnvironmentListener> listeners = new ArrayList<EnvironmentListener>();

  private final AtomicBoolean stateChanged = new AtomicBoolean();

  private final AtomicBoolean init = new AtomicBoolean(true);

  /**
   * @param width is the width of the environment.
   * @param height is the height of the environment.
   * @param timeManager is the time manager to use.
   */
  public AbstractEnvironment(final float width, final float height, final TimeManager timeManager) {
    this.width = width;
    this.height = height;
    this.timeManager = timeManager;
  }

  /**
   * Mark the state of the environment as changed.
   */
  protected void stateChanged() {
    this.stateChanged.set(true);
  }

  @Override
  public void addEnvironmentListener(final EnvironmentListener listener) {
    synchronized (this.listeners) {
      this.listeners.add(listener);
    }
  }

  @Override
  public void removeEnvironmentListener(final EnvironmentListener listener) {
    synchronized (this.listeners) {
      this.listeners.remove(listener);
    }
  }

  /**
   * Invoked to create an environment event.
   * 
   * @return an environment event.
   */
  @Pure
  protected EnvironmentEvent createEnvironmentEvent() {
    return new EnvironmentEvent(this);
  }

  /**
   * Notifies listeners about changes in environment.
   */
  protected void fireEnvironmentChange() {
    EnvironmentListener[] list = null;
    synchronized (this.listeners) {
      Object _newInstance = Array.newInstance(EnvironmentListener.class, this.listeners.size());
      list = ((EnvironmentListener[]) _newInstance);
      this.listeners.<EnvironmentListener>toArray(list);
    }
    EnvironmentEvent event = this.createEnvironmentEvent();
    for (final EnvironmentListener listener : list) {
      listener.environmentChanged(event);
    }
  }

  @Override
  public TimeManager getTimeManager() {
    return this.timeManager;
  }

  @Override
  public float getWidth() {
    return this.width;
  }

  @Override
  public float getHeight() {
    return this.height;
  }

  protected synchronized void addAgentBody(final AgentBody body, final Point2f position, final float direction) {
    boolean _get = this.init.get();
    if (_get) {
      synchronized (this.agentBodyList) {
        this.agentBodyList.put(body.getID(), body);
      }
      body.setPosition(position);
      body.setAngle(direction);
      this.onAgentBodyCreated(body);
    } else {
      throw new IllegalStateException("You cannot call this function after the start of the simulation");
    }
  }

  /**
   * Invoked when an agent body is created.
   * 
   * @param body the body.
   */
  protected abstract void onAgentBodyCreated(final AgentBody body);

  /**
   * Invoked when an agent body is destroyed.
   * 
   * @param body the body.
   */
  protected abstract void onAgentBodyDestroyed(final AgentBody body);

  @Pure
  public WorldModelState getState() {
    return new WorldModelState(this);
  }

  /**
   * Replies the bodies in the environment.
   * 
   * The replied collection is unmodifiable.
   * 
   * @return the bodies in the environment.
   */
  public Iterable<AgentBody> getAgentBodies() {
    synchronized (this.agentBodyList) {
      return Collections.<AgentBody>unmodifiableCollection(this.agentBodyList.values());
    }
  }

  @Override
  public int getAgentBodyNumber() {
    synchronized (this.agentBodyList) {
      return this.agentBodyList.size();
    }
  }

  @Override
  public AgentBody getAgentBodyFor(final UUID agentId) {
    synchronized (this.agentBodyList) {
      return this.agentBodyList.get(agentId);
    }
  }

  @Override
  public void runBehaviour() {
    boolean _andSet = this.init.getAndSet(false);
    if (_andSet) {
      this.fireEnvironmentChange();
    }
    this.stateChanged.set(false);
    ArrayList<MotionInfluence> motionInfluences = new ArrayList<MotionInfluence>();
    ArrayList<Influence> otherInfluences = new ArrayList<Influence>();
    List<AgentBody> bodies = null;
    synchronized (this.agentBodyList) {
      int _size = this.agentBodyList.size();
      ArrayList<AgentBody> _arrayList = new ArrayList<AgentBody>(_size);
      bodies = _arrayList;
      bodies.addAll(this.agentBodyList.values());
    }
    for (final AgentBody body : bodies) {
      {
        MotionInfluence mi = body.consumeMotionInfluence();
        if ((mi != null)) {
          motionInfluences.add(mi);
        }
        List<Influence> _consumeOtherInfluences = body.consumeOtherInfluences();
        for (final Influence i : _consumeOtherInfluences) {
          if ((i instanceof KillInfluence)) {
            this.stateChanged();
            AgentBody rbody = null;
            synchronized (this.agentBodyList) {
              UUID _emitter = ((KillInfluence)i).getEmitter();
              AgentBody _remove = this.agentBodyList.remove(_emitter);
              rbody = _remove;
            }
            if ((rbody != null)) {
              this.onAgentBodyDestroyed(rbody);
            }
          } else {
            otherInfluences.add(i);
          }
        }
      }
    }
    List<Influence> _computeEndogenousBehaviorInfluences = this.computeEndogenousBehaviorInfluences();
    for (final Influence i : _computeEndogenousBehaviorInfluences) {
      if ((i instanceof MotionInfluence)) {
        motionInfluences.add(((MotionInfluence)i));
      } else {
        if ((!(i instanceof KillInfluence))) {
          otherInfluences.add(i);
        }
      }
    }
    if (((!motionInfluences.isEmpty()) || (!otherInfluences.isEmpty()))) {
      this.applyInfluences(motionInfluences, otherInfluences, 
        this.timeManager);
    }
    boolean _get = this.stateChanged.get();
    if (_get) {
      this.fireEnvironmentChange();
    }
    this.timeManager.increment();
    List<Percept> list = null;
    for (final AgentBody body_1 : bodies) {
      {
        list = this.computePerceptionsFor(body_1);
        List<Percept> _elvis = null;
        if (list != null) {
          _elvis = list;
        } else {
          List<Percept> _emptyList = CollectionLiterals.<Percept>emptyList();
          _elvis = _emptyList;
        }
        body_1.setPerceptions(_elvis);
      }
    }
  }

  /**
   * Compute the influences for the endogenous behavior of the environment.
   * 
   * @return the list of the environment influences.
   */
  protected abstract List<Influence> computeEndogenousBehaviorInfluences();

  /**
   * Compute the perceptions for an agent body.
   * 
   * @param agent is the body of the perceiver.
   * @return the list of the perceived object, never <code>null</code>
   */
  protected abstract List<Percept> computePerceptionsFor(final AgentBody agent);

  /**
   * Detects conflicts between influences and applied resulting actions.
   * 
   * @param motionInfluences are the motion influences to apply.
   * @param otherInfluences are the other influences to apply.
   * @param timeManager is the time manager of the environment.
   */
  protected abstract void applyInfluences(final Collection<MotionInfluence> motionInfluences, final Collection<Influence> otherInfluences, final TimeManager timeManager);

  /**
   * Compute a steering move according to the linear move and to
   * the internal attributes of this object.
   * 
   * @param obj is the object to move.
   * @param move is the requested motion.
   * @param clock is the simulation time manager
   * @return the linear instant motion.
   */
  protected final Vector2f computeSteeringTranslation(final MobileObject obj, final Vector2f move, final TimeManager clock) {
    if ((obj instanceof AbstractMobileObject)) {
      return ((AbstractMobileObject)obj).computeSteeringTranslation(move, clock);
    }
    throw new IllegalArgumentException("obj");
  }

  /**
   * Compute a kinematic move according to the linear move and to
   * the internal attributes of this object.
   * 
   * @param obj is the object to move.
   * @param move is the requested motion.
   * @param clock is the simulation time manager
   * @return the linear instant motion.
   */
  protected final Vector2f computeKinematicTranslation(final MobileObject obj, final Vector2f move, final TimeManager clock) {
    if ((obj instanceof AbstractMobileObject)) {
      return ((AbstractMobileObject)obj).computeKinematicTranslation(move, clock);
    }
    throw new IllegalArgumentException("obj");
  }

  /**
   * Compute a kinematic move according to the angular move and to
   * the internal attributes of this object.
   * 
   * @param obj is the object to move.
   * @param move is the requested motion.
   * @param clock is the simulation time manager
   * @return the angular instant motion.
   */
  protected final float computeKinematicRotation(final MobileObject obj, final float move, final TimeManager clock) {
    if ((obj instanceof AbstractMobileObject)) {
      return ((AbstractMobileObject)obj).computeKinematicRotation(move, clock);
    }
    throw new IllegalArgumentException("obj");
  }

  /**
   * Compute a steering move according to the angular move and to
   * the internal attributes of this object.
   * 
   * @param obj is the object to move.
   * @param move is the requested motion.
   * @param clock is the simulation time manager
   * @return the angular instant motion.
   */
  protected final float computeSteeringRotation(final MobileObject obj, final float move, final TimeManager clock) {
    if ((obj instanceof AbstractMobileObject)) {
      return ((AbstractMobileObject)obj).computeSteeringRotation(move, clock);
    }
    throw new IllegalArgumentException("obj");
  }

  /**
   * Move the given object.
   * 
   * @param obj is the object to move.
   * @param instantTranslation is the linear motion in m
   * @param instantRotation is the angular motion in r
   */
  protected final void move(final MobileObject obj, final Vector2f instantTranslation, final float instantRotation) {
    if ((obj instanceof AbstractMobileObject)) {
      float duration = this.timeManager.getLastStepDuration();
      ((AbstractMobileObject)obj).move(instantTranslation.getX(), instantTranslation.getY(), duration, this.width, this.height);
      ((AbstractMobileObject)obj).rotate(instantRotation, duration);
      this.stateChanged();
    } else {
      throw new IllegalArgumentException("obj");
    }
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
    AbstractEnvironment other = (AbstractEnvironment) obj;
    if (Float.floatToIntBits(other.width) != Float.floatToIntBits(this.width))
      return false;
    if (Float.floatToIntBits(other.height) != Float.floatToIntBits(this.height))
      return false;
    return super.equals(obj);
  }

  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Float.hashCode(this.width);
    result = prime * result + Float.hashCode(this.height);
    return result;
  }
}
