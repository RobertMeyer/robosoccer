package soccer;

import robocode.HitRobotEvent;
import robocode.Rules;
import robocode.ScannedRobotEvent;
import robocode.SoccerRobot;
import robocode.WinEvent;
import robocode.util.Utils;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * This is an example SoccerRobot for use in SoccerMode. This particular robot
 * demonstrates an offensive style of play, where the robot constantly attempts
 * to position itself such that the ball lies directly between this robot and
 * the enemy goal.
 * 
 * Author: Carl Hattenfels - team-G1
 * 
 */
public class OffensiveSoccerPlayer extends SoccerRobot {
	
	private static final int clockwise = 1;
	private static final int counterClockwise = -1;
	private static final double pi = Math.PI;
	
	private double battlefieldWidth;
	private double battlefieldHeight;
	
	private Rectangle2D.Float ownGoal;
	private Rectangle2D.Float enemyGoal;
	
	private Integer goalSide;
	
	// How much energy we want to put in bullet
	private double firepower;
	
	// How far we want to stay away from walls
	private double wallMargin = 50; 
	
	// 1 == forward travel, -1 == backward
	private int direction;
	
	// Radius of the circle we travel around our enemy
	double circleDistance;
	
	// Array of colours for robot to switch between.
	private Color[] colourPallete = {Color.BLACK,Color.YELLOW,Color.RED,
			Color.BLUE, Color.ORANGE,Color.GREEN};
	
	// Index for cycling through colourPallete
	int colourIndex = 0;
	
	/* TESTCODE */
	double lastBallX = 0;
	double lastBallY = 0;
	double lastAngleToTop = 0;
	double lastAngleToBottom = 0;
	
	public void run() {
		
		direction = 1;
		firepower = 1.5;
		circleDistance = 0;
		battlefieldWidth = getBattleFieldWidth();
		battlefieldHeight = getBattleFieldHeight();
		
		ownGoal = getOwnGoal();
		enemyGoal = getEnemyGoal();
 		goalSide = getGoalSide();
 		
		// Stops Radar from moving with gun
		setAdjustRadarForGunTurn(true);
		// Stops gun from moving with Robot
		setAdjustGunForRobotTurn(true);
		
		while(true) {
			// Continuously rotate radar when not in view of enemy
			setTurnRadarRight(Double.POSITIVE_INFINITY);
			
			// Perform any setX style actions.
			execute();
			
			// Manually scan for robots so we don't miss stationary ones
			scan();			
		}
	}
	
	/**
	 * Actions to perform on scanning an enemy. i.e. lock onto enemy with radar,
	 * begin moving toward enemy or circling them if they are close, and aim at
	 * and fire at them.
	 * 
	 * @param enemy - The enemy that generated this event.
	 */
	public void onScannedRobot(ScannedRobotEvent enemy) {
		// Move radar to the other side of enemy, so we can keep a lock on
		double radarAdjust = getHeadingRadians() + enemy.getBearingRadians() 
				- getRadarHeadingRadians();
		setTurnRadarRightRadians(1.8 * Utils.normalRelativeAngle(radarAdjust));
		
		// Make robot cycle through colours for psychedelic affect
		if(getTime() % 5 == 0) {
			setAllColors(colourPallete[(colourIndex++ % 6)]);	
		}
		
		move(enemy);
		setTurnGunRightRadians(aim(enemy));
		
		if(towardsGoal(enemy) || Math.random() < 0.01) {
			setFire(firepower);
		}
		
		scan();
	}
	
	/**
	 * Linear targeting algorithm. Estimates enemies future position based
	 * on current velocity. Assumes that the enemy will stop when it hits the
	 * world.
	 *
	 *	@param enemy - event generated by Scanned Robot
	 *	@return double - the bearing to which we should aim to hit the robot
	 */
	public double aim(ScannedRobotEvent enemy) {
		
		double eVelocity = enemy.getVelocity();
		double eDistance = enemy.getDistance();
		double eHeading = enemy.getHeadingRadians();
		
		// Enemies absolute bearing
		double bearing = getHeadingRadians() + enemy.getBearingRadians();
		
		// Enemies current coordinates, assuming our robot to be the origin.
		double enemyX = getX() + eDistance * Math.sin(bearing);
		double enemyY = getY() + eDistance * Math.cos(bearing);
		
		// Start estimate for time
		double time = eDistance / Rules.getBulletSpeed(firepower) / 2;
		
		// Time it would take a bullet to travel to enemies future position
		double bulletTime;
		
		// Future coordinates and distance for enemy.
		double futureX, futureY, futureDistance;
		
		/*
		 * Increment time and recalculate future position of enemy until the
		 * time for the enemy to reach a future position is approximately 
		 * equal to the time it would take a bullet of given power to travel
		 * the distance between our current position and that future position. 
		 */
		do {
			time += 0.1;
			
			// The enemies x and y coordinates 'time' ticks in the future
			futureX = enemyX + time * eVelocity * Math.sin(eHeading);
			futureY = enemyY + time * eVelocity * Math.cos(eHeading);
			// Distance from our current position to the future enemy position
			futureDistance = Math.sqrt(Math.pow(futureX - getX(), 2) + 
					Math.pow(futureY - getY(), 2));
			
			// If enemy is likely to run into wall, shoot at where it will stop
			if (futureX < 15 || (futureX > battlefieldWidth - 15)
					|| futureY < 15 || (futureY > battlefieldHeight - 15)){
				break;
			}
			
			bulletTime = futureDistance / Rules.getBulletSpeed(firepower);
		} while (Math.abs(time - bulletTime) > 0.5 );
		
		/* 
		 * Once the aforementioned future position is found, find gun rotation
		 * required to point at this position
		 */
		double adjustedBearing = (Math.atan2(futureX - getX(), futureY - getY()) 
				- getGunHeadingRadians());
		
		return Utils.normalRelativeAngle(adjustedBearing);
		
	}
	
	/**
	 * Function manages Robot movement around target enemy. Circle enemy or
	 * close the distance if he is too far away. Returns true if we are
	 * current positioned in a trajectory towards the goal.
	 * 
	 * @param enemy - recently targeted enemy 
	 */
	public boolean move(ScannedRobotEvent ball) {
		double turn;
		double adjustedTurn;
		double distance = ball.getDistance();
		
		// The amount by which we need to modify our heading to avoid a wall or
		// or close the distance to the enemy
		double offset = 0;
		
		// Side = -1 is on left, 1 if on the right. 
		double side = getSide(ball);
		
		double ballAbsoluteBearing = Utils.normalAbsoluteAngle(
				getHeadingRadians() + ball.getBearingRadians());
		
		// Balls current absolute coordinates.
		double ballX = getX() + distance * Math.sin(ballAbsoluteBearing);
		double ballY = getY() + distance * Math.cos(ballAbsoluteBearing);
		// For graphical debugging
		lastBallX = ballX;
		lastBallY = ballY;
		
		double maxDeltaX = 5 * (enemyGoal.getCenterX() - lastBallX);
	    double maxDeltaY = 5 * (enemyGoal.getMaxY() - lastBallY);
	    double minDeltaX = maxDeltaX;
	    double minDeltaY = 5 * (enemyGoal.getMinY() - lastBallY);

	     int[] triPointsX = {(int) lastBallX, (int) (lastBallX - minDeltaX), 
	    		 (int) (lastBallX - maxDeltaX)};
	     int[] triPointsY = {(int) lastBallY, (int) (lastBallY - minDeltaY), 
	    		 (int) (lastBallY - maxDeltaY)};
	     
	     Polygon enemyPolygon = new Polygon(triPointsX, triPointsY, 3);
	     Point2D.Float us = new Point2D.Float((int) getX(),(int) getY());
	     
	     boolean polygonAbove = false;
	     boolean polygonBelow = false;
	     
	     double currentX = getX();
	     double currentY = getY();
	     
	    for(int i = 0; i < battlefieldHeight; i += (battlefieldHeight/40)) {
	    	if(enemyPolygon.contains(currentX, currentY + i)) {
	    		polygonAbove = true;
	    	}
	    	if(enemyPolygon.contains(currentX, currentY - i)) {
	    		polygonBelow = true;
	    	}
	    	if(polygonAbove || polygonBelow) {
	    		break;
	    	}
	    }
		System.out.println(goalSide);
		
		offset += headingAdjustment(distance) * side * direction;
		
		// Heading we would take in the absence of walls
		turn = ball.getBearingRadians() - (Math.PI/2) * side + offset;
		
		// Heading as adjusted for wall proximity.
		adjustedTurn = avoidWall(turn, side);
		
		// Set our turn rate for circling around our enemy, plus any spiraling.
		setTurnRightRadians(Utils.normalRelativeAngle(adjustedTurn));
		
		if(!enemyPolygon.contains(us)) {
			if(polygonAbove) {
				direction = getSide(ball) * clockwise * -goalSide;
				setAhead(50 * direction);
			} else if (polygonBelow){
				direction = getSide(ball) * counterClockwise * -goalSide;
				setAhead(50 * direction);
			} else {
				setAhead(50 * direction);
			}
		} else {
			setAhead(50 * direction);
		}
		
		if(distance < 40) {
			stop();
		}
	
		return enemyPolygon.contains(us);
	}
	
	/**
	 * Returns the absolute bearing from this robot to the supplied point, 
	 * using robocode coordinate system.
	 */
	public double getAbsoluteBearing(double originX, double originY,
			double pointX, double pointY) {
		double deltaX = pointX - originX;
		double deltaY = pointY - originY;
		return ((Math.atan2(deltaY, deltaX) * -1) + (5 * pi / 2)) % (2 * pi);
	}
	
	/**
	 * Returns the absolute bearing from this robot to it's own goal, using
	 * robocode coordinate system.
	 */
	public double getAbsoluteBearingToOwnGoal() {
		return getAbsoluteBearing(getX(), getY(), ownGoal.getCenterX(), 
				ownGoal.getCenterY());
	}
	
	/**
	 * Returns the absolute bearing from this robot to it's enemies goal, using
	 * robocode coordinate system.
	 */
	public double getAbsoluteBearingToEnemyGoal() {
		return getAbsoluteBearing(getX(), getY(), enemyGoal.getCenterX(), 
				enemyGoal.getCenterY());
	}
	
	
	/**
	 * Returns true if, when facing the ball, we are facing towards the enemy
	 * goal. i.e. the ball lies between us and the enemy goal on the x axis.
	 */
	private boolean towardsGoal(ScannedRobotEvent ball) {
		if(goalSide == null) {
			goalSide = getGoalSide();
		}
		System.out.println(Utils.normalRelativeAngle(getHeadingRadians() + 
				ball.getBearingRadians()) * goalSide < 0);
		return (Utils.normalRelativeAngle(getHeadingRadians() + 
				ball.getBearingRadians()) * goalSide < 0);
	}
	
	/**
	 * Adjusts heading in order to close distance to enemy. The further we are 
	 * from the enemy, the more direct the root towards them we take.
	 */
	public double headingAdjustment(double distance) {
		double currentDistance = Math.max(distance-circleDistance, 0);
		double normal = currentDistance /(1200-circleDistance);
		return Math.PI *  normal;
	}

	/**
	 * Wall avoidance algorithm. Projects a virtual stick in front of our robot.
	 * If the stick goes outside the bounds of the battlefield, we adjust the
	 * current heading in increments of PI/64 radians until the stick no longer
	 * projects outside the battlefield.
	 * 
	 * @param turn - current heading chosen on the basis of our targets position
	 * @param side - the side (left or right) of our bot the target is on. 
	 * @return double heading in radians we need to turn to to avoid the wall
	 */
	public double avoidWall(double turn, double side) {
		double heading = getRelativeHeading() + turn;
		double futureX = getX() + 120 * Math.sin(heading);
		double futureY = getY() + 120 * Math.cos(heading);
		
		while (!withinBounds(futureX, futureY)) {
			heading += Math.PI/64 * side * direction;
			futureX = getX() + 120 * Math.sin(heading);
			futureY = getY() + 120 * Math.cos(heading);
		}
		
		return heading - getRelativeHeading();
	}

	/** 
	 * Determine whether the scanned robot is to the left or right.
	 * 
	 * @return -1 if enemy is on robots left, 1 if on the right
	 */
	public int getSide(ScannedRobotEvent enemy) {
		return (enemy.getBearingRadians() < 0) ? -1 : 1;
	}

	/**
	 * Returns the robots heading based on direction of movement.
	 * i.e. if robot is moving backwards.
	 * 
	 * @return double - directed heading of our robot in radians
	 */
	public double getRelativeHeading() {
		if(direction == 1) {
			return getHeadingRadians();
		}
		return Utils.normalAbsoluteAngle(getHeadingRadians() - Math.PI);
	}

	/**
	 * Test for if x and y are within the bounds of the battlefield.
	 * 
	 * @param x coordinate
	 * @param y coordinate
	 * @return boolean point x,y is within field
	 */
	public boolean withinBounds(double x, double y) {
		return ((x > wallMargin) && (x < battlefieldWidth - wallMargin)
				&& (y > wallMargin) && (y < battlefieldHeight - wallMargin));
	}
	
	/**
	 * Handler for onHitRobotEvent. Change direction when we collide with a 
	 * robot
	 */
	public void onHitRobot(HitRobotEvent collision) {
		if(getVelocity() == 0) {
			direction *= -1;
			ahead(50 * direction);
		}
	}
	
	/**
	 * Handler for onHitRobotEvent. Change direction when we collide with a 
	 * robot
	 */
	public void onHitWall(HitRobotEvent collision) {
		if(getVelocity() == 0) {
			direction *= -1;
			ahead(50 * direction);
		}
	}
	
	/**
	 * Handler for WinEvent. Robot spins in a circle to celebrate his win.
	 */
	public void onWin (WinEvent event) {
		stop();
		turnRight(Double.POSITIVE_INFINITY);
	}
	
	 public void onPaint(Graphics2D g) {
	     // Set the paint color to a red half transparent color
	     g.setColor(new Color(0xff, 0x00, 0x00, 0x80));
	 
	     // Draw our own goal coloured Red
	     g.fillRect((int)ownGoal.getMinX(), (int)ownGoal.getMinY(), (int)ownGoal.width, (int)ownGoal.height);
	     
	  // Draw enemy goal coloured Green
	     g.setColor(new Color(0x00, 0xff, 0x00, 0x80));
	     g.fillRect((int)enemyGoal.getMinX(), (int)enemyGoal.getMinY(), (int)enemyGoal.width, (int)enemyGoal.height);
	     
	     double maxDeltaX = 5 * (enemyGoal.getCenterX() - lastBallX);
	     double maxDeltaY = 5 * (enemyGoal.getMaxY() - lastBallY);
	     double minDeltaX = 5 * (enemyGoal.getCenterX() - lastBallX);
	     double minDeltaY = 5 * (enemyGoal.getMinY() - lastBallY);
	     
	     int[] triPointsX = {(int) lastBallX, (int) (lastBallX - minDeltaX), (int) (lastBallX - maxDeltaX)};
	     int[] triPointsY = {(int) lastBallY, (int) (lastBallY - minDeltaY), (int) (lastBallY - maxDeltaY)};
	     Polygon polygon = new Polygon(triPointsX, triPointsY, 3);
	     
	     Point2D.Float us = new Point2D.Float((int) getX(),(int) getY());
	     Line2D.ptSegDist(triPointsX[0], triPointsY[0], triPointsX[1], triPointsX[1], us.getX(), us.getY());
	     
	     double currentX = getX();
	     double currentY = getY();
	     boolean polygonAbove = false;
	     boolean polygonBelow = false;
	     for(int i = 0; i < battlefieldHeight; i += (battlefieldHeight/40)) {
		    	if(polygon.contains(currentX, currentY + i)) {
		    		polygonAbove = true;
		    	}
		    	if(polygon.contains(currentX, currentY - i)) {
		    		polygonBelow = true;
		    	}
		    	if(polygonAbove || polygonBelow) {
		    		break;
		    	}
		    }
			
	     if(polygon.contains(us)) {
	    	// Set the paint color to a red half transparent color
		     g.setColor(new Color(0x00, 0xff, 0x00, 0x80));
	     } else if (polygonBelow){
	    	// Set the paint color to a red half transparent color
		     g.setColor(new Color(0xff, 0xA0, 0x00, 0x80));
	     } else {
	    	// Set the paint color to a red half transparent color
		     g.setColor(new Color(0xff, 0x00, 0xA0, 0x80));
	     }
	     g.fill(polygon);
	 }
}
