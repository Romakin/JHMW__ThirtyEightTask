package Task12;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int taskNum = 0;
        while (true) {
            if (taskNum == 0) {
                System.out.println("Введите номер задания 1|2");
                taskNum = sc.nextInt();
            } else {
                System.out.println("Введите порт для сервера и клиента: ");
                int port = sc.nextInt();
                if (Server.checkPort(port)) {
                    action(port, taskNum);
                    break;
                }
            }
        }
    }

    static void action(int port, int taskNum) {

        Server serv = new Server(port, taskNum);
        Client client = new Client(port, taskNum);

        new Thread(null, serv::start, "server").start();
        new Thread(null, client::start, "client").start();

    }

}
