package net.sf.robocode.battle.item;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.peer.RobotPeer;

/**
 * HaltPack represents items that can be dropped on the battlefield. 
 * It causes the robot to be halted for the rest of the round on hit.
 * 
 * @author Ghassan Thabit
 *
 */

public class HaltPack extends ItemDrop {


		
	public HaltPack(Battle battle, String name){
		super(true, 200, 0, false, battle);
		this.name = name;
		this.imageName = "halt.png";
	}
	
	
	public void doItemEffect(RobotPeer robot){
		System.out.println("Halt item USED");
		robot.setHalt(true);
		return;		

	}
	
}
