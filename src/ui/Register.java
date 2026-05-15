package ui;

import fileio.UserFile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import types.Admin;
import types.Staff;
import types.User;

import java.util.ArrayList;

public class Register {

    @FXML
    private RadioButton isAdmin;

    @FXML
    private RadioButton isStaff;

    @FXML
    private PasswordField password;

    @FXML
    private ToggleGroup role;

    @FXML
    private TextField username;

    @FXML
    void handleRegister(ActionEvent event) {
        String user = username.getText();
        String pass = password.getText();

        Alert alert = new Alert(Alert.AlertType.ERROR);

        ArrayList<User> users = new ArrayList<>();
        UserFile.readFromFile(users);

        if(user.isEmpty() || pass.isEmpty()) {
            alert.setHeaderText("Unable to Register");
            alert.setContentText("Please fill all the fields");
            alert.show();
            return;
        }

        if(!isAdmin.isSelected() && !isStaff.isSelected()) {
            alert.setHeaderText("Unable to Register");
            alert.setContentText("Please select user type");
            alert.show();
            return;
        }

        for (User u : users) {
            if(u.getUsername().equals(user)) {
                alert.setHeaderText("Unable to Register");
                alert.setContentText("Username already exists");
                alert.show();
                return;
            }
        }

        User newUser = isAdmin.isSelected() ? new Admin(user, pass) : new Staff(user, pass);
        users.add(newUser);
        UserFile.writeToFile(users);
        alert.setHeaderText("Successfully Registered");
        alert.show();
    }

    @FXML
    void openLogin(ActionEvent event) {
        SceneSwitch.switchScene(event, "/view/login.fxml", false);
    }

}
