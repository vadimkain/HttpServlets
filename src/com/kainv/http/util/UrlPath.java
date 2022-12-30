package com.kainv.http.util;

import lombok.experimental.UtilityClass;

/**
 * <h1>HTTP. Servlets. 53. Authorization. Авторизация</h1>
 * <h2>Утилитный класс, который содержит в себе URI на страницы.</h2>
 * <p><i>Заметка:</i> рассмотреть возможность использования ENUM'ов или Reflection API.</p>
 */
@UtilityClass
public class UrlPath {
    public static final String LOGIN = "/login";
    public static final String REGISTRATION = "/registration";
    public static final String IMAGES = "/images";
}
