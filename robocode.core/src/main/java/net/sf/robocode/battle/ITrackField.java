package net.sf.robocode.battle;

/**
 * This is the interface that will define the trackField layout.
 * The track layout is defined by two Area variables road and terrain.
 * Anything outside of the bounds of these areas are considered walls.
 * TrackField will also have a "" of waypoint objects that will be used to 
 * indicate the direction of travel through the race track.
 * @author Anastasios Karydas 4231811
 */
public interface ITrackField {
	
	
	
	/**
	 * Checks if a point is in the bounds of the road area
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean onRoad(double x, double y);
	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean onTerrain(double x, double y);
	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean onBounds(double x, double y);
	/**
	 * 
	 */
	public void loadTrackField();
	/**
	 * 
	 */
	public void saveTrackField();
	/**
	 * 
	 * @return
	 */
	public String toString();
	
}
