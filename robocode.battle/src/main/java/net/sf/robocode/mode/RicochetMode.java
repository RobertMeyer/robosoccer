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
//	@Override
	public boolean shouldRicochet() {
		return true;
	}
}
