package persistence;

import model.PlayList;
import model.Song;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads playlist from file and returns it;
    // throws IOException if an error occurs reading data from file
    public PlayList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePlayList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses playlist from JSON object and returns it
    private PlayList parsePlayList(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        PlayList pl = new PlayList(name);
        addSongs(pl, jsonObject);
        return pl;
    }

    // MODIFIES: pl
    // EFFECTS: parses thingies from JSON object and adds them to playlist
    private void addSongs(PlayList wr, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("songs");
        for (Object json : jsonArray) {
            JSONObject nextThingy = (JSONObject) json;
            addSong(wr, nextThingy);
        }
    }

    // MODIFIES: pl
    // EFFECTS: parses thingy from JSON object and adds it to playlist
    private void addSong(PlayList pl, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String path = jsonObject.getString("path");
        String artist = jsonObject.getString("artist");
        String format = jsonObject.getString("format");
        double rating = jsonObject.getInt("rating");
        Song song = new Song(name, artist, format, path, rating);
        pl.addSong(song);
    }
}