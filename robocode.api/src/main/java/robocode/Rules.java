/*******************************************************************************
 * Copyright (c) 2001-2012 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 *
 * Contributors:
 *     Flemming N. Larsen & Luis Crespo
 *     - Initial API and implementation
 *******************************************************************************/
package robocode;


import static java.lang.Math.abs;
import static java.lang.Math.max;


/**
 * Constants and methods that defines the rules of Robocode.
 * Constants are used for rules that will not change.
 * Methods are provided for rules that can be changed between battles or which depends
 * on some other factor.
 *
 * @author Luis Crespo (original)
 * @author Flemming N. Larsen (original)
 * @since 1.1.4
 */
public class Rules {
	
	private Robot robot; // The robot the rules are being defined for.
	
	/*** Rules for Robot ***/
	// The maximum acceleration of the robot
	public double ACCELERATION = getAcceleration();
	// The maximum deceleration of the robot
	public double DECELERATION = getDeceleration();
	// The maximum velocity of the robot
	public double MAX_VELOCITY = getMaxVelocity();
	// The radar scan radius of the robot
	public double RADAR_SCAN_RADIUS = getRadarScanRadius();
	// The minimum bullet power of the robot
	public double MIN_BULLET_POWER = getMinBulletPower();
	// The maximum bullet power of the robot
	public double MAX_BULLET_POWER = getMaxBulletPower();
	/*
	 * The maximum turning rate of the robot, in degrees.
	 * Note, that the turn rate of the robot depends on it's velocity.
	 * 
	 * @see #MAX_TURN_RATE_RADIANS
	 * @see #getTurnRate(double)
	 * @see #getTurnRateRadians(double)
	 */
	public double MAX_TURN_RATE = getMaxTurnRate();
	/*
	 * The maximum turning rate of the robot measured in radians instead of
	 * degrees.
	 * 
	 * @see #MAX_TURN_RATE
	 * @see #getTurnRate(double)
	 * @see #getTurnRateRadians(double)
	 */
	public double MAX_TURN_RATE_RADIANS = Math.toRadians(MAX_TURN_RATE);
	
	/*
	 * The turning rate of the gun measured in degrees.
	 * Note, that if setAdjustGunForRobotTurn(true) has been called, the gun
	 * turn is independent of the robot turn.
	 * In this case the gun moves relatively to the screen. If
	 * setAdjustGunForRobotTurn(false) has been called or
	 * setAdjustGunForRobotTurn() has not been called at all (this is the
	 * default), then the gun turn is dependent on the robot turn, and in this
	 * case the gun moves relatively to the robot body.
	 *
	 * @see #GUN_TURN_RATE_RADIANS
	 * @see Robot#setAdjustGunForRobotTurn(boolean)
	 */
	public double GUN_TURN_RATE = getGunTurnRate();

	/*
	 * The turning rate of the gun measured in radians instead of degrees.
	 *
	 * @see #GUN_TURN_RATE
	 */
	public double GUN_TURN_RATE_RADIANS = Math.toRadians(GUN_TURN_RATE);
	
	/*
	 * The turning rate of the radar measured in degrees.
	 * Note, that if setAdjustRadarForRobotTurn(true) and/or
	 * setAdjustRadarForGunTurn(true) has been called, the radar turn is
	 * independent of the robot and/or gun turn. If both methods has been set to
	 * true, the radar moves relatively to the screen.
	 * If setAdjustRadarForRobotTurn(false) and/or setAdjustRadarForGunTurn(false)
	 * has been called or not called at all (this is the default), then the
	 * radar turn is dependent on the robot and/or gun turn, and in this case
	 * the radar moves relatively to the gun and/or robot body.
	 *
	 * @see #RADAR_TURN_RATE_RADIANS
	 * @see Robot#setAdjustGunForRobotTurn(boolean)
	 * @see Robot#setAdjustRadarForGunTurn(boolean)
	 */
	public double RADAR_TURN_RATE = getRadarTurnRate();

	/*
	 * The turning rate of the radar measured in radians instead of degrees.
	 *
	 * @see #RADAR_TURN_RATE
	 */
	public double RADAR_TURN_RATE_RADIANS = Math.toRadians(RADAR_TURN_RATE);

	// The amount of damage taken when a robot hits or is hit by another robot.
	public double ROBOT_HIT_DAMAGE = getRobotHitDamage();
	 // The amount of bonus given when a robot moving forward hits an opponent robot (ramming).
	public double ROBOT_HIT_BONUS = getRobotHitBonus();
	/*** End of Rules for Robot ***/
	
	/**
	 * Intialises a new set of rules for a specific robot.
	 * 
	 * @param robot
	 * 			The robot for the rules to be generated for.
	 */
	public Rules(Robot robot) {
		this.robot = robot;
	}
	

	/**
	 * Returns the turn rate of a robot given a specific velocity measured in
	 * degrees/turn.
	 *
	 * @param velocity the velocity of the robot.
	 * @return turn rate in degrees/turn.
	 * @see #getTurnRateRadians(double)
	 */
	public double getTurnRate(double velocity) {
		return MAX_TURN_RATE - 0.75 * abs(velocity);
	}

	/**
	 * Returns the turn rate of a robot given a specific velocity measured in
	 * radians/turn.
	 *
	 * @param velocity the velocity of the robot.
	 * @return turn rate in radians/turn.
	 * @see #getTurnRate(double)
	 */
	public double getTurnRateRadians(double velocity) {
		return Math.toRadians(getTurnRate(velocity));
	}

	/**
	 * Returns the amount of damage taken when robot hits a wall with a
	 * specific velocity.
	 *
	 * @param velocity the velocity of the robot.
	 * @return wall hit damage in energy points.
	 */
	public double getWallHitDamage(double velocity) {
		return max(abs(velocity) / 2 - 1, 0);
	}

	/**
	 * Returns the amount of damage of a bullet given a specific bullet power.
	 *
	 * @param bulletPower the energy power of the bullet.
	 * @return bullet damage in energy points.
	 */
	public double getBulletDamage(double bulletPower) {
		double damage = 4 * bulletPower;

		if (bulletPower > 1) {
			damage += 2 * (bulletPower - 1);
		}
		return damage;
	}

	/**
	 * Returns the amount of bonus given when a robot's bullet hits an opponent
	 * robot given a specific bullet power.
	 *
	 * @param bulletPower the energy power of the bullet.
	 * @return bullet hit bonus in energy points.
	 */
	public double getBulletHitBonus(double bulletPower) {
		return 3 * bulletPower;
	}

	/**
	 * Returns the speed of a bullet given a specific bullet power measured in pixels/turn.
	 *
	 * @param bulletPower the energy power of the bullet.
	 * @return bullet speed in pixels/turn
	 */
	public double getBulletSpeed(double bulletPower) {
		bulletPower = Math.min(Math.max(bulletPower, MIN_BULLET_POWER), MAX_BULLET_POWER);
		return 20 - 3 * bulletPower;
	}

	/**
	 * Returns the heat produced by firing the gun given a specific bullet
	 * power.
	 *
	 * @param bulletPower the energy power of the bullet.
	 * @return gun heat
	 */
	public  double getGunHeat(double bulletPower) {
		return 1 + (bulletPower / 5);
	}
	
	
	/*** DETERMINING RULE METHODS ***/
	
	/**
	 * Method to check for the maximum acceleration of a certain robot.
	 * 
	 * @return
	 * 			the maximum acceleration of the robot in question.
	 */
	private double getAcceleration(){
		double acceleration = 0;
		// Check for Equipment
		// Check for Power-Ups
		 // Make sure a standard robot gets standard acceleration.
		if(acceleration == 0){
			return 1;
		}
		else{
			return acceleration;
		}
	}
	
	/**
	 * Method to check for the maximum deceleration of a certain robot.
	 * 
	 * @return
	 * 			the maximum deceleration of the robot in question.
	 */
	private double getDeceleration(){
		double deceleration = 0;
		// Check for Equipment
		// Check for Power-Ups
		 // Make sure a standard robot gets standard deceleration.
		if(deceleration == 0){
			return 2;
		}
		else{
			return deceleration;
		}
	}
	
	/**
	 * Method to check for the maximum velocity of a certain robot.
	 * 
	 * @return
	 * 			the maximum velocity of the robot in question.
	 */
	private double getMaxVelocity(){
		double maxVelocity = 0;
		// Check for Equipment
		// Check for Power-Ups
		 // Make sure a standard robot gets standard velocity.
		if(maxVelocity == 0){
			return 8;
		}
		else{
			return maxVelocity;
		}
	}
	
	/**
	 * Method to check for the radar scan radius of a certain robot.
	 * 
	 * @return
	 * 			the radar scan radius of the robot in question.
	 */
	private double getRadarScanRadius(){
		double radarScanRadius = 0;
		// Check for Equipment
		// Check for Power-Ups
		 // Make sure a standard robot gets standard radarScanRadius.
		if(radarScanRadius == 0){
			return 1200;
		}
		else{
			return radarScanRadius;
		}
	}
	
	/**
	 * Method to check for the minimum bullet power of a certain robot.
	 * 
	 * @return
	 * 			the minimum of the robot in question.
	 */
	private double getMinBulletPower(){
		double bulletPower = 0;
		// Check for Equipment
		// Check for Power-Ups
		 // Make sure a standard robot gets standard bullet power.
		if(bulletPower == 0){
			return 0.1;
		}
		else{
			return bulletPower;
		}
	}
	
	/**
	 * Method to check for the maximum bullet power of a certain robot.
	 * 
	 * @return
	 * 			the maximum bullet power of the robot in question.
	 */
	private double getMaxBulletPower(){
		double bulletPower = 0;
		// Check for Equipment
		// Check for Power-Ups
		 // Make sure a standard robot gets standard bulletPower.
		if(bulletPower == 0){
			return 3;
		}
		else{
			return bulletPower;
		}
	}
	
	/**
	 * Method to check for the maximum turn rate of a certain robot.
	 * 
	 * @return
	 * 			the maximum turn rate of the robot in question.
	 */
	private double getMaxTurnRate(){
		double turnRate = 0;
		// Check for Equipment
		// Check for Power-Ups
		 // Make sure a standard robot gets standard turn rate.
		if(turnRate == 0){
			return 10;
		}
		else{
			return turnRate;
		}
	}
	
	/**
	 * Method to check for the gun turn rate of a certain robot.
	 * 
	 * @return
	 * 			the gun turn rate of the robot in question.
	 */
	private double getGunTurnRate(){
		double turnRate = 0;
		// Check for Equipment
		// Check for Power-Ups
		 // Make sure a standard robot gets standard gun turn rate.
		if(turnRate == 0){
			return 20;
		}
		else{
			return turnRate;
		}
	}
	
	/**
	 * Method to check for the radar turn rate of a certain robot.
	 * 
	 * @return
	 * 			the radar turn rate of the robot in question.
	 */
	private double getRadarTurnRate(){
		double turnRate = 0;
		// Check for Equipment
		// Check for Power-Ups
		 // Make sure a standard robot gets standard radar turn rate.
		if(turnRate == 0){
			return 45;
		}
		else{
			return turnRate;
		}
	}
	
	/**
	 * Method to check for the robot hit damage of a certain robot.
	 * 
	 * @return
	 * 			the robot hit damage of the robot in question.
	 */
	private double getRobotHitDamage(){
		double hitDamage = 0;
		// Check for Equipment
		// Check for Power-Ups
		 // Make sure a standard robot gets standard hit damage.
		if(hitDamage == 0){
			return 0.6;
		}
		else{
			return hitDamage;
		}
	}
	
	/**
	 * Method to check for the robot hit bonus of a certain robot.
	 * 
	 * @return
	 * 			the robot hit bonus of the robot in question.
	 */
	private double getRobotHitBonus(){
		double hitBonus = 0;
		// Check for Equipment
		// Check for Power-Ups
		 // Make sure a standard robot gets standard hit bonus.
		if(hitBonus == 0){
			return 1.2;
		}
		else{
			return hitBonus;
		}
	}
	
	/*** END OF DETERMINING RULE METHODS ***/
}
