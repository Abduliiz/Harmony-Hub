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

    // adds a song to a playlist
    public List<Song> addSong(Song song) {
        this.songs.add(song);
        return this.songs;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("songs", songsToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray songsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Song s : songs) {
            jsonArray.put(s.toJson());
        }

        return jsonArray;
    }
}