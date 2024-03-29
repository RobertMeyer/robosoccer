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

import net.sf.robocode.battle.IBattleManager;
import net.sf.robocode.gui.IWindowManagerBase;
import robocode.control.events.IBattleListener;
import robocode.control.snapshot.ITurnSnapshot;

/**
 * @author Pavel Savara (original)
 */
public interface IWindowManager extends IWindowManagerBase {

    void init();

    void setEnableGUI(boolean enable);

    boolean isGUIEnabled();

    boolean isSlave();

    boolean isShowResultsEnabled();

    void setEnableShowResults(boolean enable);

    void setSlave(boolean slave);

    void showRobocodeFrame(boolean visible, boolean iconified);

    void showSplashScreen();

    void cleanup();

    JFrame getRobocodeFrame();

    void setBusyPointer(boolean enabled);

    void setStatus(String s);

    void messageWarning(String s);

    ITurnSnapshot getLastSnapshot();

    void addBattleListener(IBattleListener listener);

    void removeBattleListener(IBattleListener listener);

    void runIntroBattle();

	IBattleManager getBattleManager();
}
