package robocode;

import robocode.robotinterfaces.peer.IBasicRobotPeer;
/**
 * MinionProxy provies communication between a minion and it's parent.
 * One MinionProxy used for either direction of communication AKA
 * MinionProxy proxy (minion->parent)
 * MinionProxy proxy (parent->minion) 
 * 
 * */
public class MinionProxy {
	private IBasicRobotPeer peer;
	public MinionProxy(IBasicRobotPeer hostManager) {
		peer = hostManager;
	}
}