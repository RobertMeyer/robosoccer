/*******************************************************************************
 * Copyright (c) 2001-2012 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 *
 * Contributors:
 *     Mathew A. Nelson
 *     - Initial API and implementation
 *******************************************************************************/
package net.sf.robocode.host.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Mathew A. Nelson (original)
 */
public class BufferedPipedInputStream extends InputStream {

    private final BufferedPipedOutputStream out;

    protected BufferedPipedInputStream(BufferedPipedOutputStream out) {
        this.out = out;
    }

    @Override
    public int read() throws IOException {
        return out.read();
    }

    @Override
    public int read(byte b[], int off, int len) throws IOException {
        return out.read(b, off, len);
    }

    @Override
    public int available() {
        return out.available();
    }
}
