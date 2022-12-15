package com.kainv.http.socket;

import java.io.IOException;
import java.net.*;

/**
 * <h1>HTTP. Servlets. 10. UDP. Класс DatagramSocket</h1>
 * <b>Напишем клиент, который будет отправлять запросы по протоколу UDP.</b> Для этого у нас есть специальный класс
 * {@code DatagramSocket()}.
 * <br>
 * <img src="socket2.png" />
 */
public class DatagramRunner {
    /**
     * В отличие от протокола TCP, DatagramSocket использует так называемые датаграм пакеты для отправки и получения
     * сообщений. Следовательно, у него есть метод {@code .send()}, который отправляет пакет класса
     * {@code DatagramPacket()}. На вход он принимает некий массив байт, он ещё называется buffer'ом. Дальше мы должны
     * передать либо просто length, т.к. размер баффера, который используется (не должен быть больше, чем длинна
     * массива), либо offset, т.е. сколько надо отступить от нашего массив и потом взять длину. По сути, сколько из
     * этого баффера используем для передачи сообщения серверу. Далее должны передать {@code InetAddress()} и порт.
     * Т.е., в отличие от TCP, адрес получателя указывает не во время создания сокета, а именно в пакете.
     * Следовательно, через один и тот же сокет мы можем отправлять пакеты совершенно разным серверам.
     * <pre>{@code
     *             DatagramPacket packet = new DatagramPacket(bytes, bytes.length, inetAddress, 7777);
     *             datagramSocket.send(packet);
     * }</pre>
     * На счёт Buffer. Buffer представляет из себя массив и кто-то записывает в него информацию, а кто-то в
     * последующем будет считывать из него (----> [buffer] <----). Следовательно, создаём массив байт:
     * <pre>{@code byte[] bytes = new byte[512];}</pre>
     * Обычно, размерность массива байт регламентируется на сервере и у клиента.
     */
    public static void main(String[] args) throws UnknownHostException {
        InetAddress inetAddress = Inet4Address.getByName("localhost");

        try (DatagramSocket datagramSocket = new DatagramSocket()) {
            byte[] bytes = new byte[512];
            bytes = "Hello from UDP client".getBytes();

            DatagramPacket packet = new DatagramPacket(bytes, bytes.length, inetAddress, 7777);
            datagramSocket.send(packet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
