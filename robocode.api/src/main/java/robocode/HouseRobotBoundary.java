/*******************************************************************************
 * Copyright (c) 2001-2012 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 *
 * Contributors:
 *     Mathew Nelson
 *     - Initial API and implementation
 *     Flemming N. Larsen
 *     - Updated Javadocs
 *******************************************************************************/
package robocode;

/**
 * Object to store Home location and search boundaries for a {@link HouseRobot}.
 * 
 * @author Laurence McLean 42373414
 * @author Jack Reichelt 42338271
 * @see HouseRobot
 */
public class HouseRobotBoundary {
	
	private double xHome;
	private double yHome;
	
	private double arcRange;
	
	private double initialFacing;
	
	/**
	 * Creates a Boundary object for the {@link HouseRobot} storing home location and search boundaries.
	 * Should have xLeft <= xHome <= xRight and yBottom <= yHome <= yTop
	 * @param xHome x position for Home
	 * @param yHome y position for Home
	 */
	public HouseRobotBoundary(double xHome, double yHome, double arcRange, double initialFacing) {
		this.xHome = xHome;
		this.yHome = yHome;
		this.arcRange = arcRange;
		this.initialFacing = initialFacing;
	}
	
	public HouseRobotBoundary() {
	}
	
	/**
	 * @return x position for Home in this {@link HouseRobot}
	 */
	public double getXHome() {
		return xHome;
	}
	
	/**
	 * @return y position for Home in this {@link HouseRobot}
	 */
	public double getYHome() {
		return yHome;
	}
	
	public double getArcRange() {
		return arcRange;
	}
	
	public double getInitialFacing() {
		return initialFacing;
	}
	
	public void setxHome(double xHome) {
		this.xHome = xHome;
	}

	public void setyHome(double yHome) {
		this.yHome = yHome;
	}
	
	public void setArcRange(double arcRange) {
		this.arcRange = arcRange;
	}

	public void setInitialFacing(double initialFacing) {
		this.initialFacing = initialFacing;
	}
	
	
	
}
