package net.sf.robocode.battle.peer;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.BattleProperties;
import net.sf.robocode.battle.FreezeRobotDeath;
import net.sf.robocode.battle.peer.RobotPeer;
import net.sf.robocode.core.Container;
import net.sf.robocode.host.IHostManager;
import net.sf.robocode.host.RobotStatics;
import net.sf.robocode.mode.ClassicMode;
import net.sf.robocode.repository.IRobotRepositoryItem;
import net.sf.robocode.security.HiddenAccess;
import net.sf.robocode.settings.ISettingsManager;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import robocode.control.RobotSpecification;

public class FreezeRobotDeathEventTest {
	IHostManager hostManager;
	IRobotRepositoryItem robotItem;
	IRobotRepositoryItem freezeRobotItem;
	IRobotRepositoryItem otherRobotItem;
	ISettingsManager properties;
	RobotSpecification specification;
	RobotSpecification freezeSpecification;
	RobotSpecification otherSpecification;
	Battle battle;
	BattleProperties battleProperties;

	@BeforeClass
	public static void setUpClass() {
		HiddenAccess.init();
		Container.init();
	}

	@Before
	public void setUp() {
		hostManager = Mockito.mock(IHostManager.class);
		battle = Mockito.mock(Battle.class);
		
		robotItem = Mockito.mock(IRobotRepositoryItem.class);
		specification = HiddenAccess.createSpecification(robotItem, "",
				"", "", "", "", "", "", "");

		freezeRobotItem = Mockito.mock(IRobotRepositoryItem.class);
		when(freezeRobotItem.isFreezeRobot()).thenReturn(true);		
		freezeSpecification = HiddenAccess.createSpecification(freezeRobotItem, "",
				"", "", "", "", "", "", "");
		otherRobotItem = Mockito.mock(IRobotRepositoryItem.class);
		when(otherRobotItem.isFreezeRobot()).thenReturn(false);
		otherSpecification = HiddenAccess.createSpecification(otherRobotItem, "",
				"", "", "", "", "", "", "");
	}
	
	@Test
	public void testCollision() {
		RobotPeer freezeRobot = new RobotPeer(battle, hostManager, freezeSpecification,	0, null, 0, null);
		RobotPeer otherRobot1 = new RobotPeer(battle, hostManager, otherSpecification,	0, null, 0, null);
		RobotPeer otherRobot2 = new RobotPeer(battle, hostManager, otherSpecification,	0, null, 0, null);
		RobotPeer otherRobot3 = new RobotPeer(battle, hostManager, otherSpecification,	0, null, 0, null);
		RobotPeer otherRobot4 = new RobotPeer(battle, hostManager, otherSpecification,	0, null, 0, null);
		RobotPeer otherRobot5 = new RobotPeer(battle, hostManager, otherSpecification,	0, null, 0, null);
		
		FreezeRobotDeath d = new FreezeRobotDeath(freezeRobot, otherRobot3);
		List<RobotPeer> robots = new ArrayList<RobotPeer>();
		
		robots.add(freezeRobot);
		robots.add(otherRobot1);
		robots.add(otherRobot2);
		robots.add(otherRobot3);
		robots.add(otherRobot4);
		robots.add(otherRobot5);
		
		d.freezeEverything(robots);
		
		assertTrue("FreezeRobot should be dead", freezeRobot.energy == 0);
		assertTrue("First Robot should be frozen", otherRobot1.robotFrozen == 120);
		assertTrue("Second Robot should be frozen", otherRobot2.robotFrozen == 120);
		assertTrue("Third Robot should not be frozen", otherRobot3.robotFrozen == 0);
		assertTrue("Fourth Robot should be frozen", otherRobot4.robotFrozen == 120);
		assertTrue("Fifth Robot should be frozen", otherRobot5.robotFrozen == 120);
	}
}
