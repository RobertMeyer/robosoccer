package sampleex;

import java.awt.Color;

import robocode.ScannedRobotEvent;
import robocode.ZombieRobot;

public class NormalZombie extends ZombieRobot {

    int radarDirection = 1;
    
    public void run() {
		setBodyColor(new Color(128, 42, 42));
		setGunColor(new Color(128, 42, 42));
		setRadarColor(new Color(128, 42, 42));
		setBulletColor(new Color(255, 0, 0));
		setScanColor(new Color(255, 0, 0));
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
