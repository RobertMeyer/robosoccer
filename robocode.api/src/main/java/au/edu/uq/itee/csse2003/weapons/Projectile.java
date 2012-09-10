package au.edu.uq.itee.csse2003.weapons;

/**
 * Represents a singular projectile fired from a weapon. Many projectiles can be
 * fired and tracked.
 */
public class Projectile {
	/**
	 * @param 
	 */
	public double heading, x, y, power, projectileID;
	public String ownerName, victimName;
	public boolean status;
	
	/**
	 * 
	 */
	public void updateProjectile(double x, double y, String victimName, 
							   boolean status) {
		this.x = x;
		this.y = y;
		this.victimName = victimName;
		this.status = status;
	}
}
