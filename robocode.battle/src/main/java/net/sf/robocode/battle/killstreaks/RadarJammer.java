package net.sf.robocode.battle.killstreaks;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.IKillstreakAbility;
import net.sf.robocode.battle.peer.RobotPeer;

/**
 * Callable ability (used by killstreak tracker. Instantiate one with the robot
 * who calls the RadarJammer, and the battle it is in to enable the jam. Blocks
 * the caller from other robot's radars for JAM_TIME amount of time
 **/
public class RadarJammer implements IKillstreakAbility {
	private static final int JAM_TIME = 400;

	/**
	 * Call the RadarJammer
	 * 
	 * @param robotPeer
	 *            The robot calling the RadarJammer
	 * @param battle
	 *            The battle that robot is part of
	 */
	public void callAbility(RobotPeer robotPeer, Battle battle) {
		robotPeer.enableRadarJammer(JAM_TIME);
		robotPeer.println("KILLSTREAK: Called a RadarJammer!"
				+ " Robot will be unscannable for " + JAM_TIME + " turns");

	}
}