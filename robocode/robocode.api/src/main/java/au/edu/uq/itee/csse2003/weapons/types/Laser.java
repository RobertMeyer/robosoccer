package au.edu.uq.itee.csse2003.weapons.types;

import au.edu.uq.itee.csse2003.weapons.WeaponType;

/**
 * A laser, a futuristic weapon..
 */
public class Laser extends WeaponType {

    private String weaponName = "Laser";
    private double coolDown = 0.2;
    private double minDamage = 0.1;
    private double maxDamage = 3;
    private double riochet = 0.9;
    private double accuracy = 0.85;
    private double criticalHit = 0.1;
    private double weaponTurnRate = 20;
}