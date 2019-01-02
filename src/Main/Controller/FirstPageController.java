package Main.Controller;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class FirstPageController {

    public TextField userName;

    public void startChat() throws IOException {
        String name = userName.getText();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../resources/Views/ClientUI.fxml"));
        Scene scene = new Scene(loader.load());
        UIController controller = loader.getController();
        Stage stage = (Stage) userName.getScene().getWindow();
        controller.setName(name);
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(windowEvent -> {
            stage.close();
            Platform.exit();
            System.out.println("Seems we need close application manually :(");
            System.exit(0);
        });
    }
}
