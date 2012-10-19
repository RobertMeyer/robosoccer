package tested.equipment;

import robocode.AdvancedRobot;

/**
 * A sample equipment robot.  To be used for testing a larger radar turn
 * angle and scan radius on a robot.
 * 
 * @author Jayke Anderson - CSSE2003
 */
public class RadarLarge extends AdvancedRobot {
	
	public void run(){
		equip("Radar Test Large");
		setAdjustRadarForRobotTurn(true);
		setAdjustRadarForGunTurn(true);
		while(true){
			if(getTime()%10 == 0){
				setTurnRadarRight(65);
			}
			execute();
		}
	}
	
}
