package com.kainv.http.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;

import static java.net.http.HttpRequest.*;
import static java.net.http.HttpResponse.*;

/**
 * <h1>HTTP. Servlets. 13. Класс HttpClient</h1>
 * Этот класс создан для того чтобы выступать в роли клиента к серверу, который принимает HTTP запросы. Далее, для
 * того чтобы его создать используем паттерн builder {@code .newBuilder()} для настройки HTTP клиента,
 * где можем указывать различные варианты. Например, версия {@code .version()}, аутентификатор (если необходимо
 * провести аутентификацию пользователя) {@code .authenticator()} и многое другое. Если нужен дефолтный билдер,
 * то можно использовать {@code .newHttpClient()}.
 * <pre>{@code
 *         HttpClient httpClient = HttpClient.newBuilder()
 *                 .version(HttpClient.Version.HTTP_1_1) // по дефолту стоит HTTP/2
 *                 .build();
 * }</pre>
 * Подводя итоги. Для того, чтобы работать по протоколу HTTP и отправлять наши запросы после Java 11 предпочтительно
 * использовать {@code class HttpClient}, который содержит удобный функционал, API для работы с ним и позволяет
 * передавать удобно body, если хотим использовать POST, PUT, PATH и т.д., а так же позволяет работать с нужной версией
 * HTTP протокола. Более того, {@code HttpClient()} потокобезопасный, следовательно, достаточно задать один
 * {@code HttpClient()} для работы с ним во всём приложении. {@code class HttpClient} так же позволяет работать
 * в асинхронном режиме, отправлять запрос и не дожидавшись его ответа получать
 * {@code CompletableFuture<HttpResponse<String>>}
 */
public class HttpClientExample {
    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1) // по дефолту стоит HTTP/2
                .build();

        // Создаём и отправляем HTTP GET запрос
        HttpRequest requestGET = newBuilder(URI.create("https://www.google.com"))
                .GET()
                .build();
        HttpResponse<String> responseGET = httpClient.send(requestGET, BodyHandlers.ofString());
        System.out.println(responseGET.headers());
        System.out.println(responseGET.body());

        // Создаём и отправляем HTTP POST запрос
        HttpRequest requestPOST = newBuilder(URI.create("https://www.google.com"))
                .POST(BodyPublishers.ofFile(Path.of("path", "to", "file")))
                .build();
        HttpResponse<String> responsePOST = httpClient.send(requestPOST, BodyHandlers.ofString());
        System.out.println(responsePOST.headers());
        System.out.println(responsePOST.body());
    }
}
