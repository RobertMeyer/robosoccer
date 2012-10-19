/*package net.sf.robocode.battle;

import static org.mockito.Mockito.when;
import net.sf.robocode.battle.events.BattleEventDispatcher;
import net.sf.robocode.core.Container;
import net.sf.robocode.host.ICpuManager;
import net.sf.robocode.host.IHostManager;
import net.sf.robocode.repository.IRepositoryManager;
import net.sf.robocode.repository.IRobotRepositoryItem;
import net.sf.robocode.security.HiddenAccess;
import net.sf.robocode.settings.ISettingsManager;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import robocode.control.RobotSpecification;

public class KillFreezeBotTest {

	ISettingsManager properties;
	IBattleManager battleManager;
	IHostManager hostManager;
	IRepositoryManager repositoryManager;
	ICpuManager cpuManager;
	BattleEventDispatcher eventDispatcher;
	
	
	BattleProperties battleProperties;
	boolean paused = false;
	
	IRobotRepositoryItem freezeRobotItem;
	IRobotRepositoryItem secondRobotItem;
	IRobotRepositoryItem thirdRobotItem;
	
	RobotSpecification freezeSpecification;
	RobotSpecification secondSpecification;
	RobotSpecification thirdSpecification;

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
		eventDispatcher = Mockito.mock(BattleEventDispatcher.class);
		
		battleProperties = Mockito.mock(BattleProperties.class);
		
		freezeRobotItem = Mockito.mock(IRobotRepositoryItem.class);
		when(freezeRobotItem.isFreezeRobot()).thenReturn(true);
		freezeSpecification = HiddenAccess.createSpecification(freezeRobotItem,
				"", "", "", "", "", "", "", "");
		secondRobotItem = Mockito.mock(IRobotRepositoryItem.class);
		when(secondRobotItem.isFreezeRobot()).thenReturn(false);
		secondSpecification = HiddenAccess.createSpecification(secondRobotItem,
				"", "", "", "", "", "", "", "");

		thirdRobotItem = Mockito.mock(IRobotRepositoryItem.class);
	    when(thirdRobotItem.isFreezeRobot()).thenReturn(false);
		thirdSpecification = HiddenAccess.createSpecification(thirdRobotItem,
				"", "", "", "", "", "", "", "");
		
		

	}

	@Test
	public void test() {
		RobotSpecification[] battlingRobotsList = {freezeSpecification, secondSpecification, thirdSpecification};

		Battle battle = new Battle(properties, battleManager, hostManager,
				repositoryManager, cpuManager, eventDispatcher);
		
		battle.setup(battlingRobotsList, battleProperties, paused, repositoryManager);
	}
}
*/