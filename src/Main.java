import javax.swing.*;

public class Main extends JFrame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TimerApplication app = new TimerApplication();
            app.setVisible(true);
        });
    }
}