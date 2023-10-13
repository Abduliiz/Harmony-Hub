package model;

import java.util.WeakHashMap;

// represents a song that includes the name, artist, format, and rating of the song
public class Song {
    private String name;       // the name of the song
    private String artist;     // the name of the artist
    private String format;     // the song file format
    private String path;       // path for the file
    private double rating;        // how much a user likes the song out of 10


    // EFFECTS: constructs a song with a songName, artistName, format, and a songRating
    public Song(String songName, String artistName, String format, double songRating) {
        this.name = songName;
        this.artist = artistName;
        this.format = format;
        this.rating = songRating;

        if (!format.startsWith(".")) {
            StringBuffer sb = new StringBuffer();
            sb.append(".");
            sb.append(format);
            this.format = sb.toString();

        } else {
            this.format = format;
        }
    }



    public String getName() {
        return name;
    }

    public double getRating() {
        return rating;
    }

    public String getArtist() {
        return artist;
    }

    public String getFormat() {
        return format;
    }
}



// full marks for code coverage can be achieved only if there is at least one
// branch (i.e. if/else) or loop in the code included in the model package.

//    As a user, I want to be able to add songs to my playlist.
//        As a user, I want to be able to view my list of songs in my playlist.
//        As a user, I want to be able to be able to play, pause, and skip a song
//        As a user, I want to be able to view the artist of the song
//        As a user, I want to be able to shuffle between the songs
//        As a user, I want to be able to repeat songs
//        As a user, I want to be able to mark certain songs as favourite's