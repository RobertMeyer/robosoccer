/*******************************************************************************
 * Copyright (c) 2001-2012 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 *******************************************************************************/
package robocode;

import java.awt.Color;

import robocode.Robot;

/**
 * An extension of the basic robot class, containing methods for behaviours
 * which can be triggered by the Commander controls. Simple default behaviours
 * are contained here, but any custom SoliderRobot should override these 
 * methods and specify their own tactics.
 *
 * @author The Fightin' Mongooses (contributor)
 * @see Robot
 */
public class SoldierRobot extends Robot {
	
	Color pause = new Color(255, 0, 0); //red
	Color advance = new Color(0, 255, 0); //green
	Color retreat = new Color(255, 255, 0); //yellow
	
	Color attack = new Color(0,0,0);
	
	Color taunt = new Color(255, 20, 147); //deep pink
	
	private Color bodyColor = null;
	private Color gunColor = null;
	private Color radarColor = null;
	
	private double power = 1.5;
	
	
	/**
	 * Stop everything.
	 */
	public void pause() {
		bodyColor = pause;
		setColors(bodyColor, gunColor, radarColor);
		
		stop();
	}
	
	/**
	 * Find an enemy and approach them.
	 */
	public void advance() {
		bodyColor = advance;
		setColors(bodyColor, gunColor, radarColor);
		
		//detect enemy
		//need to take event data, prevent onScannedRobot from doing it's thing
		//move forward
	}
	
	/**
	 * Back away.
	 */
	public void retreat() {
		bodyColor = retreat;
		setColors(bodyColor, gunColor, radarColor);
		
		while(true) back(1);
	}
	
	/**
	 * Find an enemy and fire at them!
	 */
	public void attack() {
		
		setColors(attack, gunColor, radarColor);
		
		//detect enemy
		//need to take event data, prevent onScannedRobot from doing it's thing
		//shoot at them - use power variable
		
		setColors(bodyColor, gunColor, radarColor);
		
	}
	
	/**
	 * Increase the power of the robot's shots.
	 */
	public void increasePower() {
		//for gun color, use saturation: color(sat, sat, sat)
		
		if (power <= 2.5) {
			power += 0.5;
		}
	}
	
	/**
	 * Decrease the power of your shots to save power.
	 */
	public void decreasePower() {
		//for gun color, use saturation: color(sat, sat, sat)
		
		if (power > 0.5) {
			power -= 0.5;
		}
	}
	
	/**
	 * Degrade your enemies by taunting them.
	 */
	public void taunt() {
		bodyColor = taunt;
		setColors(bodyColor, gunColor, radarColor);
		
    	for (int i = 0; i < 360; i++) {
    		turnRadarRight(1);
    		turnGunRight(1);
    		turnLeft(1);
    	}
	}
}