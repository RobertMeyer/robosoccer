package net.sf.robocode.battle.item;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.peer.RobotPeer;

/**
 * A health pack item. Extends item drop
 * @author s4238358
 *
 */

public class HealthPack extends ItemDrop {


		
	public HealthPack(Battle battle, String name){
		super(true, 200, 0, false, battle);
		this.name = name;
		this.imageName = "health.png";
	}
	
	public void doItemEffect(RobotPeer robot){
		robot.updateEnergy(30);
	}
	
}
