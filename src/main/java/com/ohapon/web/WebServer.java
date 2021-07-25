package com.ohapon.web;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class WebServer {

    private static final int DEFAULT_PORT = 3000;
    private int port;
    private boolean processing;

    public WebServer() {
        this(DEFAULT_PORT);
    }

    public WebServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        if (processing) {
            throw new RuntimeException("Server is started");
        }

        processing = true;

        try (ServerSocket serverSocket = new ServerSocket(3000)) {

            System.out.println("WebServer: Start");
            while (processing) {


                try (Socket socket = serverSocket.accept();
                     BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

                    String[] lines = readLines(reader);

                    if (lines.length == 0) {
                        handleBadRequest(writer);
                        return;
                    }

                    String line = lines[0];
                    String[] elements = line.split(" ");
                    if (elements.length < 2) {
                        handleBadRequest(writer);
                        return;
                    }
                    String method = elements[0];
                    String path = elements[1];

                    if (!method.startsWith("GET")) {
                        handleInternalServerError(writer);
                        return;
                    }

                    // write response
                    writer.write("HTTP/1.1 200 OK");
                    writer.newLine();
                    writer.newLine();
                    writer.write("Hello world!");

                }

            }
            System.out.println("WebServer: Finish");

        }

    }

    public void stop() {
        processing = false;
    }

    protected String[] readLines(BufferedReader reader) throws IOException {
        String line = null;
        List<String> lines = new ArrayList<>();
        while (!(line = reader.readLine()).isEmpty()) {
            lines.add(line);
        }
        return lines.toArray(new String[0]);
    }

    protected void handleBadRequest(BufferedWriter writer) throws IOException {
        writer.write("HTTP/1.1 400 Bad Request");
    }

    protected void handleInternalServerError(BufferedWriter writer) throws IOException {
        writer.write("HTTP/1.1 500 Internal Server Error");
    }

    public static void main(String[] args) {
        WebServer server = new WebServer();
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
