import javax.swing.JFrame;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public abstract class Timer {

    private int hour; // hour field for timer application
    private int minute; // minute field for timer
    private int second; // second field for timer

    public Timer(int hour, int minute, int second){
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public void playSound(){
        try {
            File timerSound = new File("mixkit-interface-hint-notification-911.wav"); // Sound downloaded from mixkit.co
            AudioInputStream audio = AudioSystem.getAudioInputStream(timerSound);
            Clip clipPlayer = AudioSystem.getClip();
            clipPlayer.open(audio); // Load audio stream data
            clipPlayer.setFramePosition(0); // Set clip starting position to beginning
            clipPlayer.start(); // Play audio clip

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace(); // Log error to System.err

        }
    }


}
