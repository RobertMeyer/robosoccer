package sampleex;

import java.awt.Color;

import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.HouseRobot;
import robocode.HouseRobotBoundary;
import robocode.Rules;
import robocode.ScannedRobotEvent;
import robocode.TurnCompleteCondition;

public class House_Robot extends HouseRobot {
	
	HouseRobotBoundary home;
	
	public void run() {
		
		setColors(Color.RED, Color.RED, Color.RED);
		
		home = getBoundaries();
		
		home.setxHome(getX());
		home.setyHome(getY());
		home.setInitialFacing(getHeading());
		home.setArcRange(200);
		
		this.turnLeft(80);
		
		while (true) {
			if (this.getScannedRobotEvents().size() == 0) {
				setTurnRight(160);
				waitFor(new TurnCompleteCondition(this));
				setTurnLeft(160);
				waitFor(new TurnCompleteCondition(this));
			}
		}
	}
	
	public void onScannedRobot(ScannedRobotEvent e) {		
		
		if (e.getDistance() < 200) {
			
			setFire(Rules.MAX_BULLET_POWER);
			turnRight(e.getBearing());
			//setAhead(10);
		}
	}
	
	public void onHitRobot(HitRobotEvent e) {		
			
		turnRight(e.getBearing());
		setFire(Rules.MAX_BULLET_POWER);
	}
	
	public void onHitByBullet(HitByBulletEvent e) {
		turnRight(e.getBearing());
		setFire(Rules.MAX_BULLET_POWER);
	}
}
