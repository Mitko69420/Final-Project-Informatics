package Final_Project;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HardwareDataManager {
    private final List<HardwareComponent> components = new ArrayList<>();

    public void loadFromFile(String filename) {
        File file = new File(filename);

        //predvaritelni danni
        if (!file.exists()) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("Ryzen 5 5600X,CPU,3.7,32,65\n");
                writer.write("Intel i5-12400F,CPU,2.5,20,65\n");
                writer.write("Intel i7-13700K,CPU,3.4,30,125\n");
                writer.write("RTX 3060,GPU,1.8,12,170\n");
                writer.write("RX 6700 XT,GPU,2.4,16,230\n");
                writer.write("RX 7600,GPU,2.6,8,165\n");
                System.out.println("File not found. 'hardware.txt' was created with default components.");
            } catch (IOException e) {
                System.out.println("Failed to create hardware.txt: " + e.getMessage());
            }
        }
        //Reader
        try (Scanner scanner = new Scanner(file)) {
            components.clear();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 5 && !line.contains("null")) {
                    HardwareComponent hc = getHardwareComponent(parts);
                    if (hc != null) {
                        components.add(hc);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private static HardwareComponent getHardwareComponent(String[] parts) {
        String name = parts[0];
        String type = parts[1];
        double clock = Double.parseDouble(parts[2]);
        int cache = Integer.parseInt(parts[3]);
        int power = Integer.parseInt(parts[4]);
        HardwareComponent hc = null;
        if (type.equalsIgnoreCase("CPU")) {
            hc = new CPU(name, clock, cache, power);
        } else if (type.equalsIgnoreCase("GPU")) {
            hc = new GPU(name, clock, cache, power);
        }
        return hc;
    }

    //writer
    public void saveToFile(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (HardwareComponent hc : components) {
                writer.write(hc.getName() + "," + hc.getType() + "," +
                        hc.getClockSpeed() + "," + hc.getCache() + "," +
                        hc.getPower() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }


    //selection sorting algorithm
    public void sortByPerformanceDescending() {
        for (int i = 0; i < components.size() - 1; i++) {
            int maxIdx = i;
            double maxScore = components.get(i).getClockSpeed() * components.get(i).getCache();
            for (int j = i + 1; j < components.size(); j++) {
                double scoreJ = components.get(j).getClockSpeed() * components.get(j).getCache();
                if (scoreJ > maxScore) {
                    maxScore = scoreJ;
                    maxIdx = j;
                }
            }

            if (maxIdx != i) {
                HardwareComponent temp = components.get(i);
                components.set(i, components.get(maxIdx));
                components.set(maxIdx, temp);
            }
        }
    }


    public void addComponent(HardwareComponent hc) {
        for (HardwareComponent c : components) {
            if (c.getName().equalsIgnoreCase(hc.getName())) {
                System.out.println("Component with this name already exists.");
                return;
            }
        }
    components.add(hc);

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
