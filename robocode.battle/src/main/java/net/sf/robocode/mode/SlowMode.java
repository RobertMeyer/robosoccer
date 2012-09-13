package net.sf.robocode.mode;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Hashtable;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import robocode.BattleRules;

public class SlowMode extends ClassicMode {
	
	private SlowModeRulesPanel rulesPanel;
	
	public String toString() {
		return "Slow Mode";
	}
	
	public String getDescription() {
		return "Robots move at half speed.";
	}
	
	public JPanel getRulesPanel(){
		if(rulesPanel == null){
			rulesPanel = new SlowModeRulesPanel();
		}
		return rulesPanel;
	}
	
	public Hashtable<String, Object> getRulesPanelValues() {
		return rulesPanel.getValues();
	}
	
	public double modifyVelocity(double velocityIncrement, BattleRules rules) {
		return velocityIncrement * (double) Double.parseDouble((String) rules.getModeRules().get("speedModifier"));
	}
	
	@SuppressWarnings("serial")
	private class SlowModeRulesPanel extends JPanel {
		private JTextField speedModifier; 
		public SlowModeRulesPanel() {
			super();
			
			add(new JLabel("Speed modifier:"), BorderLayout.NORTH);
			
			speedModifier = new JTextField(5);
			add(speedModifier);
		}
		
		public Hashtable<String, Object> getValues() {
			Hashtable<String, Object> values = new Hashtable<String, Object>();
			values.put("speedModifier", speedModifier.getText());
			return values;
		}
	}
}
