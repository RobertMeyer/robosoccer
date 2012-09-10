package net.sf.robocode.battle;

import net.sf.robocode.battle.peer.RobotPeer;

public class Flag extends ItemDrop {
	
	// Variable to indicate the carrier of the flag
	RobotPeer carrier;

	Flag(boolean isDestroyable, int lifespan, double health,
			boolean isEquippable, Battle battle, RobotPeer carrier) {
		super(isDestroyable, lifespan, health, isEquippable, battle);
		
		this.setIsDestroyable(false);
		this.setLifespan(lifespan);
		this.setIsEquippable(true);
		this.carrier = carrier;
		this.setId(1);
		
	}
	
	public RobotPeer getCarrier() {
		return carrier;
	}

}
