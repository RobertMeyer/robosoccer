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
 *     Flemming N. Larsen
 *     - Added getPaintEvents()
 *******************************************************************************/
package net.sf.robocode.host.proxies;

import java.io.File;
import java.security.AccessControlException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.List;
import net.sf.robocode.host.IHostManager;
import net.sf.robocode.host.RobotStatics;
import net.sf.robocode.peer.IRobotPeer;
import net.sf.robocode.repository.IRobotRepositoryItem;
import robocode.*;
import robocode.robotinterfaces.peer.IAdvancedRobotPeer;

/**
 * @author Pavel Savara (original)
 * @author Flemming N. Larsen (contributor)
 */
public class AdvancedRobotProxy extends StandardRobotProxy implements
        IAdvancedRobotPeer {

    public AdvancedRobotProxy(IRobotRepositoryItem specification, IHostManager hostManager, IRobotPeer peer, RobotStatics statics) {
        super(specification, hostManager, peer, statics);
    }

    @Override
    public boolean isAdjustGunForBodyTurn() {
        getCall();
        return commands.isAdjustGunForBodyTurn();
    }

    @Override
    public boolean isAdjustRadarForGunTurn() {
        getCall();
        return commands.isAdjustRadarForGunTurn();
    }

    @Override
    public boolean isAdjustRadarForBodyTurn() {
        getCall();
        return commands.isAdjustRadarForBodyTurn();
    }

    // asynchronous actions
    @Override
    public void setResume() {
        setCall();
        setResumeImpl();
    }

    @Override
    public void setStop(boolean overwrite) {
        setCall();
        setStopImpl(overwrite);
    }

    @Override
    public void setMove(double distance) {
        setCall();
        setMoveImpl(distance);
    }

    @Override
    public void setTurnBody(double radians) {
        setCall();
        setTurnBodyImpl(radians);
    }

    @Override
    public void setTurnGun(double radians) {
        setCall();
        setTurnGunImpl(radians);
    }

    @Override
    public void setTurnRadar(double radians) {
        setCall();
        setTurnRadarImpl(radians);
    }

    // blocking actions
    @Override
    public void waitFor(Condition condition) {
        waitCondition = condition;
        do {
            execute(); // Always tick at least once
        } while (!condition.test());

        waitCondition = null;
    }

    // fast setters
    @Override
    public void setMaxTurnRate(double newTurnRate) {
        setCall();
        if (Double.isNaN(newTurnRate)) {
            println("You cannot setMaxTurnRate to: " + newTurnRate);
            return;
        }
        commands.setMaxTurnRate(newTurnRate);
    }

    @Override
    public void setMaxVelocity(double newVelocity) {
        setCall();
        if (Double.isNaN(newVelocity)) {
            println("You cannot setMaxVelocity to: " + newVelocity);
            return;
        }
        commands.setMaxVelocity(newVelocity);
    }

    // events manipulation
    @Override
    public void setInterruptible(boolean interruptable) {
        setCall();
        eventManager.setInterruptible(eventManager.getCurrentTopEventPriority(), interruptable);
    }

    @Override
    public void setEventPriority(String eventClass, int priority) {
        setCall();
        eventManager.setEventPriority(eventClass, priority);
    }

    @Override
    public int getEventPriority(String eventClass) {
        getCall();
        return eventManager.getEventPriority(eventClass);
    }

    @Override
    public void removeCustomEvent(Condition condition) {
        setCall();
        eventManager.removeCustomEvent(condition);
    }

    @Override
    public void addCustomEvent(Condition condition) {
        setCall();
        eventManager.addCustomEvent(condition);
    }

    @Override
    public void clearAllEvents() {
        setCall();
        eventManager.clearAllEvents(false);
    }

    @Override
    public List<Event> getAllEvents() {
        getCall();
        return eventManager.getAllEvents();
    }

    @Override
    public List<StatusEvent> getStatusEvents() {
        getCall();
        return eventManager.getStatusEvents();
    }

    @Override
    public List<BulletMissedEvent> getBulletMissedEvents() {
        getCall();
        return eventManager.getBulletMissedEvents();
    }

    @Override
    public List<BulletHitBulletEvent> getBulletHitBulletEvents() {
        getCall();
        return eventManager.getBulletHitBulletEvents();
    }

    @Override
    public List<BulletHitEvent> getBulletHitEvents() {
        getCall();
        return eventManager.getBulletHitEvents();
    }

    @Override
    public List<HitByBulletEvent> getHitByBulletEvents() {
        getCall();
        return eventManager.getHitByBulletEvents();
    }

    @Override
    public List<HitRobotEvent> getHitRobotEvents() {
        getCall();
        return eventManager.getHitRobotEvents();
    }

    @Override
    public List<HitWallEvent> getHitWallEvents() {
        getCall();
        return eventManager.getHitWallEvents();
    }

    @Override
    public List<RobotDeathEvent> getRobotDeathEvents() {
        getCall();
        return eventManager.getRobotDeathEvents();
    }

    @Override
    public List<ScannedRobotEvent> getScannedRobotEvents() {
        getCall();
        return eventManager.getScannedRobotEvents();
    }

    // data
    @Override
    public File getDataDirectory() {
        getCall();
        commands.setIORobot();
        return robotFileSystemManager.getWritableDirectory();
    }

    @Override
    public File getDataFile(final String filename) {
        getCall();
        commands.setIORobot();
        if (filename.contains("..")) {
            throw new AccessControlException("no relative path allowed");
        }

        return AccessController.doPrivileged(new PrivilegedAction<File>() {
            @Override
            public File run() {
                return robotFileSystemManager.getDataFile(filename);
            }
        });
    }

    @Override
    public long getDataQuotaAvailable() {
        getCall();
        return robotFileSystemManager.getMaxQuota() - robotFileSystemManager.getQuotaUsed();
    }

    @Override
    public void equip(String partName) {
        peer.equip(partName);
    }
}
