import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.io.*;

public class TimerRecord {
    public static TimerRecord[] timerRecords = new TimerRecord[100];
    private LocalDateTime dateTime;
    private TimerType timerType;
    private long sessionTime;
    private String formattedDateTime;

    public TimerRecord(String dateTime, TimerType timerType, long sessionTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(Locale.US);
        this.dateTime = LocalDateTime.parse(dateTime);
        this.formattedDateTime = this.dateTime.format(formatter);
        this.timerType = timerType;
        this.sessionTime = sessionTime;
    }

    public LocalDateTime getDateTime(){
        return dateTime;
    }
    public TimerType getTimerType(){
        return timerType;
    }
    public long getSessionTime(){
        return sessionTime;
    }
    public String getFormattedDateTime(){
        return formattedDateTime;
    }

    public static void saveRecord(TimerType type, long sessionTime){
        // Setting up format that session information will be saved as in session log
        LocalDateTime dateTimeTracked = LocalDateTime.now();

        // more concise output in log separated by commas for bufferedreader to read
        String timerEntry = String.format("%s,%d,%d", dateTimeTracked.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), type.getInt(), sessionTime);

        try (FileWriter writer = new FileWriter("Timer-Log.txt", true)) {
            writer.write(timerEntry + System.lineSeparator());
        } catch (IOException e) {
            System.err.println("Error saving session: " + e.getMessage()); // Output error message
        }
    }

    protected static void readRecord() {
        try {
            FileReader fileReader = new FileReader("Timer-Log.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            try {
                String line;
                int i = 0;

                while ((line = bufferedReader.readLine()) != null) {
                    String[] parts = line.split(","); // splitting timerlog line

                    try {
                        // converting formats in timerRecord object
                        timerRecords[i] = new TimerRecord(parts[0], TimerType.fromInt(Integer.parseInt(parts[1])), Long.parseLong(parts[2].trim()));
                        i++;
                    } catch (Exception parseE) {
                        System.err.println("Parsing error");
                    }
                }

            } catch (IOException e) {
                System.out.println("IO error");
            }
        } catch (FileNotFoundException e){
            System.out.println("File not found");
        }
    }

    public static void clearHistory() {
        try (FileWriter filewriter = new FileWriter("Timer-Log.txt", false)) {
            filewriter.write("");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    // bubble sort to sort timerRecords array contents by date and time in descending order (from most recent)
    public static TimerRecord[] sortRecordsByDateTime(TimerRecord[] timerRecords) {
        for (int i = 0; i < timerRecords.length-1; i++) {
            for (int j = 0; j < timerRecords.length-1; j++) { // is this sorting correct
                if (timerRecords[j].getDateTime().isBefore(timerRecords[j+1].getDateTime())) {
                    TimerRecord temp = timerRecords[j];
                    timerRecords[j] = timerRecords[j+1];
                    timerRecords[j+1] = temp;
                }
            }

        }
        return timerRecords; // return sorted version of timerRecords
    }

    // bubble sort to sort timerRecords array contents by duration from longest to shortest
    public static TimerRecord[] sortRecordsByDuration(TimerRecord[] timerRecords) {
        // boolean?
        for (int i = 0; i < timerRecords.length-1; i++) {
            for (int j = 0; j < timerRecords.length-1; j++) {
                if (timerRecords[j].getSessionTime() > timerRecords[j+1].getSessionTime()) {
                    TimerRecord temp = timerRecords[j];
                    timerRecords[j] = timerRecords[j+1];
                    timerRecords[j+1] = temp;
                }
            }
        }
        return timerRecords;
    }

    //todo 20/1/26


    // linear search t operate search bar to search through saved TimerRecord objects
    public static TimerRecord[] linearSearch(TimerRecord[] timerRecords, String input) {

        // convert input to lowercase to prevent case sensitivity
        String lowercaseInput = input.toLowerCase();
        TimerRecord[] searchResults = new TimerRecord[timerRecords.length];
        int j = 0;

        for (int i = 0; i < timerRecords.length && timerRecords[i] != null; i++) {
            // record format so the search function can identify the format in the sessionlogs
            String recordFormat = String.format("Date: %s, Type: %s, %s seconds", timerRecords[i].getFormattedDateTime(), timerRecords[i].getTimerType(), timerRecords[i].getSessionTime());

            // checking for match and converting record to lowercase to prevent case sensitivity
            if (recordFormat.toLowerCase().contains(lowercaseInput)) {
                searchResults[j++] = timerRecords[i];
            }
        }
        TimerRecord[] displayResults = new TimerRecord[j];
        System.arraycopy(searchResults, 0, displayResults, 0, j);
        return displayResults;
    }

    // override toString() to prevent display of hashcode on timerRecords to enhance readability
    @Override
    public String toString() {
        return String.format("Date: %s, Type: %s, %s seconds", getFormattedDateTime(), getTimerType(), getSessionTime());
    }

}
