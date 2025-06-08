package Persistence;

import Business.HardwareComponent;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BinaryStorage {

    //Save to bin file
    public static void saveToFile(String filename, List<HardwareComponent> components) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(components);
            System.out.println("Saved: " + filename);
        } catch (IOException e) {
            System.out.println("Error writing: " + e.getMessage());
        }
    }

    //Load from bin file
    public static List<HardwareComponent> loadFromFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) return new ArrayList<>();

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (List<HardwareComponent>) in.readObject();
        } catch (Exception e) {
            System.out.println("Error loading file: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
