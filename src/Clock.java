import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.time.LocalTime;

public class Clock extends TimerApplication {

    private String timezone;

    public Clock(int hour, int minute, int second) {
        super(hour, minute, second);
        this.timezone = TimeZone.getDefault().getID();

    }
    public JPanel createClockPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(50, 10, 50, 10));

        JLabel headerLabel = new JLabel("Current Time", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));

        panel.add(headerLabel, BorderLayout.NORTH);
        panel.add(super.clockLabel, BorderLayout.CENTER);

        updateClock(); // Initialize with current time

        return panel;
    }

    private void updateClock() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        clockLabel.setText("123456");
    }
}

