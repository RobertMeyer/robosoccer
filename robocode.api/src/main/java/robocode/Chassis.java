package robocode;

public class Chassis{
	public double weight;
	public double weightCapacity;
	public double durability;
	public String size;
	
	
	public Chassis(String size, double weight, double weightCapacity, double durability){
		this.weight = weight;
		this.weightCapacity = weightCapacity;
		this.durability = durability;
		this.size = size;
	}
	private double getWeight(){
		return weight;
	}
	private double getWeightCapacity(){
		return weightCapacity;
	}
	private double getDurability(){
		return durability;
	}
	private String getSize(){
		return size;
	}
}	