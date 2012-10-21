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

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.BattleManager;
import net.sf.robocode.battle.BattlePeers;
import net.sf.robocode.battle.events.BattleEventDispatcher;
import net.sf.robocode.battle.peer.RobotPeer;
import net.sf.robocode.host.ICpuManager;
import net.sf.robocode.host.IHostManager;
import net.sf.robocode.repository.IRepositoryManager;
import net.sf.robocode.settings.ISettingsManager;

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
	Battle battle = Mockito.mock(Battle.class);
	BattleManager battleManager = Mockito.mock(BattleManager.class);
	ISettingsManager settingsManager = Mockito.mock(ISettingsManager.class);
	IHostManager hostManager = Mockito.mock(IHostManager.class);
	IRepositoryManager repositoryManager = Mockito.mock(IRepositoryManager.class);
	ICpuManager cpuManager = Mockito.mock(ICpuManager.class);
	BattleEventDispatcher eventDispatcher = Mockito.mock(BattleEventDispatcher.class);
	BattlePeers peers = Mockito.mock(BattlePeers.class);
	RobotPeer botzillaPeer = Mockito.mock(RobotPeer.class);
	
	@Before
	public void setup() {
		/**
		 * Create Botzilla Mode
		 */
		bM = new BotzillaMode();
		
		/** 
		 * Create the battle.
		 */
		System.setProperty("debug", "true");
		battle = new Battle(settingsManager, battleManager, hostManager, repositoryManager,
			 	 			cpuManager, eventDispatcher);
		
		/**
		 * Add some competing robots for Botzilla to destroy.
		 */
		robotList = new ArrayList<RobotPeer>();
		robotList.add(robotOne);
		robotList.add(robotTwo);
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
	
	/**
	 * Add turn 749 check that Botzilla has not yet been spawned.
	 */
	@Test
	public void testTurnCount() {
		assertEquals("Current turn is not < 750.", 0, battle.getCurrentTurnNumber());
	}
	
	/**
	 * At and after turn 750 check that Botzilla is has been spawned.
	 */
	@Ignore
	@Test
	public void testSpawnBotzilla() {
		// Check turn count
		assertEquals("Current turn is not >= 750.", 750, battle.getCurrentTurnNumber());
		
		// Check that Botzilla is yet to be added
		assertEquals("Botzilla is already active.", true, battle.checkBotzillaActive());
		
		// Add Botzilla
		battle.addBotzilla();
		
		// Check that Botzilla is now active
		assertEquals("Botzilla is now active.", true, battle.checkBotzillaActive());
		
		// Check that Botzilla has 999 energy
		
		// Check Botzilla's hitbox size
		
		// Check Botzilla's custom image
	}
	
}
