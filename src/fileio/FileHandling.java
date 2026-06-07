package fileio;

import java.util.ArrayList;

public interface FileHandling<Type> {
    void writeToFile(ArrayList<Type> items);
    Type parseItem(String line);
    void readFromFile(ArrayList<Type> items);
}
