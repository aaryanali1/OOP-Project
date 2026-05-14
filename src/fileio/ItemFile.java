package fileio;

import types.Item;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ItemFile {

    private static final String FILE_PATH = "data\\Items.txt";

    public static void writeToFile(ArrayList<Item> items) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Item i : items) {
                writer.write(i.toFileFormat());
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

    public static Item parseItem(String line) {
        String[] part = line.split("\\|");
        String id = part[0];
        String productName = part[1];
        double buyPrice = Double.parseDouble(part[2]);
        double sellPrice = Double.parseDouble(part[3]);
        int totalSales = Integer.parseInt(part[4]);
        int monthlySales = Integer.parseInt(part[5]);
        int weeklySales = Integer.parseInt(part[6]);
        int dailySales = Integer.parseInt(part[7]);
        int stockQuantity = Integer.parseInt(part[8]);
        String supplier = part[9];
        String category = part[11];
        boolean staffFlagged = Boolean.parseBoolean(part[12]);
        boolean reordered = Boolean.parseBoolean(part[13]);
        LocalDateTime reorderTime = null;
        if(part[14] != null && !part[14].equals("") && !part[14].isEmpty()) {
            reorderTime = LocalDateTime.parse(part[14]);
        }
        int deliveryTime = Integer.parseInt(part[15]);
        boolean delivered = Boolean.parseBoolean(part[16]);

        Item item = new Item(id, productName, buyPrice, sellPrice, totalSales, monthlySales, weeklySales, dailySales, stockQuantity, supplier, category);
        item.setStaffFlagged(staffFlagged);
        item.setReordered(reordered);
        item.setReorderTime(reorderTime);
        item.setDeliveryTime(deliveryTime);
        item.setDelivered(delivered);
        item.calcuateStatus();
        return item;
    }

    public static void readFromFile(ArrayList<Item> items) {
        try(BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while((line = reader.readLine()) != null) {
                Item item = parseItem(line);
                if(item != null) {
                    items.add(item);
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