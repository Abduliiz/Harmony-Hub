package persistence;


import model.PlayList;
import model.Song;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            PlayList pl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testWriterEmptyPlayList.json");
        try {
            PlayList pl = reader.read();
            assertEquals("My playlist", pl.getName());
            assertEquals(0, pl.getPlayListSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralPlayList.json");
        try {
            PlayList pl = reader.read();
            assertEquals("My playlist", pl.getName());
            List<Song> songs = pl.getSongs();
            assertEquals(2, songs.size());
            checkThingy("lovely", pl.getSongs().get(0));
            checkThingy("inneed", pl.getSongs().get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}