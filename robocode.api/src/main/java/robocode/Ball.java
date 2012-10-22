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
 *     - Ported to Java 5
 *     - Updated Javadocs
 *     - The uninitializedException() method does not need a method name as input
 *       parameter anymore
 *     - Changed the priority of the DeathEvent from 100 to -1 in order to let
 *       robots process events before they die
 *     - Added getStatusEvents()
 *     Robert D. Maupin
 *     - Replaced old collection types like Vector and Hashtable with
 *       synchronized List and HashMap
 *     Pavel Savara
 *     - Re-work of robot interfaces
 *******************************************************************************/
package robocode;


import robocode.robotinterfaces.IAdvancedEvents;
import robocode.robotinterfaces.IBall;
import robocode.robotinterfaces.ISoccerRobot;


/**
 * A ball type Robot. Does not add any extra functionality beyond SoccerRobot, 
 * but is useful to distinguish a ball from other Soccer playing robots.
 *
 * @author Carl Hattenfels - team-G1
 * @see JuniorRobot
 * @see Robot
 * @see TeamRobot
 * @see Droid
 */
public class Ball extends SoccerRobot implements IBall, ISoccerRobot, 
		IAdvancedEvents {
}
