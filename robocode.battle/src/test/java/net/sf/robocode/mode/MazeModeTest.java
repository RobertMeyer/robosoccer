package net.sf.robocode.mode;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.BattleProperties;
import net.sf.robocode.core.Container;
import net.sf.robocode.security.HiddenAccess;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import robocode.BattleRules;

import static org.junit.Assert.*;

public class MazeModeTest {
	private Battle battle;
	private BattleProperties bp;
	private BattleRules br;
	private MazeMode maze;
	
	@Before
	public void setup() {
		HiddenAccess.init();
		Container.init();
		
		br = HiddenAccess.createRules(800, 600, 1, 0.1, 450, false, null);
		
		bp = Mockito.mock(BattleProperties.class);
		Mockito.when(bp.getBattlefieldWidth()).thenReturn(800);
		Mockito.when(bp.getBattlefieldHeight()).thenReturn(600);
		Mockito.when(bp.getGunCoolingRate()).thenReturn(0.1);
		Mockito.when(bp.getInactivityTime()).thenReturn((long) 450);
		Mockito.when(bp.getNumRounds()).thenReturn(1);
		Mockito.when(bp.getHideEnemyNames()).thenReturn(false);
		Mockito.when(bp.getInitialPositions()).thenReturn(null);
		Mockito.when(bp.getBattleMode()).thenReturn(new MazeMode());
		
		maze = Mockito.mock(MazeMode.class);
		battle = Mockito.mock(Battle.class);
		Mockito.when(battle.getBattleMode()).thenReturn(new MazeMode());
	}
	
	@Test
	public void testToString() {
		assertEquals("Battle is in the incorrect mode", battle.getBattleMode().toString(), "Maze Mode");
	}
	
	@Test
	public void testDescription() {
		assertEquals("The description for the mode is incorrect", battle.getBattleMode().getDescription(), 
				"A mode with a maze that robots have to navigate.");
	}
}
