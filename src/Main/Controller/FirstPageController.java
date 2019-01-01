package Main.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class FirstPageController {

    public TextField userName;

    public void startChat(ActionEvent actionEvent) throws IOException {
        String name = userName.getText();
        Client client = ClientBuilder.build(name);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../resources/Views/ClientUI.fxml"));
        Scene scene = new Scene(loader.load());
        UIController controller = loader.getController();
        Stage stage = (Stage) userName.getScene().getWindow();
        controller.setClient(client);
        stage.setScene(scene);
        stage.show();
    }
}
