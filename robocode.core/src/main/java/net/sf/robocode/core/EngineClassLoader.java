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
package net.sf.robocode.core;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Pavel Savara (original)
 */
public class EngineClassLoader extends URLClassLoader {

    private static final boolean isSecutityOn = !System.getProperty("NOSECURITY", "false").equals("true");
    private static Set<String> exclusions = new HashSet<String>();

    static {
        // this will be loaded on system classloader
        exclusions.add(EngineClassLoader.class.getName());
        exclusions.add(Container.class.getName());
        exclusions.add(RobocodeMainBase.class.getName());

        // .NET proxies and their interfaces must be loaded in system class loader in order to call native methods
        exclusions.add("net.sf.robocode.host.IHost");
        exclusions.add("net.sf.robocode.host.IHostManager");
        exclusions.add("net.sf.robocode.host.proxies.IHostingRobotProxy");
        exclusions.add("net.sf.robocode.peer.IRobotPeer");
        exclusions.add("net.sf.robocode.repository.IRobotRepositoryItem");
        exclusions.add("net.sf.robocode.repository.RobotType");
        exclusions.add("net.sf.robocode.host.RobotStatics");
        exclusions.add("net.sf.robocode.peer.BadBehavior");
        exclusions.add("net.sf.robocode.dotnet.host.DotnetHost");
        exclusions.add("net.sf.robocode.dotnet.repository.root.DllRootHelper");
        exclusions.add("net.sf.robocode.dotnet.nhost.ModuleN");
        exclusions.add("net.sf.robocode.host.proxies.__IHostingRobotProxy");
    }

    public static void addExclusion(String classFullName) {
        exclusions.add(classFullName);
    }

    public EngineClassLoader(ClassLoader parent) {
        super(Container.findJars(File.separator + "robocode."), parent);
    }

    public EngineClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    @Override
    public synchronized void addURL(URL url) {
        super.addURL(url);
    }

    @Override
    public synchronized Class<?> loadClass(String name, boolean resolve)
            throws ClassNotFoundException {
        if (name.startsWith("java.lang")) {
            // we always delegate java.lang stuff to parent loader
            return super.loadClass(name, resolve);
        }
        if (isSecutityOn && isEngineClass(name)) {
            // yes, it is in engine's classpath
            // we load it localy
            return loadEngineClass(name, resolve);
        }
        // it is robot API
        // or java class
        // or security is off
        // so we delegate to parent classloader
        return super.loadClass(name, resolve);
    }

    private Class<?> loadEngineClass(String name, boolean resolve) throws ClassNotFoundException {
        Class<?> result = findLoadedClass(name);

        if (result == null) {
            result = findClass(name);
        }
        if (resolve) {
            resolveClass(result);
        }
        return result;
    }

    private boolean isEngineClass(String name) {
        if (name.startsWith("net.sf.robocode") || name.startsWith("robocode.control")) {
            if (exclusions.contains(name)) {
                return false;
            }
            // try to find it in engine's classpath
            // this is URL, don't change to File.pathSeparator
            final String path = name.replace('.', '/').concat(".class");

            return AccessController.doPrivileged(new PrivilegedAction<Boolean>() {
                @Override
                public Boolean run() {
                    return findResource(path) != null;
                }
            });
        }
        return false;
    }
}
