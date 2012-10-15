package net.sf.robocode.battle.peer;

import org.junit.*;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.IBattleManager;
import net.sf.robocode.battle.events.BattleEventDispatcher;
import net.sf.robocode.host.ICpuManager;
import net.sf.robocode.host.IHostManager;
import net.sf.robocode.host.RobotStatics;
import net.sf.robocode.repository.IRepositoryManager;
import net.sf.robocode.settings.ISettingsManager;
import robocode.control.RobotSpecification;


public class TestFreezingRobots {
/*
 * -- Needs work
 * 
	ISettingsManager settingsManager = Mockito.mock(ISettingsManager.class);
	IBattleManager battleManager = Mockito.mock(IBattleManager.class);
	IHostManager hostManager = Mockito.mock(IHostManager.class);
	IRepositoryManager RepoManager = Mockito.mock(IRepositoryManager.class);
	ICpuManager cpuManager = Mockito.mock(ICpuManager.class);
	BattleEventDispatcher eventDispatch = Mockito.mock(BattleEventDispatcher.class);
	
	private Battle battle = new Battle(settingsManager, battleManager, hostManager, RepoManager, cpuManager, eventDispatch);
	private IHostManager manager = Mockito.mock(IHostManager.class);
	private RobotSpecification spec = Mockito.mock(RobotSpecification.class);
	private TeamPeer team = Mockito.mock(TeamPeer.class);
	
	private	RobotPeer otherRobot =  new RobotPeer(battle, manager, spec, 0, team, 0);
	private RobotPeer freezeRobot = new RobotPeer(battle, manager, spec, 0, team, 0);

	private RobotStatics otherStatics = Mockito.mock(RobotStatics.class);
	private RobotStatics freezeStatics = Mockito.mock(RobotStatics.class);

	
	@Before
	public void before() {
		when(freezeStatics.isFreezeRobot()).thenReturn(true);
		freezeRobot.statics = freezeStatics;
		when(otherStatics.isFreezeRobot()).thenReturn(false);
		otherRobot.statics = otherStatics;
		
	}
	
	@Test
	public void testThisRobotFreeze() {
		freezeRobot.checkForFreezeBot(otherRobot);
		verify(freezeRobot).checkForFreezeBot(otherRobot);
	}
	
	*/
}
