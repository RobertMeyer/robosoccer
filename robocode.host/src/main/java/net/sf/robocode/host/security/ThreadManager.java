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
 *     Flemming N. Larsen
 *     - Code cleanup
 *     - Fixed potential NullPointerException in getLoadingRobotProxy()
 *     - Added getRobotClasses() and getRobotProxies() for the
 *       RobocodeSecurityManager
 *     Robert D. Maupin
 *     - Replaced old collection types like Vector and Hashtable with
 *       synchronized List and HashMap
 *******************************************************************************/
package net.sf.robocode.host.security;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.sf.robocode.host.IHostedThread;
import net.sf.robocode.host.IThreadManager;
import net.sf.robocode.host.io.RobotFileOutputStream;
import net.sf.robocode.host.io.RobotFileSystemManager;
import robocode.exception.RobotException;

/**
 * @author Mathew A. Nelson (original)
 * @author Flemming N. Larsen (contributor)
 * @author Robert D. Maupin (contributor)
 */
public class ThreadManager implements IThreadManager {

    private final PrintStream syserr = System.err;
    private final List<Thread> safeThreads = new CopyOnWriteArrayList<Thread>();
    private final List<ThreadGroup> safeThreadGroups = new CopyOnWriteArrayList<ThreadGroup>();
    private final List<ThreadGroup> groups = new CopyOnWriteArrayList<ThreadGroup>();
    private final List<Thread> outputStreamThreads = new CopyOnWriteArrayList<Thread>();
    private final List<IHostedThread> robots = new CopyOnWriteArrayList<IHostedThread>();
    private Thread robotLoaderThread;
    private IHostedThread loadingRobot;

    public ThreadManager() {
    }

    @Override
    public void addSafeThread(Thread safeThread) {
        safeThreads.add(safeThread);
    }

    @Override
    public void removeSafeThread(Thread safeThread) {
        safeThreads.remove(safeThread);
    }

    @Override
    public void addSafeThreadGroup(ThreadGroup safeThreadGroup) {
        safeThreadGroups.add(safeThreadGroup);
    }

    @Override
    public void addThreadGroup(ThreadGroup g, IHostedThread robotProxy) {
        if (!groups.contains(g)) {
            groups.add(g);
            robots.add(robotProxy);
        }
    }

    @Override
    public synchronized IHostedThread getLoadingRobot() {
        return loadingRobot;
    }

    @Override
    public synchronized IHostedThread getLoadingRobotProxy(Thread t) {
        if (t != null && robotLoaderThread != null
                && (t.equals(robotLoaderThread)
                    || (t.getThreadGroup() != null && t.getThreadGroup().equals(robotLoaderThread.getThreadGroup())))) {
            return loadingRobot;
        }
        return null;
    }

    @Override
    public synchronized IHostedThread getLoadedOrLoadingRobotProxy(Thread t) {
        IHostedThread robotProxy = getRobotProxy(t);

        if (robotProxy == null) {
            robotProxy = getLoadingRobotProxy(t);
        }
        return robotProxy;
    }

    @Override
    public IHostedThread getRobotProxy(Thread t) {
        ThreadGroup g = t.getThreadGroup();

        if (g == null) {
            return null;
        }
        int index = groups.indexOf(g);

        if (index == -1) {
            return null;
        }
        return robots.get(index);
    }

    @Override
    public void reset() {
        groups.clear();
        robots.clear();
    }

    @Override
    public synchronized void setLoadingRobot(IHostedThread newLoadingRobotProxy) {
        if (newLoadingRobotProxy == null) {
            this.robotLoaderThread = null;
            loadingRobot = null;
        } else {
            this.robotLoaderThread = Thread.currentThread();
            loadingRobot = newLoadingRobotProxy;
        }
    }

    @Override
    public boolean isSafeThread() {
        return isSafeThread(Thread.currentThread());
    }

    @Override
    public FileOutputStream createRobotFileStream(String fileName, boolean append) throws IOException {
        final Thread c = Thread.currentThread();

        final IHostedThread robotProxy = getRobotProxy(c);

        if (robotProxy == null) {
            syserr.println("RobotProxy is null");
            return null;
        }
        if (!robotProxy.getStatics().isAdvancedRobot()) {
            throw new RobotException("Only advanced robots could create files");
        }

        final File dir = robotProxy.getRobotFileSystemManager().getWritableDirectory();

        if (!dir.exists()) {
            robotProxy.println("SYSTEM: Creating a data directory for you.");
            AccessController.doPrivileged(new PrivilegedAction<Object>() {
                @Override
                public Object run() {
                    outputStreamThreads.add(c);
                    if (!dir.exists() && !dir.mkdirs()) {
                        syserr.println("Can't create dir " + dir.toString());
                    }
                    return null;
                }
            });
        }

        final RobotFileSystemManager fileSystemManager = robotProxy.getRobotFileSystemManager();

        File f = new File(fileName);
        long len;

        if (f.exists()) {
            len = f.length();
        } else {
            fileSystemManager.checkQuota();
            len = 0;
        }

        if (!append) {
            fileSystemManager.adjustQuota(-len);
        }

        outputStreamThreads.add(c);
        return new RobotFileOutputStream(fileName, append, fileSystemManager);
    }

    @Override
    public boolean checkRobotFileStream() {
        final Thread c = Thread.currentThread();

        synchronized (outputStreamThreads) {
            if (outputStreamThreads.contains(c)) {
                outputStreamThreads.remove(c);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isSafeThread(Thread c) {
        try {
            if (safeThreads.contains(c)) {
                return true;
            }

            for (ThreadGroup tg : safeThreadGroups) {
                if (c.getThreadGroup() == tg) {
                    safeThreads.add(c);
                    return true;
                }
            }

            return false;
        } catch (Exception e) {
            syserr.println("Exception checking safe thread: ");
            e.printStackTrace(syserr);
            return false;
        }
    }

    @Override
    public PrintStream getRobotOutputStream() {
        Thread c = Thread.currentThread();

        if (isSafeThread(c)) {
            return null;
        }

        IHostedThread robotProxy = getLoadedOrLoadingRobotProxy(c);

        return (robotProxy != null) ? robotProxy.getOut() : null;
    }

    public void printlnToRobot(String s) {
        final PrintStream stream = getRobotOutputStream();

        if (stream != null) {
            stream.println(s);
        }
    }
}
