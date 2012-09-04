package net.sf.robocode.battle;

import net.sf.robocode.battle.peer.RobotPeer;

/**
 * A health pack item. Extends item drop
 * @author s4238358
 *
 */

public class SpeedBonusItem extends ItemDrop {

		
	public SpeedBonusItem(boolean isDestroyable, int lifespan, int health, boolean isEquippable){
		super(isDestroyable, lifespan, health, isEquippable, null);
		System.out.println("Speed Bonus");
	}
	
	public void doItemEffect(RobotPeer robot){
	//	robot.equip("Speed Boost");
	}
	
}