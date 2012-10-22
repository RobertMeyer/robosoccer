package robocode;

/**
 * The attributes that can be used to control game mechanics for a single
 * robot. Each robot has a value associated with every attribute; these values
 * represent the modification (relative to the default value) of that attribute
 * for the robot.
 *
 * @author Malcolm Inglis (CSSE2003)
 *
 */
public enum RobotAttribute {

    /** The acceleration of the robot */
    ACCELERATION,
    /** The deceleration of the robot */
    DECELERATION,
    /** The scan radius of the robot */
    SCAN_RADIUS,
    /** The gun turn rate of the robot */
    GUN_TURN_ANGLE,
    /** The chasis turn rate of the robot */
    ROBOT_TURN_ANGLE,
    /** How fast the robot moves. */
    VELOCITY,
    /** The energy (aka life) of the robot. */
    ENERGY,
    /** The rate of energy regeneration of the robot. */
    ENERGY_REGEN,
    /** The damage taken by the robot. */
    ARMOR,
    /** The damage done by the robot's bullets. */
    BULLET_DAMAGE,
    /** The speed of the bullets fired by the robot. */
    BULLET_SPEED,
    /** The rate that the robot's gun heats at. */
    GUN_HEAT_RATE,
    /** The damage done by the robot from physical contact. */
    RAM_ATTACK,
    /** The damage taken by the robot from physical contact. */
    RAM_DEFENSE,
    /** The angle that the robot's radar can view. */
    RADAR_ANGLE;
}
