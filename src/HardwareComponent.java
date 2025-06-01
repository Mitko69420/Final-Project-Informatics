public class HardwareComponent {
    private String name;
    private String type; // CPU or GPU
    private double clockSpeedGHz;
    private int cacheMB;
    private int powerWatt;

    public HardwareComponent(String name, String type, double clockSpeedGHz, int cacheMB, int powerWatt) {
        this.name = name;
        this.type = type;
        this.clockSpeedGHz = clockSpeedGHz;
        this.cacheMB = cacheMB;
        this.powerWatt = powerWatt;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public double getClockSpeedGHz() {
        return clockSpeedGHz;
    }

    public int getCacheMB() {
        return cacheMB;
    }

    public int getPowerWatt() {
        return powerWatt;
    }

    @Override
    public String toString() {
        return name + " (" + type + ") - " + clockSpeedGHz + "GHz, " +
               cacheMB + "MB cache, " + powerWatt + "W";
    }
}
