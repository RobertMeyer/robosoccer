package net.sf.robocode.battle.snapshot;

import net.sf.robocode.battle.peer.ZLevelPeer;
import robocode.control.snapshot.IZLevelSnapshot;

public class ZLevelSnapshot implements IZLevelSnapshot {
	
	private int x;
	private int y;
	private int z;
	
	public ZLevelSnapshot(ZLevelPeer zPeer) {
		x = zPeer.getX();
		y = zPeer.getY();
		z = zPeer.getZ();
	}
	
	@Override
	public double getX() {
		return x;
	}
	
	@Override
	public double getY() {
		return y;
	}
	
	@Override
	public int getZ() {
		return z;
	}
}
