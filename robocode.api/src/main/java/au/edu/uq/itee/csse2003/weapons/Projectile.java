package au.edu.uq.itee.csse2003.weapons;

/**
 * Represents a singular projectile fired from a weapon. Many projectiles can be
 * fired and tracked.
 */
public class Projectile {
	public double heading, x, y, power;
	public int projectileID;
	public String ownerName, victimName;
	public boolean status;
	public WeaponType projectileSource;

	/**
	 * Called to create a new {@code Projectile} object.
	 * Structure and wording inspired from Bullet.java.
	 * 
	 * @param heading						heading of the proejctile.
	 * @param x									starting x position of the projectile.
	 * @param y									starting y position of the projectile.
	 * @param power							chosen power of the projectile.
	 * @param projectileID			unique ID of the projectile.
	 * @param ownerName					name of the owner of the projectile.
	 * @param victimName				name of the robot hit by the projectile.
	 * @param status						{@code true} if the projectile is still in motion;
	 *													{@code false} otherwise.
	 * @param projectileSource	type of weapon the projectile was fired from.
	 */
	public Projectile(double heading, double x, double y, double power,
									  int projectileID, String ownerName, String victimName,
									  boolean status, WeaponType projectileSource) {
		this.heading = heading;
		this.x = x;
		this.y = y;
		this.power = power;
		this.projectileID = projectileID;
		this.ownerName = ownerName;
		this.victimName = victimName;
		this.status = status;
		this.projectileSource = projectileSource;
	}

	@Override
	public String toString() {
		// TODO Finish this.
		String result = null;
		
		return result;
	}
	
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

	/**************************************************************************
	 * GETTERS
	 *************************************************************************/

	/**
	* @return			double of current projectile heading.
	*/
	public double getHeading() { return heading; }

	/**
	* @return			double of current projectile x position.
	*/
	public double getXPosition() { return x; }
	
	/**
	* @return			double of current projectile y position.
	*/
	public double getYPosition() { return y; }
	
	/**
	* @return			double of projectile power.
	*/
	public double getPower() { return power; }

	/**
	* @return			int of projectile ID.
	*/
	public int getProjectileID() { return projectileID; }
	
	/**
	* @return			String of projectile's owner.
	*/
	public String getProjectileOwner() { return ownerName; }

	/**
	* @return			String of projectile's victim.
	*/
	public String getProjectileVictim() { return victimName; }

	/**
	* @return			boolean of projectile's status.
	*/
	public boolean getProjectileStatus() { return status; }

	/**
	* @return			String of projectile's source.
	*/
	public WeaponType getProjectileSource() { return projectileSource; }
}
