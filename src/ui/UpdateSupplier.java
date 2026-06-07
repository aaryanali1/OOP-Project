package ui;

import fileio.AuditLog;
import fileio.SupplierFilling;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import types.Supplier;
import utilities.Utilities;

import java.util.ArrayList;

public class UpdateSupplier {
    @FXML private TextField category;
    @FXML private TextField contact;
    @FXML private TextField deliveryTime;
    @FXML private TextField reviewScore;
    @FXML private TextField supplierName;
    @FXML private Label feedbackLabel;

    @FXML private ComboBox<String> paymentStatus;

    private ArrayList<Supplier> suppliers;
    private TableView<Supplier> supplierTable;
    private Supplier supplier;

    public void setSuppliers(ArrayList<Supplier> suppliers) { this.suppliers = suppliers; }

    public void setSupplierTable(TableView<Supplier> supplierTable) { this.supplierTable = supplierTable; }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
        supplierName.setText(supplier.getSupplierName());
        contact.setText(supplier.getContact());
        deliveryTime.setText(String.valueOf(supplier.getDeliveryTime()));
        reviewScore.setText(String.valueOf(supplier.getReviewScore()));
        category.setText(supplier.getCategory());
        paymentStatus.setValue(supplier.getPaymentStatus());
    }

    @FXML
    void handleUpdate(ActionEvent event) {
        String supplierName, contact, category;
        int deliveryTime, reviewScore;

        try {
            supplierName = this.supplierName.getText().trim();
            contact = this.contact.getText().trim();
            category = this.category.getText().trim();

            if (supplierName.isEmpty() || contact.isEmpty() || category.isEmpty()) {
                feedbackLabel.setText("Text Fields cannot be Empty");
                return;
            }

            deliveryTime = Integer.parseInt(this.deliveryTime.getText());
            reviewScore = Integer.parseInt(this.reviewScore.getText());

            if (deliveryTime < 0) {
                feedbackLabel.setText("Delivery Time cannot be Negative");
                return;
            }

            if (reviewScore < 1 || reviewScore > 5) {
                feedbackLabel.setText("Review Score should be between 1 and 5");
                return;
            }

            supplier.setSupplierName(supplierName);
            supplier.setContact(contact);
            supplier.setCategory(category);
            supplier.setDeliveryTime(deliveryTime);
            supplier.setReviewScore(reviewScore);
            supplier.setPaymentStatus(paymentStatus.getValue());
            supplierTable.getItems().setAll(suppliers);
            supplierTable.refresh();
            new SupplierFilling().writeToFile(suppliers);

            Utilities.closeWindow(event);
            AuditLog.logEntry(Utilities.getCurrentUsername(), Utilities.getCurrentRole(), "Update Supplier '(" + supplier.getId() + ") " + supplier.getSupplierName() + "'");
        }
        catch (NumberFormatException e) {
            feedbackLabel.setText("Please enter valid Numbers");
        }
        catch (Exception e) {
            feedbackLabel.setText("Error Updating Supplier");
            e.printStackTrace();
        }
    }

    @FXML
    void handleCancel(ActionEvent event) {
        Utilities.closeWindow(event);
    }

    public void initialize() {
        paymentStatus.getItems().addAll("Clear", "Pending", "Overdue");
    }
}
