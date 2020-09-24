package Experiment;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    int port;

    public Client(int port) {
        this.port = port;
    }

    public void start() {
        try (
            Socket socket = new Socket("localhost", port);
        ) {
            try (
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
                    Scanner scanner = new Scanner(System.in)
            ) {
                String msg;
                while (true) {
                    System.out.println("Введите номер пройденого теста");
                    msg = scanner.nextLine();
                    out.println(msg);
                    if ("end".equals(msg)) {
                        System.out.println("client shutdown");
                        break;
                    }
                    System.out.println("SERVER: " + in.readLine());
                }
            }
        } catch (IOException ex) {
            System.out.println("Порт занят, попробуйте другой");
        }
    }
}
