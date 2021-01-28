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

import framework.math.Point2i;
import io.sarl.api.core.DefaultContextInteractions;
import io.sarl.api.core.Destroy;
import io.sarl.api.core.Initialize;
import io.sarl.api.core.Lifecycle;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.AtomicSkillReference;
import io.sarl.lang.core.DynamicSkillProvider;
import io.sarl.lang.core.Event;
import io.sarl.lang.core.EventSpace;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;
import sokoban.environment.maze.AgentBody;
import sokoban.environment.maze.BoxObject;
import sokoban.environment.maze.sokobanBody;
import sokoban.environment.maze.sokobanObject;
import sokoban.players.SokobanAgent;
import sokoban.ui.sokobanGUI;

/**
 * Sokoban Environment Agent - Manages the state transformation puzzle.
 * 
 * This environment implements Sokoban: a discrete, grid-based state transformation puzzle.
 * 
 * Key characteristics:
 * - Fully observable: Complete state visibility at all times
 * - Deterministic: Same action from same state → same result
 * - State transformation: Every move changes the world configuration
 * - Goal: All agents reach exit positions (project requirement)
 * - Win Condition: Simulation ends when all agents reach exits
 * - All agents are autonomous (project requirement: agents must be autonomous)
 * - No randomness in step execution
 * 
 * This is NOT a maze (navigation problem) but a state transformation puzzle.
 * The challenge is not "where to go" but "what the world should look like."
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@SarlSpecification("0.15")
@SarlElementType(19)
@XbaseGenerated
@SuppressWarnings("all")
public class Environment extends Agent {
  private ArrayList<EnvironmentListener> listeners = CollectionLiterals.<EnvironmentListener>newArrayList();

  private Controller controller;

  private int time = 0;

  private final LinkedList<MazeChangeQuery> actions = CollectionLiterals.<MazeChangeQuery>newLinkedList();

  private boolean initialKnowledgeSent = false;

  private boolean simulationStarted = false;

  @SuppressWarnings("potential_inefficient_value_conversion")
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    Object _get = occurrence.parameters[0];
    Integer width = (_get == null ? null : PrimitiveCastExtensions.toInteger(_get));
    Object _get_1 = occurrence.parameters[1];
    Integer height = (_get_1 == null ? null : PrimitiveCastExtensions.toInteger(_get_1));
    Object _get_2 = occurrence.parameters[2];
    Integer nbGhosts = (_get_2 == null ? null : PrimitiveCastExtensions.toInteger(_get_2));
    Object _get_3 = occurrence.parameters[3];
    Integer perceptionDistance = (_get_3 == null ? null : PrimitiveCastExtensions.toInteger(_get_3));
    int _size = ((List<Object>)Conversions.doWrapArray(occurrence.parameters)).size();
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(4, _size, true);
    for (final Integer i : _doubleDotLessThan) {
      {
        Object o = occurrence.parameters[((i) == null ? 0 : (i).intValue())];
        if ((o instanceof EnvironmentListener)) {
          this.listeners.add(((EnvironmentListener)o));
        }
      }
    }
    DefaultMazeManagerSkill _defaultMazeManagerSkill = new DefaultMazeManagerSkill(((width) == null ? 0 : (width).intValue()), ((height) == null ? 0 : (height).intValue()));
    this.<DefaultMazeManagerSkill>setSkill(_defaultMazeManagerSkill, MazeManager.class);
    DefaultMazeFrontEndSkill _defaultMazeFrontEndSkill = new DefaultMazeFrontEndSkill();
    this.<DefaultMazeFrontEndSkill>setSkill(_defaultMazeFrontEndSkill, MazeFrontEnd.class);
    DefaultContextInteractions _$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
    EventSpace _defaultSpace = _$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.getDefaultSpace();
    DefaultContextInteractions _$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1 = this.$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
    Address _defaultAddress = _$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1.getDefaultAddress();
    Controller _controller = new Controller(_defaultSpace, _defaultAddress);
    this.controller = _controller;
    MazeManager _$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER = this.$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER();
    _$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER.createBoxes(2);
    MazeManager _$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER_1 = this.$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER();
    _$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER_1.createExits(2);
    this.fireControllerBinding();
    this.fireEnvironmentChange();
  }

  /**
   * Read agent count from listeners (GUI dashboard).
   */
  protected int readAgentCountFromListeners() {
    int nbAgents = 2;
    int _size = this.listeners.size();
    System.out.println((("[Environment] Reading agent count from " + Integer.valueOf(_size)) + " listener(s)"));
    for (final EnvironmentListener listener : this.listeners) {
      try {
        int count = listener.getAgentCount();
        System.out.println(("[Environment] Listener returned agent count: " + Integer.valueOf(count)));
        nbAgents = count;
        break;
      } catch (final Throwable _t) {
        if (_t instanceof Exception) {
          final Exception e = (Exception)_t;
          String _message = e.getMessage();
          System.out.println(("[Environment] Error getting agent count from listener: " + _message));
          nbAgents = 2;
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
    }
    return nbAgents;
  }

  private HashMap<Point2i, List<Point2i>> plannedPathsForGUI = CollectionLiterals.<Point2i, List<Point2i>>newHashMap();

  protected void fireEnvironmentChange() {
    TreeMap<Point2i, sokobanObject> objects = new TreeMap<Point2i, sokobanObject>();
    MazeManager _$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER = this.$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER();
    List<sokobanObject> _sokobanObjects = _$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER.getsokobanObjects();
    for (final sokobanObject obj : _sokobanObjects) {
      objects.put(obj.getPosition(), obj);
    }
    MazeManager _$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER_1 = this.$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER();
    List<Point2i> exitPositions = _$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER_1.getExitPositions();
    HashSet<Point2i> exitSet = CollectionLiterals.<Point2i>newHashSet();
    exitSet.addAll(exitPositions);
    UUID _iD = this.getID();
    MazeManager _$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER_2 = this.$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER();
    int _mazeWidth = _$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER_2.getMazeWidth();
    MazeManager _$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER_3 = this.$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER();
    int _mazeHeight = _$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER_3.getMazeHeight();
    Map<Point2i, sokobanObject> _unmodifiableMap = Collections.<Point2i, sokobanObject>unmodifiableMap(objects);
    Set<Point2i> _unmodifiableSet = Collections.<Point2i>unmodifiableSet(exitSet);
    EnvironmentEvent event = new EnvironmentEvent(_iD, this.time, _mazeWidth, _mazeHeight, _unmodifiableMap, _unmodifiableSet);
    for (final EnvironmentListener listener : this.listeners) {
      {
        listener.environmentChanged(event);
        if ((listener instanceof sokobanGUI)) {
          sokobanGUI gui = ((sokobanGUI)listener);
          Set<Map.Entry<Point2i, List<Point2i>>> _entrySet = this.plannedPathsForGUI.entrySet();
          for (final Map.Entry<Point2i, List<Point2i>> pathEntry : _entrySet) {
            gui.updatePlannedPath(pathEntry.getKey(), pathEntry.getValue());
          }
        }
      }
    }
    this.plannedPathsForGUI.clear();
  }

  private void $behaviorUnit$PathVisualization$1(final PathVisualization occurrence) {
    if (((occurrence.plannedPath != null) && (!occurrence.plannedPath.isEmpty()))) {
      this.plannedPathsForGUI.put(occurrence.boxPosition, occurrence.plannedPath);
    }
  }

  protected void fireControllerBinding() {
    for (final EnvironmentListener listener : this.listeners) {
      listener.bindController(this.controller);
    }
  }

  protected void fireControllerUnbinding() {
    for (final EnvironmentListener listener : this.listeners) {
      listener.unbindController(this.controller);
    }
  }

  protected void fireGameOver() {
    for (final EnvironmentListener listener : this.listeners) {
      listener.gameOver();
    }
    MazeManager _$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER = this.$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER();
    boolean _allBoxesOnGoals = _$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER.allBoxesOnGoals();
    if (_allBoxesOnGoals) {
      System.out.println("[Environment] WIN CONDITION MET: All boxes are on goals!");
    }
  }

  private void $behaviorUnit$Destroy$2(final Destroy occurrence) {
    this.fireControllerUnbinding();
    this.<MazeManager>clearSkill(MazeManager.class);
    this.<MazeFrontEnd>clearSkill(MazeFrontEnd.class);
  }

  private void $behaviorUnit$Action$3(final Action occurrence) {
    synchronized (this) {
      UUID _iD = occurrence.getSource().getID();
      System.out.println(((("[Environment] Received Action from agent " + _iD) + ": ") + occurrence.direction));
      UUID _iD_1 = occurrence.getSource().getID();
      MazeChangeQuery _mazeChangeQuery = new MazeChangeQuery(_iD_1, occurrence.direction);
      this.actions.add(_mazeChangeQuery);
      MazeManager _$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER = this.$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER();
      int bodyCount = _$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER.getBodyCount();
      int _size = this.actions.size();
      System.out.println((((("[Environment] Actions collected: " + Integer.valueOf(_size)) + " / ") + Integer.valueOf(bodyCount)) + " bodies"));
      if (((this.actions.size() >= (bodyCount - 1)) || ((this.actions.size() > 0) && (bodyCount <= 1)))) {
        int _size_1 = this.actions.size();
        System.out.println((("[Environment] Triggering RunEndOfStep with " + Integer.valueOf(_size_1)) + " action(s)"));
        DefaultContextInteractions _$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
        RunEndOfStep _runEndOfStep = new RunEndOfStep();
        class $SerializableClosureProxy implements Scope<Address> {
          
          private final Address $_defaultAddress_1;
          
          public $SerializableClosureProxy(final Address $_defaultAddress_1) {
            this.$_defaultAddress_1 = $_defaultAddress_1;
          }
          
          @Override
          public boolean matches(final Address it) {
            return Objects.equals(it, $_defaultAddress_1);
          }
        }
        final Scope<Address> _function = new Scope<Address>() {
          @Override
          public boolean matches(final Address it) {
            DefaultContextInteractions _$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = Environment.this.$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
            Address _defaultAddress = _$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.getDefaultAddress();
            return Objects.equals(it, _defaultAddress);
          }
          private Object writeReplace() throws ObjectStreamException {
            return new SerializableProxy($SerializableClosureProxy.class, Environment.this.$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER().getDefaultAddress());
          }
        };
        _$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_runEndOfStep, _function);
      }
    }
  }

  @Pure
  private boolean $behaviorUnitGuard$Action$3(final Action it, final Action occurrence) {
    return (occurrence.time >= it.time);
  }

  private int lastProgressTime = 0;

  private int progressThreshold = 50;

  private HashSet<Point2i> previousBoxPositions = CollectionLiterals.<Point2i>newHashSet();

  private void $behaviorUnit$RunEndOfStep$4(final RunEndOfStep occurrence) {
    synchronized (this) {
      MazeManager _$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER = this.$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER();
      boolean _applyActions = _$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER.applyActions(this.actions);
      if (_applyActions) {
        DefaultContextInteractions _$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
        sokobanIsDead _sokobanIsDead = new sokobanIsDead();
        _$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_sokobanIsDead);
        this.fireGameOver();
        Lifecycle _$CAPACITY_USE$IO_SARL_API_CORE_LIFECYCLE$CALLER = this.$CAPACITY_USE$IO_SARL_API_CORE_LIFECYCLE$CALLER();
        _$CAPACITY_USE$IO_SARL_API_CORE_LIFECYCLE$CALLER.killMe();
      } else {
        MazeManager _$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER_1 = this.$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER();
        boolean _allBoxesOnGoals = _$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER_1.allBoxesOnGoals();
        if (_allBoxesOnGoals) {
          System.out.println("[Environment] WIN CONDITION MET: All boxes are on goals!");
          this.fireGameOver();
          Lifecycle _$CAPACITY_USE$IO_SARL_API_CORE_LIFECYCLE$CALLER_1 = this.$CAPACITY_USE$IO_SARL_API_CORE_LIFECYCLE$CALLER();
          _$CAPACITY_USE$IO_SARL_API_CORE_LIFECYCLE$CALLER_1.killMe();
        } else {
          boolean progressMade = this.checkProgress();
          if (progressMade) {
            this.lastProgressTime = this.time;
          } else {
            int stepsWithoutProgress = (this.time - this.lastProgressTime);
            if ((stepsWithoutProgress >= this.progressThreshold)) {
              System.out.println((("[Environment] FAILURE CONDITION: No progress for " + Integer.valueOf(stepsWithoutProgress)) + " steps. Unsolvable state detected!"));
              System.out.println("[Environment] At least one box is not on an exit and no agent can make progress.");
              this.fireGameOver();
              Lifecycle _$CAPACITY_USE$IO_SARL_API_CORE_LIFECYCLE$CALLER_2 = this.$CAPACITY_USE$IO_SARL_API_CORE_LIFECYCLE$CALLER();
              _$CAPACITY_USE$IO_SARL_API_CORE_LIFECYCLE$CALLER_2.killMe();
            }
          }
        }
      }
      this.time++;
      this.fireEnvironmentChange();
      DefaultContextInteractions _$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1 = this.$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
      RunBeginingOfStep _runBeginingOfStep = new RunBeginingOfStep();
      _$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1.emit(_runBeginingOfStep);
    }
  }

  /**
   * Check if any progress was made in this step.
   * Progress means: any box moved, or any box moved closer to a goal.
   */
  protected boolean checkProgress() {
    HashSet<Point2i> currentBoxPositions = CollectionLiterals.<Point2i>newHashSet();
    MazeManager _$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER = this.$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER();
    List<sokobanObject> _sokobanObjects = _$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER.getsokobanObjects();
    for (final sokobanObject obj : _sokobanObjects) {
      if ((obj instanceof BoxObject)) {
        currentBoxPositions.add(((BoxObject)obj).getPosition());
      }
    }
    boolean _isEmpty = this.previousBoxPositions.isEmpty();
    if (_isEmpty) {
      this.previousBoxPositions = currentBoxPositions;
      return true;
    }
    boolean boxMoved = false;
    for (final Point2i currentPos : currentBoxPositions) {
      boolean _contains = this.previousBoxPositions.contains(currentPos);
      if ((!_contains)) {
        boxMoved = true;
        break;
      }
    }
    boolean boxMovedCloser = false;
    if ((!boxMoved)) {
      for (final Point2i currentPos_1 : currentBoxPositions) {
        {
          Point2i nearestGoal = this.findNearestGoalForBox(currentPos_1);
          if ((nearestGoal != null)) {
            for (final Point2i prevPos : this.previousBoxPositions) {
              {
                int prevDistance = this.calculateDistance(prevPos, nearestGoal);
                int currentDistance = this.calculateDistance(currentPos_1, nearestGoal);
                if ((currentDistance < prevDistance)) {
                  boxMovedCloser = true;
                  break;
                }
              }
            }
          }
        }
      }
    }
    this.previousBoxPositions = currentBoxPositions;
    return ((boxMoved || boxMovedCloser) || (!this.actions.isEmpty()));
  }

  /**
   * Find nearest goal for a box position.
   */
  @Pure
  protected Point2i findNearestGoalForBox(final Point2i boxPos) {
    MazeManager _$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER = this.$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER();
    List<Point2i> exitPositions = _$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER.getExitPositions();
    boolean _isEmpty = exitPositions.isEmpty();
    if (_isEmpty) {
      return null;
    }
    Point2i nearestExit = null;
    int minDistance = Integer.MAX_VALUE;
    for (final Point2i exitPos : exitPositions) {
      {
        int _x = exitPos.getX();
        int _x_1 = boxPos.getX();
        int _abs = Math.abs((_x - _x_1));
        int _y = exitPos.getY();
        int _y_1 = boxPos.getY();
        int _abs_1 = Math.abs((_y - _y_1));
        int distance = (_abs + _abs_1);
        if ((distance < minDistance)) {
          minDistance = distance;
          nearestExit = exitPos;
        }
      }
    }
    return nearestExit;
  }

  /**
   * Calculate Manhattan distance between two points.
   */
  @Pure
  protected int calculateDistance(final Point2i pos1, final Point2i pos2) {
    if (((pos1 == null) || (pos2 == null))) {
      return Integer.MAX_VALUE;
    }
    int _x = pos1.getX();
    int _x_1 = pos2.getX();
    int _abs = Math.abs((_x - _x_1));
    int _y = pos1.getY();
    int _y_1 = pos2.getY();
    int _abs_1 = Math.abs((_y - _y_1));
    return (_abs + _abs_1);
  }

  private void $behaviorUnit$AgentReachedExit$5(final AgentReachedExit occurrence) {
  }

  private void $behaviorUnit$RunBeginingOfStep$6(final RunBeginingOfStep occurrence) {
    synchronized (this) {
      if ((!this.simulationStarted)) {
        int nbAgents = this.readAgentCountFromListeners();
        System.out.println((("[Environment] Will spawn " + Integer.valueOf(nbAgents)) + " autonomous SokobanAgent(s)"));
        ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, nbAgents, true);
        for (final Integer i : _doubleDotLessThan) {
          {
            MazeManager _$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER = this.$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER();
            sokobanBody sokobanAgentBody = _$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER.createsokoban();
            UUID _agentId = sokobanAgentBody.getAgentId();
            System.out.println(((("[Environment] Spawning SokobanAgent #" + Integer.valueOf((((i) == null ? 0 : (i).intValue()) + 1))) + " with body ID: ") + _agentId));
            Lifecycle _$CAPACITY_USE$IO_SARL_API_CORE_LIFECYCLE$CALLER = this.$CAPACITY_USE$IO_SARL_API_CORE_LIFECYCLE$CALLER();
            DefaultContextInteractions _$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
            _$CAPACITY_USE$IO_SARL_API_CORE_LIFECYCLE$CALLER.spawnInContextWithID(SokobanAgent.class, sokobanAgentBody.getAgentId(), _$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.getDefaultContext());
          }
        }
        System.out.println((("[Environment] Finished spawning " + Integer.valueOf(nbAgents)) + " agents"));
        this.simulationStarted = true;
        this.initialKnowledgeSent = false;
        this.fireEnvironmentChange();
        DefaultContextInteractions _$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
        RunBeginingOfStep _runBeginingOfStep = new RunBeginingOfStep();
        _$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_runBeginingOfStep);
      }
      if ((!this.initialKnowledgeSent)) {
        ArrayList<Point2i> boxPositions = CollectionLiterals.<Point2i>newArrayList();
        MazeManager _$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER = this.$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER();
        List<Point2i> exitPositions = _$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER.getExitPositions();
        MazeManager _$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER_1 = this.$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER();
        List<sokobanObject> _sokobanObjects = _$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER_1.getsokobanObjects();
        for (final sokobanObject obj : _sokobanObjects) {
          if ((obj instanceof BoxObject)) {
            Point2i _position = ((BoxObject)obj).getPosition();
            boxPositions.add(_position);
          }
        }
        int _size = boxPositions.size();
        int _size_1 = exitPositions.size();
        System.out.println((((("[Environment] Emitting InitialKnowledge: " + Integer.valueOf(_size)) + " boxes, ") + Integer.valueOf(_size_1)) + " exits"));
        DefaultContextInteractions _$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1 = this.$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
        InitialKnowledge _initialKnowledge = new InitialKnowledge(boxPositions, exitPositions);
        _$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1.emit(_initialKnowledge);
        this.initialKnowledgeSent = true;
      }
      this.actions.clear();
      MazeManager _$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER_2 = this.$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER();
      Map<AgentBody, List<sokobanObject>> perceptions = _$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER_2.getPerceptions();
      int _size_2 = perceptions.size();
      System.out.println((("[Environment] RunBeginingOfStep: Sending perceptions to " + Integer.valueOf(_size_2)) + " agent(s)"));
      Set<Map.Entry<AgentBody, List<sokobanObject>>> _entrySet = perceptions.entrySet();
      for (final Map.Entry<AgentBody, List<sokobanObject>> e : _entrySet) {
        {
          UUID _agentId = e.getKey().getAgentId();
          Point2i _position_1 = e.getKey().getPosition();
          System.out.println(((("[Environment] Sending perception to agent " + _agentId) + " at position ") + _position_1));
          MazeFrontEnd _$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEFRONTEND$CALLER = this.$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEFRONTEND$CALLER();
          _$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEFRONTEND$CALLER.sendPerception(this.time, e.getKey().getAgentId(), e.getValue(), e.getKey().getPosition());
        }
      }
      boolean _isEmpty = perceptions.isEmpty();
      if (_isEmpty) {
        System.out.println("[Environment] Warning: No perceptions to send. Agent bodies may not be ready yet.");
      }
    }
  }

  @Extension
  @ImportedCapacityFeature(MazeManager.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER;

  @SyntheticMember
  @Pure
  private MazeManager $CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER() {
    if (this.$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER == null || this.$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER.get() == null) {
      this.$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER = $getSkill(MazeManager.class);
    }
    return $castSkill(MazeManager.class, this.$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER);
  }

  @Extension
  @ImportedCapacityFeature(MazeFrontEnd.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEFRONTEND;

  @SyntheticMember
  @Pure
  private MazeFrontEnd $CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEFRONTEND$CALLER() {
    if (this.$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEFRONTEND == null || this.$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEFRONTEND.get() == null) {
      this.$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEFRONTEND = $getSkill(MazeFrontEnd.class);
    }
    return $castSkill(MazeFrontEnd.class, this.$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEFRONTEND);
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

  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$Destroy(final Destroy occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Destroy$2(occurrence));
  }

  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$Initialize(final Initialize occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Initialize$0(occurrence));
  }

  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$Action(final Action occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    if ($behaviorUnitGuard$Action$3(occurrence, occurrence)) {
      ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Action$3(occurrence));
    }
  }

  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$AgentReachedExit(final AgentReachedExit occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$AgentReachedExit$5(occurrence));
  }

  /**
   * Handle path visualization events from agents.
   */
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$PathVisualization(final PathVisualization occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$PathVisualization$1(occurrence));
  }

  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$RunBeginingOfStep(final RunBeginingOfStep occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$RunBeginingOfStep$6(occurrence));
  }

  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$RunEndOfStep(final RunEndOfStep occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$RunEndOfStep$4(occurrence));
  }

  @SyntheticMember
  @Override
  public void $getSupportedEvents(final Set<Class<? extends Event>> toBeFilled) {
    super.$getSupportedEvents(toBeFilled);
    toBeFilled.add(Destroy.class);
    toBeFilled.add(Initialize.class);
    toBeFilled.add(Action.class);
    toBeFilled.add(AgentReachedExit.class);
    toBeFilled.add(PathVisualization.class);
    toBeFilled.add(RunBeginingOfStep.class);
    toBeFilled.add(RunEndOfStep.class);
  }

  @SyntheticMember
  @Override
  public boolean $isSupportedEvent(final Class<? extends Event> event) {
    if (Destroy.class.isAssignableFrom(event)) {
      return true;
    }
    if (Initialize.class.isAssignableFrom(event)) {
      return true;
    }
    if (Action.class.isAssignableFrom(event)) {
      return true;
    }
    if (AgentReachedExit.class.isAssignableFrom(event)) {
      return true;
    }
    if (PathVisualization.class.isAssignableFrom(event)) {
      return true;
    }
    if (RunBeginingOfStep.class.isAssignableFrom(event)) {
      return true;
    }
    if (RunEndOfStep.class.isAssignableFrom(event)) {
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
    if (Destroy.class.equals(eventType)) {
      final var occurrence = (Destroy) event;
      $guardEvaluator$Destroy(occurrence, callbacks);
    }
    if (Initialize.class.equals(eventType)) {
      final var occurrence = (Initialize) event;
      $guardEvaluator$Initialize(occurrence, callbacks);
    }
    if (Action.class.equals(eventType)) {
      final var occurrence = (Action) event;
      $guardEvaluator$Action(occurrence, callbacks);
    }
    if (AgentReachedExit.class.equals(eventType)) {
      final var occurrence = (AgentReachedExit) event;
      $guardEvaluator$AgentReachedExit(occurrence, callbacks);
    }
    if (PathVisualization.class.equals(eventType)) {
      final var occurrence = (PathVisualization) event;
      $guardEvaluator$PathVisualization(occurrence, callbacks);
    }
    if (RunBeginingOfStep.class.equals(eventType)) {
      final var occurrence = (RunBeginingOfStep) event;
      $guardEvaluator$RunBeginingOfStep(occurrence, callbacks);
    }
    if (RunEndOfStep.class.equals(eventType)) {
      final var occurrence = (RunEndOfStep) event;
      $guardEvaluator$RunEndOfStep(occurrence, callbacks);
    }
  }

  @Override
  @Pure
  @SyntheticMember
  public boolean equals(final Object obj) {
    throw new Error("Unresolved compilation problems:"
      + "\n\'agent\' is a reserved keyword which is not allowed as identifier. Please choose another word or alternatively confuse your co-workers by escaping it like this: \"^agent\""
      + "\n\'agent\' is a reserved keyword which is not allowed as identifier. Please choose another word or alternatively confuse your co-workers by escaping it like this: \"^agent\"");
  }

  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    throw new Error("Unresolved compilation problems:"
      + "\n\'agent\' is a reserved keyword which is not allowed as identifier. Please choose another word or alternatively confuse your co-workers by escaping it like this: \"^agent\"");
  }

  @SyntheticMember
  public Environment(final UUID parentID, final UUID agentID) {
    super(parentID, agentID);
  }

  @SyntheticMember
  @Inject
  public Environment(final UUID parentID, final UUID agentID, final DynamicSkillProvider skillProvider) {
    super(parentID, agentID, skillProvider);
  }
}
