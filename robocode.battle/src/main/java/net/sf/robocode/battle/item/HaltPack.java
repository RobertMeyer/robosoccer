package net.sf.robocode.battle.item;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.peer.RobotPeer;

/**
 * HaltPack is an item which can be dropped onto the battlefield. It stops the
 * movement of the robot that hits it for the rest of the current round.
 * 
 * @author Ghassan Thabit (Dream Team)
 * @author Ameer Sabri (Dream Team) (commenting only)
 */
public class HaltPack extends ItemDrop {

	/**
	 * Constructor for the item.
	 * 
	 * @param battle the battlefield the item is being added to
	 * @param name the name of the item
	 */
	public HaltPack(Battle battle, String name){
		//calls the ItemDrop constructor
		super(true, 200, 0, false, battle);
		this.name = name;
		this.imageName = "halt.png";
	}
	
	/**
	 * Halts the movement of the robot that hit the item.
	 * @param robot the robot that hit the item
	 */
	public void doItemEffect(RobotPeer robot){
	//	System.out.println("Halt item USED");
		robot.setHalt(true);
		return;		

	}
	
}
