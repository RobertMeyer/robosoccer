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


import net.sf.robocode.battle.peer.ObstaclePeer;
import net.sf.robocode.peer.ExecCommands;
import net.sf.robocode.serialization.IXmlSerializable;
import net.sf.robocode.serialization.XmlReader;
import net.sf.robocode.serialization.SerializableOptions;
import net.sf.robocode.serialization.XmlWriter;
import robocode.control.snapshot.IObstacleSnapshot;

import java.io.IOException;


/**
 * A snapshot of an obstacle at a specific time instant in a battle.
 * The snapshot contains a snapshot of the obstacle data at that specific time.
 *
 * @author Michael Tsai
 * @author Joel Addison
 * 
 */
public final class ObstacleSnapshot implements java.io.Serializable, IXmlSerializable, IObstacleSnapshot {

	private static final long serialVersionUID = 2L;

	/** The x position */
	private double x;

	/** The y position */
	private double y;
	
	/** Obstacle Width */
	private int width;
	
	/** Obstacle Height */
	private int height;

	/** The ARGB color of the bullet */
	private int color = ExecCommands.defaultBulletColor;

	private int obstacleId;

	private int victimIndex = -1;

	/**
	 * Creates a snapshot of a bullet that must be filled out with data later.
	 */
	public ObstacleSnapshot() {
		victimIndex = -1;
	}

	/**
	 * Creates a snapshot of an obstacle.
	 *
	 * @param obstacle the obstacle to make a snapshot of.
	 */
	public ObstacleSnapshot(ObstaclePeer obstacle) {
		x = obstacle.getX();
		y = obstacle.getY();
		height = obstacle.getHeight();
		width = obstacle.getWidth();

		//color = obstacle.getColor();

		//obstacleId = bullet.getBulletId();

		//final RobotPeer victim = bullet.getVictim();
	}

	@Override
	public String toString() {
		return "";
	}

	/**
	 * {@inheritDoc}
	 */
	public double getX() {
		return x;
	}

	/**
	 * {@inheritDoc}
	 */
	public double getY() {
		return y;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getColor() {
		return color;
	}

	
	/**
	 * {@inheritDoc}
	 */
	public int getVictimIndex() {
		return victimIndex;
	}

	/**
	 * {@inheritDoc}
	 */
	public void writeXml(XmlWriter writer, SerializableOptions options) throws IOException {
		/*writer.startElement(options.shortAttributes ? "b" : "bullet"); {
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
		writer.endElement();*/
	}

	/**
	 * {@inheritDoc}
	 */
	public XmlReader.Element readXml(XmlReader reader) {
		return null;
		/*return reader.expect("bullet", "b", new XmlReader.Element() {
			public IXmlSerializable read(XmlReader reader) {
				final ObstacleSnapshot snapshot = new ObstacleSnapshot();

				reader.expect("id", new XmlReader.Attribute() {
					public void read(String value) {
						String[] parts = value.split("-");

						snapshot.ownerIndex = Integer.parseInt(parts[0]);
						snapshot.bulletId = Integer.parseInt(parts[1]);
					}
				});

				reader.expect("state", "s", new XmlReader.Attribute() {
					public void read(String value) {
						snapshot.state = BulletState.valueOf(value);
					}
				});

				reader.expect("power", "p", new XmlReader.Attribute() {
					public void read(String value) {
						snapshot.power = Double.parseDouble(value);
					}
				});

				reader.expect("heading", "h", new XmlReader.Attribute() {
					public void read(String value) {
						snapshot.heading = Double.parseDouble(value);
					}
				});

				reader.expect("victim", "v", new XmlReader.Attribute() {
					public void read(String value) {
						snapshot.victimIndex = Integer.parseInt(value);
					}
				});

				reader.expect("owner", "o", new XmlReader.Attribute() {
					public void read(String value) {
						snapshot.ownerIndex = Integer.parseInt(value);
					}
				});

				reader.expect("x", new XmlReader.Attribute() {
					public void read(String value) {
						snapshot.x = Double.parseDouble(value);
						snapshot.paintX = snapshot.x;
					}
				});

				reader.expect("y", new XmlReader.Attribute() {
					public void read(String value) {
						snapshot.y = Double.parseDouble(value);
						snapshot.paintY = snapshot.y;
					}
				});

				reader.expect("color", "c", new XmlReader.Attribute() {
					public void read(String value) {
						snapshot.color = Long.valueOf(value.toUpperCase(), 16).intValue();
					}
				});

				reader.expect("isExplosion", new XmlReader.Attribute() {
					public void read(String value) {
						snapshot.isExplosion = Boolean.parseBoolean(value);
						if (snapshot.isExplosion && snapshot.state == null) {
							snapshot.state = BulletState.EXPLODED;
						}
					}
				});

				reader.expect("explosion", new XmlReader.Attribute() {
					public void read(String value) {
						snapshot.explosionImageIndex = Integer.parseInt(value);
					}
				});

				reader.expect("frame", new XmlReader.Attribute() {
					public void read(String value) {
						snapshot.frame = Integer.parseInt(value);
					}
				});
				return snapshot;
			}
		});*/
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getObstacleId() {
		return obstacleId;
	}
}
