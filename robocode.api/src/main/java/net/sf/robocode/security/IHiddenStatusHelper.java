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
package net.sf.robocode.security;

import robocode.RobotStatus;

/**
 * @author Pavel Savara (original)
 * @author CSSE2003 Team forkbomb (contributor)
 */
public interface IHiddenStatusHelper {

    RobotStatus createStatus(double energy, double x, double y, double bodyHeading, double gunHeading, double radarHeading,
                             double velocity, double bodyTurnRemaining, double radarTurnRemaining, double gunTurnRemaining,
                             double distanceRemaining, double gunHeat, int others, int roundNum, int numRounds, long time,
                             double maxVelocity, double maxBulletPower, double minBulletPower, double acceleration,
                             double deceleration, double radarScanRadius, double maxTurnRate, double gunTurnRate,
                             double radarTurnRate, double robotHitDamage, double robotHitAttack);
}
