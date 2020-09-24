package Task12;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    int port;
    int task;

    public Server(int port, int task) {
        this.port = port;
        this.task = task;
    }

    public void start() {
        try (
                ServerSocket servSocket = new ServerSocket(port);
        ) {
            while (true) {
                try (
                        Socket socket = servSocket.accept();
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
                ) {
                    String line;
                    while ((line = in.readLine()) != null) {
                        try {
                            if (task == 1)
                                out.println(task1(line));
                            else
                                out.println(task2(line));
                        } catch (Exception e) {
                            out.println("Введите верное число");
                        }
                        if (line.equals("end")) {
                            System.out.println("server shutdown");
                            break;
                        }
                    }
                    if (line.equals("end")) {
                        servSocket.close();
                        return;
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

    private String task1 (String input) {
        return String.valueOf(fibonachiCount(Integer.parseInt(input)));
    }

    private String task2(String line) {
        return line.replaceAll("\\s*", "");
    }


    public int fibonachiCount(int ind) {
        int n0 = 1;
        int n1 = 1;
        switch (ind) {
            case 0: return 0;
            case 1: return n0;
            case 2: return n1;
        }
        int n2 = -1;
        for(int i = 3; i <= ind; i++){
            n2 = n0 + n1;
            n0 = n1;
            n1 = n2;
        }
        return n2;
    }

}
