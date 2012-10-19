package robocode;

import java.awt.Graphics2D;
import java.nio.ByteBuffer;

import net.sf.robocode.peer.IRobotStatics;
import net.sf.robocode.serialization.ISerializableHelper;
import net.sf.robocode.serialization.RbSerializer;
import robocode.Event;
import robocode.robotinterfaces.IBasicEvents;
import robocode.robotinterfaces.IBasicRobot;

public class RobotFrozenEvent extends Event {
	
	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_PRIORITY = 80;
	
	/** 
	 * Called by game to create new RobotFrozenEvent
	 * 
	 */
	
	public RobotFrozenEvent() {
		super();
	}
	
	/**
	 * {@inheritDoc}
	 */
	
	public final int getPriority(){
		return DEFAULT_PRIORITY;
	}
	
	/**
	 * {@inheritDoc}
	 */
	final int getDefaultPriority() {
		return DEFAULT_PRIORITY;
	}
	
	/**
	 * {@inheritDoc}
	 */
	final void dispatch(IBasicRobot robot, IRobotStatics statics, Graphics2D graphics) {
		IBasicEvents listener = robot.getBasicEventListener();
		
		if(listener != null){
			listener.onRobotFrozen(this);
		}
	}
	
	/**
     * {@inheritDoc}
     */
    byte getSerializationType() {
        return RbSerializer.RobotFrozenEvent_TYPE;
    }

    static ISerializableHelper createHiddenSerializer() {
        return new SerializableHelper();
    }

    private static class SerializableHelper implements ISerializableHelper {

        @Override
        public int sizeOf(RbSerializer serializer, Object object) {
            return RbSerializer.SIZEOF_TYPEINFO;
        }

        @Override
        public void serialize(RbSerializer serializer, ByteBuffer buffer, Object object) {
        }

        @Override
        public Object deserialize(RbSerializer serializer, ByteBuffer buffer) {
            return new RobotFrozenEvent();
        }
    }
}