package tested.equipment;

import robocode.AdvancedRobot;


/**
 * A shell of a robot with just a weapon that has just a sound path
 * 
 * @author Jarred Filmer - CSSE2003
 */
public class SoundEquip2 extends AdvancedRobot {

	public void run(){
		equip("Test2");
		while(true){
			fire(3);
		}
	}
}
