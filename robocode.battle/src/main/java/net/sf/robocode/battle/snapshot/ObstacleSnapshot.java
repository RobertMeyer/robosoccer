/*******************************************************************************
 * Copyright (c) 2001-2012 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 *
 * Contributors:
 *     Flemming N. Larsen
 *     - Initial implementation
 *     Pavel Savara
 *     - Xml Serialization, refactoring
 *******************************************************************************/
package net.sf.robocode.battle.snapshot;


import net.sf.robocode.battle.peer.ObstaclePeer;
import net.sf.robocode.peer.ExecCommands;
import net.sf.robocode.serialization.IXmlSerializable;
import net.sf.robocode.serialization.XmlReader;
import net.sf.robocode.serialization.SerializableOptions;
import net.sf.robocode.serialization.XmlWriter;
import robocode.control.snapshot.IObstacleSnapshot;

import java.io.IOException;


/**
 * A snapshot of an obstacle at a specific time instant in a battle.
 * The snapshot contains a snapshot of the obstacle data at that specific time.
 *
 * @author Michael Tsai
 * @author Joel Addison
 * 
 */
public final class ObstacleSnapshot implements java.io.Serializable, IObstacleSnapshot {

	private static final long serialVersionUID = 2L;

	/** The x position */
	private double x;

	/** The y position */
	private double y;
	
	/** Obstacle Width */
	private int width;
	
	/** Obstacle Height */
	private int height;

	/** The ARGB color of the bullet */
	private int color = ExecCommands.defaultBulletColor;

	private int obstacleId;

	/**
	 * Creates a snapshot of an obstacle.
	 *
	 * @param obstacle the obstacle to make a snapshot of.
	 */
	public ObstacleSnapshot(ObstaclePeer obstacle) {
		x = obstacle.getX();
		y = obstacle.getY();
		height = obstacle.getHeight();
		width = obstacle.getWidth();
	}

	@Override
	public String toString() {
		return "";
	}

	/**
	 * {@inheritDoc}
	 */
	public double getX() {
		return x;
	}

	/**
	 * {@inheritDoc}
	 */
	public double getY() {
		return y;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getColor() {
		return color;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getObstacleId() {
		return obstacleId;
	}
}
