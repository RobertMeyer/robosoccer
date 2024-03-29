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
 *     - Replaced the ContestantPeerVector, BulletPeerVector, and RobotPeerVector
 *       with plain Vector
 *     - Integration of new render classes placed under the robocode.gfx package
 *     - BattleView is not given via the constructor anymore, but is retrieved
 *       from the RobocodeManager. In addition, the battleView is now allowed to
 *       be null, e.g. if no GUI is available
 *     - Ported to Java 5.0
 *     - Bugfixed sounds that were cut off after first battle
 *     - Changed initialize() to use loadClass() instead of loadRobotClass() if
 *       security is turned off
 *     - Changed the way the TPS is loaded and updated
 *     - Added updateTitle() in order to manage and update the title on the
 *       RobocodeFrame
 *     - Added replay feature
 *     - Updated to use methods from the Logger, which replaces logger methods
 *       that has been (re)moved from the robocode.util.Utils class
 *     - Changed so robots die faster graphically when the battles are over
 *     - Changed cleanup to only remove the robot in the robot peers, as the
 *       robot peers themselves are used for replay recording
 *     - Added support for playing background music when the battle is ongoing
 *     - Removed unnecessary catches of NullPointerExceptions
 *     - Added support for setting the initial robot positions on the battlefield
 *     - Removed the showResultsDialog field which is replaced by the
 *       getOptionsCommonShowResults() from the properties
 *     - Simplified the code in the run() method when battle is stopped
 *     - Changed so that stop() makes the current round stop immediately
 *     - Added handling keyboard events thru a KeyboardEventDispatcher
 *     - Added mouseMoved(), mouseClicked(), mouseReleased(), mouseEntered(),
 *       mouseExited(), mouseDragged(), mouseWheelMoved()
 *     - Changed to take the new JuniorRobot class into account
 *     - When cleaning up robots their static fields are now being cleaned up
 *     - Bugfix: Changed the runRound() so that the robot are painted after
 *       they have made their turn
 *     - The thread handling for unsafe robot loading has been put in an
 *       independent UnsafeLoadRobotsThread class. In addition, the battle
 *       thread is not sharing it's run() method anymore with the
 *       UnsafeLoadRobotsThread, which has now got its own run() method
 *     - The 'running' and 'aborted' flags are now synchronized towards
 *       'battleMonitor' instead of 'this' object
 *     - Added waitTillRunning() method so another thread can be blocked until
 *       the battle has started running
 *     - Replaced synchronizedList on lists for deathEvent, robots, bullets,
 *       and contestants with a CopyOnWriteArrayList in order to prevent
 *       ConcurrentModificationExceptions when accessing these list via
 *       Iterators using public methods to this class
 *     - The moveBullets() was simplified and moved inside the runRound() method
 *     - The flushOldEvents() method was moved into the runRound() method
 *     - Major bugfix: Two robots running with exactly the same code was getting
 *       different scores. Robots listed before other robots always got a better
 *       score in the end. Hence, the getRobotsAtRandom() method has been added
 *       in order to gain fair play, and this method should be used where robots
 *       are checked and awakened in turn
 *     - Simplified the repainting of the battle
 *     - Bugfix: In wakeupRobots(), only wakeup a robot that is running and alive
 *     - A StatusEvent is now send to all alive robot each turn
 *     - Extended allowed max. length of a robot's full package name from 16 to
 *       32 characters
 *     Luis Crespo
 *     - Added sound features using the playSounds() method
 *     - Added debug step feature
 *     - Added isRunning()
 *     Robert D. Maupin
 *     - Replaced old collection types like Vector and Hashtable with
 *       synchronized List and HashMap
 *     Titus Chen
 *     - Bugfix: Added Battle parameter to the constructor that takes a
 *       BulletRecord as parameter due to a NullPointerException that was raised
 *       as the battleField variable was not intialized
 *     Nathaniel Troutman
 *     - Bugfix: In order to prevent memory leaks, the cleanup() method has now
 *       been extended to cleanup all robots, but also all classes that this
 *       class refers to in order to avoid circular references. In addition,
 *       cleanup has been added to the KeyEventHandler
 *     Julian Kent
 *     - Fix: Method for using only nano second precision when using
 *       RobotPeer.wait(0, nanoSeconds) in order to prevent the millisecond
 *       granularity issue, which is typically were coarse compared to the one
 *       with nano seconds 
 *     Pavel Savara
 *     - Re-work of robot interfaces
 *     - Refactored large methods into several smaller methods
 *     - decomposed RobotPeer from RobotProxy, now sending messages beteen them
 *******************************************************************************/
package net.sf.robocode.battle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;

import net.sf.robocode.battle.events.BattleEventDispatcher;
import net.sf.robocode.battle.item.ItemController;
import net.sf.robocode.battle.item.ItemDrop;
import net.sf.robocode.battle.peer.BulletPeer;
import net.sf.robocode.battle.peer.ContestantPeer;
import net.sf.robocode.battle.peer.LandminePeer;
import net.sf.robocode.battle.peer.ObstaclePeer;
import net.sf.robocode.battle.peer.RobotPeer;
import net.sf.robocode.battle.peer.TeamPeer;
import net.sf.robocode.battle.peer.TeleporterPeer;
import net.sf.robocode.battle.peer.ZLevelPeer;
import net.sf.robocode.battle.snapshot.TurnSnapshot;
import net.sf.robocode.host.ICpuManager;
import net.sf.robocode.host.IHostManager;
import net.sf.robocode.io.Logger;
import net.sf.robocode.mode.ClassicMode;
import net.sf.robocode.mode.MazeMode;
import net.sf.robocode.mode.ObstacleMode;
import net.sf.robocode.repository.IRepositoryManager;
import net.sf.robocode.security.HiddenAccess;
import net.sf.robocode.settings.ISettingsManager;
import robocode.BattleEndedEvent;
import robocode.BattleResults;
import robocode.BattleRules;
import robocode.Event;
import robocode.RobotDeathEvent;
import robocode.WinEvent;
import robocode.control.RandomFactory;
import robocode.control.RobotResults;
import robocode.control.RobotSpecification;
import robocode.control.events.BattleCompletedEvent;
import robocode.control.events.BattleFinishedEvent;
import robocode.control.events.BattlePausedEvent;
import robocode.control.events.BattleStartedEvent;
import robocode.control.events.RoundEndedEvent;
import robocode.control.events.RoundStartedEvent;
import robocode.control.events.TurnEndedEvent;
import robocode.control.events.TurnStartedEvent;
import robocode.control.snapshot.BulletState;
import robocode.control.snapshot.ITurnSnapshot;
import robocode.control.snapshot.LandmineState;
import robocode.equipment.EquipmentSet;
import robocode.equipment.EquipmentPart;

/**
 * The {@code Battle} class is used for controlling a battle.
 * 
 * @author Mathew A. Nelson (original)
 * @author Flemming N. Larsen (contributor)
 * @author Luis Crespo (contributor)
 * @author Robert D. Maupin (contributor)
 * @author Titus Chen (contributor)
 * @author Nathaniel Troutman (contributor)
 * @author Julian Kent (contributor)
 * @author Pavel Savara (contributor)
 * @author CSSE2003 Team Forkbomb (equipment)
 */
public class Battle extends BaseBattle {

	// 10 seconds
	private static final int DEBUG_TURN_WAIT_MILLIS = 10 * 60 * 1000;

	private final IHostManager hostManager;
	private IRepositoryManager repositoryManager;
	private final long cpuConstant;
	// Inactivity related items
	private int inactiveTurnCount;
	private double inactivityEnergy;
	// Objects in the battle
	private BattleProperties bp;
	// Height and width of the battlefield
	private int height;
	private int width;

	/** The set of equipment parts that the robots of this battle may equip. */
	private EquipmentSet equipment;

	// List of effect areas
	private EffectAreaManager eaManager = new EffectAreaManager();
	private List<IRenderable> customObject = new ArrayList<IRenderable>();
	private int activeRobots;
	// Death events
	private final List<RobotPeer> deathRobots = new CopyOnWriteArrayList<RobotPeer>();
	private DeathEffectController deController = new DeathEffectController();
	// For retrieval of robot in timer mode
	private List<RobotPeer> robotList;
	// Flag specifying if debugging is enabled thru the debug command line
	// option
	private final boolean isDebugging;
	// Initial robot start positions (if any)
	private double[][] initialRobotPositions;
	// Botzilla specific variables
	private int currentTurn;
	// Check for Botzilla
	private Boolean botzillaActive;
	private int botzillaSpawnTime = 750;
	RobotPeer botzillaPeer;
	RobotSpecification botzilla;
	private Hashtable<String, Object> setTimeHashTable;

	// kill streak tracker
	private KillstreakTracker killstreakTracker;
	
	 //GoGoGadget Race Mode Activated?
    private boolean raceModeOn = false;

	// Turn skip related items
	private boolean parallelOn;
	private long millisWait;
	private int nanoWait;

	/*--ItemController--*/
	private ItemController itemControl;// = new ItemController();
	private List<ItemDrop> items = new ArrayList<ItemDrop>();

	// Objects in the battle
	private int robotsCount;
	private final List<BulletPeer> bullets = new CopyOnWriteArrayList<BulletPeer>();

	// List of teleporters in the arena
	private List<TeleporterPeer> teleporters = new ArrayList<TeleporterPeer>();
	private final List<LandminePeer> landmines = new CopyOnWriteArrayList<LandminePeer>();
	private BattlePeers peers;

	/* Wall variables */
	private int cellWidth, cellHeight, wallWidth, wallHeight;
	private boolean dWalls = false;

	/** List of obstacles in the battlefield */
	private List<ObstaclePeer> obstacles = new ArrayList<ObstaclePeer>();

	private int numObstacles;
	private static DefaultSpawnController spawnController = new DefaultSpawnController();
	
	private List<ZLevelPeer> zLevels;

	public Battle(ISettingsManager properties, IBattleManager battleManager,
			IHostManager hostManager, IRepositoryManager repositoryManager,
			ICpuManager cpuManager, BattleEventDispatcher eventDispatcher) {
		super(properties, battleManager, eventDispatcher);
		isDebugging = System.getProperty("debug", "false").equals("true");
		this.hostManager = hostManager;
		this.cpuConstant = cpuManager.getCpuConstant();
		this.killstreakTracker = new KillstreakTracker(this);
		this.repositoryManager = repositoryManager;
	}

	public void setup(RobotSpecification[] battlingRobotsList,
			BattleProperties battleProperties, boolean paused,
			IRepositoryManager repositoryManager) {
		bp = battleProperties;
		isPaused = paused;
		battleRules = HiddenAccess.createRules(bp.getBattlefieldWidth(),
				bp.getBattlefieldHeight(), bp.getNumRounds(),
				bp.getGunCoolingRate(), bp.getInactivityTime(),
				bp.getHideEnemyNames(), bp.getModeRules());
		robotsCount = battlingRobotsList.length;
		// get width and height of the battlefield
		width = bp.getBattlefieldWidth();
		height = bp.getBattlefieldHeight();
		battleMode = (ClassicMode) bp.getBattleMode();

		equipment = EquipmentSet.fromFile(bp.getEquipmentFile());

		//Retrieve the Botzilla RobotSpecification out of the repository to use in spawning later
		final RobotSpecification[] temp = repositoryManager.getSpecifications();
		for (int i = 0; i < temp.length; i++) {
			String className = temp[i].getClassName();
			if (className.equals("sampleex.Botzilla")) {
				botzilla = temp[i];
				break;
			}
		}

		deController.setup(bp, this);
		botzillaActive = false;

		if (battleMode.toString() == "Obstacle Mode") {
			numObstacles = battleMode.setNumObstacles(battleRules);
			cellWidth = battleMode.setCellWidth(battleRules);
			cellHeight = battleMode.setCellHeight(battleRules);
			obstacles = ObstacleMode.generateRandomObstacles(numObstacles, bp,
					battleRules, this, cellWidth, cellHeight);
		}
		if (battleMode.toString() == "Maze Mode") {
			cellWidth = battleMode.setCellWidth(battleRules);
			cellHeight = battleMode.setCellHeight(battleRules);
			wallWidth = battleMode.setWallWidth(battleRules);
			wallHeight = battleMode.setWallHeight(battleRules);
			dWalls = battleMode.dWallSetting(battleRules);
			System.out.println(cellWidth + " " + cellHeight + " " + wallWidth
					+ " " + wallHeight);
			obstacles = MazeMode.generateMaze(bp, battleRules, this, cellWidth,
					cellHeight, wallWidth, wallHeight);
		}
		this.getBattleMode().setGuiOptions();
		initialRobotPositions = this.getBattleMode().computeInitialPositions(
				bp.getInitialPositions(), bp.getBattlefieldWidth(),
				bp.getBattlefieldHeight(), robotsCount);

		peers = new BattlePeers(this, battlingRobotsList, hostManager,
				repositoryManager);

        if (battleMode.toString() == "Race Mode"){
        	raceModeOn = true;
        }
        
		if (battleMode.toString() == "Botzilla Mode") {
			setTimeHashTable = battleManager.getBattleProperties()
					.getBattleMode().getRulesPanelValues();
			if (Integer
					.parseInt((String) setTimeHashTable.get("botzillaSpawn")) != 0) {
				botzillaSpawnTime = Integer.parseInt((String) setTimeHashTable
						.get("botzillaSpawn"));
			} else if (Integer.parseInt((String) setTimeHashTable
					.get("botzillaModifier")) != 0) {
				botzillaSpawnTime = Integer.parseInt((String) setTimeHashTable
						.get("botzillaModifier")) * robotsCount;
			}
			System.out.println("Botzilla will spawn at " + botzillaSpawnTime
					+ " turns.");
		}
	}

	public void registerDeathRobot(RobotPeer r) {
		for(Iterator<RobotPeer> i = r.getMinionPeers().iterator(); i.hasNext(); ){
			i.next().kill();
		}
		deathRobots.add(r);
	}

	public BattleRules getBattleRules() {
		return battleRules;
	}

	public int getRobotsCount() {
		return robotsCount;
	}

	public List<IRenderable> getCustomObject() {
		return customObject;
	}

	/**
	 * @param name
	 *            the name of the part
	 * @return the part associated with the given name, or null if there is no
	 *         part with that name.
	 */
	public EquipmentPart getEquipmentPart(String name) {
		return equipment.getPart(name);
	}

	public ItemController getItemControl() {
		return itemControl;
	}

	public void addItem(ItemDrop item) {
		items.add(item);
	}

	public List<ObstaclePeer> getObstacleList() {
		return new ArrayList<ObstaclePeer>(obstacles);
	}

	public boolean isDebugging() {
		return isDebugging;
	}
	
	public void addBullet(BulletPeer bullet) {
		bullets.add(bullet);
	}

	public void addMinion(RobotPeer minion, double startingEnergy) {
		//Add the minion peer to the battle.
		robotsCount++;
		peers.addRobot(minion);
		minion.initializeRound(peers.getRobots(), null, startingEnergy);
		long waitTime = Math.min(300 * cpuConstant, 10000000000L);
		long waitMillis = waitTime / 1000000;
		int waitNanos = (int) (waitTime % 1000000);
		if(isDebugging)
			waitMillis = 100;
		minion.startRound(waitMillis, waitNanos);
	}

	public void addLandmine(LandminePeer landmine) {
		landmines.add(landmine);
	}

	public void resetInactiveTurnCount(double energyLoss) {
		if (energyLoss < 0) {
			return;
		}
		inactivityEnergy += energyLoss;
		while (inactivityEnergy >= 10) {
			inactivityEnergy -= 10;
			inactiveTurnCount = 0;
		}
	}

	/**
	 * Returns a list of all robots.
	 * 
	 * @return a list of all robot peers.
	 */
	public List<RobotPeer> getRobotList() {
		return robotList;
	}

	/**
	 * Gets the activeRobots.
	 * 
	 * @return Returns a int
	 */
	public int getActiveRobots() {
		return activeRobots;
	}

	/**
	 * Gets the killstreak Tracker
	 * 
	 * @return Returns the KillstreakTracker for this battle
	 */

	public KillstreakTracker getKillstreakTracker() {
		return killstreakTracker;
	}

	@Override
	public void cleanup() {
		peers.cleanup();

		super.cleanup();

		items.clear();

		customObject.clear();

		battleManager = null;

		// Request garbage collecting
		for (int i = 4; i >= 0; i--) { // Make sure it is run
			System.gc();
		}
	}

	@Override
	protected void initializeBattle() {
		super.initializeBattle();

		parallelOn = System.getProperty("PARALLEL", "false").equals("true");
		if (parallelOn) {
			// how could robots share CPUs ?
			double parallelConstant = peers.getRobots().size()
					/ Runtime.getRuntime().availableProcessors();
			// four CPUs can't run two single threaded robot faster than two
			// CPUs
			if (parallelConstant < 1) {
				parallelConstant = 1;
			}
			final long waitTime = (long) (cpuConstant * parallelConstant);

			millisWait = waitTime / 1000000;
			nanoWait = (int) (waitTime % 1000000);
		} else {
			millisWait = cpuConstant / 1000000;
			nanoWait = (int) (cpuConstant % 1000000);
		}
		if (nanoWait == 0) {
			nanoWait = 1;
		}
		
		itemControl = new ItemController();
	}

	@Override
	protected void finalizeBattle() {
		eventDispatcher.onBattleFinished(new BattleFinishedEvent(isAborted()));

		if (!isAborted()) {
			eventDispatcher.onBattleCompleted(new BattleCompletedEvent(
					battleRules, computeBattleResults()));
		}

		for (RobotPeer robotPeer : peers.getRobots()) {
			robotPeer.cleanup();
		}
		hostManager.resetThreadManager();

		super.finalizeBattle();
	}

	@SuppressWarnings("unchecked")
	protected void initialiseItems() {
		/* (team-Telos) Create the items */
		this.getBattleMode().setItems(this);
		items = (List<ItemDrop>) this.getBattleMode().getItems();
		Collections.shuffle(items);
	}

	@Override
	protected void preloadRound() {
		super.preloadRound();

		//reset currentTurn at start of a round
		currentTurn = 0;

		// At this point the unsafe loader thread will now set itself to wait
		// for a notify
		for (RobotPeer robotPeer : peers.getRobots()) {
			robotPeer.initializeRound(peers.getRobots(), initialRobotPositions);
			robotPeer.println("=========================");
			robotPeer.println("Round " + (getRoundNum() + 1) + " of "
					+ getNumRounds());
			robotPeer.println("=========================");
		}

		/* Start to initialise all the items */
		this.initialiseItems();
		eaManager.clearEffectArea();

		List<IRenderable> objs = this.getBattleMode().createRenderables();
		if (objs != null) {
			for (IRenderable obj : objs)
				customObject.add(obj);
		}
		for (IRenderable obj : customObject)
			System.out.println(obj.getName());
		// boolean switch to switch off effect areas
		if (battleManager.getBattleProperties().getEffectArea()) {
			// clear effect area and recreate every round
			eaManager.createRandomEffectAreas(bp, 1);
		}
		if (getRoundNum() == 0) {
			eventDispatcher.onBattleStarted(new BattleStartedEvent(battleRules,
					peers.getRobots().size(), false));
			if (isPaused()) {
				eventDispatcher.onBattlePaused(new BattlePausedEvent());
			}
		}

		computeActiveRobots();

		hostManager.resetThreadManager();
	}

	@Override
	protected void initializeRound() {
		super.initializeRound();

		botzillaActive = false;

		inactiveTurnCount = 0;

		/*--ItemController--*/
		/*--Remove any item sprites from previous rounds--*/
		List<IRenderable> imagesDestroyed = new ArrayList<IRenderable>();
		for (ItemDrop item : itemControl.getItems()){
			
			for (IRenderable ob : getCustomObject()){
				if (item.getName().equals(ob.getName())){
					imagesDestroyed.add(ob);					
				}
			}
			for (IRenderable ob : imagesDestroyed){
				getCustomObject().remove(ob);
			}
		}
		/*--Reset Item Controller--*/
		itemControl = new ItemController();
		itemControl.updateRobots(peers.getRobots());

		// Put list of robots into robotList
		robotList = peers.getRobots();

		// Start robots

		long waitMillis;
		int waitNanos;

		if (isDebugging) {
			waitMillis = DEBUG_TURN_WAIT_MILLIS;
			waitNanos = 0;
		} else {
			long waitTime = Math.min(300 * cpuConstant, 10000000000L);

			waitMillis = waitTime / 1000000;
			waitNanos = (int) (waitTime % 1000000);
		}

		for (RobotPeer robotPeer : getRobotsAtRandom()) {
			robotPeer.startRound(waitMillis, waitNanos);
		}

		createTeleporters();
		if(ZLevelsEnabler.getZRand()) {
			setZLevels();
		}
		Logger.logMessage(""); // puts in a new-line in the log message

		final ITurnSnapshot snapshot = new TurnSnapshot(this,
				peers.getRobots(), bullets, landmines, eaManager.effArea, customObject,
				itemControl.getItems(), obstacles, teleporters, zLevels, false);
		// final ITurnSnapshot snapshot = new TurnSnapshot(this,
		// peers.getRobots(), bullets, landmines,effArea, customObject,
		// itemControl.getItems(), false);

		eventDispatcher.onRoundStarted(new RoundStartedEvent(snapshot,
				getRoundNum()));
	}

	@Override
	protected void finalizeRound() {
		super.finalizeRound();

		if (botzillaActive) {
			removeBotzilla();
		}

		// Modified the following iteration to allow removal of minions.
		ListIterator<RobotPeer> it = peers.getRobots().listIterator();
		while (it.hasNext()) {
			RobotPeer robotPeer = (RobotPeer) it.next();
			robotPeer.waitForStop();
			robotPeer.getRobotStatistics().generateTotals(bp);
			if (robotPeer.isMinion()) {
				it.remove();
			}
		}

		// Increment mode specific points - TODO -team-Telos
		this.getBattleMode().scoreRoundPoints();

		bullets.clear();
		items.clear();
		teleporters.clear();
		if (zLevels != null) {
			zLevels.clear();
		}
		landmines.clear();

		eventDispatcher.onRoundEnded(new RoundEndedEvent(getRoundNum(),
				currentTime, totalTurns));
        
        getBattleMode().endRound(peers);
	}

	@Override
	protected void initializeTurn() {
		//Add botzilla if the mode is botzilla mode and it is the chosen or default turn.
        if (currentTurn == botzillaSpawnTime &&
        		battleMode.toString() == "Botzilla Mode" &&
        		!botzillaActive) {
        	addBotzilla();
        }

		super.initializeTurn();

		eventDispatcher.onTurnStarted(new TurnStartedEvent());
	}

	@Override
	protected void runTurn() {
		super.runTurn();

		loadCommands();

		/*--ItemController--*/
		itemControl.updateRobots(peers.getRobots());

		updateBullets();

		updateLandmines();

		eaManager.updateEffectAreas(peers);

		this.getBattleMode().updateRenderables(customObject);

		updateRobots();

		// Check for Spike mode
		if (battleManager.getBattleProperties().getBattleMode().toString() == "Spike Mode") {
			checkRobotHitSpike();
		}

		handleDeadRobots();
		handleDeadFrozenRobots();

		if (getBattleMode().respawnsOn()) {
			if (super.getTime() > getBattleMode().turnLimit()) {
				shutdownTurn();
			}
		}
		if (isAborted() || oneTeamRemaining()) {
			shutdownTurn();
		}

		inactiveTurnCount++;

		computeActiveRobots();

		publishStatuses();

		if (totalTurns % 100 == 0 || totalTurns == 1) {

			for (ItemDrop item : items) {
				if (!itemControl.getItems().contains(item)) {
					itemControl.spawnRandomItem(item);
					break;
				}
			}
		}

		List<ItemDrop> itemDestroy = new ArrayList<ItemDrop>();
		List<IRenderable> imagesDestroyed = new ArrayList<IRenderable>();
		for (ItemDrop item : itemControl.getItems()) {
			if (!item.getName().contains("flag")) {
				item.setLifespan(item.getLifespan() - 1);
				if (item.getLifespan() == 0) {
					itemDestroy.add(item);
				}
			}
		}
		for (ItemDrop item : itemDestroy) {
			for (IRenderable ob : getCustomObject()) {
				if (item.getName().equals(ob.getName())) {
					imagesDestroyed.add(ob);
				}
			}
			for (IRenderable ob : imagesDestroyed) {
				getCustomObject().remove(ob);
			}
			itemControl.removeItem(item);
		}

		currentTurn++;

		// Robot time!
		wakeupRobots();
	}
	
	/**
	 * Get the current turn number
	 * 
	 * @return currentTurn	Turn number.
	 */
	public int getCurrentTurnNumber() {
		return currentTurn;
	}

	@Override
	protected void shutdownTurn() {
		customObject.clear();
		if (getEndTimer() == 0) {
			if (isAborted()) {
				for (RobotPeer robotPeer : getRobotsAtRandom()) {
					if (robotPeer.isAlive()) {
						robotPeer.println("SYSTEM: game aborted.");
					}
				}
			} else if (oneTeamRemaining()) {
				boolean leaderFirsts = false;
				TeamPeer winningTeam = null;

				final robocode.RoundEndedEvent roundEndedEvent = new robocode.RoundEndedEvent(
						getRoundNum(), currentTime, totalTurns);
				for (RobotPeer robotPeer : getRobotsAtRandom()) {
					robotPeer.addEvent(roundEndedEvent);
					if (robotPeer.isAlive()) {
						if (!robotPeer.isWinner()) {
							robotPeer.getRobotStatistics().scoreLastSurvivor();
							robotPeer.setWinner(true);
							robotPeer.println("SYSTEM: "
									+ robotPeer.getNameForEvent(robotPeer)
									+ " wins the round.");
							robotPeer.addEvent(new WinEvent());
							if (robotPeer.getTeamPeer() != null) {
								if (robotPeer.isTeamLeader()) {
									leaderFirsts = true;
								} else {
									winningTeam = robotPeer.getTeamPeer();
								}
							}
						}
					}
				}
				if (!leaderFirsts && winningTeam != null) {
					winningTeam.getTeamLeader().getRobotStatistics()
							.scoreFirsts();
				}
			}
		}

		if (getEndTimer() == 1 && (isAborted() || isLastRound())) {

			List<RobotPeer> orderedRobots = new ArrayList<RobotPeer>(
					peers.getRobots());

			Collections.sort(orderedRobots);
			Collections.reverse(orderedRobots);

			for (int rank = 0; rank < peers.getRobots().size(); rank++) {
				RobotPeer robotPeer = orderedRobots.get(rank);

				robotPeer.getStatistics().setRank(rank + 1);
				BattleResults resultsForRobot = robotPeer.getStatistics()
						.getFinalResults();

				robotPeer.addEvent(new BattleEndedEvent(isAborted(),
						resultsForRobot));
			}
		}

		if (getEndTimer() > 4 * 30) {
			for (RobotPeer robotPeer : peers.getRobots()) {
				robotPeer.setHalt(true);
			}
		}

		super.shutdownTurn();
	}

	@Override
	protected void finalizeTurn() {
		eventDispatcher.onTurnEnded(new TurnEndedEvent(new TurnSnapshot(this,
				peers.getRobots(), bullets, landmines, eaManager.effArea, customObject,
				itemControl.getItems(), obstacles, teleporters, zLevels, true)));

		// eventDispatcher.onTurnEnded(new TurnEndedEvent(new TurnSnapshot(this,
		// peers.getRobots(), bullets, landmines,effArea, customObject,
		// itemControl.getItems(), true)));

		super.finalizeTurn();
	}

	private BattleResults[] computeBattleResults() {
		ArrayList<BattleResults> results = new ArrayList<BattleResults>();

		List<ContestantPeer> orderedContestants = new ArrayList<ContestantPeer>(
				peers.getContestants());

		System.out.println(orderedContestants.size());

		Collections.sort(orderedContestants);
		Collections.reverse(orderedContestants);

		// noinspection ForLoopReplaceableByForEach
		for (int i = 0; i < peers.getContestants().size(); i++) {
			results.add(null);
		}
		for (int rank = 0; rank < peers.getContestants().size(); rank++) {
			RobotSpecification robotSpec = null;
			ContestantPeer contestant = orderedContestants.get(rank);

			contestant.getStatistics().setRank(rank + 1);
			BattleResults battleResults = contestant.getStatistics()
					.getFinalResults();

			if (contestant instanceof RobotPeer) {
				robotSpec = ((RobotPeer) contestant).getRobotSpecification();
			} else if (contestant instanceof TeamPeer) {
				robotSpec = ((TeamPeer) contestant).getTeamLeader()
						.getRobotSpecification();
			}

			results.set(rank, new RobotResults(robotSpec, battleResults));
		}

		return results.toArray(new BattleResults[results.size()]);
	}

	/**
	 * Returns a list of all robots in random order. This method is used to gain
	 * fair play in Robocode, so that a robot placed before another robot in the
	 * list will not gain any benefit when the game checks if a robot has won,
	 * is dead, etc. This method was introduced as two equal robots like
	 * sample.RamFire got different scores even though the code was exactly the
	 * same.
	 * 
	 * @return a list of robot peers.
	 */
	private List<RobotPeer> getRobotsAtRandom() {
		List<RobotPeer> shuffledList = new ArrayList<RobotPeer>(
				peers.getRobots());

		Collections.shuffle(shuffledList, RandomFactory.getRandom());
		return shuffledList;
	}

	/**
	 * Returns a list of all bullets in random order. This method is used to
	 * gain fair play in Robocode.
	 * 
	 * @return a list of bullet peers.
	 */
	private List<BulletPeer> getBulletsAtRandom() {
		List<BulletPeer> shuffledList = new ArrayList<BulletPeer>(bullets);

		Collections.shuffle(shuffledList, RandomFactory.getRandom());
		return shuffledList;
	}

	private List<LandminePeer> getLandminesAtRandom() {
		List<LandminePeer> shuffledList = new ArrayList<LandminePeer>(landmines);

		Collections.shuffle(shuffledList, RandomFactory.getRandom());
		return shuffledList;
	}

	/**
	 * Returns a list of all death robots in random order. This method is used
	 * to gain fair play in Robocode.
	 * 
	 * @return a list of robot peers.
	 */
	private List<RobotPeer> getDeathRobotsAtRandom() {
		List<RobotPeer> shuffledList = new ArrayList<RobotPeer>(deathRobots);

		Collections.shuffle(shuffledList, RandomFactory.getRandom());
		return shuffledList;
	}

	private void loadCommands() {
		// this will load commands, including bullets from last turn
		for (RobotPeer robotPeer : peers.getRobots()) {
			robotPeer.performLoadCommands();
		}
	}

	/**
	 * Check the whether robot is on top the spike. If robot is on top of the
	 * spike, kill it instantly. If not, do nothing.
	 */
	private void checkRobotHitSpike() {
		int spikeXSize = battleManager.getSpikePosX().size();
		int spikeYSize = battleManager.getSpikePosY().size();

		for (int i = 0; i < robotList.size(); i++) {
			for (int x = 0; x < spikeXSize; x++) {
				if ((robotList.get(i).getX() < battleManager.getSpikePosX()
						.get(x) + 64)
						&& (robotList.get(i).getX() > battleManager
								.getSpikePosX().get(x))) {
					for (int y = 0; y < spikeYSize; y++) {
						if ((robotList.get(i).getY() < battleManager
								.getSpikePosY().get(y))
								&& (robotList.get(i).getY() > battleManager
										.getSpikePosY().get(y) - 64)) {
							robotList.get(i).kill();
						}
					}
				}
			}
		}
	}

	private void updateBullets() {
		for (BulletPeer bullet : getBulletsAtRandom()) {
			bullet.update(getRobotsAtRandom(), getBulletsAtRandom(),
					getObstacleList(), teleporters);
			if (bullet.getState() == BulletState.INACTIVE) {
				bullets.remove(bullet);
			}
		}
	}

	private void updateLandmines() {
		for (LandminePeer landmine : getLandminesAtRandom()) {
			landmine.update(getRobotsAtRandom(), getLandminesAtRandom());
			if (landmine.getState() == LandmineState.INACTIVE) {
				landmines.remove(landmine);
			}
		}
	}

	private void updateRobots() {
		// zaps if inactive turn > allowed inactive turns or 5*allowed inactive
		// turns for maze mode.
		boolean zap = (inactiveTurnCount > ((battleMode.toString() == "Maze Mode") ? 10
				: 1)
				* battleRules.getInactivityTime());

		final double zapEnergy = isAborted() ? 5 : zap ? .1 : 0;

		// Move all bots
		for (RobotPeer robotPeer : getRobotsAtRandom()) {
			robotPeer.performMove(getRobotsAtRandom(), items, obstacles, zLevels, zapEnergy, teleporters);
			robotPeer.spawnMinions();
		}

		if (currentTurn >= botzillaSpawnTime
				&& battleMode.toString() == "Botzilla Mode" && !botzillaActive) {
			addBotzilla();
		}

		getBattleMode().addRobots(currentTurn, peers);

		// Increment mode specific points - TODO -team-Telos
		this.getBattleMode().scoreTurnPoints();

		getBattleMode().updateRobotScans(peers.getRobots());
	}

	/*
	 * Is called at the end of a round to remove botzilla from the peers list
	 * and ensure it isn't spawned at the start of the next round
	 */
	private void removeBotzilla() {
		botzillaActive = false;
        peers.removeBotzilla();
        robotsCount--;
	}

	/**
	 * Is called when botzilla needs to be added to a battle.
	 */
	public void addBotzilla() {
		System.out.println("BOTZILLA JUST APPEARED");
		botzillaActive = true;
		
		//Create the RobotPeer to add to the battle
		botzillaPeer = new RobotPeer(this,
				hostManager,
				botzilla,
				0,
				null,
				getRobotsCount(),
				null);
		//Increment number of robots and add peer to necessary lists
		robotsCount++;
		peers.addRobot(botzillaPeer);
		peers.addContestant(botzillaPeer);
		
		//Makes botzilla appear and start interacting in the battle
		botzillaPeer.initializeRound(peers.getRobots() , null);
		long waitTime = Math.min(300 * cpuConstant, 10000000000L);
        final long waitMillis = waitTime / 1000000;
        final int waitNanos = (int) (waitTime % 1000000);
		botzillaPeer.startRound(waitMillis, waitNanos);
	}
	
	/**
	 * Indicate whether Botzilla is active or not.
	 * 
	 * @return botzillaActive	The status of Botzilla.
	 */
	public boolean checkBotzillaActive() {
		return botzillaActive;
	}

	/**
	 * Checks the battlefield for any dead frozen robots, and removes the ice
	 * cube image, and force ends the freeze for the next round.
	 */
	private void handleDeadFrozenRobots() {
		for (RobotPeer robot : robotList) {
			if (robot.containsImage("freeze") && robot.isDead()) {
				robot.removeImage("freeze");
				robot.setKsFrozen(false);
			}
		}
	}

	private void handleDeadRobots() {

		for (RobotPeer deadRobot : getDeathRobotsAtRandom()) {
			// spawn blackhole on dead robot is there was not one there already
			if (TeleporterEnabler.isBlackholesEnabled()
					&& !deadRobot.collidedWithBlackHole()) {
				double y2 = -2;
				double x2 = -2;
				double x1 = deadRobot.getX();
				double y1 = deadRobot.getY();
				teleporters.add(new TeleporterPeer(x1, y1, x2, y2));
			}

			// Death effect
			if (battleManager.getBattleProperties().getEffectArea()) {
				deController.deathEffect(deadRobot, getRobotsAtRandom(), eaManager);
			}
			// Compute scores for dead robots
			if (deadRobot.getTeamPeer() == null) {
				deadRobot.getRobotStatistics().scoreRobotDeath(
						getActiveContestantCount(deadRobot), botzillaActive);
			} else {
				boolean teammatesalive = false;

				for (RobotPeer tm : peers.getRobots()) {
					if (tm.getTeamPeer() == deadRobot.getTeamPeer()
							&& tm.isAlive()) {
						teammatesalive = true;
						break;
					}
				}
				if (!teammatesalive) {
					deadRobot.getRobotStatistics()
							.scoreRobotDeath(
									getActiveContestantCount(deadRobot),
									botzillaActive);
				}
			}

			// Publish death to live robots
			for (RobotPeer robotPeer : getRobotsAtRandom()) {
				if (robotPeer.isAlive()) {
					robotPeer.addEvent(new RobotDeathEvent(robotPeer
							.getNameForEvent(deadRobot)));

					if (robotPeer.getTeamPeer() == null
							|| robotPeer.getTeamPeer() != deadRobot
									.getTeamPeer()) {
						robotPeer.getRobotStatistics().scoreSurvival();
					}
				}
			}
		}

		deathRobots.clear();
	}

	/**
	 * Runs the death effect associated with deadRobot. Effects 1-3 are
	 * different sizes of explosions. Effects 4-6 are different effect areas.
	 * 
	 * @param deadRobot
	 *            The robot to enforce death effect from
	 */
	private void deathEffect(RobotPeer deadRobot) {
		int finalX = 0;
		int finalY = 0;
		int yOffset = bp.getBattlefieldHeight() % 64;

		// distance and damage variables used for case 1, 2 and 3
		int damage = -5;
		int explosionDistance = 75;

		if (deadRobot.getDeathEffect() > 3) {
			// Round off to closest X and Y tiles
			// Only applicable to case 4, 5 and 6
			finalX = (int) deadRobot.getX() - (int) deadRobot.getX() % 64;

			finalY = (int) deadRobot.getY() - yOffset + 64;
			finalY = (finalY / 64) * 64;
			finalY = finalY + yOffset;
		}

		switch (deadRobot.getDeathEffect()) {
		case 1:
			// Large explosion - small damage
			explosionDistance *= 3;
			for (RobotPeer aliveRobot : getRobotsAtRandom()) {
				if (aliveRobot.isAlive()) {
					// Check distance
					// Simple pythagoras math
					double xDist = deadRobot.getX() - aliveRobot.getX();
					if (xDist < 0)
						xDist = xDist * (-1);
					double yDist = deadRobot.getY() - aliveRobot.getY();
					if (yDist < 0)
						yDist = yDist * (-1);
					double robotDistance = Math.sqrt(xDist * xDist + yDist
							* yDist);

					if (robotDistance <= explosionDistance) {
						// robot is within explosion range
						aliveRobot.updateEnergy(damage);
					}
				}
			}
			break;
		case 2:
			// Medium explosion - medium damage
			explosionDistance *= 2;
			damage *= 2;
			for (RobotPeer aliveRobot : getRobotsAtRandom()) {
				if (aliveRobot.isAlive()) {
					// Check distance
					// Simple pythagoras math
					double xDist = deadRobot.getX() - aliveRobot.getX();
					if (xDist < 0)
						xDist = xDist * (-1);
					double yDist = deadRobot.getY() - aliveRobot.getY();
					if (yDist < 0)
						yDist = yDist * (-1);
					double robotDistance = Math.sqrt(xDist * xDist + yDist
							* yDist);

					if (robotDistance <= explosionDistance) {
						// robot is within explosion range
						aliveRobot.updateEnergy(damage);
					}
				}
			}
			break;
		case 3:
			// Small explosion - large damage
			damage *= 3;
			for (RobotPeer aliveRobot : getRobotsAtRandom()) {
				if (aliveRobot.isAlive()) {
					// Check distance
					// Simple pythagoras math
					double xDist = deadRobot.getX() - aliveRobot.getX();
					if (xDist < 0)
						xDist = xDist * (-1);
					double yDist = deadRobot.getY() - aliveRobot.getY();
					if (yDist < 0)
						yDist = yDist * (-1);
					double robotDistance = Math.sqrt(xDist * xDist + yDist
							* yDist);

					if (robotDistance <= explosionDistance) {
						// robot is within explosion range
						aliveRobot.updateEnergy(damage);
					}
				}
			}
			break;
		case 4:
			// Effect area 1
			EffectArea deathEffect1 = new EffectArea(finalX, finalY, 64, 64, 1);
			eaManager.addEffectArea(deathEffect1);
			break;
		case 5:
			// Effect area 2
			EffectArea deathEffect2 = new EffectArea(deadRobot.getX(),
					deadRobot.getY(), 64, 64, 2);
			eaManager.addEffectArea(deathEffect2);
			break;
		case 6:
			// Effect area 3
			EffectArea deathEffect3 = new EffectArea(deadRobot.getX(),
					deadRobot.getY(), 64, 64, 3);
			eaManager.addEffectArea(deathEffect3);
			break;
		}
	}

	private void publishStatuses() {
		for (RobotPeer robotPeer : peers.getRobots()) {
			robotPeer.publishStatus(currentTime);
		}
	}

	private void computeActiveRobots() {
		int ar = 0;

		// Compute active robots
		for (RobotPeer robotPeer : peers.getRobots()) {
			if (robotPeer.isAlive() && !robotPeer.isMinion()) {
				ar++;
			}
		}
		this.activeRobots = ar;
	}

	private void wakeupRobots() {
		// Wake up all robot threads
		final List<RobotPeer> robotsAtRandom = getRobotsAtRandom();

		if (parallelOn) {
			wakeupParallel(robotsAtRandom);
		} else {
			wakeupSerial(robotsAtRandom);
		}
	}

	private void wakeupSerial(List<RobotPeer> robotsAtRandom) {
		for (RobotPeer robotPeer : robotsAtRandom) {
			if (robotPeer.isRunning()) {
				// This call blocks until the robot's thread actually wakes up.
				robotPeer.waitWakeup();

				if (robotPeer.isAlive()) {
					if (isDebugging || robotPeer.isPaintEnabled()) {
						robotPeer.waitSleeping(DEBUG_TURN_WAIT_MILLIS, 1);
					} else if (currentTime == 1) {
						robotPeer.waitSleeping(millisWait * 10, 1);
					} else {
						robotPeer.waitSleeping(millisWait, nanoWait);
					}
					robotPeer.checkSkippedTurn();
				}
			}
		}
	}

	private void wakeupParallel(List<RobotPeer> robotsAtRandom) {
		for (RobotPeer robotPeer : robotsAtRandom) {
			if (robotPeer.isRunning()) {
				// This call blocks until the robot's thread actually wakes up.
				robotPeer.waitWakeup();
			}
		}
		for (RobotPeer robotPeer : robotsAtRandom) {
			if (robotPeer.isRunning() && robotPeer.isAlive()) {
				if (isDebugging || robotPeer.isPaintEnabled()) {
					robotPeer.waitSleeping(DEBUG_TURN_WAIT_MILLIS, 1);
				} else if (currentTime == 1) {
					robotPeer.waitSleeping(millisWait * 10, 1);
				} else {
					robotPeer.waitSleeping(millisWait, nanoWait);
				}
			}
		}
		for (RobotPeer robotPeer : robotsAtRandom) {
			if (robotPeer.isAlive()) {
				robotPeer.checkSkippedTurn();
			}
		}
	}

	private int getActiveContestantCount(RobotPeer peer) {
		int count = 0;

		for (ContestantPeer c : peers.getContestants()) {
			if (c instanceof RobotPeer && ((RobotPeer) c).isAlive()) {
				count++;
			} else if (c instanceof TeamPeer && c != peer.getTeamPeer()) {
				for (RobotPeer robotPeer : (TeamPeer) c) {
					if (robotPeer.isAlive()) {
						count++;
						break;
					}
				}
			}
		}
		return count;
	}

	private boolean oneTeamRemaining() {
		if (getActiveRobots() <= 1) {
			return true;
		}

		boolean found = false;
		TeamPeer currentTeam = null;
		
		for (RobotPeer currentRobot : peers.getRobots()) {
			if (currentRobot.isAlive()) {
				if (!found) {
					found = true;
					currentTeam = currentRobot.getTeamPeer();
				} else {
					if (currentTeam == null
							&& currentRobot.getTeamPeer() == null) {
						return false;
					}
					if (currentTeam != currentRobot.getTeamPeer()) {
						return false;
					}
				}
			}
		}
		return true;
	}

	// --------------------------------------------------------------------------
	// Processing and maintaining robot and battle controls
	// --------------------------------------------------------------------------

	public void killRobot(int robotIndex) {
		sendCommand(new KillRobotCommand(robotIndex));
	}

	public void setPaintEnabled(int robotIndex, boolean enable) {
		sendCommand(new EnableRobotPaintCommand(robotIndex, enable));
	}

	public void setSGPaintEnabled(int robotIndex, boolean enable) {
		sendCommand(new EnableRobotSGPaintCommand(robotIndex, enable));
	}

	public void sendInteractiveEvent(Event e) {
		sendCommand(new SendInteractiveEventCommand(e));
	}

	private class KillRobotCommand extends RobotCommand {
		KillRobotCommand(int robotIndex) {
			super(robotIndex);
		}

		public void execute() {
			peers.getRobots().get(robotIndex).kill();
		}
	}

	private class EnableRobotPaintCommand extends RobotCommand {
		final boolean enablePaint;

		EnableRobotPaintCommand(int robotIndex, boolean enablePaint) {
			super(robotIndex);
			this.enablePaint = enablePaint;
		}

		public void execute() {
			peers.getRobots().get(robotIndex).setPaintEnabled(enablePaint);
		}
	}

	private class EnableRobotSGPaintCommand extends RobotCommand {
		final boolean enableSGPaint;

		EnableRobotSGPaintCommand(int robotIndex, boolean enableSGPaint) {
			super(robotIndex);
			this.enableSGPaint = enableSGPaint;
		}

		public void execute() {
			peers.getRobots().get(robotIndex).setSGPaintEnabled(enableSGPaint);
		}
	}

	private class SendInteractiveEventCommand extends Command {

		public final Event event;

		SendInteractiveEventCommand(Event event) {
			this.event = event;
		}

		@Override
		public void execute() {
			for (RobotPeer robotPeer : peers.getRobots()) {
				if (robotPeer.isInteractiveRobot()) {
					robotPeer.addEvent(event);
				}
			}
		}
	}

	private void createTeleporters() {
		// do nothing if teleporters are not enabled
		if (!TeleporterEnabler.isTeleportersEnabled())
			return;

		// randomise some x and y co-ordinates that are away from the walls by 5
		double x1 = Math.random() * (width - 80) + 40;
		double x2 = Math.random() * (width - 80) + 40;
		double y1 = Math.random() * (height - 80) + 40;
		double y2 = Math.random() * (height - 80) + 40;

		// add a new TeleporterPeer
		teleporters.add(new TeleporterPeer(x1, y1, x2, y2));
	}

	
	/**
	 * Adds a Spawn Controller to the battle
	 * @param e SpawnController to add to the DefaultSpawnController
	 * @return true if e was added, false otherwise
	 * @see DefaultSpawnController
	 * @author Lee Symes 42636267
	 */
	public static boolean addController(ISpawnController e) {
		return spawnController.addController(e);
	}
	
	/**
	 * Removes a Spawn Controller from the battle
	 * @param e SpawnController to remove from the DefaultSpawnController
	 * @return true if e was added, false otherwise
	 * @see DefaultSpawnController
	 * @author Lee Symes 42636267
	 */
	public static boolean removeController(ISpawnController e) {
		return spawnController.removeController(e);
	}
	
	/**
	 * Removes all Spawn Controllers from the battle
	 * @see DefaultSpawnController
	 * @author Lee Symes 42636267
	 */
	public static void clearControllers() {
		spawnController.clearControllers();
	}
	
	/**
	 * Get the default spawn controller
	 * @return the Default spawn controller
	 * @see DefaultSpawnController
	 * @author Lee Symes 42636267
	 */
	public ISpawnController getSpawnController() {
		return spawnController;
	}

	/**
	 * This method adds a IRenderable to the scene.
	 * 
	 * @param obj
	 *            a IRenderable object.
	 */
	public void addCustomObject(IRenderable obj) {
		System.out.println("well");
		if (obj != null) {
			System.out.println("added");
			customObject.add(obj);
		}
	}

	/**
	 * This method removes a IRenderable in the scene.
	 * 
	 * 
	 * @param obj
	 *            a IRenderable object to remove.
	 */
	public void removeCustomObject(IRenderable obj) {
		if (obj != null) {
			customObject.remove(obj);
		}
	}

	/**
	 * This method removes a IRenderable in the scene.
	 * 
	 * 
	 * @param name
	 *            of IRenderable to remove.
	 */
	public void removeCustomObjectByName(String name) {
		for (IRenderable obj : customObject) {
			if (obj.getName().equals(name)) {
				customObject.remove(obj);
				return;
			}
		}
	}

	public void registerDestroyedWall(ObstaclePeer o) {
		if (dWalls) {
			for (ObstaclePeer obstacle : obstacles) {
				if (obstacle == o) {
					obstacles.remove(o);
					return;
				}
			}
		}

	}

	public IRepositoryManager getRepositoryManager() {
		return repositoryManager;
	}
	
	public void setZLevels() {
		int vTiles;
		int hTiles;
		int tSize = 64;
		int x = 0;
		int y = 0;
		int z = 0;
		
		vTiles = height / tSize + 1;
		hTiles = width / tSize + 1;
		
		zLevels = new ArrayList<ZLevelPeer>();
		
		for(int i = 0; i < vTiles; ++i) {
			for(int j = 0; j < hTiles; ++j) {
				zLevels.add(new ZLevelPeer(tSize, tSize, z, x, y));
				
				z += Math.round(Math.random() * 2);
				z -= Math.round(Math.random() * 2);
				x += tSize;				
			}
			
			z = 0;
			y += tSize;
		}
		
	}
	 
	 public boolean isRaceMode(){
		 return raceModeOn;
	 }
	 
	 public BattleProperties getBattleProperties(){
		 return bp;
	 }
	
}
