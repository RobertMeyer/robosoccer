package tested.equipment;

import robocode.AdvancedRobot;

/**
 * A sample equipment robot.  To be used for testing a higher robot turn angle.
 * 
 * @author Jayke Anderson - CSSE2003
 */
public class TurnLarge extends AdvancedRobot {
	
	public void run(){
		equip("Body Test Large");
		while (true){
			if(getTime()%10 == 0){
				setTurnRight(16);
			}
			execute();
		}
	}
	
}
