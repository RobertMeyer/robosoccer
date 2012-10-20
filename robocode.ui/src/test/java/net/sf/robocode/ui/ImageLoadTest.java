package net.sf.robocode.ui;

import static org.junit.Assert.*;

import net.sf.robocode.settings.SettingsManager;
import net.sf.robocode.ui.gfx.RenderImage;

import org.junit.Test;

/**
 * This JUnitTest checks to see if custom image files are loading into
 * the cache and acting how intended.
 * 
 * @author Benjamin Evenson
 *@version 1.0
 */
public class ImageLoadTest {
	private final static String imageP = "/net/sf/robocode/ui/images/flag.png";
	@Test
	public void test() {
		IImageManager im = new ImageManager(new SettingsManager());
		im.initialize();
		RenderImage image = null;
		image = im.addCustomImage("flag", imageP);
		assertNotNull("Can't find " + imageP, image);
		
		RenderImage expectedImage = image;
		image = null;
		
		image = im.addCustomImage("youwontfindme", "!/onlyificouldfindyou.png");
		assertNull("If its found this file wow", image);
		
		image = null;
		image = im.getCustomImage("flag");
		assertNotNull("Not caching image file", image);
		assertEquals(expectedImage, image);
	}

}
