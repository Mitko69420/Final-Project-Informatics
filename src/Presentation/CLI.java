package Presentation;

import Business.CPU;
import Business.GPU;
import Business.HardwareComponent;
import Business.HardwareService;

import javax.swing.*;
import java.util.List;
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

    //CLI
    public static void run() {
        Scanner sc = new Scanner(System.in);
        HardwareService service = new HardwareService();
        service.loadData("hardware.bin");

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
            System.out.println("9. Search by Cache or Power");
            System.out.print("Choose an option: ");
            String input = sc.nextLine();

            int option = -1;

            try {
                option = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input");
            }
            //View All
            switch (option) {
                case 1: {
                    for (HardwareComponent hc : service.getAllComponents()) {
                        System.out.println(hc);
                    }
                    break;
                }
                //Compare components
                case 2: {
                    System.out.print("Enter first component: ");
                    HardwareComponent h1 = service.findByName(sc.nextLine());
                    System.out.print("Enter second component: ");
                    HardwareComponent h2 = service.findByName(sc.nextLine());
                    System.out.println(service.compare(h1, h2));
                    break;
                }
                //Suggest Upgrade
                case 3: {
                    System.out.print("Enter CPU: ");
                    HardwareComponent cpu = service.findByName(sc.nextLine());
                    System.out.print("Enter GPU: ");
                    HardwareComponent gpu = service.findByName(sc.nextLine());
                    System.out.println(service.suggestUpgrade(cpu, gpu));
                    break;
                }
                //Add Component
                case 4: {
                    System.out.print("Name: ");
                    String name = sc.nextLine();
                    System.out.print("Type (CPU/GPU): ");
                    String type = sc.nextLine();
                    double clock;

                    while (true) {
                        System.out.print("Clock Speed (GHz): ");
                        try {
                            clock = Double.parseDouble(sc.nextLine());
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid number. Try again.");
                        }
                    }

                    int cache;
                    while (true) {
                        System.out.print("Cache (MB): ");
                        try {
                            cache = Integer.parseInt(sc.nextLine());
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid number. Try again.");
                        }
                    }

                    int power;
                    while (true) {
                        System.out.print("Power (W): ");
                        try {
                            power = Integer.parseInt(sc.nextLine());
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid number. Try again.");
                        }
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


                    service.addComponent(newComponent);
                    System.out.println("Added");
                    service.saveData("hardware.bin");
                    service.txtFile();
                    break;
                }
                //Exit and Save
                case 5: {
                    service.saveData("hardware.bin");
                    service.txtFile();
                    System.out.println("Exiting");
                    sc.close();
                    return;
                }
                //Sort by Performance
                case 6: {
                    service.sortByPerformanceDescending();
                    System.out.println("Sorted by performance:");
                    for (HardwareComponent hc : service.getAllComponents()) {
                        System.out.println(hc);
                    }
                    break;
                }
                //Edit Component
                case 7: {
                    System.out.print("Enter name of component to edit: ");
                    String nameToEdit = sc.nextLine();
                    HardwareComponent comp = service.findByName(nameToEdit);
                    if (comp == null) {
                        System.out.println("Component not found.");
                        break;
                    }

                    double newClock;
                    while (true) {
                        System.out.print("New Clock Speed (GHz): ");
                        try {
                            newClock = Double.parseDouble(sc.nextLine());
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid number. Try again.");
                        }
                    }

                    int newCache;
                    while (true) {
                        System.out.print("New Cache (MB): ");
                        try {
                            newCache = Integer.parseInt(sc.nextLine());
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid number. Try again.");
                        }
                    }

                    int newPower;
                    while (true) {
                        System.out.print("New Power (W): ");
                        try {
                            newPower = Integer.parseInt(sc.nextLine());
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid number. Try again.");
                        }
                    }


                    comp.setClockSpeed(newClock);
                    comp.setCache(newCache);
                    comp.setPower(newPower);

                    System.out.println("Updated");
                    service.saveData("hardware.bin");
                    service.txtFile();
                    break;
                }
                //Delete Component
                case 8: {
                    System.out.print("Enter name of component to delete: ");
                    String name = sc.nextLine();
                    HardwareComponent toRemove = service.findByName(name);

                    if (toRemove == null) {
                        System.out.println("Component not found");
                        break;
                    }

                    System.out.print("Sure? (yes/no): ");
                    String confirm = sc.nextLine();

                    if (confirm.equalsIgnoreCase("yes")) {
                        service.getAllComponents().remove(toRemove);
                        System.out.println("Deleted");
                        service.saveData("hardware.bin");
                        service.txtFile();
                    }
                    break;
                }
                //Search by Cache or Power
                case 9: {
                    System.out.println("Search by: 1) Cache  2) Power");
                    String choice = sc.nextLine();

                    if (choice.equals("1")) {
                        System.out.print("Enter cache size (MB): ");
                        int cache = Integer.parseInt(sc.nextLine());
                        List<HardwareComponent> found = service.findByCache(cache);
                        if (found.isEmpty()) {
                            System.out.println("No components found with cache = " + cache);
                        } else {
                            for (HardwareComponent hc : found) {
                                System.out.println(hc);
                            }
                        }
                    } else if (choice.equals("2")) {
                        System.out.print("Enter power consumption (W): ");
                        int power = Integer.parseInt(sc.nextLine());
                        List<HardwareComponent> found = service.findByPower(power);
                        if (found.isEmpty()) {
                            System.out.println("No components found with power = " + power);
                        } else {
                            for (HardwareComponent hc : found) {
                                System.out.println(hc);
                            }
                        }
                    } else {
                        System.out.println("Invalid choice.");
                    }
                    break;
                }

                //No Option
                default: {
                    if (option != -1) {
                        System.out.println("Invalid option");
                    }
                }
            }
        }
    }
}
