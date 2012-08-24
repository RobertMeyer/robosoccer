package robocode;

public class Gun{
	public double power;
	public double fireRate;
	public double turnRate;
	public double bulletSpeed;
	public String bulletGraphicPath;
	public String bulletSoundPath;
	public double weight;
	
	public Gun(double weight, double power, double fireRate, double turnRate, double bulletSpeed, String graphic, String sound){
		this.power = power;
		this.fireRate = fireRate;
		this.turnRate = turnRate;
		this.bulletSpeed = bulletSpeed;
		this.weight = weight;
		if(graphic != "" )
			this.bulletGraphicPath = graphic;
		
		if(sound != "" )
			this.bulletSoundPath = sound;
	}
	private double getPower(){
		return power;
	}
	private double getFireRate(){
		return fireRate;
	}
	private double getTurnRate(){
		return turnRate;
	}
	private double getBulletSpeed(){
		return bulletSpeed;
	}
	private double getWeight(){
		return weight;
	}
	private String getBulletGraphicPath(){
		return bulletGraphicPath;
	}
	private String getBulletSoundPath(){
		return bulletSoundPath;
	}
}	