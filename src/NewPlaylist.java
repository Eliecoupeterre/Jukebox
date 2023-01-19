import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class NewPlaylist {
    private JButton returnBtn;
    public JPanel NewPlaylist;
    private JButton validationBtn;
    private JTextField newplaylistName;
    private JLabel playlistTitle;
public NewPlaylist(JPanel main, JFrame frame) {
    returnBtn.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            frame.setVisible(false);
            frame.setContentPane(main);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        }
    });
    validationBtn.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            System.out.println(newplaylistName.getText());
            try {
                File myObj = new File(newplaylistName.getText() + ".playlist");
                if (myObj.createNewFile()) {
                    System.out.println("File created: " + myObj.getName());
                } else {
                    System.out.println("File already exists.");
                }
            } catch (IOException ex) {
                System.out.println("An error occurred.");
                ex.printStackTrace();
            }
            frame.setVisible(false);
            frame.setContentPane(main);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        }
    });
}
}
