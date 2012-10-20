package robocode.equipment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import net.sf.robocode.io.Logger;

import robocode.RobotAttribute;
import robocode.equipment.EquipmentPart.Builder;

/**
 * Represents a set of equipment parts, allowing battles to have different sets
 * of equipment to allow the robots in that battle to equip.
 * 
 * @author CSSE2003 Team Forkbomb
 */
public class EquipmentSet {
	private Map<String, EquipmentPart> parts;

	private EquipmentSet(Map<String, EquipmentPart> parts) {
		this.parts = parts;
	}

	/**
	 * @return the definition file to be used by default
	 */
	public static File defaultFile() {
		String path = EquipmentSet.class.getResource("default").getPath();
		System.out.println("Equipment path: " + path);
		return new File(path);
	}

	/**
	 * TODO: document the file format
	 * 
	 * @param file
	 *            the file containing the equipment part definitions
	 * @return a new Equipment object if the file was successfully parsed, null
	 *         otherwise.
	 */
	public static EquipmentSet fromFile(File file) {
		if (file == null) {
			file = defaultFile();
		}
		System.out.println("EquipmentSet got file: " + file.getAbsolutePath());

		FileReader fileReader = null;
		try {
			fileReader = new FileReader(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Scanner fileScanner = new Scanner(fileReader);

		Map<String, EquipmentPart> parts = new HashMap<String, EquipmentPart>();

		String name = null;
		EquipmentPart.Builder builder = null;

		while (fileScanner.hasNextLine()) {
			String line = fileScanner.nextLine().trim();
			if (line.isEmpty()) {
				continue;
			}

			if (line.contains(":")) {
				// A new part definition, so throw the part we were parsing
				// into the parts map (except if this is the first part).
				if (builder != null) {
					parts.put(name, builder.build());
				}
				String[] tokens = line.split(":");
				name = tokens[0].trim();
				try {
					EquipmentSlot slot = EquipmentSlot
							.valueOf(tokens[1].trim().toUpperCase());
					builder = new EquipmentPart.Builder(slot);
				} catch (IllegalArgumentException e) {
					logParsingError(line, "Invalid equipment slot");
					break;
				}

			} else if (line.contains("=")) {
				String error = parseAttribute(builder, line);
				if (error != null) {
					logParsingError(line, error);
					break;
				}

			} else {
				logParsingError(line, "Invalid line");
				break;
			}
		}
		fileScanner.close();

		return new EquipmentSet(parts);
	}

	/**
	 * Parses the given string for an attribute assignment and sets that
	 * attribute on the builder accordingly.
	 * 
	 * @param builder
	 *            the builder to set according to the string
	 * @param string
	 *            the string containing the attribute assignment
	 * @return an error string if an error occurred, null otherwise
	 */
	static String parseAttribute(Builder builder, String string) {
		String[] tokens = string.split("=");
		if (tokens.length != 2) {
			return "Invalid line";
		}

		String attr = tokens[0].trim().toUpperCase();
		String val = tokens[1].trim();

		try {
			if (attr.equals("IMAGE_PATH")) {
				builder.imagePath(val);
			} else if (attr.equals("SOUND_PATH")) {
				builder.soundPath(val);
			} else {
				builder.set(RobotAttribute.valueOf(attr), Double.valueOf(val));
			}
		} catch (NumberFormatException e) {
			return "Invalid attribute value";
		} catch (IllegalArgumentException e) {
			return "Invalid robot attribute";
		}
		return null;
	}

	static void logParsingError(String line, String error) {
		Logger.logError("Equipment parsing error: " + error + " => " + line);
	}

	/**
	 * @param name
	 *            the name of the part
	 * @return the part associated with the given name, or null if none
	 */
	public EquipmentPart getPart(String name) {
		return parts.get(name);
	}
}
