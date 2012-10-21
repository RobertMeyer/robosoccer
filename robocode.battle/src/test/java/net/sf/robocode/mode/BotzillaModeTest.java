/*******************************************************************************
 * Copyright (c) 2012. Team The Fightin' Mongooses
 * 
 * Conttributors:
 * 	Paul Wade
 * 	Chris Irving
 *	Jesse Claven
*******************************************************************************/

package net.sf.robocode.mode;

import static org.junit.Assert.*;

import org.junit.*;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import net.sf.robocode.battle.BattleManager;
import net.sf.robocode.battle.peer.RobotPeer;

/**
 * Test Botzilla mode to ensure that the robot spawns at the correct time and
 * follows the designed behaviour.
 * 
 * @author The Fightin' Mongooses
 *
 */
public class BotzillaModeTest {
	private BotzillaMode bM;
	
	List<RobotPeer> robotList;
	RobotPeer robotOne = Mockito.mock(RobotPeer.class);
	RobotPeer robotTwo = Mockito.mock(RobotPeer.class);
	RobotPeer robotThree = Mockito.mock(RobotPeer.class);
	BattleManager battleManager = Mockito.mock(BattleManager.class);
	
	@Before
	public void setup() {
		/**
		 * Create Botzilla Mode
		 */
		bM = new BotzillaMode();
		
		robotList = new ArrayList<RobotPeer>();
		robotList.add(robotOne);
		robotList.add(robotTwo);
		robotList.add(robotThree);
	}
	
	/**
	 * Test that the correct battle mode string is returned.
	 */
	@Test
	public void testToString() {
		assertEquals("Representation is incorrect", "Botzilla Mode", bM.toString());
	}
	
	/**
	 * Test that the correct battle mode string is returned.
	 */
	@Test
	public void testDescription() {
		assertEquals("Description is incorrect", "A mode that brings in an undefeatable enemy to speed up the finish.", bM.getDescription());
	}
}
