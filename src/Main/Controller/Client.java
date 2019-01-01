package Main.Controller;

import Main.Models.Message;
import Main.Models.User;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    private UIController controller;
    private DataOutputStream output;
    private DataInputStream input;
    private User user;

    public User getUser() {
        return user;
    }

    private void handleNewMessages() {
        try {
            while (true) {
                if (input.available() > 0) {
                    String json = input.readUTF();
                    System.out.println("new message found");
                    YaGson mapper = new YaGsonBuilder().setPrettyPrinting().create();
                    Message message = mapper.fromJson(json, Message.class);
                    controller.showMessage(message);
                }
                Thread.sleep(30);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    void sendMessage(String text) {
        try {
            YaGson mapper = new YaGsonBuilder().setPrettyPrinting().create();
            Message message = new Message(text, user);
            this.controller.showMessage(message);
            output.writeUTF(mapper.toJson(message));
            System.out.println("message sent.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("message sending failed.");
        }
    }

    void Run() {
        new Thread(this::handleNewMessages).start();
    }

    public Client(Socket socket, User user) {
        try {
            this.input = new DataInputStream(socket.getInputStream());
            this.output = new DataOutputStream(socket.getOutputStream());
            this.user = user;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void setController(UIController controller) {
        this.controller = controller;
    }
}
