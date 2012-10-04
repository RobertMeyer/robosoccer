package net.sf.robocode.battle;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.peer.RobotPeer;

/**
 * Callable ability (used by killstreak tracker). Turns the calling robot into a
 * "Super Tank" for SUPER_TIME amount of turns. Specifically, the calling robot
 * will have no gun heat, and will lock energy at 2 * starting energy (resulting
 * in a very overpowered weapon).
 **/
public class SuperTank {
	private final int SUPER_TIME = 1000;

	/**
	 * Constructor for the SuperRobot being called
	 * 
	 * @param robotPeer
	 *            The robot calling the SuperRobot
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