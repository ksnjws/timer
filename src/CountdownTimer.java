import javax.swing.*;
import java.awt.*;

public class CountdownTimer extends TimerApplication {
    private JLabel countdownTimeLabel;
    private JSpinner hourSpinner, minuteSpinner, secondSpinner;
    private boolean timeSet;
    private boolean isRunning;
    private long totalMilliseconds;
    private JButton setTimeButton, startCountdownButton, pauseCountdownButton, stopCountdownButton, resetCountdownButton;


    public CountdownTimer(int hour, int minute, int second) {
        super(hour, minute, second);
    }

    public JPanel createCountdownPanel() {
        JPanel countdownPanel = new JPanel(new BorderLayout());
        countdownPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 25, 10));

        // Creating label to display countdown time
        countdownTimeLabel = new JLabel("00.00.00", SwingConstants.CENTER);
        countdownTimeLabel.setFont(new Font("Arial", Font.PLAIN, 40));
        countdownPanel.add(countdownTimeLabel, BorderLayout.CENTER);

        // Using spinner to get user input on countdown time and reduce rooms for error
        hourSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 23, 1));
        minuteSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));
        secondSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));

        // Creating new panel for countdown time input
        JPanel inputPanel = new JPanel();
        setTimeButton = new JButton("Set Time");

        // Adding input fields for hour/minute/second and set time button onto GUI
        inputPanel.add(new JLabel("Hours:"));
        inputPanel.add(hourSpinner);
        inputPanel.add(new JLabel("Minutes:"));
        inputPanel.add(minuteSpinner);
        inputPanel.add(new JLabel("Seconds:"));
        inputPanel.add(secondSpinner);
        inputPanel.add(setTimeButton);


        // Adding input panel to the countdown panel
        countdownPanel.add(inputPanel, BorderLayout.NORTH);

        // Creating new panel for countdown buttons
        JPanel countdownButtons = new JPanel();
        startCountdownButton = new JButton("Start");
        pauseCountdownButton = new JButton("Pause");
        stopCountdownButton = new JButton("Stop & Save");
        resetCountdownButton = new JButton("Reset");

        countdownButtons.add(startCountdownButton);
        countdownButtons.add(pauseCountdownButton);
        countdownButtons.add(stopCountdownButton);
        countdownButtons.add(resetCountdownButton);
        countdownPanel.add(countdownButtons, BorderLayout.SOUTH);

        // Linking buttons to corresponding countdown methods
        startCountdownButton.addActionListener(e -> startCountdown());
        pauseCountdownButton.addActionListener(e -> pauseCountdown());
        stopCountdownButton.addActionListener(e -> stopCountdown());
        resetCountdownButton.addActionListener(e -> resetCountdown());
        setTimeButton.addActionListener(e -> setCountdownTime());

        return countdownPanel;
    }

    public void setCountdownTime() {
        if (!timeSet) {
            // Assigning countdown time values from spinner inputs
            hour = (int) hourSpinner.getValue();
            minute = (int) minuteSpinner.getValue();
            second = (int) secondSpinner.getValue();

            // Converting user inputted hour/minute/second into milliseconds and converting the ints into longs
            totalMilliseconds = ((long) hour * 3600 * 1000 + (long) minute * 60 * 1000 + second * 1000L);

            setTimeButton.setEnabled(false);
            startCountdownButton.setEnabled(true);
            pauseCountdownButton.setEnabled(true);
            stopCountdownButton.setEnabled(true);
            resetCountdownButton.setEnabled(true);

            timeSet = true;
            updateCountdownTimer();
        }
    }

    public void startCountdown() {

    }

    public void pauseCountdown() {

    }

    public void stopCountdown() {

    }

    public void resetCountdown() {
        // pausing countdown
        isRunning = false;

        // Resetting all time values
        hour = 0;
        minute = 0;
        second = 0;
        totalMilliseconds = 0;
        hourSpinner.setValue(0);
        minuteSpinner.setValue(0);
        secondSpinner.setValue(0);
        updateCountdownTimer();

        // Disabling all buttons except for setTimeButton
        setTimeButton.setEnabled(true);
        startCountdownButton.setEnabled(false);
        pauseCountdownButton.setEnabled(false);
        stopCountdownButton.setEnabled(false);
        resetCountdownButton.setEnabled(false);

        timeSet = false;
    }

    public void updateCountdownTimer() {

    }
}
