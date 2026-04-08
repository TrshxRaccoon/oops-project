import java.util.Scanner;

// Utility class for reading validated user input from Scanner
public class InputHelper {

    private static Scanner scanner = new Scanner(System.in);

    // Reads a non-empty string from the user
    public static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    // Reads an integer, handles invalid input
    public static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim()); // Integer wrapper parsing
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    // Reads a double — uses Double wrapper parsing
    public static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    // Reads yes/no and returns boolean — uses Boolean wrapper
    public static boolean readBoolean(String prompt) {
        System.out.print(prompt + " (yes/no): ");
        String input = scanner.nextLine().trim().toLowerCase();
        return Boolean.parseBoolean(input.equals("yes") ? "true" : "false");
    }

    public static Scanner getScanner() { return scanner; }
}