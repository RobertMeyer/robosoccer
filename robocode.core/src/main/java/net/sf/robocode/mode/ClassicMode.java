package net.sf.robocode.mode;

public class ClassicMode implements IMode {
	
	
	public void execute(){
		System.out.println("Classic Mode!!!");
	}

	public double modifyVelocity(double velocityIncrement) {
		return velocityIncrement;
		
	}
	
	// MICROSLOTHS
	// public double modifyScanRadius(double radius) {
	// 	return radius;
	// }
	
	public String toString() {
		return new String("Classic Mode");
	}
	
}
