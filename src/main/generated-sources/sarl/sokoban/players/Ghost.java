package sokoban.players;

import framework.math.Point2i;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;
import sokoban.environment.agent.MazeMotion;
import sokoban.environment.agent.Perception;
import sokoban.environment.agent.sokobanIsDead;
import sokoban.environment.maze.Direction;
import sokoban.environment.maze.sokobanBody;
import sokoban.environment.maze.sokobanObject;

/**
 * Ghost.
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
public class Ghost extends Agent {
  private final Random random = new Random();

  private Direction previousDirection;

  private void $behaviorUnit$sokobanIsDead$0(final sokobanIsDead occurrence) {
    Lifecycle _$CAPACITY_USE$IO_SARL_API_CORE_LIFECYCLE$CALLER = this.$CAPACITY_USE$IO_SARL_API_CORE_LIFECYCLE$CALLER();
    _$CAPACITY_USE$IO_SARL_API_CORE_LIFECYCLE$CALLER.killMe();
  }

  private void $behaviorUnit$Perception$1(final Perception occurrence) {
    sokobanBody sokoban = null;
    for (final sokobanObject o : occurrence.objects) {
      if ((o instanceof sokobanBody)) {
        sokoban = ((sokobanBody)o);
        break;
      }
    }
    Direction dir = null;
    final Point2i myPos = occurrence.position;
    if ((sokoban != null)) {
      boolean sokobanHasSuperPower = false;
      sokobanHasSuperPower = sokoban.isSupersokoban();
      final int px = sokoban.getPosition().getX();
      final int py = sokoban.getPosition().getY();
      int _x = myPos.getX();
      final int dx = (px - _x);
      int _y = myPos.getY();
      final int dy = (py - _y);
      if ((!sokobanHasSuperPower)) {
        if (((dy == 0) && (dx != 0))) {
          Direction _xifexpression = null;
          if ((dx > 0)) {
            _xifexpression = Direction.EAST;
          } else {
            _xifexpression = Direction.WEST;
          }
          dir = _xifexpression;
        } else {
          if (((dx == 0) && (dy != 0))) {
            Direction _xifexpression_1 = null;
            if ((dy > 0)) {
              _xifexpression_1 = Direction.SOUTH;
            } else {
              _xifexpression_1 = Direction.NORTH;
            }
            dir = _xifexpression_1;
          } else {
            int _abs = Math.abs(dx);
            int _abs_1 = Math.abs(dy);
            if ((_abs >= _abs_1)) {
              Direction _xifexpression_2 = null;
              if ((dx > 0)) {
                _xifexpression_2 = Direction.EAST;
              } else {
                _xifexpression_2 = Direction.WEST;
              }
              dir = _xifexpression_2;
            } else {
              Direction _xifexpression_3 = null;
              if ((dy > 0)) {
                _xifexpression_3 = Direction.SOUTH;
              } else {
                _xifexpression_3 = Direction.NORTH;
              }
              dir = _xifexpression_3;
            }
          }
        }
      } else {
        if (((dy == 0) && (dx != 0))) {
          Direction _xifexpression_4 = null;
          if ((dx > 0)) {
            _xifexpression_4 = Direction.WEST;
          } else {
            _xifexpression_4 = Direction.EAST;
          }
          dir = _xifexpression_4;
        } else {
          if (((dx == 0) && (dy != 0))) {
            Direction _xifexpression_5 = null;
            if ((dy > 0)) {
              _xifexpression_5 = Direction.NORTH;
            } else {
              _xifexpression_5 = Direction.SOUTH;
            }
            dir = _xifexpression_5;
          } else {
            int _abs_2 = Math.abs(dx);
            int _abs_3 = Math.abs(dy);
            if ((_abs_2 >= _abs_3)) {
              Direction _xifexpression_6 = null;
              if ((dx > 0)) {
                _xifexpression_6 = Direction.WEST;
              } else {
                _xifexpression_6 = Direction.EAST;
              }
              dir = _xifexpression_6;
            } else {
              Direction _xifexpression_7 = null;
              if ((dy > 0)) {
                _xifexpression_7 = Direction.NORTH;
              } else {
                _xifexpression_7 = Direction.SOUTH;
              }
              dir = _xifexpression_7;
            }
          }
        }
      }
    } else {
      final List<Direction> all = Collections.<Direction>unmodifiableList(CollectionLiterals.<Direction>newArrayList(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST));
      ArrayList<Direction> choices = new ArrayList<Direction>();
      for (final Direction d : all) {
        if ((this.previousDirection == null)) {
          choices.add(d);
        } else {
          final Direction previousDirection = this.previousDirection;
          if (previousDirection != null) {
            switch (previousDirection) {
              case NORTH:
                boolean _notEquals = (!Objects.equals(d, Direction.SOUTH));
                if (_notEquals) {
                  choices.add(d);
                }
                break;
              case SOUTH:
                boolean _notEquals_1 = (!Objects.equals(d, Direction.NORTH));
                if (_notEquals_1) {
                  choices.add(d);
                }
                break;
              case EAST:
                boolean _notEquals_2 = (!Objects.equals(d, Direction.WEST));
                if (_notEquals_2) {
                  choices.add(d);
                }
                break;
              case WEST:
                boolean _notEquals_3 = (!Objects.equals(d, Direction.EAST));
                if (_notEquals_3) {
                  choices.add(d);
                }
                break;
              default:
                choices.add(d);
                break;
            }
          } else {
            choices.add(d);
          }
        }
      }
      final Random r = new Random();
      dir = choices.get(r.nextInt(choices.size()));
    }
    if ((dir != null)) {
      this.previousDirection = dir;
      MazeMotion _$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMOTION$CALLER = this.$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMOTION$CALLER();
      _$CAPACITY_USE$SOKOBAN_ENVIRONMENT_AGENT_MAZEMOTION$CALLER.move(dir);
    }
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
  private void $guardEvaluator$Perception(final Perception occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Perception$1(occurrence));
  }

  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$sokobanIsDead(final sokobanIsDead occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$sokobanIsDead$0(occurrence));
  }

  @SyntheticMember
  @Override
  public void $getSupportedEvents(final Set<Class<? extends Event>> toBeFilled) {
    super.$getSupportedEvents(toBeFilled);
    toBeFilled.add(Perception.class);
    toBeFilled.add(sokobanIsDead.class);
  }

  @SyntheticMember
  @Override
  public boolean $isSupportedEvent(final Class<? extends Event> event) {
    if (Perception.class.isAssignableFrom(event)) {
      return true;
    }
    if (sokobanIsDead.class.isAssignableFrom(event)) {
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
    if (Perception.class.equals(eventType)) {
      final var occurrence = (Perception) event;
      $guardEvaluator$Perception(occurrence, callbacks);
    }
    if (sokobanIsDead.class.equals(eventType)) {
      final var occurrence = (sokobanIsDead) event;
      $guardEvaluator$sokobanIsDead(occurrence, callbacks);
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
  public Ghost(final UUID parentID, final UUID agentID) {
    super(parentID, agentID);
  }

  @SyntheticMember
  @Inject
  public Ghost(final UUID parentID, final UUID agentID, final DynamicSkillProvider skillProvider) {
    super(parentID, agentID, skillProvider);
  }
}
