import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private static ServerSocket serverSocket;
    private static ArrayList<Socket> clients = new ArrayList<>();// remove client after closed
    private static PrintStream printStream;

    private static void sendToAll(String message) {
        for (Socket client : clients) {
            DataOutputStream out;
            try {
                out = new DataOutputStream(client.getOutputStream());
                out.writeUTF(message);
                printStream.println("message sent.");
            } catch (IOException e) {
                printStream.println("message sending failed.");
                e.printStackTrace();
            }
        }
    }

    private static void initialize() {
        try {
            File file = new File("log");
            printStream = new PrintStream(file);
            System.out.println("logger set");
            int port = 8080;
            serverSocket = new ServerSocket(port, 5);
            printStream.println("Server is running on port " + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleSocket(Socket socket) {
        try {
            clients.add(socket);
            printStream.println("we found a new connection from IP = " + socket.getInetAddress() +
                    " port = " + socket.getPort());
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            while (true) {
                if (inputStream.available() > 0) {
                    String message = inputStream.readUTF();
                    printStream.println("new message from: " + socket.getInetAddress() + " message: " + message);
                    message = socket.getInetAddress().toString() + ": " + message;
                    sendToAll(message);
                }
                Thread.sleep(30);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    clients.remove(socket);
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        initialize();
        for (int i = 0; i < 6; i++) {
            Thread thread = new Thread(() -> {
                try {
                    handleSocket(serverSocket.accept());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
        }
    }
}
