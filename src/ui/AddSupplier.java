package ui;

import fileio.AuditLog;
import fileio.SupplierFilling;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import types.Supplier;
import utilities.Utilities;

import java.util.ArrayList;

public class AddSupplier {
    @FXML private TextField supplierName;
    @FXML private TextField category;
    @FXML private TextField contact;
    @FXML private TextField deliveryTime;
    @FXML private TextField reviewScore;
    @FXML private Label feedbackLabel;

    private ArrayList<Supplier> suppliers;
    private TableView<Supplier> supplierTable;
    private SupplierTab supplierTab;

    public void setSuppliers(ArrayList<Supplier> suppliers) { this.suppliers = suppliers; }

    public void setSupplierTable(TableView<Supplier> supplierTable) { this.supplierTable = supplierTable; }

    public void setSupplierTab(SupplierTab supplierTab) { this.supplierTab = supplierTab; }

    @FXML
    void handleAdd(ActionEvent event) {
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

            int id = getNextID();
            suppliers.add(new Supplier(id, supplierName, contact, deliveryTime, category, "Clear", reviewScore, true));
            supplierTab.refreshCategory();
            supplierTable.getItems().setAll(suppliers);
            new SupplierFilling().writeToFile(suppliers);

            AuditLog.logEntry(Utilities.getCurrentUsername(), Utilities.getCurrentRole(), "Added Supplier '(" + id + ") " + supplierName + "'");
            Utilities.closeWindow(event);
        }
        catch (NumberFormatException e) {
            feedbackLabel.setText("Please enter valid Numbers");
        }
        catch (Exception e) {
            feedbackLabel.setText("Error Adding Supplier");
            e.printStackTrace();
        }
    }

    @FXML void handleCancel(ActionEvent event) { Utilities.closeWindow(event); }

    private int getNextID() {
        int maxID = 0;

        for (Supplier s : suppliers) {
            int currentID = s.getId();
            if (currentID > maxID) maxID = currentID;
        }
        return maxID + 1;
    }
}
