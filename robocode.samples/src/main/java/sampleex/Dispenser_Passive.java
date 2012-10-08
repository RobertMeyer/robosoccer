package sampleex;

import robocode.Dispenser;
import java.awt.Color;


/**
 * Passive version of the dispenser. Basically, it sits there like some great
 * lump and waits for robots to approach it so that it can give energy. It
 * also does a weird spinny sort of thing, to make it more interesting.
 * @author The Fightin' Mongooses
 */
public class Dispenser_Passive extends Dispenser {
	
	@Override
	public void run() {
		
		setColors(Color.white, Color.red, Color.red, Color.white, Color.white);
		
    	while (true) {
    		turnRadarRight(1);
    		turnGunRight(1);
    		turnLeft(1);
    	}
	}
}
