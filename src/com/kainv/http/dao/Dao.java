package com.kainv.http.dao;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public interface Dao<K, T> {
    List<T> findAll();

    Optional<T> findById(K id);

    boolean delete(K id);

    void update(T entity);

    T save(T entity);
}
