package net.sf.robocode.mode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Hashtable;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.BattlePeers;
import net.sf.robocode.battle.BattleResultsTableModel;
import net.sf.robocode.battle.IRenderable;
import robocode.BattleResults;
import robocode.BattleRules;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JPanel;

import net.sf.robocode.battle.item.BoundingRectangle;
import net.sf.robocode.battle.item.ItemDrop;
import net.sf.robocode.battle.peer.*;
import net.sf.robocode.host.IHostManager;
import net.sf.robocode.repository.IRepositoryManager;
import robocode.control.RandomFactory;
import robocode.control.RobotSpecification;

/**
 *
 * Default implementation of the IMode interface. This class models
 * the default behaviour of a Robocode game.
 *
 */
public class ClassicMode implements IMode {

    protected GuiOptions uiOptions;
    /* Results table */
    protected BattleResultsTableModel resultsTable;

    /* Overall Score variables */
    protected RobotPeer rPeer;
    protected int numRobots;
    protected RobotStatistics robotStatistics;

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

    public int setNumObstacles(BattleRules rules) {
        return 0;
    }

    public int setCellWidth(BattleRules rules) {
        return 0;
    }

    public int setCellHeight(BattleRules rules) {
        return 0;
    }

    public int setWallWidth(BattleRules rules) {
        return 0;
    }

    public int setWallHeight(BattleRules rules) {
        return 0;
    }

    public boolean dWallSetting(BattleRules battleRules) {
        return false;
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
    public void updateRenderables(List<IRenderable> renderables) {

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
     * This function should create new CustomObjects which should
     * be stored in a ArrayList<CustomObject> and returned.
     *
     * The returned list will represent all the custom objects in
     * the scene to be rendered.
     *
     * example:-
     *      // Create ArrayList
     *      List<CustomObject> objs = new ArrayList<CustomObject>();
     *      // Create a new object at (100,100) which will render a flag
     *      CustomObject obj = new CustomObject("flag",
     *      "/net/sf/robocode/ui/images/flag.png", 100, 100);
     *      // Set Alpha blending to fade 50%
     *      obj.setAlpha(0.5f);
     *      // Add object to ArrayList
     *      objs.add(obj);
     *      return objs;
     *
     * @return a ArrayList<CustomObjects> which are added to the scene.
     */
    public List<IRenderable> createRenderables() {
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
	 * @param width Battlefield width.
	 * @param height Battlefield height.
	 * @param robotsCount Size of battlingRobotsList
	 * @return Two dimensional array of coordinates containing
	 * the starting coordinates and heading for each robot.
	 */
	public double[][] computeInitialPositions(String initialPositions,
			double width, double height, int robotsCount) {
        double[][] initialRobotPositions = null;
        this.numRobots = robotsCount;

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

            x = RobotPeer.WIDTH + random.nextDouble() * (width - 2 * RobotPeer.WIDTH);
            y = RobotPeer.HEIGHT + random.nextDouble() * (height - 2 * RobotPeer.HEIGHT);
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

    /**
     * Determines if the bullet being dealt with should ricochet
     * @param power Power of current bullet being dealt with
     * @param minBulletPower Minimum bullet power from the battle rules
     * @param ricochetValue User provided variable that power is divided by
     * each ricochet
     * @return true/false if a ricochet should occur
     */
    public boolean shouldRicochet(double power, double minBulletPower,
                                  double ricochetValue) {
        return false;
    }

    /**
     * Checks user input for Ricochet is acceptable
     * @param rules Current battle rules
     * @return ricochet value as provided by user or 1 if value provided < 1
     */
    public double modifyRicochet(BattleRules rules) {
        return 1;
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

    public void createPeers(BattlePeers peers, RobotSpecification[] battlingRobotsList, IHostManager hostManager,
                            IRepositoryManager repositoryManager) {
        peers.createPeers(battlingRobotsList);
    }

    /**
     * Initialises the GuiOptions object with the visibility options
     * applicable to this mode.
     */
    public void setGuiOptions() {
        uiOptions = new GuiOptions(true, true);
    }

    /**
     * Getter method for the GuiOptions object associated with this
     * mode.
     * @return GuiOptions object associated with this mode.
     */
    public GuiOptions getGuiOptions() {
        return uiOptions;
    }

    /**
     * Called after the death of a robot that is about to respawn
     */
    public void onRespawnDeath(RobotPeer robot) {

    }

    @Override
    public BattleResults[] getFinalResults() {
        return null;
    }

    public void addRobots(int currentTurn, BattlePeers peers){
        // do nothing
    }
	
	/** What to do at the end of a mode's specific round.
	 * @author Laurence McLean 42373414
	 * @param peers See BattlePeers.class
	 */
	public void endRound(BattlePeers peers) {
		//Do nothing by default.
	}

    /**
     *
     * @param standard the original radar scan values
     * @return the original radar scan values
     */
    public double modifyVision(double standard) {
        return standard;
    }

    /**
     *
     * @param standard the original radar scan values
     * @param rules the type of rules to command the robot
     * @return the standard original radar scan values
     */
    public double modifyVision(double standard, BattleRules rules)
    {
        return modifyVision(standard);
    }

    /**
     * Get the customised BattleResultsTableModel
     * @return Customised BattleResultsTableModel
     */
    @Override
    public BattleResultsTableModel getCustomResultsTable() {
        if (resultsTable == null) {
            this.setCustomResultsTable();
        }

        return resultsTable;
    }

    /**
     * Setup a default BattleResultsTableModel
     */
    public void setCustomResultsTable() {
        if (resultsTable == null) {
            resultsTable = new BattleResultsTableModel();
        }

        /* Set it to show the default scores */
        resultsTable.showOverallRank()
        			.showRobotName()
        			.showTotalScore()
        			.showSurvival()
        			.showSurvivalBonus()
        			.showBulletDamage()
        			.showBulletBonus()
        			.showRamDamage()
        			.showRamBonus()
        			.showFirsts()
        			.showSeconds()
        			.showThirds();
    }

    /**
     * Setup so the default overall score is affected by all scores
     * @param robotStatistics
     * @return Double representing the scores
     */
    public Double getCustomOverallScore(RobotStatistics score) {
            /*
        Double scores = 0.0;
        scores += scores.showBulletDamageScore();
        scores += scores.showBulletKillBonus();
        scores += scores.showRammingDamageScore();
        scores += scores.showRammingKillBonus();
        scores += scores.showBulletKillBonus();
                scores += scores.showSurvivalScore();
        scores += scores.showLastSurvivorBonus(); */
          return   score.getTotalSurvivalScore() + score.getTotalLastSurvivorBonus()
                    + score.getTotalBulletDamageScore() + score.getTotalBulletKillBonus() + score.getTotalRammingDamageScore()
                    + score.getTotalRammingKillBonus();
        //return score;
    }

    @Override
    public boolean allowsOneRobot() {
        return false;
    }

    public void robotKill(RobotPeer owner, RobotPeer otherRobot) {
        // do nothing
    }

	/**
	 * Returns null in classic mode. See SoccerMode for details.
	 * @return null
	 */
	public BoundingRectangle[] getGoals() {
		return null;
	}

	public void scoreRoundPoints() {
	}

	/**
	 * Modify the starting energy.
	 */
	public double modifyStartingEnergy(RobotPeer robotPeer, double startingEnergy) {
		return startingEnergy;
	}
}
