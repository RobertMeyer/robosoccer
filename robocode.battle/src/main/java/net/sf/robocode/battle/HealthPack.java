package net.sf.robocode.battle;

import net.sf.robocode.battle.peer.RobotPeer;

/**
 * A health pack item. Extends item drop
 * @author s4238358
 *
 */

public class HealthPack extends ItemDrop {

		
	public HealthPack(Battle battle){
		super(true, 400, 0, false, battle);
		System.out.println("Health Pack at (" + this.getXLocation() + "," + this.getYLocation() + ")");
	}
	
	public void doItemEffect(RobotPeer robot){
		robot.updateEnergy(30);
		System.out.println("Item USED");
	}
	
}
