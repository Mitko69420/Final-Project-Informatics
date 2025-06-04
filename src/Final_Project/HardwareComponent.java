package Final_Project;

import java.io.*    ;

public abstract class HardwareComponent implements Serializable {
    private final String name;
    private final double clockSpeed;
    private final int cache;
    private final int power;

    public HardwareComponent(String name, double clockSpeed, int cache, int power) {
        this.name = name;
        this.clockSpeed = clockSpeed;
        this.cache = cache;
        this.power = power;
    }

    public abstract String getType();

    public String getName() {
        return name;
    }

    public double getClockSpeed() {
        return clockSpeed;
    }

    public int getCache() {
        return cache;
    }

    public int getPower() {
        return power;
    }

    @Override
    public String toString() {
        return name + " " + clockSpeed + "GHz, " + cache + "MB cache, " + power + "W";
    }
}
