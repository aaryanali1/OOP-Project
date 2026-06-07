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

public class SellItem {
    @FXML private TextField quantity;
    @FXML private TextField sellPrice;
    @FXML private Label feedbackLabel;
    @FXML private Label itemName;

    private ArrayList<InventoryItem> items;
    private TableView<InventoryItem> inventoryTable;
    private InventoryItem item;
    private InventoryItemTab inventoryItemTab;

    public void setInventoryItems(ArrayList<InventoryItem> items) {
        this.items = items;
    }

    public void setInventoryTable(TableView<InventoryItem> inventoryTable) {
        this.inventoryTable = inventoryTable;
    }

    public void setInventoryItemTab(InventoryItemTab inventoryItemTab) { this.inventoryItemTab = inventoryItemTab; }

    public void setItem(InventoryItem item) {
        this.item = item;
        itemName.setText(item.getProductName());
        sellPrice.setText(String.valueOf(item.getSellPrice()));
    }

    @FXML
    void handleSell(ActionEvent event) {
        int qty;
        double price;

        try {
            qty = Integer.parseInt(quantity.getText());
            price = Double.parseDouble(sellPrice.getText());

            if (qty <= 0) {
                feedbackLabel.setText("Quantity cannot be Zero");
                return;
            }
            if (qty > item.getStockQuantity()) {
                feedbackLabel.setText("Stock Unavailable (Current Quantity: " + item.getStockQuantity() + ")");
                return;
            }
            if (price < 0) {
                feedbackLabel.setText("Price cannot be Negative");
                return;
            }

            item.setSellPrice(price);
            item.recordSale(qty);
            inventoryTable.getItems().setAll(items);
            inventoryTable.refresh();
            inventoryItemTab.updateFinances();
            new InventoryItemFilling().writeToFile(items);

            Utilities.closeWindow(event);
            AuditLog.logEntry(Utilities.getCurrentUsername(), Utilities.getCurrentRole(), "Sold Item '(" + item.getId() + ") " + item.getProductName() + "'");
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
}
