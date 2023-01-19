import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
public class MusicPlayer extends Thread{

    private JButton menuBtn;
    public JPanel MusicPlayer;
    private JButton loopBtn;
    private JButton previousBtn;
    private JButton nextBtn;
    private JComboBox playlistSelect;
    private JButton playBtn;
    private JButton pauseBtn;
    private JButton volumeDown;
    private JButton volumeUp;
    private JProgressBar timeprogressBar;
    private JLabel actualTime;
    private JLabel endTime;
    private JLabel songName;
    private JLabel previoussongName;
    private JLabel nextsongName;
    private JSlider volumeSlider;
    private ArrayList<Media> MusicList;
    MediaPlayer mediaPlayer;
    public Duration actualtime;
    public Duration endtime;
    private int musicCounter=0;
    private int actualmusicIndex=0;

    public MusicPlayer(JPanel main, JFrame frame){
        MusicList = new ArrayList<>();


        playBtn.setEnabled(true);
        pauseBtn.setEnabled(false);

        previousBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loopBtn.setEnabled(true);
                previousBtn.setSelected(true);
                System.out.println(mediaPlayer.getStatus());
                if(actualmusicIndex>0){
                    actualmusicIndex--;
                }
                if(actualmusicIndex<=0){
                    actualmusicIndex=musicCounter-1;
                }
                mediaPlayer.stop();
                conduite(actualmusicIndex);

            }
        });

        nextBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loopBtn.setEnabled(true);
                nextBtn.setSelected(true);
                mediaPlayer.stop();
                actualmusicIndex++;
                if(actualmusicIndex==musicCounter){actualmusicIndex=0;}
                conduite(actualmusicIndex);
            }
        });

        menuBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(mediaPlayer.getStatus().equals("PLAYING")){
                    mediaPlayer.pause();
                    System.out.println(mediaPlayer.getStatus());
                };
                mediaPlayer.stop();
                //TODO:Leave menu en fermant le media sans faire planter si on sort sans avoir lancé le media
                pauseBtn.setSelected(false);
                playBtn.setSelected(false);
                Mainframe.frame.setVisible(false);
                frame.setContentPane(main);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });

        loopBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(loopBtn.isSelected())
                {
                loopBtn.setSelected(false);
                }else{
                    loopBtn.setSelected(true);
                }
                System.out.println(loopBtn.isSelected());
                //TODO:getOnRepeat, pas compris
                /*if(mediaPlayer.getOnRepeat()!=null)
                {
                    mediaPlayer.setOnRepeat(null);
                    System.out.println(mediaPlayer.getOnRepeat());
                }
                else
                {
                    //mediaPlayer.setOnRepeat();
                    System.out.println(mediaPlayer.getOnRepeat());
                }*/

            }
        });

        pauseBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playBtn.setEnabled(true);
                pauseBtn.setEnabled(false);
                mediaPlayer.pause();
                actualtime=mediaPlayer.getCurrentTime();
                pauseBtn.setSelected(true);


            }
        });

        playBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean statut;
                playBtn.setEnabled(false);
                pauseBtn.setEnabled(true);
                conduite(actualmusicIndex);
                    };
        });

        playlistSelect.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Passage a la playlist:\t"+playlistSelect.getSelectedItem());
                File f = new File(playlistSelect.getSelectedItem() + ".playlist");
                MusicList.clear();
                try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                    String line;
                    musicCounter=0;
                    while ((line = br.readLine()) != null) {
                        Media liste = new Media(new File(line).toURI().toString());
                        MusicList.add(liste);
                        musicCounter++;
                        System.out.println("\n"+line+"\t"+musicCounter);
                    }
                }
                catch (FileNotFoundException ex)
                {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });



        volumeUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mediaPlayer.setMute(false);
                mediaPlayer.setVolume(1.0);
                volumeSlider.setValue(100);
            }
        });
        volumeDown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mediaPlayer.setMute(true);
                volumeSlider.setValue(0);
            }
        });
        volumeSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                mediaPlayer.setVolume((double)volumeSlider.getValue()/100);
                if(volumeSlider.getValue()>0&&mediaPlayer.isMute()){
                    mediaPlayer.setMute(false);
                }
            }
        });
    }

        public void TimeProgress(){//position de la progressbar
        mediaPlayer.setOnReady(()->{
            //Set the totalduration
            endtime=mediaPlayer.getMedia().getDuration();
            double endTimesecondes = (Math.floor(endtime.toSeconds()))%60;
            double endTimeminutes = (((Math.floor(endtime.toSeconds()))/60)-((Math.floor(endtime.toSeconds()))%60)/60);
            System.out.println(endTimesecondes);
            System.out.println(endTimeminutes);
            endTime.setText(String.format("%.0f", endTimeminutes) + ":" + String.format("%.0f", endTimesecondes));
        });
        timeprogressBar.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                mediaPlayer.seek(endtime.multiply(timeprogressBar.getValue()/1000.0));
            }
        });
        //player current time
        mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
            timeprogressBar
                    .setValue((int) (newValue.divide(endtime.toMillis()).toMillis() * 1000.0));
            double currentTimesecondes = (Math.floor(newValue.toSeconds()))%60;
            double currentTimeminutes = (((Math.floor(newValue.toSeconds()))/60)-((Math.floor(newValue.toSeconds()))%60)/60);
            if(currentTimesecondes>=10)
            {
            actualTime.setText(String.format("%.0f", currentTimeminutes) + ":" + String.format("%.0f", currentTimesecondes));
            }else{
                actualTime.setText(String.format("%.0f", currentTimeminutes) + ":" + String.format("0%.0f", currentTimesecondes));
            }actualtime=newValue;
            if (actualtime.equals(endtime.add(Duration.millis(1000).negate()))){
                //TODO:loopBtn non fonctionnel ;(
                if (loopBtn.isSelected()) {
                    mediaPlayer.setCycleCount(1);
                    System.out.println(mediaPlayer.getCycleCount());
                }
            }

        });
            /*mediaPlayer.statusProperty().addListener((observable, oldValue, newValue) ->{
                System.out.println(oldValue);
                System.out.println(newValue);
                if(oldValue.equals("PLAYING")&&newValue.equals("UNKNOWN")){
                    actualmusicIndex++;
                    System.out.println("ca passe"+actualmusicIndex);
                    //mediaPlayer.stop();
                    //conduite(actualmusicIndex);
                }
            });*/

                    mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observableValue, Duration duration, Duration t1) {
                if(duration.equals(t1)){
                    //TODO:conduite à la suite à revoir
                    actualmusicIndex++;
                    conduite(actualmusicIndex);
                }
            }
        });
    }


        public void LoadPlaylists() { //chargement des .playlist
        File f = new File(System.getProperty("user.dir"));
        String[] pathnames = f.list();
        playlistSelect.removeAllItems();
        for (String pathname : pathnames) {
            if (pathname.contains(".playlist")) {
                System.out.println(pathname);
                playlistSelect.addItem(pathname.replace(".playlist", ""));
            }
        }
    }
        public void conduite(int compteur){
        boolean encours;
            mediaPlayer = new MediaPlayer(MusicList.get(compteur));
            System.out.println(mediaPlayer.getStatus().toString());
            System.out.println(MusicList.get(compteur).getSource());
            if (mediaPlayer.getStatus().toString().equals("UNKNOWN")&&!playBtn.isSelected()) {
                //mediaPlayer.setStartTime(Duration.millis(220000));//TODO:Pour faciliter le test du looping
                System.out.println("Musique n°"+actualmusicIndex);
                mediaPlayer.play();
                endtime = mediaPlayer.getTotalDuration();
                playBtn.setSelected(true);
                TimeProgress();
                songName.setText(titresanschemin(MusicList.get(compteur).getSource()));
                if(compteur-1>0){
                    previoussongName.setText(titresanschemin(MusicList.get(compteur-1).getSource()));
                }else {
                    previoussongName.setText(titresanschemin(MusicList.get(musicCounter-1).getSource()));}
                if(compteur+1>musicCounter-1){
                    nextsongName.setText(titresanschemin(MusicList.get(0).getSource()));
                }else{
                    nextsongName.setText(titresanschemin(MusicList.get(compteur+1).getSource()));
                }
            }
            else if(mediaPlayer.getStatus().toString().equals("UNKNOWN")&&playBtn.isSelected()&&pauseBtn.isSelected()){
                System.out.println("pret pour reprise");
                System.out.println("Musique n°"+actualmusicIndex);
                mediaPlayer.setStartTime(actualtime);
                mediaPlayer.play();
                TimeProgress();
                System.out.println(pauseBtn.isSelected());
                System.out.println(playBtn.isSelected());
                songName.setText(titresanschemin(MusicList.get(compteur).getSource()));
                if(compteur-1>0){
                    previoussongName.setText(titresanschemin(MusicList.get(compteur-1).getSource()));
                }else {
                    previoussongName.setText(titresanschemin(MusicList.get(musicCounter-1).getSource()));}
                if(compteur+1>musicCounter-1){
                    nextsongName.setText(titresanschemin(MusicList.get(0).getSource()));
                }else{
                    nextsongName.setText(titresanschemin(MusicList.get(compteur+1).getSource()));
                }

            }else if(mediaPlayer.getStatus().toString().equals("UNKNOWN")&&playBtn.isSelected()&&nextBtn.isSelected())
            {
                //mediaPlayer.setStartTime(Duration.millis(220000));//TODO:Pour faciliter le test du looping
                System.out.println("Musique n°"+actualmusicIndex);
                mediaPlayer.play();
                songName.setText("");
                endtime = mediaPlayer.getTotalDuration();
                nextBtn.setSelected(false);
                TimeProgress();
                songName.setText(titresanschemin(MusicList.get(compteur).getSource()));
                if(compteur-1>0){
                    previoussongName.setText(titresanschemin(MusicList.get(compteur-1).getSource()));
                }else {
                    previoussongName.setText(titresanschemin(MusicList.get(musicCounter-1).getSource()));}
                if(compteur+1>musicCounter-1){
                    nextsongName.setText(titresanschemin(MusicList.get(0).getSource()));
                }else{
                    nextsongName.setText(titresanschemin(MusicList.get(compteur+1).getSource()));
                }
            }else if(mediaPlayer.getStatus().toString().equals("UNKNOWN")&&playBtn.isSelected()&&previousBtn.isSelected())
            {
                //mediaPlayer.setStartTime(Duration.millis(220000));//TODO:Pour faciliter le test du looping
                System.out.println("Musique n°"+actualmusicIndex);
                mediaPlayer.play();
                songName.setText("");
                endtime = mediaPlayer.getTotalDuration();
                previousBtn.setSelected(false);
                TimeProgress();
                songName.setText(titresanschemin(MusicList.get(compteur).getSource()));
                if(compteur-1>0){
                    previoussongName.setText(titresanschemin(MusicList.get(compteur-1).getSource()));
                }else {
                    previoussongName.setText(titresanschemin(MusicList.get(musicCounter-1).getSource()));}
                if(compteur+1>musicCounter-1){
                    nextsongName.setText(titresanschemin(MusicList.get(0).getSource()));
                }else{
                    nextsongName.setText(titresanschemin(MusicList.get(compteur+1).getSource()));
                }
            };
        }
        public String titresanschemin(String chemin){
                String titre = null;
                for(int i=chemin.length(); i > 0; i--){
                    if(chemin.charAt(i - 1) == '/'){
                        titre=chemin.substring(i,chemin.length()-4);
                        i=0;
                    }
                }
                return titre;
        }
    }
