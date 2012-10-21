package net.sf.robocode.battle.peer;

import static org.junit.Assert.*;

import java.awt.List;
import java.util.ArrayList;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.BattleProperties;
import net.sf.robocode.battle.peer.BallPeer;
import net.sf.robocode.battle.peer.RobotPeer;
import net.sf.robocode.battle.peer.SoccerTeamPeer;
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
public class BallPeerTest {
	IHostManager hostManager;
	IRobotRepositoryItem robotItem;
	ISettingsManager properties;
	SoccerTeamPeer team;

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
		team = Mockito.mock(SoccerTeamPeer.class);
		battle = Mockito.mock(Battle.class);

		robotItem = Mockito.mock(IRobotRepositoryItem.class);
		robotSpecification = HiddenAccess.createSpecification(robotItem, "",
				"", "", "", "", "", "", "");
	}

	/**
	 * Test the energy levels
	 */
	@Test
	public void testGetEnergy() {
		Mockito.when(battle.getBattleMode()).thenReturn(new ClassicMode());
		BallPeer peer = new BallPeer(battle, hostManager, robotSpecification,
				0, team, 0);
		peer.initializeRound(new ArrayList<RobotPeer>(), new double[1][3]);
		
		assertEquals("Starting energy should be 200", 200.00, peer.getEnergy(), 0.1);
		peer.drainEnergy();
		assertEquals("Energy should never drop below 200", 200.00, peer.getEnergy(), 0.1);
	}
	
}