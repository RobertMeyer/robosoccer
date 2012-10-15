package net.sf.robocode.mode;

import java.awt.BorderLayout;
import java.util.Hashtable;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import robocode.BattleRules;

public class RicochetMode extends ClassicMode {
	
	private RicochetModeRulesPanel rulesPanel;
	private final String description = "Ricochet Mode: WATCH THE WALLS";

	public void execute() {
		System.out.println("Ricochet Mode.");
	}

	public String toString() {
		return new String("Ricochet Mode");
	}

	public String getDescription() {
		return description;
	}
	
	public JPanel getRulesPanel(){
		if(rulesPanel == null){
			rulesPanel = new RicochetModeRulesPanel();
		}
		return rulesPanel;
	}
	
	public Hashtable<String, Object> getRulesPanelValues() {
		return rulesPanel.getValues();
	}
	

	public double modifyRicochet(BattleRules rules) {
		double ricochetValue = 1;
		//try to check the value about ricochet
		try {
			ricochetValue = (double) Double.valueOf((String) rules
				.getModeRules().get("ricochetModifier"));
		} catch (NumberFormatException e) {
			//if the value is string not for the number, set as no power 
			//loss but still has ricochet
			ricochetValue = 1;
		}
		if (ricochetValue < 1) {
			//the ricochet cannot increase the bullet power, so if the 
			//ricochetValue less than 1 (power increase), set ricochetValue
			//to 1 (no power loss)
			ricochetValue = 1;
		}
		return ricochetValue;
	}

	@SuppressWarnings("serial")
	//build the rocochet panel that user can make the decision that how many
	//ricochet they want
	private class RicochetModeRulesPanel extends JPanel {
		private JTextField ricochetModifier;

		public RicochetModeRulesPanel() {
			super();

			add(new JLabel(
					"Ricochet modifier (The bullet power will be divided by this each ricochet:"),
					BorderLayout.NORTH);
			ricochetModifier = new JTextField(5);
			add(ricochetModifier);
		}

		//get ricochet power decrease value
		public Hashtable<String, Object> getValues() {
			Hashtable<String, Object> values = new Hashtable<String, Object>();
			values.put("ricochetModifier", ricochetModifier.getText());
			return values;
		}
	}	

	@Override
	public boolean shouldRicochet(double power, double minBulletPower,
			double ricochetValue) {
		if (power / ricochetValue >= minBulletPower) {
			// only ricochet if the bullet still meets the minBulletPower rule
			// after power is reduced
			return true;
		} else {
			return false;
		}
	}
}
