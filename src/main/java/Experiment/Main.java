package Experiment;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        int mediaPort = 0;
        Server mediaServer = null;
        Thread mediaServerThread = null,
            reportServerThread;
        while (true) {
            if (mediaPort == 0) {
                System.out.println("Введите порт для медиа сервера");
                mediaPort = sc.nextInt();
                mediaServer = new Server(mediaPort, 1, null);
                mediaServerThread = new Thread(null, mediaServer::start, "server");
                mediaServerThread.start();
                System.out.println("Медиа сервер запущен");
            } else {
                System.out.println("Введите порт для отчетного сервера и клиента: ");
                int port = sc.nextInt();
                if (Server.checkPort(port)) {
                    Server serv = new Server(port, 2, mediaServer.getTests());
                    Client client = new Client(port);
                    new Thread(null, serv::start, "server").start();
                    reportServerThread = new Thread(null, client::start, "client");
                    reportServerThread.start();
                    System.out.println("Отчетные сервер и клиент запущены");
                    break;
                }
            }
        }

        while (reportServerThread.isAlive()) {
            Thread.sleep(500);
        }
        mediaServerThread.interrupt();
    }

}
