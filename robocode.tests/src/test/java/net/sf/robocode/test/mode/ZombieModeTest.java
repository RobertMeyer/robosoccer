package net.sf.robocode.test.mode;

import static org.junit.Assert.assertEquals;
import net.sf.robocode.battle.BattlePeers;
import net.sf.robocode.mode.ZombieMode;
import net.sf.robocode.test.helpers.RobocodeTestBed;
import static org.mockito.Mockito.mock;
 


import org.junit.Before;
import org.junit.Test;

public class ZombieModeTest extends RobocodeTestBed {

ZombieMode zm;//A ZombieMode 
BattlePeers mockedBattlePeers;
@Before
public void setUp() {
zm = new ZombieMode();
mockedBattlePeers = mock(BattlePeers.class);

}

@Test
public void addRobotsNoRobotsAddedTest() {
//fail("Not yet implemented");
//I need a BattlePeers and and int to call addRobots
int zombiesBeforeAddRobots = mockedBattlePeers.getRobots().size();
zm.addRobots(40, mockedBattlePeers);
int zombiesAfterAddRobots = mockedBattlePeers.getRobots().size();
assertEquals("Robot list changed, and it shouldn't", zombiesBeforeAddRobots, zombiesAfterAddRobots);
}

@Test
public void addRobotsOneRobotAddedTest() {
//fail("Not yet implemented");
//I need a BattlePeers and and int to call addRobots
	//Need to do some work on this
//	int zombiesBeforeAddRobots = mockedBattlePeers.getRobots().size();
//	zm.addRobots(50, mockedBattlePeers);
//	int zombiesAfterAddRobots = mockedBattlePeers.getRobots().size();
//	assertEquals("Robot not added", zombiesBeforeAddRobots, zombiesAfterAddRobots - 1);
}

@Override
public String getRobotNames() {
	// TODO Auto-generated method stub
	return null;
}

}