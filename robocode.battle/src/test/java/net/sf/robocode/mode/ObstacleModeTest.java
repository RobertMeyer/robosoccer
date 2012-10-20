package net.sf.robocode.mode;

import java.io.File;
import java.io.IOException;
import java.util.List;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.BattleManager;
import net.sf.robocode.battle.BattleProperties;
import net.sf.robocode.battle.events.BattleEventDispatcher;
import net.sf.robocode.battle.peer.ObstaclePeer;
import net.sf.robocode.host.CpuManager;
import net.sf.robocode.host.HostManager;
import net.sf.robocode.host.security.ThreadManager;
import net.sf.robocode.io.Logger;
import net.sf.robocode.recording.RecordManager;
import net.sf.robocode.repository.RepositoryManager;
import net.sf.robocode.security.HiddenAccess;
import net.sf.robocode.settings.SettingsManager;

import org.junit.Test;
import org.junit.Before;
import org.mockito.Mockito;

import robocode.BattleRules;
import robocode.control.BattleSpecification;
import robocode.control.BattlefieldSpecification;
import robocode.control.RobocodeEngine;
import robocode.control.RobotSpecification;

import static org.junit.Assert.*;

public class ObstacleModeTest {
	protected static String robotsPath;
	private BattleProperties bp;
	private BattleManager bm;
	private HostManager hm;
	private SettingsManager sm;
	private RepositoryManager rm;
	private CpuManager cm;
	private BattleEventDispatcher bd;
	private ThreadManager tm;
	private RecordManager rem;
	private Battle battle;
	private BattleRules br;
	
	@Before
	public void setup() {
		/*tm = new ThreadManager();
		sm = new SettingsManager();
		hm = new HostManager(sm, tm);
		rm = new RepositoryManager(sm);
		cm = new CpuManager(sm);
		bd = new BattleEventDispatcher();
		rem = new RecordManager(sm);
		bm = new BattleManager(sm, rm, hm, cm, bd, rem);*/
		
		bp = Mockito.mock(BattleProperties.class);
		Mockito.when(bp.getBattlefieldWidth()).thenReturn(800);
		Mockito.when(bp.getBattlefieldHeight()).thenReturn(600);
		Mockito.when(bp.getGunCoolingRate()).thenReturn(0.1);
		Mockito.when(bp.getInactivityTime()).thenReturn((long) 450);
		Mockito.when(bp.getNumRounds()).thenReturn(1);
		Mockito.when(bp.getHideEnemyNames()).thenReturn(false);
		Mockito.when(bp.getSelectedRobots()).thenReturn("sample.Walls, sample.Target");
		Mockito.when(bp.getInitialPositions()).thenReturn(null);
		Mockito.when(bp.getBattleMode()).thenReturn(new ObstacleMode());
		
		battle = Mockito.mock(Battle.class);
		Mockito.when(battle.getBattleMode()).thenReturn(new ObstacleMode());		
		
	}
	
	@Test
	public void testToString() {
		String output = battle.getBattleMode().toString();
		assertEquals("toString method is incorrect", output, "Obstacle Mode");
	}
	
	@Test
	public void testDescription() {
		String output = battle.getBattleMode().getDescription();
		assertEquals("Wrong description was provided", output, 
				"A mode with obstacles that robots have to avoid.");
	}
	
	@Test
	public void testNumObstacles() {
		List<ObstaclePeer> obstacles = ObstacleMode.generateRandomObstacles(10, bp, br, battle, 32, 32);
		//battle.setup(rm.getSelectedRobots(bp.getSelectedRobots()), bp, false, rm);
		assertEquals("Incorrect number of obstacles generated", obstacles.size(), 10);
	}
	
	/*@Test
	public void testObstacles() {
		BattleProperties bp = Mockito.mock(BattleProperties.class);
		// more mocking...
		 
		Mockito.when(bp.getBattlefieldWidth()).thenReturn(200);
		Mockito.when(bp.getBattlefieldHeight()).thenReturn(200);
		// etc...
		 
		//List<ObstaclePeer> obstacles = ObstaclePeer.generateRandomObstacles(10, bp, ...);
		//assert things;
	}*/
}
