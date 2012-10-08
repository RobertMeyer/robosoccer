package freezeSample;

import robocode.HitRobotEvent;
import robocode.ScannedRobotEvent;
import robocode.robotinterfaces.peer.IAdvancedRobotPeer;
import org.junit.*;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;


public class TestFreezeBot {
	
	private MyFirstFreezeBot bot = new MyFirstFreezeBot(); 
	private IAdvancedRobotPeer mockPeer = Mockito.mock(IAdvancedRobotPeer.class);
	
	@Before
	public void before() {
		bot.setPeer(mockPeer);
	}
	
	@Test 
	public void testFreeze() {
		
		ScannedRobotEvent mockEvent = Mockito.mock(ScannedRobotEvent.class);
		
		when(mockEvent.isFrozen()).thenReturn(false);
		when(mockEvent.getBearing()).thenReturn(0d);
		bot.onScannedRobot(mockEvent);
		verify(mockPeer).setMove(500);	

		
	}
	
	/*
	@Test
	public void testFrozenRobot() {
		
		ScannedRobotEvent mockEvent = Mockito.mock(ScannedRobotEvent.class);

		when(mockEvent.isFrozen()).thenReturn(true);
		when(mockEvent.getBearing()).thenReturn(0d);
		bot.onScannedRobot(mockEvent);
		verify(mockPeer).doNothing(); // - freezerobot does nothing here, can't find a way to check that it does nothing.
	}
	
	
	@Test
	public void testRobotCollision() {
		
		// Cannot mock HitRobotEvent because it is a final class, need to find another way
		
		HitRobotEvent mockEvent = Mockito.mock(HitRobotEvent.class);
		
		bot.onHitRobot(mockEvent);
		verify(mockPeer).setMove(-100);
	}
*/
}
