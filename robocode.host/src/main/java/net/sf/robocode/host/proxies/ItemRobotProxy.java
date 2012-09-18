package net.sf.robocode.host.proxies;

import net.sf.robocode.host.IHostManager;
import net.sf.robocode.host.RobotStatics;
import net.sf.robocode.peer.IRobotPeer;
import net.sf.robocode.repository.IRobotRepositoryItem;
import robocode.robotinterfaces.peer.IItemRobotPeer;

/**
 * Proxy for item robots.
 * TODO implement doItemEffect
 * 
 * @author Ameer Sabri
 */
public class ItemRobotProxy extends AdvancedRobotProxy implements IItemRobotPeer{
	
	public ItemRobotProxy(IRobotRepositoryItem specification, IHostManager hostManager, IRobotPeer peer, RobotStatics statics) {
		super(specification, hostManager, peer, statics);
	}
	
	public void doItemEffect() {
		
	}
}
