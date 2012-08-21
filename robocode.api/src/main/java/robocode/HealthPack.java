package robocode;

/**
 * A health pack item. Extends item drop
 * @author s4238358
 *
 */

public class HealthPack extends ItemDrop {

		
	public HealthPack(double xLocation, double yLocation, double width, double height, boolean isDestroyable, int lifespan,
			double health, boolean isEquippable){
		super(xLocation, yLocation, width, height, isDestroyable, lifespan, health, isEquippable);
		System.out.println("Health Pack");
	}
	
	
}
