package robocode;

import java.awt.Graphics2D;
import java.nio.ByteBuffer;

import net.sf.robocode.peer.IRobotStatics;
import net.sf.robocode.serialization.ISerializableHelper;
import net.sf.robocode.serialization.RbSerializer;
import robocode.robotinterfaces.IBasicEvents;
import robocode.robotinterfaces.IBasicRobot;

public class HitByLandmineEvent extends Event{
	
	 private static final long serialVersionUID = 1L;
	    private final static int DEFAULT_PRIORITY = 20;
	    private final double bearing;
	    private final Landmine landmine;

	    /**
	     * Called by the game to create a new HitByBulletEvent.
	     *
	     * @param bearing the bearing of the bullet that hit your robot, in radians
	     * @param bullet  the bullet that has hit your robot
	     */
	    public HitByLandmineEvent(double bearing, Landmine landmine) {
	        super();
	        this.bearing = bearing;
	        this.landmine = landmine;
	    }

	    /**
	     * Returns the bearing to the bullet, relative to your robot's heading,
	     * in degrees (-180 < getBearing() <= 180).
	     * <p/>
	     * If you were to turnRight(event.getBearing()), you would be facing the
	     * direction the bullet came from. The calculation used here is:
	     * (bullet's heading in degrees + 180) - (your heading in degrees)
	     *
	     * @return the bearing to the bullet, in degrees
	     */
	    public double getBearing() {
	        return bearing * 180.0 / Math.PI;
	    }

	    /**
	     * Returns the bearing to the bullet, relative to your robot's heading,
	     * in radians (-Math.PI < getBearingRadians() <= Math.PI).
	     * <p/>
	     * If you were to turnRightRadians(event.getBearingRadians()), you would be
	     * facing the direction the bullet came from. The calculation used here is:
	     * (bullet's heading in radians + Math.PI) - (your heading in radians)
	     *
	     * @return the bearing to the bullet, in radians
	     */
	    public double getBearingRadians() {
	        return bearing;
	    }

	    /**
	     * Returns the bullet that hit your robot.
	     *
	     * @return the bullet that hit your robot
	     */
	    public Landmine getLandmine() {
	        return landmine;
	    }

	    /**
	     * Returns the name of the robot that fired the bullet.
	     *
	     * @return the name of the robot that fired the bullet
	     */
	    public String getName() {
	        return landmine.getName();
	    }

	    /**
	     * Returns the power of this bullet. The damage you take (in fact, already
	     * took) is 4 * power, plus 2 * (power-1) if power > 1. The robot that fired
	     * the bullet receives 3 * power back.
	     *
	     * @return the power of the bullet
	     */
	    public double getPower() {
	        return landmine.getPower();
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
	            listener.onHitByLandmine(this);
	        }
	    }

	    /**
	     * {@inheritDoc}
	     */
	    @Override
	    byte getSerializationType() {
	        return RbSerializer.HitByLandmineEvent_TYPE;
	    }

	    static ISerializableHelper createHiddenSerializer() {
	        return new SerializableHelper();
	    }

	    private static class SerializableHelper implements ISerializableHelper {

	        @Override
	        public int sizeOf(RbSerializer serializer, Object object) {
	            HitByLandmineEvent obj = (HitByLandmineEvent) object;

	            return RbSerializer.SIZEOF_TYPEINFO + serializer.sizeOf(RbSerializer.Landmine_TYPE, obj.landmine)
	                    + RbSerializer.SIZEOF_DOUBLE;
	        }

	        @Override
	        public void serialize(RbSerializer serializer, ByteBuffer buffer, Object object) {
	            HitByLandmineEvent obj = (HitByLandmineEvent) object;

	            serializer.serialize(buffer, RbSerializer.Landmine_TYPE, obj.landmine);
	            serializer.serialize(buffer, obj.bearing);
	        }

	        @Override
	        public Object deserialize(RbSerializer serializer, ByteBuffer buffer) {
	            Landmine landmine = (Landmine) serializer.deserializeAny(buffer);
	            double bearing = buffer.getDouble();

	            return new HitByLandmineEvent(bearing, landmine);
	        }
	    }

}
