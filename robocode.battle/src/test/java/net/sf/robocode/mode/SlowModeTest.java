package net.sf.robocode.mode;

import static org.junit.Assert.*;

import javax.swing.JPanel;
import org.junit.*;


public class SlowModeTest {

	protected ClassicMode cm;
	
	@Before
	public void setup() {
		cm = new SlowMode();
	}
	
	@Test
	public void testToString() {
		assertTrue(toStringIsString(cm, "Slow Mode"));
	}
	
	@Test
	public void testDescription() {
		assertTrue(descriptionIsString(cm, "Robots move at half speed."));
	}
	
	@Test
	public void testRulesPanel() {
		JPanel jp = cm.getRulesPanel();
		assertNotNull("Panel returned is null.", jp);
		assertTrue("Panel returned is of wrong type.", jp instanceof JPanel);
	}
	
	@Ignore
	@Test
	public void testModifyVelocity() {
		// this needs to be fixed to ensure that we get the information from
		// a hypothetical SlowModeRulesPanel - which is a private class in Slow
		// Mode
		double velocity = 1.56;
		double scale = 0.0;
		assertEquals("Did not return " + scale + " the velocity", velocity, cm.modifyVelocity(velocity, null), 0.01);
	}
	
	public boolean toStringIsString(ClassicMode cm, String s) {
		if (cm.toString().equals(s)) {
			return true;
		}
		return false;
	}
	
	public boolean descriptionIsString(ClassicMode cm, String s) {
		if (cm.getDescription().equals(s)) {
			return true;
		}
		return false;
	}
}
