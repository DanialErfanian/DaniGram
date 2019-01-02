package Main.Views;

import Main.Models.Message;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

// TODO: scroll down if needed
// TODO: style fasele pm az box tosh
// TODO: send meta data through network
// TODO: send video
// TODO: send files with file picker

public class MessageViewBuilder {
    public static HBox build(Message message) {
        HBox outerHBox = new HBox();
        HBox innerHBox = new HBox();
        Node node;
        try {
            String text = message.getText();
            boolean isEmoji = false;
            if (text.matches("send emoji:.+")) {
                text = "src/resources/images/emojis/" + text.substring(11) + ".png";
                isEmoji = true;
            } else if (text.matches("send .+"))
                text = text.substring(5);
            else
                throw new FileNotFoundException("Invalid text format");
            System.out.println("trying open path: " + text);
            File file = new File(text);
            FileInputStream fileInputStream = new FileInputStream(file);
            Image image = new Image(fileInputStream);
            ImageView imageView = new ImageView(image);
            if (isEmoji) {
                imageView.setFitHeight(100);
                imageView.setFitWidth(100);
            } else {
                imageView.maxHeight(500);
                imageView.maxWidth(500);
            }
            node = imageView;
        } catch (FileNotFoundException e) {
            Label label = new Label(message.getText());
            label.setStyle("-fx-background-color: inherit");
            node = label;
        }
        innerHBox.getChildren().add(node);
        outerHBox.getChildren().add(innerHBox);
        outerHBox.getStyleClass().add("message-outer-HBox");
        return outerHBox;
    }
}
