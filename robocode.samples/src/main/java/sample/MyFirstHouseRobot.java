package sample;

import robocode.HouseRobot;
import robocode.HouseRobotBoundary;

public class MyFirstHouseRobot extends HouseRobot {
	
	private HouseRobotBoundary home;
	
	public void run() {
		
		home = this.getBoundaries();
		
		home.setxHome(this.getX());
		home.setyHome(this.getY());
		home.setInitialFacing(this.getHeading());
		
	}

}
