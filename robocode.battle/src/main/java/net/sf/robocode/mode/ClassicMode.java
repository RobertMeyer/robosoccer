package net.sf.robocode.mode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Hashtable;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.BattlePeers;
import net.sf.robocode.battle.CustomObject;
import robocode.BattleRules;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JPanel;

import net.sf.robocode.battle.item.ItemDrop;
import net.sf.robocode.battle.peer.*;
import net.sf.robocode.host.IHostManager;
import net.sf.robocode.repository.IRepositoryManager;
import net.sf.robocode.repository.IRobotRepositoryItem;
import net.sf.robocode.security.HiddenAccess;
import robocode.control.RandomFactory;
import robocode.control.RobotSpecification;

/**
 *
 * Default implementation of the IMode interface. This class models
 * the default behaviour of a Robocode game.
 *
 */
public class ClassicMode implements IMode {
	
	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		return "Classic Mode";
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String getDescription() {
		return "Original robocode mode.";
	}
	
	public JPanel getRulesPanel() {
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Hashtable<String, Object> getRulesPanelValues() {
		return null;
	}
	
	// ----- Mode-specific methods below this line ------
	
	/**
	 * {@inheritDoc}
	 */
	public double modifyVelocity(double velocityIncrement, BattleRules rules) {
		return modifyVelocity(velocityIncrement);
	}
	
	public double modifyVelocity(double velocityIncrement) {
		return velocityIncrement;
	}
	
	/**
	 * Returns a list of ItemDrop's to 
	 * spawn in the beginning of the round
	 * @return List of items
	 */
	public List<? extends ItemDrop> getItems() {
		return new ArrayList<ItemDrop>();
	}
	
	/**
	 * Create a list of ItemDrop's to
	 * spawn in the beginning of the round
	 * @param battle The Battle to add the items to
	 */
	public void setItems(Battle battle) {
		/* No items needed for Classic Mode */
	}

	/**
	 * Increments the score for the mode per turn
	 */
	public void scoreTurnPoints() {
		/* ClassicMode does not need a score method, optional for overriding */
	}

	
	/**
	 * {@inheritDoc}
	 */
	public boolean respawnsOn() {
		return false;
	}
	
	/**
	 * Override me if you wish to use the CustomObjectAPI.
	 * 
	 * This function will get called once a frame, you can perform
	 * functions like moving the image around the battle, changing
	 * scale, changing alpha level, so on.
	 * 
	 * Loop over the given ArrayList of objects and perform logic
	 * on them. To find an object your after look at getName() function.
	 * 
	 * @param customObject - an ArrayList of all customObjects
	 */
	public void updateCustomObjects(List<CustomObject> customObject) {
		
	}
	
	/**
	 * Override me if you wish to use the CustomObjectAPI.
	 * 
	 * This function should create new CustomObjects which should
	 * be stored in a ArrayList<CustomObject> and returned.
	 * 
	 * The returned list will represent all the custom objects in
	 * the scene to be rendered.
	 * 
	 * example:- 
	 * 		// Create ArrayList
	 * 		List<CustomObject> objs = new ArrayList<CustomObject>(); 
	 * 		// Create a new object at (100,100) which will render a flag
	 *		CustomObject obj = new CustomObject("flag", 
	 *		"/net/sf/robocode/ui/images/flag.png", 100, 100);
	 *		// Set Alpha blending to fade 50%
	 *		obj.setAlpha(0.5f);
	 *		// Add object to ArrayList
	 *		objs.add(obj);
	 *		return objs;
	 * 
	 * @return a ArrayList<CustomObjects> which are added to the scene.
	 */
	public List<CustomObject> createCustomObjects() {
		return null;
	}
	
	@Override
	public String addModeRobots(String selectedRobots) {
		// Don't need to add any extra robots for classic mode
		return selectedRobots;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int turnLimit() {
		return 5*30*60; // 9000 turns is the default
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
			BattleRules battleRules, int robotsCount) {
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
	
	/**
	 * Perform scan dictates the scanning behaviour of robots. One parameter
	 * List<RobotPeer> is iterated over an performScan called on each robot.
	 * Useful for making some robots invisible to radar.
	 */
	public void updateRobotScans(List<RobotPeer> robots) {
		// Scan after moved all
        for (RobotPeer robotPeer : getRobotsAtRandom(robots)) {
            robotPeer.performScan(getRobotsAtRandom(robots));
        }
	}
	
	public boolean isRoundOver(int endTimer, int time) {
		return (endTimer > 5 * time);
	}

	public boolean shouldRicochet(double power, double minBulletPower) {
		return false;
	}

	 /**
     * Returns a list of all robots in random order. This method is used to gain fair play in Robocode,
     * so that a robot placed before another robot in the list will not gain any benefit when the game
     * checks if a robot has won, is dead, etc.
     * This method was introduced as two equal robots like sample.RamFire got different scores even
     * though the code was exactly the same.
     *
     * @return a list of robot peers.
     */
    protected List<RobotPeer> getRobotsAtRandom(List<RobotPeer> robots) {
        List<RobotPeer> shuffledList = new ArrayList<RobotPeer>(robots);

        Collections.shuffle(shuffledList, RandomFactory.getRandom());
        return shuffledList;
    }

	@Override
	public void setItems() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void scorePoints() {
		// TODO Auto-generated method stub
	}

	public void createPeers(BattlePeers peers, RobotSpecification[] battlingRobotsList, IHostManager hostManager,
			IRepositoryManager repositoryManager) {
		peers.createPeers(battlingRobotsList);
	}
}
