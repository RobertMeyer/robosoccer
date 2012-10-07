package net.sf.robocode.mode;

public class GuiOptions {
	
	private boolean showResultsDialog;
	
	private boolean showRobotButtons;
	
	/**
	 * Contains boolean flags which determine the visibility
	 * for some of the GUI elements.
	 * @param showResults End of game results dialog box visibility.
	 * @param showButtons Side panel robot buttons visibility.
	 */
	public GuiOptions(boolean showResults, boolean showButtons) {
		this.showResultsDialog = showResults;
		this.showRobotButtons = showButtons;
	}
	
	/**
	 * Determines if the results dialog box will be shown at
	 * the end of the game.
	 * @return showResultsDialog boolean flag.
	 */
	public boolean getShowResults() {
		return showResultsDialog;
	}
	
	/**
	 * Determines whether the side panel robot buttons will be 
	 * visible or not.
	 * @return showRobotButtons boolean flag.
	 */
	public boolean getShowRobotButtons() {
		return showRobotButtons;
	}
}
