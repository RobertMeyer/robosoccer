package net.sf.robocode.battle.item;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.peer.RobotPeer;

/**
 * GunCoolerPack is an item which can be dropped on the battlefield. When this
 * item is hit, if the robot's gun heat is greater than 0, it is reduced by 20%.
 * 
 * @author Ghassan Thabit (Dream Team)
 * @author Ameer Sabri (Dream Team) (commenting only)
 */

public class GunCoolerPack extends ItemDrop {

	/**
	 * Constructor for the item.
	 * 
	 * @param battle the battlefield the item is being added to
	 * @param name the name of the item
	 */
	public GunCoolerPack(Battle battle, String name){
		super(true, 400, 0, false, battle);
		this.name = name;
		this.imageName = "cooler.png";
	}
	
	/**
	 * Reduces the gun heat of the robot that hit the item by 20%.
	 * @param robot the robot that hit the item
	 */
	public void doItemEffect(RobotPeer robot){
		if (robot.getGunHeat() > 0) {
			//System.out.println("gunHeat = " + robot.getGunHeat());
			robot.setGunHeatEffect(robot.getGunHeat() * 0.8);
			//System.out.println("GunCoolerPack item USED");
			//System.out.println("gunHeat = " + robot.getGunHeat());	
		}
		return;	
	}
	
}
