package net.sf.robocode.sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Background Music Tracker function
 * This function will use Audio Input Stream to run the sound file
 * while the boolean variable equals to true.
 * 
 * @author Eric
 *
 */

public class BackgroundMusicTracker {
	
	public static boolean music = false;
	public static Clip clip;
	//public static BackgroundMusicTracker object = new BackgroundMusicTracker();
	
	public static void enableMusic(boolean b) {
		 music = b;
	}
	
	
	public static void updateStatus(){
		
		 if(music){
			 try{ 
				AudioInputStream audioIn = AudioSystem.getAudioInputStream(new BackgroundMusicTracker().getClass().getClassLoader().getResourceAsStream("net/sf/robocode/sound/sounds/testing.wav"));
         		clip = AudioSystem.getClip(); 
         		clip.open(audioIn); 
         		clip.start();
                clip.loop(-1);
             }catch(Exception ex){ 
                 System.out.println("Error with playing sound."); 
                 ex.printStackTrace(); 
             }
		 }else{
			 try{
				 clip.stop();
			 }catch(Exception ex){
				 System.out.println("Error with playing sound."); 
                 ex.printStackTrace(); 
			 }
		 }
	}
	

}
