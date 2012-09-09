package robocode;

/**
 * A point along a path that a robot can take.
 * @author Laurence McLean 42373414
 *
 */
public class PathPoint {
	private double x;
	private double y;
	private double heading;
	
	/**
	 * Creates a point as part of a path that a robot can take.
	 * @param x X co-ordinate of the point
	 * @param y Y co-ordinate of the point
	 * @param heading Heading that the robot should face at the point
	 * @see RobotPath
	 */
	public PathPoint(double x, double y, double heading) {
		this.x = x;
		this.y = y;
		this.heading = heading;
	}
	
	/**
	 * Gets the X co-ordinate of the point
	 * @return X co-ordinate of the point
	 */
	public double getX() {
		return this.x;
	}
	
	/**
	 * Gets the Y co-ordinate of the point
	 * @return Y co-ordinate of the point
	 */
	public double getY() {
		return this.y;
	}
	
	/**
	 * Gets the heading that the robot should face at the point
	 * @return Heading that the robot should face at the point
	 */
	public double getHeading() {
		return this.heading;
	}
}
