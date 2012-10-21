package net.sf.robocode.mode;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.BattleProperties;
import net.sf.robocode.battle.DefaultSpawnController;
import net.sf.robocode.battle.events.BattleEventDispatcher;
import net.sf.robocode.battle.item.ItemDrop;
import net.sf.robocode.battle.peer.ObstaclePeer;
import net.sf.robocode.battle.peer.RobotPeer;
import net.sf.robocode.battle.peer.ZLevelPeer;
import net.sf.robocode.core.Container;
import net.sf.robocode.host.IHostManager;
import net.sf.robocode.repository.IRobotRepositoryItem;
import net.sf.robocode.security.HiddenAccess;

import org.junit.Test;
import org.junit.Before;
import org.mockito.Mockito;

import robocode.BattleRules;
import robocode.control.RobotSpecification;

import static org.junit.Assert.*;

public class ObstacleModeTest {
	protected static String robotsPath;
	private BattleProperties bp;
	private IHostManager hm;
	private BattleEventDispatcher bd;
	private Battle battle;
	private BattleRules br;
	private List<ObstaclePeer> obstacles;
	private List<RobotPeer> robots;
	private RobotSpecification spec;
	private RobotSpecification ospec;
	private List<ZLevelPeer> zLev;
	
	/**
	 * Sets up the required variables for testing
	 */
	@Before
	public void setup() {
		HiddenAccess.init();
		Container.init();
		
		bd = new BattleEventDispatcher();
		
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
		//genreate random obstacles
		obstacles = ObstacleMode.generateRandomObstacles(10, bp, br, battle, 32, 32);
		
		//loop through each obstacle to make sure they're not on top of another by checking if
		//their bounding boxes intersect
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
		//generate random obstacles
		obstacles = ObstacleMode.generateRandomObstacles(10, bp, br, battle, 32, 32);
		
		//loop through each obstacle to make sure they're not on top of each other by checking their
		//absolute coordinates on the battlefield
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
				assertFalse("Robot intersecting with obstacles", 
						robots.get(i).getBoundingBox().intersects(oPeer.getBoundingBox()));
			}
		}
	}
	
	/**
	 * Tests if a robot correctly responds when it hits an obstacle
	 */
	@Test
	public void testObstacleCollision() {
		IRobotRepositoryItem rItem = Mockito.mock(IRobotRepositoryItem.class);
		spec = HiddenAccess.createSpecification(rItem, "",
				"", "", "", "", "", "", "");
		
		//set up the initial position of a single obstacle
		obstacles = new ArrayList<ObstaclePeer>();
		obstacles.add(new ObstaclePeer(battle, br, 0));
		obstacles.get(0).setHeight(32);
		obstacles.get(0).setWidth(800);
		obstacles.get(0).setX(400);
		obstacles.get(0).setY(16);
		//System.out.println("ob: " + obstacles.get(0).getX() + " " + obstacles.get(0).getY());
		
		robots = new ArrayList<RobotPeer>();
		robots.add(new RobotPeer(battle, hm, spec, 0, null, 0, null));
		
		//set up inital location of the robot facing north (heading of 0)
		double[][] init = {{0, 0, 0}};
		init[0][0] = 400;
		init[0][1] = 50;
		init[0][2] = 0;
		robots.get(0).initializeRound(robots, init);
			
		assertEquals("Robot was not set up in the correct state", robots.get(0).getState().toString(),
				"ACTIVE");
		
		//Move obstacle into the robot (pretty much same as moving robot into obstacle), the point is
		//that they collide.
		obstacles.get(0).setY(30);
		
		//Update the state of the robot to see if collision occurred
		robots.get(0).performLoadCommands();
		robots.get(0).performMove(robots, new ArrayList<ItemDrop>(), obstacles, zLev, 0);
		
		assertEquals("Robot did not detect an obstacle collision", robots.get(0).getState().toString(),
				"HIT_WALL");
		
	}
	
	/**
	 * Tests if the obstacle can correctly obstruct the scanning of the robots
	 */
	@Test
	public void testScanObstruction() {
		IRobotRepositoryItem rItem = Mockito.mock(IRobotRepositoryItem.class);
		spec = HiddenAccess.createSpecification(rItem, "",
				"", "", "", "", "", "", "");
		
		//set up obstacle to not be in between the two robots
		obstacles = new ArrayList<ObstaclePeer>();
		obstacles.add(new ObstaclePeer(battle, br, 0));
		obstacles.get(0).setHeight(10);
		obstacles.get(0).setWidth(800);
		obstacles.get(0).setX(400);
		obstacles.get(0).setY(500);
		
		//set up inital location of the robot facing north (heading of 0)
		robots = new ArrayList<RobotPeer>();
		robots.add(new RobotPeer(battle, hm, spec, 0, null, 0, null));
		robots.add(new RobotPeer(battle, hm, spec, 0, null, 0, null));
		
		//set up initial position of first robot
		double[][] init = {{0, 0, 0}, {0, 0, 0}};
		init[0][0] = 400;
		init[0][1] = 10;
		init[0][2] = 180;
		robots.get(0).initializeRound(robots, init);
		
		//set up initial position for second robot
		init[0][0] = 400;
		init[0][1] = 100;
		init[0][2] = 0;
		robots.get(1).initializeRound(robots, init);
		
		//draw line between the two robots
		Line2D scan = new Line2D.Double(robots.get(0).getX(), robots.get(0).getY(), 
				robots.get(1).getX(), robots.get(1).getY());
		
		//obstacle should not be intersecting with the scan line
		assertFalse("Obstacle should not be obstructing the scan.", 
				scan.intersects(obstacles.get(0).getBoundingBox()));
		
		//move the obstacle to be in between the two robots
		obstacles.get(0).setY(50);
		
		//obstacle should now be obstructing the line of sight of robot
		assertTrue("Obstacle should be obstructing the scan.", 
				scan.intersects(obstacles.get(0).getBoundingBox()));
	}
}
