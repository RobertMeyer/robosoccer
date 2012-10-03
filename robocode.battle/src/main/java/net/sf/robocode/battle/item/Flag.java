package net.sf.robocode.battle.item;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.peer.RobotPeer;
import net.sf.robocode.mode.FlagMode;
import net.sf.robocode.mode.IMode;

public class Flag extends ItemDrop {

    // Variable to indicate the carrier of the flag
    RobotPeer carrier;

    public Flag(boolean isDestroyable, int lifespan, double health,
         boolean isEquippable, Battle battle, RobotPeer carrier) {
        super(isDestroyable, lifespan, health, isEquippable, battle);
        
        this.name = "Flag";
        this.carrier = carrier;
    }
    
    /**
     * Sets the carrier of the robot
     * @param robot
     */
    public void setCarrier(RobotPeer robot) {
    	this.carrier = robot;
    }

    /**
     * Get the carrier of the Flag
     * @return The Robot carrying the flag
     */
    public RobotPeer getCarrier() {
        return carrier;
    }
    
    @Override
    public void doItemEffect(RobotPeer robot) {
    	this.setCarrier(robot);
    }
}
