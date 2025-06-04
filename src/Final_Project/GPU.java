package Final_Project;

import java.io.Serializable;

public class GPU extends HardwareComponent implements Serializable {
    public GPU(String name, double clockSpeed, int cache, int power) {
        super(name, clockSpeed, cache, power);
    }

    @Override
    public String getType() {
        return "GPU";
    }
}
