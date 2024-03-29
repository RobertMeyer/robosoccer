
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
import robocode.Robot;

/**
 * @author Pavel Savara (original)
 */
public class NoPackageButReallyLongNameIWouldSayTooLongMaybeEventLonger extends Robot {

    @Override
    public void run() {
        while (true) {
            ahead(1); // Move ahead 100
            turnGunRight(360); // Spin gun around
            back(1); // Move back 100
            turnGunRight(360); // Spin gun around
        }
    }
}
