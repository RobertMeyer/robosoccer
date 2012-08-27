package robocode.weapon;

/*
 * Represents a type of weapon that a robot can use.
 */
public class WeaponType {
	
	/*
	 * 
	 */
	private final double coolDown;
	private final double energyCost;
	
	public WeaponType() {
		// Temporarily initalising variables to avoid compile errors.
		coolDown = 0.5;
		energyCost = 5.0;
	}
	
	/*
	 * Returns the weapon's cool-down rate.
	 * 
	 * @return 		double of coolDown.
	 */
	public double getCoolDown() {
		return coolDown;
	}
	
	/*
	 * Returns the energy cost to fire the weapon.
	 * 
	 * @return 		double of energy cost.
	 */
	public double getEnergyCost() {
		return energyCost;
	}
}
