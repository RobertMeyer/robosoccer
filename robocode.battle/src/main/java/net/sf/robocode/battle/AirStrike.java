package net.sf.robocode.battle;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.peer.BulletPeer;
import net.sf.robocode.battle.peer.RobotPeer;

/**
 * Callable ability (used by killstreak tracker. Instantiate one with the robot
 * who calls the airstrike, and the battle it is in to call the airstrike.
 * Spawns a series of bullets that don't effect the caller
 **/
public class AirStrike {
	/**
	 * Constructor for the airstrike being called
	 * 
	 * @param robotPeer
	 *            The robot calling the airstrike
	 * @param battle
	 *            The battle that robot is part of
	 */
	public AirStrike(RobotPeer robotPeer, Battle battle) {
		int battleHeight = battle.getBattleRules().getBattlefieldHeight();
		for (int i = 0; i < battleHeight; i += 15) {
			BulletPeer bullet = new BulletPeer(robotPeer,
					battle.getBattleRules(), i);
			bullet.setPower(0.5);
			bullet.setX(0);
			bullet.setY(i);
			bullet.setPower(2);
			bullet.setHeading(1.57);
			battle.addBullet(bullet);
		}
		robotPeer.println("KILLSTREAK: Called an Airstrike!");

	}
}