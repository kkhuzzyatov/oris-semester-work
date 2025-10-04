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
    private static final String USER_NAME = "Камиль";
    private static final String USER_PASSWORD = "StrongPass123";

    private DatabaseConnectionProvider databaseConnectionProvider;
    private UserRepository userRepository;

    private int emailNumber = 0;
    private String generateEmail()
    { emailNumber++;
        return "test" + emailNumber + "@gmail.com";
    }

    private long currentPhone = 8_000_000_0000L;
    private String generatePhone()
    {
        currentPhone++; return Long.toString(currentPhone);
    }

    @BeforeAll
    void setupDatabase() {
        databaseConnectionProvider = ApplicationConfig.generateTestDatabaseConnectionProvider();
        userRepository = new UserRepositoryJdbcImpl(databaseConnectionProvider);
    }

    @AfterAll
    void closeConnection() throws SQLException {
        databaseConnectionProvider.getConnection().close();
    }

    @Test
    @DisplayName("Сохранение пользователя без ошибок")
    void testSaveUser() {
        User user = new User();
        user.setName(USER_NAME);
        user.setEmail(generateEmail());
        user.setPhone(generatePhone());
        user.setPassword(USER_PASSWORD);

        assertDoesNotThrow(() -> userRepository.save(user));
    }

    @Test
    @DisplayName("Получение существующего пользователя по id")
    void testFindExistingUserById() {
        User user = new User();
        user.setName(USER_NAME);
        user.setEmail(generateEmail());
        user.setPhone(generatePhone());
        user.setPassword(USER_PASSWORD);
        Integer id = userRepository.save(user);

        User found = userRepository.findById(id);
        assertNotNull(found);
        assertEquals(id, found.getId());
    }

    @Test
    @DisplayName("Получение существующего пользователя по email")
    void testFindExistingUserByEmail() {
        String email = generateEmail();
        User user = new User();
        user.setName(USER_NAME);
        user.setEmail(email);
        user.setPhone(generatePhone());
        user.setPassword(USER_PASSWORD);
        userRepository.save(user);

        User found = userRepository.findByEmail(email);
        assertNotNull(found);
        assertEquals(email, found.getEmail());
    }

    @Test
    @DisplayName("Получение существующего пользователя по phone")
    void testFindExistingUserByPhone() {
        String phone = generatePhone();
        User user = new User();
        user.setName(USER_NAME);
        user.setEmail(generateEmail());
        user.setPhone(phone);
        user.setPassword(USER_PASSWORD);
        userRepository.save(user);

        User found = userRepository.findByPhone(phone);
        assertNotNull(found);
        assertEquals(phone, found.getPhone());
    }

    @Test
    @DisplayName("Поиск по email несуществующего пользователя возвращает null")
    void testFindByEmailNonExistingUser() {
        User found = userRepository.findByEmail("nonexistent@gmail.com");
        assertNull(found);
    }

    @Test
    @DisplayName("Поиск по phone несуществующего пользователя возвращает null")
    void testFindByPhoneNonExistingUser() {
        User found = userRepository.findByPhone("80123456789");
        assertNull(found);
    }

    @Test
    @DisplayName("Два пользователя не могут иметь одинаковый email")
    void testDuplicateEmail() {
        String email = generateEmail();

        User user1 = new User();
        user1.setName(USER_NAME);
        user1.setEmail(email);
        user1.setPhone(generatePhone());
        user1.setPassword(USER_PASSWORD);
        userRepository.save(user1);

        User user2 = new User();
        user2.setName(USER_NAME);
        user2.setEmail(email);
        user2.setPhone(generatePhone());
        user2.setPassword(USER_PASSWORD);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userRepository.save(user2));
        assertTrue(exception.getMessage().contains("Ошибка при сохранении пользователя"));
    }

    @Test
    @DisplayName("Два пользователя не могут иметь одинаковый phone")
    void testDuplicatePhone() {
        String phone = generatePhone();

        User user1 = new User();
        user1.setName(USER_NAME);
        user1.setEmail(generateEmail());
        user1.setPhone(phone);
        user1.setPassword(USER_PASSWORD);
        userRepository.save(user1);

        User user2 = new User();
        user2.setName(USER_NAME);
        user2.setEmail(generateEmail());
        user2.setPhone(phone);
        user2.setPassword(USER_PASSWORD);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userRepository.save(user2));
        assertTrue(exception.getMessage().contains("Ошибка при сохранении пользователя"));
    }

    @Test
    @DisplayName("После сохранения у пользователя генерируется id")
    void testGeneratedId() {
        User user = new User();
        user.setName(USER_NAME);
        user.setEmail(generateEmail());
        user.setPhone(generatePhone());
        user.setPassword(USER_PASSWORD);

        userRepository.save(user);

        assertNotNull(user.getId());
    }
}
