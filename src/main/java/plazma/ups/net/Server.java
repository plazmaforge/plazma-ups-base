package plazma.ups.net;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static final int DEFAULT_PORT = 3000;
    private int port;
    private boolean processing;

    public Server() {
        this(DEFAULT_PORT);
    }

    public Server(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        if (processing) {
            throw new RuntimeException("Server is started");
        }

        processing = true;

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("Start");
            while (processing) {
                try (Socket socket = serverSocket.accept();
                     BufferedInputStream is = new BufferedInputStream(socket.getInputStream());
                     BufferedOutputStream os = new BufferedOutputStream(socket.getOutputStream())) {

                    byte[] buf = new byte[100];
                    int count = is.read(buf);
                    String response = "Echo: "  + new String(buf, 0, count);
                    os.write(response.getBytes());
                    os.flush();
                }

            }
            System.out.println("Finish");

        }

    }

    public void stop() {
        processing = false;
    }

    public static void main(String[] args) {
        Server server = new Server();
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
