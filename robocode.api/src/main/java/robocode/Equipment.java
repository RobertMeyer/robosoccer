package robocode;

import java.util.HashMap;

/**
This is a static class that stores a hashmap of all available equitable items
 *
 */
public class Equipment {
	public static HashMap<String, Object> equipment = new HashMap<String, Object>();
	public static void main(String[] args){
		equipment.put("LightGun", new Gun(1,30,20,100));
		equipment.put("MediumGun", new Gun(2,15,15,100));
		equipment.put("HeavyGun", new Gun(3,5,5,150));
	}
	
	
}
