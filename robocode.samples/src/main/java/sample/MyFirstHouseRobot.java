package sample;

import java.awt.Color;

import robocode.HouseRobot;
import robocode.HouseRobotBoundary;
import robocode.Rules;
import robocode.ScannedRobotEvent;

public class MyFirstHouseRobot extends HouseRobot {
	
	HouseRobotBoundary home;
	boolean chasing = false;
	
	public void run() {
		home = this.getBoundaries();
		
		home.setxHome(this.getX());
		home.setyHome(this.getY());
		home.setInitialFacing(this.getHeading());
		setBoundaries();
		
		this.turnGunLeft(60);
		
		chasing = false;
		
		while (true) {
			
			if (chasing == false) {
				
				this.turnGunRight(120);
				this.turnGunLeft(120);
				
			} else {
				setAhead(25);
				if (atHome() == false) {
					this.setColors(Color.YELLOW, Color.ORANGE, Color.ORANGE);
					chasing = false;
					// TODO go to home and turn to face out.
					//break;
				}
			}	
		}
	}
	
	private void setBoundaries() {
		
		double arcDistance = 200;
		
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
		
		double xDistance = this.getX() - home.getXHome();
		double yDistance = this.getY() - home.getYHome();
		
		xDistance = xDistance*xDistance;
		yDistance = yDistance*yDistance;
		
		double distanceToHome = Math.sqrt(xDistance*yDistance);
		
		if (distanceToHome <= home.getArcRange()*2) {
			return true;
		}
		
		//return true;
		return false;
		
	}
	
	public void onScannedRobot(ScannedRobotEvent e) {		
		if (chasing == false) {
			if (inBoundaries(e)) {
				chasing = true;
				this.setColors(Color.RED, Color.RED, Color.RED);
				
				//setTurnRight(e.getBearing());
				this.setFire(Rules.MAX_BULLET_POWER);
			}
		} else {
			this.setFire(Rules.MAX_BULLET_POWER);
		}
	}
}
