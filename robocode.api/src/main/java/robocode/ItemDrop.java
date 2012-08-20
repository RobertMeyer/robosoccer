package robocode;

/**
 * Abstract class for item/powerup drops
 * 
 * @author s4238358
 *
 */


public abstract	class ItemDrop {


	private double xLocation;
	private double yLocation;
	private double width;
	private double height;
	private boolean isDestroyable;
	private int lifespan;
	private double health;
	private boolean isEquippable;
	
	
	ItemDrop(){
		System.out.println("Item made");
	}

	public double getXLocation() {
		return xLocation;
	}

	public void setXLocation(double xLocation) {
		this.xLocation = xLocation;
	}

	
	public double getYLocation() {
		return yLocation;
	}

	public void setYLocation(double yLocation) {
		this.yLocation = yLocation;
	}

	public double getWidth(){
		return width;
	}
	
	public void setWidth(double width){
		this.width = width;
	}
	
	public double getHeight(){
		return height;
	}
	
	public void setHeight(double height){
		this.height = height;
	}
	
	public boolean getIsDestroyable(){
		return isDestroyable;
	}
	
	public void setIsDestroyable(boolean isDestroyable){
		this.isDestroyable = isDestroyable;
	}
	
	public int getLifespan(){
		return lifespan;
	}
	
	public void setLifespan(int lifespan){
		this.lifespan = lifespan;
	}
	
	public double getHealth(){
		return health;
	}
	
	public void setHealth(double health){
		this.health = health;
	}
	
	public boolean getIsEquippable(){
		return isEquippable;
	}
	
	public void setIsEquippable(boolean isEquippable){
		this.isEquippable = isEquippable;
	}
}
