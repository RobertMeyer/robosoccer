package net.sf.robocode.ui.dialog;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import net.sf.robocode.ui.IWindowManager;

public class ControlDialog extends JFrame{
	private static final long serialVersionUID = 1L;
	private final IWindowManager windowManager;
	private boolean isListening;
	private ConsoleScrollPane consoleScrollPane;
	private JButton button;
	private JPanel controlDialogContentPane;
	private JPanel buttonPanel;
	
	public ControlDialog(IWindowManager windowManager) {
		this.windowManager = windowManager;
		initialize();
	}
	
	private void initialize() {
		this.setTitle("controlDialog comming through?");
		this.add(getControlDialogContentPane());
		pack();
	}
	
	@Override public void pack() {
		getConsoleScrollPane().setPreferredSize(new Dimension(426, 200));
		super.pack();
	}
	
	private JPanel getControlDialogContentPane() {
		if (controlDialogContentPane == null) {
			controlDialogContentPane = new JPanel();
			controlDialogContentPane.setLayout(new BorderLayout());
			controlDialogContentPane.add(getButtonPanel(), BorderLayout.SOUTH);
		}
		return controlDialogContentPane;
	}
	
	private JPanel getButtonPanel() {
		if (buttonPanel == null) {
			buttonPanel = new JPanel();
			buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			buttonPanel.add(getButton());
		}
		return buttonPanel;
	}
	
	private JButton getButton() {
		if (button == null) {
			button = getNewButton("BUTTON");
		}
		return button;
	}
	
	private JButton getNewButton(String text) {
		JButton button = new JButton(text);
		button.addActionListener(eventHandler);
		return button;
	}
	
	private ConsoleScrollPane getConsoleScrollPane() {
		if (consoleScrollPane == null) {
			consoleScrollPane = new ConsoleScrollPane();
		}
		return consoleScrollPane;
	}
	
	public void attach() {
		if (!isListening) {
			isListening = true;
		}
	}
	
	private final transient ActionListener eventHandler = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			Object src = e.getSource();
			if (src == ControlDialog.this.getButton()) {
				dispose();
			}
		}
	};
	
}
