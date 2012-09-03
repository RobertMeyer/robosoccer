package net.sf.robocode.items;

/**
 * A health pack item. Extends item drop
 * @author s4238358
 *
 */

public class HealthPack extends ItemDrop {

		
	public HealthPack(double width, double height, boolean isDestroyable, int lifespan, double health, boolean isEquippable){
		super(isDestroyable, lifespan, health, isEquippable, null);
		System.out.println("Health Pack");
	}
	
	
}
