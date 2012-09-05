package au.edu.uq.itee.csse2003.weapons;

import au.edu.uq.itee.csse2003.weapons.Weapon;

/**
 * Represents a type of weapon that a robot can use.
 */
public abstract class WeaponType {

    /**
     * Attributes of the gun. Default values are provided.
     *
     * @param coolDown			Cooling rate of the weapon.
     * @param projectilePower	Cost to use the weapon.
     * @param minDamage			Minimum damage of the weapon's projectile.
     * @param maxDamage			Maximum damage of the weapon's projectile.
     * @param riochet			Level of projectile riochet.
     * @param accuracy			Accuracy of the weapon.
     * @param criticalHit		Chance of a projectile causing a critical hit.
     * @param weaponTurnRate	Rate at which the weapon turns.
     */
    protected String weaponName = "Default weapon";
    protected double coolDown = 0.1;
    protected double projectilePower;
    protected double minDamage = 0.1;
    protected double maxDamage = 3;
    // TODO Check with Team MOP about a default value.
    protected double riochet = 0.1;
    protected double accuracy = 0.7;
    protected double criticalHit = 0.1;
    protected double weaponTurnRate = 20;

    @Override
    public String toString() {
        // TODO Finish this.
        String result = null;

        return result;
    }

    /**************************************************************************
     * GETTERS
     *************************************************************************/
    /**
     * Returns the weapon's name
     *
     * @return 		String of weaponName.
     */
    public String getWeaponName() {
        return weaponName;
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
    public double getProjectilePower() {
        return projectilePower;
    }

    /**
     * Returns the minimum damage of the weapon.
     *
     * @return		double of minimum damage.
     */
    public double getMinDamage() {
        return minDamage;
    }

    /**
     * Returns the maximum damage of the weapon.
     *
     * @return		double of maximum damage.
     */
    public double getMaxDamage() {
        return maxDamage;
    }

    /**
     * Returns the riochet level of the projectile.
     *
     * @return		double of riochet level.
     */
    public double getRiochet() {
        return riochet;
    }

    /**
     * Returns the accuracy level of the weapon.
     *
     * @return		double of accuracy level.
     */
    public double getAccuracy() {
        return accuracy;
    }

    /**
     * Returns the chance of a projectile causing a critical hit.
     *
     * @return		double of critical hit chance.
     */
    public double getCriticalHit() {
        return criticalHit;
    }

    /**
     * Returns the rate at which the weapon turns.
     *
     * @return		double of weapon turn rate.
     */
    public double getWeaponTurnRate() {
        return weaponTurnRate;
    }
}