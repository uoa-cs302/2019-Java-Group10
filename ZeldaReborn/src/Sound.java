import java.io.BufferedInputStream;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	//Clip here is used to reference our sound file.
	Clip clip;
	
	public Sound() {
		//nothing done as functions handle usage
	}
	
	public void playSound(String file) {
		
		//source-code: https://stackoverflow.com/questions/26305/how-can-i-play-sound-in-java
		try {
			
			BufferedInputStream myStream = new BufferedInputStream(getClass().getResourceAsStream(file)); 
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(myStream);
			
	        clip = AudioSystem.getClip();
	        clip.open(inputStream);
	        
	        //avoiding memory leaks
	        inputStream.close();
	        clip.loop(Clip.LOOP_CONTINUOUSLY);
	      } catch (Exception e) {
	    	  e.printStackTrace();
	      } 
	}
	
	public void playCollisionSound(String file) {
		
		try {
			BufferedInputStream myStream = new BufferedInputStream(getClass().getResourceAsStream(file)); 
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(myStream);
			
	        clip = AudioSystem.getClip();
	        clip.open(inputStream);
	        
	        //avoiding memory leaks
	        inputStream.close();
	        clip.start();
	      } catch (Exception e) {
	    	  e.printStackTrace();
	      }
	}
	
	public void stopSound() {
		clip.stop();
	}

}