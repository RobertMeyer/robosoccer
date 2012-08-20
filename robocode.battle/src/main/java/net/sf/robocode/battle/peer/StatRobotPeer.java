package net.sf.robocode.battle.peer;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.host.IHostManager;
import robocode.control.RobotSpecification;

public final class StatRobotPeer extends RobotPeer {
	
	public StatRobotPeer(Battle battle, IHostManager hostManager, 
			RobotSpecification robotSpecification, int duplicate, 
			TeamPeer team, int robotIndex){
		
		super(battle, hostManager, 
				robotSpecification, duplicate, team,
				robotIndex);
	}
	
	
}
