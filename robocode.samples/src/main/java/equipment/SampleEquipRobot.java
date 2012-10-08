package equipment;

import robocode.*;

/**
 * A sample equipment robot.  That equips a range of equipment and moves
 * in a simple circle path.
 * 
 * @author Jayke Anderson - CSSE2003
 */
public class SampleEquipRobot extends AdvancedRobot {
	
	/**
	 * The main run function
	 */
	public void run(){
		// Equip some items
		equip("Division 9 Plasmaprojector");
		equip("Guardian Tank Armor");
		equip("Thorium Power Cell");
		equip("Stealth Tracks");
		
		// Move in a circle
		while (true){
			setAhead(150);
			execute();
		}
	}
	
	/**
	 * Hit a wall: reverse
	 */
	public void onHitWall(HitWallEvent e){
		setBack(200);
	}
	
	/**
	 * Seen a robot: kill it!
	 */
	public void onScannedRobot(ScannedRobotEvent e){
		fire(3);
	}
	
	/**
	 * Hit a robot, if at fault, run away!
	 */
	public void onHitRobot(HitRobotEvent e){
		if(e.isMyFault()){
			setBack(200);
		}
	}
}
