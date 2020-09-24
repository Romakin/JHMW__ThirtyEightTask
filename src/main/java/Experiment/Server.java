package Experiment;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {

    int port;
    int serverType;
    CopyOnWriteArrayList tests;

    public Server(int port, int serverType, CopyOnWriteArrayList tests) {
        this.port = port;
        this.serverType = serverType;
        this.tests = tests == null ? new CopyOnWriteArrayList() : tests;
    }

    public void start() {
        try (ServerSocket servSocket = new ServerSocket(port)) {
            while (!Thread.currentThread().isInterrupted()) {
                try (
                        Socket socket = servSocket.accept();
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
                ) {
                    StringBuilder sb = new StringBuilder();
                    String line;
                    if (serverType == 1) { //Media Server
                        String requestType = null;
                        while ((line = in.readLine()) != null) {
                            if (requestType == null)
                                requestType = line.replaceAll(".*(GET|POST).*","$1");
                            sb.append(line + "\n");
                            if (line.isEmpty()) {
                                break;
                            }
                        }

                        if ("GET".equals(requestType)) {
                            String answer = getHTML();
                            out.println("HTTP/1.1 200 OK\r\n" +
                                    "Content-Type: text/html\r\n" +
                                    "Content-Length: " + answer.length() + "\r\n");
                            out.println(answer);
                            out.println("");
                        } else if ("POST".equals(requestType)) {
                            String request = sb.toString();
                            try {
                                String firstLine = request.split("\\s")[1];
                                String json = URLDecoder.decode(firstLine, StandardCharsets.UTF_8.name());
                                json = json.substring(1);
                                tests.add(json);
                            } catch (Exception e) {}
                            out.println("HTTP/1.1 200 OK\r\n");
                        } else {
                            out.println("HTTP/1.1 200 OK\r\n");
                        }
                    } else { // Report server
                        while ((line = in.readLine()) != null) {

                            out.println(getStat(line));

                            if (line.equals("end")) {
                                System.out.println("server shutdown");
                                break;
                            }
                        }
                        if (line.equals("end")) {
                            servSocket.close();
                            return;
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace(System.out);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public static boolean checkPort(int portNum) {
        try (
                ServerSocket serverSocket = new ServerSocket();
        ) {
            serverSocket.setReuseAddress(false);
            serverSocket.bind(new InetSocketAddress(InetAddress.getByName("localhost"), portNum), 1);
            return true;
        } catch (IOException ex) {
            System.out.println("Порт занят, попробуйте другой");
            return false;
        }
    }

    private String getStat(String line) {
        StringBuilder result = new StringBuilder();
        int ind = -1;
        try {
            ind = Integer.parseInt(line);
        } catch (Exception e) {}

        if (ind > tests.size() - 1 || ind < 0) {
            result.append('[');
            for (int i = 0; i < tests.size(); i++) {
                result.append(tests.get(i));
                if (i != tests.size() - 1) result.append(',');
            }
            result.append(']');
        } else result.append(tests.get(ind));
        return result.toString();
    }


    private String getHTML() {
        try (InputStream input = Server.class.getClassLoader().getResourceAsStream("index.html");
             BufferedReader in = new BufferedReader(new InputStreamReader(input))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line + "\n");
            }
            return sb.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public CopyOnWriteArrayList getTests() {
        return tests;
    }
}
