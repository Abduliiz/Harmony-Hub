package model;


// This class represents a list of Song
// A user can add, remove, or skipp songs in the list

import java.util.ArrayList;
import java.util.List;

public class PlayList {
    private List<Song> songs;
    private String name;


    public PlayList(String name) {
        this.songs = new ArrayList<>();
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public List<Song> addSong(Song song) {
        this.songs.add(song);
        return this.songs;
    }


    public void displaySongs() {
        if (songs.isEmpty()) {
            System.out.println("Your playlist is empty, try adding new songs!");
        } else {
            for (Song song : songs) {
                System.out.println("[" + song.getName() + " by:" + song.getArtist()
                        + " rating: " + song.getRating() + "]");
            }
        }

    }

}


