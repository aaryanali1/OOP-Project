package ui;

import fileio.AuditLog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import utilities.Utilities;

public class MainWindow {
    @FXML private Label currentUserLabel;
    @FXML private InventoryItemTab inventoryTabController;

    @FXML private TabPane tabPane;
    @FXML private Tab auditTab;

    @FXML
    void logout(ActionEvent event) {
        AuditLog.logEntry(Utilities.getCurrentUsername(), Utilities.getCurrentRole(), "Logged Out");
        Utilities.switchScene(event, "/view/LoginWindow.fxml", "Login", false);
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
            System.out.println(e.getMessage());
        }
    }

    public void initialize() {
        currentUserLabel.setText(Utilities.getCurrentRole() + ": " + Utilities.getCurrentUsername());
        if (!Utilities.getCurrentUser().canViewAuditLog()) {
            tabPane.getTabs().remove(auditTab);
        }
    }
}
