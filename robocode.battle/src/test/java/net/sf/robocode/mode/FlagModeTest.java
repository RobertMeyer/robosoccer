package net.sf.robocode.mode;

import static org.junit.Assert.*;

import org.junit.*;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.item.Flag;
import net.sf.robocode.battle.item.ItemDrop;
import net.sf.robocode.battle.peer.RobotPeer;

public class FlagModeTest {
	private FlagMode flagMode;

	/**
	 * Create a new Flag Mode
	 */
	@Before
	public void setup() {
		flagMode = new FlagMode();
	}
	
	/**
	 * Test to string
	 */
	@Test
	public void testToString() {
		
	}
	
	/**
	 * Test description
	 */
	@Test
	public void testDescription() {
		
	}

	/**
	 * Test the initial placement of the flag on the board
	 */
	@Test
	public void testInitialFlagOnBoard() {
		List<? extends ItemDrop> items;
		Flag flag;
		
		/* Set the items */
		Battle battle = Mockito.mock(Battle.class);
		flagMode.setItems(battle);
		
		/* Check the items */
		items = flagMode.getItems();
		
		assertEquals("Incorrect items length.", 1, items.size());
		assertEquals("Incorrect item added.", "Flag", items.get(0).getName());
		
		flag = (Flag) items.get(0);
		assertNull("Carrier is not null.", flag.getCarrier());
	}
	
	/**
	 * Test the carrier is set and after death that the robot drops the flag
	 * in the location of the robot that died
	 */
	@Test
	public void testSetCarrierAndRobotDeath() {
		Flag flag;
		
		/* Set the items */
		RobotPeer robot = Mockito.mock(RobotPeer.class);
		Mockito.when(robot.getX()).thenReturn(200.0);
		Mockito.when(robot.getY()).thenReturn(200.0);
		
		Battle battle = Mockito.mock(Battle.class);
		
		flagMode.setItems(battle);
		
		/* Set the carrier */
		flag = (Flag) flagMode.getItems().get(0);
		flag.setCarrier(robot);
		assertEquals("Carrier not setting correctly.", robot, flag.getCarrier());
		
		flagMode.onRespawnDeath(robot);
		assertNull("Flag not dropped on death", flag.getCarrier());
		assertEquals("X location of the flag = 200.0", 200.0, flag.getXLocation(), 0.0001);
		assertEquals("Y location of the flag = 200.0", 200.0, flag.getYLocation(), 0.0001);
	}
}

