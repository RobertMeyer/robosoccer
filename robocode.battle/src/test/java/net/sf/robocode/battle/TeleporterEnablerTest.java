package net.sf.robocode.battle;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for TeleporterEnabler class.
 * @author Team Omega
 *
 */
public class TeleporterEnablerTest {
	@Test
	public void testInitialization() {
		Assert.assertFalse(TeleporterEnabler.isBlackholesEnabled());
		Assert.assertFalse(TeleporterEnabler.isTeleportersEnabled());
	}	
	
	@Test
	public void testToggleTeleporters() {
		TeleporterEnabler.enableTeleporters(true);
		Assert.assertTrue(TeleporterEnabler.isTeleportersEnabled());
		TeleporterEnabler.enableTeleporters(false);
		Assert.assertFalse(TeleporterEnabler.isTeleportersEnabled());
	}
	
	@Test
	public void testToggleBlackholes() {
		TeleporterEnabler.enableBlackholes(true);
		Assert.assertTrue(TeleporterEnabler.isBlackholesEnabled());
		TeleporterEnabler.enableBlackholes(false);
		Assert.assertFalse(TeleporterEnabler.isBlackholesEnabled());
	}
}
