import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimerRecord {
    public static TimerRecord[] timerRecords = new TimerRecord[100];
    private LocalDateTime dateTime;
    private TimerType timerType;
    private String sessionTime;

    public TimerRecord(String dateTime, TimerType timerType, String sessionTime) {
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
    public String getSessionTime(){
        return sessionTime;
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

                        timerRecords[i] = new TimerRecord(parts[0], TimerType.fromInt(Integer.parseInt(parts[1])), parts[2]);
                        i++;
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
