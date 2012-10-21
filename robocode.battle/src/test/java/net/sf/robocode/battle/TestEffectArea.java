package net.sf.robocode.battle;

import net.sf.robocode.battle.EffectArea;
import net.sf.robocode.battle.peer.RobotPeer;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class TestEffectArea {
	
	@Test
	public void testCollision() {
		//test if collision detected
		EffectArea x = new EffectArea( 70, 70, 64, 64, 0);
		RobotPeer r = Mockito.mock(RobotPeer.class);
		
		//on the spot
		Mockito.when(r.getX()).thenReturn(70.0);
		Mockito.when(r.getY()).thenReturn(70.0);
		Assert.assertTrue(x.collision(r));
		
		//x in
		Mockito.when(r.getX()).thenReturn(90.0);
		Mockito.when(r.getY()).thenReturn(70.0);
		Assert.assertTrue(x.collision(r));
		
		//y in
		Mockito.when(r.getX()).thenReturn(70.0);
		Mockito.when(r.getY()).thenReturn(50.0);
		Assert.assertTrue(x.collision(r));
				
		//x out
		Mockito.when(r.getX()).thenReturn(69.0);
		Mockito.when(r.getY()).thenReturn(70.0);
		Assert.assertFalse(x.collision(r));	
		
		//y out
		Mockito.when(r.getX()).thenReturn(70.0);
		Mockito.when(r.getY()).thenReturn(71.0);
		Assert.assertFalse(x.collision(r));	
		
		//both in
		Mockito.when(r.getX()).thenReturn(90.0);
		Mockito.when(r.getY()).thenReturn(50.0);
		Assert.assertTrue(x.collision(r));	
		
		//both out
		Mockito.when(r.getX()).thenReturn(69.0);
		Mockito.when(r.getY()).thenReturn(71.0);
		Assert.assertFalse(x.collision(r));
		
		//higher limit
		Mockito.when(r.getX()).thenReturn(70.0 + 63);
		Mockito.when(r.getY()).thenReturn(70.0 - 63);
		Assert.assertTrue(x.collision(r));
		Mockito.when(r.getX()).thenReturn(70.0 + 64);
		Mockito.when(r.getY()).thenReturn(70.0 - 64);
		Assert.assertFalse(x.collision(r));
		
	}

	@Test
	public void testEffects() {
		EffectArea x = new EffectArea( 70, 70, 64, 64, 0);
		RobotPeer r = Mockito.mock(RobotPeer.class);
		RobotPeer r2 = Mockito.mock(RobotPeer.class);
		
		//no effect if active effect is 0
		x.setActiveEffect(0);
		x.handleEffect(r);
		Mockito.verifyZeroInteractions(r);
		
		//effect 1, reduce energy by 1
		x.setActiveEffect(1);
		Mockito.when(r.getEnergy()).thenReturn(20.0);
		x.handleEffect(r);
		Mockito.verify(r).setEnergyEffect(19.0, false);
		
		//effect 2, reduce speed to 0.5
		x.setActiveEffect(2);
		
		//if greater then check velocity have reduced
		Mockito.when(r.getVelocity()).thenReturn(0.7);
		x.handleEffect(r);
		Mockito.verify(r).setVelocityEffect(0.35);
		
		//if not, do not reduce velocity
		Mockito.when(r2.getVelocity()).thenReturn(0.0);
		x.handleEffect(r2);
		Mockito.verify(r2, Mockito.never()).setVelocityEffect(0.5);
		
		//effect 3
		Mockito.when(r.getGunHeat()).thenReturn((0.9));
		x.setActiveEffect(3);
		x.handleEffect(r);
		Mockito.verify(r).setGunHeatEffect(1);
	}
}
