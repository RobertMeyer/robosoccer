package robocode;

import java.util.HashMap;

/**
This is a static class that stores a hashmap of all available equitable items
 *
 */
public class Equipment {
	public static HashMap<String, Object> equipment = new HashMap<String, Object>();
	public static void main(String[] args){
		equipment.put("LightGun", new Gun(0,0,0,0,0,"",""));
		equipment.put("MediumGun", new Gun(0,0,0,0,0,"",""));
		equipment.put("HeavyGun", new Gun(0,0,0,0,0,"",""));
		
		equipment.put("LightLazer", new Gun(0,0,0,0,0,"",""));
		equipment.put("MediumLazer", new Gun(0,0,0,0,0,"",""));
		equipment.put("HeavyLazer", new Gun(0,0,0,0,0,"",""));
		
		equipment.put("LightChassis", new Chassis("",0,0,0));
		equipment.put("MediumChassis", new Chassis("",0,0,0));
		equipment.put("HeavyChassis", new Chassis("",0,0,0));
		
		equipment.put("LightChassis", new Chassis("",0,0,0));
		equipment.put("MediumChassis", new Chassis("",0,0,0));
		equipment.put("HeavyChassis", new Chassis("",0,0,0));
		
		equipment.put("LightBattery", new Battery(0,0));
		equipment.put("MediumBattery", new Battery(0,0));
		equipment.put("HeavyBattery", new Battery(0,0));
	}
	
	
}
