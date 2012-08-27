package net.sf.robocode.mode;

public interface IMode {

	void execute();
	
	double modifyVelocity(double velocityIncrement);
	
	String toString();

}
