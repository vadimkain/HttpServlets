package com.kainv.http.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * <h2>Ставим аннотации от lombok</h2>
 * <p>
 * Так, как {@code ConnectionManager} это утилитный класс, где {@code final class} и {@code private} конструктор,
 * теперь нам делать этого не нужно, можно просто поставить аннотацию:
 * <pre>{@code @UtilityClass}</pre>
 * </p>
 * <p>
 * Мы часто использовали исключения. Теперь можно этого не делать. Для этого есть специальная аннотация:
 * <pre>{@code @SneakyThrows}</pre>
 * </p>
 */
@UtilityClass
public class ConnectionManager {
    static {
        loadDriver();
    }

    private static void loadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String URL_KEY = "db.url";
    private static final String USER_KEY = "db.user";
    private static final String PASSWORD_KEY = "db.password";

   // @SneakyThrows
    public static Connection get() {
        try {
            return DriverManager.getConnection(
                    PropertiesUtil.get(URL_KEY),
                    PropertiesUtil.get(USER_KEY),
                    PropertiesUtil.get(PASSWORD_KEY)
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
