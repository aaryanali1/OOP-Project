package fileio;

import types.Admin;
import types.Staff;
import types.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class UserFilling implements FileHandling<User> {
    private static final String FILE_PATH = "src/data/Users.txt";

    @Override
    public void writeToFile(ArrayList<User> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))){
            for (User u : users) {
                writer.write(u.toFileString());
                writer.newLine();
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public User parseItem(String line) {
        String[] part = line.split("\\|");
        String username = part[0];
        String password = part[1];
        String role = part[2];

        return switch (role) {
            case "ADMIN" -> new Admin(username, password);
            case "STAFF" -> new Staff(username, password);
            default -> null;
        };
    }

    @Override
    public void readFromFile(ArrayList<User> users) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))){
            String line;
            while ((line = reader.readLine()) != null) {
                User user = parseItem(line);
                if(user != null) users.add(user);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
