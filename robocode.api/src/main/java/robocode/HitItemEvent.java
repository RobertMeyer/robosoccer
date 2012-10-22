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
 * A HitItemEvent is sent to {@link Robot#onHitItem(HitItemEvent) onHitItem()}
 * when your robot collides with an item.
 * You can use the information contained in this event to determine what to do.
 *
 * @author Ameer Sabri (Dream Team)
 *
 */

public class HitItemEvent extends Event {
	
	private static final long serialVersionUID = 1L;
	private final static int DEFAULT_PRIORITY = 20;

	private final String itemName;
	private final double energy;
	private final boolean isEquippable;
	private final boolean isDestroyable;

	/**
	 * Called by the game to create a new HitItemEvent.
	 * 
	 * @param itemName the name of the item hit
	 * @param energy the energy of the item hit
	 * @param isEquippable {@code true} if the item hit is equippable;
	 * 				{@code false} otherwise
	 * @param isDestroyable {@code true} if the item hit is destroyable;
	 * 				{@code false} otherwise
	 */
	public HitItemEvent(String itemName, double energy, boolean isEquippable, boolean isDestroyable) {
		this.itemName = itemName;
		this.energy = energy;
		this.isEquippable = isEquippable;
		this.isDestroyable = isDestroyable;
	}

	/**
	 * Returns the name of the item hit.
	 * 
	 * @return the name of the item hit
	 */
	public String getItemName() {
		return itemName;
	}
	
	/**
	 * Returns the energy of the item hit.
	 * 
	 * @return the energy of the item hit
	 */
	public double getEnergy() {
		return energy;
	}

	/**
	 * Checks if the item hit is equippable.
	 * 
	 * @return {@code true} if the item is equippable; {@code false} otherwise
	 */
	public boolean isEquippable() {
		return isEquippable;
	}

	/**
	 * Checks if the item hit is destroyable.
	 * 
	 * @return {@code true} if the item is destroyable; {@code false} otherwise
	 */
	public boolean isDestroyable() {
		return isDestroyable;
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
		IItemEvents listener = ((IItemRobot) robot).getItemEventListener();

		if (listener != null) {
			listener.onHitItem(this);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	byte getSerializationType() {
		return RbSerializer.HitItemEvent_TYPE;
	}

	static ISerializableHelper createHiddenSerializer() {
		return new SerializableHelper();
	}

	private static class SerializableHelper implements ISerializableHelper {
		@Override
        public int sizeOf(RbSerializer serializer, Object object) {
            HitItemEvent obj = (HitItemEvent) object;

            return RbSerializer.SIZEOF_TYPEINFO + serializer.sizeOf(obj.itemName) + 
                    + RbSerializer.SIZEOF_DOUBLE + 2 * RbSerializer.SIZEOF_BOOL;
        }

		@Override
        public void serialize(RbSerializer serializer, ByteBuffer buffer, Object object) {
            HitItemEvent obj = (HitItemEvent) object;

            serializer.serialize(buffer, obj.itemName);
            serializer.serialize(buffer, obj.energy);
            serializer.serialize(buffer, obj.isEquippable);
            serializer.serialize(buffer, obj.isDestroyable);
        }

		@Override
        public Object deserialize(RbSerializer serializer, ByteBuffer buffer) {
            String itemName = serializer.deserializeString(buffer);
            double energy = buffer.getDouble();
            boolean isEquippable = serializer.deserializeBoolean(buffer);
            boolean isDestroyable = serializer.deserializeBoolean(buffer);

            return new HitItemEvent(itemName, energy, isEquippable, isDestroyable);
        }
	}

}

