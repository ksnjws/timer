import javax.swing.*;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;

public class TimerApplication extends JFrame {
    protected int hour; // hour field for timer application
    protected int minute; // minute field for timer
    protected int second; // second field for timer
    protected TimerType timerType;

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
        CountdownTimer countdownTimer = new CountdownTimer(hour, minute, second);

        tabbedPane.addTab("Clock", clock.createClockPanel());
        tabbedPane.addTab("Stopwatch", stopwatch.createStopwatchPanel());
        tabbedPane.addTab("Countdown", countdownIcon, countdownTimer.createCountdownPanel());

        add(tabbedPane);
    }

    public TimerApplication(int hour, int minute, int second){
        this.hour = hour;
        this.minute = minute;
        this.second = second;

        clockTimer = new javax.swing.Timer(1000, e -> updateClock());
        clockTimer.start();

        clockLabel = new JLabel("", SwingConstants.CENTER);
        clockLabel.setFont(new Font("Monospaced", Font.PLAIN, 40));
    }

    public enum TimerType { // using enum to store timer type when stored into a txt file
        COUNTDOWN(0),
        STOPWATCH(1); // setting integer values for each timertype

        private final int i; // using i to store the integer values

        TimerType(int i) {
            this.i = i;
        }

        int getInt() { // method to retrieve timertype integer value
            return i;
        }
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
