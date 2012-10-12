package net.sf.robocode.peer;

import java.io.Serializable;
import java.nio.ByteBuffer;

import net.sf.robocode.serialization.ISerializableHelper;
import net.sf.robocode.serialization.RbSerializer;

public class LandmineStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    public LandmineStatus(int landmineId, double x, double y, String victimName, boolean isActive) {
        this.landmineId = landmineId;
        this.x = x;
        this.y = y;
        this.isActive = isActive;
        this.victimName = victimName;
    }
    public final int landmineId;
    public final String victimName;
    public final boolean isActive;
    public final double x;
    public final double y;

    static ISerializableHelper createHiddenSerializer() {
        return new SerializableHelper();
    }

    private static class SerializableHelper implements ISerializableHelper {

        @Override
        public int sizeOf(RbSerializer serializer, Object object) {
            BulletStatus obj = (BulletStatus) object;

            return RbSerializer.SIZEOF_TYPEINFO + RbSerializer.SIZEOF_INT + serializer.sizeOf(obj.victimName)
                    + RbSerializer.SIZEOF_BOOL + 2 * RbSerializer.SIZEOF_DOUBLE;
        }

        @Override
        public void serialize(RbSerializer serializer, ByteBuffer buffer, Object object) {
        	LandmineStatus obj = (LandmineStatus) object;

            serializer.serialize(buffer, obj.landmineId);
            serializer.serialize(buffer, obj.victimName);
            serializer.serialize(buffer, obj.isActive);
            serializer.serialize(buffer, obj.x);
            serializer.serialize(buffer, obj.y);
        }

        @Override
        public Object deserialize(RbSerializer serializer, ByteBuffer buffer) {
            int landmineId = buffer.getInt();
            String victimName = serializer.deserializeString(buffer);
            boolean isActive = serializer.deserializeBoolean(buffer);
            double x = buffer.getDouble();
            double y = buffer.getDouble();

            return new LandmineStatus(landmineId, x, y, victimName, isActive);
        }
    }
}
