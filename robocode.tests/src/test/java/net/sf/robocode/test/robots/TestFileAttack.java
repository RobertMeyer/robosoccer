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

import java.io.File;
import net.sf.robocode.test.helpers.Assert;
import net.sf.robocode.test.helpers.RobocodeTestBed;
import org.junit.Ignore;
import org.junit.Test;
import robocode.control.events.TurnEndedEvent;

/**
 * @author Pavel Savara (original)
 */
@Ignore("This test has been unreliable in student builds, and is not important for CSSE2003")
public class TestFileAttack extends RobocodeTestBed {

    boolean messagedWrite;
    boolean messagedRead;

    @Test
    @Override
    public void run() {
        super.run();
    }

    @Override
    public void onTurnEnded(TurnEndedEvent event) {
        super.onTurnEnded(event);
        final String out = event.getTurnSnapshot().getRobots()[1].getOutputStreamSnapshot();

        if (out.contains("Preventing tested.robots.FileAttack from access: (java.io.FilePermission C:\\MSDOS.SYS read)")) {
            messagedRead = true;
        }
        if (out.contains(
                "Preventing tested.robots.FileAttack from access: (java.io.FilePermission C:\\Robocode.attack write)")) {
            messagedWrite = true;
        }
    }

    @Override
    public String getRobotNames() {
        return "sample.Fire,tested.robots.FileAttack";
    }

    @Override
    protected void runSetup() {
        File attack = new File("C:\\Robocode.attack");

        if (attack.exists()) {
            Assert.assertTrue(attack.delete());
        }
    }

    @Override
    protected void runTeardown() {
        Assert.assertTrue("Didn't seen preventing read", messagedRead);
        Assert.assertTrue("Didn't seen preventing write", messagedWrite);
        Assert.assertFalse("Found attack file", new File("C:\\Robocode.attack").exists());
    }

    @Override
    protected int getExpectedErrors() {
        return 2; // Security error must be reported as an error
    }
}
