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
    private int landmineId;
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

        landmineId = landmine.getLandmineId();

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
		return state;
	}

	@Override
	public String getLandmineSound() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getPower() {
		// TODO Auto-generated method stub
		return power;
	}

	@Override
	public double getX() {
		// TODO Auto-generated method stub
		return x;
	}

	@Override
	public double getY() {
		// TODO Auto-generated method stub
		return y;
	}

	@Override
	public double getPaintX() {
		// TODO Auto-generated method stub
		return paintX;
	}

	@Override
	public double getPaintY() {
		// TODO Auto-generated method stub
		return paintY;
	}

	@Override
	public int getColor() {
		// TODO Auto-generated method stub
		return color;
	}

	@Override
	public int getFrame() {
		// TODO Auto-generated method stub
		return frame;
	}

	@Override
	public boolean isExplosion() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int getExplosionImageIndex() {
		// TODO Auto-generated method stub
		return explosionImageIndex;
	}

	@Override
	public int getLandmineId() {
		// TODO Auto-generated method stub
		return landmineId;
	}

	
	@Override
	public int getVictimIndex() {
		// TODO Auto-generated method stub
		return victimIndex;
	}

	@Override
	public int getOwnerIndex() {
		// TODO Auto-generated method stub
		return ownerIndex;
	}

	@Override
	public void writeXml(XmlWriter writer, SerializableOptions options)
			throws IOException {

        writer.startElement(options.shortAttributes ? "b" : "landmine");
        {
            writer.writeAttribute("id", ownerIndex + "-" + landmineId);
            if (!options.skipExploded ) {
                writer.writeAttribute(options.shortAttributes ? "s" : "state", state.toString());
                writer.writeAttribute(options.shortAttributes ? "p" : "power", power, options.trimPrecision);
            }
            if (state == LandmineState.HIT_VICTIM) {
                writer.writeAttribute(options.shortAttributes ? "v" : "victim", victimIndex);
            }
            if (state == LandmineState.FIRED) {
                writer.writeAttribute(options.shortAttributes ? "o" : "owner", ownerIndex);
            }
            writer.writeAttribute("x", paintX, options.trimPrecision);
            writer.writeAttribute("y", paintY, options.trimPrecision);
            if (!options.skipNames) {
                if (color != ExecCommands.defaultLandmineColor) {
                    writer.writeAttribute(options.shortAttributes ? "c" : "color",
                                          Integer.toHexString(color).toUpperCase());
                }
            }
            if (!options.skipExploded) {
                if (frame != 0) {
                    writer.writeAttribute("frame", frame);
                }
                if (isExplosion) {
                    writer.writeAttribute("isExplosion", true);
                    writer.writeAttribute("explosion", explosionImageIndex);
                }
            }
            if (!options.skipVersion) {
                writer.writeAttribute("ver", serialVersionUID);
            }
        }
        writer.endElement();
    
		
	}

	@Override
	public Element readXml(XmlReader reader) {

        return reader.expect("landmine", "b", new XmlReader.Element() {
            @Override
            public IXmlSerializable read(XmlReader reader) {
                final LandmineSnapshot snapshot = new LandmineSnapshot();

                reader.expect("id", new XmlReader.Attribute() {
                    @Override
                    public void read(String value) {
                        String[] parts = value.split("-");

                        snapshot.ownerIndex = Integer.parseInt(parts[0]);
                        snapshot.landmineId = Integer.parseInt(parts[1]);
                    }
                });

                reader.expect("state", "s", new XmlReader.Attribute() {
                    @Override
                    public void read(String value) {
                        snapshot.state = LandmineState.valueOf(value);
                    }
                });

                reader.expect("power", "p", new XmlReader.Attribute() {
                    @Override
                    public void read(String value) {
                        snapshot.power = Double.parseDouble(value);
                    }
                });
              /**
                reader.expect("heading", "h", new XmlReader.Attribute() {
                    @Override
                    public void read(String value) {
                        snapshot.heading = Double.parseDouble(value);
                    }
                });
                */

                reader.expect("victim", "v", new XmlReader.Attribute() {
                    @Override
                    public void read(String value) {
                        snapshot.victimIndex = Integer.parseInt(value);
                    }
                });

                reader.expect("owner", "o", new XmlReader.Attribute() {
                    @Override
                    public void read(String value) {
                        snapshot.ownerIndex = Integer.parseInt(value);
                    }
                });

                reader.expect("x", new XmlReader.Attribute() {
                    @Override
                    public void read(String value) {
                        snapshot.x = Double.parseDouble(value);
                        snapshot.paintX = snapshot.x;
                    }
                });

                reader.expect("y", new XmlReader.Attribute() {
                    @Override
                    public void read(String value) {
                        snapshot.y = Double.parseDouble(value);
                        snapshot.paintY = snapshot.y;
                    }
                });

                reader.expect("color", "c", new XmlReader.Attribute() {
                    @Override
                    public void read(String value) {
                        snapshot.color = Long.valueOf(value.toUpperCase(), 16).intValue();
                    }
                });

                reader.expect("isExplosion", new XmlReader.Attribute() {
                    @Override
                    public void read(String value) {
                        snapshot.isExplosion = Boolean.parseBoolean(value);
                        if (snapshot.isExplosion && snapshot.state == null) {
                            snapshot.state = LandmineState.EXPLODED;
                        }
                    }
                });

                reader.expect("explosion", new XmlReader.Attribute() {
                    @Override
                    public void read(String value) {
                        snapshot.explosionImageIndex = Integer.parseInt(value);
                    }
                });

                reader.expect("frame", new XmlReader.Attribute() {
                    @Override
                    public void read(String value) {
                        snapshot.frame = Integer.parseInt(value);
                    }
                });
                return snapshot;
            }
        });
    
	}

}
