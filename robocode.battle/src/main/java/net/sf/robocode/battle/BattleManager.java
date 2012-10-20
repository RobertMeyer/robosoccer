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
 *     - Code cleanup & optimizations
 *     - Removed getBattleView().setDoubleBuffered(false) as BufferStrategy is
 *       used now
 *     - Replaced FileSpecificationVector, RobotPeerVector, and
 *       RobotClassManagerVector with plain Vector
 *     - Added check for if GUI is enabled before using graphical components
 *     - Added restart() method
 *     - Ported to Java 5
 *     - Added support for the replay feature
 *     - Removed the clearBattleProperties()
 *     - Updated to use methods from FileUtil and Logger, which replaces methods
 *       that have been (re)moved from the robocode.util.Utils class
 *     - Added PauseResumeListener interface, addListener(), removeListener(),
 *       notifyBattlePaused(), notifyBattleResumed() for letting listeners
 *       receive notifications when the game is paused or resumed
 *     - Added missing functionality in to support team battles in
 *       startNewBattle(BattleSpecification spec, boolean replay)
 *     - Added missing close() on FileInputStreams and FileOutputStreams
 *     - isPaused() is now synchronized
 *     - Extended sendResultsToListener() to handle teams as well as robots
 *     - Added setDefaultBattleProperties() for resetting battle properties
 *     - Removed the showResultsDialog parameter from the stop() method
 *     - Added null pointer check to the sendResultsToListener() method
 *     - Enhanced the getBattleFilename() to look into the battle dir and also
 *       add the .battle file extension to the returned file name if this is
 *       missing
 *     - Removed battleRunning field, isBattleRunning(), and setBattle()
 *     - Bugfix: Multiple battle threads could run in the same time when the
 *       battle thread was started in startNewBattle()
 *     Luis Crespo
 *     - Added debug step feature, including the nextTurn(), shouldStep(),
 *       startNewRound()
 *     Robert D. Maupin
 *     - Replaced old collection types like Vector and Hashtable with
 *       synchronized List and HashMap
 *     Nathaniel Troutman
 *     - Bugfix: Added cleanup() to prevent memory leaks by removing circular
 *       references
 *     Pavel Savara
 *     - now driven by BattleObserver and commands to battle
 *     - initial code of battle recorder and player
 *******************************************************************************/
package net.sf.robocode.battle;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import net.sf.robocode.battle.events.BattleEventDispatcher;
import net.sf.robocode.battle.peer.RobotPeer;
import net.sf.robocode.core.Container;
import net.sf.robocode.host.ICpuManager;
import net.sf.robocode.host.IHostManager;
import net.sf.robocode.io.FileUtil;
import net.sf.robocode.io.Logger;
import static net.sf.robocode.io.Logger.logError;
import static net.sf.robocode.io.Logger.logMessage;
import net.sf.robocode.mode.ClassicMode;
import net.sf.robocode.recording.BattlePlayer;
import net.sf.robocode.recording.IRecordManager;
import net.sf.robocode.repository.IRepositoryManager;
import net.sf.robocode.settings.ISettingsManager;
import robocode.Event;
import robocode.control.BattleSpecification;
import robocode.control.BattlefieldSpecification;
import robocode.control.RandomFactory;
import robocode.control.RobotSpecification;
import robocode.control.events.BattlePausedEvent;
import robocode.control.events.BattleResumedEvent;
import robocode.control.events.IBattleListener;

/**
 * @author Mathew A. Nelson (original)
 * @author Flemming N. Larsen (contributor)
 * @author Luis Crespo (contributor)
 * @author Robert D. Maupin (contributor)
 * @author Nathaniel Troutman (contributor)
 * @author Pavel Savara (contributor)
 * @author CSSE2003 Team Forkbomb (equipment)
 */
public class BattleManager implements IBattleManager {

	private final ISettingsManager properties;
	private final IHostManager hostManager;
	private final ICpuManager cpuManager;
	private final IRecordManager recordManager;
	private final IRepositoryManager repositoryManager;
	private volatile IBattle battle;
	private BattleProperties battleProperties = new BattleProperties();
	private final BattleEventDispatcher battleEventDispatcher;
	private String battleFilename;
	private String battlePath;
	private int pauseCount = 0;
	private final AtomicBoolean isManagedTPS = new AtomicBoolean(false);

	// To store the Spike position
	private ArrayList<Integer> spikePosX = new ArrayList<Integer>();
	private ArrayList<Integer> spikePosY = new ArrayList<Integer>();

	public BattleManager(ISettingsManager properties,
			IRepositoryManager repositoryManager, IHostManager hostManager,
			ICpuManager cpuManager,
			BattleEventDispatcher battleEventDispatcher,
			IRecordManager recordManager) {
		this.properties = properties;
		this.recordManager = recordManager;
		this.repositoryManager = repositoryManager;
		this.cpuManager = cpuManager;
		this.hostManager = hostManager;
		this.battleEventDispatcher = battleEventDispatcher;
		Logger.setLogListener(battleEventDispatcher);
	}

	@Override
	public synchronized void cleanup() {
		if (battle != null) {
			battle.waitTillOver();
			battle.cleanup();
			battle = null;
		}
	}

	// Called when starting a new battle from GUI
	@Override
	public void startNewBattle(BattleProperties battleProperties,
			boolean waitTillOver, boolean enableCLIRecording) {
		this.battleProperties = battleProperties;
		final RobotSpecification[] robots = repositoryManager
				.loadSelectedRobots(battleProperties.getSelectedRobots());

		startNewBattleImpl(robots, waitTillOver, enableCLIRecording);
	}

	// Called from the RobocodeEngine
	@Override
	public void startNewBattle(BattleSpecification spec,
			String initialPositions, boolean waitTillOver,
			boolean enableCLIRecording) {
		battleProperties = new BattleProperties();

		BattlefieldSpecification battlefield = spec.getBattlefield();
		battleProperties.setBattlefieldWidth(battlefield.getWidth());
		battleProperties.setBattlefieldHeight(battlefield.getHeight());

		battleProperties.setGunCoolingRate(spec.getGunCoolingRate());
		battleProperties.setInactivityTime(spec.getInactivityTime());
		battleProperties.setNumRounds(spec.getNumRounds());
		battleProperties.setHideEnemyNames(spec.getHideEnemyNames());
		battleProperties.setSelectedRobots(spec.getRobots());
		battleProperties.setInitialPositions(initialPositions);

		battleProperties.setEquipmentFile(spec.getEquipmentFile());
		battleProperties.setBattleMode(new ClassicMode());

		final RobotSpecification[] robots = repositoryManager
				.loadSelectedRobots(spec.getRobots());

		startNewBattleImpl(robots, waitTillOver, enableCLIRecording);
	}

	private void startNewBattleImpl(RobotSpecification[] battlingRobotsList,
			boolean waitTillOver, boolean enableCLIRecording) {
		stop(true);

		logMessage("Preparing battle...");

		final boolean recording = (properties
				.getOptionsCommonEnableReplayRecording() && System.getProperty(
				"TESTING", "none").equals("none"))
				|| enableCLIRecording;

		if (recording) {
			recordManager.attachRecorder(battleEventDispatcher);
		} else {
			recordManager.detachRecorder();
		}

		// resets seed for deterministic behavior of Random
		final String seed = System.getProperty("RANDOMSEED", "none");

		if (!seed.equals("none")) {
			// init soon as it reads random
			cpuManager.getCpuConstant();

			RandomFactory.resetDeterministic(Long.valueOf(seed));
		}

		Battle realBattle = Container.createComponent(Battle.class);

		realBattle.setup(battlingRobotsList, battleProperties, isPaused(),
				repositoryManager);

		battle = realBattle;

		Thread battleThread = new Thread(Thread.currentThread()
				.getThreadGroup(), realBattle);

		battleThread.setPriority(Thread.NORM_PRIORITY);
		battleThread.setName("Battle Thread");
		realBattle.setBattleThread(battleThread);

		if (!System.getProperty("NOSECURITY", "false").equals("true")) {
			hostManager.addSafeThread(battleThread);
		}

		// Start the realBattle thread
		battleThread.start();

		// Wait until the realBattle is running and ended. This must be done as
		// a new realBattle could be started immediately after this one causing
		// multiple realBattle threads to run at the same time, which must be
		// prevented!
		realBattle.waitTillStarted();
		if (waitTillOver) {
			realBattle.waitTillOver();
		}
	}

	@Override
	public void waitTillOver() {
		if (battle != null) {
			battle.waitTillOver();
		}
	}

	private void replayBattle() {
		if (!recordManager.hasRecord()) {
			return;
		}
		logMessage("Preparing replay...");

		if (battle != null && battle.isRunning()) {
			battle.stop(true);
		}

		Logger.setLogListener(battleEventDispatcher);

		recordManager.detachRecorder();
		battle = Container.createComponent(BattlePlayer.class);

		Thread battleThread = new Thread(Thread.currentThread()
				.getThreadGroup(), battle);

		battleThread.setPriority(Thread.NORM_PRIORITY);
		battleThread.setName("BattlePlayer Thread");

		// Start the battlePlayer thread
		battleThread.start();
	}

	@Override
	public String getBattleFilename() {
		return battleFilename;
	}

	@Override
	public void setBattleFilename(String newBattleFilename) {
		if (newBattleFilename != null) {
			battleFilename = newBattleFilename.replace(
					(File.separatorChar == '/') ? '\\' : '/',
					File.separatorChar);

			if (battleFilename.indexOf(File.separatorChar) < 0) {
				try {
					battleFilename = FileUtil.getBattlesDir()
							.getCanonicalPath()
							+ File.separatorChar
							+ battleFilename;
				} catch (IOException ignore) {
				}
			}
			if (!battleFilename.endsWith(".battle")) {
				battleFilename += ".battle";
			}
		}
	}

	@Override
	public String getBattlePath() {
		if (battlePath == null) {
			battlePath = System.getProperty("BATTLEPATH");
			if (battlePath == null) {
				battlePath = "battles";
			}
			battlePath = new File(FileUtil.getCwd(), battlePath)
					.getAbsolutePath();
		}
		return battlePath;
	}

	@Override
	public void saveBattleProperties() {
		if (battleProperties == null) {
			logError("Cannot save null battle properties");
			return;
		}
		if (battleFilename == null) {
			logError("Cannot save battle to null path, use setBattleFilename()");
			return;
		}
		FileOutputStream out = null;

		try {
			out = new FileOutputStream(battleFilename);

			battleProperties.store(out, "Battle Properties");
		} catch (IOException e) {
			logError("IO Exception saving battle properties: " + e);
		} finally {
			FileUtil.cleanupStream(out);
		}
	}

	@Override
	public BattleProperties loadBattleProperties() {
		BattleProperties res = new BattleProperties();
		FileInputStream in = null;

		try {
			in = new FileInputStream(getBattleFilename());
			res.load(in);
		} catch (FileNotFoundException e) {
			logError("No file " + battleFilename + " found, using defaults.");
		} catch (IOException e) {
			logError("Error while reading " + getBattleFilename() + ": " + e);
		} finally {
			FileUtil.cleanupStream(in);
		}
		return res;
	}

	@Override
	public BattleProperties getBattleProperties() {
		if (battleProperties == null) {
			battleProperties = new BattleProperties();
		}
		return battleProperties;
	}

	@Override
	public void setDefaultBattleProperties() {
		battleProperties = new BattleProperties();
	}

	@Override
	public boolean isManagedTPS() {
		return isManagedTPS.get();
	}

	@Override
	public void setManagedTPS(boolean value) {
		isManagedTPS.set(value);
	}

	@Override
	public synchronized void addListener(IBattleListener listener) {
		battleEventDispatcher.addListener(listener);
	}

	@Override
	public synchronized void removeListener(IBattleListener listener) {
		battleEventDispatcher.removeListener(listener);
	}

	@Override
	public synchronized void stop(boolean waitTillEnd) {
		if (battle != null && battle.isRunning()) {
			battle.stop(waitTillEnd);
		}
	}

	@Override
	public synchronized void restart() {
		// Start new battle. The old battle is automatically stopped
		startNewBattle(battleProperties, false, false);
	}

	@Override
	public synchronized void replay() {
		replayBattle();
	}

	private boolean isPaused() {
		return (pauseCount != 0);
	}

	@Override
	public synchronized void togglePauseResumeBattle() {
		if (isPaused()) {
			resumeBattle();
		} else {
			pauseBattle();
		}
	}

	@Override
	public synchronized void pauseBattle() {
		if (++pauseCount == 1) {
			if (battle != null && battle.isRunning()) {
				battle.pause();
			} else {
				battleEventDispatcher.onBattlePaused(new BattlePausedEvent());
			}
		}
	}

	@Override
	public synchronized void pauseIfResumedBattle() {
		if (pauseCount == 0) {
			pauseCount++;
			if (battle != null && battle.isRunning()) {
				battle.pause();
			} else {
				battleEventDispatcher.onBattlePaused(new BattlePausedEvent());
			}
		}
	}

	@Override
	public synchronized void resumeIfPausedBattle() {
		if (pauseCount == 1) {
			pauseCount--;
			if (battle != null && battle.isRunning()) {
				battle.resume();
			} else {
				battleEventDispatcher.onBattleResumed(new BattleResumedEvent());
			}
		}
	}

	@Override
	public synchronized void resumeBattle() {
		if (--pauseCount < 0) {
			pauseCount = 0;
			logError("SYSTEM: pause game bug!");
		} else if (pauseCount == 0) {
			if (battle != null && battle.isRunning()) {
				battle.resume();
			} else {
				battleEventDispatcher.onBattleResumed(new BattleResumedEvent());
			}
		}
	}

	public Battle getBattle() {
		return (Battle) battle;
	}

	/**
	 * Retrieve the list of all robots and do a comparison on the energy level
	 * of all the robots. During the comparison, kill the robot with the lower
	 * energy level. If both robot have the same energy level, randomly pick and
	 * kill one robot. Only the top energy level robot will survive.
	 */
	@Override
	public void getTopRobot() {
		List<RobotPeer> robotList = ((Battle) battle).getRobotList();
		double currentRobotEnergy = 0;
		double topRobotEnergy = 0;
		int topRobotIndex = 0;

		for (int i = 0; i < robotList.size(); i++) {
			// Get first robot energy level for comparison
			if (i == 0) {
				topRobotEnergy = robotList.get(i).getEnergy();
				topRobotIndex = i;
			}
			// Do robot energy level comparison
			if (i > 0) {
				currentRobotEnergy = robotList.get(i).getEnergy();
				// Randomly pick and kill one robot if their energy level is the
				// same
				if (topRobotEnergy == currentRobotEnergy) {
					Random random = new Random();
					int ranNum = random.nextInt(5);
					if (ranNum >= 3) {
						robotList.get(i).kill();
					} else {
						robotList.get(topRobotIndex).kill();
						topRobotIndex = i;
						topRobotEnergy = robotList.get(i).getEnergy();
					}
					// Kill the robot with a lower energy level
				} else if (topRobotEnergy > currentRobotEnergy) {
					robotList.get(i).kill();
				} else {
					robotList.get(topRobotIndex).kill();
					topRobotIndex = i;
					topRobotEnergy = robotList.get(i).getEnergy();
				}
			}
		}
	}

	/**
	 * Retrieve the list of all robots and do a comparison on the energy level
	 * of all the robots. During the comparison, save the index of the robot
	 * with the lowest energy level. After the comparison, kill the robot with
	 * the lowest energy level.
	 */
	@Override
	public void eliminateWeakestRobot() {
		List<RobotPeer> robotList = ((Battle) battle).getRobotList();
		double lowestEnergy = 101;
		int lowestEnergyIndex = 0;

		// Find the robot with the lowest energy level
		for (int i = 0; i < robotList.size(); i++) {
			if (robotList.get(i).getEnergy() <= lowestEnergy
					&& robotList.get(i).getEnergy() != 0) {
				lowestEnergyIndex = i;
				lowestEnergy = robotList.get(i).getEnergy();
			}
		}
		// Kill robot with the lowest energy level
		robotList.get(lowestEnergyIndex).kill();
	}

	/**
	 * Save the position of all spikes for Spike mode
	 * 
	 * @param spikeArrayPosX
	 *            the X-axis of all spikes
	 * @param spikeArrayPosY
	 *            the Y-axis of all spikes
	 */
	@Override
	public void saveSpikePos(ArrayList<Integer> spikeArrayPosX,
			ArrayList<Integer> spikeArrayPosY) {
		spikePosX = spikeArrayPosX;
		spikePosY = spikeArrayPosY;
	}

	/**
	 * Return the X-axis of all spikes for Spike mode
	 * 
	 * @return X-axis of all spikes
	 */
	@Override
	public ArrayList<Integer> getSpikePosX() {
		return spikePosX;
	}

	/**
	 * Return the Y-axis of all spikes for Spike mode
	 * 
	 * @return Y-axis of all spikes
	 */
	@Override
	public ArrayList<Integer> getSpikePosY() {
		return spikePosY;
	}

	/**
	 * Steps for a single turn, then goes back to paused
	 */
	@Override
	public synchronized void nextTurn() {
		if (battle != null && battle.isRunning()) {
			battle.step();
		}
	}

	@Override
	public synchronized void prevTurn() {
		if (battle != null && battle.isRunning()
				&& battle instanceof BattlePlayer) {
			((BattlePlayer) battle).stepBack();
		}
	}

	@Override
	public synchronized void killRobot(int robotIndex) {
		if (battle != null && battle.isRunning() && battle instanceof Battle) {
			((Battle) battle).killRobot(robotIndex);
		}
	}

	@Override
	public synchronized void setPaintEnabled(int robotIndex, boolean enable) {
		if (battle != null && battle.isRunning()) {
			battle.setPaintEnabled(robotIndex, enable);
		}
	}

	@Override
	public synchronized void setSGPaintEnabled(int robotIndex, boolean enable) {
		if (battle != null && battle.isRunning() && battle instanceof Battle) {
			((Battle) battle).setSGPaintEnabled(robotIndex, enable);
		}
	}

	@Override
	public synchronized void sendInteractiveEvent(Event event) {
		if (battle != null && battle.isRunning() && !isPaused()
				&& battle instanceof Battle) {
			((Battle) battle).sendInteractiveEvent(event);
		}
	}
}
