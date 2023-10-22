package model;

import org.json.JSONObject;
import persistence.Writable;

// represents a song that includes the name, artist, format, and rating of the song
public class Song implements Writable {
    private final String name;       // the name of the song
    private final String artist;     // the name of the artist
    private String format;     // the song file format
    private final String path;       // path for the file
    private final double rating;        // how much a user likes the song out of 10


    // EFFECTS: constructs a song with a songName, artistName, format that starts with a ".", and a songRating
    public Song(String songName, String artistName, String format, String path, double songRating) {
        this.name = songName;
        this.artist = artistName;
        this.rating = songRating;
        this.format = format.toUpperCase();
        this.path = path;


        if (!format.startsWith(".")) {
            StringBuffer sb = new StringBuffer();
            sb.append(".");
            sb.append(format);
            this.format = sb.toString().toUpperCase();
        }
    }

    // Effects: Turns song into a Json file
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("path", path);
        json.put("artist", artist);
        json.put("format", format);
        json.put("rating", rating);
        return json;
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

    public String getPath() {
        return path;
    }
}