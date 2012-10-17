package net.sf.robocode.ui.dialog;

import java.awt.*;
import java.util.ArrayList;
import java.util.regex.*;

import javax.swing.*;

/**
 * an panel for user to assign the start off position for robot 
 * the format will be (Xposition,Yposition,Heading) or (Xposition,Yposition) 
 * @author miao
 *
 */
public class SetRobotPostionPanel extends WizardPanel {

	private JTextArea Input = new JTextArea(20, 10);
	private JScrollPane JSpane = new JScrollPane();

	public SetRobotPostionPanel() {
		super();
		this.setPreferredSize(new Dimension(120, 100));
		this.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), "Position:(X,Y,Heading)"));
		JSpane.setViewportView(Input);
		this.add(JSpane);

	}

	/**
	 * get the position from the panel and will assign to robot
	 * position will be assign follow the sequence of robot
	 * if there is no position in the panel 
	 * random position will be generated
	 * @return
	 */
	public ArrayList<String> GetPostion() {

		if (Input.getText() == null) {
			return null;
		}

		ArrayList<String> positions = new ArrayList<String>();

		Pattern pattern = Pattern.compile("([^,(]*[(][^)]*[)])");
		//"([^,(]*[(][^)]*[)])?[^,]*,?"
		Matcher matcher = pattern.matcher(Input.getText());

		while (matcher.find()) {
			String pos = matcher.group();

			if (pos.length() > 0) {
				positions.add(pos);
			}
		}

		if (positions.isEmpty()) {
			return null;
		} else {
			return positions;
		}
	}
	
	@Override
	public boolean isReady() {
		// TODO Auto-generated method stub
		return true;
	}

}
