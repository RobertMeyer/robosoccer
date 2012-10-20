package net.sf.robocode.battle;

import java.util.List;
import net.sf.robocode.battle.peer.RobotPeer;

public class FreezeRobotDeath {
	private RobotPeer deadRobot;
	private RobotPeer killer;
	private int turns = 120;
	
	public FreezeRobotDeath(RobotPeer deadRobot, RobotPeer killer){
		this.deadRobot = deadRobot;
		this.killer = killer;
	}
	
	/**
	 * Freeze all the robots in the game, except for the the robot that killed the freezeRobot 
	 * and other freezeRobots
	 * @param robots: list of all robots in the game
	 */
	public void freezeEverything(List<RobotPeer> robots){
		if(deadRobot.isFreezeRobot()){
			for(RobotPeer robot: robots){
				// Don't freeze the robot that did the killing
				// Don't freeze any other robots.
				if(!robot.equals(killer) && !robot.isFreezeRobot())
					robot.makeFrozen(robot, turns);
			}
		}
	}
}

