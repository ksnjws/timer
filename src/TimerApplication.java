import javax.swing.*;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;

public class TimerApplication extends JFrame {
    private int hour; // hour field for timer application
    private int minute; // minute field for timer
    private int second; // second field for timer

    private JTabbedPane tabbedPane;

    private javax.swing.Timer clockTimer;
    public JLabel clockLabel;


    public TimerApplication() {
        this.hour = 0;
        this.minute = 0;
        this.second = 0;

        tabbedPane = new JTabbedPane();

        setTitle("Timer Application");
        setSize(500,350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        ImageIcon clockIcon = new ImageIcon(); // image file for tab icons
        ImageIcon stopwatchIcon = new ImageIcon();
        ImageIcon countdownIcon = new ImageIcon();

        Clock clock = new Clock(hour, minute, second);
        Stopwatch stopwatch = new Stopwatch(hour, minute, second);

        tabbedPane.addTab("Clock", clockIcon, clock.createClockPanel());
        tabbedPane.addTab("Stopwatch", stopwatchIcon, stopwatch.createStopwatchPanel());
        // tabbedPane.addTab("Countdown", countdownIcon, createCountdownPanel);

        add(tabbedPane);
    }

    public TimerApplication(int hour, int minute, int second){
        this.hour = hour;
        this.minute = minute;
        this.second = second;

        clockTimer = new javax.swing.Timer(1000, e -> updateClock());
        clockTimer.start();

        clockLabel = new JLabel("", SwingConstants.CENTER);
        clockLabel.setFont(new Font("Arial", Font.BOLD, 40));
    }
    private void updateClock() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        clockLabel.setText(sdf.format(cal.getTime())); // displays time (Must be deleted)
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

    public static void main(String[] args) {
        LocalTime currentTime = LocalTime.now(); // to retrieve system timezone for clock display
        int hour = currentTime.getHour();
        int minute = currentTime.getMinute();
        int second = currentTime.getSecond();

        SwingUtilities.invokeLater(() -> {
            TimerApplication app = new TimerApplication();
            app.setVisible(true);
        });
    }

}
