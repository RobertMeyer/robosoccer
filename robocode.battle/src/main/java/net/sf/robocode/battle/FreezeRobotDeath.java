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
	
	public void freezeEverything(List<RobotPeer> robots){
		if(deadRobot.isFreezeRobot()){
			for(RobotPeer robot: robots){
				if(!robot.equals(killer) && !robot.equals(deadRobot))
					robot.makeFrozen(robot, turns);
			}
		}
	}
}

