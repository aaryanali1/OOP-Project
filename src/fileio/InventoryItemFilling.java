package fileio;

import types.InventoryItem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class InventoryItemFilling implements FileHandling<InventoryItem>{
    public static final String FILE_PATH = "src/data/InventoryItems.txt";

    @Override
    public void writeToFile(ArrayList<InventoryItem> items) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (InventoryItem i : items) {
                writer.write(i.toFileString());
                writer.newLine();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public InventoryItem parseItem(String line) {
        String[] part = line.split("\\|");
        int id = Integer.parseInt(part[0]);
        String productName = part[1];
        double buyPrice = Double.parseDouble(part[2]);
        double sellPrice = Double.parseDouble(part[3]);
        int totalSales = Integer.parseInt(part[4]);
        int stockQuantity = Integer.parseInt(part[5]);
        String inventoryStatus = part[6];
        String category = part[7];
        boolean staffFlagged = Boolean.parseBoolean(part[8]);
        boolean reordered = Boolean.parseBoolean(part[9]);
        int reorderQuantity = Integer.parseInt(part[10]);
        int deliveryTime = Integer.parseInt(part[11]);
        boolean delivered = Boolean.parseBoolean(part[12]);

        return new InventoryItem(id, productName, buyPrice, sellPrice, totalSales, stockQuantity, category, staffFlagged, deliveryTime, delivered, reordered, reorderQuantity);
    }

    @Override
    public void readFromFile(ArrayList<InventoryItem> items) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                InventoryItem item = parseItem(line);
                if (item != null) items.add(item);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
