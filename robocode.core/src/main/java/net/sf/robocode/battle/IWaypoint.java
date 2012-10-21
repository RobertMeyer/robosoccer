package net.sf.robocode.battle;


/**
 * This is the interface that will define the Waypoint Object.
 *
 * @author Daniel Bowden 42036483
 */
public interface IWaypoint {
	
	public void addSingleWaypoint(double x, double y);
	
	public double getSingleWaypointX(int SingleWaypointIndex);
	
	public double getSingleWaypointY(int SingleWaypointIndex);
	
	public void popSingleWaypoint(int SingleWaypointIndex);
	
	public int getNoWaypoints();
	
	public String toString();
	
}
