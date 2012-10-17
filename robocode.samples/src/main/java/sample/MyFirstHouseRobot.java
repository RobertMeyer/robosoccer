package sample;

import robocode.GunTurnCompleteCondition;
import robocode.HouseRobot;
import robocode.HouseRobotBoundary;
import robocode.Rules;
import robocode.ScannedRobotEvent;

public class MyFirstHouseRobot extends HouseRobot {
	
	HouseRobotBoundary home;
	
	public void run() {
		
		home = getBoundaries();
		
		home.setxHome(getX());
		home.setyHome(getY());
		home.setInitialFacing(getHeading());
		home.setArcRange(200);
		
		this.turnGunLeft(80);
		
		while (true) {
			if (this.getScannedRobotEvents().size() == 0) {
				setTurnGunRight(120);
				waitFor(new GunTurnCompleteCondition(this));
				setTurnGunLeft(120);
				waitFor(new GunTurnCompleteCondition(this));
			}
		}
		
	}
	
	public void onScannedRobot(ScannedRobotEvent e) {		
		
		if (e.getDistance() < 300) {
			stop(false);
			setFire(Rules.MAX_BULLET_POWER);
		}
	}
	
}
