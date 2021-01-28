/**
 * Dashboard panel for Sokoban simulator.
 * 
 * Displays:
 * - Agent count selector (before simulation starts)
 * - Boxes on targets count
 * - Agents at exit count
 * - Step count / time
 */
package sokoban.ui;

import io.sarl.lang.core.annotation.SarlElementType;
import io.sarl.lang.core.annotation.SarlSpecification;
import io.sarl.lang.core.annotation.SyntheticMember;
import io.sarl.lang.core.scoping.extensions.cast.PrimitiveCastExtensions;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;

/**
 * Dashboard panel showing game statistics and controls.
 */
@SarlSpecification("0.15")
@SarlElementType(10)
@XbaseGenerated
@SuppressWarnings("all")
public class DashboardPanel extends JPanel {
  private final JSpinner agentCountSpinner;

  private final JLabel boxesOnTargetsLabel;

  private final JLabel totalBoxesLabel;

  private final JLabel agentsAtExitLabel;

  private final JLabel totalAgentsLabel;

  private final JLabel stepCountLabel;

  private final JLabel timeLabel;

  public DashboardPanel() {
    BorderLayout _borderLayout = new BorderLayout();
    this.setLayout(_borderLayout);
    this.setBorder(BorderFactory.createTitledBorder("Dashboard"));
    JPanel controlsPanel = new JPanel();
    BoxLayout _boxLayout = new BoxLayout(controlsPanel, BoxLayout.Y_AXIS);
    controlsPanel.setLayout(_boxLayout);
    controlsPanel.setBorder(BorderFactory.createTitledBorder("Settings"));
    JPanel agentCountPanel = new JPanel();
    BorderLayout _borderLayout_1 = new BorderLayout();
    agentCountPanel.setLayout(_borderLayout_1);
    JLabel agentCountLabel = new JLabel("Number of Agents:");
    Font _font = new Font("Arial", Font.PLAIN, 12);
    agentCountLabel.setFont(_font);
    SpinnerNumberModel spinnerModel = new SpinnerNumberModel(2, 1, 6, 1);
    JSpinner _jSpinner = new JSpinner(spinnerModel);
    this.agentCountSpinner = _jSpinner;
    Dimension _dimension = new Dimension(60, 25);
    this.agentCountSpinner.setPreferredSize(_dimension);
    agentCountPanel.add(BorderLayout.WEST, agentCountLabel);
    agentCountPanel.add(BorderLayout.CENTER, this.agentCountSpinner);
    controlsPanel.add(agentCountPanel);
    this.add(BorderLayout.WEST, controlsPanel);
    JPanel statsPanel = new JPanel();
    GridLayout _gridLayout = new GridLayout(0, 2, 10, 5);
    statsPanel.setLayout(_gridLayout);
    statsPanel.setBorder(BorderFactory.createTitledBorder("Statistics"));
    JLabel boxesLabel = new JLabel("Boxes on Targets:");
    Font _font_1 = new Font("Arial", Font.BOLD, 12);
    boxesLabel.setFont(_font_1);
    JLabel _jLabel = new JLabel("0");
    this.boxesOnTargetsLabel = _jLabel;
    Font _font_2 = new Font("Arial", Font.PLAIN, 12);
    this.boxesOnTargetsLabel.setFont(_font_2);
    this.boxesOnTargetsLabel.setForeground(Color.GREEN);
    this.boxesOnTargetsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
    JLabel totalBoxesLabelText = new JLabel("Total Boxes:");
    Font _font_3 = new Font("Arial", Font.BOLD, 12);
    totalBoxesLabelText.setFont(_font_3);
    JLabel _jLabel_1 = new JLabel("0");
    this.totalBoxesLabel = _jLabel_1;
    Font _font_4 = new Font("Arial", Font.PLAIN, 12);
    this.totalBoxesLabel.setFont(_font_4);
    this.totalBoxesLabel.setHorizontalAlignment(SwingConstants.RIGHT);
    JLabel agentsLabel = new JLabel("Agents at Exit:");
    Font _font_5 = new Font("Arial", Font.BOLD, 12);
    agentsLabel.setFont(_font_5);
    JLabel _jLabel_2 = new JLabel("0");
    this.agentsAtExitLabel = _jLabel_2;
    Font _font_6 = new Font("Arial", Font.PLAIN, 12);
    this.agentsAtExitLabel.setFont(_font_6);
    this.agentsAtExitLabel.setForeground(Color.BLUE);
    this.agentsAtExitLabel.setHorizontalAlignment(SwingConstants.RIGHT);
    JLabel totalAgentsLabelText = new JLabel("Total Agents:");
    Font _font_7 = new Font("Arial", Font.BOLD, 12);
    totalAgentsLabelText.setFont(_font_7);
    JLabel _jLabel_3 = new JLabel("0");
    this.totalAgentsLabel = _jLabel_3;
    Font _font_8 = new Font("Arial", Font.PLAIN, 12);
    this.totalAgentsLabel.setFont(_font_8);
    this.totalAgentsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
    JLabel stepLabel = new JLabel("Step Count:");
    Font _font_9 = new Font("Arial", Font.BOLD, 12);
    stepLabel.setFont(_font_9);
    JLabel _jLabel_4 = new JLabel("0");
    this.stepCountLabel = _jLabel_4;
    Font _font_10 = new Font("Arial", Font.PLAIN, 12);
    this.stepCountLabel.setFont(_font_10);
    this.stepCountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
    JLabel timeLabelText = new JLabel("Time:");
    Font _font_11 = new Font("Arial", Font.BOLD, 12);
    timeLabelText.setFont(_font_11);
    JLabel _jLabel_5 = new JLabel("0");
    this.timeLabel = _jLabel_5;
    Font _font_12 = new Font("Arial", Font.PLAIN, 12);
    this.timeLabel.setFont(_font_12);
    this.timeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
    statsPanel.add(boxesLabel);
    statsPanel.add(this.boxesOnTargetsLabel);
    statsPanel.add(totalBoxesLabelText);
    statsPanel.add(this.totalBoxesLabel);
    statsPanel.add(agentsLabel);
    statsPanel.add(this.agentsAtExitLabel);
    statsPanel.add(totalAgentsLabelText);
    statsPanel.add(this.totalAgentsLabel);
    statsPanel.add(stepLabel);
    statsPanel.add(this.stepCountLabel);
    statsPanel.add(timeLabelText);
    statsPanel.add(this.timeLabel);
    this.add(BorderLayout.CENTER, statsPanel);
    Dimension _dimension_1 = new Dimension(400, 150);
    this.setPreferredSize(_dimension_1);
  }

  /**
   * Get the selected number of agents.
   * 
   * @return number of agents selected
   */
  @Pure
  public int getAgentCount() {
    Object _value = this.agentCountSpinner.getValue();
    int count = (_value == null ? null : PrimitiveCastExtensions.toInteger(_value)).intValue();
    System.out.println(("[Dashboard] Spinner value: " + Integer.valueOf(count)));
    return count;
  }

  /**
   * Set the number of agents (for display).
   * 
   * @param count number of agents
   */
  public void setAgentCount(final int count) {
    this.agentCountSpinner.setValue(Integer.valueOf(count));
  }

  /**
   * Enable or disable the agent count selector.
   * 
   * @param enabled true to enable, false to disable
   */
  public void setAgentCountEnabled(final boolean enabled) {
    this.agentCountSpinner.setEnabled(enabled);
  }

  /**
   * Update box statistics.
   * 
   * @param boxesOnTargets number of boxes currently on target cells
   * @param totalBoxes total number of boxes
   */
  public void updateBoxStats(final int boxesOnTargets, final int totalBoxes) {
    String _string = Integer.valueOf(boxesOnTargets).toString();
    String _string_1 = Integer.valueOf(totalBoxes).toString();
    this.boxesOnTargetsLabel.setText(((_string + " / ") + _string_1));
    if (((boxesOnTargets == totalBoxes) && (totalBoxes > 0))) {
      this.boxesOnTargetsLabel.setForeground(Color.GREEN);
    } else {
      this.boxesOnTargetsLabel.setForeground(Color.ORANGE);
    }
    this.totalBoxesLabel.setText(Integer.valueOf(totalBoxes).toString());
  }

  /**
   * Update agent statistics.
   * 
   * @param agentsAtExit number of agents currently at exit positions
   * @param totalAgents total number of agents
   */
  public void updateAgentStats(final int agentsAtExit, final int totalAgents) {
    String _string = Integer.valueOf(agentsAtExit).toString();
    String _string_1 = Integer.valueOf(totalAgents).toString();
    this.agentsAtExitLabel.setText(((_string + " / ") + _string_1));
    if (((agentsAtExit == totalAgents) && (totalAgents > 0))) {
      this.agentsAtExitLabel.setForeground(Color.GREEN);
    } else {
      this.agentsAtExitLabel.setForeground(Color.BLUE);
    }
    this.totalAgentsLabel.setText(Integer.valueOf(totalAgents).toString());
  }

  /**
   * Update step count and time.
   * 
   * @param stepCount current step count
   * @param time current time
   */
  public void updateTimeStats(final int stepCount, final int time) {
    this.stepCountLabel.setText(Integer.valueOf(stepCount).toString());
    this.timeLabel.setText(Integer.valueOf(time).toString());
  }

  /**
   * Reset all statistics to zero.
   */
  public void resetStats() {
    this.boxesOnTargetsLabel.setText("0");
    this.totalBoxesLabel.setText("0");
    this.agentsAtExitLabel.setText("0");
    this.totalAgentsLabel.setText("0");
    this.stepCountLabel.setText("0");
    this.timeLabel.setText("0");
    this.boxesOnTargetsLabel.setForeground(Color.GREEN);
    this.agentsAtExitLabel.setForeground(Color.BLUE);
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
  private static final long serialVersionUID = 7509071725L;
}
