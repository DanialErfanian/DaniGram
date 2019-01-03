package Main.Views;

import Main.Models.Constants;
import Main.Models.Message;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

// TODO: style fasele pm az box tosh
// TODO: send video

public class MessageViewBuilder {
    public static HBox build(Message message) throws Exception {
        HBox outerHBox = new HBox();
        HBox innerHBox = new HBox();
        Node node;
        node = getMessageView(message);
        innerHBox.getChildren().add(node);
        innerHBox.setOnMouseClicked(node.getOnMouseClicked());
        outerHBox.getChildren().add(innerHBox);
        outerHBox.getStyleClass().add("message-outer-HBox");
        return outerHBox;
    }

    private static Node getMessageView(Message message) throws Exception {
        switch (message.getType()) {
            case TEXT:
                String text = message.getText();
                return showText(text);
            case IMAGE:
                return showImage(message);
            case FILE:
                return showFile(message.getData());
        }
        throw new Exception("message type isn't valid!");
    }

    private static File saveMessageDataToFile(Message message) {
        try {
            File file = new File(Constants.DOWNLOAD_PATH + message.getFileName());
            System.out.println(file);
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file, false);
            fos.write(message.getData());
            fos.close();
            return file;
        } catch (IOException ignored) {
            System.out.println("Exception in saving...");
        }
        return null;
    }

    private static Node showFile(byte[] data) {
        // TODO support file
        assert false;
        return null;
    }

    private static File getFile(Message message) {
        return saveMessageDataToFile(message);
    }

    private static Node showImage(Message message) {
        return showImage(getFile(message));
    }

    private static ImageView showImage(File file) {
        if (file == null)
            return null;
        Image image = null;
        try {
            String path = "file://" + file.getCanonicalPath();
            System.out.println(path);
            image = new Image(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (image != null)
            return new ImageView(image);
        return null;
    }

    private static Node showText(String text) {
        if (text.startsWith("send emoji:")) {
            String path = text.substring(11);
            path = "src/resources/images/emojis/" + path + ".png";
            File file = new File(path);
            ImageView image = showImage(file);
            if (image != null) {
                image.setFitHeight(100);
                image.setFitWidth(100);
                return image;
            }
        }
        Label label = new Label(text);
        label.setStyle("-fx-background-color: inherit");
        return label;
    }
}