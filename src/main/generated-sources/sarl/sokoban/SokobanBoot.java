package sokoban;

import io.sarl.api.core.Initialize;
import io.sarl.api.core.Lifecycle;
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
import sokoban.environment.agent.Environment;
import sokoban.ui.sokobanGUI;

/**
 * Launcher of the simulation framework.
 * 
 * This launcher needs the {@link http://www.janusproject.io Janus platform}.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
@SarlSpecification("0.15")
@SarlElementType(19)
@XbaseGenerated
@SuppressWarnings("all")
public class SokobanBoot extends Agent {
  /**
   * Width of the world (in number of cells).
   */
  private final int WIDTH = 20;

  /**
   * Height of the world (in number of cells).
   */
  private final int HEIGHT = 20;

  /**
   * Number of ghosts at the start-up.
   */
  private final int NB_GHOSTS = 3;

  /**
   * Percpetion distance for the agents (usually the ghosts).
   */
  private final int PERCEPTION_DISTANCE = 5;

  /**
   * The UI will force the environment agent to wait for it.
   */
  private final int WAITING_DURATION = 500;

  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    sokobanGUI ui = new sokobanGUI(this.WAITING_DURATION);
    Lifecycle _$CAPACITY_USE$IO_SARL_API_CORE_LIFECYCLE$CALLER = this.$CAPACITY_USE$IO_SARL_API_CORE_LIFECYCLE$CALLER();
    _$CAPACITY_USE$IO_SARL_API_CORE_LIFECYCLE$CALLER.spawn(Environment.class, Integer.valueOf(this.WIDTH), Integer.valueOf(this.HEIGHT), Integer.valueOf(this.NB_GHOSTS), Integer.valueOf(this.PERCEPTION_DISTANCE), ui);
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
    SokobanBoot other = (SokobanBoot) obj;
    if (other.WIDTH != this.WIDTH)
      return false;
    if (other.HEIGHT != this.HEIGHT)
      return false;
    if (other.NB_GHOSTS != this.NB_GHOSTS)
      return false;
    if (other.PERCEPTION_DISTANCE != this.PERCEPTION_DISTANCE)
      return false;
    if (other.WAITING_DURATION != this.WAITING_DURATION)
      return false;
    return super.equals(obj);
  }

  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Integer.hashCode(this.WIDTH);
    result = prime * result + Integer.hashCode(this.HEIGHT);
    result = prime * result + Integer.hashCode(this.NB_GHOSTS);
    result = prime * result + Integer.hashCode(this.PERCEPTION_DISTANCE);
    result = prime * result + Integer.hashCode(this.WAITING_DURATION);
    return result;
  }

  @SyntheticMember
  public SokobanBoot(final UUID parentID, final UUID agentID) {
    super(parentID, agentID);
  }

  @SyntheticMember
  @Inject
  public SokobanBoot(final UUID parentID, final UUID agentID, final DynamicSkillProvider skillProvider) {
    super(parentID, agentID, skillProvider);
  }
}
