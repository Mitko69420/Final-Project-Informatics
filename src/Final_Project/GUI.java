package Final_Project;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    private final HardwareDataManager manager;
    private final JTextArea displayArea;

    public GUI() {
        manager = new HardwareDataManager();
        manager.loadFromFile("hardware.bin");

        setTitle("Hardware Comparator");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Display area
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));

        addButton(panel, "View All", e -> viewAll());
        addButton(panel, "Add Component", e -> addComponent());
        addButton(panel, "Sort by Performance", e -> sortComponents());
        addButton(panel, "Compare Components", e -> compareComponents());
        addButton(panel, "Suggest Upgrade", e -> suggestUpgrade());
        addButton(panel, "Edit Component", e -> editComponent());
        addButton(panel, "Delete Component", e -> deleteComponent());
        addButton(panel, "Exit", e -> System.exit(0));

        add(panel, BorderLayout.NORTH);

        setVisible(true);
    }

    private void addButton(JPanel panel, String title, java.awt.event.ActionListener action) {
        JButton button = new JButton(title);
        button.addActionListener(action);
        panel.add(button);
    }

    private void viewAll() {
        displayArea.setText("");
        for (HardwareComponent hc : manager.getComponents()) {
            displayArea.append(hc.toString() + "\n");
        }
    }

    private void addComponent() {
        String name = JOptionPane.showInputDialog(this, "Component Name:");
        if (name == null || name.isBlank()) return;

        String type = JOptionPane.showInputDialog(this, "Type (CPU/GPU):");
        if (type == null || (!type.equalsIgnoreCase("CPU") && !type.equalsIgnoreCase("GPU"))) {
            JOptionPane.showMessageDialog(this, "Invalid component type.");
            return;
        }

        double clock = InputValidator.getDoubleInputDialog(this, "Clock Speed (GHz):");
        int cache = InputValidator.getIntInputDialog(this, "Cache (MB):");
        int power = InputValidator.getIntInputDialog(this, "Power (W):");

        HardwareComponent newComponent = type.equalsIgnoreCase("CPU") ?
                new CPU(name, clock, cache, power) :
                new GPU(name, clock, cache, power);

        manager.addComponent(newComponent);
        manager.saveToFile("hardware.bin");
        JOptionPane.showMessageDialog(this, "Component added.");
    }

    private void sortComponents() {
        manager.sortByPerformanceDescending();
        viewAll();
    }

    private void compareComponents() {
        String name1 = JOptionPane.showInputDialog(this, "First component name:");
        String name2 = JOptionPane.showInputDialog(this, "Second component name:");

        HardwareComponent c1 = manager.findByName(name1);
        HardwareComponent c2 = manager.findByName(name2);

        if (c1 == null || c2 == null) {
            JOptionPane.showMessageDialog(this, "One or both components not found.");
            return;
        }

        displayArea.setText(HardwareAnalyzer.compare(c1, c2));
    }

    private void suggestUpgrade() {
        String cpuName = JOptionPane.showInputDialog(this, "Enter CPU name:");
        String gpuName = JOptionPane.showInputDialog(this, "Enter GPU name:");

        HardwareComponent cpu = manager.findByName(cpuName);
        HardwareComponent gpu = manager.findByName(gpuName);

        if (cpu == null || gpu == null) {
            JOptionPane.showMessageDialog(this, "One or both components not found.");
            return;
        }

        displayArea.setText(HardwareAnalyzer.suggestUpgrade(cpu, gpu));
    }

    private void editComponent() {
        String name = JOptionPane.showInputDialog(this, "Enter name of component to edit:");
        HardwareComponent comp = manager.findByName(name);
        if (comp == null) {
            JOptionPane.showMessageDialog(this, "Component not found.");
            return;
        }

        double clock = InputValidator.getDoubleInputDialog(this, "New Clock Speed (GHz):");
        int cache = InputValidator.getIntInputDialog(this, "New Cache (MB):");
        int power = InputValidator.getIntInputDialog(this, "New Power (W):");

        comp.setClockSpeed(clock);
        comp.setCache(cache);
        comp.setPower(power);

        manager.saveToFile("hardware.bin");
        JOptionPane.showMessageDialog(this, "Component updated.");
    }

    private void deleteComponent() {
        String name = JOptionPane.showInputDialog(this, "Enter name of component to delete:");
        HardwareComponent comp = manager.findByName(name);
        if (comp == null) {
            JOptionPane.showMessageDialog(this, "Component not found.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            manager.getComponents().remove(comp);
            manager.saveToFile("hardware.bin");
            JOptionPane.showMessageDialog(this, "Component deleted.");
        }
    }
}
