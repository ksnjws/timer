import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.FileWriter;


public class Stopwatch extends TimerApplication {
    public static final TimerType timerType = TimerType.STOPWATCH; // setting enum value for stopwatch
    private JButton startButton, stopButton, pauseButton, resetButton;
    private boolean isRunning;
    private long elapsedTime;
    private long stopwatchStartTime;
    private long totalSeconds;
    private JLabel stopwatchTimeLabel;
    private String dateTimeTracked;
    private String totalTrackedTime;
    private String formattedStopwatchTime;


    public Stopwatch(int hour, int minute, int second) {
        super(hour, minute, second);
    }

    public JPanel createStopwatchPanel() {
        JPanel stopwatchPanel = new JPanel(new BorderLayout());
        stopwatchPanel.setBorder(BorderFactory.createEmptyBorder(40, 10, 50, 10));

        // Creating label to display stopwatch elapsed time
        stopwatchTimeLabel = new JLabel("00.00.00", SwingConstants.CENTER);
        stopwatchTimeLabel.setFont(new Font("Arial", Font.PLAIN, 40));
        stopwatchPanel.add(stopwatchTimeLabel, BorderLayout.CENTER); // Add to the center of the panel

        // Creating panel for stopwatch buttons
        JPanel buttons = new JPanel();
        startButton = new JButton("Start"); // start tracking time
        pauseButton = new JButton("Pause"); //pause timer without saving data yet
        stopButton = new JButton("Stop & Save"); //save time tracked + reset stopwatch
        resetButton = new JButton("Reset"); // reset stopwatch without saving time tracked

        buttons.add(startButton);
        buttons.add(pauseButton);
        buttons.add(stopButton);
        buttons.add(resetButton);
        stopwatchPanel.add(buttons, BorderLayout.SOUTH);

        // Linking each button to corresponding method
        startButton.addActionListener(e -> startStopwatch());
        stopButton.addActionListener(e -> stopStopwatch());
        pauseButton.addActionListener(e -> pauseStopwatch());
        resetButton.addActionListener(e -> resetStopwatch());

        return stopwatchPanel;
    }

    private void startStopwatch() {
        if (!isRunning) {
            isRunning = true;

            // Enabling and disabling buttons
            startButton.setEnabled(false);
            stopButton.setEnabled(true);
            pauseButton.setEnabled(true);
            resetButton.setEnabled(true);

            stopwatchStartTime = System.currentTimeMillis() - elapsedTime; // allows stopwatch to resume if started previously

            Thread stopwatchThread = new Thread(new Runnable() { // new stopwatch thread for multitasking
                @Override // overriding run method in Runnable interface
                public void run() {
                    while (isRunning) {
                        elapsedTime = System.currentTimeMillis() - stopwatchStartTime; // elapsed time = currently tracked time minus start time
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                updateStopwatch(); // update stopwatch display without disrupting UI components
                            }
                        });
                        try {
                            Thread.sleep(1000); // sleep for 1000 milliseconds
                            //TODO SHOULD IT BE 1000 ms or 10 ms??
                        } catch (InterruptedException e) {
                            isRunning = false; // Pause stopwatch if interruption occurs
                            Thread.currentThread().interrupt();

                        }
                    }
                }
            });
            stopwatchThread.start();
        }
    }

    private void pauseStopwatch() {
        if (isRunning) {
            isRunning = false;

            pauseButton.setEnabled(false);
            startButton.setEnabled(true);
        }
    }

    private void stopStopwatch() {
        if (isRunning) {
            isRunning = false; // Pause the stopwatch if it is still running
            updateStopwatch(); // Update the stopwatch display one last time before saving it to the session log
        }

        // Setting up content to be saved to the session log
        LocalDateTime dateTimeTracked = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // CAN BE DELETED AFTER CHECKED
        //totalTrackedTime = formattedStopwatchTime;

        String stopwatchEntry = String.format("%s,%d,%d", dateTimeTracked.format(dateTimeFormatter), timerType.getInt(),totalSeconds);

        //String stopwatchEntry = String.format("Date/Time: %s | Timer Type: %s | Session Time: %s", dateTimeTracked.format(dateTimeFormatter), timerType, totalTrackedTime);

        // IO exception to write session information to session log
        try (FileWriter writer = new FileWriter("Timer-Log.txt", true)) {
            writer.write(stopwatchEntry + System.lineSeparator());
        } catch (IOException e) {
            System.err.println("Error saving session: " + e.getMessage()); // Output error message
        }

        stopButton.setEnabled(false);
        resetButton.setEnabled(false);
        pauseButton.setEnabled(false);
        startButton.setEnabled(true);

        // Resetting stopwatch display to 00.00.00
        hour = 0;
        minute = 0;
        second = 0;
        elapsedTime = 0;
        totalSeconds = 0;
        stopwatchStartTime = 0;

        updateStopwatch();
    }
        private void resetStopwatch() {
            isRunning = false;

            resetButton.setEnabled(false);
            startButton.setEnabled(true);

            // Resetting stopwatch display to 00.00.00
            hour = 0;
            minute = 0;
            second = 0;
            elapsedTime = 0;
            totalSeconds = 0;
            stopwatchStartTime = 0;

            updateStopwatch();
        }

    private void updateStopwatch() {
        totalSeconds = elapsedTime / 1000;

        // Display elapsed time using inherited hour/minute/second variables
        hour = (int) (totalSeconds / 3600);
        minute = (int) (totalSeconds / 60);
        second = (int) totalSeconds;

        // Setup for stopwatch display
        formattedStopwatchTime = String.format("%02d:%02d:%02d", hour, minute, second);
        stopwatchTimeLabel.setText(formattedStopwatchTime);


    }
}

