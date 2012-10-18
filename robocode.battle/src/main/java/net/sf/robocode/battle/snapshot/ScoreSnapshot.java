/*******************************************************************************
 * Copyright (c) 2001-2012 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 *
 * Contributors:
 *     Pavel Savara
 *     - Initial implementation
 *     Pavel Savara
 *     - Xml Serialization, refactoring
 *******************************************************************************/
package net.sf.robocode.battle.snapshot;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import net.sf.robocode.battle.peer.RobotStatistics;
import net.sf.robocode.serialization.IXmlSerializable;
import net.sf.robocode.serialization.SerializableOptions;
import net.sf.robocode.serialization.XmlReader;
import net.sf.robocode.serialization.XmlWriter;
import robocode.control.snapshot.IScoreSnapshot;

/**
 * A snapshot of a score at a specific time instant in a battle.
 * The snapshot contains a snapshot of the score data at that specific time.
 *
 * @author Pavel Savara (original)
 * @author Flemming N. Larsen (contributor)
 * @since 1.6.1
 */
public final class ScoreSnapshot implements Serializable, IXmlSerializable,
                                            IScoreSnapshot {

    private static final long serialVersionUID = 1L;
    private HashMap<String, ArrayList<Double>> scores;
    
    /* Scores mapping to their respective unique IDs */
    private static ArrayList<String> scoreIDs;
    
    static {
    	scoreIDs = new ArrayList<String>();
    	/* Robocode Scores */
    	scoreIDs.add("total");
    	scoreIDs.add("survival");
    	scoreIDs.add("lastsurvivorbonus");
    	scoreIDs.add("bulletdamage");
    	scoreIDs.add("bulletkillbonus");
    	scoreIDs.add("rammingdamage");
    	scoreIDs.add("rammingkill");
    	scoreIDs.add("firsts");
    	scoreIDs.add("seconds");
    	scoreIDs.add("thirds");
    	
    	/* Mode-specific Scores */
    	scoreIDs.add("flag");
    }
    
    /** The name of the contestant, i.e. a robot or team */
    private String name;

    /**
     * Creates a snapshot of a score that must be filled out with data later.
     */
    public ScoreSnapshot() {
    	scores = new HashMap<String, ArrayList<Double>>();
    }

    /**
     * Creates a snapshot of a score.
     *
     * @param score the contestant's score to take a snapshot of.
     * @param contestantName the name of the contestant.
     */
    public ScoreSnapshot(String contestantName, HashMap<String, ArrayList<Double>> scoreMap) {
    	/* TODO Scoring-Rework - Total Kills */
    	scores = (HashMap<String, ArrayList<Double>>) scoreMap.clone();
        this.name = contestantName;
    }

    /**
     * Creates a snapshot of a score based on two sets of scores that are added together.
     *
     * @param contestantName the name of the contestant.
     * @param score1 the contestant's first set of scores to base this snapshot on.
     * @param score2 the contestant's second set of scores that must be added to the first set of scores.
     */
    public ScoreSnapshot(String contestantName, IScoreSnapshot score1, IScoreSnapshot score2) {
    	scores = new HashMap<String, ArrayList<Double>>();
        this.name = contestantName;
        this.initialiseScores();
        
        for (String score : scoreIDs) {
        	scores.get(score).add(0, score1.getScores(false, score) + score2.getScores(false, score));
        	scores.get(score).add(1, score1.getScores(true, score) + score2.getScores(true, score));
        }
    }

    @Override
    public String toString() {
        return this.getTotalScore() + "/" + this.getCurrentScore();
    }
    
    /**
     * Initialise the scores to all be 0.0
     */
    private void initialiseScores() {
    	scores = new HashMap<String, ArrayList<Double>>();
    	for (String key : scoreIDs) {
    		scores.put(key, new ArrayList<Double>());
    		scores.get(key).add(0.0);
    		scores.get(key).add(0.0);
    	}
    }
    
    /**
     * Get a sum of the scores, options are:
     * 'total'
     * 'survival'
     * 'lastsurvivorbonus'
     * 'bulletdamage'
     * 'bulletkillbonus'
     * 'rammingdamage'
     * 'rammingkill'
     * 'firsts'
     * 'seconds'
     * 'thirds'
     * 'flag'
     * @param current true for current scores or false for total
     * @param scores The string representation of the score
     * @return The sum of the scores 'scores', current or total
     */
    public double getScores(boolean current, String ... scoresToGet) {
    	/* 0 for total, 1 for current */
    	int index = (current) ? 1 : 0;
    	double totalScore = 0.0;
    	
    	/* Get the score for each kind of score requested */
    	for (String score : scoresToGet) {
    		totalScore += scores.get(score).get(index);
    	}
    	
    	return totalScore;
    }

    /**
     * {@inheritDoc}
     */
    public double getTotalScore() {
    	return this.getScores(false, "total");
    }
    
    /**
     * {@inheritDoc}
     */
    public double getCurrentScore() {
    	return this.getScores(true, "total");
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(Object obj) {
        if (obj instanceof IScoreSnapshot) {
            IScoreSnapshot scoreSnapshot = (IScoreSnapshot) obj;

            double myScore = getTotalScore() + getCurrentScore();
            double hisScore = scoreSnapshot.getTotalScore() + scoreSnapshot.getCurrentScore();

            if (myScore < hisScore) {
                return -1;
            }
            if (myScore > hisScore) {
                return 1;
            }
        }
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeXml(XmlWriter writer, SerializableOptions options) throws IOException {
    	/* TODO Scoring Rework */
        writer.startElement(options.shortAttributes ? "sc" : "score");
        {
            if (!options.skipNames) {
                writer.writeAttribute("name", name);
            }
            if (!options.skipTotal) {
                writer.writeAttribute(options.shortAttributes ? "t" : "totalScore", this.getScores(false, "total"), options.trimPrecision);
                writer.writeAttribute(options.shortAttributes ? "tss" : "totalSurvivalScore", this.getScores(false, "survival"),
                                      options.trimPrecision);
                writer.writeAttribute(options.shortAttributes ? "tls" : "totalLastSurvivorBonus", this.getScores(false, "lastsurvivorbonus"),
                                      options.trimPrecision);
                writer.writeAttribute(options.shortAttributes ? "tbd" : "totalBulletDamageScore", this.getScores(false, "bulletdamage"),
                                      options.trimPrecision);
                writer.writeAttribute(options.shortAttributes ? "tbk" : "totalBulletKillBonus", this.getScores(false, "bulletkillbonus"),
                                      options.trimPrecision);
                writer.writeAttribute(options.shortAttributes ? "trd" : "totalRammingDamageScore",
                                      this.getScores(false, "rammingdamage"), options.trimPrecision);
                writer.writeAttribute(options.shortAttributes ? "trk" : "totalRammingKillBonus", this.getScores(false, "rammingkill"),
                                      options.trimPrecision);
                writer.writeAttribute(options.shortAttributes ? "t1" : "totalFirsts", (int) this.getScores(false, "firsts"));
                writer.writeAttribute(options.shortAttributes ? "t2" : "totalSeconds", (int) this.getScores(false, "seconds"));
                writer.writeAttribute(options.shortAttributes ? "t3" : "totalThirds", (int) this.getScores(false, "thirds"));
            }
            writer.writeAttribute(options.shortAttributes ? "c" : "currentScore", this.getScores(true, "total"), options.trimPrecision);
            writer.writeAttribute(options.shortAttributes ? "ss" : "currentSurvivalScore", this.getScores(true, "survival"),
                                  options.trimPrecision);
            writer.writeAttribute(options.shortAttributes ? "bd" : "currentBulletDamageScore", this.getScores(true, "bulletdamage"),
                                  options.trimPrecision);
            writer.writeAttribute(options.shortAttributes ? "bk" : "currentBulletKillBonus", this.getScores(true, "bulletkillbonus"),
                                  options.trimPrecision);
            writer.writeAttribute(options.shortAttributes ? "rd" : "currentRammingDamageScore",
                                  this.getScores(true, "rammingdamage"), options.trimPrecision);
            writer.writeAttribute(options.shortAttributes ? "rk" : "currentRammingKillBonus", this.getScores(true, "rammingkill"),
                                  options.trimPrecision);
            if (!options.skipVersion) {
                writer.writeAttribute("ver", serialVersionUID);
            }

        }
        writer.endElement();
    }

    // allows loading of minimalistic XML
    ScoreSnapshot(String contestantName) {
        this.name = contestantName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public XmlReader.Element readXml(XmlReader reader) {
    	/* TODO Scoring-Rework */
        return reader.expect("score", "sc", new XmlReader.Element() {
            @Override
            public IXmlSerializable read(XmlReader reader) {
                final ScoreSnapshot snapshot = new ScoreSnapshot();

                reader.expect("name", new XmlReader.Attribute() {
                    @Override
                    public void read(String value) {
                        snapshot.name = value;
                    }
                });
                reader.expect("totalScore", "t", new XmlReader.Attribute() {
                    @Override
                    public void read(String value) {
//                        snapshot.totalScore = Double.parseDouble(value);
                    }
                });
                reader.expect("totalSurvivalScore", "tss", new XmlReader.Attribute() {
                    @Override
                    public void read(String value) {
//                        snapshot.totalSurvivalScore = Double.parseDouble(value);
                    }
                });
                reader.expect("totalLastSurvivorBonus", "tls", new XmlReader.Attribute() {
                    @Override
                    public void read(String value) {
//                        snapshot.totalLastSurvivorBonus = Double.parseDouble(value);
                    }
                });
                reader.expect("totalBulletDamageScore", "tbd", new XmlReader.Attribute() {
                    @Override
                    public void read(String value) {
//                        snapshot.totalBulletDamageScore = Double.parseDouble(value);
                    }
                });
                reader.expect("totalBulletKillBonus", "tbk", new XmlReader.Attribute() {
                    @Override
                    public void read(String value) {
//                        snapshot.totalBulletKillBonus = Double.parseDouble(value);
                    }
                });
                reader.expect("totalRammingDamageScore", "trd", new XmlReader.Attribute() {
                    @Override
                    public void read(String value) {
//                        snapshot.totalRammingDamageScore = Double.parseDouble(value);
                    }
                });
                reader.expect("totalRammingKillBonus", "trk", new XmlReader.Attribute() {
                    @Override
                    public void read(String value) {
//                        snapshot.totalRammingKillBonus = Double.parseDouble(value);
                    }
                });
                reader.expect("totalFirsts", "t1", new XmlReader.Attribute() {
                    @Override
                    public void read(String value) {
//                        snapshot.totalFirsts = Integer.parseInt(value);
                    }
                });
                reader.expect("totalSeconds", "t2", new XmlReader.Attribute() {
                    @Override
                    public void read(String value) {
//                        snapshot.totalSeconds = Integer.parseInt(value);
                    }
                });
                reader.expect("totalThirds", "t3", new XmlReader.Attribute() {
                    @Override
                    public void read(String value) {
//                        snapshot.totalThirds = Integer.parseInt(value);
                    }
                });
                reader.expect("currentScore", "c", new XmlReader.Attribute() {
                    @Override
                    public void read(String value) {
//                        snapshot.currentScore = Double.parseDouble(value);
                    }
                });
                reader.expect("currentSurvivalScore", "ss", new XmlReader.Attribute() {
                    @Override
                    public void read(String value) {
//                        snapshot.currentSurvivalScore = Double.parseDouble(value);
                    }
                });
                reader.expect("currentBulletDamageScore", "bd", new XmlReader.Attribute() {
                    @Override
                    public void read(String value) {
//                        snapshot.currentBulletDamageScore = Double.parseDouble(value);
                    }
                });
                reader.expect("currentBulletKillBonus", "bk", new XmlReader.Attribute() {
                    @Override
                    public void read(String value) {
//                        snapshot.currentBulletKillBonus = Double.parseDouble(value);
                    }
                });
                reader.expect("currentRammingDamageScore", "rd", new XmlReader.Attribute() {
                    @Override
                    public void read(String value) {
//                        snapshot.currentRammingDamageScore = Double.parseDouble(value);
                    }
                });
                reader.expect("currentRammingKillBonus", "rk", new XmlReader.Attribute() {
                    @Override
                    public void read(String value) {
//                        snapshot.currentRammingKillBonus = Double.parseDouble(value);
                    }
                });
                return snapshot;
            }
        });
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;

        for (String score : scoreIDs) {
        	if (score.equals("firsts") || score.equals("seconds") || score.equals("thirds")) {
        		continue;
        	}
        	
        	temp = Double.doubleToLongBits(this.getScores(false, score));
            result = prime * result + (int) (temp ^ (temp >>> 32));
            
            temp = Double.doubleToLongBits(this.getScores(true, score));
            result = prime * result + (int) (temp ^ (temp >>> 32));
        }

        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + (int) this.getScores(false, "firsts");
        result = prime * result + (int) this.getScores(false, "seconds");
        result = prime * result + (int) this.getScores(false, "thirds");
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ScoreSnapshot other = (ScoreSnapshot) obj;

        for (String score : scoreIDs) {
        	if (score.equals("firsts") || score.equals("seconds") || score.equals("thirds")) {
        		continue;
        	}
        	
        	if (Double.doubleToLongBits(this.getScores(true, score)) != 
        			Double.doubleToLongBits(other.getScores(true, score))) {
                return false;
            }
        	
        	if (Double.doubleToLongBits(this.getScores(false, score)) != 
        			Double.doubleToLongBits(other.getScores(false, score))) {
                return false;
            }
        }
        
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        
        return true;
    }
}
