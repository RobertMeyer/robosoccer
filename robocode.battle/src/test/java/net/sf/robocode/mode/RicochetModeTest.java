package net.sf.robocode.mode;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class RicochetModeTest {

	RicochetMode rm;
	
	@Before
	public void setup() {
		rm = new RicochetMode();
	}
	
	@Test
	public void testToString() {
		assertEquals("Representation is incorrect", "Ricochet Mode", rm.toString());
	}

	@Test
	public void testDiscription() {
		assertEquals("Description is incorrect", "Ricochet Mode: WATCH THE WALLS", rm.getDescription());
	}
	
}
