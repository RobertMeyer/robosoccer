package net.sf.robocode.battle;

import net.sf.robocode.battle.peer.RobotPeer;
import net.sf.robocode.mode.FlagMode;
import net.sf.robocode.mode.IMode;

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
	
	public static Flag createForMode(IMode mode, Battle battle) {
		/* Flag customised for Capture the Flag */
		if(mode.getClass().isInstance(FlagMode.class))
			return new Flag(false, Integer.MAX_VALUE, 0.0,
					true, battle, null);
		
		return new Flag(false, 0, 0.0, false, null, null);
	}
}
