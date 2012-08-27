package net.sf.robocode.battle;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.peer.BulletPeer;
import net.sf.robocode.battle.peer.RobotPeer;

public class AirStrike {
	
	public AirStrike(RobotPeer robotPeer, Battle battle) {
		for (int i = 0; i < battle.getBattleRules().getBattlefieldWidth(); i+=20) {
			System.out.println("spawning AS bullet");
			BulletPeer bullet = new BulletPeer(robotPeer, battle.getBattleRules(), i);
			bullet.setPower(0.5);
			bullet.setX(i);
			bullet.setY(0);
			//bullet.setVictim(teammate);
			bullet.setPower(4);
			battle.addBullet(bullet);
		}

	}
}