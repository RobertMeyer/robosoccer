package robocode.equipment;

import java.util.Scanner;

import net.sf.robocode.io.Logger;
import robocode.RobotAttribute;

public class EquipmentParser {

	/** What separates the name and slot at the start of a part definition. */
	private static final String DEFINITION_SEPARATOR = ":";

	/** What separates the attribute and value within a part definition. */
	private static final String ATTRIBUTE_SEPARATOR = "=";

	/**
	 * @param scanner
	 *            the scanner to read the equipment part definitions from
	 * @return a new EquipmentSet object containing the defined parts up to the
	 *         last part definition that was successfully parsed.
	 */
	public static EquipmentSet parse(Scanner scanner) {
		EquipmentSet set = new EquipmentSet();

		if (scanner == null) {
			return set;
		}

		String name = null;
		EquipmentPartBuilder builder = null;

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine().trim();
			if (line.isEmpty()) {
				continue;
			}

			if (isDefinitionLine(line)) {
				// A new part definition, so throw the part we were parsing
				// into the parts map (except if this is the first part).
				if (builder != null) {
					set.putPart(name, builder.build());
				}
				name = parseDefinitionName(line);
				builder = parseDefinitionBuilder(line);
				if (builder == null) {
					logParsingError(line, "Invalid equipment slot");
					break;
				}

			} else if (isAttributeLine(line)) {
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
		scanner.close();

		return set;
	}

	/**
	 * @param string
	 *            the string to check if it is a definition line
	 * @return true if it is a definition line, false otherwise.
	 */
	private static boolean isDefinitionLine(String line) {
		return line.contains(DEFINITION_SEPARATOR);
	}

	/**
	 * @param string
	 *            the string to check if it is an attribute line
	 * @return true if it is an attribute line, false otherwise.
	 */
	private static boolean isAttributeLine(String line) {
		return line.contains(ATTRIBUTE_SEPARATOR);
	}

	/**
	 * @param line
	 *            the definition line to parse
	 * @return a new equipment part builder corresponding to the given line, or
	 *         null if a parsing error occurred.
	 */
	private static EquipmentPartBuilder parseDefinitionBuilder(String line) {
		String[] tokens = parseDefinitionTokens(line);
		if (tokens == null) {
			return null;
		}
		try {
			EquipmentSlot slot = EquipmentSlot.valueOf(tokens[1].trim());
			return new EquipmentPartBuilder(slot);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	/**
	 * @param line
	 *            the definition line to parse
	 * @return the name according to the given line, or null if a parsing error
	 *         occurred.
	 */
	private static String parseDefinitionName(String line) {
		String[] tokens = parseDefinitionTokens(line);
		if (tokens == null) {
			return null;
		}
		return tokens[0].trim();
	}

	/**
	 * @param line
	 *            the definition line to return the tokens of
	 * @return the tokens of the line, or null if there was an invalid number of
	 *         tokens
	 */
	private static String[] parseDefinitionTokens(String line) {
		String[] tokens = line.split(DEFINITION_SEPARATOR);
		if (tokens.length != 2) {
			return null;
		}
		return tokens;
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
	private static String parseAttribute(EquipmentPartBuilder builder, String string) {
		if (builder == null) {
			return "No builder defined";
		}

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

	/** Logs the given line and parsing error to the Robocode logger. */
	private static void logParsingError(String line, String error) {
		Logger.logError("Equipment parsing error: " + error + " => " + line);
	}

}
