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
    private JTabbedPane tabbedPane;
    private javax.swing.Timer clockTimer;
    public JLabel clockLabel;
    private SessionLogs sessionLogs;
    private boolean loginSuccessful = false;

    public TimerApplication() {
        this.hour = 0;
        this.minute = 0;
        this.second = 0;

        TimerRecord.readRecord();

        tabbedPane = new JTabbedPane();

        // use of selection to call showLogin(), only construct rest of TimerApplication UI if login is succssful
        if (!showLogin()) {
            dispose(); // dispose of window
            return;
        }

        setTitle("Timer Application");
        setSize(500,350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // instantiating tabs
        sessionLogs = new SessionLogs();
        Clock clock = new Clock(hour, minute, second);
        Stopwatch stopwatch = new Stopwatch(hour, minute, second, sessionLogs);
        CountdownTimer countdownTimer = new CountdownTimer(hour, minute, second, sessionLogs);

        tabbedPane.addTab("Clock", clock.createClockPanel());
        tabbedPane.addTab("Stopwatch", stopwatch.createStopwatchPanel());
        tabbedPane.addTab("Countdown", countdownTimer.createCountdownPanel());
        tabbedPane.addTab("Session logs", sessionLogs.createSessionLogsPanel());

        add(tabbedPane);
    }

    public TimerApplication(int hour, int minute, int second){
        this.hour = hour;
        this.minute = minute;
        this.second = second;

        clockTimer = new javax.swing.Timer(1000, e -> updateClock());
        clockTimer.start();

        clockLabel = new JLabel("", SwingConstants.CENTER);
        clockLabel.setFont(new Font("Arial", Font.PLAIN, 40));
    }

    // login boolean for login page display
    private boolean showLogin() {
        // set up of login window using JDialog
        JDialog loginWindow = new JDialog(this, "Login", true);
        loginWindow.setSize(500, 350);
        loginWindow.setLocationRelativeTo(this);

        JPanel loginPanel = new JPanel(new GridLayout(2,2));
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(e -> {
            String inputUsername = usernameField.getText();
            String inputPassword = passwordField.getText();

            // check if input equals the hardcoded user and password
            if (inputUsername.equals("User1") && inputPassword.equals("password")) {
                // set loginSuccessful as true if user and password are correct
                loginSuccessful = true;
                loginWindow.dispose();
            }
            else {
                // display error message in a message dialog
                JOptionPane.showMessageDialog(loginWindow, "Invalid username/password!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        loginPanel.add(new JLabel("Username: "));
        loginPanel.add(usernameField);
        loginPanel.add(new JLabel("Password: "));
        loginPanel.add(passwordField);

        loginWindow.add(loginPanel, BorderLayout.NORTH);
        loginWindow.add(loginButton, BorderLayout.SOUTH);

        loginWindow.setVisible(true);
        return loginSuccessful;
    }

    private void updateClock() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        clockLabel.setText(sdf.format(cal.getTime())); // displays time (Must be deleted)
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
