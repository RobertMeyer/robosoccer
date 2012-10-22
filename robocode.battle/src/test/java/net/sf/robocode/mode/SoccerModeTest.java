package net.sf.robocode.mode;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import net.sf.robocode.battle.BallBot;
import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.BoundingRectangle;
import net.sf.robocode.battle.peer.SoccerTeamPeer;
import net.sf.robocode.host.IHostManager;

import org.junit.*;
import org.mockito.Mockito;

import robocode.BattleResults;

/**
 * Basic Test for Soccer Mode.
 * 
 * @author kirstypurcell
 */
public class SoccerModeTest {
	
	SoccerMode s;

	@Before
	public void setUp() {
		s = new SoccerMode();
	}

	@Test
	public void testToString() {
		assertEquals("Incorrect String Representation", "Soccer Mode",
				s.toString());
	}

	@Test
	public void testAddModeRobots() {
		String robots = "robots";
		assertEquals("Did not add Ball robot", "robots, robots.theBall*",
				s.addModeRobots(robots));
	}

	@Test
	public void testGetDescription() {
		assertEquals("Incorrect Description", "Robocode soccer.",
				s.getDescription());
	}

	@Test
	public void testComputeInitialPositions() {
		double height = 800;
		double width = 800;
		int robotsCount = 4;
		double[][] expectedInitialPositions = new double[robotsCount + 1][3];
		// Horizontal spacing between columns of robots.
		double xOffset = ((width / 2))
				/ (1 + Math.max(1, Math.ceil(robotsCount / 2 / 3.0)));

		for (int i = 0; i < robotsCount / 2; i++) {
			// Team 1 Initial Positions (Left side of field).
			expectedInitialPositions[i][0] = (width / 2)
					- (((i / 3) + 1) * xOffset);
			expectedInitialPositions[i][1] = (0.2 * height)
					+ (((0.9 * height) / 3) * (i % 3));
			expectedInitialPositions[i][2] = (Math.PI / 2.0);

			// Team 2 Initial Positions (Right side of field).
			expectedInitialPositions[(i + robotsCount / 2)][0] = (width / 2)
					+ (((i / 3) + 1) * xOffset);
			expectedInitialPositions[(i + robotsCount / 2)][1] = (0.2 * height)
					+ (((0.9 * height) / 3) * (i % 3));
			expectedInitialPositions[(i + robotsCount / 2)][2] = 3 * (Math.PI / 2.0);
		}

		// Ball starting position..
		expectedInitialPositions[robotsCount][0] = (width / 2);
		expectedInitialPositions[robotsCount][1] = (height / 2);
		expectedInitialPositions[robotsCount][2] = 0;
		assertArrayEquals("Incorrect Initial Positions",
				expectedInitialPositions,
				s.computeInitialPositions("", width, height, robotsCount));
	}

	@Test
	public void testGuiOptions() {
		s.setGuiOptions();
		assertEquals("Show Results is false", true, s.getGuiOptions()
				.getShowResults());
		assertEquals("Show Robot Buttons is true", false, s.getGuiOptions()
				.getShowRobotButtons());
	}

}
