package sampleex;

import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;
import robocode.ZombieRobot;

public class NormalZombie extends ZombieRobot {

    int radarDirection = 1;
    
    public void run() {
    	
    	setTurnRadarRight(99999);
    	
    }
    public void onScannedRobot(ScannedRobotEvent e) {
    	
		if(!e.getName().startsWith("sampleex.NormalZombie")){
			setTurnRight(e.getBearing());
	    	setAhead(5);
	    	// reversing radar will keep it going and also pointing to our kill target
	    	radarDirection = -radarDirection;
	    	setTurnRadarRight(99999*radarDirection);
		}
		
    }
}
