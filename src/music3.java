import javax.swing.*;
import javax.sound.sampled.*;
import java.io.File;

public class music3 extends JFrame
{
static File fichierson;
public void player()
{
try{
AudioInputStream inp = AudioSystem.getAudioInputStream(fichierson);
AudioFormat format = inp.getFormat();
System.out.println("Format = "+format);
DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
SourceDataLine source = (SourceDataLine) AudioSystem.getLine(info);
source.open(format);
source.start();
int lect = 0;
byte[] donneeaudio = new byte[2000];

while (lect> -1)
{
	lect = inp.read(donneeaudio, 0, donneeaudio.length);
	if (lect >=0){
		source.write(donneeaudio, 0, lect);}
}
source.drain();
source.close();
} catch (Exception ex) {}

}
public static void main(String path)
{
	try {
		fichierson = new File(path);
		music3 ms = new music3();
		ms.player();
	} finally {

	}
}
}

