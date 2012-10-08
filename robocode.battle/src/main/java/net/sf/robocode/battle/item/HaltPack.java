package net.sf.robocode.battle.item;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.peer.RobotPeer;

/**
 * A health pack item. Extends item drop
 * @author Ghassan Thabit
 *
 */

public class HaltPack extends ItemDrop {


		
	public HaltPack(Battle battle, String name){
		super(true, 400, 0, false, battle);
		this.name = name;
		this.imageName = "halt.png";
	//	System.out.println("Health Pack at (" + this.getXLocation() + "," + this.getYLocation() + ")");
	}
	
	
	public void doItemEffect(RobotPeer robot){
		System.out.println("Halt item USED");
		robot.setHalt(true);
		return;		

	}
	
}
