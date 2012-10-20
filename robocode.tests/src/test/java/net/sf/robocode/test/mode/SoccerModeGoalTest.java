package net.sf.robocode.test.mode;

import static org.junit.Assert.*;

import java.util.Random;

import net.sf.robocode.battle.item.BoundingRectangle;
import net.sf.robocode.mode.SoccerMode;

import org.junit.Test;

/**
 * Basic unit test to make sure soccer goals are working as
 * Intended.
 * 
 * @author Benjamin Evenson
 * @version 1.0
 */
public class SoccerModeGoalTest {

	@Test
	public void testInitialPositions() {
		SoccerMode mode = new SoccerMode();
		mode.computeInitialPositions(null, 800, 600, 1);
		BoundingRectangle dummyBall = new BoundingRectangle(1, 300, 16, 16);
		BoundingRectangle goal1 = mode.getGoals()[0];
		BoundingRectangle goal2 = mode.getGoals()[1];
		
		// Test Goal 1 in correct position
		net.sf.robocode.test.helpers.Assert.assertNear(100, goal1.width);
		net.sf.robocode.test.helpers.Assert.assertNear(236, goal1.height);
		net.sf.robocode.test.helpers.Assert.assertNear(0, goal1.getX());
		net.sf.robocode.test.helpers.Assert.assertNear(182, goal1.getY());
		
		// Test Goal 2 in correct position
		net.sf.robocode.test.helpers.Assert.assertNear(100, goal2.width);
		net.sf.robocode.test.helpers.Assert.assertNear(236, goal2.height);
		net.sf.robocode.test.helpers.Assert.assertNear(700, goal2.getX());
		net.sf.robocode.test.helpers.Assert.assertNear(182, goal2.getY());
		
		// Make sure random is seeded
		Random rndNum = new Random(System.currentTimeMillis());
		
		// Test Goal 1 by randomly generate 1000 numbers inside goal
		for (int i = 0; i < 1000; i++) {
			double posX = rndNum.nextDouble() * (100 - 0);
			double posY = 182 + rndNum.nextDouble() * (418 - 182);
			dummyBall.setRect(posX, posY, 16, 16);
			boolean testScore = dummyBall.intersects(goal1);
			assertTrue(testScore);
		}
		
		// Test Goal 2 by randomly generating 1000 numbers inside goal
		for (int i = 0; i < 1000; i++) {
			double posX = 700 + rndNum.nextDouble() * (800 - 700);
			double posY = 182 + rndNum.nextDouble() * (418 - 182);
			dummyBall.setRect(posX, posY, 16, 16);
			boolean testScore = dummyBall.intersects(goal2);
			assertTrue(testScore);
		}
	}

}
