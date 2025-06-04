package Final_Project;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HardwareDataManager {
    private final List<HardwareComponent> components = new ArrayList<>();

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

    public void loadFromFile(String filename) {


        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("File not found");
            //predvaritelni stoinosti
            components.clear();
            components.add(new CPU("Ryzen 5 5600X", 3.7, 32, 65));
            components.add(new CPU("Intel i5-12400F", 2.5, 20, 65));
            components.add(new CPU("Intel i7-13700K", 3.4, 30, 125));
            components.add(new GPU("RTX 3060", 1.8, 12, 170));
            components.add(new GPU("RX 6700 XT", 2.4, 16, 230));
            components.add(new GPU("RX 7600", 2.6, 8, 165));

            saveToFile(filename);
            return;
        }

        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            Object obj = in.readObject();
            in.close();

            if (obj instanceof java.util.List) {
                components.clear();
                for (Object o : (java.util.List<?>) obj) {
                    if (o instanceof HardwareComponent) {
                        components.add((HardwareComponent) o);
                    }
                }
                System.out.println("Loaded from binary file: " + filename);
            } else {
                System.out.println("Binary file is not valid.");
            }

        } catch (IOException e) {
            System.out.println("IO error: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found: " + e.getMessage());
        }

    }

    //writer
    public void saveToFile(String filename) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
            out.writeObject(components);
            out.close();
            System.out.println("Saved: " + filename);
        } catch (IOException e) {
            System.out.println("Error writing: " + e.getMessage());
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
                System.out.println("Component already exists.");
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
