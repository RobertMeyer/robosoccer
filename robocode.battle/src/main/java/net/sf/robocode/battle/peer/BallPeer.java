package net.sf.robocode.battle.peer;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.host.IHostManager;
import net.sf.robocode.io.Logger;
import robocode.control.RobotSpecification;
import static robocode.util.Utils.normalAbsoluteAngle;
import static robocode.util.Utils.normalNearAbsoluteAngle;

/**
 * BallPeer class is used to add alter game physics for Ball type robots. Robots
 * assigned a BallPeer will turn and change velocity instantaneously, and will
 * not take damage for any reason. This facilitates writing a Ball type robot
 * with physical behaviour more consistent with a ball. See 
 * samples.soccer.BallBot for the Ball specific code related to movement.
 * 
 * @author Carl Hattenfels - team-G1
 * @see RobotPeer
 * @see BallBot
 */
public final class BallPeer extends RobotPeer {

    public BallPeer(Battle battle, IHostManager hostManager,
                    RobotSpecification robotSpecification, int duplicate, TeamPeer team,
                    int robotIndex) {

        super(battle, hostManager,
              robotSpecification, duplicate, team,
              robotIndex, null);
    }

    /**
     * Returns the new velocity based on the current velocity and distance to move.
     * Note that in BallPeer, velocity is updated immediately to whatever 
     * velocity is provided by parameter velocity.
     * @param velocity the current velocity
     * @param distance the distance to move
     * @return the new velocity based on the current velocity and distance to move
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
            goalVel = Math.min(getMaxVelocity(distance),
                               currentCommands.getMaxVelocity());
        }

		return goalVel;
	}
	
    /**
     * Robots that use a BallPeer do not take any damage. This method does 
     * nothing.
     */
	public void updateEnergy(double delta) {
		// Do nothing
	}
	
	/**
	 * Headings are updated instantaneously for BallPeer.
	 */
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
	
	@Override
	public double getEnergy() {
		return 200.0;
	}
}
