import javax.swing.*;
import java.awt.*;

public class SessionLogs extends JPanel {

    public JPanel createSessionLogsPanel() {
        JPanel sessionLogs = new JPanel(new BorderLayout());
        sessionLogs.setBorder(BorderFactory.createEmptyBorder(10, 10, 25, 10));


        JTextArea logDisplayArea = new JTextArea(20, 50);
        logDisplayArea.setEditable(false);
        logDisplayArea.setLineWrap(true);

        StringBuilder stringBuilder = new StringBuilder(); // string builder to loop through TimerRecords array and format into string to be displayed
        for (TimerRecord timerRecord : TimerRecord.timerRecords) {
            if (timerRecord == null) {
                break;
            }
            String record = String.format("Date: %s, %s seconds", timerRecord.getFormattedDateTime(), timerRecord.getSessionTime()); // formatting string
            stringBuilder.append(record).append("\n"); // add on new record onto the existing string builder and moving onto the next line
        }
        logDisplayArea.setText(stringBuilder.toString()); // convert string builder into a big string to be displayed in the text area

        JScrollPane displayPane = new JScrollPane(logDisplayArea); // placing the text area displaying all records into a scroll pane so it doesn't cover up the buttons
        sessionLogs.add(displayPane, BorderLayout.CENTER); // adding the scroll pane to the logs panel

        return sessionLogs;
    }
}
