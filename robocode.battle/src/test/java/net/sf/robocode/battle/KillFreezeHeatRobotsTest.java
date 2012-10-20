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

public class KillFreezeHeatRobotsTest {
	
	
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
		
		hostManager = Mockito.mock(IHostManager.class);
		battle = Mockito.mock(Battle.class);		
		
		freezeRobotItem = Mockito.mock(IRobotRepositoryItem.class);
		when(freezeRobotItem.isFreezeRobot()).thenReturn(true);
		freezeSpecification = HiddenAccess.createSpecification(freezeRobotItem, "",
				"", "", "", "", "", "", "");

		heatRobotItem = Mockito.mock(IRobotRepositoryItem.class);
		when(heatRobotItem.isHeatRobot()).thenReturn(true);		
		heatSpecification = HiddenAccess.createSpecification(heatRobotItem, "",
				"", "", "", "", "", "", "");
		otherRobotItem = Mockito.mock(IRobotRepositoryItem.class);
		when(otherRobotItem.isHeatRobot() || otherRobotItem.isFreezeRobot()).thenReturn(false);
		otherSpecification = HiddenAccess.createSpecification(otherRobotItem, "",
				"", "", "", "", "", "", "");
	}
	
	@Ignore
	@Test
	public void killFreezeHeatRobotsTest(){
		Mockito.when(battle.getBattleMode()).thenReturn(new ClassicMode());
		RobotPeer heatRobot = new RobotPeer(battle, hostManager, heatSpecification,
				0, null, 0);
		RobotPeer freezeRobot = new RobotPeer(battle, hostManager, freezeSpecification,
				0, null, 0);
		RobotPeer otherRobot = new RobotPeer(battle, hostManager, otherSpecification,
				0, null, 0);
		
		robots = new ArrayList<RobotPeer>();
		
		robots.add(heatRobot);
		robots.add(freezeRobot);
		robots.add(otherRobot);
		//robots.add(otherRobot);
		
		assertTrue("Heat robot should be alive", heatRobot.isAlive());
		assertTrue("Robot should be a heat robot", heatRobot.isHeatRobot());
		
		assertTrue("Freeze robot should be alive", freezeRobot.isAlive());
		assertTrue("Robot should be a freeze robot", freezeRobot.isFreezeRobot());
		
		assertTrue("Other robot should be alive", otherRobot.isAlive());
		assertFalse("Other robot should not be a heat or freeze robot", otherRobot.isFreezeRobot() || otherRobot.isHeatRobot());
		
		//assertTrue("list should have 3 robots in it", robots.size() == 3);
		
		killFreezeHeatRobots.killFreezeHeatRobot(robots);
		
		assertTrue("Heat robot should be dead", heatRobot.isDead());
	}
	
}