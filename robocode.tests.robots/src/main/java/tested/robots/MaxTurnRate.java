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

/**
 * @author Flemming N. Larsen (original)
 */
public class MaxTurnRate extends robocode.AdvancedRobot {

    @Override
    public void run() {

        setTurnLeft(Double.POSITIVE_INFINITY);

        for (int i = 0; i < 13; i++) {
            setMaxTurnRate(i);
            executeAndDumpTurnRate();
        }

        setTurnRight(Double.POSITIVE_INFINITY);

        for (int i = 0; i < 13; i++) {
            setMaxTurnRate(i);
            executeAndDumpTurnRate();
        }
    }

    private void executeAndDumpTurnRate() {
        double lastHeading = getHeading();

        execute();

        double turnRate = robocode.util.Utils.normalRelativeAngleDegrees(getHeading() - lastHeading);

        out.println(getTime() + ": " + getVelocity() + ", " + turnRate);
    }
}
