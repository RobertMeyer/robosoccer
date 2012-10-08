package equipment;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import net.sf.robocode.io.Logger;
import robocode.*;

/**
 * A sample equipment robot.  That equips a range of equipment and moves
 * in a simple circle path.
 * 
 * @author Jayke Anderson - CSSE2003
 */
public class SampleEquipRobot extends AdvancedRobot {
	
	/**
	 * The main run function
	 */
	public void run(){
		
		equip("Division 9 Plasmaprojector");
		
		ahead(100);
	}
	public void print(String string){
		System.out.println(string);
	}
}
