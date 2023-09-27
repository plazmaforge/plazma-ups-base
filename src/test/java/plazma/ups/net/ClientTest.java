package plazma.ups.net;

import org.junit.Test;

import java.io.IOException;
import java.net.ConnectException;

import static org.junit.Assert.*;

public class ClientTest {

    @Test
    public void testSendMessage() throws IOException {
        Server server = new Server();

        Thread thread = new Thread(() -> {
            try {
                server.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Client client = new Client();
        String response = client.sendMessage("Hi!");
        server.stop();

        assertEquals("Echo: Hi!", response);

    }

    @Test
    public void testThrowWhenConnectionRefused() throws IOException {
        Client client = new Client();

        assertThrows(ConnectException.class, () -> {
            client.sendMessage("Hi!");
        });

    }

}
