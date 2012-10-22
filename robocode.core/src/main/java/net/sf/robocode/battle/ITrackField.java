package net.sf.robocode.battle;

import java.io.File;

import net.sf.robocode.battle.IWaypoint;

/**
 * This is the interface that will define the track layout.
 * The track layout is defined by two Area variables road and terrain.
 * Anything outside of the bounds of these areas are considered walls.
 * TrackField will also have waypoint objects that will be used to 
 * indicate the direction of travel through the race track and robot sstarting positions.
 * @author Anastasios Karydas 4231811
 */
public interface ITrackField {
	
	/**
	 * Gets the name of the Track
	 * @return The name of the track
	 */
	public String getName();
	
	/**
	 * Gets the list of waypoints for the track
	 * @return Returns waypoints of map
	 */
	public IWaypoint getWaypoints();
	
	/**
	 * Gets the list of spawnpoints for the track.
	 * @return Returns spawnpoints of track
	 */
	public IWaypoint getSpawnpoints();
	
	/**
	 * Get the laps of the race
	 * @return number of laps
	 */
	public int getLaps();
	
	/**
	 * Checks if a point is in the bounds of the road area
	 * @param x x co-ord
	 * @param y y co-ord
	 * @return Returns true if point is in road area else false
	 */
	public boolean onRoad(double x, double y);
	
	/**
	 * Check if point is on terrain area
	 * @param x x co-ord
	 * @param y y co-ord
	 * @return Returns true if point is in terrain area else false
	 */
	public boolean onTerrain(double x, double y);
	
	/**
	 * Checks if point is on either terrain or road
	 * @param x x co-ord
	 * @param y y co-ord
	 * @return Returns true if point is on bounds else false
	 */
	public boolean onBounds(double x, double y);
	
	/**
	 * loads the TRackField data from a formatted bitmap
	 * @param trackFile A bmp file representing the track
	 */
	public void loadTrackField(File trackFile);

	/**
	 * Method that prints the string representation of the track
	 * @return returns name of the track.
	 */
	public String toString();
	
}
