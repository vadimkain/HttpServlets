package com.kainv.http.servlet;

import com.kainv.http.service.ImageService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * <h1>HTTP. Servlets. 48. Tag img</h1>
 * <h2>Этот сервлет отвечает за все картинки в приложении</h2>
 * <pre>{@code @WebServlet("/images/*")}</pre>
 * <p>
 * * - означает что-то вроде % в SQL LIKE. Т.е. нас не интересуют оставшиеся части после {@code /images/} , но все URL,
 * которые начинаются с {@code /images/} должны попасть на этот сервлет. Поэтому это и называется <b>URL PATTERN</b>, т.е.
 * не конкретное название URL, а по префиксу.
 * </p>
 */
@WebServlet("/images/*")
public class ImageServlet extends HttpServlet {

    /**
     * <p>
     * Получаем оставшеюся часть {@code /images/}
     * </p>
     * <pre>{@code String requestURI = req.getRequestURI();}</pre>
     * <ul>
     *     <li><b>URL</b> - содержит хост и порт сервера.</li>
     *     <li><b>URI</b> - только оставшуюся часть, т.е. path в URL.</li>
     * </ul>
     * <p>
     *     Получаем image path. Т.е. должны заменить {@code /images} часть на пустой значение. Таким образом, получим именно
     *     путь к картинке. Т.е., к примеру получим {@code users/image.png}
     * </p>
     * <pre>{@code String imagePath = requestURI.replace("/images", "");}</pre>
     * <p>
     *     Теперь должны в {@code ImageService} передать {@code imagePath} и он по нём вернёт картинку. Следовательно,
     *     должны получит инстанц:
     * </p>
     * <pre>{@code private final ImageService imageService = ImageService.getInstance();}</pre>
     * <p>И создать в нём соответствующий метод, через который будем получать изображение.</p>
     * <pre>{@code imageService.get(imagePath)}</pre>
     * <p>
     *     Если картинка есть, тогда передаём в {@code OutputStream}. Устанавливаем в ответ {@code content-type}
     *     значение {@code application/octet-stream} (он универсальный для всех потоков байт). После этого создать
     *     метод, который запишет картинку в выходной поток. Если картинки не оказалось, отправляем статус 404.
     * </p>
     * <pre>{@code
     *         imageService.get(imagePath)
     *                 .ifPresentOrElse(image -> {
     *                             // устанавливаем заголовок
     *                             resp.setContentType("application/octet-stream");
     *                             // записываем картинку в выходной поток
     *                             writeImage(image, resp);
     *                         },
     *                         // Если картинки нет, отправляем статус 404
     *                         () -> resp.setStatus(404));
     * }</pre>
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        String imagePath = requestURI.replace("/images", "");

        imageService.get(imagePath)
                .ifPresentOrElse(image -> {
                            // устанавливаем заголовок
                            resp.setContentType("application/octet-stream");
                            // записываем картинку в выходной поток
                            writeImage(image, resp);
                        },
                        // Если картинки нет, отправляем статус 404
                        () -> {
                            System.out.println("Отработка 404");
                            resp.setStatus(404);
                        });
    }

    /**
     * <h2>Метод, в котором перетаскиваем байты из {@code InputStream} в {@code OutputStream}</h2>
     * <p>
     * Если не получилось записать в выходной поток картинку, то пользователь должен об этом узнать. Нельзя catch'ить
     * и printStackTrace'ить
     * </p>
     * <p>
     * Теперь, т.к. иногда не хотим считывать байты сразу всем целиком, следовательно, делаем следующее: проходимся
     * циклом по файлу, записывая байт за байтов {@code OutputStream}. Используя этот подход мы можем записывать только
     * те байты файла, которые нам необходимы:
     * </p>
     * <pre>{@code
     *             int currentByte;
     *             // -1 означает, что поток байт закончен
     *             while ((currentByte = image.read()) != -1) {
     *                 outputStream.write(currentByte);
     *             }
     * }</pre>
     *
     * @param image
     * @param resp
     */

    private void writeImage(InputStream image, HttpServletResponse resp) {
        try (
                // получаем картинку
                image;
                // создаём выходной поток
                OutputStream outputStream = resp.getOutputStream();
        ) {
            // записываем байты в выходной поток
            int currentByte;
            // -1 означает, что поток байтов закончен
            while ((currentByte = image.read()) != -1) {
                outputStream.write(currentByte);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private final ImageService imageService = ImageService.getInstance();

}
