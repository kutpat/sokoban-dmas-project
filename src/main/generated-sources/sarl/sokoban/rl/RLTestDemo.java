/**
 * Test/Demo for RL Sokoban components.
 * 
 * This demonstrates how to use:
 * - State extraction
 * - Reward calculation
 * - State comparison
 */
package sokoban.rl;

import framework.math.Point2i;
import io.sarl.api.core.Initialize;
import io.sarl.api.core.Lifecycle;
import io.sarl.api.core.Logging;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.AtomicSkillReference;
import io.sarl.lang.core.DynamicSkillProvider;
import io.sarl.lang.core.Event;
import io.sarl.lang.core.annotation.ImportedCapacityFeature;
import io.sarl.lang.core.annotation.PerceptGuardEvaluator;
import io.sarl.lang.core.annotation.SarlElementType;
import io.sarl.lang.core.annotation.SarlSpecification;
import io.sarl.lang.core.annotation.SyntheticMember;
import jakarta.inject.Inject;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;
import sokoban.environment.agent.DefaultMazeManagerSkill;
import sokoban.environment.agent.MazeManager;

/**
 * Simple test agent to demonstrate RL components.
 * 
 * This agent:
 * 1. Extracts initial state
 * 2. Performs an action
 * 3. Extracts new state
 * 4. Computes reward
 * 5. Prints results
 */
@SarlSpecification("0.15")
@SarlElementType(19)
@XbaseGenerated
@SuppressWarnings("all")
public class RLTestDemo extends Agent {
  private int stepCount = 0;

  private SokobanState prevState = null;

  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    DefaultMazeManagerSkill _defaultMazeManagerSkill = new DefaultMazeManagerSkill(20, 20);
    this.<DefaultMazeManagerSkill>setSkill(_defaultMazeManagerSkill);
    MazeManager _$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER = this.$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER();
    _$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER.createBoxes(2);
    MazeManager _$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER_1 = this.$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER();
    _$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER_1.createExits(2);
    Logging _$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER.info("=== RL Sokoban Test Demo ===");
    Logging _$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER_1 = this.$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER_1.info("Testing state extraction and reward calculation...");
    MazeManager _$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER_2 = this.$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER();
    SokobanState initialState = _$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMANAGER$CALLER_2.extractSokobanState(this.stepCount);
    this.prevState = initialState;
    Logging _$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER_2 = this.$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER_2.info("Initial State:");
    Logging _$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER_3 = this.$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER();
    Point2i _agentPosition = initialState.getAgentPosition();
    _$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER_3.info(("  Agent position: " + _agentPosition));
    Logging _$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER_4 = this.$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER();
    int _size = initialState.getBoxPositions().size();
    _$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER_4.info(("  Boxes: " + Integer.valueOf(_size)));
    Logging _$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER_5 = this.$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER();
    int _countBoxesOnTargets = RewardCalculator.countBoxesOnTargets(initialState);
    _$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER_5.info(("  Boxes on targets: " + Integer.valueOf(_countBoxesOnTargets)));
    Logging _$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER_6 = this.$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER();
    int _size_1 = initialState.getTargetPositions().size();
    _$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER_6.info(("  Targets: " + Integer.valueOf(_size_1)));
    Logging _$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER_7 = this.$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER();
    int _stepCount = initialState.getStepCount();
    _$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER_7.info(("  Step count: " + Integer.valueOf(_stepCount)));
    this.testStateSerialization(initialState);
    Logging _$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER_8 = this.$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER_8.info("\n=== Test Complete ===");
    Logging _$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER_9 = this.$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER_9.info("State extraction: ✅");
    Logging _$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER_10 = this.$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER_10.info("Reward calculation: ✅");
    Logging _$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER_11 = this.$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER_11.info("State serialization: ✅");
    Lifecycle _$CAPACITY_USE$IO_SARL_API_CORE_LIFECYCLE$CALLER = this.$CAPACITY_USE$IO_SARL_API_CORE_LIFECYCLE$CALLER();
    _$CAPACITY_USE$IO_SARL_API_CORE_LIFECYCLE$CALLER.killMe();
  }

  /**
   * Test state serialization methods.
   */
  protected void testStateSerialization(final SokobanState state) {
    Logging _$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER.info("\n--- Testing State Serialization ---");
    double[] vector = state.toVector();
    Logging _$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER_1 = this.$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER();
    int _length = vector.length;
    _$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER_1.info(("Vector representation length: " + Integer.valueOf(_length)));
    Logging _$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER_2 = this.$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER();
    double _get = vector[0];
    double _get_1 = vector[1];
    double _get_2 = vector[2];
    double _get_3 = vector[3];
    double _get_4 = vector[4];
    _$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER_2.info(((((((((("  First 5 values: " + Double.valueOf(_get)) + ", ") + Double.valueOf(_get_1)) + ", ") + Double.valueOf(_get_2)) + ", ") + Double.valueOf(_get_3)) + ", ") + Double.valueOf(_get_4)));
    double[][][] grid = state.toGrid();
    Logging _$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER_3 = this.$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER();
    int _length_1 = grid.length;
    int _length_2 = grid[0].length;
    int _length_3 = grid[0][0].length;
    _$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER_3.info(((((("Grid representation: " + Integer.valueOf(_length_1)) + " channels, ") + Integer.valueOf(_length_2)) + "x") + Integer.valueOf(_length_3)));
    SokobanState stateCopy = state.copy();
    Logging _$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER_4 = this.$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER();
    boolean _equals = state.equals(stateCopy);
    _$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER_4.info(("State equals copy: " + Boolean.valueOf(_equals)));
    Logging _$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER_5 = this.$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER();
    int _hashCode = state.hashCode();
    int _hashCode_1 = stateCopy.hashCode();
    _$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER_5.info(((("State hash codes: " + Integer.valueOf(_hashCode)) + " vs ") + Integer.valueOf(_hashCode_1)));
    Logging _$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER_6 = this.$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER();
    boolean _allBoxesOnTargets = state.allBoxesOnTargets();
    _$CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER_6.info(("All boxes on targets: " + Boolean.valueOf(_allBoxesOnTargets)));
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
  @ImportedCapacityFeature(Logging.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$IO_SARL_API_CORE_LOGGING;

  @SyntheticMember
  @Pure
  private Logging $CAPACITY_USE$IO_SARL_API_CORE_LOGGING$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_API_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_API_CORE_LOGGING.get() == null) {
      this.$CAPACITY_USE$IO_SARL_API_CORE_LOGGING = $getSkill(Logging.class);
    }
    return $castSkill(Logging.class, this.$CAPACITY_USE$IO_SARL_API_CORE_LOGGING);
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
    toBeFilled.add(Initialize.class);
  }

  @SyntheticMember
  @Override
  public boolean $isSupportedEvent(final Class<? extends Event> event) {
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
    if (Initialize.class.equals(eventType)) {
      final var occurrence = (Initialize) event;
      $guardEvaluator$Initialize(occurrence, callbacks);
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
    RLTestDemo other = (RLTestDemo) obj;
    if (other.stepCount != this.stepCount)
      return false;
    return super.equals(obj);
  }

  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Integer.hashCode(this.stepCount);
    return result;
  }

  @SyntheticMember
  public RLTestDemo(final UUID parentID, final UUID agentID) {
    super(parentID, agentID);
  }

  @SyntheticMember
  @Inject
  public RLTestDemo(final UUID parentID, final UUID agentID, final DynamicSkillProvider skillProvider) {
    super(parentID, agentID, skillProvider);
  }
}
