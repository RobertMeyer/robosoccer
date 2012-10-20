package net.sf.robocode.mode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.BattlePeers;
import net.sf.robocode.battle.HouseRobotSpawnController;
import net.sf.robocode.battle.peer.RobotPeer;
import robocode.BattleRules;
import robocode.control.RandomFactory;
import robocode.control.RobocodeEngine;

public class HouseRobotMode extends ClassicMode {
static {
	 Battle.addController(new HouseRobotSpawnController());
}
	protected GuiOptions uiOptions;
	
	private RobotPeer[] houseRobots = new RobotPeer[4];
	
	private int numberOfHouseRobots = 2;

    /**
     * {@inheritDoc}
     */
    public String toString() {
        return "House Robot Mode";
    }

    /**
     * {@inheritDoc}
     */
    public String getDescription() {
        return "Up to four house robots will appear in the corners of the game.";
    }
    
    /**
     * Adds up to four House Robots into the battle.
     * @param currentTurn Current turn of the battle. We're only interested in currentTurn = 1
     * @param peers BattlePeers
     * @author Laurence McLean 42373414
     */
    public void addRobots(int currentTurn, BattlePeers peers){
    	if(currentTurn==1) {
    		for(int i = 0; i<numberOfHouseRobots; i++) {
	    		//Ensure to make a new RobotPeer with peer's settings. We need sampleex.MyFirstHouseRobot which
	    		// is the HouseRobot that will operate in HouseRobot mode.
	    		houseRobots[i] = new RobotPeer(peers.getBattle(), peers.getHostManager(),
	    				new RobocodeEngine().getLocalRepository("sampleex.MyFirstHouseRobot")[0], 0,
	    				null, peers.getBattle().getRobotsCount());
	    		peers.addRobot(houseRobots[i]);
	        	houseRobots[i].initializeRound(peers.getRobots(), null);
	        	houseRobots[i].startRound(0, 0);
    		}
    	}
    }
    
    public void endRound(BattlePeers peers) {
    	for(int i = 0; i<numberOfHouseRobots; i++) {
    		peers.removeRobot(houseRobots[i]);
    	}
    }

    /**
     * Sets the starting positions for all robot objects.
     * Original implementation taken from Battle.
     * @param initialPositions String of initial positions. Parsed by
     * the original implementation found in Battle. Can be ignored for
     * custom implementations.
     * @param battleRules Battle rules.
     * @param robotsCount Size of battlingRobotsList
     * @return Two dimensional array of coordinates containing
     * the starting coordinates and heading for each robot.
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
    
    public boolean allowsOneRobot() {
    	return true;
    }

}
