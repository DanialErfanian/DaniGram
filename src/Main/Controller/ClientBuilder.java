package Main.Controller;

import Main.Models.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class ClientBuilder {

    static Client build(String name) throws IOException {
        final int port = 8080;
        final String server = "127.0.0.1";
        Socket socket;
        try {
            System.out.printf("trying connect to %s:%s\n", server, port);
            socket = new Socket(server, port);
        } catch (IOException e) {
            System.out.println("trying make SocketServer...");
            ServerSocket serverSocket = new ServerSocket(port, 1);
            System.out.println("SocketServer started.");
            System.out.println("Waiting for connection...");
            socket = serverSocket.accept();
            System.out.println("Connection found.");
        }
        System.out.println("Constructing Controller.Client.");
        User user = new User(name);
        return new Client(socket, user);
    }
}
