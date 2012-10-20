/*******************************************************************************
 * Copyright (c) 2001-2012 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 *
 * Contributors:
 *     Mathew A. Nelson
 *     - Initial API and implementation
 *     Flemming N. Larsen
 *     - Added showInBrowser() for displaying content from an URL
 *     - Added showRoboWiki(), showYahooGroupRobocode(), showRobocodeRepository()
 *     - Removed the Thread.sleep(diff) from showSplashScreen()
 *     - Updated to use methods from the FileUtil, which replaces file operations
 *       that have been (re)moved from the robocode.util.Utils class
 *     - Changed showRobocodeFrame() to take a visible parameter
 *     - Added packCenterShow() for windows where the window position and
 *       dimension should not be read or saved to window.properties
 *     Luis Crespo & Flemming N. Larsen
 *     - Added showRankingDialog()
 *******************************************************************************/
package net.sf.robocode.ui;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import net.sf.robocode.battle.BattleProperties;
import net.sf.robocode.battle.BattleResultsTableModel;
import net.sf.robocode.battle.IBattleManager;
import net.sf.robocode.core.Container;
import net.sf.robocode.host.ICpuManager;
import net.sf.robocode.io.FileUtil;
import net.sf.robocode.mode.IMode;
import net.sf.robocode.repository.IRepositoryManager;
import net.sf.robocode.settings.ISettingsManager;
import net.sf.robocode.ui.battle.AwtBattleAdaptor;
import net.sf.robocode.ui.dialog.*;
import net.sf.robocode.ui.editor.IRobocodeEditor;
import net.sf.robocode.ui.packager.RobotPackager;
import net.sf.robocode.ui.trackeditor.EditorFrame;
import net.sf.robocode.version.IVersionManager;
import robocode.control.events.BattleCompletedEvent;
import robocode.control.events.IBattleListener;
import robocode.control.snapshot.ITurnSnapshot;

/**
 * @author Mathew A. Nelson (original)
 * @author Flemming N. Larsen (contributor)
 * @author Luis Crespo (contributor)
 */
public class WindowManager implements IWindowManagerExt {

    private final static int TIMER_TICKS_PER_SECOND = 50;
    private final AwtBattleAdaptor awtAdaptor;
    private RobotPackager robotPackager;
    private RobotExtractor robotExtractor;
    private final ISettingsManager properties;
    private final IBattleManager battleManager;
    private final ICpuManager cpuManager;
    private final IRepositoryManager repositoryManager;
    private final IVersionManager versionManager;
    private final IImageManager imageManager;
    private IRobotDialogManager robotDialogManager;
    private RobocodeFrame robocodeFrame;
    private boolean isGUIEnabled = true;
    private boolean isSlave;
    private boolean centerRankings = true;
    private boolean oldRankingHideState = true;
    private boolean showResults = true;

    public WindowManager(ISettingsManager properties, IBattleManager battleManager, ICpuManager cpuManager, IRepositoryManager repositoryManager, IImageManager imageManager, IVersionManager versionManager) {
        this.properties = properties;
        this.battleManager = battleManager;
        this.repositoryManager = repositoryManager;
        this.cpuManager = cpuManager;
        this.versionManager = versionManager;
        this.imageManager = imageManager;
        awtAdaptor = new AwtBattleAdaptor(battleManager, TIMER_TICKS_PER_SECOND, true);

        // we will set UI better priority than robots and battle have
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.currentThread().setPriority(Thread.NORM_PRIORITY + 2);
                } catch (SecurityException ex) {// that's a pity
                }
            }
        });
    }

    @Override
    public void setBusyPointer(boolean enabled) {
        robocodeFrame.setBusyPointer(enabled);
    }

    @Override
    public synchronized void addBattleListener(IBattleListener listener) {
        awtAdaptor.addListener(listener);
    }

    @Override
    public synchronized void removeBattleListener(IBattleListener listener) {
        awtAdaptor.removeListener(listener);
    }

    @Override
    public boolean isGUIEnabled() {
        return isGUIEnabled;
    }

    @Override
    public void setEnableGUI(boolean enable) {
        isGUIEnabled = enable;

        // Set the system property so the AWT headless mode.
        // Read more about headless mode here:
        // http://java.sun.com/developer/technicalArticles/J2SE/Desktop/headless/
        System.setProperty("java.awt.headless", "" + !enable);
    }

    @Override
    public void setSlave(boolean value) {
        isSlave = value;
    }

    @Override
    public boolean isSlave() {
        return isSlave;
    }

    @Override
    public boolean isShowResultsEnabled() {
        return properties.getOptionsCommonShowResults() && showResults;
    }

    @Override
    public void setEnableShowResults(boolean enable) {
        showResults = enable;
    }

    @Override
    public ITurnSnapshot getLastSnapshot() {
        return awtAdaptor.getLastSnapshot();
    }

    @Override
    public int getFPS() {
        return awtAdaptor.getFPS();
    }

    @Override
    public RobocodeFrame getRobocodeFrame() {
        if (robocodeFrame == null) {
            this.robocodeFrame = Container.getComponent(RobocodeFrame.class);
        }
        return robocodeFrame;
    }

    @Override
    public void showRobocodeFrame(boolean visible, boolean iconified) {
        RobocodeFrame frame = getRobocodeFrame();

        if (iconified) {
            frame.setState(Frame.ICONIFIED);
        }

        if (visible) {
            // Pack frame to size all components
            WindowUtil.packCenterShow(frame);

            WindowUtil.setStatusLabel(frame.getStatusLabel());

            frame.checkUpdateOnStart();

        } else {
            frame.setVisible(false);
        }
    }

    @Override
    public void showAboutBox() {
        packCenterShow(Container.getComponent(AboutBox.class), true);
    }

    @Override
    public String showBattleOpenDialog(final String defExt, final String name) {
        JFileChooser chooser = new JFileChooser(battleManager.getBattlePath());

        chooser.setFileFilter(
                new FileFilter() {
                    @Override
                    public boolean accept(File pathname) {
                        return pathname.isDirectory()
                                || pathname.getName().toLowerCase().lastIndexOf(defExt.toLowerCase())
                                == pathname.getName().length() - defExt.length();
                    }

                    @Override
                    public String getDescription() {
                        return name;
                    }
                });

        if (chooser.showOpenDialog(getRobocodeFrame()) == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile().getPath();
        }
        return null;
    }

    @Override
    public String saveBattleDialog(String path, final String defExt, final String name) {
        File f = new File(path);

        JFileChooser chooser;

        chooser = new JFileChooser(f);

        javax.swing.filechooser.FileFilter filter = new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory()
                        || pathname.getName().toLowerCase().lastIndexOf(defExt.toLowerCase())
                        == pathname.getName().length() - defExt.length();
            }

            @Override
            public String getDescription() {
                return name;
            }
        };

        chooser.setFileFilter(filter);
        int rv = chooser.showSaveDialog(getRobocodeFrame());
        String result = null;

        if (rv == JFileChooser.APPROVE_OPTION) {
            result = chooser.getSelectedFile().getPath();
            int idx = result.lastIndexOf('.');
            String extension = "";

            if (idx > 0) {
                extension = result.substring(idx);
            }
            if (!(extension.equalsIgnoreCase(defExt))) {
                result += defExt;
            }
        }
        return result;
    }

    @Override
    public void showVersionsTxt() {
        showInBrowser("file://" + new File(FileUtil.getCwd(), "").getAbsoluteFile() + File.separator + "versions.txt");
    }

    @Override
    public void showHelpApi() {
        showInBrowser(
                "file://" + new File(FileUtil.getCwd(), "").getAbsoluteFile() + File.separator + "javadoc" + File.separator
                + "index.html");
    }

    @Override
    public void showReadMe() {
        showInBrowser("file://" + new File(FileUtil.getCwd(), "ReadMe.html").getAbsoluteFile());
    }

    @Override
    public void showFaq() {
        showInBrowser("http://robowiki.net/w/index.php?title=Robocode/FAQ");
    }

    @Override
    public void showOnlineHelp() {
        showInBrowser("http://robowiki.net/w/index.php?title=Robocode/Getting_Started");
    }

    @Override
    public void showJavaDocumentation() {
        showInBrowser("http://java.sun.com/j2se/1.5.0/docs");
    }

    @Override
    public void showRobocodeHome() {
        showInBrowser("http://robocode.sourceforge.net");
    }

    @Override
    public void showRoboWiki() {
        showInBrowser("http://robowiki.net");
    }

    @Override
    public void showYahooGroupRobocode() {
        showInBrowser("http://groups.yahoo.com/group/robocode");
    }

    @Override
    public void showRobocodeRepository() {
        showInBrowser("http://robocoderepository.com");
    }

    @Override
    public void showOptionsPreferences() {
        try {
            battleManager.pauseBattle();

            WindowUtil.packCenterShow(getRobocodeFrame(), Container.getComponent(PreferencesDialog.class));
        } finally {
            battleManager.resumeIfPausedBattle(); // THIS is just dirty hack-fix of more complex problem with desiredTPS and pausing.  resumeBattle() belongs here.
        }
    }

    @Override
    public void showResultsDialog(BattleCompletedEvent event) {
    	IMode battleMode = battleManager.getBattleProperties().getBattleMode();
    	ResultsDialog dialog = null;
    	/* Retrieve results specific to soccer mode, unsorted */
    	if (battleMode.toString().equals("Soccer Mode")) {
    		 dialog = Container.getComponent(ResultsDialog.class);
    		dialog.setup(battleMode.getFinalResults(), event.getBattleRules().getNumRounds(), battleMode);
    	}
    	else if(battleMode.getGuiOptions().getShowResults()) {
    		dialog = Container.getComponent(ResultsDialog.class);
    		dialog.setup(event.getSortedResults(), event.getBattleRules().getNumRounds(), battleMode);
    	}
    	packCenterShow(dialog, true);
    }

    @Override
    public void showRankingDialog(boolean visible) {
        boolean currentRankingHideState = properties.getOptionsCommonDontHideRankings();

        // Check if the Ranking hide states has changed
        if (currentRankingHideState != oldRankingHideState) {
            // Remove current visible RankingDialog, if it is there
            Container.getComponent(RankingDialog.class).dispose();

            // Replace old RankingDialog, as the owner window must be replaced from the constructor
            Container.cache.removeComponent(RankingDialog.class);
            Container.cache.addComponent(RankingDialog.class);

            // Reset flag for centering the dialog the first time it is shown
            centerRankings = true;
        }

        RankingDialog rankingDialog = Container.getComponent(RankingDialog.class);

        if (visible) {
            packCenterShow(rankingDialog, centerRankings);
            centerRankings = false; // only center the first time Rankings are shown
        } else {
            rankingDialog.dispose();
        }

        // Save current Ranking hide state
        oldRankingHideState = currentRankingHideState;
    }

    @Override
    public void showRobocodeEditor() {
        JFrame editor = (JFrame) net.sf.robocode.core.Container.getComponent(IRobocodeEditor.class);

        if (!editor.isVisible()) {
            WindowUtil.packCenterShow(editor);
        } else {
            editor.setVisible(true);
        }
    }
    
    @Override
    public void showTrackEditor() {
        JFrame trackEditor = new EditorFrame();

        if (!trackEditor.isVisible()) {
            WindowUtil.packCenterShow(trackEditor);
        } else {
        	trackEditor.setVisible(true);
        }
    }

    @Override
    public void showRobotPackager() {
        if (robotPackager != null) {
            robotPackager.dispose();
            robotPackager = null;
        }

        robotPackager = net.sf.robocode.core.Container.factory.getComponent(RobotPackager.class);
        WindowUtil.packCenterShow(robotPackager);
    }

    @Override
    public void showRobotExtractor(JFrame owner) {
        if (robotExtractor != null) {
            robotExtractor.dispose();
            robotExtractor = null;
        }

        robotExtractor = new net.sf.robocode.ui.dialog.RobotExtractor(owner, this, repositoryManager);
        WindowUtil.packCenterShow(robotExtractor);
    }

    @Override
    public void showSplashScreen() {
        RcSplashScreen splashScreen = Container.getComponent(RcSplashScreen.class);

        packCenterShow(splashScreen, true);

        WindowUtil.setStatusLabel(splashScreen.getSplashLabel());

        repositoryManager.reload(versionManager.isLastRunVersionChanged());

        WindowUtil.setStatusLabel(splashScreen.getSplashLabel());
        cpuManager.getCpuConstant();

        WindowUtil.setStatus("");
        WindowUtil.setStatusLabel(null);

        splashScreen.dispose();
    }

    @Override
    public void showNewBattleDialog(BattleProperties battleProperties, boolean openBattle) {
        try {
            battleManager.pauseBattle();
            final NewBattleDialog battleDialog = Container.createComponent(NewBattleDialog.class);

            battleDialog.setup(battleProperties, openBattle);
            WindowUtil.packCenterShow(getRobocodeFrame(), battleDialog);
        } finally {
            battleManager.resumeBattle();
        }
    }

    @Override
    public boolean closeRobocodeEditor() {
        IRobocodeEditor editor = net.sf.robocode.core.Container.getComponent(IRobocodeEditor.class);

        return editor == null || !((JFrame) editor).isVisible() || editor.close();
    }

    @Override
    public void showCreateTeamDialog() {
        TeamCreator teamCreator = Container.getComponent(TeamCreator.class);

        WindowUtil.packCenterShow(teamCreator);
    }

    @Override
    public void showImportRobotDialog() {
        JFileChooser chooser = new JFileChooser();

        chooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (pathname.isHidden()) {
                    return false;
                }
                if (pathname.isDirectory()) {
                    return true;
                }
                String filename = pathname.getName();

                if (filename.equals("robocode.jar")) {
                    return false;
                }
                int idx = filename.lastIndexOf('.');

                String extension = "";

                if (idx >= 0) {
                    extension = filename.substring(idx);
                }
                return extension.equalsIgnoreCase(".jar") || extension.equalsIgnoreCase(".zip");
            }

            @Override
            public String getDescription() {
                return "Jar Files";
            }
        });

        chooser.setDialogTitle("Select the robot .jar file to copy to " + repositoryManager.getRobotsDirectory());

        if (chooser.showDialog(getRobocodeFrame(), "Import") == JFileChooser.APPROVE_OPTION) {
            File inputFile = chooser.getSelectedFile();
            String fileName = inputFile.getName();
            String extension = "";

            int idx = fileName.lastIndexOf('.');

            if (idx >= 0) {
                extension = fileName.substring(idx);
            }
            if (!extension.equalsIgnoreCase(".jar")) {
                fileName += ".jar";
            }
            File outputFile = new File(repositoryManager.getRobotsDirectory(), fileName);

            if (inputFile.equals(outputFile)) {
                JOptionPane.showMessageDialog(getRobocodeFrame(),
                                              outputFile.getName() + " is already in the robots directory!");
                return;
            }
            if (outputFile.exists()) {
                if (JOptionPane.showConfirmDialog(getRobocodeFrame(), outputFile + " already exists.  Overwrite?",
                                                  "Warning", JOptionPane.YES_NO_OPTION)
                        == JOptionPane.NO_OPTION) {
                    return;
                }
            }
            if (JOptionPane.showConfirmDialog(getRobocodeFrame(),
                                              "Robocode will now copy " + inputFile.getName() + " to " + outputFile.getParent(), "Import robot",
                                              JOptionPane.OK_CANCEL_OPTION)
                    == JOptionPane.OK_OPTION) {
                try {
                    FileUtil.copy(inputFile, outputFile);
                    repositoryManager.refresh();
                    JOptionPane.showMessageDialog(getRobocodeFrame(), "Robot imported successfully.");
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(getRobocodeFrame(), "Import failed: " + e);
                }
            }
        }
    }

    /**
     * Shows a web page using the browser manager.
     *
     * @param url The URL of the web page
     */
    private void showInBrowser(String url) {
        try {
            BrowserManager.openURL(url);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(getRobocodeFrame(), e.getMessage(), "Unable to open browser!",
                                          JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void showSaveResultsDialog(BattleResultsTableModel tableModel) {
        JFileChooser chooser = new JFileChooser();

        chooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (pathname.isHidden()) {
                    return false;
                }
                if (pathname.isDirectory()) {
                    return true;
                }
                String filename = pathname.getName();
                int idx = filename.lastIndexOf('.');

                String extension = "";

                if (idx >= 0) {
                    extension = filename.substring(idx);
                }
                return extension.equalsIgnoreCase(".csv");
            }

            @Override
            public String getDescription() {
                return "Comma Separated Value (CSV) File Format";
            }
        });

        chooser.setDialogTitle("Save battle results");

        if (chooser.showSaveDialog(getRobocodeFrame()) == JFileChooser.APPROVE_OPTION) {

            String filename = chooser.getSelectedFile().getPath();

            if (!filename.endsWith(".csv")) {
                filename += ".csv";
            }

            boolean append = properties.getOptionsCommonAppendWhenSavingResults();

            tableModel.saveToFile(filename, append);
        }
    }

    /**
     * Packs, centers, and shows the specified window on the screen.
     * @param window the window to pack, center, and show
     * @param center {@code true} if the window must be centered; {@code false} otherwise
     */
    private void packCenterShow(Window window, boolean center) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        window.pack();
        if (center) {
            window.setLocation((screenSize.width - window.getWidth()) / 2, (screenSize.height - window.getHeight()) / 2);
        }
        window.setVisible(true);
    }

    @Override
    public void cleanup() {
        if (isGUIEnabled()) {
            getRobocodeFrame().dispose();
        }
    }

    @Override
    public void setStatus(String s) {
        WindowUtil.setStatus(s);
    }

    @Override
    public void messageWarning(String s) {
        WindowUtil.messageWarning(s);
    }

    public IRobotDialogManager getRobotDialogManager() {
        if (robotDialogManager == null) {
            robotDialogManager = new RobotDialogManager();
        }
        return robotDialogManager;
    }

    @Override
    public void init() {
        setLookAndFeel();
        imageManager.initialize(); // Make sure this one is initialized so all images are available
        awtAdaptor.subscribe(isGUIEnabled);
    }

    /**
     * Sets the Look and Feel (LAF). This method first try to set the LAF to the
     * system's LAF. If this fails, it try to use the cross platform LAF.
     * If this also fails, the LAF will not be changed.
     */
    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Throwable t) {
            // Work-around for problems with setting Look and Feel described here:
            // http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6468089
            Locale.setDefault(Locale.US);

            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Throwable t2) {
                // For some reason Ubuntu 7 can cause a NullPointerException when trying to getting the LAF
                System.err.println("Could not set the Look and Feel (LAF).  The default LAF is used instead");
            }
        }
        // Java 1.6 provide system specific anti-aliasing. Enable it, if it has not been set
        if (new Double(System.getProperty("java.specification.version")) >= 1.6) {
            String aaFontSettings = System.getProperty("awt.useSystemAAFontSettings");

            if (aaFontSettings == null) {
                System.setProperty("awt.useSystemAAFontSettings", "on");
            }
        }
    }

    @Override
    public void runIntroBattle() {
        final File intro = new File(FileUtil.getCwd(), "battles/intro.battle");

        if (intro.exists()) {
            battleManager.setBattleFilename(intro.getPath());
            battleManager.loadBattleProperties();

            final boolean origShowResults = showResults; // save flag for showing the results

            showResults = false;
            try {
                battleManager.startNewBattle(battleManager.loadBattleProperties(), true, false);
                battleManager.setDefaultBattleProperties();
                robocodeFrame.afterIntroBattle();
            } finally {
                showResults = origShowResults; // always restore the original flag for showing the results
            }
        }
    }

    @Override
    public void setVisibleForRobotEngine(boolean visible) {
        if (visible && !isGUIEnabled()) {
            // The GUI must be enabled in order to show the window
            setEnableGUI(true);

            // Set the Look and Feel (LAF)
            init();
        }

        if (isGUIEnabled()) {
            showRobocodeFrame(visible, false);
            showResults = visible;
        }
    }

	@Override
	public IBattleManager getBattleManager() {
		return battleManager;
	}
}
