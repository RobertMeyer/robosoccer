package net.sf.robocode.battle.killstreaks;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.IKillstreakAbility;
import net.sf.robocode.battle.peer.RobotPeer;

/**
 * Default 3-kill killstreak ability. Makes the calling robot unscannable for
 * JAM_TIME amount of turns.
 **/
public class RadarJammer implements IKillstreakAbility {
	private static final int JAM_TIME = 800;

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