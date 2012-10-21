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
import net.sf.robocode.core.ContainerBase;
import net.sf.robocode.repository.IRepositoryManagerBase;
import robocode.BattleRules;
import robocode.control.RandomFactory;
import robocode.control.RobotSpecification;

/**
 * 
 * @author House Robot Group
 * @author Laurence McLean (42373414)
 * 
 * A mode where up to 4 House Robots will spawn in the corners of the map.
 */
public class HouseRobotMode extends ClassicMode {
	static {
		/**
		 * Need to make sure that when this mode is in operation, we add this
		 * Controller to the Battle.
		 * @see net.sf.robocode.battle.Battle#addController()
		 */
		Battle.addController(new HouseRobotSpawnController());
	}
	
	/**
	 * Need the repository to be able to get the HouseRobot.
	 */
	final IRepositoryManagerBase repository = ContainerBase.getComponent(
			IRepositoryManagerBase.class);
	
	/**
	 * Store the RobotPeers of the house robots.
	 */
	private RobotPeer[] houseRobots = new RobotPeer[4];
	
	/**
	 * Since the number in each round is random, we need to know how many
	 * HouseRobots we have.
	 */
	private int numberOfHouseRobots;
	
	/**
	 * Need to know whether we've gotten rid of all of the robots manually
	 * in this round or not, so that we don't try to access something later
	 * that doesn't exist anymore.
	 */
	private boolean alreadyRemoved;

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "House Robot Mode";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return "Up to four house robots will appear in the corners " +
        		"of the game.";
    }
    
    /**
     * Adds up to four House Robots into the battle on the first turn.
     * @param currentTurn Current turn of the battle.
     * @param peers BattlePeers
     */
    @Override
    public void addRobots(int currentTurn, BattlePeers peers){
    	if(currentTurn==1) {
    		final Random random = RandomFactory.getRandom();
    		numberOfHouseRobots = random.nextInt(4) + 1;
    		alreadyRemoved = false;
    		for(int i = 0; i<numberOfHouseRobots; i++) {
	    		//Ensure to make a new RobotPeer with peer's settings.
    			// We need sampleex.MyFirstHouseRobot which is the HouseRobot
	    		// that will operate in HouseRobot mode.
    			RobotSpecification[] specs = repository.loadSelectedRobots(
    					"sampleex.House_Robot");
	    		houseRobots[i] = new RobotPeer(peers.getBattle(),
	    				peers.getHostManager(), specs[0], 0, null,
	    				peers.getBattle().getRobotsCount(), null);
	    		peers.addRobot(houseRobots[i]);
	        	houseRobots[i].initializeRound(peers.getRobots(), null);
	        	houseRobots[i].startRound(0, 0);
    		}
    	}
    	
    	int numHouseRobotsStillAlive = 0;
    	
    	//We need to check to see if there's only HouseRobots left. If there
    	// are, we'll remove them all to end the round.
    	if(!alreadyRemoved) {
    		//Count the number of HouseRobots
    		for(int i = 0; i<numberOfHouseRobots; i++) {
    			if(houseRobots[i].isAlive()) {
    				numHouseRobotsStillAlive++;
    			}
    		}
    		
    		//We only have HouseRobots left in the round, so we should end
    		// the round.
	    	if(peers.getBattle().getActiveRobots()==numHouseRobotsStillAlive+1) {
	    		endRound(peers);
	    	}
    	}
    }
    
    /**
     * At the end of the round, we need to remove the remaining HouseRobots.
     * So we'll do that
     * @param peers BattlePeers
     */
    @Override
    public void endRound(BattlePeers peers) {
    	if(!alreadyRemoved) {
    		for(int i = 0; i<numberOfHouseRobots; i++) {
	    		peers.removeRobot(houseRobots[i]);
	    		peers.getBattle().getSpawnController().resetSpawnLocation(houseRobots[i], peers.getBattle());
	    		houseRobots[i] = null;
	    	}
    		alreadyRemoved = true;
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

            x = RobotPeer.WIDTH + random.nextDouble() * 
            		(battleRules.getBattlefieldWidth() - 2 * RobotPeer.WIDTH);
            y = RobotPeer.HEIGHT + random.nextDouble() * 
            		(battleRules.getBattlefieldHeight() - 2 * RobotPeer.HEIGHT);
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
                        y = Double.parseDouble(coords[1].replaceAll(
                        		"[\\D]", ""));
                    } catch (NumberFormatException e) {
                    }

                    if (len >= 3) {
                        // noinspection EmptyCatchBlock
                        try {
                            heading = Math.toRadians(Double.parseDouble(
                            		coords[2].replaceAll("[\\D]", "")));
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

}
