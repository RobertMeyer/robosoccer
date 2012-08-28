package net.sf.robocode.mode;

public class SlowMode extends ClassicMode {
	
	public double modifyVelocity(double velocityIncrement) {
		return velocityIncrement/2;
	}
	
	public String toString() {
		return new String("Slow Mode");
	}
	
	public String getDescription() {
		return new String("Robots move at half speed.");
	}
}
