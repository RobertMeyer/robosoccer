package sample;

import java.awt.Color;

import robocode.*;

/**
 * A Yellow Robot that turns left.
 *
 * A sample robot that might make it easier to see bugs.
 * 
 * @author Jackson Gatenby
 *
 */
public class LeftBot extends Robot {
	public void run() {
		setColors(Color.yellow, Color.yellow, Color.yellow);
		while (true) {
			turnLeft(20);
			ahead(20);
		}
	}
	
	public void onScannedRobot(ScannedRobotEvent e) {
		// Don't fire very hard
		fire(1);
	}
}
