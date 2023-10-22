package persistence;

import model.PlayList;
import model.Song;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            PlayList pl = new PlayList("My playlist");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            PlayList pl = new PlayList("My playlist");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyPlayList.json");
            writer.open();
            writer.write(pl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyPlayList.json");
            pl = reader.read();
            assertEquals("My playlist", pl.getName());
            assertEquals(0, pl.getPlayListSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            PlayList pl = new PlayList("My playlist");
            pl.addSong(new Song("lovely", "Samer", "MP3", "test/s",10));
            pl.addSong(new Song("inneed", "Abdul", "MP3", "tets/song",6));
            JsonWriter writer = new JsonWriter("./data/testReaderGeneralPlayList.json");
            writer.open();
            writer.write(pl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testReaderGeneralPlayList.json");
            pl = reader.read();
            assertEquals("My playlist", pl.getName());
            List<Song> songs = pl.getSongs();
            assertEquals(2, songs.size());
            checkThingy("lovely", songs.get(0));
            checkThingy("inneed", songs.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}