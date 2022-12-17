package com.kainv.http.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * <h1>HTTP. Servlets. 18. Single threaded server</h1>
 * <i>Разбираем, как Apache Tomcat выглядит внутри</i>
 * <br><br>
 * Так как мы будем использовать классы из пакета {@code java.net}, будем использовать {@code ServerSocket()}. Класс
 * для представления сервера, который будет принимать запросы от клиентов. Поэтому для сервера необходим будет порт.
 * <pre>{@code private final int PORT;}</pre>
 */
public class HttpsServer {

    private final int PORT;

    public HttpsServer(int PORT) {
        this.PORT = PORT;
    }

    /**
     * <h2>Метод, который запускает сервер</h2>
     * Для этого используем {@code ServerSocket()}
     */
    public void run() {
        try (ServerSocket server = new ServerSocket(PORT);) {
            // Принимаем соединение от клиента
            Socket socket = server.accept();

            // Обрабатываем соединение
            processSocket(socket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <h2>Метод, в котором работает с запросом, который прислал пользователь</h2>
     * В {@code catch (IOException e)} не выбрасываем исключение, а логируем, потому что сервер обрабатывает
     * множество запросов от клиентов и не можем из-за одно клиента прервать работу всего сервера.
     * <br><br>
     * Обработка запроса будет идти в два шага:
     * <ol>
     *     <li>Обработка request</li>
     *     <li>Обработка response, точнее его создания</li>
     * </ol>
     *
     * @param socket
     */
    private void processSocket(Socket socket) {
        try (
                socket;
                // Создаём интерфейс и получаем Request
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                // Создаём интерфейс для Response
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream())
        ) {
            // 1. Обработка request.
            // Считываем первые 400 байт из нашего запроса.
            System.out.println("Request: " + new String(dataInputStream.readNBytes(400)));

            // 2. Обработка response
            // В данном примере всем req возвращаем один response. Вернём HTML страницу.
            byte[] body = Files.readAllBytes(Path.of("resources", "example.html"));
            // В заголовки добавим стартовою строку тоже
            byte[] headers = """
                    HTTP/1.1 200 OK
                    content-type: text/html
                    content-length: %s
                    """.formatted(body.length).getBytes();
            dataOutputStream.write(headers);
            dataOutputStream.write(System.lineSeparator().getBytes());
            dataOutputStream.write(body);

        } catch (IOException e) {
            // TODO: 16/12/2022 log error message
            e.printStackTrace();
        }
    }
}
