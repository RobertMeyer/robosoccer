package sampleex;

import java.awt.Color;

import robocode.Dispenser;
import robocode.ScannedRobotEvent;

/**
 * Behaviour for the Dispenser. Basically, it just sits there.
 * @author The Fightin' Mongooses
 */
public class DispenserBot extends Dispenser {
	
	int turnDirection = 1;
	
	@Override
	public void run() {
		
		setColors(Color.white, Color.red, Color.red, Color.white, Color.white);
		
    	while (true) {
    		turnRadarRight(1);
    		turnGunRight(1);
    		turnLeft(1);
    	}
		
//		while (true) {
//			turnRadarRight(360);
//		}
	}
	
//    public void onScannedRobot(ScannedRobotEvent e) {
//    	
//    	//This points the gun at the detected robot
//    	turnGunRight(getHeading() - getGunHeading() + e.getBearing());
//    	
//    	fire(1);
//    	
//    	//Just so that the radar doesn't immediately lock on again,
//    	//causing the robot to stay in one place until *someone* dies
//    	turnRadarLeft(15);
//    }
}
