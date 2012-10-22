package net.sf.robocode.battle.item;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.peer.RobotPeer;

/**
 * HealthPack is an item which can be dropped onto the battlefield. It increases
 * the energy of the robot that hits it.
 * 
 * @author s4238358 (Dream Team)
 * @author Ameer Sabri (Dream Team) (commenting only)
 */

public class HealthPack extends ItemDrop {

	/**
	 * Constructor for the item.
	 * 
	 * @param battle the battlefield the item is being added to
	 * @param name the name of the item
	 */
	public HealthPack(Battle battle, String name){
		//calls the ItemDrop constructor
		super(true, 200, 0, false, battle);
		this.name = name;
		this.imageName = "health.png";
	}
	
	/**
	 * Increases the energy of the robot that hit the item by 30.
	 * 
	 * @param robot the robot that hit the item
	 */
	public void doItemEffect(RobotPeer robot){
		robot.updateEnergy(30);
	}
	
}
