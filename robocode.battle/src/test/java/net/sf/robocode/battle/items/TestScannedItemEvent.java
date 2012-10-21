package net.sf.robocode.battle.items;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import robocode.ScannedItemEvent;
import robocode.robotinterfaces.peer.IAdvancedRobotPeer;

public class TestScannedItemEvent {

	private ItemDemoBot bot = new ItemDemoBot();
	private IAdvancedRobotPeer mockPeer = Mockito.mock(IAdvancedRobotPeer.class);
	
	@Before
	public void before(){
		bot.setPeer(mockPeer);
	}
	
	@Test
	public void testHitItem(){
		ScannedItemEvent evt = Mockito.mock(ScannedItemEvent.class);
		when(evt.getItemName()).thenReturn("health1");
		when(evt.getX()).thenReturn(100.00);
		when(evt.getY()).thenReturn(100.00);
		when(evt.getDistance()).thenReturn(50.0);
		
		bot.onScannedItem(evt);
		bot.doTurnActions();
		verify(mockPeer,never()).setBodyColor(Color.WHITE);
		verify(mockPeer).setBodyColor(Color.BLUE);
		verify(mockPeer).setGunColor(Color.BLUE);
		verify(mockPeer).setRadarColor(Color.BLUE);
	}
	
}
