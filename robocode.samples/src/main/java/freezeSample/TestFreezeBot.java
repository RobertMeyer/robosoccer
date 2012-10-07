package freezeSample;

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

		when(mockEvent.isFrozen()).thenReturn(true);
		when(mockEvent.getBearing()).thenReturn(0d);
		bot.onScannedRobot(mockEvent);
		verify(mockPeer).setStop(true);
		
	}

}
