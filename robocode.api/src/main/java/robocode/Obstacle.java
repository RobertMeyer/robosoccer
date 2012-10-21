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

import net.sf.robocode.security.IHiddenObstacleHelper;
import net.sf.robocode.serialization.ISerializableHelper;
import net.sf.robocode.serialization.RbSerializer;

import java.io.Serializable;
import java.nio.ByteBuffer;


/**
 * Represents an obstacle.
 *
 * @author Joel Addison
 */
public class Obstacle implements Serializable {
	private static final long serialVersionUID = 1L;

	private double x;
	private double y;
	private double width;
	private double height;
	private final int obstacleId;

	/**
	 * Called by the game to create a new {@code Obstacle} object
	 *
	 * @param x		 the X position of the obstacle
	 * @param y		 the Y position of the obstacle
	 * @param width the width of the obstacle
	 * @param height the height of the obstacle
	 * @param obstacleId unique id of obstacle on the battlefield
	 */
	public Obstacle(double x, double y, double width, double height, int obstacleId) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.obstacleId = obstacleId;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		return obstacleId == ((Obstacle) obj).obstacleId;
	}

	@Override
	public int hashCode() {
		return obstacleId;
	}

	/**
	 * Returns the X position of the obstacle.
	 *
	 * @return the X position of the obstacle
	 */
	public double getX() {
		return x;
	}

	/**
	 * Returns the Y position of the obstacle.
	 *
	 * @return the Y position of the obstacle
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Returns the width position of the obstacle.
	 *
	 * @return the width position of the obstacle
	 */
	public double getWidth() {
		return width;
	}
	
	/**
	 * Returns the height position of the obstacle.
	 *
	 * @return the height position of the obstacle
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * Updates this obstacle based on the specified obstacle status.
	 *
	 * @param x the new X position of the obstacle .
	 * @param y the new Y position of the obstacle.
	 * @param width the new width of the obstacle.
	 * @param height the new height of the obstacle.
	 */
	// this method is invisible on RobotAPI
	private void update(double x, double y, double width, double height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	// this method is invisible on RobotAPI
	/**
	 * Returns the unique id of the obstacle
	 * 
	 * @return unique id of obstacle
	 */
	int getObstacleId() {
		return obstacleId;
	}

	/**
	 * Creates a hidden obstacle helper for accessing hidden methods on this object.
	 * 
	 * @return a hidden obstacle helper.
	 */
	// this method is invisible on RobotAPI
	static IHiddenObstacleHelper createHiddenHelper() {
		return new HiddenObstacleHelper();
	}
	
	/**
	 * Creates a hidden obstacle helper for accessing hidden methods on this object.
	 *
	 * @return a hidden obstacle helper.
	 */
	// this class is invisible on RobotAPI
	static ISerializableHelper createHiddenSerializer() {
		return new HiddenObstacleHelper();
	}

	// this class is invisible on RobotAPI
	private static class HiddenObstacleHelper implements IHiddenObstacleHelper, ISerializableHelper {

		public void update(Obstacle bullet, double x, double y, double width, double height) {
			bullet.update(x, y, width, height);
		}

		public int sizeOf(RbSerializer serializer, Object object) {
			return RbSerializer.SIZEOF_TYPEINFO + 4 * RbSerializer.SIZEOF_DOUBLE + RbSerializer.SIZEOF_INT;
		}

		public void serialize(RbSerializer serializer, ByteBuffer buffer, Object object) {
			Obstacle obj = (Obstacle) object;

			serializer.serialize(buffer, obj.x);
			serializer.serialize(buffer, obj.y);
			serializer.serialize(buffer, obj.width);
			serializer.serialize(buffer, obj.height);
			serializer.serialize(buffer, obj.obstacleId);
		}

		public Object deserialize(RbSerializer serializer, ByteBuffer buffer) {
			double x = buffer.getDouble();
			double y = buffer.getDouble();
			double width = buffer.getDouble();
			double height = buffer.getDouble();
			int obstacleId = serializer.deserializeInt(buffer);

			return new Obstacle(x, y, width, height, obstacleId);
		}
	}
}
