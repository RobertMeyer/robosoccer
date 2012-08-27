package au.edu.uq.itee.csse2003.weapons;

import au.edu.uq.itee.csse2003.weapons.Weapon;

/**
 * Represents a type of weapon that a robot can use.
 */
public class WeaponType {
	
	/**
	 * 
	 */
	private final double coolDown;
	private final double energyCost;
	private final double minDamage;
	private final double maxDamage;
	private final double riochet;
	
	public WeaponType() {
		// Temporarily initalising variables to avoid compile errors.
		coolDown = 0.0;
		energyCost = 0.0;
		minDamage = 0.0;
		maxDamage = 0.0;
		riochet = 0.0;
	}
	
	/**
	 * Returns the weapon's cool-down rate.
	 * 
	 * @return 		double of coolDown.
	 */
	public double getCoolDown() {
		return coolDown;
	}
	
	/**
	 * Returns the energy cost to fire the weapon.
	 * 
	 * @return 		double of energy cost.
	 */
	public double getEnergyCost() {
		return energyCost;
	}

	public double getMinDamage() {
		return minDamage;
	}
	
	public double getMaxDamage() {
		return maxDamage;
	}
	
	public double getRiochet() {
		return riochet;
	}
}
