package net.sf.robocode.mode;

import java.io.File;
import java.io.IOException;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.BattleManager;
import net.sf.robocode.battle.BattleProperties;
import net.sf.robocode.battle.events.BattleEventDispatcher;
import net.sf.robocode.host.CpuManager;
import net.sf.robocode.host.HostManager;
import net.sf.robocode.host.security.ThreadManager;
import net.sf.robocode.io.Logger;
import net.sf.robocode.recording.RecordManager;
import net.sf.robocode.repository.RepositoryManager;
import net.sf.robocode.settings.SettingsManager;

import org.junit.Test;
import org.junit.Before;
import org.mockito.Mockito;

import robocode.control.BattleSpecification;
import robocode.control.BattlefieldSpecification;
import robocode.control.RobocodeEngine;
import robocode.control.RobotSpecification;

import static org.junit.Assert.*;

public class ObstacleModeTest {
	/*protected static String robotsPath;
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
	
	RobocodeEngine engine;
	
	@Before
	public void setup() {
		System.setProperty("EXPERIMENTAL", "true");
        System.setProperty("TESTING", "true");
        System.setProperty("WORKINGDIRECTORY", "target/test-classes");
        try {
        	if (new File("").getAbsolutePath().endsWith("robocode.tests")) {
                robotsPath = new File("../robocode.tests.robots").getCanonicalPath();
            } else {
                throw new Error("Unknown directory");
            }
        } catch (IOException e) {
            e.printStackTrace(Logger.realErr);
        }
        System.setProperty("ROBOTPATH", robotsPath + "/target/classes");
        
        
		tm = new ThreadManager();
		sm = new SettingsManager();
		hm = new HostManager(sm, tm);
		rm = new RepositoryManager(sm);
		cm = new CpuManager(sm);
		bd = new BattleEventDispatcher();
		rem = new RecordManager(sm);
		bm = new BattleManager(sm, rm, hm, cm, bd, rem);
		
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

		//battle = new Battle(sm, bm, hm, rm, cm, bd);
		//battle.setup(rm.getSelectedRobots(bp.getSelectedRobots()), bp, false, rm);
		//assert things;
		RobotSpecification[] rs = engine.getLocalRepository("tested.robots.Ahead, tested.robots.Ahead");
		
        assertNotNull("Robot were not loaded", rs);
        assertEquals("Robot were not loaded", 2, rs.length);
        engine.runBattle(new BattleSpecification(1, new BattlefieldSpecification(), rs), null, true);
	}
	
	@Test
	public void testToString() {
		assertEquals("toString method is incorrect", battle.getBattleMode().toString(), "Obstacle Mode");
	}
	
	@Test
	public void testNumObstacles() {
		//assertEquals("There are too many obstacles on the battlefield", om.setNumObstacles(null),
		//		10);
	}
	
	@Test
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
