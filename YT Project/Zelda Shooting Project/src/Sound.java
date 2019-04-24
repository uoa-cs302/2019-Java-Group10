import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	
	public Sound(String file, boolean music) {
		playSound(file, music);
	}
	
	public void playSound(String file, boolean backgroundMusic) {
		
		//source-code: https://stackoverflow.com/questions/26305/how-can-i-play-sound-in-java
		try {
	        Clip clip = AudioSystem.getClip();
	        AudioInputStream inputStream = AudioSystem.getAudioInputStream(this.getClass().getResourceAsStream(file));
	        clip.open(inputStream);
	        //loop music only if music is true
	        if (backgroundMusic == true) {
	        	clip.loop(clip.LOOP_CONTINUOUSLY);
	        }
	        else {
	        	clip.start();
	        }
	      } catch (Exception e) {
	    	  e.printStackTrace();
	      }
	}
	
	public void playCollisionSound() {
		
	}
	
	public void stopSound() {
		
	}

}
