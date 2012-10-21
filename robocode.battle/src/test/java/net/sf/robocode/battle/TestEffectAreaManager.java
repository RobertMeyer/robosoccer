package net.sf.robocode.battle;


import net.sf.robocode.battle.EffectArea;
import net.sf.robocode.battle.peer.RobotPeer;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class TestEffectAreaManager {

	@Test
	public void testRandomEffectGeneration() {
		EffectAreaManager eaManager = new EffectAreaManager();
		BattleProperties bp = Mockito.mock(BattleProperties.class);
		
		//using a battlefield of 800x600
		Mockito.when(bp.getBattlefieldHeight()).thenReturn(800);
		Mockito.when(bp.getBattlefieldWidth()).thenReturn(600);
		eaManager.createRandomEffectAreas(bp, 1);
		
		//check modifier 1 is around 0-20
		Assert.assertTrue(eaManager.effArea.size() > 0);
		Assert.assertTrue(eaManager.effArea.size() < 20);
		
		//check that clearEfffectArea works
		eaManager.clearEffectArea();
		Assert.assertTrue(eaManager.effArea.size() == 0);
		
		//check modifier 50
		eaManager.createRandomEffectAreas(bp, 50);
		Assert.assertTrue(eaManager.effArea.size() > 8);
		eaManager.clearEffectArea();
		
		//check modifier 100
		eaManager.createRandomEffectAreas(bp, 100);
		Assert.assertTrue(eaManager.effArea.size() > 16);
		eaManager.clearEffectArea();
	}
	
	@Test
	public void testUpdate() {
		EffectAreaManager eaManager = new EffectAreaManager();
		BattlePeers peers = Mockito.mock(BattlePeers.class);
		List<RobotPeer> list = new ArrayList<RobotPeer>();
		list.add(Mockito.mock(RobotPeer.class));
		list.add(Mockito.mock(RobotPeer.class));
		Mockito.when(peers.getRobots()).thenReturn(list);
		
		//add an effect area and check success
		EffectArea ea1 = Mockito.mock(EffectArea.class);
		EffectArea ea2 = Mockito.mock(EffectArea.class);
		eaManager.addEffectArea(ea1);
		eaManager.addEffectArea(ea2);
		Assert.assertTrue(eaManager.effArea.size() == 2);
		
		//check for effect area 1 with robot 0 and effect area 2 with robot 1
		Mockito.when(ea1.collision(list.get(0))).thenReturn(true);
		Mockito.when(ea1.collision(list.get(1))).thenReturn(false);
		Mockito.when(ea2.collision(list.get(0))).thenReturn(false);
		Mockito.when(ea2.collision(list.get(1))).thenReturn(true);
		
		//assuming ea1 has no effect and ea2 does
		Mockito.when(ea1.getActiveEffect()).thenReturn(0);
		Mockito.when(ea2.getActiveEffect()).thenReturn(1);
		
		eaManager.updateEffectAreas(peers);
		
		//verify ea1 was given new effect and ea2 was not
		Mockito.verify(ea1).setActiveEffect(Mockito.anyInt());
		Mockito.verify(ea2, Mockito.never()).setActiveEffect(Mockito.anyInt());
		
		//check that both ran handle effect on correct robot
		Mockito.verify(ea1).handleEffect(list.get(0));
		Mockito.verify(ea1, Mockito.never()).handleEffect(list.get(1));
		Mockito.verify(ea2).handleEffect(list.get(1));
		Mockito.verify(ea2, Mockito.never()).handleEffect(list.get(0));
	}
}
