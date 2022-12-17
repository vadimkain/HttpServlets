package com.kainv.http.server;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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
    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
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

        // Отправляем одновременно три запроса и получаем то, что обработал HttpServer
        CompletableFuture<HttpResponse<String>> response1 = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        CompletableFuture<HttpResponse<String>> response2 = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        CompletableFuture<HttpResponse<String>> response3 = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response3.get().body());
    }
}
