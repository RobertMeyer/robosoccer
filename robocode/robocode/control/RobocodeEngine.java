/*******************************************************************************
 * Copyright (c) 2001, 2007 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/cpl-v10.html
 *
 * Contributors:
 *     Mathew A. Nelson
 *     - Initial API and implementation
 *     Flemming N. Larsen
 *     - Code cleanup
 *     - Replaced FileSpecificationVector with plain Vector
 *     - GUI is disabled per default. If the setVisible() is called, the GUI will
 *       be enabled. The close() method is only calling dispose() on the
 *       RobocodeFrame if the GUI is enabled
 *     - Updated to use methods from FileUtil and Logger, which replaces
 *       methods that have been (re)moved from the robocode.util.Utils class
 *     - Changed to use FileUtil.getRobotsDir()
 *     - Modified getLocalRepository() to support teams by using
 *       FileSpecification instead of RobotSpecification
 *     - Bugfix: The original System.out, System.err, and System.in were not
 *       restored when the RobocodeEngine was closed, and the secured outputs
 *       were not closed. This caused memory leaks and the RobocodeEngine to
 *       become slower and slower for each new instance of RobocodeEngine 
 *     Robert D. Maupin
 *     - Replaced old collection types like Vector and Hashtable with
 *       synchronized List and HashMap
 *******************************************************************************/
package robocode.control;


import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.security.Policy;
import java.util.List;

import robocode.RobocodeFileOutputStream;
import robocode.io.FileUtil;
import robocode.io.Logger;
import robocode.manager.RobocodeManager;
import robocode.repository.FileSpecification;
import robocode.repository.Repository;
import robocode.security.RobocodeSecurityManager;
import robocode.security.RobocodeSecurityPolicy;
import robocode.security.SecureInputStream;
import robocode.security.SecurePrintStream;


/**
 * RobocodeEngine - Class for controlling Robocode.
 *
 * @see <a target="_top" href="http://robocode.sourceforge.net">robocode.sourceforge.net</a>
 *
 * @author Mathew A. Nelson (original)
 * @author Flemming N. Larsen (contributor)
 * @author Robert D. Maupin (contributor)
 */
public class RobocodeEngine {
	private static final PrintStream ORIG_SYSTEM_OUT = System.out;
	private static final PrintStream ORIG_SYSTEM_ERR = System.err;
	private static final InputStream ORIG_SYSTEM_IN = System.in;
	
	private RobocodeListener listener;
	private RobocodeManager manager;

	private PrintStream sysout;
	private PrintStream syserr;
	private InputStream sysin;

	/**
	 * Creates a new RobocodeEngine
	 *
	 * @param robocodeHome should be the root robocode directory (i.e. c:\robocode)
	 * @param listener Your listener
	 */
	public RobocodeEngine(File robocodeHome, RobocodeListener listener) {
		init(robocodeHome, listener);
	}

	/**
	 * Creates a new RobocodeEngine using robocode.jar to determine the robocodeHome file.
	 * @param listener Your listener
	 */
	public RobocodeEngine(RobocodeListener listener) {
		File robotsDir = FileUtil.getRobotsDir();

		if (robotsDir.exists()) {
			init(FileUtil.getCwd(), listener);
		} else {
			throw new RuntimeException("File not found: " + robotsDir);
		}
	}

	@Override
	public void finalize() {
		// Make sure close() is called to prevent memory leaks
		close();
	}

	private void init(File robocodeHome, RobocodeListener listener) {
		this.listener = listener;
		manager = new RobocodeManager(true, listener);
		manager.setEnableGUI(false);

		try {
			FileUtil.setCwd(robocodeHome);
		} catch (IOException e) {
			System.err.println(e);
			return;
		}
		Thread.currentThread().setName("Application Thread");
		RobocodeSecurityPolicy securityPolicy = new RobocodeSecurityPolicy(Policy.getPolicy());

		Policy.setPolicy(securityPolicy);
		System.setSecurityManager(new RobocodeSecurityManager(Thread.currentThread(), manager.getThreadManager(), true));
		RobocodeFileOutputStream.setThreadManager(manager.getThreadManager());

		ThreadGroup tg = Thread.currentThread().getThreadGroup();

		while (tg != null) {
			((RobocodeSecurityManager) System.getSecurityManager()).addSafeThreadGroup(tg);
			tg = tg.getParent();
		}

		// Secure System.in, System.err, System.out
		sysout = new SecurePrintStream(System.out, true, "System.out");
		syserr = new SecurePrintStream(System.err, true, "System.err");
		sysin = new SecureInputStream(System.in, "System.in");

		System.setOut(sysout);
		if (!System.getProperty("debug", "false").equals("true")) {
			System.setErr(syserr);
		}
		System.setIn(sysin);
	}

	/**
	 * Call this when you are finished with this RobocodeEngine.
	 * This method disposes the Robocode window
	 */
	public void close() {
		if (manager.isGUIEnabled()) {
			manager.getWindowManager().getRobocodeFrame().dispose();
		}

		// Restore the original System.in, System.err, System.out
		System.setOut(ORIG_SYSTEM_OUT);
		System.setErr(ORIG_SYSTEM_ERR);
		System.setIn(ORIG_SYSTEM_IN);

		// Make sure the secured System.out and System.err is closed.
		// Otherwise, these will cause memory leaks
		sysout.close();
		syserr.close();
	}

	/**
	 * Returns the installed version of Robocode.
	 *
	 * @return the installed version of Robocode.
	 */
	public String getVersion() {
		return manager.getVersionManager().getVersion();
	}

	/**
	 * Shows or hides the Robocode window.
	 */
	public void setVisible(boolean visible) {
		if (visible) {
			// The GUI must be enabled in order to show the window
			manager.setEnableGUI(true);
		}
		if (manager.isGUIEnabled()) {
			// Set the Look and Feel (LAF)
			robocode.manager.LookAndFeelManager.setLookAndFeel();
	
			manager.getWindowManager().showRobocodeFrame(visible);
		}
	}

	/**
	 * Gets a list of robots available for battle.
	 *
	 * @return An array of all available robots.
	 */
	public RobotSpecification[] getLocalRepository() {
		Repository robotRepository = manager.getRobotRepositoryManager().getRobotRepository();
		List<FileSpecification> list = robotRepository.getRobotSpecificationsList(false, false, false, false, false,
				false);
		RobotSpecification robotSpecs[] = new RobotSpecification[list.size()];

		for (int i = 0; i < robotSpecs.length; i++) {
			robotSpecs[i] = new RobotSpecification(list.get(i));
		}
		return robotSpecs;
	}

	/**
	 * Runs a battle
	 */
	public void runBattle(BattleSpecification battle) {
		Logger.setLogListener(listener);

		manager.getBattleManager().startNewBattle(battle, false);
	}

	/**
	 * Asks a battle to abort.
	 */
	public void abortCurrentBattle() {
		manager.getBattleManager().stop();
	}
}
