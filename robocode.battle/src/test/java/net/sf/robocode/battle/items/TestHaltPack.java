package net.sf.robocode.battle.items;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.item.HaltPack;
import net.sf.robocode.battle.peer.RobotPeer;
import net.sf.robocode.battle.peer.RobotPeerHelper;

import org.junit.Test;
import org.mockito.Mockito;

public class TestHaltPack{
	
	private Battle battle = Mockito.mock(Battle.class);
	private RobotPeer robot = RobotPeerHelper.createMockPeer(battle);
	private HaltPack halt = new HaltPack(battle, "halt1");
	
	@Test
	public void testHealthPack(){
		halt.doItemEffect(robot);
		Mockito.verify(robot).setHalt(true);
	}

}
