package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneSwitch {

    public static void switchScene(ActionEvent event, String FILE_PATH, boolean resizable) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneSwitch.class.getResource(FILE_PATH));
            Parent root = loader.load();

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setResizable(resizable);
            stage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}