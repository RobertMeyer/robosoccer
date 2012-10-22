
package net.sf.robocode.battle;

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Area;

import net.sf.robocode.battle.item.BoundingRectangle;

/**
 * Class that stores Track Data
 * @author Taso s4231811
 *
 */
public class TrackField {
	
	// Area of the road of the Track
	private Area road;
	//Area of the Terrain of the Track
	private Area terrain;

	public TrackField(Shape field) {
		this.road = new Area(field);
		this.terrain = new Area(field);
	}
	
	/**
	 * 
	 * @param br
	 */
	public TrackField(BoundingRectangle br) {
		this.road = new Area(br);
		this.terrain = new Area(br);
	}
	
	public TrackField(Area road, Area terrain) {
		this.road = road;
		this.terrain = terrain;
	}
	
	//TODO
	/**
	 * 
	 * @return
	 */
	public Boolean onRoad() {
		return false;
	}
	
	//TODO
	/**
	 * 
	 * @return
	 */
	public Boolean onTerrain() {
		return false;
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public Boolean onBounds(int x, int y) {
		Point p = new Point(x,y);
		return onBounds(p);
	}
	
	/**
	 * 
	 * @param p
	 * @return
	 */
	public Boolean onBounds(Point p) {
		// Check if point is in the bounds of the road shape.
		if (road.contains(p)) {
			return true;
		}
		// Check if point is in the bounds of the terrain shape.
		if (terrain.contains(p)) {
			return true;
		}
		// if reaches here return false.
		return false;
	}
	
	
	
	//TODO Field saving method
	/**
	 * 
	 */
	public void saveField() {
		
	}
	
	//TODO Field Loading method
	/**
	 * 
	 */
	public void loadField() {
		
	}

}
