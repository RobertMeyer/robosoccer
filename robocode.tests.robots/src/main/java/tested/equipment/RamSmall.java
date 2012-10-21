package tested.equipment;

import robocode.AdvancedRobot;

/**
 * A sample equipment robot.  To be used for testing a small than normal ram
 * attack and ram defense
 * 
 * Thus, it tests:
 * {@link robocode.RobotAttribute#RAM_ATTACK}
 * {@link robocode.RobotAttribute#RAM_DEFENSE}
 * 
 * @author Jayke Anderson - CSSE2003
 */
public class RamSmall extends AdvancedRobot {
	
	int lastRammed = 0;
	
	public void run(){		
		equip("Ram Test Small");
		while (true){
			if(getTime() == 10) setAhead(55);
			if(getTime() == 15) setBack(50);
			execute();
		}
		
	}
	
}
