package net.sf.robocode.battle;

import java.util.ArrayList;
import java.util.List;

import net.sf.robocode.battle.peer.RobotPeer;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class TestDeathEffect {
	DeathEffectController de = new DeathEffectController();
	
	@Test
	public void testDeathEffects() {
		RobotPeer deadRobot = Mockito.mock(RobotPeer.class);
		EffectAreaManager eaManager = new EffectAreaManager();
		List<RobotPeer> surrounding = new ArrayList<RobotPeer>();
		BattleProperties bp = Mockito.mock(BattleProperties.class);
		Mockito.when(bp.getBattlefieldHeight()).thenReturn(800);
		de.setup(bp);
		
		Mockito.when(deadRobot.getX()).thenReturn(100.0);
		Mockito.when(deadRobot.getY()).thenReturn(100.0);
		
		RobotPeer aliveRobot = Mockito.mock(RobotPeer.class);
		RobotPeer aliveOutOfRangeRobot = Mockito.mock(RobotPeer.class);
		Mockito.when(aliveRobot.isAlive()).thenReturn(true);
		Mockito.when(aliveOutOfRangeRobot.isAlive()).thenReturn(true);
		surrounding.add(aliveRobot);
		surrounding.add(aliveOutOfRangeRobot);
	
		//test death effect 1
		Mockito.when(aliveRobot.getX()).thenReturn(210.0);
		Mockito.when(aliveRobot.getY()).thenReturn(210.0);
		Mockito.when(aliveOutOfRangeRobot.getX()).thenReturn(260.0);
		Mockito.when(aliveOutOfRangeRobot.getY()).thenReturn(260.0);

		Mockito.when(deadRobot.getDeathEffect()).thenReturn(1);
		de.deathEffect(deadRobot, surrounding, eaManager);
		Mockito.verify(surrounding.get(0)).updateEnergy(-5);
		Mockito.verify(surrounding.get(1), Mockito.never()).updateEnergy(-5);
		
		//test death effect 2
		Mockito.when(aliveRobot.getX()).thenReturn(160.0);
		Mockito.when(aliveRobot.getY()).thenReturn(160.0);
		Mockito.when(aliveOutOfRangeRobot.getX()).thenReturn(210.0);
		Mockito.when(aliveOutOfRangeRobot.getY()).thenReturn(210.0);

		Mockito.when(deadRobot.getDeathEffect()).thenReturn(2);
		de.deathEffect(deadRobot, surrounding, eaManager);
		Mockito.verify(surrounding.get(0)).updateEnergy(-10);
		Mockito.verify(surrounding.get(1), Mockito.never()).updateEnergy(-10);
		
		//test death effect 3
		Mockito.when(aliveRobot.getX()).thenReturn(100.0);
		Mockito.when(aliveRobot.getY()).thenReturn(100.0);
		Mockito.when(aliveOutOfRangeRobot.getX()).thenReturn(160.0);
		Mockito.when(aliveOutOfRangeRobot.getY()).thenReturn(160.0);

		Mockito.when(deadRobot.getDeathEffect()).thenReturn(3);
		de.deathEffect(deadRobot, surrounding, eaManager);
		Mockito.verify(surrounding.get(0)).updateEnergy(-15);
		Mockito.verify(surrounding.get(1), Mockito.never()).updateEnergy(-15);
		
		//test death effect 4
		Mockito.when(deadRobot.getDeathEffect()).thenReturn(4);
		de.deathEffect(deadRobot, surrounding, eaManager);
		Assert.assertTrue(eaManager.effArea.get(0).getActiveEffect() == 1);
		eaManager.clearEffectArea();
		
		//test death effect 5
		Mockito.when(deadRobot.getDeathEffect()).thenReturn(5);
		de.deathEffect(deadRobot, surrounding, eaManager);
		Assert.assertTrue(eaManager.effArea.get(0).getActiveEffect() == 2);
		eaManager.clearEffectArea();
		
		//test death effect 6
		Mockito.when(deadRobot.getDeathEffect()).thenReturn(6);
		de.deathEffect(deadRobot, surrounding, eaManager);
		Assert.assertTrue(eaManager.effArea.get(0).getActiveEffect() == 3);	
		Assert.assertTrue(eaManager.effArea.get(0).getXCoord() == 64.0); //rounded down
		Assert.assertTrue(eaManager.effArea.get(0).getYCoord() == 160.0); // rounded + offset
		
	}
}
