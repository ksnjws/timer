import javax.swing.*;

public class TimerApplication extends JFrame {
    private JTabbedPane tabbedPane;

    public TimerApplication() {
        tabbedPane = new JTabbedPane();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TimerApplication app = new TimerApplication();
            app.setVisible(true);
        });
    }

}
