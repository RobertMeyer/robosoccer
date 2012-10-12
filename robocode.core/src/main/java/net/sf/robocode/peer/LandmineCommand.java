package net.sf.robocode.peer;

import java.nio.ByteBuffer;
import net.sf.robocode.serialization.ISerializableHelper;
import net.sf.robocode.serialization.RbSerializer;
import java.io.Serializable;

public class LandmineCommand implements Serializable {

	 private final double power;
	    private final boolean fireAssistValid;
	    private final int landmineId;
	public LandmineCommand(double power, boolean fireAssistValid,  int bulletId) {
        this.fireAssistValid = fireAssistValid;
        this.landmineId = bulletId;
        this.power = power;
    }
	
	public int getLandmineId() {
        return landmineId;
    }

    public double getPower() {
        return power;
    }
    
    static ISerializableHelper createHiddenSerializer() {
        return new SerializableHelper();
    }
    
    private static class SerializableHelper implements ISerializableHelper {

        @Override
        public int sizeOf(RbSerializer serializer, Object object) {
            return RbSerializer.SIZEOF_TYPEINFO + RbSerializer.SIZEOF_DOUBLE + RbSerializer.SIZEOF_BOOL
                    + RbSerializer.SIZEOF_DOUBLE + RbSerializer.SIZEOF_INT;
        }

        @Override
        public void serialize(RbSerializer serializer, ByteBuffer buffer, Object object) {
        	LandmineCommand obj = (LandmineCommand) object;

            serializer.serialize(buffer, obj.power);
            serializer.serialize(buffer, obj.fireAssistValid);
            serializer.serialize(buffer, obj.landmineId);
        }

        @Override
        public Object deserialize(RbSerializer serializer, ByteBuffer buffer) {
            double power = buffer.getDouble();
            boolean fireAssistValid = serializer.deserializeBoolean(buffer);
            double fireAssistAngle = buffer.getDouble();
            int bulletId = buffer.getInt();

            return new BulletCommand(power, fireAssistValid, fireAssistAngle, bulletId);
        }
    }
   

}
