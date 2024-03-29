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
 *******************************************************************************/
package net.sf.robocode.ui;

import javax.swing.*;
import net.sf.robocode.battle.BattleProperties;
import net.sf.robocode.battle.BattleResultsTableModel;
import robocode.control.events.BattleCompletedEvent;

/**
 * @author Pavel Savara (original)
 */
public interface IWindowManagerExt extends IWindowManager {

    void showAboutBox();

    String showBattleOpenDialog(String defExt, String name);

    String saveBattleDialog(String path, String defExt, String name);

    void showReadMe();

    void showVersionsTxt();

    void showHelpApi();

    void showFaq();

    void showOnlineHelp();

    void showJavaDocumentation();

    void showRobocodeHome();

    void showRoboWiki();

    void showYahooGroupRobocode();

    void showRobocodeRepository();

    void showOptionsPreferences();

    void showResultsDialog(BattleCompletedEvent event);

    void showRankingDialog(boolean visible);

    void showRobocodeEditor();
    
    void showTrackEditor();

    void showRobotPackager();

    void showRobotExtractor(JFrame owner);

    void showNewBattleDialog(BattleProperties battleProperties, boolean openBattle);

    boolean closeRobocodeEditor();

    void showCreateTeamDialog();

    void showImportRobotDialog();

    void showSaveResultsDialog(BattleResultsTableModel tableModel);

    int getFPS();
}
