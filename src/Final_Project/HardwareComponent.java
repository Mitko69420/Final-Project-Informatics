package Final_Project;

import java.io.Serializable;

public abstract class HardwareComponent implements Serializable {
    private final String name;
    private double clockSpeed;
    private int cache;
    private int power;

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

    public void setClockSpeed(double clockSpeed) {
        this.clockSpeed = clockSpeed;
    }

    public int getCache() {
        return cache;
    }

    public void setCache(int cache) {
        this.cache = cache;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }


    @Override
    public String toString() {
        return name + " " + clockSpeed + "GHz, " + cache + "MB cache, " + power + "W";
    }
}
