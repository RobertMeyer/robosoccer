package sampleex;

//import robocode.Robot;
import robocode.*;

import java.awt.Color;

/**
 * New version of my first robot, rewritten as a SoldierRobot.
 * Changes:
 * onScannedRobot now includes super.onScannedRobot, as per SoldierRobot rules.
 * No new pause() is implemented, instead we inherit the default.
 * advance() and retreat() inherit code, but also change colour now.
 * increasePower() and decreasePower() have the functions swapped manually, overwriting the old versions.
 * taunt() now runs in reverse direction and changes colour, overwriting the old version.
 * @author Paul
 */
public class SoldierRobot_Example extends SoldierRobot {
	
    public void run() {
    	int i;
    	
    	//Set colors
    	setColors(new Color(120, 134, 107), new Color(237, 201, 175), new Color(237, 201, 175));
    	
    	//Turn 45 degrees initially
    	turnRight(45);
    	
    	//moves in a dodgy figure 8 pattern
    	//no need for fancy curves; it stops periodically to look for targets
        while (true) {
        	for (i = 0; i < 180; i+=30) {
	        	turnLeft(30);
	            ahead(50);
	            turnRadarRight(360);
        	}
        	for (i = 0; i < 180; i+=30) {
	        	turnRight(30);
	            ahead(50);
	            turnRadarRight(360);
        	}
        }
    }
 
    public void onScannedRobot(ScannedRobotEvent e) {
    	
    	super.onScannedRobot(e);
    	
    	//This points the gun at the detected robot
    	turnGunRight(getHeading() - getGunHeading() + e.getBearing());
    	
    	//if the enemy is close or still, we'll risk a high power shot
    	if (e.getDistance() < 25 || e.getVelocity() == 0) {
    		fire(3);
    	//else, fire if there is a reasonable proximity
    	} else if (e.getDistance() < 100) {
    		fire(1);
    	}
    	
    	//Just so that the radar doesn't immediately lock on again,
    	//causing the robot to stay in one place until *someone* dies
    	turnRadarLeft(15);
    }
    
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
    
    public void onHitRobot(HitRobotEvent e) {
    	//Point gun at the jerk who got in my way, then shoot them
    	turnGunRight(getHeading() - getGunHeading() + e.getBearing());
    	fire(3);
    	
    	//Turn tail
    	turnRight(getHeading() - getGunHeading() + e.getBearing() + 180);
    }
    
	
	/**
	 * Find an enemy and approach them.
	 */
	public void advance(ScannedRobotEvent e) {
		setColors(Color.green, Color.green, Color.green);
		super.advance(e);
	}
	
	/**
	 * Back away.
	 */
	public void retreat(ScannedRobotEvent e) {
		setColors(Color.yellow, Color.yellow, Color.yellow);
		super.retreat(e);
	}
	
	/**
	 * Find an enemy and fire at them!
	 */
	public void attack(ScannedRobotEvent e) {
		setColors(Color.red, Color.red, Color.red);
		super.attack(e);
	}
	
	/**
	 * I swapped the increasePower and decreasePower functions for funsies.
	 */
	public void increasePower() {
		if (power > 1.0) {
			power -= 0.5;
		} else {
			power = 0.5;
		}
	}
	
	public void decreasePower() {
		if (power < 2.5) {
			power += 0.5;
		} else {
			power = 3.0;
		}
	}
	
	/**
	 * I reversed the direction for my new taunt.
	 */
	public void taunt() {
    	for (int i = 0; i < 360; i++) {
    		setColors(Color.white, Color.white, Color.white);
    		turnRadarLeft(1);
    		turnGunLeft(1);
    		turnRight(1);
    		setColors(Color.pink, Color.pink, Color.pink);
    	}
	}
    
}