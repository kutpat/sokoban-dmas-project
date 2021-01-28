/**
 * Autonomous Sokoban agent that collaborates with other agents.
 * 
 * This agent can:
 * - Move autonomously
 * - Push boxes
 * - Communicate with other agents
 * - Coordinate to reach exits
 */
package sokoban.players;

import framework.math.Point2i;
import framework.math.Vector2i;
import io.sarl.api.core.DefaultContextInteractions;
import io.sarl.api.core.Initialize;
import io.sarl.api.core.Lifecycle;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.AtomicSkillReference;
import io.sarl.lang.core.DefaultSkill;
import io.sarl.lang.core.DynamicSkillProvider;
import io.sarl.lang.core.Event;
import io.sarl.lang.core.annotation.ImportedCapacityFeature;
import io.sarl.lang.core.annotation.PerceptGuardEvaluator;
import io.sarl.lang.core.annotation.SarlElementType;
import io.sarl.lang.core.annotation.SarlSpecification;
import io.sarl.lang.core.annotation.SyntheticMember;
import io.sarl.lang.core.scoping.extensions.cast.PrimitiveCastExtensions;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;
import sokoban.environment.agent.AcceptBoxTask;
import sokoban.environment.agent.AgentPositionBroadcast;
import sokoban.environment.agent.BoxAssignment;
import sokoban.environment.agent.BoxClaimed;
import sokoban.environment.agent.BoxCompleted;
import sokoban.environment.agent.BoxDiscovered;
import sokoban.environment.agent.BoxPositionUpdate;
import sokoban.environment.agent.BoxTaskCompleted;
import sokoban.environment.agent.BoxTaskFormation;
import sokoban.environment.agent.CollaborativeBoxTask;
import sokoban.environment.agent.CoordinatePush;
import sokoban.environment.agent.DefaultMazeMotionSkill;
import sokoban.environment.agent.ExitDiscovered;
import sokoban.environment.agent.InitialKnowledge;
import sokoban.environment.agent.JoinBoxTask;
import sokoban.environment.agent.MazeMotion;
import sokoban.environment.agent.OfferHelp;
import sokoban.environment.agent.PathProposal;
import sokoban.environment.agent.PathVisualization;
import sokoban.environment.agent.Perception;
import sokoban.environment.agent.ProposalResponse;
import sokoban.environment.agent.ProposeBoxTask;
import sokoban.environment.agent.ReadyForPush;
import sokoban.environment.agent.ReadyToHelp;
import sokoban.environment.agent.RequestHelp;
import sokoban.environment.maze.BoxObject;
import sokoban.environment.maze.Direction;
import sokoban.environment.maze.ExitObject;
import sokoban.environment.maze.WallObject;
import sokoban.environment.maze.sokobanObject;

/**
 * Autonomous Sokoban agent that collaborates with other agents.
 * 
 * This agent makes autonomous decisions to:
 * - Move toward exits
 * - Push boxes to clear paths
 * - Communicate with other agents for coordination
 */
@DefaultSkill(DefaultMazeMotionSkill.class)
@SarlSpecification("0.15")
@SarlElementType(19)
@XbaseGenerated
@SuppressWarnings("all")
public class SokobanAgent extends Agent {
  private int currentTime = 0;

  private Point2i myPosition;

  private HashSet<Point2i> allBoxPositions = CollectionLiterals.<Point2i>newHashSet();

  private HashSet<Point2i> allExitPositions = CollectionLiterals.<Point2i>newHashSet();

  private HashSet<Point2i> completedBoxes = CollectionLiterals.<Point2i>newHashSet();

  private HashSet<Point2i> claimedBoxes = CollectionLiterals.<Point2i>newHashSet();

  private Point2i myClaimedBox;

  private Point2i myTargetExit;

  private String agentBehaviorPhase;

  private Point2i myPushPosition;

  private Point2i lastBoxPosition;

  private ArrayList<PathProposal> pathProposals = CollectionLiterals.<PathProposal>newArrayList();

  private HashMap<Point2i, PathProposal> myProposals = CollectionLiterals.<Point2i, PathProposal>newHashMap();

  private int negotiationTimeout = 0;

  private final int maxNegotiationSteps = 3;

  private boolean coordinationActive = false;

  private boolean proposalsEmitted = false;

  private Point2i activeBoxTask = null;

  private HashSet<UUID> teamMembers = CollectionLiterals.<UUID>newHashSet();

  private int teamFormationTimeout = 0;

  private final int maxTeamFormationSteps = 5;

  private HashMap<Point2i, BoxTaskFormation> activeBoxTasks = CollectionLiterals.<Point2i, BoxTaskFormation>newHashMap();

  private String myTaskRole = null;

  private HashSet<UUID> helperReadyAgents = CollectionLiterals.<UUID>newHashSet();

  private LinkedList<Point2i> plannedBoxPath = CollectionLiterals.<Point2i>newLinkedList();

  private int currentPathStep = 0;

  private boolean pathPlanned = false;

  private boolean actionEmittedThisStep = false;

  private int lastPerceptionTime = (-1);

  private HashMap<UUID, Point2i> otherAgentPositions = CollectionLiterals.<UUID, Point2i>newHashMap();

  private HashMap<UUID, Point2i> otherAgentTargets = CollectionLiterals.<UUID, Point2i>newHashMap();

  private HashMap<UUID, String> otherAgentPhases = CollectionLiterals.<UUID, String>newHashMap();

  private HashSet<Point2i> assignedExits = CollectionLiterals.<Point2i>newHashSet();

  private int positionBroadcastCounter = 0;

  private HashSet<Point2i> previousBoxPositions = CollectionLiterals.<Point2i>newHashSet();

  private int boxProgressCount = 0;

  private int taskCompletionSteps = 0;

  private int alignmentSteps = 0;

  private Point2i lastAlignmentPosition = null;

  private List<Point2i> recentPositions = CollectionLiterals.<Point2i>newArrayList();

  private int pushStuckCounter = 0;

  private final int maxStuckSteps = 10;

  private final int maxAlignmentSteps = 15;

  private int pushFailureCount = 0;

  private final int maxPushFailures = 3;

  private HashMap<Point2i, Integer> recentlyReleasedBoxes = CollectionLiterals.<Point2i, Integer>newHashMap();

  private final int releaseCooldownSteps = 5;

  private final Random random = new Random();

  private String agentName = null;

  /**
   * Get agent name (A, B, C, etc.) for logging.
   */
  @Pure
  protected String getAgentName() {
    if ((this.agentName == null)) {
      int hash = this.getID().hashCode();
      int _abs = Math.abs(hash);
      int index = (_abs % 26);
      this.agentName = Character.toString((PrimitiveCastExtensions.charValue("A") + index));
    }
    return this.agentName;
  }

  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    this.agentBehaviorPhase = "PLANNING";
    this.myClaimedBox = null;
    this.myTargetExit = null;
    this.proposalsEmitted = false;
    this.myPushPosition = null;
    this.lastBoxPosition = null;
    this.plannedBoxPath.clear();
    this.currentPathStep = 0;
    this.pathPlanned = false;
    this.allBoxPositions.clear();
    this.allExitPositions.clear();
    this.completedBoxes.clear();
    this.claimedBoxes.clear();
    this.recentlyReleasedBoxes.clear();
    this.pathProposals.clear();
    this.myProposals.clear();
    this.negotiationTimeout = 0;
    this.coordinationActive = false;
    this.otherAgentPositions.clear();
    this.otherAgentTargets.clear();
    this.otherAgentPhases.clear();
    this.assignedExits.clear();
    this.positionBroadcastCounter = 0;
    this.pushStuckCounter = 0;
    this.pushFailureCount = 0;
    this.previousBoxPositions.clear();
    this.boxProgressCount = 0;
    this.activeBoxTask = null;
    this.teamMembers.clear();
    this.teamFormationTimeout = 0;
    this.activeBoxTasks.clear();
    this.myTaskRole = null;
    this.helperReadyAgents.clear();
    String _agentName = this.getAgentName();
    System.out.println((((("[Agent " + _agentName) + "] Initialized, waiting for InitialKnowledge (Phase: ") + this.agentBehaviorPhase) + ")"));
  }

  private void $behaviorUnit$InitialKnowledge$1(final InitialKnowledge occurrence) {
    this.allBoxPositions.clear();
    this.allExitPositions.clear();
    for (final Point2i boxPos : occurrence.boxPositions) {
      this.allBoxPositions.add(boxPos);
    }
    for (final Point2i exitPos : occurrence.exitPositions) {
      this.allExitPositions.add(exitPos);
    }
    String _agentName = this.getAgentName();
    int _size = this.allBoxPositions.size();
    int _size_1 = this.allExitPositions.size();
    System.out.println((((((("[Agent " + _agentName) + "] Received InitialKnowledge: ") + Integer.valueOf(_size)) + " boxes, ") + Integer.valueOf(_size_1)) + " exits"));
    boolean _equals = Objects.equals(this.agentBehaviorPhase, "BOX_SELECTION");
    if (_equals) {
      String _agentName_1 = this.getAgentName();
      System.out.println((("[Agent " + _agentName_1) + "] Ready to select a box"));
    }
  }

  private void $behaviorUnit$BoxClaimed$2(final BoxClaimed occurrence) {
    Point2i boxPos = occurrence.boxPosition;
    boolean _contains = this.claimedBoxes.contains(boxPos);
    if ((!_contains)) {
      this.claimedBoxes.add(boxPos);
      String _agentName = this.getAgentName();
      System.out.println(((((("[Agent " + _agentName) + "] Observed box at ") + boxPos) + " claimed by agent ") + occurrence.claimerId));
    }
    if ((((this.myClaimedBox != null) && (this.myClaimedBox.getX() == boxPos.getX())) && (this.myClaimedBox.getY() == boxPos.getY()))) {
      int _compareTo = this.getID().compareTo(occurrence.claimerId);
      if ((_compareTo > 0)) {
        String _agentName_1 = this.getAgentName();
        System.out.println((((((("[Agent " + _agentName_1) + "] Releasing box ") + boxPos) + " - another agent (") + occurrence.claimerId) + ") claimed it first (tie-breaker)"));
        this.myClaimedBox = null;
        this.myTargetExit = null;
        this.myPushPosition = null;
        this.claimedBoxes.remove(boxPos);
        this.plannedBoxPath.clear();
        this.currentPathStep = 0;
        this.pathPlanned = false;
        this.agentBehaviorPhase = "BOX_SELECTION";
      } else {
        String _agentName_2 = this.getAgentName();
        System.out.println((((((("[Agent " + _agentName_2) + "] Keeping box ") + boxPos) + " - we have priority (lower ID), staying in ") + this.agentBehaviorPhase) + " phase"));
      }
    }
  }

  @Pure
  private boolean $behaviorUnitGuard$BoxClaimed$2(final BoxClaimed it, final BoxClaimed occurrence) {
    boolean _equals = occurrence.claimerId.equals(this.getID());
    return (!_equals);
  }

  private void $behaviorUnit$PathProposal$3(final PathProposal occurrence) {
    this.pathProposals.add(occurrence);
    String _agentName = this.getAgentName();
    System.out.println((((((((("[Agent " + _agentName) + "] Received PathProposal from agent ") + occurrence.agentId) + " for box at ") + occurrence.boxPosition) + " (score: ") + Double.valueOf(occurrence.efficiencyScore)) + ")"));
  }

  @Pure
  private boolean $behaviorUnitGuard$PathProposal$3(final PathProposal it, final PathProposal occurrence) {
    boolean _equals = occurrence.agentId.equals(this.getID());
    return (!_equals);
  }

  private void $behaviorUnit$ProposalResponse$4(final ProposalResponse occurrence) {
    boolean _equals = occurrence.proposalAgentId.equals(this.getID());
    if (_equals) {
      if (occurrence.accepted) {
        String _agentName = this.getAgentName();
        System.out.println(((((("[Agent " + _agentName) + "] Agent ") + occurrence.responderId) + " accepted my proposal for box at ") + occurrence.boxPosition));
      } else {
        String _agentName_1 = this.getAgentName();
        System.out.println((((((((("[Agent " + _agentName_1) + "] Agent ") + occurrence.responderId) + " rejected my proposal for box at ") + occurrence.boxPosition) + " (alternative score: ") + Double.valueOf(occurrence.alternativeScore)) + ")"));
      }
    }
  }

  private void $behaviorUnit$BoxTaskFormation$5(final BoxTaskFormation occurrence) {
    Point2i boxPos = occurrence.boxPosition;
    boolean _contains = this.completedBoxes.contains(boxPos);
    if (_contains) {
      return;
    }
    if (((this.activeBoxTask != null) && (!this.activeBoxTask.equals(boxPos)))) {
      return;
    }
    this.activeBoxTask = boxPos;
    this.myClaimedBox = boxPos;
    this.myTargetExit = occurrence.exitPosition;
    this.teamMembers.clear();
    for (final UUID memberId : occurrence.teamMembers) {
      boolean _equals = memberId.equals(this.getID());
      if ((!_equals)) {
        this.teamMembers.add(memberId);
      }
    }
    this.plannedBoxPath.clear();
    this.plannedBoxPath.addAll(occurrence.plannedPath);
    this.currentPathStep = 0;
    boolean _isEmpty = this.plannedBoxPath.isEmpty();
    this.pathPlanned = (!_isEmpty);
    this.claimedBoxes.add(boxPos);
    this.activeBoxTasks.put(boxPos, occurrence);
    String _agentName = this.getAgentName();
    int _size = occurrence.teamMembers.size();
    System.out.println((((((((("[Agent " + _agentName) + "] Joined team for box at ") + boxPos) + " (initiator: ") + occurrence.initiatorId) + ", team size: ") + Integer.valueOf(_size)) + ")"));
    String oldPhase = this.agentBehaviorPhase;
    boolean _equals_1 = Objects.equals(this.agentBehaviorPhase, "PLANNING");
    if (_equals_1) {
      this.agentBehaviorPhase = "COORDINATION";
      String _agentName_1 = this.getAgentName();
      System.out.println((((((("[Agent " + _agentName_1) + "] Transitioned from ") + oldPhase) + " to COORDINATION phase after joining team (activeBoxTask: ") + this.activeBoxTask) + ")"));
    } else {
      String _agentName_2 = this.getAgentName();
      System.out.println((((("[Agent " + _agentName_2) + "] Received BoxTaskFormation but phase is ") + this.agentBehaviorPhase) + " (not PLANNING), not transitioning"));
    }
    DefaultContextInteractions _$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
    UUID _iD = this.getID();
    JoinBoxTask _joinBoxTask = new JoinBoxTask(boxPos, _iD, this.myPosition);
    _$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_joinBoxTask);
  }

  @Pure
  private boolean $behaviorUnitGuard$BoxTaskFormation$5(final BoxTaskFormation it, final BoxTaskFormation occurrence) {
    boolean _equals = occurrence.initiatorId.equals(this.getID());
    return (!_equals);
  }

  private void $behaviorUnit$JoinBoxTask$6(final JoinBoxTask occurrence) {
    if (((this.activeBoxTask != null) && this.activeBoxTask.equals(occurrence.boxPosition))) {
      boolean _contains = this.teamMembers.contains(occurrence.agentId);
      if ((!_contains)) {
        this.teamMembers.add(occurrence.agentId);
        String _agentName = this.getAgentName();
        int _size = this.teamMembers.size();
        System.out.println((((((((("[Agent " + _agentName) + "] Team member ") + occurrence.agentId) + " joined box task at ") + occurrence.boxPosition) + " (team size: ") + Integer.valueOf((_size + 1))) + ")"));
      }
    }
  }

  @Pure
  private boolean $behaviorUnitGuard$JoinBoxTask$6(final JoinBoxTask it, final JoinBoxTask occurrence) {
    boolean _equals = occurrence.agentId.equals(this.getID());
    return (!_equals);
  }

  private void $behaviorUnit$BoxAssignment$7(final BoxAssignment occurrence) {
    Point2i boxPos = occurrence.boxPosition;
    UUID assignedAgentId = occurrence.assignedAgentId;
    boolean _equals = assignedAgentId.equals(this.getID());
    if (_equals) {
      String _agentName = this.getAgentName();
      System.out.println(((("[Agent " + _agentName) + "] Received my own BoxAssignment for box at ") + boxPos));
      return;
    }
    boolean _contains = this.claimedBoxes.contains(boxPos);
    if ((!_contains)) {
      this.claimedBoxes.add(boxPos);
      String _agentName_1 = this.getAgentName();
      System.out.println((((((("[Agent " + _agentName_1) + "] Observed box at ") + boxPos) + " assigned to agent ") + assignedAgentId) + ", marking as claimed"));
    }
    if ((occurrence.exitPosition != null)) {
      this.assignedExits.add(occurrence.exitPosition);
      String _agentName_2 = this.getAgentName();
      System.out.println(((((("[Agent " + _agentName_2) + "] Tracked exit ") + occurrence.exitPosition) + " as assigned to agent ") + assignedAgentId));
    }
    if ((((this.myClaimedBox != null) && (this.myClaimedBox.getX() == boxPos.getX())) && (this.myClaimedBox.getY() == boxPos.getY()))) {
      boolean _equals_1 = assignedAgentId.equals(this.getID());
      if ((!_equals_1)) {
        String _agentName_3 = this.getAgentName();
        System.out.println((((("[Agent " + _agentName_3) + "] Releasing claim on box at ") + boxPos) + " (assigned to another agent)"));
        if ((this.myClaimedBox != null)) {
          this.claimedBoxes.remove(this.myClaimedBox);
        }
        this.myClaimedBox = null;
        this.myTargetExit = null;
        this.myPushPosition = null;
        this.plannedBoxPath.clear();
        this.currentPathStep = 0;
        this.pathPlanned = false;
        this.lastBoxPosition = null;
        this.agentBehaviorPhase = "PLANNING";
        this.proposalsEmitted = false;
      }
    }
  }

  private void $behaviorUnit$BoxCompleted$8(final BoxCompleted occurrence) {
    Point2i boxPos = occurrence.boxPosition;
    String _agentName = this.getAgentName();
    System.out.println(((((("[Agent " + _agentName) + "] Observed box at ") + boxPos) + " completed by agent ") + occurrence.completerId));
    if (((this.myClaimedBox != null) && this.myClaimedBox.equals(boxPos))) {
      String _agentName_1 = this.getAgentName();
      System.out.println((((("[Agent " + _agentName_1) + "] BoxCompleted: My box at ") + boxPos) + " is completed, returning to PLANNING"));
      this.myClaimedBox = null;
      this.myTargetExit = null;
      this.myPushPosition = null;
      this.lastBoxPosition = null;
      this.plannedBoxPath.clear();
      this.currentPathStep = 0;
      this.pathPlanned = false;
      this.agentBehaviorPhase = "PLANNING";
      this.proposalsEmitted = false;
      this.alignmentSteps = 0;
      this.lastAlignmentPosition = null;
      this.recentPositions.clear();
      this.pushFailureCount = 0;
      this.pushStuckCounter = 0;
      this.cleanupTeamState();
      String _agentName_2 = this.getAgentName();
      System.out.println((("[Agent " + _agentName_2) + "] Released box and returned to PLANNING for coordination"));
    }
    boolean _contains = this.completedBoxes.contains(boxPos);
    if ((!_contains)) {
      this.completedBoxes.add(boxPos);
      String _agentName_3 = this.getAgentName();
      System.out.println((("[Agent " + _agentName_3) + "] Marked box as completed in completedBoxes"));
    }
    boolean _contains_1 = this.claimedBoxes.contains(boxPos);
    if (_contains_1) {
      this.claimedBoxes.remove(boxPos);
      String _agentName_4 = this.getAgentName();
      System.out.println((("[Agent " + _agentName_4) + "] Removed completed box from claimedBoxes"));
    }
  }

  private void $behaviorUnit$CollaborativeBoxTask$9(final CollaborativeBoxTask occurrence) {
    if (((this.myClaimedBox == null) || this.myClaimedBox.equals(occurrence.boxPosition))) {
      this.myClaimedBox = occurrence.boxPosition;
      this.myTargetExit = occurrence.exitPosition;
      this.plannedBoxPath.clear();
      this.plannedBoxPath.addAll(occurrence.plannedPath);
      this.currentPathStep = 0;
      boolean _isEmpty = this.plannedBoxPath.isEmpty();
      this.pathPlanned = (!_isEmpty);
      boolean _isEmpty_1 = this.plannedBoxPath.isEmpty();
      if (_isEmpty_1) {
        String _agentName = this.getAgentName();
        System.out.println(((("[Agent " + _agentName) + "] CollaborativeBoxTask: WARNING - Received empty planned path for box at ") + occurrence.boxPosition));
      } else {
        String _agentName_1 = this.getAgentName();
        int _size = this.plannedBoxPath.size();
        System.out.println(((((("[Agent " + _agentName_1) + "] CollaborativeBoxTask: Received planned path with ") + Integer.valueOf(_size)) + " steps: ") + this.plannedBoxPath));
      }
      this.teamMembers.clear();
      this.teamMembers.addAll(occurrence.teamMembers);
      int myIndex = occurrence.teamMembers.indexOf(this.getID());
      String _xifexpression = null;
      if ((myIndex == 0)) {
        _xifexpression = "PUSHER";
      } else {
        _xifexpression = "HELPER";
      }
      this.myTaskRole = _xifexpression;
      this.claimedBoxes.add(occurrence.boxPosition);
      this.assignedExits.add(occurrence.exitPosition);
      this.agentBehaviorPhase = "ALIGNMENT";
      String _agentName_2 = this.getAgentName();
      System.out.println((((((("[Agent " + _agentName_2) + "] Joined collaborative task for box at ") + occurrence.boxPosition) + " as ") + this.myTaskRole) + " - starting execution immediately"));
    }
  }

  @Pure
  private boolean $behaviorUnitGuard$CollaborativeBoxTask$9(final CollaborativeBoxTask it, final CollaborativeBoxTask occurrence) {
    boolean _contains = occurrence.teamMembers.contains(this.getID());
    return _contains;
  }

  private void $behaviorUnit$CollaborativeBoxTask$10(final CollaborativeBoxTask occurrence) {
    boolean _contains = this.claimedBoxes.contains(occurrence.boxPosition);
    if ((!_contains)) {
      this.claimedBoxes.add(occurrence.boxPosition);
      this.assignedExits.add(occurrence.exitPosition);
      String _agentName = this.getAgentName();
      System.out.println((((((("[Agent " + _agentName) + "] CollaborativeBoxTask: Box at ") + occurrence.boxPosition) + " is being worked on by team ") + occurrence.teamMembers) + ", marking as claimed"));
    }
  }

  @Pure
  private boolean $behaviorUnitGuard$CollaborativeBoxTask$10(final CollaborativeBoxTask it, final CollaborativeBoxTask occurrence) {
    boolean _contains = occurrence.teamMembers.contains(this.getID());
    return (!_contains);
  }

  private void $behaviorUnit$ReadyToHelp$11(final ReadyToHelp occurrence) {
    this.helperReadyAgents.add(occurrence.helperId);
    String _agentName = this.getAgentName();
    System.out.println((((("[Agent " + _agentName) + "] PUSHER: Helper ") + occurrence.helperId) + " is ready (informational)"));
  }

  @Pure
  private boolean $behaviorUnitGuard$ReadyToHelp$11(final ReadyToHelp it, final ReadyToHelp occurrence) {
    return ((this.myTaskRole == "PUSHER") && this.teamMembers.contains(occurrence.helperId));
  }

  private void $behaviorUnit$Perception$12(final Perception occurrence) {
    synchronized (this) {
      if ((occurrence.time != this.lastPerceptionTime)) {
        this.actionEmittedThisStep = false;
        this.lastPerceptionTime = occurrence.time;
      }
      if (this.actionEmittedThisStep) {
        String _agentName = this.getAgentName();
        System.out.println((("[Agent " + _agentName) + "] Skipping decideAction - already emitted action this step"));
        return;
      }
      String _agentName_1 = this.getAgentName();
      int _size = occurrence.objects.size();
      System.out.println(((((((("[Agent " + _agentName_1) + "] Received Perception at time ") + Integer.valueOf(occurrence.time)) + ", position ") + occurrence.position) + ", objects: ") + Integer.valueOf(_size)));
      this.currentTime = occurrence.time;
      this.myPosition = occurrence.position;
      this.updateGlobalKnowledge(occurrence.objects);
      this.positionBroadcastCounter++;
      if ((this.positionBroadcastCounter >= 3)) {
        DefaultContextInteractions _$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
        UUID _iD = this.getID();
        AgentPositionBroadcast _agentPositionBroadcast = new AgentPositionBroadcast(_iD, this.myPosition, this.myClaimedBox, this.agentBehaviorPhase);
        _$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_agentPositionBroadcast);
        this.positionBroadcastCounter = 0;
      }
      this.checkExitReached();
      this.decideAction(occurrence.objects);
    }
  }

  /**
   * Check if agent has reached an exit.
   */
  protected void checkExitReached() {
  }

  /**
   * Update global knowledge based on current perceptions.
   * Tracks box movements to maintain accurate global knowledge.
   * Always maintains physical reality in allBoxPositions, regardless of completion state.
   */
  protected void updateGlobalKnowledge(final List<sokobanObject> perceivedObjects) {
    HashSet<Point2i> currentBoxPositions = new HashSet<Point2i>();
    for (final sokobanObject obj : perceivedObjects) {
      if ((obj instanceof BoxObject)) {
        currentBoxPositions.add(((BoxObject)obj).getPosition());
        boolean _contains = this.allBoxPositions.contains(((BoxObject)obj).getPosition());
        if ((!_contains)) {
          ArrayList<Point2i> oldPositions = new ArrayList<Point2i>();
          for (final Point2i oldPos : this.allBoxPositions) {
            if (((oldPos != null) && (((BoxObject)obj).getPosition() != null))) {
              int _x = oldPos.getX();
              int _x_1 = ((BoxObject)obj).getPosition().getX();
              int dx = (_x - _x_1);
              int _y = oldPos.getY();
              int _y_1 = ((BoxObject)obj).getPosition().getY();
              int dy = (_y - _y_1);
              int _abs = Math.abs(dx);
              int _abs_1 = Math.abs(dy);
              int distance = (_abs + _abs_1);
              if ((distance <= 2)) {
                oldPositions.add(oldPos);
              }
            }
          }
          for (final Point2i oldPos_1 : oldPositions) {
            this.allBoxPositions.remove(oldPos_1);
          }
          this.allBoxPositions.add(((BoxObject)obj).getPosition());
          String _agentName = this.getAgentName();
          Point2i _position = ((BoxObject)obj).getPosition();
          System.out.println(((("[Agent " + _agentName) + "] Updated global knowledge: box moved to ") + _position));
        }
        boolean _isOnTarget = ((BoxObject)obj).isOnTarget();
        if (_isOnTarget) {
          boolean _contains_1 = this.completedBoxes.contains(((BoxObject)obj).getPosition());
          if ((!_contains_1)) {
            this.completedBoxes.add(((BoxObject)obj).getPosition());
            String _agentName_1 = this.getAgentName();
            Point2i _position_1 = ((BoxObject)obj).getPosition();
            System.out.println((((("[Agent " + _agentName_1) + "] Box at ") + _position_1) + " is on exit, marked as completed"));
          }
        }
      }
    }
  }

  /**
   * Make autonomous decision based on perception - DECENTRALIZED BEHAVIOR.
   */
  protected void decideAction(final List<sokobanObject> perceivedObjects) {
    if (this.actionEmittedThisStep) {
      return;
    }
    if (((this.allBoxPositions.isEmpty() && this.allExitPositions.isEmpty()) && (this.lastPerceptionTime == 0))) {
      String _agentName = this.getAgentName();
      System.out.println((("[Agent " + _agentName) + "] Waiting for InitialKnowledge..."));
      return;
    }
    String _agentName_1 = this.getAgentName();
    System.out.println(((((((((((("[Agent " + _agentName_1) + "] decideAction: phase=") + this.agentBehaviorPhase) + ", claimedBox=") + this.myClaimedBox) + ", activeBoxTask=") + this.activeBoxTask) + ", position=") + this.myPosition) + ", targetExit=") + this.myTargetExit));
    boolean _equals = Objects.equals(this.agentBehaviorPhase, "PLANNING");
    if (_equals) {
      String _agentName_2 = this.getAgentName();
      System.out.println((("[Agent " + _agentName_2) + "] decideAction: Entering PLANNING phase handler"));
      this.handlePlanning(perceivedObjects);
      return;
    }
    boolean _equals_1 = Objects.equals(this.agentBehaviorPhase, "ALIGNMENT");
    if (_equals_1) {
      String _agentName_3 = this.getAgentName();
      System.out.println((("[Agent " + _agentName_3) + "] decideAction: Entering ALIGNMENT phase handler"));
      this.handleAlignment(perceivedObjects);
      return;
    }
    boolean _equals_2 = Objects.equals(this.agentBehaviorPhase, "PUSH_EXECUTION");
    if (_equals_2) {
      String _agentName_4 = this.getAgentName();
      System.out.println((("[Agent " + _agentName_4) + "] decideAction: Entering PUSH_EXECUTION phase handler"));
      this.handlePushExecution(perceivedObjects);
      return;
    }
    boolean _equals_3 = Objects.equals(this.agentBehaviorPhase, "TASK_COMPLETION");
    if (_equals_3) {
      this.handleTaskCompletion(perceivedObjects);
      return;
    }
  }

  /**
   * PHASE 0: PLANNING - Evaluate all boxes and emit path proposals.
   */
  protected void handlePlanning(final List<sokobanObject> perceivedObjects) {
    boolean _notEquals = (!Objects.equals(this.agentBehaviorPhase, "PLANNING"));
    if (_notEquals) {
      String _agentName = this.getAgentName();
      System.out.println((((("[Agent " + _agentName) + "] handlePlanning: Skipping - phase is ") + this.agentBehaviorPhase) + " (not PLANNING)"));
      return;
    }
    if ((!this.proposalsEmitted)) {
      this.myProposals.clear();
      this.pathProposals.clear();
      this.negotiationTimeout = 0;
      this.coordinationActive = true;
      this.proposalsEmitted = true;
      int availableBoxes = 0;
      for (final Point2i boxPos : this.allBoxPositions) {
        if (((!this.completedBoxes.contains(boxPos)) && (!this.claimedBoxes.contains(boxPos)))) {
          availableBoxes++;
        }
      }
      String _agentName_1 = this.getAgentName();
      System.out.println((((("[Agent " + _agentName_1) + "] PLANNING: Starting coordination cycle - evaluating ") + Integer.valueOf(availableBoxes)) + " available box(es)..."));
      this.syncBoxPositionsWithPerceptions(perceivedObjects);
      for (final Point2i boxPos_1 : this.allBoxPositions) {
        {
          boolean _contains = this.completedBoxes.contains(boxPos_1);
          if (_contains) {
            continue;
          }
          boolean _contains_1 = this.claimedBoxes.contains(boxPos_1);
          if (_contains_1) {
            continue;
          }
          sokobanObject boxObj = this.findObjectAt(perceivedObjects, boxPos_1);
          if (((boxObj instanceof BoxObject) && ((BoxObject) boxObj).isOnTarget())) {
            continue;
          }
          boolean _isNextToOuterBorder = this.isNextToOuterBorder(boxPos_1);
          if (_isNextToOuterBorder) {
            continue;
          }
          Point2i verifiedBoxPos = boxPos_1;
          sokobanObject boxObjInPerception = this.findObjectAt(perceivedObjects, boxPos_1);
          if (((boxObjInPerception == null) || (!(boxObjInPerception instanceof BoxObject)))) {
            Point2i nearbyBox = this.findCurrentBoxPosition(boxPos_1, perceivedObjects);
            if ((nearbyBox != null)) {
              verifiedBoxPos = nearbyBox;
              String _agentName_2 = this.getAgentName();
              System.out.println(((((("[Agent " + _agentName_2) + "] PLANNING: Box at ") + boxPos_1) + " not in perceptions, using nearby position ") + nearbyBox));
            } else {
              String _agentName_3 = this.getAgentName();
              System.out.println((((("[Agent " + _agentName_3) + "] PLANNING: Box at ") + boxPos_1) + " not in perceptions, using global knowledge position"));
            }
          }
          Point2i exitPos = this.findNearestExit(verifiedBoxPos);
          if ((exitPos == null)) {
            continue;
          }
          LinkedList<Point2i> path = this.planBoxPath(verifiedBoxPos, exitPos, perceivedObjects);
          boolean _isEmpty = path.isEmpty();
          if (_isEmpty) {
            continue;
          }
          int pathLength = path.size();
          int alignmentCost = this.calculateDistance(this.myPosition, verifiedBoxPos);
          int estimatedSteps = (alignmentCost + pathLength);
          double efficiencyScore = this.calculateEfficiencyScore(this.myPosition, verifiedBoxPos, exitPos, pathLength);
          UUID _iD = this.getID();
          PathProposal proposal = new PathProposal(_iD, verifiedBoxPos, exitPos, pathLength, efficiencyScore, estimatedSteps);
          this.myProposals.put(verifiedBoxPos, proposal);
          DefaultContextInteractions _$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
          _$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(proposal);
          String _agentName_4 = this.getAgentName();
          System.out.println((((((((((("[Agent " + _agentName_4) + "] PLANNING: Proposed box at ") + boxPos_1) + " -> exit ") + exitPos) + " (score: ") + Double.valueOf(efficiencyScore)) + ", steps: ") + Integer.valueOf(estimatedSteps)) + ")"));
        }
      }
      this.negotiationTimeout++;
      String _agentName_2 = this.getAgentName();
      int _size = this.myProposals.size();
      System.out.println((((((("[Agent " + _agentName_2) + "] PLANNING: Emitted ") + Integer.valueOf(_size)) + " proposals, waiting for responses (timeout: ") + Integer.valueOf(this.negotiationTimeout)) + "/2)"));
      return;
    }
    this.negotiationTimeout++;
    if ((this.negotiationTimeout < 2)) {
      String _agentName_3 = this.getAgentName();
      int _size_1 = this.pathProposals.size();
      System.out.println((((((("[Agent " + _agentName_3) + "] PLANNING: Waiting for proposals (timeout: ") + Integer.valueOf(this.negotiationTimeout)) + "/2, received: ") + Integer.valueOf(_size_1)) + ")"));
      return;
    }
    String _agentName_4 = this.getAgentName();
    int _size_2 = this.myProposals.size();
    int _size_3 = this.pathProposals.size();
    System.out.println((((((("[Agent " + _agentName_4) + "] PLANNING: Processing proposals (my proposals: ") + Integer.valueOf(_size_2)) + ", received: ") + Integer.valueOf(_size_3)) + ")"));
    HashMap<Point2i, List<PathProposal>> proposalsByBox = CollectionLiterals.<Point2i, List<PathProposal>>newHashMap();
    Set<Point2i> _keySet = this.myProposals.keySet();
    for (final Point2i boxPos_2 : _keySet) {
      {
        PathProposal myProposal = this.myProposals.get(boxPos_2);
        ArrayList<PathProposal> proposals = CollectionLiterals.<PathProposal>newArrayList();
        proposals.add(myProposal);
        proposalsByBox.put(boxPos_2, proposals);
      }
    }
    for (final PathProposal proposal : this.pathProposals) {
      {
        Point2i boxPos_3 = proposal.boxPosition;
        boolean _containsKey = proposalsByBox.containsKey(boxPos_3);
        if ((!_containsKey)) {
          proposalsByBox.put(boxPos_3, CollectionLiterals.<PathProposal>newArrayList());
        }
        proposalsByBox.get(boxPos_3).add(proposal);
      }
    }
    HashMap<Point2i, PathProposal> bestProposalsByBox = CollectionLiterals.<Point2i, PathProposal>newHashMap();
    Set<Point2i> _keySet_1 = proposalsByBox.keySet();
    for (final Point2i boxPos_3 : _keySet_1) {
      {
        List<PathProposal> proposals = proposalsByBox.get(boxPos_3);
        boolean _isEmpty = proposals.isEmpty();
        if (_isEmpty) {
          continue;
        }
        PathProposal bestProposal = null;
        for (final PathProposal proposal_1 : proposals) {
          if ((bestProposal == null)) {
            bestProposal = proposal_1;
          } else {
            if ((proposal_1.efficiencyScore < bestProposal.efficiencyScore)) {
              bestProposal = proposal_1;
            } else {
              if ((proposal_1.efficiencyScore == bestProposal.efficiencyScore)) {
                int _compareTo = proposal_1.agentId.compareTo(bestProposal.agentId);
                if ((_compareTo > 0)) {
                  bestProposal = proposal_1;
                }
              }
            }
          }
        }
        if ((bestProposal != null)) {
          bestProposalsByBox.put(boxPos_3, bestProposal);
        }
      }
    }
    HashMap<Point2i, List<UUID>> boxAssignments = CollectionLiterals.<Point2i, List<UUID>>newHashMap();
    HashMap<UUID, Point2i> agentAssignments = CollectionLiterals.<UUID, Point2i>newHashMap();
    HashSet<Point2i> usedExits = CollectionLiterals.<Point2i>newHashSet();
    HashSet<Point2i> availableBoxes_1 = CollectionLiterals.<Point2i>newHashSet();
    Set<Point2i> _keySet_2 = bestProposalsByBox.keySet();
    for (final Point2i boxPos_4 : _keySet_2) {
      {
        boolean _contains = this.claimedBoxes.contains(boxPos_4);
        if (_contains) {
          String _agentName_5 = this.getAgentName();
          System.out.println((((("[Agent " + _agentName_5) + "] PLANNING: Skipping box at ") + boxPos_4) + " - already claimed"));
          continue;
        }
        boolean _contains_1 = this.completedBoxes.contains(boxPos_4);
        if (_contains_1) {
          continue;
        }
        availableBoxes_1.add(boxPos_4);
      }
    }
    final Function1<Point2i, Double> _function = (Point2i boxPos_5) -> {
      double _xblockexpression = (double) 0;
      {
        PathProposal proposal_1 = bestProposalsByBox.get(boxPos_5);
        _xblockexpression = proposal_1.efficiencyScore;
      }
      return Double.valueOf(_xblockexpression);
    };
    List<Point2i> sortedBoxes = IterableExtensions.<Point2i, Double>sortBy(availableBoxes_1, _function);
    for (final Point2i boxPos_5 : sortedBoxes) {
      {
        boolean _containsKey = boxAssignments.containsKey(boxPos_5);
        if (_containsKey) {
          continue;
        }
        List<PathProposal> proposals = proposalsByBox.get(boxPos_5);
        if (((proposals == null) || proposals.isEmpty())) {
          continue;
        }
        ArrayList<UUID> selectedAgents = CollectionLiterals.<UUID>newArrayList();
        final Function1<PathProposal, Double> _function_1 = (PathProposal it) -> {
          return Double.valueOf(it.efficiencyScore);
        };
        List<PathProposal> sortedProposals = IterableExtensions.<PathProposal, Double>sortBy(proposals, _function_1);
        for (final PathProposal proposal_1 : sortedProposals) {
          {
            int _size_4 = selectedAgents.size();
            if ((_size_4 >= 2)) {
              break;
            }
            boolean _containsKey_1 = agentAssignments.containsKey(proposal_1.agentId);
            if ((!_containsKey_1)) {
              selectedAgents.add(proposal_1.agentId);
              agentAssignments.put(proposal_1.agentId, boxPos_5);
            }
          }
        }
        boolean _isEmpty = selectedAgents.isEmpty();
        if ((!_isEmpty)) {
          boxAssignments.put(boxPos_5, selectedAgents);
          PathProposal bestProposal = bestProposalsByBox.get(boxPos_5);
          usedExits.add(bestProposal.exitPosition);
          int _size_4 = selectedAgents.size();
          if ((_size_4 < 2)) {
            String _agentName_5 = this.getAgentName();
            int _size_5 = selectedAgents.size();
            System.out.println((((((("[Agent " + _agentName_5) + "] PLANNING: Only ") + Integer.valueOf(_size_5)) + " agent(s) assigned to box at ") + boxPos_5) + " (wanted 2)"));
          } else {
            String _agentName_6 = this.getAgentName();
            System.out.println(((((("[Agent " + _agentName_6) + "] PLANNING: Successfully assigned 2 agents to box at ") + boxPos_5) + ": ") + selectedAgents));
          }
        }
      }
    }
    boolean _containsKey = agentAssignments.containsKey(this.getID());
    if (_containsKey) {
      Point2i myAssignedBox = agentAssignments.get(this.getID());
      List<UUID> teamMembersList = boxAssignments.get(myAssignedBox);
      PathProposal bestProposal = bestProposalsByBox.get(myAssignedBox);
      int myIndex = teamMembersList.indexOf(this.getID());
      String _xifexpression = null;
      if ((myIndex == 0)) {
        _xifexpression = "PUSHER";
      } else {
        _xifexpression = "HELPER";
      }
      String myRole = _xifexpression;
      LinkedList<Point2i> plannedPath = this.planBoxPath(myAssignedBox, bestProposal.exitPosition, perceivedObjects);
      boolean _isEmpty = plannedPath.isEmpty();
      if (_isEmpty) {
        String _agentName_5 = this.getAgentName();
        System.out.println(((((("[Agent " + _agentName_5) + "] PLANNING: WARNING - Planned path is empty for box at ") + myAssignedBox) + " -> exit ") + bestProposal.exitPosition));
      } else {
        String _agentName_6 = this.getAgentName();
        int _size_4 = plannedPath.size();
        System.out.println(((((((("[Agent " + _agentName_6) + "] PLANNING: Planned path has ") + Integer.valueOf(_size_4)) + " steps from ") + myAssignedBox) + " to ") + bestProposal.exitPosition));
        String _agentName_7 = this.getAgentName();
        System.out.println(((("[Agent " + _agentName_7) + "] PLANNING: Path: ") + plannedPath));
      }
      this.myClaimedBox = myAssignedBox;
      this.myTargetExit = bestProposal.exitPosition;
      this.plannedBoxPath = plannedPath;
      this.currentPathStep = 0;
      boolean _isEmpty_1 = this.plannedBoxPath.isEmpty();
      this.pathPlanned = (!_isEmpty_1);
      this.claimedBoxes.add(myAssignedBox);
      this.assignedExits.add(this.myTargetExit);
      this.teamMembers.clear();
      this.teamMembers.addAll(teamMembersList);
      this.myTaskRole = myRole;
      LinkedList<Point2i> pathCopy = CollectionLiterals.<Point2i>newLinkedList();
      pathCopy.addAll(this.plannedBoxPath);
      String _agentName_8 = this.getAgentName();
      int _size_5 = pathCopy.size();
      System.out.println(((((("[Agent " + _agentName_8) + "] PLANNING: Creating path copy with ") + Integer.valueOf(_size_5)) + " steps: ") + pathCopy));
      DefaultContextInteractions _$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
      UUID _iD = this.getID();
      BoxAssignment _boxAssignment = new BoxAssignment(myAssignedBox, _iD, this.myTargetExit, pathCopy);
      _$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_boxAssignment);
      DefaultContextInteractions _$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1 = this.$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
      CollaborativeBoxTask _collaborativeBoxTask = new CollaborativeBoxTask(myAssignedBox, teamMembersList, bestProposal.exitPosition, pathCopy);
      _$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1.emit(_collaborativeBoxTask);
      String _agentName_9 = this.getAgentName();
      int _size_6 = pathCopy.size();
      System.out.println((((("[Agent " + _agentName_9) + "] PLANNING: Emitted CollaborativeBoxTask with path of ") + Integer.valueOf(_size_6)) + " steps"));
      boolean _isEmpty_2 = this.plannedBoxPath.isEmpty();
      if ((!_isEmpty_2)) {
        DefaultContextInteractions _$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_2 = this.$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
        UUID _iD_1 = this.getID();
        PathVisualization _pathVisualization = new PathVisualization(myAssignedBox, this.myTargetExit, this.plannedBoxPath, _iD_1);
        _$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_2.emit(_pathVisualization);
      }
      String _agentName_10 = this.getAgentName();
      int _size_7 = teamMembersList.size();
      System.out.println((((((((("[Agent " + _agentName_10) + "] PLANNING: Assigned to box at ") + myAssignedBox) + " as ") + myRole) + " (team: ") + Integer.valueOf(_size_7)) + " agents)"));
      this.agentBehaviorPhase = "ALIGNMENT";
      this.proposalsEmitted = false;
      this.alignmentSteps = 0;
      this.lastAlignmentPosition = null;
      this.recentPositions.clear();
      String _agentName_11 = this.getAgentName();
      System.out.println((("[Agent " + _agentName_11) + "] Transitioned to ALIGNMENT phase - will start pushing once in push position"));
      if ((!this.actionEmittedThisStep)) {
        Direction direction = this.calculateDirectionToTarget(myAssignedBox, perceivedObjects);
        if ((direction != null)) {
          this.emitAction(direction);
          String _agentName_12 = this.getAgentName();
          System.out.println((("[Agent " + _agentName_12) + "] PLANNING: Emitting action after transitioning to ALIGNMENT"));
        } else {
          Direction greedyDir = this.calculateDirectionToTargetGreedy(myAssignedBox);
          if ((greedyDir != null)) {
            this.emitAction(greedyDir);
            String _agentName_13 = this.getAgentName();
            System.out.println((("[Agent " + _agentName_13) + "] PLANNING: Emitting greedy action after transitioning to ALIGNMENT"));
          }
        }
      }
      return;
    } else {
      String _agentName_14 = this.getAgentName();
      int _size_8 = agentAssignments.size();
      System.out.println((((("[Agent " + _agentName_14) + "] PLANNING: No box assigned to me, continuing coordination (assigned boxes: ") + Integer.valueOf(_size_8)) + ")"));
      this.proposalsEmitted = false;
      return;
    }
  }

  /**
   * PHASE 1: BOX_SELECTION - Select nearest unclaimed box.
   */
  protected void handleBoxSelection(final List<sokobanObject> perceivedObjects) {
    if ((this.myClaimedBox != null)) {
      this.agentBehaviorPhase = "ALIGNMENT";
      return;
    }
    Point2i nearestBox = null;
    int minDistance = Integer.MAX_VALUE;
    for (final Point2i boxPos : this.allBoxPositions) {
      {
        boolean _contains = this.completedBoxes.contains(boxPos);
        if (_contains) {
          continue;
        }
        boolean _contains_1 = this.claimedBoxes.contains(boxPos);
        if (_contains_1) {
          continue;
        }
        Integer releaseTime = this.recentlyReleasedBoxes.get(boxPos);
        if ((releaseTime != null)) {
          int stepsSinceRelease = (this.lastPerceptionTime - ((releaseTime) == null ? 0 : (releaseTime).intValue()));
          if ((stepsSinceRelease < this.releaseCooldownSteps)) {
            continue;
          } else {
            this.recentlyReleasedBoxes.remove(boxPos);
          }
        }
        sokobanObject boxObj = this.findObjectAt(perceivedObjects, boxPos);
        if (((boxObj instanceof BoxObject) && ((BoxObject) boxObj).isOnTarget())) {
          continue;
        }
        boolean _isNextToOuterBorder = this.isNextToOuterBorder(boxPos);
        if (_isNextToOuterBorder) {
          continue;
        }
        int distance = this.calculateDistance(this.myPosition, boxPos);
        if ((distance < minDistance)) {
          minDistance = distance;
          nearestBox = boxPos;
        }
      }
    }
    boolean boxAlreadyBeingPushed = false;
    if ((nearestBox != null)) {
      Set<UUID> _keySet = this.otherAgentPhases.keySet();
      for (final UUID otherAgentId : _keySet) {
        {
          String otherPhase = this.otherAgentPhases.get(otherAgentId);
          Point2i otherTargetBox = this.otherAgentTargets.get(otherAgentId);
          if ((((otherPhase == "PUSH_EXECUTION") || (otherPhase == "ALIGNMENT")) && (otherTargetBox != null))) {
            int distance = this.calculateDistance(otherTargetBox, nearestBox);
            if ((distance == 0)) {
              boxAlreadyBeingPushed = true;
              String _agentName = this.getAgentName();
              System.out.println((((((((("[Agent " + _agentName) + "] Box at ") + nearestBox) + " is being worked on by agent ") + otherAgentId) + " (phase: ") + otherPhase) + "), skipping"));
              break;
            }
          }
        }
      }
      if ((!boxAlreadyBeingPushed)) {
        this.myClaimedBox = nearestBox;
        this.claimedBoxes.add(nearestBox);
        this.myTargetExit = this.findNearestExit(nearestBox);
        this.plannedBoxPath = this.planBoxPath(nearestBox, this.myTargetExit, perceivedObjects);
        this.currentPathStep = 0;
        boolean _isEmpty = this.plannedBoxPath.isEmpty();
        this.pathPlanned = (!_isEmpty);
        if (this.pathPlanned) {
          String _agentName = this.getAgentName();
          int _size = this.plannedBoxPath.size();
          System.out.println(((((((("[Agent " + _agentName) + "] Planned path with ") + Integer.valueOf(_size)) + " steps from ") + nearestBox) + " to ") + this.myTargetExit));
        } else {
          String _agentName_1 = this.getAgentName();
          System.out.println((((((("[Agent " + _agentName_1) + "] No path found from ") + nearestBox) + " to ") + this.myTargetExit) + ", using greedy approach"));
        }
        DefaultContextInteractions _$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
        UUID _iD = this.getID();
        BoxClaimed _boxClaimed = new BoxClaimed(nearestBox, _iD);
        _$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_boxClaimed);
        String _agentName_2 = this.getAgentName();
        System.out.println(((((("[Agent " + _agentName_2) + "] Selected box at ") + nearestBox) + ", target exit: ") + this.myTargetExit));
        this.agentBehaviorPhase = "ALIGNMENT";
      } else {
        String _agentName_3 = this.getAgentName();
        System.out.println((((("[Agent " + _agentName_3) + "] Skipping box at ") + nearestBox) + " (being pushed), continuing search"));
      }
    }
    if (((nearestBox == null) || boxAlreadyBeingPushed)) {
      String _agentName_4 = this.getAgentName();
      System.out.println((("[Agent " + _agentName_4) + "] No unclaimed boxes available"));
      boolean _isEmpty_1 = this.allExitPositions.isEmpty();
      if ((!_isEmpty_1)) {
        Point2i nearestExit = this.findNearestExit(this.myPosition);
        if ((nearestExit != null)) {
          Direction direction = this.calculateDirectionToTarget(nearestExit, perceivedObjects);
          if ((direction != null)) {
            this.emitAction(direction);
          }
        }
      }
    }
  }

  /**
   * PHASE 2: ALIGNMENT - Navigate to push position behind box.
   */
  protected void handleAlignment(final List<sokobanObject> perceivedObjects) {
    String _agentName = this.getAgentName();
    System.out.println((((((((("[Agent " + _agentName) + "] ALIGNMENT: Starting alignment phase (claimedBox=") + this.myClaimedBox) + ", targetExit=") + this.myTargetExit) + ", position=") + this.myPosition) + ")"));
    if (((this.myClaimedBox == null) || (this.myTargetExit == null))) {
      String _agentName_1 = this.getAgentName();
      System.out.println((("[Agent " + _agentName_1) + "] ALIGNMENT: Lost claim or target, returning to BOX_SELECTION"));
      this.agentBehaviorPhase = "BOX_SELECTION";
      return;
    }
    boolean boxIsCompleted = false;
    boolean _contains = this.completedBoxes.contains(this.myClaimedBox);
    if (_contains) {
      boxIsCompleted = true;
      String _agentName_2 = this.getAgentName();
      System.out.println((((("[Agent " + _agentName_2) + "] Box at ") + this.myClaimedBox) + " is in completedBoxes, aborting alignment"));
    }
    if ((!boxIsCompleted)) {
      sokobanObject boxObj = this.findObjectAt(perceivedObjects, this.myClaimedBox);
      if (((boxObj instanceof BoxObject) && ((BoxObject) boxObj).isOnTarget())) {
        boxIsCompleted = true;
        String _agentName_3 = this.getAgentName();
        System.out.println((((("[Agent " + _agentName_3) + "] Box at ") + this.myClaimedBox) + " is onTarget in perceptions, aborting alignment"));
      }
    }
    if (((!boxIsCompleted) && this.allExitPositions.contains(this.myClaimedBox))) {
      boxIsCompleted = true;
      String _agentName_4 = this.getAgentName();
      System.out.println((((("[Agent " + _agentName_4) + "] Box at ") + this.myClaimedBox) + " is at exit position, aborting alignment"));
    }
    if (boxIsCompleted) {
      String _agentName_5 = this.getAgentName();
      System.out.println((("[Agent " + _agentName_5) + "] Box is completed, releasing claim and returning to BOX_SELECTION"));
      this.claimedBoxes.remove(this.myClaimedBox);
      this.myClaimedBox = null;
      this.myTargetExit = null;
      this.myPushPosition = null;
      this.lastBoxPosition = null;
      this.plannedBoxPath.clear();
      this.currentPathStep = 0;
      this.pathPlanned = false;
      this.cleanupTeamState();
      this.agentBehaviorPhase = "BOX_SELECTION";
      return;
    }
    Point2i currentBoxPos = this.findCurrentBoxPosition(this.myClaimedBox, perceivedObjects);
    String _agentName_6 = this.getAgentName();
    String _plus = ((((((("[Agent " + _agentName_6) + "] ALIGNMENT: Found box at ") + currentBoxPos) + " (claimed: ") + this.myClaimedBox) + ", in perceptions: ") + Boolean.valueOf(((currentBoxPos != null) && currentBoxPos.equals(this.myClaimedBox))));
    System.out.println((_plus + ")"));
    if ((currentBoxPos != null)) {
      Set<UUID> _keySet = this.otherAgentPhases.keySet();
      for (final UUID otherAgentId : _keySet) {
        {
          String otherPhase = this.otherAgentPhases.get(otherAgentId);
          Point2i otherTargetBox = this.otherAgentTargets.get(otherAgentId);
          if ((((otherPhase == "PUSH_EXECUTION") || (otherPhase == "ALIGNMENT")) && (otherTargetBox != null))) {
            int distance = this.calculateDistance(otherTargetBox, currentBoxPos);
            if ((distance == 0)) {
              String _agentName_7 = this.getAgentName();
              System.out.println((((((((("[Agent " + _agentName_7) + "] Conflict detected in ALIGNMENT: agent ") + otherAgentId) + " is ") + otherPhase) + " for box at ") + currentBoxPos) + ", releasing claim"));
              this.claimedBoxes.remove(this.myClaimedBox);
              this.myClaimedBox = null;
              this.myTargetExit = null;
              this.myPushPosition = null;
              this.lastBoxPosition = null;
              this.plannedBoxPath.clear();
              this.currentPathStep = 0;
              this.pathPlanned = false;
              this.cleanupTeamState();
              this.agentBehaviorPhase = "BOX_SELECTION";
              return;
            }
          }
        }
      }
    }
    if ((currentBoxPos == null)) {
      String _agentName_7 = this.getAgentName();
      System.out.println((("[Agent " + _agentName_7) + "] ALIGNMENT: Box not found in perceptions, checking global knowledge..."));
      boolean _contains_1 = this.allBoxPositions.contains(this.myClaimedBox);
      if (_contains_1) {
        currentBoxPos = this.myClaimedBox;
        String _agentName_8 = this.getAgentName();
        int _calculateDistance = this.calculateDistance(this.myPosition, currentBoxPos);
        System.out.println((((((("[Agent " + _agentName_8) + "] ALIGNMENT: Using global knowledge position: ") + currentBoxPos) + " (distance from agent: ") + Integer.valueOf(_calculateDistance)) + ")"));
      } else {
        String _agentName_9 = this.getAgentName();
        System.out.println((("[Agent " + _agentName_9) + "] ALIGNMENT: Box not in global knowledge, checking if at exit..."));
        boolean boxAtExit = this.checkBoxAtExit(perceivedObjects);
        if (boxAtExit) {
          String _agentName_10 = this.getAgentName();
          System.out.println((("[Agent " + _agentName_10) + "] ALIGNMENT: Box is at exit, transitioning to TASK_COMPLETION"));
          this.agentBehaviorPhase = "TASK_COMPLETION";
          return;
        }
        String _agentName_11 = this.getAgentName();
        System.out.println((("[Agent " + _agentName_11) + "] ALIGNMENT: Box not found in perceptions or global knowledge, releasing claim"));
        if ((this.myClaimedBox != null)) {
          this.claimedBoxes.remove(this.myClaimedBox);
        }
        this.myClaimedBox = null;
        this.myTargetExit = null;
        this.plannedBoxPath.clear();
        this.currentPathStep = 0;
        this.pathPlanned = false;
        this.agentBehaviorPhase = "PLANNING";
        this.proposalsEmitted = false;
        return;
      }
    }
    if (((currentBoxPos.getX() != this.myClaimedBox.getX()) || (currentBoxPos.getY() != this.myClaimedBox.getY()))) {
      Point2i oldClaimedPos = this.myClaimedBox;
      this.myClaimedBox = currentBoxPos;
      int _x = oldClaimedPos.getX();
      int _y = oldClaimedPos.getY();
      Point2i _point2i = new Point2i(_x, _y);
      this.allBoxPositions.remove(_point2i);
      this.allBoxPositions.add(currentBoxPos);
      boolean _contains_2 = this.claimedBoxes.contains(oldClaimedPos);
      if (_contains_2) {
        this.claimedBoxes.remove(oldClaimedPos);
      }
      boolean _contains_3 = this.claimedBoxes.contains(currentBoxPos);
      if ((!_contains_3)) {
        this.claimedBoxes.add(currentBoxPos);
      }
      String _agentName_12 = this.getAgentName();
      System.out.println((((((("[Agent " + _agentName_12) + "] ALIGNMENT: Box moved from ") + oldClaimedPos) + " to ") + currentBoxPos) + ", updated claimedBoxes"));
      if ((this.myTargetExit != null)) {
        LinkedList<Point2i> newPath = this.planBoxPath(currentBoxPos, this.myTargetExit, perceivedObjects);
        boolean _isEmpty = newPath.isEmpty();
        if ((!_isEmpty)) {
          this.plannedBoxPath = newPath;
          this.currentPathStep = 0;
          this.pathPlanned = true;
          String _agentName_13 = this.getAgentName();
          int _size = newPath.size();
          System.out.println((((("[Agent " + _agentName_13) + "] ALIGNMENT: Recalculated path from new box position with ") + Integer.valueOf(_size)) + " steps"));
        } else {
          this.pathPlanned = false;
          this.plannedBoxPath.clear();
          this.currentPathStep = 0;
          String _agentName_14 = this.getAgentName();
          System.out.println(((("[Agent " + _agentName_14) + "] ALIGNMENT: WARNING - No path found from new box position ") + currentBoxPos));
        }
      }
    }
    if ((currentBoxPos != null)) {
      Set<UUID> _keySet_1 = this.otherAgentTargets.keySet();
      for (final UUID otherAgentId_1 : _keySet_1) {
        boolean _equals = otherAgentId_1.equals(this.getID());
        if ((!_equals)) {
          Point2i otherTargetBox = this.otherAgentTargets.get(otherAgentId_1);
          if ((otherTargetBox != null)) {
            int distance = this.calculateDistance(otherTargetBox, currentBoxPos);
            if ((distance <= 1)) {
              String _agentName_15 = this.getAgentName();
              System.out.println((((((((("[Agent " + _agentName_15) + "] ALIGNMENT: Conflict detected - agent ") + otherAgentId_1) + " is working on box at ") + currentBoxPos) + " (their target: ") + otherTargetBox) + "), releasing claim"));
              this.claimedBoxes.remove(this.myClaimedBox);
              this.myClaimedBox = null;
              this.myTargetExit = null;
              this.myPushPosition = null;
              this.plannedBoxPath.clear();
              this.currentPathStep = 0;
              this.pathPlanned = false;
              this.lastBoxPosition = null;
              this.alignmentSteps = 0;
              this.lastAlignmentPosition = null;
              this.recentPositions.clear();
              this.agentBehaviorPhase = "PLANNING";
              this.proposalsEmitted = false;
              return;
            }
          }
        }
      }
    }
    if (((this.teamMembers.size() > 1) && (this.myTaskRole == "HELPER"))) {
      Direction pushDir = this.calculatePushDirection(currentBoxPos, this.myTargetExit);
      if ((pushDir != null)) {
        Point2i helpPosition = this.calculateHelperPosition(currentBoxPos, pushDir, perceivedObjects);
        if ((helpPosition != null)) {
          boolean helpPosBlocked = false;
          Set<UUID> _keySet_2 = this.otherAgentPositions.keySet();
          for (final UUID otherAgentId_2 : _keySet_2) {
            {
              Point2i otherAgentPos = this.otherAgentPositions.get(otherAgentId_2);
              if ((((otherAgentPos != null) && (otherAgentPos.getX() == helpPosition.getX())) && (otherAgentPos.getY() == helpPosition.getY()))) {
                helpPosBlocked = true;
                String _agentName_16 = this.getAgentName();
                System.out.println((((((("[Agent " + _agentName_16) + "] HELPER: Help position ") + helpPosition) + " is blocked by agent ") + otherAgentId_2) + ", finding alternative..."));
                Point2i altHelpPos = this.calculateAlternativeHelperPosition(currentBoxPos, pushDir, helpPosition, perceivedObjects);
                if ((altHelpPos != null)) {
                  helpPosition = altHelpPos;
                  helpPosBlocked = false;
                  String _agentName_17 = this.getAgentName();
                  System.out.println(((("[Agent " + _agentName_17) + "] HELPER: Using alternative help position: ") + altHelpPos));
                }
                break;
              }
            }
          }
          if ((!helpPosBlocked)) {
            int distanceToHelp = this.calculateDistance(this.myPosition, helpPosition);
            if ((distanceToHelp > 0)) {
              Direction direction = this.calculateDirectionToTarget(helpPosition, perceivedObjects);
              if ((direction != null)) {
                Point2i nextPos = this.calculateNextPosition(this.myPosition, direction);
                sokobanObject nextPosObj = this.findObjectAt(perceivedObjects, nextPos);
                if (((nextPosObj == null) || (nextPosObj instanceof ExitObject))) {
                  this.emitAction(direction);
                  String _agentName_16 = this.getAgentName();
                  System.out.println((((((((("[Agent " + _agentName_16) + "] HELPER: Moving toward help position ") + helpPosition) + " (distance=") + Integer.valueOf(distanceToHelp)) + ", direction=") + direction) + ")"));
                  return;
                } else {
                  Direction altDir = this.findAlternativeDirectionToTarget(helpPosition, direction, perceivedObjects);
                  if ((altDir != null)) {
                    this.emitAction(altDir);
                    String _agentName_17 = this.getAgentName();
                    System.out.println((((("[Agent " + _agentName_17) + "] HELPER: Using alternative direction ") + altDir) + " to reach help position"));
                    return;
                  } else {
                    String _agentName_18 = this.getAgentName();
                    System.out.println((("[Agent " + _agentName_18) + "] HELPER: Cannot reach help position, waiting..."));
                    return;
                  }
                }
              } else {
                String _agentName_19 = this.getAgentName();
                System.out.println(((("[Agent " + _agentName_19) + "] HELPER: Cannot find path to help position ") + helpPosition));
                if ((distanceToHelp == 1)) {
                  int _x_1 = helpPosition.getX();
                  int _x_2 = this.myPosition.getX();
                  int dx = (_x_1 - _x_2);
                  int _y_1 = helpPosition.getY();
                  int _y_2 = this.myPosition.getY();
                  int dy = (_y_1 - _y_2);
                  Direction directDir = null;
                  if ((dx > 0)) {
                    directDir = Direction.EAST;
                  } else {
                    if ((dx < 0)) {
                      directDir = Direction.WEST;
                    } else {
                      if ((dy > 0)) {
                        directDir = Direction.SOUTH;
                      } else {
                        if ((dy < 0)) {
                          directDir = Direction.NORTH;
                        }
                      }
                    }
                  }
                  if ((directDir != null)) {
                    Point2i nextPos_1 = this.calculateNextPosition(this.myPosition, directDir);
                    sokobanObject nextPosObj_1 = this.findObjectAt(perceivedObjects, nextPos_1);
                    if (((nextPosObj_1 == null) || (nextPosObj_1 instanceof ExitObject))) {
                      this.emitAction(directDir);
                      String _agentName_20 = this.getAgentName();
                      System.out.println(((("[Agent " + _agentName_20) + "] HELPER: Using direct movement to help position: ") + directDir));
                      return;
                    }
                  }
                }
              }
            } else {
              String _agentName_21 = this.getAgentName();
              System.out.println((("[Agent " + _agentName_21) + "] HELPER: In help position, transitioning to PUSH_EXECUTION"));
              this.agentBehaviorPhase = "PUSH_EXECUTION";
              this.handlePushExecution(perceivedObjects);
              return;
            }
          } else {
            String _agentName_22 = this.getAgentName();
            System.out.println((("[Agent " + _agentName_22) + "] HELPER: Help position blocked, waiting..."));
            return;
          }
        } else {
          String _agentName_23 = this.getAgentName();
          System.out.println((("[Agent " + _agentName_23) + "] HELPER: Cannot calculate help position, transitioning to PUSH_EXECUTION"));
          this.agentBehaviorPhase = "PUSH_EXECUTION";
          this.handlePushExecution(perceivedObjects);
          return;
        }
      }
    }
    boolean shouldRecalculate = (this.myPushPosition == null);
    if (((!shouldRecalculate) && (this.lastBoxPosition != null))) {
      int boxMovement = this.calculateDistance(currentBoxPos, this.lastBoxPosition);
      shouldRecalculate = (boxMovement > 1);
    }
    if (shouldRecalculate) {
      this.calculatePushPositionForBox(currentBoxPos, this.myTargetExit);
      String _agentName_24 = this.getAgentName();
      System.out.println((((((((("[Agent " + _agentName_24) + "] ALIGNMENT: Calculated push position: ") + this.myPushPosition) + " (box at ") + currentBoxPos) + ", exit at ") + this.myTargetExit) + ")"));
    } else {
      String _agentName_25 = this.getAgentName();
      System.out.println((((((("[Agent " + _agentName_25) + "] ALIGNMENT: Using cached push position: ") + this.myPushPosition) + " (box at ") + currentBoxPos) + ")"));
    }
    if ((this.myPushPosition != null)) {
      Set<UUID> _keySet_3 = this.otherAgentPositions.keySet();
      for (final UUID otherAgentId_3 : _keySet_3) {
        {
          Point2i otherAgentPos = this.otherAgentPositions.get(otherAgentId_3);
          if ((((otherAgentPos != null) && (otherAgentPos.getX() == this.myPushPosition.getX())) && (otherAgentPos.getY() == this.myPushPosition.getY()))) {
            String _agentName_26 = this.getAgentName();
            System.out.println((((((("[Agent " + _agentName_26) + "] Push position ") + this.myPushPosition) + " is occupied by agent ") + otherAgentId_3) + ", finding alternative..."));
            Point2i altPushPos = this.findAdjacentPushPosition(currentBoxPos, this.myTargetExit, perceivedObjects);
            if (((altPushPos != null) && (!altPushPos.equals(this.myPushPosition)))) {
              boolean altBlocked = false;
              Set<UUID> _keySet_4 = this.otherAgentPositions.keySet();
              for (final UUID otherAgentId2 : _keySet_4) {
                {
                  Point2i otherAgentPos2 = this.otherAgentPositions.get(otherAgentId2);
                  if ((((otherAgentPos2 != null) && (otherAgentPos2.getX() == altPushPos.getX())) && (otherAgentPos2.getY() == altPushPos.getY()))) {
                    altBlocked = true;
                    break;
                  }
                }
              }
              if ((!altBlocked)) {
                this.myPushPosition = altPushPos;
                String _agentName_27 = this.getAgentName();
                System.out.println(((("[Agent " + _agentName_27) + "] Using alternative push position: ") + altPushPos));
              } else {
                String _agentName_28 = this.getAgentName();
                System.out.println((("[Agent " + _agentName_28) + "] Alternative push position also blocked, waiting..."));
                return;
              }
            } else {
              String _agentName_29 = this.getAgentName();
              System.out.println((((("[Agent " + _agentName_29) + "] No alternative push position, waiting for agent ") + otherAgentId_3) + " to move..."));
              return;
            }
          }
        }
      }
    }
    if ((this.myPushPosition == null)) {
      String _agentName_26 = this.getAgentName();
      System.out.println(((((("[Agent " + _agentName_26) + "] Cannot calculate push position for box at ") + currentBoxPos) + " toward exit ") + this.myTargetExit));
      sokobanObject boxObj_1 = this.findObjectAt(perceivedObjects, currentBoxPos);
      boolean boxIsOnExit = false;
      if (((boxObj_1 instanceof BoxObject) && ((BoxObject) boxObj_1).isOnTarget())) {
        boxIsOnExit = true;
        String _agentName_27 = this.getAgentName();
        System.out.println((((("[Agent " + _agentName_27) + "] Box at ") + currentBoxPos) + " is confirmed on exit (isOnTarget=true)"));
      } else {
        boolean _contains_4 = this.allExitPositions.contains(currentBoxPos);
        if (_contains_4) {
          boxIsOnExit = true;
          String _agentName_28 = this.getAgentName();
          System.out.println((((("[Agent " + _agentName_28) + "] Box at ") + currentBoxPos) + " is at exit position"));
        }
      }
      if (boxIsOnExit) {
        this.agentBehaviorPhase = "TASK_COMPLETION";
        String _agentName_29 = this.getAgentName();
        System.out.println((("[Agent " + _agentName_29) + "] Box is on exit, transitioning to TASK_COMPLETION"));
        return;
      } else {
        String _agentName_30 = this.getAgentName();
        System.out.println((("[Agent " + _agentName_30) + "] Cannot reach box or push position blocked, releasing claim"));
        if ((this.myClaimedBox != null)) {
          this.claimedBoxes.remove(this.myClaimedBox);
        }
        this.myClaimedBox = null;
        this.myTargetExit = null;
        this.myPushPosition = null;
        this.plannedBoxPath.clear();
        this.currentPathStep = 0;
        this.pathPlanned = false;
        this.agentBehaviorPhase = "PLANNING";
        this.proposalsEmitted = false;
        return;
      }
    }
    sokobanObject pushPosObj = this.findObjectAt(perceivedObjects, this.myPushPosition);
    if (((pushPosObj instanceof WallObject) && shouldRecalculate)) {
      String _agentName_31 = this.getAgentName();
      System.out.println((((("[Agent " + _agentName_31) + "] Push position ") + this.myPushPosition) + " is blocked by wall, trying adjacent positions"));
      Point2i altPushPos = this.findAdjacentPushPosition(currentBoxPos, this.myTargetExit, perceivedObjects);
      if ((altPushPos != null)) {
        this.myPushPosition = altPushPos;
        String _agentName_32 = this.getAgentName();
        System.out.println(((("[Agent " + _agentName_32) + "] Found alternative push position: ") + altPushPos));
      } else {
        String _agentName_33 = this.getAgentName();
        System.out.println((("[Agent " + _agentName_33) + "] No alternative push position found, releasing claim"));
        if ((this.myClaimedBox != null)) {
          this.claimedBoxes.remove(this.myClaimedBox);
        }
        this.myClaimedBox = null;
        this.myTargetExit = null;
        this.myPushPosition = null;
        this.plannedBoxPath.clear();
        this.currentPathStep = 0;
        this.pathPlanned = false;
        this.agentBehaviorPhase = "PLANNING";
        this.proposalsEmitted = false;
        return;
      }
    }
    int distanceToPushPos = this.calculateDistance(this.myPosition, this.myPushPosition);
    String _agentName_34 = this.getAgentName();
    System.out.println((((((((("[Agent " + _agentName_34) + "] ALIGNMENT: Distance to push position: ") + Integer.valueOf(distanceToPushPos)) + " (current: ") + this.myPosition) + ", target: ") + this.myPushPosition) + ")"));
    this.alignmentSteps++;
    boolean isOscillating = false;
    if ((this.lastAlignmentPosition != null)) {
      boolean _equals_1 = this.myPosition.equals(this.lastAlignmentPosition);
      boolean positionChanged = (!_equals_1);
      int _size_1 = this.recentPositions.size();
      if ((_size_1 >= 4)) {
        int _size_2 = this.recentPositions.size();
        Point2i pos2StepsAgo = this.recentPositions.get((_size_2 - 2));
        boolean _equals_2 = this.myPosition.equals(pos2StepsAgo);
        if (_equals_2) {
          isOscillating = true;
        }
      }
      if ((((!positionChanged) || isOscillating) && (this.alignmentSteps >= this.maxAlignmentSteps))) {
        String _xifexpression = null;
        if (isOscillating) {
          _xifexpression = "oscillating";
        } else {
          _xifexpression = "stuck at same position";
        }
        String reason = _xifexpression;
        String _agentName_35 = this.getAgentName();
        System.out.println((((((("[Agent " + _agentName_35) + "] ALIGNMENT: ") + reason) + " for ") + Integer.valueOf(this.alignmentSteps)) + " steps, releasing claim and returning to PLANNING"));
        if ((this.myClaimedBox != null)) {
          this.claimedBoxes.remove(this.myClaimedBox);
        }
        this.myClaimedBox = null;
        this.myTargetExit = null;
        this.myPushPosition = null;
        this.plannedBoxPath.clear();
        this.currentPathStep = 0;
        this.pathPlanned = false;
        this.alignmentSteps = 0;
        this.lastAlignmentPosition = null;
        this.recentPositions.clear();
        this.agentBehaviorPhase = "PLANNING";
        this.proposalsEmitted = false;
        return;
      }
    }
    int _size_3 = this.recentPositions.size();
    if ((_size_3 >= 4)) {
      this.recentPositions.remove(0);
    }
    int _x_3 = this.myPosition.getX();
    int _y_3 = this.myPosition.getY();
    Point2i _point2i_1 = new Point2i(_x_3, _y_3);
    this.recentPositions.add(_point2i_1);
    this.lastAlignmentPosition = this.myPosition;
    if ((distanceToPushPos == 0)) {
      this.agentBehaviorPhase = "PUSH_EXECUTION";
      this.lastBoxPosition = currentBoxPos;
      this.alignmentSteps = 0;
      this.lastAlignmentPosition = null;
      this.recentPositions.clear();
      String _agentName_36 = this.getAgentName();
      System.out.println((("[Agent " + _agentName_36) + "] ALIGNMENT: In push position, transitioning to PUSH_EXECUTION and starting push immediately"));
      this.handlePushExecution(perceivedObjects);
      return;
    }
    String _agentName_37 = this.getAgentName();
    System.out.println((("[Agent " + _agentName_37) + "] ALIGNMENT: Calculating path to push position..."));
    Direction direction_1 = this.calculateDirectionToTarget(this.myPushPosition, perceivedObjects);
    if ((direction_1 != null)) {
      Point2i nextPos_2 = this.calculateNextPosition(this.myPosition, direction_1);
      sokobanObject nextPosObj_2 = this.findObjectAt(perceivedObjects, nextPos_2);
      if (((nextPosObj_2 == null) || (nextPosObj_2 instanceof ExitObject))) {
        this.emitAction(direction_1);
        String _agentName_38 = this.getAgentName();
        System.out.println((((((((((("[Agent " + _agentName_38) + "] ALIGNMENT: Moving toward push position ") + this.myPushPosition) + " (distance=") + Integer.valueOf(distanceToPushPos)) + ", direction=") + direction_1) + ", nextPos=") + nextPos_2) + ")"));
      } else {
        String _agentName_39 = this.getAgentName();
        String _xifexpression_1 = null;
        if ((nextPosObj_2 instanceof WallObject)) {
          _xifexpression_1 = "wall";
        } else {
          _xifexpression_1 = "object";
        }
        System.out.println((((((("[Agent " + _agentName_39) + "] ALIGNMENT: Next position ") + nextPos_2) + " is blocked by ") + _xifexpression_1) + ", recalculating path"));
        Direction altDirection = this.findAlternativeDirectionToTarget(this.myPushPosition, direction_1, perceivedObjects);
        if ((altDirection != null)) {
          this.emitAction(altDirection);
          String _agentName_40 = this.getAgentName();
          System.out.println(((("[Agent " + _agentName_40) + "] ALIGNMENT: Using alternative direction ") + altDirection));
        } else {
          int _x_4 = this.myPushPosition.getX();
          int _x_5 = this.myPosition.getX();
          int dx_1 = (_x_4 - _x_5);
          int _y_4 = this.myPushPosition.getY();
          int _y_5 = this.myPosition.getY();
          int dy_1 = (_y_4 - _y_5);
          int _abs = Math.abs(dx_1);
          int _abs_1 = Math.abs(dy_1);
          if (((_abs + _abs_1) == 1)) {
            Direction directDir_1 = null;
            if ((dx_1 > 0)) {
              directDir_1 = Direction.EAST;
            } else {
              if ((dx_1 < 0)) {
                directDir_1 = Direction.WEST;
              } else {
                if ((dy_1 > 0)) {
                  directDir_1 = Direction.SOUTH;
                } else {
                  if ((dy_1 < 0)) {
                    directDir_1 = Direction.NORTH;
                  }
                }
              }
            }
            if ((directDir_1 != null)) {
              Point2i directNextPos = this.calculateNextPosition(this.myPosition, directDir_1);
              sokobanObject directNextObj = this.findObjectAt(perceivedObjects, directNextPos);
              if (((directNextObj == null) || (directNextObj instanceof ExitObject))) {
                this.emitAction(directDir_1);
                String _agentName_41 = this.getAgentName();
                System.out.println(((("[Agent " + _agentName_41) + "] ALIGNMENT: Using direct movement: ") + directDir_1));
              } else {
                String _agentName_42 = this.getAgentName();
                System.out.println((("[Agent " + _agentName_42) + "] ALIGNMENT: Direct movement also blocked, waiting..."));
              }
            }
          } else {
            String _agentName_43 = this.getAgentName();
            System.out.println((("[Agent " + _agentName_43) + "] ALIGNMENT: No alternative path found, waiting..."));
          }
        }
      }
    } else {
      String _agentName_44 = this.getAgentName();
      System.out.println((((((("[Agent " + _agentName_44) + "] ALIGNMENT: Cannot find path to push position ") + this.myPushPosition) + " from ") + this.myPosition) + ", trying direct movement..."));
      int _x_6 = this.myPushPosition.getX();
      int _x_7 = this.myPosition.getX();
      int dx_2 = (_x_6 - _x_7);
      int _y_6 = this.myPushPosition.getY();
      int _y_7 = this.myPosition.getY();
      int dy_2 = (_y_6 - _y_7);
      int _abs_2 = Math.abs(dx_2);
      int _abs_3 = Math.abs(dy_2);
      if (((_abs_2 + _abs_3) == 1)) {
        Direction directDir_2 = null;
        if ((dx_2 > 0)) {
          directDir_2 = Direction.EAST;
        } else {
          if ((dx_2 < 0)) {
            directDir_2 = Direction.WEST;
          } else {
            if ((dy_2 > 0)) {
              directDir_2 = Direction.SOUTH;
            } else {
              if ((dy_2 < 0)) {
                directDir_2 = Direction.NORTH;
              }
            }
          }
        }
        if ((directDir_2 != null)) {
          Point2i directNextPos_1 = this.calculateNextPosition(this.myPosition, directDir_2);
          sokobanObject directNextObj_1 = this.findObjectAt(perceivedObjects, directNextPos_1);
          if (((directNextObj_1 == null) || (directNextObj_1 instanceof ExitObject))) {
            this.emitAction(directDir_2);
            String _agentName_45 = this.getAgentName();
            System.out.println(((("[Agent " + _agentName_45) + "] ALIGNMENT: Using direct movement: ") + directDir_2));
          } else {
            String _agentName_46 = this.getAgentName();
            System.out.println((("[Agent " + _agentName_46) + "] ALIGNMENT: Direct movement blocked, waiting..."));
          }
        }
      } else {
        String _agentName_47 = this.getAgentName();
        System.out.println((("[Agent " + _agentName_47) + "] ALIGNMENT: Push position not adjacent, cannot move directly"));
      }
    }
  }

  /**
   * PHASE 3: PUSH_EXECUTION - Push box step-by-step toward exit.
   */
  protected void handlePushExecution(final List<sokobanObject> perceivedObjects) {
    String _agentName = this.getAgentName();
    System.out.println((((((((("[Agent " + _agentName) + "] PUSH_EXECUTION: Starting push execution (claimedBox=") + this.myClaimedBox) + ", targetExit=") + this.myTargetExit) + ", position=") + this.myPosition) + ")"));
    if (((this.myClaimedBox == null) || (this.myTargetExit == null))) {
      String _agentName_1 = this.getAgentName();
      System.out.println((("[Agent " + _agentName_1) + "] PUSH_EXECUTION: Lost claim or target, returning to BOX_SELECTION"));
      this.agentBehaviorPhase = "BOX_SELECTION";
      return;
    }
    boolean boxIsCompleted = false;
    boolean _contains = this.completedBoxes.contains(this.myClaimedBox);
    if (_contains) {
      boxIsCompleted = true;
      String _agentName_2 = this.getAgentName();
      System.out.println((((("[Agent " + _agentName_2) + "] Box at ") + this.myClaimedBox) + " is in completedBoxes, aborting push"));
    }
    if ((!boxIsCompleted)) {
      sokobanObject boxObj = this.findObjectAt(perceivedObjects, this.myClaimedBox);
      if (((boxObj instanceof BoxObject) && ((BoxObject) boxObj).isOnTarget())) {
        boxIsCompleted = true;
        String _agentName_3 = this.getAgentName();
        System.out.println((((("[Agent " + _agentName_3) + "] Box at ") + this.myClaimedBox) + " is onTarget in perceptions, aborting push"));
      }
    }
    if (((!boxIsCompleted) && this.allExitPositions.contains(this.myClaimedBox))) {
      boxIsCompleted = true;
      String _agentName_4 = this.getAgentName();
      System.out.println((((("[Agent " + _agentName_4) + "] Box at ") + this.myClaimedBox) + " is at exit position, aborting push"));
    }
    if (boxIsCompleted) {
      String _agentName_5 = this.getAgentName();
      System.out.println((("[Agent " + _agentName_5) + "] Box is completed, releasing claim and returning to BOX_SELECTION"));
      this.claimedBoxes.remove(this.myClaimedBox);
      this.myClaimedBox = null;
      this.myTargetExit = null;
      this.myPushPosition = null;
      this.lastBoxPosition = null;
      this.pushStuckCounter = 0;
      this.pushFailureCount = 0;
      this.plannedBoxPath.clear();
      this.currentPathStep = 0;
      this.pathPlanned = false;
      this.cleanupTeamState();
      this.agentBehaviorPhase = "PLANNING";
      this.proposalsEmitted = false;
      return;
    }
    Point2i currentBoxPos = this.findCurrentBoxPosition(this.myClaimedBox, perceivedObjects);
    if (((currentBoxPos != null) && (this.myClaimedBox != null))) {
      if (((currentBoxPos.getX() != this.myClaimedBox.getX()) || (currentBoxPos.getY() != this.myClaimedBox.getY()))) {
        Point2i oldClaimedPos = this.myClaimedBox;
        this.myClaimedBox = currentBoxPos;
        int _x = oldClaimedPos.getX();
        int _y = oldClaimedPos.getY();
        Point2i _point2i = new Point2i(_x, _y);
        this.allBoxPositions.remove(_point2i);
        this.allBoxPositions.add(currentBoxPos);
        boolean _contains_1 = this.claimedBoxes.contains(oldClaimedPos);
        if (_contains_1) {
          this.claimedBoxes.remove(oldClaimedPos);
        }
        boolean _contains_2 = this.claimedBoxes.contains(currentBoxPos);
        if ((!_contains_2)) {
          this.claimedBoxes.add(currentBoxPos);
        }
        String _agentName_6 = this.getAgentName();
        System.out.println((((((("[Agent " + _agentName_6) + "] PUSH_EXECUTION: Box moved from ") + oldClaimedPos) + " to ") + currentBoxPos) + ", updated claimedBoxes"));
        if ((this.myTargetExit != null)) {
          LinkedList<Point2i> newPath = this.planBoxPath(currentBoxPos, this.myTargetExit, perceivedObjects);
          boolean _isEmpty = newPath.isEmpty();
          if ((!_isEmpty)) {
            this.currentPathStep = 0;
            int minDist = Integer.MAX_VALUE;
            int closestStep = 0;
            for (int i = 0; (i < this.plannedBoxPath.size()); i++) {
              {
                Point2i pathPoint = this.plannedBoxPath.get(i);
                int dist = this.calculateDistance(currentBoxPos, pathPoint);
                if ((dist < minDist)) {
                  minDist = dist;
                  closestStep = i;
                }
              }
            }
            if (((minDist <= 2) && (closestStep < this.plannedBoxPath.size()))) {
              this.currentPathStep = closestStep;
              String _agentName_7 = this.getAgentName();
              System.out.println(((("[Agent " + _agentName_7) + "] PUSH_EXECUTION: Box moved, continuing from path step ") + Integer.valueOf(this.currentPathStep)));
            } else {
              this.plannedBoxPath = newPath;
              this.currentPathStep = 0;
              this.pathPlanned = true;
              String _agentName_8 = this.getAgentName();
              int _size = newPath.size();
              System.out.println((((("[Agent " + _agentName_8) + "] PUSH_EXECUTION: Box moved significantly, recalculated path with ") + Integer.valueOf(_size)) + " steps"));
            }
          } else {
            this.pathPlanned = false;
            this.plannedBoxPath.clear();
            this.currentPathStep = 0;
            String _agentName_9 = this.getAgentName();
            System.out.println(((("[Agent " + _agentName_9) + "] PUSH_EXECUTION: WARNING - No path found from new box position ") + currentBoxPos));
          }
        }
      }
    }
    if ((currentBoxPos != null)) {
      Set<UUID> _keySet = this.otherAgentPhases.keySet();
      for (final UUID otherAgentId : _keySet) {
        {
          String otherPhase = this.otherAgentPhases.get(otherAgentId);
          Point2i otherTargetBox = this.otherAgentTargets.get(otherAgentId);
          if (((otherPhase == "PUSH_EXECUTION") && (otherTargetBox != null))) {
            int distance = this.calculateDistance(otherTargetBox, currentBoxPos);
            if ((distance == 0)) {
              String _agentName_10 = this.getAgentName();
              System.out.println((((((("[Agent " + _agentName_10) + "] Conflict detected: agent ") + otherAgentId) + " is pushing box at ") + currentBoxPos) + ", releasing claim"));
              this.claimedBoxes.remove(this.myClaimedBox);
              this.myClaimedBox = null;
              this.myTargetExit = null;
              this.myPushPosition = null;
              this.lastBoxPosition = null;
              this.pushStuckCounter = 0;
              this.plannedBoxPath.clear();
              this.currentPathStep = 0;
              this.pathPlanned = false;
              this.cleanupTeamState();
              this.agentBehaviorPhase = "BOX_SELECTION";
              return;
            }
          }
        }
      }
    }
    if ((currentBoxPos == null)) {
      boolean boxAtExit = this.checkBoxAtExit(perceivedObjects);
      if (boxAtExit) {
        this.agentBehaviorPhase = "TASK_COMPLETION";
        return;
      }
      if ((this.myClaimedBox != null)) {
        this.claimedBoxes.remove(this.myClaimedBox);
      }
      this.myClaimedBox = null;
      this.myTargetExit = null;
      this.plannedBoxPath.clear();
      this.currentPathStep = 0;
      this.pathPlanned = false;
      this.agentBehaviorPhase = "PLANNING";
      this.proposalsEmitted = false;
      return;
    }
    if (((currentBoxPos != null) && (this.lastBoxPosition != null))) {
      int oldDist = this.calculateDistance(this.lastBoxPosition, this.myTargetExit);
      int newDist = this.calculateDistance(currentBoxPos, this.myTargetExit);
      if ((newDist < oldDist)) {
        this.pushStuckCounter = 0;
      } else {
        if ((((newDist == oldDist) && (currentBoxPos.getX() == this.lastBoxPosition.getX())) && (currentBoxPos.getY() == this.lastBoxPosition.getY()))) {
          this.pushStuckCounter++;
          if ((this.pushStuckCounter >= this.maxStuckSteps)) {
            String _agentName_10 = this.getAgentName();
            System.out.println((((((("[Agent " + _agentName_10) + "] Stuck pushing box at ") + currentBoxPos) + " for ") + Integer.valueOf(this.pushStuckCounter)) + " steps, releasing claim"));
            Point2i releasedBox = this.myClaimedBox;
            this.claimedBoxes.remove(this.myClaimedBox);
            this.myClaimedBox = null;
            this.myTargetExit = null;
            this.myPushPosition = null;
            this.pushStuckCounter = 0;
            this.plannedBoxPath.clear();
            this.currentPathStep = 0;
            this.pathPlanned = false;
            this.cleanupTeamState();
            if ((releasedBox != null)) {
              this.recentlyReleasedBoxes.put(releasedBox, Integer.valueOf(this.lastPerceptionTime));
            }
            this.agentBehaviorPhase = "BOX_SELECTION";
            return;
          }
        } else {
          this.pushStuckCounter = 0;
        }
      }
    }
    if (((this.lastBoxPosition != null) && (currentBoxPos != null))) {
      if (((this.lastBoxPosition.getX() == currentBoxPos.getX()) && (this.lastBoxPosition.getY() == currentBoxPos.getY()))) {
        this.pushFailureCount++;
        if ((this.pushFailureCount >= this.maxPushFailures)) {
          String _agentName_11 = this.getAgentName();
          System.out.println((((("[Agent " + _agentName_11) + "] Push failed ") + Integer.valueOf(this.pushFailureCount)) + " consecutive times - repositioning"));
          this.pushFailureCount = 0;
          this.agentBehaviorPhase = "ALIGNMENT";
          return;
        } else {
          String _agentName_12 = this.getAgentName();
          System.out.println(((((("[Agent " + _agentName_12) + "] Push failed (box didn\'t move), failure count: ") + Integer.valueOf(this.pushFailureCount)) + "/") + Integer.valueOf(this.maxPushFailures)));
        }
      } else {
        this.pushFailureCount = 0;
      }
    }
    if ((currentBoxPos != null)) {
      this.lastBoxPosition = currentBoxPos;
    }
    sokobanObject boxObj_1 = this.findObjectAt(perceivedObjects, currentBoxPos);
    if (((boxObj_1 instanceof BoxObject) && ((BoxObject) boxObj_1).isOnTarget())) {
      int distanceToTargetExit = this.calculateDistance(currentBoxPos, this.myTargetExit);
      if ((distanceToTargetExit == 0)) {
        String _agentName_13 = this.getAgentName();
        System.out.println(((("[Agent " + _agentName_13) + "] Box confirmed on target exit at ") + this.myTargetExit));
        this.agentBehaviorPhase = "TASK_COMPLETION";
        return;
      } else {
        String _agentName_14 = this.getAgentName();
        System.out.println((("[Agent " + _agentName_14) + "] Box is on exit (not target exit), but task complete"));
        this.agentBehaviorPhase = "TASK_COMPLETION";
        return;
      }
    }
    if (((this.teamMembers.size() > 1) && (this.myTaskRole != null))) {
      if ((this.myTaskRole == "HELPER")) {
        Direction pushDir = this.calculatePushDirection(currentBoxPos, this.myTargetExit);
        if ((pushDir != null)) {
          Point2i nextBoxPos = this.calculateNextPosition(currentBoxPos, pushDir);
          sokobanObject nextPosObj = this.findObjectAt(perceivedObjects, nextBoxPos);
          if (((nextPosObj == null) || (nextPosObj instanceof ExitObject))) {
            this.lastBoxPosition = currentBoxPos;
            this.emitAction(pushDir);
            String _agentName_15 = this.getAgentName();
            System.out.println((((((("[Agent " + _agentName_15) + "] HELPER: Assisting push in direction ") + pushDir) + " (box at ") + currentBoxPos) + ")"));
            if ((nextBoxPos != null)) {
              DefaultContextInteractions _$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
              UUID _iD = this.getID();
              BoxPositionUpdate _boxPositionUpdate = new BoxPositionUpdate(currentBoxPos, nextBoxPos, _iD);
              _$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_boxPositionUpdate);
            }
            return;
          } else {
            String _agentName_16 = this.getAgentName();
            System.out.println((("[Agent " + _agentName_16) + "] HELPER: Push blocked, repositioning"));
            this.agentBehaviorPhase = "ALIGNMENT";
            return;
          }
        } else {
          this.agentBehaviorPhase = "ALIGNMENT";
          return;
        }
      }
    }
    this.calculatePushPositionForBox(currentBoxPos, this.myTargetExit);
    int distanceToPushPos = this.calculateDistance(this.myPosition, this.myPushPosition);
    if ((distanceToPushPos > 0)) {
      String _agentName_17 = this.getAgentName();
      System.out.println((((((((("[Agent " + _agentName_17) + "] PUSH_EXECUTION: Not at push position (distance=") + Integer.valueOf(distanceToPushPos)) + ", agent at ") + this.myPosition) + ", push pos ") + this.myPushPosition) + "), returning to ALIGNMENT"));
      this.agentBehaviorPhase = "ALIGNMENT";
      return;
    }
    String _agentName_18 = this.getAgentName();
    System.out.println((((((("[Agent " + _agentName_18) + "] PUSH_EXECUTION: At push position, starting push (box at ") + currentBoxPos) + ", exit at ") + this.myTargetExit) + ")"));
    Direction pushDirection = null;
    Point2i nextBoxPos_1 = null;
    if (((this.pathPlanned && (!this.plannedBoxPath.isEmpty())) && (this.currentPathStep < this.plannedBoxPath.size()))) {
      Point2i nextTargetPos = this.plannedBoxPath.get(this.currentPathStep);
      String _agentName_19 = this.getAgentName();
      int _size_1 = this.plannedBoxPath.size();
      int _size_2 = this.plannedBoxPath.size();
      System.out.println((((((((((((("[Agent " + _agentName_19) + "] PUSH_EXECUTION: Following planned path step ") + Integer.valueOf(this.currentPathStep)) + "/") + Integer.valueOf((_size_1 - 1))) + " (target: ") + nextTargetPos) + ", box at: ") + currentBoxPos) + ", path size: ") + Integer.valueOf(_size_2)) + ")"));
      int distanceToTarget = this.calculateDistance(currentBoxPos, nextTargetPos);
      if ((distanceToTarget == 0)) {
        this.currentPathStep++;
        int _size_3 = this.plannedBoxPath.size();
        if ((this.currentPathStep < _size_3)) {
          nextTargetPos = this.plannedBoxPath.get(this.currentPathStep);
          String _agentName_20 = this.getAgentName();
          System.out.println(((("[Agent " + _agentName_20) + "] PUSH_EXECUTION: Reached path step, moving to next: ") + nextTargetPos));
        } else {
          String _agentName_21 = this.getAgentName();
          System.out.println((("[Agent " + _agentName_21) + "] PUSH_EXECUTION: Reached end of planned path"));
          this.agentBehaviorPhase = "TASK_COMPLETION";
          return;
        }
      } else {
        if ((distanceToTarget > 3)) {
          int minDist_1 = Integer.MAX_VALUE;
          int closestStep_1 = this.currentPathStep;
          for (int i = 0; (i < this.plannedBoxPath.size()); i++) {
            {
              Point2i pathPoint = this.plannedBoxPath.get(i);
              int dist = this.calculateDistance(currentBoxPos, pathPoint);
              if ((dist < minDist_1)) {
                minDist_1 = dist;
                closestStep_1 = i;
              }
            }
          }
          if ((closestStep_1 != this.currentPathStep)) {
            this.currentPathStep = closestStep_1;
            nextTargetPos = this.plannedBoxPath.get(this.currentPathStep);
            String _agentName_22 = this.getAgentName();
            System.out.println((((((("[Agent " + _agentName_22) + "] PUSH_EXECUTION: Box off path, adjusted to step ") + Integer.valueOf(this.currentPathStep)) + " (target: ") + nextTargetPos) + ")"));
          }
        }
      }
      int _x_1 = nextTargetPos.getX();
      int _x_2 = currentBoxPos.getX();
      int dx = (_x_1 - _x_2);
      int _y_1 = nextTargetPos.getY();
      int _y_2 = currentBoxPos.getY();
      int dy = (_y_1 - _y_2);
      int _abs = Math.abs(dx);
      int _abs_1 = Math.abs(dy);
      if ((_abs > _abs_1)) {
        Direction _xifexpression = null;
        if ((dx > 0)) {
          _xifexpression = Direction.EAST;
        } else {
          _xifexpression = Direction.WEST;
        }
        pushDirection = _xifexpression;
      } else {
        if ((dy != 0)) {
          Direction _xifexpression_1 = null;
          if ((dy > 0)) {
            _xifexpression_1 = Direction.SOUTH;
          } else {
            _xifexpression_1 = Direction.NORTH;
          }
          pushDirection = _xifexpression_1;
        }
      }
      if ((pushDirection != null)) {
        nextBoxPos_1 = this.calculateNextPosition(currentBoxPos, pushDirection);
        String _agentName_23 = this.getAgentName();
        System.out.println((((((("[Agent " + _agentName_23) + "] PUSH_EXECUTION: Using planned path direction ") + pushDirection) + " (box will move to ") + nextBoxPos_1) + ")"));
      }
    }
    if ((pushDirection == null)) {
      if ((!this.pathPlanned)) {
        String _agentName_24 = this.getAgentName();
        System.out.println((("[Agent " + _agentName_24) + "] PUSH_EXECUTION: No path planned, using greedy approach"));
      } else {
        boolean _isEmpty_1 = this.plannedBoxPath.isEmpty();
        if (_isEmpty_1) {
          String _agentName_25 = this.getAgentName();
          System.out.println((("[Agent " + _agentName_25) + "] PUSH_EXECUTION: Planned path is empty, using greedy approach"));
        } else {
          int _size_4 = this.plannedBoxPath.size();
          if ((this.currentPathStep >= _size_4)) {
            String _agentName_26 = this.getAgentName();
            System.out.println((("[Agent " + _agentName_26) + "] PUSH_EXECUTION: Reached end of path, using greedy approach"));
          }
        }
      }
      pushDirection = this.calculatePushDirection(currentBoxPos, this.myTargetExit);
      if ((pushDirection == null)) {
        String _agentName_27 = this.getAgentName();
        System.out.println((("[Agent " + _agentName_27) + "] Cannot push - path blocked or box at exit"));
        if (((boxObj_1 instanceof BoxObject) && ((BoxObject) boxObj_1).isOnTarget())) {
          this.agentBehaviorPhase = "TASK_COMPLETION";
        } else {
          this.agentBehaviorPhase = "ALIGNMENT";
        }
        return;
      }
      nextBoxPos_1 = this.calculateNextPosition(currentBoxPos, pushDirection);
      String _agentName_28 = this.getAgentName();
      System.out.println((((((("[Agent " + _agentName_28) + "] PUSH_EXECUTION: Using greedy direction ") + pushDirection) + " (box will move to ") + nextBoxPos_1) + ")"));
    }
    sokobanObject nextPosObj_1 = this.findObjectAt(perceivedObjects, nextBoxPos_1);
    if (((nextPosObj_1 != null) && ((nextPosObj_1 instanceof WallObject) || (nextPosObj_1 instanceof BoxObject)))) {
      String _agentName_29 = this.getAgentName();
      String _xifexpression_2 = null;
      if ((nextPosObj_1 instanceof WallObject)) {
        _xifexpression_2 = "wall";
      } else {
        _xifexpression_2 = "box";
      }
      System.out.println((((((("[Agent " + _agentName_29) + "] Push blocked at ") + nextBoxPos_1) + " by ") + _xifexpression_2) + " - replanning path"));
      LinkedList<Point2i> newPath_1 = this.planBoxPath(currentBoxPos, this.myTargetExit, perceivedObjects);
      boolean _isEmpty_2 = newPath_1.isEmpty();
      if ((!_isEmpty_2)) {
        this.plannedBoxPath = newPath_1;
        this.currentPathStep = 0;
        this.pathPlanned = true;
        String _agentName_30 = this.getAgentName();
        int _size_5 = newPath_1.size();
        System.out.println((((("[Agent " + _agentName_30) + "] Replanned path with ") + Integer.valueOf(_size_5)) + " steps"));
        int _size_6 = this.plannedBoxPath.size();
        if ((this.currentPathStep < _size_6)) {
          Point2i nextTargetPos_1 = this.plannedBoxPath.get(this.currentPathStep);
          int _x_3 = nextTargetPos_1.getX();
          int _x_4 = currentBoxPos.getX();
          int dx_1 = (_x_3 - _x_4);
          int _y_3 = nextTargetPos_1.getY();
          int _y_4 = currentBoxPos.getY();
          int dy_1 = (_y_3 - _y_4);
          int _abs_2 = Math.abs(dx_1);
          int _abs_3 = Math.abs(dy_1);
          if ((_abs_2 > _abs_3)) {
            Direction _xifexpression_3 = null;
            if ((dx_1 > 0)) {
              _xifexpression_3 = Direction.EAST;
            } else {
              _xifexpression_3 = Direction.WEST;
            }
            pushDirection = _xifexpression_3;
          } else {
            if ((dy_1 != 0)) {
              Direction _xifexpression_4 = null;
              if ((dy_1 > 0)) {
                _xifexpression_4 = Direction.SOUTH;
              } else {
                _xifexpression_4 = Direction.NORTH;
              }
              pushDirection = _xifexpression_4;
            }
          }
          if ((pushDirection != null)) {
            nextBoxPos_1 = this.calculateNextPosition(currentBoxPos, pushDirection);
            sokobanObject recheckObj = this.findObjectAt(perceivedObjects, nextBoxPos_1);
            if (((recheckObj != null) && ((recheckObj instanceof WallObject) || (recheckObj instanceof BoxObject)))) {
              String _agentName_31 = this.getAgentName();
              System.out.println((("[Agent " + _agentName_31) + "] Replanned push still blocked - repositioning"));
              this.agentBehaviorPhase = "ALIGNMENT";
              return;
            }
          }
        }
      } else {
        this.pathPlanned = false;
        this.plannedBoxPath.clear();
        this.currentPathStep = 0;
        String _agentName_32 = this.getAgentName();
        System.out.println((("[Agent " + _agentName_32) + "] No alternative path found - repositioning"));
        this.agentBehaviorPhase = "ALIGNMENT";
        return;
      }
    }
    int currentDistance = this.calculateDistance(currentBoxPos, this.myTargetExit);
    int nextDistance = this.calculateDistance(nextBoxPos_1, this.myTargetExit);
    if ((!this.pathPlanned)) {
      if ((nextDistance >= currentDistance)) {
        String _agentName_33 = this.getAgentName();
        System.out.println((("[Agent " + _agentName_33) + "] Push would not bring box closer to exit"));
        this.agentBehaviorPhase = "ALIGNMENT";
        return;
      }
    }
    if ((this.isDeadlockPosition(nextBoxPos_1, perceivedObjects) || this.isNextToOuterBorder(nextBoxPos_1))) {
      String _xifexpression_5 = null;
      boolean _isNextToOuterBorder = this.isNextToOuterBorder(nextBoxPos_1);
      if (_isNextToOuterBorder) {
        _xifexpression_5 = "next to outer border";
      } else {
        _xifexpression_5 = "deadlock";
      }
      String reason = _xifexpression_5;
      String _agentName_34 = this.getAgentName();
      System.out.println((((((("[Agent " + _agentName_34) + "] Push would create ") + reason) + " at ") + nextBoxPos_1) + " - trying alternative direction"));
      Direction altDirection = this.findAlternativePushDirection(currentBoxPos, this.myTargetExit, pushDirection, perceivedObjects);
      if ((altDirection != null)) {
        pushDirection = altDirection;
        nextBoxPos_1 = this.calculateNextPosition(currentBoxPos, pushDirection);
        nextDistance = this.calculateDistance(nextBoxPos_1, this.myTargetExit);
        if ((((nextDistance >= currentDistance) || this.isDeadlockPosition(nextBoxPos_1, perceivedObjects)) || this.isNextToOuterBorder(nextBoxPos_1))) {
          String _agentName_35 = this.getAgentName();
          System.out.println((("[Agent " + _agentName_35) + "] All push directions lead to deadlock/border or don\'t help - repositioning"));
          this.agentBehaviorPhase = "ALIGNMENT";
          return;
        }
      } else {
        String _agentName_36 = this.getAgentName();
        System.out.println((("[Agent " + _agentName_36) + "] No alternative push direction - repositioning"));
        this.agentBehaviorPhase = "ALIGNMENT";
        return;
      }
    }
    boolean _isBlockingOtherAgent = this.isBlockingOtherAgent(perceivedObjects);
    if (_isBlockingOtherAgent) {
      String _agentName_37 = this.getAgentName();
      System.out.println((("[Agent " + _agentName_37) + "] Blocking another agent - repositioning to allow passage"));
      this.repositionToAllowPassage(perceivedObjects);
      return;
    }
    int distanceToBox = this.calculateDistance(this.myPosition, currentBoxPos);
    if ((distanceToBox != 1)) {
      String _agentName_38 = this.getAgentName();
      System.out.println((((((((("[Agent " + _agentName_38) + "] PUSH_EXECUTION: ERROR - Agent not adjacent to box! Agent at ") + this.myPosition) + ", box at ") + currentBoxPos) + " (distance=") + Integer.valueOf(distanceToBox)) + ")"));
      this.agentBehaviorPhase = "ALIGNMENT";
      return;
    }
    sokobanObject spaceBehindBox = this.findObjectAt(perceivedObjects, nextBoxPos_1);
    if (((spaceBehindBox != null) && ((spaceBehindBox instanceof WallObject) || (spaceBehindBox instanceof BoxObject)))) {
      String _agentName_39 = this.getAgentName();
      String _xifexpression_6 = null;
      if ((spaceBehindBox instanceof WallObject)) {
        _xifexpression_6 = "wall";
      } else {
        _xifexpression_6 = "box";
      }
      System.out.println(((((((((("[Agent " + _agentName_39) + "] PUSH_EXECUTION: ERROR - Space behind box is blocked! Box at ") + currentBoxPos) + ", push direction ") + pushDirection) + ", nextBoxPos ") + nextBoxPos_1) + " blocked by ") + _xifexpression_6));
      LinkedList<Point2i> newPath_2 = this.planBoxPath(currentBoxPos, this.myTargetExit, perceivedObjects);
      boolean _isEmpty_3 = newPath_2.isEmpty();
      if ((!_isEmpty_3)) {
        this.plannedBoxPath = newPath_2;
        this.currentPathStep = 0;
        this.pathPlanned = true;
        String _agentName_40 = this.getAgentName();
        System.out.println((("[Agent " + _agentName_40) + "] Replanned path due to blocked space behind box"));
      } else {
        this.agentBehaviorPhase = "ALIGNMENT";
      }
      return;
    }
    Point2i agentNextPos = this.calculateNextPosition(this.myPosition, pushDirection);
    int distanceToBoxFromNext = this.calculateDistance(agentNextPos, currentBoxPos);
    if ((distanceToBoxFromNext != 0)) {
      String _agentName_41 = this.getAgentName();
      System.out.println(((((((((("[Agent " + _agentName_41) + "] PUSH_EXECUTION: ERROR - Push direction ") + pushDirection) + " from ") + this.myPosition) + " would move to ") + agentNextPos) + ", but box is at ") + currentBoxPos));
      this.agentBehaviorPhase = "ALIGNMENT";
      return;
    }
    String _agentName_42 = this.getAgentName();
    System.out.println((((((((("[Agent " + _agentName_42) + "] PUSH_EXECUTION: All validations passed - pushing box at ") + currentBoxPos) + " in direction ") + pushDirection) + " (box will move to ") + nextBoxPos_1) + ")"));
    this.lastBoxPosition = currentBoxPos;
    this.emitAction(pushDirection);
    String _agentName_43 = this.getAgentName();
    System.out.println(((((((("[Agent " + _agentName_43) + "] Pushing box at ") + currentBoxPos) + " toward ") + this.myTargetExit) + " in direction ") + pushDirection));
    this.boxProgressCount++;
    if ((nextBoxPos_1 != null)) {
      DefaultContextInteractions _$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1 = this.$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
      UUID _iD_1 = this.getID();
      BoxPositionUpdate _boxPositionUpdate_1 = new BoxPositionUpdate(currentBoxPos, nextBoxPos_1, _iD_1);
      _$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1.emit(_boxPositionUpdate_1);
    }
  }

  /**
   * PHASE 4: TASK_COMPLETION - Box reached exit, release and select new box.
   */
  protected void handleTaskCompletion(final List<sokobanObject> perceivedObjects) {
    this.taskCompletionSteps++;
    Point2i boxToComplete = this.myClaimedBox;
    if ((boxToComplete != null)) {
      String _agentName = this.getAgentName();
      System.out.println((((("[Agent " + _agentName) + "] Task completed - box at ") + boxToComplete) + " reached exit"));
      boolean boxConfirmed = false;
      for (final sokobanObject obj : perceivedObjects) {
        if ((obj instanceof BoxObject)) {
          BoxObject boxObj = ((BoxObject)obj);
          int distance = this.calculateDistance(boxObj.getPosition(), boxToComplete);
          if (((distance <= 5) && boxObj.isOnTarget())) {
            if ((this.allBoxPositions.contains(boxObj.getPosition()) || (distance <= 2))) {
              boxConfirmed = true;
              boxToComplete = boxObj.getPosition();
              String _agentName_1 = this.getAgentName();
              System.out.println((((((((("[Agent " + _agentName_1) + "] Box confirmed on exit via perception (isOnTarget=true) at ") + boxToComplete) + " (was claimed at ") + this.myClaimedBox) + ", distance=") + Integer.valueOf(distance)) + ")"));
              break;
            }
          }
        }
      }
      if (((!boxConfirmed) && this.allExitPositions.contains(boxToComplete))) {
        boxConfirmed = true;
        String _agentName_2 = this.getAgentName();
        System.out.println((("[Agent " + _agentName_2) + "] Box confirmed on exit via position match (position is in allExitPositions)"));
      }
      if (((!boxConfirmed) && (!this.allBoxPositions.contains(boxToComplete)))) {
        boolean _contains = this.allExitPositions.contains(boxToComplete);
        if (_contains) {
          boxConfirmed = true;
          String _agentName_3 = this.getAgentName();
          System.out.println((("[Agent " + _agentName_3) + "] Box confirmed on exit via global knowledge (removed from allBoxPositions, position is exit)"));
        }
      }
      if ((!boxConfirmed)) {
        for (final sokobanObject obj_1 : perceivedObjects) {
          if ((obj_1 instanceof BoxObject)) {
            BoxObject boxObj_1 = ((BoxObject)obj_1);
            boolean _isOnTarget = boxObj_1.isOnTarget();
            if (_isOnTarget) {
              if (((this.myClaimedBox == null) || (this.calculateDistance(boxObj_1.getPosition(), this.myClaimedBox) <= 10))) {
                boxConfirmed = true;
                boxToComplete = boxObj_1.getPosition();
                String _agentName_4 = this.getAgentName();
                System.out.println(((("[Agent " + _agentName_4) + "] Box confirmed on exit via broad search (any box on target) at ") + boxToComplete));
                break;
              }
            }
          }
        }
      }
      if (((!boxConfirmed) && (this.taskCompletionSteps >= 5))) {
        boolean _contains_1 = this.allExitPositions.contains(boxToComplete);
        if (_contains_1) {
          boxConfirmed = true;
          String _agentName_5 = this.getAgentName();
          System.out.println((("[Agent " + _agentName_5) + "] Box confirmed on exit via timeout fallback (position is exit)"));
        }
      }
      if (boxConfirmed) {
        boolean _contains_2 = this.completedBoxes.contains(boxToComplete);
        if ((!_contains_2)) {
          this.completedBoxes.add(boxToComplete);
          String _agentName_6 = this.getAgentName();
          System.out.println((("[Agent " + _agentName_6) + "] Marked box as completed in completedBoxes"));
        }
        DefaultContextInteractions _$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
        UUID _iD = this.getID();
        BoxCompleted _boxCompleted = new BoxCompleted(boxToComplete, _iD);
        _$CAPACITY_USE$IO_SARL_API_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_boxCompleted);
        String _agentName_7 = this.getAgentName();
        System.out.println((((("[Agent " + _agentName_7) + "] Announced box completion at ") + boxToComplete) + " to other agents"));
        boolean _isEmpty = this.teamMembers.isEmpty();
        if ((!_isEmpty)) {
          for (final UUID teamMemberId : this.teamMembers) {
            UUID _iD_1 = this.getID();
            boolean _notEquals = (!Objects.equals(teamMemberId, _iD_1));
            if (_notEquals) {
              String _agentName_8 = this.getAgentName();
              System.out.println((((((("[Agent " + _agentName_8) + "] Notified team member ") + teamMemberId) + " that box at ") + boxToComplete) + " is completed"));
            }
          }
        }
        this.claimedBoxes.remove(boxToComplete);
        if ((this.myTargetExit != null)) {
          this.assignedExits.remove(this.myTargetExit);
          String _agentName_9 = this.getAgentName();
          System.out.println((((("[Agent " + _agentName_9) + "] Released exit ") + this.myTargetExit) + " (box completed)"));
        }
        this.taskCompletionSteps = 0;
      } else {
        String _agentName_10 = this.getAgentName();
        System.out.println((((("[Agent " + _agentName_10) + "] Box not yet confirmed on exit, waiting... (step ") + Integer.valueOf(this.taskCompletionSteps)) + ")"));
        return;
      }
    }
    this.myClaimedBox = null;
    this.myTargetExit = null;
    this.myPushPosition = null;
    this.lastBoxPosition = null;
    this.plannedBoxPath.clear();
    this.currentPathStep = 0;
    this.pathPlanned = false;
    this.taskCompletionSteps = 0;
    this.agentBehaviorPhase = "PLANNING";
    this.proposalsEmitted = false;
    this.alignmentSteps = 0;
    this.lastAlignmentPosition = null;
    this.recentPositions.clear();
    this.pushFailureCount = 0;
    this.pushStuckCounter = 0;
    if ((this.myTargetExit != null)) {
      this.assignedExits.remove(this.myTargetExit);
    }
    String _agentName_11 = this.getAgentName();
    System.out.println((("[Agent " + _agentName_11) + "] Released box, returning to PLANNING to coordinate and select next box"));
  }

  /**
   * Sync box positions in allBoxPositions with current perceptions.
   * Removes stale positions that are not in perceptions and adds new boxes found in perceptions.
   * This ensures planning uses accurate box positions.
   */
  protected void syncBoxPositionsWithPerceptions(final List<sokobanObject> perceivedObjects) {
    HashSet<Point2i> perceivedBoxPositions = CollectionLiterals.<Point2i>newHashSet();
    for (final sokobanObject obj : perceivedObjects) {
      if ((obj instanceof BoxObject)) {
        perceivedBoxPositions.add(((BoxObject)obj).getPosition());
      }
    }
    if ((perceivedBoxPositions.isEmpty() && (this.allBoxPositions.size() > 0))) {
      return;
    }
    ArrayList<Point2i> stalePositions = CollectionLiterals.<Point2i>newArrayList();
    for (final Point2i knownPos : this.allBoxPositions) {
      boolean _contains = perceivedBoxPositions.contains(knownPos);
      if ((!_contains)) {
        boolean foundNearby = false;
        for (final Point2i perceivedPos : perceivedBoxPositions) {
          {
            int distance = this.calculateDistance(knownPos, perceivedPos);
            if ((distance <= 3)) {
              foundNearby = true;
              break;
            }
          }
        }
        if ((!foundNearby)) {
          int distanceFromAgent = this.calculateDistance(knownPos, this.myPosition);
          if ((((distanceFromAgent > 15) && (!this.claimedBoxes.contains(knownPos))) && (!this.completedBoxes.contains(knownPos)))) {
            stalePositions.add(knownPos);
          }
        }
      }
    }
    for (final Point2i stalePos : stalePositions) {
      {
        this.allBoxPositions.remove(stalePos);
        boolean _contains_1 = this.claimedBoxes.contains(stalePos);
        if (_contains_1) {
          this.claimedBoxes.remove(stalePos);
        }
        String _agentName = this.getAgentName();
        System.out.println((((("[Agent " + _agentName) + "] PLANNING: Removed stale box position ") + stalePos) + " (not in perceptions)"));
      }
    }
    for (final Point2i perceivedPos_1 : perceivedBoxPositions) {
      boolean _contains_1 = this.allBoxPositions.contains(perceivedPos_1);
      if ((!_contains_1)) {
        this.allBoxPositions.add(perceivedPos_1);
        String _agentName = this.getAgentName();
        System.out.println((((("[Agent " + _agentName) + "] PLANNING: Added new box position ") + perceivedPos_1) + " from perceptions"));
      }
    }
  }

  /**
   * Find nearest exit to a given position, excluding already assigned exits.
   */
  @Pure
  protected Point2i findNearestExit(final Point2i position) {
    return this.findNearestExitExcluding(position, this.assignedExits);
  }

  /**
   * Find nearest exit to a given position, excluding specified exits.
   */
  @Pure
  protected Point2i findNearestExitExcluding(final Point2i position, final HashSet<Point2i> excludedExits) {
    if (((position == null) || this.allExitPositions.isEmpty())) {
      return null;
    }
    Point2i nearestExit = null;
    int minDistance = Integer.MAX_VALUE;
    for (final Point2i exitPos : this.allExitPositions) {
      {
        if (((excludedExits != null) && excludedExits.contains(exitPos))) {
          continue;
        }
        int distance = this.calculateDistance(position, exitPos);
        if ((distance < minDistance)) {
          minDistance = distance;
          nearestExit = exitPos;
        }
      }
    }
    if (((nearestExit == null) && (!this.allExitPositions.isEmpty()))) {
      for (final Point2i exitPos_1 : this.allExitPositions) {
        {
          int distance = this.calculateDistance(position, exitPos_1);
          if ((distance < minDistance)) {
            minDistance = distance;
            nearestExit = exitPos_1;
          }
        }
      }
    }
    return nearestExit;
  }

  /**
   * Find current box position in perceptions (box may have moved).
   * If box is not in perceptions, returns null (caller should use global knowledge).
   */
  @Pure
  protected Point2i findCurrentBoxPosition(final Point2i claimedBoxPos, final List<sokobanObject> perceivedObjects) {
    if ((claimedBoxPos == null)) {
      return null;
    }
    sokobanObject boxAtClaimedPos = this.findObjectAt(perceivedObjects, claimedBoxPos);
    if ((boxAtClaimedPos instanceof BoxObject)) {
      return claimedBoxPos;
    }
    BoxObject nearestBox = null;
    int minDistance = Integer.MAX_VALUE;
    for (final sokobanObject obj : perceivedObjects) {
      if ((obj instanceof BoxObject)) {
        int distance = this.calculateDistance(((BoxObject)obj).getPosition(), claimedBoxPos);
        if (((distance <= 5) && (distance < minDistance))) {
          minDistance = distance;
          nearestBox = ((BoxObject)obj);
        }
      }
    }
    if ((nearestBox != null)) {
      return nearestBox.getPosition();
    }
    return null;
  }

  /**
   * Check if claimed box is at an exit.
   */
  @Pure
  protected boolean checkBoxAtExit(final List<sokobanObject> perceivedObjects) {
    if ((this.myClaimedBox == null)) {
      return false;
    }
    for (final Point2i exitPos : this.allExitPositions) {
      {
        sokobanObject obj = this.findObjectAt(perceivedObjects, exitPos);
        if ((obj instanceof BoxObject)) {
          int distance = this.calculateDistance(((BoxObject)obj).getPosition(), this.myClaimedBox);
          if ((distance <= 1)) {
            return true;
          }
        }
      }
    }
    return false;
  }

  /**
   * Clean up team state when releasing a claim.
   */
  protected void cleanupTeamState() {
    this.myTaskRole = null;
    this.teamMembers.clear();
    this.helperReadyAgents.clear();
  }

  /**
   * Calculate push position (behind box, toward exit).
   */
  protected void calculatePushPositionForBox(final Point2i boxPos, final Point2i exitPos) {
    if (((boxPos == null) || (exitPos == null))) {
      this.myPushPosition = null;
      return;
    }
    int _x = exitPos.getX();
    int _x_1 = boxPos.getX();
    int dx = (_x - _x_1);
    int _y = exitPos.getY();
    int _y_1 = boxPos.getY();
    int dy = (_y - _y_1);
    int normDx = 0;
    int normDy = 0;
    int _abs = Math.abs(dx);
    int _abs_1 = Math.abs(dy);
    if ((_abs > _abs_1)) {
      int _xifexpression = (int) 0;
      if ((dx > 0)) {
        _xifexpression = 1;
      } else {
        _xifexpression = (-1);
      }
      normDx = _xifexpression;
    } else {
      if ((dy != 0)) {
        int _xifexpression_1 = (int) 0;
        if ((dy > 0)) {
          _xifexpression_1 = 1;
        } else {
          _xifexpression_1 = (-1);
        }
        normDy = _xifexpression_1;
      } else {
        this.myPushPosition = null;
        return;
      }
    }
    int _x_2 = boxPos.getX();
    int _y_2 = boxPos.getY();
    Point2i _point2i = new Point2i((_x_2 - normDx), (_y_2 - normDy));
    this.myPushPosition = _point2i;
  }

  /**
   * Calculate push direction (from box toward exit).
   */
  @Pure
  protected Direction calculatePushDirection(final Point2i boxPos, final Point2i exitPos) {
    if (((boxPos == null) || (exitPos == null))) {
      return null;
    }
    int _x = exitPos.getX();
    int _x_1 = boxPos.getX();
    int dx = (_x - _x_1);
    int _y = exitPos.getY();
    int _y_1 = boxPos.getY();
    int dy = (_y - _y_1);
    int _abs = Math.abs(dx);
    int _abs_1 = Math.abs(dy);
    if ((_abs > _abs_1)) {
      Direction _xifexpression = null;
      if ((dx > 0)) {
        _xifexpression = Direction.EAST;
      } else {
        _xifexpression = Direction.WEST;
      }
      return _xifexpression;
    } else {
      if ((dy != 0)) {
        Direction _xifexpression_1 = null;
        if ((dy > 0)) {
          _xifexpression_1 = Direction.SOUTH;
        } else {
          _xifexpression_1 = Direction.NORTH;
        }
        return _xifexpression_1;
      }
    }
    return null;
  }

  /**
   * Calculate position for helper agent to assist pushing.
   * 
   * Helper should be adjacent to box, perpendicular to push direction.
   * Example: If pushing EAST, helper can be NORTH or SOUTH of box.
   * 
   * @param boxPos current box position
   * @param pushDirection direction box is being pushed
   * @param perceivedObjects list of perceived objects (for obstacle detection)
   * @return position for helper, or null if no valid position
   */
  protected Point2i calculateHelperPosition(final Point2i boxPos, final Direction pushDirection, final List<sokobanObject> perceivedObjects) {
    if (((boxPos == null) || (pushDirection == null))) {
      return null;
    }
    ArrayList<Point2i> candidatePositions = CollectionLiterals.<Point2i>newArrayList();
    if (pushDirection != null) {
      switch (pushDirection) {
        case NORTH:
          int _x = boxPos.getX();
          int _y = boxPos.getY();
          Point2i _point2i = new Point2i((_x - 1), _y);
          candidatePositions.add(_point2i);
          int _x_1 = boxPos.getX();
          int _y_1 = boxPos.getY();
          Point2i _point2i_1 = new Point2i((_x_1 + 1), _y_1);
          candidatePositions.add(_point2i_1);
          break;
        case SOUTH:
          int _x_2 = boxPos.getX();
          int _y_2 = boxPos.getY();
          Point2i _point2i_2 = new Point2i((_x_2 - 1), _y_2);
          candidatePositions.add(_point2i_2);
          int _x_3 = boxPos.getX();
          int _y_3 = boxPos.getY();
          Point2i _point2i_3 = new Point2i((_x_3 + 1), _y_3);
          candidatePositions.add(_point2i_3);
          break;
        case EAST:
          int _x_4 = boxPos.getX();
          int _y_4 = boxPos.getY();
          Point2i _point2i_4 = new Point2i(_x_4, (_y_4 - 1));
          candidatePositions.add(_point2i_4);
          int _x_5 = boxPos.getX();
          int _y_5 = boxPos.getY();
          Point2i _point2i_5 = new Point2i(_x_5, (_y_5 + 1));
          candidatePositions.add(_point2i_5);
          break;
        case WEST:
          int _x_6 = boxPos.getX();
          int _y_6 = boxPos.getY();
          Point2i _point2i_6 = new Point2i(_x_6, (_y_6 - 1));
          candidatePositions.add(_point2i_6);
          int _x_7 = boxPos.getX();
          int _y_7 = boxPos.getY();
          Point2i _point2i_7 = new Point2i(_x_7, (_y_7 + 1));
          candidatePositions.add(_point2i_7);
          break;
        default:
          return null;
      }
    } else {
      return null;
    }
    for (final Point2i pos : candidatePositions) {
      {
        sokobanObject obj = this.findObjectAt(perceivedObjects, pos);
        if ((obj == null)) {
          return pos;
        }
        if ((obj instanceof ExitObject)) {
          return pos;
        }
      }
    }
    return null;
  }

  /**
   * Calculate alternative helper position if primary is blocked.
   */
  protected Point2i calculateAlternativeHelperPosition(final Point2i boxPos, final Direction pushDirection, final Point2i blockedPos, final List<sokobanObject> perceivedObjects) {
    if (((boxPos == null) || (pushDirection == null))) {
      return null;
    }
    ArrayList<Point2i> candidatePositions = CollectionLiterals.<Point2i>newArrayList();
    if (pushDirection != null) {
      switch (pushDirection) {
        case NORTH:
          int _x = boxPos.getX();
          int _y = boxPos.getY();
          Point2i _point2i = new Point2i((_x - 1), (_y + 1));
          candidatePositions.add(_point2i);
          int _x_1 = boxPos.getX();
          int _y_1 = boxPos.getY();
          Point2i _point2i_1 = new Point2i((_x_1 + 1), (_y_1 + 1));
          candidatePositions.add(_point2i_1);
          int _x_2 = boxPos.getX();
          int _y_2 = boxPos.getY();
          Point2i _point2i_2 = new Point2i(_x_2, (_y_2 + 1));
          candidatePositions.add(_point2i_2);
          break;
        case SOUTH:
          int _x_3 = boxPos.getX();
          int _y_3 = boxPos.getY();
          Point2i _point2i_3 = new Point2i((_x_3 - 1), (_y_3 - 1));
          candidatePositions.add(_point2i_3);
          int _x_4 = boxPos.getX();
          int _y_4 = boxPos.getY();
          Point2i _point2i_4 = new Point2i((_x_4 + 1), (_y_4 - 1));
          candidatePositions.add(_point2i_4);
          int _x_5 = boxPos.getX();
          int _y_5 = boxPos.getY();
          Point2i _point2i_5 = new Point2i(_x_5, (_y_5 - 1));
          candidatePositions.add(_point2i_5);
          break;
        case EAST:
          int _x_6 = boxPos.getX();
          int _y_6 = boxPos.getY();
          Point2i _point2i_6 = new Point2i((_x_6 - 1), (_y_6 - 1));
          candidatePositions.add(_point2i_6);
          int _x_7 = boxPos.getX();
          int _y_7 = boxPos.getY();
          Point2i _point2i_7 = new Point2i((_x_7 - 1), (_y_7 + 1));
          candidatePositions.add(_point2i_7);
          int _x_8 = boxPos.getX();
          int _y_8 = boxPos.getY();
          Point2i _point2i_8 = new Point2i((_x_8 - 1), _y_8);
          candidatePositions.add(_point2i_8);
          break;
        case WEST:
          int _x_9 = boxPos.getX();
          int _y_9 = boxPos.getY();
          Point2i _point2i_9 = new Point2i((_x_9 + 1), (_y_9 - 1));
          candidatePositions.add(_point2i_9);
          int _x_10 = boxPos.getX();
          int _y_10 = boxPos.getY();
          Point2i _point2i_10 = new Point2i((_x_10 + 1), (_y_10 + 1));
          candidatePositions.add(_point2i_10);
          int _x_11 = boxPos.getX();
          int _y_11 = boxPos.getY();
          Point2i _point2i_11 = new Point2i((_x_11 + 1), _y_11);
          candidatePositions.add(_point2i_11);
          break;
        default:
          return null;
      }
    } else {
      return null;
    }
    for (final Point2i pos : candidatePositions) {
      {
        boolean _equals = pos.equals(blockedPos);
        if (_equals) {
          continue;
        }
        sokobanObject obj = this.findObjectAt(perceivedObjects, pos);
        if ((obj == null)) {
          return pos;
        }
        if ((obj instanceof ExitObject)) {
          return pos;
        }
      }
    }
    return null;
  }

  /**
   * Plan an efficient path from box position to exit using BFS.
   * 
   * This finds the shortest path the box should take to reach the exit,
   * considering obstacles (walls, other boxes).
   * 
   * @param boxPos current box position
   * @param exitPos target exit position
   * @param perceivedObjects list of perceived objects (for obstacle detection)
   * @return list of positions representing the path, or empty list if no path
   */
  protected LinkedList<Point2i> planBoxPath(final Point2i boxPos, final Point2i exitPos, final List<sokobanObject> perceivedObjects) {
    if (((boxPos == null) || (exitPos == null))) {
      return CollectionLiterals.<Point2i>newLinkedList();
    }
    HashSet<Point2i> obstacles = CollectionLiterals.<Point2i>newHashSet();
    for (final sokobanObject obj : perceivedObjects) {
      if (((obj != null) && ((obj instanceof WallObject) || ((obj instanceof BoxObject) && (!obj.getPosition().equals(boxPos)))))) {
        obstacles.add(obj.getPosition());
      }
    }
    for (final Point2i knownBoxPos : this.allBoxPositions) {
      if (((!knownBoxPos.equals(boxPos)) && (!this.completedBoxes.contains(knownBoxPos)))) {
        obstacles.add(knownBoxPos);
      }
    }
    LinkedList<Point2i> queue = new LinkedList<Point2i>();
    HashSet<Point2i> visited = CollectionLiterals.<Point2i>newHashSet();
    HashMap<Point2i, Point2i> parent = CollectionLiterals.<Point2i, Point2i>newHashMap();
    queue.add(boxPos);
    visited.add(boxPos);
    parent.put(boxPos, null);
    Point2i _point2i = new Point2i(0, (-1));
    Point2i _point2i_1 = new Point2i(0, 1);
    Point2i _point2i_2 = new Point2i((-1), 0);
    Point2i _point2i_3 = new Point2i(1, 0);
    final List<Point2i> directions = Collections.<Point2i>unmodifiableList(CollectionLiterals.<Point2i>newArrayList(_point2i, _point2i_1, _point2i_2, _point2i_3));
    boolean found = false;
    while (((!queue.isEmpty()) && (!found))) {
      {
        Point2i current = queue.poll();
        if (((current.getX() == exitPos.getX()) && (current.getY() == exitPos.getY()))) {
          found = true;
          break;
        }
        for (final Point2i dir : directions) {
          {
            int _x = current.getX();
            int _x_1 = dir.getX();
            int _y = current.getY();
            int _y_1 = dir.getY();
            Point2i nextPos = new Point2i((_x + _x_1), (_y + _y_1));
            if ((visited.contains(nextPos) || obstacles.contains(nextPos))) {
              continue;
            }
            boolean _isNextToOuterBorder = this.isNextToOuterBorder(nextPos);
            if (_isNextToOuterBorder) {
              continue;
            }
            int blockedCount = 0;
            for (final Point2i checkDir : directions) {
              {
                int _x_2 = nextPos.getX();
                int _x_3 = checkDir.getX();
                int _y_2 = nextPos.getY();
                int _y_3 = checkDir.getY();
                Point2i checkPos = new Point2i((_x_2 + _x_3), (_y_2 + _y_3));
                if ((obstacles.contains(checkPos) || this.isNextToOuterBorder(checkPos))) {
                  blockedCount++;
                }
              }
            }
            if ((blockedCount >= 2)) {
              continue;
            }
            visited.add(nextPos);
            parent.put(nextPos, current);
            queue.add(nextPos);
          }
        }
      }
    }
    if (found) {
      LinkedList<Point2i> path = new LinkedList<Point2i>();
      Point2i current = exitPos;
      while ((current != null)) {
        {
          path.addFirst(current);
          current = parent.get(current);
        }
      }
      return path;
    }
    return CollectionLiterals.<Point2i>newLinkedList();
  }

  /**
   * Calculate efficiency score for handling a box.
   * 
   * Lower score is better. Considers:
   * - Distance from agent to box (alignment cost)
   * - Path length from box to exit (pushing cost)
   * - Box priority (boxes closer to exits are more valuable)
   * 
   * @param agentPos current agent position
   * @param boxPos box position
   * @param exitPos target exit position
   * @param pathLength length of planned path from box to exit
   * @return efficiency score (lower is better)
   */
  @Pure
  protected double calculateEfficiencyScore(final Point2i agentPos, final Point2i boxPos, final Point2i exitPos, final int pathLength) {
    if ((((agentPos == null) || (boxPos == null)) || (exitPos == null))) {
      return Double.MAX_VALUE;
    }
    int alignmentCost = this.calculateDistance(agentPos, boxPos);
    int pathCost = pathLength;
    int totalCost = (alignmentCost + pathCost);
    int boxToExitDist = this.calculateDistance(boxPos, exitPos);
    double priorityBonus = (boxToExitDist * 0.1);
    return (totalCost + priorityBonus);
  }

  /**
   * Check if a position would create a deadlock (box in corner or blocked).
   * A deadlock occurs when a box is adjacent to 2+ walls or blocked by boxes.
   */
  @Pure
  protected boolean isDeadlockPosition(final Point2i boxPos, final List<sokobanObject> perceivedObjects) {
    if ((boxPos == null)) {
      return false;
    }
    Point2i _point2i = new Point2i(0, (-1));
    Point2i _point2i_1 = new Point2i(0, 1);
    Point2i _point2i_2 = new Point2i((-1), 0);
    Point2i _point2i_3 = new Point2i(1, 0);
    final List<Point2i> directions = Collections.<Point2i>unmodifiableList(CollectionLiterals.<Point2i>newArrayList(_point2i, _point2i_1, _point2i_2, _point2i_3));
    int blockedCount = 0;
    for (final Point2i dir : directions) {
      {
        int _x = boxPos.getX();
        int _x_1 = dir.getX();
        int _y = boxPos.getY();
        int _y_1 = dir.getY();
        Point2i adjPos = new Point2i((_x + _x_1), (_y + _y_1));
        sokobanObject adjObj = this.findObjectAt(perceivedObjects, adjPos);
        if (((adjObj != null) && ((adjObj instanceof WallObject) || (adjObj instanceof BoxObject)))) {
          blockedCount++;
        }
      }
    }
    return (blockedCount >= 2);
  }

  /**
   * Check if a position is next to the OUTER border wall.
   * 
   * This only checks for the outer perimeter border, NOT inner walls.
   * A box is "next to border" if it's at coordinates very close to the edges:
   * - x <= 1 (one cell away from left border at x=0)
   * - x >= width-2 (one cell away from right border at x=width-1)
   * - y <= 1 (one cell away from top border at y=0)
   * - y >= height-2 (one cell away from bottom border at y=height-1)
   * 
   * Since agents don't know exact dimensions, we infer from global knowledge.
   * The maze is typically 20x20, so we use that as default and refine from observations.
   */
  @Pure
  protected boolean isNextToOuterBorder(final Point2i boxPos) {
    if ((boxPos == null)) {
      return false;
    }
    int maxX = 0;
    int maxY = 0;
    for (final Point2i knownBoxPos : this.allBoxPositions) {
      {
        maxX = Math.max(maxX, knownBoxPos.getX());
        maxY = Math.max(maxY, knownBoxPos.getY());
      }
    }
    for (final Point2i exitPos : this.allExitPositions) {
      {
        maxX = Math.max(maxX, exitPos.getX());
        maxY = Math.max(maxY, exitPos.getY());
      }
    }
    if (((maxX == 0) && (maxY == 0))) {
      maxX = 20;
      maxY = 20;
    } else {
      maxX = Math.max((maxX + 2), 20);
      maxY = Math.max((maxY + 2), 20);
    }
    return ((((boxPos.getX() <= 1) || (boxPos.getX() >= (maxX - 2))) || (boxPos.getY() <= 1)) || (boxPos.getY() >= (maxY - 2)));
  }

  /**
   * Find an adjacent push position if the primary push position is blocked.
   * Tries positions adjacent to the box that are not blocked.
   */
  @Pure
  protected Point2i findAdjacentPushPosition(final Point2i boxPos, final Point2i exitPos, final List<sokobanObject> perceivedObjects) {
    if (((boxPos == null) || (exitPos == null))) {
      return null;
    }
    Point2i _point2i = new Point2i(0, (-1));
    Point2i _point2i_1 = new Point2i(0, 1);
    Point2i _point2i_2 = new Point2i((-1), 0);
    Point2i _point2i_3 = new Point2i(1, 0);
    final List<Point2i> adjacentOffsets = Collections.<Point2i>unmodifiableList(CollectionLiterals.<Point2i>newArrayList(_point2i, _point2i_1, _point2i_2, _point2i_3));
    Point2i bestPos = null;
    int bestDistance = Integer.MAX_VALUE;
    for (final Point2i offset : adjacentOffsets) {
      {
        int _x = boxPos.getX();
        int _x_1 = offset.getX();
        int _y = boxPos.getY();
        int _y_1 = offset.getY();
        Point2i adjPos = new Point2i((_x + _x_1), (_y + _y_1));
        sokobanObject adjObj = this.findObjectAt(perceivedObjects, adjPos);
        if (((adjObj instanceof WallObject) || (adjObj instanceof BoxObject))) {
          continue;
        }
        int distance = this.calculateDistance(adjPos, exitPos);
        if ((distance < bestDistance)) {
          bestDistance = distance;
          bestPos = adjPos;
        }
      }
    }
    return bestPos;
  }

  /**
   * Find alternative push direction if primary direction would create deadlock.
   * Tries other directions that still move box toward exit.
   */
  @Pure
  protected Direction findAlternativePushDirection(final Point2i boxPos, final Point2i exitPos, final Direction avoidDirection, final List<sokobanObject> perceivedObjects) {
    if (((boxPos == null) || (exitPos == null))) {
      return null;
    }
    final List<Direction> allDirections = Collections.<Direction>unmodifiableList(CollectionLiterals.<Direction>newArrayList(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST));
    Direction bestDirection = null;
    int bestDistance = Integer.MAX_VALUE;
    for (final Direction dir : allDirections) {
      {
        boolean _equals = Objects.equals(dir, avoidDirection);
        if (_equals) {
          continue;
        }
        Point2i nextPos = this.calculateNextPosition(boxPos, dir);
        if ((this.isDeadlockPosition(nextPos, perceivedObjects) || this.isNextToOuterBorder(nextPos))) {
          continue;
        }
        int currentDistance = this.calculateDistance(boxPos, exitPos);
        int nextDistance = this.calculateDistance(nextPos, exitPos);
        if (((nextDistance < currentDistance) && (nextDistance < bestDistance))) {
          bestDistance = nextDistance;
          bestDirection = dir;
        }
      }
    }
    return bestDirection;
  }

  /**
   * Check if agent is blocking another agent.
   * An agent is blocking if another agent is trying to reach the same box/position.
   */
  @Pure
  protected boolean isBlockingOtherAgent(final List<sokobanObject> perceivedObjects) {
    if ((this.myClaimedBox == null)) {
      return false;
    }
    Set<Map.Entry<UUID, Point2i>> _entrySet = this.otherAgentTargets.entrySet();
    for (final Map.Entry<UUID, Point2i> entry : _entrySet) {
      {
        UUID otherAgentId = entry.getKey();
        Point2i otherTargetBox = entry.getValue();
        if ((otherAgentId.equals(this.getID()) || (otherTargetBox == null))) {
          continue;
        }
        if (((otherTargetBox.getX() == this.myClaimedBox.getX()) && (otherTargetBox.getY() == this.myClaimedBox.getY()))) {
          Point2i otherAgentPos = this.otherAgentPositions.get(otherAgentId);
          if ((otherAgentPos != null)) {
            int distanceOtherToBox = this.calculateDistance(otherAgentPos, this.myClaimedBox);
            int distanceMeToBox = this.calculateDistance(this.myPosition, this.myClaimedBox);
            if ((distanceOtherToBox < distanceMeToBox)) {
              int _x = this.myClaimedBox.getX();
              int _x_1 = otherAgentPos.getX();
              int dx = (_x - _x_1);
              int _y = this.myClaimedBox.getY();
              int _y_1 = otherAgentPos.getY();
              int dy = (_y - _y_1);
              int _x_2 = this.myPosition.getX();
              int _x_3 = otherAgentPos.getX();
              int myDx = (_x_2 - _x_3);
              int _y_2 = this.myPosition.getY();
              int _y_3 = otherAgentPos.getY();
              int myDy = (_y_2 - _y_3);
              if (((((((dx > 0) && (myDx > 0)) && (myDx < dx)) || (((dx < 0) && (myDx < 0)) && (myDx > dx))) || (((dy > 0) && (myDy > 0)) && (myDy < dy))) || (((dy < 0) && (myDy < 0)) && (myDy > dy)))) {
                String _agentName = this.getAgentName();
                System.out.println(((((("[Agent " + _agentName) + "] Blocking agent ") + otherAgentId) + " trying to reach box at ") + this.myClaimedBox));
                return true;
              }
            }
          }
        }
      }
    }
    return false;
  }

  /**
   * Reposition to allow another agent to pass.
   * Moves away from the blocking position temporarily.
   */
  protected void repositionToAllowPassage(final List<sokobanObject> perceivedObjects) {
    List<Direction> safeDirections = Collections.<Direction>unmodifiableList(CollectionLiterals.<Direction>newArrayList(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST));
    for (final Direction dir : safeDirections) {
      {
        Point2i nextPos = this.calculateNextPosition(this.myPosition, dir);
        sokobanObject blockingObj = this.findObjectAt(perceivedObjects, nextPos);
        if (((blockingObj == null) || (!blockingObj.isOccluder()))) {
          Point2i tempPos = this.myPosition;
          this.myPosition = nextPos;
          boolean _isBlockingOtherAgent = this.isBlockingOtherAgent(perceivedObjects);
          if ((!_isBlockingOtherAgent)) {
            this.myPosition = tempPos;
            this.emitAction(dir);
            String _agentName = this.getAgentName();
            System.out.println((((("[Agent " + _agentName) + "] Repositioning to ") + nextPos) + " to allow passage"));
            return;
          }
          this.myPosition = tempPos;
        }
      }
    }
    String _agentName = this.getAgentName();
    System.out.println((("[Agent " + _agentName) + "] Cannot reposition - staying in place"));
  }

  /**
   * Calculate next position after moving in a direction.
   */
  @Pure
  protected Point2i calculateNextPosition(final Point2i currentPos, final Direction direction) {
    if (((currentPos == null) || (direction == null))) {
      return null;
    }
    if (direction != null) {
      switch (direction) {
        case NORTH:
          int _x = currentPos.getX();
          int _y = currentPos.getY();
          return new Point2i(_x, (_y - 1));
        case SOUTH:
          int _x_1 = currentPos.getX();
          int _y_1 = currentPos.getY();
          return new Point2i(_x_1, (_y_1 + 1));
        case WEST:
          int _x_2 = currentPos.getX();
          int _y_2 = currentPos.getY();
          return new Point2i((_x_2 - 1), _y_2);
        case EAST:
          int _x_3 = currentPos.getX();
          int _y_3 = currentPos.getY();
          return new Point2i((_x_3 + 1), _y_3);
        default:
          return currentPos;
      }
    } else {
      return currentPos;
    }
  }

  /**
   * Find a box that is not yet on a goal (needs to be moved to goal).
   */
  @Pure
  protected BoxObject findBoxNotOnGoal(final List<BoxObject> boxes) {
    for (final BoxObject box : boxes) {
      boolean _isOnTarget = box.isOnTarget();
      if ((!_isOnTarget)) {
        return box;
      }
    }
    return null;
  }

  /**
   * Calculate direction to move toward target using BFS pathfinding.
   * 
   * This replaces the greedy Manhattan distance approach with a proper
   * Breadth-First Search pathfinding algorithm that can navigate around
   * obstacles (walls, boxes, other agents).
   * 
   * @param target the target position to reach
   * @param perceivedObjects list of objects visible to the agent
   * @return the first move direction toward the target, or null if no path exists
   */
  protected Direction calculateDirectionToTarget(final Point2i target, final List<sokobanObject> perceivedObjects) {
    if (((target == null) || (this.myPosition == null))) {
      return null;
    }
    int _x = target.getX();
    int _x_1 = this.myPosition.getX();
    int dx = (_x - _x_1);
    int _y = target.getY();
    int _y_1 = this.myPosition.getY();
    int dy = (_y - _y_1);
    int _abs = Math.abs(dx);
    int _abs_1 = Math.abs(dy);
    if (((_abs + _abs_1) == 1)) {
      int _x_2 = this.myPosition.getX();
      int _y_2 = this.myPosition.getY();
      Point2i nextPos = new Point2i((_x_2 + dx), (_y_2 + dy));
      sokobanObject blockingObj = this.findObjectAt(perceivedObjects, nextPos);
      if (((blockingObj == null) || (!blockingObj.isOccluder()))) {
        if ((dx > 0)) {
          return Direction.EAST;
        }
        if ((dx < 0)) {
          return Direction.WEST;
        }
        if ((dy > 0)) {
          return Direction.SOUTH;
        }
        if ((dy < 0)) {
          return Direction.NORTH;
        }
      }
    }
    return this.findNextMoveBFS(this.myPosition, target, perceivedObjects);
  }

  /**
   * Breadth-First Search pathfinding to find the next move direction.
   * 
   * Builds a local grid from perceptions and performs BFS to find the shortest
   * path from start to target. Returns only the first move direction.
   * 
   * @param start starting position
   * @param target target position
   * @param perceivedObjects list of perceived objects
   * @return first move direction, or null if no path exists
   */
  protected Direction findNextMoveBFS(final Point2i start, final Point2i target, final List<sokobanObject> perceivedObjects) {
    if (((start == null) || (target == null))) {
      return null;
    }
    final int gridSize = 30;
    final int halfSize = (gridSize / 2);
    int _x = start.getX();
    final int minX = (_x - halfSize);
    int _x_1 = start.getX();
    final int maxX = (_x_1 + halfSize);
    int _y = start.getY();
    final int minY = (_y - halfSize);
    int _y_1 = start.getY();
    final int maxY = (_y_1 + halfSize);
    HashMap<String, Boolean> grid = new HashMap<String, Boolean>();
    for (final sokobanObject obj : perceivedObjects) {
      {
        Point2i pos = obj.getPosition();
        if (((((pos.getX() >= minX) && (pos.getX() <= maxX)) && (pos.getY() >= minY)) && (pos.getY() <= maxY))) {
          int _x_2 = pos.getX();
          String _plus = (Integer.valueOf(_x_2) + ",");
          int _y_2 = pos.getY();
          String key = (_plus + Integer.valueOf(_y_2));
          boolean _isOccluder = obj.isOccluder();
          if (_isOccluder) {
            grid.put(key, Boolean.valueOf(false));
          } else {
            grid.put(key, Boolean.valueOf(true));
          }
        }
      }
    }
    if (((((target.getX() < minX) || (target.getX() > maxX)) || (target.getY() < minY)) || (target.getY() > maxY))) {
      return this.calculateDirectionToTargetGreedy(target);
    }
    LinkedList<Point2i> queue = new LinkedList<Point2i>();
    queue.add(start);
    HashSet<String> visited = new HashSet<String>();
    int _x_2 = start.getX();
    String _plus = (Integer.valueOf(_x_2) + ",");
    int _y_2 = start.getY();
    visited.add((_plus + Integer.valueOf(_y_2)));
    HashMap<String, Point2i> parent = new HashMap<String, Point2i>();
    int _x_3 = start.getX();
    String _plus_1 = (Integer.valueOf(_x_3) + ",");
    int _y_3 = start.getY();
    parent.put((_plus_1 + Integer.valueOf(_y_3)), null);
    final List<Integer> dxs = Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(0), Integer.valueOf((-1))));
    final List<Integer> dys = Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf((-1)), Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(0)));
    while ((!queue.isEmpty())) {
      {
        Point2i current = queue.poll();
        if (((current.getX() == target.getX()) && (current.getY() == target.getY()))) {
          return this.reconstructFirstMove(start, target, parent);
        }
        int _size = dxs.size();
        ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size, true);
        for (final Integer i : _doubleDotLessThan) {
          {
            Integer dx = dxs.get(((i) == null ? 0 : (i).intValue()));
            Integer dy = dys.get(((i) == null ? 0 : (i).intValue()));
            int _x_4 = current.getX();
            int nextX = (_x_4 + ((dx) == null ? 0 : (dx).intValue()));
            int _y_4 = current.getY();
            int nextY = (_y_4 + ((dy) == null ? 0 : (dy).intValue()));
            String _plus_2 = (Integer.valueOf(nextX) + ",");
            String nextKey = (_plus_2 + Integer.valueOf(nextY));
            if (((((nextX < minX) || (nextX > maxX)) || (nextY < minY)) || (nextY > maxY))) {
              continue;
            }
            boolean _contains = visited.contains(nextKey);
            if (_contains) {
              continue;
            }
            boolean isTarget = ((nextX == target.getX()) && (nextY == target.getY()));
            Boolean isWalkable = grid.get(nextKey);
            if ((isWalkable == null)) {
              isWalkable = Boolean.valueOf(true);
            }
            if (((!((isWalkable) == null ? false : (isWalkable).booleanValue())) && (!isTarget))) {
              continue;
            }
            Point2i nextPos = new Point2i(nextX, nextY);
            queue.add(nextPos);
            visited.add(nextKey);
            parent.put(nextKey, current);
          }
        }
      }
    }
    String _agentName = this.getAgentName();
    System.out.println((((((("[Agent " + _agentName) + "] BFS: No path found from ") + start) + " to ") + target) + ", using greedy fallback"));
    return this.calculateDirectionToTargetGreedy(target);
  }

  /**
   * Reconstruct the first move direction from BFS parent map.
   * 
   * @param start starting position
   * @param target target position
   * @param parent parent map from BFS
   * @return first move direction
   */
  protected Direction reconstructFirstMove(final Point2i start, final Point2i target, final Map<String, Point2i> parent) {
    Point2i current = target;
    ArrayList<Point2i> path = new ArrayList<Point2i>();
    while (((current != null) && (!current.equals(start)))) {
      {
        path.add(0, current);
        int _x = current.getX();
        String _plus = (Integer.valueOf(_x) + ",");
        int _y = current.getY();
        String key = (_plus + Integer.valueOf(_y));
        current = parent.get(key);
      }
    }
    boolean _isEmpty = path.isEmpty();
    if (_isEmpty) {
      return null;
    }
    Point2i firstStep = path.get(0);
    int _x = firstStep.getX();
    int _x_1 = start.getX();
    int dx = (_x - _x_1);
    int _y = firstStep.getY();
    int _y_1 = start.getY();
    int dy = (_y - _y_1);
    if ((dx > 0)) {
      return Direction.EAST;
    }
    if ((dx < 0)) {
      return Direction.WEST;
    }
    if ((dy > 0)) {
      return Direction.SOUTH;
    }
    if ((dy < 0)) {
      return Direction.NORTH;
    }
    return null;
  }

  /**
   * Greedy fallback when BFS fails (for targets outside perception range).
   * 
   * @param target target position
   * @return greedy direction
   */
  @Pure
  protected Direction calculateDirectionToTargetGreedy(final Point2i target) {
    if (((target == null) || (this.myPosition == null))) {
      return null;
    }
    int _x = target.getX();
    int _x_1 = this.myPosition.getX();
    int dx = (_x - _x_1);
    int _y = target.getY();
    int _y_1 = this.myPosition.getY();
    int dy = (_y - _y_1);
    int _abs = Math.abs(dx);
    int _abs_1 = Math.abs(dy);
    if ((_abs > _abs_1)) {
      Direction _xifexpression = null;
      if ((dx > 0)) {
        _xifexpression = Direction.EAST;
      } else {
        _xifexpression = Direction.WEST;
      }
      return _xifexpression;
    } else {
      if ((dy != 0)) {
        Direction _xifexpression_1 = null;
        if ((dy > 0)) {
          _xifexpression_1 = Direction.SOUTH;
        } else {
          _xifexpression_1 = Direction.NORTH;
        }
        return _xifexpression_1;
      }
    }
    return null;
  }

  /**
   * Calculate next position given current position and direction.
   */
  @Pure
  protected Point2i calculateNextPosition(final Point2i pos, final Direction dir) {
    if (((pos == null) || (dir == null))) {
      return null;
    }
    Vector2i vec = dir.toVector();
    int _x = pos.getX();
    int _x_1 = vec.getX();
    int _y = pos.getY();
    int _y_1 = vec.getY();
    return new Point2i((_x + _x_1), (_y + _y_1));
  }

  /**
   * Find object at given position in perceived objects.
   */
  @Pure
  protected sokobanObject findObjectAt(final List<sokobanObject> perceivedObjects, final Point2i pos) {
    if ((pos == null)) {
      return null;
    }
    for (final sokobanObject obj : perceivedObjects) {
      boolean _equals = obj.getPosition().equals(pos);
      if (_equals) {
        return obj;
      }
    }
    return null;
  }

  /**
   * Find alternative direction to target when primary direction is blocked.
   */
  @Pure
  protected Direction findAlternativeDirectionToTarget(final Point2i target, final Direction blockedDirection, final List<sokobanObject> perceivedObjects) {
    if (((target == null) || (this.myPosition == null))) {
      return null;
    }
    List<Direction> directions = Collections.<Direction>unmodifiableList(CollectionLiterals.<Direction>newArrayList(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST));
    Direction bestDir = null;
    int minDistance = Integer.MAX_VALUE;
    for (final Direction dir : directions) {
      {
        boolean _equals = Objects.equals(dir, blockedDirection);
        if (_equals) {
          continue;
        }
        Point2i nextPos = this.calculateNextPosition(this.myPosition, dir);
        sokobanObject nextPosObj = this.findObjectAt(perceivedObjects, nextPos);
        if (((nextPosObj == null) || (nextPosObj instanceof ExitObject))) {
          int dist = this.calculateDistance(nextPos, target);
          if ((dist < minDistance)) {
            minDistance = dist;
            bestDir = dir;
          }
        }
      }
    }
    return bestDir;
  }

  /**
   * Wrapper for move() that ensures only one action per step.
   */
  protected void emitAction(final Direction direction) {
    if (this.actionEmittedThisStep) {
      String _agentName = this.getAgentName();
      System.out.println(((("[Agent " + _agentName) + "] Action already emitted this step, ignoring ") + direction));
      return;
    }
    this.actionEmittedThisStep = true;
    MazeMotion _$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMOTION$CALLER = this.$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMOTION$CALLER();
    _$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMOTION$CALLER.move(direction);
  }

  /**
   * Validate if pushing state is still valid before continuing.
   * 
   * Checks if:
   * - Task exists
   * - Box still exists and is visible
   * - Box is not already on goal
   * 
   * @param perceivedObjects list of perceived objects
   * @return true if pushing state is valid, false otherwise
   */
  @Pure
  protected boolean validatePushingState(final List<sokobanObject> perceivedObjects) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method or field getBoxPosition is undefined for the type Point2i");
  }

  /**
   * Validate that the current task is still valid.
   */
  protected boolean validateTask(final List<sokobanObject> perceivedObjects) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method or field getBoxPosition is undefined for the type Point2i"
      + "\nThe method taskFailureCount(int) is undefined for the type SokobanAgent"
      + "\nThe method taskFailureCount(int) is undefined for the type SokobanAgent"
      + "\nThe method updateBoxPosition(Point2i) is undefined for the type Point2i"
      + "\nThe method or field getTargetGoal is undefined for the type Point2i"
      + "\nThe method or field taskFailureCount is undefined for the type SokobanAgent"
      + "\nThe method or field taskFailureCount is undefined for the type SokobanAgent"
      + "\nThe method or field MAX_TASK_FAILURES is undefined"
      + "\nThe method or field taskFailureCount is undefined for the type SokobanAgent"
      + "\nThe method or field MAX_TASK_FAILURES is undefined"
      + "\nThe method or field MAX_TASK_FAILURES is undefined"
      + "\n++ cannot be resolved"
      + "\n>= cannot be resolved");
  }

  /**
   * Cancel the current task and reset state.
   */
  protected void cancelTask(final String reason) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method or field getBoxId is undefined for the type Point2i"
      + "\nThe method or field getBoxId is undefined for the type Point2i"
      + "\nThe method or field readyAgents is undefined for the type SokobanAgent"
      + "\nThe method taskFailureCount(int) is undefined for the type SokobanAgent"
      + "\nThe method coordinationState(String) is undefined for the type SokobanAgent"
      + "\nclear cannot be resolved");
  }

  /**
   * Handle box blocking the path - UPDATED to use clearing points instead of exit.
   */
  protected void handleBoxBlocking(final BoxObject box, final Direction direction, final Point2i boxPos) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method or field getBoxPosition is undefined for the type Point2i"
      + "\nThe method or field getPushDirection is undefined for the type Point2i"
      + "\nThe method coordinationState(String) is undefined for the type SokobanAgent"
      + "\nThe method assignedBox(Point2i) is undefined for the type SokobanAgent"
      + "\nThe method or field pendingHelpRequest is undefined for the type SokobanAgent"
      + "\nThe method pendingHelpRequest(RequestHelp) is undefined for the type SokobanAgent"
      + "\nThe method or field pendingHelpRequest is undefined for the type SokobanAgent"
      + "\nequals cannot be resolved"
      + "\n=== cannot be resolved");
  }

  /**
   * Find boxes in perceived objects.
   */
  protected List<BoxObject> findBoxes(final List<sokobanObject> perceivedObjects) {
    ArrayList<BoxObject> boxes = new ArrayList<BoxObject>();
    String _agentName = this.getAgentName();
    int _size = perceivedObjects.size();
    System.out.println((((("[Agent " + _agentName) + "] findBoxes: checking ") + Integer.valueOf(_size)) + " perceived objects"));
    for (final sokobanObject obj : perceivedObjects) {
      {
        String _agentName_1 = this.getAgentName();
        String _simpleName = obj.getClass().getSimpleName();
        Point2i _position = obj.getPosition();
        System.out.println(((((("[Agent " + _agentName_1) + "]   Object type: ") + _simpleName) + " at ") + _position));
        if ((obj instanceof BoxObject)) {
          String _agentName_2 = this.getAgentName();
          Point2i _position_1 = ((BoxObject)obj).getPosition();
          System.out.println(((("[Agent " + _agentName_2) + "]   Found BoxObject at ") + _position_1));
          boxes.add(((BoxObject)obj));
        }
      }
    }
    String _agentName_1 = this.getAgentName();
    int _size_1 = boxes.size();
    System.out.println((((("[Agent " + _agentName_1) + "] findBoxes: found ") + Integer.valueOf(_size_1)) + " box(es)"));
    return boxes;
  }

  /**
   * Find boxes that are blocking paths (not already on clearing points).
   */
  protected List<BoxObject> findBlockingBoxes(final List<sokobanObject> perceivedObjects) {
    List<BoxObject> boxes = this.findBoxes(perceivedObjects);
    ArrayList<BoxObject> blockingBoxes = new ArrayList<BoxObject>();
    for (final BoxObject box : boxes) {
      boolean _isOnTarget = box.isOnTarget();
      if ((!_isOnTarget)) {
        blockingBoxes.add(box);
      }
    }
    return blockingBoxes;
  }

  /**
   * Find the nearest unoccupied goal (exit) for a box.
   * This replaces findClearingPoint() - boxes are pushed TO goals, not away from them.
   * Checks perceived objects for goals. If no goal is perceived, returns a heuristic target
   * (center of maze) to encourage exploration toward goals.
   */
  protected Point2i findNearestGoal(final Point2i boxPosition, final List<sokobanObject> perceivedObjects) {
    Point2i nearestGoalPos = null;
    int minDistance = Integer.MAX_VALUE;
    for (final Point2i exitPos : this.allExitPositions) {
      {
        BoxObject goalBox = this.findBoxAt(exitPos, perceivedObjects);
        if ((goalBox == null)) {
          int distance = this.calculateDistance(boxPosition, exitPos);
          if ((distance < minDistance)) {
            minDistance = distance;
            nearestGoalPos = exitPos;
          }
        }
      }
    }
    if ((nearestGoalPos == null)) {
      Point2i estimatedCenter = new Point2i(10, 10);
      String _agentName = this.getAgentName();
      System.out.println(((("[Agent " + _agentName) + "] No goal perceived, using heuristic target (center) for box at ") + boxPosition));
      return estimatedCenter;
    }
    return nearestGoalPos;
  }

  /**
   * Find a box at a specific position.
   */
  @Pure
  protected BoxObject findBoxAt(final Point2i position, final List<sokobanObject> perceivedObjects) {
    throw new Error("Unresolved compilation problems:"
      + "\nType mismatch: cannot convert from sokobanObject to BoxObject");
  }

  /**
   * Evaluate a candidate clearing point (higher score = better).
   */
  protected int evaluateClearingPoint(final Point2i candidate, final Point2i boxPosition, final List<sokobanObject> perceivedObjects) {
    int score = 0;
    sokobanObject obj = this.findObjectAt(perceivedObjects, candidate);
    if ((obj != null)) {
      boolean _isOccluder = obj.isOccluder();
      if (_isOccluder) {
        return (-1000);
      }
    }
    int adjacentWalls = this.countAdjacentWalls(candidate, perceivedObjects);
    score = (score - (adjacentWalls * 10));
    int openNeighbors = this.countOpenNeighbors(candidate, perceivedObjects);
    score = (score + (openNeighbors * 2));
    return score;
  }

  /**
   * Count walls adjacent to a position.
   */
  protected int countAdjacentWalls(final Point2i pos, final List<sokobanObject> perceivedObjects) {
    int count = 0;
    int _x = pos.getX();
    int _y = pos.getY();
    Point2i _point2i = new Point2i((_x + 1), _y);
    int _x_1 = pos.getX();
    int _y_1 = pos.getY();
    Point2i _point2i_1 = new Point2i((_x_1 - 1), _y_1);
    int _x_2 = pos.getX();
    int _y_2 = pos.getY();
    Point2i _point2i_2 = new Point2i(_x_2, (_y_2 + 1));
    int _x_3 = pos.getX();
    int _y_3 = pos.getY();
    Point2i _point2i_3 = new Point2i(_x_3, (_y_3 - 1));
    final List<Point2i> neighbors = Collections.<Point2i>unmodifiableList(CollectionLiterals.<Point2i>newArrayList(_point2i, _point2i_1, _point2i_2, _point2i_3));
    for (final Point2i neighbor : neighbors) {
      {
        sokobanObject obj = this.findObjectAt(perceivedObjects, neighbor);
        if ((obj instanceof WallObject)) {
          count++;
        }
      }
    }
    return count;
  }

  /**
   * Count open (walkable) neighbors.
   */
  protected int countOpenNeighbors(final Point2i pos, final List<sokobanObject> perceivedObjects) {
    int count = 0;
    int _x = pos.getX();
    int _y = pos.getY();
    Point2i _point2i = new Point2i((_x + 1), _y);
    int _x_1 = pos.getX();
    int _y_1 = pos.getY();
    Point2i _point2i_1 = new Point2i((_x_1 - 1), _y_1);
    int _x_2 = pos.getX();
    int _y_2 = pos.getY();
    Point2i _point2i_2 = new Point2i(_x_2, (_y_2 + 1));
    int _x_3 = pos.getX();
    int _y_3 = pos.getY();
    Point2i _point2i_3 = new Point2i(_x_3, (_y_3 - 1));
    final List<Point2i> neighbors = Collections.<Point2i>unmodifiableList(CollectionLiterals.<Point2i>newArrayList(_point2i, _point2i_1, _point2i_2, _point2i_3));
    for (final Point2i neighbor : neighbors) {
      {
        sokobanObject obj = this.findObjectAt(perceivedObjects, neighbor);
        if (((obj == null) || (!obj.isOccluder()))) {
          count++;
        }
      }
    }
    return count;
  }

  /**
   * Handle Priority 1: PUSHING_COMMITMENT - Active box task.
   * 
   * Now includes post-push re-evaluation to fix oscillation/loop issues.
   */
  protected void handlePushingCommitment(final List<sokobanObject> perceivedObjects) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method or field getBoxPosition is undefined for the type Point2i"
      + "\nThe method or field getTargetGoal is undefined for the type Point2i"
      + "\nThe method or field markCompleted is undefined for the type Point2i"
      + "\nThe method or field getBoxId is undefined for the type Point2i"
      + "\nThe method or field taskFailureCount is undefined for the type SokobanAgent"
      + "\nThe method or field taskFailureCount is undefined for the type SokobanAgent"
      + "\nThe method or field MAX_TASK_FAILURES is undefined"
      + "\nThe method or field taskFailureCount is undefined for the type SokobanAgent"
      + "\nThe method or field MAX_TASK_FAILURES is undefined"
      + "\nThe method or field MAX_TASK_FAILURES is undefined"
      + "\nThe method updateBoxPosition(Point2i) is undefined for the type Point2i"
      + "\nThe method taskFailureCount(int) is undefined for the type SokobanAgent"
      + "\nThe method or field getBoxPosition is undefined for the type Point2i"
      + "\nThe method or field markCompleted is undefined for the type Point2i"
      + "\nThe method or field getBoxId is undefined for the type Point2i"
      + "\nThe method or field readyAgents is undefined for the type SokobanAgent"
      + "\nThe method coordinationState(String) is undefined for the type SokobanAgent"
      + "\nThe method or field coordinationState is undefined for the type SokobanAgent"
      + "\nThe method coordinationState(String) is undefined for the type SokobanAgent"
      + "\nThe method or field getBoxId is undefined for the type Point2i"
      + "\nThe method or field getBoxPosition is undefined for the type Point2i"
      + "\nThe method or field getTargetGoal is undefined for the type Point2i"
      + "\nThe method or field getTargetGoal is undefined for the type Point2i"
      + "\n++ cannot be resolved"
      + "\n>= cannot be resolved"
      + "\nclear cannot be resolved"
      + "\n!= cannot be resolved"
      + "\n!== cannot be resolved"
      + "\nx cannot be resolved"
      + "\n- cannot be resolved"
      + "\nx cannot be resolved"
      + "\ny cannot be resolved"
      + "\n- cannot be resolved"
      + "\ny cannot be resolved"
      + "\n> cannot be resolved"
      + "\n!= cannot be resolved"
      + "\n> cannot be resolved"
      + "\nx cannot be resolved"
      + "\n- cannot be resolved"
      + "\ny cannot be resolved"
      + "\n- cannot be resolved");
  }

  /**
   * Re-evaluate situation after a push action.
   * 
   * Critical method that verifies:
   * - Did the box actually move?
   * - Is the box closer to the exit?
   * - Is the path still valid?
   * - Should we continue, abort, or change strategy?
   * 
   * @param perceivedObjects list of perceived objects
   * @param oldBoxPosition the box position before the push
   */
  protected void reEvaluateAfterPush(final List<sokobanObject> perceivedObjects, final Point2i oldBoxPosition) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method or field getTargetGoal is undefined for the type Point2i"
      + "\nThe method updateBoxPosition(Point2i) is undefined for the type Point2i"
      + "\nThe method taskFailureCount(int) is undefined for the type SokobanAgent"
      + "\nThe method updateBoxPosition(Point2i) is undefined for the type Point2i"
      + "\nThe method updateBoxPosition(Point2i) is undefined for the type Point2i"
      + "\nThe method or field markCompleted is undefined for the type Point2i"
      + "\nThe method or field getBoxId is undefined for the type Point2i"
      + "\nThe method or field markCompleted is undefined for the type Point2i"
      + "\nThe method or field getBoxId is undefined for the type Point2i"
      + "\nThe method or field taskFailureCount is undefined for the type SokobanAgent"
      + "\nThe method or field taskFailureCount is undefined for the type SokobanAgent"
      + "\nThe method or field MAX_TASK_FAILURES is undefined"
      + "\n++ cannot be resolved"
      + "\n>= cannot be resolved");
  }

  /**
   * Calculate the position agent should be in for pushing.
   * 
   * Always uses CURRENT box position from task (never stale positions).
   * Recalculates push position based on current box position and target goal.
   * 
   * @param perceivedObjects list of perceived objects (for validation)
   */
  protected void calculatePushPosition(final List<sokobanObject> perceivedObjects) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method or field getBoxPosition is undefined for the type Point2i"
      + "\nThe method or field getTargetGoal is undefined for the type Point2i"
      + "\nThe method updateBoxPosition(Point2i) is undefined for the type Point2i"
      + "\n=== cannot be resolved"
      + "\n|| cannot be resolved"
      + "\n=== cannot be resolved"
      + "\nx cannot be resolved"
      + "\n- cannot be resolved"
      + "\nx cannot be resolved"
      + "\ny cannot be resolved"
      + "\n- cannot be resolved"
      + "\ny cannot be resolved"
      + "\n> cannot be resolved"
      + "\n!= cannot be resolved"
      + "\n> cannot be resolved"
      + "\nx cannot be resolved"
      + "\n- cannot be resolved"
      + "\ny cannot be resolved"
      + "\n- cannot be resolved");
  }

  /**
   * Recalculate push position based on current box position and target goal.
   * 
   * This is a wrapper that ensures we always use the most current box position.
   * 
   * @param currentBoxPosition the current box position (must be up-to-date)
   * @param targetGoal the target goal position
   */
  protected void recalculatePushPosition(final Point2i currentBoxPosition, final Point2i targetGoal) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method updateBoxPosition(Point2i) is undefined for the type Point2i");
  }

  /**
   * Handle Priority 3: WAITING_FOR_PUSH_SYNC - In position, waiting for others.
   */
  protected void handleWaitingForPushSync(final List<sokobanObject> perceivedObjects) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method or field getBoxId is undefined for the type Point2i"
      + "\nThe method or field getAssignedAgents is undefined for the type Point2i"
      + "\nThe method or field readyAgents is undefined for the type SokobanAgent"
      + "\nThe method or field readyAgents is undefined for the type SokobanAgent"
      + "\nsize cannot be resolved"
      + "\nsize cannot be resolved"
      + "\n>= cannot be resolved"
      + "\n- cannot be resolved"
      + "\n- cannot be resolved"
      + "\nsize cannot be resolved"
      + "\n- cannot be resolved");
  }

  /**
   * Execute synchronized push with all agents.
   */
  protected void executeSynchronizedPush(final List<sokobanObject> perceivedObjects) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method or field getTargetGoal is undefined for the type Point2i"
      + "\nThe method or field getBoxPosition is undefined for the type Point2i"
      + "\nThe method or field getBoxPosition is undefined for the type Point2i"
      + "\nThe method or field markCompleted is undefined for the type Point2i"
      + "\nThe method or field getBoxId is undefined for the type Point2i"
      + "\nThe method or field markCompleted is undefined for the type Point2i"
      + "\nThe method or field getBoxId is undefined for the type Point2i"
      + "\nThe method or field isCompleted is undefined for the type Point2i"
      + "\nThe method or field readyAgents is undefined for the type SokobanAgent"
      + "\nThe method coordinationState(String) is undefined for the type SokobanAgent"
      + "\nx cannot be resolved"
      + "\n- cannot be resolved"
      + "\ny cannot be resolved"
      + "\n- cannot be resolved"
      + "\n> cannot be resolved"
      + "\n!= cannot be resolved"
      + "\n> cannot be resolved"
      + "\nclear cannot be resolved");
  }

  /**
   * Handle Priority 4: BOX_TASK_SELECTION - Propose box-clearing task.
   */
  protected void handleBoxTaskSelection(final List<BoxObject> blockingBoxes, final List<sokobanObject> perceivedObjects) {
    throw new Error("Unresolved compilation problems:"
      + "\nType mismatch: cannot convert from BoxTask to Point2i");
  }

  /**
   * Assign a box to push based on proximity and coordination.
   */
  protected void assignBoxToPush(final List<BoxObject> boxes, final List<sokobanObject> perceivedObjects) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method assignedBox(Point2i) is undefined for the type SokobanAgent"
      + "\nThe method coordinationState(String) is undefined for the type SokobanAgent"
      + "\nThe method or field assignedBox is undefined for the type SokobanAgent");
  }

  /**
   * Handle assigned box pushing.
   */
  protected void handleAssignedBox(final List<sokobanObject> perceivedObjects) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method or field assignedBox is undefined for the type SokobanAgent"
      + "\nThe method coordinationState(String) is undefined for the type SokobanAgent"
      + "\nThe method or field assignedBox is undefined for the type SokobanAgent"
      + "\nThe method assignedBox(Object) is undefined for the type SokobanAgent"
      + "\nThe method coordinationState(String) is undefined for the type SokobanAgent"
      + "\nThe method or field assignedBox is undefined for the type SokobanAgent"
      + "\nThe method or field assignedBox is undefined for the type SokobanAgent"
      + "\nThe method or field assignedBox is undefined for the type SokobanAgent"
      + "\nType mismatch: cannot convert from sokobanObject to BoxObject"
      + "\n=== cannot be resolved");
  }

  /**
   * Handle helping mode - go to help another agent.
   */
  protected void handleHelpingMode(final List<sokobanObject> perceivedObjects) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method or field helpingAgent is undefined for the type SokobanAgent"
      + "\nThe method coordinationState(String) is undefined for the type SokobanAgent"
      + "\nThe method coordinationState(String) is undefined for the type SokobanAgent"
      + "\nThe method helpingAgent(Object) is undefined for the type SokobanAgent"
      + "\n=== cannot be resolved");
  }

  /**
   * Handle waiting for help.
   */
  protected Object handleWaitingForHelp(final List<sokobanObject> perceivedObjects) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method or field helpOffers is undefined for the type SokobanAgent"
      + "\nThe method waitingForHelp(boolean) is undefined for the type SokobanAgent"
      + "\nThe method coordinationState(String) is undefined for the type SokobanAgent"
      + "\nThe method or field helpOffers is undefined for the type SokobanAgent"
      + "\nThe method or field helpOffers is undefined for the type SokobanAgent"
      + "\nThe method or field assignedBox is undefined for the type SokobanAgent"
      + "\nThe method or field assignedBox is undefined for the type SokobanAgent"
      + "\nisEmpty cannot be resolved"
      + "\n! cannot be resolved"
      + "\nsize cannot be resolved"
      + "\nclear cannot be resolved"
      + "\n!== cannot be resolved");
  }

  /**
   * Explore randomly when no target.
   */
  protected void exploreRandomly() {
    final List<Direction> directions = Collections.<Direction>unmodifiableList(CollectionLiterals.<Direction>newArrayList(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST));
    Direction dir = directions.get(this.random.nextInt(directions.size()));
    String _agentName = this.getAgentName();
    System.out.println(((("[Agent " + _agentName) + "] Exploring randomly in direction: ") + dir));
    this.emitAction(dir);
  }

  /**
   * Handle Phase 1: Exploration phase - record discoveries and emit events, never push boxes.
   */
  protected void handleExplorationPhase(final List<sokobanObject> perceivedObjects) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method or field discoveredBoxes is undefined for the type SokobanAgent"
      + "\nThe method or field discoveredBoxes is undefined for the type SokobanAgent"
      + "\ncontains cannot be resolved"
      + "\n! cannot be resolved"
      + "\nadd cannot be resolved");
  }

  /**
   * Check and perform phase transitions based on knowledge availability.
   */
  protected void checkPhaseTransition() {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method or field systemPhase is undefined for the type SokobanAgent"
      + "\nThe method or field globalBoxKnowledge is undefined for the type SokobanAgent"
      + "\nThe method or field globalExitKnowledge is undefined for the type SokobanAgent"
      + "\nThe method systemPhase(String) is undefined for the type SokobanAgent"
      + "\nThe method or field globalBoxKnowledge is undefined for the type SokobanAgent"
      + "\nThe method or field globalExitKnowledge is undefined for the type SokobanAgent"
      + "\n== cannot be resolved"
      + "\nisEmpty cannot be resolved"
      + "\n! cannot be resolved"
      + "\n&& cannot be resolved"
      + "\nisEmpty cannot be resolved"
      + "\n! cannot be resolved"
      + "\nsize cannot be resolved"
      + "\nsize cannot be resolved");
  }

  /**
   * Calculate distance between two points.
   */
  @Pure
  protected int calculateDistance(final Point2i p1, final Point2i p2) {
    if (((p1 == null) || (p2 == null))) {
      return Integer.MAX_VALUE;
    }
    int _x = p1.getX();
    int _x_1 = p2.getX();
    int dx = (_x - _x_1);
    int _y = p1.getY();
    int _y_1 = p2.getY();
    int dy = (_y - _y_1);
    int _abs = Math.abs(dx);
    int _abs_1 = Math.abs(dy);
    return (_abs + _abs_1);
  }

  private void $behaviorUnit$RequestHelp$13(final RequestHelp occurrence) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method or field helpingAgent is undefined for the type SokobanAgent"
      + "\nThe method or field assignedBox is undefined for the type SokobanAgent"
      + "\nThe method or field assignedBox is undefined for the type SokobanAgent"
      + "\nThe method helpingAgent(UUID) is undefined for the type SokobanAgent"
      + "\nThe method coordinationState(String) is undefined for the type SokobanAgent"
      + "\nThe method or field coordinationState is undefined for the type SokobanAgent"
      + "\n=== cannot be resolved"
      + "\n=== cannot be resolved"
      + "\n|| cannot be resolved");
  }

  @Pure
  private boolean $behaviorUnitGuard$RequestHelp$13(final RequestHelp it, final RequestHelp occurrence) {
    boolean _equals = occurrence.requesterId.equals(this.getID());
    return (!_equals);
  }

  private void $behaviorUnit$OfferHelp$14(final OfferHelp occurrence) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method or field waitingForHelp is undefined for the type SokobanAgent"
      + "\nThe method or field assignedBox is undefined for the type SokobanAgent"
      + "\nThe method or field helpOffers is undefined for the type SokobanAgent"
      + "\nThe method or field assignedBox is undefined for the type SokobanAgent"
      + "\nThe method or field assignedBox is undefined for the type SokobanAgent"
      + "\n&& cannot be resolved"
      + "\nadd cannot be resolved"
      + "\n!== cannot be resolved");
  }

  private void $behaviorUnit$CoordinatePush$15(final CoordinatePush occurrence) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method or field helpingAgent is undefined for the type SokobanAgent"
      + "\nThe method or field helpingAgent is undefined for the type SokobanAgent"
      + "\n!== cannot be resolved"
      + "\n&& cannot be resolved");
  }

  @Pure
  private boolean $behaviorUnitGuard$CoordinatePush$15(final CoordinatePush it, final CoordinatePush occurrence) {
    boolean _equals = occurrence.coordinatorId.equals(this.getID());
    return (!_equals);
  }

  private void $behaviorUnit$ProposeBoxTask$16(final ProposeBoxTask occurrence) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe field leaderId is not visible"
      + "\nType mismatch: cannot convert from BoxTask to Point2i");
  }

  @Pure
  private boolean $behaviorUnitGuard$ProposeBoxTask$16(final ProposeBoxTask it, final ProposeBoxTask occurrence) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe field leaderId is not visible");
  }

  private void $behaviorUnit$AcceptBoxTask$17(final AcceptBoxTask occurrence) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method or field getBoxId is undefined for the type Point2i"
      + "\nThe method addHelper(UUID) is undefined for the type Point2i");
  }

  private void $behaviorUnit$ReadyForPush$18(final ReadyForPush occurrence) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method or field getBoxId is undefined for the type Point2i"
      + "\nThe method or field readyAgents is undefined for the type SokobanAgent"
      + "\nThe method or field readyAgents is undefined for the type SokobanAgent"
      + "\ncontains cannot be resolved"
      + "\n! cannot be resolved"
      + "\nadd cannot be resolved");
  }

  private void $behaviorUnit$BoxTaskCompleted$19(final BoxTaskCompleted occurrence) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method or field getBoxId is undefined for the type Point2i"
      + "\nThe method or field readyAgents is undefined for the type SokobanAgent"
      + "\nThe method coordinationState(String) is undefined for the type SokobanAgent"
      + "\nclear cannot be resolved");
  }

  private void $behaviorUnit$AgentPositionBroadcast$20(final AgentPositionBroadcast occurrence) {
    this.otherAgentPositions.put(occurrence.agentId, occurrence.position);
    this.otherAgentPhases.put(occurrence.agentId, occurrence.phase);
    if ((occurrence.targetBox != null)) {
      this.otherAgentTargets.put(occurrence.agentId, occurrence.targetBox);
    } else {
      this.otherAgentTargets.remove(occurrence.agentId);
    }
  }

  @Pure
  private boolean $behaviorUnitGuard$AgentPositionBroadcast$20(final AgentPositionBroadcast it, final AgentPositionBroadcast occurrence) {
    boolean _equals = occurrence.agentId.equals(this.getID());
    return (!_equals);
  }

  private void $behaviorUnit$BoxPositionUpdate$21(final BoxPositionUpdate occurrence) {
    boolean _contains = this.allBoxPositions.contains(occurrence.oldPosition);
    if (_contains) {
      this.allBoxPositions.remove(occurrence.oldPosition);
      this.allBoxPositions.add(occurrence.newPosition);
      String _agentName = this.getAgentName();
      System.out.println(((((("[Agent " + _agentName) + "] Updated global knowledge: box moved from ") + occurrence.oldPosition) + " to ") + occurrence.newPosition));
    }
    if ((((this.myClaimedBox != null) && (this.myClaimedBox.getX() == occurrence.oldPosition.getX())) && (this.myClaimedBox.getY() == occurrence.oldPosition.getY()))) {
      this.myClaimedBox = occurrence.newPosition;
      String _agentName_1 = this.getAgentName();
      System.out.println(((("[Agent " + _agentName_1) + "] Updated claimed box position to ") + occurrence.newPosition));
      this.claimedBoxes.remove(occurrence.oldPosition);
      this.claimedBoxes.add(occurrence.newPosition);
      String _agentName_2 = this.getAgentName();
      System.out.println(((((("[Agent " + _agentName_2) + "] Updated claimedBoxes: removed ") + occurrence.oldPosition) + ", added ") + occurrence.newPosition));
    }
    boolean _contains_1 = this.claimedBoxes.contains(occurrence.oldPosition);
    if (_contains_1) {
      this.claimedBoxes.remove(occurrence.oldPosition);
      boolean _contains_2 = this.claimedBoxes.contains(occurrence.newPosition);
      if ((!_contains_2)) {
        this.claimedBoxes.add(occurrence.newPosition);
      }
      String _agentName_3 = this.getAgentName();
      System.out.println(((((("[Agent " + _agentName_3) + "] Updated claimedBoxes for moved box: ") + occurrence.oldPosition) + " -> ") + occurrence.newPosition));
    }
  }

  private void $behaviorUnit$BoxDiscovered$22(final BoxDiscovered occurrence) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method or field globalBoxKnowledge is undefined for the type SokobanAgent"
      + "\nThe method or field globalBoxKnowledge is undefined for the type SokobanAgent"
      + "\nadd cannot be resolved"
      + "\nsize cannot be resolved");
  }

  @Pure
  private boolean $behaviorUnitGuard$BoxDiscovered$22(final BoxDiscovered it, final BoxDiscovered occurrence) {
    boolean _equals = occurrence.discovererId.equals(this.getID());
    return (!_equals);
  }

  private void $behaviorUnit$ExitDiscovered$23(final ExitDiscovered occurrence) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method or field globalExitKnowledge is undefined for the type SokobanAgent"
      + "\nThe method or field globalExitKnowledge is undefined for the type SokobanAgent"
      + "\nThe method or field globalExitKnowledge is undefined for the type SokobanAgent"
      + "\ncontains cannot be resolved"
      + "\n! cannot be resolved"
      + "\nadd cannot be resolved"
      + "\nsize cannot be resolved");
  }

  @Pure
  private boolean $behaviorUnitGuard$ExitDiscovered$23(final ExitDiscovered it, final ExitDiscovered occurrence) {
    boolean _equals = occurrence.discovererId.equals(this.getID());
    return (!_equals);
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
  @ImportedCapacityFeature(MazeMotion.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMOTION;

  @SyntheticMember
  @Pure
  private MazeMotion $CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMOTION$CALLER() {
    if (this.$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMOTION == null || this.$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMOTION.get() == null) {
      this.$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMOTION = $getSkill(MazeMotion.class);
    }
    return $castSkill(MazeMotion.class, this.$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMOTION);
  }

  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$Initialize(final Initialize occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Initialize$0(occurrence));
  }

  /**
   * Handle task acceptance from helper agents.
   */
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$AcceptBoxTask(final AcceptBoxTask occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$AcceptBoxTask$17(occurrence));
  }

  /**
   * Handle agent position broadcasts for blocking detection.
   */
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$AgentPositionBroadcast(final AgentPositionBroadcast occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    if ($behaviorUnitGuard$AgentPositionBroadcast$20(occurrence, occurrence)) {
      ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$AgentPositionBroadcast$20(occurrence));
    }
  }

  /**
   * Handle box assignment events - update claimed boxes when other agents get assigned.
   */
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$BoxAssignment(final BoxAssignment occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$BoxAssignment$7(occurrence));
  }

  /**
   * Observe when other agents claim boxes (decentralized coordination).
   */
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$BoxClaimed(final BoxClaimed occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    if ($behaviorUnitGuard$BoxClaimed$2(occurrence, occurrence)) {
      ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$BoxClaimed$2(occurrence));
    }
  }

  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$BoxCompleted(final BoxCompleted occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$BoxCompleted$8(occurrence));
  }

  /**
   * Handle box discovery events from other agents.
   */
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$BoxDiscovered(final BoxDiscovered occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    if ($behaviorUnitGuard$BoxDiscovered$22(occurrence, occurrence)) {
      ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$BoxDiscovered$22(occurrence));
    }
  }

  /**
   * Handle box position updates from other agents.
   */
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$BoxPositionUpdate(final BoxPositionUpdate occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$BoxPositionUpdate$21(occurrence));
  }

  /**
   * Handle task completion.
   */
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$BoxTaskCompleted(final BoxTaskCompleted occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$BoxTaskCompleted$19(occurrence));
  }

  /**
   * Handle team formation events - join a team working on a box.
   */
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$BoxTaskFormation(final BoxTaskFormation occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    if ($behaviorUnitGuard$BoxTaskFormation$5(occurrence, occurrence)) {
      ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$BoxTaskFormation$5(occurrence));
    }
  }

  /**
   * Handle collaborative box task announcements.
   * 
   * Agents update their state immediately when they receive this event - no waiting.
   */
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$CollaborativeBoxTask(final CollaborativeBoxTask occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    if ($behaviorUnitGuard$CollaborativeBoxTask$9(occurrence, occurrence)) {
      ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$CollaborativeBoxTask$9(occurrence));
    }
    if ($behaviorUnitGuard$CollaborativeBoxTask$10(occurrence, occurrence)) {
      ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$CollaborativeBoxTask$10(occurrence));
    }
  }

  /**
   * Handle coordinated push requests.
   */
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$CoordinatePush(final CoordinatePush occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    if ($behaviorUnitGuard$CoordinatePush$15(occurrence, occurrence)) {
      ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$CoordinatePush$15(occurrence));
    }
  }

  /**
   * Handle exit discovery events from other agents.
   */
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$ExitDiscovered(final ExitDiscovered occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    if ($behaviorUnitGuard$ExitDiscovered$23(occurrence, occurrence)) {
      ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$ExitDiscovered$23(occurrence));
    }
  }

  /**
   * Receive initial knowledge of all boxes and exits from Environment.
   */
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$InitialKnowledge(final InitialKnowledge occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$InitialKnowledge$1(occurrence));
  }

  /**
   * Handle join requests from team members.
   */
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$JoinBoxTask(final JoinBoxTask occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    if ($behaviorUnitGuard$JoinBoxTask$6(occurrence, occurrence)) {
      ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$JoinBoxTask$6(occurrence));
    }
  }

  /**
   * Handle help offers from other agents.
   */
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$OfferHelp(final OfferHelp occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$OfferHelp$14(occurrence));
  }

  /**
   * Handle path proposals from other agents during coordination.
   */
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$PathProposal(final PathProposal occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    if ($behaviorUnitGuard$PathProposal$3(occurrence, occurrence)) {
      ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$PathProposal$3(occurrence));
    }
  }

  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$Perception(final Perception occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Perception$12(occurrence));
  }

  /**
   * Handle proposal responses from other agents during negotiation.
   */
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$ProposalResponse(final ProposalResponse occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$ProposalResponse$4(occurrence));
  }

  /**
   * Handle box task proposals from other agents.
   */
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$ProposeBoxTask(final ProposeBoxTask occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    if ($behaviorUnitGuard$ProposeBoxTask$16(occurrence, occurrence)) {
      ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$ProposeBoxTask$16(occurrence));
    }
  }

  /**
   * Handle readiness signals from other agents.
   */
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$ReadyForPush(final ReadyForPush occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$ReadyForPush$18(occurrence));
  }

  /**
   * Handle helper ready signal (informational only - no waiting).
   */
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$ReadyToHelp(final ReadyToHelp occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    if ($behaviorUnitGuard$ReadyToHelp$11(occurrence, occurrence)) {
      ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$ReadyToHelp$11(occurrence));
    }
  }

  /**
   * Handle help requests from other agents.
   */
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$RequestHelp(final RequestHelp occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    if ($behaviorUnitGuard$RequestHelp$13(occurrence, occurrence)) {
      ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$RequestHelp$13(occurrence));
    }
  }

  @SyntheticMember
  @Override
  public void $getSupportedEvents(final Set<Class<? extends Event>> toBeFilled) {
    super.$getSupportedEvents(toBeFilled);
    toBeFilled.add(Initialize.class);
    toBeFilled.add(AcceptBoxTask.class);
    toBeFilled.add(AgentPositionBroadcast.class);
    toBeFilled.add(BoxAssignment.class);
    toBeFilled.add(BoxClaimed.class);
    toBeFilled.add(BoxCompleted.class);
    toBeFilled.add(BoxDiscovered.class);
    toBeFilled.add(BoxPositionUpdate.class);
    toBeFilled.add(BoxTaskCompleted.class);
    toBeFilled.add(BoxTaskFormation.class);
    toBeFilled.add(CollaborativeBoxTask.class);
    toBeFilled.add(CoordinatePush.class);
    toBeFilled.add(ExitDiscovered.class);
    toBeFilled.add(InitialKnowledge.class);
    toBeFilled.add(JoinBoxTask.class);
    toBeFilled.add(OfferHelp.class);
    toBeFilled.add(PathProposal.class);
    toBeFilled.add(Perception.class);
    toBeFilled.add(ProposalResponse.class);
    toBeFilled.add(ProposeBoxTask.class);
    toBeFilled.add(ReadyForPush.class);
    toBeFilled.add(ReadyToHelp.class);
    toBeFilled.add(RequestHelp.class);
  }

  @SyntheticMember
  @Override
  public boolean $isSupportedEvent(final Class<? extends Event> event) {
    if (Initialize.class.isAssignableFrom(event)) {
      return true;
    }
    if (AcceptBoxTask.class.isAssignableFrom(event)) {
      return true;
    }
    if (AgentPositionBroadcast.class.isAssignableFrom(event)) {
      return true;
    }
    if (BoxAssignment.class.isAssignableFrom(event)) {
      return true;
    }
    if (BoxClaimed.class.isAssignableFrom(event)) {
      return true;
    }
    if (BoxCompleted.class.isAssignableFrom(event)) {
      return true;
    }
    if (BoxDiscovered.class.isAssignableFrom(event)) {
      return true;
    }
    if (BoxPositionUpdate.class.isAssignableFrom(event)) {
      return true;
    }
    if (BoxTaskCompleted.class.isAssignableFrom(event)) {
      return true;
    }
    if (BoxTaskFormation.class.isAssignableFrom(event)) {
      return true;
    }
    if (CollaborativeBoxTask.class.isAssignableFrom(event)) {
      return true;
    }
    if (CoordinatePush.class.isAssignableFrom(event)) {
      return true;
    }
    if (ExitDiscovered.class.isAssignableFrom(event)) {
      return true;
    }
    if (InitialKnowledge.class.isAssignableFrom(event)) {
      return true;
    }
    if (JoinBoxTask.class.isAssignableFrom(event)) {
      return true;
    }
    if (OfferHelp.class.isAssignableFrom(event)) {
      return true;
    }
    if (PathProposal.class.isAssignableFrom(event)) {
      return true;
    }
    if (Perception.class.isAssignableFrom(event)) {
      return true;
    }
    if (ProposalResponse.class.isAssignableFrom(event)) {
      return true;
    }
    if (ProposeBoxTask.class.isAssignableFrom(event)) {
      return true;
    }
    if (ReadyForPush.class.isAssignableFrom(event)) {
      return true;
    }
    if (ReadyToHelp.class.isAssignableFrom(event)) {
      return true;
    }
    if (RequestHelp.class.isAssignableFrom(event)) {
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
    if (Initialize.class.equals(eventType)) {
      final var occurrence = (Initialize) event;
      $guardEvaluator$Initialize(occurrence, callbacks);
    }
    if (AcceptBoxTask.class.equals(eventType)) {
      final var occurrence = (AcceptBoxTask) event;
      $guardEvaluator$AcceptBoxTask(occurrence, callbacks);
    }
    if (AgentPositionBroadcast.class.equals(eventType)) {
      final var occurrence = (AgentPositionBroadcast) event;
      $guardEvaluator$AgentPositionBroadcast(occurrence, callbacks);
    }
    if (BoxAssignment.class.equals(eventType)) {
      final var occurrence = (BoxAssignment) event;
      $guardEvaluator$BoxAssignment(occurrence, callbacks);
    }
    if (BoxClaimed.class.equals(eventType)) {
      final var occurrence = (BoxClaimed) event;
      $guardEvaluator$BoxClaimed(occurrence, callbacks);
    }
    if (BoxCompleted.class.equals(eventType)) {
      final var occurrence = (BoxCompleted) event;
      $guardEvaluator$BoxCompleted(occurrence, callbacks);
    }
    if (BoxDiscovered.class.equals(eventType)) {
      final var occurrence = (BoxDiscovered) event;
      $guardEvaluator$BoxDiscovered(occurrence, callbacks);
    }
    if (BoxPositionUpdate.class.equals(eventType)) {
      final var occurrence = (BoxPositionUpdate) event;
      $guardEvaluator$BoxPositionUpdate(occurrence, callbacks);
    }
    if (BoxTaskCompleted.class.equals(eventType)) {
      final var occurrence = (BoxTaskCompleted) event;
      $guardEvaluator$BoxTaskCompleted(occurrence, callbacks);
    }
    if (BoxTaskFormation.class.equals(eventType)) {
      final var occurrence = (BoxTaskFormation) event;
      $guardEvaluator$BoxTaskFormation(occurrence, callbacks);
    }
    if (CollaborativeBoxTask.class.equals(eventType)) {
      final var occurrence = (CollaborativeBoxTask) event;
      $guardEvaluator$CollaborativeBoxTask(occurrence, callbacks);
    }
    if (CoordinatePush.class.equals(eventType)) {
      final var occurrence = (CoordinatePush) event;
      $guardEvaluator$CoordinatePush(occurrence, callbacks);
    }
    if (ExitDiscovered.class.equals(eventType)) {
      final var occurrence = (ExitDiscovered) event;
      $guardEvaluator$ExitDiscovered(occurrence, callbacks);
    }
    if (InitialKnowledge.class.equals(eventType)) {
      final var occurrence = (InitialKnowledge) event;
      $guardEvaluator$InitialKnowledge(occurrence, callbacks);
    }
    if (JoinBoxTask.class.equals(eventType)) {
      final var occurrence = (JoinBoxTask) event;
      $guardEvaluator$JoinBoxTask(occurrence, callbacks);
    }
    if (OfferHelp.class.equals(eventType)) {
      final var occurrence = (OfferHelp) event;
      $guardEvaluator$OfferHelp(occurrence, callbacks);
    }
    if (PathProposal.class.equals(eventType)) {
      final var occurrence = (PathProposal) event;
      $guardEvaluator$PathProposal(occurrence, callbacks);
    }
    if (Perception.class.equals(eventType)) {
      final var occurrence = (Perception) event;
      $guardEvaluator$Perception(occurrence, callbacks);
    }
    if (ProposalResponse.class.equals(eventType)) {
      final var occurrence = (ProposalResponse) event;
      $guardEvaluator$ProposalResponse(occurrence, callbacks);
    }
    if (ProposeBoxTask.class.equals(eventType)) {
      final var occurrence = (ProposeBoxTask) event;
      $guardEvaluator$ProposeBoxTask(occurrence, callbacks);
    }
    if (ReadyForPush.class.equals(eventType)) {
      final var occurrence = (ReadyForPush) event;
      $guardEvaluator$ReadyForPush(occurrence, callbacks);
    }
    if (ReadyToHelp.class.equals(eventType)) {
      final var occurrence = (ReadyToHelp) event;
      $guardEvaluator$ReadyToHelp(occurrence, callbacks);
    }
    if (RequestHelp.class.equals(eventType)) {
      final var occurrence = (RequestHelp) event;
      $guardEvaluator$RequestHelp(occurrence, callbacks);
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
    SokobanAgent other = (SokobanAgent) obj;
    if (other.currentTime != this.currentTime)
      return false;
    if (!Objects.equals(this.agentBehaviorPhase, other.agentBehaviorPhase))
      return false;
    if (other.negotiationTimeout != this.negotiationTimeout)
      return false;
    if (other.maxNegotiationSteps != this.maxNegotiationSteps)
      return false;
    if (other.coordinationActive != this.coordinationActive)
      return false;
    if (other.proposalsEmitted != this.proposalsEmitted)
      return false;
    if (other.teamFormationTimeout != this.teamFormationTimeout)
      return false;
    if (other.maxTeamFormationSteps != this.maxTeamFormationSteps)
      return false;
    if (!Objects.equals(this.myTaskRole, other.myTaskRole))
      return false;
    if (other.currentPathStep != this.currentPathStep)
      return false;
    if (other.pathPlanned != this.pathPlanned)
      return false;
    if (other.actionEmittedThisStep != this.actionEmittedThisStep)
      return false;
    if (other.lastPerceptionTime != this.lastPerceptionTime)
      return false;
    if (other.positionBroadcastCounter != this.positionBroadcastCounter)
      return false;
    if (other.boxProgressCount != this.boxProgressCount)
      return false;
    if (other.taskCompletionSteps != this.taskCompletionSteps)
      return false;
    if (other.alignmentSteps != this.alignmentSteps)
      return false;
    if (other.pushStuckCounter != this.pushStuckCounter)
      return false;
    if (other.maxStuckSteps != this.maxStuckSteps)
      return false;
    if (other.maxAlignmentSteps != this.maxAlignmentSteps)
      return false;
    if (other.pushFailureCount != this.pushFailureCount)
      return false;
    if (other.maxPushFailures != this.maxPushFailures)
      return false;
    if (other.releaseCooldownSteps != this.releaseCooldownSteps)
      return false;
    if (!Objects.equals(this.agentName, other.agentName))
      return false;
    return super.equals(obj);
  }

  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Integer.hashCode(this.currentTime);
    result = prime * result + Objects.hashCode(this.agentBehaviorPhase);
    result = prime * result + Integer.hashCode(this.negotiationTimeout);
    result = prime * result + Integer.hashCode(this.maxNegotiationSteps);
    result = prime * result + Boolean.hashCode(this.coordinationActive);
    result = prime * result + Boolean.hashCode(this.proposalsEmitted);
    result = prime * result + Integer.hashCode(this.teamFormationTimeout);
    result = prime * result + Integer.hashCode(this.maxTeamFormationSteps);
    result = prime * result + Objects.hashCode(this.myTaskRole);
    result = prime * result + Integer.hashCode(this.currentPathStep);
    result = prime * result + Boolean.hashCode(this.pathPlanned);
    result = prime * result + Boolean.hashCode(this.actionEmittedThisStep);
    result = prime * result + Integer.hashCode(this.lastPerceptionTime);
    result = prime * result + Integer.hashCode(this.positionBroadcastCounter);
    result = prime * result + Integer.hashCode(this.boxProgressCount);
    result = prime * result + Integer.hashCode(this.taskCompletionSteps);
    result = prime * result + Integer.hashCode(this.alignmentSteps);
    result = prime * result + Integer.hashCode(this.pushStuckCounter);
    result = prime * result + Integer.hashCode(this.maxStuckSteps);
    result = prime * result + Integer.hashCode(this.maxAlignmentSteps);
    result = prime * result + Integer.hashCode(this.pushFailureCount);
    result = prime * result + Integer.hashCode(this.maxPushFailures);
    result = prime * result + Integer.hashCode(this.releaseCooldownSteps);
    result = prime * result + Objects.hashCode(this.agentName);
    return result;
  }

  @SyntheticMember
  public SokobanAgent(final UUID parentID, final UUID agentID) {
    super(parentID, agentID);
  }

  @SyntheticMember
  @Inject
  public SokobanAgent(final UUID parentID, final UUID agentID, final DynamicSkillProvider skillProvider) {
    super(parentID, agentID, skillProvider);
  }
}
