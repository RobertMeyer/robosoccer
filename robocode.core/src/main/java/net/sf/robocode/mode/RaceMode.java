package net.sf.robocode.mode;

/**
 * Mode class used to implement Racing Mode functional enhancement. See ticket #32
 * 
 * @author Team - GoGoRobotRacer
 * @author s4203648
 * @author s42008024
 */

public class RaceMode extends ClassicMode{

	//private class variables
	private int noLaps;
	
	/**
	 * Called from the GUI to set the number of laps in the race.
	 * <p>
	 * @param laps Number of laps in the race. 0 < noLaps.
	 * 
	 */
	public void setLaps(int laps){
		if(0 <= laps){
			throw new IllegalArgumentException("Number of laps must be positive");
		}else{
			this.noLaps = laps;
		}
		return;
	}
	
	public void execute(){
		System.out.println("Race Mode.");
	}
	
	//overwriting toString() method so our "mode" name is now returned
	public String toString(){
		return new String("Race Mode");
	}
	
	public String getDescription(){
		return new String("Race mode allows you to race your robot to be first across the finish line.");
	}
}