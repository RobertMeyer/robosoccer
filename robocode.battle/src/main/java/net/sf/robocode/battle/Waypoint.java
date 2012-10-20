package net.sf.robocode.battle;

/**
 * A waypoint object designed specifically for the RaceMode 
 * Modification as per Functional Enhancement Ticket #32.
 * Used as a "guide" for robots to follow the track.
 * 
 * @author Team - GoGoRobotRacer
 * @author s4203648
 *
 */

import java.util.*;

public class Waypoint {
	ArrayList<SingleWaypoint> wayPoints = new ArrayList<SingleWaypoint>();

	/**
	 * A WayPoint cannot be empty and must contain at least one SingleWaypoint
	 * when being initialised
	 * 
	 * @param x The x co-ordinate of the first SingleWaypoint.
	 * @param y The y co-ordinate of the first SingleWaypoint.
	 */
	Waypoint(double x, double y){
		wayPoints.add(new SingleWaypoint(x, y));
	}

	public void addSingleWaypoint(double x, double y){
		wayPoints.add(new SingleWaypoint(x, y));
	}

	public double getSingleWaypointX(int SingleWaypointIndex){
		return wayPoints.get(SingleWaypointIndex).getXLocation();
	}

	public double getSingleWaypointY(int SingleWaypointIndex){
		return wayPoints.get(SingleWaypointIndex).getYLocation();
	}

	//class used to store x & y co-ordinates of each waypoint.
	public class SingleWaypoint{
		private double xLocation;
		private double yLocation;


		SingleWaypoint(double x, double y){
			this.xLocation = x;
			this.yLocation = y;
		}

		public double getXLocation(){
			return xLocation;
		}

		public double getYLocation(){
			return yLocation;
		}

	}
}
