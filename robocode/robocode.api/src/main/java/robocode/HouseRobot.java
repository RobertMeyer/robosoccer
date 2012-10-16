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

import robocode.exception.BoundaryException;
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
	private boolean boundariesSet;
	
	/**
	 * Constructs a new House Robot
	 * @see HouseRobotBoundary
	 */
	public HouseRobot() {
		HouseRobotBoundary boundaries = new HouseRobotBoundary(0,0,0,100,0,100);
		this.boundaries = boundaries;
		boundariesSet = false;
	}
	
	/**
	 * Constructs a new House Robot
	 * @param boundaries Boundaries for this House Robot, defined in {@link HouseRobotBoundary}
	 * @see HouseRobotBoundary
	 * @deprecated Use {@link HouseRobot#HouseRobot()} and {@link HouseRobot#setBoundaries(HouseRobotBoundary boundaries}
	 */
	public HouseRobot(HouseRobotBoundary boundaries) {
		this.boundaries = boundaries;
		boundariesSet = true;
	}
	
	/**
	 * Gets the {@link HouseRobotBoundary} object associated with this HouseRobot.
	 * @return Boundaries of this House Robot.
	 */
	public HouseRobotBoundary getBoundaries() {
		return this.boundaries;
	}
	
	/**
	 * Sets the {@link HouseRobotBoundary} object associated with this HouseRobot.
	 * Can <b>only</b> be called once. If called a second time, will throw a BoundaryException.
	 * @param boundaries
	 * @throws BoundaryException
	 */
	protected void setBoundaries(HouseRobotBoundary boundaries) {
		if(boundariesSet) {
			throw new BoundaryException("Cannot set boundaries a second time.");
		} else {
			boundariesSet = true;
			this.boundaries = boundaries;
		}
	}
	
}
