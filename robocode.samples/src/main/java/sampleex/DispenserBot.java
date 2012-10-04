package sampleex;

import java.awt.Color;

import robocode.Dispenser;

/**
 * Behaviour for the Dispenser. Basically, it just sits there.
 * @author The Fightin' Mongooses
 */
public class DispenserBot extends Dispenser {
	
	int turnDirection = 1;
	
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
