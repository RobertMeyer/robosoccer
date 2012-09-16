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
 * @see HouseRobot
 */
public class HouseRobotBoundary {
	
	private double xHome;
	private double yHome;
	
	private double xLeft;
	private double xRight;
	
	private double yBottom;
	private double yTop;
	
	/**
	 * Creates a Boundary object for the {@link HouseRobot} storing home location and search boundaries.
	 * Should have xLeft <= xHome <= xRight and yBottom <= yHome <= yTop
	 * @param xHome x position for Home
	 * @param yHome y position for Home
	 * @param xLeft x position for Left Boundary
	 * @param xRight x position for Right Boundary
	 * @param yBottom y position for Bottom Boundary
	 * @param yTop y position for Top Boundary
	 */
	public HouseRobotBoundary(double xHome, double yHome, double xLeft, double xRight, double yBottom, double yTop) {
		this.xHome = xHome;
		this.yHome = yHome;
		this.xLeft = xLeft;
		this.xRight = xRight;
		this.yBottom = yBottom;
		this.yTop = yTop;
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
	
	/**
	 * @return Left x boundary in this {@link HouseRobot}
	 */
	public double getXLeft() {
		return xLeft;
	}
	
	/**
	 * @return Right x boundary in this {@link HouseRobot}
	 */
	public double getXRight() {
		return xRight;
	}
	
	/**
	 * @return Bottom y boundary in this {@link HouseRobot}
	 */
	public double getYBottom() {
		return yBottom;
	}
	
	/**
	 * @return Top y boundary in this {@link HouseRobot}
	 */
	public double getYTop() {
		return yTop;
	}
}
