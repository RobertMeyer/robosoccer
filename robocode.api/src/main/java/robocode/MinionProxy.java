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
}