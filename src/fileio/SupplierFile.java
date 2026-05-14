package fileio;

import types.Supplier;

import java.io.*;
import java.util.ArrayList;

public class SupplierFile {

    private static final String FILE_PATH = "data\\Supplier.txt";

    public static void writeToFile(ArrayList<Supplier> suppliers) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Supplier s : suppliers) {
                writer.write(s.toFileFormat());
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

    public static Supplier parseSupplier(String line) {
        String[] part = line.split("\\|");
        String name =  part[0];
        String contact = part[1];
        int deliveryTime = Integer.parseInt(part[2]);
        boolean pendingPayment = Boolean.parseBoolean(part[3]);
        int reviewScore = Integer.parseInt(part[4]);

        Supplier supplier = new Supplier(name, contact, deliveryTime, pendingPayment, reviewScore);
        return supplier;
    }

    public static void readFromFile(ArrayList<Supplier> suppliers) {
        try(BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while((line = reader.readLine()) != null) {
                Supplier supplier = parseSupplier(line);
                if(supplier != null) {
                    suppliers.add(supplier);
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