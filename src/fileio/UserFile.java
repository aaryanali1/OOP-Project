package fileio;

import types.Admin;
import types.Staff;
import types.User;

import java.io.*;
import java.util.ArrayList;

public class UserFile {

    //private static final String FILE_PATH = "data/Users.txt";

    private static final String FILE_PATH = "src/data/Users.txt";

    public static void writeToFile(ArrayList<User> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (User u : users) {
                writer.write(u.toFileFormat());
                writer.newLine();
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        catch (IOException e) {
            System.out.println("Error while writing to file");
        }
    }

    public static User parseUser(String line) {
        String[] part = line.split("\\|");
        String username = part[0];
        String password = part[1];
        String role = part[2];

        switch (role) {
            case "Admin":
                return new Admin(username, password);
            case "Staff":
                return new Staff(username, password);
            default:
                return null;
        }
    }

    public static void readFromFile(ArrayList<User> users) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = parseUser(line);
                if (user != null) {
                    users.add(user);
                }
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        catch (IOException e) {
            System.out.println("Error while reading file");
        }
    }
}
