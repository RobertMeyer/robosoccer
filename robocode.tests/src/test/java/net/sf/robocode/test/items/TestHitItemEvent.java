package net.sf.robocode.test.items;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;
//import org.mockito.Mockito;
//import static org.mockito.Mockito.*;


import robocode.HitItemEvent;
import robocode.robotinterfaces.peer.IAdvancedRobotPeer;
import sample.ItemDemoBot;

public class TestHitItemEvent {

	private ItemDemoBot bot = new ItemDemoBot();
	/*private IAdvancedRobotPeer mockPeer = Mockito.mock(IAdvancedRobotPeer.class);
	
	@Before
	public void before(){
		bot.setPeer(mockPeer);
	}
	
	@Test
	public void testHitItem(){
		HitItemEvent evt = Mockito.mock(HitItemEvent.class);
		when(evt.getItemName()).thenReturn("health1");
		when(evt.isDestroyable()).thenReturn(true);
		when(evt.isEquippable()).thenReturn(false);
		
		bot.onHitItem(evt);
		bot.doTurnActions();
		verify(mockPeer,never()).setBodyColor(Color.BLUE);
		verify(mockPeer).setBodyColor(Color.WHITE);
		verify(mockPeer).setGunColor(Color.WHITE);
		verify(mockPeer).setRadarColor(Color.WHITE);
	}*/
	
}
