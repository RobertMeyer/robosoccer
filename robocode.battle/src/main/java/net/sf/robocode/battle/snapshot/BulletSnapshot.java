/*******************************************************************************
 * Copyright (c) 2001-2012 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 *
 * Contributors:
 *     Flemming N. Larsen
 *     - Initial implementation
 *     Pavel Savara
 *     - Xml Serialization, refactoring
 *******************************************************************************/
package net.sf.robocode.battle.snapshot;

import java.io.IOException;
import net.sf.robocode.battle.peer.BulletPeer;
import net.sf.robocode.battle.peer.ExplosionPeer;
import net.sf.robocode.battle.peer.RobotPeer;
import net.sf.robocode.peer.ExecCommands;
import net.sf.robocode.serialization.IXmlSerializable;
import net.sf.robocode.serialization.SerializableOptions;
import net.sf.robocode.serialization.XmlReader;
import net.sf.robocode.serialization.XmlWriter;
import robocode.control.snapshot.BulletState;
import robocode.control.snapshot.IBulletSnapshot;
import robocode.equipment.EquipmentSlot;

/**
 * A snapshot of a bullet at a specific time instant in a battle.
 * The snapshot contains a snapshot of the bullet data at that specific time.
 *
 * @author Flemming N. Larsen (original)
 * @author Pavel Savara (contributor)
 * @since 1.6.1
 */
public final class BulletSnapshot implements java.io.Serializable,
                                             IXmlSerializable, IBulletSnapshot {

    private static final long serialVersionUID = 2L;
    /** The bullet state */
    private BulletState state;
    /** The bullet power */
    private double power;
    /** The x position */
    private double x;
    /** The y position */
    private double y;
    /** The x painting position (due to offset on robot when bullet hits a robot) */
    private double paintX;
    /** The y painting position (due to offset on robot when bullet hits a robot) */
    private double paintY;
    /** The ARGB color of the bullet */
    private int color = ExecCommands.defaultBulletColor;
    /** The current frame number to display, i.e. when the bullet explodes */
    private int frame;
    /** Flag specifying if this bullet has turned into an explosion */
    private boolean isExplosion;
    /** Index to which explosion image that must be rendered */
    private int explosionImageIndex;
    private int bulletId;
    private int victimIndex = -1;
    private int ownerIndex;
    private double heading;
    private String bulletPath;
    private RobotPeer owner;

    /**
     * Creates a snapshot of a bullet that must be filled out with data later.
     */
    public BulletSnapshot() {
        state = BulletState.INACTIVE;
        ownerIndex = -1;
        victimIndex = -1;
        explosionImageIndex = -1;
        heading = Double.NaN;
        power = Double.NaN;
    }

    /**
     * Creates a snapshot of a bullet.
     *
     * @param bullet the bullet to make a snapshot of.
     */
    public BulletSnapshot(BulletPeer bullet) {
        state = bullet.getState();
        
        owner = bullet.getOwner(); //Returns the robotpeer of the robot who fired the bullet

        power = bullet.getPower();

        x = bullet.getX();
        y = bullet.getY();

        paintX = bullet.getPaintX();
        paintY = bullet.getPaintY();

        color = bullet.getColor();

        frame = bullet.getFrame();

        isExplosion = (bullet instanceof ExplosionPeer);
        explosionImageIndex = bullet.getExplosionImageIndex();

        bulletId = bullet.getBulletId();

        final RobotPeer victim = bullet.getVictim();

        if (victim != null) {
            victimIndex = victim.getRobotIndex();
        }

        ownerIndex = bullet.getOwner().getRobotIndex();

        heading = bullet.getHeading();
        
        try{
        	bulletPath = owner.getEquipment().get().get(EquipmentSlot.GUN).getSoundPath();
        	}
        catch(NullPointerException e){
        	bulletPath = null;
        }
    }

    @Override
    public String toString() {
        return ownerIndex + "-" + bulletId + " (" + (int) power + ") X" + (int) x + " Y" + (int) y + " "
                + state.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getBulletId() {
        return bulletId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BulletState getState() {
        return state;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getPower() {
        return power;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getX() {
        return x;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getY() {
        return y;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getPaintX() {
        return paintX;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getPaintY() {
        return paintY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getColor() {
        return color;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getFrame() {
        return frame;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isExplosion() {
        return isExplosion;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getExplosionImageIndex() {
        return explosionImageIndex;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getHeading() {
        return heading;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getVictimIndex() {
        return victimIndex;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getOwnerIndex() {
        return ownerIndex;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
	public String getBulletSound() {
		return bulletPath;
	}

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeXml(XmlWriter writer, SerializableOptions options) throws IOException {
        writer.startElement(options.shortAttributes ? "b" : "bullet");
        {
            writer.writeAttribute("id", ownerIndex + "-" + bulletId);
            if (!options.skipExploded || state != BulletState.MOVING) {
                writer.writeAttribute(options.shortAttributes ? "s" : "state", state.toString());
                writer.writeAttribute(options.shortAttributes ? "p" : "power", power, options.trimPrecision);
            }
            if (state == BulletState.HIT_VICTIM) {
                writer.writeAttribute(options.shortAttributes ? "v" : "victim", victimIndex);
            }
            if (state == BulletState.FIRED) {
                writer.writeAttribute(options.shortAttributes ? "o" : "owner", ownerIndex);
                writer.writeAttribute(options.shortAttributes ? "h" : "heading", heading, options.trimPrecision);
            }
            writer.writeAttribute("x", paintX, options.trimPrecision);
            writer.writeAttribute("y", paintY, options.trimPrecision);
            if (!options.skipNames) {
                if (color != ExecCommands.defaultBulletColor) {
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

    /**
     * {@inheritDoc}
     */
    @Override
    public XmlReader.Element readXml(XmlReader reader) {
        return reader.expect("bullet", "b", new XmlReader.Element() {
            @Override
            public IXmlSerializable read(XmlReader reader) {
                final BulletSnapshot snapshot = new BulletSnapshot();

                reader.expect("id", new XmlReader.Attribute() {
                    @Override
                    public void read(String value) {
                        String[] parts = value.split("-");

                        snapshot.ownerIndex = Integer.parseInt(parts[0]);
                        snapshot.bulletId = Integer.parseInt(parts[1]);
                    }
                });

                reader.expect("state", "s", new XmlReader.Attribute() {
                    @Override
                    public void read(String value) {
                        snapshot.state = BulletState.valueOf(value);
                    }
                });

                reader.expect("power", "p", new XmlReader.Attribute() {
                    @Override
                    public void read(String value) {
                        snapshot.power = Double.parseDouble(value);
                    }
                });

                reader.expect("heading", "h", new XmlReader.Attribute() {
                    @Override
                    public void read(String value) {
                        snapshot.heading = Double.parseDouble(value);
                    }
                });

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
                            snapshot.state = BulletState.EXPLODED;
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
