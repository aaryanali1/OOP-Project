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

    @FXML private TextField bad;
    @FXML private Label feedbackLabel;
    @FXML private TextField good;
    @FXML private TextField ok;

    private InventoryItemTab inventoryItemTab;

    public void setInventoryItemTab(InventoryItemTab inventoryItemTab) { this.inventoryItemTab = inventoryItemTab; }

    @FXML
    void handleUpdate(ActionEvent event) {
        try {
            int goodValue = Integer.parseInt(good.getText());
            int okValue = Integer.parseInt(ok.getText());
            int badValue = Integer.parseInt(bad.getText());

            if (goodValue < 0 || okValue < 0 || badValue < 0) {
                feedbackLabel.setText("Values cannot be Negative");
                return;
            }

            if(!(goodValue > okValue && okValue > badValue)) {
                feedbackLabel.setText("Wrong format, Follow: GOOD > OK > BAD");
                return;
            }

            InventoryItem.setGoodThreshold(goodValue);
            InventoryItem.setOkThreshold(okValue);
            InventoryItem.setBadThreshold(badValue);
            ThresholdFile.writeToFile(goodValue, okValue, badValue);

            inventoryItemTab.refreshAfterThreshold();
            Utilities.closeWindow(event);
            AuditLog.logEntry(Utilities.getCurrentUsername(), Utilities.getCurrentRole(), "Updated Threshold");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleCancel(ActionEvent event) {
        Utilities.closeWindow(event);
    }

    public void initialize() {
        good.setText((String.valueOf((InventoryItem.getGoodThreshold()))));
        ok.setText((String.valueOf((InventoryItem.getOkThreshold()))));
        bad.setText((String.valueOf((InventoryItem.getBadThreshold()))));
    }

}
