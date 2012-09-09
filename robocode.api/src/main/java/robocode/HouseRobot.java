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
 *     - Updated Javadocs
 *******************************************************************************/
package robocode;

import robocode.robotinterfaces.IAdvancedEvents;
import robocode.robotinterfaces.IHouseRobot;

/**
 * A House Robot is a {@link Robot} which is more powerful, and only shoots when
 * enemy robots enter a specified area.
 *
 * @author Mathew A. Nelson (original)
 * @author Laurence McLean 42373414
 * @author Jack Reichelt
 * @see JuniorRobot
 * @see Robot
 * @see TeamRobot
 * @see Droid
 * @see AdvancedRobot
 */
public class HouseRobot extends AdvancedRobot implements IHouseRobot,
                                                         IAdvancedEvents {
	private HouseRobotBoundary boundaries;
	
	/**
	 * Constructs a new House Robot
	 * @see HouseRobotBoundary
	 * @deprecated Use {@link HouseRobot#HouseRobot(HouseRobotBoundary boundaries)}
	 */
	public HouseRobot() {
		HouseRobotBoundary boundaries = new HouseRobotBoundary(0,0,0,100,0,100);
		this.boundaries = boundaries;
	}
	
	/**
	 * Constructs a new House Robot
	 * @param boundaries Boundaries for this House Robot, defined in {@link HouseRobotBoundary}
	 * @see HouseRobotBoundary
	 */
	public HouseRobot(HouseRobotBoundary boundaries) {
		this.boundaries = boundaries;
	}
	
	/**
	 * Gets the {@link HouseRobotBoundary} object associated with this HouseRobot.
	 * @return Boundaries of this House Robot.
	 */
	public HouseRobotBoundary getBoundaries() {
		return this.boundaries;
	}
	
}
