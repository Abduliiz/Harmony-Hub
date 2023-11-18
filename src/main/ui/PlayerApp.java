package ui;

import model.AudioPlayer;
import model.PlayList;
import model.Song;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


// MP3 player application
public class PlayerApp extends JFrame {
    private JFrame frame;
    private JButton addSongButton;
    private JButton removeSongButton;
//    private JButton submitButton;

    private JTextField name;
    private JTextField artist;
    private JTextField format;
    boolean status;
    private JTextField path;
    private JTextField rating;
    private DefaultTableModel tableModel;

    private JTable songTable; // Table for song list
    private JButton pauseButton;
    private JButton saveButton;
    private JButton loadButton; // Control buttons
    private JLabel statusLabel; // Label for displaying status messages
    private JButton browseButton;
    private JButton stopButton;
    private JButton resumeButton;


    private static final String JSON_STORE = "./data/playlist.json";
    private PlayList myList;
    private Song song;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private AudioPlayer audioPlayer;

    private static final Dimension fixedSize = new Dimension(400, 20);
    private static final Dimension max = new Dimension(500, 30);
    private static final Dimension min = new Dimension(300, 10);

    // runs the Player application
    public PlayerApp() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        runPlayerApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user inputs
    private void runPlayerApp() throws UnsupportedAudioFileException, LineUnavailableException, IOException {

        boolean runningStatus = true;
        String command;
        init();


    }

    private void init() {
        myList = new PlayList("My PlayList");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        frame = new JFrame();
        frame.setTitle("My Harmony Hub");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(820, 700);
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
        ImageIcon icon = new ImageIcon("music logo design.png");
        frame.setIconImage(icon.getImage());
        initPanel();
        initTextFields();
        init4();
        addListeners();
        addListeners2();

    }


    private void initPanel() {
        // Control panel
        JPanel controlPanel = new JPanel();
        statusLabel = new JLabel("Welcome to Harmony Hub!");
        addSongButton = new JButton("Add");
        removeSongButton = new JButton("Remove");
        pauseButton = new JButton("Pause");
        saveButton = new JButton("Save");
        loadButton = new JButton("Load");
        stopButton = new JButton("Stop");
        resumeButton = new JButton("Resume");
        controlPanel.add(statusLabel);
        controlPanel.add(addSongButton);
        controlPanel.add(removeSongButton);
        controlPanel.add(pauseButton);
        controlPanel.add(saveButton);
        controlPanel.add(loadButton);
        controlPanel.add(stopButton);
        controlPanel.add(resumeButton);
        frame.add(controlPanel, BorderLayout.NORTH);



    }

    private void addListeners2() {


        songTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = songTable.rowAtPoint(e.getPoint());

                if (row >= 0) {
                    String songPath = (String) songTable.getValueAt(row, 3);

                    try {
                        doPlay(songPath);
                    } catch (UnsupportedAudioFileException ex) {
                        JOptionPane.showMessageDialog(frame, "Unsupported Audio file format!");
                    } catch (LineUnavailableException ex) {
                        JOptionPane.showMessageDialog(frame, "Error!");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(frame, "Something went Wrong!");
                    }
                }
            }
        });

        saveButton.addActionListener(e -> savePlayList());

        pauseButton.addActionListener(e -> audioPlayer.pause());

        addSongButton.addActionListener(e -> processInputData());

        removeSongButton.addActionListener(e -> doRemove());
    }

    private void addListeners() {

        browseButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            int result = fileChooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                path.setText(selectedFile.getAbsolutePath());
            }
        });

        loadButton.addActionListener(e -> loadPlayList());

        resumeButton.addActionListener(e -> {
            try {
                audioPlayer.resumeAudio();
            } catch (UnsupportedAudioFileException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (LineUnavailableException ex) {
                throw new RuntimeException(ex);
            }
        });






    }

    private void initTextFields() {
        String[] columns = new String[]{"Title", "Artist", "Rating", "path"};
        tableModel = new DefaultTableModel(columns, 0);
        songTable = new JTable(tableModel);
        frame.add(new JScrollPane(songTable), BorderLayout.EAST);
        name = createTextField();
        artist = createTextField();
        path = createTextField();
        rating = createTextField();
        browseButton = new JButton("Browse");

        stopButton.addActionListener(e -> {
            try {
                audioPlayer.stop();
                status = false;
            } catch (UnsupportedAudioFileException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (LineUnavailableException ex) {
                throw new RuntimeException(ex);
            }
        });

    }

    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setPreferredSize(fixedSize);
        textField.setMaximumSize(max);
        return textField;
    }

    private void init4() {

        JLabel nameLabel = new JLabel("Name");
        JLabel artistLabel = new JLabel("Artist:");
        JLabel pathLabel = new JLabel("Path");
        JLabel ratingLabel = new JLabel("Rating (int)");

        JPanel songPanel = new JPanel();
        songPanel.setLayout(new BoxLayout(songPanel, BoxLayout.Y_AXIS));
        songPanel.add(nameLabel);
        songPanel.add(name);
        songPanel.add(artistLabel);
        songPanel.add(artist);
        songPanel.add(pathLabel);
        songPanel.add(path);
        songPanel.add(browseButton);
        songPanel.add(browseButton);
        songPanel.add(ratingLabel);
        songPanel.add(rating);
        frame.add(songPanel, BorderLayout.WEST);

        frame.pack();
    }

    // EFFECTS: saves the Playlist to file
    private void savePlayList() {
        try {
            jsonWriter.open();
            jsonWriter.write(myList);
            jsonWriter.close();
            JOptionPane.showMessageDialog(frame,"Saved " + myList.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    private void processInputData() {
        String name = this.name.getText();
        String path = this.path.getText();
        String artist = this.artist.getText();

        try {
            double rating = Double.parseDouble(this.rating.getText());

            if (rating < 0 || rating > 10) {
                JOptionPane.showMessageDialog(frame, "Rating should be between 0 and 10.");
            }

            Song newSong = new Song(name, artist, "wav", path, rating);
            myList.addSong(newSong);
            this.name.setText("");
            this.artist.setText("");
            this.path.setText("");
            this.rating.setText("");
            tableModel.setRowCount(0); // Clear existing content
            for (Song song : myList.getSongs()) {
                tableModel.addRow(new Object[]{song.getName(), song.getArtist(), song.getRating(), song.getPath()});
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid rating. Please enter a valid number.");
        }
    }

    // MODIFIES: this
    // EFFECTS: loads Playlist from file
    private void loadPlayList() {
        try {
            myList = jsonReader.read();
            JOptionPane.showMessageDialog(frame,"Loaded " + myList.getName() + " from " + JSON_STORE);
            tableModel.setRowCount(0); // Clear existing content
            for (Song song : myList.getSongs()) {
                tableModel.addRow(new Object[]{song.getName(), song.getArtist(), song.getRating(), song.getPath()});
            }
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    private void doPlay(String path) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if (myList.getSongs().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "your playlist is empty!");
        } else if (status == true) {
            JOptionPane.showMessageDialog(frame, "Song already plaing!");
        } else {
            audioPlayer = new AudioPlayer(path);
            audioPlayer.play(path);
            status = true;
        }
    }

    // MODIFIES: this
    // EFFECTS: removes a song from a playlist
    private void doRemove() {
        songTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = songTable.rowAtPoint(e.getPoint());
                String songName = (String) songTable.getValueAt(row, 1);

                for (int i = 0;i < myList.getSongs().size();i++) {
                    if (songName.equals(myList.getSongs().get(i).getName())) {
                        myList.getSongs().remove(i);
                        JOptionPane.showMessageDialog(frame, songName + " has been removed!");
                    } else {
                        System.out.println("I couldn't find a song with this name:" + name);
                        break;
                    }
                }
                if (row >= 0) {
                    tableModel.removeRow(row);
                }
            }
        });

    }

}