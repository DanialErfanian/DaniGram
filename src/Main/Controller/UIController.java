package Main.Controller;

import Main.Models.Message;
import javafx.application.Platform;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import Main.Views.MessageViewBuilder;

import java.io.IOException;

public class UIController {
    public TextField textInput;
    public VBox messages;
    private Client client = null;
    private String name;

    private void setClient() {
        new Thread(() -> {
            try {
                this.client = ClientBuilder.build(name);
                client.setController(this);
                client.Run();
                System.out.println("Client found and is listening...");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void sendMessage() {
        String text = textInput.getText();
        if (client != null)
            client.sendMessage(text);
        textInput.clear();
        System.out.println(text);
    }

    void setName(String name) {
        this.name = name;
        setClient();
    }

    void showMessage(Message message) {
        Platform.runLater(() -> {
            HBox hBox = MessageViewBuilder.build(message);
            if (message.getSender() == this.client.getUser())
                hBox.getStyleClass().add("user-message");
            else
                hBox.getStyleClass().add("audience-message");
            messages.getChildren().add(hBox);
        });
    }
}
