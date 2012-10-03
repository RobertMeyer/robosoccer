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
		double ricochetValue = (double) Double.parseDouble((String) rules
				.getModeRules().get("ricochetModifier"));
		if (ricochetValue < 1) {
			ricochetValue = 1;
		}
		return ricochetValue;
	}

	@SuppressWarnings("serial")
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
