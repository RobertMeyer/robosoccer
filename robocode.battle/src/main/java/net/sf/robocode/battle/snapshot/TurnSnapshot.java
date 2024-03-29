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


import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.IRenderable;
import net.sf.robocode.battle.item.ItemDrop;
import net.sf.robocode.battle.peer.BulletPeer;
import net.sf.robocode.battle.peer.LandminePeer;
import net.sf.robocode.battle.peer.ObstaclePeer;
import net.sf.robocode.battle.peer.RobotPeer;
import net.sf.robocode.battle.peer.TeleporterPeer;
import net.sf.robocode.battle.peer.ZLevelPeer;
import net.sf.robocode.battle.EffectArea;
import net.sf.robocode.serialization.IXmlSerializable;
import net.sf.robocode.serialization.XmlReader;
import net.sf.robocode.serialization.SerializableOptions;
import net.sf.robocode.serialization.XmlWriter;
import robocode.control.snapshot.*;

import java.io.IOException;
import java.util.*;


/**
 * A snapshot of a battle turn at a specific time instant in a battle.
 * The snapshot contains a snapshot of the battle turn data at that specific time.
 *
 * @author Flemming N. Larsen (original)
 * @author Pavel Savara (contributor)
 * @since 1.6.1
 */

public final class TurnSnapshot implements java.io.Serializable, IXmlSerializable, ITurnSnapshot {

	private static final long serialVersionUID = 1L;

	private List<ILandmineSnapshot> landmines;
    /** List of snapshots for the robots participating in the battle */
    private List<IRobotSnapshot> robots;
    /** List of snapshots for the bullets that are currently on the battlefield */
    private List<IBulletSnapshot> bullets;
    /** List of snapshots for the obstacles that are currently on the battlefield */
    private List<IObstacleSnapshot> obstacles;
    /** List of snapshots for the items that are currently on the battlefield */
    //TODO expand this use
    private List<IItemSnapshot> items;
    /** List of snapshots of effect areas */
	private List<IEffectAreaSnapshot> effArea;
	private List<IRenderableSnapshot> customObj;
	private List<IZLevelSnapshot> zSnaps;
	/** Current round in the battle */
	private int round;

	/*List of snapshots for teleporters on the current battefield*/
	private List<ITeleporterSnapshot> teleports;

	/** Current turn in the battle round */
	private int turn;
    /** Current TPS (turns per second) */
    private int tps;

	/**
	 * Creates a snapshot of a battle turn that must be filled out with data later.
	 */
	public TurnSnapshot() {}

	 /**
     * Creates a snapshot of a battle turn.
     *
     * @param battle the battle to make a snapshot of.
     * @param battleRobots the robots participating in the battle.
     * @param battleBullets the current bullet on the battlefield.
     * @param readoutText {@code true} if the output text from the robots must be included in the snapshot;
     *                    {@code false} otherwise.
     */
    public TurnSnapshot(Battle battle, List<RobotPeer> battleRobots, List<BulletPeer> battleBullets,List<LandminePeer> battleLandmines, List<EffectArea> effectAreas, List<IRenderable> customObjects, List<ItemDrop> battleItems, List<ObstaclePeer> battleObstacle, List<TeleporterPeer> battleTeleporters, List<ZLevelPeer> zLevels, boolean readoutText) {
        robots = new ArrayList<IRobotSnapshot>();
        bullets = new ArrayList<IBulletSnapshot>();
        landmines=new ArrayList<ILandmineSnapshot>();
        items = new ArrayList<IItemSnapshot>();
        obstacles = new ArrayList<IObstacleSnapshot>();
		effArea = new ArrayList<IEffectAreaSnapshot>();
		customObj = new ArrayList<IRenderableSnapshot>();
		teleports = new ArrayList<ITeleporterSnapshot>();
		zSnaps = new ArrayList<IZLevelSnapshot>();
		for (RobotPeer robotPeer : battleRobots) {
			robots.add(new RobotSnapshot(robotPeer, readoutText));
		}

		for (BulletPeer bulletPeer : battleBullets) {
			bullets.add(new BulletSnapshot(bulletPeer));
		}

		for (LandminePeer landminePeer : battleLandmines) {
			landmines.add(new LandmineSnapshot(landminePeer));
		}

		for (TeleporterPeer teleporterPeer : battleTeleporters) {
			teleports.add(new TeleporterSnapshot(teleporterPeer));
		}

		if (zLevels != null) {
			for (ZLevelPeer zP : zLevels) {
				zSnaps.add(new ZLevelSnapshot(zP));
			}
		}

		/*--ItemController--*/
		for (ItemDrop item : battleItems) {
			items.add(new ItemSnapshot(item));
		}

		//Add list of obstacle to the arraylist
		for (ObstaclePeer obstaclePeer: battleObstacle) {
			obstacles.add(new ObstacleSnapshot(obstaclePeer));
		}

		for (EffectArea effectArea : effectAreas) {
			effArea.add(new EffectAreaSnapshot(effectArea));
		}

        for (IRenderable customObject : customObjects) {
        	customObj.add(new RenderableSnapshot(customObject));
        }

		tps = battle.getTPS();
		turn = battle.getTime();
		round = battle.getRoundNum();
	}

	@Override
	public String toString() {
		return this.round + "/" + turn + " (" + this.robots.size() + ")";
	}

	/**
	 * {@inheritDoc}
	 */
	public IRobotSnapshot[] getRobots() {
		return robots.toArray(new IRobotSnapshot[robots.size()]);
	}


	@Override
	public IItemSnapshot[] getItems() {
		// TODO Auto-generated method stub
		return items.toArray(new IItemSnapshot[items.size()]);
	}

	/**
	 * {@inheritDoc}
	 */
	public IBulletSnapshot[] getBullets() {
		return bullets.toArray(new IBulletSnapshot[bullets.size()]);
	}

    /**
     * {@inheritDoc}
     */
    public IObstacleSnapshot[] getObstacles() {
        return obstacles.toArray(new IObstacleSnapshot[obstacles.size()]);
    }

	/**
	 * {@inheritDoc}
	 */
	public IItemSnapshot[] getItem() {
		return items.toArray(new IItemSnapshot[items.size()]);
	}

	/**
	 * {@inheritDoc}
	 */
	public int getTPS() {
		return tps;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getRound() {
		return round;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getTurn() {
		return turn;
	}

	public IEffectAreaSnapshot[] getEffectAreas() {
		return effArea.toArray(new IEffectAreaSnapshot[effArea.size()]);
	}

	@Override
	public IRenderableSnapshot[] getCustomObjects() {
		return customObj.toArray(new IRenderableSnapshot[customObj.size()]);
	}



	/**
	 * {@inheritDoc}
	 */
	public IScoreSnapshot[] getSortedTeamScores() {
		List<IScoreSnapshot> copy = new ArrayList<IScoreSnapshot>(Arrays.asList(getIndexedTeamScores()));

		Collections.sort(copy);
		Collections.reverse(copy);
		return copy.toArray(new IScoreSnapshot[copy.size()]);
	}

	/**
	 * {@inheritDoc}
	 */
	public IScoreSnapshot[] getIndexedTeamScores() {
		// team scores are computed on demand from team scores to not duplicate data in the snapshot

		List<IScoreSnapshot> results = new ArrayList<IScoreSnapshot>();

		// noinspection ForLoopReplaceableByForEach
		for (int i = 0; i < robots.size(); i++) {
			results.add(null);
		}
		for (IRobotSnapshot robot : robots) {
			final int contestantIndex = robot.getContestantIndex();
			final IScoreSnapshot snapshot = results.get(contestantIndex);

			IScoreSnapshot score = (snapshot == null)
					? robot.getScoreSnapshot()
					: new ScoreSnapshot(robot.getTeamName(), snapshot, robot.getScoreSnapshot());

			results.set(contestantIndex, score);
		}
		List<IScoreSnapshot> scores = new ArrayList<IScoreSnapshot>();

		for (IScoreSnapshot scoreSnapshot : results) {
			if (scoreSnapshot != null) {
				scores.add(scoreSnapshot);
			}
		}

		return scores.toArray(new IScoreSnapshot[scores.size()]);
	}

	public void stripDetails(SerializableOptions options) {
		for (IRobotSnapshot r : getRobots()) {
			((RobotSnapshot) r).stripDetails(options);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void writeXml(XmlWriter writer, SerializableOptions options) throws IOException {
		writer.startElement(options.shortAttributes ? "t" : "turn"); {
			writer.writeAttribute(options.shortAttributes ? "ro" : "round", round);
			writer.writeAttribute(options.shortAttributes ? "tu" : "turn", turn);
			if (!options.skipVersion) {
				writer.writeAttribute("ver", serialVersionUID);
			}

			writer.startElement(options.shortAttributes ? "rs" : "robots"); {
				SerializableOptions op = options;

				if (turn == 0) {
					op = new SerializableOptions(options);
					op.skipNames = false;
				}
				for (IRobotSnapshot r : robots) {
					final RobotSnapshot rs = (RobotSnapshot) r;

					if (!options.skipExploded || rs.getState() != RobotState.DEAD) {
						rs.writeXml(writer, op);
					} else {
						boolean writeFirstExplosionFrame = false;
                        boolean writeSecondExplosionFrame=false;
						for (IBulletSnapshot b : bullets) {
							if (b.isExplosion() && b.getFrame() == 0 && b.getVictimIndex() == r.getRobotIndex()) {
								writeFirstExplosionFrame = true;
								break;
							}
						}
						if (writeFirstExplosionFrame) {
							rs.writeXml(writer, op);
						}

						for (ILandmineSnapshot l : landmines) {
							if (l.getFrame() == 0&& l.getVictimIndex() == r.getRobotIndex()) {
								writeSecondExplosionFrame = true;
								break;
							}
						}
						if (writeSecondExplosionFrame) {
							rs.writeXml(writer, op);
						}
					}
				}
			}
			writer.endElement();

			writer.startElement(options.shortAttributes ? "bs" : "bullets"); {
				for (IBulletSnapshot b : bullets) {
					final BulletSnapshot bs = (BulletSnapshot) b;
					final BulletState state = bs.getState();

					if (!options.skipExploded
							|| (state != BulletState.EXPLODED && state != BulletState.INACTIVE
							&& (bs.getFrame() == 0 || state == BulletState.MOVING))) {
						bs.writeXml(writer, options);
					}
				}
			}
			writer.endElement();

			writer.startElement(options.shortAttributes ? "bs" : "Landmines");
			{
				for (ILandmineSnapshot l : landmines) {
					final LandmineSnapshot ls = (LandmineSnapshot) l;
					final LandmineState state = ls.getState();

					if (!options.skipExploded
							|| (state != LandmineState.EXPLODED
									&& state != LandmineState.INACTIVE && ls
									.getFrame() == 0)) {
						ls.writeXml(writer, options);
					}
				}
			}
			writer.endElement();
		}
		writer.endElement();
	}

	/**
	 * {@inheritDoc}
	 */
	public XmlReader.Element readXml(final XmlReader reader) {
		return reader.expect("turn", "t", new XmlReader.Element() {
			public IXmlSerializable read(final XmlReader reader) {
				final TurnSnapshot snapshot = new TurnSnapshot();

				reader.expect("turn", "tu", new XmlReader.Attribute() {
					public void read(String value) {
						snapshot.turn = Integer.parseInt(value);
					}
				});
				reader.expect("round", "ro", new XmlReader.Attribute() {
					public void read(String value) {
						snapshot.round = Integer.parseInt(value);
					}
				});

				reader.expect("robots", "rs", new XmlReader.ListElement() {
					public IXmlSerializable read(XmlReader reader) {
						snapshot.robots = new ArrayList<IRobotSnapshot>();
						// prototype
						return new RobotSnapshot();
					}

					public void add(IXmlSerializable child) {
						snapshot.robots.add((RobotSnapshot) child);
					}

					public void close() {
						// allows loading of minimalistic XML, which skips dead robots, but GUI expects them
						Hashtable<String, Object> context = reader.getContext();
						Integer robotCount = (Integer) context.get("robots");
						boolean[] present = new boolean[robotCount];

						for (IRobotSnapshot robot : snapshot.robots) {
							present[robot.getRobotIndex()] = true;
						}
						for (int i = 0; i < robotCount; i++) {
							if (!present[i]) {
								String name = (String) context.get(Integer.toString(i));

								snapshot.robots.add(new RobotSnapshot(name, i, RobotState.DEAD));
							}
						}
					}
				});

				reader.expect("bullets", "bs", new XmlReader.ListElement() {
					public IXmlSerializable read(XmlReader reader) {
						snapshot.bullets = new ArrayList<IBulletSnapshot>();
						// prototype
						return new BulletSnapshot();
					}

					public void add(IXmlSerializable child) {
						snapshot.bullets.add((BulletSnapshot) child);
					}

					public void close() {}
				});

				reader.expect("landmines", "ls", new XmlReader.ListElement() {
					public IXmlSerializable read(XmlReader reader) {
						snapshot.landmines = new ArrayList<ILandmineSnapshot>();
						// prototype
						return new LandmineSnapshot();
					}

					public void add(IXmlSerializable child) {
						snapshot.landmines.add((LandmineSnapshot) child);
					}

					public void close() {
					}
				});

				return snapshot;
			}
		});
	}

	public ITeleporterSnapshot[] getTeleporters() {
		return teleports.toArray(new ITeleporterSnapshot[teleports.size()]);
	}
	
	@Override
	public IZLevelSnapshot[] getZLevels() {
		return zSnaps.toArray(new IZLevelSnapshot[zSnaps.size()]);
	}

	@Override
	public ILandmineSnapshot[] getLandmines() {
		return landmines.toArray(new ILandmineSnapshot[landmines.size()]);
	}
}

//=======
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

/**
package net.sf.robocode.battle.snapshot;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.IRenderable;
import net.sf.robocode.battle.item.ItemDrop;
import net.sf.robocode.battle.peer.BulletPeer;
import net.sf.robocode.battle.peer.LandminePeer;
import net.sf.robocode.battle.peer.RobotPeer;
import net.sf.robocode.battle.EffectArea;
import net.sf.robocode.serialization.IXmlSerializable;
import net.sf.robocode.serialization.XmlReader;
import net.sf.robocode.serialization.SerializableOptions;
import net.sf.robocode.serialization.XmlWriter;
import robocode.control.snapshot.*;

import java.io.IOException;
import java.util.*;

/**
 * A snapshot of a battle turn at a specific time instant in a battle. The
 * snapshot contains a snapshot of the battle turn data at that specific time.
 * 
 * @author Flemming N. Larsen (original)
 * @author Pavel Savara (contributor)
 * @since 1.6.1
 */

/**

public final class TurnSnapshot implements java.io.Serializable,
		IXmlSerializable, ITurnSnapshot {

	private static final long serialVersionUID = 1L;

	/** List of snapshots for the robots participating in the battle */
/**
	private List<IRobotSnapshot> robots;
	/** List of snapshots for the bullets that are currently on the battlefield */
/**
	private List<IBulletSnapshot> bullets;
/**

	private List<ILandmineSnapshot> landmines;
	/** List of snapshots for the items that are currently on the battlefield */
	// TODO expand this use
/**
	private List<IItemSnapshot> items;
	/**
	/** List of snapshots of effect areas */
/**
	private List<IEffectAreaSnapshot> effArea;
	private List<IRenderableSnapshot> customObj;
	/** Current round in the battle */
/**
	private int round;

	/** Current turn in the battle round */
/**
	private int turn;
	/** Current TPS (turns per second) */
/**
	private int tps;
	

	/**
	 * Creates a snapshot of a battle turn that must be filled out with data
	 * later.
	 */
/**
	public TurnSnapshot() {
	}

	/**
	 * Creates a snapshot of a battle turn.
	 * 
	 * @param battle
	 *            the battle to make a snapshot of.
	 * @param battleRobots
	 *            the robots participating in the battle.
	 * @param battleBullets
	 *            the current bullet on the battlefield.
	 * @param readoutText
	 *            {@code true} if the output text from the robots must be
	 *            included in the snapshot; {@code false} otherwise.
	 */
/**
	public TurnSnapshot(Battle battle, List<RobotPeer> battleRobots,
			List<BulletPeer> battleBullets, List<LandminePeer> battleLandmines,
			List<EffectArea> effectAreas, List<IRenderable> customObjects,
			List<ItemDrop> battleItems, boolean readoutText) {
		robots = new ArrayList<IRobotSnapshot>();
		bullets = new ArrayList<IBulletSnapshot>();
		landmines = new ArrayList<ILandmineSnapshot>();
		items = new ArrayList<IItemSnapshot>();
		effArea = new ArrayList<IEffectAreaSnapshot>();
		customObj = new ArrayList<IRenderableSnapshot>();

		for (RobotPeer robotPeer : battleRobots) {
			robots.add(new RobotSnapshot(robotPeer, readoutText));
		}

		for (BulletPeer bulletPeer : battleBullets) {
			bullets.add(new BulletSnapshot(bulletPeer));
		}

		for (LandminePeer landminePeer : battleLandmines) {
			landmines.add(new LandmineSnapshot(landminePeer));
		}
/**
		/*--ItemController--*/
/**
		for (ItemDrop item : battleItems) {
			items.add(new ItemSnapshot(item));
		}

		for (EffectArea effectArea : effectAreas) {
			effArea.add(new EffectAreaSnapshot(effectArea));
		}

		for (IRenderable customObject : customObjects) {
			customObj.add(new RenderableSnapshot(customObject));
		}

		tps = battle.getTPS();
		turn = battle.getTime();
		round = battle.getRoundNum();
	}

	@Override
	public String toString() {
		return this.round + "/" + turn + " (" + this.robots.size() + ")";
	}

	/**
	 * {@inheritDoc}
	 */
/**
	public IRobotSnapshot[] getRobots() {
		return robots.toArray(new IRobotSnapshot[robots.size()]);
	}

	@Override
	public IItemSnapshot[] getItems() {
		// TODO Auto-generated method stub
		return items.toArray(new IItemSnapshot[items.size()]);
	}

	/**
	 * {@inheritDoc}
	 */
/**
	public IBulletSnapshot[] getBullets() {
		return bullets.toArray(new IBulletSnapshot[bullets.size()]);
	}

	/**
	 * {@inheritDoc}
	 */
/**
	public ILandmineSnapshot[] getLandmines() {
		return landmines.toArray(new ILandmineSnapshot[landmines.size()]);
	}

	/**
	 * {@inheritDoc}
	 */
/**
	public IItemSnapshot[] getItem() {
		return items.toArray(new IItemSnapshot[items.size()]);
	}

	/**
	 * {@inheritDoc}
	 */
/**
	public int getTPS() {
		return tps;
	}

	/**
	 * {@inheritDoc}
	 */
/**
	public int getRound() {
		return round;
	}

	/**
	 * {@inheritDoc}
	 */
/**
	public int getTurn() {
		return turn;
	}

	public IEffectAreaSnapshot[] getEffectAreas() {
		return effArea.toArray(new IEffectAreaSnapshot[effArea.size()]);
	}

	@Override
	public IRenderableSnapshot[] getCustomObjects() {
		return customObj.toArray(new IRenderableSnapshot[customObj.size()]);
	}

	/**
	 * {@inheritDoc}
	 */
/**
	public IScoreSnapshot[] getSortedTeamScores() {
		List<IScoreSnapshot> copy = new ArrayList<IScoreSnapshot>(
				Arrays.asList(getIndexedTeamScores()));

		Collections.sort(copy);
		Collections.reverse(copy);
		return copy.toArray(new IScoreSnapshot[copy.size()]);
	}

	/**
	 * {@inheritDoc}
	 */
/**
	public IScoreSnapshot[] getIndexedTeamScores() {
		// team scores are computed on demand from team scores to not duplicate
		// data in the snapshot

		List<IScoreSnapshot> results = new ArrayList<IScoreSnapshot>();

		// noinspection ForLoopReplaceableByForEach
		for (int i = 0; i < robots.size(); i++) {
			results.add(null);
		}
		for (IRobotSnapshot robot : robots) {
			final int contestantIndex = robot.getContestantIndex();
			final IScoreSnapshot snapshot = results.get(contestantIndex);

			IScoreSnapshot score = (snapshot == null) ? robot
					.getScoreSnapshot() : new ScoreSnapshot(
					robot.getTeamName(), snapshot, robot.getScoreSnapshot());

			results.set(contestantIndex, score);
		}
		List<IScoreSnapshot> scores = new ArrayList<IScoreSnapshot>();

		for (IScoreSnapshot scoreSnapshot : results) {
			if (scoreSnapshot != null) {
				scores.add(scoreSnapshot);
			}
		}

		return scores.toArray(new IScoreSnapshot[scores.size()]);
	}

	public void stripDetails(SerializableOptions options) {
		for (IRobotSnapshot r : getRobots()) {
			((RobotSnapshot) r).stripDetails(options);
		}
	}

	/**
	 * {@inheritDoc}
	 */
/**
	public void writeXml(XmlWriter writer, SerializableOptions options)
			throws IOException {
		writer.startElement(options.shortAttributes ? "t" : "turn");
		{
			writer.writeAttribute(options.shortAttributes ? "ro" : "round",
					round);
			writer.writeAttribute(options.shortAttributes ? "tu" : "turn", turn);
			if (!options.skipVersion) {
				writer.writeAttribute("ver", serialVersionUID);
			}

			writer.startElement(options.shortAttributes ? "rs" : "robots");
			{
				SerializableOptions op = options;

				if (turn == 0) {
					op = new SerializableOptions(options);
					op.skipNames = false;
				}
				for (IRobotSnapshot r : robots) {
					final RobotSnapshot rs = (RobotSnapshot) r;

					if (!options.skipExploded
							|| rs.getState() != RobotState.DEAD) {
						rs.writeXml(writer, op);
					} else {
						boolean writeFirstExplosionFrame = false;
						boolean writeSecondExplosionFrame = false;
						for (IBulletSnapshot b : bullets) {
							if (b.isExplosion() && b.getFrame() == 0
									&& b.getVictimIndex() == r.getRobotIndex()) {
								writeFirstExplosionFrame = true;
								break;
							}
						}
						if (writeFirstExplosionFrame) {
							rs.writeXml(writer, op);
						}
						for (ILandmineSnapshot l : landmines) {
							if (l.getFrame() == 0&& l.getVictimIndex() == r.getRobotIndex()) {
								writeSecondExplosionFrame = true;
								break;
							}
						}
						if (writeSecondExplosionFrame) {
							rs.writeXml(writer, op);
						}
					}
				}
			}
			writer.endElement();

			writer.startElement(options.shortAttributes ? "bs" : "bullets");
			{
				for (IBulletSnapshot b : bullets) {
					final BulletSnapshot bs = (BulletSnapshot) b;
					final BulletState state = bs.getState();

					if (!options.skipExploded
							|| (state != BulletState.EXPLODED
									&& state != BulletState.INACTIVE && (bs
									.getFrame() == 0 || state == BulletState.MOVING))) {
						bs.writeXml(writer, options);
					}
				}

			}
			writer.endElement();

			writer.startElement(options.shortAttributes ? "bs" : "Landmines");
			{
				for (ILandmineSnapshot l : landmines) {
					final LandmineSnapshot ls = (LandmineSnapshot) l;
					final LandmineState state = ls.getState();

					if (!options.skipExploded
							|| (state != LandmineState.EXPLODED
									&& state != LandmineState.INACTIVE && ls
									.getFrame() == 0)) {
						ls.writeXml(writer, options);
					}
				}
			}
			writer.endElement();
		}
		writer.endElement();
	}

	/**
	 * {@inheritDoc}
	 */
/**
	public XmlReader.Element readXml(final XmlReader reader) {
		return reader.expect("turn", "t", new XmlReader.Element() {
			public IXmlSerializable read(final XmlReader reader) {
				final TurnSnapshot snapshot = new TurnSnapshot();

				reader.expect("turn", "tu", new XmlReader.Attribute() {
					public void read(String value) {
						snapshot.turn = Integer.parseInt(value);
					}
				});
				reader.expect("round", "ro", new XmlReader.Attribute() {
					public void read(String value) {
						snapshot.round = Integer.parseInt(value);
					}
				});

				reader.expect("robots", "rs", new XmlReader.ListElement() {
					public IXmlSerializable read(XmlReader reader) {
						snapshot.robots = new ArrayList<IRobotSnapshot>();
						// prototype
						return new RobotSnapshot();
					}

					public void add(IXmlSerializable child) {
						snapshot.robots.add((RobotSnapshot) child);
					}

					public void close() {
						// allows loading of minimalistic XML, which skips dead
						// robots, but GUI expects them
						Hashtable<String, Object> context = reader.getContext();
						Integer robotCount = (Integer) context.get("robots");
						boolean[] present = new boolean[robotCount];

						for (IRobotSnapshot robot : snapshot.robots) {
							present[robot.getRobotIndex()] = true;
						}
						for (int i = 0; i < robotCount; i++) {
							if (!present[i]) {
								String name = (String) context.get(Integer
										.toString(i));

								snapshot.robots.add(new RobotSnapshot(name, i,
										RobotState.DEAD));
							}
						}
					}
				});

				reader.expect("bullets", "bs", new XmlReader.ListElement() {
					public IXmlSerializable read(XmlReader reader) {
						snapshot.bullets = new ArrayList<IBulletSnapshot>();
						// prototype
						return new BulletSnapshot();
					}

					public void add(IXmlSerializable child) {
						snapshot.bullets.add((BulletSnapshot) child);
					}

					public void close() {
					}
				});

				reader.expect("landmines", "ls", new XmlReader.ListElement() {
					public IXmlSerializable read(XmlReader reader) {
						snapshot.landmines = new ArrayList<ILandmineSnapshot>();
						// prototype
						return new LandmineSnapshot();
					}

					public void add(IXmlSerializable child) {
						snapshot.landmines.add((LandmineSnapshot) child);
					}

					public void close() {
					}
				});

				return snapshot;
			}
		});
	}

}
>>>>>>> MCJJ
*/
