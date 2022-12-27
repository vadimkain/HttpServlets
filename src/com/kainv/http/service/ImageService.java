package com.kainv.http.service;

import com.kainv.http.util.PropertiesUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.*;

/**
 * <h1>HTTP. Servlets. 47. File upload. Multipart form-data</h1>
 * <h2>Сервис, который будет сохранять в папку (указана в .properties файле) все картинки.</h2>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageService {

    private final String basePATH = PropertiesUtil.get("image.base.url");

    /**
     * <p>
     * Т.к. директория, в которой будет хранится изображение может быть не создана, следовательно, на всякий случай
     * создадим все директории до картинки. Т.е. получится, что берём parent из {@code image.base.path/users/image.png}
     * (все, что лежит слева от картинки {@code image.base.path/users}) и создаём, если их нет.
     * </p>
     * <pre>{@code Files.createDirectories(imageFullPath.getParent());}</pre>
     * <br>
     * <pre>{@code Files.write(imageFullPath, imageContent.readAllBytes(), CREATE, TRUNCATE_EXISTING);}</pre>
     * <ul>
     *     <li>Параметр {@code StandardOpenOption.CREATE} говорит о том, что должны создать его, если его нет.</li>
     *     <li>{@code StandardOpenOption.TRUNCATE_EXISTING} - если пользователь сохраняет картинку второй раз с таким
     *     именем, то мы не должны фейлить наше приложение.</li>
     * </ul>
     *
     * @param imagePath    То, где будем хранить картинку (например для пользователя в директории image создавать папку user. Для самолётов
     *                     будем создавать папку aircraft и там будем хранить картинки. Следовательно, путь будем отличаться.
     * @param imageContent
     */
    public void upload(String imagePath, InputStream imageContent) {
        Path imageFullPath = Path.of(basePATH, imagePath);

        // Сохраняем по указанному пути нашу картинку
        try (imageContent) {
            Files.createDirectories(imageFullPath.getParent());
            Files.write(imageFullPath, imageContent.readAllBytes(), CREATE, TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <h1>HTTP. Servlets. 48. Tag img</h1>
     * <h2>Метод, который возвращает картинку</h2>
     * <p>
     * Возвращает {@code Optional<InputStream>}, потому что вдруг этой картинки не будет и используем
     * {@code InputStream} потому что с ним есть возможность скачивать не только маленькие файлы, но и более большие
     * или видео, то в данном случае надо держать именно {@code InputStream}, который будет скачивать все байты
     * и передавать на сторону клиента.
     * </p>
     *
     * @param imagePath
     * @return {@code Optional<InputStream>}
     */
    public Optional<InputStream> get(String imagePath) {
        // Получаем полный путь
        Path imageFullPath = Path.of(basePATH, imagePath);

        try {
            // Если такой файл существует, то считываем InputStream. В противном случае передаём empty
            // return Files.exists(imageFullPath) ? Optional.of(Files.newInputStream(imageFullPath)) : Optional.empty();
            if (Files.exists(imageFullPath)) {
                return Optional.of(Files.newInputStream(imageFullPath));
            } else {
                return Optional.empty();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static final ImageService INSTANCE = new ImageService();

    public static ImageService getInstance() {
        return INSTANCE;
    }
}
