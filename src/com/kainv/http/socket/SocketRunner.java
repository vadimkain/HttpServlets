package com.kainv.http.socket;

import java.io.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class SocketRunner {
    public static void main(String[] args) throws UnknownHostException {
        // Возвращаем адрес хоста
        InetAddress inetAddress = Inet4Address.getByName("localhost");
        try (
                Socket socket = new Socket(inetAddress, 7777);
                // Создаём класс для отправки информации
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                // Создаём класс для получения информации
                DataInputStream inputStream = new DataInputStream(socket.getInputStream());

                Scanner scanner = new Scanner(System.in);
        ) {
            while (scanner.hasNextLine()) {
                String request = scanner.nextLine();
                outputStream.writeUTF(request); // Отправляем информацию
                System.out.println("response from the server: " + inputStream.readUTF());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
