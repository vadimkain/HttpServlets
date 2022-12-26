package com.kainv.http.dao;

import com.kainv.http.entity.User;
import com.kainv.http.util.ConnectionManager;

import java.sql.*;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.*;

public class UserDao implements Dao<Integer, User> {
    private static final UserDao INSTANCE = new UserDao();

    private UserDao() {
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }

    public static final String SAVE_SQL = """
            INSERT INTO flight_storage.users (name, birthday, email, password, role, gender) VALUES (?, ?, ?, ?, ?, ?); 
            """;

    @Override
    public User save(User entity) {
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, RETURN_GENERATED_KEYS);
        ) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setObject(2, entity.getBirthday());
            preparedStatement.setString(3, entity.getEmail());
            preparedStatement.setString(4, entity.getPassword());
            preparedStatement.setObject(5, entity.getRole().name());
            preparedStatement.setObject(6, entity.getGender().name());

            preparedStatement.executeUpdate();

            ResultSet userResultSet = preparedStatement.getGeneratedKeys();

            userResultSet.next();

            entity.setId(userResultSet.getInt("id"));

            return entity;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public Optional<User> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public void update(User entity) {

    }
}
