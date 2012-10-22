package net.sf.robocode.ui.trackeditor;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

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

	public TrackEditor(int trackXSize, int trackYSize) {
		// TrackEditor constructor
		
		// Initialise the action listeners
		final MouseHandler mouseHandler = new MouseHandler();
		this.addMouseListener(mouseHandler);
		this.addMouseMotionListener(mouseHandler);
		
		// Set up the size of the track
		setPreferredSize(new Dimension(trackXSize, trackYSize));
		setMinimumSize(new Dimension(5*64, 5*64));
		setMaximumSize(new Dimension(30*64, 30*64));
		
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
			layField();
			repaint();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			xPos = e.getX();
			yPos = e.getY();
			if (graphics2D != null) {
				try {
					layField();
				} catch (ArrayIndexOutOfBoundsException e1) {
					// Do nothing
				}
			}
			repaint();
		}

		@Override
		public void mouseMoved(MouseEvent e) {
		}
	}

	public void layField() {
		if (EditorFrame.editBar.getLastButtonPressed() == 1) {
			// Road
			graphics2D.setPaint(Color.BLACK);
			if (checkColour() == true) {
				graphics2D.fillRect((xPos / 64) * 64, (yPos / 64) * 64, 64, 64);
			}
		} else if (EditorFrame.editBar.getLastButtonPressed() == 2) {
			// Waypoint
			graphics2D.setPaint(Color.BLUE);
			if (checkColour() == true) {
				graphics2D.fillRect((xPos / 64) * 64 + 32, (yPos / 64) * 64 + 32, 1, 1);
			}
		} else if (EditorFrame.editBar.getLastButtonPressed() == 3) {
			// Terrain
			graphics2D.setPaint(Color.GREEN);
			if (checkColour() == true) {
				graphics2D.fillRect((xPos / 64) * 64, (yPos / 64) * 64, 64, 64);
			}
		} else if (EditorFrame.editBar.getLastButtonPressed() == 4) {
			// Wall
			graphics2D.setPaint(Color.WHITE);
			if (checkColour() == true) {
				graphics2D.fillRect((xPos / 64) * 64, (yPos / 64) * 64, 64, 64);
			}
		} else if (EditorFrame.editBar.getLastButtonPressed() == 5) {
			// Spawn
			graphics2D.setPaint(Color.RED);
			if (checkColour() == true) {
				graphics2D.fillRect((xPos / 64) * 64 + 32, (yPos / 64) * 64 + 32, 1, 1);
			}
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
	
	public boolean checkColour() {
		BufferedImage bi = (BufferedImage) image;
		int pixelColour = bi.getRGB(xPos, yPos);
		if (pixelColour == Color.WHITE.getRGB() && graphics2D.getPaint() == Color.BLUE) {
			JOptionPane.showMessageDialog(this, "You can't put a waypoint on a wall. Why not lay some road or terrain first?");
			return false;
		} else if (pixelColour == Color.WHITE.getRGB() && graphics2D.getPaint() == Color.RED) {
			JOptionPane.showMessageDialog(this, "You can't put a spawn point on a wall. Why not lay some road or terrain first?");
			return false;
		}
		return true;
	}

	public void fill(Color color) {
		graphics2D.setPaint(color);
		graphics2D.fillRect(0, 0, getSize().width, getSize().height);
		graphics2D.setPaint(Color.black);
		repaint();
	}
}