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

	/**
	 * TODO
	 * Team-Telos : Convert BattleResults into a form where it only
	 * takes two arguments: A string for team leader name and
	 * A list of some sort containing each of the score variables.
	 * 
	 * each getter method will return a certain index of the list.
	 * each setter will set a certain index of the list
	 */
    protected static final long serialVersionUID = 1L;
    protected String teamLeaderName;
    protected int rank;
    /* Current score HashMap */
    protected static HashMap<String, Double> scores;
    
    /* Scores mapping to their respective unique IDs */
    protected static ArrayList<String> scoreIDs;
    
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

    /**
     * Create a new Battle Results
     * @param teamLeaderName Team leader's name
     * @param rank Rank of the robot
     * @param allScores Scores for the robot
     */
	public BattleResults(String teamLeaderName, int rank, HashMap<String, Double> scores) { 
    	this.teamLeaderName = teamLeaderName;
    	this.rank = rank;
    	this.scores = (HashMap<String, Double>) scores.clone();
    }
	
	/**
	 * Create a new Battle results
	 * @param teamLeaderName Team leader's name
	 * @param rank Rank of the robot
	 */
	public BattleResults(String teamLeaderName, int rank) {
		this.teamLeaderName = teamLeaderName;
		this.rank = rank;
		scores = new HashMap<String, Double>();
		
		for (String score : scoreIDs) {
			scores.put(score, 0.0);
		}
	}
	
	/**
	 * Set the scores map
	 * @param scores The scores to store
	 */
	public void setScores(HashMap<String, ArrayList<Double>> allScores) {
		for (String score : scores.keySet()) {
    		scores.put(score, allScores.get(score).get(1));
    	}
	}

    /**
     * Get the score map
     * @return The scores map
     */
	public HashMap<String, Double> getScoreMap() {
    	return (HashMap<String, Double>) scores.clone();
    }
    
    /**
     * Populate the scores map with the scores stored in Battle Results
     * @param scores The scores to modify
     */
	public void populateScoresMap(HashMap<String, Double> scores) {
    	scores = (HashMap<String, Double>) this.scores.clone();
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
     * @param scores The string representation of the score
     * @return The sum of the scores 'scores'
     */
    public double getScores(String ... scoresToGet) {
    	double totalScore = 0.0;
    	
    	/* Get the score for each kind of score requested */
    	for (String score : scoresToGet) {
    		totalScore += scores.get(score);
    	}
    	
    	return totalScore;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(BattleResults o) {
    	double score = this.getScores("total");
        return ((Double) score).compareTo(o.getScores("total"));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        double score = this.getScores("total");

        temp = Double.doubleToLongBits(score);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
    	double score = this.getScores("total");
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

        if (Double.doubleToLongBits(score) != Double.doubleToLongBits(other.getScores("total"))) {
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

        /* TODO Rework Scoring */
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
