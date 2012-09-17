package net.sf.robocode.ui.trackeditor;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class EditorFrame extends JFrame{
	
	public EditorFrame() {
		
        JMenuBar menubar = new JMenuBar();
        
        JMenu file = new JMenu("File");
        JMenu help = new JMenu("Help");

        JMenuItem fileNew = new JMenuItem("New");
        JMenuItem fileOpen = new JMenuItem("Open");
        JMenuItem fileSave = new JMenuItem("Save");
        JMenuItem fileQuit = new JMenuItem("Quit");
        
        JMenuItem helpAbout = new JMenuItem("About");
        
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
