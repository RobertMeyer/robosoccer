package net.sf.robocode.battle;

import java.util.List;

import robocode.*;

public class EffectArea {
	
	protected BattleRules battleRules;
	
	private double xCoord;
	private double yCoord;
	private int activeEffAreas;
	private int tileWidth;
	private int tileHeight;
	private int activeEffect;
	
	EffectArea(double xCoord, double yCoord, int tileWidth, int tileHeight, int activeEffAreas, int activeEffect){
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.activeEffAreas = activeEffAreas;
		this.activeEffect = activeEffect;
	}
	
	public void setActiveEffect(int effect){
		activeEffect = effect;
	}
	
	public int getActiveEffect(){
		return activeEffect;
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
	
	public double getXCoord(){
		return xCoord;
	}
	
	public double getYCoord(){
		return yCoord;
	}
	
	protected double getBattleFieldHeight() {
		return battleRules.getBattlefieldHeight();
	}

	protected double getBattleFieldWidth() {
		return battleRules.getBattlefieldWidth();
	}
}