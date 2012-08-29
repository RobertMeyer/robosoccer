/*
 * Copyright (c) 2012 Team Steel.
 * 
 * Contributors:
 * 	Paul Wade
 * 	Chris Iriving
 * 	Jesse Claven
 */

package robocode.weapon;

import robocode.weapon.WeaponType;
import robocode.weapon.types.*;

/*
 * Represents a weapon.
 * 
 * @author Jesse Claven
 */
public class Weapon {

	/*
	 * @param coolDown		Cool-down rate of the weapon.
	 * @param energyCost	Cost to shoot the weapon once.
	 */
	private WeaponType wpn;
	private final double coolDown;
	private final double energyCost;

	public Weapon(WeaponType w) {
		coolDown = w.getCoolDown();
		energyCost = w.getEnergyCost();
	}
}
