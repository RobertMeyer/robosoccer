package net.sf.robocode.mode;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.BattlePeers;
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
import robocode.BattleRules;
import robocode.control.RobotSpecification;
import robocode.control.snapshot.RenderableType;

public class SoccerMode extends ClassicMode implements IMode {	
	// This stores the ball(s) in a list for use in updateRobotScans
	private List<RobotPeer> ball;
	private List<RobotPeer> robots;
	
	/*This stores the width and height of the playing field, plus the current
	 * x coordinate of the ball bot.
	 */
	private final int GOALX = 80;
	private final int GOALY = 236;
	private double fieldWidth;
	private double fieldHeight;
	private Goal scoreTeam;
	private BoundingRectangle goal1;
	private BoundingRectangle goal2;
	
	/*TeamPeers for the two soccer teams*/
	private SoccerTeamPeer team1;
	private SoccerTeamPeer team2;
	
	private RenderString scoreTeam1;
	private RenderString scoreTeam2;
	
	private boolean roundOver = false;
	
	private final String description = "Robocode soccer.";
	
	private enum Goal {
		TEAM1,
		TEAM2
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
	 *{@inheritDoc}
	 */
	@Override
	public double[][] computeInitialPositions(String initialPositions,
			BattleRules battleRules, Battle battle, int robotsCount) {
		double[][] initialRobotPositions = null;
		roundOver = false;
		
		int count = (robotsCount % 2 == 0 ? robotsCount : robotsCount - 1);
		
		initialRobotPositions = new double[(count + 1)][3];
 		
 		fieldHeight = battleRules.getBattlefieldHeight();
 		fieldWidth = battleRules.getBattlefieldWidth();
 		
 		goal1 = new BoundingRectangle(0, (fieldHeight/2) - (GOALY/2), GOALX, GOALY);
 		goal2 = new BoundingRectangle(fieldWidth - GOALX, (fieldHeight/2) - (GOALY/2), GOALX, GOALY);
 		
 		int teamSize = count / 2;
 		
 		// Horizontal spacing between columns of robots.
 		double xOffset = ((fieldWidth / 2)) / (1 + Math.max(1, Math.ceil(teamSize / 3.0)));

 		for(int i = 0; i < teamSize; i++) {
 			// Team 1 Initial Positions (Left side of field).
 			initialRobotPositions[i][0] = (fieldWidth / 2) - (((i/3) + 1) * xOffset);
 			initialRobotPositions[i][1] = (0.2 * fieldHeight) + (((0.9 * fieldHeight) / 3) * (i % 3));
 			initialRobotPositions[i][2] = (Math.PI / 2.0);
 			
 			// Team 2 Initial Positions (Right side of field).
 			initialRobotPositions[(i + teamSize)][0] = (fieldWidth / 2) + (((i / 3) + 1) * xOffset);
 			initialRobotPositions[(i + teamSize)][1] = (0.2 * fieldHeight) + (((0.9 * fieldHeight) / 3) * (i % 3));
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
		Hashtable<String, Integer> duplicates = new Hashtable<String, Integer>();
		int teamSize = peers.getBattle().getRobotsCount() / 2;
		// Counts the number of duplicates for each robot being used.
		for(int i = 0; i < peers.getBattle().getRobotsCount(); i++) {
			RobotSpecification spec = battlingRobotsList[i];
			String name = spec.getName();
			
			if(duplicates.contains(name)) {
				duplicates.put(name, duplicates.get(name) + 1);
			} else {
				duplicates.put(name, 1);
			}
		}
		
		// Create teams 1 and 2.
		team1 = new SoccerTeamPeer("Team 1", null, 0);
		team2 = new SoccerTeamPeer("Team 2", null, 1);
		TeamPeer ballTeam = new TeamPeer("Ball", null, 2);
		
		peers.addContestant(team1);
		peers.addContestant(team2);
		
		for(int j = 0; j < peers.getBattle().getRobotsCount(); j++) {
			RobotSpecification spec = battlingRobotsList[j];
			RobotPeer robot = null;
			
			if(j < teamSize) {
				robot = new RobotPeer(peers.getBattle(), hostManager, spec, 
						duplicates.get(spec.getName()), team1, j);
			} else {
				robot = new RobotPeer(peers.getBattle(), hostManager, spec, 
						duplicates.get(spec.getName()), team2, j);
			}
			
			peers.addRobot(robot);
		}
		
		// Create the ball robot and add it to the appropriate peer lists/team.
		RobotSpecification ballSpec = 
				repositoryManager.loadSelectedRobots("soccer.BallBot*")[0];
		BallPeer ball = new BallPeer(peers.getBattle(), hostManager, ballSpec, 0, ballTeam, peers.getRobots().size());
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
	 * Useful for making some robots invisible to radar.
	 * In this mode it makes the ball the only element visible to radar.
	 */
	@Override
	public void updateRobotScans(List<RobotPeer> robots) {
		// Scan after moved all
		
        for (RobotPeer robotPeer : getRobotsAtRandom(robots)) {
        	// Check to see if ball is in goal
        	if (robotPeer.isBall() && robotPeer.isAlive()) {
        		if (goal1.intersects(robotPeer.getBoundingBox())) {
        			roundOver = true;
        			scoreTeam = Goal.TEAM1;
        			robotPeer.kill();
        		} else if (goal2.intersects(robotPeer.getBoundingBox())) {
        			scoreTeam = Goal.TEAM2;
        			roundOver = true;
        			robotPeer.kill();
        		}
        	}
            robotPeer.performScan(ball);
        }
	}
	
	@Override
	public void scoreTurnPoints() {
		// Which team scored?
		if (scoreTeam == Goal.TEAM1 && !roundOver) {
			team1.getStatistics().incrementScore();
			scoreTeam1.setText("Team 1\n" + 
						(int)team1.getStatistics().getTotalScore());
			scoreTeam = null;
		} else if(scoreTeam == Goal.TEAM2 && !roundOver) {
			team2.getStatistics().incrementScore();
			scoreTeam2.setText("Team 2\n         " + 
					(int)team2.getStatistics().getTotalScore());
			scoreTeam = null;
		}
	}
	
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
		return endTimer > 5*time;
	}
	
	@Override
	public List<IRenderable> createRenderables() {
		List<IRenderable> objs = new ArrayList<IRenderable>(); 
		scoreTeam1 = new RenderString("score2", "Team 1\n" + 
				(int)team1.getStatistics().getTotalScore());
		scoreTeam1.setTranslate(25, 50);
		scoreTeam1.setColour(Color.WHITE);
		objs.add(scoreTeam1);
		
		scoreTeam2 = new RenderString("score1", ("Team 2\n         " + 
				(int)team2.getStatistics().getTotalScore()));
		scoreTeam2.setTranslate(fieldWidth - 70, 50);
		scoreTeam2.setColour(Color.WHITE);
		objs.add(scoreTeam2);
		return objs;
	}
	
	@Override
	public void setGuiOptions() {
		super.uiOptions = new GuiOptions(true, false);
	}
	
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
}
