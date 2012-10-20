package net.sf.robocode.battle.peer;

import static org.junit.Assert.*;

import java.util.ArrayList;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.BattleProperties;
import net.sf.robocode.core.Container;
import net.sf.robocode.host.IHostManager;
import net.sf.robocode.mode.ClassicMode;
import net.sf.robocode.repository.IRobotRepositoryItem;
import net.sf.robocode.security.HiddenAccess;
import net.sf.robocode.settings.ISettingsManager;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import robocode.control.RobotSpecification;

/**
 * Tests for the RobotPeer class.
 */
public class RobotPeerTest {
	IHostManager hostManager;
	IRobotRepositoryItem robotItem;
	ISettingsManager properties;
	TeamPeer team;

	RobotSpecification robotSpecification;
	Battle battle;

	BattleProperties battleProperties;

	@BeforeClass
	public static void setUpClass() {
		HiddenAccess.init();
		Container.init();
	}

	@Before
	public void setUp() {
		hostManager = Mockito.mock(IHostManager.class);
		team = Mockito.mock(TeamPeer.class);
		battle = Mockito.mock(Battle.class);

		robotItem = Mockito.mock(IRobotRepositoryItem.class);
		robotSpecification = HiddenAccess.createSpecification(robotItem, "",
				"", "", "", "", "", "", "");
	}

	/**
	 * Test the kill method.
	 */
	@Test
	public void testKill() {
		Mockito.when(battle.getBattleMode()).thenReturn(new ClassicMode());
		RobotPeer peer = new RobotPeer(battle, hostManager, robotSpecification,
				0, null, 0, null);
		peer.initializeRound(new ArrayList<RobotPeer>(), new double[1][3]);

		assertTrue("Peer should start alive", peer.isAlive());
		assertFalse("Peer should not start dead", peer.isDead());
		peer.kill();
		assertTrue("Peer should be dead after being killed", peer.isDead());
		assertFalse("Peer should not be alive after being killed",
				peer.isAlive());
	}

	/**
	 * A sample test showing how to set attributes in the peer's RobotStatics
	 * object.
	 */
	@Test
	public void testIsDroid() {
		// Data in the RobotStatics object is set by calling methods of the
		// robotItem object on construction.

		Mockito.when(robotItem.isDroid()).thenReturn(true);
		RobotPeer droid = new RobotPeer(battle, hostManager,
				robotSpecification, 0, null, 0, null);
		assertTrue("Droid should be a droid", droid.isDroid());
	}

}
