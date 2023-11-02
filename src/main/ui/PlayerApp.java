package ui;

import model.AudioPlayer;
import model.PlayList;
import model.Song;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


// MP3 player application
public class PlayerApp {
    private static final String JSON_STORE = "./data/playlist.json";
    private PlayList myList;
    private Song song;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private AudioPlayer audioPlayer;

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


        while (runningStatus) {
            mainMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                System.out.println("Thank you!");
                runningStatus = false;
            } else {
                processCommands(command);
            }
        }


    }
    // MODIFIES: this
    // EFFECTS: initializes the application

    private void init() {
        System.out.println("\nWelcome to YOUR MP3 Player APP");
        System.out.println("\nLets create a playlist for you!");
        System.out.println("\nName your playlist:");
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        String x = input.next();
        myList = new PlayList(x);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // MODIFIES: this
    // EFFECTS: processes user commands

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void processCommands(String command) throws UnsupportedAudioFileException,
            LineUnavailableException, IOException {
        if (command.equals("add")) {
            Song newSong = doAdd();
            myList.addSong(newSong);
            System.out.println(newSong.getName() + "Has been added to " + myList.getName());
        } else if (command.equals("display")) {
            displaySongs();
        } else if (command.equals("remove")) {
            doRemove();
//        } else if (command.equals("create")) {
//            doCreateList();
        } else if (command.equals("save")) {
            savePlayList();
        } else if (command.equals("load")) {
            loadPlayList();
        } else if (command.equals("play")) {
            doPlay();
        } else if (command.equals("main")) {
            mainMenu();
//        } else if (command.equals("3")) {
//            audioPlayer.resumeAudio();
//        } else if (command.equals("4")) {
//            audioPlayer.resetAudioStream();
//        } else if (command.equals("5")) {
//            audioPlayer.stop();
//        } else if (command.equals("6")) {
//            audioPlayer.jump();
        } else {
            System.out.println("Selection not valid...");
        }
    }




    // EFFECTS: saves the Playlist to file
    private void savePlayList() {
        try {
            jsonWriter.open();
            jsonWriter.write(myList);
            jsonWriter.close();
            System.out.println("Saved " + myList.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads Playlist from file
    private void loadPlayList() {
        try {
            myList = jsonReader.read();
            System.out.println("Loaded " + myList.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a song to a playlist
    private Song doAdd() {
        System.out.println("Enter the name of the Song: ");
        String name = input.next();
        System.out.println("Enter the name of the Artist of the Song: ");
        String artist = input.next();
        System.out.println("Enter the Format of the Song (i.e MP3, WAV): ");
        String format = input.next();
        System.out.println("Enter the path of the song: ");
        String path = input.next();
        System.out.println("How much do you rate this song out of 10? ");
        double rating = input.nextDouble();

        return new Song(name,artist,format,path,rating);
    }

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void doPlay() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        String songPath;
        if (myList.getSongs().isEmpty()) {
            System.out.println("your playlist is empty!");
        } else {
            System.out.println("What song would you like to play?");
            displaySongs();
            String songName = input.next();

            for (int i = 0; i < myList.getSongs().size(); i++) {
                if (songName.equals(myList.getSongs().get(i).getName())) {
                    songPath = myList.getSongs().get(i).getPath();
                    System.out.println(songPath + " is now playing!");

                    try {
                        audioPlayer = new AudioPlayer(songPath);
                        Scanner sc = new Scanner(System.in);
                        audioPlayer.play(songPath);
                        while (true) {
                            int c = sc.nextInt();
                            audioPlayer.gotoChoice(c);
                            if (c == 4) {
                                break;
                            }
                        }
                    } catch (Exception ex) {
                        System.out.println("Error with playing sound.");
                        ex.printStackTrace();
                    }

                } else {
                    System.out.println("I couldn't find a song with this name:" + songName);
                }
                break;
            }
        }

    }

//    // MODIFIES: this
//    // EFFECTS: creates a new playlist (still need more implementation)
//    private PlayList doCreateList() {
//        System.out.println("Enter the name of the Playlist: ");
//        String name = input.next();
//        PlayList newList = new PlayList(name);
//        System.out.println("Your new list [" + newList.getName() + "] have been created!");
//        return newList;
//    }

    // MODIFIES: this
    // EFFECTS: removes a song from a playlist
    private void doRemove() {
        System.out.println("Enter the name of the Song to remove: ");
        displaySongs();
        String name = input.next();

        for (int i = 0;i < myList.getSongs().size();i++) {
            if (name.equals(myList.getSongs().get(i).getName()))  {
                myList.getSongs().remove(i);
                System.out.println(name + " has been removed!");
            } else {
                System.out.println("I couldn't find a song with this name:" + name);
                break;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: displays the songs in a playlist
    public void displaySongs() {
        if (myList.getSongs().isEmpty()) {
            System.out.println("Your playlist is empty, try adding new songs!");
        } else {
            for (Song song : myList.getSongs()) {
                System.out.println("[" + song.getName() + " By:" + song.getArtist()
                        + " Rating: " + song.getRating() + " Format:" + song.getFormat() + "]");
            }
        }

    }

    // EFFECTS: displays the menu of options to user
    private void mainMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tadd -> add song to playlist");
        System.out.println("\tplay -> play a song");
        System.out.println("\tremove -> remove song from playlist");
        System.out.println("\tdisplay -> display the songs in your playlist");
        System.out.println("\tsave -> save work room to file");
        System.out.println("\tload -> load work room from file");
        System.out.println("\tq -> quit");
    }

}