package net.sf.robocode.ui.battleview;

import java.awt.RenderingHints;

import net.sf.robocode.ui.IImageManager;
import net.sf.robocode.ui.ImageManager;

public interface ICustomBattleViewEvent {
	double scale = 1.0;
	final int groundTileWidth = 64;
	final int groundTileHeight = 64;
	
	public void createBattleField();
	public void battleFieldRendering(BattleField battleField, int[][] groundTiles, IImageManager imageManager, RenderingHints renderingHints);
}
