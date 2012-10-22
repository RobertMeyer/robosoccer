/**
 * CSSE2003 Team MCJJ
 */

package net.sf.robocode.battle;

import net.sf.robocode.battle.peer.BulletPeer;
import net.sf.robocode.battle.peer.RobotPeer;
import net.sf.robocode.battle.peer.TeamPeer;
import net.sf.robocode.mode.ClassicMode;
import net.sf.robocode.battle.FriendlyFireTracker;

import static org.junit.Assert.*;
import org.junit.*;
import org.mockito.Mockito;

public class TestFriendlyFire {
	//Create Team
	TeamPeer teamFriendly = Mockito.mock(TeamPeer.class);
	//Create Robots
	RobotPeer TeamRobotOne = Mockito.mock(RobotPeer.class);
	RobotPeer TeamRobotTwo = Mockito.mock(RobotPeer.class);
	RobotPeer soloRobot = Mockito.mock(RobotPeer.class);
	FriendlyFireTracker BulletState = Mockito.mock(FriendlyFireTracker.class);
	
	//mocks BulletPeer class
	BulletPeer b = Mockito.mock(BulletPeer.class);
	//mocks battle class
	Battle battle = Mockito.mock(Battle.class);
	
	@Before
	public void setup(){
		Mockito.when(TeamRobotOne.getEnergy()).thenReturn((double)100);
		Mockito.when(TeamRobotTwo.getEnergy()).thenReturn((double)100);
		Mockito.when(teamFriendly.getTeamIndex()).thenReturn(0);
	
		teamFriendly.add(TeamRobotOne);
		teamFriendly.add(TeamRobotTwo);
	}
		
	public void TestFriendlyfire(){
		//mocks a classic battle
		Mockito.when(battle.getBattleMode()).thenReturn(new ClassicMode());
		Mockito.when(FriendlyFireTracker.enableFriendlyfire).thenReturn(true);
		assertEquals("Friendly Fire Failed", 100, (int)TeamRobotTwo.getEnergy());
	}
}
