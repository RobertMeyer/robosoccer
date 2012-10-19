/*package net.sf.robocode.battle;

import java.util.ArrayList;

import org.mockito.Mockito;

import org.junit.*;

import robocode.control.RobotSpecification;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import net.sf.robocode.battle.events.BattleEventDispatcher;
import net.sf.robocode.battle.peer.RobotPeer;
import net.sf.robocode.battle.peer.TeamPeer;
import net.sf.robocode.core.Container;
import net.sf.robocode.host.ICpuManager;
import net.sf.robocode.host.IHostManager;
import net.sf.robocode.mode.ClassicMode;
import net.sf.robocode.repository.IRepositoryManager;
import net.sf.robocode.repository.IRobotRepositoryItem;
import net.sf.robocode.security.HiddenAccess;
import net.sf.robocode.settings.ISettingsManager;

public class KillFreezeRobotTest {
	ISettingsManager properties;
	IBattleManager battleManager;
	IHostManager hostManager;
	IRepositoryManager repositoryManager;
	ICpuManager cpuManager;

	IRobotRepositoryItem freezeRobotItem;
	IRobotRepositoryItem otherRobotItem;
	IRobotRepositoryItem thirdRobotItem;

	RobotSpecification freezeSpecification;
	RobotSpecification otherSpecification;
	RobotSpecification thirdSpecification;

	BattleEventDispatcher eventDispatcher;
	
	ArrayList<RobotPeer> robots = new ArrayList<RobotPeer>();

	Battle battle;

	@BeforeClass
	public static void setUpClass() {
		HiddenAccess.init();
		Container.init();
	}

	@Before
	public void setUp() {
		properties = Mockito.mock(ISettingsManager.class);
		battleManager = Mockito.mock(IBattleManager.class);
		hostManager = Mockito.mock(IHostManager.class);
		repositoryManager = Mockito.mock(IRepositoryManager.class);
		cpuManager = Mockito.mock(ICpuManager.class);

		hostManager = Mockito.mock(IHostManager.class);
		battle = Mockito.mock(Battle.class);

		freezeRobotItem = Mockito.mock(IRobotRepositoryItem.class);
		when(freezeRobotItem.isFreezeRobot()).thenReturn(true);
		freezeSpecification = HiddenAccess.createSpecification(freezeRobotItem,
				"", "", "", "", "", "", "", "");
		otherRobotItem = Mockito.mock(IRobotRepositoryItem.class);
		when(otherRobotItem.isFreezeRobot()).thenReturn(false);
		otherSpecification = HiddenAccess.createSpecification(otherRobotItem,
				"", "", "", "", "", "", "", "");

		thirdRobotItem = Mockito.mock(IRobotRepositoryItem.class);
		when(thirdRobotItem.isFreezeRobot()).thenReturn(false);
		thirdSpecification = HiddenAccess.createSpecification(thirdRobotItem,
				"", "", "", "", "", "", "", "");
	}

	@Test
	public void testKillFreezeBot() {
		
		RobotPeer freezeRobot = new RobotPeer(battle, hostManager,
		freezeSpecification, 0, null, 0);
		 
		RobotPeer otherRobot = new RobotPeer(battle, hostManager,
				otherSpecification, 0, null, 0);
		RobotPeer thirdRobot = new RobotPeer(battle, hostManager,
				otherSpecification, 0, null, 0);
	
		battle = new Battle(properties, battleManager, hostManager,
				repositoryManager, cpuManager, eventDispatcher);
		
		Mockito.when(battle.getBattleMode()).thenReturn(new ClassicMode());
		
		
		freezeRobot.initializeRound(robots, new double[1][3]);

		assertTrue("freezeRobot should start alive", freezeRobot.isAlive());
		assertTrue("otherRobot should start alive", otherRobot.isAlive());
		assertTrue("thirdRobot should start alive", thirdRobot.isAlive());
		
		//assertTrue("Active robots should be 3", battle.getActiveRobots() == 3);

		//assertFalse("freeze robot should not start dead", freezeRobot.isDead());
		
		thirdRobot.kill();
		
		assertTrue("thirdRobot should be dead after being killed", thirdRobot.isDead());
		
		assertTrue("Active robots should be 2", battle.getActiveRobots() == 2);
		
		assertFalse("Freeze robot should be alive", freezeRobot.isAlive());
		
		battle.killFreezeRobot();
		
		assertTrue("freeze robot should not be alive", freezeRobot.isAlive());
		
		assertTrue("Active robots should be 1", battle.getActiveRobots() == 1);
		
		
		

	}

}
*/