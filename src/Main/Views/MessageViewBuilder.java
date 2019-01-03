package Main.Views;

import Main.Models.Constants;
import Main.Models.Message;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.*;

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
            System.out.println(1);
            file.createNewFile();
            System.out.println(2);
            System.out.println(file);
            System.out.println(3);
            FileOutputStream fos = new FileOutputStream(file, false);
            System.out.println(4);
            fos.write(message.getData());
            System.out.println(5);
            fos.close();
            System.out.println(6);
            return file;
        } catch (IOException ignored) {
            System.out.println("Exception in saving...");
        }
        return null;
    }

    private static Node showFile(byte[] data) {
        assert false;
        return null;
    }

    private static Node showImage(Message message) {
        File file = saveMessageDataToFile(message);
        assert (file != null);
        System.out.println(file);
        Image image = null;
        try {
            String path = "file://" + file.getCanonicalPath();
            System.out.println(path);
            image = new Image(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ImageView(image);
    }

    private static Node showText(String text) {
        // TODO: send emoji:happy
        Label label = new Label(text);
        label.setStyle("-fx-background-color: inherit");
        return label;
    }

    private static ImageView openImage(String path) {
        Image image = new Image(path);
        return new ImageView(image);
    }

    private static Node handleMedia(String text) throws FileNotFoundException {
        if (!text.startsWith("send "))
            throw new FileNotFoundException("Invalid format");
        ImageView imageView = handleImage("send src/resources/images/play-voice-icon.png");
        imageView.setStyle("-fx-cursor: CLOSED_HAND");
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        imageView.setOnMouseClicked(mouseEvent -> {
            final String musicFile = text.substring(5);
            Media sound = new Media(new File(musicFile).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
        });
        return imageView;
    }

    private static ImageView handleImage(String text) throws FileNotFoundException {
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
        return imageView;
    }

}


//            boolean isEmoji = false;
//            if (text.matches("send emoji:.+")) {
//                text = "src/resources/images/emojis/" + text.substring(11) + ".png";
//                isEmoji = true;
//            } else if (text.matches("send .+"))
//                text = text.substring(5);
//            else
//                throw new FileNotFoundException("Invalid text format");
//            System.out.println("trying open path: " + text);
//            File file = new File(text);
//            FileInputStream fileInputStream = new FileInputStream(file);
//            Image image = new Image(fileInputStream);
//            ImageView imageView = new ImageView(image);
//            if (isEmoji) {
//                imageView.setFitHeight(100);
//                imageView.setFitWidth(100);
//            } else {
//                imageView.maxHeight(500);
//                imageView.maxWidth(500);
//            }
//            node = imageView;
