package net.sf.robocode.ui.trackeditor;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
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
            	// Help about code
            }
        }
    }
	
	public EditorFrame() {
		
	    final EventHandler eventHandler = new EventHandler();
	    
        JMenuBar menubar = new JMenuBar();
        
        JMenu file = new JMenu("File");
        JMenu help = new JMenu("Help");

        fileNew = new JMenuItem("New");
        fileNew.addActionListener(eventHandler);
        
        fileOpen = new JMenuItem("Open");
        fileOpen.addActionListener(eventHandler);
        
        fileSave = new JMenuItem("Save");
        fileSave.addActionListener(eventHandler);
        
        fileQuit = new JMenuItem("Quit");
        fileQuit.addActionListener(eventHandler);
        
        helpAbout = new JMenuItem("About");
        helpAbout.addActionListener(eventHandler);
        
        file.add(fileNew);
        file.add(fileOpen);
        file.add(fileSave);
        file.add(fileQuit);
        help.add(helpAbout);
        
        menubar.add(file);
        menubar.add(help);
        
        this.setJMenuBar(menubar);
		
		BorderLayout layout = new BorderLayout();
		this.setLayout(layout);
		
		DisplayPanel displayPanel = new DisplayPanel();
		EditBar toolBar = new EditBar();
		
		this.add(displayPanel, BorderLayout.CENTER);
		this.add(toolBar, BorderLayout.EAST);
		
	}
	
}
