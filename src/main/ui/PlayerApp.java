package ui;

import model.PlayList;
import model.Song;

import java.util.Scanner;

public class PlayerApp {
    private PlayList myList;
    private Song song;
    private Scanner input;

    public PlayerApp() {
        runPlayerApp();
    }


    private void runPlayerApp() {
        boolean runningStatus = true;
        String command = null;

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


    private void init() {
        System.out.println("\nWelcome to YOUR MP3 Player APP");
        System.out.println("\nLets create a playlist for you!");
        System.out.println("\nName your playlist:");
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        String x = input.next();
        myList = new PlayList(x);
    }

    // MODIFIES: this
    // EFFECTS: processes user commands
    private void processCommands(String command) {
        if (command.equals("a")) {
            Song newSong = doAdd();
            myList.addSong(newSong);
            System.out.println(newSong.getName() + "Has been added to " + myList.getName());
        } else if (command.equals("d")) {
            displaySongs();
        } else if (command.equals("r")) {
            doRemove();
        } else {
            System.out.println("Selection not valid...");
        }
    }


    private Song doAdd() {
        System.out.println("Enter the name of the Song: ");
        String name = input.next();
        System.out.println("Enter the name of the Artist of the Song: ");
        String artist = input.next();
        System.out.println("Enter the Format of the Song (i.e MP3, WAV): ");
        String format = input.next();
        format.toUpperCase();
        System.out.println("How much do you rate this song out of 10? ");
        Double rating = input.nextDouble();


        return new Song(name,artist,format,rating);
    }

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


    public void displaySongs() {
        if (myList.getSongs().isEmpty()) {
            System.out.println("Your playlist is empty, try adding new songs!");
        } else {
            for (Song song : myList.getSongs()) {
                System.out.println("[" + song.getName() + " by:" + song.getArtist()
                        + " rating: " + song.getRating() + "]");
            }
        }

    }





    private void mainMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add song to playlist");
        System.out.println("\tr -> remove song from playlist");
        System.out.println("\td -> display the songs in your playlist");
        System.out.println("\tq -> quit");
    }

}
