/*******************************************************************************
 * Copyright (c) 2001, 2008 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/cpl-v10.html
 *
 * Contributors:
 *     Flemming N. Larsen
 *     - Initial implementation
 *******************************************************************************/
package robocode;


import robocode.peer.RobotPeer;
import robocode.peer.RobotCommands;
import robocode.battle.Battle;


/**
 * Contains the status of a robot for a specific time/turn returned by
 * {@link StatusEvent#getStatus()}.
 *
 * @author Flemming N. Larsen (original)
 * @since 1.5
 */
public final class RobotStatus {

	private final double energy;
	private final double x;
	private final double y;
	private final double bodyHeading;
	private final double gunHeading;
	private final double radarHeading;
	private final double velocity;
	private final double bodyTurnRemaining;
	private final double radarTurnRemaining;
	private final double gunTurnRemaining;
	private final double distanceRemaining;
	private final double gunHeat;
	private final long dataQuotaAvailable;
	private final int others;
	private final int roundNum;
	private final int numRounds;
	private final long time;

	/**
	 * Creates a new RobotStatus based a a RobotPeer.
	 * This constructor is called internally from the game.
	 *
	 * @param robotPeer the RobotPeer containing the states we must make a snapshot of
	 */
	public RobotStatus(RobotPeer robotPeer, RobotCommands commands, Battle battle) {
		synchronized (robotPeer) {
			energy = robotPeer.getEnergy();
			x = robotPeer.getX();
			y = robotPeer.getY();
			bodyHeading = robotPeer.getBodyHeading();
			gunHeading = robotPeer.getGunHeading();
			radarHeading = robotPeer.getRadarHeading();
			velocity = robotPeer.getVelocity();
			bodyTurnRemaining = commands.getBodyTurnRemaining();
			radarTurnRemaining = commands.getRadarTurnRemaining();
			gunTurnRemaining = commands.getGunTurnRemaining();
			distanceRemaining = commands.getDistanceRemaining();
			gunHeat = robotPeer.getGunHeat();
			dataQuotaAvailable = robotPeer.getDataQuotaAvailable();
			others = battle.getActiveRobots() - (robotPeer.isAlive() ? 1 : 0);
			roundNum = battle.getRoundNum();
			numRounds = battle.getNumRounds();
			time = battle.getTime();
		}
	}

	/**
	 * Returns the robot's current energy.
	 *
	 * @return the robot's current energy
	 */
	public double getEnergy() {
		return energy;
	}

	/**
	 * Returns the X position of the robot. (0,0) is at the bottom left of the
	 * battlefield.
	 *
	 * @return the X position of the robot
	 * @see #getY()
	 */
	public double getX() {
		return x;
	}

	/**
	 * Returns the Y position of the robot. (0,0) is at the bottom left of the
	 * battlefield.
	 *
	 * @return the Y position of the robot
	 * @see #getX()
	 */
	public double getY() {
		return y;
	}

	/**
	 * Returns the direction that the robot's body is facing, in radians.
	 * The value returned will be between 0 and 2 * PI (is excluded).
	 * <p/>
	 * Note that the heading in Robocode is like a compass, where 0 means North,
	 * PI / 2 means East, PI means South, and 3 * PI / 4 means West.
	 *
	 * @return the direction that the robot's body is facing, in radians.
	 */
	public double getHeadingRadians() {
		return bodyHeading;
	}

	/**
	 * Returns the direction that the robot's body is facing, in degrees.
	 * The value returned will be between 0 and 360 (is excluded).
	 * <p/>
	 * Note that the heading in Robocode is like a compass, where 0 means North,
	 * 90 means East, 180 means South, and 270 means West.
	 *
	 * @return the direction that the robot's body is facing, in degrees.
	 */
	public double getHeading() {
		return Math.toDegrees(bodyHeading);
	}

	/**
	 * Returns the direction that the robot's gun is facing, in radians.
	 * The value returned will be between 0 and 2 * PI (is excluded).
	 * <p/>
	 * Note that the heading in Robocode is like a compass, where 0 means North,
	 * PI / 2 means East, PI means South, and 3 * PI / 4 means West.
	 *
	 * @return the direction that the robot's gun is facing, in radians.
	 */
	public double getGunHeadingRadians() {
		return gunHeading;
	}

	/**
	 * Returns the direction that the robot's gun is facing, in degrees.
	 * The value returned will be between 0 and 360 (is excluded).
	 * <p/>
	 * Note that the heading in Robocode is like a compass, where 0 means North,
	 * 90 means East, 180 means South, and 270 means West.
	 *
	 * @return the direction that the robot's gun is facing, in degrees.
	 */
	public double getGunHeading() {
		return Math.toDegrees(gunHeading);
	}

	/**
	 * Returns the direction that the robot's radar is facing, in radians.
	 * The value returned will be between 0 and 2 * PI (is excluded).
	 * <p/>
	 * Note that the heading in Robocode is like a compass, where 0 means North,
	 * PI / 2 means East, PI means South, and 3 * PI / 4 means West.
	 *
	 * @return the direction that the robot's radar is facing, in radians.
	 */
	public double getRadarHeadingRadians() {
		return radarHeading;
	}

	/**
	 * Returns the direction that the robot's radar is facing, in degrees.
	 * The value returned will be between 0 and 360 (is excluded).
	 * <p/>
	 * Note that the heading in Robocode is like a compass, where 0 means North,
	 * 90 means East, 180 means South, and 270 means West.
	 *
	 * @return the direction that the robot's radar is facing, in degrees.
	 */
	public double getRadarHeading() {
		return Math.toDegrees(radarHeading);
	}

	/**
	 * Returns the velocity of the robot measured in pixels/turn.
	 * <p/>
	 * The maximum velocity of a robot is defined by {@link Rules#MAX_VELOCITY}
	 * (8 pixels / turn).
	 *
	 * @return the velocity of the robot measured in pixels/turn
	 * @see Rules#MAX_VELOCITY
	 */
	public double getVelocity() {
		return velocity;
	}

	/**
	 * Returns the angle remaining in the robots's turn, in radians.
	 * <p/>
	 * This call returns both positive and negative values. Positive values
	 * means that the robot is currently turning to the right. Negative values
	 * means that the robot is currently turning to the left.
	 *
	 * @return the angle remaining in the robots's turn, in radians
	 */
	public double getTurnRemainingRadians() {
		return bodyTurnRemaining;
	}

	/**
	 * Returns the angle remaining in the robots's turn, in degrees.
	 * <p/>
	 * This call returns both positive and negative values. Positive values
	 * means that the robot is currently turning to the right. Negative values
	 * means that the robot is currently turning to the left.
	 *
	 * @return the angle remaining in the robots's turn, in degrees
	 */
	public double getTurnRemaining() {
		return Math.toDegrees(bodyTurnRemaining);
	}

	/**
	 * Returns the angle remaining in the radar's turn, in radians.
	 * <p/>
	 * This call returns both positive and negative values. Positive values
	 * means that the radar is currently turning to the right. Negative values
	 * means that the radar is currently turning to the left.
	 *
	 * @return the angle remaining in the radar's turn, in radians
	 */
	public double getRadarTurnRemainingRadians() {
		return radarTurnRemaining;
	}

	/**
	 * Returns the angle remaining in the radar's turn, in degrees.
	 * <p/>
	 * This call returns both positive and negative values. Positive values
	 * means that the radar is currently turning to the right. Negative values
	 * means that the radar is currently turning to the left.
	 *
	 * @return the angle remaining in the radar's turn, in degrees
	 */
	public double getRadarTurnRemaining() {
		return Math.toDegrees(radarTurnRemaining);
	}

	/**
	 * Returns the angle remaining in the gun's turn, in radians.
	 * <p/>
	 * This call returns both positive and negative values. Positive values
	 * means that the gun is currently turning to the right. Negative values
	 * means that the gun is currently turning to the left.
	 *
	 * @return the angle remaining in the gun's turn, in radians
	 */
	public double getGunTurnRemainingRadians() {
		return gunTurnRemaining;
	}

	/**
	 * Returns the angle remaining in the gun's turn, in degrees.
	 * <p/>
	 * This call returns both positive and negative values. Positive values
	 * means that the gun is currently turning to the right. Negative values
	 * means that the gun is currently turning to the left.
	 *
	 * @return the angle remaining in the gun's turn, in degrees
	 */
	public double getGunTurnRemaining() {
		return Math.toDegrees(gunTurnRemaining);
	}

	/**
	 * Returns the distance remaining in the robot's current move measured in
	 * pixels.
	 * <p/>
	 * This call returns both positive and negative values. Positive values
	 * means that the robot is currently moving forwards. Negative values means
	 * that the robot is currently moving backwards.
	 *
	 * @return the distance remaining in the robot's current move measured in
	 *         pixels.
	 */
	public double getDistanceRemaining() {
		return distanceRemaining;
	}

	/**
	 * Returns the current heat of the gun. The gun cannot fire unless this is
	 * 0. (Calls to fire will succeed, but will not actually fire unless
	 * getGunHeat() == 0).
	 * <p/>
	 * The amount of gun heat generated when the gun is fired is
	 * 1 + (firePower / 5). Each turn the gun heat drops by the amount returned
	 * by {@link Robot#getGunCoolingRate()}, which is a battle setup.
	 * <p/>
	 * Note that all guns are "hot" at the start of each round, where the gun
	 * heat is 3.
	 *
	 * @return the current gun heat
	 * @see Robot#getGunCoolingRate()
	 * @see Robot#fire(double)
	 * @see Robot#fireBullet(double)
	 */
	public double getGunHeat() {
		return gunHeat;
	}

	/**
	 * Returns the data quota available in your data directory, i.e. the amount
	 * of bytes left in the data directory for the robot.
	 *
	 * @return the amount of bytes left in the robot's data directory
	 * @see AdvancedRobot#getDataDirectory()
	 * @see AdvancedRobot#getDataFile(String)
	 */
	public long getDataQuotaAvailable() {
		return dataQuotaAvailable;
	}

	/**
	 * Returns how many opponents that are left in the current round.
	 *
	 * @return how many opponents that are left in the current round.
	 */
	public int getOthers() {
		return others;
	}

	/**
	 * Returns the number of rounds in the current battle.
	 *
	 * @return the number of rounds in the current battle
	 * @see #getRoundNum()
	 */
	public int getNumRounds() {
		return numRounds;
	}

	/**
	 * Returns the current round number (0 to {@link #getNumRounds()} - 1) of
	 * the battle.
	 *
	 * @return the current round number of the battle
	 * @see #getNumRounds()
	 */
	public int getRoundNum() {
		return roundNum;
	}

	/**
	 * Returns the game time of the round, where the time is equal to the current turn in the round.
	 *
	 * @return the game time/turn of the current round.
	 *
	 * @since 1.6.2
	 */
	public long getTime() {
		return time;
	}
}
