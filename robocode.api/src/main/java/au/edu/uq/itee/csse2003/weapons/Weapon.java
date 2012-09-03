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

	private final double coolDown;
	private final double projectilePower;
	private final double minDamage;
	private final double maxDamage;
	private final double riochet;
	private final double accuracy;
	private final double criticalHit;
	private final double weaponTurnRate;

	public Weapon(WeaponType w) {
		coolDown = w.getCoolDown();
		projectilePower = w.getProjectilePower();
		minDamage = w.getMinDamage();
		maxDamage = w.getMaxDamage();
		riochet = w.getRiochet();
		accuracy = w.getAccuracy();
		criticalHit = w.getCriticalHit();
		weaponTurnRate = w.getWeaponTurnRate();
	}
	
}