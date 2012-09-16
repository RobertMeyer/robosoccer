package robocode;

import java.util.ArrayList;

/**
 * Object for a path that a robot can take.
 * When getters are called, the pointer moves to the next point in the path so that next() and prev() can be called continuously.
 * Exception to pointer moving is current().
 * @author Laurence McLean 42373414
 * @see HouseRobot
 */
public class RobotPath {
	private ArrayList<PathPoint> path;
	private int current;
	private boolean cycle;
	
	/**
	 * Constructor for a Robot Path
	 * Defaults to empty path with a cycle.
	 */
	public RobotPath() {
		this.path = new ArrayList<PathPoint>();
		this.current = 0;
		this.cycle = true;
	}
	
	/**
	 * Constructor for a Robot Path
	 * @param path An ArrayList of {@link PathPoint}s for the robot to 
	 * @param cycle If the path is a cycle. True will result in the first PathPoint being returned after the last,
	 * 		false will result in null being returned after the last.
	 * @see PathPoint
	 */
	public RobotPath(ArrayList<PathPoint> path, boolean cycle) {
		path = new ArrayList<PathPoint>();
		for(int i = 0; i<path.size(); i++) {
			this.path.set(i, path.get(i));
		}
		this.current = 0;
		this.cycle = cycle;
	}
	
	/**
	 * Adds a point onto the end of the path.
	 * @param p Point to add onto the end of a path.
	 */
	public void addPoint(PathPoint p) {
		path.add(p);
	}
	
	/**
	 * Set the pointer to the first point in this path and return it.
	 * @return First point in the Path.
	 * @see next()
	 */
	public PathPoint first() {
		current = 0;
		return next();
	}
	
	/**
	 * Set the pointer to the last point in this path and return it.
	 * @return Last point in the Path.
	 * @see next()
	 */
	public PathPoint last() {
		current = path.size()-1;
		return next();
	}
	
	/**
	 * Get the next point in the path, and increment the pointer in the path.
	 * @return null if the RobotPath has no PathPoints. null if this is a cyclical RobotPath and we were already at the end. Otherwise, return the PathPoint and increment the pointer.
	 */
	public PathPoint next() {
		if(path.size()>0) {
			if(current==path.size()) {
				if(cycle) current = 0;
				else return null;
			}
			return path.get(current++);
		} else {
			return null;
		}
	}
	
	/**
	 * Get the previous point in the path, and decrement the pointer in the path.
	 * @return null if the RobotPath has no PathPoints. null if this is a cyclical RobotPath and we were already at the beginning. Otherwise, return the PathPoint and decrement the pointer.
	 */
	public PathPoint prev() {
		if(path.size()>0) {
			if(current==-1) {
				if(cycle) current = path.size()-1;
				else return null;
			}
			return path.get(current--);
		} else {
			return null;
		}
	}
	
	/**
	 * Get the current point in the path, without changing the current value.
	 * @return PathPoint of the current point in the RobotPath.
	 */
	public PathPoint current() {
		if(path.size()>0) {
			return path.get(current);
		} else {
			return null;
		}
	}

}
