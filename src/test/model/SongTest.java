package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SongTest {
    Song testSong1;
    Song testSong2;
    Song testSong3;

    @BeforeEach
    void runBefore() {
        testSong1 = new Song("run it up", "Tjay", "MP3", "hi",1);
        testSong2 = new Song("idk", "some", "WAV", "who" ,4);
        testSong3 = new Song("huh", "one", ".MP3", "",10);
    }



    @Test
    void getName() {
        assertEquals("run it up",testSong1.getName());
        assertEquals("idk",testSong2.getName());

    }

    @Test
    void getRating() {
        assertEquals(1,testSong1.getRating());
        assertEquals(10,testSong3.getRating());

    }

    @Test
    void getArtist() {
        assertEquals("Tjay",testSong1.getArtist());
        assertEquals("one",testSong3.getArtist());

    }

    @Test
    void getFormat() {
        assertEquals(".MP3",testSong1.getFormat());
        assertEquals(".WAV",testSong2.getFormat());
        assertEquals(".MP3",testSong3.getFormat());

    }

    @Test
    void getPath() {
        assertEquals("hi",testSong1.getPath());
        assertEquals("who",testSong2.getPath());
        assertEquals("",testSong3.getPath());

    }
}