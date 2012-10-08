package net.sf.robocode.battle;

import java.text.NumberFormat;

import robocode.BattleResults;

@SuppressWarnings("serial")
public class SoccerResultsTableModel extends BattleResultsTableModel {
	
	BattleResults[] results;
	
	public SoccerResultsTableModel(BattleResults[] results, int rounds) {
		super(results, rounds);
		this.results = results;
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public int getRowCount() {
		return results.length;
	}
	
	@Override
    public String getColumnName(int col) {
        switch (col) {
            case 0:
                return "Rank";

            case 1:
                return "Team";

            case 2:
                return "    Total Score    ";
            default:
                return "";
        }
    }
	
	@Override
    public Object getValueAt(int row, int col) {

        BattleResults statistics = results[row];

        switch (col) {
            case 0: {
                int place = row + 1;

                while (place < getRowCount() && statistics.getScore() == results[place].getScore()) {
                    place++;
                }
                return getPlacementString(place);
            }

            case 1:
                return statistics.getTeamLeaderName();

            case 2:
                return "" + (int) statistics.getScore();

            default:
                return "";
        }
    }

}


