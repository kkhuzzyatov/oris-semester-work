package ru.orissemesterwork.api.user.model.repository.impl;

import ru.orissemesterwork.api.user.model.entity.User;
import ru.orissemesterwork.api.user.model.repository.UserRepository;
import ru.orissemesterwork.config.DatabaseConnectionProvider;

import java.sql.*;

public class UserRepositoryJdbcImpl implements UserRepository {
    //language=SQL
    public static final String CREATE_TABLE_SQL = """
            CREATE TABLE IF NOT EXISTS users (
                id SERIAL PRIMARY KEY,
                surname VARCHAR(255),
                name VARCHAR(255) NOT NULL,
                patronymic VARCHAR(255),
                email VARCHAR(255) UNIQUE NOT NULL,
                phone VARCHAR(11) UNIQUE NOT NULL,
                password_hash VARCHAR(255) NOT NULL,
                city_id INT,
                point_id INT,
                gender VARCHAR(10) CHECK (gender IN ('male','female'))
            )
            """;

    //language=SQL
    public static final String INSERT_SQL = """
            INSERT INTO users
                (surname, name, patronymic, email, phone, password_hash, city_id, point_id, gender)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

    //language=SQL
    public static final String SELECT_BY_EMAIL_SQL = """
            SELECT * FROM users WHERE email = ?
            """;

    public static final String ERROR_CREATE_USERS_TABLE = "Ошибка при создании таблицы users";
    public static final String ERROR_SAVE_USER = "Ошибка при сохранении пользователя";
    public static final String ERROR_FIND_USER_BY_EMAIL = "Ошибка при поиске пользователя по email";

    private final DatabaseConnectionProvider databaseConnectionProvider;

    public UserRepositoryJdbcImpl(DatabaseConnectionProvider databaseConnectionProvider) {
        this.databaseConnectionProvider = databaseConnectionProvider;
        ensureTableExists();
    }

    private void ensureTableExists() {
        try (Statement stmt = databaseConnectionProvider.getConnection().createStatement()) {
            stmt.execute(CREATE_TABLE_SQL);
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_CREATE_USERS_TABLE, e);
        }
    }

    @Override
    public void save(User user) {
        try (Connection conn = databaseConnectionProvider.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getSurname());
            ps.setString(2, user.getName());
            ps.setString(3, user.getPatronymic());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPhone());
            ps.setString(6, user.getPasswordHash());
            ps.setObject(7, user.getCityId(), Types.INTEGER);
            ps.setObject(8, user.getPointId(), Types.INTEGER);
            ps.setString(9, user.getGender());

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(ERROR_SAVE_USER, e);
        }
    }

    @Override
    public User findByEmail(String email) {
        try (Connection conn = databaseConnectionProvider.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_EMAIL_SQL)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setSurname(rs.getString("surname"));
                    user.setName(rs.getString("name"));
                    user.setPatronymic(rs.getString("patronymic"));
                    user.setEmail(rs.getString("email"));
                    user.setPhone(rs.getString("phone"));
                    user.setPasswordHash(rs.getString("passwordHash"));
                    user.setCityId(rs.getObject("city_id", Integer.class));
                    user.setPointId(rs.getObject("point_id", Integer.class));
                    user.setGender(rs.getString("gender"));
                    return user;
                } else {
                    return null;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(ERROR_FIND_USER_BY_EMAIL, e);
        }
    }
}
