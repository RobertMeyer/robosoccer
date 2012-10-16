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
 *     - Code cleanup
 *******************************************************************************/
package net.sf.robocode.ui.dialog;

import java.awt.*;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import javax.swing.*;
import javax.swing.event.ChangeEvent;

/**
 * @author Mathew A. Nelson (original)
 * @author Flemming N. Larsen (contributor)
 */
@SuppressWarnings("serial")
public class WizardCardPanel extends JPanel implements Wizard {

    private WizardController wizardController;
    private CardLayout cardLayout = null;
    private int currentIndex = 0;
    private final WizardListener listener;
    private final EventHandler eventHandler = new EventHandler();

    public class EventHandler implements ContainerListener {

        @Override
        public void componentRemoved(ContainerEvent e) {
        }

        @Override
        public void componentAdded(ContainerEvent e) {
            if (e.getChild() instanceof WizardPanel) {
                setWizardControllerOnPanel((WizardPanel) e.getChild());
                getWizardController().stateChanged(new ChangeEvent(e.getChild()));
            }
        }
    }

    /**
     * WizardCardLayout constructor
     *
     * @param listener WizardListener
     */
    public WizardCardPanel(WizardListener listener) {
        this.listener = listener;
        initialize();
    }

    @Override
    public void back() {
        currentIndex--;
        getWizardController().stateChanged(null);
        getCardLayout().previous(this);
    }

    public CardLayout getCardLayout() {
        if (cardLayout == null) {
            cardLayout = new CardLayout();
        }
        return cardLayout;
    }

    @Override
    public Component getCurrentPanel() {
        return getComponent(currentIndex);
    }

    @Override
    public WizardController getWizardController() {
        if (wizardController == null) {
            wizardController = new WizardController(this);
        }
        return wizardController;
    }

    @Override
    public WizardListener getWizardListener() {
        return listener;
    }

    public void initialize() {
        this.setLayout(getCardLayout());
        this.addContainerListener(eventHandler);
    }

    @Override
    public boolean isBackAvailable() {
        return (currentIndex > 0);
    }

    public boolean isCurrentPanelReady() {
        Component c = getCurrentPanel();

        return (!(c instanceof WizardPanel)) || ((WizardPanel) c).isReady();
    }

    @Override
    public boolean isNextAvailable() {
        return ((currentIndex < getComponentCount() - 1) && isCurrentPanelReady());
    }

    @Override
    public boolean isReady() {
        for (Component c : getComponents()) {
            if (!((WizardPanel) c).isReady()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void next() {
        currentIndex++;
        getWizardController().stateChanged(null);
        getCardLayout().next(this);
    }

    @Override
    public void setWizardControllerOnPanel(WizardPanel panel) {
        panel.setWizardController(getWizardController());
    }
}
