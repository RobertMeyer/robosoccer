package net.sf.robocode.battle.peer;

import net.sf.robocode.battle.item.BoundingRectangle;

public class ZLevelPeer {
	private BoundingRectangle bound;
	private int z;
	private int x;
	private int y;
	
	public ZLevelPeer(int h, int w, int zL, int x, int y) {
		bound = new BoundingRectangle(x, y, w, h);
		z = zL;
	}
	
	public BoundingRectangle getBounds() {
		return bound;
	}
	
	public int getZ() {
		return z;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
}
