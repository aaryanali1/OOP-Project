package ui;

import fileio.AuditLog;
import fileio.ThresholdFile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import types.InventoryItem;
import utilities.Utilities;

public class ThresholdWindow {

    @FXML private TextField good;
    @FXML private TextField ok;
    @FXML private TextField critical;

    @FXML private Label feedbackLabel;

    private InventoryItemTab inventoryItemTab;

    public void setInventoryItemTab(InventoryItemTab inventoryItemTab) { this.inventoryItemTab = inventoryItemTab; }

    @FXML
    void handleUpdate(ActionEvent event) {
        try {
            int goodValue = Integer.parseInt(good.getText());
            int okValue = Integer.parseInt(ok.getText());
            int criticalValue = Integer.parseInt(critical.getText());

            if (goodValue < 0 || okValue < 0 || criticalValue < 0) {
                feedbackLabel.setText("Values cannot be Negative");
                return;
            }

            if(!(goodValue > okValue && okValue > criticalValue)) {
                feedbackLabel.setText("Wrong format, Follow: GOOD > OK > CRITICAL");
                return;
            }

            InventoryItem.setGoodThreshold(goodValue);
            InventoryItem.setOkThreshold(okValue);
            InventoryItem.setCriticalThreshold(criticalValue);
            ThresholdFile.writeToFile(goodValue, okValue, criticalValue);

            inventoryItemTab.refreshAfterThreshold();
            Utilities.closeWindow(event);
            AuditLog.logEntry(Utilities.getCurrentUsername(), Utilities.getCurrentRole(), "Updated Threshold");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML void handleCancel(ActionEvent event) { Utilities.closeWindow(event); }

    public void initialize() {
        good.setText((String.valueOf((InventoryItem.getGoodThreshold()))));
        ok.setText((String.valueOf((InventoryItem.getOkThreshold()))));
        critical.setText((String.valueOf((InventoryItem.getCriticalThreshold()))));
    }
}
