
package sample;

import java.awt.*;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;

public class MazeCrawler extends Robot {

    double moveAmount; // How much to move

    /**
     * run: Move around the walls
     */
    @Override
    public void run() {
        // Set colors
        setBodyColor(Color.black);
        setGunColor(Color.black);
        setRadarColor(Color.orange);
        setBulletColor(Color.cyan);
        setScanColor(Color.cyan);

        // Initialize moveAmount to the maximum possible for this battlefield.
        //moveAmount = Math.max(getBattleFieldWidth(), getBattleFieldHeight());
        moveAmount = 20;
        // Initialize peek to false
        
        // turnLeft to face a wall.
        // getHeading() % 90 means the remainder of
        // getHeading() divided by 90.
        turnLeft(getHeading() % 90);
        ahead(10000);

        while (true) {
            ahead(moveAmount);
            turnLeft(90);
        }
    }
    
    @Override
    public void onHitWall(HitWallEvent e) {
    	turnRight(90);
    	ahead(moveAmount);
    	
    }

    /**
     * onHitRobot:  Move away a bit.
     */
    @Override
    public void onHitRobot(HitRobotEvent e) {
        // If he's in front of us, set back up a bit.
        if (e.getBearing() > -90 && e.getBearing() < 90) {
            back(100);
        } // else he's in back of us, so set ahead a bit.
        else {
            ahead(100);
        }
    }
    
    /**
     * onScannedRobot:  Fire!
     */
    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        fire(2);
    }
}
