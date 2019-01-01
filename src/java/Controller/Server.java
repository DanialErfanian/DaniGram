package java.Controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        final int port = 8080;
        final String server = "127.0.0.1";
        Socket socket = null;
        try {
            System.out.printf("trying connect to %s:%s\n", server, port);
            socket = new Socket(server, port);
        } catch (IOException e) {
            try {
                System.out.println("trying make SocketServer...");
                ServerSocket serverSocket = new ServerSocket(port, 1);
                System.out.println("SocketServer started.");
                System.out.println("Waiting for connection...");
                socket = serverSocket.accept();
                System.out.println("Connection found.");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        if (socket != null) {
            System.out.println("Constructing Controller.Client.");
            Client client = new Client(socket);
            client.Run();
        }
    }
}
