package net.sf.robocode.battle;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.peer.RobotPeer;

/**
 * Callable ability (used by killstreak tracker. Instantiate one with the robot
 * who calls the RadarJammer, and the battle it is in to enable the jam. Blocks
 * the caller from other robot's radars for JAM_TIME amount of time
 **/
public class SuperTank {
	private final int SUPER_TIME = 1000;

	/**
	 * Constructor for the RadarJammer being called
	 * 
	 * @param robotPeer
	 *            The robot calling the RadarJammer
	 * @param battle
	 *            The battle that robot is part of
	 */
	public SuperTank(RobotPeer robotPeer, Battle battle) {
		robotPeer.enableSuperTank(SUPER_TIME);
		robotPeer.println("KILLSTREAK: Called a SuperTank!"
				+ " Robot will be an indestructible killing machine for "
				+ SUPER_TIME + " turns");
	}
}