import javax.swing.*;
import java.awt.event.*;

public class Recorder {
    public JPanel Enregistrement;
    private JButton captureBtn;
    private JButton pauseBtn;
    private JButton playBtn;
    private JButton stopBtn;
    private JProgressBar TempsListener;
    private JButton menuBtn;
    private JTextField TitreListener;
    private JLabel enregTime;
    private JButton reecouterBtn;
    private JLabel Temps;

    public Recorder(JPanel main, JFrame frame) {
        captureBtn.setEnabled(true);
        stopBtn.setEnabled(false);
        playBtn.setEnabled(false);
        pauseBtn.setEnabled(true);
        AudioRecorder05 enregistrement = new AudioRecorder05();
        menuBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                frame.setContentPane(main);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
        captureBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                System.out.println("Bouton Capture");
                captureBtn.setEnabled(false);
                stopBtn.setEnabled(true);
                enregistrement.captureAudio();
            }
        });
        stopBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                System.out.println("Bouton Stop");
                captureBtn.setEnabled(true);
                stopBtn.setEnabled(false);
                enregistrement.cibledonneeligne.stop();
                enregistrement.cibledonneeligne.close();
            }
        });
        pauseBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //marche pas ;(
                System.out.println("Bouton Pause");
                captureBtn.setEnabled(false);
                pauseBtn.setEnabled(false);
                stopBtn.setEnabled(true);
                playBtn.setEnabled(true);
                enregistrement.cibledonneeligne.stop();
            }
        });
        playBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //marche pas ;(
                System.out.println("Bouton Play");
                playBtn.setEnabled(false);
                captureBtn.setEnabled(false);
                stopBtn.setEnabled(true);
                pauseBtn.setEnabled(true);
                enregistrement.cibledonneeligne.start();
            }
        });
        TitreListener.addContainerListener(new ContainerAdapter() {
            public String RecordPath() {
                return super.toString();
            }
        });
        reecouterBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                music3.main("sonenreg.wav");
            }
        });
    }

}

