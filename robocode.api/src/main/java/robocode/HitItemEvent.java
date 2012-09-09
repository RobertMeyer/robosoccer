package robocode;

import java.awt.*;
import net.sf.robocode.peer.IRobotStatics;
import robocode.robotinterfaces.IBasicRobot;
//import robocode.robotinterfaces.IBasicEvents;
//import net.sf.robocode.serialization.ISerializableHelper;
//import net.sf.robocode.serialization.RbSerializer;
//import net.sf.robocode.battle.*;

/**
 * A HitItemEvent is sent to {@link Robot#onHitItem(HitItemEvent) onHitItem()}
 * when your robot collides with an item.
 * You can use the information contained in this event to determine what to do.
 * 
 * TODO serializables, addition of tracked variables that may have been missed
 * 
 * @author Ameer Sabri
 *
 */
public final class HitItemEvent extends Event {
	private static final long serialVersionUID = 1L;
	private final static int DEFAULT_PRIORITY = 15;
	
	private final String robotName;
	private final double energy;
	private final boolean isDestroyable;
	private final boolean isEquippable;
	
	/**
	 * Called by the game to create a new HitItemEvent.
	 * 
	 * @param name the name of the robot that hit the item
	 * @param energy the energy of the robot that hit the item
	 * @param isDestructible {@code true} if the item hit is destructible;
	 * 				{@code false} otherwise
	 * @param isEquippable {@code true} if the item hit is equippable;
	 * 				{@code false} otherwise
	 */
	private HitItemEvent(String name, double energy, boolean isDestroyable, boolean isEquippable) {
		this.robotName = name;
		this.energy = energy;
		this.isDestroyable = isDestroyable;
		this.isEquippable = isEquippable;
	}
	
	/**
	 * Returns the name of the robot that hit the item.
	 * 
	 * @return the name of the robot that hit the item
	 */
	public String getName() {
		return robotName;
	}
	
	/**
	 * Returns the amount of energy of the robot that hit the item.
	 * 
	 * @return the amount of energy of the robot that hit the item
	 */
	public double getEnergy() {
		return energy;
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
	 * Checks if the item hit is equippable.
	 * 
	 * @return {@code true} if the item is equippable; {@code false} otherwise
	 */
	public boolean isEquippable() {
		return isEquippable;
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
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	//@Override
	//byte getSerializationType() {
	//	
	//}
	
	//static ISerializableHelper createHiddenSerializer() {
	//	return new SerializableHelper();
	//}
	
	//private static class SerializableHelper implements ISerializableHelper {
		
	//}
}
