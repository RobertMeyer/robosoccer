package net.sf.robocode.battle;

import java.awt.geom.Area;

//import net.sf.robocode.battle.Waypoint;

/**
 * Class that stores Track layout 
 * @author Anastasios Karydas s4231811
 *
 */
public class TrackField implements ITrackField {
	
	// Area of the road of the Track
	private Area road;
	//Area of the Terrain of the Track
	private Area terrain;
	// 
	private Waypoint waypoint;
	
	public TrackField(Area road, Area terrain) {
		this.road = road;
		this.terrain = terrain;
	}

	@Override
	public boolean onRoad(double x, double y) {
		// Check if point is in the bounds of the road shape.
		if (road.contains(x, y)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean onTerrain(double x, double y) {
		// Check if point is in the bounds of the terrain Area.
		if (terrain.contains(x, y)) {
			return true;
		} else {
			return false;
		}
	}

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

	@Override
	public void loadTrackField() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveTrackField() {
		// TODO Auto-generated method stub
		
	}

}
