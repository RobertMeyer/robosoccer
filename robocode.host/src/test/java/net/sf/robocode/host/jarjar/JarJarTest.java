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
package net.sf.robocode.host.jarjar;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import net.sf.robocode.io.JarJar;
import net.sf.robocode.io.URLJarCollector;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Pavel Savara (original)
 */
public class JarJarTest {

    static {
        JarJarURLConnection.register();
    }

    @Test
    public void run() throws IOException {

        String clas = "Hello.class";
        String inner = "Inner.jar";
        String outer = "file:src/test/resources/Outer.jar";

        final String separ = "!/";
        URL u = new URL("jar:jarjar:" + outer + JarJar.SEPARATOR + inner + separ + clas);
        final URLConnection urlConnection = URLJarCollector.openConnection(u);
        final InputStream inputStream = urlConnection.getInputStream();
        InputStreamReader isr = new InputStreamReader(inputStream);
        char[] c = new char[4];

        int len = isr.read(c);

        Assert.assertEquals(len, 4);
        Assert.assertEquals('T', c[0]);
        Assert.assertEquals('e', c[1]);
        Assert.assertEquals('s', c[2]);
        Assert.assertEquals('t', c[3]);
        Assert.assertFalse(isr.ready());
        isr.close();
        inputStream.close();
    }

    @Test
    public void runClassLoader() throws IOException, ClassNotFoundException {
        String clas = "tested.robots.Ahead";
        String inner = "Inner.jar";
        String outer = "file:src/test/resources/Outer.jar";
        final String separ = "!/";
        final String root = "jar:jarjar:" + outer + JarJar.SEPARATOR + inner + separ;
        URL u = new URL(root);

        ClassLoader ucl = new URLClassLoader(new URL[]{u});

        ucl.loadClass(clas);
    }
}
