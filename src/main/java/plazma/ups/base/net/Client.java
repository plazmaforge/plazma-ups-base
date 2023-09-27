package plazma.ups.base.net;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {

    public static final String DEFAULT_HOST = "localhost";
    public static final int DEFAULT_PORT = 3000;

    private String host;
    private int port;

    public Client() {
        this(DEFAULT_HOST, DEFAULT_PORT);
    }

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }


    public String sendMessage(String message) throws IOException {

        try (Socket socket = new Socket(host, port);
             BufferedInputStream is = new BufferedInputStream(socket.getInputStream());
             BufferedOutputStream os = new BufferedOutputStream(socket.getOutputStream())) {

            os.write(message.getBytes());
            os.flush();

            byte[] buf = new byte[100];
            int count = is.read(buf);
            String response = new String(buf, 0, count);
            return response;
        }

    }

    public static void main(String[] args) {
        Client client = new Client();
        try {
            String response = client.sendMessage("Hello!");
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
