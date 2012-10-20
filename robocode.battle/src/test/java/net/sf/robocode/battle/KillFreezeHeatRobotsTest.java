package net.sf.robocode.battle;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import net.sf.robocode.battle.peer.RobotPeer;
import net.sf.robocode.core.Container;
import net.sf.robocode.host.IHostManager;
import net.sf.robocode.mode.ClassicMode;
import net.sf.robocode.repository.IRobotRepositoryItem;
import net.sf.robocode.security.HiddenAccess;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import robocode.control.RobotSpecification;

/**
 * Test that makes sure the KillFreezeHeatRobot class is working properly
 * @author Roan Coetzee CSSE2003
 *
 */
public class KillFreezeHeatRobotsTest {
	
	// Variable declirations
	IRobotRepositoryItem freezeRobotItem;
	IRobotRepositoryItem heatRobotItem;
	IRobotRepositoryItem otherRobotItem;
	
	RobotSpecification freezeSpecification;
	RobotSpecification heatSpecification;
	RobotSpecification otherSpecification;
	List<RobotPeer> robots;
	Battle battle;
	IHostManager hostManager;
	
	KillFreezeHeatRobots killFreezeHeatRobots;
	
	@BeforeClass
	public static void setUpClass(){
		HiddenAccess.init();
		Container.init();
	}
	
	@Before
	public void setUp(){
		// Mocks IHostManager class
		hostManager = Mockito.mock(IHostManager.class);
		
		// Mocks Battle class
		battle = Mockito.mock(Battle.class);		
		
		// Mocks IRobotRepository class and sets it to a freezeRobot
		freezeRobotItem = Mockito.mock(IRobotRepositoryItem.class);
		when(freezeRobotItem.isFreezeRobot()).thenReturn(true);
		freezeSpecification = HiddenAccess.createSpecification(freezeRobotItem, "",
				"", "", "", "", "", "", "");
		// Mocks IRobotRepository class and sets it to a heatRobot

		heatRobotItem = Mockito.mock(IRobotRepositoryItem.class);
		when(heatRobotItem.isHeatRobot()).thenReturn(true);		
		heatSpecification = HiddenAccess.createSpecification(heatRobotItem, "",
				"", "", "", "", "", "", "");
		// Mocks IRobotRepository class and sets it to any other robot but a freeze or heat robot

		otherRobotItem = Mockito.mock(IRobotRepositoryItem.class);
		when(otherRobotItem.isHeatRobot() || otherRobotItem.isFreezeRobot()).thenReturn(false);
		otherSpecification = HiddenAccess.createSpecification(otherRobotItem, "",
				"", "", "", "", "", "", "");
	}
	
	@Ignore
	@Test
	public void killFreezeHeatRobotsTest(){
		
		// Mocks a classic mode battle
		Mockito.when(battle.getBattleMode()).thenReturn(new ClassicMode());
		
		// Creates four robots of different specifications
		RobotPeer heatRobot = new RobotPeer(battle, hostManager, heatSpecification,
				0, null, 0, null);
		RobotPeer freezeRobot = new RobotPeer(battle, hostManager, freezeSpecification,
				0, null, 0, null);
		RobotPeer otherRobot = new RobotPeer(battle, hostManager, otherSpecification,
				0, null, 0, null);
		RobotPeer fourthRobot = new RobotPeer(battle, hostManager, otherSpecification,
				0, null, 0, null);
		
		// Creates a new instance of KillFreezeHeatRobots
		KillFreezeHeatRobots killFreezeHeatRobots = new KillFreezeHeatRobots();
		
		// Creates an ArrayList of type RobotPeer
		robots = new ArrayList<RobotPeer>();
		
		// Adds the four robots to the arraylist
		robots.add(heatRobot);
		robots.add(freezeRobot);
		robots.add(otherRobot);
		robots.add(fourthRobot);
		
		// Checks if the robots are alive and of the type they should be
		assertTrue("Heat robot should be alive", heatRobot.isAlive());
		assertTrue("Robot should be a heat robot", heatRobot.isHeatRobot());
		
		assertTrue("Freeze robot should be alive", freezeRobot.isAlive());
		assertTrue("Robot should be a freeze robot", freezeRobot.isFreezeRobot());
		
		assertTrue("Other robot should be alive", otherRobot.isAlive());
		assertFalse("Other robot should not be a heat or freeze robot", otherRobot.isFreezeRobot() || otherRobot.isHeatRobot());
		
		assertTrue("Other robot should be alive", otherRobot.isAlive());
		assertFalse("Fourth robot should not be a heat or freeze robot", fourthRobot.isFreezeRobot() || fourthRobot.isHeatRobot());		
		
		// Calls the killFreezeHeatRobot method on the robots list
		killFreezeHeatRobots.killFreezeHeatRobot(robots);
		
		// No robots should be dead
		assertTrue("Heat robot should be alive", heatRobot.isAlive());
		assertTrue("Freeze robot should be alive", freezeRobot.isAlive());
		assertTrue("Other robot should be alive", otherRobot.isAlive());
		assertTrue("Fourth robot should be alive", fourthRobot.isAlive());
		
		// Remove the fourth robot from the list and check there are three robots in it
		robots.remove(fourthRobot);
		assertTrue("Robot list should be 3", robots.size() == 3);
		
		// Calls the killFreezeHeatRobot method again
		killFreezeHeatRobots.killFreezeHeatRobot(robots);
		
		// All but the other robot should be alive
		assertTrue("Heat robot should be alive", heatRobot.isDead());
		assertTrue("Freeze robot should be alive", freezeRobot.isDead());
		assertTrue("Other robot should be alive", otherRobot.isAlive());
	}
	
}