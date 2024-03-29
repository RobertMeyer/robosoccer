/*******************************************************************************
 * Copyright (c) 2001-2012 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 *
 * Contributors:
 *     Pavel Savara
 *     - Initial implementation
 *     Flemming N. Larsen
 *     - Extended to support both class path and source path
 *******************************************************************************/
package net.sf.robocode.repository.items;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.sf.robocode.core.Container;
import net.sf.robocode.host.IHostManager;
import net.sf.robocode.io.FileUtil;
import net.sf.robocode.io.Logger;
import static net.sf.robocode.io.Logger.logError;
import net.sf.robocode.io.URLJarCollector;
import net.sf.robocode.repository.IRobotRepositoryItem;
import net.sf.robocode.repository.RobotType;
import net.sf.robocode.repository.root.ClassPathRoot;
import net.sf.robocode.repository.root.IRepositoryRoot;
import net.sf.robocode.security.HiddenAccess;
import net.sf.robocode.version.IVersionManager;
import robocode.control.RobotSpecification;

/**
 * @author Pavel Savara (original)
 * @author Flemming N. Larsen (contributor)
 */
public class RobotItem extends NamedItem implements IRobotRepositoryItem {

    private static final long serialVersionUID = 1L;
	// Allowed maximum length for a robot's full package name
	private final static int MAX_FULL_PACKAGE_NAME_LENGTH = 32;
	// Allowed maximum length for a robot's short class name
	private final static int MAX_SHORT_CLASS_NAME_LENGTH = 32;
	private final static String ROBOT_CLASSNAME = "robot.classname";
	private final static String ROBOT_VERSION = "robot.version";
	protected final static String ROBOT_LANGUAGE = "robot.language";
	private final static String ROBOT_DESCRIPTION = "robot.description";
	private final static String ROBOT_AUTHOR_NAME = "robot.author.name";
	private final static String ROBOT_WEBPAGE = "robot.webpage";
	// private final static String ROBOT_AUTHOR_EMAIL = "robot.author.email";
	// private final static String ROBOT_AUTHOR_WEBSITE = "robot.author.website";
	private final static String ROBOCODE_VERSION = "robocode.version";
	private final static String ROBOT_INCLUDE_SOURCE = "robot.include.source"; 
	// File extensions
	private static final String CLASS_EXTENSION = ".class";
	private static final String PROPERTIES_EXTENSION = ".properties";
	private static final String HTML_EXTENSION = ".html";
	private static final boolean ALWAYS_USE_CACHE_FOR_DATA = System.getProperty("ALWAYSUSECACHEFORDATA", "false").equals(
			"true");
	RobotType robotType;
	private URL classPathURL;
	private Set<URL> sourcePathURLs; // This is a Set in order to avoid duplicates
	private URL classURL;
	private URL propertiesURL;
	private String className;
	protected boolean isPropertiesLoaded;

	public RobotItem(URL itemURL, IRepositoryRoot root) {
		super(itemURL, root);

		isValid = true;

		classPathURL = root.getURL();
		sourcePathURLs = new HashSet<URL>();
	}

	private void populate() {
		populatePropertiesURLFromClassURL();
		populateClassURLFromPropertiesURL();
		loadProperties();
	}

	public void setClassURL(URL classUrl) {
		this.classURL = classUrl;
		populate();
	}

	public void setPropertiesURL(URL propertiesUrl) {
		this.propertiesURL = propertiesUrl;
		populate();
	}

	public void setClassPathURL(URL classPathUrl) {
		this.classPathURL = classPathUrl;
	}

	public void addSourcePathURL(URL sourcePathUrl) {
		sourcePathURLs.add(sourcePathUrl);
	}

	// -------------------------------------
	// URL initialization
	// -------------------------------------
	private void populatePropertiesURLFromClassURL() {
		if (propertiesURL == null && classURL != null) {
			final String path = classURL.toString().replaceFirst("\\" + CLASS_EXTENSION, PROPERTIES_EXTENSION);

			try {
				propertiesURL = new URL(path);
			} catch (MalformedURLException e) {
				Logger.logError(e);
			}
			if (isInvalidURL(propertiesURL)) {
				propertiesURL = null;
			}
		}
	}

	private void populateClassURLFromPropertiesURL() {
		if (classURL == null && propertiesURL != null) {
			final String path = propertiesURL.toString().replaceAll("\\" + PROPERTIES_EXTENSION, CLASS_EXTENSION);

			try {
				classURL = new URL(path);
			} catch (MalformedURLException e) {
				Logger.logError(e);
			}
			if (isInvalidURL(classURL)) {
				classURL = null;
			}
		}
	}

	private void populateClassNameFromClassURL() {
		populate();

		if (className == null && classURL != null) {
			final String path = classURL.toString();

			// .dll file?
			int index = (path.toLowerCase().indexOf(".dll!/"));

			if (index > 0) {
				className = path.substring(index + 6);
				return;
			}

			// Class within .jar or regular file path?

			index = path.lastIndexOf('!');

			if (index > 0) {
				// .jar file
				className = path.substring(index + 2);
			} else {
				// file path
				className = path.substring(root.getURL().toString().length());
			}

			// Remove the file extension from the class name
			index = className.lastIndexOf('.');

			if (index > 0) {
				className = className.substring(0, index);
			}

			// Replace all file separators with dots (from file path the package/namespace)
			className = className.replaceAll("[\\\\\\/]", ".");
		}
	}

	private void populateHtmlURL() {
		populate();

		if (htmlURL == null && classURL != null) {
			try {
				htmlURL = new URL(classURL.toString().replaceFirst(CLASS_EXTENSION, HTML_EXTENSION));
            } catch (MalformedURLException ignore) {
            }
		}
		if (htmlURL == null && propertiesURL != null) {
			try {
				htmlURL = new URL(propertiesURL.toString().replaceFirst(PROPERTIES_EXTENSION, HTML_EXTENSION));
            } catch (MalformedURLException ignore) {
            }
		}
		if (isInvalidURL(htmlURL)) {
			htmlURL = null;
		}
	}

	private boolean isInvalidURL(URL url) {
		if (url != null) {
			InputStream is = null;

			try {
				URLConnection conn = URLJarCollector.openConnection(url);

				is = conn.getInputStream();
				return false;
			} catch (IOException e) {
				return true;
			} finally {
				FileUtil.cleanupStream(is);
			}
		}
		return true;
	}

	// / -------------------------------------
	// / public
	// / -------------------------------------
    @Override
	public boolean isTeam() {
		return false;
	}

    @Override
	public URL getHtmlURL() {
		// Lazy
		if (htmlURL == null) {
			populateHtmlURL();
		}
		return htmlURL;
	}

    @Override
	public URL getPropertiesURL() {
		if (propertiesURL == null) {
			populatePropertiesURLFromClassURL();
		}
		return propertiesURL;
	}

    @Override
	public List<String> getFriendlyURLs() {
		populate();

		final Set<String> urls = new HashSet<String>();

		if (propertiesURL != null) {
			final String pUrl = propertiesURL.toString();
			final String noType = pUrl.substring(0, pUrl.lastIndexOf('.'));

			urls.add(pUrl);
			urls.add(noType);
			urls.add(propertiesURL.getPath());
		}
		if (classURL != null) {
			final String cUrl = classURL.toString();
			final String noType = cUrl.substring(0, cUrl.lastIndexOf('.'));

			urls.add(cUrl);
			urls.add(noType);
			urls.add(classURL.getPath());
		}
		if (getFullClassName() != null) {
			if (System.getProperty("TESTING", "false").equals("true")) {
				urls.add(getFullClassName());
			} else {
				urls.add(getUniqueFullClassName());
			}
			urls.add(getUniqueFullClassNameWithVersion());
		}
		if (root.isJAR()) {
			urls.add(root.getURL().toString());
		}
		if (root instanceof ClassPathRoot) {
			String friendly = ((ClassPathRoot) root).getFriendlyProjectURL(itemURL);

			if (friendly != null) {
				urls.add(friendly);
			}
		}
		return new ArrayList<String>(urls);
	}

    @Override
	public void update(long lastModified, boolean force) {
		if (lastModified > this.lastModified || force) {
			if (force) {
				isValid = true;
			}

			// trying to guess all correct file locations
			populate();

			this.lastModified = lastModified;
			if (classURL == null) {
				isValid = false;
			}
			loadProperties();
			if (root.isJAR() && !isPropertiesLoaded) {
				isValid = false;
			}
			if (isValid) {
				validateType(false);
			}
			if (isValid) {
				verifyName();
			}
		}
	}

	protected void validateType(boolean resolve) {
		populate();

		final IHostManager hostManager = Container.getComponent(IHostManager.class);

		robotType = hostManager.getRobotType(this, resolve, classURL != null);
		if (!robotType.isValid()) {
			isValid = false;
		}
	}
	
	public int getMinionType() {
		populate();
		
		final IHostManager hostManager = Container.getComponent(IHostManager.class);
		
		return hostManager.getMinionType(this,  classURL != null);
	}

	// Stronger than update
	public boolean validate() {
		validateType(true);
		return isValid;
	}

	// Note that ROBOCODE_CLASSNAME can be invalid, an hence can't be trusted when loaded!
	// Hence, we read the fullClassName field instead and NOT the ROBOCODE_CLASSNAME property.
	private boolean loadProperties() {
		if (!isPropertiesLoaded && propertiesURL != null) {
			InputStream ios = null;

			try {
				URLConnection con = URLJarCollector.openConnection(propertiesURL);

				ios = con.getInputStream();
				properties.load(ios);
				isPropertiesLoaded = true;
				return true;
			} catch (IOException e) {
				return false;
			} finally {
				FileUtil.cleanupStream(ios);
			}
		}
		return false;
	}

	private boolean verifyName() {
		String robotName = getFullClassName();
		String shortClassName = getShortClassName();

		final boolean valid = verifyRobotName(robotName, shortClassName, false);

		if (!valid) {
			isValid = false;
		}
		return valid;
	}

	public static boolean verifyRobotName(String fullClassName, String shortClassName, boolean silent) {
		if (fullClassName == null || fullClassName.length() == 0 || fullClassName.contains("$")) {
			return false;
		}

        int lIndex = fullClassName.indexOf('.');

		if (lIndex > 0) {
			String rootPackage = fullClassName.substring(0, lIndex);

			if (rootPackage.equalsIgnoreCase("robocode")) {
				if (!silent) {
					logError("Robot " + fullClassName + " ignored.  You cannot use package " + rootPackage);
				}
				return false;
			}

			if (rootPackage.length() > MAX_FULL_PACKAGE_NAME_LENGTH) {
				if (!silent) {
					logError(
							"Robot " + fullClassName + " has package name too long.  " + MAX_FULL_PACKAGE_NAME_LENGTH
							+ " characters maximum please.");
				}
				return false;
			}
		}

		if (shortClassName != null && shortClassName.length() > MAX_SHORT_CLASS_NAME_LENGTH) {
			if (!silent) {
				logError(
						"Robot " + fullClassName + " has classname too long.  " + MAX_SHORT_CLASS_NAME_LENGTH
						+ " characters maximum please.");
			}
			return false;
		}

		return true;
	}

    @Override
	public void storeProperties(OutputStream os, boolean includeSource, String version, String desc, String author, URL web) throws IOException {
		if (className != null) {
			properties.setProperty(ROBOT_CLASSNAME, className);
		}
		if (version != null) {
			properties.setProperty(ROBOT_VERSION, version);
		}
		if (desc != null) {
			properties.setProperty(ROBOT_DESCRIPTION, desc);
		}
		if (author != null) {
			properties.setProperty(ROBOT_AUTHOR_NAME, author);
		}
		if (web != null) {
			properties.setProperty(ROBOT_WEBPAGE, web.toString());
		}
		properties.setProperty(ROBOCODE_VERSION, Container.getComponent(IVersionManager.class).getVersion());

		properties.setProperty(ROBOT_INCLUDE_SOURCE, "" + includeSource);

		saveProperties(os);

		saveProperties();
	}

	private void saveProperties(OutputStream os) throws IOException {
		properties.store(os, "Robocode Robot");
	}

	private void saveProperties() {
		File file = new File(root.getPath(), className.replaceAll("\\.", "/") + PROPERTIES_EXTENSION);
		FileOutputStream fos = null;

		try {
			fos = new FileOutputStream(file);
			saveProperties(fos);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			FileUtil.cleanupStream(fos);
		}
		populatePropertiesURLFromClassURL();
	}

    @Override
	public boolean isDroid() {
		return robotType.isDroid();
	}

    @Override
	public boolean isTeamRobot() {
		return robotType.isTeamRobot();
	}

    @Override
	public boolean isAdvancedRobot() {
		return robotType.isAdvancedRobot();
	}

    @Override
	public boolean isStandardRobot() {
		return robotType.isStandardRobot();
	}

    @Override
	public boolean isInteractiveRobot() {
		return robotType.isInteractiveRobot();
	}

    @Override
	public boolean isPaintRobot() {
		return robotType.isPaintRobot();
	}

    @Override
	public boolean isJuniorRobot() {
		return robotType.isJuniorRobot();
	}
	
    @Override
    public boolean isBall() {
		return robotType.isBall();
	}
    
    @Override
    public boolean isSoccerRobot() {
		return robotType.isSoccerRobot();
	}

    @Override
	public boolean isHouseRobot() {
		return robotType.isHouseRobot();
	}
	
	public boolean isFreezeRobot() {
		return robotType.isFreezeRobot();
	}

    @Override
    public boolean isBotzillaBot() {
    	return robotType.isBotzillaBot();
    }
    
    @Override
    public boolean isDispenser() {
    	return robotType.isDispenser();
    }
    
    @Override
    public boolean isMinion() {
    	return robotType.isMinion();
    }

    @Override
    public boolean isZombie() {
    	return robotType.isZombie();
    }
    
    public boolean isHeatRobot() {
    	return robotType.isHeatRobot();
    }

    @Override
	public URL getClassPathURL() {
		return classPathURL;
	}

	public URL[] getSourcePathURLs() {
		// If no source path URL exists, we must use the class path URL
        if (sourcePathURLs.isEmpty()) {
            return new URL[]{classPathURL};
		}
        return sourcePathURLs.toArray(new URL[]{});
	}

    @Override
	public String getFullClassName() {
		// Lazy
		if (className == null) {
			populateClassNameFromClassURL();
		}
		return className;
	}

    @Override
	public String getVersion() {
		return properties.getProperty(ROBOT_VERSION, null);
	}

    @Override
	public String getDescription() {
		return properties.getProperty(ROBOT_DESCRIPTION, null);
	}

    @Override
	public String getAuthorName() {
		return properties.getProperty(ROBOT_AUTHOR_NAME, null);
	}

    @Override
	public String getRobotLanguage() {
		final String lang = properties.getProperty(ROBOT_LANGUAGE, null);

		return lang == null ? "java" : lang;
	}

    @Override
	public URL getWebpage() {
		try {
			return new URL(properties.getProperty(ROBOT_WEBPAGE, null));
		} catch (MalformedURLException e) {
			return null;
		}
	}

    @Override
	public boolean getIncludeSource() {
		return properties.getProperty(ROBOT_INCLUDE_SOURCE, "true").equalsIgnoreCase("true");
	}

    @Override
	public boolean isSourceIncluded() {
		return sourcePathURLs.size() > 0;
	}

    @Override
	public String getRobocodeVersion() {
		return properties.getProperty(ROBOCODE_VERSION, null);
	}

    @Override
	public String getReadableDirectory() {
		if (getRootPackage() == null) {
			return null;
		}
		String dir;

		if (root.isJAR()) {
			String jarFile = getClassPathURL().getFile();

			jarFile = jarFile.substring(jarFile.lastIndexOf('/') + 1, jarFile.length());
			dir = FileUtil.getRobotsDataDir().getPath();
			if (jarFile.length() > 0) {
				dir += File.separator + jarFile + '_';
			}
			dir += File.separator;
		} else {
			dir = getClassPathURL().getFile();
		}
		return dir + getRootPackage();
	}

    @Override
	public String getWritableDirectory() {
		if (getRootPackage() == null) {
			return null;
		}
		File dir;

		if (root.isJAR()) {
			String jarFile = getClassPathURL().getFile();

			jarFile = jarFile.substring(jarFile.lastIndexOf('/') + 1, jarFile.length());
			dir = FileUtil.getRobotsDataDir();
			if (jarFile.length() > 0) {
				dir = new File(dir, File.separator + jarFile + '_');
			}
		} else {
			dir = ALWAYS_USE_CACHE_FOR_DATA ? FileUtil.getRobotsDataDir() : root.getPath();
		}
		return dir + File.separator + getFullPackage().replace('.', File.separatorChar);
	}

	public RobotSpecification createRobotSpecification(RobotSpecification battleRobotSpec, String teamId) {
		RobotSpecification specification;

		if (battleRobotSpec != null) {
			specification = battleRobotSpec;
		} else {
			specification = createRobotSpecification();
		}
		if (teamId != null) {
			HiddenAccess.setTeamId(specification, teamId);
		}
		return specification;
	}

    @Override
	public String toString() {
		return itemURL.toString();
	}

}
