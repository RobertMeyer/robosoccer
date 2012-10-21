package tested.equipment;

import robocode.AdvancedRobot;

/**
 * A sample equipment robot.  To be used for testing a higher then ram attack
 * and ram defense
 * 
 * Thus, it tests:
 * {@link robocode.RobotAttribute#RAM_ATTACK}
 * {@link robocode.RobotAttribute#RAM_DEFENSE}
 * 
 * @author Jayke Anderson - CSSE2003
 */
public class RamLarge extends AdvancedRobot {
	
	public void run(){		
		equip("Ram Test Large");
		while (true){
			if(getTime() == 5) setAhead(55);
			if(getTime() == 10) setBack(50);
			execute();
		}
		
	}
	
}
