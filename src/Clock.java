import java.util.TimeZone;
import java.time.LocalTime;

public class Clock extends Timer {

    private String timezone;

    public Clock() {
        /**
         *
         */
        LocalTime currentTime = LocalTime.now(); // to retrieve system timezone for clock display
        int hour = currentTime.getHour();
        int minute = currentTime.getMinute();
        int second = currentTime.getSecond();

        super(hour, minute, second);
        this.timezone = TimeZone.getDefault().getID();
    }
}

