package com.kainv.http.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * <h1>HTTP. Servlets. 9. TCP. Класс ServerSocket</h1>
 * <br>
 * <img src="socket2.png" />
 */
public class SocketServerRunner {
    /**
     * Для того чтобы реализовать сервер, у нас есть специальный класс {@code ServerSocket()} тоже из пакета
     * {@code java.net}, который в свою очередь уже не принимает хост, потому что он и является хостом, к которому
     * будут обращаться, следовательно, там, где мы развернем наше приложение - IP той машины и будет тем IP/хостом,
     * к которому будут образаться клиенты. Следовательно, нам необходимо передать только порт:
     * <pre>{@code ServerSocket serverSocket = new ServerSocket(7777)}</pre>
     * Можно использовать также параметр backlog, который указывает количество максимальных соединений.
     * <br><br>
     * Так же, как и Сокет, он работает с обычными потоками ввода-вывода. Следовательно, у {@code ServerSocket()}
     * есть специальный метод, который возвращает {@code Socket()}. Т.е. тот клиент, который подключился к нашему
     * серверу: {@code serverSocket.accept();}. Следовательно, {.accept()} ожидаем, того, пока самый первый клиент
     * не подключиться к нашему серверу. Следовательно, можно предположить, что в один момент времени наш
     * {@code ServerSocket()} не может работать больше, чем с одним соединением, потому что пока он до тех пор не
     * вызовет метод {@code .accept()}, он не может принимать соединения. Решить эту проблему можем используя
     * многопоточность. <b></b>Метод {@code .accept()} является блокирующим. До тех пор, пока клиент не подключился
     * к {@code ServerSocket()}, мы будем ожидать в этом методе.</b>
     * <pre>{@code Socket socket = serverSocket.accept();}</pre>
     * Далее, так как мы приняли объект сокета, то вся работа сводится к работе с данными (чтение, обработка, ответ).
     */
    public static void main(String[] args) {
        try (
                ServerSocket serverSocket = new ServerSocket(7777);
                Socket socket = serverSocket.accept();
                // Подготавливаем response
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                // Подготавливаем request
                DataInputStream inputStream = new DataInputStream(socket.getInputStream());

                Scanner scanner = new Scanner(System.in);
        ) {
            String request = inputStream.readUTF(); // Читаем
            while (!"stop".equals(request)) {
                System.out.println("Client request: " + request);
                String response = scanner.nextLine();
                outputStream.writeUTF(response);
                request = inputStream.readUTF();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
