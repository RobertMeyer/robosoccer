package net.sf.robocode.mode;

public class ModeContext {
	
	private IMode currentMode;
	
	public void setMode(IMode mode){
		this.currentMode = mode;
	}
	
	public void execute() {
		currentMode.execute();
	}
	
	public double modifyVelocity(double velocityIncrement){
		return currentMode.modifyVelocity(velocityIncrement);
	}
}
