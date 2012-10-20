package net.sf.robocode.battle.item;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.peer.RobotPeer;

/**
 * GunCoolerPack represents items that can be dropped on the battlefield. 
 * It reduces robot's gunHeat by the amount of 'gunCoolingRate' on hit.
 * 
 * @author Ghassan Thabit
 *
 */

public class GunCoolerPack extends ItemDrop {


		
	public GunCoolerPack(Battle battle, String name){
		super(true, 400, 0, false, battle);
		this.name = name;
		this.imageName = "cooler.png";
	}
	
	public void doItemEffect(RobotPeer robot){
		if (robot.getGunHeat() > 0) {
			System.out.println("gunHeat = " + robot.getGunHeat());
			robot.setGunHeatEffect(robot.getGunHeat() * 0.9);
			System.out.println("GunCoolerPack item USED");
			System.out.println("gunHeat = " + robot.getGunHeat());	
		}
			
	}
	
}
