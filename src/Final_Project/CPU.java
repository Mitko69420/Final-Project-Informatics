package Final_Project;

import java.io.Serializable;

public class CPU extends HardwareComponent implements Serializable {
    public CPU(String name, double clockSpeed, int cache, int power) {
        super(name, clockSpeed, cache, power);
    }

    @Override
    public String getType() {
        return "CPU";
    }
}
