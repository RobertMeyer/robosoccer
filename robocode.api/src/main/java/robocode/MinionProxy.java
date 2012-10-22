package robocode;

import robocode.robotinterfaces.peer.IBasicRobotPeer;
/**
 * MinionProxy provies communication between a minion and it's parent.
 * One MinionProxy used for either direction of communication AKA
 * MinionProxy proxyMinion (minion->parent)
 * MinionProxy proxyParent (parent->minion)
 * 
 * */
public class MinionProxy {
	private IBasicRobotPeer peer;
	public MinionProxy(IBasicRobotPeer hostManager) {
		peer = hostManager;
	}
	
    /**
     * Returns the direction that the parent robot's body is facing, in degrees.
     * The value returned will be between 0 and 360 (is excluded).
     * <p/>
     * Note that the heading in Robocode is like a compass, where 0 means North,
     * 90 means East, 180 means South, and 270 means West.
     *
     * @return the direction that the parent robot's body is facing, in degrees.
     * @see #getGunHeading()
     * @see #getRadarHeading()
     */
    public double getHeading() {
        if (peer != null) {
            double rv = 180.0 * peer.getBodyHeading() / Math.PI;

            while (rv < 0) {
                rv += 360;
            }
            while (rv >= 360) {
                rv -= 360;
            }
            return rv;
        }
        return 0; // never called
    }
	
    /**
     * Returns the parent robot's name.
     *
     * @return the parent robot's name.
     */
    public String getName() {
        if (peer != null) {
            return peer.getName();
        }
        return null; // never called
    }
	
    /**
     * Returns the X position of the parent bot. (0,0) is at the bottom left of the
     * battlefield.
     *
     * @return the X position of the parent robot.
     * @see #getY()
     */
    public double getX() {
        if (peer != null) {
            return peer.getX( );
        }
        return 0; // never called
    }
    
    /**
     * Returns the Y position of the parent robot. (0,0) is at the bottom left of the
     * battlefield.
     *
     * @return the Y position of the parent robot.
     * @see #getX()
     */
    public double getY() {
        if (peer != null) {
            return peer.getY( );
        }
        return 0; // never called
    }
    
    /**
     * Returns the rate at which the parent's gun will cool down, i.e. the amount of heat
     * the gun heat will drop per turn.
     * <p/>
     * The gun cooling rate is default 0.1 / turn, but can be changed by the
     * battle setup. So don't count on the cooling rate being 0.1!
     *
     * @return the parent's gun cooling rate
     * @see #getGunHeat()
     * @see IBasicRobotPeer#fire(double)
     * @see IBasicRobotPeer#fireBullet(double)
     */
    public double getGunCoolingRate() {
        if (peer != null) {
            return peer.getGunCoolingRate();
        }
        return 0; // never called
    }

    /**
     * Returns the direction that the parent robot's gun is facing, in degrees.
     * The value returned will be between 0 and 360 (is excluded).
     * <p/>
     * Note that the heading in Robocode is like a compass, where 0 means North,
     * 90 means East, 180 means South, and 270 means West.
     *
     * @return the direction that the parent robot's gun is facing, in degrees.
     * @see #getHeading()
     * @see #getRadarHeading()
     */
    public double getGunHeading() {
        if (peer != null) {
            return peer.getGunHeading() * 180.0 / Math.PI;
        }
        return 0; // never called
    }

    /**
     * Returns the current heat of the gun. The gun cannot fire unless this is
     * 0. (Calls to fire will succeed, but will not actually fire unless
     * getGunHeat() == 0).
     * <p/>
     * The amount of gun heat generated when the gun is fired is
     * 1 + (firePower / 5). Each turn the gun heat drops by the amount returned
     * by {@link #getGunCoolingRate()}, which is a battle setup.
     * <p/>
     * Note that all guns are "hot" at the start of each round, where the gun
     * heat is 3.
     *
     * @return the current gun heat
     * @see #getGunCoolingRate()
     * @see IBasicRobotPeer#fire(double)
     * @see IBasicRobotPeer#fireBullet(double)
     */
    public double getGunHeat() {
        if (peer != null) {
            return peer.getGunHeat();
        }
        return 0; // never called
    }
    
    /**
     * Returns the direction that the parent robot's radar is facing, in degrees.
     * The value returned will be between 0 and 360 (is excluded).
     * <p/>
     * Note that the heading in Robocode is like a compass, where 0 means North,
     * 90 means East, 180 means South, and 270 means West.
     *
     * @return the direction that the parent robot's radar is facing, in degrees.
     * @see IBasicRobotPeer#getHeading()
     * @see IBasicRobotPeer#getGunHeading()
     */
    public double getRadarHeading() {
        if (peer != null) {
            return peer.getRadarHeading() * 180.0 / Math.PI;
        }
        return 0; // never called
    }
    
    /**
     * Returns the velocity of the parent robot measured in pixels/turn.
     * <p/>
     * The maximum velocity of a robot is defined by {@link Rules#MAX_VELOCITY}
     * (8 pixels / turn).
     *
     * @return the velocity of the parent robot measured in pixels/turn.
     * @see Rules#MAX_VELOCITY
     */
    public double getVelocity() {
        if (peer != null) {
            return peer.getVelocity();
        }
        return 0; // never called
    }
    
    /**
     * @param other
     * @return true if other uses the same peer as (this) instance.
     */
    public boolean equals(MinionProxy other) {
    	if(other.peer == this.peer)
    		return true;
    	return false;
    }
}