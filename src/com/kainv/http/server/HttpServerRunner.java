package com.kainv.http.server;

/**
 * <h1>Класс, в котором запускаем сервер</h1>
 */
public class HttpServerRunner {
    public static void main(String[] args) {
        HttpsServer httpServer = new HttpsServer(9000, 100);
        httpServer.run();
    }
}
