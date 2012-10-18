package net.sf.robocode.ui.trackeditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;

import net.sf.robocode.ui.gfx.ImageUtil;

/**
 * Toolbar for the track editor
 * 
 * @author Reinard s4200802
 * @author Taso s4231811
 * 
 */
@SuppressWarnings("serial")
public class EditBar extends JToolBar {

	JButton trackButton;
	JButton waypointButton;
	JButton terrainButton;

	private int lastButtonPressed = 0;

	private class EditBarEventHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			final Object source = e.getSource();

			if (source == trackButton) {
				lastButtonPressed = 1;
			} else if (source == waypointButton) {
				lastButtonPressed = 2;
			} else if (source == terrainButton) {
				lastButtonPressed = 3;
			}
		}
	}

	public EditBar() {

		final EditBarEventHandler editBarEventHandler = new EditBarEventHandler();

		this.setOrientation(JToolBar.VERTICAL);

		ImageIcon road = new ImageIcon(
				ImageUtil.getImage("/net/sf/robocode/ui/icons/road.png"));
		ImageIcon waypoint = new ImageIcon(
				ImageUtil.getImage("/net/sf/robocode/ui/icons/waypoint.png"));
		ImageIcon terrain = new ImageIcon(
				ImageUtil.getImage("/net/sf/robocode/ui/icons/terrain.png"));

		trackButton = new JButton(road);
		trackButton.addActionListener(editBarEventHandler);

		waypointButton = new JButton(waypoint);
		waypointButton.addActionListener(editBarEventHandler);

		terrainButton = new JButton(terrain);
		terrainButton.addActionListener(editBarEventHandler);

		trackButton.setBorder(new EmptyBorder(3, 0, 3, 0));
		waypointButton.setBorder(new EmptyBorder(3, 0, 3, 0));
		terrainButton.setBorder(new EmptyBorder(3, 0, 3, 0));

		this.add(trackButton);
		this.add(waypointButton);
		this.add(terrainButton);

		this.setFloatable(false);
	}

	public int getLastButtonPressed() {
		return lastButtonPressed;
	}
}
