package net.sf.robocode.mode;

public class RicochetMode extends ClassicMode {

	private final String description = "Ricochet Mode: WATCH THE WALLS";

	public void execute() {
		System.out.println("Ricochet Mode.");
	}

	public String toString() {
		return new String("Ricochet Mode");
	}

	public String getDescription() {
		return description;
	}

	/**
	 * Will check if a projectile should ricochet and return modified values
	 * accordingly, currently just returns true
	 */
	@Override
	public boolean shouldRicochet(double power, double minBulletPower) {
		if (power / 2 >= minBulletPower) {
			// only ricochet if the bullet still meets the minBulletPower rule
			// after power is halved
			return true;
		} else {
			return false;
		}
	}
}
