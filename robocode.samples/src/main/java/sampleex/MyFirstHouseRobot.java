package sampleex;

import java.awt.Color;

import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.HouseRobot;
import robocode.HouseRobotBoundary;
import robocode.Rules;
import robocode.ScannedRobotEvent;
import robocode.TurnCompleteCondition;

public class MyFirstHouseRobot extends HouseRobot {
	
	private final static int PASSIVE = 0;
	private final static int AGGRESSIVE = 1;
	private final static int VENDETTA = 2;
	private int behaviour;
	private String lastRobot;
	private int hitCount;
	HouseRobotBoundary home;
	
	public void run() {
		
		setColors(Color.GREEN, Color.GREEN, Color.GREEN);
		setBulletColor(Color.GREEN);
		
		home = getBoundaries();
		home.setxHome(getX());
		home.setyHome(getY());
		home.setInitialFacing(getHeading());
		home.setArcRange(200);
		
		behaviour = PASSIVE;
		hitCount = 0;
		
		this.turnLeft(80);
		
		while (true) {
			if (this.getEnergy() < 150) {
				behaviour = AGGRESSIVE;
				setColors(Color.RED, Color.RED, Color.RED);
				setBulletColor(Color.RED);
			}
			if (this.getScannedRobotEvents().size() == 0) {
				setTurnRight(160);
				waitFor(new TurnCompleteCondition(this));
				setTurnLeft(160);
				waitFor(new TurnCompleteCondition(this));
			}
		}
	}
	
	
	public void onScannedRobot(ScannedRobotEvent e) {	
		
		switch (behaviour) {

		case '0':
			if (e.getDistance() < 200) {	
				setFire(Rules.MAX_BULLET_POWER);
			}
			break;

		case '1':
			setFire(Rules.MAX_BULLET_POWER);
			break;

		case '2':
			if (e.getName() == lastRobot) {
				setFire(Rules.MAX_BULLET_POWER);
			}
			break;
		}

	}
	
	public void onHitRobot(HitRobotEvent e) {
		
		lastRobot = e.getName();
		
		if (behaviour != AGGRESSIVE) {
			
			if (lastRobot == e.getName()) {
				hitCount += 1;
			} else {
				hitCount = 1;
				behaviour = PASSIVE;
				setColors(Color.GREEN, Color.GREEN, Color.GREEN);
				setBulletColor(Color.GREEN);
			}
			
			if (hitCount >= 5) {
				behaviour = VENDETTA;
				setColors(Color.BLACK, Color.BLACK, Color.BLACK);
				setBulletColor(Color.BLACK);
			}
			
		}
		
		switch (behaviour) {

		case '0':
			turnRight(e.getBearing());
			setFire(Rules.MAX_BULLET_POWER);
			break;

		case '1':
			turnRight(e.getBearing());
			setFire(Rules.MAX_BULLET_POWER);
			break;

		case '2':
			if (e.getName() == lastRobot) {
				turnRight(e.getBearing());
				setFire(Rules.MAX_BULLET_POWER);
			}
			break;
		}
			
		
	}
	
	public void onHitByBullet(HitByBulletEvent e) {
		
		lastRobot = e.getName();
		
		if (behaviour != AGGRESSIVE) {
			
			if (lastRobot == e.getName()) {
				hitCount += 1;
			} else {
				hitCount = 1;
				behaviour = PASSIVE;
				setColors(Color.GREEN, Color.GREEN, Color.GREEN);
				setBulletColor(Color.GREEN);
			}
			
			if (hitCount >= 5) {
				behaviour = VENDETTA;
				setColors(Color.BLACK, Color.BLACK, Color.BLACK);
				setBulletColor(Color.BLACK);
			}
			
		}
			
		switch (behaviour) {

		case '0':
			turnRight(e.getBearing());
			setFire(Rules.MAX_BULLET_POWER);
			break;

		case '1':
			turnRight(e.getBearing());
			setFire(Rules.MAX_BULLET_POWER);
			break;

		case '2':
			if (e.getName() == lastRobot) {
				turnRight(e.getBearing());
				setFire(Rules.MAX_BULLET_POWER);
			}
			break;

		}
		

	}
}
