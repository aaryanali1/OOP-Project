package fileio;

import types.InventoryItem;
import types.Supplier;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class SupplierFilling implements FileHandling<Supplier> {
    public static final String FILE_PATH = "src/data/Suppliers.txt";

    @Override
    public void writeToFile(ArrayList<Supplier> suppliers) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Supplier s : suppliers) {
                writer.write(s.toFileString());
                writer.newLine();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Supplier parseItem(String line) {
        String[] part = line.split("\\|");
        int id = Integer.parseInt(part[0]);
        String name = part[1];
        String contact = part[2];
        int deliveryTime = Integer.parseInt(part[3]);
        String category = part[4];
        String paymentStatus = part[5];
        int reviewScore = Integer.parseInt(part[6]);
        boolean active = Boolean.parseBoolean(part[7]);

        return new Supplier(id, name, contact, deliveryTime, category, paymentStatus, reviewScore, active);
    }

    @Override
    public void readFromFile(ArrayList<Supplier> suppliers) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Supplier supplier = parseItem(line);
                if (supplier != null) suppliers.add(supplier);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
