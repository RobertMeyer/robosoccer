package net.sf.robocode.battle;

import robocode.*;
import robocode.util.Utils;

/**
 * BallBot is the robot used to represent the soccer ball in Soccer Mode.
 * 
 * @author kirstypurcell @ TeamG1
 */
public class BallBot extends Ball {

	public void run() {
		execute();
	}

	/**
	 * On getting hit by a bullet, the ball bot adjusts it heading and velocity
	 * according to its current heading and velocity as well as the heading and
	 * velocity of the bullet it was hit by. The resulting movement is intended
	 * to mimic real world physics of ball collision.
	 * 
	 * @param e
	 *            - HitByBulletEvent
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		/*
		 * The offending bullet and its X and Y coordinates at the time of
		 * collision
		 */
		Bullet bullet = e.getBullet();
		double bulletX = bullet.getX();
		double bulletY = bullet.getY();
		double bulletVelocity = bullet.getVelocity();
		double bulletHeading = bullet.getHeadingRadians();

		/* X and Y coordinates of the Ball */
		double currentX = getX();
		double currentY = getY();
		double currentVelocity = getVelocity();
		double currentHeading = getHeadingRadians();

		double[] velocityVector = calculateMovement(currentX, currentY,
				currentVelocity, currentHeading, bulletX, bulletY, bulletVelocity,
				bulletHeading);

		setMaxVelocity(velocityVector[0]);
		setTurnRightRadians(Utils.normalRelativeAngle(velocityVector[1])
				- getHeadingRadians());
		setAhead(200);

	}

	public double[] calculateMovement(double currentX, double currentY,
			double currentVelocity, double currentHeading, double bulletX, double bulletY,
			double bulletVelocity, double bulletHeading) {
		/* Normal vector of the collision */
		double[] normal = new double[2];
		normal[0] = currentX - bulletX;
		normal[1] = currentY - bulletY;

		/* Unit normal vector */
		double[] unitN = new double[2];
		unitN[0] = normal[0]
				/ (Math.sqrt(Math.pow(normal[0], 2) + Math.pow(normal[1], 2)));
		unitN[1] = normal[1]
				/ (Math.sqrt(Math.pow(normal[1], 2) + Math.pow(normal[1], 2)));

		/* Unit tangent vector -> ut = (-uny, unx) */
		double[] unitT = new double[2];
		unitT[0] = -unitN[1];
		unitT[1] = unitN[0];

		/* Initial velocity vector of the ball */
		double[] velocityBall = new double[2];
		velocityBall[0] = currentVelocity * Math.cos(currentHeading);
		velocityBall[1] = currentVelocity * Math.sin(currentHeading);

		/* Initial velocity vector of the ball */
		double[] velocityBullet = new double[2];
		velocityBullet[0] = bulletVelocity * Math.cos(bulletHeading);
		velocityBullet[1] = bulletVelocity * Math.cos(bulletHeading);

		/* Scalar velocity of the bullet in the normal direction */
		double vBulletN = unitN[0] * velocityBullet[0] + unitN[1]
				* velocityBullet[1];
		/* Scalar velocity of the ball in the tangential direction */
		double vBallT = unitT[0] * velocityBall[0] + unitT[1] * velocityBall[1];
		/* Scalar veloctu of the ball in the normal direction */
		double vBallN = unitN[0] * velocityBall[0] + unitN[1] * velocityBall[1];

		/*
		 * Velocity of the ball in the normal direction after collision v2 = (u2
		 * * (m2 - m1) + 2m1*u1) / (m1+m2)
		 * 
		 * As this is suppose to mimic a foot with great force kicking a small
		 * air-filled soccer ball, the mass of the ball is 1 and the mass of the
		 * bullet is some arbitrarily large number (1000)
		 */
		double vBallNAfter = (vBallN * (-999) + 2000 * vBulletN) / 1001;

		/* Vector velocity of ball in the normal direction after collision */
		double[] finalVBallN = new double[2];
		finalVBallN[0] = vBallNAfter * unitN[0];
		finalVBallN[1] = vBallNAfter * unitN[1];

		/*
		 * Vector velocity of the ball in the tangential direction after
		 * collision
		 */
		double[] finalVBallT = new double[2];
		finalVBallT[0] = vBallT * unitN[0];
		finalVBallT[1] = vBallT * unitN[1];

		/* Vector velocity of the ball after collision */
		double[] finalVBall = new double[2];
		finalVBall[0] = finalVBallN[0] + finalVBallT[0];
		finalVBall[1] = finalVBallN[1] + finalVBallT[1];

		/* Final velocity and heading */
		double finalVelocity = Math.sqrt(Math.pow(finalVBall[0], 2)
				+ Math.pow(finalVBall[1], 2));
		double finalHeading = Math.atan(finalVBall[1] / finalVBall[0]);

		double[] velocityVector = { finalVelocity, finalHeading };

		return velocityVector;
	}

	/**
	 * On hitting another robot, the ball changes heading to match the heading
	 * of the other robot and moves just 50. This is to represent dribbling.
	 * 
	 */
	public void onHitRobot(HitRobotEvent e) {
		setTurnRight(e.getBearing() + 180);
		setAhead(50);

	}

	/**
	 * On hitting a wall the ball will bounce off at a reflective angle. This is
	 * to mimic a real ball hitting a wall.
	 * 
	 */
	public void onHitWall(HitWallEvent e) {
		double bearing = e.getBearing();
		setTurnRight(180 + (bearing + bearing));
		setAhead(200);

	}

}