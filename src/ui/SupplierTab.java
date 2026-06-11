package ui;

import fileio.AuditLog;
import fileio.SupplierFilling;
import javafx.beans.binding.DoubleBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import types.Supplier;
import types.User;
import utilities.Utilities;

import java.util.ArrayList;
import java.util.Optional;

public class SupplierTab {
    private ArrayList<Supplier> suppliers = new ArrayList<>();

    @FXML private Button add;
    @FXML private Button clearPay;
    @FXML private Button delete;
    @FXML private Button update;
    @FXML private Button markInactive;
    @FXML private Button markActive;

    @FXML private TableView<Supplier> supplierTable;
    @FXML private TableColumn<Supplier, String> priorityScore;
    @FXML private TableColumn<Supplier, String> category;
    @FXML private TableColumn<Supplier, String> contact;
    @FXML private TableColumn<Supplier, Integer> delivery;
    @FXML private TableColumn<Supplier, Integer> id;
    @FXML private TableColumn<Supplier, String> name;
    @FXML private TableColumn<Supplier, Integer> review;
    @FXML private TableColumn<Supplier, String> paymentStatus;
    @FXML private TableColumn<Supplier, Boolean> activeStatus;

    @FXML private ComboBox<String> categoryBox;
    @FXML private TextField searchField;

    @FXML
    void add(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddSupplier.fxml"));
            Parent root = loader.load();
            AddSupplier controller = loader.getController();
            controller.setSuppliers(suppliers);
            controller.setSupplierTable(supplierTable);
            controller.setSupplierTab(this);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Add Supplier");
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.show();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void update(ActionEvent event) {
        Supplier selected = supplierTable.getSelectionModel().getSelectedItem();

        Alert alert;

        if (selected == null) {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Supplier Selected");
            alert.setHeaderText("No Supplier Selected");
            alert.setContentText("Please select a Supplier first");
            alert.show();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UpdateSupplier.fxml"));
            Parent root = loader.load();
            UpdateSupplier controller = loader.getController();
            controller.setSuppliers(suppliers);
            controller.setSupplierTable(supplierTable);
            controller.setSupplier(selected);
            controller.setSupplierTab(this);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Update Supplier");
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.show();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void delete(ActionEvent event) {
        Supplier selected = supplierTable.getSelectionModel().getSelectedItem();

        Alert alert;

        if (selected == null) {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Supplier Selected");
            alert.setHeaderText("No Supplier Selected");
            alert.setContentText("Please select a Supplier first");
            alert.show();
            return;
        }

        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Delete Supplier");
        alert.setContentText("Are you sure you want to delete " + selected.getSupplierName() + "?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            suppliers.remove(selected);
            supplierTable.getItems().setAll(suppliers);
            refreshCategory();
            new SupplierFilling().writeToFile(suppliers);
            AuditLog.logEntry(Utilities.getCurrentUsername(), Utilities.getCurrentRole(), "Deleted Supplier '(" + selected.getId() + ") " + selected.getSupplierName() + "'");
        }
    }

    @FXML
    void clearPay(ActionEvent event) {
        Supplier selected = supplierTable.getSelectionModel().getSelectedItem();

        Alert alert;

        if (selected == null) {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Supplier Selected");
            alert.setHeaderText("No Supplier Selected");
            alert.setContentText("Please select an Supplier first");
            alert.show();
            return;
        }

        if (!selected.isActive()) {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Supplier Inactive");
            alert.setHeaderText("Supplier Inactive");
            alert.setContentText("Cannot clear dues for Inactive supplier");
            alert.show();
            return;
        }

        if (selected.getPaymentStatus().equalsIgnoreCase("Clear")) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Already Clear");
            alert.setHeaderText("Already Clear");
            alert.setContentText("Payment of " + selected.getSupplierName() + " already Clear");
            alert.show();
            return;
        }

        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Clear Payment");
        alert.setHeaderText("Clear Supplier Payment");
        alert.setContentText("Clear Payment for " + selected.getSupplierName() + "?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            selected.setPaymentStatus("Clear");
            supplierTable.getItems().setAll(suppliers);
            new SupplierFilling().writeToFile(suppliers);
            AuditLog.logEntry(Utilities.getCurrentUsername(), Utilities.getCurrentRole(), "Cleared Supplier Dues '(" + selected.getId() + ") " + selected.getSupplierName() + "'");
        }
    }

    @FXML
    void markInactive(ActionEvent event) {
        Supplier selected = supplierTable.getSelectionModel().getSelectedItem();

        Alert alert;

        if (selected == null) {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Supplier Selected");
            alert.setHeaderText("No Supplier Selected");
            alert.setContentText("Please select a Supplier first");
            alert.show();
            return;
        }

        if (!selected.isActive()) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Supplier Inactive");
            alert.setHeaderText("Supplier Inactive");
            alert.setContentText(selected.getSupplierName() + " is already Inactive");
            alert.show();
            return;
        }

        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Inactive");
        alert.setHeaderText("Mark Supplier as Inactive");
        alert.setContentText("Are you sure you want to mark " + selected.getSupplierName() + " as Inactive?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            selected.setActive(false);
            supplierTable.refresh();
            new SupplierFilling().writeToFile(suppliers);
            AuditLog.logEntry(Utilities.getCurrentUsername(), Utilities.getCurrentRole(), "Marked Supplier Inactive '(" + selected.getId() + ") " + selected.getSupplierName() + "'");
        }
    }

    @FXML
    void markActive(ActionEvent event) {
        Supplier selected = supplierTable.getSelectionModel().getSelectedItem();

        Alert alert;

        if (selected == null) {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Supplier Selected");
            alert.setHeaderText("No Supplier Selected");
            alert.setContentText("Please select a Supplier first");
            alert.show();
            return;
        }

        if (selected.isActive()) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Supplier Active");
            alert.setHeaderText("Supplier Active");
            alert.setContentText(selected.getSupplierName() + " is already Active");
            alert.show();
            return;
        }

        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Active");
        alert.setHeaderText("Mark Supplier as Active");
        alert.setContentText("Are you sure you want to mark " + selected.getSupplierName() + " as Active?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            selected.setActive(true);
            supplierTable.refresh();
            new SupplierFilling().writeToFile(suppliers);
            AuditLog.logEntry(Utilities.getCurrentUsername(), Utilities.getCurrentRole(), "Marked Supplier Active '(" + selected.getId() + ") " + selected.getSupplierName() + "'");
        }
    }

    @FXML void category(ActionEvent event) { applyFilters(); }

    @FXML void search(ActionEvent event) { applyFilters(); }

    public void applyFilters() {
        String search = searchField.getText().trim().toLowerCase();
        String selected = categoryBox.getValue();

        ArrayList<Supplier> filtered = new ArrayList<>();

        for (Supplier s : suppliers) {
            if (selected != null && !selected.equalsIgnoreCase("All") && !s.getCategory().equalsIgnoreCase(selected)) {
                continue;
            }
            if (String.valueOf(s.getId()).contains(search) || s.getSupplierName().toLowerCase().contains(search) || s.getCategory().toLowerCase().contains(search)) filtered.add(s);
        }
        supplierTable.getItems().setAll(filtered);
    }

    public void applyPermissions() {
        User currentUser = Utilities.getCurrentUser();

        add.setDisable(!currentUser.canAdd());
        delete.setDisable(!currentUser.canDelete());
        update.setDisable(!currentUser.canUpdate());
        clearPay.setDisable(!currentUser.canClearPay());
        markInactive.setDisable(!currentUser.canMarkInactive());
        markActive.setDisable(!currentUser.canMarkInactive());
    }

    public void refreshCategory() {
        String current = categoryBox.getValue();

        categoryBox.getItems().clear();
        categoryBox.getItems().add("All");

        for (Supplier s : suppliers) {
            if (!categoryBox.getItems().contains(s.getCategory())) {
                categoryBox.getItems().add(s.getCategory());
            }
        }
        if (current != null && categoryBox.getItems().contains(current)) {
            categoryBox.setValue(current);
        }
        else {
            categoryBox.setValue("All");
        }
    }

    public void initialize() {
        applyPermissions();

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        contact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        priorityScore.setCellValueFactory(new PropertyValueFactory<>("priorityScore"));
        delivery.setCellValueFactory(new PropertyValueFactory<>("deliveryTime"));
        review.setCellValueFactory(new PropertyValueFactory<>("reviewScore"));
        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        paymentStatus.setCellValueFactory(new PropertyValueFactory<>("paymentStatus"));
        activeStatus.setCellValueFactory(new PropertyValueFactory<>("activeStatus"));

        priorityScore.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String score, boolean empty) {
                super.updateItem(score, empty);
                if (empty || score == null) {
                    setText(null);
                    setStyle("");
                    return;
                }

                setText(score);
                if (score.contains("PRIORITY")) {
                    setStyle("-fx-text-fill: #1A7A4A; -fx-font-weight: bold;");
                } else if (score.contains("STANDARD")) {
                    setStyle("-fx-text-fill: #2471A3; -fx-font-weight: bold;");
                } else if (score.contains("AVOID")) {
                    setStyle("-fx-text-fill: #C0392B; -fx-font-weight: bold;");
                } else {
                    setStyle("");
                }
            }
        });

        DoubleBinding usableWidth = supplierTable.widthProperty().subtract(2);
        id.prefWidthProperty().bind(usableWidth.multiply(60.0 / 1300));
        name.prefWidthProperty().bind(usableWidth.multiply(190.0 / 1300));
        contact.prefWidthProperty().bind(usableWidth.multiply(190.0 / 1300));
        priorityScore.prefWidthProperty().bind(usableWidth.multiply(240.0 / 1300));
        delivery.prefWidthProperty().bind(usableWidth.multiply(100.0 / 1300));
        review.prefWidthProperty().bind(usableWidth.multiply(80.0 / 1300));
        category.prefWidthProperty().bind(usableWidth.multiply(170.0 / 1300));
        paymentStatus.prefWidthProperty().bind(usableWidth.multiply(130.0 / 1300));
        activeStatus.prefWidthProperty().bind(usableWidth.multiply(140.0 / 1300));
        id.setMinWidth(60);
        name.setMinWidth(190);
        contact.setMinWidth(190);
        priorityScore.setMinWidth(240);
        delivery.setMinWidth(100);
        review.setMinWidth(80);
        category.setMinWidth(170);
        paymentStatus.setMinWidth(130);
        activeStatus.setMinWidth(140);

        new SupplierFilling().readFromFile(suppliers);
        supplierTable.getItems().setAll(suppliers);

        categoryBox.getItems().add("All");
        for (Supplier s : suppliers) {
            if (!categoryBox.getItems().contains(s.getCategory())) categoryBox.getItems().add(s.getCategory());
        }
        categoryBox.setValue("All");

        searchField.textProperty().addListener((obs, oldText, newText) -> applyFilters());
    }
}
