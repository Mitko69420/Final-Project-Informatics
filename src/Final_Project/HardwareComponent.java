package Final_Project;

public abstract class HardwareComponent {
    private String name;
    private double clockSpeed;
    private int cache;
    private int power;

    public HardwareComponent(String s, String name, double clockSpeed, int cache, int power) {
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
