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
 *     - Rewritten
 *     Pavel Savara
 *     - now driven by BattleObserver
 *******************************************************************************/
package net.sf.robocode.ui.battleview;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import static java.lang.Math.*;

import java.util.HashMap;
import java.util.Random;

import net.sf.robocode.battle.IBattleManager;
import net.sf.robocode.battle.snapshot.RobotSnapshot;
import net.sf.robocode.mode.SoccerMode;
import net.sf.robocode.robotpaint.Graphics2DSerialized;
import net.sf.robocode.robotpaint.IGraphicsProxy;
import net.sf.robocode.settings.ISettingsListener;
import net.sf.robocode.settings.ISettingsManager;
import net.sf.robocode.ui.IImageManager;
import net.sf.robocode.ui.IWindowManager;
import net.sf.robocode.ui.IWindowManagerExt;
import net.sf.robocode.ui.gfx.GraphicsState;
import net.sf.robocode.ui.gfx.RenderImage;
import net.sf.robocode.ui.gfx.RobocodeLogo;
import robocode.control.events.BattleAdaptor;
import robocode.control.events.BattleFinishedEvent;
import robocode.control.events.BattleStartedEvent;
import robocode.control.events.TurnEndedEvent;
import robocode.control.snapshot.IBulletSnapshot;
import robocode.control.snapshot.ICustomObjectSnapshot;
import robocode.control.snapshot.IRobotSnapshot;
import robocode.control.snapshot.ITurnSnapshot;
import robocode.control.snapshot.IEffectAreaSnapshot;
import robocode.util.Utils;

import java.util.ArrayList;

import java.io.*;


/**
 * @author Mathew A. Nelson (original)
 * @author Flemming N. Larsen (contributor)
 * @author Pavel Savara (contributor)
 */
@SuppressWarnings("serial")
public class BattleView extends Canvas {

    private final static String ROBOCODE_SLOGAN = "Build the best, destroy the rest!";
    private final static Color CANVAS_BG_COLOR = SystemColor.controlDkShadow;
    private final static Area BULLET_AREA = new Area(new Ellipse2D.Double(-0.5, -0.5, 1, 1));
    private final static int ROBOT_TEXT_Y_OFFSET = 24;
    // The battle and battlefield,
    private BattleField battleField;
    private boolean initialized;
    private double scale = 1.0;
    // Ground
    private int[][] groundTiles;
    private final int groundTileWidth = 64;
    private final int groundTileHeight = 64;
    private Image groundImage;
    // Draw option related things
    private boolean drawRobotName;
    private boolean drawRobotEnergy;
    private boolean drawScanArcs;
    private boolean drawExplosions;
    private boolean drawGround;
    private boolean drawExplosionDebris;
    private int numBuffers = 2; // defaults to double buffering
    private RenderingHints renderingHints;
    // Fonts and the like
    private Font smallFont;
    private FontMetrics smallFontMetrics;
    private final IImageManager imageManager;
    private final ISettingsManager properties;
    private final IWindowManagerExt windowManager;
    private BufferStrategy bufferStrategy;
    private final GeneralPath robocodeTextPath = new RobocodeLogo().getRobocodeText();
    private static final MirroredGraphics mirroredGraphics = new MirroredGraphics();
    private final GraphicsState graphicsState = new GraphicsState();
    private IGraphicsProxy[] robotGraphics;
    private IBattleManager battleManager;

    private HashMap<String, RenderImage> customImage;
    
    public BattleView(ISettingsManager properties, IWindowManager windowManager, IImageManager imageManager) {
        this.properties = properties;
        this.windowManager = (IWindowManagerExt) windowManager;
        this.imageManager = imageManager;
        this.battleManager = windowManager.getBattleManager();
        this.customImage = new HashMap<String, RenderImage>();
        
        battleField = new BattleField(800, 600);

        new BattleObserver(windowManager);

        properties.addPropertyListener(new ISettingsListener() {
            @Override
            public void settingChanged(String property) {
                loadDisplayOptions();
                if (property.startsWith("robocode.options.rendering")) {
                    initialized = false;
                    reinitialize();
                }
            }
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                initialized = false;
                reinitialize();
            }
        });
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }

    @Override
    public void paint(Graphics g) {
        final ITurnSnapshot lastSnapshot = windowManager.getLastSnapshot();

        if (lastSnapshot != null) {
            update(lastSnapshot);
        } else {
            paintRobocodeLogo((Graphics2D) g);
        }
    }

    public BufferedImage getScreenshot() {
        BufferedImage screenshot = getGraphicsConfiguration().createCompatibleImage(getWidth(), getHeight());

        if (windowManager.getLastSnapshot() == null) {
            paintRobocodeLogo((Graphics2D) screenshot.getGraphics());
        } else {
            drawBattle((Graphics2D) screenshot.getGraphics(), windowManager.getLastSnapshot());
        }
        return screenshot;
    }

    private void update(ITurnSnapshot snapshot) {
        if (!initialized) {
            initialize();
        }

        if (!isDisplayable() || (getWidth() <= 0) || (getHeight() <= 0)) {
            return;
        }

        if (bufferStrategy != null) {
            try {
                Graphics2D g2 = (Graphics2D) bufferStrategy.getDrawGraphics();

                if (g2 != null) {
                    do {
                        try {
                            g2.setRenderingHints(renderingHints);

                            drawBattle(g2, snapshot);
                        } finally {
                            g2.dispose();
                        }
                        bufferStrategy.show();
                    } while (bufferStrategy.contentsLost());

                    Toolkit.getDefaultToolkit().sync(); // Update like... now!
                }
            } catch (NullPointerException e) {
            }
        }
    }

    private void loadDisplayOptions() {
        ISettingsManager props = properties;

        drawRobotName = props.getOptionsViewRobotNames();
        drawRobotEnergy = props.getOptionsViewRobotEnergy();
        drawScanArcs = props.getOptionsViewScanArcs();
        drawGround = props.getOptionsViewGround();
        drawExplosions = props.getOptionsViewExplosions();
        drawExplosionDebris = props.getOptionsViewExplosionDebris();

        renderingHints = props.getRenderingHints();
        numBuffers = props.getOptionsRenderingNoBuffers();
    }

    private void reinitialize() {
        initialized = false;
        bufferStrategy = null;
    }

    private void initialize() {
        loadDisplayOptions();

        if (bufferStrategy == null) {
            createBufferStrategy(numBuffers);
            bufferStrategy = getBufferStrategy();
        }

        // If we are scaled...
        if (getWidth() < battleField.getWidth() || getHeight() < battleField.getHeight()) {
            // Use the smaller scale.
            // Actually we don't need this, since
            // the RobocodeFrame keeps our aspect ratio intact.

            scale = min((double) getWidth() / battleField.getWidth(), (double) getHeight() / battleField.getHeight());
        } else {
            scale = 1;
        }

        // Scale font
        smallFont = new Font("Dialog", Font.PLAIN, (int) (10 / scale));
        smallFontMetrics = bufferStrategy.getDrawGraphics().getFontMetrics();
        
        // Initialize ground image
        if (drawGround) {
        	if(battleManager.getBattleProperties().getBattleMode() instanceof SoccerMode) {
        		imageManager.addCustomImage("ball", "/net/sf/robocode/ui/images/ball.png");
        		createSoccerField();
        	} else {
        		createGroundImage();
        	}
            
        	
        } else {
            groundImage = null;
        }

        initialized = true;
    }
	
	private void createSoccerField() {
		final int NUM_HORZ_TILES = battleField.getWidth() / groundTileWidth + 1;
		final int NUM_VERT_TILES = battleField.getHeight() / groundTileHeight + 1;
		
		int counter = 129;
		
		int groundWidth = (int) (battleField.getWidth() * scale);
		int groundHeight = (int) (battleField.getHeight() * scale);

		groundImage = new BufferedImage(groundWidth, groundHeight, BufferedImage.TYPE_INT_RGB);

		Graphics2D groundGfx = (Graphics2D) groundImage.getGraphics();

		groundGfx.setRenderingHints(renderingHints);

		groundGfx.setTransform(AffineTransform.getScaleInstance(scale, scale));

		for (int y = NUM_VERT_TILES - 1; y >= 0; y--) {
			for (int x = NUM_HORZ_TILES - 1; x >= 0; x--) {
				Image img = imageManager.getFieldTileImage(counter--);
				if (img != null) {
					groundGfx.drawImage(img, x * groundTileWidth, y * groundTileHeight, null);
				}
			}
		}
	}

    private void createGroundImage() {
        // Reinitialize ground tiles

        Random r = new Random(); // independent

        final int NUM_HORZ_TILES = battleField.getWidth() / groundTileWidth + 1;
        final int NUM_VERT_TILES = battleField.getHeight() / groundTileHeight + 1;

        if ((groundTiles == null) || (groundTiles.length != NUM_VERT_TILES) || (groundTiles[0].length != NUM_HORZ_TILES)) {

            groundTiles = new int[NUM_VERT_TILES][NUM_HORZ_TILES];
            for (int y = NUM_VERT_TILES - 1; y >= 0; y--) {
                for (int x = NUM_HORZ_TILES - 1; x >= 0; x--) {
                    groundTiles[y][x] = (int) round(r.nextDouble() * 4);
                }
            }
        }

        // Create new buffered image with the ground pre-rendered

        int groundWidth = (int) (battleField.getWidth() * scale);
        int groundHeight = (int) (battleField.getHeight() * scale);

        groundImage = new BufferedImage(groundWidth, groundHeight, BufferedImage.TYPE_INT_RGB);

        Graphics2D groundGfx = (Graphics2D) groundImage.getGraphics();

        groundGfx.setRenderingHints(renderingHints);

        groundGfx.setTransform(AffineTransform.getScaleInstance(scale, scale));

        for (int y = NUM_VERT_TILES - 1; y >= 0; y--) {
            for (int x = NUM_HORZ_TILES - 1; x >= 0; x--) {
                Image img = imageManager.getGroundTileImage(groundTiles[y][x]);

                if (img != null) {
                    groundGfx.drawImage(img, x * groundTileWidth, y * groundTileHeight, null);
                }
            }
        }
    }

    private void drawBattle(Graphics2D g, ITurnSnapshot snapShot) {
        // Save the graphics state
        graphicsState.save(g);

        // Reset transform
        g.setTransform(new AffineTransform());

        // Reset clip
        g.setClip(null);

        // Clear canvas
        g.setColor(CANVAS_BG_COLOR);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Calculate border space
        double dx = (getWidth() - scale * battleField.getWidth()) / 2;
        double dy = (getHeight() - scale * battleField.getHeight()) / 2;

        // Scale and translate the graphics
        AffineTransform at = AffineTransform.getTranslateInstance(dx, dy);

        at.concatenate(AffineTransform.getScaleInstance(scale, scale));
        g.setTransform(at);

        // Set the clip rectangle
        g.setClip(0, 0, battleField.getWidth(), battleField.getHeight());

        // Draw ground
        drawGround(g);

        if (snapShot != null) {
            // Draw scan arcs
            drawScanArcs(g, snapShot);

            drawEffectAreas(g, snapShot);
            
            // Draw custom
            drawImages(g, snapShot);
            
            // Draw robots
            drawRobots(g, snapShot);

            // Draw robot (debug) paintings
            drawRobotPaint(g, snapShot);
        }

        // Draw the border of the battlefield
        drawBorder(g);

        if (snapShot != null) {
            // Draw all bullets
            drawBullets(g, snapShot);

            // Draw all text
            drawText(g, snapShot);
        }

        // Restore the graphics state
        graphicsState.restore(g);
    }

    private void drawGround(Graphics2D g) {
        if (!drawGround) {
            // Ground should not be drawn
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, battleField.getWidth(), battleField.getHeight());
        } else {
            // Create pre-rendered ground image if it is not available
            if (groundImage == null) {
                createGroundImage();
            }

            // Draw the pre-rendered ground if it is available
            if (groundImage != null) {
                int groundWidth = (int) (battleField.getWidth() * scale) + 1;
                int groundHeight = (int) (battleField.getHeight() * scale) + 1;

                int dx = (getWidth() - groundWidth) / 2;
                int dy = (getHeight() - groundHeight) / 2;

                final AffineTransform savedTx = g.getTransform();

                g.setTransform(new AffineTransform());
                g.drawImage(groundImage, dx, dy, groundWidth, groundHeight, null);

                g.setTransform(savedTx);
            }
        }
    }

    private void drawBorder(Graphics2D g) {
        final Shape savedClip = g.getClip();

        g.setClip(null);

        g.setColor(Color.RED);
        g.drawRect(-1, -1, battleField.getWidth() + 2, battleField.getHeight() + 2);

        g.setClip(savedClip);
    }

    private void drawScanArcs(Graphics2D g, ITurnSnapshot snapShot) {
        if (drawScanArcs) {
            for (IRobotSnapshot robotSnapshot : snapShot.getRobots()) {
                if (robotSnapshot.getState().isAlive()) {
                    drawScanArc(g, robotSnapshot);
                }
            }
        }
    }

    //TODO Update graphic display of item
    private void drawItems(Graphics2D g, ITurnSnapshot snapShot) {
        double x, y;
        AffineTransform at;
        int battleFieldHeight = battleField.getHeight();
    }
    
    /* Draws all active images in the scene.
     *  
     * @param Grahpics2D g - rendering context used to
     * render goodies to the screen
     */
    private void drawImages(Graphics2D g, ITurnSnapshot snapShot) {
    	for (ICustomObjectSnapshot snap : snapShot.getCustomObjects()) {
    		RenderImage image = customImage.get(snap.getName());
    		if (image == null) {
    			image = addImage(snap.getName(), snap.getFilename());
    		}
			AffineTransform at = snap.getMatrix();
    		image.setTransform(at);
    		image.paint(g);
    		
    	}
    }

    /* Loads image in from given filename, puts RenderImage in
     * Hashmap<String, RenderImage>. Once added it will get rendered
     * each frame update.
     * 
     * @param String name - Key used for hashmap, used to fetch.
     * @param String filename - path to file
     * @param double x - X position
     * @param double y - Y position
     * 
     * @return newly added RenderImage
     */
	private RenderImage addImage(String name, String filename) {
		RenderImage img = new RenderImage(imageManager.addCustomImage(name,
				filename));
		customImage.put(name, img);

		return (img != null) ? img : null;
	}
	
	/* Added already created RenderImage to HashMap
	 * 
	 * @param String name - key name
	 * @param RenderImage img - already created render object
	 */
	private RenderImage addImage(String name, RenderImage img) {
		if (img != null) {
			customImage.put(name,  img);
			return img;
		}
		return null;
	}
	
	/* Removes a given key from HashMap
	 * 
	 * @param String name - key to remove
	 * 
	 * @return Deleted RenderImage
	 */
	private RenderImage removeImage(String name) {
		if (customImage.containsKey(name)) {
			return customImage.remove(name);
		}
		return null;
	}
    
    private void drawRobots(Graphics2D g, ITurnSnapshot snapShot) {
        double x, y;
        AffineTransform at;
        int battleFieldHeight = battleField.getHeight();

        if (drawGround && drawExplosionDebris) {
            RenderImage explodeDebrise = imageManager.getExplosionDebriseRenderImage();

            for (IRobotSnapshot robotSnapshot : snapShot.getRobots()) {
                if (robotSnapshot.getState().isDead()) {
                    x = robotSnapshot.getX();
                    y = battleFieldHeight - robotSnapshot.getY();

                    at = AffineTransform.getTranslateInstance(x, y);

                    explodeDebrise.setTransform(at);
                    explodeDebrise.paint(g);
                }
            }
        }

        for (IRobotSnapshot robotSnapshot : snapShot.getRobots()) {
        	if(robotSnapshot.getName().equals("soccer.BallBot* (1)")) {
        		x = robotSnapshot.getX();
                y = battleFieldHeight - robotSnapshot.getY();
                
                at = AffineTransform.getTranslateInstance(x, y);
                at.rotate(robotSnapshot.getBodyHeading());
                
                RenderImage robotRenderImage = imageManager.getCustomImage("ball");
                
                robotRenderImage.setTransform(at);
                robotRenderImage.paint(g);
                
        	} else if (robotSnapshot.getState().isAlive()) {
                x = robotSnapshot.getX();
                y = battleFieldHeight - robotSnapshot.getY();

                at = AffineTransform.getTranslateInstance(x, y);
                at.rotate(robotSnapshot.getBodyHeading());

                RenderImage robotRenderImage = imageManager.getColoredBodyRenderImage(robotSnapshot.getBodyColor());

                robotRenderImage.setTransform(at);
                robotRenderImage.paint(g);

                at = AffineTransform.getTranslateInstance(x, y);
                at.rotate(robotSnapshot.getGunHeading());

                RenderImage gunRenderImage = imageManager.getColoredGunRenderImage(robotSnapshot.getGunColor());

                gunRenderImage.setTransform(at);
                gunRenderImage.paint(g);

                if (!robotSnapshot.isDroid()) {
                    at = AffineTransform.getTranslateInstance(x, y);
                    at.rotate(robotSnapshot.getRadarHeading());

                    RenderImage radarRenderImage = imageManager.getColoredRadarRenderImage(robotSnapshot.getRadarColor());

                    radarRenderImage.setTransform(at);
                    radarRenderImage.paint(g);
                }
            }
        }
    }

    private void drawText(Graphics2D g, ITurnSnapshot snapShot) {
        final Shape savedClip = g.getClip();
    
	     g.setClip(null);

        for (IRobotSnapshot robotSnapshot : snapShot.getRobots()) {
            if (robotSnapshot.getState().isDead()) {
                continue;
            }
            int x = (int) robotSnapshot.getX();
            int y = battleField.getHeight() - (int) robotSnapshot.getY();

            if (drawRobotEnergy) {
                g.setColor(Color.white);
                int ll = (int) robotSnapshot.getEnergy();
                int rl = (int) ((robotSnapshot.getEnergy() - ll + .001) * 10.0);

                if (rl == 10) {
                    rl = 9;
                }
                String energyString = ll + "." + rl;

                if (robotSnapshot.getEnergy() == 0 && robotSnapshot.getState().isAlive()) {
                    energyString = "Disabled";
                }
                centerString(g, energyString, x, y - ROBOT_TEXT_Y_OFFSET - smallFontMetrics.getHeight() / 2, smallFont,
                             smallFontMetrics);
            }
            if (drawRobotName) {
                g.setColor(Color.white);
                centerString(g, robotSnapshot.getVeryShortName(), x,
                             y + ROBOT_TEXT_Y_OFFSET + smallFontMetrics.getHeight() / 2, smallFont, smallFontMetrics);
            }
        }

        g.setClip(savedClip);
    }


	private void drawEffectAreas(Graphics2D g, ITurnSnapshot snapShot) {
		double x, y;
		int tileIndex = 0;
		int battleFieldHeight = battleField.getHeight();
		
		for(IEffectAreaSnapshot effectAreaSnapshot : snapShot.getEffectAreas()) {
			x = effectAreaSnapshot.getXCoord();
			y = battleFieldHeight - effectAreaSnapshot.getYCoord();
			
			int x1 = (int)(x);
			int y1 = (int)((battleFieldHeight - effectAreaSnapshot.getYCoord()));
			
			//first four is default ground images
			tileIndex = effectAreaSnapshot.getActiveEffect() + 5;
		
			Image effAreaImg = imageManager.getGroundTileImage(tileIndex);
			
			Graphics2D g2d = (Graphics2D) g;
			g2d.drawImage(effAreaImg, x1, y1, null);
		}
	}
	
    private void drawRobotPaint(Graphics2D g, ITurnSnapshot turnSnapshot) {

        int robotIndex = 0;

        for (IRobotSnapshot robotSnapshot : turnSnapshot.getRobots()) {
            final Object graphicsCalls = ((RobotSnapshot) robotSnapshot).getGraphicsCalls();

            if (graphicsCalls == null || !robotSnapshot.isPaintEnabled()) {
                continue;
            }

            // Save the graphics state
            GraphicsState gfxState = new GraphicsState();

            gfxState.save(g);

            g.setClip(null);
            g.setComposite(AlphaComposite.SrcAtop);

            IGraphicsProxy gfxProxy = getRobotGraphics(robotIndex);

            if (robotSnapshot.isSGPaintEnabled()) {
                gfxProxy.processTo(g, graphicsCalls);
            } else {
                mirroredGraphics.bind(g, battleField.getHeight());
                gfxProxy.processTo(mirroredGraphics, graphicsCalls);
                mirroredGraphics.release();
            }

            // Restore the graphics state
            gfxState.restore(g);

            robotIndex++;
        }
    }

    private IGraphicsProxy getRobotGraphics(int robotIndex) {
        if (robotGraphics[robotIndex] == null) {
            robotGraphics[robotIndex] = new Graphics2DSerialized();
            robotGraphics[robotIndex].setPaintingEnabled(true);
        }
        return robotGraphics[robotIndex];
    }

    private void drawBullets(Graphics2D g, ITurnSnapshot snapShot) {
        final Shape savedClip = g.getClip();

        g.setClip(null);

        double x, y;

        for (IBulletSnapshot bulletSnapshot : snapShot.getBullets()) {
            x = bulletSnapshot.getPaintX();
            y = battleField.getHeight() - bulletSnapshot.getPaintY();

            AffineTransform at = AffineTransform.getTranslateInstance(x, y);

            if (bulletSnapshot.getState().isActive()) {

                // radius = sqrt(x^2 / 0.1 * power), where x is the width of 1 pixel for a minimum 0.1 bullet
                double scale = max(2 * sqrt(2.5 * bulletSnapshot.getPower()), 2 / this.scale);

                at.scale(scale, scale);
                Area bulletArea = BULLET_AREA.createTransformedArea(at);

                Color bulletColor;

                if (properties.getOptionsRenderingForceBulletColor()) {
                    bulletColor = Color.WHITE;
                } else {
                    bulletColor = new Color(bulletSnapshot.getColor());
                }
                g.setColor(bulletColor);
                g.fill(bulletArea);

            } else if (drawExplosions) {
                if (!bulletSnapshot.isExplosion()) {
                    double scale = sqrt(1000 * bulletSnapshot.getPower()) / 128;

                    at.scale(scale, scale);
                }

                RenderImage explosionRenderImage = imageManager.getExplosionRenderImage(
                        bulletSnapshot.getExplosionImageIndex(), bulletSnapshot.getFrame());

                explosionRenderImage.setTransform(at);
                explosionRenderImage.paint(g);
            }
        }
        g.setClip(savedClip);
    }

    private void centerString(Graphics2D g, String s, int x, int y, Font font, FontMetrics fm) {
        g.setFont(font);

        int width = fm.stringWidth(s);
        int height = fm.getHeight();
        int descent = fm.getDescent();

        double left = x - width / 2;
        double top = y - height / 2;

        double scaledViewWidth = getWidth() / scale;
        double scaledViewHeight = getHeight() / scale;

        double borderWidth = (scaledViewWidth - battleField.getWidth()) / 2;
        double borderHeight = (scaledViewHeight - battleField.getHeight()) / 2;

        if (left + width > scaledViewWidth) {
            left = scaledViewWidth - width;
        }
        if (top + height > scaledViewHeight) {
            top = scaledViewHeight - height;
        }
        if (left < -borderWidth) {
            left = -borderWidth;
        }
        if (top < -borderHeight) {
            top = -borderHeight;
        }
        g.drawString(s, (int) (left + 0.5), (int) (top + height - descent + 0.5));
    }

    private void drawScanArc(Graphics2D g, IRobotSnapshot robotSnapshot) {
        Arc2D.Double scanArc = (Arc2D.Double) ((RobotSnapshot) robotSnapshot).getScanArc();

        if (scanArc == null) {
            return;
        }

        final Composite savedComposite = g.getComposite();

        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));

        scanArc.setAngleStart((360 - scanArc.getAngleStart() - scanArc.getAngleExtent()) % 360);
        scanArc.y = battleField.getHeight() - robotSnapshot.getY() - robotSnapshot.getScanRadius();

        int scanColor = robotSnapshot.getScanColor();

        g.setColor(new Color(scanColor, true));

        if (abs(scanArc.getAngleExtent()) >= .5) {
            g.fill(scanArc);
        } else {
            g.draw(scanArc);
        }

        g.setComposite(savedComposite);
    }

    private void paintRobocodeLogo(Graphics2D g) {
        setBackground(Color.BLACK);
        g.clearRect(0, 0, getWidth(), getHeight());

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.transform(AffineTransform.getTranslateInstance((getWidth() - 320) / 2.0, (getHeight() - 46) / 2.0));
        g.setColor(new Color(0, 0x40, 0));
        g.fill(robocodeTextPath);

        Font font = new Font("Dialog", Font.BOLD, 14);
        int width = g.getFontMetrics(font).stringWidth(ROBOCODE_SLOGAN);

        g.setTransform(new AffineTransform());
        g.setFont(font);
        g.setColor(new Color(0, 0x50, 0));
        g.drawString(ROBOCODE_SLOGAN, (float) ((getWidth() - width) / 2.0), (float) (getHeight() / 2.0 + 50));
    }

    private class BattleObserver extends BattleAdaptor {

        public BattleObserver(IWindowManager windowManager) {
            windowManager.addBattleListener(this);
        }

        @Override
        public void onBattleStarted(BattleStartedEvent event) {
            battleField = new BattleField(event.getBattleRules().getBattlefieldWidth(),
                                          event.getBattleRules().getBattlefieldHeight());

            initialized = false;
            setVisible(true);

            super.onBattleStarted(event);

            robotGraphics = new IGraphicsProxy[event.getRobotsCount()];
        }

        @Override
        public void onBattleFinished(BattleFinishedEvent event) {
            super.onBattleFinished(event);
            robotGraphics = null;
        }

        @Override
        public void onTurnEnded(final TurnEndedEvent event) {
            if (event.getTurnSnapshot() == null) {
                repaint();
            } else {
                update(event.getTurnSnapshot());
            }
        }
    }
}
