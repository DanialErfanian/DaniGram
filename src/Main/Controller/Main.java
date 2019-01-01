package Main.Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../resources/Views/FirstPage.fxml"));
        primaryStage.setTitle("DaniGram");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("../../resources/images/icon.png")));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
