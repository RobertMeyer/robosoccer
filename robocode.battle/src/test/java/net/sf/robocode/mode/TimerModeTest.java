package net.sf.robocode.mode;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import net.sf.robocode.battle.BattleManager;
import net.sf.robocode.battle.peer.RobotPeer;

import org.junit.*;
import org.mockito.Mockito;

/**
 * JUnit test for Timer Mode. 
 * 
 * @author Team - MCJJ
 * @author Jake Ching Leong Ong
 */

public class TimerModeTest {

	List<RobotPeer> robotList;
	RobotPeer robotOne = Mockito.mock(RobotPeer.class);
	RobotPeer robotTwo = Mockito.mock(RobotPeer.class);
	RobotPeer robotThree = Mockito.mock(RobotPeer.class);
	BattleManager battleManager = Mockito.mock(BattleManager.class);

	@Before
	public void setup(){
		Mockito.when(robotOne.getEnergy()).thenReturn((double) 100);
		Mockito.when(robotTwo.getEnergy()).thenReturn((double) 50);
		Mockito.when(robotThree.getEnergy()).thenReturn((double) 25);
		robotList = new ArrayList<RobotPeer>();
		robotList.add(robotOne);
		robotList.add(robotTwo);
		robotList.add(robotThree);
	}
	
	@Test
	public void testRobotCount() {
		assertEquals("Robot count was incorrect.", 3 , robotList.size());
	}
	
	@Ignore
	public void testGetTopRobot() {
		//battleManager.getTopRobot();
		assertEquals("getTopRobot() does not work as expected (Robot One)", 100 , (int) robotList.get(0).getEnergy());
		assertEquals("getTopRobot() does not work as expected (Robot Two)", 0 , (int) robotList.get(1).getEnergy());
		assertEquals("getTopRobot() does not work as expected (Robot Three)", 0 , (int) robotList.get(2).getEnergy());
	}

}
