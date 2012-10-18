package net.sf.robocode.ui.trackeditor;

import javax.swing.JComponent;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Main bitmap editor for the track editor
 * 
 * @author Reinard s4200802
 * 
 */

@SuppressWarnings("serial")
public class TrackEditor extends JComponent {

	Image image;
	Graphics2D graphics2D;
	int xPos, yPos;

	public TrackEditor() {
		// TrackEditor constructor.
		final MouseHandler mouseHandler = new MouseHandler();
		this.addMouseListener(mouseHandler);
		this.addMouseMotionListener(mouseHandler);
	}

	class MouseHandler implements MouseListener, MouseMotionListener {

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
			xPos = e.getX();
			yPos = e.getY();
			setLayType();
			graphics2D.fillRect((xPos / 50) * 50, (yPos / 50) * 50, 50, 50);
			repaint();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			xPos = e.getX();
			yPos = e.getY();
			if (graphics2D != null)
				setLayType();
			graphics2D.fillRect((xPos / 64) * 64, (yPos / 64) * 64, 64, 64);
			repaint();
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub

		}
	}

	public void setLayType() {
		if (EditorFrame.toolBar.getLastButtonPressed() == 1) {
			graphics2D.setPaint(Color.BLACK);
		} else if (EditorFrame.toolBar.getLastButtonPressed() == 2) {
			graphics2D.setPaint(Color.BLUE);
		} else if (EditorFrame.toolBar.getLastButtonPressed() == 3) {
			graphics2D.setPaint(Color.GREEN);
		}
	}

	public void paintComponent(Graphics g) {
		if (image == null) {
			image = createImage(getSize().width, getSize().height);
			graphics2D = (Graphics2D) image.getGraphics();
			graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			clear();
		}
		g.drawImage(image, 0, 0, null);
	}

	public void clear() {
		graphics2D.setPaint(Color.white);
		graphics2D.fillRect(0, 0, getSize().width, getSize().height);
		graphics2D.setPaint(Color.black);
		repaint();
	}

}