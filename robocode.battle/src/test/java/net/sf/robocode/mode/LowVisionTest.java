/**
 * 
 */
package net.sf.robocode.mode;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.swing.JPanel;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Terry
 *
 */
public class LowVisionTest {
	
	LowVision LowVis;
	
	/**
	 * Testing Unit of setup method
	 */
	@Before
	public void setup() {
		LowVis = new LowVision();
	}
	
	/**
	 * Testing Unit of ToString method
	 */
	@Test
	public void testToString() {
		assertEquals("Mode name is incorrect", "Low Vision Mode", LowVis.toString());
	}

	/**
	 * Testing Unit of Discription method
	 */
	@Test
	public void testDiscription() {
		assertEquals("Description is incorrect", "Modify all robots' vision. (0 = blind, 100 = standard vision)", LowVis.getDescription());
	}
	
	/**
	 * Testing unit of RulesPanel method
	 */
	@Test
	public void testRulesPanel() {
		JPanel panel = LowVis.getRulesPanel();
		assertNotNull("Panel returned is null.", panel);
		assertTrue("Panel returned is of wrong type.", panel instanceof JPanel);
	}
	
	/**
	 * Testing unit of ModifyVision method
	 */
	@Test
	public void testModifyVision() {
	// this test checks the method if it does modify the radar scan radius.
	int standard = 1200;
	int modifier = 80/100;
	int result = standard*modifier;
	assertEquals("Did not return " + result + " as the radar scan radius", result, LowVis.modifyVision(standard, null),1200);
	}
	

}
