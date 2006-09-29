/*******************************************************************************
 * Copyright (c) 2001-2006 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.robocode.net/license/CPLv1.0.html
 * 
 * Contributors:
 *     Luis Crespo
 *     - Initial API and implementation
 *     Flemming N. Larsen
 *     - Integration and minor corrections
 *******************************************************************************/
package robocode.dialog;


import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

import robocode.battle.*;
import robocode.manager.RobocodeManager;


/**
 * Dialog to display the running ranking of a battle
 * 
 * @author Luis Crespo
 */
public class RankingDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private JPanel rankingContentPane;
	private JScrollPane resultsScrollPane;
	private JTable resultsTable;
	private RobocodeManager manager;
	private BattleRankingTableModel rankingTableModel;
	private Dimension tableSize;

	/**
	 * RankingDialog constructor
	 */
	public RankingDialog(Frame owner, RobocodeManager manager) {
		super(owner);
		this.manager = manager;
		initialize();
	}

	/**
	 * Initializes the class
	 */
	private void initialize() {
		setTitle("Ranking");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setContentPane(getRankingContentPane());
		startRefreshThread();
	}
	
	/**
	 * Return the resultsTable.
	 * 
	 * @return JTable
	 */
	private JTable getResultsTable() {
		if (resultsTable == null) {
			resultsTable = new JTable();
			// resultsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			resultsTable.setColumnSelectionAllowed(true);
			resultsTable.setRowSelectionAllowed(true);
			resultsTable.getTableHeader().setReorderingAllowed(false);
			setResultsData();
		}
		return resultsTable;
	}

	private void startRefreshThread() {
		// Hehe, anonymous-inner-class obfuscation...
		new Thread() {
			public void run() {
				pause(1000); // Make sure we start when the window is already visible
				while (isVisible()) {
					repaint();
					pause(1000);
				}
			}

			private void pause(int millis) {
				try {
					sleep(millis);
				} catch (InterruptedException e) {}
			}
		}.start();
	}

	/**
	 * Return the JDialogContentPane property value.
	 * 
	 * @return JPanel
	 */
	private JPanel getRankingContentPane() {
		if (rankingContentPane == null) {
			rankingContentPane = new JPanel();
			rankingContentPane.setLayout(new BorderLayout());
			rankingContentPane.add(getRankingScrollPane(), "Center");
		}
		return rankingContentPane;
	}

	/**
	 * Return the rankingScrollPane
	 * 
	 * @return JScrollPane
	 */
	private JScrollPane getRankingScrollPane() {
		if (resultsScrollPane == null) {
			resultsScrollPane = new JScrollPane();
			resultsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			resultsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			resultsScrollPane.setViewportView(getResultsTable());
			resultsScrollPane.setColumnHeaderView(resultsTable.getTableHeader());
			resultsScrollPane.getViewport().setScrollMode(JViewport.BLIT_SCROLL_MODE);

			tableSize = new Dimension(getResultsTable().getColumnModel().getTotalColumnWidth(),
					getResultsTable().getModel().getRowCount()
					* (getResultsTable().getRowHeight() + getResultsTable().getRowMargin()));

			resultsTable.setPreferredScrollableViewportSize(tableSize);
			resultsTable.setPreferredSize(tableSize);
			resultsTable.setMinimumSize(tableSize);
		}
		return resultsScrollPane;
	}

	private void setResultsData() {
		getResultsTable().setModel(getBattleRankingTableModel());
		int maxScoreColWidth = 0;

		// TODO: the "name" column is getting too much width compared with the two others
		// ...Columns should get a fixed width instead
		for (int x = 0; x < getBattleRankingTableModel().getColumnCount(); x++) {
			if (x > 0) {
				getResultsTable().getColumnModel().getColumn(x).setCellRenderer(new ResultsTableCellRenderer(false));
			}
			TableColumn column = getResultsTable().getColumnModel().getColumn(x);
			Component comp = null;

			column.setHeaderRenderer(new ResultsTableCellRenderer(true));
			comp = column.getHeaderRenderer().getTableCellRendererComponent(null, column.getHeaderValue(), false, false,
					0, 0);
			int width = comp.getPreferredSize().width;

			for (int y = 0; y < getBattleRankingTableModel().getRowCount(); y++) {
				comp = getResultsTable().getDefaultRenderer(getBattleRankingTableModel().getColumnClass(x)).getTableCellRendererComponent(
						getResultsTable(), getBattleRankingTableModel().getValueAt(y, x), false, false, 0, x);
				if (comp.getPreferredSize().width > width) {
					width = comp.getPreferredSize().width;
				}
			}
			getResultsTable().getColumnModel().getColumn(x).setPreferredWidth(width);
			getResultsTable().getColumnModel().getColumn(x).setMinWidth(width);
			getResultsTable().getColumnModel().getColumn(x).setWidth(width);
			if (x >= 2 && width > maxScoreColWidth) {
				maxScoreColWidth = width;
			}
		}
	}

	private BattleRankingTableModel getBattleRankingTableModel() {
		if (rankingTableModel == null) {
			rankingTableModel = new BattleRankingTableModel(manager);
		}
		return rankingTableModel;
	}
}