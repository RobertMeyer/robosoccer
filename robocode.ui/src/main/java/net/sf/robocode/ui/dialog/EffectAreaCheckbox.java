package net.sf.robocode.ui.dialog;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.StringWriter;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.sf.robocode.battle.BattleProperties;
import net.sf.robocode.io.Logger;
import net.sf.robocode.serialization.IXmlSerializable;
import net.sf.robocode.serialization.SerializableOptions;
import net.sf.robocode.serialization.XmlWriter;
import net.sf.robocode.ui.IWindowManager;
import robocode.control.events.*;
import robocode.control.snapshot.ITurnSnapshot;

public class EffectAreaCheckbox extends JCheckBox{

	private static final long serialVersionUID = 1L;
	public BattleProperties bp;
	
	public EffectAreaCheckbox(BattleProperties bp) {
        this.setText("Effect areas");
        this.bp = bp;
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
              EffectAreaCheckbox box = (EffectAreaCheckbox) actionEvent.getSource();
              box.bp.setEffectArea(box.getModel().isSelected());
            }
          };
        this.addActionListener(actionListener);   
    }
	
	
	
}
