
package homePage;

import javafx.stage.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class HomePageControl {

    Stage window;
    Scene scene;
    public void addMedicine_Click(ActionEvent actionEvent) throws IOException {

        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Add Medicine");
        scene = new Scene(FXMLLoader.load(getClass().getResource("addMedicine.fxml")));
        window.setScene(scene);
        window.show();
    }


    public void searchMedicine_Click(ActionEvent actionEvent) throws IOException {

        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Add Medicine");
        scene = new Scene(FXMLLoader.load(getClass().getResource("searchMedicine.fxml")));
        window.setScene(scene);
        window.show();
    }

}
