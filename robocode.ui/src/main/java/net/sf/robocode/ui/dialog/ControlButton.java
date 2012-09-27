package net.sf.robocode.ui.dialog;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ControlButton extends JButton implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private final ControlDialog controlDialog;
	
	public ControlButton(ControlDialog controlDialog) {
		this.controlDialog = controlDialog;
		
		initialize();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		attach();
		if (!controlDialog.isVisible() ||
				controlDialog.getState() != Frame.NORMAL) {
			WindowUtil.packPlaceShow(controlDialog);
		} else {
			controlDialog.setVisible(true);
		}
	}
	
	private void initialize() {
		addActionListener(this);
		setPreferredSize(new Dimension(110, 25));
		setMinimumSize(new Dimension(110, 25));
		setMaximumSize(new Dimension(110, 25));
		setHorizontalAlignment(SwingConstants.CENTER);
		setMargin(new Insets(0, 0, 0, 0));
		setText("Control");
		setToolTipText("Control");
	}
	
	public void attach() {
		controlDialog.attach();
	}
}
