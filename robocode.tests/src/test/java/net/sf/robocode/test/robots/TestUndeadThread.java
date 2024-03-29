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
import org.junit.Ignore;
import org.junit.Test;
import robocode.control.events.BattleErrorEvent;

/**
 * @author Pavel Savara (original)
 */
@Ignore("is very time consuming test, please run explicitly if you did something to security or timing")
public class TestUndeadThread extends RobocodeTestBed {

    boolean messagedStop;
    boolean messagedForcing;

    @Test
    @Override
    public void run() {
        super.run();
    }

    @Override
    public void onBattleError(BattleErrorEvent event) {
        super.onBattleError(event);
        final String error = event.getError();

        if (error.contains("is not stopping.  Forcing a stop.")) {
            messagedForcing = true;
        }
        if (error.contains("Unable to stop thread")) {
            messagedStop = true;
        }
    }

    @Override
    protected int getExpectedErrors() {
        return 3;
    }

    @Override
    public String getRobotNames() {
        return "sample.SittingDuck,tested.robots.UndeadThread";
    }

    @Override
    protected void runTeardown() {
        Assert.assertTrue(messagedForcing);
        Assert.assertTrue(messagedStop);
    }
}
