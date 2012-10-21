package robocode.equipment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Represents a set of equipment parts, allowing battles to have different sets
 * of equipment to allow the robots in that battle to equip.
 * 
 * @author CSSE2003 Team Forkbomb
 */
public class EquipmentSet {
	/** The default part definition file to be used if no file is specified. */
	public static final String DEFAULT_RESOURCE = "default";

	/** An association of part names to equipment parts. */
	private Map<String, EquipmentPart> parts;

	/**
	 * @return the definition input stream to be used by default
	 */
	public static InputStream defaultFile() {
		return EquipmentSet.class.getResourceAsStream("default");
	}

	/**
	 * @param resource
	 *            the name of the resource containing the equipment part
	 *            definitions
	 * @return a new EquipmentSet object containing the defined parts up to the
	 *         last part definition that was successfully parsed.
	 */
	public static EquipmentSet fromResource(String resource) {
		InputStream stream = EquipmentSet.class.getResourceAsStream("default");
		if (stream == null) {
			return new EquipmentSet();
		}
		return EquipmentParser.parse(new Scanner(stream));
	}

	/**
	 * @param file
	 *            the file containing the equipment part definitions
	 * @return a new EquipmentSet object containing the defined parts up to the
	 *         last part definition that was successfully parsed.
	 */
	public static EquipmentSet fromFile(File file) {
		if (file == null) {
			return fromResource(DEFAULT_RESOURCE);
		}

		try {
			return EquipmentParser.parse(new Scanner(new FileReader(file)));
		} catch (FileNotFoundException e) {
			return new EquipmentSet();
		}
	}

	/** Returns an equipment set with no parts. */
	public EquipmentSet() {
		parts = new HashMap<String, EquipmentPart>();
	}

	/**
	 * @param name
	 *            the name of the part
	 * @return the part associated with the given name, or null if none
	 */
	public EquipmentPart getPart(String name) {
		return parts.get(name);
	}

	/**
	 * @param name
	 *            the name to be associated with the given part
	 * @param part
	 *            the part to include in this equipment set
	 */
	public void putPart(String name, EquipmentPart part) {
		parts.put(name, part);
	}
}
