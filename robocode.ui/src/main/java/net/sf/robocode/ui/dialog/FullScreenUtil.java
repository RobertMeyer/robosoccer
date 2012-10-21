/*
 *  Copyright (C) 2012 lee
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.sf.robocode.ui.dialog;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Window;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import net.sf.robocode.ui.IFullScreenListener;

/**
 * @author House Robot Team
 * @author Lee Symes 42636267
 */
public class FullScreenUtil {
// TODO: Implement this as a weak reference to allow for Garbage Collection

    private static final Map<JFrame, WindowInformation> frameInformation = new HashMap<JFrame, WindowInformation>();
    private static final GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

    /**
     * Enables full screen mode availability on OSX 10.7 and later. On other
     * operating systems, this code will not produce exceptions and will do
     * nothing. This code will also add a listener for changing into Full Screen
     * mode. This will fire any listeners added using {@link #addFullScreenListener(net.sf.robocode.ui.IFullScreenListener)
     * }.
     *
     * Code copied from <a
     * href="http://netbeans.org/projects/platform/lists/dev/archive/2011-12/message/226">
     * http://netbeans.org/projects/platform/lists/dev/archive/2011-12/message/226</a>
     *
     *
     */
    public static boolean setWindowCanFullScreen(final JFrame window) {
        if (frameInformation.containsKey(window)) {
            return true; // The window has already been added, so don't do anything.
        } else {
            frameInformation.put(window, new WindowInformation());
            try {
                Class<?> fullScreenUtil = Class.forName("com.apple.eawt.FullScreenUtilities");
                Method m = fullScreenUtil.getDeclaredMethod("setWindowCanFullScreen", Window.class, Boolean.TYPE);
                m.invoke(null, window, true);
                {
                    Class<?> fullScreenListener = Class.forName("com.apple.eawt.FullScreenListener");
                    Object fullScreenListenerImpl;
                    // Create a new listener using a Proxy.
                    // Use the class loader `null`(default class loader)
                    // Implement the classes [`fullScreenListener`(set above)].
                    // And when a function is called, Call the InvocationHandler.
                    fullScreenListenerImpl = Proxy.newProxyInstance(
                            null, new Class[]{fullScreenListener},
                            new InvocationHandler() {
                                JFrame wd = window;

                                @Override
                                /**
                                 * Called when the following methods are called:
                                 * - windowEnteringFullScreen(FullScreenEvent e)
                                 * - windowEnteredFullScreen(FullScreenEvent e)
                                 * - windowExitingFullScreen(FullScreenEvent e)
                                 * - windowExitedFullScreen(FullScreenEvent e)
                                 *
                                 */
                                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                                    if (method.getName().equals("windowEnteredFullScreen")) {
                                        frameInformation.get(wd).fullScreen = true;
                                        fireFullScreenChangedEvent(wd);
                                    } else if (method.getName().equals("windowExitedFullScreen")) {
                                        frameInformation.get(wd).fullScreen = false;
                                        fireFullScreenChangedEvent(wd);
                                    }
                                    return null;
                                }
                            });

                    m = fullScreenUtil.getDeclaredMethod("addFullScreenListenerTo", Window.class, fullScreenListener);
                    m.invoke(null, window, fullScreenListenerImpl);
                }
                frameInformation.get(window).useOsX = true;
                return true; // All setup for OSX.
            } catch (Exception e) {
                // Catch all exceptions.
                // Mainly because the Class.forName is for OS X Only.
                // If error occured or not using OS X, then use the platform
                // Independent Full Screen Support.
                // Before that, Just check to see if it is supported.
                if (device.isFullScreenSupported()) {
                    return true;
                } else {
                    // Not supported, so remove it from the list to be sure.
                    frameInformation.remove(window);
                    return false;
                }
            }
        }
    }

    private static void toggleOsXFullScreen(JFrame frame) {
        try {
            Class<?> app = Class.forName("com.apple.eawt.Application");
            Method m = app.getDeclaredMethod("getApplication", new Class[0]);
            Object application = m.invoke(null, new Object[0]);
            m = app.getDeclaredMethod("requestToggleFullScreen", Window.class);
            m.invoke(application, frame);
        } catch (Exception ex) {
            // Catch all exceptions.
            // Mainly because the Class.forName is for OS X Only.
        }

    }

    public static boolean isMacOSX() {
        return System.getProperty("os.name").startsWith("Mac");
    }

    private static class WindowInformation {

        @SuppressWarnings("unused")
		boolean useOsX = false;
        boolean fullScreen = false;
        final List<IFullScreenListener> listeners = new ArrayList<IFullScreenListener>();
    }

    public static boolean isInFullScreenMode(JFrame frame) {
        if (frameInformation.containsKey(frame)) {
            return frameInformation.get(frame).fullScreen;
        } else {
            return false;
        }
    }

    public static void toggleFullScreen(JFrame frame) {
        if (!frameInformation.containsKey(frame)) {
            return;
        }
        WindowInformation wInfo = frameInformation.get(frame);
        if (isMacOSX()) {
            toggleOsXFullScreen(frame);
        } else {
            if (wInfo.fullScreen) {
                device.setFullScreenWindow(null);
                wInfo.fullScreen = false;
            } else {
                device.setFullScreenWindow(frame);
                wInfo.fullScreen = true;
            }
            fireFullScreenChangedEvent(frame);
        }
    }

    public static void removeFullScreenListener(IFullScreenListener l, JFrame frame) {
        if (frameInformation.containsKey(frame)) {
            frameInformation.get(frame).listeners.remove(l);
        }
    }

    private static void fireFullScreenChangedEvent(JFrame frame) {
        try {
            System.out.println("Debug - fireFullScreenChangedEvent");
            if (!frameInformation.containsKey(frame)) {
                return;
            }
            WindowInformation wInfo = frameInformation.get(frame);
            for (int i = 0; i < wInfo.listeners.size(); i++) {
                try {
                    wInfo.listeners.get(i).fullScreenModeChanged(frame, wInfo.fullScreen);
                } catch (Exception e) {
                    // Just make sure that any exceptions don't affect the other listeners.
                }
            }
        } catch (Exception e) {
            // Just make sure the exceptions don't get passed up the line.
        }
    }

    public static void addFullScreenListener(IFullScreenListener l, JFrame frame) {
        if (!frameInformation.containsKey(frame)) {
            return;
        }
        if (frameInformation.containsKey(frame)) {
            frameInformation.get(frame).listeners.add(l);
        }
    }
}
