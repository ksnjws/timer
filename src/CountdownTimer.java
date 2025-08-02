import javax.swing.*;
import java.awt.*;

public class CountdownTimer extends TimerApplication {
    private JLabel countdownTimeLabel;
    private JSpinner hourSpinner, minuteSpinner, secondSpinner;
    private int countdownHour, countdownMinute, countdownSecond;
    private boolean timeSet;
    private boolean isRunning;
    private long totalSeconds;
    private JButton setTimeButton, startCountdownButton, pauseCountdownButton, stopCountdownButton, resetCountdownButton;
    private String timerType;
    private long timeRemaining;
    private String formattedCountdownTime;


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

        setTimeButton.setEnabled(true);
        startCountdownButton.setEnabled(false);
        pauseCountdownButton.setEnabled(false);
        stopCountdownButton.setEnabled(false);
        resetCountdownButton.setEnabled(false);
        return countdownPanel;
    }

    public void setCountdownTime() {
        if (!timeSet) {

            // Assigning original countdown time values to new variables from spinner inputs
            countdownHour = (int) hourSpinner.getValue();
            countdownMinute = (int) minuteSpinner.getValue();
            countdownSecond = (int) secondSpinner.getValue();

            // Converting user inputted hour/minute/second into milliseconds and converting the ints into longs
            totalSeconds = ((long) countdownHour * 3600 + (long) countdownMinute * 60 + (long) countdownSecond);

            // Setting the time remaining to equal to total seconds since updateCountdownTimer() relies on timeRemaining
            timeRemaining = totalSeconds;

            setTimeButton.setEnabled(false);
            startCountdownButton.setEnabled(true);
            resetCountdownButton.setEnabled(true);

            timeSet = true;
            updateCountdownTimer();
        }
    }

    public void startCountdown() {
        timerType = "Countdown Timer";

        if (!isRunning) {
            isRunning = true;

            timeRemaining = totalSeconds;

            startCountdownButton.setEnabled(false);
            stopCountdownButton.setEnabled(true);
            pauseCountdownButton.setEnabled(true);
            resetCountdownButton.setEnabled(true);

            Thread countdownThread = new Thread(new Runnable() { // countdown thread
                @Override //overriding run method in Runnable
                public void run() {
                    while (isRunning && timeRemaining >= 0) { // While loop
                        totalSeconds = timeRemaining; //Allows other methods using totalSeconds to update remaining time
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                updateCountdownTimer();
                            }
                        });
                        try {
                            Thread.sleep(1000); // sleep for 1000 milliseconds (1 second)
                            timeRemaining -= 1; // Decrease by 1 second every time the loop runs
                        } catch (InterruptedException e) {
                            isRunning = false; // Pause countdown if interruption occurs
                            Thread.currentThread().interrupt();
                        }
                    }
                    if (timeRemaining < 0) {
                        SwingUtilities.invokeLater(() -> {
                            autoResetCountdown(); // add method to reset countdown after countdown ends
                        });
                    }
                }

            });
            countdownThread.start();
        }
    }

    public void pauseCountdown() {

    }

    public void stopCountdown() {

    }

    public void resetCountdown() {
        // pausing countdown
        isRunning = false;

        // Resetting all time values
        countdownHour = 0;
        countdownMinute = 0;
        countdownSecond = 0;
        totalSeconds = 0;
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

    public void autoResetCountdown() { // autoreset method to automatically reset countdown timer after the countdown reaches 0
        isRunning = false;
        timeSet = false;

        // resetting all time values
        countdownHour = 0;
        countdownMinute = 0;
        countdownSecond = 0;
        totalSeconds = 0;
        timeRemaining = 0;
        hourSpinner.setValue(0);
        minuteSpinner.setValue(0);
        secondSpinner.setValue(0);

        // update countdown display after resetting
        updateCountdownTimer();

        // disable buttons except for setTimeButton
        setTimeButton.setEnabled(true);
        startCountdownButton.setEnabled(false);
        pauseCountdownButton.setEnabled(false);
        stopCountdownButton.setEnabled(false);
        resetCountdownButton.setEnabled(false);
    }

    public void updateCountdownTimer() {
        // Using inherited hour/minute/second variables to display remaining time
        hour = (int) (timeRemaining / 3600); //converting seconds to hour
        minute = (int) ((timeRemaining % 3600) / 60); // converting seconds to minutes
        second = (int) (timeRemaining % 60);

        // Setup format for countdown display
        formattedCountdownTime = String.format("%02d:%02d:%02d", hour, minute, second);
        countdownTimeLabel.setText(formattedCountdownTime);
    }
}
