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

public class UpdateItemPrice {

    @FXML private Label itemName;
    @FXML private TextField buyPrice;
    @FXML private TextField sellPrice;

    @FXML private Label feedbackLabel;

    private ArrayList<InventoryItem> items;
    private TableView<InventoryItem> inventoryTable;
    private InventoryItemTab inventoryItemTab;
    private InventoryItem item;

    public void setInventoryItems(ArrayList<InventoryItem> items) { this.items = items; }

    public void setInventoryTable(TableView<InventoryItem> inventoryTable) { this.inventoryTable = inventoryTable; }

    public void setInventoryItemTab(InventoryItemTab inventoryItemTab) { this.inventoryItemTab = inventoryItemTab; }

    public void setItem(InventoryItem item) {
        this.item = item;
        itemName.setText(item.getProductName());
        buyPrice.setText(String.valueOf(item.getBuyPrice()));
        sellPrice.setText(String.valueOf(item.getSellPrice()));
    }

    @FXML
    void handleUpdate(ActionEvent event) {
        double buy, sell;

        try {
            buy = Double.parseDouble(buyPrice.getText());
            sell = Double.parseDouble(sellPrice.getText());

            if (buy < 0 || sell < 0) {
                feedbackLabel.setText("Price(s) cannot be Negative");
                return;
            }

            item.setBuyPrice(buy);
            item.setSellPrice(sell);
            inventoryTable.getItems().setAll(items);
            inventoryTable.refresh();
            inventoryItemTab.updateFinances();
            new InventoryItemFilling().writeToFile(items);

            Utilities.closeWindow(event);
            AuditLog.logEntry(Utilities.getCurrentUsername(), Utilities.getCurrentRole(), "Updated Item Price '(" + item.getId() + ") " + item.getProductName() + "'");
        }
        catch (NumberFormatException e) {
            feedbackLabel.setText("Please enter valid Numbers");
        }
        catch (Exception e) {
            feedbackLabel.setText("Error Updating Item Price");
            System.out.println(e.getMessage());
        }
    }

    @FXML void handleCancel(ActionEvent event) { Utilities.closeWindow(event); }
}
