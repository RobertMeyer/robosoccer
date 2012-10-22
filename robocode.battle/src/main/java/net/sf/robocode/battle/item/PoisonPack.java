package net.sf.robocode.battle.item;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.peer.RobotPeer;

/**
 * PoisonPack is an item which can be dropped onto the battlefield. It reduces
 * the energy of the robot that hits it.
 * 
 * @author Ghassan Thabit (Dream Team)
 * @author Ameer Sabri (Dream Team) (commenting only)
 */
public class PoisonPack extends ItemDrop {

	/**
	 * Constructor for the item.
	 * 
	 * @param battle the battlefield the item is being added to
	 * @param name the name of the item
	 */
	public PoisonPack(Battle battle, String name){
		//calls the ItemDrop constructor
		super(true, 200, 0, false, battle);
		this.name = name;
		this.imageName = "poison.png";
	}
	
	
	/**
	 * Decreases the energy of the robot that hits the item by 15.
	 * 
	 * @param robot the robot that hit the item
	 */
	public void doItemEffect(RobotPeer robot){
		//System.out.println("Energy = " + robot.getEnergy());
		robot.updateEnergy(-15);
		//System.out.println("Poison item USED");
		//System.out.println("Energy = " + robot.getEnergy());
		
	}
	
}
