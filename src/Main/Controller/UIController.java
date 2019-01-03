package Main.Controller;

import Main.Models.Message;
import Main.Models.User;
import Main.Views.MessageViewBuilder;
import javafx.application.Platform;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class UIController {
    public TextField textInput;
    public VBox messages;
    public ScrollPane scrollPane;
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

    private void scrollToBottom() {
        scrollPane.vvalueProperty().bind(messages.heightProperty());
    }

    private void sendMessage(String text) {
        if (client != null)
            client.sendMessage(text);
        textInput.clear();
        System.out.println(text);
    }

    private void sendMessage(Message message) {
        if (client != null)
            client.sendMessage(message);
    }

    public void sendMessage() {
        sendMessage(textInput.getText());
    }

    void setName(String name) {
        this.name = name;
        setClient();
    }

    void showMessage(Message message) {
        Platform.runLater(() -> {
            HBox hBox;
            try {
                hBox = MessageViewBuilder.build(message);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            if (message.getSender() == this.client.getUser()) {
                hBox.getStyleClass().add("user-message");
                hBox.getChildren().get(0).setStyle("-fx-background-color: #73b9f5");
            } else {
                hBox.getChildren().get(0).setStyle("-fx-background-color: #2b5278");
                hBox.getStyleClass().add("audience-message");
            }
            messages.getChildren().add(hBox);
            Platform.runLater(this::scrollToBottom);
        });
    }

    public void showChooseFileDialog() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                Message.Type type = Message.getFileType(selectedFile);
                User user = this.client.getUser();
                Message message = new Message(user,
                        type,
                        Files.readAllBytes(selectedFile.toPath()),
                        selectedFile.getName());
                sendMessage(message);
            } catch (IOException ignored) {
            }
        }
    }

}
