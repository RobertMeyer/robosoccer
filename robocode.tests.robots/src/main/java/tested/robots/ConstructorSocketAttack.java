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

import java.io.*;
import java.net.*;
import robocode.AdvancedRobot;

/**
 * @author Flemming N. Larsen (original)
 */
public class ConstructorSocketAttack extends AdvancedRobot {

    static ByteArrayOutputStream baos = new ByteArrayOutputStream();

    static {
        ServerSocket server = null;
        Socket client = null;

        try {
            server = new ServerSocket(9999);
            client = server.accept();

            InputStream is = new DataInputStream(client.getInputStream());
            OutputStream os = new PrintStream(client.getOutputStream());

            os.write(1);
            is.read();
        } catch (IOException e) {
            // The stack trace from the exception is redirected to a temporary buffer,
            // which is read and written out to the robot's output in the run() method.

            PrintStream ps = new PrintStream(baos);

            e.printStackTrace(ps);
            ps.flush();
        } finally {
            if (server != null) {
                try {
                    server.close();
                } catch (IOException e) {
                }
            }
            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                }
            }
        }
    }

    @Override
    public void run() {
        // Write out the buffered output
        out.append(baos.toString());
    }
}
