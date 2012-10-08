package net.sf.robocode.battle.item;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.peer.RobotPeer;

/**
 * A health pack item. Extends item drop
 * @author s4238358
 *
 */

public class PoisonPack extends ItemDrop {


		
	public PoisonPack(Battle battle, String name){
		super(true, 400, 0, false, battle);
		this.name = name;
		this.imageName = "poison.png";
	//	System.out.println("Health Pack at (" + this.getXLocation() + "," + this.getYLocation() + ")");
	}
	
	public void doItemEffect(RobotPeer robot){
		System.out.println("Energy = " + robot.getEnergy());
		robot.updateEnergy(-15);
		System.out.println("Energy = " + robot.getEnergy());
		System.out.println("Poison item USED");
	}
	
}
