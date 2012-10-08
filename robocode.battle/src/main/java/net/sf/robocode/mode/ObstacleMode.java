package net.sf.robocode.mode;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.BattleProperties;
import net.sf.robocode.battle.peer.ObstaclePeer;
import net.sf.robocode.battle.peer.RobotPeer;

import robocode.BattleRules;
import robocode.control.RandomFactory;

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
	
	/**
	 * {@inheritDoc}
	 */
	public double[][] computeInitialPositions(String initialPositions,
			BattleRules battleRules, Battle battle, int robotsCount) {
		double[][] initialRobotPositions = null;

        if (initialPositions == null || initialPositions.trim().length() == 0) {
            return null;
        }

        List<String> positions = new ArrayList<String>();

        Pattern pattern = Pattern.compile("([^,(]*[(][^)]*[)])?[^,]*,?");
        Matcher matcher = pattern.matcher(initialPositions);

        while (matcher.find()) {
            String pos = matcher.group();

            if (pos.length() > 0) {
                positions.add(pos);
            }
        }

        if (positions.isEmpty()) {
            return null;
        }

        initialRobotPositions = new double[positions.size()][3];

        String[] coords;
        double x, y, heading;

        for (int i = 0; i < positions.size(); i++) {
            coords = positions.get(i).split(",");

            final Random random = RandomFactory.getRandom();

            x = RobotPeer.WIDTH + random.nextDouble() * (battleRules.getBattlefieldWidth() - 2 * RobotPeer.WIDTH);
            y = RobotPeer.HEIGHT + random.nextDouble() * (battleRules.getBattlefieldHeight() - 2 * RobotPeer.HEIGHT);
            heading = 2 * Math.PI * random.nextDouble();

            int len = coords.length;

            if (len >= 1) {
                // noinspection EmptyCatchBlock
                try {
                    x = Double.parseDouble(coords[0].replaceAll("[\\D]", ""));
                } catch (NumberFormatException e) {
                }

                if (len >= 2) {
                    // noinspection EmptyCatchBlock
                    try {
                        y = Double.parseDouble(coords[1].replaceAll("[\\D]", ""));
                    } catch (NumberFormatException e) {
                    }

                    if (len >= 3) {
                        // noinspection EmptyCatchBlock
                        try {
                            heading = Math.toRadians(Double.parseDouble(coords[2].replaceAll("[\\D]", "")));
                        } catch (NumberFormatException e) {
                        }
                    }
                }
            }
            initialRobotPositions[i][0] = x;
            initialRobotPositions[i][1] = y;
            initialRobotPositions[i][2] = heading;
        }

        return initialRobotPositions;
	}
	
	public static List<ObstaclePeer> generateRandomObstacles(int num, BattleProperties bp, BattleRules battleRules, Battle battle) {
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
	
	public static List<ObstaclePeer> generateMazeObstacles(int num, BattleProperties bp, BattleRules battleRules, Battle battle) {
		List<ObstaclePeer> obstacles = new ArrayList<ObstaclePeer>();
		/* Depth first search goes here. */		
		return obstacles;
	}
	
	public static List<ObstaclePeer> generateRoomObstacles(int num, BattleProperties bp, BattleRules battleRules, Battle battle) {
		List<ObstaclePeer> obstacles = new ArrayList<ObstaclePeer>();
		/* Recursive Division Maze Generation goes here. */		
		return obstacles;
	}
	
}
