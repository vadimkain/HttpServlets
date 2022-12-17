package com.kainv.http.server;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;

import static java.net.http.HttpRequest.BodyPublishers.*;

/**
 * <h1>Класс, который отправляет запрос</h1>
 * Передадим серверу <i>first.json</i>. Для этого создадим:
 * <pre>{@code
 *         // Создание запроса
 *         HttpRequest request = HttpRequest.newBuilder()
 *                 .uri(URI.create("http://localhost:9000"))
 *                 .header("content-type", "application/json")
 *                 // Отправляем Body по POST запросу
 *                 .POST(ofFile(Path.of("resources", "first.json")))
 *                 .build();
 * }</pre>
 */
public class HttpClientRunner {
    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        // Создание запроса
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9000"))
                .header("content-type", "application/json")
                // Отправляем в качестве body .json файл по POST запросу
                .POST(ofFile(Path.of("resources", "first.json")))
                .build();

        // Отправляем запрос и получаем html страничку от сервера
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.headers());
        System.out.println(response.body());
    }
}
