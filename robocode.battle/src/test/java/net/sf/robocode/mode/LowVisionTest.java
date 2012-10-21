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
	 * Checks the string shown is similar with the expected output
	 */
	@Test
	public void testToString() {
		assertEquals("Mode name is incorrect", "Low Vision Mode", LowVis.toString());
	}

	/**
	 * Testing Unit of Description method
	 * Checks the string shown is similar with the expected output
	 */
	@Test
	public void testDiscription() {
		assertEquals("Description is incorrect", "This mode will reduce the vision of the Robots by a user specified amount.", LowVis.getDescription());
	}
	
	/**
	 * Testing unit of RulesPanel method
	 * 1st test that the rules panel has values
	 * 2nd test that the values are correct type
	 */
	@Test
	public void testRulesPanel() {
		JPanel panel = LowVis.getRulesPanel();
		assertNotNull("Panel returned is null.", panel);
		assertTrue("Panel returned is of wrong type.", panel instanceof JPanel);
	}
	
	/**
	 * Testing unit of ModifyVision method
	 * uses standard value of radar scan radius
	 * with a sample modifier of 80%
	 * test the outcome matches the expected results
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
