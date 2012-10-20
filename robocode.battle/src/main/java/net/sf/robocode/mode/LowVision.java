package net.sf.robocode.mode;

import java.awt.BorderLayout;
import java.util.Hashtable;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import robocode.BattleRules;
import robocode.Rules;

/**
 * Low Vision mode:
 *  Contains methods for initialising settings
 *  overwrites rules for Radar Scan
 *
 *
 * @author Team Microsolth
 *
 */
public class LowVision extends ClassicMode{

	private LowVisionRulesPanel rulesPanel;

	private final String title = "Low Vision Mode";
    private final String description = "Modify all robots' vision. (0 = blind, 100 = standard vision)";

    public String toString() {
        return title;
    }

    public String getDescription() {
        return description;
    }
    /**
     * set the rules panel
     */
    public JPanel getRulesPanel(){
		if(rulesPanel == null){
			rulesPanel = new LowVisionRulesPanel();
		}
		return rulesPanel;
	}

    public Hashtable<String, Object> getRulesPanelValues() {
		return rulesPanel.getValues();
	}

    /**
     * Set the range using the modifier, overwrite the robot scan radius
     */
    public double modifyVision(double standard, BattleRules rules){

    	double modifier, range;

    	try {
    		// Attempt to retrieve a modifier value
    		modifier = (double) Double.valueOf( (String)
    				rules.getModeRules().get("VisionModifier") );
    		modifier = modifier / 100;
    	} catch (Exception e) {
    		// If a value can't be found (for whatever reason), stick to standard (100%)
    		modifier = 1;
    	}

    	// New range (vision) set as standard multiplied by the modifier
    	range = standard * modifier;

    	return range;
    }

    /**
     * JLabel to imput the value in term of percentage
     */
    @SuppressWarnings("serial")
	private class LowVisionRulesPanel extends JPanel {
		private JTextField VisionModifier;
		public LowVisionRulesPanel() {
			super();

			add(new JLabel("Vision:"), BorderLayout.NORTH);

			VisionModifier = new JTextField(5);
			VisionModifier.setText("100");
			add(VisionModifier);

			add(new JLabel("%"));
		}

		public Hashtable<String, Object> getValues() {
			Hashtable<String, Object> values = new Hashtable<String, Object>();
			values.put("VisionModifier", VisionModifier.getText());
			return values;
		}
	}

}
