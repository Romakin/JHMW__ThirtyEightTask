package Task12;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    int port;
    int task;

    public Client(int port, int task) {
        this.port = port;
        this.task = task;
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
                    if (task == 1) {
                        System.out.println("Введите порядковый номер числа фибоначи или end для завершения");
                        msg = scanner.nextLine();
                        out.println(msg);
                    } else {
                        System.out.println("Введите что нибудь с пробелами");
                        msg = scanner.nextLine();
                        out.println(msg);
                    }
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
