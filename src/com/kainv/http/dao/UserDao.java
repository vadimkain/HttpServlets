package com.kainv.http.dao;

import com.kainv.http.entity.Gender;
import com.kainv.http.entity.Role;
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
            INSERT INTO flight_storage.users (name, birthday, email, password, role, gender, image) VALUES (?, ?, ?, ?, ?, ?, ?); 
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
            preparedStatement.setObject(7, entity.getImage());

            preparedStatement.executeUpdate();

            ResultSet userResultSet = preparedStatement.getGeneratedKeys();

            userResultSet.next();

            entity.setId(userResultSet.getInt("id"));

            return entity;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String FIND_BY_EMAIL_AND_PASSWORD_SQL = """
            SELECT * FROM flight_storage.users u WHERE u.email = ? AND u.password = ?;
            """;

    public Optional<User> findByEmailAndPassword(String email, String password) {
        try (
                Connection connection = ConnectionManager.get();
                PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_EMAIL_AND_PASSWORD_SQL);
        ) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            User user = null;

            if (resultSet.next()) {
                user = buildEntity(resultSet);
            }

            return Optional.ofNullable(user);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // К этому билдеру будем часто обращаться
    private static User buildEntity(ResultSet resultSet) throws SQLException {
        return User.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .birthday(resultSet.getObject("birthday", Date.class).toLocalDate())
                .email(resultSet.getString("email"))
                .image(resultSet.getString("image"))
                .password(resultSet.getString("password"))
                // Ищем роль. Если её не существует, то возвращаем null. Используем для необязательных полей
                .role(Role.find(resultSet.getString("role")).orElse(null))
                .gender(Gender.valueOf(resultSet.getString("gender")))
                .build();
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
