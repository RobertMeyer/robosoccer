package robocode.control.snapshot;

/**
 * Interface of an item snapshot at a specific time in a battle.
 * @author s4238358
 *
 */

//TODO implement this class fully
public interface IItemSnapshot {
	
	/**
	 * Returns the name of the item.
	 * 
	 * @return the name of the item.
	 */
	String getName();
	
	/**
	 * Returns the index of the item, which is unique for the specific item and constant during a battle.
	 * 
	 * @return the item index.
	 */
	int getItemIndex();
	
	/**
	 * Returns the energy level of the item. Not sure whether to use boolean check or double check. 
	 * Depends on item implementation.
	 * 
	 * @return the energy level of the item.
	 */
	double getEnergy();
	
	/**
	 * Returns the X position of the item.
	 * 
	 * @return the X position of the item.
	 */
	double getX();
	
	/**
	 * Returns the Y position of the item.
	 * 
	 * @return the Y position of the item.
	 */
	double getY();
	
	/**
	 * Checks if this item is destructible.
	 * 
	 * @return {@code true} if this item is destructible; {@code false} otherwise.
	 */
	boolean isDestructible();
	
	/**
	 * Checks if this item is equippable.
	 * 
	 * @return {@code true} if this item is equippable; {@code false} otherwise.
	 */
	boolean isEquippable();
	
	/**
	 * Returns a snapshot of debug properties. Needs configuration
	 * 
	 * @return a snapshot of debug properties.
	 */
	IDebugProperty[] getDebugProperties();
	
	/**
	 * Returns a snapshot of the output print stream for this item.
	 *
	 * @return a string containing the snapshot of the output print stream.
	 */
	String getOutputStreamSnapshot();
}
