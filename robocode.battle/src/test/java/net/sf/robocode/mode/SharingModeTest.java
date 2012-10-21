package net.sf.robocode.mode;

import static org.junit.Assert.*;

import net.sf.robocode.battle.peer.RobotPeer;
import net.sf.robocode.battle.peer.TeamPeer;

import org.junit.*;
import org.mockito.Mockito;

/**
 * JUnit test for Team Energy Sharing Mode. 
 * 
 * @author Team - MCJJ
 * @author Jake Ching Leong Ong
 */

public class SharingModeTest {

	TeamPeer teamOne = Mockito.mock(TeamPeer.class);
	TeamPeer teamTwo = Mockito.mock(TeamPeer.class);
	RobotPeer robotOne = Mockito.mock(RobotPeer.class);
	RobotPeer robotTwo = Mockito.mock(RobotPeer.class);
	RobotPeer robotThree = Mockito.mock(RobotPeer.class);
	RobotPeer robotFour = Mockito.mock(RobotPeer.class);
	
	@Before
	public void setup(){
		Mockito.when(robotOne.getEnergy()).thenReturn((double) 100);
		Mockito.when(robotTwo.getEnergy()).thenReturn((double) 50);
		Mockito.when(teamOne.getTeamIndex()).thenReturn(0);
		Mockito.when(robotThree.getEnergy()).thenReturn((double) 50);
		Mockito.when(robotFour.getEnergy()).thenReturn((double) 25);
		Mockito.when(teamTwo.getTeamIndex()).thenReturn(1);
		teamOne.add(robotOne);
		teamOne.add(robotTwo);
		teamTwo.add(robotThree);
		teamTwo.add(robotFour);
	}

	@Test
	public void testAllRobotEnergy() {
		assertEquals("Robot One energy level is incorrect", 100,  (int) robotOne.getEnergy());
		assertEquals("Robot Two energy level is incorrect", 50,  (int) robotTwo.getEnergy());
		assertEquals("Robot Three energy level is incorrect", 50,  (int) robotThree.getEnergy());
		assertEquals("Robot Four energy level is incorrect", 25,  (int) robotFour.getEnergy());
	}
	
	@Test
	public void testTeamOneRobotOneEnergy() {
		Mockito.when(teamOne.get(0)).thenReturn(robotOne);
		assertEquals("Retrieval of Robot One energy level in Team One is incorrect", 100, (int) teamOne.get(0).getEnergy());
	}
	
	@Test
	public void testTeamOneTotalEnergy() {
		Mockito.when(teamOne.get(0)).thenReturn(robotOne);
		Mockito.when(teamOne.get(1)).thenReturn(robotTwo);
		Mockito.when(robotOne.getTotalTeamEnergy(teamOne, 0, 2)).thenCallRealMethod();
		assertEquals("getTotalTeamEnergy() does not work as expected.", 150 , robotOne.getTotalTeamEnergy(teamOne, 0, 2));
	}
	
	@Test
	public void testTeamTwoTotalEnergy() {
		Mockito.when(teamTwo.get(0)).thenReturn(robotThree);
		Mockito.when(teamTwo.get(1)).thenReturn(robotFour);
		Mockito.when(robotFour.getTotalTeamEnergy(teamTwo, 1, 2)).thenCallRealMethod();
		assertEquals("getTotalTeamEnergy() does not work as expected.", 75 , robotFour.getTotalTeamEnergy(teamTwo, 1, 2));
	}

}
