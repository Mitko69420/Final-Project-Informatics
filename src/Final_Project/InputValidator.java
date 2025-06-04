package Final_Project;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class InputValidator {
    public static double getDoubleInput(Scanner sc, String message) {
        while (true) {
            try {
                System.out.print(message);
                return Double.parseDouble(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid");
            }
        }
    }

    public static int getIntInput(Scanner sc, String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid.");
            }
        }
    }

    public static double getDoubleInputDialog(Component parent, String message) {
        while (true) {
            String input = JOptionPane.showInputDialog(parent, message);
            if (input == null) return 0;
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(parent, "Invalid number. Try again.");
            }
        }
    }

    public static int getIntInputDialog(Component parent, String message) {
        while (true) {
            String input = JOptionPane.showInputDialog(parent, message);
            if (input == null) {
                return 0;
            }
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(parent, "Invalid integer. Try again.");
            }
        }
    }

}
