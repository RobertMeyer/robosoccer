package net.sf.robocode.battle;

import java.lang.Math;

public class Flag extends ItemDrop {

	Flag(boolean isDestroyable, int lifespan, double health,
			boolean isEquippable, Battle battle) {
		super(isDestroyable, lifespan, health, isEquippable, battle);
		
		this.setXLocation(Math.random() * 40);
		this.setYLocation(Math.random() * 40);
		this.setIsDestroyable(false);
		this.setLifespan(lifespan);
		this.setIsEquippable(true);
	}
	
	

}
