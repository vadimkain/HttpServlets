package com.kainv.http.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * <h1>HTTP. Servlets. 31. Скачивание файла с сервера</h1>
 * <p>
 * Чтобы получить файл от сервера - нам нужно передать дополнительный заголовок <b>content-disposition</b>.
 * Дело в том, что когда возвращаем response и указываем в заголовке, что это будет
 * <b>content-disposition: attachment; filename="filename.jpg"</b>, то браузер видит этот заголовок и вместо того,
 * чтобы отобразить эту информацию (т.е. то, что вернул request), он просто начинает скачивание файла с именем, который
 * указан в нашем параметре <b>filename=""</b> и мы видим процесс скачивания этого файла.
 * </p>
 * <p>
 * Тоже самое мы можем и загружать через какую-нибудь форму, т.е. когда хотим загрузить файл на наш сервер через форму,
 * мы так же передаём заголовок <b>content-disposition: form-data; name="fileName"; filename="filename.jpg"</b>.
 * Так мы можем сохранить файл куда-нибудь в облако и обращаться в следующий раз, когда пользователю понадобится он.
 * </p>
 * <p>
 * То есть, для передачи файла пользователю:
 * <pre>{@code content-disposition: attachment; filename="filename.*"}</pre>
 * ,а для получения и сохранения на сервер:
 * <pre>{@code content-disposition: form-data; name="fileName"; filename="filename.*"}</pre>
 * </p>
 * <h2>Реализуем такой вариант скачивания текстового файла.</h2>
 */
@WebServlet("/download")
public class DownloadServlet extends HttpServlet {
    public static void main(String[] args) {

    }

    /**
     * <h2>Метод, в котором возвращаем пользователю файл</h2>
     * Для этого передаём в заголовок соответствующее название и параметры:
     * <pre>{@code
     *         // Соответсвующий заголовок для загрузки у пользователя файла
     *         resp.setHeader("content-disposition", "attachment; filename=\"filename.txt\"");
     * }</pre>
     * Устанавливаем прочие заголовки
     * <pre>{@code
     *         // Указываем, какой MIME type возвращаем
     *         resp.setContentType("text/plain");
     *         // Устанавливаем кодировку
     *         resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
     * }</pre>
     * Записываем информацию в файл, который передаём пользователю на загрузку:
     * <pre>{@code
     *         // Writer позволяет писать текстовую информацию, а не байты
     *         try (PrintWriter printWriter = resp.getWriter()) {
     *             printWriter.write("Data from servlet!");
     *         }
     * }</pre>
     * <p><b>Передадим пользователю файл из корневой (root) директории <i>resources</i></b></p>
     * <p>Для этого мы должны обозначить директорию <i>resources</i> как root и иметь в виду, что наше реальное
     * приложение - это Apache Tomcat, а значит, для работы с файлами нам нужно использовать Reflection API у наших
     * сервлетов.</p>
     * <pre>{@code
     *         // Соответсвующий заголовок для загрузки у пользователя файла
     *         resp.setHeader("content-disposition", "attachment; filename=\"filename.txt\"");
     *         // Указываем, какой MIME type возвращаем
     *         resp.setContentType("application/json");
     *         // Устанавливаем кодировку
     *         resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
     *
     *         try (
     *                 // Writer позволяет писать текстовую информацию, а не байты
     *                 ServletOutputStream outputStream = resp.getOutputStream();
     *                 // Наше реальное приложение - Apache Tomcat, поэтому используем Reflection API для получения resources
     *                 InputStream stream = DownloadServlet.class.getClassLoader().getResourceAsStream("first.json");
     *         ) {
     *             outputStream.write(stream.readAllBytes());
     *         }
     * }</pre>
     * <p>
     *     Если зайти в папку <b>out/artifacts/HttpServlets_Web_exploded/WEB-INF/classes</b>
     *     (в HttpServlets_Web_exploded хранится архитектура разархивированного .WAR), то файл <b>first.json</b>
     *     будет в одной папке с <b>classes</b>.
     * </p>
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Соответсвующий заголовок для загрузки у пользователя файла
        resp.setHeader("content-disposition", "attachment; filename=\"filename.txt\"");
        // Указываем, какой MIME type возвращаем
        resp.setContentType("application/json");
        // Устанавливаем кодировку
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        try (
                // Writer позволяет писать текстовую информацию, а не байты
                ServletOutputStream outputStream = resp.getOutputStream();
                // Наше реальное приложение - Apache Tomcat, поэтому используем Reflection API для получения resources
                InputStream stream = DownloadServlet.class.getClassLoader().getResourceAsStream("first.json");
        ) {
            outputStream.write(stream.readAllBytes());
        }
    }
}
