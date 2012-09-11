package net.sf.robocode.mode;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.peer.BallPeer;
import net.sf.robocode.battle.peer.ContestantPeer;
import net.sf.robocode.battle.peer.RobotPeer;
import net.sf.robocode.battle.peer.TeamPeer;
import net.sf.robocode.host.IHostManager;
import net.sf.robocode.repository.IRepositoryManager;
import net.sf.robocode.repository.IRobotRepositoryItem;
import net.sf.robocode.security.HiddenAccess;
import robocode.BattleRules;
import robocode.control.RandomFactory;
import robocode.control.RobotSpecification;

public class SoccerMode extends ClassicMode implements IMode {
	
	private final String description = "Robocode soccer.";
	
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
			BattleRules battleRules, int robotsCount) {
		double[][] initialRobotPositions = null;
		
		int count = (robotsCount % 2 == 0 ? robotsCount : robotsCount - 1);
		
		initialRobotPositions = new double[(count + 1)][3];
 		
 		double height = battleRules.getBattlefieldHeight();
 		double width = battleRules.getBattlefieldWidth();
 		
 		int teamSize = count / 2;
 		
 		// Horizontal spacing between columns of robots.
 		double xOffset = ((width / 2)) / (1 + Math.max(1, Math.ceil(teamSize / 3.0)));

 		for(int i = 0; i < teamSize; i++) {
 			// Team 1 Initial Positions (Left side of field).
 			initialRobotPositions[i][0] = (width / 2) - (((i/3) + 1) * xOffset);
 			initialRobotPositions[i][1] = (0.2 * height) + (((0.9 * height) / 3) * (i % 3));
 			initialRobotPositions[i][2] = (Math.PI / 2.0);
 			
 			// Team 2 Initial Positions (Right side of field).
 			initialRobotPositions[(i + teamSize)][0] = (width / 2) + (((i / 3) + 1) * xOffset);
 			initialRobotPositions[(i + teamSize)][1] = (0.2 * height) + (((0.9 * height) / 3) * (i % 3));
 			initialRobotPositions[(i + teamSize)][2] = 3 * (Math.PI / 2.0);
 		}
 		
 		// Ball starting position..
 		initialRobotPositions[count][0] = (width / 2);
 		initialRobotPositions[count][1] = (height / 2);
 		initialRobotPositions[count][2] = 0;
 		
 		return initialRobotPositions;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createPeers(Battle battle,
			RobotSpecification[] battlingRobotsList, IHostManager hostManager,
			List<RobotPeer> robots, List<ContestantPeer> contestants,
			IRepositoryManager repositoryManager) {
		Hashtable<String, Integer> duplicates = new Hashtable<String, Integer>();
		int teamSize = battle.getRobotsCount() / 2;
		
		
		
		// Counts the number of duplicates for each robot being used.
		for(int i = 0; i < battle.getRobotsCount(); i++) {
			RobotSpecification spec = battlingRobotsList[i];
			String name = spec.getName();
			
			if(duplicates.contains(name)) {
				duplicates.put(name, duplicates.get(name) + 1);
			} else {
				duplicates.put(name, 1);
			}
		}
		
		// Create teams 1 and 2.
		TeamPeer team1 = new TeamPeer("Team 1", null, 0);
		TeamPeer team2 = new TeamPeer("Team 2", null, 1);
		TeamPeer ballTeam = new TeamPeer("Ball", null, 2);
		
		contestants.add(team1);
		contestants.add(team2);
		
		for(int j = 0; j < battle.getRobotsCount(); j++) {
			RobotSpecification spec = battlingRobotsList[j];
			RobotPeer robot = null;
			
			if(j < teamSize) {
				robot = new RobotPeer(battle, hostManager, spec, 
						duplicates.get(spec.getName()), team1, j);
			} else {
				robot = new RobotPeer(battle, hostManager, spec, 
						duplicates.get(spec.getName()), team2, j);
			}
			
			robots.add(robot);
		}
		
		// Create the ball robot and add it to the appropriate peer lists/team.
		RobotSpecification ballSpec = 
				repositoryManager.loadSelectedRobots("robots.BallBot*")[0];
		BallPeer ball = new BallPeer(battle, hostManager, ballSpec, 0, ballTeam, robots.size());

		ballTeam.add(ball);
		contestants.add(ballTeam);
		robots.add(ball);
	}
}
