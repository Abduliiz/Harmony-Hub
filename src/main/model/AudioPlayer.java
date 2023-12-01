package model;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

// Represents an audio handler class that handles audios streams from local audio files
// that are strictly non MP3 files.
public class AudioPlayer {

    // to store current position
    private Long currentFrame;
    private Clip clip;
    // current status of clip
    private String status;
    private AudioInputStream audioInputStream;
    private String filePath;

    // constructor to initialize streams and clip
    public AudioPlayer(String filePath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        this.filePath = filePath;
        // create AudioInputStream object
        audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());

        // create clip reference
        clip = AudioSystem.getClip();
        // open audioInputStream to the clip
        clip.open(audioInputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    // Method to play the audio
    public void play(String path) {
        this.filePath = path;
        clip.start();
        status = "play";
        EventLog.getInstance().logEvent(new Event("Started Playing a Song"));

    }

    // Method to pause the audio
    public void pause() {
        EventLog.getInstance().logEvent(new Event("Song Paused"));
        if (status.equals("paused")) {
            System.out.println("Song is already paused");
        } else {
            this.currentFrame = this.clip.getMicrosecondPosition();
            clip.stop();
            status = "paused";
        }
    }

    // Method to resume the audio
    public void resumeAudio() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        EventLog.getInstance().logEvent(new Event("Song Resumed"));
        if (status.equals("play")) {
            System.out.println("Audio is already " + "being played");
        } else {
            clip.close();
            resetAudioStream();
            clip.setMicrosecondPosition(currentFrame);
            this.play(filePath);
        }
    }

    // Method to restart the audio
    public void restart() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        EventLog.getInstance().logEvent(new Event("Song Restarted"));
        clip.stop();
        clip.close();
        resetAudioStream();
        currentFrame = 0L;
        clip.setMicrosecondPosition(0);
        this.play(filePath);
    }

    // Method to stop the audio
    public void stop() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        EventLog.getInstance().logEvent(new Event("Song Stopped"));
        currentFrame = 0L;
        clip.stop();
        clip.close();
    }

    // Method to jump over a specific part
    public void jump() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        long minutes = (clip.getMicrosecondLength() / 1000000) / 60;
        long seconds = (clip.getMicrosecondLength() / 1000000) - minutes * 60;

        System.out.println("Enter time (" + 0 + ", " + minutes + ":" + seconds + "s)");
        Scanner sc = new Scanner(System.in);

        long c = sc.nextLong();
        if (c > 0 && c < clip.getMicrosecondLength() / 1000000) {
            clip.stop();
            clip.close();
            resetAudioStream();
            currentFrame = c * 1000000;
            clip.setMicrosecondPosition(currentFrame);
            this.play(filePath);
        }
    }

    // Method to reset audio stream
    public void resetAudioStream() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        clip.open(audioInputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

}