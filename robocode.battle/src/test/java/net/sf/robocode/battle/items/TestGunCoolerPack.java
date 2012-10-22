package net.sf.robocode.battle.items;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.item.GunCoolerPack;
import net.sf.robocode.battle.peer.RobotPeer;
import net.sf.robocode.battle.peer.RobotPeerHelper;

import org.junit.Test;
import org.mockito.Mockito;

public class TestGunCoolerPack{
	
	private Battle battle = Mockito.mock(Battle.class);
	private RobotPeer robot = RobotPeerHelper.createMockPeer(battle);
	private GunCoolerPack gunCooler = new GunCoolerPack(battle, "gunCooler1");
	
	@Test
	public void testHealthPack(){
		Mockito.when(robot.getGunHeat()).thenReturn(100.0);
		gunCooler.doItemEffect(robot);
		Mockito.verify(robot).setGunHeatEffect(80.0);
	}

}
