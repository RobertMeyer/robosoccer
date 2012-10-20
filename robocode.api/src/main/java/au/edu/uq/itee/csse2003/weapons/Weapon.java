/******************************************************************************
 * Copyright (c) 2012 The Fightin' Mongooses team.
 *
 * Contributors:
 *    Paul Wade
 *    Chris Irving
 *    Jesse Claven
******************************************************************************/

package au.edu.uq.itee.csse2003.weapons;

/**
 * Represents a weapon.
 *
 * @author The Fightin' Moongooses
 * @deprecated This feature has temporarily been postponed.
 */
public class Weapon {

    private final String weaponName;
    private final double coolDown;
    private final double projectilePower;
    private final double minDamage;
    private final double maxDamage;
    private final double riochet;
    private final double accuracy;
    private final double criticalHit;
    private final double weaponTurnRate;

    public Weapon(WeaponType w) {
        weaponName = w.getWeaponName();
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
