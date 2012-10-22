package net.sf.robocode.battle;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import net.sf.robocode.io.Logger;

//import net.sf.robocode.battle.Waypoint;

/**
 * Class that stores Track layout 
 * @author Anastasios Karydas s4231811
 */
public class TrackField implements ITrackField {
	
	//Track Name
	private String name;
	//Track Waypoints.
	private Waypoint waypoints;
	//Track Starting positions.
	private Waypoint spawnpoints;
	//Number of trackLaps
	final private int LAPS = 3;
	//Area of the road of the Track
	private Area road;
	//Area of the Terrain of the Track
	private Area terrain;
	
	/**
	 * This constructor for the TrackField takes a 
	 * bitmap file and scans the colors to construct the track.
	 * @param trackFile
	 */
	public TrackField(File trackFile) {
		loadTrackField(trackFile);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return this.name;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public IWaypoint getWaypoints() {
		return this.waypoints;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public IWaypoint getSpawnpoints() {
		return this.spawnpoints;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getLaps() {
		return this.LAPS;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onRoad(double x, double y) {
		// Check if point is in the bounds of the road shape.
		if (road.contains(x, y)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onTerrain(double x, double y) {
		// Check if point is in the bounds of the terrain Area.
		if (terrain.contains(x, y)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onBounds(double x, double y) {
		// Check if point is in the bounds of both the areas
		// if returns false the robot has hit a wall.
		if (onRoad(x, y) || onTerrain(x, y)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void loadTrackField(File trackFile) {
		name = trackFile.getName();
		// Get Image FIle and load into imageBuffer
		Image trackImage = getImage(trackFile.getPath());
		BufferedImage buffer = getBufferedImage(trackImage);
		// Get the Height and Width of the Image.
		int height = buffer.getHeight();
		int width = buffer.getWidth();
		// Image is broken into 64 by 64 blocks
		// Variable to hold each blocks colour.
		int blockColour;
		
		// Initialise Track Areas based on Bitmap file.
		road = new Area(new Rectangle(width, height));
		terrain = new Area(new Rectangle(width, height));
		// Scan through Bitmap file to cut away from 
		for (int i = 0; i < width/64; i++) {
			for (int j = 0; j < height/64; j++) {
				blockColour = buffer.getRGB(i*64+32, j*64+32);
				if (blockColour == Color.green.getRGB()) {
					
				}
				if (blockColour == Color.black.getRGB()) {
					
				}
				if (blockColour == Color.red.getRGB()) {
					
				}
				if (blockColour == Color.blue.getRGB()) {
					
				}
			}
		}
	}
	
	// copied from ImageUtil
    /**
     * Returns an image resource.
     *
     * @param filename the filename of the image to load
     * @return the loaded image
     */
    public static Image getImage(String filename) {
        URL url = TrackField.class.getResource(filename);

        if (url == null) {
            Logger.logError("Could not load image because of invalid filename: " + filename);
            return null;
        }

        try {
            final BufferedImage result = ImageIO.read(url);

            if (result == null) {
                final String message = "Could not load image: " + filename;

                Logger.logError(message);
                throw new Error();
            }
            return result;
        } catch (IOException e) {
            Logger.logError("Could not load image: " + filename);
            return null;
        }

    }
    
    // copied from ImageUtil
    /**
     * Creates and returns a buffered version of the specified image.
     *
     * @param image the image to create a buffered image for
     * @return a buffered image based on the specified image
     */
    public static BufferedImage getBufferedImage(Image image) {
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null),
                                                        BufferedImage.TYPE_INT_ARGB);

        Graphics g = bufferedImage.getGraphics();

        g.drawImage(image, 0, 0, null);

        return bufferedImage;
    }
	
	@Override
	public String toString(){
		return this.name + road.toString() + terrain.toString();
	}

}
