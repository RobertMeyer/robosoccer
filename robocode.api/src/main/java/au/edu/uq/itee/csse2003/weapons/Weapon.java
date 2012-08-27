/**
 * Copyright (c) 2012 Team Steel.
 * 
 * Contributors:
 * 	Paul Wade
 * 	Chris Iriving
 * 	Jesse Claven
 */

package au.edu.uq.itee.csse2003.weapons;

import au.edu.uq.itee.csse2003.weapons.WeaponType;
import au.edu.uq.itee.csse2003.weapons.types.*;
import robocode.Robot;

/**
 * Represents a weapon.
 * 
 * @author Chris Irving
 * @author Paul Wade 
 * @author Jesse Claven
 */
public class Weapon {

	/**
	 * @param coolDown		Cool-down rate of the weapon.
	 * @param energyCost	Cost to shoot the weapon once.
	 * @param minDamage		Minimum damage of the weapon's projectile.
	 * @param maxDamage		Maximum damage of the weapon's projectile.
	 */
	private WeaponType wpn;
	private final double coolDown;
	private final double energyCost;
	private final double minDamage;
	private final double maxDamage;
	private final double riochet;

	public Weapon(WeaponType w) {
		coolDown = w.getCoolDown();
		energyCost = w.getEnergyCost();
		minDamage = w.getMinDamage();
		maxDamage = w.getMaxDamage();
		riochet = w.getRiochet();
	}
	
}
