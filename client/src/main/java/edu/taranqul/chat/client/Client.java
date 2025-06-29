package edu.taranqul.chat.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 8080;

        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to server at " + host + ":" + port);

            // Запускаем отдельный поток для чтения сообщений от сервера
            Thread readerThread = new Thread(() -> {
                try {
                    String message;
                    while ((message = in.readLine()) != null) {
                        System.out.println("Received: " + message);
                    }
                } catch (Exception e) {
                    System.out.println("Disconnected from server");
                }
            });
            readerThread.start();

            // Читаем ввод из консоли и отправляем серверу
            while (scanner.hasNextLine()) {
                String message = scanner.nextLine();
                if (message.equalsIgnoreCase("exit")) {
                    break;
                }
                out.println(message);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}