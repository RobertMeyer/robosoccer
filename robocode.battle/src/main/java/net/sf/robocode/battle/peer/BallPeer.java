package net.sf.robocode.battle.peer;

import static java.lang.Math.abs;
import static java.lang.Math.min;
import net.sf.robocode.battle.Battle;
import net.sf.robocode.host.IHostManager;
import net.sf.robocode.io.Logger;
import robocode.Rules;
import robocode.control.RobotSpecification;
import static robocode.util.Utils.normalAbsoluteAngle;
import static robocode.util.Utils.normalNearAbsoluteAngle;

public final class BallPeer extends RobotPeer {

    public BallPeer(Battle battle, IHostManager hostManager,
                    RobotSpecification robotSpecification, int duplicate, TeamPeer team,
                    int robotIndex) {

        super(battle, hostManager,
              robotSpecification, duplicate, team,
              robotIndex);
    }

    /**
     * Returns the new velocity based on the current velocity and distance to move.
     *
     * @param velocity the current velocity
     * @param distance the distance to move
     * @return the new velocity based on the current velocity and distance to move
     *
     * This is Patrick Cupka (aka Voidious), Julian Kent (aka Skilgannon), and Positive's method described here:
     *   http://robowiki.net/wiki/User:Voidious/Optimal_Velocity#Hijack_2
     */
    @Override
    protected double getNewVelocity(double velocity, double distance) {
        if (distance < 0) {
            // If the distance is negative, then change it to be positive
            // and change the sign of the input velocity and the result
            return -getNewVelocity(-velocity, -distance);
        }

        final double goalVel;

        if (distance == Double.POSITIVE_INFINITY) {
            goalVel = currentCommands.getMaxVelocity();
        } else {
            goalVel = Math.min(getMaxVelocity(distance) * 4,
                               currentCommands.getMaxVelocity() * 4);
        }

		if (velocity >= 0) {
			return Math.max(velocity - getRobotDeceleration(), Math.min(
					goalVel, velocity + getRobotAcceleration()));
		}
		// else
		return Math.max(velocity - getRobotAcceleration(), 
				Math.min(goalVel, velocity + maxDecel(-velocity)));
	}
	
	protected void updateHeading() {
		boolean normalizeHeading = true;
		
		if (currentCommands.getBodyTurnRemaining() > 0) {	
			bodyHeading += currentCommands.getBodyTurnRemaining();
			gunHeading += currentCommands.getBodyTurnRemaining();
			radarHeading += currentCommands.getBodyTurnRemaining();
			if (currentCommands.isAdjustGunForBodyTurn()) {
				currentCommands.setGunTurnRemaining(
						currentCommands.getGunTurnRemaining() - currentCommands.getBodyTurnRemaining());
			}
			if (currentCommands.isAdjustRadarForBodyTurn()) {
				currentCommands.setRadarTurnRemaining(
						currentCommands.getRadarTurnRemaining() - currentCommands.getBodyTurnRemaining());
			}
			currentCommands.setBodyTurnRemaining(0);
		} else if (currentCommands.getBodyTurnRemaining() < 0) {
			bodyHeading += currentCommands.getBodyTurnRemaining();
			gunHeading += currentCommands.getBodyTurnRemaining();
			radarHeading += currentCommands.getBodyTurnRemaining();
			if (currentCommands.isAdjustGunForBodyTurn()) {
				currentCommands.setGunTurnRemaining(
						currentCommands.getGunTurnRemaining() - currentCommands.getBodyTurnRemaining());
			}
			if (currentCommands.isAdjustRadarForBodyTurn()) {
				currentCommands.setRadarTurnRemaining(
						currentCommands.getRadarTurnRemaining() - currentCommands.getBodyTurnRemaining());
			}
			currentCommands.setBodyTurnRemaining(0);
		} else {
			normalizeHeading = false;
		}


        if (normalizeHeading) {
            if (currentCommands.getBodyTurnRemaining() == 0) {
                bodyHeading = normalNearAbsoluteAngle(bodyHeading);
            } else {
                bodyHeading = normalAbsoluteAngle(bodyHeading);
            }
        }
        if (Double.isNaN(bodyHeading)) {
            Logger.realErr.println("HOW IS HEADING NAN HERE");
        }
    }
}
