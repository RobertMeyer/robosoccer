package net.sf.robocode.host.proxies;

import java.awt.geom.Rectangle2D;
import robocode.robotinterfaces.peer.ISoccerRobotPeer;
import net.sf.robocode.host.IHostManager;
import net.sf.robocode.host.RobotStatics;
import net.sf.robocode.peer.IRobotPeer;
import net.sf.robocode.repository.IRobotRepositoryItem;


public class SoccerRobotProxy extends TeamRobotProxy implements 
		ISoccerRobotPeer {
	
	 public SoccerRobotProxy(IRobotRepositoryItem specification, 
			 IHostManager hostManager, IRobotPeer peer, RobotStatics statics) {
	        super(specification, hostManager, peer, statics);
	 }
	 
	 public Rectangle2D.Float getOwnGoal() {
		 return peer.getOwnGoal();
	 }
	 
	 public Rectangle2D.Float getEnemyGoal() {
		 return peer.getEnemyGoal();
	 }
}
