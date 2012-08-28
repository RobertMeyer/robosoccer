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
import robocode.robotinterfaces.IHouseRobot;
import robocode.robotinterfaces.peer.IAdvancedRobotPeer;

import java.io.File;
import java.util.Vector;


/**
 * A more advanced type of robot than Robot that allows non-blocking calls,
 * custom events, and writes to the filesystem.
 * <p/>
 * If you have not already, you should create a {@link Robot} first.
 *
 * @author Mathew A. Nelson (original)
 * @author Flemming N. Larsen (contributor)
 * @author Robert D. Maupin (contributor)
 * @author Pavel Savara (contributor)
 * @see <a target="_top" href="http://robocode.sourceforge.net">
 *      robocode.sourceforge.net</a>
 * @see <a href="http://robocode.sourceforge.net/myfirstrobot/MyFirstRobot.html">
 *      Building your first robot<a>
 * @see JuniorRobot
 * @see Robot
 * @see TeamRobot
 * @see Droid
 */
public class HouseRobot extends AdvancedRobot implements IHouseRobot, IAdvancedEvents {

}
