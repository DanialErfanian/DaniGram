package resources.Views;

import Main.Models.Message;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

// TODO close application after closeRequest
public class MessageViewBuilder {
    public static HBox build(Message message) {
        HBox outerHBox = new HBox();
        HBox innerHBox = new HBox();
        Label label = new Label(message.getText());
        innerHBox.getChildren().add(label);
        outerHBox.getChildren().add(innerHBox);
        outerHBox.getStyleClass().add("message-outer-HBox");
        return outerHBox;
    }
}
