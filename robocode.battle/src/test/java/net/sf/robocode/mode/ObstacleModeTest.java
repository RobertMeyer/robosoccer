package net.sf.robocode.mode;

import java.util.ArrayList;
import java.util.List;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.BattleManager;
import net.sf.robocode.battle.BattleProperties;
import net.sf.robocode.battle.DefaultSpawnController;
import net.sf.robocode.battle.events.BattleEventDispatcher;
import net.sf.robocode.battle.peer.ObstaclePeer;
import net.sf.robocode.battle.peer.RobotPeer;
import net.sf.robocode.core.Container;
import net.sf.robocode.host.CpuManager;
import net.sf.robocode.host.IHostManager;
import net.sf.robocode.host.security.ThreadManager;
import net.sf.robocode.recording.RecordManager;
import net.sf.robocode.repository.IRobotRepositoryItem;
import net.sf.robocode.repository.RepositoryManager;
import net.sf.robocode.security.HiddenAccess;
import net.sf.robocode.settings.SettingsManager;

import org.junit.Test;
import org.junit.Before;
import org.mockito.Mockito;

import robocode.BattleRules;
import robocode.control.BattleSpecification;
import robocode.control.BattlefieldSpecification;
import robocode.control.RobotSpecification;

import static org.junit.Assert.*;

public class ObstacleModeTest {
	protected static String robotsPath;
	private BattleProperties bp;
	private BattleManager bm;
	private IHostManager hm;
	private SettingsManager sm;
	private RepositoryManager rm;
	private CpuManager cm;
	private BattleEventDispatcher bd;
	private ThreadManager tm;
	private RecordManager rem;
	private Battle battle;
	private BattleRules br;
	private List<ObstaclePeer> obstacles;
	private List<RobotPeer> robots;
	private RobotSpecification spec;
	
	/**
	 * Sets up the required variables for testing
	 */
	@Before
	public void setup() {
		/*tm = new ThreadManager();
		sm = new SettingsManager();
		rm = new RepositoryManager(sm);
		cm = new CpuManager(sm);
		bd = new BattleEventDispatcher();
		rem = new RecordManager(sm);
		bm = new BattleManager(sm, rm, hm, cm, bd, rem);*/
		HiddenAccess.init();
		Container.init();
		
		hm = Mockito.mock(IHostManager.class);
		br = HiddenAccess.createRules(800, 600, 1, 0.1, 450, false, null);
		
		bp = Mockito.mock(BattleProperties.class);
		Mockito.when(bp.getBattlefieldWidth()).thenReturn(800);
		Mockito.when(bp.getBattlefieldHeight()).thenReturn(600);
		Mockito.when(bp.getGunCoolingRate()).thenReturn(0.1);
		Mockito.when(bp.getInactivityTime()).thenReturn((long) 450);
		Mockito.when(bp.getNumRounds()).thenReturn(1);
		Mockito.when(bp.getHideEnemyNames()).thenReturn(false);
		Mockito.when(bp.getInitialPositions()).thenReturn(null);
		Mockito.when(bp.getBattleMode()).thenReturn(new ObstacleMode());
		
		battle = Mockito.mock(Battle.class);
		Mockito.when(battle.getBattleMode()).thenReturn(new ObstacleMode());
		Mockito.when(battle.getBattleRules()).thenReturn(br);
		Mockito.when(battle.getSpawnController()).thenReturn(new DefaultSpawnController());
	}
	
	/**
	 * Tests if mode's toString method returns the correct string representation 
	 */
	@Test
	public void testToString() {
		String output = battle.getBattleMode().toString();
		assertEquals("toString method is incorrect", output, "Obstacle Mode");
	}
	
	/**
	 * Tests if mode returns the correct description
	 */
	@Test
	public void testDescription() {
		String output = battle.getBattleMode().getDescription();
		assertEquals("Wrong description was provided", output, 
				"A mode with obstacles that robots have to avoid.");
	}
	
	/**
	 * Tests if the mode generates the correct number of obstacles (with a reasonable number of obstacles)
	 */
	@Test
	public void testNumObstacles() {
		obstacles = ObstacleMode.generateRandomObstacles(10, bp, br, battle, 32, 32);
		//battle.setup(rm.getSelectedRobots(bp.getSelectedRobots()), bp, false, rm);
		assertEquals("Incorrect number of obstacles generated", obstacles.size(), 10);
	}
	
	/**
	 * Tests if newly generated obstacles intersect with each other on the battlefield using
	 * their set bounding boxes
	 */
	@Test
	public void testObstacleBoundingBoxIntersect() {
		obstacles = ObstacleMode.generateRandomObstacles(10, bp, br, battle, 32, 32);
		for (int i = 0; i < obstacles.size() - 1; i++) {
			for (int j = i; j < obstacles.size(); j++) {
				if (i != j) {
					assertFalse("The bounding boxes of obstacles are intersecting with each other",
							obstacles.get(i).getBoundingBox().intersects(obstacles.get(j).getBoundingBox()));
				}
			}	
		}
	}
	
	/**
	 * Tests if newly generated obstacles intersect with each other on the battlefield using absolute
	 * coordinates
	 */
	@Test
	public void testObstacleAbsCoord() {
		obstacles = ObstacleMode.generateRandomObstacles(10, bp, br, battle, 32, 32);
		for (int i = 0; i < obstacles.size() - 1; i++) {
			for (int j = i + 1; j < obstacles.size(); j++) {
				assertFalse("The obstacles are intersecting with each other",
						(obstacles.get(j).getX() < obstacles.get(i).getX() + obstacles.get(i).getWidth() &&
						obstacles.get(j).getX() > obstacles.get(i).getX() - obstacles.get(i).getWidth()) &&
						(obstacles.get(j).getY() < obstacles.get(i).getY() + obstacles.get(i).getHeight() &&
						obstacles.get(j).getY() > obstacles.get(i).getY() - obstacles.get(i).getHeight()));
			}
		}
	}
	
	/**
	 * Tests if robots will spawn on top of obstacles at the beginning of a round
	 */
	@Test
	public void testObstacleRobotIntersect() {
		IRobotRepositoryItem rItem = Mockito.mock(IRobotRepositoryItem.class);
		spec = HiddenAccess.createSpecification(rItem, "",
				"", "", "", "", "", "", "");
		
		obstacles = ObstacleMode.generateRandomObstacles(100, bp, br, battle, 32, 32);
		Mockito.when(battle.getObstacleList()).thenReturn(obstacles);	
		robots = new ArrayList<RobotPeer>();
		robots.add(new RobotPeer(battle, hm, spec, 0, null, 0, null));
		robots.add(new RobotPeer(battle, hm, spec, 0, null, 0, null));
		for (RobotPeer bot : robots) {
			bot.initializeRound(robots, null);
		}
		
		for (int i = 0; i < robots.size(); i++) {
			for (ObstaclePeer oPeer : obstacles) {
				System.out.println(robots.get(i).getX() + " " + robots.get(i).getY() + " : " + oPeer.getX()
						+ " " + oPeer.getY());
				System.out.println(
						robots.get(i).getBoundingBox().intersects(oPeer.getBoundingBox()));
				assertFalse("Robot intersecting with obstacles", 
						robots.get(i).getBoundingBox().intersects(oPeer.getBoundingBox()));
			}
		}
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
