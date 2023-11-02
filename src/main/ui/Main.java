package ui;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            new PlayerApp();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}