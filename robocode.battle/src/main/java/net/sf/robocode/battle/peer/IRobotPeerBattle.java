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
package net.sf.robocode.battle.peer;

import java.util.List;

import net.sf.robocode.battle.item.ItemDrop;
import robocode.Event;

/**
 * @author Pavel Savara (original)
 */
public interface IRobotPeerBattle extends ContestantPeer {

    void setSGPaintEnabled(boolean enabled);

    RobotStatistics getRobotStatistics();

    TeamPeer getTeamPeer();

    void publishStatus(long currentTurn);

    void addEvent(Event event);

    void setPaintEnabled(boolean enabled);

    void kill();

    void cleanup();

    boolean isDead();

    boolean isAlive();

    boolean isRunning();

    boolean isWinner();

    boolean isTeamLeader();

    void setHalt(boolean value);

    void println(String s);

    void waitWakeup();

    void waitSleeping(long millisWait, int nanosWait);

    void waitForStop();

    void setWinner(boolean newWinner);

    void initializeRound(List<RobotPeer> robots, double[][] initialRobotPositions);

    void startRound(long millisWait, int nanosWait);

    void checkSkippedTurn();

    void performLoadCommands();

    void performMove(List<RobotPeer> robots, List<ItemDrop> items, List<ObstaclePeer> obstacles, List<ZLevelPeer> zLevels, double zapEnergy, List<TeleporterPeer>teleporters);
    
    void performScan(List<RobotPeer> robots);
}
