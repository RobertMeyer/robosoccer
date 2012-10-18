/*******************************************************************************
 * Copyright (c) 2001-2012 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 *
 * Contributors:
 *     Mathew A. Nelson
 *     - Initial API and implementation
 *     Flemming N. Larsen
 *     - Replaced ContestantPeerVector with plain Vector
 *     - Added Rank column
 *     - Ported to Java 5
 *     - Optimized
 *     - Code cleanup
 *     - Updated to use methods from the Logger and StringUtil, which
 *       replaces methods that have been (re)moved from the robocode.util.Utils
 *     - Changed the column names to be more informative and equal in width
 *     Robert D. Maupin
 *     - Replaced old collection types like Vector and Hashtable with
 *       synchronized List and HashMap
 *     Nathaniel Troutman
 *     - Added sanity check on battle object in getRowCount()
 *     Endre Palatinus, Eniko Nagy, Attila Csizofszki and Laszlo Vigh
 *     - Score with % (percentage) in the table view
 *******************************************************************************/
package net.sf.robocode.battle;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import net.sf.robocode.io.Logger;

import robocode.BattleResults;

/**
 * Set options for Scoring to display in the Results Table at the
 * end of the battle.
 * @author Brandon Warwick (BrandonCW) - team-Telos (Re-write)
 * @author Mathew A. Nelson (original)
 * @author Flemming N. Larsen (contributor)
 * @author Robert D. Maupin (contributor)
 * @author Nathaniel Troutman (contributor)
 * @author Endre Palatinus, Eniko Nagy, Attila Csizofszki and Laszlo Vigh (contributors)
 */
@SuppressWarnings("serial")
public class BattleResultsTableModel extends javax.swing.table.AbstractTableModel {
	/* HashMap linking a unique int to it's respective score title */
	private HashMap<Integer, String> scoreTitles;
	/* Columns to display */
	private ArrayList<Integer> columns;
	/* The battles results */
	private BattleResults[] results;
	/* The number of rounds */
	private int numberRounds;
	/* Sum of total scores gather by all the robots */
	private double totalScore;
	/* Title of the high scores */
	private String title;
	
	/**
	 * New empty BattleResultsTableModel without results (won't display anything)
	 */
	public BattleResultsTableModel() {
		columns = new ArrayList<Integer>();
		/* Populate the scores titles */
		this.populateScoreTitles();
	}
	
	/**
	 * New empty BattleResultsTableModel with results (won't display anything)
	 * @param results The battle's results
	 * @param numberRounds The number of rounds played in the battle
	 */
	public BattleResultsTableModel(BattleResults[] results, int numberRounds) {
		this.numberRounds = numberRounds;
		/* Set results and populate total score */
		this.setResults(results);
		
		columns = new ArrayList<Integer>();
		/* Populate the scores titles */
		this.populateScoreTitles();
	}
	
	/**
	 * Populate the score titles with their corresponding unique ID's
	 */
	private void populateScoreTitles() {
		scoreTitles = new HashMap<Integer, String>();
		scoreTitles.put(0, "Rank");
		scoreTitles.put(1, "Robot Name");
		scoreTitles.put(2, "Total Score");
		scoreTitles.put(3, "Survival");
		scoreTitles.put(4, "Survival Bonus");
		scoreTitles.put(5, "Bullet Damage");
		scoreTitles.put(6, "Bullet Bonus");
		scoreTitles.put(7, "Ram Dmg * 2");
		scoreTitles.put(8, "Ram Bonus");
		scoreTitles.put(9, "1sts");
		scoreTitles.put(10, "2nds");
		scoreTitles.put(11, "3rds");
		scoreTitles.put(12, "Flag Score");
		scoreTitles.put(13, "Team Leader");
	}
	
	/**
	 * Set the results from a battle and calculate the total score
	 * @param results The results from a battle
	 */
	public void setResults(BattleResults[] results) {
		this.results = results;
		this.countTotalScore();
	}
	
	/**
	 * Set the number of rounds in the battle
	 * @param numberRounds Number of rounds played in the battle
	 */
	public void setNumberOfRounds(int numberRounds) {
		this.numberRounds = numberRounds;
	}
	
	/**
	 * Get the title of a column
	 * @param column The column id
	 * @return Title of column
	 */
	public String getColumnName(int column) {
		/* Column is the physical column showing but there is a chance
		 * that the Mode has set it to not show all columns so get the
		 * matched column score */
		return scoreTitles.get(columns.get(column));
	}
	
	/**
	 * Get the amount of columns
	 * @return
	 */
	public int getColumnCount() {
		return columns.size();
	}
	
	/**
	 * Get the amount of rows in the results
	 * @return Amount of rows in the results
	 */
	public int getRowCount() {
		return this.results.length;
	}
	
	/**
	 * Get the result at a specific location in the table
	 * @param results The results to query
	 * @param row
	 * @param column
	 * @return The result at (row, column)
	 */
	public Object getValueAt(int row, int column) {
		/* Get the results for the appropriate row */
		BattleResults statistics = results[row];
		
		/* Column is the physical column showing but there is a chance
		 * that the Mode has set it to not show all columns so get the
		 * matched column score */
		int scoreColumn = columns.get(column);
		
		/* Get the appropriate score */
		switch (scoreColumn) {
		case 0:
			/* Rank */
			int place = row + 1;
			
			while (place < this.getRowCount() && statistics.getScores("total") == results[place].getScores("total")) {
                place++;
            }
            return this.getPlacementString(place);
		case 1:
			/* Robot Name */
			return statistics.getTeamLeaderName();
		case 2:
			/* Total Score */
			String percent = "";
			
			if (totalScore != 0) {
				percent = " (" + NumberFormat.getPercentInstance().format(statistics.getScores("total") / totalScore) + ")";
			}
			return "" + (int) (statistics.getScores("total") + 0.5) + percent;
		case 3:
			/* Survival */
			return "" + (int) (statistics.getScores("survival") + 0.5);
		case 4:
			/* Survival Bonus */
			return "" + (int) (statistics.getScores("lastsurvivorbonus") + 0.5);
		case 5:
			/* Bullet Damage */
			return "" + (int) (statistics.getScores("bulletdamage") + 0.5);
		case 6:
			/* Bullet Damage Bonus */
			return "" + (int) (statistics.getScores("bulletkillbonus") + 0.5);
		case 7:
			/* Ram Damage */
			return "" + (int) (statistics.getScores("rammingdamage") + 0.5);
		case 8:
			/* Ram Bonus */
			return "" + (int) (statistics.getScores("rammingkill") + 0.5);
		case 9:
			/* Firsts */
			return "" + statistics.getScores("firsts");
		case 10:
			/* Seconds */
			return "" + statistics.getScores("seconds");
		case 11:
			/* Thirds */
			return "" + statistics.getScores("thirds");
		case 12:
			/* Flag Score */
			return "" + (int) (statistics.getScores("flag") + 0.5);
		case 13:
			/* Team Leader */
			return statistics.getTeamLeaderName();
		default:
			return "";	
		}
	}
	
	/**
	 * Get the Placement String (th, st, rn, rd, th)
	 * @param i The rank to determine the placement string for
	 * @return The placement suffix
	 */
	public String getPlacementString(int i) {
        String result = "" + i;

        if (i > 3 && i < 20) {
            result += "th";
        } else if (i % 10 == 1) {
            result += "st";
        } else if (i % 10 == 2) {
            result += "nd";
        } else if (i % 10 == 3) {
            result += "rd";
        } else {
            result += "th";
        }
        return result;
    }
	
	/**
	 * Clears the columns to be populated again
	 */
	public void clearColumns() {
		if (columns != null) {
			this.columns.clear();
		}
	}
	
	/**
	 * Sum up the total scores by all the robots
	 * @return Total sum
	 */
	private double countTotalScore() {
		double totalScore = 0;
		
		for (BattleResults result : results) {
			totalScore += result.getScores("total");
		}
		return totalScore;
	}

	
	/**
	 * Get the Title of the Highscores
	 * @return Title
	 */
	public String getTitle() {
		if (title == null) {
			int round = numberRounds;
			
			title = "Results for " + round + " round";
			if (round > 1) {
				title += 's';
			}
		}
		return title;
	}
	
	/**
	 * Set the title of the highscores
	 * @param title The new Title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/*********** PRINTER METHODS ***********/
	/**
	 * Print the highscores results to an output stream
	 * @param out The output stream
	 */
	public void print(PrintStream out) {
		out.println(getTitle());

        for (int col = 1; col < getColumnCount(); col++) {
            out.print(getColumnName(col) + "\t");
        }

        out.println();

        for (int row = 0; row < getRowCount(); row++) {
            out.print(getValueAt(row, 0) + ": ");
            for (int col = 1; col < getColumnCount(); col++) {
                out.print(getValueAt(row, col) + "\t");
            }
            out.println();
        }
	}
	
	/**
	 * Save the highscores results to a file
	 * @param filename Name of the file to log to
	 * @param append Append the results or not
	 */
	public void saveToFile(String filename, boolean append) {
        try {
            PrintStream out = new PrintStream(new FileOutputStream(filename, append));

            out.println(DateFormat.getDateTimeInstance().format(new Date()));

            out.println(getTitle());

            for (int col = 0; col < getColumnCount(); col++) {
                if (col > 0) {
                    out.print(',');
                }
                out.print(getColumnName(col));
            }

            out.println();

            for (int row = 0; row < getRowCount(); row++) {
                for (int col = 0; col < getColumnCount(); col++) {
                    if (col > 0) {
                        out.print(',');
                    }
                    out.print(getValueAt(row, col));
                }
                out.println();
            }

            out.println("$");

            out.close();

        } catch (IOException e) {
            Logger.logError(e);
        }
    }
	/*********** END PRINTER METHODS ***********/
	
	/**
	 * Update the column for a specific score
	 * @param id Score's unique ID
	 * @param show Show the score or not in the Results Table
	 * @param title The new title
	 */
	private void updateColumn(int id, boolean show, String title) {
		if (show) {
			columns.add(id);
			if (title != null && title != "") {
				scoreTitles.put(id, title);
			}
		} else {
			columns.remove(id);
		}
	}
	/*********** OPTIONS THAT CAN BE ADDED AS SCORES ***********/
	/**
	 * Show the overall rank
	 * @param show
	 */
 	public void showOverallRank(boolean show) {
		this.showOverallRank(show, "");
	}
 	
 	/**
	 * Show the overall rank
	 * @param show
	 * @param title The new title
	 */
 	public void showOverallRank(boolean show, String title) {
 		this.updateColumn(0, show, title);
 	}
	
	/**
	 * Show the robot's name
	 * @param show
	 */
	public void showRobotName(boolean show) {
		this.showRobotName(show, "");
	}
	
	/**
	 * Show the robot's name
	 * @param show
	 * @param title The new title
	 */
	public void showRobotName(boolean show, String title) {
		this.updateColumn(1, show, title);
	}
	
	/**
	 * Show the total score
	 * @param show
	 */
	public void showTotalScore(boolean show) {
		this.showTotalScore(show, "");
	}
	
	/**
	 * Show the total score
	 * @param show
	 * @param title The new title
	 */
	public void showTotalScore(boolean show, String title) {
		this.updateColumn(2, show, title);
	}
	
	/**
	 * Show the survival score
	 * @param show
	 */
	public void showSurvival(boolean show) {
		this.showSurvival(show, "");
	}
	
	/**
	 * Show the survival score
	 * @param show
	 * @param title The new title
	 */
	public void showSurvival(boolean show, String title) {
		this.updateColumn(3, show, title);
	}
	
	/**
	 * Show the survival bonus
	 * @param show
	 */
	public void showSurvivalBonus(boolean show) {
		this.showSurvivalBonus(show, "");
	}
	
	/**
	 * Show the survival bonus
	 * @param show
	 * @param title The new title
	 */
	public void showSurvivalBonus(boolean show, String title) {
		this.updateColumn(4, show, title);
	}
	
	/**
	 * Show the bullet damage score
	 * @param show
	 */
	public void showBulletDamage(boolean show) {
		this.showBulletDamage(show, "");
	}
	
	/**
	 * Show the bullet damage score
	 * @param show
	 * @param title The new title
	 */
	public void showBulletDamage(boolean show, String title) {
		this.updateColumn(5,  show, title);
	}
	
	/**
	 * Show the bullet bonus score
	 * @param show
	 */
	public void showBulletBonus(boolean show) {
		this.showBulletBonus(show, "");
	}
	
	/**
	 * Show the bullet bonus score
	 * @param show
	 * @param title The new title
	 */
	public void showBulletBonus(boolean show, String title) {
		this.updateColumn(6, show, title);
	}
	
	/**
	 * Show the total ram damage score
	 * @param show
	 */
	public void showRamDamage(boolean show) {
		this.showRamDamage(show, "");
	}
	
	/**
	 * Show the total ram damage score
	 * @param show
	 * @param title The new title
	 */
	public void showRamDamage(boolean show, String title) {
		this.updateColumn(7, show, title);
	}
	
	/**
	 * Show the total ram bonus score
	 * @param show
	 */
	public void showRamBonus(boolean show) {
		this.showRamBonus(show, "");
	}
	
	/**
	 * Show the total ram bonus score
	 * @param show
	 * @param title The new title
	 */
	public void showRamBonus(boolean show, String title) {
		this.updateColumn(8, show, title);
	}
	
	/**
	 * Show the total amount of first placings
	 * @param show
	 */
	public void showFirsts(boolean show) {
		this.updateColumn(9, show, "");
	}
	
	/**
	 * Show the total amount of first placings
	 * @param show
	 * @param title The new title
	 */
	public void showFirsts(boolean show, String title) {
		this.updateColumn(9, show, title);
	}
	
	/**
	 * Show the total amount of second placings
	 * @param show
	 */
	public void showSeconds(boolean show) {
		this.showSeconds(show, "");
	}
	
	/**
	 * Show the total amount of second placings
	 * @param show
	 * @param title The new title
	 */
	public void showSeconds(boolean show, String title) {
		this.updateColumn(10, show, title);
	}
	
	/**
	 * Show the total amount of third placings
	 * @param show
	 */
	public void showThirds(boolean show) {
		this.showThirds(show, "");
	}
	
	/**
	 * Show the total amount of third placings
	 * @param show
	 * @param title The new title
	 */
	public void showThirds(boolean show, String title) {
		this.updateColumn(11, show, title);
	}
	/*********** END OPTIONS THAT CAN BE ADDED AS SCORES ***********/
	/*********** MODE SPECIFIC OPTIONS THAT CAN BE ADDED AS SCORES ***********/
	/**
	 * Show the flag score
	 * @param show
	 */
	public void showFlagScore(boolean show) {
		this.showFlagScore(show, "");
	}
	
	/**
	 * Show the flag score
	 * @param show
	 * @param title The new title
	 */
	public void showFlagScore(boolean show, String title) {
		this.updateColumn(12, show, title);
	}
	
	/**
	 * Show the team's name
	 * @param show
	 */
	public void showTeam(boolean show) {
		this.showTeam(show, "");
	}
	
	/**
	 * Show the team's name
	 * @param show
	 * @param title The new title
	 */
	public void showTeam(boolean show, String title) {
		this.updateColumn(13, show, title);
	}
	/*********** END MODE SPECIFIC OPTIONS THAT CAN BE ADDED AS SCORES ***********/
}