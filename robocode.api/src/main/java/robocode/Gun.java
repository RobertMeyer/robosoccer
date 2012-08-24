package robocode;

public class Gun{
	public double power;
	public double fireRate;
	public double turnRate;
	public double bulletSpeed;
	public String bulletGraphicPath;
	public String bulletSoundPath;
	
	public Gun(double power, double fireRate, double turnRate, double bulletSpeed){
		this.power = power;
		this.fireRate = fireRate;
		this.turnRate = turnRate;
		this.bulletSpeed = bulletSpeed;
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
}	