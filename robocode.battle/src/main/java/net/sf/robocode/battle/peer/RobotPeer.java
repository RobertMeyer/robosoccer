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


import static net.sf.robocode.io.Logger.logMessage;

import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import static java.lang.Math.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.BoundingRectangle;
import net.sf.robocode.battle.ItemDrop;
import net.sf.robocode.battle.EffectArea;
import net.sf.robocode.host.IHostManager;
import net.sf.robocode.host.RobotStatics;
import net.sf.robocode.host.events.EventManager;
import net.sf.robocode.host.events.EventQueue;
import net.sf.robocode.host.proxies.IHostingRobotProxy;
import net.sf.robocode.io.Logger;
import static net.sf.robocode.io.Logger.logMessage;
import net.sf.robocode.peer.*;
import net.sf.robocode.repository.IRobotRepositoryItem;
import net.sf.robocode.security.HiddenAccess;
import net.sf.robocode.serialization.RbSerializer;
import robocode.*;
import robocode.control.RandomFactory;
import robocode.control.RobotSpecification;
import robocode.control.snapshot.BulletState;
import robocode.control.snapshot.RobotState;
import robocode.exception.AbortedException;
import robocode.exception.DeathException;
import robocode.exception.WinException;
import static robocode.util.Utils.*;

import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import static java.lang.Math.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

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
 */
public class RobotPeer implements IRobotPeerBattle, IRobotPeer {

    public static final int WIDTH = 40,
            HEIGHT = 40;
    protected static final int HALF_WIDTH_OFFSET = (WIDTH / 2 - 2),
            HALF_HEIGHT_OFFSET = (HEIGHT / 2 - 2);
    protected static final int MAX_SKIPPED_TURNS = 30;
    protected static final int MAX_SKIPPED_TURNS_WITH_IO = 240;
    protected Battle battle;
    protected RobotStatistics statistics;
    protected final TeamPeer teamPeer;
    protected final RobotSpecification robotSpecification;
    protected IHostingRobotProxy robotProxy;
    protected AtomicReference<RobotStatus> status = new AtomicReference<RobotStatus>();
    protected AtomicReference<ExecCommands> commands = new AtomicReference<ExecCommands>();
    protected AtomicReference<EventQueue> events = new AtomicReference<EventQueue>(new EventQueue());
    protected AtomicReference<List<TeamMessage>> teamMessages = new AtomicReference<List<TeamMessage>>(
            new ArrayList<TeamMessage>());
    protected AtomicReference<List<BulletStatus>> bulletUpdates = new AtomicReference<List<BulletStatus>>(
            new ArrayList<BulletStatus>());
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
    protected boolean scan;
    protected boolean turnedRadarWithGun; // last round
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
    /**
     * An association of values to every RobotAttribute, such that game
     * mechanics can be uniquely determined for each robot based on a variety
     * of factors (such as, e.g., equipment).
     *
     * Attribute values are defined as 1=100%. Thus, in RobotPeer's
     * constructor, all attribute values are initialised to 1.
     *
     * @see RobotAttribute
     */
    protected AtomicReference<Map<RobotAttribute, Double>> attributes =
            new AtomicReference<Map<RobotAttribute, Double>>(
            new HashMap<RobotAttribute, Double>());
    /**
     * Keeps track of the equipment parts equipped to slots to prevent multiple
     * parts being equipped to the same slot.
     *
     * @see #equip()
     * @see #unequip()
     */
    protected AtomicReference<Map<EquipmentSlot, EquipmentPart>> equipment =
            new AtomicReference<Map<EquipmentSlot, EquipmentPart>>(
            new HashMap<EquipmentSlot, EquipmentPart>());

    public RobotPeer(Battle battle, IHostManager hostManager, RobotSpecification robotSpecification, int duplicate, TeamPeer team, int robotIndex) {
        super();

        this.battle = battle;
        this.robotSpecification = robotSpecification;

        this.rbSerializer = new RbSerializer();

        this.boundingBox = new BoundingRectangle();
        this.scanArc = new Arc2D.Double();
        this.teamPeer = team;
        this.state = RobotState.ACTIVE;
        this.battleRules = battle.getBattleRules();

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

        this.robotProxy = (IHostingRobotProxy) hostManager.createRobotProxy(robotSpecification, statics, this);
    }

    @Override
    public void println(String s) {
        synchronized (proxyText) {
            battleText.append(s);
            battleText.append("\n");
        }
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

    @Override
    public RobotStatistics getRobotStatistics() {
        return statistics;
    }

    @Override
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

    public boolean isHouseRobot() {
        return statics.isHouseRobot();
    }
    
    public boolean isBall() {
    	return statics.isBall();
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
    
    public boolean isBotzilla() {
    	return statics.isBotzilla();
    }

    @Override
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

    @Override
    public int getRobotIndex() {
        return statics.getRobotIndex();
    }

    @Override
    public int getTeamIndex() {
        return statics.getTeamIndex();
    }

    @Override
    public int getContestantIndex() {
        return getTeamIndex() >= 0 ? getTeamIndex() : getRobotIndex();
    }

    // -------------------
    // status
    // -------------------
    @Override
    public void setPaintEnabled(boolean enabled) {
        isPaintEnabled = enabled;
    }

    public boolean isPaintEnabled() {
        return isPaintEnabled;
    }

    @Override
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

    public int getScanColor() {
        return commands.get().getScanColor();
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

        return new ExecResults(resCommands, resStatus, readoutEvents(), readoutTeamMessages(), readoutBullets(),
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

        return new ExecResults(resCommands, resStatus, readoutEvents(), new ArrayList<TeamMessage>(), readoutBullets(),
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
        newCommands.setMaxVelocity(Math.min(abs(newCommands.getMaxVelocity()), getMaxVelocity()));
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
            final Random random = RandomFactory.getRandom();

            for (int j = 0; j < 1000; j++) {
                x = RobotPeer.WIDTH + random.nextDouble() * (battleRules.getBattlefieldWidth() - 2 * RobotPeer.WIDTH);
                y = RobotPeer.HEIGHT + random.nextDouble() * (battleRules.getBattlefieldHeight() - 2 * RobotPeer.HEIGHT);
                bodyHeading = 2 * Math.PI * random.nextDouble();
                gunHeading = radarHeading = bodyHeading;
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
        } else if (statics.isHouseRobot()) {
            energy = 500;
            //TODO: Change to actual starting spots [Team Awesome]
            x = 0;
            y = 0;
        } else {
            energy = getStartingEnergy();
        }
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
        return true;
    }

    @Override
    public void startRound(long waitMillis, int waitNanos) {
        Logger.logMessage(".", false);

        statistics.initialize();

        ExecCommands newExecCommands = new ExecCommands();

        // Copy the colors from the last commands.
        // Bugfix [2628217] - Robot Colors don't stick between rounds.
        newExecCommands.copyColors(commands.get());

        currentCommands = newExecCommands;
        int others = battle.getActiveRobots() - (isAlive() ? 1 : 0);
        RobotStatus stat = HiddenAccess.createStatus(energy, x, y, bodyHeading, gunHeading, radarHeading, velocity,
                                                     currentCommands.getBodyTurnRemaining(), currentCommands.getRadarTurnRemaining(),
                                                     currentCommands.getGunTurnRemaining(), currentCommands.getDistanceRemaining(), gunHeat, others,
                                                     battle.getRoundNum(), battle.getNumRounds(), battle.getTime());

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
    }

    @Override
    public void performLoadCommands() {
        currentCommands = commands.get();

        fireBullets(currentCommands.getBullets());

        if (currentCommands.isScan()) {
            scan = true;
        }

        if (currentCommands.isIORobot()) {
            isIORobot = true;
        }

        if (currentCommands.isMoved()) {
            currentCommands.setMoved(false);
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
            if ((getMinBulletPower() * getMaxBulletPower() * getEnergyRegen())
                    - 1.0 < 0.00001) {
                firePower = min(energy,
                                min(max(bulletCmd.getPower(), Rules.MIN_BULLET_POWER),
                                    Rules.MAX_BULLET_POWER));
            } else {
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

    @Override
    public final void performMove(List<RobotPeer> robots, List<ItemDrop> items, double zapEnergy) {

        // Reset robot state to active if it is not dead
        if (isDead()) {
            return;
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

        // At this point, robot has turned then moved.
        // We could be touching a wall or another bot...

        // First and foremost, we can never go through a wall:
        checkWallCollision();

        // Now check for robot collision
        checkRobotCollision(robots);

        // Now check for item collision
        checkItemCollision(items);

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
        if (zapEnergy != 0) {
            zap(zapEnergy);
        }
    }

    @Override
    public void performScan(List<RobotPeer> robots) {
        if (isDead()) {
            return;
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

    private void checkItemCollision(List<ItemDrop> items) {
        inCollision = false;
        List<ItemDrop> itemsDestroyed = new ArrayList<ItemDrop>();

        for (ItemDrop item : items) {
            if (!(item == null) && boundingBox.intersects(item.getBoundingBox())) {
                inCollision = true;
                if (item.getHealth() > 0) {
                    if (item.getIsDestroyable()) {
                        item.setHealth(item.getHealth() - 20);
                    }
                }
                if (item.getHealth() <= 0) {
                    itemsDestroyed.add(item);
                }
                //addEvent(new HitItemEvent());
            }
        }
        for (ItemDrop item : itemsDestroyed) {
            items.remove(item);
        }
        if (inCollision) {
            setState(RobotState.HIT_ITEM);
        }
    }

    protected void checkRobotCollision(List<RobotPeer> robots) {
        inCollision = false;

        for (RobotPeer otherRobot : robots) {
            if (!(otherRobot == null || otherRobot == this || otherRobot.isDead())
                    && boundingBox.intersects(otherRobot.boundingBox)) {
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

                    if (!teamFire) {
                        statistics.scoreRammingDamage(otherRobot.getName());
                    }

                    //Use a factor of the armor if it has been changed
                    //This Robot
                    if (otherRobot.isBotzilla()) {
                    	this.updateEnergy(-(this.energy));
                	} else if (getRobotArmor() - 1.0 < 0.00001) {
                        this.updateEnergy(-(this.getRamDamage()));
                    } else {
                        this.updateEnergy(-(this.getRamDamage()
                                            * 1 / this.getRobotArmor()));
                    }

                    // Other Robot
                    if (otherRobot.isBotzilla()) {
                    	//do nothing to Botzilla
                    } else if (otherRobot.getRobotArmor() - 1.0 < 0.00001) {
                        otherRobot.updateEnergy(-(otherRobot.getRamDamage()));
                    } else {
                        otherRobot.updateEnergy(-(otherRobot.getRamDamage()
                                                  / 1 / otherRobot.getRobotArmor()));
                    }

                    if (otherRobot.energy == 0) {
                        if (otherRobot.isAlive()) {
                            otherRobot.kill();
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
                                              otherRobot.energy, atFault));
                    otherRobot.addEvent(
                            new HitRobotEvent(getNameForEvent(this),
                                              normalRelativeAngle(PI + angle - otherRobot.getBodyHeading()), energy, false));
                }
            }
        }
        if (inCollision) {
            setState(RobotState.HIT_ROBOT);
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
            if (statics.isAdvancedRobot()) {
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
        boundingBox.setRect(x - WIDTH / 2 + 2, y - HEIGHT / 2 + 2, WIDTH - 4, HEIGHT - 4);
    }

    // TODO: Only add events to robots that are alive? + Remove checks if the Robot is alive before adding the event?
    @Override
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
                            currentCommands.getRadarTurnRemaining()
                            - currentCommands.getGunTurnRemaining());
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
                            currentCommands.getRadarTurnRemaining()
                            - currentCommands.getGunTurnRemaining());
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
                                                                                 / getMaxVelocity()))) * getMaxTurnRateRadians());

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
            if (currentCommands.getRadarTurnRemaining() > -getRadarTurnRateRadians()) {
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
        return battle.getBattleMode().modifyVelocity(velocityIncrement, battle.getBattleRules());
    }

    protected double getMaxVelocity(double distance) {

        final double decelTime = Math.max(1, Math.ceil(// sum of 0... decelTime, solving for decelTime using quadratic formula
                (Math.sqrt((4 * 2 / getRobotDeceleration()) * distance + 1) - 1) / 2));

        if (decelTime == Double.POSITIVE_INFINITY) {
            return getMaxVelocity();
        }

        final double decelDist = (decelTime / 2.0) * (decelTime - 1) // sum of 0..(decelTime-1)
                * getRobotDeceleration();

        return ((decelTime - 1) * getRobotDeceleration()) + ((distance - decelDist) / decelTime);
    }

    protected double maxDecel(double speed) {
        double decelTime = speed / getRobotDeceleration();
        double accelTime = (1 - decelTime);

        return Math.min(1, decelTime) * getRobotDeceleration() + Math.max(0, accelTime) * getRobotAcceleration();
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

        scanArc.setArc(x - Rules.RADAR_SCAN_RADIUS, y - Rules.RADAR_SCAN_RADIUS, 2 * Rules.RADAR_SCAN_RADIUS,
                       2 * Rules.RADAR_SCAN_RADIUS, 180.0 * startAngle / PI, 180.0 * scanRadians / PI, Arc2D.PIE);

        for (RobotPeer otherRobot : robots) {
            if (!(otherRobot == null || otherRobot == this || otherRobot.isDead())
                    && intersects(scanArc, otherRobot.boundingBox)) {
                double dx = otherRobot.x - x;
                double dy = otherRobot.y - y;
                double angle = atan2(dx, dy);
                double dist = Math.hypot(dx, dy);

                final ScannedRobotEvent event = new ScannedRobotEvent(getNameForEvent(otherRobot), otherRobot.energy,
                                                                      normalRelativeAngle(angle - getBodyHeading()), dist, otherRobot.getBodyHeading(),
                                                                      otherRobot.getVelocity());

                addEvent(event);
            }
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

    @Override
    public void setRunning(boolean value) {
        isRunning.set(value);
    }

    @Override
    public void drainEnergy() {
        setEnergy(0, true);
        isEnergyDrained = true;
    }

    @Override
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
            setEnergy(energy + delta, true);
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

    @Override
    public void setWinner(boolean newWinner) {
        isWinner = newWinner;
    }

    @Override
    public void kill() {
        battle.resetInactiveTurnCount(10.0);
        if (isAlive()) {
            addEvent(new DeathEvent());
            if (statics.isTeamLeader()) {
                for (RobotPeer teammate : teamPeer) {
                    if (teammate.isAlive() && teammate != this) {
                        teammate.updateEnergy(-30);

                        BulletPeer sBullet = new BulletPeer(this, battleRules, -1);

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
    	initializeRound(robots, null);
    }

    @Override
    public void waitForStop() {
        robotProxy.waitForStopThread();
    }

    /**
     * Clean things up removing all references to the robot.
     */
    @Override
    public void cleanup() {
        battle = null;

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

    @Override
    public void publishStatus(long currentTurn) {

        final ExecCommands currentCommands = commands.get();
        int others = battle.getActiveRobots() - (isAlive() ? 1 : 0);
        RobotStatus stat = HiddenAccess.createStatus(energy, x, y, bodyHeading, gunHeading, radarHeading, velocity,
                                                     currentCommands.getBodyTurnRemaining(), currentCommands.getRadarTurnRemaining(),
                                                     currentCommands.getGunTurnRemaining(), currentCommands.getDistanceRemaining(), gunHeat, others,
                                                     battle.getRoundNum(), battle.getNumRounds(), battle.getTime());

        status.set(stat);
    }

    public void addBulletStatus(BulletStatus bulletStatus) {
        if (isAlive()) {
            bulletUpdates.get().add(bulletStatus);
        }
    }

    /**
     * If the part's slot attribute matches the given slot, it equips the part
     * in that slot and loads the attributes provided by the part.
     *
     * @param partName the name of the part to equip
     * @see Equipment
     */
    @Override
    public void equip(String partName) {
        EquipmentPart part = Equipment.getPart(partName);

        // Unequip whatever's currently occupying this slot (if anything)
        unequip(part.getSlot());

        /* Add all the attribute modifiers of the part to the current
         * attribute modifiers (many attributes of the part may be 0).
         */
        for (RobotAttribute attribute : RobotAttribute.values()) {
            double partValue = part.get(attribute);
            double currentValue = part.get(attribute);

            /* Part modifiers are represented as 1=+1% effectiveness, hence
             * the division by 100 (as this.attributes represents 1.0 as 100%
             * effectiveness for easy multiplication).
             */
            double newValue = currentValue + (partValue / 100.0);

            attributes.get().put(attribute, newValue);
        }
    }

    /**
     * Unequips the part equipped to the given slot, if any, and resets all
     * attributes provided by the part.
     *
     * @param slot the slot to clear
     */
    public void unequip(EquipmentSlot slot) {
        EquipmentPart part = equipment.get().get(slot);

        /* If there is any part in the given slot, add all the attribute
         * modifiers of the part to the current attribute modifiers (many
         * attributes of the part may be 0).
         */
        if (part != null) {
            for (RobotAttribute attribute : RobotAttribute.values()) {
                double partValue = part.get(attribute);
                double currentValue = attributes.get().get(attribute);

                /* Part modifiers are represented as 1=+1% effectiveness,
                 * hence the division by 100 (as this.attributes represents
                 * 1.0 as 100% effectiveness for easy multiplication).
                 */
                double newValue = currentValue - (partValue / 100.0);

                attributes.get().put(attribute, newValue);
            }
        }
    }

    /**
     * @return a collection of all equipment parts equipped to the robot
     */
    public AtomicReference<Map<EquipmentSlot, EquipmentPart>> getEquipment() {
        return equipment;
    }

    /**
     * Returns the speed of a bullet given a specific bullet power measured in pixels/turn.
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
     * Returns the robots acceleration due to the items it has equipped or
     * other bonuses it may have received.
     *
     * @return The acceleration of the robot associated with this peer.
     */
    public double getRobotAcceleration() {
        return attributes.get().get(RobotAttribute.ACCELERATION)
                * Rules.ACCELERATION;
    }

    /**
     * Returns the robots deceleration due to the items it has equipped or
     * other bonuses it may have received.
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
     * 			peer in degrees.
     */
    public double getGunTurnRate() {
        // Avoid multiplying doubles if it is not needed
        if (attributes.get().get(RobotAttribute.GUN_TURN_ANGLE) - 1.0 < 0.00001) {
            return attributes.get().get(RobotAttribute.GUN_TURN_ANGLE) * Rules.GUN_TURN_RATE;
        } else {
            return Rules.GUN_TURN_RATE;
        }
    }

    /**
     * Returns the robots gun turn rate due to the items it has equipped or
     * other bonuses it may have received in radians.
     *
     * @return The turning rate of the gun of the robot associated with this
     * 			peer in radians.
     */
    public double getGunTurnRateRadians() {
        return Math.toRadians(getGunTurnRate());
    }

    /**
     * Returns the maximum turn rate of the robot due to the items it has
     * equipped or the bonuses it may have received in degrees.
     *
     * @return The turning rate of the robot associated with this peer in
     * 			degrees
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
     * 			radians
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
    public double getMaxVelocity() {
        return attributes.get().get(RobotAttribute.SPEED) * Rules.MAX_VELOCITY;
    }

    /**
     * Returns the energy (life) of the robot due to the items it has equipped
     * or the bonuses it may have received.
     *
     * Note: This is the life at the start of the round (not a constant update)
     * 		That is it is the energy factor * 100 (base energy). To find the
     * 		current energy: @see getEnergy()
     *
     * @return the starting energy of the robot associated with this peer.
     */
    public double getStartingEnergy() {
        return attributes.get().get(RobotAttribute.ENERGY) * 100;
    }

    /**
     * Returns the current energy regeneration rate of the robot due to the
     * items it has equipped or other bonuses.
     *
     * @return The current energy regeneration rate of the robot associated
     * 			with this peer.
     */
    public double getEnergyRegen() {
        return attributes.get().get(RobotAttribute.ENERGY_REGEN);
    }

    /**
     * Returns the armor of the robot has compared to standard. That is, 1 is
     * standard armor, 0.5 would be half armor and 2 would be double armor.
     * This reduces the amount of damage taken in battle, compared to normal
     * (or increases).  This is caused by the items it has equipped or the
     * bonuses it may have received.
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
     * This is caused by the items the robot has equipped or other bonuses
     * it may have received.
     *
     * @return the robots minimum bullet power associated with this peer.
     */
    public double getMinBulletPower() {
        return attributes.get().get(RobotAttribute.BULLET_DAMAGE) * Rules.MIN_BULLET_POWER;
    }

    /**
     * Returns the maximum bullet power of a robots bullet, which is the
     * attribute factor * the value of @see Rules.MAX_BULLET_POWER
     *
     * This is caused by the items the robot has equipped or other bonuses
     * it may have received.
     *
     * @return the robots maximum bullet power associated with this peer.
     */
    public double getMaxBulletPower() {
        return attributes.get().get(RobotAttribute.BULLET_DAMAGE) * Rules.MAX_BULLET_POWER;
    }

    /**
     * Returns the amount the gun will heat for a certain amount of bullet
     * power, this may be increased by the effects of equipment or other
     * bonuses (or decreased)
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
     * 		robot
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
        return attributes.get().get(RobotAttribute.RADAR_ANGLE) * Rules.RADAR_TURN_RATE;
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
	
	public void setVelocityEffect(double v)
	{
		velocity = v;
	}
	
	public void setGunHeatEffect(double g)
	{
		gunHeat = g;
	}
	
    @Override
    public int compareTo(ContestantPeer cp) {
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
        return 0;
    }

    @Override
    public String toString() {
        return statics.getShortName() + "(" + (int) energy + ") X" + (int) x + " Y" + (int) y + " " + state.toString()
                + (isSleeping() ? " sleeping " : "") + (isRunning() ? " running" : "") + (isHalt() ? " halted" : "");
    }
}
