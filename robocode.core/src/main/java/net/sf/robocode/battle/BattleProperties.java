/*******************************************************************************
 * Copyright (c) 2001-2012 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 *
 * Contributors:
 *     Mathew A. Nelson
 *     - Initial API and implementation
 *     Flemming N. Larsen
 *     - Added setSelectedRobots(RobotSpecification[])
 *     - Added property for specifying initial positions and headings of the
 *       selected robots
 *******************************************************************************/
package net.sf.robocode.battle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Properties;
import net.sf.robocode.mode.*;
import robocode.AdvancedRobot;
import robocode.Robot;
import robocode.control.RobotSpecification;

/**
 * @author Mathew A. Nelson (original)
 * @author Flemming N. Larsen (contributor)
 * @author CSSE2003 Team Forkbomb (equipment)
 */
public class BattleProperties implements Serializable {

	private static final long serialVersionUID = 1L;

	private final static String
			BATTLEFIELD_WIDTH = "robocode.battleField.width",
			BATTLEFIELD_HEIGHT = "robocode.battleField.height",
			BATTLE_NUMROUNDS = "robocode.battle.numRounds",
			BATTLE_GUNCOOLINGRATE = "robocode.battle.gunCoolingRate",
			BATTLE_RULES_INACTIVITYTIME = "robocode.battle.rules.inactivityTime",
			BATTLE_HIDE_ENEMY_NAMES = "robocode.battle.hideEnemyNames",
			BATTLE_SELECTEDROBOTS = "robocode.battle.selectedRobots",
			BATTLE_EQUIPMENT_FILE = "robots.battle.equipmentFile",
			BATTLE_INITIAL_POSITIONS = "robocode.battle.initialPositions";

	private int battlefieldWidth = 800;
	private int battlefieldHeight = 600;
	private int numRounds = 10;
	private double gunCoolingRate = 0.1;
	private long inactivityTime = 450;
	private boolean hideEnemyNames;
	private String selectedRobots;
	private String initialPositions;
	private final Properties props = new Properties();

	private File equipmentFile;
	private IMode battleMode;
	private ITrackField trackField;
	private Hashtable<String, Object> modeRules;
	private Boolean effectAreaOn = false;

	/**
	 * Gets the battlefieldWidth.
	 *
	 * @return Returns a int
	 */
	public int getBattlefieldWidth() {
		return battlefieldWidth;
	}

	/**
	 * Sets the battlefieldWidth.
	 *
	 * @param battlefieldWidth The battlefieldWidth to set
	 */
	public void setBattlefieldWidth(int battlefieldWidth) {
		this.battlefieldWidth = battlefieldWidth;
		props.setProperty(BATTLEFIELD_WIDTH, "" + battlefieldWidth);
	}

	/**
	 * Gets the battlefieldHeight.
	 *
	 * @return Returns a int
	 */
	public int getBattlefieldHeight() {
		return battlefieldHeight;
	}

	/**
	 * Sets the battlefieldHeight.
	 *
	 * @param battlefieldHeight The battlefieldHeight to set
	 */
	public void setBattlefieldHeight(int battlefieldHeight) {
		this.battlefieldHeight = battlefieldHeight;
		props.setProperty(BATTLEFIELD_HEIGHT, "" + battlefieldHeight);
	}

	/**
	 * Gets the numRounds.
	 *
	 * @return Returns a int
	 */
	public int getNumRounds() {
		return numRounds;
	}

	/**
	 * Sets the numRounds.
	 *
	 * @param numRounds The numRounds to set
	 */
	public void setNumRounds(int numRounds) {
		this.numRounds = numRounds;
		props.setProperty(BATTLE_NUMROUNDS, "" + numRounds);
	}

	/**
	 * Returns the rate at which the gun will cool down, i.e. the amount of heat the gun heat will drop per turn.
	 * <p/>
	 * The gun cooling rate is default 0.1 per turn, but can be changed by the battle setup.
	 * So don't count on the cooling rate being 0.1!
	 *
	 * @return the gun cooling rate
	 * @see #setGunCoolingRate(double)
	 * @see Robot#getGunHeat()
	 * @see Robot#fire(double)
	 * @see Robot#fireBullet(double)
	 * @see robocode.BattleRules#getGunCoolingRate()
	 */
	public double getGunCoolingRate() {
		return gunCoolingRate;
	}

	/**
	 * Sets the rate at which the gun will cool down, i.e. the amount of heat the gun heat will drop per turn.
	 *
	 * @param gunCoolingRate the new gun cooling rate
	 * @see #getGunCoolingRate
	 * @see Robot#getGunHeat()
	 * @see Robot#fire(double)
	 * @see Robot#fireBullet(double)
	 * @see robocode.BattleRules#getGunCoolingRate()
	 */
	public void setGunCoolingRate(double gunCoolingRate) {
		this.gunCoolingRate = gunCoolingRate;
		props.setProperty(BATTLE_GUNCOOLINGRATE, "" + gunCoolingRate);
	}

	/**
	 * Returns the allowed inactivity time, where the robot is not taking any action, before will begin to be zapped.
	 * The inactivity time is measured in turns, and is the allowed time that a robot is allowed to omit taking
	 * action before being punished by the game by zapping.
	 * <p/>
	 * When a robot is zapped by the game, it will loose 0.1 energy points per turn. Eventually the robot will be
	 * killed by zapping until the robot takes action. When the robot takes action, the inactivity time counter is
	 * reset. 
	 * <p/>
	 * The allowed inactivity time is per default 450 turns, but can be changed by the battle setup.
	 * So don't count on the inactivity time being 450 turns!
	 *
	 * @return the allowed inactivity time.
	 * @see robocode.BattleRules#getInactivityTime()
	 * @see Robot#doNothing()
	 * @see AdvancedRobot#execute()
	 */
	public long getInactivityTime() {
		return inactivityTime;
	}

	/**
	 * Sets the allowed inactivity time, where the robot is not taking any action, before will begin to be zapped.
	 *
	 * @param inactivityTime the new allowed inactivity time.
	 * @see robocode.BattleRules#getInactivityTime()
	 * @see Robot#doNothing()
	 * @see AdvancedRobot#execute()
	 */
	public void setInactivityTime(long inactivityTime) {
		this.inactivityTime = inactivityTime;
		props.setProperty(BATTLE_RULES_INACTIVITYTIME, "" + inactivityTime);
	}

	/**
	 * Sets the flag defining if enemy names are hidden for robots during a battle.
	 *
	 * @param hideEnemyNames true if the enemy names must be hidden for a robot; false otherwise.
	 */
	public void setHideEnemyNames(boolean hideEnemyNames) {
		this.hideEnemyNames = hideEnemyNames;
		props.setProperty(BATTLE_HIDE_ENEMY_NAMES, "" + hideEnemyNames);
	}

	/**
	 * Returns true if the enemy names are hidden for robots during a battle; false otherwise.
	 */
	public boolean getHideEnemyNames() {
		return hideEnemyNames;
	}

	/**
	 * Gets the selectedRobots.
	 *
	 * @return Returns a String
	 */
	public String getSelectedRobots() {
		return selectedRobots;
	}

	/**
	 * Sets the selectedRobots.
	 *
	 * @param selectedRobots The selectedRobots to set
	 */
	public void setSelectedRobots(String selectedRobots) {
		this.selectedRobots = selectedRobots;
		props.setProperty(BATTLE_SELECTEDROBOTS, "" + selectedRobots);
	}

	/**
	 * Sets the selectedRobots.
	 *
	 * @param robots The robots to set
	 */
	public void setSelectedRobots(RobotSpecification[] robots) {
		StringBuffer robotString = new StringBuffer();
		RobotSpecification robot;

		for (int i = 0; i < robots.length; i++) {
			robot = robots[i];
			if (robot == null) {
				continue;
			}

			robotString.append(robot.getClassName());

			if (!(robot.getVersion() == null || robot.getVersion().length() == 0)) {
				robotString.append(' ').append(robot.getVersion());
			}

			if (i < robots.length - 1) {
				robotString.append(',');
			}
		}
		setSelectedRobots(robotString.toString());
	}

	/**
	 * Gets the initial robot positions and heading.
	 *
	 * @return a comma-separated string
	 */
	public String getInitialPositions() {
		return initialPositions;
	}

	public void setInitialPositions(String initialPositions) {
		if(initialPositions!=null)
		{
		this.initialPositions = initialPositions; 

		}
	}
	
	public void setInitialPositionToNull()
	{
		this.initialPositions=null;

	}
	
	/**
	 * @return the file containing the equipment definitions for the battle.
	 */
	public File getEquipmentFile() {
		return equipmentFile;
	}

	/**
	 * Sets the equipment definition file for the battle.
	 * 
	 * @param file
	 *            the file containing the equipment definitions for the battle.
	 */
	public void setEquipmentFile(File file) {
		equipmentFile = file;
		props.setProperty(BATTLE_EQUIPMENT_FILE, String.valueOf(file));
	}

	/**
	 * Gets the current battle mode.
	 * @return mode as a ModeContext object
	 */
	public IMode getBattleMode() {
		return battleMode;
	}
	
	/**
	 * Sets the battle mode.
	 */
	public void setBattleMode(IMode mode) {
		this.battleMode = mode;
	}
	
	/**
	 * Sets the Track for race mode
	 * @param trackField THe current trackField
	 */
	public void setTrackField(ITrackField trackField) {
		this.trackField = trackField;
	}
	
	/**
	 * Gets the TrackField to test boundries.
	 */
	public ITrackField getTrackField() {
		return trackField;
	}
	
	/**
	 * Get the current mode's rules
	 * @return current mode rules
	 */
	public Hashtable<String, Object> getModeRules() {
		return modeRules;
	}
	
	/** 
	 * Get whether effect area is on
	 * @return whether effect area is on
	 */
	public Boolean getEffectArea() {
		return this.effectAreaOn;
	}

	 /**
	  * Set effect area
	  */
	
	public void setEffectArea(Boolean b) {
		effectAreaOn = b;
	}
	
	/**
	 * Set the selected mode rules as a Hashtable
	 * @param selectedModeRulesValues
	 */
	public void setModeRules(Hashtable<String, Object> selectedModeRulesValues) {
		this.modeRules = selectedModeRulesValues;
	}

	public void store(FileOutputStream out, String desc) throws IOException {
		props.store(out, desc);
	}

	public void load(FileInputStream in) throws IOException {
		props.load(in);
		battlefieldWidth = Integer.parseInt(props.getProperty(BATTLEFIELD_WIDTH, "800"));
		battlefieldHeight = Integer.parseInt(props.getProperty(BATTLEFIELD_HEIGHT, "600"));
		gunCoolingRate = Double.parseDouble(props.getProperty(BATTLE_GUNCOOLINGRATE, "0.1"));
		inactivityTime = Long.parseLong(props.getProperty(BATTLE_RULES_INACTIVITYTIME, "450"));
		hideEnemyNames = Boolean.parseBoolean(props.getProperty(BATTLE_HIDE_ENEMY_NAMES, "false"));
		numRounds = Integer.parseInt(props.getProperty(BATTLE_NUMROUNDS, "10"));
		selectedRobots = props.getProperty(BATTLE_SELECTEDROBOTS, "");
		initialPositions = props.getProperty(BATTLE_INITIAL_POSITIONS,"" );
	}

}
