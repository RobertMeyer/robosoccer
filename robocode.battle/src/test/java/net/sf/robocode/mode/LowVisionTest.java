/**
 * 
 */
package net.sf.robocode.mode;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import robocode.BattleRules;

/**
 * @author Terry
 *
 */
public class LowVisionTest {
	
	LowVision LowVis;
	
	@Before
	public void setup() {
		LowVis = new LowVision();
	}
	
	@Test
	public void testToString() {
		assertEquals("Mode name is incorrect", "Low Vision Mode", LowVis.toString());
	}

	/*
	@Test
	public void testDiscription() {
		assertEquals("Description is incorrect", "Modify all robots' vision. (0 = blind, 100 = standard vision)", LowVis.getDescription());
	}
	this test is not consistent with what you have defined in LowVision.java
	*/

}
