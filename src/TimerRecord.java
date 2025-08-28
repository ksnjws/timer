import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimerRecord {
    private LocalDateTime dateTime;
    private TimerType timerType;
    private String sessionTime;

    public TimerRecord(String dateTime, TimerType timerType, String sessionTime) {
        this.dateTime = LocalDateTime.parse(dateTime); // TODO use iso string to store date time
        this.timerType = timerType;
        this.sessionTime = sessionTime;
    }

    //public static TimerRecord[] sortRecords(TimerRecord[] timerRecords) {
    // TODO sort
    //}
}
