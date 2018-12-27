import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static String server = "localhost";

    private static void handleNewMessages(Socket socket) {
        try {
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            while (true) {
                if (inputStream.available() > 0) {
                    String message = inputStream.readUTF();
                    System.out.println(message);
                }
                Thread.sleep(30);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        server = args[0];
        Socket socket = null;
        try {
            int port = 8080;
            socket = new Socket(server, port);
            Socket finalSocket = socket;
            Thread thread = new Thread(() -> handleNewMessages(finalSocket));
            thread.start();
            handleSendMessages(socket);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private static void handleSendMessages(Socket socket) {
        Scanner scanner = new Scanner(System.in);
        try {
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            while (true) {
                String message = scanner.nextLine();
                outputStream.writeUTF(message);
                if (message.equals("exit"))
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
