package net.sf.robocode.battle;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.peer.RobotPeer;

/**
 * Callable ability (used by killstreak tracker. Instantiate one with the robot
 * who calls the RadarJammer, and the battle it is in to enable the jam.
 * Blocks the caller from other robot's radars for JAM_TIME amount of time
 **/
public class RobotFreeze {
	private final int FREEZE_TIME = 400;
	
	/**
	 * Constructor for the RadarJammer being called
	 * 
	 * @param robotPeer
	 *            The robot calling the RadarJammer
	 * @param battle
	 *            The battle that robot is part of
	 */
	public RobotFreeze(RobotPeer robotPeer, Battle battle) {
		for (RobotPeer robot : battle.getRobotList()) {
			if (robot == robotPeer) {
				robot.println("KILLSTREAK: Freezing all other robots for " + FREEZE_TIME + " turns" );
				continue;
			} else {
				robot.enableFreeze(FREEZE_TIME);
				robot.println("KILLSTREAK: Robot has been frozen for " + FREEZE_TIME + " turns" );
			}
		}
	}
}