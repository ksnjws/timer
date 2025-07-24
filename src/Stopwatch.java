import javax.swing.*;
import java.awt.*;

public class Stopwatch extends TimerApplication{
    private JLabel displayTime;
    private JButton startButton, stopButton, pauseButton, resetButton;
    private boolean isRunning;
    private long elapsedTime;


    public Stopwatch(int hour, int minute, int second) {
        super(hour, minute, second);

        JPanel stopwatchPanel = new JPanel(new BorderLayout());
        stopwatchPanel.setBorder(BorderFactory.createEmptyBorder(50, 10, 50, 10));


        JPanel buttons = new JPanel();
        startButton = new JButton("Start"); // start tracking time
        stopButton = new JButton("Stop"); //save time tracked + reset stopwatch
        pauseButton = new JButton("Pause"); //pause timer without saving data yet
        resetButton = new JButton("Reset"); // reset stopwatch without saving time tracked

        buttons.add(startButton);
        buttons.add(stopButton);
        buttons.add(pauseButton);
        buttons.add(resetButton);
        stopwatchPanel.add(buttons, BorderLayout.SOUTH);

    }

    private void startStopwatch() {
        isRunning = true;
    }
    private void pauseStopwatch() {
        isRunning = false;
    }
    private void stopStopwatch() {
        isRunning = false;
        long trackedTime = getElapsedTime / 10000
    }
}
