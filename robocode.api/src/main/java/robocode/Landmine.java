package robocode;

import java.io.Serializable;
import java.nio.ByteBuffer;


import net.sf.robocode.security.IHiddenLandmineHelper;
import net.sf.robocode.serialization.ISerializableHelper;
import net.sf.robocode.serialization.RbSerializer;

public class Landmine implements Serializable {

	private double x;
	private double y;
	private final double power;
	private final String ownerName;
	private String victimName;
	private boolean isActive;
	private final int LandmineId;
	/**
	 * Called by game to create Landmine
	 * @param x
	 * @param y
	 * @param power
	 * @param ownerName
	 * @param victimName
	 * @param isActive
	 * @param bulletId
	 */

	public Landmine(double x, double y, double power, String ownerName,
			String victimName, boolean isActive, int bulletId) {
		this.x = x;
		this.y = y;
		this.power = power;
		this.ownerName = ownerName;
		this.victimName = victimName;
		this.isActive = isActive;
		this.LandmineId = bulletId;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		return LandmineId == ((Landmine) obj).LandmineId;
	}

	@Override
	public int hashCode() {
		return LandmineId;
	}

	/**
	 * 
	 * @return name who own this Landmine
	 */
	public String getName() {
		return ownerName;
	}

	/**
	 * 
	 * @return Landmine's power
	 */
	public double getPower() {
		return power;
	}

	/**
	 * 
	 * @return Robot Name who been killed by this Landmine
	 */
	public String getVictim() {
		return victimName;
	}

	/**
	 * 
	 * @return x position
	 */
	public double getX() {
		return x;
	}

	/**
	 * 
	 * @return y position
	 */
	public double getY() {
		return y;
	}

	/**
	 * 
	 * @return check the landmine whether still active
	 */
	public boolean isActive() {
		return isActive;
	}

	/**
	 * update the landmine base on given information
	 * @param x
	 * @param y
	 * @param victimName
	 * @param isActive
	 */
	private void update(double x, double y, String victimName, boolean isActive) {
		this.x = x;
		this.y = y;
		this.victimName = victimName;
		this.isActive = isActive;
	}

	/**
	 * 
	 * @return landmine ID
	 */
	int getBulletId() {
		return LandmineId;
	}
	
	static ISerializableHelper createHiddenSerializer() {
        return new HiddenLandmineHelper();
    }

	/**
	 * 
	 * @return a hidden landmine helper
	 */
	static IHiddenLandmineHelper createHiddenHelper() {
		return new HiddenLandmineHelper();
	}

	
	private static class HiddenLandmineHelper implements IHiddenLandmineHelper,
			ISerializableHelper {

		@Override
		public void update(Landmine landmine, double x, double y,
				String victimName, boolean isActive) {
			landmine.update(x, y, victimName, isActive);
		}

		@Override
		public int sizeOf(RbSerializer serializer, Object object) {
			Landmine obj = (Landmine) object;

			return RbSerializer.SIZEOF_TYPEINFO + 4
					* RbSerializer.SIZEOF_DOUBLE
					+ serializer.sizeOf(obj.ownerName)
					+ serializer.sizeOf(obj.victimName)
					+ RbSerializer.SIZEOF_BOOL + RbSerializer.SIZEOF_INT;
		}

		@Override
		public void serialize(RbSerializer serializer, ByteBuffer buffer,
				Object object) {
			Landmine obj = (Landmine) object;

			serializer.serialize(buffer, obj.x);
			serializer.serialize(buffer, obj.y);
			serializer.serialize(buffer, obj.power);
			serializer.serialize(buffer, obj.ownerName);
			serializer.serialize(buffer, obj.victimName);
			serializer.serialize(buffer, obj.isActive);
			serializer.serialize(buffer, obj.LandmineId);
		}

		@Override
		public Object deserialize(RbSerializer serializer, ByteBuffer buffer) {
			double x = buffer.getDouble();
			double y = buffer.getDouble();
			double power = buffer.getDouble();
			String ownerName = serializer.deserializeString(buffer);
			String victimName = serializer.deserializeString(buffer);
			boolean isActive = serializer.deserializeBoolean(buffer);
			int landmineId = serializer.deserializeInt(buffer);

			return new Landmine( x, y, power, ownerName,
					victimName, isActive, landmineId);
		}
	}

}
