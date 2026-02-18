import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Clock extends TimerApplication {
    private javax.swing.Timer clockTimer;
    public JLabel clockLabel;

    public Clock(int hour, int minute, int second) {
        super(hour, minute, second);

        clockLabel = new JLabel("", SwingConstants.CENTER);
        clockLabel.setFont(new Font("Arial", Font.PLAIN, 40));

        clockTimer = new javax.swing.Timer(1000, e -> updateClock());
        clockTimer.start();
    }
    public JPanel createClockPanel() {
        JPanel clockPanel = new JPanel(new BorderLayout());
        clockPanel.setBorder(BorderFactory.createEmptyBorder(50, 10, 50, 10));

        JLabel headerLabel = new JLabel("Current Time", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.PLAIN, 18));

        clockPanel.add(headerLabel, BorderLayout.NORTH);
        clockPanel.add(clockLabel, BorderLayout.CENTER);

        updateClock(); // Initialize with current time

        return clockPanel;
    }

    private void updateClock() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        clockLabel.setText(sdf.format(cal.getTime())); // displays time

    }
}

