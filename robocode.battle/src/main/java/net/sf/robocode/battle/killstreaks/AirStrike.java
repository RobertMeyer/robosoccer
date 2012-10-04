package net.sf.robocode.battle.killstreaks;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.IKillstreakAbility;
import net.sf.robocode.battle.peer.BulletPeer;
import net.sf.robocode.battle.peer.RobotPeer;

/**
 * Default 5-kill killstreak ability. Spawns a series of bullets that don't
 * effect the caller.
 **/
public class AirStrike implements IKillstreakAbility {

	/**
	 * Call an airstrike
	 * 
	 * @param robotPeer
	 *            The robot calling the airstrike
	 * @param battle
	 *            The battle that robot is part of
	 */
	public void callAbility(RobotPeer robotPeer, Battle battle) {
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