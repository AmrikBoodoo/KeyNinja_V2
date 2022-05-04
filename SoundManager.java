import javax.sound.sampled.AudioInputStream;		
import javax.sound.sampled.*;
import java.io.*;

import java.util.HashMap;				


public class SoundManager {				
	HashMap<String, Clip> clips;

   	Clip hitClip = null;				
   	Clip appearClip = null;				
   	Clip backgroundClip = null;			

	private static SoundManager instance = null;	

	private SoundManager () {
		clips = new HashMap<String, Clip>();

		Clip clip = loadClip("sounds/Intro Theme.wav");
		clips.put("backgroundMenu", clip);		

		clip = loadClip("sounds/level1 theme.wav");
		clips.put("backgroundLevel1", clip);			
							

		clip = loadClip("sounds/buttonSelect.wav");
		clips.put("buttonSelect", clip);		 
							

		clip = loadClip("sounds/jump.wav");
		clips.put("jump", clip);	
		
		clip = loadClip("sounds/coin.wav");
		clips.put("coin", clip);	

		clip = loadClip("sounds/lvl2 theme.wav");
		clips.put("backgroundLevel2", clip);	

		clip = loadClip("sounds/diamond.wav");
		clips.put("diamond", clip);	

		clip = loadClip("sounds/key.wav");
		clips.put("key", clip);	
		
	}


	public static SoundManager getInstance() {	// class method to get Singleton instance
		if (instance == null)
			instance = new SoundManager();
		
		return instance;
	}		


	public Clip getClip (String title) {

		return clips.get(title);		// gets a sound by supplying key
	}


    	public Clip loadClip (String fileName) {	// gets clip from the specified file
 		AudioInputStream audioIn;
		Clip clip = null;

		try {
    			File file = new File(fileName);
    			audioIn = AudioSystem.getAudioInputStream(file.toURI().toURL()); 
    			clip = AudioSystem.getClip();
    			clip.open(audioIn);
		}
		catch (Exception e) {
 			System.out.println ("Error opening sound files: " + e);
		}
    		return clip;
    	}


    	public void playSound(String title, Boolean looping) {
		Clip clip = getClip(title);
		if (clip != null) {
			clip.setFramePosition(0);
			if (looping)
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			else
				clip.start();
		}
    	}


    	public void stopSound(String title) {
		Clip clip = getClip(title);
		if (clip != null) {
			clip.stop();
		}
    	}

}