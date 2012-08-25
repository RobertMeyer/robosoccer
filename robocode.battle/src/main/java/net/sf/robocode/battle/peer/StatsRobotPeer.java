package net.sf.robocode.battle.peer;

import java.util.List;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.host.IHostManager;
import net.sf.robocode.peer.BulletCommand;
import robocode.control.RobotSpecification;

/**
 * StatsRobotPeer is an object that deals with game mechanics and rules, and
 * makes sure the robot abides by the rules, specifically stat robots, that
 * equip a variety of different items.
 * 
 * @author Jayke Anderson
 * @author Jarred Filmer
 * @author Andrew Schaul
 * @author Malcolm Inglis
 *
 */
public final class StatsRobotPeer extends RobotPeer {

	public StatsRobotPeer(Battle battle, IHostManager hostManager, 
			RobotSpecification robotSpecification, int duplicate, TeamPeer team,
			int robotIndex) {

		super(battle, hostManager, robotSpecification, duplicate, team, robotIndex);
	}

	@Override
	protected double getNewVelocity(double velocity, double distance) {
		// Will need to make adjustments here for acceleration and deceleration
		return 0;
	}

	@Override
	protected double getMaxVelocity(double distance) {
		// Make adjustments here for velocity
		return 0;
	}

	@Override
	protected double maxDecel(double speed) {
		// Make adjustments for deceleration here
		return 0;
	}

	@Override
	protected void updateGunHeat() {
		// Make adjustments here for gun cooling rate.
	}

	@Override
	protected void scan(double lastRadarHeading, List<RobotPeer> robots) {
		// Make adjustments here for radar scan radius
	}

	@Override
	protected void updateGunHeading() {
		// Make adjustments here for gun turn rate.
	}

	@Override
	protected void updateHeading() {
		// Make adjustments here for robot turn rate
	}

	@Override
	protected void updateRadarHeading() {
		// Make adjustments here for radar turn rate.
	}

	@Override
	protected void fireBullets(List<BulletCommand> bulletCommands) {
		// Make adjustments here for maximum and minimum bullet power
	}
}
