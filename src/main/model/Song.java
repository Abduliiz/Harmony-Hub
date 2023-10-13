package model;

// represents a song that includes the name, artist, format, and rating of the song
public class Song {
    private String name;       // the name of the song
    private String artist;     // the name of the artist
    private String format;     // the song file format
    private String path;       // path for the file
    private double rating;        // how much a user likes the song out of 10


    // EFFECTS: constructs a song with a songName, artistName, format that starts with a ".", and a songRating
    public Song(String songName, String artistName, String format, double songRating) {
        this.name = songName;
        this.artist = artistName;
        this.rating = songRating;
        this.format = format.toUpperCase();

        if (!format.startsWith(".")) {
            StringBuffer sb = new StringBuffer();
            sb.append(".");
            sb.append(format);
            this.format = sb.toString().toUpperCase();
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
