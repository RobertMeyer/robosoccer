package net.sf.robocode.battle.peer;

import org.junit.Assert;
import net.sf.robocode.battle.item.BoundingRectangle;
import java.awt.geom.Ellipse2D.Float;

import net.sf.robocode.teleporters.ITeleporter.Portal;

import org.junit.Test;

/**
 * Unit test for TeleporterPeer class.
 * 
 * @author Team Omega
 */
public class TeleporterPeerTest {
	@Test
	public void testConstructor() {
		TeleporterPeer teleporter = new TeleporterPeer(1.0,2.0,3.0,4.0);
		Assert.assertEquals(teleporter.getX(Portal.PORTAL1), 1.0, 0.0);
		Assert.assertEquals(teleporter.getY(Portal.PORTAL1), 2.0, 0.0);
		Assert.assertEquals(teleporter.getX(Portal.PORTAL2), 3.0, 0.0);
		Assert.assertEquals(teleporter.getY(Portal.PORTAL2), 4.0, 0.0);
		Assert.assertEquals(teleporter.getHeight(), 40.0, 0.0);
		Assert.assertEquals(teleporter.getWidth(), 40.0, 0.0);
	}
	
	@Test
	public void testBlackholes() {
		TeleporterPeer teleporter = new TeleporterPeer(1.0,2.0,-1.0,-2.0);
		Assert.assertEquals(teleporter.getX(Portal.PORTAL1), 1.0, 0.0);
		Assert.assertEquals(teleporter.getY(Portal.PORTAL1), 2.0, 0.0);
		Assert.assertEquals(teleporter.getHeight(), 40.0, 0.0);
		Assert.assertEquals(teleporter.getWidth(), 40.0, 0.0);
		Assert.assertTrue(teleporter.isBlackHole());
		/*teleporter.updateBlackHoleSize();
		Assert.assertEquals(teleporter.getHeight(), 80.0, 0.0);
		Assert.assertEquals(teleporter.getWidth(), 80.0, 0.0);*/
	}
	
	@Test
	public void testTeleporterSetXY() {
		TeleporterPeer teleporter = new TeleporterPeer(1.0,1.0,2.0,2.0);
		teleporter.setXY(3.0, 4.0, Portal.PORTAL1);
		teleporter.setXY(5.0, 6.0, Portal.PORTAL2);
		Assert.assertEquals(teleporter.getX(Portal.PORTAL1), 3.0, 0.0);
		Assert.assertEquals(teleporter.getY(Portal.PORTAL1), 4.0, 0.0);
		Assert.assertEquals(teleporter.getX(Portal.PORTAL2), 5.0, 0.0);
		Assert.assertEquals(teleporter.getY(Portal.PORTAL2), 6.0, 0.0);
	}
	
	@Test
	public void testCollisionReaction() {
		TeleporterPeer teleporter = new TeleporterPeer(10,15,60,70);
		BoundingRectangle rect = new BoundingRectangle(5,10,10,10);
		double[] newPosition = teleporter.getCollisionReaction(rect);
		double[] expectedPosition = {60.0,70.0};
		Assert.assertArrayEquals(null, newPosition, expectedPosition, 0);
	}
	
	@Test
	public void testCircle() {
		TeleporterPeer teleporter = new TeleporterPeer(10,15,60,70);
		Float circle = new Float();
		circle.setFrame(10-(40/2), 15-(40/2), 40, 40);
		Assert.assertEquals(circle.getCenterX(), teleporter.getCircle(Portal.PORTAL1).getCenterX(), 0);
		Assert.assertEquals(circle.getCenterY(), teleporter.getCircle(Portal.PORTAL1).getCenterY(), 0);
	}
}
