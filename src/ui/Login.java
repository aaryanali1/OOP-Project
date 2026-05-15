package ui;

import fileio.UserFile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import types.User;

import java.util.ArrayList;

public class Login {

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    void handleLogin(ActionEvent event) {
        String user = username.getText();
        String pass = password.getText();

        Alert alert = new Alert(Alert.AlertType.ERROR);

        if(user.isEmpty() || pass.isEmpty()) {
            alert.setHeaderText("Unable to Login");
            alert.setContentText("Please enter username and password to proceed");
            alert.show();
            return;
        }

        ArrayList<User> users = new ArrayList<>();
        UserFile.readFromFile(users);

        for (User u : users) {
            if (u.getUsername().equals(user) && u.getPassword().equals(pass)) {
                SceneSwitch.switchScene(event, "/view/mainframe.fxml", true);
                return;
            }
        }

        alert.setHeaderText("Unable to Login");
        alert.setContentText("Invalid username or password");
        alert.show();
    }

    @FXML
    void openRegister(ActionEvent event) {
        SceneSwitch.switchScene(event, "/view/register.fxml", false);
    }

}
