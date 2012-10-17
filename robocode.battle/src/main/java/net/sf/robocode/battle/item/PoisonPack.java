package net.sf.robocode.battle.item;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.peer.RobotPeer;

/**
 * PoisonPack represents items that can be dropped on the battlefield. 
 * It reduces robot's energy by a certain amount on hit.
 * 
 * @author Ghassan Thabit
 *
 */

public class PoisonPack extends ItemDrop {


		
	public PoisonPack(Battle battle, String name){
		super(true, 200, 0, false, battle);
		this.name = name;
		this.imageName = "poison.png";
	}
	
	public void doItemEffect(RobotPeer robot){
		System.out.println("Energy = " + robot.getEnergy());
		robot.updateEnergy(-15);
		System.out.println("Poison item USED");
		System.out.println("Energy = " + robot.getEnergy());
		
	}
	
}
