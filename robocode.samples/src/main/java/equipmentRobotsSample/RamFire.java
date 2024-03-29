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
 *******************************************************************************/
package equipmentRobotsSample;

import java.awt.*;

import robocode.AdvancedRobot;
import robocode.HitRobotEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;

/**
 * RamFire - a sample robot by Mathew Nelson, and maintained by Flemming N. Larsen
 * <p/>
 * Drives at robots trying to ram them.
 * Fires when it hits them.
 */
public class RamFire extends AdvancedRobot {

    int turnDirection = 1; // Clockwise or counterclockwise

    /**
     * run: Spin around looking for a target
     */
    @Override
    public void run() {
        // Set colors
    	equip("Pistol");
    	equip("Scout armour");
    	equip("Thorium Power Cell");
    	equip("All terrain tracks");
    	equip("Small Radar");
        setBodyColor(Color.lightGray);
        setGunColor(Color.gray);
        setRadarColor(Color.darkGray);

        while (true) {
            turnRight(5 * turnDirection);
        }
    }

    /**
     * onScannedRobot:  We have a target.  Go get it.
     */
    @Override
    public void onScannedRobot(ScannedRobotEvent e) {

        if (e.getBearing() >= 0) {
            turnDirection = 1;
        } else {
            turnDirection = -1;
        }

        turnRight(e.getBearing());
        ahead(e.getDistance() + 5);
        scan(); // Might want to move ahead again!
    }

    /**
     * onHitRobot:  Turn to face robot, fire hard, and ram him again!
     */
    @Override
    public void onHitRobot(HitRobotEvent e) {
        if (e.getBearing() >= 0) {
            turnDirection = 1;
        } else {
            turnDirection = -1;
        }
        turnRight(e.getBearing());

        // Determine a shot that won't kill the robot...
        // We want to ram him instead for bonus points
        if (e.getEnergy() > 16) {
            fire(3);
        } else if (e.getEnergy() > 10) {
            fire(2);
        } else if (e.getEnergy() > 4) {
            fire(1);
        } else if (e.getEnergy() > 2) {
            fire(.5);
        } else if (e.getEnergy() > .4) {
            fire(.1);
        }
        ahead(40); // Ram him again!
    }
}
