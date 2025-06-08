package Persistence;

import Business.HardwareComponent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BinaryStorage {

    public static void saveToFile(String filename, List<HardwareComponent> components) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(components);
            System.out.println("Saved: " + filename);
        } catch (IOException e) {
            System.out.println("Error writing: " + e.getMessage());
        }
    }

    public static List<HardwareComponent> loadFromFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = in.readObject();
            if (obj instanceof List<?>) {
                List<HardwareComponent> loaded = new ArrayList<>();
                for (Object o : (List<?>) obj) {
                    if (o instanceof HardwareComponent) {
                        loaded.add((HardwareComponent) o);
                    }
                }
                System.out.println("Loaded from binary file: " + filename);
                return loaded;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
