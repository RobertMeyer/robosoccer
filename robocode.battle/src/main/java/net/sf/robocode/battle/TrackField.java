package net.sf.robocode.battle;

import java.awt.geom.Area;
import java.awt.image.PixelGrabber;
import java.io.File;

//import net.sf.robocode.battle.Waypoint;

/**
 * Class that stores Track layout 
 * @author Anastasios Karydas s4231811
 */
public class TrackField implements ITrackField {
	
	//Track Name
	private String name;
	//Track Waypoints.
	private Waypoint waypoints;
	//Track Starting positions.
	private Waypoint spawnpoints;
	//Number of trackLaps
	private int laps;
	//Area of the road of the Track
	private Area road;
	//Area of the Terrain of the Track
	private Area terrain;
	
	/**
	 * 
	 * @param trackFile
	 */
	public TrackField(File trackFile) {
		loadTrackField(trackFile);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return this.name;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public IWaypoint getWaypoints() {
		return this.waypoints;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public IWaypoint getSpawnpoints() {
		return this.spawnpoints;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getLaps() {
		return this.laps;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onRoad(double x, double y) {
		// Check if point is in the bounds of the road shape.
		if (road.contains(x, y)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onTerrain(double x, double y) {
		// Check if point is in the bounds of the terrain Area.
		if (terrain.contains(x, y)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onBounds(double x, double y) {
		// Check if point is in the bounds of both the areas
		// if returns false the robot has hit a wall.
		if (onRoad(x, y) || onTerrain(x, y)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void loadTrackField(File trackFile) {
	}
	
	@Override
	public String toString(){
		return this.name + road.toString() + terrain.toString();
	}

}
