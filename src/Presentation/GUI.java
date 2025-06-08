package Presentation;

import Business.CPU;
import Business.GPU;
import Business.HardwareComponent;
import Business.HardwareService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GUI extends JFrame {
    private final HardwareService service;
    private final JTextArea displayArea;

    public GUI() {
        service = new HardwareService();
        service.loadData("hardware.bin");

        setTitle("Hardware Comparator");
        setSize(1200, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //GIF
        ImageIcon gifIcon = new ImageIcon(getClass().getResource("/Presentation/GIF.gif"));
        JLabel imageLabel = new JLabel(gifIcon);
        JPanel imagePanel = new JPanel();
        imagePanel.add(imageLabel);

        // Display area
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        add(scrollPane, BorderLayout.CENTER);
        add(imagePanel, BorderLayout.NORTH);

        // Buttons
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));
        panel.setBackground(Color.LIGHT_GRAY);
        panel.setOpaque(true);


        panel.setLayout(new GridLayout(4, 2, 10, 10));

        //All Buttons
        JButton viewAllButton = new JButton("View All");
        viewAllButton.setBackground(Color.RED);
        viewAllButton.setForeground(Color.WHITE);
        viewAllButton.addActionListener(e -> viewAll());
        panel.add(viewAllButton);
        addButton(panel, "Add Component", e -> addComponent());
        addButton(panel, "Sort by Performance", e -> sortComponents());
        addButton(panel, "Compare Components", e -> compareComponents());
        addButton(panel, "Suggest Upgrade", e -> suggestUpgrade());
        addButton(panel, "Edit Component", e -> editComponent());
        addButton(panel, "Delete Component", e -> deleteComponent());
        addButton(panel, "Search by Cache/Power", e -> searchByAttribute());
        addButton(panel, "Exit", e -> System.exit(0));

        add(panel, BorderLayout.WEST);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI::new);
    }

    //Add Buttons
    private void addButton(JPanel panel, String title, java.awt.event.ActionListener action) {
        JButton button = new JButton(title);
        button.addActionListener(action);
        panel.add(button);
    }

    //View All Components
    private void viewAll() {
        displayArea.setText("");
        for (HardwareComponent hc : service.getAllComponents()) {
            displayArea.append(hc.toString() + "\n");
        }
    }

    //Add a Component
    private void addComponent() {
        String name = JOptionPane.showInputDialog(this, "Component Name:");
        if (name == null || name.isBlank()) return;

        String type = JOptionPane.showInputDialog(this, "Type (CPU/GPU):");
        if (type == null || (!type.equalsIgnoreCase("CPU") && !type.equalsIgnoreCase("GPU"))) {
            JOptionPane.showMessageDialog(this, "Invalid component type.");
            return;
        }

        double clock;

        while (true) {
            try {
                clock = Double.parseDouble(JOptionPane.showInputDialog(this, "Clock Speed (GHz):"));
                break;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid number.");
            }
        }

        int cache;

        while (true) {
            try {
                cache = Integer.parseInt(JOptionPane.showInputDialog(this, "Cache (MB):"));
                break;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid number.");
            }
        }

        int power;

        while (true) {
            try {
                power = Integer.parseInt(JOptionPane.showInputDialog(this, "Power (W):"));
                break;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid number.");
            }
        }

        HardwareComponent newComponent;
        if (type.equalsIgnoreCase("CPU")) {
            newComponent = new CPU(name, clock, cache, power);
        } else {
            newComponent = new GPU(name, clock, cache, power);
        }

        service.addComponent(newComponent);
        service.saveData("hardware.bin");
        JOptionPane.showMessageDialog(this, "Component added.");
    }

    //Sort Components
    private void sortComponents() {
        service.sortByPerformanceAscending();
        viewAll();
    }

    //Compare Components
    private void compareComponents() {
        String name1 = JOptionPane.showInputDialog(this, "First component name:");
        String name2 = JOptionPane.showInputDialog(this, "Second component name:");

        HardwareComponent c1 = service.findByName(name1);
        HardwareComponent c2 = service.findByName(name2);

        if (c1 == null || c2 == null) {
            JOptionPane.showMessageDialog(this, "One or both components not found.");
            return;
        }
        displayArea.setText(service.compare(c1, c2));
    }

    //Suggest an Upgrade
    private void suggestUpgrade() {
        String cpuName = JOptionPane.showInputDialog(this, "Enter CPU name:");
        String gpuName = JOptionPane.showInputDialog(this, "Enter GPU name:");

        HardwareComponent cpu = service.findByName(cpuName);
        HardwareComponent gpu = service.findByName(gpuName);

        if (cpu == null || gpu == null) {
            JOptionPane.showMessageDialog(this, "One or both components not found.");
            return;
        }
        displayArea.setText(service.suggestUpgrade(cpu, gpu));
    }

    //Edit Component
    private void editComponent() {
        String name = JOptionPane.showInputDialog(this, "Enter name of component to edit:");
        HardwareComponent comp = service.findByName(name);
        if (comp == null) {
            JOptionPane.showMessageDialog(this, "Component not found.");
            return;
        }

        double clock;

        while (true) {
            try {
                clock = Double.parseDouble(JOptionPane.showInputDialog(this, "Clock Speed (GHz):"));
                break;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid number.");
            }
        }

        int cache;

        while (true) {
            try {
                cache = Integer.parseInt(JOptionPane.showInputDialog(this, "Cache (MB):"));
                break;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid number.");
            }
        }

        int power;

        while (true) {
            try {
                power = Integer.parseInt(JOptionPane.showInputDialog(this, "Power (W):"));
                break;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid number.");
            }
        }

        comp.setClockSpeed(clock);
        comp.setCache(cache);
        comp.setPower(power);

        service.saveData("hardware.bin");
        service.txtFile();
        JOptionPane.showMessageDialog(this, "Component updated.");
    }

    //Delete a Component
    private void deleteComponent() {
        String name = JOptionPane.showInputDialog(this, "Enter name of component to delete:");
        HardwareComponent comp = service.findByName(name);
        if (comp == null) {
            JOptionPane.showMessageDialog(this, "Component not found.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            service.getAllComponents().remove(comp);
            service.saveData("hardware.bin");
            service.txtFile();
            JOptionPane.showMessageDialog(this, "Component deleted.");
        }
    }

    private void searchByAttribute() {
        String[] options = {"Cache", "Power"};
        String choice = (String) JOptionPane.showInputDialog(
                this,
                "Search by:",
                "Search",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );
        if (choice == null) return;

        String prompt;
        if (choice.equals("Cache")) {
            prompt = "Enter cache size (MB):";
        } else {
            prompt = "Enter power (W):";
        }
        String input = JOptionPane.showInputDialog(this, prompt);
        if (input == null) return;


        int value;

        try {
            value = Integer.parseInt(input);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid number.");
            return;
        }

        List<HardwareComponent> results;
        if (choice.equals("Cache")) {
            results = service.findByCache(value);
        } else {
            results = service.findByPower(value);
        }

        displayArea.setText("");
        if (results.isEmpty()) {
            displayArea.append("No components found for " + choice + " = " + value);
        } else {
            for (HardwareComponent hc : results) {
                displayArea.append(hc + "\n");
            }
        }
    }
}
