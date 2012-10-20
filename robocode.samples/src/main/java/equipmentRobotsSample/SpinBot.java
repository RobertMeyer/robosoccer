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
import robocode.ScannedRobotEvent;

/**
 * SpinBot - a sample robot by Mathew Nelson, and maintained by Flemming N. Larsen
 * <p/>
 * Moves in a circle, firing hard when an enemy is detected
 */
public class SpinBot extends AdvancedRobot {

    /**
     * SpinBot's run method - Circle
     */
    @Override
    public void run() {

    	equip("Division 9 Plasmaprojector");
    	equip("Guardian Heavy Tank Armor");
    	equip("Thorium Power Cell");
    	equip("All terrain tracks");
        // Set colors
        setBodyColor(Color.blue);
        setGunColor(Color.blue);
        setRadarColor(Color.black);
        setScanColor(Color.yellow);
        
        
        while (true) {
            setTurnRight(50);
            execute();
        }
    }

    /**
     * onScannedRobot: Fire hard!
     */
    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        fire(3);
    }

    /**
     * onHitRobot:  If it's our fault, we'll stop turning and moving,
     * so we need to turn again to keep spinning.
     */
    @Override
    public void onHitRobot(HitRobotEvent e) {
        if (e.getBearing() > -10 && e.getBearing() < 10) {
            fire(3);
        }
        if (e.isMyFault()) {
            turnRight(10);
        }
    }
}
