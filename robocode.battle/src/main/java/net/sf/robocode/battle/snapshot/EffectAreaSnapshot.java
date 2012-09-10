package net.sf.robocode.battle.snapshot;


import net.sf.robocode.battle.EffectArea;
import robocode.control.snapshot.IEffectAreaSnapshot;

public final class EffectAreaSnapshot implements IEffectAreaSnapshot {
	
	private double xCoord;
	private double yCoord;
	private int activeEffAreas;
	private int tileWidth;
	private int tileHeight;
	
	public EffectAreaSnapshot(){
		
	}
	
	public EffectAreaSnapshot(EffectArea effArea){
		xCoord = effArea.getXCoord();
		yCoord = effArea.getYCoord();
		activeEffAreas = effArea.getActiveEffectAreas();
		tileWidth = effArea.getTileWidth();
		tileHeight = effArea.getTileHeight();
	}
	
	public double getXCoord(){
		return xCoord;
	}
	
	public double getYCoord(){
		return yCoord;
	}
	
	public int getActiveEffectAreas(){
		return activeEffAreas;
	}
	
	public int getTileWidth(){
		return tileWidth;
	}
	
	public int getTileHeight(){
		return tileHeight;
	}
}