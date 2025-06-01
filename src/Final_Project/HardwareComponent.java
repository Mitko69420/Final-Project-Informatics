package Final_Project;

public class HardwareComponent {
    private final String name;
    private final String type; // CPU or GPU
    private final double clockSpeed;
    private final int cache;
    private final int power;

    public HardwareComponent(String name, String type, double clockSpeed, int cache, int power) {
        this.name = name;
        this.type = type;
        this.clockSpeed = clockSpeed;
        this.cache = cache;
        this.power = power;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
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
        return name + " (" + type + ") - " + clockSpeed + "GHz, " +
                cache + "MB cache, " + power + "W";
    }
}
