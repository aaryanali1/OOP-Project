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

public class ReorderItem {
    @FXML private Label itemName;
    @FXML private TextField quantity;
    @FXML private TextField buyPrice;
    @FXML private Label feedbackLabel;

    private ArrayList<InventoryItem> items;
    private TableView<InventoryItem> inventoryTable;
    private InventoryItem item;
    private InventoryItemTab inventoryItemTab;

    public void setInventoryItems(ArrayList<InventoryItem> items) { this.items = items; }

    public void setInventoryTable(TableView<InventoryItem> inventoryTable) { this.inventoryTable = inventoryTable; }

    public void setInventoryItemTab(InventoryItemTab inventoryItemTab) { this.inventoryItemTab = inventoryItemTab; }

    public void setItem(InventoryItem item) {
        this.item = item;
        itemName.setText(item.getProductName());
        buyPrice.setText(String.valueOf(item.getBuyPrice()));
    }

    @FXML
    void handleReorder(ActionEvent event) {
        int qty;
        double price;

        try {
            qty = Integer.parseInt(quantity.getText());
            price = Double.parseDouble(buyPrice.getText());

            if (qty <= 0) {
                feedbackLabel.setText("Quantity cannot be Zero");
                return;
            }

            if (price < 0) {
                feedbackLabel.setText("Price cannot be Negative");
                return;
            }

            item.setReordered(true);
            item.setDelivered(false);
            item.setReorderQuantity(qty);
            item.setBuyPrice(price);
            inventoryTable.refresh();
            inventoryItemTab.updateFinances();
            new InventoryItemFilling().writeToFile(items);

            Utilities.closeWindow(event);
            AuditLog.logEntry(Utilities.getCurrentUsername(), Utilities.getCurrentRole(), "Reordered Item '(" + item.getId() + ") " + item.getProductName() + "'");

        }
        catch (NumberFormatException e) {
            feedbackLabel.setText("Please enter valid Numbers");
        }
        catch (Exception e) {
            feedbackLabel.setText("Error Reordering Item");
            System.out.println(e.getMessage());
        }
    }

    @FXML void handleCancel(ActionEvent event) { Utilities.closeWindow(event); }
}
