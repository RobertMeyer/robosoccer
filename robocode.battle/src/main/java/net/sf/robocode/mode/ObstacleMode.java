package net.sf.robocode.mode;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.BattleProperties;
import net.sf.robocode.battle.peer.ObstaclePeer;
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
    
    @Override
    public int setCellWidth(BattleRules rules) {
    	return (int) Integer.parseInt((String) rules.getModeRules().get("obstacleWidth"));
	}
    
    @Override
    public int setCellHeight(BattleRules rules) {
    	return (int) Integer.parseInt((String) rules.getModeRules().get("obstacleHeight"));
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
		private JTextField numberObstacles, obstacleWidth, obstacleHeight;
		public ObstacleModeRulesPanel() {
			super();
			
			GridBagConstraints c = new GridBagConstraints();
	        setLayout(new GridBagLayout());
	        
	        JLabel numberObstaclesLbl = new JLabel("Number of Obstacles: ");
	        numberObstacles = new JTextField("10", 5);
	        JLabel obstacleWidthLbl = new JLabel("Width of Obstacles:");
	        obstacleWidth = new JTextField("32", 5);
	        JLabel obstacleHeightLbl = new JLabel("Height of Obstacles:");
	        obstacleHeight = new JTextField("32", 5);
	        
	        c.gridx = 0;
	        c.gridy = 0;
	        c.weightx = 0.1;
	        c.anchor = GridBagConstraints.LINE_START;
	        add(numberObstaclesLbl, c);
	        c.gridx = 0;
	        c.gridy = 1;
	        add(obstacleWidthLbl, c);
	        c.gridx = 0;
	        c.gridy = 2;
	        add(obstacleHeightLbl, c);
	        
	        c.gridx = 1;
	        c.gridy = 0;
	        c.anchor = GridBagConstraints.LINE_END;
	        add(numberObstacles, c);
	        c.gridx = 1;
	        c.gridy = 1;
	        add(obstacleWidth, c);
	        c.gridx = 1;
	        c.gridy = 2;
	        add(obstacleHeight, c);
		}
		
		public Hashtable<String, Object> getValues() {
			Hashtable<String, Object> values = new Hashtable<String, Object>();
			values.put("numberObstacles", numberObstacles.getText());
			values.put("obstacleWidth", obstacleWidth.getText());
			values.put("obstacleHeight", obstacleHeight.getText());
			return values;
		}
	}
	
	/**
	 * Generated random obstacles for the given battle, up to the specified number.
	 * Ensure that when placing each obstacle, 
	 * it is not intersecting with a previously placed obstacle.
	 * If obstacles can no longer be placed without touch another obstacle,
	 * stop placing any more obstacles and just return.
	 * 
	 * @param num			Number of obstacles to place.
	 * @param bp			BattleProperties for the battle.
	 * @param battleRules	BattleRules for the battle.
	 * @param battle		Battle to create obstacles for.
	 * @param cellWidth		Width of the obstacles.
	 * @param cellHeight	Height of the obstacles.
	 * @return List of obstacles for the battle.
	 */
	public static List<ObstaclePeer> generateRandomObstacles(int num, BattleProperties bp, BattleRules battleRules, Battle battle, int cellWidth, int cellHeight) {
		List<ObstaclePeer> obstacles = new ArrayList<ObstaclePeer>();
		Random randomGen = new Random();
		ObstaclePeer newObstacle;
		boolean intersect;
		double x, y;
		int fail = 0;
		for (int i = 0; i < num; i++) {
			/* Ensure new obstacle is not intersecting a previously placed obstacle. */
			do {
				intersect = false;
				x = randomGen.nextDouble() * bp.getBattlefieldWidth();
				y = randomGen.nextDouble() * bp.getBattlefieldHeight();
				newObstacle = new ObstaclePeer(battle, battleRules, i);
				newObstacle.setX(x);
				newObstacle.setY(y);
				newObstacle.setWidth(cellWidth);
				newObstacle.setHeight(cellHeight);
				for (int j = 0; j < obstacles.size(); j++) {
					if (obstacles.get(j).obstacleIntersect(newObstacle)) {
						intersect = true;
						/* Record number on which it failed, 
						 * if it fails as many times as there are obstacles,
						 * there is no more room for any obstacles, so return.
						 */
						if(fail == obstacles.size()) {
							return obstacles;
						}
						fail++;
					}
				}
			} while(intersect);
			fail = 0;
			obstacles.add(newObstacle);
		}
		return obstacles;
	}
}
