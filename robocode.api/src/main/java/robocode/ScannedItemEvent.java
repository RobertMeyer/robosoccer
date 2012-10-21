package robocode;

import java.awt.*;
import java.nio.ByteBuffer;

import net.sf.robocode.peer.IRobotStatics;
import net.sf.robocode.serialization.ISerializableHelper;
import net.sf.robocode.serialization.RbSerializer;
import robocode.robotinterfaces.IBasicRobot;
import robocode.robotinterfaces.IItemEvents;
import robocode.robotinterfaces.IItemRobot;

/**
 * A ScannedItemEvent is sent to {@link Robot#onScannedItem(ScannedItemEvent) onScannedItem()}
 * when your robot scans an item.
 * You can use the information contained in this event to determine what to do.
 *
 * @author Ameer Sabri (Dream Team)
 */
public class ScannedItemEvent extends Event {
	
	private static final long serialVersionUID = 2L;
	private final static int DEFAULT_PRIORITY = 20;
	private final String itemName;
	private final String robotName;
	private final double distance;
	private final double bearing;
	private final double x;
	private final double y;
	
	/**
	 * New ScannedItemEvent
	 * @param itemName the name of the item scanned
	 * @param robotName the name of the robot carrying the item scanned
	 * @param distance the distance the robot is from the item scanned
	 * @param bearing the bearing of the item scanned
	 * @param x the x-coordinate of the item scanned
	 * @param y the y-coordinate of the item scanned
	 */
	public ScannedItemEvent(String itemName, String robotName, double distance, double bearing, double x, double y) {
		this.itemName = itemName;
		this.robotName = robotName;
		this.distance = distance;
		this.bearing = bearing;
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Get the name of the item scanned
	 * @return String representation of the name of the item
	 */
	public String getItemName() {
		return this.itemName;
	}
	
	/**
	 * Get the Robot's name holding the item
	 * @return The Robot's name holding the item OR null if none
	 */
	public String getRobotName() {
		return this.robotName;
	}
	
	/**
	 * Get the distance to the scanned item
	 * @return the distance the item is from the robot
	 */
	public double getDistance() {
		return this.distance;
	}
	
	/**
	 * Get the bearing of the scanned item.
	 * @return the bearing of the scanned item
	 */
	public double getBearing() {
		return this.bearing;
	}
	
	/**
	 * Get the x location of the item
	 * @return x location of item
	 */
	public double getX() {
		return this.x;
	}
	
	/**
	 * Get the y location of the item
	 * @return y location of item
	 */
	public double getY() {
		return this.y;
	}
	
	/**
	 * Get the current priority of the event
	 * @return the priority of the event
	 */
	public int getPriority() {
		return DEFAULT_PRIORITY;
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
        /* Compare the distance, if the events are ScannedItemEvents. 
         * The shorter the distance to the robot, the higher the priority.
         */
        if (event instanceof ScannedItemEvent) {
            return (int) (this.getDistance() - ((ScannedItemEvent) event).getDistance());
        }
        // No difference found
        return 0;
    }
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	final void dispatch(IBasicRobot robot, IRobotStatics statics, Graphics2D graphics) {
		IItemEvents listener = ((IItemRobot) robot).getItemEventListener();

		if (listener != null) {
			listener.onScannedItem(this);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	byte getSerializationType() {
		return RbSerializer.ScannedItemEvent_TYPE;
	}

	static ISerializableHelper createHiddenSerializer() {
		return new SerializableHelper();
	}

	private static class SerializableHelper implements ISerializableHelper {
        @Override
		public int sizeOf(RbSerializer serializer, Object object) {
			ScannedItemEvent obj = (ScannedItemEvent) object;

			return RbSerializer.SIZEOF_TYPEINFO + serializer.sizeOf(obj.itemName) +
					serializer.sizeOf(obj.robotName) + 4 * RbSerializer.SIZEOF_DOUBLE;
		}

        @Override
		public void serialize(RbSerializer serializer, ByteBuffer buffer, Object object) {
			ScannedItemEvent obj = (ScannedItemEvent) object;

			serializer.serialize(buffer, obj.itemName);
			serializer.serialize(buffer, obj.robotName);
			serializer.serialize(buffer, obj.distance);
			serializer.serialize(buffer, obj.bearing);
			serializer.serialize(buffer, obj.x);
			serializer.serialize(buffer, obj.y);
		}

        @Override
		public Object deserialize(RbSerializer serializer, ByteBuffer buffer) {
			String itemName = serializer.deserializeString(buffer);
			String robotName = serializer.deserializeString(buffer);
			double distance = buffer.getDouble();
			double bearing = buffer.getDouble();
			double x = buffer.getDouble();
			double y = buffer.getDouble();

			return new ScannedItemEvent(itemName, robotName, distance, bearing, x, y);
		}
	}
}
