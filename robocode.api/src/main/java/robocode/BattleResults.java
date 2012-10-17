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
 *     Flemming N. Larsen
 *     - Javadocs
 *******************************************************************************/
package robocode;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import net.sf.robocode.serialization.ISerializableHelper;
import net.sf.robocode.serialization.RbSerializer;

/**
 * Contains the battle results returned by {@link BattleEndedEvent#getResults()}
 * when a battle has ended.
 *
 * @author Pavel Savara (original)
 * @see BattleEndedEvent#getResults()
 * @see Robot#onBattleEnded(BattleEndedEvent)
 * @since 1.6.1
 */
public class BattleResults implements java.io.Serializable,
                                      Comparable<BattleResults> {

    protected static final long serialVersionUID = 1L;
    protected String teamLeaderName;
    protected int rank;
    /* ArrayList of scores for type String, ArrayList:
     * 0: total score
     * 1: current score
     */
    protected static HashMap<String, ArrayList<Double>> scores;
    
    /* Scores mapping to their respective unique IDs */
    protected static HashMap<String, Integer> scoreIDs;
    
    static {
    	scoreIDs = new HashMap<String, Integer>();
    	/* Robocode Scores */
    	scoreIDs.put("total", 0);
    	scoreIDs.put("survival", 1);
    	scoreIDs.put("lastsurvivorbonus", 2);
    	scoreIDs.put("bulletdamage", 3);
    	scoreIDs.put("bulletkillbonus", 4);
    	scoreIDs.put("rammingdamage", 5);
    	scoreIDs.put("rammingkill", 6);
    	scoreIDs.put("firsts", 7);
    	scoreIDs.put("seconds", 8);
    	scoreIDs.put("thirds", 9);
    	
    	/* Mode-specific Scores */
    	scoreIDs.put("flag", 10);
    }
    
    /**
     * Initialise the scores to all be 0.0
     */
    private void initialiseScores() {
    	scores = new HashMap<String, ArrayList<Double>>();
    	for (String key : scoreIDs.keySet()) {
    		scores.put(key, new ArrayList<Double>());
    		scores.get(key).add(0.0);
    		scores.get(key).add(0.0);
    	}
    }

    /**
     * Create a new Battle Results
     * @param teamLeaderName Team leader's name
     * @param rank Rank of the robot
     * @param scores Scores for the robot
     */
    @SuppressWarnings("unchecked")
	public BattleResults(String teamLeaderName, int rank, HashMap<String, ArrayList<Double>> scores) { 
    	this.initialiseScores();
    	this.teamLeaderName = teamLeaderName;
    	this.rank = rank;
    	this.scores = (HashMap<String, ArrayList<Double>>) scores.clone();
    }
    
    /**
     * Populate the scores map with the scores stored in Battle Results
     * @param scores The scores to modify
     */
    @SuppressWarnings("unchecked")
	public void populateScoresMap(HashMap<String, ArrayList<Double>> scores) {
    	scores = (HashMap<String, ArrayList<Double>>) this.scores.clone();
    }

    /**
     * Returns the name of the team leader in the team or the name of the
     * robot if the robot is not participating in a team.
     *
     * @return the name of the team leader in the team or the name of the robot.
     */
    public String getTeamLeaderName() {
        return teamLeaderName;
    }

    /**
     * Returns the rank of this robot in the battle results.
     *
     * @return the rank of this robot in the battle results.
     */
    public int getRank() {
        return rank;
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
    @Override
    public int compareTo(BattleResults o) {
    	double score = this.getScores(false, "total");
        return ((Double) score).compareTo(o.getScores(false, "total"));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        double score = this.getScores(false, "total");

        temp = Double.doubleToLongBits(score);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
    	double score = this.getScores(false, "total");
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        BattleResults other = (BattleResults) obj;

        if (Double.doubleToLongBits(score) != Double.doubleToLongBits(other.getScores(false, "total"))) {
            return false;
        }
        return true;
    }

    static ISerializableHelper createHiddenSerializer() {
        return new SerializableHelper();
    }

    private static class SerializableHelper implements ISerializableHelper {

        @Override
        public int sizeOf(RbSerializer serializer, Object object) {
            BattleResults obj = (BattleResults) object;

            return RbSerializer.SIZEOF_TYPEINFO + serializer.sizeOf(obj.teamLeaderName) + 4 * RbSerializer.SIZEOF_INT
                    + 7 * RbSerializer.SIZEOF_DOUBLE;
        }

        /* TODO Scoring rework */
        @Override
        public void serialize(RbSerializer serializer, ByteBuffer buffer, Object object) {
            BattleResults obj = (BattleResults) object;

            serializer.serialize(buffer, obj.teamLeaderName);
            serializer.serialize(buffer, obj.rank);
//            serializer.serialize(buffer, obj.score);
//            serializer.serialize(buffer, obj.survival);
//            serializer.serialize(buffer, obj.lastSurvivorBonus);
//            serializer.serialize(buffer, obj.bulletDamage);
//            serializer.serialize(buffer, obj.bulletDamageBonus);
//            serializer.serialize(buffer, obj.ramDamage);
//            serializer.serialize(buffer, obj.ramDamageBonus);
//            serializer.serialize(buffer, obj.flagScore);
//            serializer.serialize(buffer, obj.firsts);
//            serializer.serialize(buffer, obj.seconds);
//            serializer.serialize(buffer, obj.thirds);
            /*
             * Any custom scoring option should be serializable, as above
             */
        }

        @Override
        public Object deserialize(RbSerializer serializer, ByteBuffer buffer) {
            String teamLeaderName = serializer.deserializeString(buffer);
            int rank = buffer.getInt();
            double score = buffer.getDouble();
            double survival = buffer.getDouble();
            double lastSurvivorBonus = buffer.getDouble();
            double bulletDamage = buffer.getDouble();
            double bulletDamageBonus = buffer.getDouble();
            double ramDamage = buffer.getDouble();
            double ramDamageBonus = buffer.getDouble();
            //Team-Telos addition
            double flagScore = buffer.getDouble();
            int firsts = buffer.getInt();
            int seconds = buffer.getInt();
            int thirds = buffer.getInt();

            return new BattleResults(teamLeaderName, rank, scores);
        }
    }
}
