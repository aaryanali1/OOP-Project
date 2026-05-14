package fileio;

import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AuditLog {

    private static final String FILE_PATH = "data\\AuditLog.txt";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public void logEntry(String username, String role, String action) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        String entry = String.format("%s %s %s %s",  timestamp, role, username, action);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write(entry);
            writer.newLine();
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        catch (IOException e) {
            System.out.println("Error while writing to file");
        }
    }

    public ArrayList<String> loadAllLog() {
        ArrayList<String> logs = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) logs.add(line);
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        catch (IOException e) {
            System.out.println("Error while writing to file");
        }
        return logs;
    }
}
