package tested.equipment;

import robocode.AdvancedRobot;

/**
 * A sample equipment robot.  To be used for testing a smaller robot turn angle.
 * 
 * @author Jayke Anderson - CSSE2003
 */
public class TurnSmall extends AdvancedRobot {
	
	public void run(){
		equip("Body Test Small");
		while (true){
			if(getTime()%10 == 0){
				setTurnRight(8);
			}
			execute();
		}
	}
	
}
