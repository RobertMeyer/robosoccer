package net.sf.robocode.mode;

import static org.junit.Assert.*;
import java.util.ArrayList;
import net.sf.robocode.battle.BattleManager;
import org.junit.*;

/**
 * JUnit test for Spike Mode. 
 * 
 * @author Team - MCJJ
 * @author Jake Ching Leong Ong
 */

public class SpikeModeTest {
	
	private BattleManager battleManager = new BattleManager(null, null, null, null, null, null);
	private ArrayList<Integer> posX = new ArrayList<Integer>();
	private ArrayList<Integer> posY = new ArrayList<Integer>();
	
	@Before
	public void setup() {
		posX.add(50);
		posY.add(100);
		battleManager.saveSpikePos(posX, posY);
	}
	
	@Test
	public void testFirstSpikePosX() {
		ArrayList<Integer> spikeX = battleManager.getSpikePosX();
		assertEquals("The position (X) of first spike should 50 but was "+spikeX.get(0), 50, (int) spikeX.get(0));
	}
	
	@Test 
	public void testFirstSpikePosY() {
		ArrayList<Integer> spikeY = battleManager.getSpikePosY();
		assertEquals("The position (Y) of first spike should 100 but was "+spikeY.get(0), 100, (int) spikeY.get(0));
	}
	
	@Test 
	public void testSecondSpikePosX() {
		posX.add(150);
		ArrayList<Integer> spikeX = battleManager.getSpikePosX();
		assertEquals("The position (X) of second spike should 150 but was "+spikeX.get(1), 150, (int) spikeX.get(1));
	}

	@Test 
	public void testSecondSpikePosY() {
		posY.add(200);
		ArrayList<Integer> spikeY = battleManager.getSpikePosY();
		assertEquals("The position (Y) of second spike should 200 but was "+spikeY.get(1), 200, (int) spikeY.get(1));
	}
	
}