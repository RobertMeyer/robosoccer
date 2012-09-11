package net.sf.robocode.battle;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.peer.BulletPeer;
import net.sf.robocode.battle.peer.RobotPeer;

public class AirStrike {

	public AirStrike(RobotPeer robotPeer, Battle battle) {
		int battleHeight = battle.getBattleRules().getBattlefieldHeight();
		for (int i = 0; i < battleHeight; i += 30) {
			BulletPeer bullet = new BulletPeer(robotPeer,
					battle.getBattleRules(), i);
			bullet.setPower(0.5);
			bullet.setX(0);
			bullet.setY(i);
			bullet.setPower(2);
			bullet.setHeading(1.57);
			battle.addBullet(bullet);
		}
		robotPeer.println("SYSTEM: Called an Airstrike!");

	}
}