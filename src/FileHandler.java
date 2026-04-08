import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Handles file I/O operations — satisfies File Handling requirement
public class FileHandler {

    // Writes a list of lines to a file
    public static void writeToFile(String filename, List<String> lines) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("[FileHandler] Data written to: " + filename);
        } catch (IOException e) {
            System.out.println("[FileHandler] Error writing to file: " + e.getMessage());
        }
    }

    // Reads all lines from a file and returns them
    public static List<String> readFromFile(String filename) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            System.out.println("[FileHandler] Data read from: " + filename);
        } catch (FileNotFoundException e) {
            System.out.println("[FileHandler] File not found: " + filename);
        } catch (IOException e) {
            System.out.println("[FileHandler] Error reading file: " + e.getMessage());
        }
        return lines;
    }

    // Appends a single line to an existing file
    public static void appendToFile(String filename, String line) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("[FileHandler] Error appending to file: " + e.getMessage());
        }
    }

    // Overloaded writeToFile — write a single string
    public static void writeToFile(String filename, String content) {
        List<String> lines = new ArrayList<>();
        lines.add(content);
        writeToFile(filename, lines);
    }
}