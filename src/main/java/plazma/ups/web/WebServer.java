package plazma.ups.web;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class WebServer {

    private static final int DEFAULT_PORT = 3000;
    private int port;
    private String webAppPath;
    private boolean processing;

    public WebServer() {
        this(DEFAULT_PORT);
    }

    public WebServer(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getWebAppPath() {
        return webAppPath;
    }

    public void setWebAppPath(String webAppPath) {
        this.webAppPath = webAppPath;
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
                        continue;
                    }

                    String line = lines[0];
                    String[] elements = line.split(" ");
                    if (elements.length < 2) {
                        handleBadRequest(writer);
                        continue;
                    }
                    String method = elements[0];

                    if (!method.startsWith("GET")) {
                        handleInternalServerError(writer);
                        continue;
                    }

                    String webAppDir = getWebAppDir();
                    if (webAppDir == null || webAppDir.isEmpty()) {
                        // Configuration error
                        handleInternalServerError(writer);
                        continue;
                    }

                    String path = elements[1];
                    //System.out.println("PATH:" + path);
                    if (path.equals("/")) {
                        path = "/index.html";
                    }

                    String fileName = getFileName(path);
                    //System.out.println("FILE:" + fileName);
                    String fileContent = null;

                    try {
                        fileContent = readFileContent(fileName);
                    } catch (IOException e) {
                        handleNotFound(writer);
                        continue;
                    }

                    // write response
                    writer.write("HTTP/1.1 200 OK");
                    writer.newLine();
                    writer.newLine();

                    writer.write(fileContent);
                    //writer.write("Hello world!");

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

    protected String readFileContent(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)))) {
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append('\n');
            }
            return builder.toString();
        }
    }

    protected String getFileName(String path) {
        return getWebAppDir() + path;
    }

    protected String getWebAppDir() {
        // TODO: For test only
        if (webAppPath == null) {
            webAppPath = "src/test/resources/WebApp";
        }
        return System.getProperty("user.dir") + "/" + webAppPath;
    }

    protected void handleBadRequest(BufferedWriter writer) throws IOException {
        writer.write("HTTP/1.1 400 Bad Request");
    }

    protected void handleNotFound(BufferedWriter writer) throws IOException {
        writer.write("HTTP/1.1 404 Not Found");
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
