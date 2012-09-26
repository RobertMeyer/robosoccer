package net.sf.robocode.battle;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.peer.BulletPeer;
import net.sf.robocode.battle.peer.RobotPeer;

/**
 * Callable ability (used by killstreak tracker. Instantiate one with the robot
 * who calls the airstrike, and the battle it is in to call the airstrike.
 * Spawns a series of bullets that don't effect the caller
 **/
public class RadarJammer {
	private final int JAM_TIME = 400;
	
	/**
	 * Constructor for the airstrike being called
	 * 
	 * @param robotPeer
	 *            The robot calling the airstrike
	 * @param battle
	 *            The battle that robot is part of
	 */
	public RadarJammer(RobotPeer robotPeer, Battle battle) {
		robotPeer.enableRadarJammer(JAM_TIME);
		robotPeer.println("SYSTEM: Called a RadarJammer!");
	}
}