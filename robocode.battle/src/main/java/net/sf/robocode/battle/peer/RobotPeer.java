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
 *     - Bugfix: updateMovement() checked for distanceRemaining > 1 instead of
 *       distanceRemaining > 0 if slowingDown and moveDirection == -1
 *     - Bugfix: Substituted wait(10000) with wait() in execute() method, so
 *       that robots do not hang when game is paused
 *     - Bugfix: Teleportation when turning the robot to 0 degrees while forcing
 *       the robot towards the bottom
 *     - Added setPaintEnabled() and isPaintEnabled()
 *     - Added setSGPaintEnabled() and isSGPaintEnabled()
 *     - Replaced the colorIndex with bodyColor, gunColor, and radarColor
 *     - Replaced the setColors() with setBodyColor(), setGunColor(), and
 *       setRadarColor()
 *     - Added bulletColor, scanColor, setBulletColor(), and setScanColor() and
 *       removed getColorIndex()
 *     - Optimizations
 *     - Ported to Java 5
 *     - Bugfix: HitRobotEvent.isMyFault() returned false despite the fact that
 *       the robot was moving toward the robot it collides with. This was the
 *       case when distanceRemaining == 0
 *     - Removed isDead field as the robot state is used as replacement
 *     - Added isAlive() method
 *     - Added constructor for creating a new robot with a name only
 *     - Added the set() that copies a RobotRecord into this robot in order to
 *       support the replay feature
 *     - Fixed synchronization issues with several member fields
 *     - Added features to support the new JuniorRobot class
 *     - Added cleanupStaticFields() for clearing static fields on robots
 *     - Added getMaxTurnRate()
 *     - Added turnAndMove() in order to support the turnAheadLeft(),
 *       turnAheadRight(), turnBackLeft(), and turnBackRight() for the
 *       JuniorRobot, which moves the robot in a perfect curve that follows a
 *       circle
 *     - Changed the behaviour of checkRobotCollision() so that HitRobotEvents
 *       are only created and sent to robot when damage do occur. Previously, a
 *       robot could receive HitRobotEvents even when no damage was done
 *     - Renamed scanReset() to rescan()
 *     - Added getStatusEvents()
 *     - Added getGraphicsProxy(), getPaintEvents()
 *     Luis Crespo
 *     - Added states
 *     Titus Chen
 *     - Bugfix: Hit wall and teleporting problems with checkWallCollision()
 *     Robert D. Maupin
 *     - Replaced old collection types like Vector and Hashtable with
 *       synchronized List and HashMap
 *     Nathaniel Troutman
 *     - Added cleanup() method for cleaning up references to internal classes
 *       to prevent circular references causing memory leaks
 *     Pavel Savara
 *     - Re-work of robot interfaces
 *     - hosting related logic moved to robot proxy
 *     - interlocked synchronization
 *     - (almost) minimized surface between RobotPeer and RobotProxy to serializable messages.
 *******************************************************************************/
package net.sf.robocode.battle.peer;


import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.tan;
import static net.sf.robocode.io.Logger.logMessage;
import static robocode.util.Utils.isNear;
import static robocode.util.Utils.normalAbsoluteAngle;
import static robocode.util.Utils.normalNearAbsoluteAngle;
import static robocode.util.Utils.normalRelativeAngle;

import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

import static java.lang.Math.*;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.FreezeRobotDeath;
import net.sf.robocode.battle.IRenderable;

import net.sf.robocode.battle.KillFreezeHeatRobots;
import net.sf.robocode.battle.MinionData;
import net.sf.robocode.battle.RenderObject;
import net.sf.robocode.battle.TeamCollisionTracker;
import net.sf.robocode.battle.Waypoint;
import net.sf.robocode.battle.item.BoundingRectangle;
import net.sf.robocode.battle.item.ItemDrop;

import net.sf.robocode.battle.TrackField;

import net.sf.robocode.host.IHostManager;
import net.sf.robocode.host.RobotStatics;
import net.sf.robocode.host.events.EventManager;
import net.sf.robocode.host.events.EventQueue;
import net.sf.robocode.host.proxies.IHostingRobotProxy;
import net.sf.robocode.io.Logger;
import net.sf.robocode.mode.SoccerMode;
import net.sf.robocode.peer.BadBehavior;
import net.sf.robocode.peer.BulletCommand;
import net.sf.robocode.peer.BulletStatus;
import net.sf.robocode.peer.DebugProperty;
import net.sf.robocode.peer.ExecCommands;
import net.sf.robocode.peer.ExecResults;
import net.sf.robocode.peer.IRobotPeer;
import net.sf.robocode.peer.LandmineCommand;
import net.sf.robocode.peer.LandmineStatus;
import net.sf.robocode.peer.TeamMessage;
import net.sf.robocode.repository.IRepositoryManager;
import net.sf.robocode.repository.IRobotRepositoryItem;
import net.sf.robocode.security.HiddenAccess;
import net.sf.robocode.serialization.RbSerializer;
import robocode.BattleRules;
import robocode.DeathEvent;
import robocode.Event;
import robocode.HitItemEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.MinionProxy;
import robocode.RobotAttribute;
import robocode.RobotFrozenEvent;
import robocode.RobotStatus;
import robocode.Rules;
import robocode.ScannedItemEvent;
import robocode.ScannedRobotEvent;
import robocode.SkippedTurnEvent;
import robocode.WaypointPassedEvent;
import robocode.WinEvent;
import robocode.control.RobotSpecification;
import robocode.control.snapshot.BulletState;
import robocode.control.snapshot.LandmineState;
import robocode.control.snapshot.RobotState;
import robocode.equipment.EquipmentPart;
import robocode.equipment.EquipmentSlot;
import robocode.exception.AbortedException;
import robocode.exception.DeathException;
import robocode.exception.WinException;
import robocode.robotinterfaces.peer.IBasicRobotPeer;

/**
 * RobotPeer is an object that deals with game mechanics and rules, and makes
 * sure that robots abides the rules.
 * 
 * @author Mathew A. Nelson (original)
 * @author Flemming N. Larsen (contributor)
 * @author Luis Crespo (contributor)
 * @author Titus Chen (contributor)
 * @author Robert D. Maupin (contributor)
 * @author Nathaniel Troutman (contributor)
 * @author Pavel Savara (contributor)
 * @author Patrick Cupka (contributor)
 * @author Julian Kent (contributor)
 * @author "Positive" (contributor)
 * @author Malcolm Inglis (CSSE2003) (contributor - attributes, equipment)
 * @author CSSE2003 Team HoneyBadgers (contributor)
 * @author CSSE2003 Team Forkbomb (attributes, equipment)
 * @author CSSE2003 Team Mysterious-Incontinence (minion functionality)
 */
public class RobotPeer implements IRobotPeerBattle, IRobotPeer {

	public static final int WIDTH = 40, HEIGHT = 40;

	protected static final int HALF_WIDTH_OFFSET = (WIDTH / 2 - 2),
			HALF_HEIGHT_OFFSET = (HEIGHT / 2 - 2);
	
	// Special hitbox settings for Ball
	public static final int BALL_WIDTH = 15, BALL_HEIGHT = 15;

	// Special hitbox settings for Botzilla
	public static final int BZ_WIDTH = WIDTH * 2, BZ_HEIGHT = HEIGHT * 2;
	protected static final int BZ_HALF_WIDTH_OFFSET = (BZ_WIDTH / 2 - 2),
			BZ_HALF_HEIGHT_OFFSET = (BZ_HEIGHT / 2 - 2);

	protected static final int MAX_SKIPPED_TURNS = 30;
	protected static final int MAX_SKIPPED_TURNS_WITH_IO = 240;

	protected Battle battle;
	protected RobotStatistics statistics;
	protected final TeamPeer teamPeer;
	protected final RobotSpecification robotSpecification;

	protected IHostingRobotProxy robotProxy;
	protected AtomicReference<RobotStatus> status = new AtomicReference<RobotStatus>();
	protected AtomicReference<ExecCommands> commands = new AtomicReference<ExecCommands>();
	protected AtomicReference<EventQueue> events = new AtomicReference<EventQueue>(
			new EventQueue());
	protected AtomicReference<List<TeamMessage>> teamMessages = new AtomicReference<List<TeamMessage>>(
			new ArrayList<TeamMessage>());
	protected AtomicReference<List<BulletStatus>> bulletUpdates = new AtomicReference<List<BulletStatus>>(
			new ArrayList<BulletStatus>());

	protected AtomicReference<List<LandmineStatus>> landmineUpdates = new AtomicReference<List<LandmineStatus>>(
			new ArrayList<LandmineStatus>());

	// thread is running
	protected final AtomicBoolean isRunning = new AtomicBoolean(false);

	protected final StringBuilder battleText = new StringBuilder(1024);
	protected final StringBuilder proxyText = new StringBuilder(1024);
	protected RobotStatics statics;
	protected BattleRules battleRules;

	// for battle thread, during robots processing
	protected ExecCommands currentCommands;
	protected double lastHeading;
	protected double lastGunHeading;
	protected double lastRadarHeading;
	protected double energy;
	protected double velocity;
	protected double bodyHeading;
	protected double radarHeading;
	protected double gunHeading;
	protected double gunHeat;
	protected double x;
	protected double y;
	protected int skippedTurns;

	// Radius in which Dispenser will give energy
	protected double dispenseRadius = WIDTH * 3;
	// Rate at which Dispenser will give energy
	protected double maxDispenseRate = 1;

	protected boolean scan;
	protected boolean turnedRadarWithGun; // last round
	protected boolean melt; // whether or not robot chooses to melt if frozen

	protected boolean isIORobot;
	protected boolean isPaintEnabled;
	protected boolean sgPaintEnabled;

	// waiting for next tick
	protected final AtomicBoolean isSleeping = new AtomicBoolean(false);
	protected final AtomicBoolean halt = new AtomicBoolean(false);

	protected boolean isExecFinishedAndDisabled;
	protected boolean isEnergyDrained;
	protected boolean isWinner;
	protected boolean inCollision;
	protected boolean isOverDriving;

	protected RobotState state;
	protected final Arc2D scanArc;
	protected final BoundingRectangle boundingBox;
	protected final RbSerializer rbSerializer;

	// The number of turns the robot is frozen for, 0 if not frozen
	protected int robotFrozen = 0;
	

	// The same as robotFrozen. Except it doesn't get set to 0 when a robot melts itself
	// This is so when a robots melts others still see it as frozen.
	// Just another advantage to melting.
	protected int seenAsFrozen = 0;

	//raceMode int
	protected int currentWaypointIndex = 0;
	protected int currentLap = 0;
	//TODO Remove below
	Waypoint way = new Waypoint(42.2,44.3);

	// The robot can use these to melt itself without losing energy
	protected int meltCredit = 0;

	// item inventory
	protected List<ItemDrop> itemsList = new ArrayList<ItemDrop>();

	// killstreak booleans
	private boolean isScannable = true;
	private boolean isKsFrozen = false;
	private boolean isSuperTank = false;

	// blackhole
	private boolean collidedWithBlackHole = false;

	// killstreak timers
	private int radarJammerTimeout;
	private int superTankTimeout;
	private int frozenTimeout;

	// killstreak image manager^M
	HashMap<String, RenderObject> ksImages = new HashMap<String, RenderObject>();

	// For calculation of team's total energy (Team energy sharing mode)
	private TeamPeer teamList;

	// Minion specific variables.
	// Store parent's minions in an array for parent->minion communication.
	private List<RobotPeer> minionList = new ArrayList<RobotPeer>();
	// Store minion proxies for communication between parent/minion.
	private List<MinionProxy> minionProxyList = new ArrayList<MinionProxy>();
	// Need to store host manager for minion creation.
	private IHostManager hostManager;
	// Store parent proxy for minions.
	private MinionProxy minionParent;
	
	protected int zLevel;

	/**
	 * An association of values to every RobotAttribute, such that game
	 * mechanics can be uniquely determined for each robot based on a variety
	 * of factors (such as, e.g., equipment).
	 *
	 * Attribute values are defined as 1=100%. Thus, in RobotPeer's
	 * constructor, all attribute values are initialized to 1.
	 *
	 * @see RobotAttribute
	 */
	protected AtomicReference<Map<RobotAttribute, Double>> attributes =
			new AtomicReference<Map<RobotAttribute, Double>>(
					new HashMap<RobotAttribute, Double>()
			);

	/**
	 * Keeps track of the equipment parts equipped to slots to prevent multiple
	 * parts being equipped to the same slot.
	 *
	 * @see #equip()
	 * @see #unequip()
	 */
	protected AtomicReference<Map<EquipmentSlot, EquipmentPart>> equipment =
			new AtomicReference<Map<EquipmentSlot, EquipmentPart>>(
					new HashMap<EquipmentSlot, EquipmentPart>()
			);

	double fullEnergy;

	public RobotPeer(Battle battle, IHostManager hostManager, RobotSpecification robotSpecification, int duplicate, TeamPeer team, int robotIndex, IHostingRobotProxy parentProxy) {
		super();

		this.battle = battle;
		this.robotSpecification = robotSpecification;

		this.rbSerializer = new RbSerializer();

		this.boundingBox = new BoundingRectangle();
		this.scanArc = new Arc2D.Double();
		this.teamPeer = team;
		this.state = RobotState.ACTIVE;
		this.battleRules = battle.getBattleRules();

		//TODO Delete below
		way.addSingleWaypoint(57.7, 160);
		way.addSingleWaypoint(65.4, 500.2);
		way.addSingleWaypoint(800.4, 77.3);
		way.addSingleWaypoint(45.5, 44.3);
		
		if (team != null) {
			team.add(this);
		}
		String teamName;
		List<String> teamMembers;
		boolean isTeamLeader;
		int teamIndex;

		if (teamPeer == null) {
			teamName = null;
			teamMembers = null;
			isTeamLeader = false;
			teamIndex = -1; // Must be set to -1 when robot is not in a team
		} else {
			teamName = team.getName();
			teamMembers = team.getMemberNames();
			isTeamLeader = team.size() == 1; // That is current team size, more might follow later. First robot is leader
			teamIndex = team.getTeamIndex();
			teamList = team;
		}

		// Default all attributes to 1.0, such that all game mechanics are
		// at default for this robot. (until the attributes are changed by,
		// e.g., equipment)
		for (RobotAttribute attribute : RobotAttribute.values()) {
			attributes.get().put(attribute, 1.00);
		}

		this.statics = new RobotStatics(robotSpecification, duplicate, isTeamLeader, battleRules, teamName, teamMembers,
				robotIndex, teamIndex);
		this.statistics = new RobotStatistics(this, battle.getRobotsCount());
		
		if(parentProxy != null) 
			this.setParent(parentProxy);
		
		this.robotProxy = (IHostingRobotProxy) hostManager.createRobotProxy(robotSpecification, statics, this);

		this.hostManager = hostManager;
	}
	
	/**
	 * Spawns minions, if minions are enabled and the robot has
	 * requested a minion to be spawned.
	 * 
	 * This function sets up communication between the parent robot
	 * and the minion/s.
	 * 
	 * Minions are added to the battle using battle.addMinion().
	 * @see MinionProxy
	 * @author Jordan Henderson
	 */
	public void spawnMinions() {
		if(currentCommands.getSpawnMinion() && !isMinion() && 
		(MinionData.getMinionsEnabled() || !MinionData.getIsGui())) {
			int minionType = currentCommands.getMinionType();
			IRepositoryManager repo = battle.getRepositoryManager();
			RobotSpecification[] minionSpecs;
			if(MinionData.getMinionsEnabled() || !MinionData.getIsGui()) {
				 minionSpecs = repo.loadSelectedRobots(MinionData.getMinions());
	        }
			else {
				return;
			}
			//Validate the robot has enough power to spawn a minion.
			double energyConsumption = currentCommands.getMinionEnergyCost();
			if(!MinionData.getInsaneMode()) {
				if(energy <= energyConsumption)
					//If minion spawns, parent will have no energy.
					return;
				else
					energy -= energyConsumption;
			}
			
			//Spawn the minion.
			RobotSpecification minion = minionSpecs[minionType];
			//Pass robotProxy to provide a proxy for the minion (Minion => Parent)
			RobotPeer minionPeer = createMinionPeer(battle, hostManager, minion, 0, null, 0, robotProxy);

			battle.addMinion(minionPeer, energyConsumption);

			//Provide a proxy for the parent. (Parent=>Minion)
			MinionProxy minionProxy = null;
			try{
				minionProxy = new MinionProxy((IBasicRobotPeer)minionPeer.robotProxy);
			}
			catch(Exception ex) {
				//Only possible exception that can occur here is a mockito cast error.
			}
			
			minionList.add(minionPeer);
			minionProxyList.add(minionProxy);
		}
		currentCommands.setSpawnMinion(false, 0, 0);
		//Update the minions proxy list in commands.
		currentCommands.setMinions(minionProxyList);
	}
	
	/**
	 * Method provided to allow testing (using different RobotPeer implementation).
	 * @param battle
	 * @param hostManager
	 * @param robotSpecification
	 * @param duplicate
	 * @param team
	 * @param robotIndex
	 * @param parentProxy
	 * @return new RobotPeer
	 */
	public RobotPeer createMinionPeer(Battle battle, IHostManager hostManager, RobotSpecification robotSpecification, 
			int duplicate, TeamPeer team, int robotIndex, IHostingRobotProxy parentProxy) {
		return new RobotPeer(battle, hostManager, robotSpecification, duplicate, team, robotIndex, parentProxy);
	}
	
	/**
	 * @return a list containing this robot's minions (as RobotPeers)
	 */
	public List<RobotPeer> getMinionPeers() {
		return minionList;
	}
	
	/**
	 * If the robot is a minion, update it's parent peer.
	 * @param parent the new parent.
	 */
	public void setParent(IHostingRobotProxy parent) {
		if(this.isMinion() && parent != null) {
			MinionProxy minionProxy = new MinionProxy((IBasicRobotPeer)parent);
			this.minionParent = minionProxy;
		}
	}
	
	/**
	 * @param child
	 * @return true if child is owned by this robot, false otherwise.
	 */
	public boolean isParent(RobotPeer child) {
		if(minionList.contains(child))
			return true;
		return false;
	}
	
	/**
	 * Clean up minions after a round.
	 */
	public void finalizeMinions() {
		for(RobotPeer minion: minionList) {
			minion.waitForStop();
			minion.cleanup();
		}
	}


	public void println(String s) {
		synchronized (proxyText) {
			battleText.append(s);
			battleText.append("\n");
		}
	}
	
	/**
	 * check whether robot equip Sword by checking the
	 * equipment.get(Weapon)==getEquipmentPart("Sword")
	 */
	public boolean checkSword() {
		EquipmentPart part = battle.getEquipmentPart("Sword");
		if (equipment.get().get(part.getSlot()) == part) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * @return the parent's MinionProxy.
	 */
	public MinionProxy getMinionParent(){
		return this.minionParent;
	}

	public void print(Throwable ex) {
		println(ex.toString());
		StackTraceElement[] trace = ex.getStackTrace();

		for (StackTraceElement aTrace : trace) {
			println("\tat " + aTrace);
		}

		Throwable ourCause = ex.getCause();

		if (ourCause != null) {
			print(ourCause);
		}
	}

	public void print(String s) {
		synchronized (proxyText) {
			proxyText.append(s);
		}
	}

	public String readOutText() {
		synchronized (proxyText) {
			final String robotText = battleText.toString() + proxyText.toString();

			battleText.setLength(0);
			proxyText.setLength(0);
			return robotText;
		}
	}

	public RobotStatistics getRobotStatistics() {
		return statistics;
	}

	public ContestantStatistics getStatistics() {
		return statistics;
	}

	public RobotSpecification getRobotSpecification() {
		return robotSpecification;
	}

	// -------------------
	// statics
	// -------------------

	public boolean isDroid() {
		return statics.isDroid();
	}

	/**
	 * Is the robot a house robot
	 * @return true if a house robot, false otherwise
	 */
	public boolean isHouseRobot() {
		return statics.isHouseRobot();
	}
	
	public boolean isFreezeRobot() {
		return statics.isFreezeRobot();
	}

	public boolean isBall() {
    	return statics.isBall();
    }
	
	public boolean isSoccerRobot() {
		return statics.isSoccerRobot();
	}

	public boolean isZombie() {
		return statics.isZombie();
	}

	public boolean isJuniorRobot() {
		return statics.isJuniorRobot();
	}

	public boolean isInteractiveRobot() {
		return statics.isInteractiveRobot();
	}

	public boolean isPaintRobot() {
		return statics.isPaintRobot();
	}

	public boolean isAdvancedRobot() {
		return statics.isAdvancedRobot();
	}

	public boolean isTeamRobot() {
		return statics.isTeamRobot();
	}
	
	public boolean isHeatRobot() {
		return statics.isHeatRobot();
	}

	/**
	 * Test for checking if current robot is Botzilla.
	 * @return True if robot is Botzilla.
	 * 		   False otherwise.
	 */
	public boolean isBotzilla() {
    	//return statics.isBotzilla();
		return (robotSpecification.getName().equals("sampleex.Botzilla"));
    }

	/**
	 * Test for checking if current robot is a Dispenser.
	 * @return True if robot is a Dispenser.
	 * 		   False otherwise.
	 */
	public boolean isDispenser() {
		return statics.isDispenser();
	}
	
	public boolean isMinion() {
		return statics.isMinion();
	}

	public String getName() {
		return statics.getName();
	}

	public String getAnnonymousName() {
		return statics.getAnnonymousName();
	}

	public String getShortName() {
		return statics.getShortName();
	}

	public String getVeryShortName() {
		return statics.getVeryShortName();
	}

	public int getRobotIndex() {
		return statics.getRobotIndex();
	}

	public int getTeamIndex() {
		return statics.getTeamIndex();
	}

	public int getContestantIndex() {
		return getTeamIndex() >= 0 ? getTeamIndex() : getRobotIndex();
	}

	// -------------------
	// status
	// -------------------

	public void setPaintEnabled(boolean enabled) {
		isPaintEnabled = enabled;
	}

	public boolean isPaintEnabled() {
		return isPaintEnabled;
	}

	public void setSGPaintEnabled(boolean enabled) {
		sgPaintEnabled = enabled;
	}

	public boolean isSGPaintEnabled() {
		return sgPaintEnabled;
	}

	public RobotState getState() {
		return state;
	}

	public void setState(RobotState state) {
		this.state = state;
	}

    @Override
	public boolean isDead() {
		return state == RobotState.DEAD;
	}

    @Override
	public boolean isAlive() {
		return state != RobotState.DEAD;
	}
	
	public boolean isFrozen() {
		return state == RobotState.FROZEN;
	}
	
	public boolean isSeenAsFrozen() {
		return seenAsFrozen > 0;
	}

    @Override
	public boolean isWinner() {
		return isWinner;
	}

    @Override
	public boolean isRunning() {
		return isRunning.get();
	}

	public boolean isSleeping() {
		return isSleeping.get();
	}

	public boolean isHalt() {
		return halt.get();
	}

    @Override
	public void setHalt(boolean value) {
		halt.set(value);
	}

	public BoundingRectangle getBoundingBox() {
		return boundingBox;
	}

	public Arc2D getScanArc() {
		return scanArc;
	}

	// -------------------
	// robot space
	// -------------------
	public double getGunHeading() {
		return gunHeading;
	}

	public double getBodyHeading() {
		return bodyHeading;
	}

	public double getRadarHeading() {
		return radarHeading;
	}

	public double getVelocity() {
		return velocity;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getEnergy() {
		return energy;
	}

	public double getGunHeat() {
		return gunHeat;
	}

	public int getBodyColor() {
		return commands.get().getBodyColor();
	}

	public int getRadarColor() {
		return commands.get().getRadarColor();
	}

	public int getGunColor() {
		return commands.get().getGunColor();
	}

	public int getBulletColor() {
		return commands.get().getBulletColor();
	}
	
	public int getLandmineColor()
	{
		return commands.get().getLandmineColor();
	}

	public int getScanColor() {
		return commands.get().getScanColor();
	}

	public int getDeathEffect() {
		return commands.get().getDeathEffect();
	}

	// ------------
	// team
	// ------------
    @Override
	public TeamPeer getTeamPeer() {
		return teamPeer;
	}

	public String getTeamName() {
		return statics.getTeamName();
	}

    @Override
	public boolean isTeamLeader() {
		return statics.isTeamLeader();
	}

	public boolean isTeamMate(RobotPeer otherRobot) {
		if (getTeamPeer() != null) {
			for (RobotPeer mate : getTeamPeer()) {
				if (otherRobot == mate) {
					return true;
				}
			}
		}
		return false;
	}
	
	/* Team Telos Addition */
	public RobotPeer getRobotPeer() {
		return this;
	}

	// -----------
	// execute
	// -----------
	ByteBuffer bidirectionalBuffer;

    @Override
	public void setupBuffer(ByteBuffer bidirectionalBuffer) {
		this.bidirectionalBuffer = bidirectionalBuffer;
	}

    @Override
	public void setupThread() {
		Thread.currentThread().setName(getName());
	}

    @Override
	public void executeImplSerial() throws IOException {
		ExecCommands commands = (ExecCommands) rbSerializer.deserialize(bidirectionalBuffer);

		final ExecResults results = executeImpl(commands);

		bidirectionalBuffer.clear();
		rbSerializer.serializeToBuffer(bidirectionalBuffer, RbSerializer.ExecResults_TYPE, results);
	}

    @Override
	public void waitForBattleEndImplSerial() throws IOException {
		ExecCommands commands = (ExecCommands) rbSerializer.deserialize(bidirectionalBuffer);

		final ExecResults results = waitForBattleEndImpl(commands);

		bidirectionalBuffer.clear();
		rbSerializer.serializeToBuffer(bidirectionalBuffer, RbSerializer.ExecResults_TYPE, results);
	}

    @Override
	public final ExecResults executeImpl(ExecCommands newCommands) {
		validateCommands(newCommands);

		if (!isExecFinishedAndDisabled) {
			// from robot to battle
			commands.set(new ExecCommands(newCommands, true));
			print(newCommands.getOutputText());
		} else {
			// slow down spammer
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}

		// If we are stopping, yet the robot took action (in onWin or onDeath), stop now.
		if (battle.isAborted()) {
			isExecFinishedAndDisabled = true;
			throw new AbortedException();
		}
		if (isDead()) {
			isExecFinishedAndDisabled = true;
			throw new DeathException();
		}
		if (isHalt()) {
			isExecFinishedAndDisabled = true;
			if (isWinner) {
				throw new WinException();
			} else {
				throw new AbortedException();
			}
		}

		waitForNextTurn();

		// from battle to robot
		final ExecCommands resCommands = new ExecCommands(this.commands.get(), false);
		final RobotStatus resStatus = status.get();

		final boolean shouldWait = battle.isAborted() || (battle.isLastRound() && isWinner());

		return new ExecResults(resCommands, resStatus, readoutEvents(), readoutTeamMessages(), readoutBullets(), readoutLandmines(),
				isHalt(), shouldWait, isPaintEnabled());
	}

    @Override
	public final ExecResults waitForBattleEndImpl(ExecCommands newCommands) {
		if (!isHalt()) {
			// from robot to battle
			commands.set(new ExecCommands(newCommands, true));
			print(newCommands.getOutputText());

			waitForNextTurn();
		}
		// from battle to robot
		final ExecCommands resCommands = new ExecCommands(this.commands.get(), false);
		final RobotStatus resStatus = status.get();

		final boolean shouldWait = battle.isAborted() || (battle.isLastRound() && !isWinner());

		readoutTeamMessages(); // throw away

		return new ExecResults(resCommands, resStatus, readoutEvents(), new ArrayList<TeamMessage>(), readoutBullets(),readoutLandmines(),
				isHalt(), shouldWait, false);
	}

	protected void validateCommands(ExecCommands newCommands) {
		if (Double.isNaN(newCommands.getMaxTurnRate())) {
			println("You cannot setMaxTurnRate to: " + newCommands.getMaxTurnRate());
		}
		newCommands.setMaxTurnRate(Math.min(abs(newCommands.getMaxTurnRate()), getMaxTurnRateRadians()));

		if (Double.isNaN(newCommands.getMaxVelocity())) {
			println("You cannot setMaxVelocity to: " + newCommands.getMaxVelocity());
		}
		newCommands.setMaxVelocity(Math.min(abs(newCommands.getMaxVelocity()), getRealMaxVelocity()));
	}

	protected List<Event> readoutEvents() {
		return events.getAndSet(new EventQueue());
	}

	protected List<TeamMessage> readoutTeamMessages() {
		return teamMessages.getAndSet(new ArrayList<TeamMessage>());
	}

	protected List<BulletStatus> readoutBullets() {
		return bulletUpdates.getAndSet(new ArrayList<BulletStatus>());
	}
	
	protected List<LandmineStatus> readoutLandmines() {
		return landmineUpdates.getAndSet(new ArrayList<LandmineStatus>());
	}
	

	protected void waitForNextTurn() {
		synchronized (isSleeping) {
			// Notify the battle that we are now asleep.
			// This ends any pending wait() call in battle.runRound().
			// Should not actually take place until we release the lock in wait(), below.
			isSleeping.set(true);
			isSleeping.notifyAll();
			// Notifying battle that we're asleep
			// Sleeping and waiting for battle to wake us up.
			try {
				isSleeping.wait();
			} catch (InterruptedException e) {
				// We are expecting this to happen when a round is ended!

				// Immediately reasserts the exception by interrupting the caller thread itself
				Thread.currentThread().interrupt();
			}
			isSleeping.set(false);
			// Notify battle thread, which is waiting in
			// our wakeup() call, to return.
			// It's quite possible, by the way, that we'll be back in sleep (above)
			// before the battle thread actually wakes up
			isSleeping.notifyAll();
		}
	}

	// -----------
	// called on battle thread
	// -----------
    @Override
	public void waitWakeup() {
		synchronized (isSleeping) {
			if (isSleeping()) {
				// Wake up the thread
				isSleeping.notifyAll();
				try {
					isSleeping.wait(10000);
				} catch (InterruptedException e) {
					// Immediately reasserts the exception by interrupting the caller thread itself
					Thread.currentThread().interrupt();
				}
			}
		}
	}

	public void waitWakeupNoWait() {
		synchronized (isSleeping) {
			if (isSleeping()) {
				// Wake up the thread
				isSleeping.notifyAll();
			}
		}
	}

    @Override
	public void waitSleeping(long millisWait, int nanosWait) {
		synchronized (isSleeping) {
			// It's quite possible for simple robots to
			// complete their processing before we get here,
			// so we test if the robot is already asleep.

			if (!isSleeping()) {
				try {
					for (long i = millisWait; i > 0 && !isSleeping() && isRunning(); i--) {
						isSleeping.wait(0, 999999);
					}
					if (!isSleeping() && isRunning()) {
						isSleeping.wait(0, nanosWait);
					}
				} catch (InterruptedException e) {
					// Immediately reasserts the exception by interrupting the caller thread itself
					Thread.currentThread().interrupt();

					logMessage("Wait for " + getName() + " interrupted.");
				}
			}
		}
	}

    @Override
	public void checkSkippedTurn() {
		if (isHalt() || isSleeping() || !isRunning() || battle.isDebugging() || isPaintEnabled()) {
			skippedTurns = 0;
		} else {
			skippedTurns++;
			events.get().clear(false);
			if (isAlive()) {
				addEvent(new SkippedTurnEvent(battle.getTime()));
			}
			println("SYSTEM: " + getShortName() + " skipped turn " + battle.getTime());

			if ((!isIORobot && skippedTurns > MAX_SKIPPED_TURNS)
					|| (isIORobot && skippedTurns > MAX_SKIPPED_TURNS_WITH_IO)) {
				println("SYSTEM: " + getShortName() + " has not performed any actions in a reasonable amount of time.");
				println("SYSTEM: No score will be generated.");
				setHalt(true);
				waitWakeupNoWait();
				punishBadBehavior(BadBehavior.SKIPPED_TOO_MANY_TURNS);
				robotProxy.forceStopThread();
			}
		}
	}

    public void initializeRound(List<RobotPeer> robots, double[][] initialRobotPositions, double startingEnergy) {
    	initializeRound(robots, initialRobotPositions);
    	energy = startingEnergy;
    }
    
    @Override

	public void initializeRound(List<RobotPeer> robots, double[][] initialRobotPositions) {
		boolean valid = false;
		if (initialRobotPositions != null) {
			int robotIndex = statics.getRobotIndex();

			if (robotIndex >= 0 && robotIndex < initialRobotPositions.length) {
				double[] pos = initialRobotPositions[robotIndex];
                x = pos[0];
                y = pos[1];
                bodyHeading = pos[2];
                gunHeading = radarHeading = bodyHeading;
                updateBoundingBox();
                valid = validSpot(robots);
            }
        }
        if (!valid) {

            for (int j = 0; j < 1000; j++) {
                double[] sl = battle.getSpawnController().getSpawnLocation(this, battle);
//                if (!isBotzilla()) {
//                    x = RobotPeer.WIDTH + random.nextDouble()* (battleRules.getBattlefieldWidth() - 2 * RobotPeer.WIDTH);
//                    y = RobotPeer.HEIGHT + random.nextDouble() * (battleRules.getBattlefieldHeight() - 2 * RobotPeer.HEIGHT);
//                } else {
//                    x = RobotPeer.BZ_WIDTH + random.nextDouble() * (battleRules.getBattlefieldWidth() - 2 * RobotPeer.BZ_WIDTH);
//                    y = RobotPeer.BZ_HEIGHT + random.nextDouble() * (battleRules.getBattlefieldHeight() - 2 * RobotPeer.BZ_HEIGHT);
//                }
//                                x= sl[0];
//                                y=sl[1];
//                gunHeading = radarHeading = bodyHeading =
//                        2 * Math.PI * random.nextDouble();
////                Logger.realOut.println(x + " <- " + sl[0]);
//                Logger.realOut.println(y + " <- " + sl[1]);
//                Logger.realOut.println(bodyHeading + " <- " + sl[2]);
                gunHeading = radarHeading = bodyHeading = sl[2];
                x = sl[0];
                y = sl[1];

                updateBoundingBox();

                if (validSpot(robots)) {
                    break;
                }
            }
        }

		setState(RobotState.ACTIVE);

		isWinner = false;
		velocity = 0;

		if (statics.isTeamLeader() && statics.isDroid()) {
			energy = 220;
		} else if (statics.isTeamLeader()) {
			energy = 200;
		} else if (statics.isDroid()) {
			energy = 120;
		} else if (statics.isHouseRobot()){
			energy = 500;

		// Botzilla gets extra energy, for the unlikely case of energy drain.
		} else if (statics.isBotzilla()){
			energy = 999;
		//Dispensers get extra energy, for they are purely defensive

		} else if (statics.isDispenser()) {
			energy = 500;
			
		// FreezeRobot moves slower to give the other robots a chance.
		// Gets extra energy so it doesn't die too quickly
		} else if (statics.isFreezeRobot()) {
			energy = 300;
			attributes.get().put(RobotAttribute.VELOCITY, 0.40);
		
		// HeatRobot gets more energy so it lasts longer
		} else if (statics.isHeatRobot()) {
			energy = 300;
		} else {
			energy = getStartingEnergy();
		}
		fullEnergy = getEnergy();
		gunHeat = 3;
		setHalt(false);
		isExecFinishedAndDisabled = false;
		isEnergyDrained = false;

		scan = false;

		inCollision = false;

		scanArc.setAngleStart(0);
		scanArc.setAngleExtent(0);
		scanArc.setFrame(-100, -100, 1, 1);

		skippedTurns = 0;

		status = new AtomicReference<RobotStatus>();

		readoutEvents();
		readoutTeamMessages();
		readoutBullets();
        readoutLandmines();
		battleText.setLength(0);
		proxyText.setLength(0);

		// Prepare new execution commands, but copy the colors from the last commands.
		// Bugfix [2628217] - Robot Colors don't stick between rounds.
		ExecCommands newExecCommands = new ExecCommands();

		newExecCommands.copyColors(commands.get());
		commands = new AtomicReference<ExecCommands>(newExecCommands);
	}

	protected boolean validSpot(List<RobotPeer> robots) {
		for (RobotPeer otherRobot : robots) {
			if (otherRobot != null && otherRobot != this) {
				if (getBoundingBox().intersects(otherRobot.getBoundingBox())) {
					return false;
				}
			}
		}
		
		for (ObstaclePeer bObstacle : battle.getObstacleList()) {
			if (getBoundingBox().intersects(bObstacle.getBoundingBox())) {
				return false;
			}
		}

		return true;
	}

	public void startRound(long waitMillis, int waitNanos) {
		Logger.logMessage(".", false);

		statistics.initialize();

		ExecCommands newExecCommands = new ExecCommands();
		
		// Copy the colors from the last commands.
		// Bugfix [2628217] - Robot Colors don't stick between rounds.
		newExecCommands.copyColors(commands.get());

		currentCommands = newExecCommands;
		currentCommands.setParent(minionParent);
		int others = battle.getActiveRobots() - (isAlive() ? 1 : 0);
		RobotStatus stat = HiddenAccess.createStatus(energy, x, y, bodyHeading, gunHeading, radarHeading, velocity,
				currentCommands.getBodyTurnRemaining(), currentCommands.getRadarTurnRemaining(),
				currentCommands.getGunTurnRemaining(), currentCommands.getDistanceRemaining(), gunHeat, others,
				battle.getRoundNum(), battle.getNumRounds(), battle.getTime(), currentCommands.getMaxVelocity(),
				getMaxBulletPower(), getMinBulletPower(), getRobotAcceleration(), getRobotDeceleration(),
				getRadarScanRadius(), getMaxTurnRate(), getGunTurnRate(), getRadarTurnRate(), getRamDamage(),
				getRamAttack());

		status.set(stat);
		robotProxy.startRound(currentCommands, stat);

		synchronized (isSleeping) {
			try {
				// Wait for the robot to go to sleep (take action)
				isSleeping.wait(waitMillis, waitNanos);
			} catch (InterruptedException e) {
				logMessage("Wait for " + getName() + " interrupted.");

				// Immediately reasserts the exception by interrupting the caller thread itself
				Thread.currentThread().interrupt();
			}
		}
		if (!isSleeping() && !battle.isDebugging()) {
			logMessage("\n" + getName() + " still has not started after " + waitMillis + " ms... giving up.");
		}
		
		/*
		 * check if at the start of the round any of the killstreaks are still
		 * persistent and redraw them
		 */
		if (isSuperTank()) {
			battle.addCustomObject(ksImages.get("tank"));
		}
		
		if (!isScannable()) {
			battle.addCustomObject(ksImages.get("jammer"));
		}
		
		if (isKsFrozen()) {
			battle.addCustomObject(ksImages.get("freeze"));
		}

		if(battle.isRaceMode()){
			//calculate the bearing to the waypoint relative to the robots Heading.
	    	double dx = way.getSingleWaypointX(currentWaypointIndex)-x;
			double dy = way.getSingleWaypointY(currentWaypointIndex)-y;

	    	double relativeBearingtoWaypoint = (Math.PI/2)-atan2(dy, dx) - bodyHeading;
	    	
	    	//TODO (Waypoint)(battle.getBattleProperties().getTrackField().getWaypoints())
	    	relativeBearingtoWaypoint = normalNearAbsoluteAngle(relativeBearingtoWaypoint);
	    	double  distToWay = Math.hypot(dx, dy);
	    	
	    	//create the new WaypointPassedEvent	
	    	addEvent(new WaypointPassedEvent(currentWaypointIndex, way.getSingleWaypointX(
	    			currentWaypointIndex), way.getSingleWaypointY(currentWaypointIndex), 
	    			relativeBearingtoWaypoint, Math.hypot(dx, dy), distToWay));
		}
	}


	public void performLoadCommands() {
		currentCommands = commands.get();


		fireBullets(currentCommands.getBullets());
		
		fireLandmines(currentCommands.getLandmines());

		if (currentCommands.isScan()) {
			scan = true;
		}

		if (currentCommands.isIORobot()) {
			isIORobot = true;
		}

		if (currentCommands.isMoved()) {
			currentCommands.setMoved(false);
		}
		
		if (currentCommands.isMelt()) {
			// robot wishes to melt if frozen
			melt = true;
			currentCommands.setMelt(false);
		}
	}

	protected void fireBullets(List<BulletCommand> bulletCommands) {
		BulletPeer newBullet = null;

		for (BulletCommand bulletCmd : bulletCommands) {
			if (Double.isNaN(bulletCmd.getPower())) {
				println("SYSTEM: You cannot call fire(NaN)");
				continue;
			}
			if (gunHeat > 0 || energy == 0) {
				return;
			}
			double firePower;

			/*
			 * Avoid using the factor of the RobotAttributes, if they are 1.0
			 * or very close too.  This is to avoid unnecessary double
			 * multiplication, which was causing some bugs.
			 */
			if((getMinBulletPower() * getMaxBulletPower() * getEnergyRegen()) -
					1.0 < 0.00001){
				firePower = min(energy,
						min(max(bulletCmd.getPower(), Rules.MIN_BULLET_POWER),
								Rules.MAX_BULLET_POWER));
			}
			else{
				firePower = min(energy, min(max(bulletCmd.getPower(),
						getMinBulletPower()), getMaxBulletPower())) * getEnergyRegen();
			}

			updateEnergy(-firePower);

			gunHeat += getGunHeat(firePower);

			newBullet = new BulletPeer(this, battleRules, bulletCmd.getBulletId());

			newBullet.setPower(firePower);
			if (!turnedRadarWithGun || !bulletCmd.isFireAssistValid() || statics.isAdvancedRobot()) {
				newBullet.setHeading(gunHeading);
			} else {
				newBullet.setHeading(bulletCmd.getFireAssistAngle());
			}
			newBullet.setX(x);
			newBullet.setY(y);
		}
		// there is only last bullet in one turn
		if (newBullet != null) {
			// newBullet.update(robots, bullets);
			battle.addBullet(newBullet);
		}
	}
	
	protected void fireLandmines(List<LandmineCommand> landmineCommands) {
		LandminePeer newLandmine = null;

		for (LandmineCommand landmineCmd : landmineCommands) {
			if (Double.isNaN(landmineCmd.getPower())) {
				println("SYSTEM: You cannot call fire(NaN)");
				continue;
			}
			if (gunHeat > 0 || energy == 0) {
				return;
			}
			double firePower;
			
			/*
			 * Avoid using the factor of the RobotAttributes, if they are 1.0
			 * or very close too.  This is to avoid unnecessary double
			 * multiplication, which was causing some bugs.
			 */
			if((getMinBulletPower() * getMaxBulletPower() * getEnergyRegen()) -
					1.0 < 0.00001){
				firePower = min(energy,
						min(max(landmineCmd.getPower(), Rules.MIN_BULLET_POWER),
								Rules.MAX_BULLET_POWER));
			}
			else{
				firePower = min(energy, min(max(landmineCmd.getPower(), 
						getMinBulletPower()), getMaxBulletPower())) * getEnergyRegen();
			}
			
			updateEnergy(-firePower);

			gunHeat += getGunHeat(firePower);

			newLandmine = new LandminePeer(this, battleRules, landmineCmd.getLandmineId());

			newLandmine.setPower(firePower);
			/**
			if (!turnedRadarWithGun  || statics.isAdvancedRobot()) {
				newLandmine.setHeading(gunHeading);
			} else {
				newBullet.setHeading(bulletCmd.getFireAssistAngle());
			}
			*/
			newLandmine.setX(x);
			newLandmine.setY(y);
		}
		// there is only last bullet in one turn
		if (newLandmine != null) {
			// newBullet.update(robots, bullets);
			battle.addLandmine(newLandmine);
		}
	}

	@Override
	public final void performMove(List<RobotPeer> robots, List<ItemDrop> items, List<ObstaclePeer> obstacles, List<ZLevelPeer> zLevels, double zapEnergy, List<TeleporterPeer> teleporters) {

		// Reset robot state to active if it is not dead
		if (isDead()) {
			return;
		}
		
		if (seenAsFrozen > 0)
			seenAsFrozen--;
		
		// Creates a new instance of KillFreezeHeatRobots class
		KillFreezeHeatRobots killFreezeHeatRobot = new KillFreezeHeatRobots();
		
		// Calls the killFreezeHeatRobots method on the robots list
		killFreezeHeatRobot.killFreezeHeatRobot(robots);
		
		// Stop the robot being both dead and frozen.
		if (isFrozen() && energy > 0) {
			if(melt == true){
				robotFrozen = 1; //unfreeze robot
				if(meltCredit > 0)
					meltCredit--;
				else
					energy *= 0.7; //sacrifice 30% of health to do so
				melt = false;
			}
			robotFrozen--;
			if (robotFrozen != 0) {
				return;
			}
			setState(RobotState.ACTIVE);
		}

		if (isSuperTank && (battle.getTotalTurns() >= superTankTimeout)) {
			setSuperTank(false);
		}
		
		// check radar jamming robots for timeout
		if ((!isScannable) && (battle.getTotalTurns() >= radarJammerTimeout)) {
			setScannable(true);
            
            /* remove the image from the battle */
            battle.removeCustomObject(ksImages.get("jammer"));
            
            /* remove the image from the robot */
            ksImages.remove("jammer");
		} else if ((!isScannable) && (battle.getTotalTurns() <= radarJammerTimeout)) {
            /* check if robot has image */
            if (ksImages.containsKey("jammer")) {
                    /* move the image to the robots coordinates */
            		
                    ksImages.get("jammer").setTranslate(this.getX(), this.getY());
            }

		}

		// check if a frozen robot can move again
		if (isKsFrozen() && (battle.getTotalTurns() >= frozenTimeout)) {
			setKsFrozen(false);
			/* remove the image from the battle */
            battle.removeCustomObject(ksImages.get("freeze"));
            
            /* remove the image from the robot */
            ksImages.remove("freeze");
		} else if (isKsFrozen() && (battle.getTotalTurns() <= frozenTimeout)) {
			if (ksImages.containsKey("freeze")) {
				ksImages.get("freeze").setTranslate(this.getX(), this.getY());
			}

		}
		
		/* check if a robot has died while frozen */
		if (isKsFrozen() && !isAlive()) {
			/* remove the image from the battle */
            battle.removeCustomObject(ksImages.get("freeze"));
            
            /* remove the image from the robot */
            ksImages.remove("freeze");
		}
		
		/* check if a robot has died while frozen */
		if (isKsFrozen() && !isAlive()) {
			/* remove the image from the battle */
            battle.removeCustomObject(ksImages.get("freeze"));
            
            /* remove the image from the robot */
            ksImages.remove("freeze");
		}

		// apply super tank bonuses
		if (isSuperTank()) {
			setGunHeatEffect(0.1);
			setEnergyEffect(getStartingEnergy() * 2, inCollision);
			
			/* move the image with the robot */

			ksImages.get("tank").setTranslate(this.getX(), this.getY());
		} else {
			if (ksImages.containsKey("tank")) {
                /* remove the image from the battle */
                battle.removeCustomObject(ksImages.get("tank"));
                
                /* remove the image from the robot */

                ksImages.remove("tank");
			}
		}

		setState(RobotState.ACTIVE);

		updateGunHeat();
		
		lastHeading = bodyHeading;
		lastGunHeading = gunHeading;
		lastRadarHeading = radarHeading;
		final double lastX = x;
		final double lastY = y;
		
		if (!inCollision) {
			updateHeading();
		}

		updateGunHeading();
		updateRadarHeading();
		updateMovement();
		
		// do not move frozen robots
		if (isKsFrozen()) {
			setVelocityEffect(0.1);
		}

		// At this point, robot has turned then moved.
		// We could be touching a wall or another bot...

		// First and foremost, we can never go through a wall:
		checkWallCollision();

		// Now check for robot collision
        checkObstacleCollision(obstacles);        

		// Now check for robot collision
		checkRobotCollision(robots);

		//
		checkTeleporterCollision(teleporters);
		
		// If Dispenser, dispense
		if (isDispenser()) {
			dispenseHealth(robots);
		}

        // Now check for item collision
        checkItemCollision(items);
        
        if(zLevels != null) {
        	checkZLevelCollision(zLevels);
        }
        
        // Scans items
        scanItems(radarHeading, items);
        
        // Now check if the robot has reached the centre of a maze
        if (boundingBox.intersects(getBattleFieldWidth()/2 - 50, getBattleFieldHeight()/2 - 50, 100, 100) && 
        			battle.getBattleMode().toString() == "Maze Mode"){
        	for (RobotPeer otherRobot: robots) {
        		if (!(otherRobot == null || otherRobot == this || otherRobot.isDead()))
        				otherRobot.kill();
        	}
        }
        
		// Scan false means robot did not call scan() manually.
		// But if we're moving, scan
		if (!scan) {
			scan = (lastHeading != bodyHeading || lastGunHeading != gunHeading || lastRadarHeading != radarHeading
					|| lastX != x || lastY != y);
		}

		if (isDead()) {
			return;
		}

		// zap
		if (zapEnergy != 0){
			zap(zapEnergy);
		}
	}

	public void performScan(List<RobotPeer> robots) {
		if (isDead()) {
			return;
		}
		
		if (isFrozen()) {
			addEvent(new RobotFrozenEvent(robotFrozen));
			return;
		}
		
        if(battle.isRaceMode()){
        	checkWaypointPass(way, WIDTH*3.0 - 20); 
        	//checkWaypointPass((Waypoint)(battle.getBattleProperties().getTrackField().getWaypoints()), WIDTH*3.0 - 20); 
        }

		turnedRadarWithGun = false;
		// scan
		if (scan) {
			scan(lastRadarHeading, robots);
			turnedRadarWithGun = (lastGunHeading == lastRadarHeading) && (gunHeading == radarHeading);
			scan = false;
		}

		// dispatch messages
		if (statics.isTeamRobot() && teamPeer != null) {
			for (TeamMessage teamMessage : currentCommands.getTeamMessages()) {
				for (RobotPeer member : teamPeer) {
					if (checkDispatchToMember(member, teamMessage.recipient)) {
						member.addTeamMessage(teamMessage);
					}
				}
			}
		}
		currentCommands = null;
		lastHeading = -1;
		lastGunHeading = -1;
		lastRadarHeading = -1;
	}

	protected void addTeamMessage(TeamMessage message) {
		final List<TeamMessage> queue = teamMessages.get();


		queue.add(message);
	}


	protected boolean checkDispatchToMember(RobotPeer member, String recipient) {
		if (member.isAlive()) {
			if (recipient == null) {
				if (member != this) {
					return true;
				}
			} else {
				final int nl = recipient.length();
				final String currentName = member.statics.getName();

				if ((currentName.length() >= nl && currentName.substring(0, nl).equals(recipient))) {
					return true;
				}

				final String currentClassName = member.statics.getFullClassName();

				if ((currentClassName.length() >= nl && currentClassName.substring(0, nl).equals(recipient))) {
					return true;
				}

			}
		}
		return false;
	}

	public String getNameForEvent(RobotPeer otherRobot) {
		if (battleRules.getHideEnemyNames() && !isTeamMate(otherRobot)) {
			return otherRobot.getAnnonymousName();
		}
		return otherRobot.getName();
	}

	/**
	 * Checks for collisions between the robots and the items, and sends a
	 * HitItemEvent to the robots that hit an item.
	 * 
	 * @param items the list of items on the battlefield
	 */
	// check for, add, remove items
	private boolean checkForItem(ItemDrop item){
		return this.itemsList.contains(item);
	}

	private void addItem(ItemDrop item){
		this.itemsList.add(item);
	}

	private void removeItem(ItemDrop item){
		this.itemsList.remove(item);
	}


	/**
     * 
     * @param waypoint The Maps Waypoint Object.
     * @param waypointDistance The maximum perpendicular distance from the robot that a waypoint
     * can be, and still be scanned.
     */
    private void checkWaypointPass(Waypoint waypoint, Double waypointDistance){
    	
    	
    	//calculate the bearing to the waypoint relative to the robots Heading.
    	double dx = waypoint.getSingleWaypointX(currentWaypointIndex)-x;
		double dy = waypoint.getSingleWaypointY(currentWaypointIndex)-y;

    	double relativeBearingtoWaypoint = (Math.PI/2)-atan2(dy, dx) - bodyHeading;
    	
    	relativeBearingtoWaypoint = normalNearAbsoluteAngle(relativeBearingtoWaypoint);

    	if((Math.abs(relativeBearingtoWaypoint - (Math.PI/2)) < .03) || (Math.abs(relativeBearingtoWaypoint - 
    			(3 * Math.PI / 2)) < .03)){
    		double  distToWay = Math.hypot(dx, dy);

    		//Check if the waypoint is at the maximum distance from the robot or closer.
    		if(distToWay < waypointDistance){
    			if(currentWaypointIndex != waypoint.getNoWaypoints() -1){
    				currentWaypointIndex++;
    				dx = waypoint.getSingleWaypointX(currentWaypointIndex)-x;
    				dy = waypoint.getSingleWaypointY(currentWaypointIndex)-y;
    				relativeBearingtoWaypoint = normalNearAbsoluteAngle((Math.PI/2)-atan2(dy, dx) - bodyHeading);
    				
    				//create the new WaypointPassedEvent	
    				addEvent(new WaypointPassedEvent(currentWaypointIndex, waypoint.getSingleWaypointX(
    					 currentWaypointIndex), waypoint.getSingleWaypointY(currentWaypointIndex), 
    					 relativeBearingtoWaypoint, Math.hypot(dx, dy), distToWay));

    			}else{
    				currentLap++;
    				if(currentLap == 1){//change to totalNOLAps
    					statistics.scoreRace();
    					battle.killRound();
    				}else{
    					//next lap reset robots wayPoints
    					statistics.scoreRace();
    					currentWaypointIndex = 0;
    				}   				
    			}
    		}
    	}
    	
    }
    

	private void checkItemCollision(List<ItemDrop> items){
		List<ItemDrop> itemsDestroyed = new ArrayList<ItemDrop>();
		List<IRenderable> imagesDestroyed = new ArrayList<IRenderable>();

		for (ItemDrop item : items){
			if ( !(item == null) && boundingBox.intersects(item.getBoundingBox())){
				if (item.getHealth() > 0){
					if (item.getIsDestroyable()){
						item.setHealth(item.getHealth() - 20);
					}
				}
				if (item.getHealth() <= 0){
					itemsDestroyed.add(item);
				}
				addEvent(new HitItemEvent(item.getName(), item.getHealth(), item.getIsEquippable(), item.getIsDestroyable()));
				item.doItemEffect(this);
				item.setXLocation(-50);
				item.setYLocation(-50);
			}
		}
		for (ItemDrop item : itemsDestroyed){
			for (IRenderable ob : battle.getCustomObject()){
				if (item.getName().equals(ob.getName())){
					imagesDestroyed.add(ob);
				}
			}
			for (IRenderable ob : imagesDestroyed){
				battle.getCustomObject().remove(ob);
			}
			items.remove(item);
		}
	}

	/**
	* Called by Dispenser bots, for giving health to robots which enter the
	* immediate area. Healing effect intensifies with proximity to Dispenser.
	* Dispenser scores points for healing other robots, and also earns some
	* energy back. Healing effect is not applied to Botzilla or other
	* Dispensers.
	* @param robots List of robots in the current game
	*/
	protected void dispenseHealth(List<RobotPeer> robots) {
		double amount = 0;
		
		for (RobotPeer otherRobot : robots) {
			//max healing range is calculated as (dx^2 + dy^2)/ r^2
			if (pow(otherRobot.x - x, 2) + pow(otherRobot.y - y, 2) < pow(dispenseRadius, 2)) {
				if (!otherRobot.isDispenser() && !otherRobot.isBotzilla()) {

					//Healing scales with proximity
					//Scaling is calculated as (r^2 - dx^2 - dy^2)/r^2
					amount = maxDispenseRate*(
							(pow(dispenseRadius, 2)
							- (pow(otherRobot.x - x, 2)
							+ pow(otherRobot.y - y, 2)))
							/pow(dispenseRadius, 2));

					otherRobot.updateEnergy(amount);

					//Dispenser has an ability to heal self, at a reduced rate
					this.updateEnergy(amount/2);
					
					//Dispenser earns points for healing
					statistics.incrementTotalScore(this.getName());
				}
			}
		}
	}

	/**
	 * Checks whether a robot has collided with a teleporter
	 * @param teleporters list of teleporters
	 */
	private void checkTeleporterCollision(List<TeleporterPeer> teleporters){
		BoundingRectangle bound = getBoundingBox();
		double newHeading = getBodyHeading()+PI;
		double[] xy;
		double[] fail = {-1.0, -1.0};
		double[] death = {-2.0, -2.0};
		while(newHeading>(2*PI)){
			newHeading -=(2*PI);
		}
		for(TeleporterPeer teleporter : teleporters){
			xy = teleporter.getCollisionReaction(bound);
			if(xy.equals(fail)){
				
			}else if(xy[0] == -2 && xy[1] == -2){
				//if there is a collision with a black hole, update size, set
				//the collision to true and kill the robot
				teleporter.updateBlackHoleSize();
				collidedWithBlackHole = true;
				kill();
				
			}else if(xy[0]>0 && xy[1]>0){
				this.x = xy[0]+(Math.sin(newHeading)*50);
				this.y = xy[1]+(Math.cos(newHeading)*50);
				this.bodyHeading = newHeading;
				//update bounding box to prevent neverending teleportation
				updateBoundingBox();
				
			}
		}
		
	}
	
	protected void checkRobotCollision(List<RobotPeer> robots) {
		inCollision = false;

		/*
		 * these robots have special behaviours concerning collisions
    	 * normally, ignore collisions with dispenser
    	 * however, if collision is between Botzilla and dispenser
    	 * wreck the dispenser like a regular robot
    	 */
		boolean dispenserInvolved;
		boolean botzillaInvolved;

        for (RobotPeer otherRobot : robots) {

        	dispenserInvolved = isDispenser() || otherRobot.isDispenser();
        	botzillaInvolved = isBotzilla() || otherRobot.isBotzilla();

        	if (!(otherRobot == null || otherRobot == this || otherRobot.isDead())
                    && boundingBox.intersects(otherRobot.boundingBox)
                    && (!dispenserInvolved || (dispenserInvolved && botzillaInvolved))) {

        		// Bounce back
                double angle = atan2(otherRobot.x - x, otherRobot.y - y);

				double movedx = velocity * sin(bodyHeading);
				double movedy = velocity * cos(bodyHeading);

				boolean atFault;
				double bearing = normalRelativeAngle(angle - bodyHeading);

				if ((velocity > 0 && bearing > -PI / 2 && bearing < PI / 2)
						|| (velocity < 0 && (bearing < -PI / 2 || bearing > PI / 2))) {

					inCollision = true;
					atFault = true;
					velocity = 0;
					currentCommands.setDistanceRemaining(0);
					x -= movedx;
					y -= movedy;

					boolean teamFire = (teamPeer != null && teamPeer == otherRobot.teamPeer);
					
					/**
					 * CSSE2003 Team MCJJ 
					 * TeamCollision - if checkbox is true, owner(this) will receive damage per normal but negate the damage to 
					 * other robot
					 */
					if(TeamCollisionTracker.enableteamCollision == true){
						if(teamFire){
							otherRobot.updateEnergy(+(otherRobot.getRamDamage()));
							this.updateEnergy(-(this.getRamAttack()));
						}
					}
					
					if (!teamFire) {
						statistics.scoreRammingDamage(otherRobot.getName());
					}
					
					//If minion is ramming into parent, do nothing.
					if(isParent(otherRobot))
						continue;
                    // Check if one of the robots is a FreezeRobot, if so, freeze the other robot.
                    if (!checkForFreezeBot(otherRobot) || !checkForHeatBot(otherRobot)) {
                    	
                    	double thisRobotFault = this.getRamDamage();
                    	double thisRobotNotFault = this.getRamDamage();
                    	double otherRobotFault = otherRobot.getRamDamage();
                    	double otherRobotNotFault = otherRobot.getRamDamage();
                    	boolean normalRobot = true;
                    	
                    	// Determine corect value if have Ram Attack or Def Attributes
                    	if (abs(getRamDamage() - 1.0) > 0.00001 || abs(
                    			getRamAttack() - 1.0) > 0.00001){
                    		normalRobot = false;
                    		if(atFault) {
                    			thisRobotFault = getRamDamage() - getRamAttack();
                    			otherRobotNotFault = otherRobot.getRamDamage();
                    		}
                    		else {
                    			thisRobotNotFault = getRamDamage();
                    			otherRobotFault = otherRobot.getRamDamage() - 
                    					otherRobot.getRamAttack();
                    		}
                    	}
                    		
	                    //Use a factor of the armor if it has been changed
	                    //This Robot
                    	if(battle.getBattleMode().shouldDoRamDamage(this, otherRobot)){
		                    if (isBotzilla()) {
		                    	//If this robot is botzilla the other robot dies instantly
		                    	otherRobot.updateEnergy(-(otherRobot.energy + 1));
		                    // Neither Robot has armor
		                    } else if (abs(getRobotArmor() - 1.0) < 0.00001 &&
		                    		abs(otherRobot.getRobotArmor()-1.0) < 0.00001 &&
		                    		normalRobot == false){
		                    	if(atFault) {
		                    		this.updateEnergy(-(thisRobotFault));
		                    		otherRobot.updateEnergy(-(otherRobotNotFault));
		                    	}
		                    	else {
		                    		this.updateEnergy(-(thisRobotNotFault));
		                    		otherRobot.updateEnergy(-(otherRobotFault));
		                    	}
		                    // Other Robot has armor
		                    } else if(abs(getRobotArmor() - 1.0) < 0.00001 &&
		                    		abs(otherRobot.getRobotArmor()-1.0) > 0.00001){
		                    	if(atFault) {
		                    		this.updateEnergy(-thisRobotFault);
		                    		otherRobot.updateEnergy(-(otherRobotNotFault / 
		                    				otherRobot.getRobotArmor()));
		                    	}
		                    	else {
		                    		this.updateEnergy(-thisRobotNotFault);
		                    		otherRobot.updateEnergy(-(otherRobotFault / 
		                    				otherRobot.getRobotArmor()));
		                    	}
		                    // This robot has armor
		                    } else if(abs(getRobotArmor() - 1.0) > 0.00001 &&
		                    		abs(otherRobot.getRobotArmor()-1.0) < 0.00001){
		                    	if(atFault) {
		                    		this.updateEnergy(-(thisRobotFault / 
		                    				getRobotArmor()));
		                    		otherRobot.updateEnergy(-otherRobotNotFault);
		                    	}
		                    	else {
		                    		this.updateEnergy(-(thisRobotNotFault / 
		                    				getRobotArmor()));
		                    		otherRobot.updateEnergy(-otherRobotFault);
		                    	}
		                    // Both robots must have armor
		                    } else {
		                    	if(atFault) {
		                    		this.updateEnergy(-(thisRobotFault / getRobotArmor()));
		                    		otherRobot.updateEnergy(-(otherRobotNotFault / 
		                    				otherRobot.getRobotArmor()));
		                    	}
		                    	else {
		                    		this.updateEnergy(-(thisRobotNotFault / 
		                    				getRobotArmor()));
		                    		otherRobot.updateEnergy(-(otherRobotFault / 
		                    				otherRobot.getRobotArmor()));
		                    	}
		                    }
		                    
		                    // Other Robot
		                    if (otherRobot.isBotzilla()) {
		                    	//If the other robot is botzilla then this robot dies instantly
		                    	updateEnergy(-(energy + 1));
		                    }
		                    
		                    if(normalRobot){
		                    	this.updateEnergy(-(this.getRamDamage()));
		                    	otherRobot.updateEnergy(-(otherRobot.getRamDamage()));
		                    }
                    	}
                    }                    
					
					
                    if (otherRobot.energy == 0) {
                        if (otherRobot.isAlive()) {
                            otherRobot.kill();
                            
                            FreezeRobotDeath massFreeze = new FreezeRobotDeath(otherRobot, this);
    						massFreeze.freezeEverything(robots);
    						
                            if (battle.getBattleMode().respawnsOn()) {
                            	otherRobot.respawn(robots);
                            }
                            
                            if (!teamFire) {
                                final double bonus = statistics.scoreRammingKill(otherRobot.getName());

                                if (bonus > 0) {
                                    println(
                                            "SYSTEM: Ram bonus for killing " + this.getNameForEvent(otherRobot) + ": "
                                            + (int) (bonus + .5));
                                }
                            }
                        }
                    }
                    addEvent(
                            new HitRobotEvent(getNameForEvent(otherRobot), normalRelativeAngle(angle - bodyHeading),
                                              otherRobot.energy, atFault, otherRobot.isHeatRobot()));
                    otherRobot.addEvent(
                            new HitRobotEvent(getNameForEvent(this),
                                              normalRelativeAngle(PI + angle - otherRobot.getBodyHeading()), energy, false, isHeatRobot()));
                }
            }
        }
        if (inCollision) {
            setState(RobotState.HIT_ROBOT);
        }
          
    }
	
	/**
	 * Checks if this robot or the other robot are FreezeRobots. 
	 * If so will freeze the robot that is not a FreezeRobot.
	 * @param otherRobot: The other robot in the collision
	 * @return true if one of the robots is a FreezeRobot, false otherwise
	 */
	protected boolean checkForFreezeBot(RobotPeer otherRobot) {
		// Make sure no more than one robot is a FreezeRobot
		// FreezeRobot cannot freeze another FreezeRobot
		if (!this.isFreezeRobot() || !otherRobot.isFreezeRobot()) {
			if (otherRobot.isFreezeRobot()) {
				makeFrozen(this, 100);
				return true;
			}
			
			if (this.isFreezeRobot()) {
				makeFrozen(otherRobot, 100);
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Freezes robot for length amount of turns 
	 * @param robot: robot to freeze
	 * @param turns: turns frozen for
	 */
	public void makeFrozen(RobotPeer robot, int turns){
		robot.setState(RobotState.FROZEN);
		robot.robotFrozen = turns;
		robot.seenAsFrozen = turns;
	}

	/**
	 * Checks if this robot or the other robot is a HeatRobot. 
	 * If so, the HeatRobot will give meltCredit to the robot that has hit it.
	 * @param otherRobot: The other robot in the collision
	 * @return true if one of the robots is a HeatRobot, false otherwise
	 */
	protected boolean checkForHeatBot(RobotPeer otherRobot) {
		// Make sure no more than one robot is a HeatRobot
		// HeatRobot gives meltCredit to robot that has hit it
		if (!this.isHeatRobot() || !otherRobot.isHeatRobot()) {
			if (otherRobot.isHeatRobot()) {
				meltCredit++;
				return true;
			}
			
			if (this.isHeatRobot()) {
				otherRobot.meltCredit++;
				return true;
			}
		}
		
		return false;
	}
	
	
	protected void checkObstacleCollision(List<ObstaclePeer> obstacles) {
        boolean hitObstacle = false;
        double angle = 0;
        double movedx = velocity * sin(bodyHeading);
        double movedy = velocity * cos(bodyHeading);

        for (ObstaclePeer obstacle : obstacles) {
        	obstacle.updateBoundingBox();

        	if (!(obstacle == null) && obstacle.getBoundingBox().intersects(boundingBox)) {
        		hitObstacle = true;
        		angle = atan2(obstacle.getX() - x, obstacle.getY() - y);
        	}
        }

        if (hitObstacle) {
        	addEvent(new HitWallEvent(normalRelativeAngle(angle - bodyHeading)));
        	velocity = 0;
        	x -= movedx;
            y -= movedy;

        	updateBoundingBox();
        	currentCommands.setDistanceRemaining(0);
            setState(RobotState.HIT_WALL);
        }
    }

	protected void checkWallCollision() {
		boolean hitWall = false;
		double fixx = 0, fixy = 0;
		double angle = 0;

		if (x > getBattleFieldWidth() - HALF_WIDTH_OFFSET) {
			hitWall = true;
			fixx = getBattleFieldWidth() - HALF_WIDTH_OFFSET - x;
			angle = normalRelativeAngle(PI / 2 - bodyHeading);
		}

		if (x < HALF_WIDTH_OFFSET) {
			hitWall = true;
			fixx = HALF_WIDTH_OFFSET - x;
			angle = normalRelativeAngle(3 * PI / 2 - bodyHeading);
		}

		if (y > getBattleFieldHeight() - HALF_HEIGHT_OFFSET) {
			hitWall = true;
			fixy = getBattleFieldHeight() - HALF_HEIGHT_OFFSET - y;
			angle = normalRelativeAngle(-bodyHeading);
		}

		if (y < HALF_HEIGHT_OFFSET) {
			hitWall = true;
			fixy = HALF_HEIGHT_OFFSET - y;
			angle = normalRelativeAngle(PI - bodyHeading);
		}

		if (hitWall) {
			addEvent(new HitWallEvent(angle));

			// only fix both x and y values if hitting wall at an angle
			if ((bodyHeading % (Math.PI / 2)) != 0) {
				double tanHeading = tan(bodyHeading);

				// if it hits bottom or top wall
				if (fixx == 0) {
					fixx = fixy * tanHeading;
				} // if it hits a side wall
				else if (fixy == 0) {
					fixy = fixx / tanHeading;
				} // if the robot hits 2 walls at the same time (rare, but just in case)
				else if (abs(fixx / tanHeading) > abs(fixy)) {
					fixy = fixx / tanHeading;
				} else if (abs(fixy * tanHeading) > abs(fixx)) {
					fixx = fixy * tanHeading;
				}
			}
			x += fixx;
			y += fixy;

			x = (HALF_WIDTH_OFFSET >= x)
					? HALF_WIDTH_OFFSET
					: ((getBattleFieldWidth() - HALF_WIDTH_OFFSET < x) ? getBattleFieldWidth() - HALF_WIDTH_OFFSET : x);
			y = (HALF_HEIGHT_OFFSET >= y)
					? HALF_HEIGHT_OFFSET
					: ((getBattleFieldHeight() - HALF_HEIGHT_OFFSET < y) ? getBattleFieldHeight() - HALF_HEIGHT_OFFSET : y);

			// Update energy, but do not reset inactiveTurnCount
			if (isBotzilla() || isHouseRobot() || isHeatRobot()) { // These robots will not get damage from walls.
				// Do nothing
			} else if (statics.isAdvancedRobot()) {
				setEnergy(energy - Rules.getWallHitDamage(velocity), false);
			}

			updateBoundingBox();

			currentCommands.setDistanceRemaining(0);
			velocity = 0;
		}
		if (hitWall) {
			setState(RobotState.HIT_WALL);
		}
	}

	protected double getBattleFieldHeight() {
		return battleRules.getBattlefieldHeight();
	}

	protected double getBattleFieldWidth() {
		return battleRules.getBattlefieldWidth();
	}

	public void updateBoundingBox() {
		//Botzilla has larger hitbox, and hence requires a special case
		if(isBotzilla()) {
			boundingBox.setRect(x - BZ_WIDTH / 2 + 2, y - BZ_HEIGHT / 2 + 2, BZ_WIDTH - 4, BZ_HEIGHT - 4);
		} else if(isBall()) {
			boundingBox.setRect(x - BALL_WIDTH / 2 + 2, y - BALL_HEIGHT / 2 + 2, BALL_WIDTH - 4, BALL_HEIGHT - 4);
		} else {
			boundingBox.setRect(x - WIDTH / 2 + 2, y - HEIGHT / 2 + 2, WIDTH - 4, HEIGHT - 4);
		}
	}
	
	//TODO
	protected void checkTerrainCollision() {
		
	}
	
	/**
	 * Checks if robot is in the bounds of the track.
	 */
	protected void checkBoundaryCollision() {
		if (battle.getBattleProperties().getTrackField() != null) {
			if (!battle.getBattleProperties().getTrackField().onBounds(x,y)) {
				setState(RobotState.HIT_WALL);
			}
		}
	}
	// TODO: Only add events to robots that are alive? + Remove checks if the Robot is alive before adding the event?
	public void addEvent(Event event) {
		if (isRunning()) {
			final EventQueue queue = events.get();

			if ((queue.size() > EventManager.MAX_QUEUE_SIZE)
					&& !(event instanceof DeathEvent || event instanceof WinEvent || event instanceof SkippedTurnEvent)) {
				println(
						"Not adding to " + statics.getShortName() + "'s queue, exceeded " + EventManager.MAX_QUEUE_SIZE
						+ " events in queue.");
				// clean up old stuff
				queue.clear(battle.getTime() - EventManager.MAX_EVENT_STACK);
				return;
			}
			queue.add(event);
		}
	}
	
	protected void updateGunHeading() {
		if (currentCommands.getGunTurnRemaining() > 0) {
			if (currentCommands.getGunTurnRemaining() < getGunTurnRateRadians()) {
				gunHeading += currentCommands.getGunTurnRemaining();
				radarHeading += currentCommands.getGunTurnRemaining();
				if (currentCommands.isAdjustRadarForGunTurn()) {
					currentCommands.setRadarTurnRemaining(
							currentCommands.getRadarTurnRemaining() -
							currentCommands.getGunTurnRemaining());
				}
				currentCommands.setGunTurnRemaining(0);
			} else {
				gunHeading += getGunTurnRateRadians();
				radarHeading += getGunTurnRateRadians();
				currentCommands.setGunTurnRemaining(currentCommands.
						getGunTurnRemaining() - getGunTurnRateRadians());
				if (currentCommands.isAdjustRadarForGunTurn()) {
					currentCommands.setRadarTurnRemaining(
							currentCommands.getRadarTurnRemaining() - getGunTurnRateRadians());
				}
			}
		} else if (currentCommands.getGunTurnRemaining() < 0) {
			if (currentCommands.getGunTurnRemaining() > -getGunTurnRateRadians()) {
				gunHeading += currentCommands.getGunTurnRemaining();
				radarHeading += currentCommands.getGunTurnRemaining();
				if (currentCommands.isAdjustRadarForGunTurn()) {
					currentCommands.setRadarTurnRemaining(
							currentCommands.getRadarTurnRemaining() -
							currentCommands.getGunTurnRemaining());
				}
				currentCommands.setGunTurnRemaining(0);
			} else {
				gunHeading -= getGunTurnRateRadians();
				radarHeading -= getGunTurnRateRadians();
				currentCommands.setGunTurnRemaining(currentCommands.
						getGunTurnRemaining() + getGunTurnRateRadians());
				if (currentCommands.isAdjustRadarForGunTurn()) {
					currentCommands.setRadarTurnRemaining(
							currentCommands.getRadarTurnRemaining() + getGunTurnRateRadians());
				}
			}
		}
		gunHeading = normalAbsoluteAngle(gunHeading);
	}

	protected void updateHeading() {
		boolean normalizeHeading = true;

		double turnRate = min(currentCommands.getMaxTurnRate(), (.4 + .6 * (1 - (abs(velocity)
				/ getRealMaxVelocity()))) * getMaxTurnRateRadians());

		if (currentCommands.getBodyTurnRemaining() > 0) {
			if (currentCommands.getBodyTurnRemaining() < turnRate) {
				bodyHeading += currentCommands.getBodyTurnRemaining();
				gunHeading += currentCommands.getBodyTurnRemaining();
				radarHeading += currentCommands.getBodyTurnRemaining();
				if (currentCommands.isAdjustGunForBodyTurn()) {
					currentCommands.setGunTurnRemaining(
							currentCommands.getGunTurnRemaining() - currentCommands.getBodyTurnRemaining());
				}
				if (currentCommands.isAdjustRadarForBodyTurn()) {
					currentCommands.setRadarTurnRemaining(
							currentCommands.getRadarTurnRemaining() - currentCommands.getBodyTurnRemaining());
				}
				currentCommands.setBodyTurnRemaining(0);
			} else {
				bodyHeading += turnRate;
				gunHeading += turnRate;
				radarHeading += turnRate;
				currentCommands.setBodyTurnRemaining(currentCommands.getBodyTurnRemaining() - turnRate);
				if (currentCommands.isAdjustGunForBodyTurn()) {
					currentCommands.setGunTurnRemaining(currentCommands.getGunTurnRemaining() - turnRate);
				}
				if (currentCommands.isAdjustRadarForBodyTurn()) {
					currentCommands.setRadarTurnRemaining(currentCommands.getRadarTurnRemaining() - turnRate);
				}
			}
		} else if (currentCommands.getBodyTurnRemaining() < 0) {
			if (currentCommands.getBodyTurnRemaining() > -turnRate) {
				bodyHeading += currentCommands.getBodyTurnRemaining();
				gunHeading += currentCommands.getBodyTurnRemaining();
				radarHeading += currentCommands.getBodyTurnRemaining();
				if (currentCommands.isAdjustGunForBodyTurn()) {
					currentCommands.setGunTurnRemaining(
							currentCommands.getGunTurnRemaining() - currentCommands.getBodyTurnRemaining());
				}
				if (currentCommands.isAdjustRadarForBodyTurn()) {
					currentCommands.setRadarTurnRemaining(
							currentCommands.getRadarTurnRemaining() - currentCommands.getBodyTurnRemaining());
				}
				currentCommands.setBodyTurnRemaining(0);
			} else {
				bodyHeading -= turnRate;
				gunHeading -= turnRate;
				radarHeading -= turnRate;
				currentCommands.setBodyTurnRemaining(currentCommands.getBodyTurnRemaining() + turnRate);
				if (currentCommands.isAdjustGunForBodyTurn()) {
					currentCommands.setGunTurnRemaining(currentCommands.getGunTurnRemaining() + turnRate);
				}
				if (currentCommands.isAdjustRadarForBodyTurn()) {
					currentCommands.setRadarTurnRemaining(currentCommands.getRadarTurnRemaining() + turnRate);
				}
			}
		} else {
			normalizeHeading = false;
		}

		if (normalizeHeading) {
			if (currentCommands.getBodyTurnRemaining() == 0) {
				bodyHeading = normalNearAbsoluteAngle(bodyHeading);
			} else {
				bodyHeading = normalAbsoluteAngle(bodyHeading);
			}
		}
		if (Double.isNaN(bodyHeading)) {
			Logger.realErr.println("HOW IS HEADING NAN HERE");
		}
	}

	protected void updateRadarHeading() {
		if (currentCommands.getRadarTurnRemaining() > 0) {
			if (currentCommands.getRadarTurnRemaining() < getRadarTurnRateRadians()) {
				radarHeading += currentCommands.getRadarTurnRemaining();
				currentCommands.setRadarTurnRemaining(0);
			} else {
				radarHeading += getRadarTurnRateRadians();
				currentCommands.setRadarTurnRemaining(currentCommands.
						getRadarTurnRemaining() - getRadarTurnRateRadians());
			}
		} else if (currentCommands.getRadarTurnRemaining() < 0) {
			if (currentCommands.getRadarTurnRemaining() > - getRadarTurnRateRadians()) {
				radarHeading += currentCommands.getRadarTurnRemaining();
				currentCommands.setRadarTurnRemaining(0);
			} else {
				radarHeading -= getRadarTurnRateRadians();
				currentCommands.setRadarTurnRemaining(
						currentCommands.getRadarTurnRemaining() + getRadarTurnRateRadians());
			}
		}

		radarHeading = normalAbsoluteAngle(radarHeading);
	}

	/**
	 * Updates the robots movement.
	 *
	 * This is Nat Pavasants method described here:
	 *   http://robowiki.net/wiki/User:Positive/Optimal_Velocity#Nat.27s_updateMovement
	 */
	protected void updateMovement() {
		double distance = currentCommands.getDistanceRemaining();

		if (Double.isNaN(distance)) {
			distance = 0;
		}

		velocity = getNewVelocity(velocity, distance);

		// If we are over-driving our distance and we are now at velocity=0
		// then we stopped.
		if (isNear(velocity, 0) && isOverDriving) {
			currentCommands.setDistanceRemaining(0);
			distance = 0;
			isOverDriving = false;
		}

		// If we are moving normally and the breaking distance is more
		// than remaining distance, enabled the overdrive flag.
		if (Math.signum(distance * velocity) != -1) {
			if (getDistanceTraveledUntilStop(velocity) > Math.abs(distance)) {
				isOverDriving = true;
			} else {
				isOverDriving = false;
			}
		}

		currentCommands.setDistanceRemaining(distance - velocity);

		if (velocity != 0) {
			x += velocity * sin(bodyHeading);
			y += velocity * cos(bodyHeading);
			updateBoundingBox();
		}
	}

	protected double getDistanceTraveledUntilStop(double velocity) {
		double distance = 0;

		velocity = Math.abs(velocity);
		while (velocity > 0) {
			distance += (velocity = getNewVelocity(velocity, 0));
		}
		return distance;
	}

	protected double maxRadarScan() {
		double scanRadius =  getRadarScanRadius()/2;

		return scanRadius;
	}
	/**
	 * Returns the new velocity based on the current velocity and distance to move.
	 *
	 * @param velocity the current velocity
	 * @param distance the distance to move
	 * @return the new velocity based on the current velocity and distance to move
	 *
	 * This is Patrick Cupka (aka Voidious), Julian Kent (aka Skilgannon), and Positive's method described here:
	 *   http://robowiki.net/wiki/User:Voidious/Optimal_Velocity#Hijack_2
	 */
	protected double getNewVelocity(double velocity, double distance) {
		if (distance < 0) {
			// If the distance is negative, then change it to be positive
			// and change the sign of the input velocity and the result
			return -getNewVelocity(-velocity, -distance);
		}

		final double goalVel;

		if (distance == Double.POSITIVE_INFINITY) {
			goalVel = currentCommands.getMaxVelocity();
		} else {
			goalVel = Math.min(getMaxVelocity(distance), currentCommands.getMaxVelocity());
		}
		double velocityIncrement = 0d;
		if (velocity >= 0) {
			velocityIncrement = Math.max(velocity - getRobotDeceleration(),
					Math.min(goalVel, velocity + getRobotAcceleration()));
		} else {
			velocityIncrement = Math.max(velocity - getRobotAcceleration(),
					Math.min(goalVel, velocity + maxDecel(-velocity)));
		}
		return battle.getBattleMode().modifyVelocity(velocityIncrement);
	}

	protected double getMaxVelocity(double distance) {

		final double decelTime = Math.max(1, Math.ceil(// sum of 0... decelTime, solving for decelTime using quadratic formula
				(Math.sqrt((4 * 2 / getRobotDeceleration()) * distance + 1) - 1) / 2));

		if (decelTime == Double.POSITIVE_INFINITY) {
			return getRealMaxVelocity();
		}

		final double decelDist = (decelTime / 2.0) * (decelTime - 1) // sum of 0..(decelTime-1)
				* getRobotDeceleration();

		return ((decelTime - 1) * getRobotDeceleration()) + ((distance - decelDist) / decelTime);
	}

	protected double maxDecel(double speed) {
		double decelTime = speed / getRobotDeceleration();
		double accelTime = (1 - decelTime);

		return Math.min(1, decelTime) * getRobotDeceleration() + Math.max
				(0, accelTime) * getRobotAcceleration();
	}

	protected void updateGunHeat() {
		gunHeat -= battleRules.getGunCoolingRate() * attributes.get().get(RobotAttribute.GUN_HEAT_RATE);
		if (gunHeat < 0) {
			gunHeat = 0;
		}
	}

	protected void scan(double lastRadarHeading, List<RobotPeer> robots) {
		if (statics.isDroid()) {
			return;
		}

		double startAngle = lastRadarHeading;
		double scanRadians = getRadarHeading() - startAngle;
		double scanDistance = battle.getBattleMode().modifyVision(Rules.RADAR_SCAN_RADIUS, battleRules);

		// Check if we passed through 360
		if (scanRadians < -PI) {
			scanRadians = 2 * PI + scanRadians;
		} else if (scanRadians > PI) {
			scanRadians = scanRadians - 2 * PI;
		}

		// In our coords, we are scanning clockwise, with +y up
		// In java coords, we are scanning counterclockwise, with +y down
		// All we need to do is adjust our angle by -90 for this to work.
		startAngle -= PI / 2;

		startAngle = normalAbsoluteAngle(startAngle);

		scanArc.setArc(x - scanDistance, y - scanDistance, 2 * scanDistance,
				2 * scanDistance, 180.0 * startAngle / PI, 180.0 * scanRadians / PI, Arc2D.PIE);

		for (RobotPeer otherRobot : robots) {
			if (!(otherRobot == null || otherRobot == this || otherRobot.isDead())
					&& intersects(scanArc, otherRobot.boundingBox)) {
				boolean obstructed = false;
				Line2D scanLine = new Line2D.Double(x, y, otherRobot.getX(), otherRobot.getY());
				for (ObstaclePeer obstacle : battle.getObstacleList()) {
					if (scanLine.intersects(obstacle.getBoundingBox())) {
						obstructed = true;
					}
				}
			 	if (obstructed == false) {
					double dx = otherRobot.x - x;
					double dy = otherRobot.y - y;
					double angle = atan2(dx, dy);
					double dist = Math.hypot(dx, dy);
	
	
					// block the scan if the robot is jamming UAV
					if (!otherRobot.isScannable()) {
						return;
					}
					
					//Prevent minions from scanning parents, parents from scanning minions,
					//and minions from scanning other minions.
					if(isParent(otherRobot) || otherRobot.isParent(this)
							|| (this.isMinion() && otherRobot.isMinion() && 
									this.getMinionParent().equals(otherRobot.getMinionParent()))) {
						return;
					}

					final ScannedRobotEvent event = new ScannedRobotEvent(getNameForEvent(otherRobot), otherRobot.energy,
							normalRelativeAngle(angle - getBodyHeading()), dist, otherRobot.getBodyHeading(),
							otherRobot.getVelocity(), otherRobot.isSeenAsFrozen(), otherRobot.isFreezeRobot(), otherRobot.isHeatRobot());
	
					addEvent(event);
				}
			}
		}
	}
	
	/**
	 * Scans the items on the field and returns a ScannedItemEvent to the robot
	 * if an item is scanned.
	 * 
	 * @param lastRadarHeading the heading of the robot's radar
	 * @param items the list of items on the battlefield
	 */
	protected void scanItems(double lastRadarHeading, List<ItemDrop> items) {
		double startAngle = lastRadarHeading;
		double scanRadians = getRadarHeading() - startAngle;
		double scanDistance = battle.getBattleMode().modifyVision(Rules.RADAR_SCAN_RADIUS, battleRules);

		// Check if we passed through 360
		if (scanRadians < -PI) {
			scanRadians = 2 * PI + scanRadians;
		} else if (scanRadians > PI) {
			scanRadians = scanRadians - 2 * PI;
		}

		// In our coords, we are scanning clockwise, with +y up
		// In java coords, we are scanning counterclockwise, with +y down
		// All we need to do is adjust our angle by -90 for this to work.
		startAngle -= PI / 2;

		startAngle = normalAbsoluteAngle(startAngle);

		scanArc.setArc(x - scanDistance, y - scanDistance, 2 * scanDistance,
				2 * scanDistance, 180.0 * startAngle / PI, 180.0 * scanRadians / PI, Arc2D.PIE);
		
		for (ItemDrop item : items) {
			if (!(item == null) && intersects(scanArc, item.getBoundingBox())) {
				if(checkItemLocation(item)) {
					double dx = item.getXLocation() - x;
					double dy = item.getYLocation() - y;
					double angle = atan2(dx, dy);
					double dist = Math.hypot(dx, dy);
				
					final ScannedItemEvent event = new ScannedItemEvent(item.getName(), dist, 
							normalRelativeAngle(angle - getBodyHeading()), item.getXLocation(), item.getYLocation());
				
					addEvent(event);
				}
			}
		}
	}
	
	/**
	 * Ensures item checked is actually on the battlefield.
	 * 
	 * @param item the item to be checked
	 * @return {@code true} if the item is on the field; {@code false} otherwise
	 */
	private boolean checkItemLocation(ItemDrop item) {
		double x = item.getXLocation();
		double y = item.getYLocation();
		double fieldHeight = this.getBattleFieldHeight();
		double fieldWidth = this.getBattleFieldWidth();
		
		if ((x < fieldWidth) && (y < fieldHeight) && !(x < 0) && !(y < 0)) {
			return true;
		} else {
			return false;
		}
	}

	protected boolean intersects(Arc2D arc, Rectangle2D rect) {
		return (rect.intersectsLine(arc.getCenterX(), arc.getCenterY(), arc.getStartPoint().getX(),
				arc.getStartPoint().getY()))
				|| arc.intersects(rect);
	}

	protected void zap(double zapAmount) {
		if (energy == 0) {
			kill();
			return;
		}
		energy -= abs(zapAmount);
		if (energy < .1) {
			energy = 0;
			currentCommands.setDistanceRemaining(0);
			currentCommands.setBodyTurnRemaining(0);
		}
	}

	public void setRunning(boolean value) {
		isRunning.set(value);
	}

	public void drainEnergy() {
		setEnergy(0, true);
		isEnergyDrained = true;
	}

	public void punishBadBehavior(BadBehavior badBehavior) {
		kill(); // Bug fix [2828479] - Missed onRobotDeath events

		statistics.setInactive();

		final IRobotRepositoryItem repositoryItem = (IRobotRepositoryItem) HiddenAccess.getFileSpecification(
				robotSpecification);

		StringBuffer message = new StringBuffer(getName()).append(' ');

		boolean disableInRepository = false; // Per default, robots are not disabled in the repository

		switch (badBehavior) {
		case CANNOT_START:
			message.append("could not be started or loaded.");
			disableInRepository = true; // Disable in repository when it cannot be started anyways
			break;

		case UNSTOPPABLE:
			message.append("cannot be stopped.");
			break;

		case SKIPPED_TOO_MANY_TURNS:
			message.append("has skipped too many turns.");
			break;

		case SECURITY_VIOLATION:
			message.append("has caused a security violation.");
			disableInRepository = true; // No mercy here!
			break;
		}

		if (disableInRepository) {
			repositoryItem.setValid(false);
			message.append(" This ").append(repositoryItem.isTeam() ? "team" : "robot").append(
					" has been banned and will not be allowed to participate in battles.");
		}

		logMessage(message.toString());
	}



	public void updateEnergy(double delta) {
		if ((!isExecFinishedAndDisabled && !isEnergyDrained) || delta < 0) {
			setEnergy(energy + (delta * getEnergyRegen()), true);
			
			//If selected battle mode is Team energy sharing mode, activate distribution of energy level
			if (battle.getBattleMode().toString() == "Energy Sharing Mode"){
				distributeEnergy();
			}
			
		}
	}

	protected void setEnergy(double newEnergy, boolean resetInactiveTurnCount) {
		if (resetInactiveTurnCount && (energy != newEnergy)) {
			battle.resetInactiveTurnCount(energy - newEnergy);
		}
		energy = newEnergy;
		if (energy < .01) {
			energy = 0;
			ExecCommands localCommands = commands.get();

			localCommands.setDistanceRemaining(0);
			localCommands.setBodyTurnRemaining(0);
		}
	}

	/**
	 * Calculate the team total energy level with the given teampeer, team index and size.
	 * 
	 * @param teamPeerList list of robot in the team (TeamPeer) 
	 * @param teamIndex index of the team
	 * @param teamSize size of the team
	 * @return team's total energy level
	 */
	public int getTotalTeamEnergy(TeamPeer teamPeerList, int teamIndex, int teamSize){
		int totalTeamEnergy = 0;
		
		for (int i=0; i < teamSize; i++){
			if (teamPeerList.getTeamIndex() == teamIndex){
				totalTeamEnergy += teamPeerList.get(i).getEnergy();
			}
		}
		return totalTeamEnergy;
	}

	/**
	 * Assign robot energy level based on the calculated average robot energy level in the team
	 */
	public void distributeEnergy(){
		int totalTeamEnergy = 0;
		double distribute = 0;
		
		//Distribute energy only if there is more than one robot in the team
		if (statics.getTeamSize() > 1) {
			totalTeamEnergy = getTotalTeamEnergy(teamList, statics.getTeamIndex(), statics.getTeamSize());
			distribute = totalTeamEnergy / statics.getTeamSize();
		} else {
			distribute = energy;
		}
		energy = distribute;
	}

	public void setWinner(boolean newWinner) {
		isWinner = newWinner;
	}

	public void kill() {
		battle.resetInactiveTurnCount(10.0);
		if (isAlive()) {
			addEvent(new DeathEvent());
			if (statics.isTeamLeader()) {
				for (RobotPeer teammate : teamPeer) {
					if (teammate.isAlive() && teammate != this) {
						teammate.updateEnergy(-30);

						BulletPeer sBullet = new BulletPeer(this, battleRules, -1);
						LandminePeer sLandmine= new LandminePeer(this,battleRules, -1);
						
						sLandmine.setState(LandmineState.HIT_VICTIM);
						sLandmine.setX(teammate.x);
						sLandmine.setY(teammate.y);
						sLandmine.setVictim(teammate);
						sLandmine.setPower(4);
						battle.addLandmine(sLandmine);
						
						sBullet.setState(BulletState.HIT_VICTIM);
						sBullet.setX(teammate.x);
						sBullet.setY(teammate.y);
						sBullet.setVictim(teammate);
						sBullet.setPower(4);
						battle.addBullet(sBullet);
					}
				}
			}
			battle.registerDeathRobot(this);

			// 'fake' bullet for explosion on self
			final ExplosionPeer fake = new ExplosionPeer(this, battleRules);

			battle.addBullet(fake);
		}
		updateEnergy(-energy);
		

		setState(RobotState.DEAD);
	}

    public void respawn(List<RobotPeer> robots) {
    	this.battle.getBattleMode().onRespawnDeath(this);
    	initializeRound(robots, null);
    }

	public void waitForStop() {
		robotProxy.waitForStopThread();
	}

	/**
	 * Clean things up removing all references to the robot.
	 */
	public void cleanup() {
		battle = null;

		for(RobotPeer minion: minionList) {
		    minion.cleanup();
		}
		
		if (robotProxy != null) {
			robotProxy.cleanup();
			robotProxy = null;
		}

		if (statistics != null) {
			statistics.cleanup();
			statistics = null;
		}

		status = null;
		commands = null;
		events = null;
		teamMessages = null;
		bulletUpdates = null;
		landmineUpdates=null;
		battleText.setLength(0);
		proxyText.setLength(0);
		statics = null;
		battleRules = null;
	}

	public Object getGraphicsCalls() {
		return commands.get().getGraphicsCalls();
	}

	public boolean isTryingToPaint() {
		return commands.get().isTryingToPaint();
	}

	public List<DebugProperty> getDebugProperties() {
		return commands.get().getDebugProperties();
	}

	public void publishStatus(long currentTurn) {

		final ExecCommands currentCommands = commands.get();
		int others = battle.getActiveRobots() - (isAlive() ? 1 : 0);
		RobotStatus stat = HiddenAccess.createStatus(energy, x, y, bodyHeading, gunHeading, radarHeading, velocity,
				currentCommands.getBodyTurnRemaining(), currentCommands.getRadarTurnRemaining(),
				currentCommands.getGunTurnRemaining(), currentCommands.getDistanceRemaining(), gunHeat, others,
				battle.getRoundNum(), battle.getNumRounds(), battle.getTime(), currentCommands.getMaxVelocity(),
				getMaxBulletPower(), getMinBulletPower(), getRobotAcceleration(), getRobotDeceleration(),
				getRadarScanRadius(), getMaxTurnRate(), getGunTurnRate(), getRadarTurnRate(), getRamDamage(),
				getRamAttack());

		status.set(stat);
	}

	public void addBulletStatus(BulletStatus bulletStatus) {
		if (isAlive()) {
			bulletUpdates.get().add(bulletStatus);
		}
	}

	public void addLandmineStatus(LandmineStatus landmineStatus) {
		if (isAlive()) {
			landmineUpdates.get().add(landmineStatus);
		}
	}

	/**
	 * @param attribute
	 *            the attribute to set.
	 * @param value
	 *            the modifier value to set the attribute to.
	 */
	private void setAttributeModifier(RobotAttribute attribute, double value) {
		attributes.get().put(attribute,  value);
	}

	/**
	 * @param attribute
	 *            the attribute to return the modifier value of
	 * @return the modifier value for the given attribute
	 */
	private double getAttributeModifier(RobotAttribute attribute) {
		return attributes.get().get(attribute);
	}

	/**
	 * @param slot
	 *            the slot containing the part to return
	 * @return the part equipped to the given slot, or null if none
	 */
	private EquipmentPart getEquipmentPartInSlot(EquipmentSlot slot) {
		return equipment.get().get(slot);
	}

	/**
	 * If the part's slot attribute matches the given slot, it equips the part
	 * in that slot and loads the attributes provided by the part.
	 * 
	 * This will reset any calls to:
	 * {@link robocode.AdvancedRobot#setMaxVelocity()}
	 * {@link robocode.AdvancedRobot#setMaxTurnRate()}
	 * 
	 * @param name
	 *            the name of the part to equip
	 */
	public void equip(String name) {
		EquipmentPart newPart = battle.getEquipmentPart(name);

		// If no part was found with the given name, don't do anything.
		if (newPart == null) {
			return;
		}

		EquipmentPart oldPart = getEquipmentPartInSlot(newPart.getSlot());

		// Replace whatever's currently occupying this slot with the new part.
		equipment.get().put(newPart.getSlot(), newPart);

		// For every possible robot attribute, subtract the modifier provided by
		// the old part in this slot, and add the modifier provided by the new
		// part being equipped.
		for (RobotAttribute attribute : RobotAttribute.values()) {
			double currentValue = getAttributeModifier(attribute);
			double oldPartValue = oldPart == null ? 0 : oldPart.get(attribute);
			double newPartValue = newPart.get(attribute);
			double newValue = currentValue - oldPartValue + newPartValue;
			setAttributeModifier(attribute, newValue);
		}

		currentCommands.setMaxVelocity(getRealMaxVelocity());
		currentCommands.setMaxTurnRate(getMaxTurnRateRadians());
		energy = energy + getStartingEnergy() - 100;
	}

	/**
	 * @return a collection of all equipment parts equipped to the robot
	 */
	public AtomicReference<Map<EquipmentSlot, EquipmentPart>> getEquipment() {
		return equipment;
	}

	/**
	 * Returns the speed of a bullet given a specific bullet power measured in
	 * pixels/turn.
	 * 
	 * @param bulletPower the energy power of the bullet.
	 * @return bullet speed in pixels/turn
	 */
	public double getBulletSpeed(double bulletPower) {
		bulletPower = Math.min(Math.max(bulletPower, getMinBulletPower()),
				getMaxBulletPower());
		return 20 - 3 * bulletPower;
	}

	/**
	 * Returns the robots acceleration due to the items it has equipped or other
	 * bonuses it may have received.
	 * 
	 * @return The acceleration of the robot associated with this peer.
	 */
	public double getRobotAcceleration() {
		return attributes.get().get(RobotAttribute.ACCELERATION)
				* Rules.ACCELERATION;
	}

	/**
	 * Returns the robots deceleration due to the items it has equipped or other
	 * bonuses it may have received.
	 * 
	 * @return The deceleration of the robot associated with this peer.
	 */
	public double getRobotDeceleration() {
		return attributes.get().get(RobotAttribute.DECELERATION)
				* Rules.DECELERATION;
	}

	/**
	 * Returns the robots radar scan radius due to the items it has equipped or
	 * other bonuses it may have received.
	 * 
	 * @return The scan radius of the robot associated with this peer.
	 */
	public double getRadarScanRadius() {
		return attributes.get().get(RobotAttribute.SCAN_RADIUS)
				* Rules.RADAR_SCAN_RADIUS;
	}

	/**
	 * Returns the robots gun turn rate due to the items it has equipped or
	 * other bonuses it may have received in degrees.
	 * 
	 * @return The turning rate of the gun of the robot associated with this
	 *         peer in degrees.
	 */
	public double getGunTurnRate() {
		// Avoid multiplying doubles if it is not needed
		if (abs(attributes.get().get(RobotAttribute.GUN_TURN_ANGLE) - 1.0) > 0.00001) {
			return attributes.get().get(RobotAttribute.GUN_TURN_ANGLE)
					* Rules.GUN_TURN_RATE;
		} else
			return Rules.GUN_TURN_RATE;
	}

	/**
	 * Returns the robots gun turn rate due to the items it has equipped or
	 * other bonuses it may have received in radians.
	 * 
	 * @return The turning rate of the gun of the robot associated with this
	 *         peer in radians.
	 */
	public double getGunTurnRateRadians() {
		return Math.toRadians(getGunTurnRate());
	}

	/**
	 * Returns the maximum turn rate of the robot due to the items it has
	 * equipped or the bonuses it may have received in degrees.
	 * 
	 * @return The turning rate of the robot associated with this peer in
	 *         degrees
	 */
	public double getMaxTurnRate() {
		return attributes.get().get(RobotAttribute.ROBOT_TURN_ANGLE)
				* Rules.MAX_TURN_RATE;
	}

	/**
	 * Returns the maximum turn rate of the robot due to the items it has
	 * equipped or the bonuses it may have received in radians.
	 * 
	 * @return The turning rate of the robot associated with this peer in
	 *         radians
	 */
	public double getMaxTurnRateRadians() {
		return Math.toRadians(getMaxTurnRate());
	}

	/**
	 * Returns the speed (velocity) of the robot due to the items it has
	 * equipped or the bonuses it may have received.
	 * 
	 * @return the speed (velocity) of the robot associated with this peer.
	 */
	public double getRealMaxVelocity() {
		return attributes.get().get(RobotAttribute.VELOCITY)
				* Rules.MAX_VELOCITY;
	}

	/**
	 * Returns the energy (life) of the robot due to the items it has equipped
	 * or the bonuses it may have received.
	 * 
	 * Note: This is the life at the start of the round (not a constant update)
	 * That is it is the energy factor * 100 (base energy). To find the current
	 * energy: @see getEnergy()
	 * 
	 * @return the starting energy of the robot associated with this peer.
	 */
	public double getStartingEnergy() {
		double e = attributes.get().get(RobotAttribute.ENERGY) * 100;
		if(this.isZombie()) {
			return battle.getBattleMode().modifyStartingEnergy(this, e);
		} else {
			return e;
		}
	}

	/**
	 * Returns the current energy regeneration rate of the robot due to the
	 * items it has equipped or other bonuses.
	 * 
	 * @return The current energy regeneration rate of the robot associated with
	 *         this peer.
	 */
	public double getEnergyRegen() {
		return attributes.get().get(RobotAttribute.ENERGY_REGEN);
	}

	/**
	 * Returns the armor of the robot has compared to standard. That is, 1 is
	 * standard armor, 0.5 would be half armor and 2 would be double armor. This
	 * reduces the amount of damage taken in battle, compared to normal (or
	 * increases). This is caused by the items it has equipped or the bonuses it
	 * may have received.
	 * 
	 * @return the current armor factor of the robot associated with this peer.
	 */
	public double getRobotArmor() {
		return attributes.get().get(RobotAttribute.ARMOR);
	}

	/**
	 * Returns the minimum bullet power of a robots bullet, which is the
	 * attribute factor * the value of @see Rules.MIN_BULLET_POWER
	 * 
	 * This is caused by the items the robot has equipped or other bonuses it
	 * may have received.
	 * 
	 * @return the robots minimum bullet power associated with this peer.
	 */
	public double getMinBulletPower() {
		return attributes.get().get(RobotAttribute.BULLET_DAMAGE)
				* Rules.MIN_BULLET_POWER;
	}

	/**
	 * Returns the maximum bullet power of a robots bullet, which is the
	 * attribute factor * the value of @see Rules.MAX_BULLET_POWER
	 * 
	 * This is caused by the items the robot has equipped or other bonuses it
	 * may have received.
	 * 
	 * @return the robots maximum bullet power associated with this peer.
	 */
	public double getMaxBulletPower() {
		return attributes.get().get(RobotAttribute.BULLET_DAMAGE)
				* Rules.MAX_BULLET_POWER;
	}

	/**
	 * Returns the amount the gun will heat for a certain amount of bullet
	 * power, this may be increased by the effects of equipment or other bonuses
	 * (or decreased)
	 * 
	 * @param bulletPower the energy power of the bullet
	 * @return the gun heat.
	 */
	public double getGunHeat(double bulletPower) {
		return attributes.get().get(RobotAttribute.GUN_HEAT_RATE)
				* (1 + (bulletPower / 5));
	}

	/**
	 * Returns the bonus the robot gains from ramming another robot due to the
	 * items equipped or other bonuses it may have received.
	 * 
	 * @return robot's ramming attack bonus associated with this peer.
	 */
	public double getRamAttack() {
		return attributes.get().get(RobotAttribute.RAM_ATTACK)
				* Rules.ROBOT_HIT_BONUS;
	}

	/**
	 * Returns the damage the robot receives from being hit by another robot,
	 * due to having items equipped or receiving other bonuses.
	 * 
	 * @return the amount of damage this robot takes from being hit by another
	 *         robot
	 */
	public double getRamDamage() {
		return attributes.get().get(RobotAttribute.RAM_DEFENSE)
				* Rules.ROBOT_HIT_DAMAGE;
	}

	/**
	 * Returns the radar turn rate of a robot in degrees due to having items
	 * equipped or having received other bonuses.
	 * 
	 * @return the radar turn rate of the robot in degrees
	 */
	public double getRadarTurnRate() {
		return attributes.get().get(RobotAttribute.RADAR_ANGLE)
				* Rules.RADAR_TURN_RATE;
	}

	/**
	 * Returns the radar turn rate of a robot in radians due to having items
	 * equipped or having received other bonuses.
	 * 
	 * @return the radar turn rate of the robot in radians
	 */
	public double getRadarTurnRateRadians() {
		return Math.toRadians(getRadarTurnRate());
	}

	/**
	 * Effects
	 */
	public void setEnergyEffect(double newEnergy, boolean resetInactiveTurnCount) {
		if (resetInactiveTurnCount && (energy != newEnergy)) {
			battle.resetInactiveTurnCount(energy - newEnergy);
		}
		energy = newEnergy;
		if (energy < .01) {
			energy = 0;
			ExecCommands localCommands = commands.get();

			localCommands.setDistanceRemaining(0);
			localCommands.setBodyTurnRemaining(0);
		}
	}

	public void setVelocityEffect(double v) {
		velocity = v;
	}

	public void setGunHeatEffect(double g) {
		gunHeat = g;
	}

	@Override
	public int compareTo(ContestantPeer cp) {
		if (cp.getStatistics() != null) {
			double myScore = statistics.getTotalScore();
			double hisScore = cp.getStatistics().getTotalScore();

			if (statistics.isInRound()) {
				myScore += statistics.getCurrentScore();
				hisScore += cp.getStatistics().getCurrentScore();
			}
			if (myScore < hisScore) {
				return -1;
			}
			if (myScore > hisScore) {
				return 1;
			}
		}
		return 0;
	}

	@Override
	public String toString() {
		return statics.getShortName() + "(" + (int) energy + ") X" + (int) x
				+ " Y" + (int) y + " " + state.toString()
				+ (isSleeping() ? " sleeping " : "")
				+ (isRunning() ? " running" : "") + (isHalt() ? " halted" : "");
	}

	/**
	 * @return the isScannable
	 */
	public boolean isScannable() {
		return isScannable;
	}

	/**
	 * @param isScannable the isScannable to set
	 */
	public void setScannable(boolean isScannable) {
		if (isScannable) {
			this.println("KILLSTREAK: Radar Jammer expired");
		}
		//this.println("setting scannable to " + isScannable);
		this.isScannable = isScannable;
	}

	/**
	 * Enables the RadarJamming ability for jamTime amount of turns. Robots who
	 * call this are unscannable for this duration
	 * 
	 * @param jamTime the amount of time to block other robot's radars
	 */
	public void enableRadarJammer(int jamTime) {
		setScannable(false);

		/* create image */
		RenderObject jammer = new RenderObject("jammer",
				"/net/sf/robocode/ui/images/field.png", this.getX(),
				this.getY());

		/* make it transparent */
		jammer.setAlpha(0.3f);

		/* add it to the battle */
		battle.addCustomObject(jammer);

		/* add it to the robot */
		ksImages.put(jammer.getName(), jammer);

		radarJammerTimeout = battle.getTotalTurns() + jamTime;
	}

	/**
	 * @param isKsFrozen the isKsFrozen to set
	 */
	 public void setKsFrozen(boolean isKsFrozen) {
		if (!isKsFrozen) {
			this.println("KILLSTREAK: Freeze expired");
		}
		this.isKsFrozen = isKsFrozen;
	}
	 
	/**
	 * Returns whether the robot is frozen by the RobotFreeze killstreak
	 * 
	 * @return the frozen (killstreak) status
	 */
	public boolean isKsFrozen() {
		return this.isKsFrozen;
	}

	/**
	 * Freezes the robot for freezeTime amount of turns
	 * 
	 * @param freezeTime the amount of time to freeze the robot
	 */
	public void enableKsFreeze(int freezeTime) {
		if (this.isAlive()) {
			setKsFrozen(true);
			/* create image */
			RenderObject freeze = new RenderObject("freeze",
					"/net/sf/robocode/ui/images/cube.png", this.getX(),
					this.getY());

			/* make it transparent */
			freeze.setAlpha(0.3f);

			/* add it to the battle */
			battle.addCustomObject(freeze);

			/* add it to the robot */
			ksImages.put(freeze.getName(), freeze);

			frozenTimeout = battle.getTotalTurns() + freezeTime;
		}
	}

	/**
	 * @param isSuperTank the isSuperTank to set
	 */
	private void setSuperTank(boolean isSuperTank) {
		if(!isSuperTank) {
			this.println("KILLSTREAK: Super Tank expired");
		}
		this.isSuperTank = isSuperTank;
	}

	/**
	 * Turns robot into a Super Tank for superTankTimeout amount of turns
	 * 
	 * @param superTankTime the amount of time to become a Super Tank
	 */
	public void enableSuperTank(int superTankTime) {
		setSuperTank(true);
		superTankTimeout = battle.getTotalTurns() + superTankTime;

		/* create image */
		RenderObject tank = new RenderObject("tank",
				"/net/sf/robocode/ui/images/star.gif", this.getX(), this.getY());

		/* make it transparent */
		tank.setAlpha(0.3f);

		/* add it to the battle */
		battle.addCustomObject(tank);

		/* add it to the robot */
		ksImages.put(tank.getName(), tank);

		superTankTimeout = battle.getTotalTurns() + superTankTime;
	}

	/**
	 * @return the isSuperTank
	 */
	private boolean isSuperTank() {
		return isSuperTank;
	}
	
	/**
	 * @return true if is a blackhole
	 */
	public boolean collidedWithBlackHole() {
		return collidedWithBlackHole;
	}

	public double getFullEnergy() {
		return fullEnergy;
	}
	
	public int getCurrentWaypointIndex(){
		return currentWaypointIndex;
	}

	public void removeImage(String image) {
		ksImages.remove(image);
		battle.removeCustomObjectByName(image);
	}

	public boolean containsImage(String image) {
		if (ksImages.containsKey(image)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Returns the amount of meltCredit that the robot has
	 * @return meltCredit
	 */
	public int getMeltCredit() {
		return meltCredit;
	}

	/**
	 * Returns the bounding rectangles representing the enemy goal of this robot
	 * in SoccerMode. Returns null in other modes.
	 * @return BoundingRectangle[] - goal boxes
	 */
	public Rectangle2D.Float getEnemyGoal() {
		BoundingRectangle[] goals = battle.getBattleMode().getGoals();
		int index = (getTeamIndex() + 1) % 2;
		if (goals == null) {
			return new Rectangle2D.Float((int) getBattleFieldWidth()
					- SoccerMode.GOALX, (int) (getBattleFieldHeight() / 2) 
					- (SoccerMode.GOALY / 2), SoccerMode.GOALX, 
					SoccerMode.GOALY);
		}
		return goals[index];
	}
	
	/**
	 * Returns the bounding rectangles representing the own goal of this robot
	 * in SoccerMode. Returns null in other modes.
	 * @return BoundingRectangle[] - goal boxes
	 */
	public Rectangle2D.Float getOwnGoal() {
		BoundingRectangle[] goals = battle.getBattleMode().getGoals();
		int index = getTeamIndex() % 2;
		if (goals == null) {
			return new Rectangle2D.Float(0, (int) (getBattleFieldWidth() / 2) 
					- (SoccerMode.GOALY / 2), SoccerMode.GOALX, 
					SoccerMode.GOALY);
		}
		return goals[index];
	}
	
	protected void checkZLevelCollision(List<ZLevelPeer> z) {
		boolean tooSteep = false;
        double angle = 0;
        double dx = velocity * sin(bodyHeading);
        double dy = velocity * cos(bodyHeading);

        for(ZLevelPeer zP : z) {
        	

        	if (zP.getBounds().intersects(boundingBox)) {
        		if(Math.abs(zLevel - zP.getZ()) == 1) {
        			zLevel = zP.getZ();
        		} else {
        			tooSteep = true;
        		}
        		angle = atan2(zP.getBounds().getX() - x, zP.getBounds().getY() - y);
        	}
        }

        if (tooSteep) {
        	addEvent(new HitWallEvent(normalRelativeAngle(angle - bodyHeading)));
        	velocity = 0;
        	x -= dx;
            y -= dy;

        	updateBoundingBox();
        	currentCommands.setDistanceRemaining(0);
            setState(RobotState.HIT_WALL);
        }
	}
}
