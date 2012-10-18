/*******************************************************************************
 *Team GoGoGadgetRacer
 *
 *S4203638
 *
 *******************************************************************************/
package robocode;

import java.awt.*;
import java.nio.ByteBuffer;
import net.sf.robocode.peer.IRobotStatics;
import net.sf.robocode.serialization.ISerializableHelper;
import net.sf.robocode.serialization.RbSerializer;
import robocode.Event;
import robocode.WaypointPassedEvent;
import robocode.robotinterfaces.IBasicEvents;
import robocode.robotinterfaces.IBasicRobot;

/**
 * A WayPointPassedEvent is sent to a robot when it passes a waypoint
 * the user can use the information in this to start traveling towards 
 * the next waypoint and hence continue along the racetrack. 
 * 
 *
 * @author Daniel Bowden - S4203648
 */
public class WaypointPassedEvent extends Event {

	private static final long serialVersionUID = 1L;
    private final static int DEFAULT_PRIORITY = 10;
	private final int nextWaypointIndex;
	private final double wayPointX;
	private final double wayPointY;
	private final double bearing;
	private final double distance;
	private final double distToWay;


	public WaypointPassedEvent(int nextWaypointIndex, double wayPointX, double wayPointY, double bearing, 
			double distance, double distToWay){
		super();
		this.nextWaypointIndex = nextWaypointIndex;
		this.wayPointX = wayPointX;
		this.wayPointY = wayPointY;
		this.bearing = bearing;
		this.distance = distance;
		this.distToWay = distToWay;

	}

	/**
	 * Returns the x co-ordinate of the next waypoint.
	 * 
	 * @return the x co-ordinate of the next waypoint.
	 */
	public double getWaypointX(){
		return wayPointX;
	}

	/**
	 * Returns the y co-ordinate of the next waypoint.
	 * 
	 * @return the y co-ordinate of the next waypoint.
	 */
	public double getWaypointY(){
		return wayPointY;
	}

	/**
	 * Returns the index of the next waypoint.
	 * 
	 * @return the index of the next waypoint.
	 */
	public int getIndex(){
		return nextWaypointIndex;
	}

	/**
	 * Returns the bearing to the next way point in radians. (Relative to your robot's heading)
	 * 
	 * @return the bearing to the next way point in radians.
	 */
	public double getBearingRadians(){
		return bearing;
	}

	/**
	 * Returns the bearing to the next waypoint in degrees. (Relative to your robot's heading)
	 * 
	 * @return the bearing to the next waypoint in degrees.
	 */
	public double getBearingDegrees(){
		return bearing * 180.0 / Math.PI;
	}

	/**
	 * Returns the distance from the robot to the waypoint that has just been passsed. (From Robot's centre)
	 * 
	 * @return the distance from the robot to the waypoint that has just been passsed.
	 */
	public double getDistToLastWaypoint(){
		return distToWay;
	}

	/**
	 * Returns the distance from the robot to the next waypoint. (From Robot's centre)
	 * 
	 * @return the distance from the robot to the next waypoint.
	 */
	public double getDistToNextWaypoint(){
		return distance;
	}

	  /**
     * {@inheritDoc}
     */
    @Override
    public final int compareTo(Event event) {
        final int res = super.compareTo(event);

        if (res != 0) {
            return res;
        }
        // Compare the distance, if the events are WaypointPassedEvent's
        // The shorter distance to the robot, the higher priority
        if (event instanceof WaypointPassedEvent) {
            return (int) (this.getDistToLastWaypoint() - ((WaypointPassedEvent) event).getDistToLastWaypoint());
        }
        // No difference found
        return 0;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    final int getDefaultPriority() {
        return DEFAULT_PRIORITY;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    final void dispatch(IBasicRobot robot, IRobotStatics statics, Graphics2D graphics) {
        IBasicEvents listener = robot.getBasicEventListener();

        if (listener != null) {
            listener.onWaypointPassed(this);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    byte getSerializationType() {
        return RbSerializer.WaypointPassedEvent_TYPE;
    }

    static ISerializableHelper createHiddenSerializer() {
        return new SerializableHelper();
    }

    private static class SerializableHelper implements ISerializableHelper {

        @Override
        public int sizeOf(RbSerializer serializer, Object object) {
            return RbSerializer.SIZEOF_TYPEINFO + 5 * RbSerializer.SIZEOF_DOUBLE + RbSerializer.SIZEOF_INT;
        }

        @Override
        public void serialize(RbSerializer serializer, ByteBuffer buffer, Object object) {
        	WaypointPassedEvent obj = (WaypointPassedEvent) object;

            serializer.serialize(buffer, obj.nextWaypointIndex);
            serializer.serialize(buffer, obj.wayPointX);
            serializer.serialize(buffer, obj.wayPointY);
            serializer.serialize(buffer, obj.bearing);
            serializer.serialize(buffer, obj.distance);
            serializer.serialize(buffer, obj.distToWay);
        }

        @Override
        public Object deserialize(RbSerializer serializer, ByteBuffer buffer) {
            int nextWaypointIndex = buffer.getInt();
            double wayPointX = buffer.getDouble();
            double wayPointY = buffer.getDouble();
            double bearing = buffer.getDouble();
            double distance = buffer.getDouble();
            double distToWay = buffer.getDouble();

            return new WaypointPassedEvent(nextWaypointIndex, wayPointX, wayPointY, bearing, distance, 
            		distToWay);
        }
    }
    
}