package model;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

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




    // Work as the user enters his choice

    public void gotoChoice(int c)
            throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        switch (c) {
            case 1:
                pause();
                break;
            case 2:
                resumeAudio();
                break;
            case 3:
                restart();
                break;
            case 4:
                stop();
                break;
            case 5:
                jump();
                break;

        }

    }

    // Method to play the audio
    public void play(String path) {
        this.filePath = path;
        System.out.println("1. pause");
        System.out.println("2. resume");
        System.out.println("3. restart");
        System.out.println("4. stop");
        System.out.println("5. Jump to specific time");

        clip.start();
        status = "play";
    }

    // Method to pause the audio
    public void pause() {
        if (status.equals("paused")) {
            System.out.println("Song is already paused");
        } else {
            System.out.println("Song has been paused");
            this.currentFrame = this.clip.getMicrosecondPosition();
            clip.stop();
            status = "paused";
        }

    }

    // Method to resume the audio
    public void resumeAudio() throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        if (status.equals("play")) {
            System.out.println("Audio is already " + "being played");
        } else {
            System.out.println("Song has been resumed");
            clip.close();
            resetAudioStream();
            clip.setMicrosecondPosition(currentFrame);
            this.play(filePath);
        }

    }

    // Method to restart the audio
    public void restart() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        System.out.println("Song has been restarted!");
        clip.stop();
        clip.close();
        resetAudioStream();
        currentFrame = 0L;
        clip.setMicrosecondPosition(0);
        this.play(filePath);
    }

    // Method to stop the audio
    public void stop() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        System.out.println("Song has been Stopped");

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


