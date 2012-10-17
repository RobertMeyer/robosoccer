package robocode;

import java.awt.Graphics2D;
import java.nio.ByteBuffer;

import net.sf.robocode.peer.IRobotStatics;
import net.sf.robocode.serialization.ISerializableHelper;
import net.sf.robocode.serialization.RbSerializer;
import robocode.robotinterfaces.IBasicRobot;

/**
 * ScannedItemEvent:
 *  Received when a robot scans the item, whether it is captured or not
 *  Returns a ScannedRobotEvent directly after this IF a robot has the item
 *  Tells the robot the location of the item, and the name of robot
 *  that is holding it
 *
 * @author team-Telos
 * 
 * Contributors:
 * 
 * @author Ameer Sabri (Dream Team) (serializables)
 *
 */
public class ScannedItemEvent extends Event {
	/* Serial */
	private static final long serialVersionUID = 2L;
	/** Can be simplified if we have access to ItemDrop here */
	
	/* String representation of the item */
	private final String item;
	/* Robot's name carrying the item */
	private final String robotName;
	/* x-location of the item */
	private final int x;
	/* y-location of the item */
	private final int y;
	/*Distance variable*/
	private final double distance;
	/* Default priority for the event. ScannedRobot is 10...*/
	private final static int DEFAULT_PRIORITY = 20;
	
	/**
	 * New ScannedItemEvent
	 * @param item String representation of the item
	 * @param robotName Robot's name carrying the item
	 * @param distance - the distance the robot is from the item
	 * @param x x-location of the item
	 * @param y y-location of the item
	 */
	ScannedItemEvent(String item, String robotName, double distance, int x, 
			int y) {
		this.item = item;
		this.robotName = robotName;
		this.distance = distance;
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Get the name of the item scanned
	 * @return String representation of the name of the item
	 */
	public String getItemName() {
		return this.item;
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
	 * Get the x location of the item
	 * @return x location of item
	 */
	public int getX() {
		return this.x;
	}
	
	/**
	 * Get the y location of the item
	 * @return y location of item
	 */
	public int getY() {
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
	 * set the priority for the event. Each item may have a different
	 * priority so this method will be used to change each items priority.
	 * TODO: Maybe change this bit, cos i don't know exactly how right it is.
	 * Event has a setPriority method that is final, so it can't be overriden,
	 * but we should be able to set the priority for each item.
	 * @param priority
	 */
	public void priority(int priority) {
		super.setPriority(priority);
	}
	
	/**
	 * 
	 * @param event
	 * @return
	 */
	public final int compareTo(Event event) {
        final int res = super.compareTo(event);

        if (res != 0) {
            return res;
        }
        // Compare the distance, if the events are ScannedRobotEvents
        // The shorter distance to the robot, the higher priority
        if (event instanceof ScannedRobotEvent) {
            return (int) (this.getDistance() - ((ScannedRobotEvent) event).getDistance());
        }
        // No difference found
        return 0;
    }
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	final void dispatch(IBasicRobot robot, IRobotStatics statics, Graphics2D graphics) {
		
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

            return RbSerializer.SIZEOF_TYPEINFO + serializer.sizeOf(obj.item) + serializer.sizeOf(obj.robotName)
                    + RbSerializer.SIZEOF_DOUBLE + 2 * RbSerializer.SIZEOF_INT;
        }
		
		@Override
        public void serialize(RbSerializer serializer, ByteBuffer buffer, Object object) {
            ScannedItemEvent obj = (ScannedItemEvent) object;

            serializer.serialize(buffer, obj.item);
            serializer.serialize(buffer, obj.robotName);
            serializer.serialize(buffer, obj.distance);
            serializer.serialize(buffer, obj.x);
            serializer.serialize(buffer, obj.y);
        }
		
		@Override
        public Object deserialize(RbSerializer serializer, ByteBuffer buffer) {
            String item = serializer.deserializeString(buffer);
            String robotName = serializer.deserializeString(buffer);
            double distance = serializer.deserializeDouble(buffer);
            int x = serializer.deserializeInt(buffer);
            int y = serializer.deserializeInt(buffer);

            return new ScannedItemEvent(item, robotName, distance, x, y);
		}
	}
	
}
