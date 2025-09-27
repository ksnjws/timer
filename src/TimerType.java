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

    public static TimerType fromInt(int i) {
        for (TimerType timerType : TimerType.values()) {
            if (timerType.getInt() == i) {
                return timerType;
            }
        }
        throw new IllegalArgumentException("Invalid timer type " + i);
    }
}
