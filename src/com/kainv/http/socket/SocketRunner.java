package com.kainv.http.socket;

import java.io.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * <h1>HTTP. Servlets. 8. TCP. Класс Socket</h1>
 * Для работы с сетями есть два основных пакета: {@code java.net} и {@code java.nio}. В основном используется nio.
 * DatagramSocket/DatagramChannel служит для UDP протокола.
 * <br><br>
 * <img src="socket1.png" />
 * <br>
 * <img src="socket2.png" />
 */
public class SocketRunner {
    /**
     * Создаём сокет и передаём хост и порт. Порт по умолчанию http - 80, https - 443.
     * <pre>{@code Socket socket = new Socket("google.com", 80)}</pre>
     * Будем передавать данные серверу, который мы указали в качестве параметров. Каждый из объектов Socket реализует
     * интерфейс Closeable, следовательно, его нужно закрывать после использования.
     * <br><br>
     * Так как пакеты {@code java.net} и {@code java.nio} реализуют {@code java.io}, следовательно, для того чтобы
     * передать информацию нашему серверу (сейчас у нас TCP Networking и Socket это наш клиент, который должен
     * открыть соединение и передать запрос) нужно открыть соответствующий {@code OutputStream()}, куда будем
     * записывать информацию. Когда же будем получать response от нашего сервера, мы должны будем получить
     * {@code InputStream()} откуда считаем данные:
     * <pre>{@code
     *                 // Создаём класс для отправки информации
     *                 OutputStream outputStream = new DataOutputStream(socket.getOutputStream());
     *                 // Создаём класс для получения информации
     *                 InputStream inputStream = new DataInputStream(socket.getInputStream());
     * }</pre>
     * Добавили класс-обёртку {@code new DataOutputStream()} и {@code new DataInputStream()}, чтобы удобно было
     * работать с данными (Java Core - I/O).
     * <br><br>
     * <pre>{@code
     *             // Отправляем информацию
     *             outputStream.write("Hello world!".getBytes());
     *             // Получаем ответ
     *             byte[] response = inputStream.readAllBytes();
     * }</pre>
     * В {@code inputStream.readAllBytes();} наш клиент (сокет) будет ожидать до тех пор, пока не вернём запрос
     * из нашего сервера.
     * <br><br>
     * Каждый раз отправлять google.com не удобно и для этого есть специальный класс {@code InetAddress()}.
     * У него есть несколько реализаций для этого класса: {@code Inet4Address()} и {@code Inet6Address()}. Т.е.
     * по сути, это классы, которые работают с IPv4 & IPv6.
     * <pre>{@code InetAddress inetAddress = Inet4Address.getByName("google.com");}</pre>
     */
    public static void main(String[] args) throws UnknownHostException {
        // Возвращаем адрес хоста
        InetAddress inetAddress = Inet4Address.getByName("google.com");
        try (
                Socket socket = new Socket(inetAddress, 80);
                // Создаём класс для отправки информации
                OutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                // Создаём класс для получения информации
                InputStream inputStream = new DataInputStream(socket.getInputStream());
        ) {
            // Отправляем информацию
            outputStream.write("Hello world!".getBytes());
            // Получаем ответ
            byte[] response = inputStream.readAllBytes();
            System.out.println(response.length);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
