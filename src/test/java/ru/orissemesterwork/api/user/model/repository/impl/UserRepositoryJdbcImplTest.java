package ru.orissemesterwork.api.user.model.repository.impl;

import org.junit.jupiter.api.*;
import ru.orissemesterwork.api.user.model.entity.User;
import ru.orissemesterwork.api.user.model.repository.UserRepository;
import ru.orissemesterwork.config.ApplicationConfig;
import ru.orissemesterwork.config.DatabaseConnectionProvider;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserRepositoryJdbcImplTest {
    private static final String USER_SURNAME = "Хуззятов";
    private static final String USER_NAME = "Камиль";
    private static final String USER_PATRONYMIC = "Артурович";
    private static final String USER_PASSWORD = "StrongPass123";

    private DatabaseConnectionProvider databaseConnectionProvider;
    private UserRepository userRepository;

    private int emailNumber = 0;
    private String generateUnusedEmail() {
        emailNumber++;
        return "test" + emailNumber + "@gmail.com";
    }

    @BeforeAll
    void setupDatabase() {
        databaseConnectionProvider = ApplicationConfig.getTestDatabaseConnectionProvider();
        userRepository = new UserRepositoryJdbcImpl(databaseConnectionProvider);
    }

    @AfterAll
    void closeConnection() throws SQLException {
        databaseConnectionProvider.getConnection().close();
    }

    @Test
    @DisplayName("Сохранение нового пользователя")
    void testSaveUser() {
        User user = new User();
        user.setSurname(USER_SURNAME);
        user.setName(USER_NAME);
        user.setPatronymic(USER_PATRONYMIC);
        user.setEmail(generateUnusedEmail());
        user.setPassword(USER_PASSWORD);

        assertDoesNotThrow(() -> userRepository.save(user));
    }

    @Test
    @DisplayName("Получение несуществующего пользователя возвращает null")
    void testFindNonExistentUser() {
        assertNull(userRepository.findByEmail("nonexistent@gmail.com"));
    }

    @Test
    @DisplayName("Проверка пароля пользователя")
    void testPasswordCheck() {
        String email = generateUnusedEmail();
        User user = new User();
        user.setSurname(USER_SURNAME);
        user.setName(USER_NAME);
        user.setPatronymic(USER_PATRONYMIC);
        user.setEmail(email);
        user.setPassword(USER_PASSWORD);
        userRepository.save(user);

        assertTrue(userRepository.findByEmail(email).checkPassword(USER_PASSWORD));
    }

    @Test
    @DisplayName("Нельзя добавить пользователя с дублирующим USER_EMAIL")
    void testDuplicateEmail() {
        String email = generateUnusedEmail();
        User user1 = new User();
        user1.setSurname(USER_SURNAME);
        user1.setName(USER_NAME);
        user1.setPatronymic(USER_PATRONYMIC);
        user1.setEmail(email);
        user1.setPassword(USER_PASSWORD);
        userRepository.save(user1);

        User user2 = new User();
        user2.setSurname(USER_SURNAME);
        user2.setName(USER_NAME);
        user2.setPatronymic(USER_PATRONYMIC);
        user2.setEmail(email);
        user2.setPassword(USER_PASSWORD);

        Exception e = assertThrows(RuntimeException.class, () -> userRepository.save(user2));
        assertTrue(e.getMessage().contains(UserRepositoryJdbcImpl.ERROR_SAVE_USER));
    }

    @Test
    @DisplayName("Проверка генерации id после сохранения")
    void testGeneratedId() {
        User user = new User();
        user.setSurname(USER_SURNAME);
        user.setName(USER_NAME);
        user.setPatronymic(USER_PATRONYMIC);
        user.setEmail(generateUnusedEmail());
        user.setPassword(USER_PASSWORD);

        userRepository.save(user);
        assertNotNull(user.getId());
    }
}
