package sample;

import java.awt.geom.Point2D;

import robocode.HitByBulletEvent;
import robocode.HitWallEvent;
import robocode.HouseRobot;
import robocode.Rules;
import robocode.ScannedRobotEvent;
import static robocode.util.Utils.*;

public class HouseRobotTest extends HouseRobot {

    private final static double CORNER_SIZE = 80;
    private final static short NORTH_WEST = -135;
    private final static short NORTH_EAST = -45;
    private final static short SOUTH_EAST = 45;
    private final static short SOUTH_WEST = 135;
    private int moveDirection = 1;
    private boolean inCorner;
    private boolean killThem;
    private short selectedCorner;

    @Override
    public void run() {
        determineCorner();
        moveToCorner();
        while (true) {
            setTurnGunRight(180);
            setTurnGunLeft(180);
            ahead(moveDirection * 5);

        }
    }

    private void moveToCorner() {
        goToCorner();
        setTurnLeft(normalAbsoluteAngleDegrees((smallestTurn(getHeading() + selectedCorner))));
    }
    
	/**
	 * A private helper method to calculate the smallest possible turn to
	 * achieve a required angle.
	 * 
	 * 
	 * @param angle
	 *            The angle that is being reduced to a more efficient angle
	 * 
	 * @return Returns the smallest angle to achieve the required turn
	 */
	private double smallestTurn(double angle) {

		// If the angle is greater than 180 degrees, subtract 360 to obtain
		// the shortest angle, which will be in the opposite direction
		if (angle > 180)
			angle -= 360;

		// If the angle is less than -180 degrees, add 360 to obtain the
		// shortest angle, which will be in the opposite direction
		if (angle < -180)
			angle += 360;

		return angle;
	}

    // START COPIED CODE BLOCK //////////////////////////////////////////
	/*
     * Code copied from:
     * http://old.robowiki.net/robowiki?Movement/CodeSnippetBasicGoTo
     */
    /**
     * Finds the bearing between two points.
     *
     * @param source
     * @param target
     * @return
     */
    private double absoluteBearingRadians(Point2D source, Point2D target) {
        return Math.atan2(target.getX() - source.getX(),
                          target.getY() - source.getY());
    }

    private Point2D getRobotLocation() {
        return new Point2D.Double(getX(), getY());
    }

    private void goTo(Point2D point) {
        turnRightRadians(normalRelativeAngle(absoluteBearingRadians(
                getRobotLocation(), point) - getHeadingRadians()));
        ahead(getRobotLocation().distance(point));
    }

    // END COPIED CODE BLOCK //////////////////////////////////////////
    private void goToCorner() {
        switch (selectedCorner) {
            case NORTH_EAST:
                goTo(new Point2D.Double(CORNER_SIZE, getBattleFieldHeight()
                        - CORNER_SIZE));
                break;
            case NORTH_WEST:
                goTo(new Point2D.Double(getBattleFieldWidth() - CORNER_SIZE,
                                        getBattleFieldHeight() - CORNER_SIZE));
                break;
            case SOUTH_EAST:
                goTo(new Point2D.Double(CORNER_SIZE, CORNER_SIZE));
                break;
            case SOUTH_WEST:
                goTo(new Point2D.Double(getBattleFieldWidth() - CORNER_SIZE,
                                        CORNER_SIZE));
                break;
            default:
                break;
        }

    }

    private void determineCorner() {
        double cx = getBattleFieldWidth() / 2;
        double cy = getBattleFieldHeight() / 2;
        if (getX() < cx) {
            if (getY() < cy) {
                selectedCorner = SOUTH_EAST;
            } else {
                selectedCorner = NORTH_EAST;
            }
        } else {
            if (getY() < cy) {
                selectedCorner = SOUTH_WEST;
            } else {
                selectedCorner = NORTH_WEST;
            }
        }
    }

    @Override
    public void onHitWall(HitWallEvent event) {
        moveDirection = -moveDirection;
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent event) {
        if (!inCorner) {
            return;
        }
        if (event.getDistance() < 30) {
            fire(Rules.MAX_BULLET_POWER);
        }
    }

    @Override
    public void onHitByBullet(HitByBulletEvent event) {
        //turnLeft(event.getBearing());
        //target = event.getBullet().getName();
        // TODO something useful
        //turnGunRight(event.);
    }
}
