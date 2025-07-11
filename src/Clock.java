import java.util.TimeZone;
import java.time.LocalTime;

public class Clock extends Timer {

    private String timezone;

    public Clock() {
        /**
         * java.util.TimeZone and java.time.LocalTime are libraries imported to retrieve the system timezone and time of the device in order to display it into the Clock panel in the Timer application.
         */
        LocalTime currentTime = LocalTime.now(); // to retrieve system timezone for clock display
        int hour = currentTime.getHour();
        int minute = currentTime.getMinute();
        int second = currentTime.getSecond();

        super(hour, minute, second);
        this.timezone = TimeZone.getDefault().getID();

    }
}

