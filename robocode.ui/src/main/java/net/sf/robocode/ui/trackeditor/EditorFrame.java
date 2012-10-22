package net.sf.robocode.ui.trackeditor;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.sf.robocode.ui.dialog.WindowUtil;

/**
 * Main Frame for the track editor
 * 
 * @author Reinard s4200802
 * @author Taso s4231811
 * 
 */
@SuppressWarnings("serial")
public class EditorFrame extends JFrame {

	private JMenuItem fileNew;
	private JMenuItem fileOpen;
	private JMenuItem fileSave;
	private JMenuItem fileQuit;
	private JMenuItem trackClear;
	private JMenuItem helpAbout;

	private TrackEditor trackEditor;
	public static EditBar editBar;

	private int trackXSize = 10*64, trackYSize = 10*64;

	JDialog newTrack;

	Container contentPane;

	private class EventHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			final Object source = e.getSource();

			if (source == fileNew) {
				NewTrack();
			} else if (source == fileOpen) {
				final JFileChooser fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(trackEditor);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					try {
						BufferedImage bi = ImageIO.read(fc.getSelectedFile());
						if (trackEditor != null) {
							contentPane.remove(trackEditor);
							contentPane.remove(editBar);
						}

						contentPane.repaint();

						trackEditor = new TrackEditor(bi.getWidth(), bi.getHeight());
						trackEditor.image = bi;
						trackEditor.graphics2D = (Graphics2D) trackEditor.image.getGraphics();
						trackEditor.graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
								RenderingHints.VALUE_ANTIALIAS_ON);

						contentPane.add(trackEditor);
						trackEditor.setAlignmentY(TOP_ALIGNMENT);
						contentPane.add(editBar);
						editBar.setAlignmentY(TOP_ALIGNMENT);
						pack();
						contentPane.repaint();
						trackEditor.revalidate();

					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

			} else if (source == fileSave) {
				final JFileChooser fc = new JFileChooser();
				int returnVal = fc.showSaveDialog(trackEditor);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					try {
						String name = fc.getSelectedFile().getAbsolutePath();
						BufferedImage bi = (BufferedImage) trackEditor.image;
						File file = new File(name + ".BMP");
						ImageIO.write(bi, "BMP", file);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

			} else if (source == fileQuit) {
				dispose();
			} else if (source == trackClear) {
				if (trackEditor != null) {
					trackEditor.clear();
				}
			} else if (source == helpAbout) {
				AboutBoxFrame();
			}
		}
	}

	public EditorFrame() {

		final EventHandler eventHandler = new EventHandler();

		JMenuBar menubar = new JMenuBar();

		// Create the menus
		JMenu file = new JMenu("File");
		JMenu track = new JMenu("Track");
		JMenu help = new JMenu("Help");

		// Create the menu items, add eventHandlers
		fileNew = new JMenuItem("New");
		fileNew.addActionListener(eventHandler);

		fileOpen = new JMenuItem("Open");
		fileOpen.addActionListener(eventHandler);

		fileSave = new JMenuItem("Save");
		fileSave.addActionListener(eventHandler);

		fileQuit = new JMenuItem("Quit");
		fileQuit.addActionListener(eventHandler);

		trackClear = new JMenuItem("Clear map");
		trackClear.addActionListener(eventHandler);

		helpAbout = new JMenuItem("About");
		helpAbout.addActionListener(eventHandler);

		// Add all menu items to the menus
		file.add(fileNew);
		file.add(fileOpen);
		file.add(fileSave);
		file.add(fileQuit);
		track.add(trackClear);
		help.add(helpAbout);

		// Add the menus to the menubar
		menubar.add(file);
		menubar.add(track);
		menubar.add(help);

		// Add the menubar to the window
		this.setJMenuBar(menubar);

		// Create the Edit Bar
		editBar = new EditBar();

		// Setup the contentPane for the trackEditor to go inside
		contentPane = getContentPane();
		setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		contentPane.repaint();
		pack();

		setResizable(false);

	}

	private void NewTrack() {
		// Create JFrame
		newTrack = new JDialog(this, "New Track");
		newTrack.setLayout(new BoxLayout(newTrack.getContentPane(), BoxLayout.Y_AXIS));

		newTrack.setResizable(false);
		newTrack.setPreferredSize(new Dimension(300, 200));
		newTrack.setMinimumSize(new Dimension(300, 200));
		newTrack.setMaximumSize(new Dimension(300, 200));
		newTrack.pack();
		newTrack.repaint();

		JLabel trackXSizeFieldLabel = new JLabel("Please enter horizontal track size: ", JLabel.CENTER);
		trackXSizeFieldLabel.setHorizontalAlignment(SwingConstants.CENTER);

		JSlider trackXSizeField = new JSlider(5, 25, 10);
		trackXSizeField.setMajorTickSpacing(5);
		trackXSizeField.setMinorTickSpacing(1);
		trackXSizeField.setPaintTicks(true);
		trackXSizeField.setPaintLabels(true);
		trackXSizeField.setSnapToTicks(true);

		// Change listener on the track X size field
		trackXSizeField.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
				if (!source.getValueIsAdjusting()) {
					trackXSize = (int)source.getValue() * 64;
				}    
			}
		});

		JLabel trackYSizeFieldLabel = new JLabel("Please enter vertical track size: ", JLabel.CENTER);
		trackYSizeFieldLabel.setHorizontalAlignment(SwingConstants.CENTER);

		JSlider trackYSizeField = new JSlider(5, 25, 10);
		trackYSizeField.setMajorTickSpacing(5);
		trackYSizeField.setMinorTickSpacing(1);
		trackYSizeField.setPaintTicks(true);
		trackYSizeField.setPaintLabels(true);
		trackYSizeField.setSnapToTicks(true);

		// Change listener on the track Y size field
		trackYSizeField.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
				if (!source.getValueIsAdjusting()) {
					trackYSize = (int)source.getValue() * 64;
				}    
			}
		});

		JButton createNewTrack = new JButton("Create track");
		JButton cancelWindow = new JButton("Cancel");

		newTrack.add(trackXSizeFieldLabel);
		newTrack.add(trackXSizeField);
		newTrack.add(trackYSizeFieldLabel);
		newTrack.add(trackYSizeField);
		newTrack.add(createNewTrack);
		newTrack.add(cancelWindow);

		cancelWindow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newTrack.dispose();
			}
		});

		createNewTrack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (trackEditor != null) {
					contentPane.remove(trackEditor);
					contentPane.remove(editBar);
				}

				contentPane.repaint();

				trackEditor = null;
				System.gc();
				trackEditor = new TrackEditor(trackXSize, trackYSize);

				contentPane.add(trackEditor);
				trackEditor.setAlignmentY(TOP_ALIGNMENT);
				contentPane.add(editBar);
				editBar.setAlignmentY(TOP_ALIGNMENT);
				pack();
				contentPane.repaint();
				trackEditor.revalidate();
				newTrack.dispose();
			}
		});

		if (!newTrack.isVisible()) {
			WindowUtil.packCenterShow(newTrack);
		} else {
			newTrack.setVisible(true);
		}

	}

	private void AboutBoxFrame() {
		JOptionPane.showMessageDialog(this, "Track Editor made by s4200802 for Team-gogorobotracer");
	}

}
