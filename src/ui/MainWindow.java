package ui;

import fileio.AuditLog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import utilities.Utilities;

public class MainWindow {
    @FXML private Label currentUserLabel;

    @FXML private InventoryItemTab inventoryTabController;

    @FXML
    void logout(ActionEvent event) {
        Utilities.switchScene(event, "/view/LoginWindow.fxml", "Login", false);
        AuditLog.logEntry(Utilities.getCurrentUsername(), Utilities.getCurrentRole(), "Logged Out");
    }

    @FXML
    void threshold(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ThresholdWindow.fxml"));
            Parent root = loader.load();
            ThresholdWindow controller = loader.getController();
            controller.setInventoryItemTab(inventoryTabController);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Edit Threshold");
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        currentUserLabel.setText(Utilities.getCurrentRole() + ": " + Utilities.getCurrentUsername());
    }
}
