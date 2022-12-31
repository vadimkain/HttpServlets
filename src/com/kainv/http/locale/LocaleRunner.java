package com.kainv.http.locale;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * <h1>HTTP. Servlets. 54. Интернационализация и локализация. Класс Locale</h1>
 * <p>
 * В Java есть специальный класс для локалей - {@code Locale}. Для того чтобы создать объект этого класса - нам
 * необходимо передать язык и страну. Т.е., например для РФ может быть русских язык и страна RU: {@code ru_RU}. Эти
 * имена зарезервированы.
 * </p>
 * <pre>{@code Locale locale = new Locale("ru", "RU");}</pre>
 * <p>
 * Так же можно взять константу по государствам, где вернётся язык и название гос-ва.
 * </p>
 * <pre>{@code Locale.US}</pre>
 * <p>
 * В каждом приложении есть дефолтная локаль. Для этого у локали вызываем метод {@code .getDefault()} и получаем
 * дефолтную локаль, которая сейчас установлена на компьютере.
 * </p>
 * <pre>{@code Locale.getDefault()}</pre>
 * <h3>Есть второй класс {@code ResourceBundle} для того, чтобы вытягивать конкретные переводы из .properties файлов.</h3>
 * <pre>{@code ResourceBundle.getBundle("translations   ");}</pre>
 * <p>
 * Для этого вызываем метод {@code .getBundle("<название properties файла>")}. Для этого создадим properties файл,
 * который назовем <b>{@code translations.properties}</b>, где ключом будет являться то, что определим (например
 * для логин страницы поля пароль будем называть {@code page.login.password=Password}. Т.е. по ключу потом можем
 * достать значения на соответствующем языке.
 * </p>
 * <p>
 * Все эти Bundle'ы создаём с одинаковым названием, но для разных языков. Т.е. если хотим создать Bundle для
 * российского региона (т.е. для российской локали). то создаём properties файл с таким же названием, но после этого
 * нижним подчёркиванием указываем локаль: <b>{@code translations_ru_RU.properties}</b>, {@code page.login.password=Пароль}
 * </p>
 * <p>
 * Обычно, в дефолтном {@code translations.properties} лежат переводы на английском языке, а все последующие уже
 * содержат какой-то перевод на них.
 * </p>
 * <p>
 * Теперь можем вытащить bundle по значению этого файла (.properties указывать не надо). Для этого указываем ключ:
 * </p>
 * <pre>{@code
 *         ResourceBundle translations = ResourceBundle.getBundle("translations");
 *         System.out.println(translations.getString("page.login.password"));
 * }</pre>
 * <p>
 * Так же можем получить по конкретной локали:
 * </p>
 * <pre>{@code
 * ResourceBundle translations = ResourceBundle.getBundle("translations", locale);
 * System.out.println(translations.getString("page.login.password"));
 * }</pre>
 */
public class LocaleRunner {
    public static void main(String[] args) {
        Locale locale = new Locale("ru", "RU");
        System.out.println(Locale.US);
        System.out.println(Locale.getDefault());

        ResourceBundle translations = ResourceBundle.getBundle("translations", locale);
        System.out.println(translations.getString("page.login.password"));
    }
}
