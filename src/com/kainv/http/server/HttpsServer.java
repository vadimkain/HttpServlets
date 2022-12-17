package com.kainv.http.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <h1>HTTP. Servlets. 19. Thread pooled server</h1>
 * <i>Разбираем, как Apache Tomcat выглядит внутри</i>
 * <br><br>
 * Так как мы будем использовать классы из пакета {@code java.net}, будем использовать {@code ServerSocket()}. Класс
 * для представления сервера, который будет принимать запросы от клиентов. Поэтому для сервера необходим будет порт.
 * <pre>{@code private final int PORT;}</pre>
 * <br>
 * В таких серверах, как Apache Tomcat существуют так называемые "пул соединений" и каждый из этих пулов обрабатывает
 * сокеты. Поэтому для реализации точно такого же варианта мы должны создать
 * <pre>{@code
 * private final ExecutorService pool;
 * public HttpsServer(int PORT, int poolSize)
 * {
 *     this.PORT = PORT;
 *     this.pool = Executors.newFixedThreadPool(poolSize);
 * }
 * }</pre>
 * И при создании сервера передаём размер пула.
 * <br><br>
 * Создаём переменную, которая означает остановку сервера:
 * <pre>{@code private boolean stopped;}</pre>
 * И используем её в {@code .run()}:
 * <pre>{@code
 *             while (!stopped) {
 *                 // Принимаем соединение от клиента
 *                 Socket socket = server.accept();
 *                 pool.submit(() -> processSocket(socket));
 *             }
 * }</pre>
 * После отправки сокета на обработку в нашем пуле ({@code pool.submit(() -> processSocket(socket));}), мы опять
 * возвращаемся в наш цикл, проверяем условие и опять ждём request от клиента (Socket socket = server.accept();).
 */
public class HttpsServer {

    private final int PORT;
    private final ExecutorService pool;
    private boolean stopped;

    public HttpsServer(int PORT, int poolSize) {
        this.PORT = PORT;
        this.pool = Executors.newFixedThreadPool(poolSize);
    }

    /**
     * <h2>Метод, который запускает сервер</h2>
     * Для этого используем {@code ServerSocket()}
     * <br><br>
     * Передаём пулу сокет на поточное выполнение
     * <pre>{@code pool.submit(() -> processSocket(socket));}</pre>
     */
    public void run() {
        try {
            // Создаём сервер
            ServerSocket server = new ServerSocket(PORT);
            while (!stopped) {
                // Принимаем соединение от клиента
                Socket socket = server.accept();
                System.out.println("Socket accepted");
                pool.submit(() -> processSocket(socket));
            }
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

            Thread.sleep(10000);

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

        } catch (IOException | InterruptedException e) {
            // TODO: 16/12/2022 log error message
            e.printStackTrace();
        }
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }
}
