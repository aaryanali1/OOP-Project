package ui;

import fileio.AuditLog;
import fileio.InventoryItemFilling;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import types.InventoryItem;
import utilities.Utilities;

import java.util.ArrayList;

public class AddItem {
    @FXML private TextField buyPrice;
    @FXML private TextField category;
    @FXML private TextField productName;
    @FXML private TextField sellPrice;
    @FXML private TextField stockQuantity;
    @FXML private TextField totalSales;
    @FXML private Label feedbackLabel;

    private ArrayList<InventoryItem> items;
    private TableView<InventoryItem> inventoryTable;
    private InventoryItemTab inventoryItemTab;

    public void setInventoryItems(ArrayList<InventoryItem> items) { this.items = items; }

    public void setInventoryTable(TableView<InventoryItem> inventoryTable) { this.inventoryTable = inventoryTable; }

    public void setInventoryItemTab(InventoryItemTab inventoryItemTab) { this.inventoryItemTab = inventoryItemTab; }

    @FXML
    void handleAdd(ActionEvent event) {
        String productName, category;
        double buyPrice, sellPrice;
        int totalSales, stockQuantity;

        try {
            productName = this.productName.getText().trim();
            category = this.category.getText().trim();

            if (productName.isEmpty() || category.isEmpty()) {
                feedbackLabel.setText("Text Fields cannot be Empty");
                return;
            }

            buyPrice = Double.parseDouble(this.buyPrice.getText());
            sellPrice = Double.parseDouble(this.sellPrice.getText());
            totalSales = Integer.parseInt(this.totalSales.getText());
            stockQuantity = Integer.parseInt(this.stockQuantity.getText());

            if (buyPrice < 0 || sellPrice < 0 || totalSales < 0 || stockQuantity < 0) {
                feedbackLabel.setText("Values cannot be Negative");
                return;
            }

            int id = getNextID();
            items.add(new InventoryItem(id, productName, buyPrice, sellPrice, totalSales, stockQuantity, category, false, 0, true, false, 0));
            inventoryTable.getItems().setAll(items);
            inventoryItemTab.updateFinances();
            new InventoryItemFilling().writeToFile(items);

            Utilities.closeWindow(event);
            AuditLog.logEntry(Utilities.getCurrentUsername(), Utilities.getCurrentRole(), "Added Item '(" + id + ") " + productName + "'");
        }
        catch (NumberFormatException e) {
            feedbackLabel.setText("Please enter valid Numbers");
        }
        catch (Exception e) {
            feedbackLabel.setText("Error Adding Item");
            e.printStackTrace();
        }
    }

    @FXML
    void handleCancel(ActionEvent event) {
        Utilities.closeWindow(event);
    }

    private int getNextID() {
        int maxID = 0;

        for (InventoryItem i : items) {
            int currentID = i.getId();
            if (currentID > maxID) maxID = currentID;
        }
        return maxID + 1;
    }
}
