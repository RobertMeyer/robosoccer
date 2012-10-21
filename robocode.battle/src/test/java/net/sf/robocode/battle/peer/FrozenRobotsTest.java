package net.sf.robocode.battle.peer;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.BattleProperties;
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

/**
 * Tests for the RobotPeer class.
 */
public class FrozenRobotsTest {
	IHostManager hostManager;
	IRobotRepositoryItem robotItem;
	IRobotRepositoryItem freezeRobotItem;
	IRobotRepositoryItem otherRobotItem;
	ISettingsManager properties;
	TeamPeer team;

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
		team = Mockito.mock(TeamPeer.class);
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
		RobotPeer freezeRobot = new RobotPeer(battle, hostManager, freezeSpecification,
				0, null, 0, null);
		RobotPeer otherRobot = new RobotPeer(battle, hostManager, otherSpecification,
				0, null, 0, null);
		
		otherRobot.checkForFreezeBot(freezeRobot);
		assertTrue("Robot should be frozen", otherRobot.robotFrozen == 100);
		
		otherRobot.robotFrozen = 0;
		freezeRobot.checkForFreezeBot(otherRobot);
		assertTrue("Robot should be frozen again", otherRobot.robotFrozen == 100);
		
		otherRobot.robotFrozen = 0;
		otherRobot.checkForFreezeBot(otherRobot);
		assertTrue("Robot should not be frozen", otherRobot.robotFrozen == 0);
		
		freezeRobot.checkForFreezeBot(freezeRobot);
		assertTrue("FreezeRobot should not be frozen", freezeRobot.robotFrozen == 0);
	}
}

