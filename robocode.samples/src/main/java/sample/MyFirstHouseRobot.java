package sample;

import robocode.HouseRobot;
import robocode.HouseRobotBoundary;
import robocode.Rules;
import robocode.ScannedRobotEvent;

public class MyFirstHouseRobot extends HouseRobot {
	
	HouseRobotBoundary home;
	boolean chasing = false;
	
	public void run() {
		
		home = new HouseRobotBoundary();
		
		home.setxHome(this.getX());
		home.setyHome(this.getY());
		
		setBoundaries();
		
		this.turnGunLeft(50);
		
		while (true) {
			
			while (chasing == false) {
				this.turnGunRight(100);
				this.turnGunLeft(100);
			}
			
			while (chasing == true) {
				if (atHome() == false) {
					chasing = false;
					break;
				}
			}
			
		}
		
	}
	
	private void setBoundaries() {
		
		double arcDistance = 100;
		
		//TODO get some logic here based on starting position. Reliant on HouseRobotPeer changes incoming.
		
		home.setArcRange(arcDistance);
		
	}
	
	private boolean inBoundaries(ScannedRobotEvent e) {
		
		if (e.getDistance() <= home.getArcRange()) {
			return true;
		}
		return false;
	}
	
	private boolean atHome() {
		
		double xDistance = Math.abs(this.getX() - home.getXHome());
		double yDistance = Math.abs(this.getY() - home.getYHome());
		
		xDistance = xDistance*xDistance;
		yDistance = yDistance*yDistance;
		
		if (Math.sqrt(xDistance*yDistance) < home.getArcRange()) {
			return true;
		}
		
		return false;
		
	}
	
	public void onScannedRobot(ScannedRobotEvent e) {

		if (inBoundaries(e)) {
			chasing = true;
			
			this.fire(Rules.MAX_BULLET_POWER);
		}
	}
}
