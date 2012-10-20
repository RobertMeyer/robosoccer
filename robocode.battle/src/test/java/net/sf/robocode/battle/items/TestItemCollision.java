package net.sf.robocode.battle.items;

import static org.junit.Assert.*;
import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.BoundingRectangle;
import net.sf.robocode.battle.item.HealthPack;


import org.junit.Test;
import org.mockito.Mockito;

public class TestItemCollision{
	
	private Battle battle = Mockito.mock(Battle.class);
	private BoundingRectangle robot = new BoundingRectangle(100,100,40,40); //Represents the bounding box of a robot
	private HealthPack health = new HealthPack(battle, "health1");
	private boolean result = false;
	
	@Test
	public void testItemCollision(){
		health.setXLocation(100);
		health.setYLocation(100);
		health.setBoundingBox();
		if (health.getBoundingBox().intersects(robot)){
			result = true;
		}
		assertTrue("Item & Robot didn't collide", result);
	}

}
