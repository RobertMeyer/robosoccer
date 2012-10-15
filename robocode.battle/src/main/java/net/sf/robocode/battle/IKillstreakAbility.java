/**
 * 
 */
package net.sf.robocode.battle;

import net.sf.robocode.battle.peer.RobotPeer;

/**
 * A Killstreak ability. Must implement the callAbility method. This method
 * should handle any effects caused by the killstreak ability. These killstreaks
 * can be added to the KillstreakTracker attached to the battle
 */
public interface IKillstreakAbility {
	/**
	 * Call the killstreak ability
	 * 
	 * @param caller
	 *            the robot who calls the airstrike
	 * @param battle
	 *            the battle the robot is a part of
	 */
	public void callAbility(RobotPeer caller, Battle battle);
}
