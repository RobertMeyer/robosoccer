package net.sf.robocode.mode;

public interface IMode {

	void execute();
	
	double modifyVelocity(double velocityIncrement);
	
	// MICROSLOTHS
	//public double modifyScanRadius(double radius);
	
	String toString();

}
