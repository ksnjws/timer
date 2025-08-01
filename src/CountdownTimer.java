import javax.swing.*;
import java.awt.*;

public class CountdownTimer extends TimerApplication {
    private JLabel countdownTimeLabel;

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

        // Get user input on hour/minute/second for countdown timer
        JTextField hourInput = new JTextField(2);
        JTextField minuteInput = new JTextField(2);
        JTextField secondInput = new JTextField(2);

        // Creating new panel for countdown time input
        JPanel inputPanel = new JPanel();
        JButton setTimeButton = new JButton("Set Time");

        // Adding input fields for hour/minute/second and set time button onto GUI
        inputPanel.add(new JLabel("Hours:"));
        inputPanel.add(hourInput);
        inputPanel.add(new JLabel("Minutes:"));
        inputPanel.add(minuteInput);
        inputPanel.add(new JLabel("Seconds:"));
        inputPanel.add(secondInput);
        inputPanel.add(setTimeButton);

        // Adding input panel to the countdown panel
        countdownPanel.add(inputPanel, BorderLayout.NORTH);

        // Creating new panel for countdown buttons
        JPanel countdownButtons = new JPanel();
        JButton startCountdownButton = new JButton("Start");
        JButton pauseCountdownButton = new JButton("Pause");
        JButton stopCountdownButton = new JButton("Stop & Save");
        JButton resetCountdownButton = new JButton("Reset");

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

    }

    public void startCountdown() {

    }

    public void pauseCountdown() {

    }

    public void stopCountdown() {

    }

    public void resetCountdown() {

    }

    public void updateCountdownTimer() {

    }
}
