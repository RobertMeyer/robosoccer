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
 *     - Removed the battlefield field, which can be created when calling
 *       getBattlefield() and optimized constructor
 *     - Changed getRobots() to return a copy of the robots
 *     - This class now implements java.io.Serializable
 *     - Updated Javadocs
 *******************************************************************************/
package robocode.control;

import java.io.File;

/**
 * A BattleSpecification defines a battle configuration used by the
 * {@link RobocodeEngine}.
 * 
 * @author Mathew A. Nelson (original)
 * @author Flemming N. Larsen (contributor)
 * @author CSSE2003 Team Forkbomb (equipment)
 */
public class BattleSpecification implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private final int battlefieldWidth;
	private final int battlefieldHeight;
	private final int numRounds;
	private final double gunCoolingRate;
	private final long inactivityTime;
	private final boolean hideEnemyNames;
	private final RobotSpecification[] robots;
	private File equipmentFile;

	/**
	 * Creates a new BattleSpecification with the given number of rounds,
	 * battlefield size, and robots. Inactivity time for the robots defaults to
	 * 450, and the gun cooling rate defaults to 0.1.
	 * 
	 * @param numRounds
	 *            the number of rounds in this battle
	 * @param battlefieldSize
	 *            the battlefield size
	 * @param robots
	 *            the robots participating in this battle
	 */
	public BattleSpecification(int numRounds,
			BattlefieldSpecification battlefieldSize,
			RobotSpecification[] robots) {
		this(numRounds, 450, .1, battlefieldSize, robots, null);
	}

	/**
	 * Creates a new BattleSpecification with the given number of rounds,
	 * battlefield size, and robots. Inactivity time for the robots defaults to
	 * 450, and the gun cooling rate defaults to 0.1.
	 * 
	 * @param numRounds
	 *            the number of rounds in this battle
	 * @param battlefieldSize
	 *            the battlefield size
	 * @param robots
	 *            the robots participating in this battle
	 * @param equipmentFile
	 *            the equipment definition file to be used in this battle
	 */
	public BattleSpecification(int numRounds,
			BattlefieldSpecification battlefieldSize,
			RobotSpecification[] robots, File equipmentFile) {
		this(numRounds, 450, .1, battlefieldSize, robots, equipmentFile);
	}

	/**
	 * Creates a new BattleSpecification with the given settings.
	 * 
	 * @param numRounds
	 *            the number of rounds in this battle
	 * @param inactivityTime
	 *            the inactivity time allowed for the robots before they will
	 *            loose energy
	 * @param gunCoolingRate
	 *            the gun cooling rate for the robots
	 * @param battlefieldSize
	 *            the battlefield size
	 * @param robots
	 *            the robots participating in this battle
	 * @param equipmentFile
	 *            the equipment definition file to be used in this battle
	 */
	public BattleSpecification(int numRounds, long inactivityTime,
			double gunCoolingRate, BattlefieldSpecification battlefieldSize,
			RobotSpecification[] robots, File equipmentFile) {
		this(numRounds, inactivityTime, gunCoolingRate, false, battlefieldSize,
				robots, equipmentFile);
	}

	/**
	 * Creates a new BattleSpecification with the given settings.
	 * 
	 * @param numRounds
	 *            the number of rounds in this battle
	 * @param inactivityTime
	 *            the inactivity time allowed for the robots before they will
	 *            loose energy
	 * @param gunCoolingRate
	 *            the gun cooling rate for the robots
	 * @param hideEnemyNames
	 *            flag specifying if enemy names are hidden from robots
	 * @param battlefieldSize
	 *            the battlefield size
	 * @param robots
	 *            the robots participating in this battle
	 * @param equipmentFile
	 *            the equipment definition file to be used in this battle
	 * 
	 * @since 1.7.3
	 */
	public BattleSpecification(int numRounds, long inactivityTime,
			double gunCoolingRate, boolean hideEnemyNames,
			BattlefieldSpecification battlefieldSize,
			RobotSpecification[] robots, File equipmentFile) {
		this.numRounds = numRounds;
		this.inactivityTime = inactivityTime;
		this.gunCoolingRate = gunCoolingRate;
		this.hideEnemyNames = hideEnemyNames;
		this.battlefieldWidth = battlefieldSize.getWidth();
		this.battlefieldHeight = battlefieldSize.getHeight();
		this.robots = robots;
		this.equipmentFile = equipmentFile;
	}

	/**
	 * Returns the allowed inactivity time for the robots in this battle.
	 * 
	 * @return the allowed inactivity time for the robots in this battle.
	 */
	public long getInactivityTime() {
		return inactivityTime;
	}

	/**
	 * Returns the gun cooling rate of the robots in this battle.
	 * 
	 * @return the gun cooling rate of the robots in this battle.
	 */
	public double getGunCoolingRate() {
		return gunCoolingRate;
	}

	/**
	 * Returns the battlefield size for this battle.
	 * 
	 * @return the battlefield size for this battle.
	 */
	public BattlefieldSpecification getBattlefield() {
		return new BattlefieldSpecification(battlefieldWidth, battlefieldHeight);
	}

	/**
	 * Returns the number of rounds in this battle.
	 * 
	 * @return the number of rounds in this battle.
	 */
	public int getNumRounds() {
		return numRounds;
	}

	/**
	 * Flag specifying if the enemy names must be hidden from events sent to
	 * robots.
	 * 
	 * @return true if the enemy names must be hidden; false otherwise.
	 * 
	 * @since 1.7.3
	 */
	public boolean getHideEnemyNames() {
		return hideEnemyNames;
	}

	/**
	 * Returns the specifications of the robots participating in this battle.
	 * 
	 * @return the specifications of the robots participating in this battle.
	 */
	public RobotSpecification[] getRobots() {
		if (robots == null) {
			return null;
		}

		RobotSpecification[] robotsCopy = new RobotSpecification[robots.length];

		System.arraycopy(robots, 0, robotsCopy, 0, robots.length);

		return robotsCopy;
	}

	/**
	 * @return the equipment definition file to be used in this battle
	 */
	public File getEquipmentFile() {
		return equipmentFile;
	}
}
