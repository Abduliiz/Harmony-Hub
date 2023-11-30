package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// This class represents a list of Song
// A user can add, remove, or skipp songs in the list
public class PlayList implements Writable {
    private List<Song> songs;
    private String name;

    // EFFECTS: constructs a playlist with a name and an arraylist of songs
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

    public int getPlayListSize() {
        return songs.size();
    }

    // MODIFIES: this
    // EFFECTS: adds a song to a playlist
    public List<Song> addSong(Song song) {
        this.songs.add(song);
        EventLog.getInstance().logEvent(new Event("Added song: " + song + "To: " + this.name));
        return this.songs;
    }

    // MODIFIES: this
    // EFFECTS: adds a song to a playlist
    public void removeSong(String song) {
        for (int i = 0;i < songs.size();i++) {
            if (song.equals(songs.get(i).getName())) {
                songs.remove(i);
                EventLog.getInstance().logEvent(new Event("Removed: " + song + "From: " + this.name));
            }
        }

    }

    // EFFECTS: Turns playlist into a json file
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("songs", songsToJson());
        return json;
    }

    // EFFECTS: returns songs in this playlist as a JSON array
    private JSONArray songsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Song s : songs) {
            jsonArray.put(s.toJson());
        }

        return jsonArray;
    }
}