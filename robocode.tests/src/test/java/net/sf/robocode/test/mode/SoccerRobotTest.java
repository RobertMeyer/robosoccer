package net.sf.robocode.test.mode;

import robocode.control.events.RoundStartedEvent;
import robocode.control.events.TurnEndedEvent;
import robocode.control.snapshot.IRobotSnapshot;
import robocode.control.snapshot.ITurnSnapshot;
import net.sf.robocode.test.helpers.Assert;
import net.sf.robocode.test.helpers.RobocodeTestBed;

/**
 * Test class for testing addition methods provided by SoccerRobot above 
 * TeamRobot. Namely, tests GetEnemyGoal() and getOwnGoal() and also checks
 * that the body colour of the robot is set by the team colour.
 * @author Carl Hattenfels - team-G1
 */
public class SoccerRobotTest extends RobocodeTestBed {
	
	ITurnSnapshot snapshot;
	
	@Override
	public String getRobotNames() {
		return "tested.robots.SoccerType, tested.robots.SoccerType";
	}

	@Override  
	public void onRoundStarted(final RoundStartedEvent event) {
		snapshot = event.getStartSnapshot();
		
		IRobotSnapshot robot1 = snapshot.getRobots()[0];
		IRobotSnapshot robot2 = snapshot.getRobots()[1];
		System.out.println(robot1.getOwnGoal());
		// Test Goal retrieval functions.
		Assert.assertNear(750.0, robot1.getEnemyGoal().getCenterX());
		Assert.assertNear(300.0, robot1.getEnemyGoal().getCenterY());
		Assert.assertNear(50.0, robot2.getOwnGoal().getCenterX());
		Assert.assertNear(400.0, robot2.getOwnGoal().getCenterY());
	}
	
	@Override
	public void onTurnEnded(TurnEndedEvent event) {
		super.onTurnEnded(event);
		IRobotSnapshot robot1 = snapshot.getRobots()[0];
		IRobotSnapshot robot2 = snapshot.getRobots()[1];
		if(event.getTurnSnapshot().getTurn() == 1) {
			// Test Goals remain unchanged
			Assert.assertNear(750.0, robot1.getEnemyGoal().getCenterX());
			Assert.assertNear(300.0, robot1.getEnemyGoal().getCenterY());
			Assert.assertNear(50.0, robot2.getOwnGoal().getCenterX());
			Assert.assertNear(400.0, robot2.getOwnGoal().getCenterY());
			Assert.assertTrue(robot1.getBodyColor() == -16776961);
			Assert.assertTrue(robot2.getBodyColor() != -65536);
		} else if(event.getTurnSnapshot().getTurn() == 20) {
			// Test Colour cannot be changes by user.
			Assert.assertTrue(robot1.getBodyColor() == -16776961);
			Assert.assertTrue(robot2.getBodyColor() != -65536);
		}
	}

}
