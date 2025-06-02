package Final_Project;

public class GPU extends HardwareComponent {
    public GPU(String name, double clockSpeed, int cache, int power) {
        super(name, name, clockSpeed, cache, power);
    }

    @Override
    public String getType() {
        return "GPU";
    }
}
