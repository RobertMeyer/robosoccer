package net.sf.robocode.test.mode;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.geom.AffineTransform;

import net.sf.robocode.battle.RenderObject;
import net.sf.robocode.battle.RenderString;
import net.sf.robocode.battle.snapshot.RenderableSnapshot;

import org.junit.Test;

import robocode.control.snapshot.IRenderableSnapshot;
import robocode.control.snapshot.RenderableType;

/**
 * A Basic JUnit test to make sure IRenderables work as intended. The tests
 * consist of testing out all its methods.
 * 
 * @author Benjamin Evenson
 * @version 1.0
 */
public class RenderObjectTest {
	private static final double DELTA = 1e-15;
	private static final String PATH = "/net/sf/robocode/ui/images/flag.png";

	@Test
	public void test() {
		// Build up a mock RenderObject
		RenderObject mockObject = new RenderObject("flag", PATH);
		mockObject.setTranslate(100, 100);
		mockObject.setAlpha(0.5f);
		mockObject.setColour(Color.BLACK);
		mockObject.setRotation(45);
		mockObject.setScale(2, 2);
		mockObject.setShear(2, 2);

		// Build a mock AffineTransform to compare mock IRenderables
		AffineTransform mockTransform = new AffineTransform();
		mockTransform.translate(100, 100);
		mockTransform.rotate(Math.toRadians(45));
		mockTransform.scale(2, 2);
		mockTransform.shear(2, 2);

		// Check mockObject is working as intended
		assertEquals(100, mockObject.getTranslateX(), DELTA);
		assertEquals(100, mockObject.getTranslateY(), DELTA);
		assertEquals(0.5f, mockObject.getAlpha(), DELTA);
		assertEquals(Color.BLACK, mockObject.getColour());
		assertEquals(45, mockObject.getRotationDegree(), DELTA);
		assertEquals(Math.toRadians(45), mockObject.getRotationRadian(), DELTA);
		assertEquals(mockTransform.getScaleX(), mockObject.getScale()
				.getScaleX(), DELTA);
		assertEquals(mockTransform.getScaleY(), mockObject.getScale()
				.getScaleY(), DELTA);
		assertEquals(mockTransform.getShearX(), mockObject.getShear()
				.getShearX(), DELTA);
		assertEquals(mockTransform.getShearY(), mockObject.getShear()
				.getShearY(), DELTA);
		assertEquals(mockTransform, mockObject.getAffineTransform());
		assertEquals(RenderableType.SPRITE, mockObject.getType());

		// Build up a mock RenderString
		RenderString mockString = new RenderString("text", "Hello mocky");
		mockString.setTranslate(100, 100);
		mockString.setAlpha(0.5f);
		mockString.setColour(Color.BLACK);
		mockString.setRotation(45);
		mockString.setScale(2, 2);
		mockString.setShear(2, 2);

		// Check mockString is working as intended
		assertEquals(100, mockString.getTranslateX(), DELTA);
		assertEquals(100, mockString.getTranslateY(), DELTA);
		assertEquals(0.5f, mockString.getAlpha(), DELTA);
		assertEquals(Color.BLACK, mockString.getColour());
		assertEquals(45, mockString.getRotationDegree(), DELTA);
		assertEquals(Math.toRadians(45), mockString.getRotationRadian(), DELTA);
		assertEquals(mockTransform.getScaleX(), mockString.getScale()
				.getScaleX(), DELTA);
		assertEquals(mockTransform.getScaleY(), mockString.getScale()
				.getScaleY(), DELTA);
		assertEquals(mockTransform.getShearX(), mockString.getShear()
				.getShearX(), DELTA);
		assertEquals(mockTransform.getShearY(), mockString.getShear()
				.getShearY(), DELTA);
		assertEquals(mockTransform, mockString.getAffineTransform());
		assertEquals(RenderableType.SPRITE_STRING, mockString.getType());

		// Now lets see if a snapshot of a Renderable is build correctly
		IRenderableSnapshot mockSnapshot = new RenderableSnapshot(mockObject);
		assertEquals(mockObject.getAffineTransform(),
				mockSnapshot.getAffineTransform());

		mockSnapshot = null;
		mockSnapshot = new RenderableSnapshot(mockString);

		assertEquals(mockString.getAffineTransform(),
				mockSnapshot.getAffineTransform());
	}

}
