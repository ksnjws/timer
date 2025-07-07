import javax.swing.JFrame;

public abstract class Timer {

    private int hour; // hour field for timer application
    private int minute; // minute field for timer
    private int second; // second field for timer

    public Timer(int hour, int minute, int second){
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }
}
