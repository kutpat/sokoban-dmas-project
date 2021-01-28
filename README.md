# Multi-Agent Sokoban Simulation using SARL (Agentic Programming Language)


## Project Overview

This project implements a multi-agent system inspired by the classic **Sokoban** puzzle game, using the **Agent & Artifact (A&A)** paradigm in **SARL**. The system demonstrates how autonomous agents can collaborate to solve spatial reasoning tasks in a deterministic **2D grid** environment.

### The simulation features
- Multiple autonomous agents that make independent decisions
- Box artifacts that are manipulated through pushing actions
- A deterministic 2D grid environment with static walls and dynamic objects
- Decentralized coordination through event-based communication
- A graphical user interface for visualization

---

## Compliance with Project Requirements

This implementation fully adheres to all project requirements as specified in the PRD:

### 1) Agent & Artifact Paradigm
- ✓ Agents are implemented as autonomous SARL agents (`SokobanAgent`)
- ✓ Boxes are modeled as passive artifacts (`BoxObject`) with state only
- ✓ Agents manipulate boxes through push actions; boxes have no autonomy
- ✓ Clear separation between active entities (agents) and passive entities (artifacts)

### 2) Multi-Agent System
- ✓ Multiple agents operate concurrently in the same environment
- ✓ Agents are autonomous and reactive, making decisions based on local perception
- ✓ Agents coordinate through event-based communication (`BoxClaimed`, `BoxPositionUpdate`, `BoxCompleted`)
- ✓ No central controller exists; all decisions are made locally by agents

### 3) 2D Grid Environment
- ✓ Discrete grid-based movement (cell-by-cell)
- ✓ Four-directional movement (North, South, East, West)
- ✓ Boxes can only be pushed (never pulled)
- ✓ Movement and pushing follow standard Sokoban rules

### 4) Static and Mobile Objects
- ✓ Walls are immobile obstacles (`WallObject`) that block all movement
- ✓ Boxes are mobile artifacts that can be pushed by agents
- ✓ Agents cannot move through walls or boxes
- ✓ Boxes cannot be pushed through walls or other boxes

### 5) Exit Condition
- ✓ Boxes are moved to designated exit positions (cell properties)
- ✓ Box placement on exits is the primary task; once all boxes are placed, agents subsequently navigate to exits, completing the simulation
- ✓ Win condition is correctly detected and reported
- ✓ Agents navigate toward exits after completing box-pushing tasks

### 6) No Central Control
- ✓ Each agent maintains local state and makes independent decisions
- ✓ Agents base decisions only on:
  - Local perception (line-of-sight within 5 cells)
  - Shared knowledge accumulated through event-based communication (initial box/exit positions, updated via events)
  - Received communication events from other agents
- ✓ No central planner, coordinator, or command structure exists
- ✓ Coordination emerges through decentralized claiming and observation

### 7) Graphical User Interface
- ✓ GUI adapted from the provided Pacman framework
- ✓ Custom rendering for Sokoban entities:
  - Agents (Omani-style characters in traditional dress)
  - Boxes (wooden crates with visual feedback when on target)
  - Walls (dark gray obstacles)
  - Exits (green square goal tiles)
- ✓ Dashboard displays real-time statistics:
  - Boxes on targets / Total boxes
  - Agents at exit / Total agents
  - Simulation time
- ✓ Fast-forward button for controlling simulation speed
- ✓ Visual-only changes; no impact on game logic

---

## Agent Behavior Design

Agents operate through a four-phase behavior system that ensures robust coordination and task completion.

### Phase 1: `BOX_SELECTION`
- Agent evaluates all known box positions
- Selects nearest unclaimed, incomplete box
- Claims box by emitting `BoxClaimed` event
- Resolves conflicts using deterministic tie-breaker (agent ID comparison)
- Transitions to **ALIGNMENT** phase upon successful claim

### Phase 2: `ALIGNMENT`
- Agent navigates to push position (behind box, opposite to target exit)
- Uses local BFS pathfinding to find optimal route
- Handles cases where box is temporarily out of perception (SEEK behavior)
- Verifies box completion status before pushing
- Transitions to **PUSH_EXECUTION** when in position

### Phase 3: `PUSH_EXECUTION`
- Agent pushes box step-by-step toward nearest exit
- Recalculates push direction dynamically based on current box position
- Continues pushing as long as path is clear and box moves closer to exit
- Handles obstacles and path changes reactively
- Transitions to **TASK_COMPLETION** when box reaches exit

### Phase 4: `TASK_COMPLETION`
- Agent verifies box is on exit (perception-based confirmation)
- Emits `BoxCompleted` event to notify other agents
- Releases box claim and clears local task state
- Updates global knowledge (marks box as completed)
- Transitions back to **BOX_SELECTION** for next task

### Key design principles

**Reactive Behavior**
- Agents respond immediately to perception events
- No planning ahead; decisions made per step
- Behavior adapts to changing environment state

**Local Perception**
- Agents perceive objects within 5-cell radius using line-of-sight
- Bresenham's line algorithm ensures accurate visibility
- Perception includes walls, boxes, other agents, and exit positions

**Robust Coordination**
- Decentralized claiming prevents multiple agents from working on the same box
- Event-based communication ensures all agents stay informed
- Deterministic tie-breaker prevents coordination deadlocks
- Global knowledge synchronization via `BoxPositionUpdate` events

**Deterministic Execution**
- One action per agent per simulation step
- Step boundaries enforced by environment (action gating)
- Predictable state transitions and behavior

---

## Environment Design

The environment provides a deterministic, solvable grid structure.

### Grid structure
- Fixed-size 2D grid (configurable width/height)
- Outer walls form boundaries
- Inner walls placed using deterministic layouts (3 predefined patterns)
- All layouts guarantee connectivity (flood-fill validation)

### Wall layouts
- Layout 0: Horizontal barrier with decorative edge segments
- Layout 1: Vertical barrier with decorative edge segments
- Layout 2: L-shaped corner pattern with decorative segments
- Walls are sparse and decorative; do not create narrow corridors
- Minimum corridor width: 3 cells (ensures box maneuverability)

### Box and exit placement
- Boxes spawn in open areas away from walls
- Exits are cell properties (`Set<Point2i>`), not objects
- Boxes can occupy exit cells (visual feedback via darker color)
- Initial positions ensure solvability

### Deterministic execution
- No random topology changes during execution
- Wall positions fixed at initialization
- Box and exit positions known to all agents at start
- Reproducible simulation runs

---

## Graphical User Interface

The GUI provides clear visual feedback and real-time statistics.

### Visual design
- Light beige floor tiles replace black background for better visibility
- Agents rendered as Omani-style characters in traditional white dishdasha
- Boxes shown as wooden crates with darker shading when on target
- Exits displayed as green square goal tiles with pulsing highlight
- Walls rendered as simple dark gray obstacles
- All rendering is visual-only; no impact on game logic

### Dashboard features
- Real-time box statistics (boxes on targets / total boxes)
- Real-time agent statistics (agents at exit / total agents)
- Simulation time display
- Agent count selector (configurable at start)
- Start button to begin simulation
- Fast-forward button to control simulation speed

### User interaction
- No keyboard input required (all agents are autonomous)
- GUI updates automatically as environment state changes
- Win condition displayed when all boxes reach exits

---

## How to Run

### Prerequisites
- Java Development Kit (JDK) 8 or later
- SARL Development Environment (Eclipse with SARL plugin)
- Janus runtime platform

### Running the simulation
1. Import the project into Eclipse
2. Ensure all SARL files compile successfully
3. Run the main class: `sokoban.SokobanBoot`
4. In the GUI:
   - Select desired number of agents using the spinner
   - Click the **Start** button to begin simulation
   - Use **Fast Forward** button to speed up execution
   - Observe agents coordinating to move boxes to exits

### Configuration
- Agent count: Set via dashboard spinner before starting
- Grid size: Configured in Environment agent initialization
- Perception distance: 5 cells (configurable in `DefaultMazeManagerSkill`)
- Simulation speed: Controlled via fast-forward toggle

---

## Conclusion

This implementation successfully demonstrates a multi-agent system that solves Sokoban-style puzzles through decentralized coordination. The system strictly adheres to the Agent & Artifact paradigm, with autonomous agents making local decisions based on perception and communication.

The design ensures:
- **Correctness:** Agents reliably complete tasks and reach win conditions
- **PRD Compliance:** All requirements met without central control
- **Educational Value:** Clear demonstration of multi-agent coordination, reactive behavior, and event-based communication in SARL

The codebase is modular, well-structured, and maintains clear separation between agent logic, artifact state, and environment management. The graphical interface provides intuitive visualization of agent behavior and system state.
