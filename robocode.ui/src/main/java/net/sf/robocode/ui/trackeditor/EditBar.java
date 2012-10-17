package net.sf.robocode.ui.trackeditor;

import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;

import net.sf.robocode.ui.gfx.ImageUtil;

/**
 * Toolbar for the track editor
 * @author Taso s4231811
 * @author Reinard s4200802
 *
 */
@SuppressWarnings("serial")
public class EditBar extends JToolBar{
	
	public EditBar() {
		
		this.setOrientation(JToolBar.VERTICAL);
		
        ImageIcon road = new ImageIcon(ImageUtil.getImage(
        		"/net/sf/robocode/ui/icons/road.png"));
        ImageIcon waypoint = new ImageIcon(ImageUtil.getImage(
        		"/net/sf/robocode/ui/icons/waypoint.png"));
        ImageIcon terrain = new ImageIcon(ImageUtil.getImage(
        		"/net/sf/robocode/ui/icons/terrain.png"));

        JButton selectb = new JButton(road);
        JButton freehandb = new JButton(waypoint);
        JButton shapeedb = new JButton(terrain);
        
        selectb.setBorder(new EmptyBorder(3, 0, 3, 0));
        freehandb.setBorder(new EmptyBorder(3, 0, 3, 0));
        shapeedb.setBorder(new EmptyBorder(3, 0, 3, 0));

        this.add(selectb);
        this.add(freehandb);
        this.add(shapeedb);
        
        this.setFloatable(false);
	}
}
