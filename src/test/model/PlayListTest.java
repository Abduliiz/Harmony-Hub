package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayListTest {
    PlayList testPlaylist;
    Song testSong1;
    Song testSong2;
    Song testSong3;



    @BeforeEach
    void runBefore() {
        testPlaylist = new PlayList("NewList");
        testSong1 = new Song("run it up", "Tjay", "MP3", 1);
        testSong2 = new Song("idk", "some", "WAV", 4);
        testSong3 = new Song("huh", "one", "MP3", 10);


    }

    @Test
    void testConstructor() {
        assertEquals("NewList", testPlaylist.getName());
        assertTrue(testPlaylist.getSongs().isEmpty());
    }

    @Test
    void addSongsOneTime() {
        assertEquals(0, testPlaylist.getSongs().size());
        testPlaylist.addSong(testSong1);
        assertEquals(1, testPlaylist.getSongs().size());
        assertEquals("run it up", testPlaylist.getSongs().get(0).getName());
    }

    @Test
    void addSongsManyTimes() {
        testPlaylist.addSong(testSong1);
        testPlaylist.addSong(testSong1);
        testPlaylist.addSong(testSong2);
        assertEquals(3, testPlaylist.getSongs().size());
        assertEquals("idk", testPlaylist.getSongs().get(2).getName());
        assertEquals("run it up", testPlaylist.getSongs().get(1).getName());


    }

    @Test
    void displaySongs() {
        assertTrue(testPlaylist.getSongs().isEmpty());
    }
}