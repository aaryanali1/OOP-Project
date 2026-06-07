package ui;

import fileio.AuditLog;
import fileio.UserFilling;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import types.Admin;
import types.Staff;
import types.User;
import utilities.Utilities;

import java.util.ArrayList;

public class RegisterWindow {

    @FXML private RadioButton isAdmin;
    @FXML private RadioButton isStaff;
    @FXML private PasswordField password;
    @FXML private ToggleGroup role;
    @FXML private TextField username;

    @FXML
    void handleRegister(ActionEvent event) {
        String user = username.getText();
        String pass = password.getText();

        Alert alert = new Alert(Alert.AlertType.ERROR);

        if (user.isEmpty() || pass.isEmpty()) {
            alert.setHeaderText("Unable to Register");
            alert.setContentText("Please enter Username and Password");
            alert.show();
            return;
        }

        if (!isAdmin.isSelected() && !isStaff.isSelected()) {
            alert.setHeaderText("Unable to Register");
            alert.setContentText("Please select User type");
            alert.show();
            return;
        }

        ArrayList<User> users = new ArrayList<>();
        UserFilling.readFromFile(users);

        for (User u : users) {
            if (u.getUsername().equals(user)) {
                alert.setHeaderText("Unable to Register");
                alert.setContentText("Username already exists");
                alert.show();
                return;
            }
        }

        User newUser = isAdmin.isSelected() ? new Admin(user, pass) : new Staff(user, pass);
        users.add(newUser);
        UserFilling.writeToFile(users);
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Registered Successfully");
        alert.setContentText("User Registered Successfully");
        alert.show();
        AuditLog.logEntry(Utilities.getCurrentUsername(), Utilities.getCurrentRole(), "Registered User");
    }

    @FXML
    void openLogin(ActionEvent event) {
        Utilities.switchScene(event, "/view/LoginWindow.fxml", "Login",false);
    }

}
