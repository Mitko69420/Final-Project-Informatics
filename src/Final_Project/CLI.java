package Final_Project;

import java.util.Scanner;

public class CLI {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        HardwareDataManager manager = new HardwareDataManager();
        manager.loadFromFile("hardware.bin");

        while (true) {
            System.out.println("\n--- Hardware Comparator ---");
            System.out.println("1. View All Components");
            System.out.println("2. Compare Two Components");
            System.out.println("3. Suggest Upgrade");
            System.out.println("4. Add New Component");
            System.out.println("5. Save and Exit");
            System.out.println("6. Sort Components by Performance");
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
                    if (h1 == null || h2 == null) {
                        System.out.println("Component(s) not found");
                    }
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
                    double clock = 0;
                    int cache = 0, power = 0;

                    //handle errors
                    try {
                        System.out.print("Clock Speed (GHz): ");
                        clock = Double.parseDouble(sc.nextLine());
                        System.out.print("Cache (MB): ");
                        cache = Integer.parseInt(sc.nextLine());
                        System.out.print("Power (Watt): ");
                        power = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input");
                        break;
                    }


                    HardwareComponent newComponent = new HardwareComponent(type, clock, cache, power) {
                        @Override
                        public String getType() {
                            return "";
                        }
                    };
                    manager.addComponent(newComponent);
                    System.out.println("Added");
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

                default: {
                    if (option != -1) {
                        System.out.println("Invalid option");
                    }
                }
            }
        }
    }
}