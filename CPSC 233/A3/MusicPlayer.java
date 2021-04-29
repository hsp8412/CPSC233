/*
Author: Sipeng He
Version: March 10, 2021
 *  Made some small modifications for the method to obtain sound file
 *  
Author: Max O'Didily (see Readme.txt)
Version: November 29, 2018
 * Implementing the feature of playing and stopping background music
 * 
Limitations: 
-The background music cannot be turned off when program is running
 */

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class MusicPlayer {
	Clip clip;
	boolean ifContinue = true;

	/**
	 * Method: playMusic
	 * Features:
	 * -get the sound file
	 * -play the background music
	 */
	public void playMusic() {
		try {
			URL soundUrl = Simulator.class.getResource("music.wav");
			AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundUrl);
			clip = AudioSystem.getClip();
			clip.open(audioInput);
			clip.start();
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Method: endMusic
	 * Features:
	 * -end the music
	 */
	public void endMusic() {
		clip.stop();
	}
}