package net.sf.robocode.sound;

import java.io.File;
import java.util.logging.Logger;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

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
