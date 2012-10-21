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
		// Create a ImageManage to test out the custom image management system
		IImageManager im = new ImageManager(new SettingsManager());
		im.initialize();
		
		// Check to see if vaild image is loaded
		RenderImage image = null;
		image = im.addCustomImage("flag", imageP);
		assertNotNull("Can't find " + imageP, image);
		
		// Check to see if not valid image is not loaded
		RenderImage expectedImage = image;
		image = null;
		image = im.addCustomImage("youwontfindme", "!/onlyificouldfindyou.png");
		assertNull("If its found this file wow", image);
		
		// See if cache function works
		image = null;
		image = im.getCustomImage("flag");
		assertNotNull("Not caching image file", image);
		assertEquals(expectedImage, image);
	}

}
