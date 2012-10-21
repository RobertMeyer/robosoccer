package net.sf.robocode.mode;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import net.sf.robocode.battle.BattlePeers;
import net.sf.robocode.battle.BattleResultsTableModel;
import net.sf.robocode.battle.IRenderable;
import net.sf.robocode.battle.RenderString;
import net.sf.robocode.battle.item.BoundingRectangle;
import net.sf.robocode.battle.peer.BallPeer;
import net.sf.robocode.battle.peer.RobotPeer;
import net.sf.robocode.battle.peer.SoccerTeamPeer;
import net.sf.robocode.battle.peer.TeamPeer;
import net.sf.robocode.host.IHostManager;
import net.sf.robocode.repository.IRepositoryManager;
import robocode.BattleResults;
import robocode.control.RobotSpecification;

public class SoccerMode extends ClassicMode implements IMode {
	// This stores the ball(s) in a list for use in updateRobotScans
	private List<RobotPeer> ball;
	private List<RobotPeer> robots;

	/*
	 * This stores the width and height of the playing field, plus the current x
	 * coordinate of the ball bot.
	 */
	public static final int GOALX = 100;
	public static final int GOALY = 236;
	private double fieldWidth;
	private double fieldHeight;
	private Goal scoreTeam;
	private BoundingRectangle goal1;
	private BoundingRectangle goal2;

	/* TeamPeers for the two soccer teams */
	private SoccerTeamPeer team1;
	private SoccerTeamPeer team2;

	private RenderString scoreTeam1;
	private RenderString scoreTeam2;

	private boolean roundOver = false;

	private final String description = "Robocode soccer.";

	private enum Goal {
		TEAM1, TEAM2
	}

	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		return "Soccer Mode";
	}

	/**
	 * {@inheritDoc}
	 */
	public String addModeRobots(String selectedRobots) {
		return selectedRobots + ", robots.theBall*";
	}

	/**
	 * {@inheritDoc}
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double[][] computeInitialPositions(String initialPositions,
			double width, double height, int robotsCount) {
		double[][] initialRobotPositions = null;
		roundOver = false;

		int count = (robotsCount % 2 == 0 ? robotsCount : robotsCount - 1);

		initialRobotPositions = new double[(count + 1)][3];

		fieldHeight = height;
		fieldWidth = width;

		goal1 = new BoundingRectangle(0, (fieldHeight / 2) - (GOALY / 2),
				GOALX, GOALY);
		goal2 = new BoundingRectangle(fieldWidth - GOALX, (fieldHeight / 2)
				- (GOALY / 2), GOALX, GOALY);

		int teamSize = count / 2;

		// Horizontal spacing between columns of robots.
		double xOffset = ((fieldWidth / 2))
				/ (1 + Math.max(1, Math.ceil(teamSize / 3.0)));

		for (int i = 0; i < teamSize; i++) {
			// Team 1 Initial Positions (Left side of field).
			initialRobotPositions[i][0] = (fieldWidth / 2)
					- (((i / 3) + 1) * xOffset);
			initialRobotPositions[i][1] = (0.2 * fieldHeight)
					+ (((0.9 * fieldHeight) / 3) * (i % 3));
			initialRobotPositions[i][2] = (Math.PI / 2.0);

			// Team 2 Initial Positions (Right side of field).
			initialRobotPositions[(i + teamSize)][0] = (fieldWidth / 2)
					+ (((i / 3) + 1) * xOffset);
			initialRobotPositions[(i + teamSize)][1] = (0.2 * fieldHeight)
					+ (((0.9 * fieldHeight) / 3) * (i % 3));
			initialRobotPositions[(i + teamSize)][2] = 3 * (Math.PI / 2.0);
		}

		// Ball starting position..
		initialRobotPositions[count][0] = (fieldWidth / 2);
		initialRobotPositions[count][1] = (fieldHeight / 2);
		initialRobotPositions[count][2] = 0;

		return initialRobotPositions;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createPeers(BattlePeers peers,
			RobotSpecification[] battlingRobotsList, IHostManager hostManager,
			IRepositoryManager repositoryManager) {

		// Duplicate robot hashtables.
		Hashtable<String, Integer> team1Duplicates = new Hashtable<String, Integer>();
		Hashtable<String, Integer> team2Duplicates = new Hashtable<String, Integer>();

		// Team member names.
		List<String> team1Names = new LinkedList<String>();
		List<String> team2Names = new LinkedList<String>();

		int teamSize = peers.getBattle().getRobotsCount() / 2;

		// Counts the number of duplicates for each robot being used.
		for (int i = 0; i < peers.getBattle().getRobotsCount(); i++) {
			RobotSpecification spec = battlingRobotsList[i];
			String name = spec.getName();
			String botName = null;

			if (i < teamSize) {
				// Populate duplicates list and member names list for team 1.
				if (team1Duplicates.contains(name)) {
					int count = team1Duplicates.get(name);

					team1Duplicates.put(name, count == 1 ? 3 : count + 1);
				} else {
					team1Duplicates.put(name, 1);
				}

				botName = name + team1Duplicates.get(name);
				team1Names.add(botName);
			} else {
				// Populate duplicates list and member names list for team 2.
				if (team2Duplicates.contains(name)) {
					int count = team2Duplicates.get(name);

					team2Duplicates.put(name, count == 1 ? 3 : count + 1);
				} else {
					team2Duplicates.put(name, 1);
				}

				botName = name + team2Duplicates.get(name);
				team2Names.add(botName);
			}
		}

		// Create teams 1 and 2.
		team1 = new SoccerTeamPeer("Blue Team", team1Names, 0);
		team2 = new SoccerTeamPeer("Red Team", team2Names, 1);
		TeamPeer ballTeam = new TeamPeer("Ball", null, 2);

		// Create robot peer objects, assign teams and add them to the
		// peer list.
		for (int j = 0; j < peers.getBattle().getRobotsCount(); j++) {
			RobotSpecification spec = battlingRobotsList[j];
			RobotPeer robot = null;

			if (j < teamSize) {
				robot = new RobotPeer(peers.getBattle(), hostManager, spec,
						team1Duplicates.get(spec.getName()), team1, j, null);
			} else {
				robot = new RobotPeer(peers.getBattle(), hostManager, spec,
						team2Duplicates.get(spec.getName()), team2, j, null);
			}

			peers.addRobot(robot);
		}

		peers.addContestant(team1);
		peers.addContestant(team2);

		// Create the ball robot and add it to the appropriate peer lists/team.
		RobotSpecification ballSpec = repositoryManager
				.loadSelectedRobots("soccer.BallBot*")[0];
		BallPeer ball = new BallPeer(peers.getBattle(), hostManager, ballSpec,
				0, ballTeam, peers.getRobots().size());
		this.ball = new ArrayList<RobotPeer>();
		this.ball.add(ball);

		ballTeam.add(ball);
		peers.addContestant(ballTeam);
		peers.addRobot(ball);
		robots = peers.getRobots();
	}

	/**
	 * Perform scan dictates the scanning behaviour of robots. One parameter
	 * List<RobotPeer> is iterated over an performScan called on each robot.
	 * Useful for making some robots invisible to radar. In this mode it makes
	 * the ball the only element visible to radar.
	 */
	@Override
	public void updateRobotScans(List<RobotPeer> robots) {
		// Scan after moved all

		for (RobotPeer robotPeer : getRobotsAtRandom(robots)) {
			// Check to see if ball is in goal
			if (robotPeer.isBall()) {
				if (goal1.intersects(robotPeer.getBoundingBox())) {
					roundOver = true;
					scoreTeam = Goal.TEAM1;
				} else if (goal2.intersects(robotPeer.getBoundingBox())) {
					scoreTeam = Goal.TEAM2;
					roundOver = true;
				}
				robotPeer.performScan(getRobotsAtRandom(robots));
			} else {
				robotPeer.performScan(ball);
			}
		}
	}

	/**
	 * Increments the score for one of either team depending on which team
	 * scored.
	 */
	@Override
	public void scoreRoundPoints() {
		// Which team scored?
		if (scoreTeam == Goal.TEAM1) {
			team1.getStatistics().incrementScore();
			scoreTeam = null;
			scoreTeam1.setText("Blue Team: "
					+ (int) team1.getStatistics().getTotalScore());
		} else if (scoreTeam == Goal.TEAM2) {
			team2.getStatistics().incrementScore();
			scoreTeam = null;
			scoreTeam2.setText("Red Team: "
					+ (int) team2.getStatistics().getTotalScore());
		}
	}

	/**
	 * Checks to see if round is over, if so destroys all other robots except
	 * for the ball bot in order to end round smoothly.
	 */
	@Override
	public boolean isRoundOver(int endTimer, int time) {
		if (roundOver) {
			for (RobotPeer robotPeer : robots) {
				if (!(robotPeer.isBall())) {
					robotPeer.kill();
				}
			}
			roundOver = false;
		}
		return endTimer > 5 * time;
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public List<IRenderable> createRenderables() {
		System.out.println("test");
		List<IRenderable> objs = new ArrayList<IRenderable>();
		scoreTeam1 = new RenderString("score2", "Blue Team: "
				+ (int) team1.getStatistics().getTotalScore());
		scoreTeam1.setTranslate(25, 50);
		scoreTeam1.setColour(Color.WHITE);
		objs.add(scoreTeam1);

		scoreTeam2 = new RenderString("score1", ("Red Team: " + (int) team2
				.getStatistics().getTotalScore()));
		scoreTeam2.setTranslate(fieldWidth - 85, 50);
		scoreTeam2.setColour(Color.WHITE);
		objs.add(scoreTeam2);
		return objs;
	}

	@Override
	public void setGuiOptions() {
		super.uiOptions = new GuiOptions(true, false);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return an array of BattleResults containing the final score of each team
	 *         sorted according to rank.
	 */
	@Override
	public BattleResults[] getFinalResults() {
		List<BattleResults> results = new ArrayList<BattleResults>();
		double team1Score = team1.getStatistics().getTotalScore();
		double team2Score = team2.getStatistics().getTotalScore();
		if (team1Score > team2Score) {
			results.add(team1.getStatistics().getFinalResults());
			results.add(team2.getStatistics().getFinalResults());
		} else {
			results.add(team2.getStatistics().getFinalResults());
			results.add(team1.getStatistics().getFinalResults());
		}
		return results.toArray(new BattleResults[results.size()]);
	}

	@Override
	public BoundingRectangle[] getGoals() {
		BoundingRectangle[] goals = { goal1, goal2 };
		return goals;
	}

	/**
	 * Setup for SoccerMode to just display the rank, the team and the total
	 * score
	 */
	public void setCustomResultsTable() {
		/* BRANDONCW */
		if (resultsTable == null) {
			resultsTable = new BattleResultsTableModel();
		}
		resultsTable.showOverallRank().showTeam("Team Name").showTotalScore();
	}

	/**
	 * {@inheritDoc}
	 */
	public BattleResultsTableModel getCustomResultsTable() {
		return resultsTable;
	}

	@Override
	public boolean allowsOneRobot() {
		return true;
	}

	public void setScoreTeam(String team) {
		if (team.equals("team1")) {
			scoreTeam = Goal.TEAM1;
		}
		if (team.equals("team2")) {
			scoreTeam = Goal.TEAM2;
		}
	}
}
