package utilities;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import types.User;

import java.util.ArrayList;


public class Utilities {
    public static void switchScene(ActionEvent event, String FILE_PATH, String title, boolean resizable) {
        try {
            FXMLLoader loader = new FXMLLoader(Utilities.class.getResource(FILE_PATH));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setMinHeight(0);
            stage.setMinWidth(0);
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.setResizable(resizable);
            stage.sizeToScene();
            stage.centerOnScreen();
            stage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static User currentUser;

    public static void setCurrentUser(User user) { currentUser = user; }

    public static User getCurrentUser() { return currentUser; }

    public static String getCurrentUsername() { return currentUser.getUsername(); }

    public static String getCurrentRole() { return currentUser.getRole(); }

    public static void closeWindow(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
