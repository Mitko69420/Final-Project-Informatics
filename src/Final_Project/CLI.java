package Final_Project;

import javax.swing.*;
import java.util.Scanner;

public class CLI {
    public static void main(String[] args) {

        System.out.println("Choose mode: 1. GUI  2. CLI");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        if (input.equals("1")) {
            SwingUtilities.invokeLater(GUI::new);
        } else if (input.equals("2")) {
            CLI.run();
        } else {
            System.out.println("Invalid choice. Exiting.");
        }
    }

    public static void run() {
        Scanner sc = new Scanner(System.in);
        HardwareDataManager manager = new HardwareDataManager();
        manager.loadFromFile("hardware.bin");

        while (true) {
            System.out.println("\n  Hardware Comparator    ");
            System.out.println("1. View All Components");
            System.out.println("2. Compare Components");
            System.out.println("3. Suggest Upgrade");
            System.out.println("4. Add Component");
            System.out.println("5. Exit");
            System.out.println("6. Sort Components by Performance");
            System.out.println("7. Edit");
            System.out.println("8. Delete");
            System.out.print("Choose an option: ");
            String input = sc.nextLine();

            int option = -1;

            try {
                option = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input");
            }

            switch (option) {
                case 1: {
                    for (HardwareComponent hc : manager.getComponents()) {
                        System.out.println(hc);
                    }
                    break;
                }

                case 2: {
                    System.out.print("Enter first component: ");
                    HardwareComponent h1 = manager.findByName(sc.nextLine());
                    System.out.print("Enter second component: ");
                    HardwareComponent h2 = manager.findByName(sc.nextLine());
                    System.out.println(HardwareAnalyzer.compare(h1, h2));
                    break;
                }

                case 3: {
                    System.out.print("Enter CPU: ");
                    HardwareComponent cpu = manager.findByName(sc.nextLine());
                    System.out.print("Enter GPU: ");
                    HardwareComponent gpu = manager.findByName(sc.nextLine());
                    System.out.println(HardwareAnalyzer.suggestUpgrade(cpu, gpu));
                    break;
                }

                case 4: {
                    System.out.print("Name: ");
                    String name = sc.nextLine();
                    System.out.print("Type (CPU/GPU): ");
                    String type = sc.nextLine();
                    double clock;
                    int cache;
                    int power;

                    //errors
                    try {
                        clock = InputValidator.getDoubleInput(sc, "Clock Speed (GHz): ");
                        cache = InputValidator.getIntInput(sc, "Cache (MB): ");
                        power = InputValidator.getIntInput(sc, "Power (Watt): ");
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input");
                        break;
                    }

                    HardwareComponent newComponent;
                    if (type.equalsIgnoreCase("CPU")) {
                        newComponent = new CPU(name, clock, cache, power);
                    } else if (type.equalsIgnoreCase("GPU")) {
                        newComponent = new GPU(name, clock, cache, power);
                    } else {
                        System.out.println("Invalid component type.");
                        break;
                    }


                    manager.addComponent(newComponent);
                    System.out.println("Added");
                    manager.saveToFile("hardware.bin");
                    break;
                }

                case 5: {
                    manager.saveToFile("hardware.bin");
                    System.out.println("Exiting");
                    sc.close();
                    return;
                }

                case 6: {
                    manager.sortByPerformanceDescending();
                    System.out.println("Sorted by performance:");
                    for (HardwareComponent hc : manager.getComponents()) {
                        System.out.println(hc);
                    }
                    break;
                }

                case 7: {
                    System.out.print("Enter name of component to edit: ");
                    String nameToEdit = sc.nextLine();
                    HardwareComponent comp = manager.findByName(nameToEdit);
                    if (comp == null) {
                        System.out.println("Component not found.");
                        break;
                    }

                    double newClock = InputValidator.getDoubleInput(sc, "New Clock Speed (GHz): ");
                    int newCache = InputValidator.getIntInput(sc, "New Cache (MB): ");
                    int newPower = InputValidator.getIntInput(sc, "New Power (W): ");

                    comp.setClockSpeed(newClock);
                    comp.setCache(newCache);
                    comp.setPower(newPower);

                    System.out.println("Updated");
                    manager.saveToFile("hardware.bin");
                    break;
                }

                case 8: {
                    System.out.print("Enter name of component to delete: ");
                    String name = sc.nextLine();
                    HardwareComponent toRemove = manager.findByName(name);

                    if (toRemove == null) {
                        System.out.println("Component not found");
                        break;
                    }

                    System.out.print("Sure? (yes/no): ");
                    String confirm = sc.nextLine();

                    if (confirm.equalsIgnoreCase("yes")) {
                        manager.getComponents().remove(toRemove);
                        System.out.println("Deleted");
                        manager.saveToFile("hardware.bin");
                    }
                    break;
                }

                default: {
                    if (option != -1) {
                        System.out.println("Invalid option");
                    }
                }
            }
        }
    }
}

