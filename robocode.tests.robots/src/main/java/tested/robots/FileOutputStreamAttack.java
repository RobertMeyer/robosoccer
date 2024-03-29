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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import robocode.AdvancedRobot;

/**
 * @author Flemming N. Larsen (original)
 */
public class FileOutputStreamAttack extends AdvancedRobot {

    @Override
    public void run() {
        File file = null;
        FileOutputStream fis = null;
        try {
            file = getDataFile("test");
            fis = new FileOutputStream(file);
            fis.write(1);
        } catch (IOException e) {
            e.printStackTrace(out);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException ignore) {
                }
            }
            if (file != null) {
                file.delete();
            }
        }
    }
}
