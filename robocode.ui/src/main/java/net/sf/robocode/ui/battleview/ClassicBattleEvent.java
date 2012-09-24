package net.sf.robocode.ui.battleview;

import static java.lang.Math.round;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Random;

import net.sf.robocode.ui.IImageManager;

public class ClassicBattleEvent implements ICustomBattleViewEvent{

	@Override
	public void battleFieldRendering(BattleField battleField, int[][] groundTiles, IImageManager imageManager, RenderingHints renderingHints) {
		 // Reinitialize ground tiles

		 Random r = new Random(); // independent

	        final int NUM_HORZ_TILES = battleField.getWidth() / groundTileWidth + 1;
	        final int NUM_VERT_TILES = battleField.getHeight() / groundTileHeight + 1;

	        if ((groundTiles == null) || (groundTiles.length != NUM_VERT_TILES) || (groundTiles[0].length != NUM_HORZ_TILES)) {

	            groundTiles = new int[NUM_VERT_TILES][NUM_HORZ_TILES];
	            for (int y = NUM_VERT_TILES - 1; y >= 0; y--) {
	                for (int x = NUM_HORZ_TILES - 1; x >= 0; x--) {
	                    groundTiles[y][x] = (int) round(r.nextDouble() * 4);
	                }
	            }
	        }

	        // Create new buffered image with the ground pre-rendered

	        int groundWidth = (int) (battleField.getWidth() * scale);
	        int groundHeight = (int) (battleField.getHeight() * scale);

	        Image groundImage = new BufferedImage(groundWidth, groundHeight, BufferedImage.TYPE_INT_RGB);

	        Graphics2D groundGfx = (Graphics2D) groundImage.getGraphics();

	        groundGfx.setRenderingHints(renderingHints);

	        groundGfx.setTransform(AffineTransform.getScaleInstance(scale, scale));

	        for (int y = NUM_VERT_TILES - 1; y >= 0; y--) {
	            for (int x = NUM_HORZ_TILES - 1; x >= 0; x--) {
	                Image img = imageManager.getGroundTileImage(groundTiles[y][x]);

	                if (img != null) {
	                    groundGfx.drawImage(img, x * groundTileWidth, y * groundTileHeight, null);
	                }
	            }
	        }
	}

	@Override
	public void createBattleField() {
		// TODO Auto-generated method stub
		
	}
}
