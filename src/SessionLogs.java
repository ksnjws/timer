import javax.swing.*;
import java.awt.*;

public class SessionLogs extends JPanel {
    private JButton clearHistoryButton;
    private JButton sortByDateTimeButton;
    private JButton sortByDurationButton;
    private JTextArea logDisplayArea;

    public JPanel createSessionLogsPanel() {
        JPanel sessionLogs = new JPanel(new BorderLayout());
        sessionLogs.setBorder(BorderFactory.createEmptyBorder(10, 10, 25, 10));

        // SessionLogs text area setup
        logDisplayArea = new JTextArea(20, 70);
        logDisplayArea.setEditable(false);
        logDisplayArea.setLineWrap(true);

        // scroll pane to display many rows of records
        JScrollPane displayPane = new JScrollPane(logDisplayArea);
        sessionLogs.add(displayPane, BorderLayout.CENTER);

        // Instantiating searching components
        JPanel searchPanel = new JPanel(new BorderLayout());
        JTextField searchBar = new JTextField();

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> {
            // convert searchBar input into a string
            String input = searchBar.getText();

            // save the results found into searchResults TimerRecord array
            TimerRecord[] searchResults = TimerRecord.linearSearch(TimerRecord.timerRecords, input);

            if (searchResults.length > 0) {
                // converting searchResults content into a string using a string builder
                StringBuilder resultsBuilder = new StringBuilder();
                for (int i = 0; i < searchResults.length; i++) {
                    TimerRecord displayRecord = searchResults[i];
                    resultsBuilder.append(String.format("Date: %s, Type: %s, %s seconds\n", displayRecord.getFormattedDateTime(), displayRecord.getTimerType(), displayRecord.getSessionTime()));
                }
                // display in the logDisplayArea
                logDisplayArea.setText(resultsBuilder.toString());
            } else {
                logDisplayArea.setText("No results.");
            }
        });
        searchPanel.add(searchBar, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        // button panel containing sessionLogs panel buttons
        JPanel buttons = new JPanel();
        clearHistoryButton = new JButton("Clear History");
        clearHistoryButton.addActionListener(e -> {TimerRecord.clearHistory(); logDisplayArea.setText("");}); // immediately clear all text in logDisplayArea and also link button to clearHistory method in TimerRecord class

        sortByDateTimeButton = new JButton("Sort by date/time");
        sortByDateTimeButton.addActionListener(e -> {TimerRecord.sortByDateTime(TimerRecord.timerRecords); updateRecords();});

        sortByDurationButton = new JButton("Sort by duration");
        sortByDurationButton.addActionListener(e -> {TimerRecord.sortByDuration(TimerRecord.timerRecords); updateRecords();});

        buttons.add(clearHistoryButton);
        buttons.add(sortByDateTimeButton);
        buttons.add(sortByDurationButton);

        // top panel containing the upper components
        JPanel top = new JPanel(new BorderLayout());
        top.add(searchPanel, BorderLayout.NORTH);
        top.add(buttons, BorderLayout.SOUTH);

        sessionLogs.add(top, BorderLayout.NORTH);
        updateRecords();
        return sessionLogs;

    }

    public void updateRecords() {
        // string builder to loop through TimerRecords array and format into string to be displayed
        StringBuilder stringBuilder = new StringBuilder();
        for (TimerRecord timerRecord : TimerRecord.timerRecords) {
            if (timerRecord == null) {
                continue;
            }

            // format of how session record is displayed, including the session date and time, timer type, and session duration
            String record = String.format("Date: %s, Type: %s, %s seconds", timerRecord.getFormattedDateTime(), timerRecord.getTimerType(), timerRecord.getSessionTime());
            stringBuilder.append(record).append("\n"); // skips a line
        }
        // convert string builder into a big existing string to be displayed in the text area
        logDisplayArea.setText(stringBuilder.toString());

    }

}
