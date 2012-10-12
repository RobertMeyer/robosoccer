package net.sf.robocode.mode;

import java.awt.BorderLayout;
import java.util.Hashtable;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import robocode.BattleRules;
import robocode.Rules;


public class LowVision extends ClassicMode{
	
	private LowVisionRulesPanel rulesPanel;
	
	private final String title = "Low Vision Mode";
    private final String description = "This mode will reduce the vision of the Robots by a user specified amount.";

    public String toString() {
        return title;
    }

    public String getDescription() {
        return description;
    }
    
    public JPanel getRulesPanel(){
		if(rulesPanel == null){
			rulesPanel = new LowVisionRulesPanel();
		}
		return rulesPanel;
	}
    
    public Hashtable<String, Object> getRulesPanelValues() {
		return rulesPanel.getValues();
	}
     
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
    
    
    @SuppressWarnings("serial")
	private class LowVisionRulesPanel extends JPanel {
		private JTextField VisionModifier; 
		public LowVisionRulesPanel() {
			super();
			
			add(new JLabel("Vision modifier:"), BorderLayout.NORTH);
			
			VisionModifier = new JTextField(5);
			VisionModifier.setText("100");
			add(VisionModifier);
			
			add(new JLabel("100 is standard"));
		}
		
		public Hashtable<String, Object> getValues() {
			Hashtable<String, Object> values = new Hashtable<String, Object>();
			values.put("VisionModifier", VisionModifier.getText());
			return values;
		}
	}

}
