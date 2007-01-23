/*******************************************************************************
 * Copyright (c) 2001, 2007 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/cpl-v10.html
 * 
 * Contributors:
 *     Mathew A. Nelson
 *     - Initial API and implementation
 *     Flemming N. Larsen
 *     - Changed to give notification only if the available version number is
 *       greater than the version retrieved from the robocode.jar file, and only
 *       give warning if the user rejects downloading a new version, if the new
 *       version is not an alpha or beta version
 *     - Changed the checkdate time interval from 10 days to 5 days
 *     - Updated to use methods from WindowUtil, FileUtil, Logger, which replaces
 *       methods that have been (re)moved from the Utils and Constants class
 *     - Added a connect timeout of 5 seconds when checking for a new version
 *     - Code cleanup
 *******************************************************************************/
package robocode.manager;


import java.io.*;
import java.net.*;
import javax.swing.*;
import java.util.*;

import robocode.dialog.WindowUtil;
import robocode.io.FileUtil;
import static robocode.io.Logger.log;


/**
 * @author Mathew A. Nelson (original)
 * @author Flemming N. Larsen (contributor)
 */
public class VersionManager {
	private String version;
	private RobocodeManager manager;
	
	public VersionManager(RobocodeManager manager) {
		this.manager = manager;
	}

	public void checkUpdateCheck() {
		Date lastCheckedDate = manager.getProperties().getVersionChecked();

		Date today = new Date();

		if (lastCheckedDate == null) {
			lastCheckedDate = today;
			manager.getProperties().setVersionChecked(lastCheckedDate);
			manager.saveProperties();
		}
		Calendar checkDate = Calendar.getInstance();

		checkDate.setTime(lastCheckedDate);
		checkDate.add(Calendar.DATE, 5);

		if (checkDate.getTime().before(today) && checkForNewVersion(false)) {
			manager.getProperties().setVersionChecked(today);
			manager.saveProperties();
		}
	}
	
	public boolean checkForNewVersion(boolean notifyNoUpdate) {
		URL u = null;

		try {
			u = new URL("http://robocode.sourceforge.net/version/version.html");
		} catch (MalformedURLException e) {
			log("Unable to check for new version: " + e);
			if (notifyNoUpdate) {
				WindowUtil.messageError("Unable to check for new version: " + e);
			}
			return false;
		}
	
		BufferedReader reader;

		try {
			URLConnection urlConnection = u.openConnection();

			urlConnection.setConnectTimeout(5000);

			if (urlConnection instanceof HttpURLConnection) {
				log("Update checking with http.");
				HttpURLConnection h = (HttpURLConnection) urlConnection;

				if (h.usingProxy()) {
					log("http using proxy.");
				}
			}
			reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		} catch (IOException e) {
			log("Unable to check for new version: " + e);
			if (notifyNoUpdate) {
				WindowUtil.messageError("Unable to check for new version: " + e);
			}
			return false;
		}
	
		String v = null;

		try {
			v = reader.readLine();
		} catch (IOException e) {
			log("Unable to check for new version: " + e);
			if (notifyNoUpdate) {
				WindowUtil.messageError("Unable to check for new version: " + e);
			}
			return false;
		}
	
		String installurl = "http://robocode.sourceforge.net/installer";

		if (v.compareToIgnoreCase(getVersion()) > 0) {
			if (JOptionPane.showConfirmDialog(manager.getWindowManager().getRobocodeFrame(),
					"Version " + v + " of Robocode is now available.  Would you like to download it?",
					"Version " + v + " available", JOptionPane.YES_NO_OPTION)
					== JOptionPane.YES_OPTION) {
				try {
					BrowserManager.openURL(installurl);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(manager.getWindowManager().getRobocodeFrame(), e.getMessage(),
							"Unable to open browser!", JOptionPane.INFORMATION_MESSAGE);
				}
			} else if (!v.matches(".*([Aa][Ll][Pp][Hh]|[Bb][Ee][Tt])[Aa].*")) {
				JOptionPane.showMessageDialog(manager.getWindowManager().getRobocodeFrame(),
						"It is highly recommended that you always download the latest version.  You may get it at "
						+ installurl,
						"Update when you can!",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} else if (notifyNoUpdate) {
			JOptionPane.showMessageDialog(manager.getWindowManager().getRobocodeFrame(),
					"You have version " + version + ".  This is the latest version of Robocode.", "No update available",
					JOptionPane.INFORMATION_MESSAGE);
		}
	
		return true;
	}
	
	public String getVersion() {
		if (version == null) {
			version = getVersionFromJar();
		}
		return version;
	}
	
	private String getVersionFromJar() {
		String versionString = null;

		try {
			URL versionsUrl = getClass().getResource("/resources/versions.txt");

			if (versionsUrl == null) {
				log("no url");
			}
				
			BufferedReader in = new BufferedReader(new InputStreamReader(versionsUrl.openStream()));

			versionString = in.readLine();
			while (versionString != null && !versionString.substring(0, 8).equalsIgnoreCase("Version ")) {
				versionString = in.readLine();
			}
			in.close();
		} catch (FileNotFoundException e) {
			log("No versions.txt file in robocode.jar.");
			versionString = "unknown";
		} catch (IOException e) {
			log("IO Exception reading versions.txt from robocode.jar" + e);
			versionString = "unknown";
		}
	
		String version = "unknown";

		if (versionString != null) {
			try {
				version = versionString.substring(8);
			} catch (Exception e) {
				version = "unknown";
			}
		}
		if (version.equals("unknown")) {
			log("Warning:  Getting version from file.");
			return getVersionFromFile();
		}
		return version;
	}

	private String getVersionFromFile() {
		String versionString = null;

		try {
			BufferedReader in = new BufferedReader(new FileReader(new File(FileUtil.getCwd(), "versions.txt")));

			versionString = in.readLine();
			while (versionString != null && !versionString.substring(0, 8).equalsIgnoreCase("Version ")) {
				versionString = in.readLine();
			}
		} catch (FileNotFoundException e) {
			log("No versions.txt file.");
			versionString = "unknown";
	
		} catch (IOException e) {
			log("IO Exception reading versions.txt" + e);
			versionString = "unknown";
		}
	
		String version = "unknown";

		if (versionString != null) {
			try {
				version = versionString.substring(8);
			} catch (Exception e) {
				version = "unknown";
			}
		}
		return version;
	}
}
