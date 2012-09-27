package robocode;

/**
 * Behaviour for Botzilla. For the most part, just uses RamFire's ramming 
 * behaviour.
 * @author The Fightin' Mongooses
 */
public class Botzilla extends Robot {
	
	int turnDirection = 1;
	
	@Override
	public void run() {
		while (true) {
			turnRight(5*turnDirection);
		}
	}
	
	@Override
	public void onScannedRobot(ScannedRobotEvent e) {
		changeDirection(e.getBearing());
		turnRight(e.getBearing());
		ahead(e.getDistance() + 5);
		scan();
	}
	
	@Override
	public void onHitRobot(HitRobotEvent e) {
		changeDirection(e.getBearing());
		turnRight(e.getBearing());
		ahead(40);
	}
	
	private void changeDirection(double bearing) {
		if (bearing >= 0) {
			turnDirection = 1;
		} else {
			turnDirection = -1;
		}
	}
	
}
