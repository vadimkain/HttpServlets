package com.kainv.http.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * <b>Для реализация сервера и клиента для протокола UDP один интерфейс!</b> Отличие только в том, что при создании
 * серверы мы должны передать только порт.
 * <br><br>
 * Чтобы принять UDP пакет есть метод {@code .receive()}:
 * <pre>{@code datagramServer.receive(packet);}</pre>
 * В данном случае передаём только два или три параметра в зависимости от того, есть ли у нас offset или нет И не
 * передаём {@code InetAddress()} или port. Они нас интересуют только при отправке пакета:
 * <pre>{@code
 *             byte[] buffer = new byte[512];
 *             DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
 *             datagramServer.receive(packet);
 * }</pre>
 */
public class DatagramServerRunner {
    public static void main(String[] args) {
        try (DatagramSocket datagramServer = new DatagramSocket(7777);) {
            byte[] buffer = new byte[512];

            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            datagramServer.receive(packet);

            // Выводим данные, которые получили
            System.out.println(new String(buffer));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
