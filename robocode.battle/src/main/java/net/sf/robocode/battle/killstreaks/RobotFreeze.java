package net.sf.robocode.battle.killstreaks;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.IKillstreakAbility;
import net.sf.robocode.battle.peer.RobotPeer;

/**
 * Default 7-kill killstreak ability. Added to KillstreakTracker when it is
 * instantiated. Freeze all other robots for FREEZE_TIME amount of turns.
 **/
public class RobotFreeze implements IKillstreakAbility {
	private static final int FREEZE_TIME = 800;

	/**
	 * Call the RobotFreeze
	 * 
	 * @param robotPeer
	 *            The robot calling the RobotFreeze
	 * @param battle
	 *            The battle that robot is part of
	 */
	public void callAbility(RobotPeer robotPeer, Battle battle) {
		for (RobotPeer robot : battle.getRobotList()) {
			if (robot.equals(robotPeer)) {
				robot.println("KILLSTREAK: Freezing all other robots for "
						+ FREEZE_TIME + " turns");
				continue;
			} else {
				robot.enableKsFreeze(FREEZE_TIME);
				robot.println("KILLSTREAK: Robot has been frozen for "
						+ FREEZE_TIME + " turns");
			}
		}
	}
}