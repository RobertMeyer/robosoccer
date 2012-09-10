package sample;

import robocode.HouseRobot;
import robocode.HouseRobotBoundary;

public class MyFirstHouseRobot extends HouseRobot {
	
	public void run() {
		this.setBoundaries(new HouseRobotBoundary(0,0,0,100,0,100));
	}

}
