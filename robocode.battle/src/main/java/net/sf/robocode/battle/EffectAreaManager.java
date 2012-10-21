package net.sf.robocode.battle;

import static java.lang.Math.round;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.sf.robocode.battle.peer.RobotPeer;

public class EffectAreaManager {
	public List<EffectArea> effArea;
	
	/**
	 * Creates a Effect Area Manager. To be used at the start of battle
	 */
	public EffectAreaManager() {
		effArea = new ArrayList<EffectArea>();
	}
	
	/**
	 * Create random effect areas according to multiplier
	 * @param bp Battle Properties to determine battlefield width and height
	 * @param modifier Integer modifier to determine number of effect areas, 1 being the default and least.
	 * @see EffectAreas
	 * @Author Team Unusual Disco Insurgents
	 */
	public void createRandomEffectAreas(BattleProperties bp, int modifier){
		int tileWidth = 64;
		int tileHeight = 64;
		double xCoord, yCoord;
		
		if (modifier < 1) modifier = 1;
		if (modifier > 100) modifier = 100;
		
		//get battlefield width and height from battle properties and calculate number of tiles
		final int NUM_HORZ_TILES = bp.getBattlefieldWidth() / tileWidth + 1;
		final int NUM_VERT_TILES = bp.getBattlefieldHeight() / tileHeight + 1;
		int numEffectAreasModifier = (101 - modifier) * 1000; // smaller the number -> more effect areas
		//formula to determine number of effect areas
		int numEffectAreas = (int) round((bp.getBattlefieldWidth()*bp.getBattlefieldHeight()/numEffectAreasModifier));
		Random effectAreaR = new Random();
		int effectAreaRandom;

		while(numEffectAreas > 0){
			for (int y = NUM_VERT_TILES - 1; y >= 0; y--) {
				for (int x = NUM_HORZ_TILES - 1; x >= 0; x--) {
					effectAreaRandom = effectAreaR.nextInt(51) + 1; //The 51 is the modifier for the odds of the tile appearing
					if(effectAreaRandom == 10){
						xCoord = x * tileWidth;
						yCoord = bp.getBattlefieldHeight() - (y * tileHeight);
						//create an effect area at current tile
						EffectArea effectArea = new EffectArea(xCoord, yCoord, tileWidth, tileHeight, 0);
						//add it to effect areas
						effArea.add(effectArea);
						numEffectAreas--;
					}
				}
			}
		}
	}
	
	/**
	 * Update effects of robots if they are in the effect area
	 * @param peers RobotPeers to be updated
 	 * @see EffectAreas
 	 * @Author Team Unusual Disco Insurgents
	 */
	public void updateEffectAreas(BattlePeers peers) {
	    //update robots with effect areas
	    for (EffectArea ea : effArea) {
	        int collided = 0;
	        for (RobotPeer r : peers.getRobots()) {
	            //for all effect areas, check if all robots collide
	            if (ea.collision(r)) {
	                if (ea.getActiveEffect() == 0)
	                {
	                    //if collide, give a random effect
	                    Random effR = new Random();
	                    collided = effR.nextInt(3) + 1;
	                    ea.setActiveEffect(collided);
	                }
	                //handle effect
	                ea.handleEffect(r);
	            }
	        }
	    }
	}
	
	/**
	 * Add an effect area to the list
	 * @param ea EffectArea to be added to the list
 	 * @see EffectAreas
 	 * @Author Team Unusual Disco Insurgents
	 */
	 public void addEffectArea(EffectArea ea) {
		 effArea.add(ea);
	 }
	 
	/**
	 * Clear the effect area list
 	 * @see EffectAreas
 	 * @Author Team Unusual Disco Insurgents
	 */
	 public void clearEffectArea() {
		 effArea.clear();
	 }

}
