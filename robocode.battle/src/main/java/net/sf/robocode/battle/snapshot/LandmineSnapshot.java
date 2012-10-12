package net.sf.robocode.battle.snapshot;

import java.io.IOException;

import net.sf.robocode.battle.peer.BulletPeer;
import net.sf.robocode.battle.peer.ExplosionPeer;
import net.sf.robocode.battle.peer.LandminePeer;
import net.sf.robocode.battle.peer.RobotPeer;
import net.sf.robocode.peer.ExecCommands;
import net.sf.robocode.serialization.IXmlSerializable;
import net.sf.robocode.serialization.SerializableOptions;
import net.sf.robocode.serialization.XmlReader;
import net.sf.robocode.serialization.XmlReader.Element;
import net.sf.robocode.serialization.XmlWriter;
import robocode.EquipmentSlot;
import robocode.control.snapshot.BulletState;
import robocode.control.snapshot.ILandmineSnapshot;
import robocode.control.snapshot.LandmineState;

public class LandmineSnapshot implements java.io.Serializable,
IXmlSerializable, ILandmineSnapshot{
	
	private static final long serialVersionUID = 2L;
    /** The bullet state */
    private LandmineState state;
    /** The bullet power */
    private double power;
    /** The x position */
    private double x;
    /** The y position */
    private double y;
    private double paintX;
    private double paintY;
    /** The ARGB color of the bullet */
    private int color = ExecCommands.defaultLandmineColor;
    /** The current frame number to display, i.e. when the bullet explodes */
    private int frame;
    /** Flag specifying if this bullet has turned into an explosion */
    private boolean isExplosion;
    /** Index to which explosion image that must be rendered */
    private int explosionImageIndex;
    private int bulletId;
    private int victimIndex = -1;
    private int ownerIndex;
    //private String bulletPath;
    private RobotPeer owner;
    
    public LandmineSnapshot() {
        state = LandmineState.INACTIVE;
        ownerIndex = -1;
        victimIndex = -1;
        explosionImageIndex = -1;
        power = Double.NaN;
    }
    
    public LandmineSnapshot(LandminePeer landmine) {
        state = landmine.getState();
        
        owner = landmine.getOwner(); //Returns the robotpeer of the robot who fired the bullet

        power = landmine.getPower();

        x = landmine.getX();
        y = landmine.getY();

        paintX = landmine.getX();
        paintY = landmine.getY();

        color = landmine.getColor();

        frame = landmine.getFrame();

        isExplosion = true;
        explosionImageIndex = landmine.getExplosionImageIndex();

        bulletId = landmine.getLandmineId();

        final RobotPeer victim = landmine.getVictim();

        if (victim != null) {
            victimIndex = victim.getRobotIndex();
        }

        ownerIndex = landmine.getOwner().getRobotIndex();
        
        /**
        try{
        	bulletPath = owner.getEquipment().get().get(EquipmentSlot.WEAPON).getSoundPath();
        	}
        catch(NullPointerException e){
        	bulletPath = null;
        }
        */
    }


	@Override
	public LandmineState getState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLandmineSound() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getPower() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getPaintX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getPaintY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getColor() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getFrame() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isExplosion() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getExplosionImageIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getLandmineId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getVictimIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getOwnerIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeXml(XmlWriter writer, SerializableOptions options)
			throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Element readXml(XmlReader reader) {
		// TODO Auto-generated method stub
		return null;
	}

}
