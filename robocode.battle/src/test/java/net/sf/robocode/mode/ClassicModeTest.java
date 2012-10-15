package net.sf.robocode.mode;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import net.sf.robocode.battle.item.ItemDrop;

import org.junit.*;

public class ClassicModeTest {
	
	private static int TURN_LIMIT = 9000;
	private final static int TURNS_DISPLAYED_AFTER_ENDING = 30;
	
	private ClassicMode cm;
	
	@Before
	public void setup() {
		cm = new ClassicMode();
	}
	
	@Test
	public void testToString() {
		assertTrue(toStringIsString(cm, "Classic Mode"));
	}
	
	@Test
	public void testDescription() {
		assertTrue(descriptionIsString(cm, "Original robocode mode."));
	}
	
	@Test
	public void testRulesPanel() {
		assertNull("Rules Panel was not null", cm.getRulesPanel());
	}
	
	@Test
	public void testRulesPanelValues() {
		assertNull("Rules Panel Values was not null", cm.getRulesPanelValues());
	}
	
	@Test
	public void testSetObstacleNum() {
		assertEquals("ClassicMode should have no obstacles", 0, cm.setNumObstacles(null));
	}
	
	@Test
	public void testGetItems() {
		List<? extends ItemDrop> idList = cm.getItems();
		assertTrue("Has not returned correct array type", idList instanceof ArrayList);
	}
	
	@Test
	public void testModifyVelocity() {
		double velocity = 1.56;
		assertEquals("Did not return the same velocity", velocity, cm.modifyVelocity(velocity, null), 0.01);
		velocity = 1.4092;
		assertEquals("Did not return the same velocity", 1.41, cm.modifyVelocity(velocity, null), 0.01);
	}
	
	@Test
	public void testRespawnsOn() {
		assertFalse("Respawns should be false", cm.respawnsOn());
	}
	
	@Test
	public void testCreateRenderables() {
		assertNull("Renderables should be null", cm.createRenderables());
	}
	
	@Test
	public void testAddModeRobots() {
		String robots = "these.are.robots";
		assertEquals("Method does not return correct string", robots, cm.addModeRobots(robots));
	}
	
	@Test
	public void testTurnLimit() {
		assertEquals("Turn limit should be "+TURN_LIMIT+".", TURN_LIMIT, cm.turnLimit());
	}
	
	@Test
	public void testIsRoundOver() {
		int endTimer = TURNS_DISPLAYED_AFTER_ENDING * 5 + 1, time = TURNS_DISPLAYED_AFTER_ENDING;
		assertTrue("For current time "+endTimer+" and end time "+ time +" expected true.", cm.isRoundOver(endTimer, time));
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
