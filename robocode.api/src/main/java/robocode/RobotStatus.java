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
 *******************************************************************************/
package robocode;

import java.io.Serializable;
import java.nio.ByteBuffer;
import net.sf.robocode.security.IHiddenStatusHelper;
import net.sf.robocode.serialization.ISerializableHelper;
import net.sf.robocode.serialization.RbSerializer;

/**
 * Contains the status of a robot for a specific time/turn returned by
 * {@link StatusEvent#getStatus()}.
 *
 * @author Flemming N. Larsen (original)
 * @author CSSE2003 Team forkbomb (contributor)
 * @since 1.5
 */
public final class RobotStatus implements Serializable {

    private static final long serialVersionUID = 1L;
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
    private final int others;
    private final int roundNum;
    private final int numRounds;
    private final long time;
    private final double maxVelocity;
    private final double maxBulletPower;
    private final double minBulletPower;
    private final double acceleration;
    private final double deceleration;
    private final double radarScanRadius;
    private final double maxTurnRate;
    private final double gunTurnRate;
    private final double radarTurnRate;
    private final double robotHitDamage;
    private final double robotHitAttack;

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
     * PI / 2 means East, PI means South, and 3 * PI / 2 means West.
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
     * PI / 2 means East, PI means South, and 3 * PI / 2 means West.
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
     * PI / 2 means East, PI means South, and 3 * PI / 2 means West.
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
     * Returns how many opponents that are left in the current round.
     *
     * @return how many opponents that are left in the current round.
     * @since 1.6.2
     */
    public int getOthers() {
        return others;
    }

    /**
     * Returns the number of rounds in the current battle.
     *
     * @return the number of rounds in the current battle
     * @see #getRoundNum()
     * @since 1.6.2
     */
    public int getNumRounds() {
        return numRounds;
    }

    /**
     * Returns the current round number (0 to {@link #getNumRounds()} - 1) of
     * the battle.
     *
     * @return the current round number of the battle (zero indexed).
     * @see #getNumRounds()
     * @since 1.6.2
     */
    public int getRoundNum() {
        return roundNum;
    }

    /**
     * Returns the game time of the round, where the time is equal to the current turn in the round.
     *
     * @return the game time/turn of the current round.
     * @since 1.6.2
     */
    public long getTime() {
        return time;
    }
    
    /**
     * Returns the maximum velocity of the robot measured in pixels/turn.
     * <p/>
     * The maximum velocity of a robot is defined as {@link Rules#MAX_VELOCITY}
     * * {@link RobotAttribute#VELOCITY}
     * 
     * @return the maximum velocity of the robot in pixels/turn
     * @see Rules#MAX_VELOCITY
     * @see RobotAttribute#VELOCITY
     */
    public double getMaxVelocity(){
    	return maxVelocity;
    }
    
    /**
     * Returns the maximum bullet power of the robot.
     * <p/>
     * The maximum bullet power is defined as {@link Rules#MAX_BULLET_POWER} *
     * {@link RobotAttribute#BULLET_DAMAGE}
     * 
     * @return the maximum bullet power of the robot
     * @see Rules#MAX_BULLET_POWER
     * @see RobotAttribute#BULLET_DAMAGE
     */
    public double getMaxBulletPower(){
    	return maxBulletPower;
    }
    
    /**
     * Returns the minimum bullet power of the robot.
     * <p/>
     * The minimum bullet power is defined as {@link Rules#MIN_BULLET_POWER} *
     * {@link RobotAttribute#BULLET_DAMAGE}
     * 
     * @return the minimum bullet power of the robot
     * @see Rules#MIN_BULLET_POWER
     * @see RobotAttribute#BULLET_DAMAGE
     */
    public double getMinBulletPower(){
    	return minBulletPower;
    }
    
    /**
     * Returns the acceleration of the robot.
     * <p/>
     * The acceleration of the robot defined as 
     * {@link Rules#ACCELERATION} * {@link RobotAttribute#ACCELERATION}
     * 
     * @return the acceleration of the robot
     * @see Rules#ACCELERATION
     * @see RobotAttribute#ACCELERATION
     */
    public double getAcceleration(){
    	return acceleration;
    }
    
    /**
     * Returns the deceleration of the robot.
     * <p/>
     * The deceleration of the robot defined as 
     * {@link Rules#DECELERATION} * {@link RobotAttribute#DECELERATION}
     * 
     * @return the deceleration of the robot
     * @see Rules#DECELERATION
     * @see RobotAttribute#DECELERATION
     */
    public double getDeceleration(){
    	return deceleration;
    }
    
    /**
     * Returns the radar scan radius of the robot
     * <p/>
     * The radar scan radius is defined as {@link Rules#RADAR_SCAN_RADIUS}
     *  * {@link RobotAttribute#SCAN_RADIUS}
     *  
     * @return the radar scan radius of the robot
     * @see Rules#RADAR_SCAN_RADIUS
     * @see RobotAttribute#SCAN_RADIUS
     */
    public double getRadarScanRadius(){
    	return radarScanRadius;
    }
    
    /**
     * Returns the maximum turn rate of the robot in degrees.  The rate at 
     * which the robot turns also depends on velocity.
     * <p/>
     * The robot maximum turn rate is defined as {@link Rules#MAX_TURN_RATE}
     *  * {@link RobotAttribute#ROBOT_TURN_ANGLE}
     * 
     * @return the maximum turn rate of the robot in degrees
     * @see Rules#MAX_TURN_RATE
     * @see RobotAttribute#ROBOT_TURN_ANGLE
     * @see Rules#getTurnRate(double)
     * @see Rules#getTurnRateRadians(double)
     */
    public double getMaxTurnRate(){
    	return maxTurnRate;
    }
    
    /**
     * Returns the maximum gun turn rate of the robot in degrees.
     * 
     * Note, that if setAdjustGunForRobotTurn(true) has been called, the gun
     * turn is independent of the robot turn.
     * In this case the gun moves relatively to the screen. If
     * setAdjustGunForRobotTurn(false) has been called or
     * setAdjustGunForRobotTurn() has not been called at all (this is the
     * default), then the gun turn is dependent on the robot turn, and in this
     * case the gun moves relatively to the robot body.
     * 
     * <p/>
     * The robot maximum turn rate is defined as {@link Rules#GUN_TURN_RATE}
     *  * {@link RobotAttribute#GUN_TURN_ANGLE}
     * 
     * @return the maximum gun turn rate of the robot in degrees
     * @see Rules#GUN_TURN_RATE
     * @see RobotAttribute#ROBOT_TURN_ANGLE
     * @see Robot#setAdjustGunForRobotTurn(boolean)
     */
    public double getGunTurnRate(){
    	return gunTurnRate;
    }
    
    /**
     * Returns the radar turn rate of the robot in degrees.
     * 
     * Note, that if setAdjustRadarForRobotTurn(true) and/or
     * setAdjustRadarForGunTurn(true) has been called, the radar turn is
     * independent of the robot and/or gun turn. If both methods has been set to
     * true, the radar moves relatively to the screen.
     * If setAdjustRadarForRobotTurn(false) and/or setAdjustRadarForGunTurn(false)
     * has been called or not called at all (this is the default), then the
     * radar turn is dependent on the robot and/or gun turn, and in this case
     * the radar moves relatively to the gun and/or robot body.
     * 
     * <p/>
     * The robot maximum turn rate is defined as {@link Rules#RADAR_TURN_RATE}
     *  * {@link RobotAttribute#RADAR_ANGLE}
     * 
     * @return the radar turn rate of the robot in degrees
     * @see Rules#RADAR_TURN_RATE_RADIANS
     * @see RobotAttribute#RADAR_ANGLE
     * @see Robot#setAdjustGunForRobotTurn(boolean)
     * @see Robot#setAdjustRadarForGunTurn(boolean)
     */
    public double getRadarTurnRate(){
    	return radarTurnRate;
    }
    
    /**
     * The amount of damage taken when a robot hits or is hit by another robot
     * <p/>
     * This is defined as {@link Rules#ROBOT_HIT_DAMAGE} * 
     * {@link RobotAttribute#RAM_DEFENSE}
     * 
     * @return the amount of damage taken when a robot hits or is hit by
     * 			another robot
     * @see Rules#ROBOT_HIT_DAMAGE
     * @see RobotAttribute#RAM_DEFENSE
     */
    public double getRobotHitDamage(){
    	return robotHitDamage;
    }
    
    /**
     * The amount of bonus given when a robot moving forward hits an opponent
     * robot (ramming)
     * <p/>
     * This is defined as {@link Rules#ROBOT_HIT_BONUS} * 
     * {@link RobotAttribute#RAM_ATTACK}
     * 
     * @return the amount of damage taken when a robot hits or is hit by
     * 			another robot
     * @see Rules#ROBOT_HIT_BONUS
     * @see RobotAttribute#RAM_ATTACK
     */
    public double getRobotHitAttack(){
    	return robotHitAttack;
    }

    private RobotStatus(double energy, double x, double y, double bodyHeading, double gunHeading, double radarHeading,
                        double velocity, double bodyTurnRemaining, double radarTurnRemaining, double gunTurnRemaining,
                        double distanceRemaining, double gunHeat, int others, int roundNum, int numRounds, long time,
                        double maxVelocity, double maxBulletPower, double minBulletPower, double acceleration,
                        double deceleration, double radarScanRadius, double maxTurnRate, double gunTurnRate,
                        double radarTurnRate, double robotHitDamage, double robotHitAttack) {
        this.energy = energy;
        this.x = x;
        this.y = y;
        this.bodyHeading = bodyHeading;
        this.gunHeading = gunHeading;
        this.radarHeading = radarHeading;
        this.bodyTurnRemaining = bodyTurnRemaining;
        this.velocity = velocity;
        this.radarTurnRemaining = radarTurnRemaining;
        this.gunTurnRemaining = gunTurnRemaining;
        this.distanceRemaining = distanceRemaining;
        this.gunHeat = gunHeat;
        this.others = others;
        this.roundNum = roundNum;
        this.numRounds = numRounds;
        this.time = time;
        this.maxVelocity = maxVelocity;
        this.maxBulletPower = maxBulletPower;
        this.minBulletPower = minBulletPower;
        this.acceleration = acceleration;
        this.deceleration = deceleration;
        this.radarScanRadius = radarScanRadius;
        this.maxTurnRate = maxTurnRate;
        this.gunTurnRate = gunTurnRate;
        this.radarTurnRate = radarTurnRate;
        this.robotHitDamage = robotHitDamage;
        this.robotHitAttack = robotHitAttack;
    }

    static ISerializableHelper createHiddenSerializer() {
        return new SerializableHelper();
    }

    private static class SerializableHelper implements ISerializableHelper,
                                                       IHiddenStatusHelper {

        @Override
        public int sizeOf(RbSerializer serializer, Object object) {
            return RbSerializer.SIZEOF_TYPEINFO + 12 * RbSerializer.SIZEOF_DOUBLE + 3 * RbSerializer.SIZEOF_INT
                    + RbSerializer.SIZEOF_LONG;
        }

        @Override
        public void serialize(RbSerializer serializer, ByteBuffer buffer, Object object) {
            RobotStatus obj = (RobotStatus) object;

            serializer.serialize(buffer, obj.energy);
            serializer.serialize(buffer, obj.x);
            serializer.serialize(buffer, obj.y);
            serializer.serialize(buffer, obj.bodyHeading);
            serializer.serialize(buffer, obj.gunHeading);
            serializer.serialize(buffer, obj.radarHeading);
            serializer.serialize(buffer, obj.velocity);
            serializer.serialize(buffer, obj.bodyTurnRemaining);
            serializer.serialize(buffer, obj.radarTurnRemaining);
            serializer.serialize(buffer, obj.gunTurnRemaining);
            serializer.serialize(buffer, obj.distanceRemaining);
            serializer.serialize(buffer, obj.gunHeat);
            serializer.serialize(buffer, obj.others);
            serializer.serialize(buffer, obj.roundNum);
            serializer.serialize(buffer, obj.numRounds);
            serializer.serialize(buffer, obj.time);
            serializer.serialize(buffer, obj.maxVelocity);
            serializer.serialize(buffer, obj.maxBulletPower);
            serializer.serialize(buffer, obj.minBulletPower);
            serializer.serialize(buffer, obj.acceleration);
            serializer.serialize(buffer, obj.deceleration);
            serializer.serialize(buffer, obj.radarScanRadius);
            serializer.serialize(buffer, obj.maxTurnRate);
            serializer.serialize(buffer, obj.gunTurnRate);
            serializer.serialize(buffer, obj.radarTurnRate);
            serializer.serialize(buffer, obj.robotHitDamage);
            serializer.serialize(buffer, obj.robotHitAttack);
        }

        @Override
        public Object deserialize(RbSerializer serializer, ByteBuffer buffer) {
            double energy = buffer.getDouble();
            double x = buffer.getDouble();
            double y = buffer.getDouble();
            double bodyHeading = buffer.getDouble();
            double gunHeading = buffer.getDouble();
            double radarHeading = buffer.getDouble();
            double velocity = buffer.getDouble();
            double bodyTurnRemaining = buffer.getDouble();
            double radarTurnRemaining = buffer.getDouble();
            double gunTurnRemaining = buffer.getDouble();
            double distanceRemaining = buffer.getDouble();
            double gunHeat = buffer.getDouble();
            int others = buffer.getInt();
            int roundNum = buffer.getInt();
            int numRounds = buffer.getInt();
            long time = buffer.getLong();
            double maxVelocity = buffer.getDouble();
            double maxBulletPower = buffer.getDouble();
            double minBulletPower = buffer.getDouble();
            double acceleration = buffer.getDouble();
            double deceleration = buffer.getDouble();
            double radarScanRadius = buffer.getDouble();
            double maxTurnRate = buffer.getDouble();
            double gunTurnRate = buffer.getDouble();
            double radarTurnRate = buffer.getDouble();
            double robotHitDamage = buffer.getDouble();
            double robotHitAttack = buffer.getDouble();
            

            return new RobotStatus(energy, x, y, bodyHeading, gunHeading, radarHeading, velocity, bodyTurnRemaining,
                                   radarTurnRemaining, gunTurnRemaining, distanceRemaining, gunHeat, others, roundNum, numRounds, time,
                                   maxVelocity, maxBulletPower, minBulletPower, acceleration,deceleration, radarScanRadius, maxTurnRate,
                                   gunTurnRate, radarTurnRate, robotHitDamage, robotHitAttack);
        }

        @Override
        public RobotStatus createStatus(double energy, double x, double y, double bodyHeading, double gunHeading, double radarHeading, 
        		double velocity, double bodyTurnRemaining, double radarTurnRemaining, double gunTurnRemaining, double distanceRemaining, 
        		double gunHeat, int others, int roundNum, int numRounds, long time, double maxVelocity, double maxBulletPower,
        		double minBulletPower, double acceleration, double deceleration, double radarScanRadius, double maxTurnRate, 
        		double gunTurnRate, double radarTurnRate, double robotHitDamage, double robotHitAttack) {
            return new RobotStatus(energy, x, y, bodyHeading, gunHeading, radarHeading, velocity, bodyTurnRemaining,
                                   radarTurnRemaining, gunTurnRemaining, distanceRemaining, gunHeat, others, roundNum, numRounds, time,
                                   maxVelocity, maxBulletPower, minBulletPower, acceleration, deceleration, radarScanRadius, maxTurnRate,
                                   gunTurnRate, radarTurnRate, robotHitDamage, robotHitAttack);
        }
    }
}
