package ui;

import fileio.AuditLog;
import fileio.UserFilling;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import types.User;
import utilities.Utilities;

import java.util.ArrayList;

public class LoginWindow {

    @FXML private PasswordField password;
    @FXML private TextField username;

    @FXML
    void handleLogin(ActionEvent event) {
        String user = username.getText();
        String pass = password.getText();

        Alert alert = new Alert(Alert.AlertType.ERROR);

        if (user.isEmpty() || pass.isEmpty()) {
            alert.setHeaderText("Unable to Login");
            alert.setContentText("Please enter Username and Password");
            alert.show();
            return;
        }

        ArrayList<User> users = new ArrayList<>();
        UserFilling.readFromFile(users);

        for (User u : users) {
            if (u.getUsername().equals(user) && u.getPassword().equals(pass)) {
                try {
                    Utilities.setCurrentUser(u);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainWindow.fxml"));
                    Parent root = loader.load();
                    Stage stage = (Stage) username.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.setResizable(true);
                    stage.setTitle("Inventory & Supplier Management System");
                    stage.centerOnScreen();
                    stage.setMinWidth(1316);
                    stage.setMinHeight(800);
                    stage.show();
                    AuditLog.logEntry(u.getUsername(), u.getRole(), "Logged In");
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }
        }

        alert.setHeaderText("Unable to Login");
        alert.setContentText("Invalid Username or Password");
        alert.show();
    }

    @FXML
    void openRegister(ActionEvent event) {
        Utilities.switchScene(event, "/view/RegisterWindow.fxml", "Register", false);
    }

}
