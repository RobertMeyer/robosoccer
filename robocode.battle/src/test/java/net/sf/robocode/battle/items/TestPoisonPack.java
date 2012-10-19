package net.sf.robocode.battle.items;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.item.PoisonPack;
import net.sf.robocode.battle.peer.RobotPeer;
import net.sf.robocode.battle.peer.RobotPeerHelper;

import org.junit.Test;
import org.mockito.Mockito;

public class TestPoisonPack{
	
	private Battle battle = Mockito.mock(Battle.class);
	private RobotPeer robot = RobotPeerHelper.createMockPeer(battle);
	private PoisonPack poison = new PoisonPack(battle, "poison1");
	
	@Test
	public void testHealthPack(){
		poison.doItemEffect(robot);
		Mockito.verify(robot).updateEnergy(-15);
	}

}
