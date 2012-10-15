/*******************************************************************************
 * Copyright (c) 2001-2012 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 *******************************************************************************/
package robocode;

import robocode.Robot;
import robocode.robotinterfaces.ISoldierEvents;
import robocode.robotinterfaces.ISoldierRobot;

/**
 * An extension of the basic robot class, containing methods for behaviours
 * which can be triggered by the Commander controls. Simple default behaviours
 * are contained here, but any custom SoliderRobot should override these 
 * methods and specify their own tactics.
 *
 * @author The Fightin' Mongooses (contributor)
 * @see Robot
 */
public class SoldierRobot extends Robot implements ISoldierRobot, ISoldierEvents {
	
    protected static final int NO_TACTIC = 0;
	protected static final int PAUSE = 1;
	protected static final int ADVANCE = 2;
	protected static final int RETREAT = 3;
	protected static final int ATTACK = 4;
	protected static final int INCREASE_POWER = 5;
	protected static final int DECREASE_POWER = 6;
	protected static final int TAUNT = 7;
	
	protected double power = 1.5;

	public void onCommandRecieved(CommanderEvent e) {
		
	}
	
	
	/**
	 * Passes information to the tactic methods on scan.
	 * When writing your own onScannedRobot for your own SoldierRobot,
	 * you should put super.onScannedRobot early in the method, so that
	 * ScannedRobotEvents can still be passed.
	 */
	
	/**
	 * Stop everything.
	 */
	public void pause() {
		stop();
	}
	
	/**
	 * Move forward.
	 */
	public void advance() {
		ahead(100);
	}
	
	/**
	 * Back away.
	 */
	public void retreat() {
		back(100);
	}
	
	/**
	 * Fire around randomly!
	 */
	public void attack() {
		turnRight(45);
		fire(1);
	}
	
	/**
	 * Increase the power of the robot's shots.  Returns the 
	 * tactic to continue with after increase.
	 */
	public int increasePower() {
		if (power < 2.5) {
			power += 0.5;
		} else {
			power = 3.0;
		}
		return RETREAT;
	}
	
	/**
	 * Decrease the power of your shots to save power.  Returns the
	 * tactic to continue with after decrease.
	 */
	public int decreasePower() {
		if (power > 1.0) {
			power -= 0.5;
		} else {
			power = 0.5;
		}
		return ADVANCE;
	}
	
	/**
	 * Degrade your enemies by taunting them.
	 */
	public void taunt() {
    	turnRadarRight(360);
    	turnGunRight(-360);
	}
	
	public final ISoldierEvents getSoldierEventListener() {
        return this; // this robot is listening
    }
	
}