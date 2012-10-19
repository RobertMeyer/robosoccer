package net.sf.robocode.battle.items;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.item.HealthPack;
import net.sf.robocode.battle.peer.RobotPeer;
import net.sf.robocode.battle.peer.RobotPeerHelper;

import org.junit.Test;
import org.mockito.Mockito;

public class TestHealthPack{
	
	private Battle battle = Mockito.mock(Battle.class);
	private RobotPeer robot = RobotPeerHelper.createMockPeer(battle);
	private HealthPack health = new HealthPack(battle, "health1");
	
	@Test
	public void testHealthPack(){
		health.doItemEffect(robot);
		Mockito.verify(robot).updateEnergy(30);
	}

}
