/*******************************************************************************
 * Copyright (c) 2001-2012 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 *
 * Contributors:
 *     Pavel Savara
 *     - Initial implementation
 *******************************************************************************/
package sampleex;

import robocode.AdvancedRobot;
import robocode.HitByBulletEvent;
import robocode.ScannedRobotEvent;

/**
 * This is robot derived from AdvancedRobot.
 * Only reason to use this inheritance and this class is that external robots are unable to call RobotPeer directly
 */
class Slave extends AdvancedRobot {

    final MasterBase parent;

    public Slave(MasterBase parent) {
        this.parent = parent;
    }

    @Override
    public void run() {
        parent.run();
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        parent.onScannedRobot(e);
    }

    @Override
    public void onHitByBullet(HitByBulletEvent e) {
        parent.onHitByBullet(e);
    }
}
