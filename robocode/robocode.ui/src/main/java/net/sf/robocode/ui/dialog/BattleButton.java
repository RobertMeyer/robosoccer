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
package net.sf.robocode.ui.dialog;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * @author Pavel Savara (original)
 */
public class BattleButton extends JButton implements ActionListener {

    private static final long serialVersionUID = 1L;
    private final BattleDialog battleDialog;

    public BattleButton(BattleDialog battleDialog) {
        this.battleDialog = battleDialog;

        initialize();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        attach();
        if (!battleDialog.isVisible() || battleDialog.getState() != Frame.NORMAL) {
            WindowUtil.packPlaceShow(battleDialog);
        } else {
            battleDialog.setVisible(true);
        }
    }

    /**
     * Initialize the class.
     */
    private void initialize() {
        addActionListener(this);
        setPreferredSize(new Dimension(110, 25));
        setMinimumSize(new Dimension(110, 25));
        setMaximumSize(new Dimension(110, 25));
        setHorizontalAlignment(SwingConstants.CENTER);
        setMargin(new Insets(0, 0, 0, 0));
        setText("Main battle log");
        setToolTipText("Main battle log");
    }

    public void attach() {
        battleDialog.attach();
    }
}
