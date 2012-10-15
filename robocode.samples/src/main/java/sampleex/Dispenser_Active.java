package sampleex;

import robocode.Dispenser;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;
import java.awt.Color;

/**
 * The version of the Dispenser takes a more active role in the battle. It
 * periodically moves, and fires healing bullets at robots.
 * @author The Fightin' Mongooses
 */
public class Dispenser_Active extends Dispenser {
	
	int counter = 0;
	
	private final static int REQUIREMENT = 5;
	
	@Override
	public void run() {
		
		setColors(Color.white, Color.red, Color.red, Color.white, Color.white);
		
		while (true) {
			//if the Dispenser has fired at enough targets, move
			if (counter >= REQUIREMENT) {
				ahead(100);
				counter = 0;
			}
			turnRadarRight(360);
		}
	}
	
	@Override
    public void onScannedRobot(ScannedRobotEvent e) {
    	
    	//This points the gun at the detected robot
    	turnGunRight(getHeading() - getGunHeading() + e.getBearing());
    	
    	fire(1);
    	
    	counter++;
    	
    	//Just so that the radar doesn't immediately lock on again,
    	//causing the robot to stay in one place until *someone* dies
    	turnRadarLeft(10);
    }
    
	@Override
    public void onHitWall(HitWallEvent e) {
    	//Rebounds off of walls at 90 degrees from angle of collision
		if (e.getBearing() >= 0) {
			turnLeft(90);
			ahead(100);
		} else {
			turnRight(90);
			ahead(100);
		}
    }
}
