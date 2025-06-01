package Final_Project;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HardwareDataManager {
    private List<HardwareComponent> components = new ArrayList<>();

    public void loadFromFile(String filename) {
        components.clear();
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 5 && !line.contains("null")) {
                    String name = parts[0];
                    String type = parts[1];
                    double clock = Double.parseDouble(parts[2]);
                    int cache = Integer.parseInt(parts[3]);
                    int power = Integer.parseInt(parts[4]);
                    HardwareComponent hc = new HardwareComponent(name, type, clock, cache, power);
                    components.add(hc);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public void saveToFile(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (HardwareComponent hc : components) {
                writer.write(hc.getName() + "," + hc.getType() + "," +
                             hc.getClockSpeedGHz() + "," + hc.getCacheMB() + "," +
                             hc.getPowerWatt() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public void addComponent(HardwareComponent hc) {
        if (hc != null) {
            components.add(hc);
        }
    }

    public HardwareComponent findByName(String name) {
        for (HardwareComponent hc : components) {
            if (hc.getName().equalsIgnoreCase(name)) {
                return hc;
            }
        }
        return null;
    }

    public List<HardwareComponent> getComponents() {
        return components;
    }
}
