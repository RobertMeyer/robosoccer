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
package net.sf.robocode.host.events;

import java.util.List;
import robocode.*;

/**
 * @author Pavel Savara (original)
 */
public interface IEventManager {

    void addCustomEvent(Condition condition);

    void removeCustomEvent(Condition condition);

    void clearAllEvents(boolean includingSystemEvents);

    void setEventPriority(String eventClass, int priority);

    int getEventPriority(String eventClass);

    java.util.List<Event> getAllEvents();

    List<BulletMissedEvent> getBulletMissedEvents();

    List<BulletHitBulletEvent> getBulletHitBulletEvents();

    List<BulletHitEvent> getBulletHitEvents();

    List<HitByBulletEvent> getHitByBulletEvents();

    List<HitRobotEvent> getHitRobotEvents();

    List<HitWallEvent> getHitWallEvents();

    List<RobotDeathEvent> getRobotDeathEvents();

    List<ScannedRobotEvent> getScannedRobotEvents();
    
    List<WaypointPassedEvent> getWaypointPasedEvents();

    // team
    List<MessageEvent> getMessageEvents();

	List<LandmineHitEvent> getLandmineHitEvents();

	List<HitByLandmineEvent> getHitByLandmineEvents();
}
