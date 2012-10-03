package net.sf.robocode.mode;

import java.awt.BorderLayout;
import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import robocode.BattleRules;

/**
 * Creates a game mode with obstacles placed on the battlefield
 * 
 * @author Team a-MAZE-ing
 *
 */
public class ObstacleMode extends ClassicMode {

	private ObstacleModeRulesPanel rulesPanel;
	
    @Override
    public String toString() {
        return "Obstacle Mode";
    }

    @Override
    public String getDescription() {
        return "A mode with obstacles that robots have to avoid.";
    }
    
    @Override
    public int setNumObstacles(BattleRules rules) {
    	return (int) Integer.parseInt((String) rules.getModeRules().get("numberObstacles"));
	}
    
    public JPanel getRulesPanel(){
		if(rulesPanel == null){
			rulesPanel = new ObstacleModeRulesPanel();
		}
		return rulesPanel;
	}
	
	public Hashtable<String, Object> getRulesPanelValues() {
		return rulesPanel.getValues();
	}
	
	@SuppressWarnings("serial")
	private class ObstacleModeRulesPanel extends JPanel {
		private JTextField numberObstacles; 
		public ObstacleModeRulesPanel() {
			super();
			
			add(new JLabel("Number of Obstacles:"), BorderLayout.NORTH);
			
			numberObstacles = new JTextField("10", 5);
			add(numberObstacles);
		}
		
		public Hashtable<String, Object> getValues() {
			Hashtable<String, Object> values = new Hashtable<String, Object>();
			values.put("numberObstacles", numberObstacles.getText());
			return values;
		}
	}
}
