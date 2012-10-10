/*******************************************************************************
 * Copyright (c) 2001-2012 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 *******************************************************************************/
package robocode;

import robocode.Robot;
import robocode.robotinterfaces.IBasicRobot;

/**
 * An extension of the basic robot class, containing methods for behaviours
 * which can be triggered by the Commander controls. Simple default behaviours
 * are contained here, but any custom SoliderRobot should override these 
 * methods and specify their own tactics.
 *
 * @author The Fightin' Mongooses (contributor)
 * @see Robot
 */
public class SoldierRobot extends Robot implements IBasicRobot {
	
    public final int NO_TACTIC = 0;
	public final int PAUSE = 1;
	public final int ADVANCE = 2;
	public final int RETREAT = 3;
	public final int ATTACK = 4;
	public final int INCREASE_POWER = 5;
	public final int DECREASE_POWER = 6;
	public final int TAUNT = 7;
	
	private int tactic = 0;
	protected double power = 1.5;
	
	public void receiveCommand(CommanderEvent e) {
		tactic = e.getTactic();
		switch (tactic) {
			case PAUSE:
				pause();
				break;
				
			case ADVANCE:
			case RETREAT:
			case ATTACK:
				turnRadarRight(360);
				break;
				
			case INCREASE_POWER:
				increasePower();
				break;
				
			case DECREASE_POWER:
				decreasePower();
				break;
				
			case TAUNT:
				taunt();
				break;
		}
	}
	
	/**
	 * Passes information to the tactic methods on scan.
	 * When writing your own onScannedRobot for your own SoldierRobot,
	 * you should put super.onScannedRobot early in the method, so that
	 * ScannedRobotEvents can still be passed.
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		switch (tactic) {
			case ADVANCE:
				advance(e);
				break;
				
			case RETREAT:
				retreat(e);
				break;
				
			case ATTACK:
				attack(e);
				break;
		}
	}
	
	/**
	 * Stop everything.
	 */
	public void pause() {
		stop();
	}
	
	/**
	 * Find an enemy and approach them.
	 */
	public void advance(ScannedRobotEvent e) {
		turnRight(e.getBearing());
		while(true) ahead(1);
	}
	
	/**
	 * Back away.
	 */
	public void retreat(ScannedRobotEvent e) {
		turnRight(e.getBearing());
		while(true) back(1);
	}
	
	/**
	 * Find an enemy and fire at them!
	 */
	public void attack(ScannedRobotEvent e) {
		turnGunRight(getHeading() - getGunHeading() + e.getBearing());
		fire(power);
	}
	
	/**
	 * Increase the power of the robot's shots.
	 */
	public void increasePower() {
		if (power < 2.5) {
			power += 0.5;
		} else {
			power = 3.0;
		}
	}
	
	/**
	 * Decrease the power of your shots to save power.
	 */
	public void decreasePower() {
		if (power > 1.0) {
			power -= 0.5;
		} else {
			power = 0.5;
		}
	}
	
	/**
	 * Degrade your enemies by taunting them.
	 */
	public void taunt() {
    	for (int i = 0; i < 360; i++) {
    		turnRadarRight(1);
    		turnGunRight(1);
    		turnLeft(1);
    	}
	}
	
}