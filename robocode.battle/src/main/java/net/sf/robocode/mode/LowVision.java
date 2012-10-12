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
    private final String description = "This mode will reduce the vision of the Robots by 1/4";

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
     
    public double modifyVision(double VisionDecrease){
    
    	return Rules.RADAR_SCAN_RADIUS/4;
    }
    
    
    @SuppressWarnings("serial")
	private class LowVisionRulesPanel extends JPanel {
		private JTextField VisionModifier; 
		public LowVisionRulesPanel() {
			super();
			
			add(new JLabel("Vision modifier:"), BorderLayout.NORTH);
			
			VisionModifier = new JTextField(5);
			add(VisionModifier);
		}
		
		public Hashtable<String, Object> getValues() {
			Hashtable<String, Object> values = new Hashtable<String, Object>();
			values.put("VisionModifier", VisionModifier.getText());
			return values;
		}
	}

}
