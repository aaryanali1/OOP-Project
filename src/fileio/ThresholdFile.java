package fileio;

import types.InventoryItem;
import ui.InventoryItemTab;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class ThresholdFile {
    private static final String FILE_PATH = "src/data/Threshold.txt";

    public static void writeToFile(int good, int ok, int bad) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write(good + "|" + ok + "|" + bad);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void readFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line = reader.readLine();
            if (line != null) {
                String[] part = line.split("\\|");
                InventoryItem.setGoodThreshold(Integer.parseInt(part[0]));
                InventoryItem.setOkThreshold(Integer.parseInt(part[1]));
                InventoryItem.setBadThreshold(Integer.parseInt(part[2]));
            }
        }
        catch (Exception e) {
            writeToFile(100, 80, 50);
            InventoryItem.setGoodThreshold(100);
            InventoryItem.setOkThreshold(80);
            InventoryItem.setBadThreshold(50);
            e.printStackTrace();
        }
    }
}
