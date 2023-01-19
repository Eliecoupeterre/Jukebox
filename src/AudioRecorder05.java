// fonctionne avec en sortie le fichier sonenreg.wav enregistré
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.sound.sampled.*;

public class AudioRecorder05 {

AudioFormat audioFormat;
TargetDataLine cibledonneeligne;



public void captureAudio() {
	try{
	audioFormat = getAudioFormat();
	DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
	cibledonneeligne = (TargetDataLine)AudioSystem.getLine(dataLineInfo);
	new capturerligne().start();
	} catch (Exception e) {
      	e.printStackTrace();
      	System.exit(0); }
}

public AudioFormat getAudioFormat() {
	return new AudioFormat(8000.0F, 16, 1, true, false);
}

class capturerligne extends Thread {
	public void run(){
	try{
	cibledonneeligne.open(audioFormat);
	cibledonneeligne.start();
	AudioSystem.write(new AudioInputStream(cibledonneeligne), 
			    AudioFileFormat.Type.WAVE,
			    new File("sonenreg.wav"));
    	} catch (Exception e) {
  	e.printStackTrace(); }
	}
}

}