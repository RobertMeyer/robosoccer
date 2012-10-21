package sampleex;

import java.awt.Color;

import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.HouseRobot;
import robocode.HouseRobotBoundary;
import robocode.Rules;
import robocode.ScannedRobotEvent;
import robocode.TurnCompleteCondition;

/**
 * 
 * @author House Robot Team
 * @author Jack Reichelt (42338271)
 * @author David Wei
 * @author Laurence McLean (42373414 - javadoc)
 *
 * The House Robot that will appear in House Robot Mode.
 * It will get spawned in a corner by the HouseRobotspawnController and
 * attack any robot that comes too close. If a robot is continually attacking
 * it from outside of the boundaries, it might just get a little bit
 * angry and fire regardless of where you are.
 */
public class MyFirstHouseRobot extends HouseRobot {
	
	/**
	 * Behaviour types of the House Robot.
	 */
	private final static int PASSIVE = 0; //Robot will be green
	private final static int AGGRESSIVE = 1; //Robot will be red
	private final static int VENDETTA = 2; //Robot will be black
	
	/**
	 * Current behaviour of the House Robot
	 */
	
	private int behaviour;
	
	/**
	 * The name of the robot that last hit us.
	 */
	private String lastRobot;
	
	/**
	 * How many times the last robot has hit us.
	 */
	private int hitCount;
	
	/**
	 * Boundary of where the House Robot can be
	 */
	HouseRobotBoundary home;
	
	/**
	 * {@inheritDoc}
	 */
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
			//When we're low on energy, we automatically want to go into aggressive mode
			//We'll also have red bullets.
			if (this.getEnergy() < 150) {
				if(behaviour < AGGRESSIVE) {
					behaviour = AGGRESSIVE;
				}
				setColors(Color.RED, Color.RED, Color.RED);
				setBulletColor(Color.RED);
			}
			//Continually scan the corner that we're in.
			if (this.getScannedRobotEvents().size() == 0) {
				setTurnRight(160);
				waitFor(new TurnCompleteCondition(this));
				setTurnLeft(160);
				waitFor(new TurnCompleteCondition(this));
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void onScannedRobot(ScannedRobotEvent e) {	
		switch (behaviour) {
			case PASSIVE:
				if (e.getDistance() < 200) {	
					setFire(Rules.MAX_BULLET_POWER);
				}
				break;
	
			case AGGRESSIVE:
				setFire(Rules.MAX_BULLET_POWER);
				break;
	
			case VENDETTA:
				if (e.getName() == lastRobot) {
					setFire(Rules.MAX_BULLET_POWER);
				}
				break;
		}

	}
	
	/**
	 * {@inheritDoc}
	 */
	public void onHitRobot(HitRobotEvent e) {
		lastRobot = e.getName();
		if (behaviour != AGGRESSIVE) {
			//We'll attack you with a friendly green warning bullet.
			if (lastRobot == e.getName()) {
				hitCount += 1;
			} else {
				hitCount = 1;
				behaviour = PASSIVE;
				setColors(Color.GREEN, Color.GREEN, Color.GREEN);
				setBulletColor(Color.GREEN);
			}
			
			//We're going to come after you with scary black bullets!
			if (hitCount >= 5) {
				behaviour = VENDETTA;
				setColors(Color.BLACK, Color.BLACK, Color.BLACK);
				setBulletColor(Color.BLACK);
			}
		}
		switch (behaviour) {
			case PASSIVE:
				turnRight(e.getBearing());
				setFire(Rules.MAX_BULLET_POWER);
				break;
	
			case AGGRESSIVE:
				turnRight(e.getBearing());
				setFire(Rules.MAX_BULLET_POWER);
				break;
	
			case VENDETTA:
				if (e.getName() == lastRobot) {
					turnRight(e.getBearing());
					setFire(Rules.MAX_BULLET_POWER);
				}
				break;
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		lastRobot = e.getName();
		if (behaviour != AGGRESSIVE) {
			//We'll attack you with a friendly green warning bullet.
			if (lastRobot == e.getName()) {
				hitCount += 1;
			} else {
				hitCount = 1;
				behaviour = PASSIVE;
				setColors(Color.GREEN, Color.GREEN, Color.GREEN);
				setBulletColor(Color.GREEN);
			}

			//We're going to come after you with scary black bullets!
			if (hitCount >= 5) {
				behaviour = VENDETTA;
				setColors(Color.BLACK, Color.BLACK, Color.BLACK);
				setBulletColor(Color.BLACK);
			}
		}
		switch (behaviour) {
			case PASSIVE:
				turnRight(e.getBearing());
				setFire(Rules.MAX_BULLET_POWER);
				break;
	
			case AGGRESSIVE:
				turnRight(e.getBearing());
				setFire(Rules.MAX_BULLET_POWER);
				break;
	
			case VENDETTA:
				if (e.getName() == lastRobot) {
					turnRight(e.getBearing());
					setFire(Rules.MAX_BULLET_POWER);
				}
				break;
		}
	}
}
