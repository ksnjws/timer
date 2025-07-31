import javax.swing.*;
import java.awt.*;

public class CountdownTimer extends TimerApplication {

    public CountdownTimer(int hour, int minute, int second) {
        super(hour, minute, second);
    }

    public JPanel createCountdownPanel() {
        JPanel countdownPanel = new JPanel(new BorderLayout());
        countdownPanel.setBorder(BorderFactory.createEmptyBorder(60, 10, 60, 10));

        JPanel buttons = new JPanel();

        return countdownPanel;
    }

    public void updateCountdownTimer() {

    }
}
