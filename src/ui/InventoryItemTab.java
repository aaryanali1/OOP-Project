package ui;

import fileio.AuditLog;
import fileio.InventoryItemFilling;
import fileio.ThresholdFile;
import javafx.beans.binding.DoubleBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import types.InventoryItem;
import types.User;
import utilities.Utilities;

import java.util.ArrayList;
import java.util.Optional;

public class InventoryItemTab {
    private ArrayList<InventoryItem> items = new ArrayList<>();

    @FXML private Button add;
    @FXML private Button delete;
    @FXML private Button delivered;
    @FXML private Button flag;
    @FXML private Button price;
    @FXML private Button reorder;
    @FXML private Button sell;
    @FXML private Button update;

    @FXML private TableView<InventoryItem> inventoryTable;
    @FXML private TableColumn<InventoryItem, Double> buyprice;
    @FXML private TableColumn<InventoryItem, String> category;
    @FXML private TableColumn<InventoryItem, String> estimated;
    @FXML private TableColumn<InventoryItem, String> productName;
    @FXML private TableColumn<InventoryItem, Integer> id;
    @FXML private TableColumn<InventoryItem, Double> sellprice;
    @FXML private TableColumn<InventoryItem, String> inventorystatus;
    @FXML private TableColumn<InventoryItem, Integer> stockquantity;
    @FXML private TableColumn<InventoryItem, Integer> totalsales;

    @FXML private ComboBox<String> categoryBox;
    @FXML private TextField searchField;

    @FXML private Label totalSpending;
    @FXML private Label totalRevenue;
    @FXML private Label totalProfit;

    @FXML
    void add(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddItem.fxml"));
            Parent root = loader.load();
            AddItem controller = loader.getController();
            controller.setInventoryItems(items);
            controller.setInventoryTable(inventoryTable);
            controller.setInventoryItemTab(this);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Add Item");
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void update(ActionEvent event) {
        InventoryItem selected = inventoryTable.getSelectionModel().getSelectedItem();

        Alert alert;

        if (selected == null) {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Item Selected");
            alert.setHeaderText("No Item Selected");
            alert.setContentText("Please select an Item first");
            alert.show();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UpdateItem.fxml"));
            Parent root = loader.load();
            UpdateItem controller = loader.getController();
            controller.setInventoryItems(items);
            controller.setInventoryTable(inventoryTable);
            controller.setInventoryItemTab(this);
            controller.setItem(selected);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Update Item");
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void delete(ActionEvent event) {
        InventoryItem selected = inventoryTable.getSelectionModel().getSelectedItem();

        Alert alert;

        if (selected == null) {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Item Selected");
            alert.setHeaderText("No Item Selected");
            alert.setContentText("Please select an Item first");
            alert.show();
            return;
        }

        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Delete Item");
        alert.setContentText("Are you sure you want to delete " + selected.getProductName() + "?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            items.remove(selected);
            updateFinances();
            inventoryTable.getItems().setAll(items);
            new InventoryItemFilling().writeToFile(items);
            AuditLog.logEntry(Utilities.getCurrentUsername(), Utilities.getCurrentRole(), "Deleted Item '(" + selected.getId() + ") " + selected.getProductName() + "'");
        }
    }

    @FXML
    void sell(ActionEvent event) {
        InventoryItem selected = inventoryTable.getSelectionModel().getSelectedItem();

        Alert alert;

        if (selected == null) {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Item Selected");
            alert.setHeaderText("No Item Selected");
            alert.setContentText("Please select an Item first");
            alert.show();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SellItem.fxml"));
            Parent root = loader.load();
            SellItem controller = loader.getController();
            controller.setInventoryItems(items);
            controller.setInventoryTable(inventoryTable);
            controller.setInventoryItemTab(this);
            controller.setItem(selected);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Sell Item");
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void reorder(ActionEvent event) {
        InventoryItem selected = inventoryTable.getSelectionModel().getSelectedItem();

        Alert alert;

        if (selected == null) {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Item Selected");
            alert.setHeaderText("No Item Selected");
            alert.setContentText("Please select an Item first");
            alert.show();
            return;
        }

        if (selected.isReordered()) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Already Reordered");
            alert.setContentText("This item has already been reordered.");
            alert.show();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ReorderItem.fxml"));
            Parent root = loader.load();
            ReorderItem controller = loader.getController();
            controller.setInventoryItems(items);
            controller.setInventoryTable(inventoryTable);
            controller.setInventoryItemTab(this);
            controller.setItem(selected);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Reorder Item");
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void markDelivered(ActionEvent event) {
        InventoryItem selected = inventoryTable.getSelectionModel().getSelectedItem();

        Alert alert;

        if (selected == null) {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Item Selected");
            alert.setHeaderText("No Item Selected");
            alert.setContentText("Please select an Item first");
            alert.show();
            return;
        }

        if (!selected.isReordered()) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Not Reordered");
            alert.setHeaderText("Not Reordered");
            alert.setContentText(selected.getProductName() + " is not Reordered");
            alert.show();
            return;
        }

        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delivered");
        alert.setHeaderText("Mark Item as Delivered");
        alert.setContentText("Are you sure you want to mark " + selected.getProductName() + "as delivered?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            selected.setDelivered(true);
            selected.setStockQuantity(selected.getStockQuantity() + selected.getReorderQuantity());
            selected.setReordered(false);
            selected.setStaffFlagged(false);
            selected.calculateStatus();
            inventoryTable.refresh();
            updateFinances();
            new InventoryItemFilling().writeToFile(items);
            AuditLog.logEntry(Utilities.getCurrentUsername(), Utilities.getCurrentRole(), "Marked Item Delivered'(" + selected.getId() + ") " + selected.getProductName() + "'");
        }
    }

    @FXML
    void price(ActionEvent event) {
        InventoryItem selected = inventoryTable.getSelectionModel().getSelectedItem();

        Alert alert;

        if (selected == null) {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Item Selected");
            alert.setHeaderText("No Item Selected");
            alert.setContentText("Please select an Item first");
            alert.show();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UpdateItemPrice.fxml"));
            Parent root = loader.load();
            UpdateItemPrice controller = loader.getController();
            controller.setInventoryItems(items);
            controller.setInventoryTable(inventoryTable);
            controller.setInventoryItemTab(this);
            controller.setItem(selected);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Update Item Price");
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void flag(ActionEvent event) {
        InventoryItem selected = inventoryTable.getSelectionModel().getSelectedItem();

        Alert alert;

        if (selected == null) {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Item Selected");
            alert.setHeaderText("No Item Selected");
            alert.setContentText("Please select an Item first");
            alert.show();
            return;
        }

        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Flagged");
        alert.setHeaderText("Flag Item");
        alert.setContentText("Are you sure you want to flag " + selected.getProductName() + "?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            selected.setStaffFlagged(true);
            selected.calculateStatus();
            inventoryTable.refresh();
            new InventoryItemFilling().writeToFile(items);
            AuditLog.logEntry(Utilities.getCurrentUsername(), Utilities.getCurrentRole(), "Flagged Item '(" + selected.getId() + ") " + selected.getProductName() + "'");
        }
    }

    @FXML
    void search(ActionEvent event) {
        String search = searchField.getText().trim().toLowerCase();

        ArrayList<InventoryItem> filtered = new ArrayList<>();

        for (InventoryItem i : items) {
            if (String.valueOf(i.getId()).contains(search) || i.getProductName().toLowerCase().contains(search) || i.getCategory().toLowerCase().contains(search)) {
                filtered.add(i);
            }
        }
        inventoryTable.getItems().setAll(filtered);
    }

    @FXML
    void category(ActionEvent event) {
        String selected = categoryBox.getValue();

        if (selected.equals("All")) {
            inventoryTable.getItems().setAll(items);
            return;
        }

        ArrayList<InventoryItem> filtered = new ArrayList<>();

        for (InventoryItem i : items) {
            if (i.getCategory().equalsIgnoreCase(selected)) filtered.add(i);
        }

        inventoryTable.getItems().setAll(filtered);
    }

    public void initialize() {
        applyPermissions();
        ThresholdFile.readFromFile();

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        buyprice.setCellValueFactory(new PropertyValueFactory<>("buyPrice"));
        sellprice.setCellValueFactory(new PropertyValueFactory<>("sellPrice"));
        stockquantity.setCellValueFactory(new PropertyValueFactory<>("stockQuantity"));
        totalsales.setCellValueFactory(new PropertyValueFactory<>("totalSales"));
        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        inventorystatus.setCellValueFactory(new PropertyValueFactory<>("inventoryStatus"));
        estimated.setCellValueFactory(new PropertyValueFactory<>("predictedOutOfStock"));

        inventorystatus.setCellFactory(column -> new TableCell<InventoryItem, String>() {
            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);
                if (empty || status == null) {
                    setText(null);
                    setStyle("");
                    return;
                }

                setText(status);
                if (status.contains("(FLAGGED)")) {
                    setStyle("-fx-text-fill: #7C3AED; -fx-font-weight: bold;");
                }
                else {
                    switch (status) {
                        case "GOOD":
                            setStyle("-fx-text-fill: #1A7A4A; -fx-font-weight: bold;");
                            break;
                        case "OK":
                            setStyle("-fx-text-fill: #2471A3; -fx-font-weight: bold;");
                            break;
                        case "BAD":
                            setStyle("-fx-text-fill: #C0392B; -fx-font-weight: bold;");
                            break;
                        default:
                            setStyle("");
                            break;
                    }
                }
            }
        });

        DoubleBinding usableWidth = inventoryTable.widthProperty().subtract(2);
        id.prefWidthProperty().bind(usableWidth.multiply(60.0 / 1300));
        productName.prefWidthProperty().bind(usableWidth.multiply(240.0 / 1300));
        category.prefWidthProperty().bind(usableWidth.multiply(180.0 / 1300));
        buyprice.prefWidthProperty().bind(usableWidth.multiply(110.0 / 1300));
        sellprice.prefWidthProperty().bind(usableWidth.multiply(110.0 / 1300));
        stockquantity.prefWidthProperty().bind(usableWidth.multiply(80.0 / 1300));
        totalsales.prefWidthProperty().bind(usableWidth.multiply(110.0 / 1300));
        inventorystatus.prefWidthProperty().bind(usableWidth.multiply(190.0 / 1300));
        estimated.prefWidthProperty().bind(usableWidth.multiply(220.0 / 1300));
        id.setMinWidth(60);
        productName.setMinWidth(240);
        category.setMinWidth(180);
        buyprice.setMinWidth(110);
        sellprice.setMinWidth(110);
        stockquantity.setMinWidth(80);
        totalsales.setMinWidth(110);
        inventorystatus.setMinWidth(190);
        estimated.setMinWidth(220);

        new InventoryItemFilling().readFromFile(items);
        updateFinances();
        inventoryTable.getItems().setAll(items);

        categoryBox.getItems().add("All");
        for (InventoryItem i : items) {
            if (!categoryBox.getItems().contains(i.getCategory())) categoryBox.getItems().add(i.getCategory());
        }
        categoryBox.setValue("All");
    }

    public void applyPermissions() {
        User currentUser = Utilities.getCurrentUser();

        add.setDisable(!currentUser.canAdd());
        delete.setDisable(!currentUser.canDelete());
        update.setDisable(!currentUser.canUpdate());
        delivered.setDisable(!currentUser.canMarkDelivered());
        price.setDisable(!currentUser.canUpdatePrices());
        flag.setDisable(!currentUser.canFlag());
        update.setDisable(!currentUser.canUpdate());
        reorder.setDisable(!currentUser.canReorder());
        sell.setDisable(!currentUser.canSell());
    }

    public void updateFinances() {
        double spending = 0, revenue = 0, profit;

        for (InventoryItem i : items) {
            spending += i.getBuyPrice() * (i.getTotalSales() + i.getStockQuantity());
            revenue += i.getSellPrice() * (i.getTotalSales() + i.getStockQuantity());
        }
        profit = revenue - spending;

        totalSpending.setText(String.format("Total Spending: %.2f", spending));
        totalRevenue.setText(String.format("Total Revenue: %.2f", revenue));
        totalProfit.setText(String.format("Total Profit: %.2f", profit));

    }

    public void refreshAfterThreshold() {
        for (InventoryItem i : items) {
            i.calculateStatus();
        }
        inventoryTable.getItems().setAll(items);
        inventoryTable.refresh();
        new InventoryItemFilling().writeToFile(items);
    }
}
