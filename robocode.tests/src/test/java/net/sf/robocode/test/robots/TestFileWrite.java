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
import net.sf.robocode.io.Logger;
import net.sf.robocode.test.helpers.RobocodeTestBed;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Pavel Savara (original)
 */
public class TestFileWrite extends RobocodeTestBed {

    @Test
    @Override
    public void run() {
        super.run();
    }

    @Override
    public String getRobotNames() {
        return "sample.Walls,sample.SittingDuck";
    }
    File file = new File(robotsPath, "/target/classes/sample/SittingDuck.data/count.dat");

    @Override
    protected void runSetup() {
        if (file.exists()) {
            if (!file.delete()) {
                Logger.logError("Can't delete" + file);
            }
        }
    }

    @Override
    protected void runTeardown() {
        Assert.assertTrue(file.exists());
        if (!file.delete()) {
            Logger.logError("Can't delete" + file);
        }
    }
}
