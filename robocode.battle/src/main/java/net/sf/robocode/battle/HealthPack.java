package net.sf.robocode.battle;

import net.sf.robocode.battle.peer.RobotPeer;

/**
 * A health pack item. Extends item drop
 * @author s4238358
 *
 */

public class HealthPack extends ItemDrop {

		
	public HealthPack(boolean isDestroyable, int lifespan, int health, boolean isEquippable){
		super(isDestroyable, lifespan, health, isEquippable, null);
		System.out.println("Health Pack");
	}
	
	public void doItemEffect(RobotPeer robot){
		robot.updateEnergy(30);
	}
	
}
