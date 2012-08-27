package net.sf.robocode.mode;

public class ClassicMode implements IMode {
	
	
	public void execute(){
		System.out.println("Classic Mode!!!");
	}

	public double modifyVelocity(double velocityIncrement) {
		return velocityIncrement;
		
	}
	
	public String toString() {
		return new String("Classic Mode");
	}
	
}
