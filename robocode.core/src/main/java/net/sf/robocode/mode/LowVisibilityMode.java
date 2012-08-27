package net.sf.robocode.mode;

public class LowVisibilityMode extends ClassicMode {
	
	// MICROSLOTHS
	// public double modifyScanRadius(double radius) {
	// 	return radius/2;
	// }
	
	public void execute() {
		System.out.println("Low Visibility Mode");
	}
	
	public String toString() {
		return new String("Low Visibility Mode");
	}
	
}
