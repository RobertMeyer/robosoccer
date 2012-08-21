package net.sf.robocode.mode;

public class SlowMode implements IMode {
	public double modifyVelocity(double velocityIncrement) {
		return velocityIncrement/2;
		
	}

	public void execute() {
		System.out.println("SLow MODE");
	}
}
