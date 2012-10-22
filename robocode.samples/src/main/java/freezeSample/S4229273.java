/*
 *
 * Clare Heesh Robot
 * 42292739
 *
 */

package freezeSample;

import java.awt.Color;

import robocode.*;

public class S4229273 extends Robot {
	
	public void run() {
		
		while(true) {
			scan();
			//Keep moving to avoid fire
			ahead(100);
			turnGunRight(360);
			back(100);
			turnGunRight(360);
		}
	}
	
	public void onScannedRobot(ScannedRobotEvent e){
		
		//Figure out where the robot is and turn to face it
		if(e.getBearing() > 0 ){
			turnRight(Math.abs(e.getBearing()));
		} else {
			turnLeft(Math.abs(e.getBearing()));
		}
		
		//If you are further than 250 pixels from the robot move closer
		if(e.getDistance() > 250){
			ahead(e.getDistance() - 0.3 * getBattleFieldHeight());
		}
	
		if(getEnergy() > 50){
			fire(3);
		} else {
			fire(1);
		}
		
		setColors(Color.BLUE, Color.BLUE, Color.BLUE);
	}
	
	public void onRobotFrozen(RobotFrozenEvent e){
		setColors(Color.ORANGE, Color.ORANGE, Color.ORANGE);
		melt();
	}
}