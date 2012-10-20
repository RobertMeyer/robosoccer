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
public class MeltCreditTest {
	IHostManager hostManager;
	IRobotRepositoryItem robotItem;
	IRobotRepositoryItem heatRobotItem;
	IRobotRepositoryItem otherRobotItem;
	ISettingsManager properties;
	TeamPeer team;

	RobotSpecification specification;
	RobotSpecification heatSpecification;
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

		heatRobotItem = Mockito.mock(IRobotRepositoryItem.class);
		when(heatRobotItem.isHeatRobot()).thenReturn(true);		
		heatSpecification = HiddenAccess.createSpecification(heatRobotItem, "",
				"", "", "", "", "", "", "");
		otherRobotItem = Mockito.mock(IRobotRepositoryItem.class);
		when(otherRobotItem.isHeatRobot()).thenReturn(false);
		otherSpecification = HiddenAccess.createSpecification(otherRobotItem, "",
				"", "", "", "", "", "", "");
	}
	
	@Test
	public void testCollision() {
		RobotPeer heatRobot = new RobotPeer(battle, hostManager, heatSpecification,
				0, null, 0);
		RobotPeer otherRobot = new RobotPeer(battle, hostManager, otherSpecification,
				0, null, 0);
		
		otherRobot.checkForHeatBot(heatRobot);
		assertTrue("Robot should get meltCredit", otherRobot.meltCredit == 1);
		
		heatRobot.checkForHeatBot(otherRobot);
		assertTrue("Robot should get more meltCredit", otherRobot.meltCredit == 2);
		
		otherRobot.checkForHeatBot(otherRobot);
		assertTrue("Robot should not get meltCredit", otherRobot.meltCredit == 2);
		
		heatRobot.checkForHeatBot(heatRobot);
		assertTrue("FreezeRobot should not get meltCredit", heatRobot.meltCredit == 0);
	}
}
