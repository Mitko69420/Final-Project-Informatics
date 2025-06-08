package Business;

import Persistence.BinaryStorage;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class HardwareService {
    private final List<HardwareComponent> components = new ArrayList<>();

    //selection sorting algorithm
    public void sortByPerformanceAscending() {
        for (int i = 0; i < components.size() - 1; i++) {
            int minIndex = i;
            double minScore = components.get(i).getClockSpeed() * components.get(i).getCache();
            for (int j = i + 1; j < components.size(); j++) {
                double scoreJ = components.get(j).getClockSpeed() * components.get(j).getCache();
                if (scoreJ < minScore) {
                    minScore = scoreJ;
                    minIndex = j;
                }
            }

            if (minIndex != i) {
                HardwareComponent temp = components.get(i);
                components.set(i, components.get(minIndex));
                components.set(minIndex, temp);
            }
        }
        txtFile();
    }

    //Add Component
    public void addComponent(HardwareComponent hc) {
        for (HardwareComponent c : components) {
            if (c.getName().equalsIgnoreCase(hc.getName())) {
                System.out.println("Component already exists.");
                return;
            }
        }
        components.add(hc);
        txtFile();

    }

    //Search by Name of Component
    public HardwareComponent findByName(String name) {
        for (HardwareComponent hc : components) {
            if (hc.getName().equalsIgnoreCase(name)) {
                return hc;
            }
        }
        return null;
    }

    //reference to list
    public List<HardwareComponent> getAllComponents() {
        return components;
    }

    //Compare Components Logic
    public String compare(HardwareComponent h1, HardwareComponent h2) {
        if (h1 == null || h2 == null) return "Component(s) not found.";
        if (!h1.getType().equalsIgnoreCase(h2.getType())) return "Cannot compare different types (CPU vs GPU).";

        String result = "Comparing " + h1.getName() + " vs. " + h2.getName() + ":\n";

        double clockDiffRaw = Math.abs(h1.getClockSpeed() - h2.getClockSpeed());
        String clockDiff = String.format("%.1f", clockDiffRaw);
        int cacheDiff = Math.abs(h1.getCache() - h2.getCache());
        int powerDiff = Math.abs(h1.getPower() - h2.getPower());

        // Clock speed comparison
        if (h1.getClockSpeed() > h2.getClockSpeed()) {
            result += h1.getName() + " has higher clock speed (+" + clockDiff + " GHz).\n";
        } else if (h1.getClockSpeed() < h2.getClockSpeed()) {
            result += h2.getName() + " has higher clock speed (+" + clockDiff + " GHz).\n";
        } else {
            result += "Both have the same clock speed.\n";
        }

        // Cache Comparison Logic
        if (h1.getCache() > h2.getCache()) {
            result += h1.getName() + " has more cache (+" + cacheDiff + " MB).\n";
        } else if (h1.getCache() < h2.getCache()) {
            result += h2.getName() + " has more cache (+" + cacheDiff + " MB).\n";
        } else {
            result += "Both have the same cache.\n";
        }

        // Power Comparison Logic
        if (h1.getPower() < h2.getPower()) {
            result += h1.getName() + " is more power-efficient (−" + powerDiff + " W).\n";
        } else if (h1.getPower() > h2.getPower()) {
            result += h2.getName() + " is more power-efficient (−" + powerDiff + " W).\n";
        } else {
            result += "Both have the same power consumption.\n";
        }

        //Performance Score Logic
        double score1 = h1.getClockSpeed() * h1.getCache();
        double score2 = h2.getClockSpeed() * h2.getCache();

        String formatted1 = String.format("%.1f", score1);
        String formatted2 = String.format("%.1f", score2);

        if (score1 > score2) {
            result += h1.getName() + " is overall better (Score: " + formatted1 + " vs. " + formatted2 + ").\n";
        } else if (score2 > score1) {
            result += h2.getName() + " is overall better (Score: " + formatted2 + " vs. " + formatted1 + ").\n";
        } else {
            result += "Both have the same overall score (" + formatted1 + ").\n";
        }

        return result;
    }

    //Suggest an Upgrade Logic 30%
    public String suggestUpgrade(HardwareComponent cpu, HardwareComponent gpu) {
        if (cpu == null || gpu == null) {
            return "CPU or GPU not found.";
        }

        double cpuScore = cpu.getClockSpeed() * cpu.getCache();
        double gpuScore = gpu.getClockSpeed() * gpu.getCache();

        if (cpuScore > gpuScore * 1.3) {
            return "Your GPU is underpowered compared to your CPU. Upgrade GPU.";
        } else if (gpuScore > cpuScore * 1.3) {
            return "Your CPU is underpowered compared to your GPU. Upgrade CPU.";
        } else {
            return "Your system is balanced.";
        }
    }

    //Load File Components From File
    public void loadData(String filename) {
        List<HardwareComponent> loaded = BinaryStorage.loadFromFile(filename);
        if (loaded.isEmpty()) {
            System.out.println("No saved data—loading default components.");
            components.clear();
            components.addAll(getDefaultComponents());
            BinaryStorage.saveToFile(filename, components);
        } else {
            components.clear();
            components.addAll(loaded);
        }
    }

    //Pre=Saved Data
    private List<HardwareComponent> getDefaultComponents() {
        List<HardwareComponent> defaults = new ArrayList<>();
        defaults.add(new CPU("Ryzen 5 5600X", 3.7, 32, 65));
        defaults.add(new CPU("Intel i5-12400F", 2.5, 20, 65));
        defaults.add(new CPU("Intel i7-13700K", 3.4, 30, 125));
        defaults.add(new GPU("RTX 3060", 1.8, 12, 170));
        defaults.add(new GPU("RX 6700 XT", 2.4, 16, 230));
        defaults.add(new GPU("RX 7600", 2.6, 8, 165));
        return defaults;
    }


    public void saveData(String filename) {
        BinaryStorage.saveToFile(filename, components);
    }

    //Search Component by Cache
    public List<HardwareComponent> findByCache(int cacheSize) {
        List<HardwareComponent> result = new ArrayList<>();
        for (HardwareComponent c : components) {
            if (c.getCache() == cacheSize) {
                result.add(c);
            }
        }
        return result;
    }

    //Search Component by Power
    public List<HardwareComponent> findByPower(int powerWatt) {
        List<HardwareComponent> result = new ArrayList<>();
        for (HardwareComponent c : components) {
            if (c.getPower() == powerWatt) {
                result.add(c);
            }
        }
        return result;
    }

    //Make txt file report
    public void txtFile() {
        try (PrintWriter writer = new PrintWriter("performance_report.txt")) {
            writer.println("Hardware Performance Ranking");
            writer.println("Name | Type | Clock (GHz) | Cache (MB) | Power (W) | Score");
            writer.println("-------------------------------------------------------------");
            for (HardwareComponent hc : components) {
                double score = hc.getClockSpeed() * hc.getCache();
                writer.printf("%s | %s | %.2f | %d | %d | %.1f%n",
                        hc.getName(),
                        hc.getType(),
                        hc.getClockSpeed(),
                        hc.getCache(),
                        hc.getPower(),
                        score
                );
            }
        } catch (IOException e) {
            System.out.println("Error writing report: " + e.getMessage());
        }
    }

}
