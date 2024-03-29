package net.sf.robocode.battle;

import net.sf.robocode.battle.peer.RobotPeer;

import robocode.*;

public class EffectArea {
	
	protected BattleRules battleRules;
	
	private double xCoord;
	private double yCoord;
	private int tileWidth;
	private int tileHeight;
	private int activeEffect;
	
	public EffectArea(double xCoord, double yCoord, int tileWidth, int tileHeight, int activeEffect){
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.activeEffect = activeEffect;
	}
	
	public void setActiveEffect(int effect){
		activeEffect = effect;
	}
	
	public int getActiveEffect(){
		return activeEffect;
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
	
	/**
	 * Check if Robot is in effect area
	 * @param r RobotPeer to check
	 * @return True if it is in effect area
	 */
	public Boolean collision(RobotPeer r){
		//check collision
		if(r.getX() >= getXCoord() && r.getX() < getXCoord() + 
			getTileWidth() && r.getY() <= getYCoord() 
					&& r.getY() > getYCoord() - getTileHeight()
				){
				return true;
			}
		return false;
	}
	
	/**
	 * Handle effect to robotpeer according to active effect
	 * @param r Robot to modify
	 */
	public void handleEffect(RobotPeer r) {
		switch (getActiveEffect()) {
		case 1: 
			//decreases energy
			r.setEnergyEffect(r.getEnergy()-1, false);
			break;
		case 2: 
			//decreases speed
			if(r.getVelocity()!=0){
				r.setVelocityEffect(r.getVelocity()/2);
			}
			break;
		case 3:
			//increases gunheat
			r.setGunHeatEffect(r.getGunHeat() + 0.1);
			break;
		default:break;
		}
	}
	
}