package sampleex;

import robocode.ScannedRobotEvent;
import robocode.ZombieRobot;

public class NormalZombie extends ZombieRobot {

    int radarDirection = 1;
    
    public void run() {
    	
    	setTurnRadarRight(99999);
    	
    }
    public void onScannedRobot(ScannedRobotEvent e) {
    	
    	// only go towards non-zombies
		if(!e.getName().startsWith("sampleex.NormalZombie")){
			
			// head towards scanned robot
			setTurnRight(e.getBearing());
	    	setAhead(2);
	    	
	    	// stay locked on to our target
	    	radarDirection = -radarDirection;
	    	setTurnRadarRight(99999*radarDirection);
		}
		
    }
}
