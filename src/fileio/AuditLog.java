package fileio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AuditLog {
    private static final String FILE_PATH = "src/data/AuditLog.txt";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");

    public static void logEntry(String username, String role, String action) {
        String time = LocalDateTime.now().format(FORMATTER);
        String entry = String.format("[%s] '%s' %s: %s", time, role, username, action);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(entry);
            writer.newLine();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<String> loadAllLog() {
        ArrayList<String> logs = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) logs.add(line);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return logs;
    }
}
