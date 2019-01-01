package Main.Controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private DataOutputStream output;
    private DataInputStream input;

    private void handleNewMessages() {
        try {
            while (true) {
                if (input.available() > 0) {
                    String message = input.readUTF();
                    System.out.println(message);
                }
                Thread.sleep(30);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void handleSendMessages() {
        Scanner scanner = new Scanner(System.in);
        try {
            while (true) {
                String message = scanner.nextLine();
                output.writeUTF(message);
                if (message.equals("exit"))
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void Run() {
        new Thread(this::handleSendMessages).start();
        handleNewMessages();
    }

    public Client(Socket socket) {
        try {
            this.input = new DataInputStream(socket.getInputStream());
            this.output = new DataOutputStream(socket.getOutputStream());
            this.socket = socket;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
