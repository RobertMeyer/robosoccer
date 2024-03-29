/*******************************************************************************
 * Copyright (c) 2001-2012 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 *
 * Contributors:
 *     Mathew A. Nelson
 *     - Initial implementation
 *     Flemming N. Larsen
 *     - Maintainance
 *     Riley Neville
 *     - Adaption for maze mode
 *******************************************************************************/
package sample;

import java.awt.*;
import robocode.HitRobotEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;

/**
 * MazeWalls - a extension of robot "Walls" by Riley Neville.
 * <p/>
 * Moves around maze generated by Maze mode.
 */
public class MazeWalls extends Robot {

    boolean peek; // Don't turn if there's a robot there
    double moveAmount; // How much to move
    boolean turnLeft;
    double lastX;
    double lastY;

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
        moveAmount = Math.max(getBattleFieldWidth(), getBattleFieldHeight());
        // Initialize peek to false
        peek = false;
        turnLeft = true;
        lastX = this.getX();
        lastY = this.getY();
        
        // turnLeft to face a wall.
        // getHeading() % 90 means the remainder of
        // getHeading() divided by 90.
        turnLeft(getHeading() % 90);
        ahead(moveAmount);
        // Turn the gun to turn right 90 degrees.
        peek = true;
        turnGunRight(90);
        turnRight(90);

        while (true) {
            // Look before we turn when ahead() completes.
            peek = true;
            // Move up the wall
            ahead(moveAmount);
            // Don't look now
            peek = false;
            // Turn to the next wall
            if (this.getX() == lastX && this.getY() == lastY) {
            	if (turnLeft == true)
            		turnLeft(180);
        		else
        			turnRight(180);
            } else {
            	if (turnLeft == true)
            		turnLeft(90);
        		else
        			turnRight(90);
            	
            }
            lastX = this.getX();
            lastY = this.getY();
            turnLeft = !turnLeft;
            fire(2);
        }
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
        // Note that scan is called automatically when the robot is moving.
        // By calling it manually here, we make sure we generate another scan event if there's a robot on the next
        // wall, so that we do not start moving up it until it's gone.
        if (peek) {
            scan();
        }
    }
}
