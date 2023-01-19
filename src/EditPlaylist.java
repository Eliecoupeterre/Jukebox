import javafx.scene.media.Media;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.util.List;

public class EditPlaylist extends JFrame{
    private JButton menuBtn;
    public JPanel EditPlaylist;
    public JComboBox playlistSelect;
    private JButton addmusicBtn;

    private JButton uplistBtn;
    private JButton downlistBtn;
    private JButton deleteBtn;
    private List<File> MusicList;
    private int tailleplayList=0;

    public EditPlaylist(JPanel main, JFrame frame) {

        menuBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                frame.setContentPane(main);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });

        addmusicBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                final JFileChooser fc = new JFileChooser();
                int returnVal = fc.showOpenDialog(menuBtn);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    System.out.println(file.getPath());
                    System.out.println(playlistSelect.getSelectedItem().toString() + ".playlist");

                    try(FileWriter fw = new FileWriter(playlistSelect.getSelectedItem().toString() + ".playlist", true);
                        BufferedWriter bw = new BufferedWriter(fw);
                        PrintWriter out = new PrintWriter(bw))
                    {
                        out.println(file.getPath());
                    } catch (IOException ex) {
                        //exception handling left as an exercise for the reader
                    }
                }
            }

        });
        playlistSelect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(playlistSelect.getSelectedItem());
                //int i=0;
                //for(i=1;i<=tailleplayList;i++){playList.remove(i);}
                tailleplayList=0;
                LoadPlaylistContent();
            }
        });
        uplistBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playList.getSelectedIndex();
                //monter le lien dans le fichier
                playlistfileedit(1,1);
                LoadPlaylistContent();
            }
        });
        downlistBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playList.getSelectedIndex();
                //descendre le lien dans le fichier
                playlistfileedit(2,1);
                LoadPlaylistContent();
            }
        });

        deleteBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //TODO:playListSelectedIndew à chier, return -1 à voir
                playList.getSelectedIndex();
                //playlistfileedit(0);
                System.out.println(playList.getSelectedValue());
                LoadPlaylistContent();
            }
        });
        playList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                System.out.println(String.valueOf(playList.getSelectedIndex()));
                System.out.println(playList.getSelectedIndex());
            }
        });
    }

    public void LoadPlaylists() { //chargement des .playlist
        File f = new File(System.getProperty("user.dir"));
        String[] pathnames = f.list();
        playlistSelect.removeAllItems();
        for (String pathname : pathnames) {
            System.out.println(pathname);
            if (pathname.contains(".playlist")) {
                playlistSelect.addItem(pathname.replace(".playlist", ""));
            }
        }

    }
    public void playlistfileedit(int choix,int selectionIndex){
        if(choix ==0){
            //TODO:Delete function
            int i=0;
            File f = new File(playlistSelect.getSelectedItem() + ".playlist");
            File f2 = new File("temp.txt");
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if(i!=selectionIndex) {
                        //TODO:réécrire le fichier sans la ligne
                        // Possible d'en faire une fonction pour le up&down qui suivra
                        System.out.println(i);
                    }
                    i++;
                }
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        if(choix ==1){
            //TODO:up function

        }
        if(choix ==2){
            //TODO:down function

        }
    }

    private JList<String> playList;
    public void LoadPlaylistContent(){
        DefaultListModel<String> songList = new DefaultListModel<>();
        File f = new File(playlistSelect.getSelectedItem()+".playlist");
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String line;
                while ((line = br.readLine()) != null) {
                    File f2 = new File(line);
                    songList.addElement(f2.getName().replace(".mp3",""));
                    tailleplayList++;
                }
            }
catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } ;
        playList.setModel((songList));
        playList=new JList<>(songList);
        add(playList);
        this.setTitle("Liste des musiques de la playlist");
        this.setVisible(true);
        add(new JScrollPane(playList));
        playList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
}


