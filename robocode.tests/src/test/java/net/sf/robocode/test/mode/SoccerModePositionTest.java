package net.sf.robocode.test.mode;

import net.sf.robocode.mode.ClassicMode;
import net.sf.robocode.mode.SoccerMode;

import org.junit.Assert;
import org.junit.Test;

public class SoccerModePositionTest {
	
	// Robot headings for 270 and 90 degrees respectively. 
	private final static double FACE_LEFT = 4.71238898038469;
	private final static double FACE_RIGHT = 1.5707963267948966;

	@Test
	public void testInitialPositions() {
		ClassicMode mode = new SoccerMode();
		double[][] positions;
		
		double[][] twoBots = {{200.0,120.0,FACE_RIGHT}, 
				{600.0,120.0,FACE_LEFT}, 
				{400.0,300.0,0.0}};

		// Least number of bots allowed on the field.
		positions = mode.computeInitialPositions(null, 800, 600, 2);
		
		Assert.assertArrayEquals(twoBots, positions);
		
		// Test that computeInitialPositions truncates the last
		// bot for an odd number of bots.
		positions = mode.computeInitialPositions(null, 800, 600, 3);
		
		Assert.assertArrayEquals(twoBots, positions);
		
		double[][] sixBots = {{200.0,120.0,FACE_RIGHT}, 
				{200.0,300.0,FACE_RIGHT}, 
				{200.0,480.0,FACE_RIGHT}, 
				{600.0,120.0,FACE_LEFT}, 
				{600.0,300.0,FACE_LEFT}, 
				{600.0,480.0,FACE_LEFT}, 
				{400.0,300.0,0.0}};
		
		// Single column of three bots for each team.
		positions = mode.computeInitialPositions(null, 800, 600, 6);
		
		Assert.assertArrayEquals(sixBots, positions);
		
		double[][] twentyFourBots = {{320.0,120.0,FACE_RIGHT},
				{320.0,300.0,FACE_RIGHT},
				{320.0,480.0,FACE_RIGHT},
				{240.0,120.0,FACE_RIGHT},
				{240.0,300.0,FACE_RIGHT},
				{240.0,480.0,FACE_RIGHT},
				{160.0,120.0,FACE_RIGHT},
				{160.0,300.0,FACE_RIGHT},
				{160.0,480.0,FACE_RIGHT},
				{80.0,120.0,FACE_RIGHT},
				{80.0,300.0,FACE_RIGHT},
				{80.0,480.0,FACE_RIGHT},
				{480.0,120.0,FACE_LEFT},
				{480.0,300.0,FACE_LEFT},
				{480.0,480.0,FACE_LEFT},
				{560.0,120.0,FACE_LEFT},
				{560.0,300.0,FACE_LEFT},
				{560.0,480.0,FACE_LEFT},
				{640.0,120.0,FACE_LEFT},
				{640.0,300.0,FACE_LEFT},
				{640.0,480.0,FACE_LEFT},
				{720.0,120.0,FACE_LEFT},
				{720.0,300.0,FACE_LEFT},
				{720.0,480.0,FACE_LEFT},
				{400.0,300.0,0.0}};
		
		// Single column of three bots for each team.
		positions = mode.computeInitialPositions(null, 800, 600, 24);
		
		Assert.assertArrayEquals(twentyFourBots, positions);
	}

}
