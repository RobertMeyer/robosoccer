package robocode;

import java.awt.Graphics2D;
import java.nio.ByteBuffer;

import net.sf.robocode.peer.IRobotStatics;
import net.sf.robocode.serialization.ISerializableHelper;
import net.sf.robocode.serialization.RbSerializer;
import robocode.robotinterfaces.IBasicEvents;
import robocode.robotinterfaces.IBasicRobot;

public class LandmineHitEvent extends Event {
	
	private static final long serialVersionUID = 1L;
    private final static int DEFAULT_PRIORITY = 50;
    private final String name;
    private final double energy;
    private Landmine landmine;
    
    
    public LandmineHitEvent(String name, double energy, Landmine landmine) {
        super();
        this.name = name;
        this.energy = energy;
        this.landmine = landmine;
    }
    
    public Landmine get() {
        return landmine;
    }
    
    public double getEnergy() {
        return energy;
    }
    
    @Deprecated
    public double getLife() {
        return energy;
    }
    
    public String getName() {
        return name;
    }
    
    @Deprecated
    public double getRobotLife() {
        return energy;
    }
    
    @Deprecated
    public String getRobotName() {
        return name;
    }
    
    @Override
    final int getDefaultPriority() {
        return DEFAULT_PRIORITY;
    }
    
    @Override
    final void dispatch(IBasicRobot robot, IRobotStatics statics, Graphics2D graphics) {
        IBasicEvents listener = robot.getBasicEventListener();

        if (listener != null) {
            listener.onLandmineHit(this);
        }
    }
    
    @Override
    byte getSerializationType() {
        return RbSerializer.BulletHitEvent_TYPE;
    }

    static ISerializableHelper createHiddenSerializer() {
        return new SerializableHelper();
    }

    private static class SerializableHelper implements ISerializableHelper {

        @Override
        public int sizeOf(RbSerializer serializer, Object object) {
            LandmineHitEvent obj = (LandmineHitEvent) object;

            return RbSerializer.SIZEOF_TYPEINFO + RbSerializer.SIZEOF_INT + serializer.sizeOf(obj.name)
                    + RbSerializer.SIZEOF_DOUBLE;
        }

        @Override
        public void serialize(RbSerializer serializer, ByteBuffer buffer, Object object) {
            LandmineHitEvent obj = (LandmineHitEvent) object;

            serializer.serialize(buffer, obj.landmine.getBulletId());
            serializer.serialize(buffer, obj.name);
            serializer.serialize(buffer, obj.energy);
        }

        @Override
        public Object deserialize(RbSerializer serializer, ByteBuffer buffer) {
            Landmine landmine = new Landmine(0, 0, 0, null, null, false, buffer.getInt());
            String name = serializer.deserializeString(buffer);
            double energy = buffer.getDouble();

            return new LandmineHitEvent(name, energy, landmine);
        }
    }

}
