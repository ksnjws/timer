public enum TimerType {
    COUNTDOWN(0),
    STOPWATCH(1); // setting integer values for each timertype

    private final int i; // using i to store the integer values

    TimerType(int i) {
        this.i = i;
    }

    int getInt() { // method to retrieve timertype integer value
        return i;
    }
}
