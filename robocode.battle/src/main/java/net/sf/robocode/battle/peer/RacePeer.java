package net.sf.robocode.battle.peer;

import java.awt.geom.Arc2D;
import java.util.List;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.Waypoint;
import net.sf.robocode.host.IHostManager;
import net.sf.robocode.host.RobotStatics;
import net.sf.robocode.host.proxies.IHostingRobotProxy;
import net.sf.robocode.io.Logger;
import net.sf.robocode.serialization.RbSerializer;
import robocode.HitWallEvent;
import robocode.RobotAttribute;
import robocode.Rules;
import robocode.WaypointPassedEvent;
import robocode.control.RobotSpecification;
import robocode.control.snapshot.RobotState;
import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.atan2;
import static java.lang.Math.tan;
import static robocode.util.Utils.normalAbsoluteAngle;
import static robocode.util.Utils.normalNearAbsoluteAngle;
import static robocode.util.Utils.normalRelativeAngle;
import net.sf.robocode.battle.item.BoundingRectangle;
import net.sf.robocode.battle.item.ItemDrop;

public final class RacePeer extends RobotPeer {

	//raceMode int
	protected int currentWaypointIndex;
	
	public RacePeer(Battle battle, IHostManager hostManager, RobotSpecification robotSpecification, 
			int duplicate, TeamPeer team, int robotIndex) {
		
		super(battle, hostManager,robotSpecification, duplicate, team, robotIndex);
		currentWaypointIndex = 0; //maybe change to 1 depending on starting waypoint

	}
	
	@Override
	protected void checkWallCollision() {
		boolean hitWall = false;
		double fixx = 0, fixy = 0;
		double angle = 0;

		if (x > getBattleFieldWidth() - HALF_WIDTH_OFFSET) {
			hitWall = true;
			fixx = getBattleFieldWidth() - HALF_WIDTH_OFFSET - x;
			angle = normalRelativeAngle(PI / 2 - bodyHeading);
		}

		if (x < HALF_WIDTH_OFFSET) {
			hitWall = true;
			fixx = HALF_WIDTH_OFFSET - x;
			angle = normalRelativeAngle(3 * PI / 2 - bodyHeading);
		}

		if (y > getBattleFieldHeight() - HALF_HEIGHT_OFFSET) {
			hitWall = true;
			fixy = getBattleFieldHeight() - HALF_HEIGHT_OFFSET - y;
			angle = normalRelativeAngle(-bodyHeading);
		}

		if (y < HALF_HEIGHT_OFFSET) {
			hitWall = true;
			fixy = HALF_HEIGHT_OFFSET - y;
			angle = normalRelativeAngle(PI - bodyHeading);
		}

		if (hitWall) {
			addEvent(new HitWallEvent(angle));

			// only fix both x and y values if hitting wall at an angle
			if ((bodyHeading % (Math.PI / 2)) != 0) {
				double tanHeading = tan(bodyHeading);

				// if it hits bottom or top wall
				if (fixx == 0) {
					fixx = fixy * tanHeading;
				} // if it hits a side wall
				else if (fixy == 0) {
					fixy = fixx / tanHeading;
				} // if the robot hits 2 walls at the same time (rare, but just in case)
				else if (abs(fixx / tanHeading) > abs(fixy)) {
					fixy = fixx / tanHeading;
				} else if (abs(fixy * tanHeading) > abs(fixx)) {
					fixx = fixy * tanHeading;
				}
			}
			x += fixx;
			y += fixy;

			x = (HALF_WIDTH_OFFSET >= x)
					? HALF_WIDTH_OFFSET
					: ((getBattleFieldWidth() - HALF_WIDTH_OFFSET < x) ? getBattleFieldWidth() - HALF_WIDTH_OFFSET : x);
			y = (HALF_HEIGHT_OFFSET >= y)
					? HALF_HEIGHT_OFFSET
					: ((getBattleFieldHeight() - HALF_HEIGHT_OFFSET < y) ? getBattleFieldHeight() - HALF_HEIGHT_OFFSET : y);

			// Update energy, but do not reset inactiveTurnCount
			if (isBotzilla() || isHouseRobot()) { // The house robot will not get damage from walls.
				// Do nothing
			} else if (statics.isAdvancedRobot()) {
				setEnergy(energy - Rules.getWallHitDamage(velocity), false);
			}

			updateBoundingBox();

			currentCommands.setDistanceRemaining(0);
			velocity = 0;
		}
		if (hitWall) {
			setState(RobotState.HIT_WALL);
		}
		
		//For RaceMode we need to check if a waypoint has been passed.
		//TODO figureout what distance we are using and where to get WAypoint object from. 	
		checkWaypointPass(way, 43.32);
	}
	
	/**
     * 
     * @param waypoint The Maps Waypoint Object.
     * @param waypointDistance The maximum perpendicular distance from the robot that a waypoint
     * can be and still be scanned.
     */
    private void checkWaypointPass(Waypoint waypoint, Double waypointDistance){

    	//calculate the bearing to the waypoint relative to the robots Heading.
    	 double relativeBearingtoWaypoint = bodyHeading + ((Math.PI/2)-atan2(waypoint.getSingleWaypointY(
    			currentWaypointIndex)-y, waypoint.getSingleWaypointX(currentWaypointIndex)-x));
    	
    	if(robocode.util.Utils.isNear(relativeBearingtoWaypoint, bodyHeading - (Math.PI/2)) | 
    			robocode.util.Utils.isNear(relativeBearingtoWaypoint, bodyHeading + (Math.PI/2))){
    		double dx = waypoint.getSingleWaypointX(currentWaypointIndex)-x;
    		double dy = waypoint.getSingleWaypointY(currentWaypointIndex)-y;
    		double  distToWay = Math.hypot(dx, dy);

    		//Check if the waypoint is at the maximum distance from the robot or closer.
    		if(robocode.util.Utils.isNear(distToWay, waypointDistance) | distToWay < waypointDistance){
    			currentWaypointIndex += 1;
    			relativeBearingtoWaypoint = bodyHeading + ((Math.PI/2)-atan2( waypoint.getSingleWaypointY(
    					currentWaypointIndex)-y, waypoint.getSingleWaypointX(currentWaypointIndex)-x));
    			dx = waypoint.getSingleWaypointX(currentWaypointIndex)-x;
        		dy = waypoint.getSingleWaypointY(currentWaypointIndex)-y;
        		//create the new WaypointPassedEvent
    			addEvent(new WaypointPassedEvent(currentWaypointIndex, waypoint.getSingleWaypointX(
    					 currentWaypointIndex), waypoint.getSingleWaypointY(currentWaypointIndex), 
    					 relativeBearingtoWaypoint, Math.hypot(dx, dy), distToWay));
    		}
    	}
    	
    }
}