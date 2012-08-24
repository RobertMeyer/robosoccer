package robocode;

public class Battery{
	public double weight;
	public double capacity;
	
	
	public Battery(double capacity, double weight){
		this.weight = weight;
		this.capacity = capacity;}
	
	private double getWeight(){
		return weight;
	}
	private double getCapacity(){
		return capacity;
	}
}	