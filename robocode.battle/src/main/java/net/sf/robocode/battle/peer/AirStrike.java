package net.sf.robocode.battle.peer;

import robocode.BattleRules;
import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.peer.BulletPeer;

public class AirStrike {
	
	public AirStrike(RobotPeer robotPeer, Battle battle) {
		for (int i = 0; i < 20; i++) {
			System.out.println("spawning AS bullet");
			BulletPeer bullet = new BulletPeer(robotPeer, battle.getBattleRules(), i);
			bullet.setX(100);
			bullet.setY(100);
			//bullet.setVictim(teammate);
			bullet.setPower(4);
			battle.addBullet(bullet);
		}

	}
}