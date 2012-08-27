package net.sf.robocode.mode;

public class ModeContext {
	
	private IMode currentMode;
	
	public void setMode(IMode mode){
		this.currentMode = mode;
	}
	
	
	public double modifyVelocity(double velocityIncrement){
		return currentMode.modifyVelocity(velocityIncrement);
	}
	
	
}
