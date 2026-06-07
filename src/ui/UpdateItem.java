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

public class UpdateItem {
    @FXML private TextField buyPrice;
    @FXML private TextField category;
    @FXML private TextField productName;
    @FXML private TextField sellPrice;
    @FXML private TextField stockQuantity;
    @FXML private TextField totalSales;
    @FXML private Label feedbackLabel;

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
        productName.setText(item.getProductName());
        buyPrice.setText(String.valueOf(item.getBuyPrice()));
        sellPrice.setText(String.valueOf(item.getSellPrice()));
        totalSales.setText(String.valueOf(item.getTotalSales()));
        stockQuantity.setText(String.valueOf(item.getStockQuantity()));
        category.setText(item.getCategory());
    }

    @FXML
    void handleUpdate(ActionEvent event) {
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

            item.setProductName(productName);
            item.setBuyPrice(buyPrice);
            item.setSellPrice(sellPrice);
            item.setTotalSales(totalSales);
            item.setStockQuantity(stockQuantity);
            item.setCategory(category);
            inventoryTable.getItems().setAll(items);
            inventoryTable.refresh();
            inventoryItemTab.updateFinances();
            new InventoryItemFilling().writeToFile(items);

            Utilities.closeWindow(event);
            AuditLog.logEntry(Utilities.getCurrentUsername(), Utilities.getCurrentRole(), "Updated Item '(" + item.getId() + ") " + item.getProductName() + "'");
        }
        catch (NumberFormatException e) {
            feedbackLabel.setText("Please enter valid Numbers");
        }
        catch (Exception e) {
            feedbackLabel.setText("Error Updating Item");
            e.printStackTrace();
        }
    }

    @FXML
    void handleCancel(ActionEvent event) {
        Utilities.closeWindow(event);
    }

}
