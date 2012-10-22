package tested.robots;

/**
 * @author Flemming N. Larsen (original)
 */
public class DispenserBotHeal extends robocode.Dispenser {

    @Override
    public void run() {
        while (true) {
    		turnRadarRight(1);
    		turnGunRight(1);
    		turnLeft(1);
    	}
    }
}
