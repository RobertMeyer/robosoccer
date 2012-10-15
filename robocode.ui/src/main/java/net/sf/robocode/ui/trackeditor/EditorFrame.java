package net.sf.robocode.ui.trackeditor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * Main Frame for the track editor
 * @author Taso s4231811
 * @author Reinard s4200802
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
	
    private class EventHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            final Object source = e.getSource();
            
            if (source == fileNew) {
                // New code
            } else if (source == fileOpen) {
                // Open code
            } else if (source == fileSave) {
                // Save code
            } else if (source == fileQuit) {
                dispose();
                System.exit(0);
            } else if (source == helpAbout) {
            	AboutBoxFrame();
            }
        }
    }
	
	public EditorFrame() {
		
	    final EventHandler eventHandler = new EventHandler();
	    
        JMenuBar menubar = new JMenuBar();
        
        JMenu file = new JMenu("File");
        JMenu track = new JMenu("Track");
        JMenu help = new JMenu("Help");
        
        TrackEditor trackEditor = new TrackEditor();

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
        
        file.add(fileNew);
        file.add(fileOpen);
        file.add(fileSave);
        file.add(fileQuit);
        track.add(trackClear);
        help.add(helpAbout);
        
        menubar.add(file);
        menubar.add(track);
        menubar.add(help);
        
        this.setJMenuBar(menubar);
		
		BorderLayout layout = new BorderLayout();
		this.setLayout(layout);
		
		DisplayPanel displayPanel = new DisplayPanel();
		EditBar toolBar = new EditBar();
		
		this.add(displayPanel, BorderLayout.CENTER);
		this.add(toolBar, BorderLayout.EAST);
		
	}
	
	public void AboutBoxFrame() {
		// Create JFrame
		JFrame frame = new JFrame("About");
		frame.setSize(200,200);
		
		// Text inside About Box
		JLabel aboutText = new JLabel("Track Editor made by s4200802", JLabel.CENTER);
		
		// Edit JFrame properties
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(aboutText, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
	}
	
}
