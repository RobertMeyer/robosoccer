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
package net.sf.robocode.test.robots;

import net.sf.robocode.test.helpers.Assert;
import net.sf.robocode.test.helpers.RobocodeTestBed;
import org.junit.Test;
import robocode.control.events.TurnEndedEvent;
import robocode.control.snapshot.IRobotSnapshot;

/**
 * @author Pavel Savara (original)
 */
public class TestPrivateConstructor extends RobocodeTestBed {

    boolean messaged;

    @Test
    @Override
    public void run() {
        super.run();
    }

    @Override
    public void onTurnEnded(final TurnEndedEvent event) {
        super.onTurnEnded(event);
        final IRobotSnapshot robot = event.getTurnSnapshot().getRobots()[1];

        if (robot.getOutputStreamSnapshot().contains("SYSTEM: Is your constructor marked public?")) {
            messaged = true;
        }
        Assert.assertNear(0, robot.getEnergy());
    }

    @Override
    public String getRobotNames() {
        return "sample.Fire,tested.robots.PrivateConstructor";
    }

    @Override
    protected void runTeardown() {
        Assert.assertTrue(messaged);
    }

    @Override
    protected int getExpectedErrors() {
        return 1; // Security error must be reported as an error
    }
}
