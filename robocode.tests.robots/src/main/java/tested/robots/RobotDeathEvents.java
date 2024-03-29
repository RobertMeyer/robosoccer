/*******************************************************************************
 * Copyright (c) 2001-2012 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 *
 * Contributors:
 *     Flemming N. Larsen
 *     - Initial implementation
 *******************************************************************************/
package tested.robots;

import robocode.AdvancedRobot;
import robocode.DeathEvent;
import robocode.RobotDeathEvent;

/**
 * @author Flemming N. Larsen (original)
 */
public class RobotDeathEvents extends AdvancedRobot {

    private boolean dead;
    private long enemyCount;

    @Override
    public void run() {
        enemyCount = getOthers();
        while (!dead) {
            if (enemyCount != getOthers()) {
                throw new RuntimeException("enemyCount != getOthers()");
            }
            execute();
        }
    }

    @Override
    public void onRobotDeath(RobotDeathEvent e) {
        enemyCount--;
    }

    @Override
    public void onDeath(DeathEvent e) {
        dead = true;
    }
}
