/*******************************************************************************
 * Copyright (c) 2001 Mathew Nelson
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.robocode.net/license/CPLv1.0.html
 * 
 * Contributors:
 *     Mathew Nelson - initial API and implementation
 *******************************************************************************/
package robocode.dialog;

import java.awt.*;
import javax.swing.*;
import robocode.util.*;
import robocode.manager.*;

/**
 * This type was generated by a SmartGuide.
 */ 
public class AboutBox extends JDialog {


	private JPanel aboutBoxContentPane = null;
	private JLabel appNameLabel = null;
	private JPanel buttonPanel = null;
	private JLabel copyrightLabel = null;
	EventHandler eventHandler = new EventHandler();
	private JLabel graphicsByLabel = null;
	private JLabel iconLabel = null;
	private JPanel iconPanel = null;
	private JPanel mainPanel = null;
	private JButton okButton = null;
	private JLabel programmedByLabel = null;
	private JPanel robotBattlePanel = null;
	private JTextArea robotBattleTextPane = null;
	private JLabel versionLabel = null;
	private JLabel websiteLabel = null;
	private RobocodeManager manager = null;

class EventHandler implements java.awt.event.ActionListener {
		public void actionPerformed(java.awt.event.ActionEvent e) {
			if (e.getSource() == AboutBox.this.getOkButton()) 
				okButtonActionPerformed();
		};
	}

/**
 * AboutBox constructor comment.
 */
public AboutBox(RobocodeManager manager) {
	super();
	this.manager = manager;
	initialize();
}

/**
 * Return the iconLabel
 * @return javax.swing.JLabel
 */
private javax.swing.JLabel getIconLabel() {
	if (iconLabel == null) {
		try {
			iconLabel = new javax.swing.JLabel();
			iconLabel.setName("iconLabel");
			iconLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/largeicon.jpg")));
			iconLabel.setText("");
		} catch (java.lang.Throwable e) {
			log(e);
		}
	}
	return iconLabel;
}






/**
 * Return the okButton
 * @return javax.swing.JButton
 */
private javax.swing.JButton getOkButton() {
	if (okButton == null) {
		try {
			okButton = new javax.swing.JButton();
			okButton.setName("okButton");
			okButton.setText("OK");
			okButton.addActionListener(eventHandler);
		} catch (java.lang.Throwable e) {
			log(e);
		}
	}
	return okButton;
}





/**
 * Initialize the class.
 */
private void initialize() {
	try {
		setName("aboutBox");
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		//setSize(385, 215);
		setTitle("About Robocode");
		setContentPane(getAboutBoxContentPane());
	} catch (java.lang.Throwable e) {
		log(e);
	}
}

/**
 * Return the AboutBoxContentPane property value.
 * @return javax.swing.JPanel
 */
private javax.swing.JPanel getAboutBoxContentPane() {
	if (aboutBoxContentPane == null) {
		try {
			aboutBoxContentPane = new javax.swing.JPanel();
			aboutBoxContentPane.setName("aboutBoxContentPane");
			aboutBoxContentPane.setLayout(new BorderLayout());
			aboutBoxContentPane.add(getButtonPanel(), BorderLayout.SOUTH);
			aboutBoxContentPane.add(getMainPanel(), BorderLayout.CENTER);
			aboutBoxContentPane.add(getIconPanel(), BorderLayout.WEST);
		} catch (java.lang.Throwable e) {
			log(e);
		}
	}
	return aboutBoxContentPane;
}

/**
 * Return the appNameLabel
 * @return javax.swing.JLabel
 */
private javax.swing.JLabel getAppNameLabel() {
	if (appNameLabel == null) {
		try {
			appNameLabel = new javax.swing.JLabel();
			appNameLabel.setName("appNameLabel");
			appNameLabel.setText("Robocode");
//			appNameLabel.setBorder(new javax.swing.border.EtchedBorder());
//			appNameLabel.setHorizontalTextPosition(JLabel.LEFT);
//			appNameLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
//			appNameLabel.setHorizontalAlignment(JLabel.LEFT);
		} catch (java.lang.Throwable e) {
			log(e);
		}
	}
	return appNameLabel;
}

/**
 * Return the buttonPanel
 * @return javax.swing.JPanel
 */
private javax.swing.JPanel getButtonPanel() {
	if (buttonPanel == null) {
		try {
			buttonPanel = new javax.swing.JPanel();
			buttonPanel.setName("buttonPanel");
			buttonPanel.setLayout(new java.awt.FlowLayout());
			buttonPanel.add(getOkButton(), getOkButton().getName());
		} catch (java.lang.Throwable e) {
			log(e);
		}
	}
	return buttonPanel;
}

/**
 * Return the copyrightLabel
 * @return javax.swing.JLabel
 */
private javax.swing.JLabel getCopyrightLabel() {
	if (copyrightLabel == null) {
		try {
			copyrightLabel = new javax.swing.JLabel();
			copyrightLabel.setName("copyrightLabel");
			copyrightLabel.setText("(c) Copyright 2001 Mathew Nelson ");
		} catch (java.lang.Throwable e) {
			log(e);
		}
	}
	return copyrightLabel;
}

/**
 * Return the graphicsByLabel
 * @return javax.swing.JLabel
 */
private javax.swing.JLabel getGraphicsByLabel() {
	if (graphicsByLabel == null) {
		try {
			graphicsByLabel = new javax.swing.JLabel();
			graphicsByLabel.setName("graphicsByLabel");
			graphicsByLabel.setText("Graphics by Garrett S. Hourihan");
		} catch (java.lang.Throwable e) {
			log(e);
		}
	}
	return graphicsByLabel;
}

/**
 * Return the iconPanel
 * @return javax.swing.JPanel
 */
private javax.swing.JPanel getIconPanel() {
	if (iconPanel == null) {
		try {
			iconPanel = new javax.swing.JPanel();
			iconPanel.setName("iconPanel");
			iconPanel.setLayout(new java.awt.FlowLayout());
			iconPanel.add(getIconLabel(), getIconLabel().getName());
		} catch (java.lang.Throwable e) {
			log(e);
		}
	}
	return iconPanel;
}

/**
 * Return the mainPanel
 * @return javax.swing.JPanel
 */
private javax.swing.JPanel getMainPanel() {
	if (mainPanel == null) {
		try {
			mainPanel = new javax.swing.JPanel();
			mainPanel.setName("mainPanel");
	
			mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));//GridLayout(6,1));
			
			JPanel topRow = new JPanel();
			topRow.setLayout(new GridLayout(1,2)); //topRow,BoxLayout.X_AXIS));
			topRow.add(getAppNameLabel());
			topRow.add(getVersionLabel());
			mainPanel.add(topRow);
			
			JPanel secondRow = new JPanel();
			secondRow.setLayout(new GridLayout(1,2)); //BoxLayout(secondRow,BoxLayout.X_AXIS));
			secondRow.add(getCopyrightLabel());
//			secondRow.add(new JLabel("   "));
			secondRow.add(getWebsiteLabel());
			mainPanel.add(secondRow);
			
			JPanel p = new JPanel();
			p.add(getProgrammedByLabel());
			mainPanel.add(p);
			
			p = new JPanel();
			p.add(getGraphicsByLabel());
			mainPanel.add(p);
			
			mainPanel.add(Box.createVerticalStrut(5));
			p = new JPanel();
//			p.setBackground(Color.green);
			p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
			JPanel q = new JPanel();
			q.add(new JLabel("Inspired by Brad Schick's excellent game"));
			q.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
//			q.setBackground(Color.blue);
			q.setMaximumSize(q.getPreferredSize());
			p.add(q);
			q = new JPanel();
			q.add(new JLabel("RobotBattle, at http://www.robotbattle.com"));
			q.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
//			q.setBackground(Color.red);
			q.setMaximumSize(q.getPreferredSize());
			p.add(q);
			p.setMaximumSize(p.getPreferredSize());
//			JPanel r = new JPanel();
//			r.setLayout(new BorderLayout());
//			r.setBackground(Color.yellow);
//			r.add(p,BorderLayout.CENTER);
			mainPanel.add(p);
			
			mainPanel.add(Box.createVerticalStrut(5));
			
			p = new JPanel();
			String javaVersion = "You are using Java version " + System.getProperty("java.version") + " by " + System.getProperty("java.vendor");
			p.add(new JLabel(javaVersion));
			mainPanel.add(p);
			

			//getRobotBattlePanel());
		
			

/*			GridBagLayout gridBagLayout = new GridBagLayout();
			GridBagConstraints constraints = new GridBagConstraints();
			mainPanel.setLayout(gridBagLayout);

			constraints.fill = GridBagConstraints.BOTH;
			constraints.ipadx = 20;
			constraints.ipady = 5;
			constraints.gridwidth = 1;
			
			gridBagLayout.setConstraints(getAppNameLabel(),constraints);
			mainPanel.add(getAppNameLabel());

			constraints.gridwidth = GridBagConstraints.REMAINDER; // last in row
			gridBagLayout.setConstraints(getVersionLabel(),constraints);
			mainPanel.add(getVersionLabel());
			constraints.gridwidth = 1; // reset to default

			gridBagLayout.setConstraints(getCopyrightLabel(),constraints);
			mainPanel.add(getCopyrightLabel());

			constraints.gridwidth = GridBagConstraints.REMAINDER; // last in row
			gridBagLayout.setConstraints(getWebsiteLabel(),constraints);
			mainPanel.add(getWebsiteLabel());
			constraints.gridwidth = 1; // reset to default

			constraints.gridwidth = GridBagConstraints.REMAINDER; // last in row
			gridBagLayout.setConstraints(getProgrammedByLabel(),constraints);
			mainPanel.add(getProgrammedByLabel());

			constraints.gridwidth = GridBagConstraints.REMAINDER; // last in row
			gridBagLayout.setConstraints(getGraphicsByLabel(),constraints);
			mainPanel.add(getGraphicsByLabel());

			constraints.gridwidth = GridBagConstraints.REMAINDER; // last in row
			gridBagLayout.setConstraints(getRobotBattlePanel(),constraints);
			mainPanel.add(getRobotBattlePanel());

	*/		
		} catch (java.lang.Throwable e) {
			log(e);
		}
	}
	return mainPanel;
}

/**
 * Return the programmedBy label
 * @return javax.swing.JLabel
 */
private javax.swing.JLabel getProgrammedByLabel() {
	if (programmedByLabel == null) {
		try {
			programmedByLabel = new javax.swing.JLabel();
			programmedByLabel.setName("programmedByLabel");
			programmedByLabel.setText("Designed and Programmed by Mathew A. Nelson");
		} catch (java.lang.Throwable e) {
			log(e);
		}
	}
	return programmedByLabel;
}

/**
 * Return the robotBattlePanel
 * @return javax.swing.JTextPane
 */
private javax.swing.JPanel getRobotBattlePanel() {
	if (robotBattlePanel == null) {
		try {
			robotBattlePanel = new javax.swing.JPanel();
			robotBattlePanel.setName("robotBattlePanel");
			// Hackish way to get the text itself in a smaller box
			robotBattlePanel.setLayout(new BorderLayout());
			robotBattlePanel.add(new JLabel("  "),BorderLayout.NORTH);
			robotBattlePanel.add(new JLabel("  "),BorderLayout.WEST);
			robotBattlePanel.add(getRobotBattleTextPane(),BorderLayout.CENTER);
			robotBattlePanel.add(new JLabel("        "),BorderLayout.EAST);
		} catch (java.lang.Throwable e) {
			log(e);
		}
	}
	return robotBattlePanel;
}

/**
 * Return the robotBattleTextPane
 * @return javax.swing.JTextPane
 */
private javax.swing.JTextArea getRobotBattleTextPane() {
	if (robotBattleTextPane == null) {
		try {
			robotBattleTextPane = new javax.swing.JTextArea(2,40);
			robotBattleTextPane.setName("robotBattleTextPane");
			robotBattleTextPane.setText("Inspired by Brad Schick\'s excellent game RobotBattle, at http://www.robotbattle.com");
			robotBattleTextPane.setEditable(false);
			robotBattleTextPane.setBackground(Color.lightGray);
			//robotBattleTextPane.setPreferredSize(new Dimension(robotBattleTextPane.getMinimumSize().width,100));
		} catch (java.lang.Throwable e) {
			log(e);
		}
	}
	return robotBattleTextPane;
}

/**
 * Return the versionLabel
 * @return javax.swing.JLabel
 */
private javax.swing.JLabel getVersionLabel() {
	if (versionLabel == null) {
		try {
			versionLabel = new javax.swing.JLabel();
			versionLabel.setName("Version");
			versionLabel.setText("Version: " + (manager.getVersionManager().getVersion()));
		} catch (java.lang.Throwable e) {
			log(e);
		}
	}
	return versionLabel;
}

/**
 * Return the JLabel1 property value.
 * @return javax.swing.JLabel
 */
private javax.swing.JLabel getWebsiteLabel() {
	if (websiteLabel == null) {
		try {
			websiteLabel = new javax.swing.JLabel();
			websiteLabel.setName("websiteLabel");
			websiteLabel.setText("robocode.net");
		} catch (java.lang.Throwable e) {
			log(e);
		}
	}
	return websiteLabel;
}

/**
 * Insert the method's description here.
 * Creation date: (8/22/2001 1:41:21 PM)
 * @param e java.lang.Exception
 */
private void log(Throwable e) {
	Utils.log(e);
}

/**
 * Insert the method's description here.
 * Creation date: (9/1/2001 11:59:28 AM)
 */
private void okButtonActionPerformed() {
	this.dispose();
}
}