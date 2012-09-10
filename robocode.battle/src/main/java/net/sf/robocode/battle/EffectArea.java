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
	
	EffectArea(double xCoord, double yCoord, int tileWidth, int tileHeight, int activeEffAreas){
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.activeEffAreas = activeEffAreas;
	}
	
	public boolean checkOverlap(List<EffectArea> effArea){
		for (EffectArea secondEffArea : effArea) {
			if (secondEffArea != null) {
				if (xCoord == secondEffArea.getXCoord() && yCoord == secondEffArea.getYCoord()) {
					return true;
				}
			}
		}
		return true;
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