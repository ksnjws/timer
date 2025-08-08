import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CountdownTimer extends TimerApplication {
    private JLabel countdownTimeLabel;
    private JSpinner hourSpinner, minuteSpinner, secondSpinner;
    private int countdownHour, countdownMinute, countdownSecond;
    private boolean timeSet;
    private boolean isRunning;
    private long totalSeconds;
    private JButton setTimeButton, startCountdownButton, pauseCountdownButton, stopCountdownButton, resetCountdownButton;
    private long timeRemaining;
    private String formattedCountdownTime;
    private long timeCounted;
    private boolean logSaved; // flag to prevent session log saving twice


    public CountdownTimer(int hour, int minute, int second) {
        super(hour, minute, second);
        timerType = TimerType.COUNTDOWN; // setting enum value for countdown timer
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

        JPanel countdownRecord = new JPanel();
        String[] records = readRecord(); // To display usage history

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

            logSaved = false; // reset logSaved flag for new session

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
                    while (isRunning && timeRemaining > 0) { // While loop
                        try {
                            Thread.sleep(1000); // sleep for 1000 milliseconds (1 second)
                            timeRemaining -= 1; // Decrease by 1 second every time the loop runs

                            SwingUtilities.invokeLater(() -> updateCountdownTimer());
                        } catch (InterruptedException e) {
                            isRunning = false; // Pause countdown if interruption occurs
                            Thread.currentThread().interrupt();
                        }
                    }
                    SwingUtilities.invokeLater(() -> {
                        updateCountdownTimer();
                        stopCountdown(); // add method to reset countdown after countdown ends (when timeRemaining < 0)
                    });
                }
            });
            countdownThread.start();
        }
    }

    public void pauseCountdown() {

    }

    public void stopCountdown() {
        if (logSaved) {
            return; // exits method if log already saved
        }

        logSaved = true; // set it to true so it doesn't run twice

        if (isRunning) {
            isRunning = false; // pause countdown if it's still running
            updateCountdownTimer(); //update countdown display to see remaining time (could be 0, could be more than 0)
        }

        // disable buttons except for setTime button
        stopCountdownButton.setEnabled(false);
        pauseCountdownButton.setEnabled(false);
        resetCountdownButton.setEnabled(false);
        setTimeButton.setEnabled(true);

        // Setting up format that session information will be saved as in session log
        LocalDateTime dateTimeTracked = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Subtracting the timeRemaining from the original inputted totalSeconds to find the total duration of the session
        timeCounted = totalSeconds - timeRemaining;


        // CAN BE DELETED AFTER CHECKED
        // using variables to save the duration of the session in hours, minutes, and seconds
        // int savedHours = (int) (timeCounted / 3600); // converting seconds to hours
        // int savedMinutes = (int) ((timeCounted % 3600) / 60); // subtracting hours from minutes
        // int savedSeconds = (int) (timeCounted % 60); // display seconds after subtracting hours and minutes
        // Formatting countdown session log display
        // String timeSaved = String.format("%02d:%02d:%02d", savedHours, savedMinutes, savedSeconds);

        // more concise output in log separated by commas for bufferedreader to read
        String countdownEntry = String.format("%s,%d,%d", dateTimeTracked.format(dateTimeFormatter), timerType.getInt(),timeCounted);

        // IO exception to write session information to session log
        try (FileWriter writer = new FileWriter("Timer-Log.txt", true)) {
            writer.write(countdownEntry + System.lineSeparator());
        } catch (IOException e) {
            System.err.println("Error saving session: " + e.getMessage()); // Output error message
        }

        // Resetting countdown display and time values to 00.00.00
        countdownHour = 0;
        countdownMinute = 0;
        countdownSecond = 0;
        totalSeconds = 0;
        timeRemaining = 0;
        hourSpinner.setValue(0);
        minuteSpinner.setValue(0);
        secondSpinner.setValue(0);
        timeSet = false;
        isRunning = false;

        updateCountdownTimer();
    }

    public void resetCountdown() {
        // pausing countdown
        isRunning = false;

        // Resetting all time values
        countdownHour = 0;
        countdownMinute = 0;
        countdownSecond = 0;
        totalSeconds = 0;
        timeRemaining = 0;
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
        // Using inherited hour/minute/second variables to display remaining time

        // Preventing any negatives in the hour/minute/second display using math.max
        hour = (int) (Math.max(timeRemaining, 0) / 3600); // converting seconds to hours
        minute = (int) ((Math.max(timeRemaining, 0) % 3600) / 60); // display minutes left after subtracting hours
        second = (int) (Math.max(timeRemaining, 0) % 60); // display seconds after subtracting minutes and hours

        // Setup format for countdown display
        formattedCountdownTime = String.format("%02d:%02d:%02d", hour, minute, second);
        countdownTimeLabel.setText(formattedCountdownTime);
    }

    private static String[] readRecord() {
        try {
            FileReader fileReader = new FileReader("Timer-Log.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            try {
                String line;
                String[] records = new String[100];
                int i = 0;

                while ((line = bufferedReader.readLine()) != null) {
                     records[i] = line;
                     i++;
                }
                return records;

            } catch (IOException e) {
                System.out.println("IO error");
                return new String[100]; // return empty array
            }
        } catch (FileNotFoundException e){
            System.out.println("File not found");
            return new String[100];
        }
    }
}
