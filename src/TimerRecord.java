import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimerRecord {
    public static TimerRecord[] timerRecords = new TimerRecord[100];
    private LocalDateTime dateTime;
    private TimerType timerType;
    private long sessionTime;

    public TimerRecord(String dateTime, TimerType timerType, long sessionTime) {
        this.dateTime = LocalDateTime.parse(dateTime);
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

    //public static TimerRecord[] sortRecords(TimerRecord[] timerRecords) {
    // TODO sort
    //}
}
