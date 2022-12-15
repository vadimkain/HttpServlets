package com.kainv.http.client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Каждое HTTP сообщение, независимо от того, следует оно от клиента к серверу (request) или от сервера к клиенту
 * (response), состоит из трех основных частей:
 * <ol>
 *     <li>
 *         <b>Стартовая строка</b> - определяет тип сообщения,  которой указываются следующие данные (general):
 *         <ul>
 *             <li>
 *                 <b>Request:</b>
 *                 <br>
 *                 - метод (GET, POST, PUT, DELETE, etc)
 *                 <br>
 *                 - адрес (URL)
 *                 <br>
 *                 - версия протокола (HTTP/1.1, HTTP/2)
 *             </li>
 *             <li>
 *                 <b>Response:</b>
 *                 <br>
 *                 - версия протокола (HTTP/1.1, HTTP/2)
 *                 <br>
 *                 - код состояния (1хх, 2хх, 3хх, 4хх, 5хх)
 *                 <br>
 *                 - текстовое пояснение
 *             </li>
 *         </ul>
 *     </li>
 *     <li>
 *         <b>Заголовок (header)</b> - характеризует тело сообщения (body) и параметры его передачи в виде
 *         "header_name: header_value". Другими словами, является метаинформацией HTTP сообщения.
 *     </li>
 *     <li>
 *         <b>Тело сообщения (body)</b> - это непосредственно пересылаемые данные HTTP сообщением. Отделяется от
 *         заголовка пустой строкой.
 *     </li>
 * </ol>
 * <img src="http1.png" />
 * <br>
 * <h1>HTTP. Servlets. 12. Класс URL</h1>
 */
public class UrlExample {
    public static void main(String[] args) throws IOException {
    }

    /**
     * Класс {@code Url()} из пакета {@code java.net} нужен для работы с HTTP. В качестве аргумента мы должны указать URL,
     * к кому будем обращаться. По умолчанию URL работают с методом Get, т.е. только для получения информации.
     * <pre>{@code
     *         URL url = new URL("https://www.google.com");
     *         URLConnection urlConnection = url.openConnection();
     * }</pre>
     * Из {@code url.openConnection()} можем получить всю информацию об HTTP сообщении.
     *
     * @throws IOException
     */
    private static void checkGoogle() throws IOException {
        URL url = new URL("https://www.google.com");
        URLConnection urlConnection = url.openConnection();

        // Отправка POST запроса
        urlConnection.setDoOutput(true);
        try (OutputStream outputStream = urlConnection.getOutputStream()) {
        }

        System.out.println();
    }

    private static void checkFile() throws IOException {
        URL url = new URL("file:///C:/Users/kainv/IdeaProjects/Courses%20dmdev/HttpServlets/src/com/kainv/http/socket/DatagramRunner.java");
        URLConnection urlConnection = url.openConnection();
        System.out.println(new String(urlConnection.getInputStream().readAllBytes()));
    }
}
