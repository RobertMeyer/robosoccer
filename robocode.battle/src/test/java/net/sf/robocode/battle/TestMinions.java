package net.sf.robocode.battle;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.List;

import junit.framework.Assert;
import net.sf.robocode.battle.events.BattleEventDispatcher;
import net.sf.robocode.battle.peer.RobotPeer;
import net.sf.robocode.battle.peer.TeamPeer;
import net.sf.robocode.core.Container;
import net.sf.robocode.host.ICpuManager;
import net.sf.robocode.host.IHostManager;
import net.sf.robocode.host.RobotStatics;
import net.sf.robocode.host.proxies.HostingRobotProxy;
import net.sf.robocode.host.proxies.IHostingRobotProxy;
import net.sf.robocode.mode.ClassicMode;
import net.sf.robocode.peer.ExecCommands;
import net.sf.robocode.peer.IRobotPeer;
import net.sf.robocode.repository.IRepositoryManager;
import net.sf.robocode.repository.IRobotRepositoryItem;
import net.sf.robocode.security.HiddenAccess;
import net.sf.robocode.settings.ISettingsManager;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import robocode._RobotBase;
import robocode.control.RobotSpecification;


/**
 * Minions functional testing - using mocked and reflected robocode classes directly.
 * Directly interfaces with robotpeer/battle to ensure accurate test results.
 * @author Jordan Henderson
 *
 */
public class TestMinions {
	private IHostingRobotProxy proxy;
	private RobotSpecification minionParentSpec;
	private Battle battle;
	private RobotSpecification[] robotSpecs = new RobotSpecification[0];
	private BattleProperties properties;
	private ExtendedPeer robotPeer;
	private IRobotRepositoryItem minionParentBot;
	private ClassicMode mode;
	
	private class ExtendedPeer extends RobotPeer {
		public ExtendedPeer(Battle battle, IHostManager hostManager,
				RobotSpecification robotSpecification, int duplicate,
				TeamPeer team, int robotIndex, IHostingRobotProxy parentProxy) {
			super(battle, hostManager, robotSpecification, duplicate, team, robotIndex,
					parentProxy);
			this.robotProxy = proxy;	
			this.energy = 100;
		}
		
		@Override
		public void startRound(long milis, int nanos)  {
			this.currentCommands = new ExecCommands();
			this.currentCommands.setSpawnMinion(true, _RobotBase.MINION_TYPE_ATK, 10);
			this.commands.set(currentCommands);
		}
		
		@Override
		public void spawnMinions() {
			super.spawnMinions();
			
		}
		
		@Override
		public ExtendedPeer createMinionPeer(Battle battle, IHostManager hostManager, RobotSpecification robotSpecification, int duplicate, 
				TeamPeer team, int robotIndex, IHostingRobotProxy parentProxy) {
			return new ExtendedPeer(battle, hostManager, robotSpecification, duplicate, team, robotIndex, parentProxy);		
		}
	
		public ExecCommands getCommands () {
			return currentCommands;
		}
		
		public void setCommands(ExecCommands commands) {
			this.commands.set(commands);
		}
	}

	@BeforeClass
	public static void setUpClass(){
		HiddenAccess.init();
		Container.init();
		
	}
	
	@Before
	public void setupTest(){
		IHostManager hostManager = setupBattle();

		//Create the 'minion parent' peer.
		robotPeer = new ExtendedPeer(battle, hostManager, minionParentSpec, 0, null, 0, null);
		
		//Add robotPeer into battle, using reflection.
		try {
			Field peers = battle.getClass().getDeclaredField("peers");
			peers.setAccessible(true);
			BattlePeers actualPeers = (BattlePeers) peers.get(battle);
			actualPeers.addRobot(robotPeer);
		}
		catch(Exception ex) {
			return;
		}

	}

	private IHostManager setupBattle() {
		// Standard mocked classes.
		IHostManager hostManager = mock(IHostManager.class);
        ISettingsManager settingsManager = mock(ISettingsManager.class);
        IBattleManager battleManager = mock(IBattleManager.class);
        IRepositoryManager repositoryManager = mock(IRepositoryManager.class);
        ICpuManager cpuManager = mock(ICpuManager.class);
        BattleEventDispatcher eventDispatcher = mock(BattleEventDispatcher.class);
        
        // Mock the minion parent IRobotRepositoryItem.
        minionParentBot = Mockito.mock(IRobotRepositoryItem.class);
        // Trick robocode into thinking our 'robot' is valid.
		when(minionParentBot.isAdvancedRobot()).thenReturn(true);
		when(minionParentBot.getRobotLanguage()).thenReturn("java");
		
		//Bypass robot proxies.
		IHostingRobotProxy hostProxy = Mockito.mock(IHostingRobotProxy.class);
	
		when(hostManager.createRobotProxy(any(RobotSpecification.class), 
				any(RobotStatics.class), any(IRobotPeer.class))).thenReturn(hostProxy);
		
		
		// Create our fake robot specifications.
		minionParentSpec = HiddenAccess.createSpecification(minionParentBot, "MinionParentBot",
				"", "", "", "", "", "MinionParentBot", "");
		RobotSpecification minionAtkSpec = HiddenAccess.createSpecification(minionParentBot, "MinionAtk"
				, "", "", "", "", "", "MinionAtk", "");
		RobotSpecification minionDefSpec = HiddenAccess.createSpecification(minionParentBot, "MinionDef"
				, "", "", "", "", "", "MinionDef", "");
		RobotSpecification minionUtlSpec = HiddenAccess.createSpecification(minionParentBot, "MinionUtl"
				, "", "", "", "", "", "MinionUtl", "");
		
		
		//Set up the battle properties.
		mode = new ClassicMode();
		properties = mock(BattleProperties.class);
		when(properties.getBattleMode()).thenReturn(mode);
		when(properties.getNumRounds()).thenReturn(1);
        when(battleManager.getBattleProperties()).thenReturn(properties);
        
		//Return an empty spec list (we are creating peers manually later).
		when(repositoryManager.getSpecifications()).thenReturn(robotSpecs);
		//Create minion RobotSpecifications.
		RobotSpecification minionList[] = new RobotSpecification[3];
		minionList[0] = minionAtkSpec;
		minionList[1] = minionDefSpec;
		minionList[2] = minionUtlSpec;
		
		//Return minions
		when(repositoryManager.loadSelectedRobots(Mockito.anyString())).thenReturn(minionList);
   
		//Create the battle.
        System.setProperty("debug", "true");
		battle = new Battle(settingsManager, battleManager, hostManager, repositoryManager,
				cpuManager, eventDispatcher);
		
		battle.setup(robotSpecs, properties, false, repositoryManager);
		
		proxy = Mockito.mock(HostingRobotProxy.class);
		return hostManager;
	}
	
	@Test
	public void testMinions(){
		robotPeer.startRound(0,0);
		robotPeer.spawnMinions();
		//Test minion spawning
		List<RobotPeer> minions = robotPeer.getMinionPeers();
		Assert.assertEquals(true, minions.size() == 1);
		//Test minion power consumption.
		Assert.assertEquals(90.0, robotPeer.getEnergy());
		robotPeer.getCommands().setSpawnMinion(true, _RobotBase.MINION_TYPE_DEF, 20);
		robotPeer.spawnMinions();
		//Ensure minion 2 has spawned.
		Assert.assertEquals(true, minions.size() == 2);
		Assert.assertEquals(70.0, robotPeer.getEnergy());
		//Test minion power.
		Assert.assertEquals(10.0, minions.get(0).getEnergy());
		Assert.assertEquals(20.0, minions.get(1).getEnergy());

		//Check if the parent is correct on both minions.
		Assert.assertEquals(true, robotPeer.isParent(minions.get(0)));
		Assert.assertEquals(true, robotPeer.isParent(minions.get(1)));
				
	}	
}