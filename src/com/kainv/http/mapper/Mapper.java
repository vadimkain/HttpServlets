package com.kainv.http.mapper;

/**
 * <h1>HTTP. Servlets. 46. Практика. Часть 2</h1>
 * <h2>Интерфейс, который занимается преобразованием одного типа F (from) в какой-то другой T (to)</h2>
 */
public interface Mapper<F, T> {
    T mapFrom(F object);
}
