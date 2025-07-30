import javax.swing.*;
import java.awt.*;

public class Stopwatch extends TimerApplication{
    private JLabel displayTime;
    private JButton startButton, stopButton, pauseButton, resetButton;
    private boolean isRunning;
    private long elapsedTime;
    private long startTime;
    private long totalSeconds;

    public Stopwatch(int hour, int minute, int second) {
        super(hour, minute, second);
    }

    public JPanel createStopwatchPanel(){
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

        startButton.addActionListener(e -> startStopwatch());
        stopButton.addActionListener(e -> stopStopwatch());
        pauseButton.addActionListener(e -> pauseStopwatch());
        resetButton.addActionListener(e -> resetStopwatch());

        return stopwatchPanel;
    }

    private void startStopwatch() {
        if (!isRunning) {
            isRunning = true;

            startTime = System.currentTimeMillis() - elapsedTime; // allows stopwatch to resume if started previously

            Thread stopwatchThread = new Thread(new Runnable() { // new stopwatch thread for multitasking
                @Override // overriding run method in Runnable interface
                public void run() {
                    while (isRunning) {
                        elapsedTime = System.currentTimeMillis() - startTime; // elapsed time = currently tracked time minus start time
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                updateStopwatch(); // update stopwatch display without disrupting UI components
                            }
                        });
                        try {
                            Thread.sleep(10); // sleep for 10 milliseconds
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();

                        }
                    }
                }
            });
            stopwatchThread.start();
        }
    }
    private void pauseStopwatch() {
        isRunning = false;
    }
    private void stopStopwatch() {
        isRunning = false;
    }
    private void resetStopwatch() {
        isRunning = false;
        elapsedTime = 0;
    }

    private void updateStopwatch() {

    }
}
