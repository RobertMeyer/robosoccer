/*******************************************************************************
*
*
 *******************************************************************************/
package raceMode;

import java.awt.*;
import robocode.Robot;
import robocode.ScannedRobotEvent;
import robocode.WaypointPassedEvent;
import static java.lang.Math.atan2;

/**
*
*
 */
public class WayPointFollowBasic extends Robot {

    int others; // Number of other robots in the game


    /**
     * run:  Corners' main run function.
     */
    @Override
    public void run() {
        // Set colors
        setBodyColor(Color.yellow);
        setGunColor(Color.black);
        setRadarColor(Color.yellow);
        setBulletColor(Color.green);
        setScanColor(Color.green);

        // Save # of other bots
        others = getOthers();

        // Spin gun back and forth
        turnRight(waypointDirection(2.1, 44.3));
        while (true) {
            
            ahead(10);
            turnRight(360);
            
        }
    }
    
    public double waypointDirection(double iX, double iY){
    	//calculate the bearing to the waypoint relative to the robots Heading.
   	 return  (180/Math.PI) * ((Math.PI/2)-atan2(iY-getY(), iX-getX())) - getHeading();
    }
    
    @Override
    public void onWaypointPassed(WaypointPassedEvent e){
    	turnRight(waypointDirection(e.getWaypointX(), e.getWaypointY()));
    	//System.out.println("########################################");

    	
    }

    /**
     * onScannedRobot:  Stop and fire!
     */
    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
  
    }

    /**
     * smartFire:  Custom fire method that determines firepower based on distance.
     *
     * @param robotDistance the distance to the robot to fire at
     */
    public void smartFire(double robotDistance) {
        if (robotDistance > 200 || getEnergy() < 15) {
            fire(1);
        } else if (robotDistance > 50) {
            fire(2);
        } else {
            fire(3);
        }
    }
}
