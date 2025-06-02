package Final_Project;

public class CPU extends HardwareComponent {
    public CPU(String name, double clockSpeed, int cache, int power) {
        super(name, name, clockSpeed, cache, power);
    }

    @Override
    public String getType() {
        return "CPU";
    }
}
