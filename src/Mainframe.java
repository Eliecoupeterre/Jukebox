import javafx.application.Application;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Mainframe extends Application {
    public JPanel Mainframe;
    private JButton EnregistrementBtn;
    private JButton PlayerBtn;
    private JButton CreatePlaylistBtn;
    private JButton EditPlaylistBtn;
    static JFrame frame = new JFrame("Main");
    private Recorder rec = new Recorder(Mainframe, frame);
    private MusicPlayer play = new MusicPlayer(Mainframe, frame);
    private EditPlaylist edit = new EditPlaylist(Mainframe, frame);
    private NewPlaylist newplay = new NewPlaylist(Mainframe, frame);

    @Override
    public void start(Stage primaryStage) throws Exception {
        frame.setContentPane(new Mainframe().Mainframe);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    
    public Mainframe() {
        EnregistrementBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                frame.setContentPane(rec.Enregistrement);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
        PlayerBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                play.LoadPlaylists(); //chargement des .playlist
                frame.setVisible(false);
                frame.setContentPane(play.MusicPlayer);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
        EditPlaylistBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                edit.LoadPlaylists(); //chargement des .playlist
                edit.LoadPlaylistContent();//chargement du contenu de la playlist
                frame.setVisible(false);
                frame.setContentPane(edit.EditPlaylist);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
        CreatePlaylistBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                frame.setContentPane(newplay.NewPlaylist);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

    public static void main(String args[]) {
        Application.launch(args);
    }
}
