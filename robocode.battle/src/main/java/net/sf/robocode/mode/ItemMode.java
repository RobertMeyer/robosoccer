package net.sf.robocode.mode;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.item.GunCoolerPack;
import net.sf.robocode.battle.item.HaltPack;
import net.sf.robocode.battle.item.HealthPack;
import net.sf.robocode.battle.item.PoisonPack;
import net.sf.robocode.battle.item.ItemDrop;

/**
 * Mode Class used for Item Mode.
 * 
 * @author Dream Team
 */
public class ItemMode extends ClassicMode {
	// Item Mode GUI
	private setItemPanel modePanel;
	private final String title = "Item Mode";
	private final String description = "A mode with items and power-ups that robots can pickup and use";
	//Default value of counters if not set
	private int deafultValue = 3;
	//List of items for battlemode
	List<ItemDrop> items = new ArrayList<ItemDrop>();
	
	/*-- Individual Item variables --*/
	// Health
	private JLabel healthLabel = new JLabel("Health Packs");
	private JTextField healthText = new JTextField(3);	
	// Poison
	private JLabel poisonLabel = new JLabel("Poison Packs");
	private JTextField poisonText = new JTextField(3);
	// Halt
	private JLabel haltLabel = new JLabel("Halt Packs");
	private JTextField haltText = new JTextField(3);
	// GunCool
	private JLabel coolLabel = new JLabel("Gun Coolers");
	private JTextField coolText = new JTextField(3);
	
	@Override
	public String toString() {
		return title;
	}

	@Override
	public String getDescription() {
		return description;
	}

	/**
	 * Helper method If string s is whole number, return int of s. Else return
	 * defaultValue
	 * 
	 * @param s
	 *            String to extract int
	 * @return int of s if possible, defaultValue otherwise
	 */
	private int isNumber(String s) {
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException nfe) {
			return deafultValue;
		}
	}

	@Override
	public void setItems(Battle battle) {
		//Get data
		int healthCount = isNumber(healthText.getText());
		int poisonCount = isNumber(poisonText.getText());
		int haltCount = isNumber(haltText.getText());
		int coolCount = isNumber(coolText.getText());

		// Add Health Packs
		for (int i = 0; i < healthCount; i++) {
			items.add(new HealthPack(battle, "health" + (i + 1)));
		}
		// Add Poison Packs
		for (int i = 0; i < poisonCount; i++) {
			items.add(new PoisonPack(battle, "poison" + (i + 1)));
		}
		// Add Cooler Packs
		for (int i = 0; i < coolCount; i++) {
			items.add(new GunCoolerPack(battle, "cooler" + (i + 1)));
		}
		// Add Halt Packs
		for (int i = 0; i < haltCount; i++) {
			items.add(new HaltPack(battle, "halt" + (i + 1)));
		}

	}

	/**
	 * Get the items needed for the items
	 * 
	 * @return the items needed
	 */
	public List<? extends ItemDrop> getItems() {
		return items;
	}

	/**
	 * Add GUI elements to interface
	 *
	 */
	@SuppressWarnings("serial")
	private class setItemPanel extends JPanel {
		public setItemPanel() {
			/*-- Set text field defaults--*/
			healthText.setText("" + deafultValue);
			poisonText.setText("" + deafultValue);
			haltText.setText("" + deafultValue);
			coolText.setText("" + deafultValue);
			/*-- Add gui elements--*/
			add(healthLabel, BorderLayout.NORTH);
			add(healthText);
			add(poisonLabel, BorderLayout.NORTH);
			add(poisonText);
			add(haltLabel, BorderLayout.NORTH);
			add(haltText);
			add(coolLabel, BorderLayout.NORTH);
			add(coolText);
		}
	}

	@Override
	public JPanel getRulesPanel() {
		if (modePanel == null) {
			modePanel = new setItemPanel();
		}
		return modePanel;
	}
}
