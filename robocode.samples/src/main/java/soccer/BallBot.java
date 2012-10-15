package soccer;

import robocode.*;
import robocode.util.Utils;

public class BallBot extends Ball {

	/* Bounds for the goal - possibly to be altered at a later date */
	public static final double GOALXMIN = 150;
	public static final double GOALXMAX = 650;
	public static final double GOALYMIN = 150;
	public static final double GOALYMAX = 450;
	public static final double XBOUND = 800;
	public static final double YBOUND = 600;
	public double team1; //team 1 score
	public double team2; //team 2 score

	public void run() {
		addCustomEvent(new Condition("scored") {
			public boolean test() {
				if ((getX() > GOALXMIN) && (getX() < GOALXMAX)
						&& ((getY() > GOALYMAX) || (getY() < GOALYMIN))) {
					return true;
				} else {
					return false;
				}
			}
		});

		execute();
	}

	public void onHitByBullet(HitByBulletEvent e) {
		Bullet bullet = e.getBullet();
		double bulletX = bullet.getX();
		double bulletY = bullet.getY();
		double currentX = getX();
		double currentY = getY();

		double[] normal = new double[2];
		normal[0] = currentX - bulletX;
		normal[1] = currentY - bulletY;

		double[] unitN = new double[2];
		unitN[0] = normal[0] / (Math.sqrt(Math.pow(normal[0],2) + Math.pow(normal[1],2)));
		unitN[1] = normal[1] / (Math.sqrt(Math.pow(normal[1],2) + Math.pow(normal[1],2)));

		double[] unitT = new double[2];
		unitT[0] = -unitN[1];
		unitT[1] = unitN[0];

		double[] velocityBall = new double[2];
		velocityBall[0] = getVelocity()*Math.cos(getHeadingRadians());
		velocityBall[1] = getVelocity()*Math.sin(getHeadingRadians());

		double[] velocityBullet = new double[2];
		velocityBullet[0] = bullet.getVelocity()*Math.cos(bullet.getHeadingRadians());
		velocityBullet[1] = bullet.getVelocity()*Math.cos(bullet.getHeadingRadians());

		double vBulletN = unitN[0]*velocityBullet[0] + unitN[1]*velocityBullet[1];
		double vBallT = unitT[0]*velocityBall[0] + unitT[1]*velocityBall[1];
		//Take mass in to consideration/?/
		double vBallN = unitN[0]*velocityBall[0] + unitN[1]*velocityBall[1];
		double vBallNAfter = (vBallN*(-999) + 2000*vBulletN)/ 1001;

		double[] finalVBallN = new double[2];
		finalVBallN[0] = vBallNAfter*unitN[0];
		finalVBallN[1] = vBallNAfter*unitN[1];

		double[] finalVBallT = new double[2];
		finalVBallT[0] = vBallT*unitN[0];
		finalVBallT[1] = vBallT*unitN[1];

		double[] finalVBall = new double[2];
		finalVBall[0] = finalVBallN[0] + finalVBallT[0];
		finalVBall[1] = finalVBallN[1] + finalVBallT[1];

		//double finalVelocity = Math.sqrt(Math.pow(finalVBall[0], 2) + Math.pow(finalVBall[1], 2));
		double finalHeading = Math.atan(finalVBall[1]/finalVBall[0]);

		setTurnRightRadians(Utils.normalRelativeAngle(finalHeading - getHeadingRadians()));
		setTurnRight(e.getBearing() + 180);
		setAhead(200);

	}

	public void onHitRobot(HitRobotEvent e) {
		setTurnRight(e.getBearing() + 180);
		setAhead(50);

	}

	public void onHitWall(HitWallEvent e) {
		double bearing = e.getBearing();
		setTurnRight(180 + (bearing + bearing));
		setAhead(200);

	}

}